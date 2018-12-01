package com.example.admin.projectpoetato.Database.Room.Ladder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.admin.projectpoetato.Models.Ladder;

import java.util.List;

public class LadderViewModel extends AndroidViewModel {
    public static final String TAG = LadderViewModel.class.getSimpleName();

    private LadderRepository mRepository;
    private LiveData<List<Ladder>> mLadderList;

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    // Constructor
    public LadderViewModel(@NonNull Application application) {
        super(application);
        mRepository = new LadderRepository(application);
        mLadderList = mRepository.getAllLadders();
    }

    // Insert
    public void insert(Ladder ladder){
        mRepository.insert(ladder);
    }

    // Update
    public void update(Ladder ladder){
        mRepository.update(ladder);
    }

    // Delete
    public void delete(Ladder ladder){
        mRepository.delete(ladder);
    }

    // Delete All
    public void deleteAllLadders(){
        mRepository.deleteAllLadders();
    }

    // Get All
    public LiveData<List<Ladder>> getAllLadders() {
        return mLadderList;
    }
}
