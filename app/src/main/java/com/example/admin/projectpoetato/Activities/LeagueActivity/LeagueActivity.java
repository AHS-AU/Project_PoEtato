package com.example.admin.projectpoetato.Activities.LeagueActivity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.admin.projectpoetato.API.Resources.League.LeagueApi;
import com.example.admin.projectpoetato.Fragments.LeagueInfo.LeagueInfoFragment;
import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.projectpoetato.Utilities.GlobalVariables.URL_API_PATHOFEXILE;

public class LeagueActivity extends AppCompatActivity implements LeagueInfoFragment.OnFragmentInteractionListener{
    // Class Variables
    public static final String TAG = LeagueActivity.class.getSimpleName();

    // UI Variables
    private RecyclerView mRvLeagues;
    private LeagueAdapter mLeagueAdapter;
    private RecyclerView.LayoutManager mLeagueLayoutManager;
    private FrameLayout mFragmentContainer;
    private SlidrInterface mSlidr;
    private Toolbar mToolbar;

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
        UpdateLeagueList(mLeagueList);

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
            for(int i = 0; i < league.getRules().size(); i++){
                content += "rule #" + (i+1) + " " + league.getRules().get(i) + "\t";
            }
            content += "\n";

            Log.d(TAG, "Content = " + content);
        }
    }

    /**
     * Open LeagueInfoFragment when certain league has been clicked
     * @param position : the Item's position, starting from 0
     */
    public void OnAdapterItemClick(int position){
        OpenLeagueInfoFragment(mLeagueList.get(position));
    }

    /**
     * Create the RecyclerView
     */
    public void CreateRecyclerView(){
        mRvLeagues = findViewById(R.id.listLeagueLeague);
        mRvLeagues.setHasFixedSize(true);
        mLeagueLayoutManager = new LinearLayoutManager(this);
        mLeagueAdapter = new LeagueAdapter(mLeagueList);
        mRvLeagues.setLayoutManager(mLeagueLayoutManager);
        mRvLeagues.setAdapter(mLeagueAdapter);
    }

    /**
     * Update League List
     * @param leagues : List of leagues
     */
    public void UpdateLeagueList(List<League> leagues){
        mLeagueAdapter = new LeagueAdapter(leagues);
        mRvLeagues.setAdapter(mLeagueAdapter);
        mLeagueAdapter.setOnItemClickListener(this::OnAdapterItemClick);
    }

    /**
     * Method to open LeagueInfoFragment
     * @param league : League to display in next fragment
     */
    // Source: codinginflow.com
    public void OpenLeagueInfoFragment(League league){
        LeagueInfoFragment mLeagueInfoFragment = LeagueInfoFragment.newInstance(league);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        // 4 Arguments, because we need for the back button as well!
        mFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.add(R.id.league_frame, mLeagueInfoFragment, LeagueInfoFragment.TAG).commit();

    }



    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        // Slidr: https://github.com/r0adkll/Slidr
        //mSlidr = Slidr.attach(this);

        // UI Find View
        mFragmentContainer = findViewById(R.id.league_frame);

        // Init Recyclerview
        CreateRecyclerView();

        // Action Bar
        mToolbar = findViewById(R.id.toolbar_activity_league);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());


        // Send Request
        SendLeagueRequest();
    }

    /**
     * Modified to work with Opened Fragments
     */
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction");
    }
}
