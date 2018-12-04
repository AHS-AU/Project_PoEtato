package com.example.admin.projectpoetato.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.os.Process;

import com.example.admin.projectpoetato.API.Resources.LadderApi;
import com.example.admin.projectpoetato.Database.Room.Ladder.LadderRepository;
import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.R;
import com.example.admin.projectpoetato.Utilities.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.projectpoetato.Utilities.App.CHANNEL_ID;
import static com.example.admin.projectpoetato.Utilities.GlobalVariables.URL_API_PATHOFEXILE;

/**
 * Service to handle Ladder Tracking of Characters from the LadderDatabase
 * {@link Service) - official Service doc
 */
public class LadderService extends Service {
    public static final String TAG = LadderService.class.getSimpleName();

    // Variables
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    public static final long mServiceInterval = 600*1000;
    private boolean isRunning = false;
    private List<Ladder> mLadderList = new ArrayList<>();


    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }

        public void SendLadderRequest(String leagueId, int limit, int offset, String type,
                                      String track, String accountName, String difficulty, String start,
                                      String uid){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_API_PATHOFEXILE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LadderApi mLadder = retrofit.create(LadderApi.class);
            Call<Ladder> mCallLadder = mLadder.getLadders(leagueId,String.valueOf(limit),String.valueOf(offset),type,track,accountName,difficulty,start);
            mCallLadder.enqueue(new retrofit2.Callback<Ladder>() {
                @Override
                public void onResponse(Call<Ladder> call, Response<Ladder> response) {
                    LadderOnResponse(call,response, leagueId,uid);
                }

                @Override
                public void onFailure(Call<Ladder> call, Throwable t) {

                }
            });
        }

        public void LadderOnResponse(Call<Ladder> call, Response<Ladder> response, String leagueId ,String uid){
            if(!response.isSuccessful()){
                Log.d(TAG, "LadderOnResponse() onResponse Code: " + response.code());
                if(response.code() == 429){
                    call.clone();
                }
                return;
            }

            JSONArray mLadderEntries = new JSONArray(response.body().getEntries());

            for(int i = 0; i < mLadderEntries.length(); i++){
                try {
                    JSONObject mCharacterEntry = mLadderEntries.getJSONObject(i);
                    JSONObject mCharacterObject = mCharacterEntry.getJSONObject("character");
                    // If the character found with accountName matches the uid, then proceed!
                    if(mCharacterObject.getString("id").equals(uid)){
                        // Ladder Object Handling
                        Ladder mLadder = new Ladder();
                        // Rank
                        mLadder.setRank(mCharacterEntry.getInt("rank"));
                        // Dead
                        mLadder.setDead(mCharacterEntry.getBoolean("dead"));
                        // Online
                        mLadder.setOnline(mCharacterEntry.getBoolean("online"));
                        // Character Name
                        mLadder.setCharacterName(mCharacterObject.getString("name"));
                        // Level
                        mLadder.setLevel(mCharacterObject.getInt("level"));
                        // Class (id)
                        mLadder.setClassId(mCharacterObject.getString("class"));
                        // Character/Track id: Erorr handling in case non-existent (if track != 1)
                        if(mCharacterObject.has("id")){
                            mLadder.setCharacterId(mCharacterObject.getString("id"));
                        }
                        // Experience
                        mLadder.setExperience(mCharacterObject.getLong("experience"));
                        // Error Handling for Retired in case non-existent
                        if(mCharacterEntry.has("retired")){
                            mLadder.setRetired(mCharacterEntry.getBoolean("retired"));
                        }
                        // Error Handling for Delve in case non-existent
                        if(mCharacterObject.has("depth")){
                            JSONObject mDelveObject = mCharacterObject.getJSONObject("depth");
                            mLadder.setDelveParty(mDelveObject.getInt("default"));
                            mLadder.setDelveSolo(mDelveObject.getInt("solo"));
                        }
                        // Set League Name
                        mLadder.setLeagueId(leagueId);
                        // Add to LadderList
                        mLadderList.add(mLadder);
                        Log.d(TAG, "LadderOnResponse: " + "CharEntry: " + mLadder.PrintLadderInfo());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "LadderOnResponse Done");

        }

        private void doBackgroundWork(){
            Thread backgroundThread = new Thread( () -> {
                LadderRepository mLadderRepository = new LadderRepository(new App());
                List<Ladder> ladders = mLadderRepository.getAllCharacters();
                if(ladders != null){
                    for(int i = 0; i < ladders.size(); i++){
                        Ladder ladder = ladders.get(i);
                        Log.d(TAG, "doBackgroundWork: league = " + ladder.getLeagueId() + " acc = " + ladder.getAccountName() + " uid = " + ladder.getCharacterId());
                        SendLadderRequest(ladder.getLeagueId(),20,0,"league",
                                null,ladder.getAccountName(),null,null,
                                ladder.getCharacterId());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                    for (Ladder ladder : mLadderList ) {
                        String msgString = "League: " + ladder.getLeagueId() + ", Rank: " + ladder.getRank() + ", Name: " + ladder.getCharacterName();
                        inboxStyle.addLine(msgString);
                    }
                    Notification notification = new NotificationCompat.Builder(LadderService.this, CHANNEL_ID)
                            .setContentTitle("Ladder Tracker")
                            .setContentText("Ladder updated mm:ss ago")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setStyle(inboxStyle)
                            .build();
                    NotificationManagerCompat notMan = NotificationManagerCompat.from(LadderService.this);
                    notMan.notify(2,notification);

                    mLadderList.clear();
                }

            });
            backgroundThread.start();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            while(isRunning){
                try {
                    // Do some work here
//                    Log.d(TAG, "handleMessage: Ladder Size = " + mLadderList.size() );
//                    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//                    for (Ladder ladder : mLadderList ) {
//                        Log.d(TAG, "handleMessage: Ladder = " + ladder.getCharacterName());
//                        String msgString = "League: " + ladder.getLeagueId() + ", Rank: " + ladder.getRank() + ", Name: " + ladder.getCharacterName();
//                        inboxStyle.addLine(msgString);
//                    }
//                    Notification notification = new NotificationCompat.Builder(LadderService.this, CHANNEL_ID)
//                            .setContentTitle("Ladder Tracker")
//                            .setContentText("Ladder updated mm:ss ago")
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setStyle(inboxStyle)
//                            .build();
//                    NotificationManagerCompat notMan = NotificationManagerCompat.from(LadderService.this);
//                    notMan.notify(2,notification);

                    doBackgroundWork();
                    Thread.sleep(mServiceInterval);
                } catch (InterruptedException e) {
                    // Restore the interrupt status
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            // Stops the service using start id
            stopSelf(msg.arg1);
        }
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        // Start the thread that runs the Service.
        HandlerThread thread = new HandlerThread(
                TAG + "HandlerThread",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get HandlerThread's Looper and use it for the Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        isRunning = true;
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If killed, after returning from here, restart.
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d(TAG, "onDestroy: ");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }
}
