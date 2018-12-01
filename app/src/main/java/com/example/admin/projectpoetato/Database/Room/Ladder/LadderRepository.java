package com.example.admin.projectpoetato.Database.Room.Ladder;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.admin.projectpoetato.Models.Ladder;

import java.util.List;

public class LadderRepository {
    public static final String TAG = LadderRepository.class.getSimpleName();
    private LadderDao mLadderDao;
    private MutableLiveData<List<Ladder>> mLadderList;

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

    public MutableLiveData<List<Ladder>> getAllLadders(){
        return mLadderList;
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
}
