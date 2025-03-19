package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.adapter.League_User_Adapter;
import com.fan.core.model.laugue_user.Responsedatum;
import com.fan.core.model.played_user.PlayedUser;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.common.StringUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.fan.core.util.AppConstants.PLAYED_USERS;
import static com.fan.core.util.AppConstants.getUserSaved;

/**
 * created by mohit.soni @ 23-Jan-20 _ 2:09 PM
 */
public class League_Detail_Child extends BaseFragment {
    private static final String TAG = My_Team.class.getSimpleName();
    Context context;
    Activity activity;
//    List<Responsedatum> responsedatum = new ArrayList<>();
    Responsedatum responsedatum;
    ArrayList<Responsedatum> response;

    Responsedata responsedata;
    RecyclerView rv_league_user;
    LinearLayoutManager mLayoutManager;
    LinearLayout tb_user;
    TextView number;
    TextView point;
    TextView user_name;
    TextView view_team;
    ImageView trophy;
    Button prev;
    Button next;
    League_User_Adapter league_user_adapter;
    String gameId = "";
    int nextVal = 0;


//    public static final int TOTAL_NUM_ITEMS = 52;
    public static final int ITEMS_PER_PAGE = 10;
    public int ITEMS_REMAINING;
    public int LAST_PAGE;
    private int totalPages;
    private int currentPage = 0;
    String value;


