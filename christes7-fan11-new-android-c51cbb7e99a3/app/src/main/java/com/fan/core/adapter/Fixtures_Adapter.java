package com.fan.core.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.model.fixtures.FixturesData;
import com.fan.core.util.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mohit.soni @ 21-Oct-19.
 */
public class Fixtures_Adapter extends RecyclerView.Adapter<Fixtures_Adapter.ViewHolder> {
    public static final String TAG = Fixtures_Adapter.class.getSimpleName();
    Context context;
    ArrayList<FixturesData> fixturesDataArrayList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_fixture_icon_1, iv_fixture_icon_2;
        TextView tv_fixture_name_1, tv_fixture_score_1, tv_fixture_name_2, tv_fixture_score_2,
                tv_fixture_time_1, tv_fixture_time_2;
        LinearLayout ll_fixture_;

        public ViewHolder(View v) {
            super(v);
            iv_fixture_icon_1 = (ImageView) v.findViewById(R.id.iv_fixture_icon_1);
            iv_fixture_icon_2 = (ImageView) v.findViewById(R.id.iv_fixture_icon_2);
            tv_fixture_name_1 = (TextView) v.findViewById(R.id.tv_fixture_name_1);
            tv_fixture_score_1 = (TextView) v.findViewById(R.id.tv_fixture_score_1);
            tv_fixture_name_2 = (TextView) v.findViewById(R.id.tv_fixture_name_2);
            tv_fixture_score_2 = (TextView) v.findViewById(R.id.tv_fixture_score_2);
            tv_fixture_time_1 = (TextView) v.findViewById(R.id.tv_fixture_time_1);
            tv_fixture_time_2 = (TextView) v.findViewById(R.id.tv_fixture_time_2);
            /*tv_fixture_date = (TextView) v.findViewById(R.id.tv_fixture_date);*/
            ll_fixture_ = (LinearLayout) v.findViewById(R.id.ll_fixture_);

        }
    }

    public Fixtures_Adapter(Context context, ArrayList<FixturesData> fixturesDataArrayList) {
        this.context = context;
        this.fixturesDataArrayList = fixturesDataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pl_fixture_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FixturesData fixturesData = fixturesDataArrayList.get(position);
        if (position % 2 != 0) {
            holder.ll_fixture_.setBackgroundColor(context.getResources().getColor(R.color.list_even));
        } else {
            holder.ll_fixture_.setBackgroundColor(context.getResources().getColor(R.color.list_odd));
        }

        if(fixturesData.getHomeTeam().contains(" ")){
            String x1 = fixturesData.getHomeTeam();
            String x11 = x1.substring(0, x1.indexOf(" "));
            String x111 = x1.substring(x1.indexOf(" ")+1, x1.length());
            holder.tv_fixture_name_1.setText(x11+"\n"+x111);
        }else{
            holder.tv_fixture_name_1.setText(fixturesData.getHomeTeam());
        }
        if(fixturesData.getAwayTeam().contains(" ")){
            String y1 = fixturesData.getAwayTeam();
            String y11 = y1.substring(0, y1.indexOf(" "));
            String y111 = y1.substring(y1.indexOf(" ")+1, y1.length());
            holder.tv_fixture_name_2.setText(y11+"\n"+y111);
        }else{
            holder.tv_fixture_name_2.setText(fixturesData.getAwayTeam());
        }
        Picasso.get().load("https://www.api-football.com/public/teams/" + fixturesData.getHomeTeamId() + ".png").resize(100, 100).into(holder.iv_fixture_icon_1);
        Picasso.get().load("https://www.api-football.com/public/teams/" + fixturesData.getAwayTeamId() + ".png").resize(100, 100).into(holder.iv_fixture_icon_2);
        holder.tv_fixture_score_1.setText(fixturesData.getGoalsHomeTeam() != null && !fixturesData.getGoalsHomeTeam().toString().equals("null") ? fixturesData.getGoalsHomeTeam().toString() : "");
        holder.tv_fixture_score_2.setText(fixturesData.getGoalsAwayTeam() != null && !fixturesData.getGoalsAwayTeam().toString().equals("null") ? fixturesData.getGoalsAwayTeam().toString() : "");
        holder.tv_fixture_time_1.setText(getTime(fixturesData.getFirstHalfStart()));
        holder.tv_fixture_time_2.setText(getTime(fixturesData.getSecondHalfStart()));
        /*if(fixturesData.getStatus().equals("Not Started")){
            holder.tv_fixture_score_1.setText("Not");
            holder.tv_fixture_score_1.setTextSize(5f);
            holder.tv_fixture_score_2.setText("Started");
            holder.tv_fixture_score_2.setTextSize(5f);
        }else{
            holder.tv_fixture_score_1.setText(fixturesData.getGoalsHomeTeam() != null && !fixturesData.getGoalsHomeTeam().toString().equals("null") ? fixturesData.getGoalsHomeTeam().toString() : "0");
            holder.tv_fixture_score_2.setText(fixturesData.getGoalsAwayTeam() != null && !fixturesData.getGoalsAwayTeam().toString().equals("null") ? fixturesData.getGoalsAwayTeam().toString() : "0");

        }*/
        if (fixturesData.getGoalsHomeTeam().toString().equals("null")) {
            String[] s = fixturesData.getStatus().split(" ");
            if (s.length == 1) {
                holder.tv_fixture_score_1.setText(s[0]);
                holder.tv_fixture_score_1.setTextSize(5f);
                holder.tv_fixture_score_2.setTextSize(5f);
            }
            if (s.length == 2) {
                holder.tv_fixture_score_1.setText(s[0]);
                holder.tv_fixture_score_1.setTextSize(5f);
                holder.tv_fixture_score_2.setText(s[1]);
                holder.tv_fixture_score_2.setTextSize(5f);
            }
        } else {
            holder.tv_fixture_score_1.setText(fixturesData.getGoalsHomeTeam().toString().equals("0") ? "0" : fixturesData.getGoalsHomeTeam().toString());
            holder.tv_fixture_score_2.setText(fixturesData.getGoalsAwayTeam().toString().endsWith("0") ? "0" : fixturesData.getGoalsAwayTeam().toString());
        }
       /* holder.tv_fixture_date.setText(getDate(fixturesData.getEventDate().substring(0, fixturesData.getEventDate().indexOf("T"))));*/
    }

    @Override
    public int getItemCount() {
        return fixturesDataArrayList.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }

    public String getTime(String str) {
        long milliseconds = Long.parseLong(str);
        long millis = milliseconds % 1000;
        long second = (milliseconds / 1000) % 60;
        long minute = (milliseconds / (1000 * 60)) % 60;
        long hour = (milliseconds / (1000 * 60 * 60)) % 24;
        /*String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);*/
        String time = String.format("%02d:%02d", hour, minute);
        log(time);
        if (hour > 12) {
            time = time + "PM";
        } else {
            time = time + "AM";
        }
        return time;
    }

    /*public String getDate(String str) {
        String[] s = str.split("-");
        String mon = AppConstants.getMonth(s[1]);
        return s[2] + " " + mon + "," + s[0];
    }*/
}
