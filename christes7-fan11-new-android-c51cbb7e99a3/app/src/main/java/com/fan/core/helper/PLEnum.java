package com.fan.core.helper;

import com.fan.core.R;

/**
 * Created by mohit.soni @ 20-Oct-19.
 */
public enum PLEnum {

    PL1(R.layout.pl_fixtures);

    int pageLayout;

    PLEnum(int layout){
        pageLayout = layout;
    }

    public int getLayoutResId() {
        return pageLayout;
    }
}
