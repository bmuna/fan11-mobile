//package com.fan.core.module;

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager.widget.ViewPager;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toolbar;
//
//import com.fan.core.R;
//import com.fan.core.adapter.Ranking_Adapter;
//import com.fan.core.helper.PointLeague;
//import com.fan.core.model.points.Points;
//import com.fan.core.model.user.Responsedata;
//import com.fan.core.module_fragment.BaseFragment;
//import com.fan.core.module_fragment.MainFragmentActivity;
//import com.fan.core.module_fragment.Point_Ranking;
//import com.fan.core.tutorial.Main;
//import com.fan.core.util.AppConstants;
//import com.fan.core.util.CircleImageView;
//import com.fan.core.util.NetworkAPI;
//import com.fan.core.util.SharedPrefUtility;
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//import com.loopj.android.http.RequestParams;
//
//import java.util.List;
//
//import static com.fan.core.util.AppConstants.VIEW_SCORES;
//import static com.fan.core.util.AppConstants.callLanguage;
//import static com.fan.core.util.AppConstants.getUserSaved;
//import static com.fan.core.util.AppConstants.profile_pic;
//import static com.fan.core.util.AppConstants.startTutorial;
//
//public class Ranking extends BaseActivity {
//            private final String TAG = Ranking.class.getSimpleName();
//    Context context;
//        Activity activity;
//        private ViewPager viewPage;
//        private TextView tv_point_league_point, tv_point_league_league, tv_point;
//        ImageView iv_point_refresh;
//        PointLeague pointLeague = null;
//        String game_Id, user_id;
//        Responsedata responsedata;
//        RecyclerView rv_league_user;
//        LinearLayoutManager mLayoutManager;
//        Ranking_Adapter ranking_adapter;
//     TextView user_name;
//
//    public  Ranking(Context context, String game_Id, String user_id, Activity activity, TextView user_name) {
//        this.context = context;
//        this.activity = activity;
//        this.user_name = user_name;
//
//        try {
//            responsedata = getUserSaved(context);
//        } catch (NullPointerException e) {
//        }
//        this.game_Id = game_Id;
//        this.user_id = user_id;
////        init(context);
////        refresh();
//    }
//    public Ranking(){
//
//    }
//
//
//
////    public void refresh(){
////        if (AppConstants.isOnline(activity)) {
////            RequestParams params = new RequestParams();
////            params.put("user_id", user_id);
////            params.put("game_id", game_Id);
////            postService(NetworkAPI.VIEW_SCORES, params, VIEW_SCORES);
////            showDialog("Getting details", false);
////        } else {
////            showToast(getResources().getString(R.string.no_internet_connection));
////        }
////    }
//
////    @Override
////    public void onStart() {
////        super.onStart();
////        ((MainFragmentActivity) context).updateMyTeam();
////    }
//
//    private ImageView iv_toolbar_wallet_icon;
//    FragmentManager fragmentManager;
//    ProgressDialog progressDialog;
//    Toolbar tb_main_frame_2, tb_header;
//    RelativeLayout rl_main_frame_my_team,
//            rl_main_frame_pl;
//    ImageView iv_main_frame_my_team,
//            iv_main_frame_pl, iv_power;
//    ImageView iv_toolbar_back;
//    TextView tv_toolbar_head, tv_toolbar_language;
//    ImageView iv_toolbar_icon_tutorial;
//    CircleImageView iv_toolbar_profile_image;
//
//    Resources resources;
//    FrameLayout main_fragment;
//
//    LinearLayout ll_toolbar_language_change;
//    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
//    SharedPrefUtility prefUtility;
//    private Button bt_add_balance;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ranking);
//        refresh();
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
//        }
//        resources = getResources();
//        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
//        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
//        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
//        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
//        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
//        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);
//        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
//        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
//        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);
//
//        bt_add_balance = (Button) findViewById(R.id.bt_add_balance);
//
//        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
//        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
//        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Ranking.this, AddBalanceActivity.class));
//            }
//        });
//
//        if (profile_pic != null) {
//            iv_toolbar_profile_image.setImageBitmap(profile_pic);
//        }
//        try {
//            responsedata = getUserSaved(this);
//        } catch (NullPointerException e) {
//        }
//
//        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTutorial(Ranking.this);
//            }
//        });
//        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        iv_toolbar_profile_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        tv_toolbar_language_amheric.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (prefUtility != null) {
//                    prefUtility.setLanguage("ar");
//                }
//                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
//                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
//                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
//                callLanguage(Ranking.this, Ranking.this, AddBalanceActivity.class, resources, 1);
//            }
//        });
//        tv_toolbar_language_english.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (prefUtility != null) {
//                    prefUtility.setLanguage("en");
//                }
//                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
//                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
//                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.grey));
//                callLanguage(Ranking.this, Ranking.this, AddBalanceActivity.class, resources, 0);
//            }
//        });
//
////        ImageView imageView = (ImageView) findViewById(R.id.expandable_icon);
////        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_right_24));
//        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
//        rv_league_user = (RecyclerView) findViewById(R.id.rv_league_user);
//        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
//        rv_league_user.setLayoutManager(mLayoutManager);
//
//
//
//    }
//
//    public void refresh(){
//        if (AppConstants.isOnline(activity)) {
//            RequestParams params = new RequestParams();
//            params.put("user_id", user_id);
//            params.put("game_id", game_Id);
//            postService(NetworkAPI.VIEW_SCORES, params, VIEW_SCORES);
//            showDialog("Getting details", false);
//        } else {
//            showToast(getResources().getString(R.string.no_internet_connection));
//        }
//    }
//
//    @Override
//    public void onResponseListener(int requestCode, Object responseModel) {
//        try {
//            log(TAG, responseModel.toString());
////        parseJson((String) responseModel);
//            Gson gson = new Gson();
//            if (requestCode == VIEW_SCORES) {
//                Points bean = gson.fromJson((String) responseModel, Points.class);
//                ranking_adapter = new Ranking_Adapter(this, bean.getResponsedata().getRanking(), activity, user_name);
//                setPoint(bean);
//                rv_league_user.setLayoutManager(new LinearLayoutManager(this));
//                rv_league_user.setAdapter(ranking_adapter);
//                Log.d("pointtt", bean.getResponsedata().getUserPoints());
////                user_name.setText(bean.getResponsedata().getUserPoints());
//
////                bean.getResponsedata().getUserPoints();
//
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (JsonSyntaxException e){
//            e.printStackTrace();
//            showToast("No data");
//            ((MainFragmentActivity)activity).onBackPressed();
//        }
//        hideProgress();
//    }
//
//    private void setPoint(Points bean) {
//        List<com.fan.core.model.points.Ranking> list = bean.getResponsedata().getRanking();
//        for (int i = 0; i < list.size(); i++) {
//            com.fan.core.model.points.Ranking responsedatum = list.get(i);
//            responsedatum.setPoint(Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
////            Log.d("myLoggggggs", Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
//        }
//        bean.getResponsedata().setRanking(list);
//    }
//    @Override
//    public void onErrorListener(int requestCode, String error) {
//
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//}


package com.fan.core.module;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.viewpager.widget.ViewPager;

        import android.app.Activity;
        import android.content.Context;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.fan.core.R;
        import com.fan.core.adapter.Ranking_Adapter;
        import com.fan.core.helper.PointLeague;
        import com.fan.core.model.points.Points;
        import com.fan.core.model.user.Responsedata;
        import com.fan.core.module_fragment.BaseFragment;
        import com.fan.core.module_fragment.MainFragmentActivity;
        import com.fan.core.module_fragment.Point_Ranking;
        import com.fan.core.util.AppConstants;
        import com.fan.core.util.NetworkAPI;
        import com.google.gson.Gson;
        import com.google.gson.JsonSyntaxException;
        import com.loopj.android.http.RequestParams;

        import org.jetbrains.annotations.NotNull;

        import java.util.List;

        import static com.fan.core.util.AppConstants.VIEW_SCORES;
        import static com.fan.core.util.AppConstants.getUserSaved;

public class Ranking extends BaseFragment {

        private final String TAG = Ranking.class.getSimpleName();
         Context context;
        Activity activity;
        private ViewPager viewPage;
        private TextView tv_point_league_point, tv_point_league_league, tv_point;
        ImageView iv_point_refresh;
        PointLeague pointLeague = null;
        String game_Id, user_id;
        Responsedata responsedata;
        RecyclerView rv_league_user;
        LinearLayoutManager mLayoutManager;
        Ranking_Adapter ranking_adapter;
         TextView user_name;

    public  Ranking(Context context, String game_Id,String user_id, Activity activity, TextView user_name) {
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
//        refresh();
    }
    public Ranking(){

    }



//    public void refresh(){
//        if (AppConstants.isOnline(activity)) {
//            RequestParams params = new RequestParams();
//            params.put("user_id", user_id);
//            params.put("game_id", game_Id);
//            postService(NetworkAPI.VIEW_SCORES, params, VIEW_SCORES);
//            showDialog("Getting details", false);
//        } else {
//            showToast(getResources().getString(R.string.no_internet_connection));
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainFragmentActivity) context).updateMyTeam();
    }

    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ranking, container, false);
        rv_league_user = (RecyclerView)view.findViewById(R.id.rv_league_user);
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        rv_league_user.setLayoutManager(mLayoutManager);
        if (AppConstants.isOnline(activity)){
            RequestParams params = new RequestParams();
            params.put("user_id", user_id);
            params.put("game_id", game_Id);
            postService(NetworkAPI.VIEW_SCORES, params, VIEW_SCORES);
            showDialog("Getting details", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }


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
//                tv_point.setText(bean.getResponsedata().getUserPoints() + "\n" + "Points");
                ranking_adapter = new Ranking_Adapter(context, bean.getResponsedata().getRanking(), activity, user_name);
                rv_league_user.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_league_user.setAdapter(ranking_adapter);

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
        List<com.fan.core.model.points.Ranking> list = bean.getResponsedata().getRanking();
        for (int i = 0; i < list.size(); i++) {
            com.fan.core.model.points.Ranking responsedatum = list.get(i);
            responsedatum.setPoint(Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
//            Log.d("myLoggggggs", Double.toString(bean.getResponsedata().getRanking().get(i).getPoints()));
        }
        bean.getResponsedata().setRanking(list);
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }


}