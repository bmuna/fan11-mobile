package com.fan.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.module.AddBalanceActivity;

import java.util.ArrayList;

/**
 * created by mohit.soni @ 11/9/2019 _ 5:06 PM
 */
public class GameList_Completed_Adapter extends RecyclerView.Adapter<GameList_Completed_Adapter.ViewHolder>  {
    public static final String TAG = Fixtures_Adapter.class.getSimpleName();
    private Context context;
    ArrayList<String> strings = new ArrayList<>();
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_game_list_amount, tv_game_list_currency, tv_game_list_name,
                tv_game_list_text_1, tv_game_list_sub_text_1,tv_game_list_text_2,tv_game_list_sub_text_2;
        Button bt_game_list_join;

        public ViewHolder(View v) {
            super(v);
            tv_game_list_amount = (TextView) v.findViewById(R.id.tv_game_list_amount);
//            tv_game_list_currency = (TextView) v.findViewById(R.id.tv_game_list_currency);
            tv_game_list_name = (TextView) v.findViewById(R.id.tv_game_list_name);
            tv_game_list_text_1 = (TextView) v.findViewById(R.id.tv_game_list_text_1);
            tv_game_list_sub_text_1 = (TextView) v.findViewById(R.id.tv_game_list_sub_text_1);
            tv_game_list_text_2 = (TextView) v.findViewById(R.id.tv_game_list_text_2);
            tv_game_list_sub_text_2 = (TextView) v.findViewById(R.id.tv_game_list_sub_text_2);
//            bt_game_list_join = (Button) v.findViewById(R.id.bt_game_list_join);
        }
    }

    public GameList_Completed_Adapter(Context context, ArrayList<String> strings, Activity activity) {
        this.context = context;
        this.strings = strings;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_game_list_amount.setText("0");
        holder.tv_game_list_currency.setText(context.getResources().getString(R.string.birr));
        holder.tv_game_list_name.setText("Game 1");
        holder.tv_game_list_text_1.setText("12/12");
        holder.tv_game_list_sub_text_1.setText("Entrant");
        holder.tv_game_list_text_2.setText("6 hours");
        holder.tv_game_list_sub_text_2.setText("Until Draft");
        holder.bt_game_list_join.setText(context.getResources().getString(R.string.completed));

        holder.bt_game_list_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*((MainFragmentActivity) context). replaceFragment(new Transfer(context,activity));*/
                context.startActivity(new Intent(context, AddBalanceActivity.class));
            }
        });
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
