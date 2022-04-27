
package com.fan.core.model.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("forget_pass_verification")
    @Expose
    private String forgetPassVerification;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getForgetPassVerification() {
        return forgetPassVerification;
    }

    public void setForgetPassVerification(String forgetPassVerification) {
        this.forgetPassVerification = forgetPassVerification;
    }

}
