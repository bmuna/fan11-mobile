package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fan.core.R;
import com.fan.core.adapter.Ranking_Adapter;
import com.fan.core.custom_view.CustomViewPager;
import com.fan.core.helper.PointLeague;
import com.fan.core.model.points.Points;
import com.fan.core.model.points.Ranking;
import com.fan.core.model.points.Score;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.fan.core.module_fragment.Transfer2.response;
import static com.fan.core.util.AppConstants.VIEW_SCORES;
import static com.fan.core.util.AppConstants.getUserSaved;

/**
 * Created by mohit.soni @ 20-Oct-19.
 */
public class Point_Ranking extends BaseFragment {
    private final String TAG = Point_Ranking.class.getSimpleName();
    Context context;
    Activity activity;
    private ViewPager viewPage;
    private TextView tv_point_league_point, tv_point_league_league, tv_point;
    TextView iv_point_refresh;
    PointLeague pointLeague = null;
    String game_Id, user_id;
    Responsedata responsedata;

    LinearLayout ll_team_position_0, ll_team_position_1, ll_team_position_2, ll_team_position_3, ll_team_position_4,
            ll_team_position_5, ll_team_position_6, ll_team_position_7, ll_team_position_8,
            ll_team_position_9, ll_team_position_10, ll_team_position;
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

    public Point_Ranking(Context context, String game_Id,String user_id, Activity activity, TextView user_name) {
        this.context = context;
        this.activity = activity;
        this.user_name = user_name;
        try {
            responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
        this.game_Id = game_Id;
        this.user_id = user_id;
        init(context);
        refresh();
    }

    public void refresh(){
        if (AppConstants.isOnline(activity)) {
            RequestParams params = new RequestParams();
            params.put("user_id", user_id);
            params.put("game_id", game_Id);
            postService(NetworkAPI.VIEW_SCORES, params, VIEW_SCORES);
            showDialog("Getting details", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
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
        View view = inflater.inflate(R.layout.point_league, container, false);
        viewPage = (ViewPager) view.findViewById(R.id.vp_point_league);
        tv_point_league_point = (TextView) view.findViewById(R.id.tv_point_league_point);
        tv_point_league_league = (TextView) view.findViewById(R.id.tv_point_league_league);
        tv_point = (TextView) view.findViewById(R.id.tv_point);


        viewPage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                pointLeague = PointLeague.values()[position];
                int layout = pointLeague.getLayoutResId();
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layout, container, false);

                if (layout == R.layout.point_layout) {
                    /*ll_team_position_0 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_0);
                    ll_team_position_1 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_1);
                    ll_team_position_2 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_2);
                    ll_team_position_3 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_3);
                    ll_team_position_4 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_4);
                    ll_team_position_5 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_5);
                    ll_team_position_6 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_6);
                    ll_team_position_7 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_7);
                    ll_team_position_8 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_8);
                    ll_team_position_9 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_9);
                    ll_team_position_10 = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position_10);*/

                    ll_team_position = (LinearLayout) viewGroup.findViewById(R.id.ll_team_position);

                    /*tv_team_position_name_0 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_0_name);

                    tv_team_position_name_1 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_1_name);
                    tv_team_position_name_2 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_2_name);
                    tv_team_position_name_3 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_3_name);
                    tv_team_position_name_4 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_4_name);
                    tv_team_position_name_5 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_5_name);
                    tv_team_position_name_6 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_6_name);
                    tv_team_position_name_7 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_7_name);
                    tv_team_position_name_8 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_8_name);
                    tv_team_position_name_9 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_9_name);
                    tv_team_position_name_10 = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_10_name);

                    tv_team_position_name_0_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_0_point);
                    tv_team_position_name_1_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_1_point);
                    tv_team_position_name_2_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_2_point);
                    tv_team_position_name_3_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_3_point);
                    tv_team_position_name_4_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_4_point);
                    tv_team_position_name_5_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_5_point);
                    tv_team_position_name_6_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_6_point);
                    tv_team_position_name_7_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_7_point);
                    tv_team_position_name_8_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_8_point);
                    tv_team_position_name_9_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_9_point);
                    tv_team_position_name_10_point = (TextView) viewGroup.findViewById(R.id.tv_team_position_name_10_point);*/


                    tv_point = (TextView) viewGroup.findViewById(R.id.tv_point);
                    iv_point_refresh = (TextView) viewGroup.findViewById(R.id.iv_point_refresh);

                    /*iv_team_position_icon_0 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_0);
                    iv_team_position_icon_1 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_1);
                    iv_team_position_icon_2 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_2);
                    iv_team_position_icon_3 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_3);
                    iv_team_position_icon_4 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_4);
                    iv_team_position_icon_5 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_5);
                    iv_team_position_icon_6 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_6);
                    iv_team_position_icon_7 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_7);
                    iv_team_position_icon_8 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_8);
                    iv_team_position_icon_9 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_9);
                    iv_team_position_icon_10 = (ImageView) viewGroup.findViewById(R.id.iv_team_position_icon_10);*/
                    iv_point_refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refresh();
                        }
                    });
                }

