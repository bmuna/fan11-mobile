package com.fan.core.helper;

import com.fan.core.R;

public enum MyTeamSlide {

    PointLeague1(R.layout.point_layout),
    PointLeague2(R.layout.point_layout);

    int pageLayout;

    MyTeamSlide(int layout)
    {
        pageLayout = layout;
    }

    public int getLayoutResId()
    {
        return pageLayout;
    }
}