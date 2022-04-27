
package com.fan.core.model.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FixturesData {

    private String fixtureId;
    private String eventTimestamp;
    private String eventDate;
    private String leagueId;
    private String round;
    private String homeTeamId;
    private String awayTeamId;
    private String homeTeam;
    private String awayTeam;
    private String status;
    private String statusShort;
    private Object goalsHomeTeam;
    private Object goalsAwayTeam;
    private Object halftimeScore;
    private Object finalScore;
    private String penalty;
    private String elapsed;
    private String firstHalfStart;
    private String secondHalfStart;

    public String getFixtureId()
    {
        return fixtureId;
    }

    public void setFixtureId(String fixtureId) {
        this.fixtureId = fixtureId;
    }

    public String getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(String eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusShort() {
        return statusShort;
    }

    public void setStatusShort(String statusShort) {
        this.statusShort = statusShort;
    }

    public Object getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(Object goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public Object getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(Object goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

    public Object getHalftimeScore() {
        return halftimeScore;
    }

    public void setHalftimeScore(Object halftimeScore) {
        this.halftimeScore = halftimeScore;
    }

    public Object getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Object finalScore) {
        this.finalScore = finalScore;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getFirstHalfStart() {
        return firstHalfStart;
    }

    public void setFirstHalfStart(String firstHalfStart) {
        this.firstHalfStart = firstHalfStart;
    }

    public String getSecondHalfStart() {
        return secondHalfStart;
    }

    public void setSecondHalfStart(String secondHalfStart) {
        this.secondHalfStart = secondHalfStart;
    }

}
