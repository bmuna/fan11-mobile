package com.fan.core.module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fan.core.R;
import com.fan.core.helper.TransferPageEnum;
import com.fan.core.model.get_games.Responsedatum;
import com.fan.core.model.join.Join;
import com.fan.core.model.transfer_list.PlayerDatum;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.module_fragment.Transfer2;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.fan.core.module_fragment.Transfer2.DEF;
import static com.fan.core.module_fragment.Transfer2.FWD;
import static com.fan.core.module_fragment.Transfer2.GKP;
import static com.fan.core.module_fragment.Transfer2.MID;
import static com.fan.core.module_fragment.Transfer2.allowed;
import static com.fan.core.module_fragment.Transfer2.allowedList;
import static com.fan.core.util.AppConstants.JOIN_GAME;
import static com.fan.core.util.AppConstants.getUserSaved;

/**
 * Created by mohit.soni on 18-Jun-20.2:24 PM
 */
public class JoinGame extends BaseActivity {
    private static  final String TAG = JoinGame.class.getSimpleName();

    LinearLayout ll_join_game_position;
    TextView tv_join_game_amount;
    Button bt_join_game_reset, bt_join_game_confirm;

    Responsedata user_responsedata;
    Responsedatum response;
    TransferPageEnum transferPageEnum = null;
    Context context;


    String vc = "", c = "";
    ArrayList<TextView> tv_vc_ArrayList = new ArrayList<>();
    ArrayList<TextView> tv_c_ArrayList = new ArrayList<>();
    HashMap<Integer, String> post = new HashMap<>();
    int i, j, k, l, m, n;
    ViewPager vp_tranfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        setContentView(R.layout.join_game);

        ll_join_game_position = (LinearLayout) findViewById(R.id.ll_join_game_position);
        tv_join_game_amount = (TextView) findViewById(R.id.tv_join_game_amount);
        bt_join_game_reset = (Button) findViewById(R.id.bt_join_game_reset);
        bt_join_game_confirm = (Button) findViewById(R.id.bt_join_game_confirm);

