package com.example.admin.projectpoetato.Utilities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

/**
 * Followed guide: https://codinginflow.com/tutorials/android/foreground-service
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    public static final String CHANNEL_ID = "LADDER_SERVICE_CHANNEL_ID";
    public static final String CHANNEL_NAME = "Ladder Service Channel";

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() about to create notification channel");
        createNotificationChannel();
    }

}
