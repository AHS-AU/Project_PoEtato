package com.example.admin.projectpoetato.Utilities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Functions that are frequently used in separate places are placed here.
 */
public class GlobalFunctions {

    // Converts UTC String to Locale Time String. Don't get confused by Summer Time :)
    public String ConvertUtcToLocal(String timeInUTC){
        // PoE displays their time in UTC formatted with ISO 8601
        if (timeInUTC != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                Date date = sdf.parse(timeInUTC);
                sdf.setTimeZone(TimeZone.getDefault());

                String localTime = sdf.format(date);
                localTime = localTime.replace("T", " at ");

                return localTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
