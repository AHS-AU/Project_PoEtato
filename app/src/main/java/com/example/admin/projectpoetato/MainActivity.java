package com.example.admin.projectpoetato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.admin.projectpoetato.API.Resources.League.LeagueApi;
import com.example.admin.projectpoetato.Models.League;

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
    Button mBtnLeague;

    // Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Find View
        mBtnLeague = findViewById(R.id.btnMainLeague);

        mBtnLeague.setOnClickListener(v -> StartLeagueActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.pathofexile.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LeagueApi mLeague = retrofit.create(LeagueApi.class);

        Call<List<League>> mCallLeague = mLeague.getLeagues("main",null,0,0,0);

        // retrofit enqueue creates the background handling!! no need for async tasks :)
        mCallLeague.enqueue(new Callback<List<League>>() {
            @Override
            public void onResponse(Call<List<League>> call, Response<List<League>> response) {
                LeagueOnResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<League>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public void StartLeagueActivity(){
        Intent intent = new Intent(this, LeagueActivity.class);
        startActivity(intent);
    }

    /**
     * Method that get called when there is response for League Call
     * @param call : call
     * @param response : league response
     */
    public void LeagueOnResponse(Call<List<League>> call, Response<List<League>> response){
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

}
