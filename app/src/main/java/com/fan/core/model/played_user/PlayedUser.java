
package com.fan.core.model.played_user;

import java.util.List;

import com.fan.core.model.laugue_user.Responsedatum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayedUser {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responsedata")
    @Expose
    private List<Responsedatum> responsedata = null;

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

    public List<Responsedatum> getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(List<Responsedatum> responsedata) {
        this.responsedata = responsedata;
    }

}