package com.example.admin.projectpoetato.Models;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "ladder_table", primaryKeys = {"characterName", "accountName", "leagueId"})
public class Ladder implements Parcelable {
    /**********************************************************************************************
     *                                       Variables                                            *
     *********************************************************************************************/
    // Object: Entries
    @SerializedName("entries") @Ignore
    private ArrayList entries;

    private int rank;
    private boolean dead;
    private boolean retired;
    private boolean online;
    // Sub-Object: Character
    @NonNull
    private String characterName;
    private int level;
    private String classId;
    private String characterId;
    private long experience;
    private int delveParty;
    private int delveSolo;
    // Sub-Object: Account
    @NonNull
    private String accountName;
    private int challenges;
    private String twitch;
    // User-Defined
    @NonNull
    private String leagueId;

    // Number of Variables used in the Model
    // Don't include: uid
    private static final int numberOfArgs = 15;

    /**********************************************************************************************
     *                                       Class Methods                                        *
     *********************************************************************************************/
    public Ladder(){

    }

    public Ladder(int rank, boolean dead, boolean retired, boolean online,
                  String characterName, int level, String classId, String characterId,
                  long experience, int delveParty, int delveSolo,
                  String accountName, int challenges, String twitch,
                  String leagueId) {
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
        this.leagueId = leagueId;
    }

    public String PrintLadderInfo(){
        String LadderInfo = "";

        LadderInfo += "\tRank = " + getRank();
        LadderInfo += "\tDead = " + isDead();
        LadderInfo += "\tRetired = " + isRetired();
        LadderInfo += "\tOnline = " + isOnline();

        LadderInfo += "\nCharacterName = " + getCharacterName();
        LadderInfo += "\tLevel = " + getLevel();
        LadderInfo += "\tClass = " + getClassId();
        //LadderInfo += "\tCharId = " + getCharacterId();
        LadderInfo += "\tExp = " + getExperience();
        LadderInfo += "\tDelveParty = " + getDelveParty();
        LadderInfo += "\tDelveSolo = " + getDelveSolo();

        LadderInfo += "\nAccountName = " + getAccountName();
        LadderInfo += "\tChallenges = " + getChallenges();
        LadderInfo += "\tTwitch = " + getTwitch();

        LadderInfo += "\nLeague = " + getLeagueId();

        LadderInfo += "\n";

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public int getDelveParty() {
        return delveParty;
    }

    public void setDelveParty(int delveParty) {
        this.delveParty = delveParty;
    }

    public int getDelveSolo() {
        return delveSolo;
    }

    public void setDelveSolo(int delveSolo) {
        this.delveSolo = delveSolo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getChallenges() {
        return challenges;
    }

    public void setChallenges(int challenges) {
        this.challenges = challenges;
    }

    public String getTwitch() {
        return twitch;
    }

    public void setTwitch(String twitch) {
        this.twitch = twitch;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    /**********************************************************************************************
     *                                  Parcelable Functions                                      *
     *********************************************************************************************/
    public Ladder(Parcel parcel){
        String[] data = new String[numberOfArgs];
        parcel.readStringArray(data);
        // Remember the data order must correspond to writeToParcel!
        this.rank = Integer.parseInt(data[0]);
        this.dead = Boolean.parseBoolean(data[1]);
        this.retired = Boolean.parseBoolean(data[2]);
        this.online = Boolean.parseBoolean(data[3]);
        // Character
        this.characterName = data[4];
        this.level = Integer.parseInt(data[5]);
        this.classId = data[6];
        this.characterId = data[7];
        this.experience = Long.parseLong(data[8]);
        this.delveParty = Integer.parseInt(data[9]);
        this.delveSolo = Integer.parseInt(data[10]);
        // Account
        this.accountName = data[11];
        this.challenges = Integer.parseInt(data[12]);
        this.twitch = data[13];
        // User Defined
        this.leagueId = data[14];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.rank),
                String.valueOf(this.dead),
                String.valueOf(this.retired),
                String.valueOf(this.online),

                this.characterName,
                String.valueOf(this.level),
                this.classId,
                this.characterId,
                String.valueOf(this.experience),
                String.valueOf(this.delveParty),
                String.valueOf(this.delveSolo),

                this.accountName,
                String.valueOf(this.challenges),
                this.twitch,

                this.leagueId
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
