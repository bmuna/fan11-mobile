
package com.fan.core.model.transfer_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("game_date")
    @Expose
    private String gameDate;
    @SerializedName("game_time")
    @Expose
    private String gameTime;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("entry_fee")
    @Expose
    private String entryFee;
    @SerializedName("type_of_game")
    @Expose
    private String typeOfGame;
    @SerializedName("no_of_players")
    @Expose
    private String noOfPlayers;
    @SerializedName("salary_cap")
    @Expose
    private String salaryCap;
    @SerializedName("fantasy_format")
    @Expose
    private String fantasyFormat;
    @SerializedName("game_type")
    @Expose
    private String gameType;
    @SerializedName("no_of_users_permitted")
    @Expose
    private String noOfUsersPermitted;
    @SerializedName("active_teams")
    @Expose
    private String activeTeams;
    @SerializedName("max_gkp")
    @Expose
    private String maxGkp;
    @SerializedName("max_def")
    @Expose
    private String maxDef;
    @SerializedName("max_mid")
    @Expose
    private String maxMid;
    @SerializedName("max_fwd")
    @Expose
    private String maxFwd;
    @SerializedName("allowed_players_in_team")
    @Expose
    private String allowedPlayersInTeam;
    @SerializedName("winning_amt_by_rank")
    @Expose
    private String winningAmtByRank;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getTypeOfGame() {
        return typeOfGame;
    }

    public void setTypeOfGame(String typeOfGame) {
        this.typeOfGame = typeOfGame;
    }

    public String getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(String noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public String getSalaryCap() {
        return salaryCap;
    }

    public void setSalaryCap(String salaryCap) {
        this.salaryCap = salaryCap;
    }

    public String getFantasyFormat() {
        return fantasyFormat;
    }

    public void setFantasyFormat(String fantasyFormat) {
        this.fantasyFormat = fantasyFormat;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getNoOfUsersPermitted() {
        return noOfUsersPermitted;
    }

    public void setNoOfUsersPermitted(String noOfUsersPermitted) {
        this.noOfUsersPermitted = noOfUsersPermitted;
    }

    public String getActiveTeams() {
        return activeTeams;
    }

    public void setActiveTeams(String activeTeams) {
        this.activeTeams = activeTeams;
    }

    public String getMaxGkp() {
        return maxGkp;
    }

    public void setMaxGkp(String maxGkp) {
        this.maxGkp = maxGkp;
    }

    public String getMaxDef() {
        return maxDef;
    }

    public void setMaxDef(String maxDef) {
        this.maxDef = maxDef;
    }

    public String getMaxMid() {
        return maxMid;
    }

    public void setMaxMid(String maxMid) {
        this.maxMid = maxMid;
    }

    public String getMaxFwd() {
        return maxFwd;
    }

    public void setMaxFwd(String maxFwd) {
        this.maxFwd = maxFwd;
    }

    public String getAllowedPlayersInTeam() {
        return allowedPlayersInTeam;
    }

    public void setAllowedPlayersInTeam(String allowedPlayersInTeam) {
        this.allowedPlayersInTeam = allowedPlayersInTeam;
    }

    public String getWinningAmtByRank() {
        return winningAmtByRank;
    }

    public void setWinningAmtByRank(String winningAmtByRank) {
        this.winningAmtByRank = winningAmtByRank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
