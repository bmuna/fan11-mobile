package com.fan.core.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.helper.UpdateProgress;
import com.fan.core.model.transfer_list.PlayerDatum;
import com.fan.core.model.transfer_list.Responsedata;
import com.fan.core.module_fragment.Transfer2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.fan.core.module_fragment.Transfer2.allowedList;

/**
 * Created by mohit.soni @ 21-Oct-19.
 */
public class Transfer_GKP_Adapter extends RecyclerView.Adapter<Transfer_GKP_Adapter.ViewHolder> {
    public static final String TAG = Transfer_GKP_Adapter.class.getSimpleName();
    Context context;
    Responsedata root_responsedata, inx_responsedata;
    String type;
    String allowedTeamPlayer;
    UpdateProgress updateProgress;
    /*HashMap<Integer, Integer> allowed = new HashMap<>();*/
    /*View windowView;
    WindowManager.LayoutParams params;
    PopupWindow popupWindow;*/

    int index = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_transfer_list_icon;
        TextView tv_transfer_list_name, tv_transfer_list_type, tv_transfer_list_pts;
        ImageView tv_transfer_list_team_name;
        LinearLayout ll_transfer_list_;

        public ViewHolder(View v) {
            super(v);
            iv_transfer_list_icon = (ImageView) v.findViewById(R.id.iv_transfer_list_icon);
            tv_transfer_list_name = (TextView) v.findViewById(R.id.tv_transfer_list_name);
            tv_transfer_list_type = (TextView) v.findViewById(R.id.tv_transfer_list_type);
            tv_transfer_list_team_name = (ImageView) v.findViewById(R.id.tv_transfer_list_team_name);
            tv_transfer_list_pts = (TextView) v.findViewById(R.id.tv_transfer_list_pts);
            ll_transfer_list_ = (LinearLayout) v.findViewById(R.id.ll_transfer_list_);
        }
    }

    public Transfer_GKP_Adapter(Context context, Responsedata responsedata,
                                String type, String allowedTeamPlayer, UpdateProgress progress) {
        this.context = context;
        this.type = type;
        this.updateProgress = progress;
        this.allowedTeamPlayer = allowedTeamPlayer;
        this.root_responsedata = responsedata;
        updateIndex();
        /* addTeamPlayerAllowed(allowedTeamPlayer);*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pl_transfer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PlayerDatum playerDatum = inx_responsedata.getPlayerData().get(position);
        if (position % 2 != 0) {
            holder.ll_transfer_list_.setBackgroundColor(context.getResources().getColor(R.color.list_even));
        } else {
            holder.ll_transfer_list_.setBackgroundColor(context.getResources().getColor(R.color.list_odd));
        }
        holder.tv_transfer_list_name.setText(playerDatum.getPlayerName());
        Picasso.get().load(playerDatum.getPlayerImg()).resize(100, 100).into(holder.iv_transfer_list_icon);
        holder.tv_transfer_list_type.setText(playerDatum.getPlayerPosition());
        Picasso.get().load(playerDatum.getTeamImg()).resize(120, 120).into(holder.tv_transfer_list_team_name);

//        holder.tv_transfer_list_team_name.setText(playerDatum.getTeamName());
        holder.tv_transfer_list_pts.setText(playerDatum.getPlayerPoint());
        holder.ll_transfer_list_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("GKP")) {
                    if (checkIdForGoalkeeper(playerDatum.getPlayerId())) {
                        allowedList.remove(Integer.parseInt(Transfer2.GKP[index].getPlayerId()));
                        Transfer2.GKP[index] = null;
                        notifyDataSetChanged();
                    } else {
                        if (!checkTeamPlayerAllowed(playerDatum)) {
                            return;
                        }
                        boolean haveGaol = false;
                        for (int i = 0; i < Transfer2.GKP.length; i++) {
                            if (Transfer2.GKP[i] == null) {
                                Transfer2.GKP[i] = playerDatum;
                                allowedList.put(Integer.parseInt(Transfer2.GKP[i].getPlayerId()),Transfer2.GKP[i]);
                                haveGaol = true;
                                break;
                            }
                        }
                        if (!haveGaol) {
                            Toast.makeText(context, "Maximum " + Transfer2.GKP.length + " Player allowed for this position. Please select players for other positions.", Toast.LENGTH_SHORT).show();
                        } else {
                            notifyDataSetChanged();
                        }
                    }
                    updateProgress.update();
                }
            }
        });
       /* holder.ll_transfer_.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int[] out = new int[2];
                v.getLocationOnScreen(out);
                try {
                    callPopUp(out, v);
                } catch (WindowManager.BadTokenException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });*/
        if (type.equals("GKP")) {
            if (checkIdForGoalkeeper(playerDatum.getPlayerId())) {
                holder.ll_transfer_list_.setBackgroundColor(context.getResources().getColor(R.color.app_dark_yellow));
            }
        }
    }

    @Override
    public int getItemCount() {
        return inx_responsedata.getPlayerData().size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }

    /*public void callPopUp(int[] out, View views) {
        int layout_params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_params = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layout_params = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_params,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.x = (views.getWidth() / 2) + 100;
        params.y = out[1] - ((int) (views.getHeight() * 1.5));
        params.gravity = Gravity.TOP | Gravity.LEFT;

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        windowView = inflater.inflate(R.layout.transfer_list_popup, null);
        TextView tv_transfer_list_popup_player_info = (TextView) windowView.findViewById(R.id.tv_transfer_list_popup_player_info);
        TextView tv_transfer_list_popup_transfer = (TextView) windowView.findViewById(R.id.tv_transfer_list_popup_transfer);

        tv_transfer_list_popup_player_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ((MainFragmentActivity) context).replaceFragment(new TransferSummary(context));
            }
        });
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(windowView, params.width, params.height, focusable);

        popupWindow.showAtLocation(windowView, params.gravity, params.x, params.y);
    }*/

    public boolean checkIdForGoalkeeper(String id) {
        for (int i = 0; i < Transfer2.GKP.length; i++) {
            PlayerDatum responsedatum = Transfer2.GKP[i];
            if (responsedatum != null) {
                if (responsedatum.getPlayerPosition().equals("GKP")) {
                    if (responsedatum.getPlayerId().equals(id)) {
                        index = i;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void search(String str) {
        if (!str.equals("")) {
            Responsedata responsedata = new Responsedata();
            responsedata.setAllowedTeamPlayers(root_responsedata.getAllowedTeamPlayers());
            responsedata.setErrorMessage(root_responsedata.getErrorMessage());
            responsedata.setResData(root_responsedata.getResData());
            responsedata.setUserBalance(root_responsedata.getUserBalance());

            List<PlayerDatum> playerData = new ArrayList<>();
            for (int i = 0; i < root_responsedata.getPlayerData().size(); i++) {
                PlayerDatum playerDatum = root_responsedata.getPlayerData().get(i);
                String s = playerDatum.getPlayerName().toLowerCase();
                if (s.contains(str)) {
                    playerData.add(playerDatum);
                }
            }
            responsedata.setPlayerData(playerData);
            inx_responsedata = responsedata;
        } else {
            inx_responsedata = root_responsedata;
        }
        notifyDataSetChanged();
    }

    public boolean checkTeamPlayerAllowed(PlayerDatum playerDatum) {
        try {
            int teamID = Integer.parseInt(playerDatum.getTeamId());
            int allowedteamID = 0;
            if (Transfer2.allowed.containsKey(teamID)) {
                allowedteamID = Transfer2.allowed.get(teamID);
            } else {
                return false;
            }
            int x = 0;
            for (int i = 0; i < Transfer2.GKP.length; i++) {
                if (Transfer2.GKP[i] != null) {
                    if (Transfer2.GKP[i].getTeamId().equals(playerDatum.getTeamId())) {
                        x++;
                    }
                }
            }
            for (int i = 0; i < Transfer2.DEF.length; i++) {
                if (Transfer2.DEF[i] != null) {
                    if (Transfer2.DEF[i].getTeamId().equals(playerDatum.getTeamId())) {
                        x++;
                    }
                }
            }
            for (int i = 0; i < Transfer2.MID.length; i++) {
                if (Transfer2.MID[i] != null) {
                    if (Transfer2.MID[i].getTeamId().equals(playerDatum.getTeamId())) {
                        x++;
                    }
                }
            }
            for (int i = 0; i < Transfer2.FWD.length; i++) {
                if (Transfer2.FWD[i] != null) {
                    if (Transfer2.FWD[i].getTeamId().equals(playerDatum.getTeamId())) {
                        x++;
                    }
                }
            }
            if (x < allowedteamID) {
                return true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Maximum player allowed from this team is full. Please select player from other teams.", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void updateIndex() {
        try {
            String all = allowedTeamPlayer;
            String[] alll = all.split(",");
            List<PlayerDatum> list = new ArrayList<>();

            inx_responsedata = new Responsedata();
            inx_responsedata.setUserBalance(root_responsedata.getUserBalance());
            inx_responsedata.setResData(root_responsedata.getResData());
            inx_responsedata.setErrorMessage(root_responsedata.getErrorMessage());
            inx_responsedata.setAllowedTeamPlayers(root_responsedata.getAllowedTeamPlayers());
            for (PlayerDatum playerDatum : root_responsedata.getPlayerData()) {
                for (int i = 0; i < alll.length; i++) {
                    String[] a = alll[i].split("-");
                    if (playerDatum.getTeamId().equals(a[0])) {
                        list.add(playerDatum);
                    }
                }

            }
            inx_responsedata.setPlayerData(list);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
