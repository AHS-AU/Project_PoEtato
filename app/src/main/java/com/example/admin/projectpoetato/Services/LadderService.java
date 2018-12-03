package com.example.admin.projectpoetato.Services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Process;

/**
 * Bound Service to handle Ladder Tracking of Characters from the LadderDatabase
 * {@link Service) - official Service doc
 * (@link https://developer.android.com/guide/components/bound-services) - bound services doc
 */
public class LadderService extends Service {
    public static final String TAG = LadderService.class.getSimpleName();

    // Variables
    private final IBinder mBinder = new LocalBinder();
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

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            while(isRunning){
                try {
                    // Do some work here
                    Log.d(TAG, "Trying Service Handle Message");
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


    public class LocalBinder extends Binder {
        public LadderService getService(){
            return LadderService.this;
        }
    }


    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        // Start the thread that runs the Service.
        HandlerThread thread = new HandlerThread(TAG + "HandlerThread",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get HandlerThread's Looper and use it for the Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        isRunning = true;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If killed, after returning from here, restart.
        return START_STICKY;
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.d(TAG, "startService()");
        isRunning = true;
        return super.startService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mBinder;
    }
}
