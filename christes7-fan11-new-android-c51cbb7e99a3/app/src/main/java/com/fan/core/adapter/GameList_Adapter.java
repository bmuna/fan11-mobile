package com.fan.core.adapter;
import com.fan.core.module_fragment.BaseFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fan.core.R;
import com.fan.core.model.get_games.Responsedatum;
import com.fan.core.model.my_balance.MyBalance;
import com.fan.core.model.transfer_list.PlayerDatum;
import com.fan.core.model.user.Responsedata;
import com.fan.core.model.user.UserBean;
import com.fan.core.module.AddBalanceActivity;
import com.fan.core.module.Login;
import com.fan.core.module.MyBalanceActivity;
import com.fan.core.module.Point;
import com.fan.core.module.Ranking;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.module_fragment.Point_Ranking;
import com.fan.core.module_fragment.Transfer2;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.fan.core.util.AppConstants.CURRENT_LANGUAGE;
import static com.fan.core.util.AppConstants.GAMES_JOINED_USER;
import static com.fan.core.util.AppConstants.MY_BALANCE;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.prefUtility;

/**
 * created by mohit.soni @ 11/9/2019 _ 5:06 PM
 */
public class GameList_Adapter extends RecyclerView.Adapter<GameList_Adapter.ViewHolder> {
    public static final String TAG = Fixtures_Adapter.class.getSimpleName();
    private Context context;
    List<Responsedatum> responsedatum = new ArrayList<>();
    private Activity activity;
    Responsedata responsedata;
    Responsedata user_responsedata;
    boolean balance = false;
    public static PlayerDatum[] PLAYERS_DATA;
    String a;



    View windowView;
    WindowManager.LayoutParams params;
    PopupWindow popupWindow;

    View root;
    private TextView user_name;

    public static class ViewHolder extends RecyclerView.ViewHolder {
       Button bt_game_list_join_again;
        TextView tv_game_list_amount, tv_game_list_currency, tv_game_list_name, tv_game_list_prize,
                tv_game_list_text_1, tv_game_list_sub_text_1, tv_game_list_text_2, tv_game_list_sub_text_2,
                tv_game_list_view_points;
        TextView bt_game_list_join, joined_sign;
        TextView button_name;
        LinearLayout button_holder;
        FrameLayout cover, button_cover, edge_color;
        com.github.florent37.shapeofview.shapes.CutCornerView clipCorner;
        ImageView iv_game_list_info, join;


        public ViewHolder(View v) {
            super(v);
            tv_game_list_amount = (TextView) v.findViewById(R.id.tv_game_list_amount);
//            tv_game_list_currency = (TextView) v.findViewById(R.id.tv_game_list_currency);
            tv_game_list_name = (TextView) v.findViewById(R.id.tv_game_list_name);
            tv_game_list_text_1 = (TextView) v.findViewById(R.id.tv_game_list_text_1);
            tv_game_list_sub_text_1 = (TextView) v.findViewById(R.id.tv_game_list_sub_text_1);
            tv_game_list_text_2 = (TextView) v.findViewById(R.id.tv_game_list_text_2);
            tv_game_list_sub_text_2 = (TextView) v.findViewById(R.id.tv_game_list_sub_text_2);
            tv_game_list_prize = (TextView) v.findViewById(R.id.tv_game_list_prize);
            button_name = (TextView) v.findViewById(R.id.button_name);
            button_holder = (LinearLayout) v.findViewById(R.id.button_holder);

//            bt_game_list_join = (TextView) v.findViewById(R.id.bt_game_list_join);
            joined_sign = (TextView) v.findViewById(R.id.joined_sign);
//            clipCorner = (com.github.florent37.shapeofview.shapes.CutCornerView)v.findViewById(R.id.clipCorner);
            join = (ImageView)v.findViewById(R.id.join);
//            edge_color = (FrameLayout)v.findViewById(R.id.edge_color);
//            tv_game_list_currency2 = (TextView)  v.findViewById(R.id.tv_game_list_currency2);
//            cover = (FrameLayout) v.findViewById(R.id.cover);

//            iv_game_list_info = (ImageView) v.findViewById(R.id.iv_game_list_info);
        }
    }

