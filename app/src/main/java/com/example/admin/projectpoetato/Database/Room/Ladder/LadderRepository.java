package com.example.admin.projectpoetato.Database.Room.Ladder;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.projectpoetato.Models.Ladder;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LadderRepository {
    public static final String TAG = LadderRepository.class.getSimpleName();
    private LadderDao mLadderDao;
    private LiveData<List<Ladder>> mLadderList;

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    public LadderRepository(Application application){
        LadderDatabase db = LadderDatabase.getInstance(application);
        mLadderDao = db.mLadderDao();
        mLadderList = mLadderDao.getAllLadders();
    }

    public void insert(Ladder ladder){
        new InsertLadderAsyncTask(mLadderDao).execute(ladder);
    }

    public void update(Ladder ladder){
        new UpdateLadderAsyncTask(mLadderDao).execute(ladder);
    }

    public void delete(Ladder ladder){
        new DeleteLadderAsyncTask(mLadderDao).execute(ladder);
    }

    public void deleteAllLadders(){
        new DeleteAllLadderAsyncTask(mLadderDao).execute();
    }

    public LiveData<List<Ladder>> getAllLadders(){
        return mLadderList;
    }

    public Ladder getLadderTrackerStatus(String charName){
        try {
            Log.d(TAG, "Sending charName = " + charName);
            return ( new GetLadderByUid(mLadderDao, charName).execute().get() );
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**********************************************************************************************
     *                                   AsyncTask Classes                                        *
     *********************************************************************************************/
    // Insert
    private static class InsertLadderAsyncTask extends AsyncTask<Ladder, Void, Void>{
        private LadderDao ladderDao;

        private InsertLadderAsyncTask(LadderDao ladderDao){
            this.ladderDao = ladderDao;
        }

        @Override
        protected Void doInBackground(Ladder... ladders) {
            ladderDao.insert(ladders[0]);
            return null;
        }
    }

    // Update
    private static class UpdateLadderAsyncTask extends AsyncTask<Ladder, Void, Void>{
        private LadderDao ladderDao;

        private UpdateLadderAsyncTask(LadderDao ladderDao){
            this.ladderDao = ladderDao;
        }

        @Override
        protected Void doInBackground(Ladder... ladders) {
            ladderDao.update(ladders[0]);
            return null;
        }
    }

    // Delete
    private static class DeleteLadderAsyncTask extends AsyncTask<Ladder, Void, Void>{
        private LadderDao ladderDao;

        private DeleteLadderAsyncTask(LadderDao ladderDao){
            this.ladderDao = ladderDao;
        }

        @Override
        protected Void doInBackground(Ladder... ladders) {
            ladderDao.delete(ladders[0]);
            return null;
        }
    }

    // Delete All
    private static class DeleteAllLadderAsyncTask extends AsyncTask<Void, Void, Void>{
        private LadderDao ladderDao;

        private DeleteAllLadderAsyncTask(LadderDao ladderDao){
            this.ladderDao = ladderDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ladderDao.deleteAllLadders();
            return null;
        }
    }

    // Get by uid
    private static class GetLadderByUid extends AsyncTask<Void, Void, Ladder>{
        private LadderDao ladderDao;
        private String characterName;

        private GetLadderByUid(LadderDao ladderDao, String characterName){
            this.ladderDao = ladderDao;
            this.characterName = characterName;
        }

        @Override
        protected Ladder doInBackground(Void... voids) {
            return ladderDao.getLadderTrackerStatus(characterName);
        }
    }
}
