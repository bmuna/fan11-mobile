package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fan.core.R;
import com.fan.core.adapter.Transfer_DEF_Adapter;
import com.fan.core.adapter.Transfer_FWD_Adapter;
import com.fan.core.adapter.Transfer_GKP_Adapter;
import com.fan.core.adapter.Transfer_MID_Adapter;
import com.fan.core.custom_view.StepProgressBar;
import com.fan.core.helper.TransferPageEnum;
import com.fan.core.helper.UpdateProgress;
import com.fan.core.model.get_games.Responsedatum;
import com.fan.core.model.transfer_list.PlayerDatum;
import com.fan.core.model.transfer_list.TransferList;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module.JoinGame;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fan.core.util.AppConstants.TRANSFER_LIST;
import static com.fan.core.util.AppConstants.getUserSaved;

/**
 * Created by mohit.soni @ 29-May-2020.
 */
public class Transfer2 extends BaseFragment implements UpdateProgress {
    private static final String TAG = Transfer2.class.getSimpleName();
    private Context context;
    private Activity activity;
    ArrayList<String> player_type = new ArrayList<>();
    ArrayList<String> player_filter = new ArrayList<>();

    TextView tv_transfer_base_title, tv_transfer_currency, tv_transfer_ts;
    TextView tv_transfer_view_team, tv_transfer_tag_type_gkp, tv_transfer_tag_type_def,
            tv_transfer_tag_type_mid, tv_transfer_tag_type_fwd;
    EditText et_transfer_title_search;
    StepProgressBar progress_transfer;
    RecyclerView rv_transfer_pager_gpk, rv_transfer_pager_def, rv_transfer_pager_mid, rv_transfer_pager_fwd;
    LinearLayoutManager mLayoutManager_gpk, mLayoutManager_def, mLayoutManager_mid, mLayoutManager_fwd;
    String type = "GKP";
    String filter = "ASC";

    Transfer_GKP_Adapter transfer_Goal_adapter;
    Transfer_DEF_Adapter transfer_def_adapter;
    Transfer_MID_Adapter transfer_mid_adapter;
    Transfer_FWD_Adapter transfer_fwd_adapter;
    Responsedata user_responsedata;

    public static Responsedatum response;

    public static PlayerDatum[] GKP;
    public static PlayerDatum[] DEF;
    public static PlayerDatum[] MID;
    public static PlayerDatum[] FWD;
    public static HashMap<Integer, PlayerDatum> allowedList = new HashMap<>();

    public static HashMap<Integer, Integer> allowed = new HashMap<>();


    private ViewPager vp_tranfer;
    private int position = 0;
    TransferPageEnum transferPageEnum = null;
    boolean init = true;

