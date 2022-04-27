package com.fan.core.module_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.adapter.Transfer_History_Adapter;

import static com.fan.core.util.AppConstants.getDummyArrayList2;

/**
 * Created by mohit.soni @ 02-Nov-19.
 */
public class TransferSummary extends BaseFragment {
    Context context;

    public TransferSummary(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainFragmentActivity) context).updateGameList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transfer_summary, container, false);
        final TextView tv_transfer_summary = (TextView) view.findViewById(R.id.tv_transfer_summary);
        final TextView tv_transfer_history = (TextView) view.findViewById(R.id.tv_transfer_history);

        final TextView tv_transfer_summary_team_value = (TextView) view.findViewById(R.id.tv_transfer_summary_team_value);
        final TextView tv_transfer_summary_position_value = (TextView) view.findViewById(R.id.tv_transfer_summary_position_value);
        final TextView tv_transfer_summary_form_value = (TextView) view.findViewById(R.id.tv_transfer_summary_form_value);
        final TextView tv_transfer_summary_gameweek_1_value = (TextView) view.findViewById(R.id.tv_transfer_summary_gameweek_1_value);
        final TextView tv_transfer_summary_total_score_value = (TextView) view.findViewById(R.id.tv_transfer_summary_total_score_value);
        final TextView tv_transfer_summary_value_value = (TextView) view.findViewById(R.id.tv_transfer_summary_value_value);
        final TextView tv_transfer_summary_selected_by_value = (TextView) view.findViewById(R.id.tv_transfer_summary_selected_by_value);

        final LinearLayout ll_transfer_history = (LinearLayout) view.findViewById(R.id.ll_transfer_history);
        final RelativeLayout rl_transfer_summary = (RelativeLayout) view.findViewById(R.id.rl_transfer_summary);


        final RecyclerView rv_transfer_history_1 = (RecyclerView) view.findViewById(R.id.rv_transfer_history_1);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        rv_transfer_history_1.setLayoutManager(mLayoutManager);

        final Transfer_History_Adapter transfer_history_adapter = new Transfer_History_Adapter(context,getDummyArrayList2());

        tv_transfer_summary_team_value.setText("asdasd");
        tv_transfer_summary_position_value.setText("Goal keeper");
        tv_transfer_summary_form_value.setText("0.0");
        tv_transfer_summary_gameweek_1_value.setText("0pts");
        tv_transfer_summary_total_score_value.setText("142");
        tv_transfer_summary_value_value.setText("5.0");
        tv_transfer_summary_selected_by_value.setText("7.6%");

        tv_transfer_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_transfer_history.setVisibility(View.GONE);
                rl_transfer_summary.setVisibility(View.VISIBLE);
                tv_transfer_summary.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                tv_transfer_history.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
        tv_transfer_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_transfer_history.setVisibility(View.VISIBLE);
                rl_transfer_summary.setVisibility(View.GONE);
                tv_transfer_summary.setTextColor(context.getResources().getColor(R.color.white));
                tv_transfer_history.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                rv_transfer_history_1.setAdapter(transfer_history_adapter);
                rv_transfer_history_1.scrollToPosition(transfer_history_adapter.getItemCount() - 1);
            }
        });
        ll_transfer_history.setVisibility(View.GONE);
        rl_transfer_summary.setVisibility(View.VISIBLE);
        tv_transfer_summary.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
        tv_transfer_history.setTextColor(context.getResources().getColor(R.color.white));
        return view;
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {

    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }
}
