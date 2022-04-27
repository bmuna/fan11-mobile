
package com.fan.core.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohit.soni @ 08-Oct-19.
 */
public class UserBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responsedata")
    @Expose
    private Responsedata responsedata;

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

    public Responsedata getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(Responsedata responsedata) {
        this.responsedata = responsedata;
    }

}