
package com.fan.core.model.forget;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("forget_pass_otp")
    @Expose
    private Integer forgetPassOtp;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getForgetPassOtp() {
        return forgetPassOtp;
    }

    public void setForgetPassOtp(Integer forgetPassOtp) {
        this.forgetPassOtp = forgetPassOtp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
