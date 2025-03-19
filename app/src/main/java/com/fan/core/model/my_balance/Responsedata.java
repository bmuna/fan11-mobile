
package com.fan.core.model.my_balance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responsedata {

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("current_balance")
    @Expose
    private String currentBalance;
    @SerializedName("total_deposited")
    @Expose
    private Integer totalDeposited;
    @SerializedName("total_winnings")
    @Expose
    private Integer totalWinnings;
    @SerializedName("total_bonus")
    @Expose
    private Integer totalBonus;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Integer getTotalDeposited() {
        return totalDeposited;
    }

    public void setTotalDeposited(Integer totalDeposited) {
        this.totalDeposited = totalDeposited;
    }

    public Integer getTotalWinnings() {
        return totalWinnings;
    }

    public void setTotalWinnings(Integer totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    public Integer getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(Integer totalBonus) {
        this.totalBonus = totalBonus;
    }

}
