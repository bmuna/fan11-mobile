
package com.fan.core.model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedatum {

    @SerializedName("city_id")
    @Expose
    private String city_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getCityId() {
        return city_id;
    }

    public void setCityId(String cityId) {
        this.city_id = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateId() {
        return state_id;
    }

    public void setStateId(String state_id) {
        this.state_id = state_id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
