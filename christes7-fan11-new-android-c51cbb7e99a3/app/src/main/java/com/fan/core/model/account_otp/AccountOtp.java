
package com.fan.core.model.account_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountOtp {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responsedata")
    @Expose
    private Integer responsedata;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(Integer responsedata) {
        this.responsedata = responsedata;
    }

}
