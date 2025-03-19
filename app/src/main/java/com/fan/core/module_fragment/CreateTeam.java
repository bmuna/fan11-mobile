//package com.fan.core.module_fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.SwitchCompat;
//
//import com.fan.core.R;
//import com.fan.core.model.get_player.GetPlayer;
//import com.fan.core.model.get_player.Responsedatum;
//import com.fan.core.model.user.Responsedata;
//import com.fan.core.util.AppConstants;
//import com.fan.core.util.NetworkAPI;
//import com.google.gson.Gson;
//import com.loopj.android.http.RequestParams;
//import com.squareup.picasso.Picasso;
//
//import static com.fan.core.util.AppConstants.CREATE_TEAM;
//import static com.fan.core.util.AppConstants.PLAYERS;
//import static com.fan.core.util.AppConstants.PLAYERS_DATA;
//import static com.fan.core.util.AppConstants.getUserSaved;
//
///**
// * Created by mohit.soni @ 18-Oct-19.
// */
//public class CreateTeam extends BaseFragment {
//    private static final String TAG = CreateTeam.class.getName();
//    private SwitchCompat sw_create_team;
//    private Activity activity;
//    Button bt_create_team_confirm, bt_create_team_reset;
//    Context context;
//
//    TextView tv_create_team_switch_text, tv_create_team_amount;
//
//    LinearLayout ll_team_position_0,ll_team_position_1, ll_team_position_2, ll_team_position_3, ll_team_position_4,
//            ll_team_position_5, ll_team_position_6, ll_team_position_7, ll_team_position_8,
//            ll_team_position_9, ll_team_position_10;
//    TextView tv_team_position_name_0,tv_team_position_name_1,tv_team_position_name_2,tv_team_position_name_3,
//            tv_team_position_name_4,tv_team_position_name_5,tv_team_position_name_6,
//            tv_team_position_name_7,tv_team_position_name_8,tv_team_position_name_9,
//            tv_team_position_name_10;
//    ImageView iv_team_position_icon_0,iv_team_position_icon_1,iv_team_position_icon_2,iv_team_position_icon_3,
//            iv_team_position_icon_4,iv_team_position_icon_5,iv_team_position_icon_6,
//            iv_team_position_icon_7,iv_team_position_icon_8,iv_team_position_icon_9,
//            iv_team_position_icon_10;
//
//    Responsedata user_responsedata;
//    public CreateTeam(Context context, Activity activity) {
//        this.context = context;
//        this.activity = activity;
//        init(this.context);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((MainFragmentActivity) context).updateCreateTeam();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.create_team, container, false);
//        sw_create_team = (SwitchCompat) view.findViewById(R.id.sw_create_team);
//        tv_create_team_switch_text = (TextView) view.findViewById(R.id.tv_create_team_switch_text);
//        tv_create_team_amount = (TextView) view.findViewById(R.id.tv_create_team_amount);
//
//        bt_create_team_confirm = (Button) view.findViewById(R.id.bt_create_team_confirm);
//        bt_create_team_reset = (Button) view.findViewById(R.id.bt_create_team_reset);
//
//        ll_team_position_0 = (LinearLayout) view.findViewById(R.id.ll_team_position_0);
//        ll_team_position_1 = (LinearLayout) view.findViewById(R.id.ll_team_position_1);
//        ll_team_position_2 = (LinearLayout) view.findViewById(R.id.ll_team_position_2);
//        ll_team_position_3 = (LinearLayout) view.findViewById(R.id.ll_team_position_3);
//        ll_team_position_4 = (LinearLayout) view.findViewById(R.id.ll_team_position_4);
//        ll_team_position_5 = (LinearLayout) view.findViewById(R.id.ll_team_position_5);
//        ll_team_position_6 = (LinearLayout) view.findViewById(R.id.ll_team_position_6);
//        ll_team_position_7 = (LinearLayout) view.findViewById(R.id.ll_team_position_7);
//        ll_team_position_8 = (LinearLayout) view.findViewById(R.id.ll_team_position_8);
//        ll_team_position_9 = (LinearLayout) view.findViewById(R.id.ll_team_position_9);
//        ll_team_position_10 = (LinearLayout) view.findViewById(R.id.ll_team_position_10);
//
//        tv_team_position_name_0 = (TextView) view.findViewById(R.id.tv_team_position_name_0);
//        tv_team_position_name_1 = (TextView) view.findViewById(R.id.tv_team_position_name_1);
//        tv_team_position_name_2 = (TextView) view.findViewById(R.id.tv_team_position_name_2);
//        tv_team_position_name_3 = (TextView) view.findViewById(R.id.tv_team_position_name_3);
//        tv_team_position_name_4 = (TextView) view.findViewById(R.id.tv_team_position_name_4);
//        tv_team_position_name_5 = (TextView) view.findViewById(R.id.tv_team_position_name_5);
//        tv_team_position_name_6 = (TextView) view.findViewById(R.id.tv_team_position_name_6);
//        tv_team_position_name_7 = (TextView) view.findViewById(R.id.tv_team_position_name_7);
//        tv_team_position_name_8 = (TextView) view.findViewById(R.id.tv_team_position_name_8);
//        tv_team_position_name_9 = (TextView) view.findViewById(R.id.tv_team_position_name_9);
//        tv_team_position_name_10 = (TextView) view.findViewById(R.id.tv_team_position_name_10);
//
//        iv_team_position_icon_0 = (ImageView) view.findViewById(R.id.iv_team_position_icon_0);
//        iv_team_position_icon_1 = (ImageView) view.findViewById(R.id.iv_team_position_icon_1);
//        iv_team_position_icon_2 = (ImageView) view.findViewById(R.id.iv_team_position_icon_2);
//        iv_team_position_icon_3 = (ImageView) view.findViewById(R.id.iv_team_position_icon_3);
//        iv_team_position_icon_4 = (ImageView) view.findViewById(R.id.iv_team_position_icon_4);
//        iv_team_position_icon_5 = (ImageView) view.findViewById(R.id.iv_team_position_icon_5);
//        iv_team_position_icon_6 = (ImageView) view.findViewById(R.id.iv_team_position_icon_6);
//        iv_team_position_icon_7 = (ImageView) view.findViewById(R.id.iv_team_position_icon_7);
//        iv_team_position_icon_8 = (ImageView) view.findViewById(R.id.iv_team_position_icon_8);
//        iv_team_position_icon_9 = (ImageView) view.findViewById(R.id.iv_team_position_icon_9);
//        iv_team_position_icon_10 = (ImageView) view.findViewById(R.id.iv_team_position_icon_10);
//
//        try {
//            user_responsedata = getUserSaved(context);
//        } catch (NullPointerException e) {
//        }
//        sw_create_team.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    tv_create_team_switch_text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                    tv_create_team_switch_text.setPadding(30, 0, 0, 0);
//                    tv_create_team_switch_text.setTextColor(context.getColor(R.color.app_light_yellow));
//                } else {
//                    tv_create_team_switch_text.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
//                    tv_create_team_switch_text.setPadding(0, 0, 30, 0);
//                    tv_create_team_switch_text.setTextColor(Color.BLACK);
//                }
//
//            }
//        });
//        bt_create_team_confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new GameList(context, activity));
//            }
//        });
//        tv_create_team_amount.setText(getResources().getString(R.string.birr) +" "+ user_responsedata.getMyBalance());
//        sw_create_team.setChecked(true);
//        ll_team_position_0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//        ll_team_position_10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Transfer(context, activity));
//            }
//        });
//
////        sendRequest();
//        return view;
//    }
//
//    @Override
//    public void onResponseListener(int requestCode, Object responseModel) {
//        log(TAG, responseModel.toString());
//        Gson gson = new Gson();
//        if (requestCode == CREATE_TEAM) {
//            GetPlayer bean = gson.fromJson((String) responseModel, GetPlayer.class);
//            PLAYERS.clear();
//            if (bean.getResponseCode() == 1) {
//                PLAYERS.addAll(bean.getResponsedata());
//                updateImagePath(bean);
//                PLAYERS_DATA = PLAYERS.toArray(new Responsedatum[PLAYERS.size()]);
//                SetPlayer();
//            } else {
////                showToast(bean.getResponsedata().getErrorMessage());
//            }
//
//        }
//    }
//
//    @Override
//    public void onErrorListener(int requestCode, String error) {
//        showToast(requestCode + " : " + error);
//    }
//
//    public void sendRequest() {
//        if (AppConstants.isOnline(context)) {
//            showDialog("Getting team", true);
//            RequestParams params = new RequestParams();
//            getService(NetworkAPI.CREATE_TEAM, params, CREATE_TEAM);
//        } else {
//            showToast(getResources().getString(R.string.no_internet_connection));
//        }
//    }
//
//    public void SetPlayer(){
//        Picasso.get().load(PLAYERS_DATA[0].getProfileImage()).into(iv_team_position_icon_0);
//        Picasso.get().load(PLAYERS_DATA[1].getProfileImage()).into(iv_team_position_icon_1);
//        Picasso.get().load(PLAYERS_DATA[2].getProfileImage()).into(iv_team_position_icon_2);
//        Picasso.get().load(PLAYERS_DATA[3].getProfileImage()).into(iv_team_position_icon_3);
//        Picasso.get().load(PLAYERS_DATA[4].getProfileImage()).into(iv_team_position_icon_4);
//        Picasso.get().load(PLAYERS_DATA[5].getProfileImage()).into(iv_team_position_icon_5);
//        Picasso.get().load(PLAYERS_DATA[6].getProfileImage()).into(iv_team_position_icon_6);
//        Picasso.get().load(PLAYERS_DATA[7].getProfileImage()).into(iv_team_position_icon_7);
//        Picasso.get().load(PLAYERS_DATA[8].getProfileImage()).into(iv_team_position_icon_8);
//        Picasso.get().load(PLAYERS_DATA[9].getProfileImage()).into(iv_team_position_icon_9);
//        Picasso.get().load(PLAYERS_DATA[10].getProfileImage()).into(iv_team_position_icon_10);
//        tv_team_position_name_0.setText(PLAYERS_DATA[0].getFirstName());
//        tv_team_position_name_1.setText(PLAYERS_DATA[1].getFirstName());
//        tv_team_position_name_2.setText(PLAYERS_DATA[2].getFirstName());
//        tv_team_position_name_3.setText(PLAYERS_DATA[3].getFirstName());
//        tv_team_position_name_4.setText(PLAYERS_DATA[4].getFirstName());
//        tv_team_position_name_5.setText(PLAYERS_DATA[5].getFirstName());
//        tv_team_position_name_6.setText(PLAYERS_DATA[6].getFirstName());
//        tv_team_position_name_7.setText(PLAYERS_DATA[7].getFirstName());
//        tv_team_position_name_8.setText(PLAYERS_DATA[8].getFirstName());
//        tv_team_position_name_9.setText(PLAYERS_DATA[9].getFirstName());
//        tv_team_position_name_10.setText(PLAYERS_DATA[10].getFirstName());
//
//        hideProgress();
//    }
//    public void updateImagePath(GetPlayer bean){
//        for(Responsedatum responsedatum : PLAYERS){
//            responsedatum.setProfileImage(bean.getImageUrl()+responsedatum.getProfileImage());
//        }
//    }
//}
