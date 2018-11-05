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
            JSONObject jsonObject = response.getJSONObject(0);
            String id = jsonObject.getString("id");
            String url = jsonObject.getString("url");
            String startAt = jsonObject.getString("startAt");
            String endAt = jsonObject.getString("endAt");

            if(compact == 0){
                String description = jsonObject.getString("description");
                String registerAt = jsonObject.getString("registerAt");
                String event = jsonObject.getString("event");
                JSONArray rulesArray = jsonObject.getJSONArray("rules");
                if(rulesArray.length() > 0){
                    JSONObject rulesObject = rulesArray.getJSONObject(0);
                    String idRules = rulesObject.getString("id");
                    String nameRules = rulesObject.getString("name");
                    String descriptionRules = rulesObject.getString("description");
                    Log.d(TAG, "id = " + id + "\nurl = " + url + ", startAt = " + startAt + ", endAt = " + endAt + ", description = " + description +
                            ", registerAt = " + registerAt + ", event = " + event +
                            ", idrules = " + idRules + ", nameRules = " + nameRules + ", descriptionRules = " + descriptionRules);
                } else {
                    // TODO: Add Compact = 0 stuff here
                    String rules = jsonObject.getString("rules");
                    Log.d(TAG, "id = " + id + "\nurl = " + url + ", startAt = " + startAt + ", endAt = " + endAt + ", description = " + description +
                            ", registerAt = " + registerAt + ", event = " + event + ", rules = " + rules);
                }
            }else{
                // TODO: Add Compact = 1 stuff here
                Log.d(TAG, "id = " + id + ", url = " + url + ", startAt = " + startAt + ", endAt = " + endAt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ResponseErrorListener(VolleyError error){
        error.printStackTrace();
    }

}
