package com.fan.core.model.game_joined;

import com.fan.core.model.points.CenterDatum;
import com.fan.core.model.points.DefenderDatum;
import com.fan.core.model.points.ForwardDatum;
import com.fan.core.model.points.GoalkeeperDatum;
import com.fan.core.model.points.Ranking;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Responsedata {

    @SerializedName("field_id")
    @Expose
    private String id;

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    @SerializedName("ranking")
    @Expose
    private List<Ranking> ranking = null;

    @SerializedName("games")
    @Expose
    private List games = null;

    @SerializedName("userPoints")
    @Expose
    private String userPoints;

    @SerializedName("amount")
    @Expose
    private int amount;


    @SerializedName("goalkeeperData")
    @Expose
    private List<GoalkeeperDatum> goalkeeperData = null;
    @SerializedName("defenderData")
    @Expose
    private List<DefenderDatum> defenderData = null;
    @SerializedName("centerData")
    @Expose
    private List<CenterDatum> centerData = null;
    @SerializedName("forwardData")
    @Expose
    private List<ForwardDatum> forwardData = null;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Ranking> getRanking() {
        return ranking;
    }

    public void setRanking(List<Ranking> ranking) {
        this.ranking = ranking;
    }

    public String getField_id() {
        return id;
    }

    public void setField_id(String id) {
        this.id = id;
    }


    public List getGames() {
        return games;
    }

    public void setGames(List games) {
        this.games = games;
    }

    public String getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(String userPoints) {
        this.userPoints = userPoints;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }




    public List<GoalkeeperDatum> getGoalkeeperData() {
        return goalkeeperData;
    }

    public void setGoalkeeperData(List<GoalkeeperDatum> goalkeeperData) {
        this.goalkeeperData = goalkeeperData;
    }

    public List<DefenderDatum> getDefenderData() {
        return defenderData;
    }

    public void setDefenderData(List<DefenderDatum> defenderData) {
        this.defenderData = defenderData;
    }

    public List<CenterDatum> getCenterData() {
        return centerData;
    }

    public void setCenterData(List<CenterDatum> centerData) {
        this.centerData = centerData;
    }

    public List<ForwardDatum> getForwardData() {
        return forwardData;
    }

    public void setForwardData(List<ForwardDatum> forwardData) {
        this.forwardData = forwardData;
    }

}

