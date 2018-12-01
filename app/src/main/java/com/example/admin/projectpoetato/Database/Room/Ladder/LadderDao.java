package com.example.admin.projectpoetato.Database.Room.Ladder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.admin.projectpoetato.Models.Ladder;

import java.util.List;

@Dao
public interface LadderDao {
    @Insert
    void insert(Ladder ladder);

    @Update
    void update(Ladder ladder);

    @Delete
    void delete(Ladder ladder);

    @Query("DELETE FROM ladder_table")
    void deleteAllLadders();

    @Query("SELECT * FROM ladder_table")
    LiveData<List<Ladder>> getAllLadders();

    // Get by league, account & character.
    // Get by uid
    // Get All
}
