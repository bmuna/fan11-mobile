package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.adapter.League_User_Adapter;
import com.fan.core.model.laugue_user.League;
import com.fan.core.model.laugue_user.Responsedatum;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.util.List;

import static com.fan.core.util.AppConstants.LEAGUE_USERS;
import static com.fan.core.util.AppConstants.getUserSaved;

/**
 * created by mohit.soni @ 21-Jan-20 _ 4:37 PM
 */
public class League_Detail extends BaseFragment {
    private static final String TAG = My_Team.class.getSimpleName();
    Context context;
    Activity activity;

    Responsedata responsedata;
    RecyclerView rv_league_user;
    LinearLayoutManager mLayoutManager;

    Toolbar tb_user;
    TextView number;
    TextView point;
    TextView user_name;
    TextView view_team;


    League_User_Adapter league_user_adapter;
    String leagueId = "";
    int nextVal;
     String value;

    public League_Detail(Context context, Activity activity, String leagueId) {
        this.context = context;
        this.activity = activity;
        this.leagueId = leagueId;
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
        View view = inflater.inflate(R.layout.league_user, container, false);
        rv_league_user = (RecyclerView) view.findViewById(R.id.rv_league_user);
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        user_name = (TextView) view.findViewById(R.id.user_name);
        rv_league_user.setLayoutManager(mLayoutManager);

//        tb_user = (Toolbar) view.findViewById(R.id.tb_user);
//        number = (TextView) view.findViewById(R.id.number);
//        user_name = (TextView) view.findViewById(R.id.user_name);
//        point = (TextView) view.findViewById(R.id.point);
//        view_team = (TextView) view.findViewById(R.id.view_team);
        if (AppConstants.isOnline(activity)) {
            RequestParams params = new RequestParams();
            params.put("league_id", leagueId);
            postService(NetworkAPI.LEAGUE_USERS, params, LEAGUE_USERS);
            showDialog("Getting league user", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
        return view;
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == LEAGUE_USERS) {
                League bean = gson.fromJson((String) responseModel, League.class);
                league_user_adapter = new League_User_Adapter(context, bean.getResponsedata(), activity, number, user_name,point,view_team,nextVal, value);
                setPoint(bean);
                rv_league_user.setAdapter(league_user_adapter);
                hideProgress();
//                user_name.setText("hnnii");
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }

    }

    private void setPoint(League bean) {
        List<Responsedatum> list = bean.getResponsedata();
        for (int i = 0; i < list.size(); i++) {
            Responsedatum responsedatum = list.get(i);
            responsedatum.setPoint(Double.toString(bean.getResponsedata().get(i).getPoints()));
        }
        bean.setResponsedata(list);
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

}
