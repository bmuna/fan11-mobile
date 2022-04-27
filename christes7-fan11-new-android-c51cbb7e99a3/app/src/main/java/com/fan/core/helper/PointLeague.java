package com.fan.core.helper;

import com.fan.core.R;

/**
 * Created by mohit.soni @ 20-Oct-19.
 */
public enum PointLeague {


    PointLeague1(R.layout.point_layout),
    PointLeague2(R.layout.league_user);

    int pageLayout;


    PointLeague(int layout){
        pageLayout = layout;
    }

    public int getLayoutResId() {
        return pageLayout;
    }
}
