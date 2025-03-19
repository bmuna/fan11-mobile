package com.fan.core.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;

import java.util.ArrayList;

/**
 * Created by mohit.soni @ 20-Oct-19.
 */
public class Table_Adapter extends RecyclerView.Adapter<Table_Adapter.ViewHolder>  {
    public static final String TAG = Table_Adapter.class.getSimpleName();
    Context context;
    ArrayList<String> strings = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_table_icon;
        TextView tv_table_hash,tv_table_team_name,tv_table_p,tv_table_w_d_l,tv_table_g_d,tv_table_pts;

        public ViewHolder(View v) {
            super(v);
            iv_table_icon = (ImageView) v.findViewById(R.id.iv_table_icon);
            tv_table_hash = (TextView) v.findViewById(R.id.tv_table_hash);
            tv_table_team_name = (TextView) v.findViewById(R.id.tv_table_team_name);
            tv_table_p = (TextView) v.findViewById(R.id.tv_table_p);
            tv_table_w_d_l = (TextView) v.findViewById(R.id.tv_table_w_d_l);
            tv_table_g_d = (TextView) v.findViewById(R.id.tv_table_g_d);
            tv_table_pts = (TextView) v.findViewById(R.id.tv_table_pts);
        }
    }

    public Table_Adapter(Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pl_table_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_table_team_name.setText(strings.get(position));
        holder.tv_table_hash.setText(position+"");
       /* Picasso.get().load(meal.get(0).getImage()).resize(100,100).into(holder.iv_category_icon);*/
        holder.iv_table_icon.setBackgroundResource(R.mipmap.shirt);
        holder.tv_table_p.setText("0");
        holder.tv_table_w_d_l.setText("0-0-0");
        holder.tv_table_pts.setText("0");
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }
}
