package com.example.admin.projectpoetato;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.projectpoetato.API.Resources.League.APILeague;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // Class Variables
    public static final String TAG = MainActivity.class.getSimpleName();

    // UI Variables

    // Variables
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APILeague mLeague = new APILeague("main", null,false,0,0);
        String mLeagueUrl = mLeague.getRequestUrl();
        Log.d(TAG, "league request url = " + mLeagueUrl);
        if(mQueue == null){
            Log.d(TAG, "mQueue is null, requesting new");
            mQueue = Volley.newRequestQueue(getApplicationContext());
        }
        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.GET, mLeagueUrl, null,
                (JSONArray response) -> ResponseListener(response,mLeague.getCompact()),
                this::ResponseErrorListener);
        mQueue.add(mRequest);


    }

    public void ResponseListener(JSONArray response, int compact){
        try {
            for(int i = 0; i < response.length(); i++){

                String print = "";
                String printRules = "";
                JSONObject jsonObject = response.getJSONObject(i);
                String id = jsonObject.getString("id");
                String url = jsonObject.getString("url");
                String startAt = jsonObject.getString("startAt");
                String endAt = jsonObject.getString("endAt");

                if(compact == 0){
                    // TODO: If Compact = 0 stuff here
                    String description = jsonObject.getString("description");
                    String registerAt = jsonObject.getString("registerAt");
                    String event = jsonObject.getString("event");
                    JSONArray rulesArray = jsonObject.getJSONArray("rules");

                    print += "id = " + id + "url = " + url + ", startAt = " + startAt + ", endAt = " + endAt + ", description = " + description +
                            ", registerAt = " + registerAt + ", event = " + event;

                    if(rulesArray.length() > 0){
                        // TODO: If RulesArray has value & Compact = 0
                        for(int i2 = 0; i2 < rulesArray.length(); i2++){
                            JSONObject rulesObject = rulesArray.getJSONObject(i2);
                            String idRules = rulesObject.getString("id");
                            String nameRules = rulesObject.getString("name");
                            String descriptionRules = rulesObject.getString("description");
                            printRules += "******["+i2+"]idrules = " + idRules + ", nameRules = " + nameRules + ", descriptionRules = " + descriptionRules;
                        }
                        print += printRules;
                    } else {
                        // TODO: If RulesArray does not have value & Compact = 0
                        String rules = jsonObject.getString("rules");
                        printRules += rules;
                        print += printRules;
                    }
                }else{
                    // TODO: Add Compact = 1 stuff here
                    print = "id = " + id + ", url = " + url + ", startAt = " + startAt + ", endAt = " + endAt;
                }
                Log.d(TAG, print);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ResponseErrorListener(VolleyError error){
        error.printStackTrace();
    }

}