        vp_tranfer = (ViewPager)findViewById(R.id.vp_tranfer);
//
//        vp_tranfer.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return 2;
//            }
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                transferPageEnum = transferPageEnum.values()[position];
//                int layout = transferPageEnum.getLayoutResId();
////                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(Context.).inflate(layout, container, false);
//
//                container.addView(viewGroup);
//                return viewGroup;
//            }
//
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//                return view == object;
//            }
//
//        });
        this.response = Transfer2.response;
        try {
            user_responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        ArrayList<PlayerDatum> data = new ArrayList<>(allowedList.values());
        for (PlayerDatum player : data) {
            post.put(Integer.parseInt(player.getPlayerId()), "");
        }

        bt_join_game_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GKP = new PlayerDatum[Integer.parseInt(response.getMaxGkp())];
                DEF = new PlayerDatum[Integer.parseInt(response.getMaxDef())];
                MID = new PlayerDatum[Integer.parseInt(response.getMaxMid())];
                FWD = new PlayerDatum[Integer.parseInt(response.getMaxFwd())];
                allowed.clear();
                allowedList.clear();
                finish();
            }
        });
        bt_join_game_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJoinGame();
            }
        });
        tv_join_game_amount.setText(response.getEntryFee() + " " + getResources().getString(R.string.birr));
        withInflater();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            /*parseJson((String) responseModel);*/
            Gson gson = new Gson();
            if (requestCode == JOIN_GAME) {
                Join bean = gson.fromJson((String) responseModel, Join.class);
                if (bean.getResponseMessage().equals("Success")) {
                    Toast.makeText(this, "Joined Sucessfully", Toast.LENGTH_SHORT).show();
                    allowed.clear();
                    startActivity(new Intent(JoinGame.this,MainFragmentActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, bean.getResponsedata().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                hideProgress();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch (JsonSyntaxException e){
            log(TAG,  e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public void withInflater() {
        LinearLayout.LayoutParams root_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout root_linearLayout = new LinearLayout(this);
        root_linearLayout.setOrientation(LinearLayout.VERTICAL);
        root_linearLayout.setWeightSum(4);
        root_linearLayout.setLayoutParams(root_param);
        for (i = 0; i < 4; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            final LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(5);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.removeAllViews();
            linearLayout.setLayoutParams(params);
            if (i == 0) {
                for (j = 0; j < GKP.length; j++) {
                    final LinearLayout linearLayout_ = new LinearLayout(this);
                    LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    params_.gravity = Gravity.CENTER;
                    params_.rightMargin = 7;
                    params_.leftMargin = 7;
                    linearLayout_.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_.setWeightSum(1);
                    linearLayout_.setLayoutParams(params_);
                    linearLayout_.setTag(j);

                    ImageView iv = new ImageView(this);
                    LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                    iv_params_.gravity = Gravity.BOTTOM;
                    iv_params_.bottomMargin = 5;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                    Picasso.get().load(GKP[i].getPlayerImg()).into(iv);
                    linearLayout_.addView(iv);

                    LinearLayout linearLayout_tv = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_.gravity = Gravity.CENTER;
                    ll_params_.bottomMargin = 5;
                    linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_tv.setWeightSum(1);
                    linearLayout_tv.setLayoutParams(ll_params_);
                    linearLayout_tv.setBackground(getResources().getDrawable(R.drawable.player_detail_back));

                    TextView textView1 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                    ll_params_tv_1.topMargin = 6;
                    textView1.setLayoutParams(ll_params_tv_1);
                    textView1.setText((GKP[i].getPlayerName()));
                    textView1.setTextSize(10);
                    textView1.setTypeface(Typeface.DEFAULT_BOLD);
                    textView1.setGravity(Gravity.CENTER);
                    textView1.setTextColor(getResources().getColor(R.color.white));
                    linearLayout_tv.addView(textView1);

                    LinearLayout linearLayout_tv_base = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_base.gravity = Gravity.CENTER;
                    linearLayout_tv_base.setWeightSum(1);
                    linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout_tv_base.setLayoutParams(ll_params_base);

                    final TextView textView_base_2 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                    /* ll_params_tv_base_2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);*/
                    ll_params_tv_base_2.leftMargin = 10;
                    ll_params_tv_base_2.rightMargin = 10;
                    ll_params_tv_base_2.topMargin = 5;
                    ll_params_tv_base_2.bottomMargin = 5;
                    textView_base_2.setLayoutParams(ll_params_tv_base_2);
                    textView_base_2.setText("VC");
                    if (Build.VERSION.SDK_INT > 25) {
                        textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    }
                    textView_base_2.setGravity(Gravity.CENTER);
                    textView_base_2.setTextColor(getResources().getColor(R.color.white));
                    textView_base_2.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = DEF[pos].getPlayerId();
                            vc = id;
                            updateforPost("vc", Integer.parseInt(id));
                            checkVCTextView(textView_base_2, id);
                        }
                    });
                    tv_vc_ArrayList.add(textView_base_2);
                    linearLayout_tv_base.addView(textView_base_2);


                    final TextView textView_base_3 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
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
                    textView_base_3.setTextColor(getResources().getColor(R.color.white));
                    textView_base_3.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = GKP[pos].getPlayerId();
                            c = id;
                            updateforPost("c", Integer.parseInt(id));
                            checkCTextView(textView_base_3, id);
                            /*if (vc.equals(id)) {
                                Toast.makeText(this, "Deselect as VC ", Toast.LENGTH_SHORT).show();
                            } else {
                                checkCTextView(textView_base_3, id);
                            }*/
                        }
                    });
                    tv_c_ArrayList.add(textView_base_3);
                    linearLayout_tv_base.addView(textView_base_3);

                    LinearLayout linearLayout_tv_base_2 = new LinearLayout(this);
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
                for (k = 0; k < DEF.length; k++) {
                    final LinearLayout linearLayout_ = new LinearLayout(this);
                    LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    params_.gravity = Gravity.CENTER;
                    params_.rightMargin = 7;
                    params_.leftMargin = 7;
                    linearLayout_.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_.setWeightSum(1);
                    linearLayout_.setLayoutParams(params_);
                    linearLayout_.setTag(k);

                    ImageView iv = new ImageView(this);
                    LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                    iv_params_.gravity = Gravity.BOTTOM;
                    iv_params_.bottomMargin = 5;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                    Picasso.get().load(DEF[k].getPlayerImg()).into(iv);
                    linearLayout_.addView(iv);

                    LinearLayout linearLayout_tv = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_.gravity = Gravity.CENTER;
                    ll_params_.bottomMargin = 5;
                    linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_tv.setWeightSum(1);
                    linearLayout_tv.setLayoutParams(ll_params_);
                    linearLayout_tv.setBackground(getResources().getDrawable(R.drawable.player_detail_back));

                    TextView textView1 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.5f);
                    ll_params_tv_1.gravity = Gravity.CENTER;
                    ll_params_tv_1.topMargin = 6;
                    textView1.setLayoutParams(ll_params_tv_1);
                    textView1.setText(DEF[k].getPlayerName());
                    textView1.setTextSize(10);
                    textView1.setTypeface(Typeface.DEFAULT_BOLD);
                    textView1.setGravity(Gravity.CENTER);
                    textView1.setTextColor(getResources().getColor(R.color.white));
                    linearLayout_tv.addView(textView1);

                    LinearLayout linearLayout_tv_base = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_base.gravity = Gravity.CENTER;
                    linearLayout_tv_base.setWeightSum(1);
                    linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout_tv_base.setLayoutParams(ll_params_base);

                    final TextView textView_base_2 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                    /* ll_params_tv_base_2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);*/
                    ll_params_tv_base_2.leftMargin = 10;
                    ll_params_tv_base_2.rightMargin = 10;
                    ll_params_tv_base_2.topMargin = 5;
                    ll_params_tv_base_2.bottomMargin = 5;
                    textView_base_2.setLayoutParams(ll_params_tv_base_2);
                    textView_base_2.setText("VC");
                    if (Build.VERSION.SDK_INT > 25) {
                        textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    }
                    textView_base_2.setGravity(Gravity.CENTER);
                    textView_base_2.setTextColor(this.getResources().getColor(R.color.white));
                    textView_base_2.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = DEF[pos].getPlayerId();
                            vc = id;
                            updateforPost("vc", Integer.parseInt(id));
                            checkVCTextView(textView_base_2, id);
                        }
                    });
                    tv_vc_ArrayList.add(textView_base_2);
                    linearLayout_tv_base.addView(textView_base_2);

                    final TextView textView_base_3 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
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
                    textView_base_3.setTextColor(getResources().getColor(R.color.white));
                    textView_base_3.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = DEF[pos].getPlayerId();
                            c = id;
                            updateforPost("c", Integer.parseInt(id));
                            checkCTextView(textView_base_3, id);
                        }
                    });
                    tv_c_ArrayList.add(textView_base_3);
                    textView_base_3.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    linearLayout_tv_base.addView(textView_base_3);

                    LinearLayout linearLayout_tv_base_2 = new LinearLayout(this);
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
                for (l = 0; l < MID.length; l++) {
                    final LinearLayout linearLayout_ = new LinearLayout(this);
                    LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    params_.gravity = Gravity.CENTER;
                    params_.rightMargin = 7;
                    params_.leftMargin = 7;
                    linearLayout_.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_.setWeightSum(1);
                    linearLayout_.setLayoutParams(params_);
                    linearLayout_.setTag(l);

                    ImageView iv = new ImageView(this);
                    LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                    iv_params_.gravity = Gravity.BOTTOM;
                    iv_params_.bottomMargin = 5;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                    Picasso.get().load(MID[l].getPlayerImg()).into(iv);
                    linearLayout_.addView(iv);

                    LinearLayout linearLayout_tv = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_.gravity = Gravity.CENTER;
                    ll_params_.bottomMargin = 5;
                    linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_tv.setWeightSum(1);
                    linearLayout_tv.setLayoutParams(ll_params_);
                    linearLayout_tv.setBackground(getResources().getDrawable(R.drawable.player_detail_back));

                    TextView textView1 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.5f);
                    ll_params_tv_1.gravity = Gravity.CENTER;
                    ll_params_tv_1.topMargin = 6;
                    textView1.setLayoutParams(ll_params_tv_1);
                    textView1.setText(MID[l].getPlayerName());
                    textView1.setTextSize(10);
                    textView1.setGravity(Gravity.CENTER);
                    textView1.setTypeface(Typeface.DEFAULT_BOLD);
                    textView1.setTextColor(getResources().getColor(R.color.white));
                    linearLayout_tv.addView(textView1);

                    LinearLayout linearLayout_tv_base = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_base.gravity = Gravity.CENTER;
                    linearLayout_tv_base.setWeightSum(1);
                    linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout_tv_base.setLayoutParams(ll_params_base);

                    final TextView textView_base_2 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                    /*ll_params_tv_base_2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);*/
                    ll_params_tv_base_2.leftMargin = 10;
                    ll_params_tv_base_2.rightMargin = 10;
                    ll_params_tv_base_2.topMargin = 5;
                    ll_params_tv_base_2.bottomMargin = 5;
                    textView_base_2.setLayoutParams(ll_params_tv_base_2);
                    textView_base_2.setText("VC");
                    if (Build.VERSION.SDK_INT > 25) {
                        textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    }
                    textView_base_2.setGravity(Gravity.CENTER);
                    textView_base_2.setTextColor(getResources().getColor(R.color.white));
                    textView_base_2.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = MID[pos].getPlayerId();
                            vc = id;
                            updateforPost("vc", Integer.parseInt(id));
                            checkVCTextView(textView_base_2, id);
                        }
                    });
                    tv_vc_ArrayList.add(textView_base_2);
                    linearLayout_tv_base.addView(textView_base_2);

                    final TextView textView_base_3 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
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
                    textView_base_3.setTextColor(getResources().getColor(R.color.white));
                    textView_base_3.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = MID[pos].getPlayerId();
                            c = id;
                            updateforPost("c", Integer.parseInt(id));
                            checkCTextView(textView_base_3, id);
                        }
                    });
                    tv_c_ArrayList.add(textView_base_3);
                    linearLayout_tv_base.addView(textView_base_3);

                    LinearLayout linearLayout_tv_base_2 = new LinearLayout(this);
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
                for (m = 0; m < FWD.length; m++) {
                    final LinearLayout linearLayout_ = new LinearLayout(this);
                    LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    params_.gravity = Gravity.CENTER;
                    params_.rightMargin = 7;
                    params_.leftMargin = 7;
                    linearLayout_.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_.setWeightSum(1);
                    linearLayout_.setLayoutParams(params_);
                    linearLayout_.setTag(m);

                    ImageView iv = new ImageView(this);
                    LinearLayout.LayoutParams iv_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
                    iv_params_.gravity = Gravity.BOTTOM;
                    iv_params_.bottomMargin = 5;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(iv_params_));
                    Picasso.get().load(FWD[m].getPlayerImg()).into(iv);
                    linearLayout_.addView(iv);

                    LinearLayout linearLayout_tv = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_ = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_.gravity = Gravity.CENTER;
                    ll_params_.bottomMargin = 5;
                    linearLayout_tv.setOrientation(LinearLayout.VERTICAL);
                    linearLayout_tv.setWeightSum(1);
                    linearLayout_tv.setLayoutParams(ll_params_);
                    linearLayout_tv.setBackground(getResources().getDrawable(R.drawable.player_detail_back));

                    TextView textView1 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0.5f);
                    ll_params_tv_1.gravity = Gravity.CENTER;
                    ll_params_tv_1.topMargin = 6;
                    textView1.setLayoutParams(ll_params_tv_1);
                    textView1.setText(FWD[m].getPlayerName().replaceAll("-", "\n "));
                    textView1.setTextSize(10);
                    textView1.setGravity(Gravity.CENTER);
                    textView1.setTypeface(Typeface.DEFAULT_BOLD);
                    textView1.setTextColor(getResources().getColor(R.color.white));
                    linearLayout_tv.addView(textView1);

                    LinearLayout linearLayout_tv_base = new LinearLayout(this);
                    LinearLayout.LayoutParams ll_params_base = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f);
                    ll_params_base.gravity = Gravity.CENTER;
                    linearLayout_tv_base.setWeightSum(1);
                    linearLayout_tv_base.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout_tv_base.setLayoutParams(ll_params_base);

                    final TextView textView_base_2 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                    /*ll_params_tv_base_2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);*/
                    ll_params_tv_base_2.leftMargin = 10;
                    ll_params_tv_base_2.rightMargin = 10;
                    ll_params_tv_base_2.topMargin = 5;
                    ll_params_tv_base_2.bottomMargin = 5;
                    textView_base_2.setLayoutParams(ll_params_tv_base_2);
                    textView_base_2.setText("VC");
                    if (Build.VERSION.SDK_INT > 25) {
                        textView_base_2.setAutoSizeTextTypeUniformWithConfiguration(8, 15, 2, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    }
                    textView_base_2.setGravity(Gravity.CENTER);
                    textView_base_2.setTextColor(getResources().getColor(R.color.white));
                    textView_base_2.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = FWD[pos].getPlayerId();
                            vc = id;
                            updateforPost("vc", Integer.parseInt(id));
                            checkVCTextView(textView_base_2, id);
                        }
                    });
                    tv_vc_ArrayList.add(textView_base_2);
                    linearLayout_tv_base.addView(textView_base_2);

                    final TextView textView_base_3 = new TextView(this);
                    LinearLayout.LayoutParams ll_params_tv_base_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
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
                    textView_base_3.setTextColor(getResources().getColor(R.color.white));
                    textView_base_3.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
                    textView_base_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) linearLayout_.getTag();
                            String id = FWD[pos].getPlayerId();
                            c = id;
                            updateforPost("c", Integer.parseInt(id));
                            checkCTextView(textView_base_3, id);
                        }
                    });
                    tv_c_ArrayList.add(textView_base_3);
                    linearLayout_tv_base.addView(textView_base_3);

                    LinearLayout linearLayout_tv_base_2 = new LinearLayout(this);
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
        ll_join_game_position.addView(root_linearLayout);
    }

    public void sendJoinGame() {
        if (AppConstants.isOnline(this)) {
            if (c.equals("") || vc.equals("")) {
                Toast.makeText(this, "PLease select as C and VC", Toast.LENGTH_SHORT).show();
            } else {
//                Log.d("myMoney", user_responsedata.get);
                showDialog("Joining Game", true);
                RequestParams params = new RequestParams();
                params.put("user_id", user_responsedata.getId());
                params.put("game_id", response.getId());
                params.put("no_of_selected_players", response.getNoOfPlayers());
                params.put("player_data", getPlyayerData());
                postService(NetworkAPI.JOIN_GAME, params, JOIN_GAME);
            }
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    private String getPlyayerData() {
        try {
            JSONObject item = new JSONObject();
            item.put("__ci_last_regenerate", System.currentTimeMillis() + "");
            item.put("id", user_responsedata.getId());
            item.put("goalkeeper_count", response.getMaxGkp());
            item.put("goalkeeper_player_id", getGKPId());
            item.put("defender_count", response.getMaxDef());
            item.put("defender_player_id", getDEFId());
            item.put("center_count", response.getMaxMid());
            item.put("center_player_id", getMIDId());
            item.put("forward_count", response.getMaxFwd());
            item.put("forward_player_id", getFWDId());
            item.put("game_id", response.getId());
            item.put("caption", c);
            item.put("voic_caption", vc);

            return item.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String getFWDId() {
        String fwd = "";
        for (int i = 0; i < FWD.length; i++) {
            fwd = fwd + FWD[i].getPlayerId() + ",";
        }
        return fwd.substring(0, fwd.length() - 1);
    }

    private String getMIDId() {
        String mid = "";
        for (int i = 0; i < MID.length; i++) {
            mid = mid + MID[i].getPlayerId() + ",";
        }
        return mid.substring(0, mid.length() - 1);
    }

    private String getDEFId() {
        String def = "";
        for (int i = 0; i < DEF.length; i++) {
            def = def + DEF[i].getPlayerId() + ",";
        }
        return def.substring(0, def.length() - 1);
    }

    private String getGKPId() {
        String gkp = "";
        for (int i = 0; i < GKP.length; i++) {
            gkp = gkp + GKP[i].getPlayerId() + ",";
        }
        return gkp.substring(0, gkp.length() - 1);
    }

    public void checkVCTextView(TextView textView, String id) {
        for (TextView text_vc : tv_vc_ArrayList) {
            if (text_vc == textView) {
                text_vc.setBackground(getResources().getDrawable(R.drawable.vc_select));
            } else {
                text_vc.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
            }
        }
        if (c.equals(id)) {
            for (TextView text_c : tv_c_ArrayList) {
                text_c.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
            }
            c = "";
        }
    }

    public void checkCTextView(TextView textView, String id) {
        for (TextView text_c : tv_c_ArrayList) {
            if (text_c == textView) {
                text_c.setBackground(getResources().getDrawable(R.drawable.vc_select));
            } else {
                text_c.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
            }
        }
        if (vc.equals(id)) {
            for (TextView text_vc : tv_vc_ArrayList) {
                text_vc.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
            }
            vc = "";
        }

        /*for (TextView textView1 : tv_c_ArrayList) {
            if (textView1 == textView) {
                if (!vc.equals(id)) {
                    textView1.setBackground(getResources().getDrawable(R.drawable.vc_select));
                    c = id;
                } else {
                    Toast.makeText(this, "Deselect as VC ", Toast.LENGTH_SHORT).show();
                }
            } else {
                textView1.setBackground(getResources().getDrawable(R.drawable.vc_deselect));
            }
        }*/
    }

    public void updateforPost(String str, Integer id) {
        try {
            Set<Integer> ids = post.keySet();
            ArrayList<Integer> id_ = new ArrayList<>();
            id_.addAll(ids);
            for (int i = 0; i < post.size(); i++) {
                String strs = post.get(id_.get(i));
                if (strs.equals(str)) {
                    post.put(id_.get(i), "");
                }
                post.put(id, str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
