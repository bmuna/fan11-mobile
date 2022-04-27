package com.fan.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.model.recent_transaction.ResDatum;
import com.fan.core.model.recent_transaction.Responsedata;
import com.fan.core.model.transfer_list.PlayerDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * created by mohit.soni @ 24-Jan-20 _ 7:23 PM
 */
public class RecentTransaction_Adapter extends RecyclerView.Adapter<RecentTransaction_Adapter.ViewHolder>  {
    public static final String TAG = Fixtures_Adapter.class.getSimpleName();
    private Context context;
    List<ResDatum> responsedatum = new ArrayList<>();
    private Activity activity;

    public static PlayerDatum[] PLAYERS_DATA;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_recent_transaction_id,tv_recent_transaction_date,tv_recent_transaction_old,
                tv_recent_transaction_new,tv_recent_transaction_method,tv_recent_transaction_type,
                tv_recent_transaction_amount,tv_recent_transaction_status;

        public ViewHolder(View v) {
            super(v);
            tv_recent_transaction_id = (TextView) v.findViewById(R.id.tv_recent_transaction_id);
            tv_recent_transaction_date = (TextView) v.findViewById(R.id.tv_recent_transaction_date);
            tv_recent_transaction_old = (TextView) v.findViewById(R.id.tv_recent_transaction_old);
            tv_recent_transaction_new = (TextView) v.findViewById(R.id.tv_recent_transaction_new);
            tv_recent_transaction_method = (TextView) v.findViewById(R.id.tv_recent_transaction_method);
            tv_recent_transaction_type = (TextView) v.findViewById(R.id.tv_recent_transaction_type);
            tv_recent_transaction_amount = (TextView) v.findViewById(R.id.tv_recent_transaction_amount);
            tv_recent_transaction_status = (TextView)v.findViewById(R.id.tv_recent_transaction_status);
        }
    }

    public RecentTransaction_Adapter(Context context, List<ResDatum> responsedatum, Activity activity) {
        this.context = context;
        this.responsedatum = responsedatum;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_transaction_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ResDatum response = responsedatum.get(position);
        holder.tv_recent_transaction_id.setText(response.getId());
        holder.tv_recent_transaction_date.setText(response.getCreatedAt());
        holder.tv_recent_transaction_old.setText(response.getOldBalance());
        holder.tv_recent_transaction_new.setText(response.getNewBalance());
        holder.tv_recent_transaction_method.setText(response.getPaymentMethod());
        holder.tv_recent_transaction_type.setText(response.getPaymentType());
        holder.tv_recent_transaction_amount.setText(response.getAmount());
        holder.tv_recent_transaction_status.setText(response.getStatus());
    }

    @Override
    public int getItemCount() {
        return responsedatum.size();
    }

    @Override
    public void onViewAttachedToWindow(RecentTransaction_Adapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }

}
