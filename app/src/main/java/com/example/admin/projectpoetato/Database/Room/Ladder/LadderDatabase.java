package com.example.admin.projectpoetato.Database.Room.Ladder;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.projectpoetato.Models.Ladder;

@Database(entities = {Ladder.class}, version = 1, exportSchema = false)
public abstract class LadderDatabase extends RoomDatabase {
    public static final String TAG = LadderDatabase.class.getSimpleName();

    private static LadderDatabase instance;
    public abstract LadderDao mLadderDao();

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    // Get Instance
    public static synchronized LadderDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LadderDatabase.class, "ladder_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // Room Callback
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DatabaseOnCreateAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DatabaseOnOpenAsyncTask(instance).execute();
        }
    };

    // Database onCreate AsyncTask
    private static class DatabaseOnCreateAsyncTask extends AsyncTask<Void, Void, Void>{

        private DatabaseOnCreateAsyncTask(LadderDatabase db){
            Log.d(TAG, "onCreate()");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
    // TODO: Database onOpen AsyncTask
    private static class DatabaseOnOpenAsyncTask extends AsyncTask<Void, Void, Void>{

        private DatabaseOnOpenAsyncTask(LadderDatabase db){
            Log.d(TAG, "onOpen()");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