    public League_Detail_Child(Context context, Activity activity, String gameId) {
        this.context = context;
        this.activity = activity;
        this.gameId = gameId;
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


    private void toggleButton(){
        if(currentPage == LAST_PAGE){
            next.setEnabled(false);
            prev.setEnabled(true);
        }else if(currentPage == 0) {
            next.setEnabled(false);
            prev.setEnabled(true);
        }
        else if(currentPage >= 1 && currentPage <= LAST_PAGE) {
            next.setEnabled(true);
            prev.setEnabled(true);
        }

//        else{
//            next.setEnabled(true);
//            prev.setEnabled(false);
//        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_user, container, false);
        trophy = (ImageView) view.findViewById(R.id.trophy);
        rv_league_user = (RecyclerView) view.findViewById(R.id.rv_league_user);
        tb_user = (LinearLayout) view.findViewById(R.id.tb_user);
        number = (TextView) view.findViewById(R.id.number);
        user_name = (TextView) view.findViewById(R.id.user_name);
        point = (TextView) view.findViewById(R.id.point);
        view_team = (TextView) view.findViewById(R.id.view_team);
        prev = (Button) view.findViewById(R.id.prev);
        next = (Button) view.findViewById(R.id.next);
        prev.setEnabled(false);


        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        rv_league_user.setLayoutManager(mLayoutManager);
        if (AppConstants.isOnline(activity)) {
            RequestParams params = new RequestParams();
            params.put("game_id", gameId);
            /*params.put("user_id", responsedata.getId());*/
            postService(NetworkAPI.PLAYED_USERS, params, PLAYED_USERS);
            showDialog("Getting played user", false);
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
            if (requestCode == PLAYED_USERS) {
                final PlayedUser bean = gson.fromJson((String) responseModel, PlayedUser.class);
                Log.d("size", String.valueOf(bean.getResponsedata().size()));

                setPoint(bean);
//

                generatePage(currentPage, bean);
                rv_league_user.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_league_user.setAdapter(new League_User_Adapter(getActivity(), generatePage(currentPage, bean), activity, number, user_name,point,view_team, nextVal, value));
//                rv_league_user.setAdapter();
                next.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        currentPage += 1;
                        nextVal += 10;
                        rv_league_user.setAdapter(new League_User_Adapter(getActivity(), generatePage(currentPage, bean), activity, number, user_name,point,view_team, nextVal, value));

                        toggleButton();
                    }
                });
                prev.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        currentPage -= 1;
                        nextVal -= 10;
                        rv_league_user.setAdapter(new League_User_Adapter(getActivity(), generatePage(currentPage, bean), activity,  number, user_name,point,view_team, nextVal, value));
                    toggleButton();
                    }
                });
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
        hideProgress();
    }


    public ArrayList<Responsedatum> generatePage(int currentPage, PlayedUser bean) {
        int startItem = currentPage*ITEMS_PER_PAGE;
        int numOfData = ITEMS_PER_PAGE;

       response = new ArrayList<>();
        totalPages = bean.getResponsedata().size()/ITEMS_PER_PAGE;
        ITEMS_REMAINING = bean.getResponsedata().size()% ITEMS_PER_PAGE;
        LAST_PAGE = bean.getResponsedata().size()/ITEMS_PER_PAGE;
          if (currentPage == LAST_PAGE && ITEMS_REMAINING > 0) {
            for (int i = startItem; i < startItem + ITEMS_REMAINING; i++) {
                response.add(bean.getResponsedata().get(i));

            }
        } else {
            for (int i = startItem; i < startItem + numOfData; i++) {
                response.add(bean.getResponsedata().get(i));
            }
        }
        return response;

    }
    private void setPoint(PlayedUser bean) {
        List<Responsedatum> list = bean.getResponsedata();
        for (int i = 0; i < list.size(); i++) {
            Responsedatum responsedatum = list.get(i);
            responsedatum.setPoint(Double.toString(bean.getResponsedata().get(i).getPoints()));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


 //            for(int j=0; j<=2; j++)
//            {
//                // Create LinearLayout
//                LinearLayout ll = new LinearLayout(context);
//                ll.setOrientation(LinearLayout.HORIZONTAL);
//
//                // Create TextView
//                TextView product = new TextView(context);
//                product.setText(" Product"+j+"    ");
//                ll.addView(product);
//
//                // Create TextView
////                    TextView price = new TextView(context);
////                    price.setText("  $"+j+"     ");
////                    ll.addView(price);
//
//                // Create Button
//                final Button btn = new Button(context);
//                // Give button an ID
//                btn.setId(j+1);
//                btn.setText("Add To Cart");
//                // set the layoutParams on the button
//                btn.setLayoutParams(params);
//
//                final int index = j;
//                // Set click listener for button
//                btn.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//
//                        Log.i("TAG", "index :" + index);
//
//                        Toast.makeText(getApplicationContext(),
//                                "Clicked Button Index :" + index,
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//                ll.addView(btn);
//                //Add button to LinearLayout defined in XML
//
//                //Add button to LinearLayout
////                    ll.addView(btn);
//                //Add button to LinearLayout defined in XML
//                tb_user.addView(ll);
//            }
//
//            ArrayList<Integer> list2 = new ArrayList<Integer>();
//
//            int count = 0;
//            for (int i2 = 0; i2 < bean.getResponsedata().size(); i2++) {
//                if (bean.getResponsedata().get(i).getUsername().equals(responsedata.getFirstName() + " " + responsedata.getLastName())) {
//                    count++;
//                    break;
////                        list2.add(count);
//                }
//            }

//            Log.d("nizat", "" + count);

            if(responsedatum.getUsername().contains(responsedata.getFirstName() + " " + responsedata.getLastName())){
//
//
//                Log.d("indexxxxx", String.valueOf());
//                responsedatum.getGameid().length();
//                response.

//                                Log.d("index",  "" + response.listIterator());
//                                            response.indexOf(responsedatum);


//                        response.indexOf("biruk yonas");
//            user_name.setText(responsedatum.getUsername());
////          number.setText(bean.getResponsedata().indexOf(responsedatum.getUsername()));
//            number.setText(String.valueOf(i+1));

//            point.setText(responsedatum.getPoint());

//                ArrayList places = new ArrayList<>();
//                places.add(bean.getResponsedata().get(i).getUsername());
//                Log.d("arrays", String.valueOf(places.size()));



//            view_team.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((MainFragmentActivity) context).replaceFragment(new Point_Ranking(context,response.getGameid(),response.getUserid(),activity, user_name));
//                }
//            });

        }

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
