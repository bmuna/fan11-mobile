
package com.fan.core.model.image_upload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("upload_url")
    @Expose
    private String uploadUrl;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
