package com.example.admin.projectpoetato.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Ladder implements Parcelable {
    /**********************************************************************************************
     *                                       Variables                                            *
     *********************************************************************************************/
    // TODO: Object: Total
    // Object: Entries
    @SerializedName("entries")
    private ArrayList entries;

    private String rank;
    private String dead;
    private String retired;
    private String online;
    // Sub-Object: Character
    private String characterName;
    private String level;
    private String classId;
    private String characterId;
    private String experience;
    private String delveParty;
    private String delveSolo;
    // Sub-Object: Account
    private String accountName;
    private String challenges;
    private String twitch;

    // Number of Variables used in the Model
    private static final int numberOfArgs = 14;

    /**********************************************************************************************
     *                                       Class Methods                                        *
     *********************************************************************************************/
    public Ladder(){

    }

    public Ladder(String rank, String dead, String retired, String online,
                  String characterName, String level, String classId, String characterId,
                  String experience, String delveParty, String delveSolo,
                  String accountName, String challenges, String twitch) {
        this.rank = rank;
        this.dead = dead;
        this.retired = retired;
        this.online = online;
        this.characterName = characterName;
        this.level = level;
        this.classId = classId;
        this.characterId = characterId;
        this.experience = experience;
        this.delveParty = delveParty;
        this.delveSolo = delveSolo;
        this.accountName = accountName;
        this.challenges = challenges;
        this.twitch = twitch;
    }

    public String PrintLadderInfo(){
        String LadderInfo = "";

        LadderInfo += "\tRank = " + getRank();
        LadderInfo += "\tDead = " + getDead();
        LadderInfo += "\tRetired = " + getRetired();
        LadderInfo += "\tOnline = " + getOnline();

        LadderInfo += "\nCharacterName = " + getCharacterName();

        return LadderInfo;
    }

    /**********************************************************************************************
     *                                    Getters & Setters                                       *
     *********************************************************************************************/
    public ArrayList getEntries() {
        return entries;
    }

    public void setEntries(ArrayList entries) {
        this.entries = entries;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getRetired() {
        return retired;
    }

    public void setRetired(String retired) {
        this.retired = retired;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDelveParty() {
        return delveParty;
    }

    public void setDelveParty(String delveParty) {
        this.delveParty = delveParty;
    }

    public String getDelveSolo() {
        return delveSolo;
    }

    public void setDelveSolo(String delveSolo) {
        this.delveSolo = delveSolo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getChallenges() {
        return challenges;
    }

    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    public String getTwitch() {
        return twitch;
    }

    public void setTwitch(String twitch) {
        this.twitch = twitch;
    }

    /**********************************************************************************************
     *                                  Parcelable Functions                                      *
     *********************************************************************************************/
    public Ladder(Parcel parcel){
        String[] data = new String[numberOfArgs];
        parcel.readStringArray(data);
        // Remember the data order must correspond to writeToParcel!
        this.rank = data[0];
        this.dead = data[1];
        this.retired = data[2];
        this.online = data[3];

        this.characterName = data[4];
        this.level = data[5];
        this.classId = data[6];
        this.characterId = data[7];
        this.experience = data[8];
        this.delveParty = data[9];
        this.delveSolo = data[10];

        this.accountName = data[11];
        this.challenges = data[12];
        this.twitch = data[13];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.rank,
                this.dead,
                this.retired,
                this.online,

                this.characterName,
                this.level,
                this.classId,
                this.characterId,
                this.experience,
                this.delveParty,
                this.delveSolo,

                this.accountName,
                this.challenges,
                this.twitch
        });
    }

    // Parcelable.Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Ladder createFromParcel(Parcel parcel){
            return new Ladder(parcel);
        }
        public Ladder[] newArray(int size){
            return new Ladder[size];
        }
    };
}
