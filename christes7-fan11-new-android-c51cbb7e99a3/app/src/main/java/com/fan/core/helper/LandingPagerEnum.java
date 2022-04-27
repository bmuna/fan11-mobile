package com.fan.core.helper;


import com.fan.core.R;

/**
 * Created by mohit.soni @ 01-Oct-19
 */

public enum LandingPagerEnum {

    L1(R.layout.step1),
    L2(R.layout.step2),
    L3(R.layout.step3),
    L4(R.layout.step4),
    L5(R.layout.step5),
    L6(R.layout.step6),
    L7(R.layout.step7),
    L8(R.layout.step8),
    L9(R.layout.step9),
    L10(R.layout.step10);

    int pageLayout;

    LandingPagerEnum(int layout){
        pageLayout = layout;
    }

    public int getLayoutResId() {
        return pageLayout;
    }
}
