
package com.fan.core.model.recent_transaction;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("resData")
    @Expose
    private List<ResDatum> resData = null;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ResDatum> getResData() {
        return resData;
    }

    public void setResData(List<ResDatum> resData) {
        this.resData = resData;
    }

}
