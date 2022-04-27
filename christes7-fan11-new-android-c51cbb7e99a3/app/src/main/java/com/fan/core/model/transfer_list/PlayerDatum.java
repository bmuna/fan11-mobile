
package com.fan.core.model.transfer_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerDatum {

    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("player_position")
    @Expose
    private String playerPosition;
    @SerializedName("player_name")
    @Expose
    private String playerName;
    @SerializedName("player_img")
    @Expose
    private String playerImg;
    @SerializedName("team_img")
    @Expose
    private String teamImg;
    @SerializedName("player_point")
    @Expose
    private String playerPoint;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("team_name")
    @Expose
    private String teamName;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerImg() {
        return playerImg;
    }

    public void setPlayerImg(String playerImg) {
        this.playerImg = playerImg;
    }
    public String getTeamImg() {
        return teamImg;
    }

    public void setTeamImg(String teamImg) {
        this.teamImg = teamImg;
    }


    public String getPlayerPoint() {
        return playerPoint;
    }

    public void setPlayerPoint(String playerPoint) {
        this.playerPoint = playerPoint;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
