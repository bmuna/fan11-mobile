package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fan.core.R;
import com.fan.core.adapter.MyTeam_Adapter;
import com.fan.core.model.my_team.GetPlayer;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fan.core.util.AppConstants.POINT_AND_LEAGUES;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.updateNull;

/**
 * Created by mohit.soni @ 20-Jan-20.
 */
public class My_Team extends BaseFragment {
    private final String TAG = My_Team.class.getSimpleName();
    Context context;

    private Activity activity;
    MyTeam_Adapter myTeam_adapter;

    ExpandableListView expListView;
    Responsedata responsedata;
    TextView tv_my_team_list_no_leagues;

    public My_Team(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        init(context);
        try {
            responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainFragmentActivity) context).updateMyTeam();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_teams, container, false);
        expListView = (ExpandableListView) view.findViewById(R.id.elv_my_team_list);
        tv_my_team_list_no_leagues = (TextView) view.findViewById(R.id.tv_my_team_list_no_leagues);
        sendRequest();
        return view;
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            JSONObject object = new JSONObject(responseModel.toString());
            if(object.getString("responseMessage").equals("Success")){
                Gson gson = new Gson();
                if (requestCode == POINT_AND_LEAGUES) {
                    GetPlayer bean = gson.fromJson((String) responseModel, GetPlayer.class);
                    if (bean.getResponsedata().getLeagues().size() > 0) {
                        myTeam_adapter = new MyTeam_Adapter(context, bean.getResponsedata().getLeagues(), activity);
                        expListView.setAdapter(myTeam_adapter);
                        expListView.setGroupIndicator(null);
                        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                                return false;
                            }
                        });
                        for (int i = 0; i < bean.getResponsedata().getLeagues().size(); i++) {
                            expListView.expandGroup(i);
                        }
                    }else{
                        tv_my_team_list_no_leagues.setVisibility(View.VISIBLE);
                    }
                }
            }else {
                tv_my_team_list_no_leagues.setVisibility(View.VISIBLE);
            }
            hideProgress();

        } catch (NullPointerException e) {
            tv_my_team_list_no_leagues.setVisibility(View.VISIBLE);
        } catch (JSONException e){
            log(TAG,  e.getMessage());
        }catch (JsonSyntaxException e){
            log(TAG,  e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
        hideProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void sendRequest() {
        if (AppConstants.isOnline(activity)) {
            RequestParams params = new RequestParams();
            params.put("user_id", responsedata.getId());
            postService(NetworkAPI.POINT_AND_LEAGUES, params, POINT_AND_LEAGUES);
            showDialog("Getting players", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }
}
