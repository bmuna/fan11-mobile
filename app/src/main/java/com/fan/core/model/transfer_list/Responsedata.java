
package com.fan.core.model.transfer_list;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("resData")
    @Expose
    private ResData resData;
    @SerializedName("allowedTeamPlayers")
    @Expose
    private String allowedTeamPlayers;
    @SerializedName("userBalance")
    @Expose
    private String userBalance;
    @SerializedName("playerData")
    @Expose
    private List<PlayerDatum> playerData = null;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public String getAllowedTeamPlayers() {
        return allowedTeamPlayers;
    }

    public void setAllowedTeamPlayers(String allowedTeamPlayers) {
        this.allowedTeamPlayers = allowedTeamPlayers;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public List<PlayerDatum> getPlayerData() {
        return playerData;
    }

    public void setPlayerData(List<PlayerDatum> playerData) {
        this.playerData = playerData;
    }

}
