package com.fan.core.module;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fan.core.R;
import com.fan.core.adapter.Ranking_Adapter;
import com.fan.core.helper.MyTeamSlide;
//import com.fan.core.helper.PointLeague;
import com.fan.core.model.game_joined.gameJoined;
import com.fan.core.model.points.Points;
import com.fan.core.model.points.Ranking;
import com.fan.core.model.points.Score;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module_fragment.BaseFragment;
import com.fan.core.module_fragment.League_Detail_Child;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.module_fragment.Point_Ranking;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.fan.core.util.AppConstants.VIEW_SCORES;
import static com.fan.core.util.AppConstants.GAMES_JOINED_USER;
import static com.fan.core.util.AppConstants.getUserSaved;

public class Point extends BaseFragment {

    private final String TAG = Point.class.getSimpleName();
    private final LayoutInflater layoutInflater;
    List games;


    Context context;
    Activity activity;
    private ViewPager viewPage;
    private TextView tv_point_league_point, tv_point_league_league, tv_point;
    TextView iv_point_refresh;
    MyTeamSlide slide = null;
    String game_Id, user_id, field_id;
    Responsedata responsedata;
    LinearLayout root_linearLayout;
    int places;




    LinearLayout ll_team_position_0, ll_team_position_1, ll_team_position_2, ll_team_position_3, ll_team_position_4,
            ll_team_position_5, ll_team_position_6, ll_team_position_7, ll_team_position_8,
            ll_team_position_9, ll_team_position_10;
    /*TextView tv_team_position_name_0, tv_team_position_name_1, tv_team_position_name_2, tv_team_position_name_3,
            tv_team_position_name_4, tv_team_position_name_5, tv_team_position_name_6,
            tv_team_position_name_7, tv_team_position_name_8, tv_team_position_name_9,
            tv_team_position_name_10;
    TextView tv_team_position_name_0_point, tv_team_position_name_1_point, tv_team_position_name_2_point,
            tv_team_position_name_3_point, tv_team_position_name_4_point, tv_team_position_name_5_point,
            tv_team_position_name_6_point, tv_team_position_name_7_point, tv_team_position_name_8_point,
            tv_team_position_name_9_point, tv_team_position_name_10_point;
    ImageView iv_team_position_icon_0, iv_team_position_icon_1, iv_team_position_icon_2, iv_team_position_icon_3,
            iv_team_position_icon_4, iv_team_position_icon_5, iv_team_position_icon_6,
            iv_team_position_icon_7, iv_team_position_icon_8, iv_team_position_icon_9,
            iv_team_position_icon_10;*/

    TextView user_name;
    RecyclerView rv_league_user;
    LinearLayoutManager mLayoutManager;
    Ranking_Adapter ranking_adapter;
    View windowView;
    WindowManager.LayoutParams params;
    PopupWindow popupWindow;
    LinearLayout ll_team_position;
    int field_loop;
    private Inflater mInflater;
    Object field_ids;
    private com.fan.core.model.points.Responsedata bean_data;
    private ArrayList<View> responses;
    private com.fan.core.model.points.Responsedata viewScoreData;
    private int size;
    private Object field_ids1;
    private Object field_id2;
    private gameJoined bean;
    private int looper;
    View view;
    private Object sent;


