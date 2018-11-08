package com.example.admin.projectpoetato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.admin.projectpoetato.API.Resources.League.LeagueApi;
import com.example.admin.projectpoetato.Models.League;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.pathofexile.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LeagueApi mLeague = retrofit.create(LeagueApi.class);

        Call<List<League>> call = mLeague.getLeagues("main",null,0,0,0);

        // retrofit enqueue creates the background handling!! no need for async tasks :)
        call.enqueue(new Callback<List<League>>() {
            @Override
            public void onResponse(Call<List<League>> call, Response<List<League>> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse Code: " + response.code());
                    return;
                }

                List<League> leagues = response.body();

                for(League league : leagues){
                    String content = "";
                    content += "ID: " + league.getId() + "\t";
                    content += "Desc: " + league.getDescription() + "\t";
                    content += "regAt: " + league.getRegisterAt() + "\t";
                    content += "URL: " + league.getUrl() + "\t";
                    content += "startAt: " + league.getStartAt() + "\t";
                    content += "endAt: " + league.getEndAt() + "\t";
                    content += "leagueEvent: " + league.getLeagueEvent() + "\t";

                    // This takes 5 ms extra... consider if it's worth it.
//                    for(int i = 0; i < league.getRules().size(); i++){
//                        content += "rule #" + (i+1) + " Id: " + league.getRules().get(i).getAsJsonObject().get("name") + "\t";
//                        content += "rule #" + (i+1) + " Desc: " + league.getRules().get(i).getAsJsonObject().get("description") + "\t";
//                    }
                    content += "\n";

                    Log.d(TAG, "Content = " + content);
                }
            }

            @Override
            public void onFailure(Call<List<League>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

//        LeagueUrl mLeague = new LeagueUrl("main", null,false,0,0);
//        String mLeagueUrl = mLeague.getRequestUrl();
//        Log.d(TAG, "league request url = " + mLeagueUrl);
//        if(mQueue == null){
//            Log.d(TAG, "mQueue is null, requesting new");
//            mQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.GET, mLeagueUrl, null,
//                (JSONArray response) -> ResponseListener2(response,mLeague.getCompact()),
//                this::ResponseErrorListener);
//        mQueue.add(mRequest);


    }

    public void ResponseListener2(JSONArray response, int compact){
        String str = response.toString();
        Log.d(TAG, "Response str is \n" + str);
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
