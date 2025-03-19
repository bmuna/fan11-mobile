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

import java.util.ArrayList;

/**
 * Created by mohit.soni @ 21-Oct-19.
 */
public class Transfer_History_Adapter extends RecyclerView.Adapter<Transfer_History_Adapter.ViewHolder> {
    public static final String TAG = Transfer_History_Adapter.class.getSimpleName();
    Context context;
    ArrayList<String> strings = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_transfer_history_item_1, tv_transfer_history_item_2;

        public ViewHolder(View v) {
            super(v);
            tv_transfer_history_item_1 = (TextView) v.findViewById(R.id.tv_transfer_history_item_1);
            tv_transfer_history_item_2 = (TextView) v.findViewById(R.id.tv_transfer_history_item_2);
        }
    }

    public Transfer_History_Adapter(Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_history_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_transfer_history_item_1.setText("P");
        holder.tv_transfer_history_item_2.setText("142");
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
