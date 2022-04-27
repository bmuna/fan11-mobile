
package com.fan.core.model.points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefenderDatum {

    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("player_img")
    @Expose
    private String playerImg;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("isCaptain")
    @Expose
    private String isCaptain;
    @SerializedName("isViceCaptain")
    @Expose
    private String isViceCaptain;
    @SerializedName("scores")
    @Expose
    private List<Score> scores = null;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerImg() {
        return playerImg;
    }

    public void setPlayerImg(String playerImg) {
        this.playerImg = playerImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(String isCaptain) {
        this.isCaptain = isCaptain;
    }

    public String getIsViceCaptain() {
        return isViceCaptain;
    }

    public void setIsViceCaptain(String isViceCaptain) {
        this.isViceCaptain = isViceCaptain;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

}
