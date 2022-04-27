package com.fan.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fan.core.model.user.Responsedata;


import com.fan.core.R;
import com.fan.core.model.laugue_user.Responsedatum;
import com.fan.core.model.points.Ranking;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.module_fragment.Point_Ranking;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * created by mohit.soni @ 21-Jan-20 _ 4:43 PM
 */
public class Ranking_Adapter extends RecyclerView.Adapter<Ranking_Adapter.ViewHolder> {
    public static final String TAG = Fixtures_Adapter.class.getSimpleName();
    private Context context;
    List<Ranking> responsedatum = new ArrayList<>();
    private Activity activity;
    TextView user_name;
    Responsedata responsedata;
    RecyclerView rv_league_user;
    ;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ranking_no, tv_ranking_name, tv_ranking_pts;
        ImageView trophy;

        public ViewHolder(View v) {
            super(v);
            tv_ranking_no = (TextView) v.findViewById(R.id.tv_ranking_no);
            tv_ranking_name = (TextView) v.findViewById(R.id.tv_ranking_name);
            tv_ranking_pts = (TextView) v.findViewById(R.id.tv_ranking_pts);
            trophy = (ImageView) v.findViewById(R.id.trophy);


        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.rv_league_user = recyclerView;
    }

    public Ranking_Adapter(Context context, List<Ranking> responsedatum, Activity activity, TextView user_name) {
        this.context = context;
        this.responsedatum = responsedatum;
        this.activity = activity;
        this.user_name = user_name;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Ranking response = responsedatum.get(position);
        holder.tv_ranking_no.setText(String.valueOf((position + 1)));
        holder.tv_ranking_name.setText(response.getUsername());
        holder.tv_ranking_pts.setText(response.getPoint());


        if(holder.tv_ranking_pts.getText() == "1"){


            holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.gold));

        }else if(holder.tv_ranking_pts.getText() == "2"){
            holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.silver));

        }else if(holder.tv_ranking_pts.getText() == "3"){
            holder.trophy.setImageDrawable(holder.trophy.getContext().getResources().getDrawable(R.drawable.bronze));

        }

//        user_name.setText(responsedata.getm());
//        user_name.setText(response.getUsername());
//        Log.d("myTag", response.getPoint());
    }

    @Override
    public int getItemCount() {
        return responsedatum.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }

}