                if (layout == R.layout.league_user) {
                    rv_league_user = (RecyclerView) viewGroup.findViewById(R.id.rv_league_user);
                    mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    rv_league_user.setLayoutManager(mLayoutManager);
                }
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
        viewPage.setCurrentItem(0, true);

        tv_point_league_point.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
        tv_point_league_league.setTextColor(context.getResources().getColor(R.color.white));

        final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv_point_league_point.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                    tv_point_league_league.setTextColor(context.getResources().getColor(R.color.white));
                    viewPage.setCurrentItem(0, true);
                }
                if (position == 1) {
                    tv_point_league_point.setTextColor(context.getResources().getColor(R.color.white));
                    tv_point_league_league.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                    viewPage.setCurrentItem(1, true);
                    rv_league_user.setAdapter(ranking_adapter);
                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        tv_point_league_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_point_league_point.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                tv_point_league_league.setTextColor(context.getResources().getColor(R.color.white));
                viewPage.setCurrentItem(0, true);
            }
        });
        tv_point_league_league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_point_league_point.setTextColor(context.getResources().getColor(R.color.white));
                tv_point_league_league.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                viewPage.setCurrentItem(1, true);
                rv_league_user.setAdapter(ranking_adapter);
            }
        });
//        viewPage.enableScroll();
        return view;
    }


    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
