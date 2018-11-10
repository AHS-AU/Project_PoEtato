package com.example.admin.projectpoetato.Activities.LeagueActivity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.projectpoetato.API.Resources.League.LeagueApi;
import com.example.admin.projectpoetato.Activities.MainActivity.MainActivity;
import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.projectpoetato.Utilities.GlobalVariables.URL_API_PATHOFEXILE;

public class LeagueActivity extends AppCompatActivity {
    // Class Variables
    public static final String TAG = LeagueActivity.class.getSimpleName();

    // UI Variables
    private RecyclerView mRvLeagues;
    private LeagueAdapter mLeagueAdapter;
    private RecyclerView.LayoutManager mLeagueLayoutManager;


    // Variables
    List<League> mLeagueList = new ArrayList<>();


    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    public void SendLeagueRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API_PATHOFEXILE)
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


        // Add the Retrieved List of Leagues to the Adapter & Set the Adapter
        mLeagueList = leagues;
        UpdateLeagueList(leagues);

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

    public void OnAdapterItemClick(int position){
        Log.d(TAG, "position = " + position);
    }

    public void CreateRecyclerView(){
        mRvLeagues = findViewById(R.id.listLeagueLeague);
        mRvLeagues.setHasFixedSize(true);
        mLeagueLayoutManager = new LinearLayoutManager(this);
        mLeagueAdapter = new LeagueAdapter(mLeagueList);

        mRvLeagues.setLayoutManager(mLeagueLayoutManager);
        mRvLeagues.setAdapter(mLeagueAdapter);



//        mLeagueAdapter.setOnItemClickListener(new LeagueAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Log.d(TAG, "position = " + position);
//            }
//        });
    }

    public void UpdateLeagueList(List<League> leagues){
        mLeagueAdapter = new LeagueAdapter(leagues);
        mRvLeagues.setAdapter(mLeagueAdapter);
        mLeagueAdapter.setOnItemClickListener(this::OnAdapterItemClick);
    }



    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        // Slidr Configuration: https://github.com/r0adkll/Slidr
        Slidr.attach(this);
        // TODO: Global Lock/Unlock Slidr from Menu Navigation

        // UI Find View

        // Init Recyclerview
        CreateRecyclerView();

        // Action Bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Send Request
        SendLeagueRequest();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}
