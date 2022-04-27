package com.fan.core.module;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
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
import com.fan.core.model.points.Points;
import com.fan.core.model.points.Ranking;
import com.fan.core.model.points.Score;
import com.fan.core.module_fragment.BaseFragment;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.fan.core.util.AppConstants.VIEW_SCORES;
import static com.fan.core.util.AppConstants.VIEW_SCORES_NEW;

public class Point2 extends BaseFragment {
    private final String TAG = Point2.class.getSimpleName();

    final Context context;
    final String game_id;
    final String user_id;
     final LinearLayout ll_team_position;
    final Object index;
     final TextView user_name;
    final int position;
     final int loop;
    LinearLayout root_linearLayout;
    final Activity activity;
    WindowManager.LayoutParams params;
    PopupWindow popupWindow;
    View windowView;
    Ranking_Adapter ranking_adapter;
    ArrayList<Object> responses;

    LinearLayout[] res = new LinearLayout[10];
     ArrayList<View>  viewsList =new ArrayList<>(3);;


    public Point2(Context context, Activity activity,String user_id, String game_id, LinearLayout layout, Object index, TextView user_name, int position, int loop) {
//        context, activity, user_id, game_Id, ll_team_position, i, user_name, position,loop
        this.context = context;
        this.activity = activity;
        this.user_id = user_id;
        this.game_id = game_id;
        this.ll_team_position = layout;
        this.index = index;
        this.user_name = user_name;
        this.position = position;
        this.loop = loop;
        refresh(index);


    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (view != null) {
//            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
//            if (parentViewGroup != null) {
//                parentViewGroup.removeAllViews();
//            }
//        }
//    }

    public void refresh(Object a){


        if (AppConstants.isOnline(activity)) {
            RequestParams params = new RequestParams();
            params.put("user_id", user_id);
            params.put("game_id", game_id);
            params.put("field_id", a);

//           postService(NetworkAPI.GAMES_JOINED_USER, params, GAMES_JOINED_USER);
            postService(NetworkAPI.VIEW_SCORES_NEW, params, VIEW_SCORES_NEW);


//            showDialog("Getting details", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        Log.d("requet code2", "" + requestCode);
        log(TAG, responseModel.toString());

        Gson gson = new Gson();
                    if (requestCode == VIEW_SCORES_NEW) {
                        Points bean = gson.fromJson((String) responseModel, Points.class);
//                        tv_point.setText(bean.getResponsedata().getUserPoints() + "\n" + "Points");
//                        viewScoreData = bean.getResponsedata();
                        withInflater(bean.getResponsedata(), position, loop);
                         ranking_adapter = new Ranking_Adapter(context, bean.getResponsedata().getRanking(), activity, user_name);
                        setPoint(bean);
                        Log.d("pointtt", bean.getResponsedata().getUserPoints());
                    }


    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    private void setPoint(Points bean) {
        List<com.fan.core.model.points.Ranking> list = bean.getResponsedata().getRanking();
        for (int i = 0; i < list.size(); i++) {
            Ranking responsedatum = list.get(i);
            responsedatum.setPoint(Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
//            Log.d("myLoggggggs", Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
        }
        bean.getResponsedata().setRanking(list);
    }

    public void withInflater(final com.fan.core.model.points.Responsedata responsedatas, int position, int index) {
        try {
            LinearLayout.LayoutParams root_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            root_linearLayout = new LinearLayout(context);
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


                viewsList.add(position, root_linearLayout);
                                Log.d("array", "" + viewsList.get(position));

//                viewsList.add(1, root_linearLayout);
//                viewsList.add(2, root_linearLayout);
//                LinearLayout res = new LinearLayout(context);
//                if(position == 0)
//                res[0] = new LinearLayout(context);
//                res[0].addView(root_linearLayout);
//

//                Log.d("array response", "" + viewsList.get(position));


//                responses.add(position, root_linearLayout);

//                responses.add(position, root_linearLayout);

            }

//            ll_team_position.addView(viewsList.get(position));

//            games = ['1101','1102','1103']
//
//            foreach(game as games){
//                ll_team_position[game].addView(root_linearLayout[game]);
//            }
//            ll_team_position.addView(root_linearLayout);

//            if(position == 0) {
//                ll_team_position.addView(viewsList.get(position));
//            }
//            switch (position) {
//                case 0: ll_team_position.addView(root_linearLayout);
//                break;
//                case 1: ll_team_position.addView(root_linearLayout);
//                break;
//                case 2: ll_team_position.addView(root_linearLayout);
//            }
//            if(position == 0) {
//                ll_team_position.addView(root_linearLayout);
//                break;
//
//            }
//            if(position == 1) {
//                ll_team_position.addView(root_linearLayout);
//            }
//            if(position == 2) {
//                ll_team_position.addView(root_linearLayout);
//                                    Log.d("secondpage index", "" + index);
//            Log.d("secondpage postion", "" + position);
//
//            }
//              if(position == index) {
//                  ll_team_position.addView(root_linearLayout);
//////
//              }
//              }else if(position==1){
//                  ll_team_position.addView(root_linearLayout);
//
//              }
//            for (int a = 0; a <= field_loop, )
//            }
//            else if(position == 1){
//                ll_team_position.addView(root_linearLayout);
//
//
//            }
//            else if(position==2){
//                ll_team_position.addView(root_linearLayout);
//
//
//            }

//                ll_team_position.addView(root_linearLayout);

//               ll_team_position.addView(res[0]);

//            ll_team_position.addView(responses.get(0));



//           for (int i2 = 0; i2 <= field_loop; i2++) {
////               ll_team_position.addView(root_linearLayout);
////           }
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

}