//        parseJson((String) responseModel);
            Gson gson = new Gson();
            if (requestCode == VIEW_SCORES) {
                Points bean = gson.fromJson((String) responseModel, Points.class);
                tv_point.setText(bean.getResponsedata().getUserPoints() + "\n" + "Points");
                withInflater((bean.getResponsedata()));
                ranking_adapter = new Ranking_Adapter(context, bean.getResponsedata().getRanking(), activity, user_name);
                setPoint(bean);
                Log.d("pointtt", bean.getResponsedata().getUserPoints());
//                user_name.setText(bean.getResponsedata().getUserPoints());

//                bean.getResponsedata().getUserPoints();

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e){
            e.printStackTrace();
            showToast("No data");
            ((MainFragmentActivity)context).onBackPressed();
        }
        hideProgress();
    }

    private void setPoint(Points bean) {
        List<Ranking> list = bean.getResponsedata().getRanking();
        for (int i = 0; i < list.size(); i++) {
            Ranking responsedatum = list.get(i);
            responsedatum.setPoint(Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
//            Log.d("myLoggggggs", Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
        }
        bean.getResponsedata().setRanking(list);
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * pass custom layout file
     */
    public void withInflater(final com.fan.core.model.points.Responsedata responsedatas) {
        try {
            LinearLayout.LayoutParams root_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout root_linearLayout = new LinearLayout(context);
            root_linearLayout.setOrientation(LinearLayout.VERTICAL);
            root_linearLayout.setWeightSum(4);
            root_linearLayout.setLayoutParams(root_param);
            for (int i = 0; i < 4; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(5);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.removeAllViews();
                linearLayout.setLayoutParams(params);
                if (i == 0) {
                    for (int j = 0; j < responsedatas.getGoalkeeperData().size(); j++) {
                        final int pos = j;
                        LinearLayout linearLayout_ = new LinearLayout(context);
                        LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        params_.gravity = Gravity.CENTER;
                        params_.rightMargin = 7;
                        params_.leftMargin = 7;
                        linearLayout_.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_.setWeightSum(1);
                        linearLayout_.setLayoutParams(params_);

                        ImageView iv = new ImageView(context);
                        LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                        iv_params_.gravity = Gravity.BOTTOM;
                        iv_params_.bottomMargin = 5;
                        iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                        Picasso.get().load(responsedatas.getGoalkeeperData().get(j).getPlayerImg()).into(iv);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<String> pop_data = new ArrayList<>();
                                List<Score> sroce_data = responsedatas.getGoalkeeperData().get(pos).getScores();
                                for (int i = 0; i < sroce_data.size(); i++) {
                                    if (sroce_data.size() > 0) {
                                        String data = sroce_data.get(i).getName() + "_" + sroce_data.get(i).getValue() + "_" + sroce_data.get(i).getPoint();
                                        pop_data.add(data);
                                    }
                                }
                                int[] out = new int[2];
                                v.getLocationOnScreen(out);
                                log(TAG, out[0] + ":" + out[1]);
                                try {
                                    callPopUp(out, v, pop_data);
                                } catch (WindowManager.BadTokenException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        linearLayout_.addView(iv);

                        LinearLayout linearLayout_tv = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_.gravity = Gravity.CENTER;
                        ll_params_.bottomMargin = 5;
                        linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_tv.setWeightSum(1);
                        linearLayout_tv.setLayoutParams(ll_params_);
                        linearLayout_tv.setBackground(context.getResources().getDrawable(R.drawable.player_detail_back));

                        TextView textView1 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                        ll_params_tv_1.topMargin = 6;
                        textView1.setLayoutParams(ll_params_tv_1);
                        textView1.setText(responsedatas.getGoalkeeperData().get(j).getName());
                        textView1.setTextSize(10);
                        textView1.setTypeface(Typeface.DEFAULT_BOLD);
                        textView1.setGravity(Gravity.CENTER);
                        textView1.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv.addView(textView1);

                        LinearLayout linearLayout_tv_base = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_base.gravity = Gravity.CENTER;
                        linearLayout_tv_base.setGravity(Gravity.CENTER);
                        linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base.setLayoutParams(ll_params_base);

                        TextView textView_base = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        ll_params_tv_base_1.gravity = Gravity.CENTER;
                        ll_params_tv_base_1.leftMargin = 10;
                        ll_params_tv_base_1.rightMargin = 10;
                        ll_params_tv_base_1.topMargin = 5;
                        ll_params_tv_base_1.bottomMargin = 5;
                        textView_base.setLayoutParams(ll_params_tv_base_1);
                        textView_base.setGravity(Gravity.CENTER);
                        textView_base.setText(String.valueOf(responsedatas.getGoalkeeperData().get(j).getPoints()));
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv_base.addView(textView_base);

                        final TextView textView_base_2 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(45, 35);
                        /*ll_params_tv_base_2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);*/
                        ll_params_tv_base_2.leftMargin = 10;
                        ll_params_tv_base_2.rightMargin = 10;
                        ll_params_tv_base_2.topMargin = 5;
                        ll_params_tv_base_2.bottomMargin = 5;
                        textView_base_2.setLayoutParams(ll_params_tv_base_2);
                        textView_base_2.setText(" VC ");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_2.setGravity(Gravity.CENTER);
                        textView_base_2.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_2.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getGoalkeeperData().get(j).getIsViceCaptain().equals("")) {
                            textView_base_2.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_2);


                        final TextView textView_base_3 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(35, 35);
                        ll_params_tv_base_3.leftMargin = 15;
                        ll_params_tv_base_3.rightMargin = 15;
                        ll_params_tv_base_3.topMargin = 5;
                        ll_params_tv_base_3.bottomMargin = 5;
                        textView_base_3.setLayoutParams(ll_params_tv_base_3);
                        textView_base_3.setText("C");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_3.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_3.setGravity(Gravity.CENTER);
                        textView_base_3.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_3.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getGoalkeeperData().get(j).getIsCaptain().equals("")) {
                            textView_base_3.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_3);

                        LinearLayout linearLayout_tv_base_2 = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f);
                        ll_params_base_2.gravity = Gravity.CENTER;
                        linearLayout_tv_base_2.setWeightSum(1);
                        linearLayout_tv_base_2.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base_2.setLayoutParams(ll_params_base_2);

                        linearLayout_tv.addView(linearLayout_tv_base);
                        linearLayout_tv.addView(linearLayout_tv_base_2);
                        linearLayout_.addView(linearLayout_tv);
                        linearLayout.addView(linearLayout_);
                    }
                }
                if (i == 1) {
                    for (int k = 0; k < responsedatas.getDefenderData().size(); k++) {
                        final int pos = k;
                        LinearLayout linearLayout_ = new LinearLayout(context);
                        LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        params_.gravity = Gravity.CENTER;
                        params_.rightMargin = 7;
                        params_.leftMargin = 7;
                        linearLayout_.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_.setWeightSum(1);
                        linearLayout_.setLayoutParams(params_);

                        ImageView iv = new ImageView(context);
                        LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                        iv_params_.gravity = Gravity.BOTTOM;
                        iv_params_.bottomMargin = 5;
                        iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<String> pop_data = new ArrayList<>();
                                List<Score> sroce_data = responsedatas.getDefenderData().get(pos).getScores();
                                for (int i = 0; i < sroce_data.size(); i++) {
                                    if (sroce_data.size() > 0) {
                                        String data = sroce_data.get(i).getName() + "_" + sroce_data.get(i).getValue() + "_" + sroce_data.get(i).getPoint();
                                        pop_data.add(data);
                                    }
                                }
                                int[] out = new int[2];
                                v.getLocationOnScreen(out);
                                log(TAG, out[0] + ":" + out[1]);
                                try {
                                    callPopUp(out, v, pop_data);
                                } catch (WindowManager.BadTokenException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Picasso.get().load(responsedatas.getDefenderData().get(k).getPlayerImg()).into(iv);
                        linearLayout_.addView(iv);

                        LinearLayout linearLayout_tv = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_.gravity = Gravity.CENTER;
                        ll_params_.bottomMargin = 5;
                        linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_tv.setWeightSum(1);
                        linearLayout_tv.setLayoutParams(ll_params_);
                        linearLayout_tv.setBackground(context.getResources().getDrawable(R.drawable.player_detail_back));

                        TextView textView1 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.5f);
                        ll_params_tv_1.gravity = Gravity.CENTER;
                        ll_params_tv_1.topMargin = 6;
                        textView1.setLayoutParams(ll_params_tv_1);
                        textView1.setText(responsedatas.getDefenderData().get(k).getName());
                        textView1.setTextSize(10);
                        textView1.setTypeface(Typeface.DEFAULT_BOLD);
                        textView1.setGravity(Gravity.CENTER);
                        textView1.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv.addView(textView1);

                        LinearLayout linearLayout_tv_base = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_base.gravity = Gravity.CENTER;
                        linearLayout_tv_base.setGravity(Gravity.CENTER);
                        linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base.setLayoutParams(ll_params_base);

                        TextView textView_base = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        ll_params_tv_base_1.gravity = Gravity.CENTER;
                        ll_params_tv_base_1.leftMargin = 10;
                        ll_params_tv_base_1.rightMargin = 10;
                        ll_params_tv_base_1.topMargin = 5;
                        ll_params_tv_base_1.bottomMargin = 5;
                        textView_base.setLayoutParams(ll_params_tv_base_1);
                        textView_base.setGravity(Gravity.CENTER);
                        textView_base.setText(String.valueOf(responsedatas.getDefenderData().get(k).getPoints()));
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv_base.addView(textView_base);


                        final TextView textView_base_2 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(45, 35);
                        ll_params_tv_base_2.leftMargin = 10;
                        ll_params_tv_base_2.rightMargin = 10;
                        ll_params_tv_base_2.topMargin = 5;
                        ll_params_tv_base_2.bottomMargin = 5;
                        textView_base_2.setLayoutParams(ll_params_tv_base_2);
                        textView_base_2.setText(" VC ");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_2.setGravity(Gravity.CENTER);
                        textView_base_2.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_2.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getDefenderData().get(k).getIsViceCaptain().equals("")) {
                            textView_base_2.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_2);

                        final TextView textView_base_3 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(35, 35);
                        ll_params_tv_base_3.leftMargin = 15;
                        ll_params_tv_base_3.rightMargin = 15;
                        ll_params_tv_base_3.topMargin = 5;
                        ll_params_tv_base_3.bottomMargin = 5;
                        textView_base_3.setLayoutParams(ll_params_tv_base_3);
                        textView_base_3.setText("C");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_3.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_3.setGravity(Gravity.CENTER);
                        textView_base_3.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_3.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getDefenderData().get(k).getIsCaptain().equals("")) {
                            textView_base_3.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_3);


                        LinearLayout linearLayout_tv_base_2 = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f);
                        ll_params_base_2.gravity = Gravity.CENTER;
                        linearLayout_tv_base_2.setWeightSum(1);
                        linearLayout_tv_base_2.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base_2.setLayoutParams(ll_params_base_2);

                        linearLayout_tv.addView(linearLayout_tv_base);
                        linearLayout_tv.addView(linearLayout_tv_base_2);
                        linearLayout_.addView(linearLayout_tv);
                        linearLayout.addView(linearLayout_);
                    }
                }
                if (i == 2) {
                    for (int l = 0; l < responsedatas.getCenterData().size(); l++) {
                        final int pos = l;
                        LinearLayout linearLayout_ = new LinearLayout(context);
                        LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        params_.gravity = Gravity.CENTER;
                        params_.rightMargin = 7;
                        params_.leftMargin = 7;
                        linearLayout_.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_.setWeightSum(1);
                        linearLayout_.setLayoutParams(params_);

                        ImageView iv = new ImageView(context);
                        LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                        iv_params_.gravity = Gravity.BOTTOM;
                        iv_params_.bottomMargin = 5;
                        iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                        Picasso.get().load(responsedatas.getCenterData().get(l).getPlayerImg()).into(iv);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<String> pop_data = new ArrayList<>();
                                List<Score> sroce_data = responsedatas.getCenterData().get(pos).getScores();
                                for (int i = 0; i < sroce_data.size(); i++) {
                                    if (sroce_data.size() > 0) {
                                        String data = sroce_data.get(i).getName() + "_" + sroce_data.get(i).getValue() + "_" + sroce_data.get(i).getPoint();
                                        pop_data.add(data);
                                    }
                                }
                                int[] out = new int[2];
                                v.getLocationOnScreen(out);
                                log(TAG, out[0] + ":" + out[1]);
                                try {
                                    callPopUp(out, v, pop_data);
                                } catch (WindowManager.BadTokenException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        linearLayout_.addView(iv);

                        LinearLayout linearLayout_tv = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_.gravity = Gravity.CENTER;
                        ll_params_.bottomMargin = 5;
                        linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_tv.setWeightSum(1);
                        linearLayout_tv.setLayoutParams(ll_params_);
                        linearLayout_tv.setBackground(context.getResources().getDrawable(R.drawable.player_detail_back));

                        TextView textView1 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.5f);
                        ll_params_tv_1.gravity = Gravity.CENTER;
                        ll_params_tv_1.topMargin = 6;
                        textView1.setLayoutParams(ll_params_tv_1);
                        textView1.setText(responsedatas.getCenterData().get(l).getName());
                        textView1.setTextSize(10);
                        textView1.setGravity(Gravity.CENTER);
                        textView1.setTypeface(Typeface.DEFAULT_BOLD);
                        textView1.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv.addView(textView1);

                        LinearLayout linearLayout_tv_base = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_base.gravity = Gravity.CENTER;
                        linearLayout_tv_base.setGravity(Gravity.CENTER);
                        linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base.setLayoutParams(ll_params_base);

                        TextView textView_base = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        ll_params_tv_base_1.gravity = Gravity.CENTER;
                        ll_params_tv_base_1.leftMargin = 10;
                        ll_params_tv_base_1.rightMargin = 10;
                        ll_params_tv_base_1.topMargin = 5;
                        ll_params_tv_base_1.bottomMargin = 5;
                        textView_base.setLayoutParams(ll_params_tv_base_1);
                        textView_base.setGravity(Gravity.CENTER);
                        textView_base.setText(String.valueOf(responsedatas.getCenterData().get(l).getPoints()));
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv_base.addView(textView_base);


                        final TextView textView_base_2 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(45, 35);
                        ll_params_tv_base_2.leftMargin = 10;
                        ll_params_tv_base_2.rightMargin = 10;
                        ll_params_tv_base_2.topMargin = 5;
                        ll_params_tv_base_2.bottomMargin = 5;
                        textView_base_2.setLayoutParams(ll_params_tv_base_2);
                        textView_base_2.setText(" VC ");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_2.setGravity(Gravity.CENTER);
                        textView_base_2.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_2.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getCenterData().get(l).getIsViceCaptain().equals("")) {
                            textView_base_2.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_2);

                        final TextView textView_base_3 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(35, 35);
                        ll_params_tv_base_3.leftMargin = 15;
                        ll_params_tv_base_3.rightMargin = 15;
                        ll_params_tv_base_3.topMargin = 5;
                        ll_params_tv_base_3.bottomMargin = 5;
                        textView_base_3.setLayoutParams(ll_params_tv_base_3);
                        textView_base_3.setText("C");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_3.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_3.setGravity(Gravity.CENTER);
                        textView_base_3.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_3.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getCenterData().get(l).getIsCaptain().equals("")) {
                            textView_base_3.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_3);

                        LinearLayout linearLayout_tv_base_2 = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f);
                        ll_params_base_2.gravity = Gravity.CENTER;
                        linearLayout_tv_base_2.setWeightSum(1);
                        linearLayout_tv_base_2.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base_2.setLayoutParams(ll_params_base_2);

                        linearLayout_tv.addView(linearLayout_tv_base);
                        linearLayout_tv.addView(linearLayout_tv_base_2);
                        linearLayout_.addView(linearLayout_tv);
                        linearLayout.addView(linearLayout_);
                    }
                }
                if (i == 3) {
                    for (int m = 0; m < responsedatas.getForwardData().size(); m++) {
                        final int pos = m;
                        LinearLayout linearLayout_ = new LinearLayout(context);
                        LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        params_.gravity = Gravity.CENTER;
                        params_.rightMargin = 7;
                        params_.leftMargin = 7;
                        linearLayout_.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_.setWeightSum(1);
                        linearLayout_.setLayoutParams(params_);

                        ImageView iv = new ImageView(context);
                        LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                        iv_params_.gravity = Gravity.BOTTOM;
                        iv_params_.bottomMargin = 5;
                        iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                        Picasso.get().load(responsedatas.getForwardData().get(m).getPlayerImg()).into(iv);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<String> pop_data = new ArrayList<>();
                                List<Score> sroce_data = responsedatas.getForwardData().get(pos).getScores();
                                for (int i = 0; i < sroce_data.size(); i++) {
                                    if (sroce_data.size() > 0) {
                                        String data = sroce_data.get(i).getName() + "_" + sroce_data.get(i).getValue() + "_" + sroce_data.get(i).getPoint();
                                        pop_data.add(data);
                                    }
                                }
                                int[] out = new int[2];
                                v.getLocationOnScreen(out);
                                log(TAG, out[0] + ":" + out[1]);
                                try {
                                    callPopUp(out, v, pop_data);
                                } catch (WindowManager.BadTokenException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        linearLayout_.addView(iv);

                        LinearLayout linearLayout_tv = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_.gravity = Gravity.CENTER;
                        ll_params_.bottomMargin = 5;
                        linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                        linearLayout_tv.setWeightSum(1);
                        linearLayout_tv.setLayoutParams(ll_params_);
                        linearLayout_tv.setBackground(context.getResources().getDrawable(R.drawable.player_detail_back));

                        TextView textView1 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.5f);
                        ll_params_tv_1.gravity = Gravity.CENTER;
                        ll_params_tv_1.topMargin = 6;
                        textView1.setLayoutParams(ll_params_tv_1);
                        textView1.setText(responsedatas.getForwardData().get(m).getName());
                        textView1.setTextSize(10);
                        textView1.setGravity(Gravity.CENTER);
                        textView1.setTypeface(Typeface.DEFAULT_BOLD);
                        textView1.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv.addView(textView1);

                        LinearLayout linearLayout_tv_base = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                        ll_params_base.gravity = Gravity.CENTER;
                        linearLayout_tv_base.setGravity(Gravity.CENTER);
                        linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base.setLayoutParams(ll_params_base);

                        TextView textView_base = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        ll_params_tv_base_1.gravity = Gravity.CENTER;
                        ll_params_tv_base_1.leftMargin = 10;
                        ll_params_tv_base_1.rightMargin = 10;
                        ll_params_tv_base_1.topMargin = 5;
                        ll_params_tv_base_1.bottomMargin = 5;
                        textView_base.setLayoutParams(ll_params_tv_base_1);
                        textView_base.setGravity(Gravity.CENTER);
                        textView_base.setText(String.valueOf(responsedatas.getForwardData().get(m).getPoints()));
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base.setTextColor(context.getResources().getColor(R.color.white));
                        linearLayout_tv_base.addView(textView_base);


                        final TextView textView_base_2 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(45, 35);
                        ll_params_tv_base_2.leftMargin = 10;
                        ll_params_tv_base_2.rightMargin = 10;
                        ll_params_tv_base_2.topMargin = 5;
                        ll_params_tv_base_2.bottomMargin = 5;
                        textView_base_2.setLayoutParams(ll_params_tv_base_2);
                        textView_base_2.setText(" VC ");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_2.setGravity(Gravity.CENTER);
                        textView_base_2.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_2.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getForwardData().get(m).getIsViceCaptain().equals("")) {
                            textView_base_2.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_2);

                        final TextView textView_base_3 = new TextView(context);
                        LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(35, 35);
                        ll_params_tv_base_3.leftMargin = 15;
                        ll_params_tv_base_3.rightMargin = 15;
                        ll_params_tv_base_3.topMargin = 5;
                        ll_params_tv_base_3.bottomMargin = 5;
                        textView_base_3.setLayoutParams(ll_params_tv_base_3);
                        textView_base_3.setText("C");
                        if (Build.VERSION.SDK_INT > 25) {
                            textView_base_3.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                        }
                        textView_base_3.setGravity(Gravity.CENTER);
                        textView_base_3.setTextColor(context.getResources().getColor(R.color.white));
                        textView_base_3.setBackground(context.getResources().getDrawable(R.drawable.vc_select));
                        if (responsedatas.getForwardData().get(m).getIsCaptain().equals("")) {
                            textView_base_3.setVisibility(View.GONE);
                        }
                        linearLayout_tv_base.addView(textView_base_3);

                        LinearLayout linearLayout_tv_base_2 = new LinearLayout(context);
                        LinearLayout.LayoutParams ll_params_base_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f);
                        ll_params_base_2.gravity = Gravity.CENTER;
                        linearLayout_tv_base_2.setWeightSum(1);
                        linearLayout_tv_base_2.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout_tv_base_2.setLayoutParams(ll_params_base_2);

                        linearLayout_tv.addView(linearLayout_tv_base);
                        linearLayout_tv.addView(linearLayout_tv_base_2);
                        linearLayout_.addView(linearLayout_tv);
                        linearLayout.addView(linearLayout_);
                    }
                }
                root_linearLayout.addView(linearLayout);
            }
            ll_team_position.addView(root_linearLayout);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void callPopUp(int[] out, View views, final List<String> popup_data) {
        int layout_params;
        String side = "left";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_params = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layout_params = WindowManager.LayoutParams.TYPE_PHONE;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        double width_ = width / 2.5;
        params = new WindowManager.LayoutParams(
                (int) width_,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_params,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        int x = width - out[0];
        int y = width / 2;
        log(TAG, x + "" + ", width/2 : " + width / 2);
        if (x > y) {
            params.x = out[0] + (views.getWidth());
            side = "right";
        }
        if (x < y) {
            double u = views.getWidth() * 2;
            params.x = out[0] - ((int) u);
            side = "left";
        }
        params.y = out[1] - (views.getHeight());
        params.gravity = Gravity.TOP | Gravity.LEFT;

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        windowView = inflater.inflate(R.layout.list_left_popup, null);
        LinearLayout ll_popup = (LinearLayout) windowView.findViewById(R.id.ll_popup);
        try {
            LinearLayout.LayoutParams root_param = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout root_linearLayout = new LinearLayout(context);
            root_param.leftMargin = 8;
            root_linearLayout.setOrientation(LinearLayout.VERTICAL);
            root_linearLayout.setLayoutParams(root_param);

            for (int i = 0; i < popup_data.size(); i++) {
                String[] strings = popup_data.get(i).split("_");

                LinearLayout.LayoutParams params = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
                params.leftMargin = 5;
                params.rightMargin = 5;
                params.topMargin = 7;
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(1);
                linearLayout.removeAllViews();
                linearLayout.setLayoutParams(params);


                final TextView textView_1 = new TextView(context);
                LinearLayout.LayoutParams tv_base_1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, .33f);
                tv_base_1.leftMargin = 5;
                tv_base_1.rightMargin = 5;
                textView_1.setLayoutParams(tv_base_1);
                textView_1.setText(strings[0]);
                textView_1.setTextColor(getResources().getColor(R.color.black));
                if (Build.VERSION.SDK_INT > 25) {
                    textView_1.setAutoSizeTextTypeUniformWithConfiguration(8, 10, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                }
                textView_1.setGravity(Gravity.CENTER);
                linearLayout.addView(textView_1);

                final TextView textView_2 = new TextView(context);
                LinearLayout.LayoutParams tv_base_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, .33f);
                tv_base_2.leftMargin = 5;
                tv_base_2.rightMargin = 5;
                textView_2.setLayoutParams(tv_base_2);
                textView_2.setText(strings[1]);
                textView_2.setTextColor(getResources().getColor(R.color.black));
                if (Build.VERSION.SDK_INT > 25) {
                    textView_2.setAutoSizeTextTypeUniformWithConfiguration(8, 10, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                }
                textView_2.setGravity(Gravity.CENTER);
                linearLayout.addView(textView_2);

                final TextView textView_3 = new TextView(context);
                LinearLayout.LayoutParams tv_base_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, .33f);
                tv_base_3.leftMargin = 5;
                tv_base_3.rightMargin = 5;
                textView_3.setLayoutParams(tv_base_3);
                textView_3.setText(strings[2]);
                textView_3.setTextColor(getResources().getColor(R.color.black));
                if (Build.VERSION.SDK_INT > 25) {
                    textView_3.setAutoSizeTextTypeUniformWithConfiguration(8, 10, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                }
                textView_3.setGravity(Gravity.CENTER);
                linearLayout.addView(textView_3);
                root_linearLayout.addView(linearLayout);
            }
            ll_popup.addView(root_linearLayout);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        ImageView iv_popup_right = (ImageView) windowView.findViewById(R.id.iv_popup_right);
        ImageView iv_popup_left = (ImageView) windowView.findViewById(R.id.iv_popup_left);

        if (side.equals("right")) {
            iv_popup_right.setVisibility(View.VISIBLE);
        }
        if (side.equals("left")) {
            iv_popup_left.setVisibility(View.VISIBLE);
        }

       /* lv_popup_1.setAdapter(new ArrayAdapter<String>(context, R.layout.popup_list_item, popup_data) {
            @Override
            public int getCount() {
                return popup_data.size();
            }

            @Nullable
            @Override
            public String getItem(int position) {
                return popup_data.get(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.popup_list_item, parent, false);
                    String[] strings = popup_data.get(position).split("_");
                    ((TextView) view.findViewById(R.id.tv_popup_item_1)).setText(strings[0]);
                    ((TextView) view.findViewById(R.id.tv_popup_item_2)).setText(strings[1]);
                    ((TextView) view.findViewById(R.id.tv_popup_item_3)).setText(strings[2]);
                }
                return view;
            }
        });*/
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(windowView, params.width, params.height, focusable);

        if (popup_data.size() > 0) {
            popupWindow.showAtLocation(windowView, params.gravity, params.x, params.y);
        } else {
            showToast("No data");
        }
    }

/*    public void setPlayer(com.fan.core.model.points.Responsedata responsedatas) {
        if (responsedatas.getGoalkeeperData().size() > 0) {
            for (int i = 0; i < responsedatas.getGoalkeeperData().size(); i++) {
                if (i == 0) {
                    ll_team_position_0.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getGoalkeeperData().get(0).getPlayerImg());
                    tv_team_position_name_0.setText(responsedatas.getGoalkeeperData().get(0).getName());
                    tv_team_position_name_0_point.setText(String.valueOf(responsedatas.getGoalkeeperData().get(0).getPoints()));
                }
            }
        }
        if (responsedatas.getDefenderData().size() > 0) {
            for (int i = 0; i < responsedatas.getDefenderData().size(); i++) {
                if (i == 0) {
                    ll_team_position_2.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getDefenderData().get(i).getPlayerImg());
                    tv_team_position_name_2.setText(responsedatas.getDefenderData().get(i).getName());
                    tv_team_position_name_2_point.setText(String.valueOf(responsedatas.getDefenderData().get(i).getPoints()));
                }
                if (i == 1) {
                    ll_team_position_1.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getDefenderData().get(i).getPlayerImg());
                    tv_team_position_name_1.setText(responsedatas.getDefenderData().get(i).getName());
                    tv_team_position_name_1_point.setText(String.valueOf(responsedatas.getDefenderData().get(i).getPoints()));
                }
                if (i == 2) {
                    ll_team_position_3.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getDefenderData().get(i).getPlayerImg());
                    tv_team_position_name_3.setText(responsedatas.getDefenderData().get(i).getName());
                    tv_team_position_name_3_point.setText(String.valueOf(responsedatas.getDefenderData().get(i).getPoints()));
                }
            }

        }
        if (responsedatas.getCenterData().size() > 0) {
            for (int i = 0; i < responsedatas.getCenterData().size(); i++) {
                if (i == 0) {
                    ll_team_position_5.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getCenterData().get(i).getPlayerImg());
                    tv_team_position_name_5.setText(responsedatas.getCenterData().get(i).getName());
                    tv_team_position_name_5_point.setText(String.valueOf(responsedatas.getCenterData().get(i).getPoints()));
                }
                if (i == 1) {
                    ll_team_position_4.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getCenterData().get(i).getPlayerImg());
                    tv_team_position_name_4.setText(responsedatas.getCenterData().get(i).getName());
                    tv_team_position_name_4_point.setText(String.valueOf(responsedatas.getCenterData().get(i).getPoints()));
                }
                if (i == 2) {
                    ll_team_position_6.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getCenterData().get(i).getPlayerImg());
                    tv_team_position_name_6.setText(responsedatas.getCenterData().get(i).getName());
                    tv_team_position_name_6_point.setText(String.valueOf(responsedatas.getCenterData().get(i).getPoints()));
                }
            }

        }
        if (responsedatas.getForwardData().size() > 0) {
            for (int i = 0; i < responsedatas.getForwardData().size(); i++) {
                if (i == 0) {
                    ll_team_position_8.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getForwardData().get(i).getPlayerImg());
                    tv_team_position_name_8.setText(responsedatas.getForwardData().get(i).getName());
                    tv_team_position_name_8_point.setText(String.valueOf(responsedatas.getForwardData().get(i).getPoints()));
                }
                if (i == 1) {
                    ll_team_position_9.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getForwardData().get(i).getPlayerImg());
                    tv_team_position_name_9.setText(responsedatas.getForwardData().get(i).getName());
                    tv_team_position_name_9_point.setText(String.valueOf(responsedatas.getForwardData().get(i).getPoints()));
                }
                if (i == 2) {
                    ll_team_position_7.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getForwardData().get(i).getPlayerImg());
                    tv_team_position_name_7.setText(responsedatas.getForwardData().get(i).getName());
                    tv_team_position_name_7_point.setText(String.valueOf(responsedatas.getForwardData().get(i).getPoints()));
                }
                if (i == 3) {
                    ll_team_position_10.setVisibility(View.VISIBLE);
                    Picasso.get().load(responsedatas.getForwardData().get(i).getPlayerImg());
                    tv_team_position_name_10.setText(responsedatas.getForwardData().get(i).getName());
                    tv_team_position_name_10_point.setText(String.valueOf(responsedatas.getForwardData().get(i).getPoints()));
                }
            }
        }
    }*/

    /*public void parseJson(String responseModel) {
        Points bean = new Points();
        try {
            JSONObject root = new JSONObject(responseModel);
            bean.setResponseCode(Integer.parseInt(root.getString("responseCode")));
            bean.setResponseMessage(root.getString("responseMessage"));

            com.fan.core.model.points.Responsedata response = new com.fan.core.model.points.Responsedata();
            JSONObject user_responsedata = root.getJSONObject("user_responsedata");
            response.setErrorMessage(user_responsedata.getString("errorMessage"));

            List<Ranking> ranking = new ArrayList<>();
            JSONArray ranking_json = user_responsedata.getJSONArray("ranking");
            for (int i = 0; i < ranking_json.length(); i++) {
                Ranking ran = new Ranking();
                JSONObject data = ranking_json.getJSONObject(i);
                ran.setUsername(data.getString("username"));
                ran.setProfileImage(data.getString("profile_image"));
                ran.setPoints(Double.parseDouble(data.getString("points")));
                ran.setPoint(data.getString("points"));
                ranking.add(ran);
            }
            response.setRanking(ranking);
            response.setUserPoints(Integer.parseInt(user_responsedata.getString("userPoints")));

            List<GoalkeeperDatum> goalkeeper = new ArrayList<>();
            JSONArray goalkeeperData = user_responsedata.getJSONArray("goalkeeperData");
            for (int i = 0; i < goalkeeperData.length(); i++) {
                GoalkeeperDatum goal = new GoalkeeperDatum();
                JSONObject data = goalkeeperData.getJSONObject(i);
                goal.setPlayerImg(data.getString("player_img"));
                goal.setName(data.getString("name"));
                goal.setPoints(Integer.parseInt(data.getString("points")));
                goal.setIsCaptain(data.getString("isCaptain"));
                goal.setIsViceCaptain(data.getString("isViceCaptain"));
                goalkeeper.add(goal);
            }
            response.setGoalkeeperData(goalkeeper);

            List<DefenderDatum> defender = new ArrayList<>();
            JSONArray defenderData = user_responsedata.getJSONArray("defenderData");
            for (int i = 0; i < defenderData.length(); i++) {
                DefenderDatum def = new DefenderDatum();
                JSONObject data = defenderData.getJSONObject(i);
                def.setPlayerImg(data.getString("player_img"));
                def.setName(data.getString("name"));
                def.setPoints(Integer.parseInt(data.getString("points")));
                def.setIsCaptain(data.getString("isCaptain"));
                def.setIsViceCaptain(data.getString("isViceCaptain"));
                defender.add(def);
            }
            response.setDefenderData(defender);

            List<CenterDatum> center = new ArrayList<>();
            JSONArray centerData = user_responsedata.getJSONArray("centerData");
            for (int i = 0; i < centerData.length(); i++) {
                CenterDatum cen = new CenterDatum();
                JSONObject data = centerData.getJSONObject(i);
                cen.setPlayerImg(data.getString("player_img"));
                cen.setName(data.getString("name"));
                cen.setPoints(Integer.parseInt(data.getString("points")));
                cen.setIsCaptain(data.getString("isCaptain"));
                cen.setIsViceCaptain(data.getString("isViceCaptain"));
                center.add(cen);
            }
            response.setCenterData(center);

            List<ForwardDatum> forward = new ArrayList<>();
            JSONArray forwardData = user_responsedata.getJSONArray("forwardData");
            for (int i = 0; i < forwardData.length(); i++) {
                ForwardDatum forw = new ForwardDatum();
                JSONObject data = forwardData.getJSONObject(i);
                forw.setPlayerImg(data.getString("player_img"));
                forw.setName(data.getString("name"));
                forw.setPoints(Integer.parseInt(data.getString("points")));
                forw.setIsCaptain(data.getString("isCaptain"));
                forw.setIsViceCaptain(data.getString("isViceCaptain"));
                forward.add(forw);
            }
            response.setForwardData(forward);
            bean.setResponsedata(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ranking_adapter = new Ranking_Adapter(context, bean.getResponsedata().getRanking(), activity);
        setPoint(bean);
        hideProgress();
    }*/
}
