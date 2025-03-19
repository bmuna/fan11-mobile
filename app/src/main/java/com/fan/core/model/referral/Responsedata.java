
package com.fan.core.model.referral;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("refer_comm")
    @Expose
    private String referComm;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferComm() {
        return referComm;
    }

    public void setReferComm(String referComm) {
        this.referComm = referComm;
    }

}
