package com.fan.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.model.laugue_user.Responsedatum;
import com.fan.core.model.my_team.JoinGame;
import com.fan.core.model.my_team.League;
import com.fan.core.model.points.Points;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module_fragment.League_Detail;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.module_fragment.Point_Ranking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.fan.core.util.AppConstants.getUserSaved;

/**
 * created by mohit.soni @ 21-Jan-20 _ 4:43 PM
 */
public class League_User_Adapter extends RecyclerView.Adapter<League_User_Adapter.ViewHolder> {
    public static final String TAG = Fixtures_Adapter.class.getSimpleName();
    private Context context;
    List<Responsedatum> responsedatum = new ArrayList<>();
    private Activity activity;
    Responsedata responsedata;
    TextView user_name;
    TextView point;
    TextView number;
    TextView view_team;
    int nextVal;
    Ranking_Adapter ranking_adapter;





    RecyclerView rv_league_user;;
    String value;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_league_user_no, tv_league_user_name, tv_league_user_pts, tv_league_user_view;
        ImageView trophy;
        LinearLayout ll_league_user_;

        public ViewHolder(View v) {
            super(v);
            tv_league_user_no = (TextView) v.findViewById(R.id.tv_league_user_no);
            tv_league_user_name = (TextView) v.findViewById(R.id.tv_league_user_name);
            tv_league_user_pts = (TextView) v.findViewById(R.id.tv_league_user_pts);
            tv_league_user_view = (TextView) v.findViewById(R.id.tv_league_user_view);
            trophy = (ImageView) v.findViewById(R.id.trophy);
            ll_league_user_ = (LinearLayout) v.findViewById(R.id.ll_league_user_);


        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.rv_league_user = recyclerView;
    }

    public League_User_Adapter(Context context, List<Responsedatum> responsedatum, Activity activity,TextView number, TextView user_name, TextView point,  TextView view_team, int nextVal, String value) {
        this.context = context;
        this.responsedatum = responsedatum;
        this.activity = activity;
        this.user_name = user_name;
        this.point = point;
        this.number = number;
        this.view_team = view_team;
        this.nextVal = nextVal;
        this.value = value;
//        setHasStableIds(true);

        try {
            responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
//        setHasStableIds(true);

    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {


        final Responsedatum response = responsedatum.get(position);
//        holder.setIsRecyclable(false);
            holder.tv_league_user_no.setText(String.valueOf(position+ nextVal+ 1));
            holder.tv_league_user_name.setText(response.getUsername());
            holder.tv_league_user_pts.setText(response.getPoint());

        if(response.getUsername().contains(responsedata.getFirstName() + " " + responsedata.getLastName())){
            holder.ll_league_user_.setBackgroundColor(Color.RED);


//            number.setText(String.valueOf((position+1)));
//            value = String.valueOf((position+1));
//            value = holder.tv_league_user_no.getText().toString();
            Log.d("value", String.valueOf((position+ nextVal+ 1)));

        }

//
//        if(response.getUsername().contains(responsedata.getFirstName() + " " + responsedata.getLastName())){
//            Log.d("name", responsedata.getFirstName());
//            Log.d("name", response.getUsername());
//            user_name.setText(response.getUsername());
//            number.setText(String.valueOf((position+1)));
//            point.setText(response.getPoint());
//            view_team.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((MainFragmentActivity) context).replaceFragment(new Point_Ranking(context,response.getGameid(),response.getUserid(),activity, user_name));
//                }
//            });

//        }



//        for (int i = 0 ; position <= 10 ; position++) {
//            if (holder.tv_league_user_no.getText().toString() == "1") {
//                holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.silver));
//        Log.d("mfijoljfowfjwpofjwyTag", response.getUserid());
//
//            }
//        }
//                for (int i = 0 ; i <= responsedatum.size() ; i++) {
//                    if (holder.tv_league_user_no.getText().toString().contentEquals("1")) {
//                        holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.gold));
//
////                    } else if (position == 1) {
////                        holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.silver));
////
////                    } else if (position == 2) {
////                        holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.bronze));
////
////                    }
//                    }
//
//                }
        if(holder.tv_league_user_no.getText().toString() == "1"){
            holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.gold));

        }else if(holder.tv_league_user_no.getText().toString() == "2"){
            holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.silver));

        }else if(holder.tv_league_user_no.getText().toString() == "3"){
            holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.bronze));

        }


//        Log.d("myTag", responsedatum.get(0).toString());
//        Log.d("myTag", response.getUserid());




//        if(responsedata.getFirstName() + " " + responsedata.getLastName() == response.getUsername()){
//            user_name.setText(response.getPoint());
//        }


//        user_name.setText(responsedata.getFirstName() + " " + responsedata.getLastName());
//        user_name.setText(responsedata.getUserPoints());


        holder.tv_league_user_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainFragmentActivity) context).replaceFragment(new Point_Ranking(context,response.getGameid(),response.getUserid(),activity, user_name));
            }
        });


    }


//    public ArrayList<Responsedatum> generatePage(int currentPage) {
//        int startItem = currentPage * ITEMS_PER_PAGE;
//        int numOfData = ITEMS_PER_PAGE;
//
//        ArrayList<Responsedatum> pageData = new ArrayList<>();
//        if (currentPage == LAST_PAGE && ITEMS_REMAINING > 0) {
//            for (int i = startItem; i < startItem + ITEMS_REMAINING; i++) {
//                pageData.add(responsedatum.get(po));
//            }
//        } else {
//            for (int i = startItem; i < startItem + numOfData; i++) {
//                pageData.add(league_user_adapter);
//            }
//        }
//        return pageData;
//
//    }
    @Override
    public int getItemCount() {
        return responsedatum.size();
    }




    public void log(String msg) {
        Log.i(TAG, msg);
    }

}
