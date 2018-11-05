package com.example.admin.projectpoetato.Models;

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
    private String delveEvent;  // TODO IMPLEMENT!!!!!
    private List<String> rulesId;
    private List<String> rulesName;
    private List<String> rulesDescription;

    public League(String id, String description, String registerAt, String event, String url, String startAt, String endAt, String delveEvent, List<String> rulesId, List<String> rulesName, List<String> rulesDescription) {
        this.id = id;
        this.description = description;
        this.registerAt = registerAt;
        this.event = event;
        this.url = url;
        this.startAt = startAt;
        this.endAt = endAt;
        this.delveEvent = delveEvent;
        this.rulesId = rulesId;
        this.rulesName = rulesName;
        this.rulesDescription = rulesDescription;
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

    public String getDelveEvent() {
        return delveEvent;
    }

    public void setDelveEvent(String delveEvent) {
        this.delveEvent = delveEvent;
    }

    public List<String> getRulesId() {
        return rulesId;
    }

    public void setRulesId(List<String> rulesId) {
        this.rulesId = rulesId;
    }

    public List<String> getRulesName() {
        return rulesName;
    }

    public void setRulesName(List<String> rulesName) {
        this.rulesName = rulesName;
    }

    public List<String> getRulesDescription() {
        return rulesDescription;
    }

    public void setRulesDescription(List<String> rulesDescription) {
        this.rulesDescription = rulesDescription;
    }
}
