package com.fan.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fan.core.R;
import com.fan.core.model.my_team.JoinGame;
import com.fan.core.model.my_team.League;
import com.fan.core.module_fragment.League_Detail;
import com.fan.core.module_fragment.League_Detail_Child;
import com.fan.core.module_fragment.MainFragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mohit.soni on 20-Jan-20 @ 5:00 PM
 */
public class MyTeam_Adapter extends BaseExpandableListAdapter {
    public static final String TAG = MyTeam_Adapter.class.getSimpleName();
    private Context context;
    private Activity activity;
    Map<League, List<JoinGame>> groupCollection;
    List<League> leagues;
    List<JoinGame> joinGames = new ArrayList<>();

    public MyTeam_Adapter(Context context, List<League> leagues, Activity activity) {
        this.context = context;
        this.leagues = leagues;
        this.activity = activity;
        creteChild();

        for (League league : this.leagues) {
            List<JoinGame> joinGames = league.getJoinGames();
            for (int i = 0; i < joinGames.size(); i++) {
                if (i == joinGames.size() - 1) {
                    joinGames.get(i).setMaxChild(1);
                }
            }
        }
    }

    private void creteChild() {
        groupCollection = new HashMap<>();
        for (League league : leagues) {
            groupCollection.put(league, league.getJoinGames());
        }
    }

    public void log(String msg) {
        Log.i(TAG, msg);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<JoinGame> list = groupCollection.get(leagues.get(groupPosition));
        JoinGame joinGame = list.get(childPosition);
        return joinGame;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final JoinGame joinGame = (JoinGame) getChild(groupPosition, childPosition);
        View view = convertView;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.elv_my_team_list_child, null);
        }
        LinearLayout ll_elv_my_team_list_child = (LinearLayout) view.findViewById(R.id.ll_elv_my_team_list_child);
        TextView tv_elv_my_team_list_child_name = (TextView) view.findViewById(R.id.tv_elv_my_team_list_child_name);
        TextView tv_elv_my_team_list_child_view = (TextView) view.findViewById(R.id.tv_elv_my_team_list_child_view);

        log(String.valueOf(joinGame.getMaxChild() +" : "+ joinGame.getGameName()));
        if (joinGame.getMaxChild() == 1) {
            ll_elv_my_team_list_child.setBackground(context.getResources().getDrawable(R.drawable.round_rectangle_base_back_blue));
            tv_elv_my_team_list_child_name.setBackground(context.getResources().getDrawable(R.drawable.round_rectangle_base_front_left_blue));
            tv_elv_my_team_list_child_view.setBackground(context.getResources().getDrawable(R.drawable.round_rectangle_base_front_right_blue));
        }else if (joinGame.getMaxChild() == 0){
            ll_elv_my_team_list_child.setBackground(context.getResources().getDrawable(R.drawable.rectangle_base_back_blue));
            tv_elv_my_team_list_child_name.setBackground(context.getResources().getDrawable(R.drawable.rectangle_base_front_left_blue));
            tv_elv_my_team_list_child_view.setBackground(context.getResources().getDrawable(R.drawable.rectangle_base_front_right_blue));
        }

        tv_elv_my_team_list_child_name.setText(joinGame.getGameName());
        tv_elv_my_team_list_child_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainFragmentActivity) context).replaceFragment(new League_Detail_Child(context, activity, joinGame.getGameId()));
            }
        });
        return view;
    }


    public int getChildrenCount(int groupPosition) {
        joinGames = groupCollection.get(leagues.get(groupPosition));
        return joinGames.size();
    }

    public Object getGroup(int groupPosition) {
        return leagues.get(groupPosition);
    }

    public int getGroupCount() {
        return leagues.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final League league = (League) getGroup(groupPosition);

        View view = convertView;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.elv_my_team_list_group, null);
        }
        TextView tv_elv_my_team_list_group = (TextView) view.findViewById(R.id.tv_elv_my_team_list_group);
        tv_elv_my_team_list_group.setText(league.getLeagueName());
//        tv_elv_my_team_list_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragmentActivity) context).replaceFragment(new League_Detail(context, activity, league.getLeagueId()));
//            }
//        });
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
