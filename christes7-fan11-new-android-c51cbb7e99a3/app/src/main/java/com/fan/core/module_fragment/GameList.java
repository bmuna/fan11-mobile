package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.internal.metrics.Tag;
import com.fan.core.R;
import com.fan.core.adapter.GameList_Adapter;
import com.fan.core.model.get_games.GetGames;
import com.fan.core.model.my_balance.MyBalance;
import com.fan.core.model.transfer_list.TransferList;
import com.fan.core.model.user.Responsedata;
import com.fan.core.model.user.UserBean;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;

import static com.fan.core.util.AppConstants.GET_GAMES;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.updateNull;

/**
 * created by mohit.soni 11/9/2019 _ 4:49 PM
 */
public class GameList extends BaseFragment {

    private static final String TAG = GameList.class.getSimpleName();
    private Context context;
    private Activity activity;
    private RecyclerView rv_game_list;
    LinearLayoutManager mLayoutManager;
    TextView rv_game_list_no_record;
    SharedPrefUtility prefUtility;
  /*  private TextView tv_game_list_current, tv_game_list_completed;*/

    GameList_Adapter gameList_adapter;
    Responsedata user_responsedata;


    public GameList(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        try {
            user_responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
        init(this.context);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainFragmentActivity) context).updateGameList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_list, container, false);

        /*tv_game_list_current = (TextView) view.findViewById(R.id.tv_game_list_current);
        tv_game_list_completed = (TextView) view.findViewById(R.id.tv_game_list_completed);*/
        rv_game_list_no_record = (TextView) view.findViewById(R.id.rv_game_list_no_record);

        rv_game_list = (RecyclerView) view.findViewById(R.id.rv_game_list);
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        rv_game_list.setLayoutManager(mLayoutManager);

       /* tv_game_list_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_game_list_current.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                tv_game_list_completed.setTextColor(context.getResources().getColor(R.color.white));
                rv_game_list.setAdapter(myTeam_adapter);
                rv_game_list.scrollToPosition(myTeam_adapter.getItemCount() - 1);
            }
        });
        tv_game_list_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_game_list_current.setTextColor(context.getResources().getColor(R.color.white));
                tv_game_list_completed.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                rv_game_list.setAdapter(myTeam_adapter);
                rv_game_list.scrollToPosition(myTeam_adapter.getItemCount() - 1);
            }
        });*/
        sendRequest();
        return view;
    }


    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            Gson gson = new Gson();

//            UserBean bean2 = gson.fromJson((String) responseModel, UserBean.class);
//            String balance = bean1.getResponsedata().getCurrentBalance();
//            String money = bean2.getResponsedata().getMyBalance();
////                        money = balance;
//            Log.d("bean1",money);
//            Log.d("bean2",balance);
            log(TAG, responseModel.toString());
            if (requestCode == GET_GAMES) {
                GetGames bean = gson.fromJson((String) responseModel, GetGames.class);


                if (bean.getResponseCode() == 1) {
                    if (bean.getResponsedata().size() == 0) {
                        rv_game_list_no_record.setVisibility(View.VISIBLE);
                    }
                    if (bean.getResponsedata().size() > 0) {
//                        Log.d("myTags", responsedata.getMyBalance());
                        rv_game_list_no_record.setVisibility(View.GONE);
                        gameList_adapter = new GameList_Adapter(context, bean.getResponsedata(), activity);
//                        MyBalance bean1 = gson.fromJson((String) responseModel, MyBalance.class);

                /*tv_game_list_current.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                tv_game_list_completed.setTextColor(context.getResources().getColor(R.color.white));*/
                        rv_game_list.setAdapter(gameList_adapter);
                        rv_game_list.scrollToPosition(0);
                    }
                } else {
                    /*showToast(bean.getResponsedata().getErrorMessage());*/
                }
                hideProgress();
            }
        }catch (NullPointerException e){
            rv_game_list_no_record.setVisibility(View.VISIBLE);
        }catch (JsonSyntaxException e){
            log(TAG,e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public void sendRequest() {
        if (AppConstants.isOnline(context)) {
            showDialog("Getting games", true);
            RequestParams params = new RequestParams();
            params.put("user_id", user_responsedata.getId());
            postService(NetworkAPI.GET_GAMES, params, GET_GAMES);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
