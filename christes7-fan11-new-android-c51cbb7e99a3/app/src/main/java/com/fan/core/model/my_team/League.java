
package com.fan.core.model.my_team;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class League {

    @SerializedName("league_id")
    @Expose
    private String leagueId;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    @SerializedName("join_games")
    @Expose
    private List<JoinGame> joinGames = null;

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public List<JoinGame> getJoinGames() {
        return joinGames;
    }

    public void setJoinGames(List<JoinGame> joinGames) {
        this.joinGames = joinGames;
    }

}
