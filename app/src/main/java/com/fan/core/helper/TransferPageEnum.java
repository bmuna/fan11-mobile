package com.fan.core.helper;

import com.fan.core.R;

/**
 * Created by mohit.soni on 28-May-20.5:21 PM
 */
public enum TransferPageEnum {
    L1(R.layout.transfer_pager_gpk),
    L2(R.layout.transfer_pager_def),
    L3(R.layout.transfer_pager_mid),
    L4(R.layout.transfer_pager_fwd);

    int pageLayout;

    TransferPageEnum(int layout){
        pageLayout = layout;
    }

    public int getLayoutResId() {
        return pageLayout;
    }
}