    public  Point(Context context, String game_Id, String user_id, List games, Activity activity, TextView user_name) {
        this.context = context;
        this.activity = activity;
        this.user_name = user_name;
        this.games = games;

        this.layoutInflater = LayoutInflater.from(context);


        try {
            responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
        this.game_Id = game_Id;
        this.user_id = user_id;
        init(this.context);
        refresh1();
//        refresh();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        view = inflater.inflate(R.layout.activity_point, parent, false);
        viewPage = (ViewPager) view.findViewById(R.id.slide);
        ll_team_position = (LinearLayout) view.findViewById(R.id.ll_team_position);
        tv_point = (TextView) view.findViewById(R.id.tv_point);
        iv_point_refresh = (TextView) view.findViewById(R.id.iv_point_refresh);
        tv_point_league_league = (TextView) view.findViewById(R.id.tv_point_league_league);
//        iv_point_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refresh();
//            }
//        });



        return  view;



    }



    public void refresh1(){
        if (AppConstants.isOnline(activity)) {
            RequestParams params = new RequestParams();
            params.put("user_id", user_id);
            params.put("game_id", game_Id);
            postService(NetworkAPI.GAMES_JOINED_USER, params, GAMES_JOINED_USER);
            showDialog("Getting details", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

//    public void refresh(Object a){
//        if (AppConstants.isOnline(activity)) {
//            RequestParams params = new RequestParams();
//            params.put("user_id", user_id);
//            params.put("game_id", game_Id);
//            params.put("field_id", a);
//
////            postService(NetworkAPI.GAMES_JOINED_USER, params, GAMES_JOINED_USER);
//            postService(NetworkAPI.VIEW_SCORES, params, VIEW_SCORES);
//
//
//            showDialog("Getting details", false);
//        } else {
//            showToast(getResources().getString(R.string.no_internet_connection));
//        }
//    }




    @Override
    public void onStart() {
        refresh1();
        super.onStart();
        ((MainFragmentActivity) context).updateMyTeam();
    }


    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());

            Gson gson = new Gson();
            if (requestCode == GAMES_JOINED_USER) {
                bean = gson.fromJson((String) responseModel, gameJoined.class);
                Log.d("Amounttttt", "" + bean.getResponsedata().getAmount());
                field_loop = bean.getResponsedata().getAmount();
                field_ids = bean.getResponsedata().getGames().get(0);
                field_ids1 = bean.getResponsedata().getGames().get(1);
                field_id2 = bean.getResponsedata().getGames().get(2);
                size = bean.getResponsedata().getGames().size();

                Log.d("game list", "" + bean.getResponsedata().getGames());
                Log.d("game 1", "" + bean.getResponsedata().getGames().get(0));
                Log.d("game 2", "" + bean.getResponsedata().getGames().get(1));
                Log.d("game 3", "" + bean.getResponsedata().getGames().get(2));


//                for (int i = 0; i < size; i++) {
//                    Log.d("requet code", "" + requestCode);
//                    Log.d("index total", "" + i );
//                    looplist(bean.getResponsedata().getGames().get(i), i);
//                }
////                looplist(bean.getResponsedata().getGames().get(1), 1);

//                     refresh(bean.getResponsedata().getGames().get(i));


//                    requestCode = 119;
//                    log(TAG, "rspnse model" + responseModel.toString());
//                    Log.d("requet code2", "" + requestCode);
//                    if (requestCode == VIEW_SCORES) {
//                        Points bean = gson.fromJson((String) responseModel, Points.class);
//                        tv_point.setText(bean.getResponsedata().getUserPoints() + "\n" + "Points");
//                        viewScoreData = bean.getResponsedata();
//                         ranking_adapter = new Ranking_Adapter(context, bean.getResponsedata().getRanking(), activity, user_name);
//                        setPoint(bean);
//                        Log.d("pointtt", bean.getResponsedata().getUserPoints());
//                    }

//                    looplist(looper);





            }

//            }
looplist();

            tv_point_league_league.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainFragmentActivity) context).replaceFragment(new com.fan.core.module.Ranking(context, game_Id, user_id,activity, user_name));
                }
            });


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e){
            e.printStackTrace();
            showToast("No data");
            ((MainFragmentActivity)context).onBackPressed();
        }
        hideProgress();
    }

//1134, 0
    private void looplist(){
        viewPage.setAdapter(new PagerAdapter() {
            int position;
            ViewGroup container;
            @Override
            public int getCount() {
                return size;
            }



            @NotNull
            @Override
            public Object instantiateItem(@NotNull ViewGroup container, int position) {
                this.container = container;
                this.position = position;


                Log.d("current page", "" + viewPage.getCurrentItem());


//                places = position;

//                position = loop;
//                iv_point_refresh.setText(String.valueOf(position + 1 + "/" + field_loop));

//                Log.d("places", "" + position);
                int layout = R.layout.point_layout;

                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layout, container, false);
                ll_team_position = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position);
                tv_point = (TextView) viewGroup.findViewById(R.id.tv_point);
                iv_point_refresh = (TextView) viewGroup.findViewById(R.id.iv_point_refresh);
                                iv_point_refresh.setText(String.valueOf(position + 1 + "/" + field_loop));


                for (int i = 0; i < size; i++) {
                sent = bean.getResponsedata().getGames().get(i);
                Point2 point2 = new Point2(context, activity, user_id, game_Id, ll_team_position, sent, user_name, i, i);
//
            }
//                        iv_point_refresh.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                refresh();
//                            }
//                        });
//                    for(int i = 1; i <= field_loop; i++){
//                        iv_point_refresh.setText(String.valueOf(i));
//                    }
//                    ll_team_position.addView(root_linearLayout);

//                if (layout == R.layout.point_layout) {
//                    rv_league_user = (RecyclerView) viewGroup.findViewById(R.id.rv_league_user);
//                    mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
//                    rv_league_user.setLayoutManager(mLayoutManager);
//                }
//                ((ViewPager)container).addView(viewGroup, position);


                container.addView(viewGroup);
                return viewGroup;
            }




            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
        });
    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}