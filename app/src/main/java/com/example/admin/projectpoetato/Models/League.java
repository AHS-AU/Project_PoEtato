package com.example.admin.projectpoetato.Models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class League implements Serializable {
    private String id;
    private String description;
    private String registerAt;
    private String event;
    private String url;
    private String startAt;
    private String endAt;
    @SerializedName("delveEvent")
    private String leagueEvent;  // TODO IMPLEMENT!!!!!
    private JsonArray rules;

    public League(String id, String description, String registerAt, String event, String url, String startAt, String endAt, String leagueEvent, JsonArray rules) {
        this.id = id;
        this.description = description;
        this.registerAt = registerAt;
        this.event = event;
        this.url = url;
        this.startAt = startAt;
        this.endAt = endAt;
        this.leagueEvent = leagueEvent;
        this.rules = rules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(String registerAt) {
        this.registerAt = registerAt;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getLeagueEvent() {
        return leagueEvent;
    }

    public void setLeagueEvent(String leagueEvent) {
        this.leagueEvent = leagueEvent;
    }

    public JsonArray getRules() {
        return rules;
    }
}