    public Transfer2(Context context, Activity activity, Responsedatum response) {
        this.context = context;
        this.activity = activity;
        this.response = response;
        init(this.context);
        init(context);
        try {
            user_responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
        player_type.add("GKP");
        player_type.add("DEF");
        player_type.add("MID");
        player_type.add("FWD");

        player_filter.add("Total Point");
        player_filter.add("ASC");
        player_filter.add("DESC");

        GKP = new PlayerDatum[Integer.parseInt(response.getMaxGkp())];
        DEF = new PlayerDatum[Integer.parseInt(response.getMaxDef())];
        MID = new PlayerDatum[Integer.parseInt(response.getMaxMid())];
        FWD = new PlayerDatum[Integer.parseInt(response.getMaxFwd())];

        allowedList.clear();

    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainFragmentActivity) context).updateTransfer();
        if (allowedList != null) {
            progress_transfer.setActiveIndex(allowedList.size());
            vp_tranfer.setCurrentItem(0, true);
            type = player_type.get(0);
            filter = player_filter.get(1);
            set_type_gkp_button();
            sendRequest(type, filter);
        }
        progress_transfer.setActiveIndex(allowedList.size());
        int x = progress_transfer.getActiveIndex();
        if (x == Integer.parseInt(response.getNoOfPlayers())) {
            tv_transfer_view_team.setBackground(context.getResources().getDrawable(R.drawable.view_team));
            if (Build.VERSION.SDK_INT >= 23) {
                tv_transfer_view_team.setTextColor(context.getColor(R.color.app_light_yellow));
            }
        }
        if (x < Integer.parseInt(response.getNoOfPlayers())) {
            tv_transfer_view_team.setBackground(context.getResources().getDrawable(R.drawable.view_team_disable));
            if (Build.VERSION.SDK_INT >= 23) {
                tv_transfer_view_team.setTextColor(context.getColor(R.color.black));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transfer2, container, false);
        tv_transfer_base_title = (TextView) view.findViewById(R.id.tv_transfer_base_title);
        tv_transfer_currency = (TextView) view.findViewById(R.id.tv_transfer_currency);
        tv_transfer_ts = (TextView) view.findViewById(R.id.tv_transfer_ts);
        tv_transfer_view_team = (TextView) view.findViewById(R.id.tv_transfer_view_team);
        tv_transfer_tag_type_gkp = (TextView) view.findViewById(R.id.tv_transfer_tag_type_gkp);
        tv_transfer_tag_type_def = (TextView) view.findViewById(R.id.tv_transfer_tag_type_def);
        tv_transfer_tag_type_mid = (TextView) view.findViewById(R.id.tv_transfer_tag_type_mid);
        tv_transfer_tag_type_fwd = (TextView) view.findViewById(R.id.tv_transfer_tag_type_fwd);


        et_transfer_title_search = (EditText) view.findViewById(R.id.et_transfer_title_search);
        progress_transfer = (StepProgressBar) view.findViewById(R.id.progress_transfer);

        tv_transfer_view_team.setBackground(context.getResources().getDrawable(R.drawable.view_team_disable));
        if (Build.VERSION.SDK_INT >= 23) {
            tv_transfer_view_team.setTextColor(context.getColor(R.color.black));
        }
        tv_transfer_view_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNullinArray()) {
                    String msg = "This Game required " + response.getNoOfPlayers() + " Players. Please create team according to Game.";
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    type = "GKP";
                    rv_transfer_pager_gpk.setAdapter(null);
                    rv_transfer_pager_def.setAdapter(null);
                    rv_transfer_pager_mid.setAdapter(null);
                    rv_transfer_pager_fwd.setAdapter(null);
                    startActivity(new Intent(context, JoinGame.class));
                    /* ((MainFragmentActivity) context).replaceFragment(new JoinGame(context, activity, response));*/
                }
            }
        });
        progress_transfer.setNumDots(Integer.parseInt(response.getNoOfPlayers()));
        progress_transfer.setActiveIndex(0);
        et_transfer_title_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.length() == 0) {
                        if (type.equals("GKP")) {
                            transfer_Goal_adapter.search("");
                        }
                        if (type.equals("DEF")) {
                            transfer_def_adapter.search("");
                        }
                        if (type.equals("MID")) {
                            transfer_mid_adapter.search("");
                        }
                        if (type.equals("FWD")) {
                            transfer_fwd_adapter.search("");
                        }
                    } else {
                        if (type.equals("GKP")) {
                            transfer_Goal_adapter.search(s.toString());
                        }
                        if (type.equals("DEF")) {
                            transfer_def_adapter.search(s.toString());
                        }
                        if (type.equals("MID")) {
                            transfer_mid_adapter.search(s.toString());
                        }
                        if (type.equals("FWD")) {
                            transfer_fwd_adapter.search(s.toString());
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        vp_tranfer = (ViewPager) view.findViewById(R.id.vp_tranfer);

        vp_tranfer.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                transferPageEnum = transferPageEnum.values()[position];
                int layout = transferPageEnum.getLayoutResId();
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layout, container, false);

                if (layout == R.layout.transfer_pager_gpk) {
                    rv_transfer_pager_gpk = (RecyclerView) viewGroup.findViewById(R.id.rv_transfer_pager_gpk);
                    mLayoutManager_gpk = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    rv_transfer_pager_gpk.setLayoutManager(mLayoutManager_gpk);
                    /*if (type.equals("GKP")) {
                        type = player_type.get(0);
                        filter = player_filter.get(1);
                        sendRequest(type, filter);
                    }
                    init = false;*/
                }

                if (layout == R.layout.transfer_pager_def) {
                    rv_transfer_pager_def = (RecyclerView) viewGroup.findViewById(R.id.rv_transfer_pager_def);
                    mLayoutManager_def = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    rv_transfer_pager_def.setLayoutManager(mLayoutManager_def);

                    /*if (type.equals("DEF")) {
                        type = player_type.get(1);
                        filter = player_filter.get(1);
                        sendRequest(type, filter);
                    }*/
                }
                if (layout == R.layout.transfer_pager_mid) {
                    rv_transfer_pager_mid = (RecyclerView) viewGroup.findViewById(R.id.rv_transfer_pager_mid);
                    mLayoutManager_mid = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    rv_transfer_pager_mid.setLayoutManager(mLayoutManager_mid);

                    /*if (type.equals("MID")) {
                        type = player_type.get(2);
                        filter = player_filter.get(1);
                        sendRequest(type, filter);
                    }*/
                }
                if (layout == R.layout.transfer_pager_fwd) {
                    rv_transfer_pager_fwd = (RecyclerView) viewGroup.findViewById(R.id.rv_transfer_pager_fwd);
                    mLayoutManager_fwd = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    rv_transfer_pager_fwd.setLayoutManager(mLayoutManager_fwd);

                    /*if (type.equals("FWD")) {
                        type = player_type.get(3);
                        filter = player_filter.get(1);
                        sendRequest(type, filter);
                    }*/
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
        vp_tranfer.setCurrentItem(position, true);
        set_type_gkp_button();
        final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    /*if (!init) {*/
                    type = player_type.get(0);
                    filter = player_filter.get(1);
                    set_type_gkp_button();
                    sendRequest(type, filter);
                    /* }*/
                }
                if (position == 1) {
                    type = player_type.get(1);
                    filter = player_filter.get(1);
                    set_type_def_button();
                    sendRequest(type, filter);
                }
                if (position == 2) {
                    type = player_type.get(2);
                    filter = player_filter.get(1);
                    set_type_mid_button();
                    sendRequest(type, filter);
                }
                if (position == 3) {
                    type = player_type.get(3);
                    filter = player_filter.get(1);
                    set_type_fwd_button();
                    sendRequest(type, filter);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        tv_transfer_tag_type_gkp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                type = player_type.get(0);
                filter = player_filter.get(1);
                vp_tranfer.setCurrentItem(0, true);
            }
        });
        tv_transfer_tag_type_def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                type = player_type.get(1);
                filter = player_filter.get(1);
                vp_tranfer.setCurrentItem(1, true);
            }
        });
        tv_transfer_tag_type_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                type = player_type.get(2);
                filter = player_filter.get(1);
                vp_tranfer.setCurrentItem(2, true);
            }
        });
        tv_transfer_tag_type_fwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                type = player_type.get(3);
                filter = player_filter.get(1);
                vp_tranfer.setCurrentItem(3, true);
            }
        });
        vp_tranfer.addOnPageChangeListener(onPageChangeListener);
        onPageChangeListener.onPageSelected(0);
        return view;
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == TRANSFER_LIST) {
                TransferList bean = gson.fromJson((String) responseModel, TransferList.class);
                sortData(bean);
                if (bean.getResponseCode() == 1) {
                    /*updateImagePath(bean);*/
                    if (bean.getResponsedata().getAllowedTeamPlayers().equals("")) {
                        showToast("No players available");
                        ((MainFragmentActivity) context).onBackPressed();
                    } else {
                        allowed.clear();
                        addTeamPlayerAllowed(bean.getResponsedata().getAllowedTeamPlayers());
                        if (type.equals("GKP")) {
                            rv_transfer_pager_gpk.setAdapter(null);
                            transfer_Goal_adapter = new Transfer_GKP_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers(), this);
                            rv_transfer_pager_gpk.setAdapter(transfer_Goal_adapter);
                        }
                        if (type.equals("DEF")) {
                            rv_transfer_pager_def.setAdapter(null);
                            transfer_def_adapter = new Transfer_DEF_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers(), this);
                            rv_transfer_pager_def.setAdapter(transfer_def_adapter);
                        }
                        if (type.equals("MID")) {
                            rv_transfer_pager_mid.setAdapter(null);
                            transfer_mid_adapter = new Transfer_MID_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers(), this);
                            rv_transfer_pager_mid.setAdapter(transfer_mid_adapter);
                        }
                        if (type.equals("FWD")) {
                            rv_transfer_pager_fwd.setAdapter(null);
                            transfer_fwd_adapter = new Transfer_FWD_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers(), this);
                            rv_transfer_pager_fwd.setAdapter(transfer_fwd_adapter);
                        }
                    }

                } else {
                    /*showToast(bean.getResponsedata().getErrorMessage());*/
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
        hideProgress();
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*public void callTypeTitle(ArrayList<String> arrayList) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.list_content);

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, arrayList));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rv_transfer.setAdapter(null);
                type = player_type.get(position);
                tv_transfer_title.setText(type);
                sendRequest(type, filter);
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/

    /*public void callFilterTitle(ArrayList<String> arrayList) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.list_content);

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, arrayList));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rv_transfer_pager_gpk.setAdapter(null);
                if (position == 0) {
                    filter = player_filter.get(1);
                } else {
                    filter = player_filter.get(position);
                }
                tv_transfer_point.setText(filter);
                sendRequest(type, filter);
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/

    public void sendRequest(String type, String filter) {
        if (AppConstants.isOnline(context)) {
            showDialog("Getting Player", true);
            RequestParams params = new RequestParams();
            params.put("game_id", response.getId());
            params.put("user_id", user_responsedata.getId());
            params.put("position", type);
            params.put("point_filter", filter);
            postService(NetworkAPI.TRANSFER_LIST, params, TRANSFER_LIST);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    /*public void updateImagePath(GetPlayer bean) {
        for (int i = 0; i < bean.getResponsedata().size(); i++) {
            bean.getResponsedata().get(i).setProfileImage(bean.getImageUrl()+bean.getResponsedata().get(i).getProfileImage());
        }
    }*/

    public boolean checkNullinArray() {
        for (int i = 0; i < GKP.length; i++) {
            if (GKP[i] == null) {
                return false;
            }
        }
        for (int i = 0; i < DEF.length; i++) {
            if (DEF[i] == null) {
                return false;
            }
        }
        for (int i = 0; i < MID.length; i++) {
            if (MID[i] == null) {
                return false;
            }
        }
        for (int i = 0; i < FWD.length; i++) {
            if (FWD[i] == null) {
                return false;
            }
        }
        return true;
    }

    public void addTeamPlayerAllowed(String string) {
        String[] strings = string.split(",");
        for (String s : strings) {
            String[] ss = s.split("-");
            if (ss.length > 1) {
                allowed.put(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
            }
        }
    }

    @Override
    public void update() {
        log(TAG, allowedList.toString() + "");
        progress_transfer.setActiveIndex(allowedList.size());
        int x = progress_transfer.getActiveIndex();
        if (x == Integer.parseInt(response.getNoOfPlayers())) {
            tv_transfer_view_team.setBackground(context.getResources().getDrawable(R.drawable.view_team));
            if (Build.VERSION.SDK_INT >= 23) {
                tv_transfer_view_team.setTextColor(context.getColor(R.color.app_light_yellow));
            }
        }
        if (x < Integer.parseInt(response.getNoOfPlayers())) {
            tv_transfer_view_team.setBackground(context.getResources().getDrawable(R.drawable.view_team_disable));
            if (Build.VERSION.SDK_INT >= 23) {
                tv_transfer_view_team.setTextColor(context.getColor(R.color.black));
            }
        }
    }

    public void set_type_gkp_button() {
        tv_transfer_tag_type_gkp.setTextColor(Color.BLACK);
        tv_transfer_tag_type_gkp.setBackground(context.getResources().getDrawable(R.drawable.type_tag_start_select));
        tv_transfer_tag_type_def.setTextColor(Color.BLACK);
        tv_transfer_tag_type_def.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid));
        tv_transfer_tag_type_mid.setTextColor(Color.BLACK);
        tv_transfer_tag_type_mid.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid));
        tv_transfer_tag_type_fwd.setTextColor(Color.BLACK);
        tv_transfer_tag_type_fwd.setBackground(context.getResources().getDrawable(R.drawable.type_tag_end));
    }

    public void set_type_def_button() {
        tv_transfer_tag_type_gkp.setTextColor(Color.BLACK);
        tv_transfer_tag_type_gkp.setBackground(context.getResources().getDrawable(R.drawable.type_tag_start));
        tv_transfer_tag_type_def.setTextColor(Color.BLACK);
        tv_transfer_tag_type_def.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid_select));
        tv_transfer_tag_type_mid.setTextColor(Color.BLACK);
        tv_transfer_tag_type_mid.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid));
        tv_transfer_tag_type_fwd.setTextColor(Color.BLACK);
        tv_transfer_tag_type_fwd.setBackground(context.getResources().getDrawable(R.drawable.type_tag_end));
    }

    public void set_type_mid_button() {
        tv_transfer_tag_type_gkp.setTextColor(Color.BLACK);
        tv_transfer_tag_type_gkp.setBackground(context.getResources().getDrawable(R.drawable.type_tag_start));
        tv_transfer_tag_type_def.setTextColor(Color.BLACK);
        tv_transfer_tag_type_def.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid));
        tv_transfer_tag_type_mid.setTextColor(Color.BLACK);
        tv_transfer_tag_type_mid.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid_select));
        tv_transfer_tag_type_fwd.setTextColor(Color.BLACK);
        tv_transfer_tag_type_fwd.setBackground(context.getResources().getDrawable(R.drawable.type_tag_end));
    }

    public void set_type_fwd_button() {
        tv_transfer_tag_type_gkp.setTextColor(Color.BLACK);
        tv_transfer_tag_type_gkp.setBackground(context.getResources().getDrawable(R.drawable.type_tag_start));
        tv_transfer_tag_type_def.setTextColor(Color.BLACK);
        tv_transfer_tag_type_def.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid));
        tv_transfer_tag_type_mid.setTextColor(Color.BLACK);
        tv_transfer_tag_type_mid.setBackground(context.getResources().getDrawable(R.drawable.type_tag_mid));
        tv_transfer_tag_type_fwd.setTextColor(Color.BLACK);
        tv_transfer_tag_type_fwd.setBackground(context.getResources().getDrawable(R.drawable.type_tag_end_select));
    }

    public void sortData(TransferList bean) {
        List<PlayerDatum> playerDatalist = bean.getResponsedata().getPlayerData();
        /*Random random = new Random();
        for (int i = 0; i < 10; i++) {
            PlayerDatum playerDatum = new PlayerDatum();
            playerDatum.setPlayerPoint(String.valueOf(random.nextInt(1000)));
            playerDatalist.add(playerDatum);
        }*/
        PlayerDatum[] playerData = new PlayerDatum[playerDatalist.size()];
        playerData = playerDatalist.toArray(playerData);
        PlayerDatum player = new PlayerDatum();

        int pivot = 0;
        for (int i = 0; i < playerData.length; i++) {
            for (int j = 0; j < i; j++) {
                int x = Integer.parseInt(playerData[pivot].getPlayerPoint());
                int y = Integer.parseInt(playerData[j].getPlayerPoint());
                if (x < y) {
                    player = playerData[j];
                    playerData[j] = playerData[pivot];
                    playerData[pivot] = player;
                }
            }
            // increase pivot by 1 to check value
            pivot = pivot + 1;
        }
        List<PlayerDatum> playerDatalist2 = new ArrayList<>();
        for (int j = playerData.length - 1; j >= 0; j--) {
            playerDatalist2.add(playerData[j]);
        }
        bean.getResponsedata().setPlayerData(playerDatalist2);
    }
}
