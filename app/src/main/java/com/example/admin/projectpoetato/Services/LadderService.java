package com.example.admin.projectpoetato.Services;

import android.app.Notification;
import android.app.Service;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.os.Process;

import com.example.admin.projectpoetato.API.Resources.LadderApi;
import com.example.admin.projectpoetato.Activities.MainActivity.MainActivity;
import com.example.admin.projectpoetato.Database.Room.Ladder.LadderRepository;
import com.example.admin.projectpoetato.Database.Room.Ladder.LadderViewModel;
import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.R;
import com.example.admin.projectpoetato.Utilities.App;
import com.google.gson.Gson;

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
 * Bound Service to handle Ladder Tracking of Characters from the LadderDatabase
 * {@link Service) - official Service doc
 * (@link https://developer.android.com/guide/components/bound-services) - bound services doc
 */
public class LadderService extends Service {
    public static final String TAG = LadderService.class.getSimpleName();

    // Variables
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    public static final long mServiceInterval = 300*1000;
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
                                      String track, String accountName, String difficulty, String start){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_API_PATHOFEXILE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LadderApi mLadder = retrofit.create(LadderApi.class);
            Call<Ladder> mCallLadder = mLadder.getLadders(leagueId,String.valueOf(limit),String.valueOf(offset),type,track,accountName,difficulty,start);
            mCallLadder.enqueue(new retrofit2.Callback<Ladder>() {
                @Override
                public void onResponse(Call<Ladder> call, Response<Ladder> response) {
                    LadderOnResponse(call,response);
                }

                @Override
                public void onFailure(Call<Ladder> call, Throwable t) {

                }
            });
        }

        public void LadderOnResponse(Call<Ladder> call, Response<Ladder> response){
            if(!response.isSuccessful()){
                Log.d(TAG, "LadderOnResponse() onResponse Code: " + response.code());
                if(response.code() == 429){
                    call.clone();
                }
                return;
            }
            mLadderList.clear();

            JSONArray mLadderEntries = new JSONArray(response.body().getEntries());
            for(int i = 0; i < mLadderEntries.length(); i++){
                try {
                    JSONObject mCharacterEntry = mLadderEntries.getJSONObject(i);
                    JSONObject mCharacterObject = mCharacterEntry.getJSONObject("character");
                    if(mCharacterObject.getString("id").equals("0dc2d7a3dd57cf98ca786cc60043b0d76f433463b490e72e392e629bcde1f577")){
                        Ladder mLadder = new Ladder(
                                mCharacterEntry.getInt("rank"),
                                mCharacterEntry.getBoolean("dead"),
                                false,
                                mCharacterEntry.getBoolean("online"),
                                mCharacterObject.getString("name"),
                                mCharacterObject.getInt("level"),
                                mCharacterObject.getString("class"),
                                mCharacterObject.getString("id"),
                                mCharacterObject.getLong("experience"),
                                0,
                                0,
                                null,
                                0,
                                null,
                                false,
                                null);
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
                        // Start fragment
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
                // TODO: Background Work here for LadderTracker
                LadderRepository mLadderRepository = new LadderRepository(new App());
                List<Ladder> ladders = mLadderRepository.getAllCharacters();
                if(ladders != null){
                    for(int i = 0; i < ladders.size(); i++){
                        Ladder ladder = ladders.get(i);
                        if(ladder.getCharacterName().equals("Disp_DelveIntoFatAss")){
                            SendLadderRequest("SSF Delve HC",20,0,"league",null,ladder.getAccountName(),null,null);
                        }
                    }
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
                    Log.d(TAG, "handleMessage: Creating Notification and doing Backgroundwork");
                    String msgString = null;
                    if(mLadderList.size() > 0){
                        Ladder ladder = mLadderList.get(0);
                        msgString = "Rank: " + ladder.getRank() + ", Name: " + ladder.getCharacterName();

                    }
                    Notification notification = new NotificationCompat.Builder(LadderService.this, CHANNEL_ID)
                            .setContentTitle("Ladder Tracker")
                            .setContentText("Ladder updated mm:ss ago")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .addLine(msgString)
                                    .addLine("XD2")
                                    .addLine("XD3")
                                    .addLine("XD4")
                                    .addLine("XD5")
                                    .addLine("XD6")
                                    .addLine("XD7"))
                            .build();
                    startForeground(1, notification);
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
