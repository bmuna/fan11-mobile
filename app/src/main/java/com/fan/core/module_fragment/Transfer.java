//package com.fan.core.module_fragment;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.fan.core.R;
//import com.fan.core.adapter.Transfer_DEF_Adapter;
//import com.fan.core.adapter.Transfer_FWD_Adapter;
//import com.fan.core.adapter.Transfer_GKP_Adapter;
//import com.fan.core.adapter.Transfer_MID_Adapter;
//import com.fan.core.model.get_games.Responsedatum;
//import com.fan.core.model.transfer_list.PlayerDatum;
//import com.fan.core.model.transfer_list.TransferList;
//import com.fan.core.model.user.Responsedata;
//import com.fan.core.util.AppConstants;
//import com.fan.core.util.NetworkAPI;
//import com.google.gson.Gson;
//import com.loopj.android.http.RequestParams;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static com.fan.core.util.AppConstants.TRANSFER_LIST;
//import static com.fan.core.util.AppConstants.getUserSaved;
//
///**
// * Created by mohit.soni @ 21-Oct-19.
// */
//public class Transfer extends BaseFragment {
//    private final String TAG = Transfer.class.getSimpleName();
//    private Context context;
//    private Activity activity;
//    ArrayList<String> player_type = new ArrayList<>();
//    ArrayList<String> player_filter = new ArrayList<>();
//
//    TextView tv_transfer_title, tv_transfer_point, tv_transfer_base_title,
//            tv_transfer_currency, tv_transfer_ts;
//    Button bt_transfer_play;
//    EditText et_transfer_title_search;
//    ImageView iv_transfer_title_search;
//    RecyclerView rv_transfer;
//    LinearLayoutManager mLayoutManager;
//    String type = "GKP";
//    String filter = "ASC";
//
//    Transfer_GKP_Adapter transfer_Goal_adapter;
//    Transfer_DEF_Adapter transfer_def_adapter;
//    Transfer_MID_Adapter transfer_mid_adapter;
//    Transfer_FWD_Adapter transfer_fwd_adapter;
//    Responsedata user_responsedata;
//
//    Responsedatum response;
//
//    public static PlayerDatum[] GKP;
//    public static PlayerDatum[] DEF;
//    public static PlayerDatum[] MID;
//    public static PlayerDatum[] FWD;
//
//    public static HashMap<Integer, Integer> allowed = new HashMap<>();
//
//    public Transfer(Context context, Activity activity, Responsedatum response) {
//        this.context = context;
//        this.activity = activity;
//        this.response = response;
//        init(this.context);
//        init(context);
//        try {
//            user_responsedata = getUserSaved(context);
//        } catch (NullPointerException e) {
//        }
//        player_type.add("GKP");
//        player_type.add("DEF");
//        player_type.add("MID");
//        player_type.add("FWD");
//
//        player_filter.add("Total Point");
//        player_filter.add("ASC");
//        player_filter.add("DESC");
//
//        GKP = new PlayerDatum[Integer.parseInt(response.getMaxGkp())];
//        DEF = new PlayerDatum[Integer.parseInt(response.getMaxDef())];
//        MID = new PlayerDatum[Integer.parseInt(response.getMaxMid())];
//        FWD = new PlayerDatum[Integer.parseInt(response.getMaxFwd())];
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((MainFragmentActivity) context).updateTransfer();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.transfer, container, false);
//        tv_transfer_title = (TextView) view.findViewById(R.id.tv_transfer_title);
//        tv_transfer_point = (TextView) view.findViewById(R.id.tv_transfer_point);
//        tv_transfer_base_title = (TextView) view.findViewById(R.id.tv_transfer_base_title);
//        tv_transfer_currency = (TextView) view.findViewById(R.id.tv_transfer_currency);
//        tv_transfer_ts = (TextView) view.findViewById(R.id.tv_transfer_ts);
//        bt_transfer_play = (Button) view.findViewById(R.id.bt_transfer_play);
//        et_transfer_title_search = (EditText) view.findViewById(R.id.et_transfer_title_search);
//        iv_transfer_title_search = (ImageView) view.findViewById(R.id.iv_transfer_title_search);
//
//        rv_transfer = (RecyclerView) view.findViewById(R.id.rv_transfer);
//        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
//        rv_transfer.setLayoutManager(mLayoutManager);
//
//        tv_transfer_title.setText(player_type.get(0));
//        tv_transfer_point.setText(player_filter.get(0));
//
//        tv_transfer_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callTypeTitle(player_type);
//            }
//        });
//        tv_transfer_point.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callFilterTitle(player_filter);
//            }
//        });
//        bt_transfer_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!checkNullinArray()) {
//                    String msg = "This Game required " + response.getNoOfPlayers() + " Players. Please create team according to Game.";
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                } else {
//                    type = "GKP";
//                    ((MainFragmentActivity) context).replaceFragment(new JoinGame(context, activity, response));
//                }
//            }
//        });
//        type =player_type.get(0);
//        filter = player_filter.get(1);
//        sendRequest(type, filter);
//        et_transfer_title_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() == 0) {
//                    iv_transfer_title_search.setVisibility(View.VISIBLE);
//                    if (type.equals("GKP")) {
//                        transfer_Goal_adapter.search("");
//                    }
//                    if (type.equals("DEF")) {
//                        transfer_def_adapter.search("");
//                    }
//                    if (type.equals("MID")) {
//                        transfer_mid_adapter.search("");
//                    }
//                    if (type.equals("FWD")) {
//                        transfer_fwd_adapter.search("");
//                    }
//                } else {
//                    if (type.equals("GKP")) {
//                        transfer_Goal_adapter.search(s.toString());
//                    }
//                    if (type.equals("DEF")) {
//                        transfer_def_adapter.search(s.toString());
//                    }
//                    if (type.equals("MID")) {
//                        transfer_mid_adapter.search(s.toString());
//                    }
//                    if (type.equals("FWD")) {
//                        transfer_fwd_adapter.search(s.toString());
//                    }
//                    iv_transfer_title_search.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onResponseListener(int requestCode, Object responseModel) {
//        try {
//            log(TAG, responseModel.toString());
//
//            Gson gson = new Gson();
//            if (requestCode == TRANSFER_LIST) {
//                TransferList bean = gson.fromJson((String) responseModel, TransferList.class);
//                if (bean.getResponseCode() == 1) {
//                    /*updateImagePath(bean);*/
//                    if(bean.getResponsedata().getAllowedTeamPlayers().equals("")){
//                        showToast("No players available");
//                        ((MainFragmentActivity)context).onBackPressed();
//                    }else {
//                        allowed.clear();
//                        addTeamPlayerAllowed(bean.getResponsedata().getAllowedTeamPlayers());
//                        rv_transfer.setAdapter(null);
//                       /* if (type.equals("GKP")) {
//                            transfer_Goal_adapter = new Transfer_GKP_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers());
//                            rv_transfer.setAdapter(transfer_Goal_adapter);
//                        }
//                        if (type.equals("DEF")) {
//                            transfer_def_adapter = new Transfer_DEF_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers());
//                            rv_transfer.setAdapter(transfer_def_adapter);
//                        }
//                        if (type.equals("MID")) {
//                            transfer_mid_adapter = new Transfer_MID_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers());
//                            rv_transfer.setAdapter(transfer_mid_adapter);
//                        }
//                        if (type.equals("FWD")) {
//                            transfer_fwd_adapter = new Transfer_FWD_Adapter(context, bean.getResponsedata(), type, bean.getResponsedata().getAllowedTeamPlayers());
//                            rv_transfer.setAdapter(transfer_fwd_adapter);
//                        }*/
//                    }
//
//                } else {
//                    /*showToast(bean.getResponsedata().getErrorMessage());*/
//                }
//
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        hideProgress();
//    }
//
//    @Override
//    public void onErrorListener(int requestCode, String error) {
//        showToast(requestCode + " : " + error);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    public void callTypeTitle(ArrayList<String> arrayList) {
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(android.R.layout.list_content);
//
//        ListView list = (ListView) dialog.findViewById(android.R.id.list);
//        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
//                android.R.id.text1, arrayList));
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                rv_transfer.setAdapter(null);
//                type = player_type.get(position);
//                tv_transfer_title.setText(type);
//                sendRequest(type, filter);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    public void callFilterTitle(ArrayList<String> arrayList) {
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(android.R.layout.list_content);
//
//        ListView list = (ListView) dialog.findViewById(android.R.id.list);
//        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
//                android.R.id.text1, arrayList));
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                rv_transfer.setAdapter(null);
//                if (position == 0) {
//                    filter = player_filter.get(1);
//                } else {
//                    filter = player_filter.get(position);
//                }
//                tv_transfer_point.setText(filter);
//                sendRequest(type, filter);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    public void sendRequest(String type, String filter) {
//        if (AppConstants.isOnline(context)) {
//            showDialog("Getting Player", true);
//            RequestParams params = new RequestParams();
//            params.put("game_id", response.getId());
//            params.put("user_id", user_responsedata.getId());
//            params.put("position", type);
//            params.put("point_filter", filter);
//            postService(NetworkAPI.TRANSFER_LIST, params, TRANSFER_LIST);
//        } else {
//            showToast(getResources().getString(R.string.no_internet_connection));
//        }
//    }
//
//    /*public void updateImagePath(GetPlayer bean) {
//        for (int i = 0; i < bean.getResponsedata().size(); i++) {
//            bean.getResponsedata().get(i).setProfileImage(bean.getImageUrl()+bean.getResponsedata().get(i).getProfileImage());
//        }
//    }*/
//
//    public boolean checkNullinArray() {
//        for (int i = 0; i < GKP.length; i++) {
//            if (GKP[i] == null) {
//                return false;
//            }
//        }
//        for (int i = 0; i < DEF.length; i++) {
//            if (DEF[i] == null) {
//                return false;
//            }
//        }
//        for (int i = 0; i < MID.length; i++) {
//            if (MID[i] == null) {
//                return false;
//            }
//        }
//        for (int i = 0; i < FWD.length; i++) {
//            if (FWD[i] == null) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void addTeamPlayerAllowed(String string) {
//        String[] strings = string.split(",");
//        for (String s : strings) {
//            String[] ss = s.split("-");
//            if (ss.length > 1) {
//                allowed.put(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
//            }
//        }
//    }
//
//
//}
