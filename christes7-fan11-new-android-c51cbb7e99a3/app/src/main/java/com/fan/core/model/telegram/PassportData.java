
package com.fan.core.model.telegram;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassportData {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("credentials")
    @Expose
    private Credentials credentials;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

}
