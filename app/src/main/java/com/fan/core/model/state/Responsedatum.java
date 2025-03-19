
package com.fan.core.model.state;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedatum {

    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country_id")
    @Expose
    private String country_id;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getStateId() {
        return state_id;
    }

    public void setStateId(String state_id) {
        this.state_id = state_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return country_id;
    }

    public void setCountryId(String country_id) {
        this.country_id = country_id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
