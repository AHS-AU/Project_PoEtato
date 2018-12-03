package com.example.admin.projectpoetato.Services;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.os.Process;

import com.example.admin.projectpoetato.R;

import static com.example.admin.projectpoetato.Utilities.App.CHANNEL_ID;

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
    public static final long mServiceInterval = 5*1000;
    private boolean isRunning = false;


    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }

        private void doBackgroundWork(){
            Thread backgroundThread = new Thread( () -> {
                // TODO: Background Work here for LadderTracker
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

                    Notification notification = new NotificationCompat.Builder(LadderService.this, CHANNEL_ID)
                            .setContentTitle("Ladder Tracker")
                            .setContentText("Ladder updated mm:ss ago")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .addLine("XD1")
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
