
package com.fan.core.model.my_team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinGame {

    @SerializedName("game_id")
    @Expose
    private String gameId;
    @SerializedName("game_name")
    @Expose
    private String gameName;

    private int maxChild;

    public int getMaxChild() {
        return maxChild;
    }

    public void setMaxChild(int maxChild) {
        this.maxChild = maxChild;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

}
