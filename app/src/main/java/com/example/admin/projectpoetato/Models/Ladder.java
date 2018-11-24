package com.example.admin.projectpoetato.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Ladder implements Parcelable {
    // Variables
    @SerializedName("entries")
    private ArrayList entries;

    private String dead;
    private String online;
    private String rank;
    private String characterName;
    private String accountName;
    private String level;
    private String classId;
    private String experience;

    // Number of Variables used in the Model
    private static final int numberOfArgs = 7;

    // Constructor
    public Ladder(String dead,String online, String rank, String characterName, String accountName, String level, String classId, String experience) {
        this.dead = dead;
        this.online = online;
        this.rank = rank;
        this.characterName = characterName;
        this.accountName = accountName;
        this.level = level;
        this.classId = classId;
        this.experience = experience;
    }

    // Getters & Setters
    public ArrayList getEntries() {
        return entries;
    }

    public void setEntries(ArrayList entries) {
        this.entries = entries;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    /**********************************************************************************************
     *                                  Parcelable Functions                                      *
     *********************************************************************************************/
    public Ladder(Parcel parcel){
        String[] data = new String[numberOfArgs];
        parcel.readStringArray(data);
        // Remember the data order must correspond to writeToParcel!
        this.online = data[0];
        this.rank = data[1];
        this.characterName = data[2];
        this.accountName = data[3];
        this.level = data[4];
        this.classId = data[5];
        this.experience = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.online,
                this.rank,
                this.characterName,
                this.accountName,
                this.level,
                this.classId,
                this.experience
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
