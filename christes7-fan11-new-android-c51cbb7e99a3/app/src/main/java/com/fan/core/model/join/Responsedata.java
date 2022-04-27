
package com.fan.core.model.join;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