    public GameList_Adapter(Context context, List<Responsedatum> responsedatum, Activity activity) {
        this.context = context;
        this.responsedatum = responsedatum;
        this.activity = activity;
        try {
            responsedata = getUserSaved(context);
        } catch (NullPointerException e) {
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
        root = v;
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Responsedatum response = responsedatum.get(position);
        holder.tv_game_list_amount.setText(response.getEntryFee() + " " + context.getResources().getString(R.string.birr));
        holder.tv_game_list_prize.setText(response.getPrize() + " " + "BIRR");
//        holder.tv_game_list_currency.setText(context.getResources().getString(R.string.birr));
//        holder.tv_game_list_currency2.setText(context.getResources().getString(R.string.birr));
        holder.tv_game_list_name.setText(response.getName());
        holder.tv_game_list_text_1.setText(response.getJoinCount() + "/" + response.getNoOfUsersPermitted());
        holder.tv_game_list_sub_text_1.setText("Entrant");
        String txt = getUntil(response);
//        Log.d("myTag", response.getGames().toString());

        if (Integer.parseInt(response.getJoinCount()) < Integer.parseInt(response.getNoOfUsersPermitted())) {
            /*if (response.getUserJoinStatus().equals("In progress")) {
                if (s[1].equals("false")) {
                    holder.bt_game_list_join.setText(context.getResources().getString(R.string.join));
                    holder.tv_game_list_text_2.setText(txt);
                    holder.tv_game_list_sub_text_2.setText("Until Draft");
                    holder.tv_game_list_view_points.setVisibility(View.GONE);
                } else {
                    holder.bt_game_list_join.setText(context.getResources().getString(R.string.in_progress));
                    holder.tv_game_list_view_points.setVisibility(View.VISIBLE);
                }
            }else{
                holder.bt_game_list_join.setText(context.getResources().getString(R.string.joined));
                holder.tv_game_list_text_2.setText(txt);
                holder.tv_game_list_sub_text_2.setText("Until Draft");
                holder.tv_game_list_view_points.setVisibility(View.GONE);
            }*/
            if (response.getUserJoinStatus().equals("In progress")) {
//                holder.bt_game_list_join.setText(context.getResources().getString(R.string.view_team));
                holder.joined_sign.setText(context.getResources().getString(R.string.in_progress));
                holder.button_name.setText(context.getResources().getString(R.string.view_team));

//                holder.cover.setBackgroundColor(Color.parseColor("#F7E419"));
//                holder.button_cover.setBackgroundColor(Color.WHITE);
//                holder.edge_color.setBackgroundColor(Color.WHITE);


//                holder.bt_game_list_join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new Point_Ranking(context, response.getId(), responsedata.getId(), activity, user_name));
//            }
//        });
//                holder.tv_game_list_view_points.setVisibility(View.VISIBLE);
            }
            if (response.getUserJoinStatus().equals("Joined")) {
                holder.button_name.setText(context.getResources().getString(R.string.join_again));
                holder.joined_sign.setText(context.getResources().getString(R.string.joined));
//                holder.cover.setBackgroundColor(Color.parseColor("#F7E419"));
//                holder.button_cover.setBackgroundColor(Color.WHITE);
//                holder.edge_color.setBackgroundColor(Color.WHITE);

//                holder.bt_game_list_join.setBackgroundColor(Color.RED);
//                holder.bt_game_list_join_again.setVisibility(View.VISIBLE);
//                holder.bt_game_list_join_again.setText(context.getResources().getString(R.string.join_again));
                holder.tv_game_list_text_2.setText(txt);
                holder.tv_game_list_sub_text_2.setText("Until Draft");
//                holder.tv_game_list_view_points.setVisibility(View.GONE);
            }
            if (response.getUserJoinStatus().equals("Join")) {
//                holder.bt_game_list_join.setText(context.getResources().getString(R.string.join));
//                holder.joined_sign.setVisibility(View.GONE);
//                holder.cover.setBackgroundColor(Color.WHITE);
//                holder.button_cover.setBackgroundColor(Color.parseColor("#F7E419"));
//                holder.edge_color.setBackgroundColor(Color.parseColor("#F7E419"));
                holder.button_name.setText(context.getResources().getString(R.string.join));

                holder.tv_game_list_text_2.setText(txt);
                holder.tv_game_list_sub_text_2.setText("Until Draft");
//                holder.tv_game_list_view_points.setVisibility(View.GONE);
            }
        } else {
            holder.bt_game_list_join.setText(context.getResources().getString(R.string.full));
        }

//        holder.tv_game_list_count.setText(response.getNoOfPlayers());




        holder.button_holder.setOnClickListener(new View.OnClickListener() {



//            String title = "", msg = "";

            @Override
            public void onClick(View v) {

                if (Integer.parseInt(response.getJoinCount()) < Integer.parseInt(response.getNoOfUsersPermitted())) {
                    if (response.getUserJoinStatus().equals("Join")) {
                        if(response.getLocked().equals("Locked")){
                            Log.d("locked", response.getLocked());
                            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle("Locked");
                            builder.setMessage("This is locked game. Enter password");
                            final EditText input = new EditText(context);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            input.setLayoutParams(lp);
                            builder.setView(input);
                            builder.setCancelable(false);
                            builder.setPositiveButton(context.getResources().getString(android.R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.d("password", "" + response.getPassword());

                                            if(response.getPassword().equals(input.getText().toString())){
                                             Log.d("true", "true");

                                                Log.d("myTag", responsedata.getMyBalance());
                                                if (Integer.parseInt(responsedata.getMyBalance()) >= Integer.parseInt(response.getEntryFee())) {
                                                    balance = true;
                                                }
                                                String title = "", msg = "";
                                                if (!balance) {
                                                    title = "Information";
                                                    msg = "Your balance is low. Please recharge your wallet.";
                                                } else {
//
                                                    msg = response.getGameInfo();
//
                                                }
                                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                                builder.setTitle(title);
                                                builder.setMessage(msg);
                                                builder.setCancelable(false);
                                                builder.setPositiveButton(context.getResources().getString(android.R.string.ok),
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                if (balance) {
                                                                    PLAYERS_DATA = new PlayerDatum[Integer.parseInt(response.getNoOfPlayers())];
                                                                    ((MainFragmentActivity) context).updateTransfer();
                                                                    ((MainFragmentActivity) context).replaceFragment(new Transfer2(context, activity, response));
                                                                } else {
                                                                    context.startActivity(new Intent(activity, AddBalanceActivity.class));
                                                                }

                                                            }
                                                        });
                                                builder.setNegativeButton(context.getResources().getString(android.R.string.cancel),
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                final AlertDialog alertDialog = builder.create();
                                                alertDialog.show();

                                         }else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                                builder.setTitle("Password incorrect");//
                                                builder.setCancelable(false);
                                                builder.setNegativeButton(context.getResources().getString(android.R.string.ok),
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                final AlertDialog alertDialog = builder.create();
                                                alertDialog.show();
                                            }

                                         }


                                    });
                            builder.setNegativeButton(context.getResources().getString(android.R.string.cancel),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();


                        }else if(response.getLocked().equals("Un-locked")) {

                            Log.d("myTag", responsedata.getMyBalance());
                            if (Integer.parseInt(responsedata.getMyBalance()) >= Integer.parseInt(response.getEntryFee())) {
                                balance = true;
                            }
                            String title = "", msg = "";
                            if (!balance) {
                                title = "Information";
                                msg = "Your balance is low. Please recharge your wallet.";
                            } else {
//                            int[] out = new int[2];
//                            v.getLocationOnScreen(out);
//                            try {
//                                callPopUp(out, root, response);
//                            } catch (WindowManager.BadTokenException e) {
//                                e.printStackTrace();
//                            }
                                msg = response.getGameInfo();
//                            title = "Player";
//                            msg = "This game requires " + response.getNoOfPlayers()
//                                    + " players. View team after finishing selecting players.";
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(title);
                            builder.setMessage(msg);
                            builder.setCancelable(false);
                            builder.setPositiveButton(context.getResources().getString(android.R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (balance) {
                                                PLAYERS_DATA = new PlayerDatum[Integer.parseInt(response.getNoOfPlayers())];
                                                ((MainFragmentActivity) context).updateTransfer();
                                                ((MainFragmentActivity) context).replaceFragment(new Transfer2(context, activity, response));
                                            } else {
                                                context.startActivity(new Intent(activity, AddBalanceActivity.class));
                                            }

                                        }
                                    });
                            builder.setNegativeButton(context.getResources().getString(android.R.string.cancel),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        if(response.getUserJoinStatus().equals("Joined")){
                            if (Integer.parseInt(responsedata.getMyBalance()) >= Integer.parseInt(response.getEntryFee())) {
                                balance = true;
                            }
                            String title = "", msg = "";
                            if (!balance) {
                                title = "Information";
                                msg = "Your balance is low. Please recharge your wallet.";
                            } else {
//                            int[] out = new int[2];
//                            v.getLocationOnScreen(out);
//                            try {
//                                callPopUp(out, root, response);
//                            } catch (WindowManager.BadTokenException e) {
//                                e.printStackTrace();
//                            }
                                msg = response.getGameInfo();
//                            title = "Player";
//                            msg = "This game requires " + response.getNoOfPlayers()
//                                    + " players. View team after finishing selecting players.";
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(title);
                            builder.setMessage(msg);
                            builder.setCancelable(false);
                            builder.setPositiveButton(context.getResources().getString(android.R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (balance) {
                                                PLAYERS_DATA = new PlayerDatum[Integer.parseInt(response.getNoOfPlayers())];
                                                ((MainFragmentActivity) context).updateTransfer();
                                                ((MainFragmentActivity) context).replaceFragment(new Transfer2(context, activity, response));
                                            } else {
                                                context.startActivity(new Intent(activity, AddBalanceActivity.class));
                                            }

                                        }
                                    });
                            builder.setNegativeButton(context.getResources().getString(android.R.string.cancel),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }

                        }






                    if(response.getUserJoinStatus().equals("In progress")){
                        holder.button_holder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                RequestParams params = new RequestParams();
//                                params.put("user_id", response.getId());
//                                params.put("game_id", responsedata.getId());
//                                (NetworkAPI.GAMES_JOINED_USER, params, GAMES_JOINED_USER);
                                ((MainFragmentActivity) context).replaceFragment(new Point(context, response.getId(), responsedata.getId(), response.getGames(),activity, user_name));
                            }
                        });
                    }                }
            }
        });



//        holder.bt_game_list_join_again.setOnClickListener(new View.OnClickListener() {
////            String title = "", msg = "";
//
//            @Override
//            public void onClick(View v) {
//                if (Integer.parseInt(response.getJoinCount()) < Integer.parseInt(response.getNoOfUsersPermitted())) {
//                    if (response.getUserJoinStatus().equals("Joined")) {
//                        Log.d("myTag", responsedata.getMyBalance());
//                        if (Integer.parseInt(responsedata.getMyBalance()) >= Integer.parseInt(response.getEntryFee())) {
//                            balance = true;
//                        }
//                        String title = "", msg = "";
//                        if (!balance) {
//                            title = "Information";
//                            msg = "Your balance is low. Please recharge your wallet.";
//                        } else {
////                            int[] out = new int[2];
////                            v.getLocationOnScreen(out);
////                            try {
////                                callPopUp(out, root, response);
////                            } catch (WindowManager.BadTokenException e) {
////                                e.printStackTrace();
////                            }
//                            msg = response.getGameInfo();
////                            title = "Player";
////                            msg = "This game requires " + response.getNoOfPlayers()
////                                    + " players. View team after finishing selecting players.";
//                        }
//                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                        builder.setTitle(title);
//                        builder.setMessage(msg);
//                        builder.setCancelable(false);
//                        builder.setPositiveButton(context.getResources().getString(android.R.string.ok),
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        if (balance) {
//                                            PLAYERS_DATA = new PlayerDatum[Integer.parseInt(response.getNoOfPlayers())];
//                                            ((MainFragmentActivity) context).updateTransfer();
//                                            ((MainFragmentActivity) context).replaceFragment(new Transfer2(context, activity, response));
//                                        } else {
//                                            context.startActivity(new Intent(activity, AddBalanceActivity.class));
//                                        }
//
//                                    }
//                                });
//                        builder.setNegativeButton(context.getResources().getString(android.R.string.cancel),
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        final AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                    }
//                }
//            }
//        });
//        holder.iv_game_list_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int[] out = new int[2];
//                v.getLocationOnScreen(out);
//                try {
//                    callPopUp(out, root, response);
//                } catch (WindowManager.BadTokenException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
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

    public String getUntil(Responsedatum response) {
        boolean expire = false;
        String time = response.getGameTime();
        String date = response.getGameDate();
        String[] s = date.split("-");
        String[] t = time.split(":");
        int g_day = Integer.parseInt(s[2]);
        int g_mon = Integer.parseInt(s[1]);
        int g_hr = Integer.parseInt(t[0]);
        int g_min = Integer.parseInt(t[1]);
       /* int g_day = 01;
        int g_mon = 07;
        int g_hr = 14;
        int g_min = 30;*/

        DateFormat dateFormatdisplay = new SimpleDateFormat("MM-dd-yyyy");
        DateFormat timeFormatdisplay = new SimpleDateFormat("HH:mm:ss");// hh - 12 g_hr, HH - 24hr
        Date dates = new Date();
        String dateFormatdisplay_ = dateFormatdisplay.format(dates) + "";
        String[] dateFormatdisplay__ = dateFormatdisplay_.split("-");
        int c_day = Integer.parseInt(dateFormatdisplay__[1]);
        int c_mon = Integer.parseInt(dateFormatdisplay__[0]);
       /* int c_day = 27;
        int c_mon = 06;*/
        String timeFormatdisplay_ = timeFormatdisplay.format(dates) + "";
        String[] timeFormatdisplay__ = timeFormatdisplay_.split(":");
        int c_hr = Integer.parseInt(timeFormatdisplay__[0]);
        int c_min = Integer.parseInt(timeFormatdisplay__[1]);
        /*int c_hr = 8;
        int c_min = 10;*/
        int d = 0;
        String timeLeft = "";
        if (g_mon < c_mon) {
            int m = 60 - g_min;
            int h = 23 - g_hr;

            d = getMonthCode(g_mon + "") - g_day;
            g_mon = g_mon + 1;
            while (g_mon != c_mon) {
                g_mon = g_mon + 1;
                d = d + getMonthCode(g_mon + "");
            }
            d = d + (c_day - 1);
            int m__ = m + c_min;
            int h__ = h + c_hr;
            if (m__ > 59) {
                m__ = m__ - 59;
                h__ = h__ + 1;
            }
            if (h__ > 23) {
                h__ = h__ - 23;
                d = d + 1;
            }
            timeLeft = d + " days - \n" + h__ + ":" + m__ + " Hours";
        }
        if (g_mon == c_mon) {
            if (g_day < c_day) {
                int m = 60 - g_min;
                int h = 23 - g_hr;
                d = c_day - g_day - 1;
                int m__ = m + c_min;
                int h__ = h + c_hr;
                if (m__ > 59) {
                    m__ = m__ - 59;
                    h__ = h__ + 1;
                }
                if (h__ > 23) {
                    h__ = h__ - 23;
                    d = d + 1;
                }
                timeLeft = d + " days - \n" + h__ + ":" + m__ + " Hours";
            }
            if (g_day > c_day) {
                int m = 60 - c_min;
                int h = 23 - c_hr;
                d = g_day - c_day - 1;
                int m__ = m + g_min;
                int h__ = h + g_hr;
                if (m__ > 59) {
                    m__ = m__ - 59;
                    h__ = h__ + 1;
                }
                if (h__ > 23) {
                    h__ = h__ - 23;
                    d = d + 1;
                }
                timeLeft = d + " days - \n" + h__ + ":" + m__ + " Hours";
            }
            if (g_day == c_day) {
                if (g_hr < c_hr) {
                    int m = 60 - g_min;
                    int m__ = m + c_min;
                    int h__ = (c_hr - g_hr) - 1;
                    if (m__ > 59) {
                        m__ = m__ - 59;
                        h__ = h__ + 1;
                    }
                    if (h__ > 23) {
                        h__ = h__ - 23;
                    }
                    timeLeft = h__ + ":" + m__ + " Hours";
                }
                if (g_hr > c_hr) {
                    int m = 60 - c_min;
                    int m__ = m + g_min;
                    int h__ = (g_hr - c_hr) - 1;
                    if (m__ > 59) {
                        m__ = m__ - 59;
                        h__ = h__ + 1;
                    }
                    if (h__ > 23) {
                        h__ = h__ - 23;
                    }
                    timeLeft = h__ + ":" + m__ + " Hours";
                }
            }
        }
        if (g_mon > c_mon) {
            int m = 60 - c_min;
            int h = 23 - c_hr;

            d = getMonthCode(c_mon + "") - c_day;
            c_mon = c_mon + 1;
            while (c_mon != c_mon) {
                c_mon = c_mon + 1;
                d = d + getMonthCode(c_mon + "");
            }
            d = d + (g_day);
            int m__ = m + g_min;
            int h__ = h + g_hr;
            if (m__ > 59) {
                m__ = m__ - 59;
                h__ = h__ + 1;
            }
            if (h__ > 23) {
                h__ = h__ - 23;
                d = d + 1;
            }
            timeLeft = d + " days - \n" + h__ + ":" + m__ + " Hours";
        }
        return timeLeft;
    }

    public String add(Integer x) {
        String y = Integer.toString(x);
        return y.length() == 1 ? "0" + y : y;
    }

    public void callPopUp(int[] out, View views, Responsedatum response) {
        int layout_params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_params = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layout_params = WindowManager.LayoutParams.TYPE_PHONE;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
//        AlertDialog dialog = new AlertDialog();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        double c = width / 1.5;
        params = new WindowManager.LayoutParams(
                (int) c,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_params,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        params.x = out[0] - ((int) (views.getWidth() * .63));
        params.y = out[1] + ((int) (views.getHeight() * .20));
        params.gravity = Gravity.LEFT | Gravity.TOP;

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        windowView = inflater.inflate(R.layout.game_list_i_popup, null);

        LinearLayout ll_popup_info_item = (LinearLayout) windowView.findViewById(R.id.ll_popup_info_item);
        TextView tv_popup_info_item_1 = (TextView) windowView.findViewById(R.id.tv_popup_info_item_1);
        /*TextView tv_popup_info_item_2_value = (TextView) windowView.findViewById(R.id.tv_popup_info_item_2_value);
        TextView tv_popup_info_item_3_value = (TextView) windowView.findViewById(R.id.tv_popup_info_item_3_value);
        TextView tv_popup_info_item_4_value = (TextView) windowView.findViewById(R.id.tv_popup_info_item_4_value);
        TextView tv_popup_info_item_5_value = (TextView) windowView.findViewById(R.id.tv_popup_info_item_5_value);*/

        /*String date = response.getGameDate();
        String[] date_array = date.split("-");

        String time = response.getGameTime();
        String[] time_array = time.split(":");
        String type = "AM";

        int x = Integer.parseInt(time_array[0]);
        if (x > 12) {
            x = x - 12;
            type = "PM";
        } else {
            type = "AM";
        }*/

       /* tv_popup_info_item_1_value.setText(getMonth(date_array[1]) + " " + date_array[2] + ", \n" + date_array[0]);
        tv_popup_info_item_2_value.setText(x + ":" + time_array[1] + " " + type);
        tv_popup_info_item_3_value.setText(response.getEntryFee());
        tv_popup_info_item_4_value.setText(response.getNoOfPlayers());
        tv_popup_info_item_5_value.setText(response.getNoOfUsersPermitted());*/


        tv_popup_info_item_1.setText(response.getGameInfo());

        ll_popup_info_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        boolean focusable = true; // lets taps outside the popup also dismiss it

        popupWindow = new PopupWindow(windowView, params.width, params.height, focusable);

        if (!response.getGameInfo().equals("")) {
            popupWindow.showAtLocation(windowView, params.gravity, params.x, params.y);
        } else {
            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    public int getMonthCode(String value) {
        switch (value) {
            case "01":
                return 31;

            case "02":
                return 28;

            case "03":
                return 31;

            case "04":
                return 30;

            case "05":
                return 31;

            case "06":
                return 30;

            case "07":
                return 31;

            case "08":
                return 31;

            case "09":
                return 30;

            case "10":
                return 31;

            case "11":
                return 30;

            case "12":
                return 31;
        }
        return 30;
    }

}
