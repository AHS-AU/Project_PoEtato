package com.example.admin.projectpoetato.Activities.LeagueActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.example.admin.projectpoetato.API.Resources.LeagueApi;
import com.example.admin.projectpoetato.Fragments.LeagueInfo.LeagueInfoFragment;
import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;

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
    private Toolbar mToolbar;

    // Variables
    List<League> mLeagueList = new ArrayList<>();


    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    /**
     * ConnectivityManager, checks if internet is avaialble
     * @return : boolean connect state
     */
    public boolean isConnected(){
        // Set up the ConnectivityManager to ensure Connection to the Internet.
        NetworkInfo mActiveNetworkInfo = null;
        ConnectivityManager mConnMan = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMan != null){
            mActiveNetworkInfo = mConnMan.getActiveNetworkInfo();
        }
        return (mActiveNetworkInfo != null && mActiveNetworkInfo.isConnectedOrConnecting());
    }


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
                Log.d(TAG, "SendLeagueRequest() onFailure: " + t.getMessage());
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
    }

    /**
     * Open LeagueInfoFragment when certain league has been clicked
     * @param position : the Item's position, starting from 0
     */
    public void OnAdapterItemClick(int position){
        if(isConnected()){
            OpenLeagueInfoFragment(mLeagueList.get(position));
        }else{
            Toast.makeText(this, getResources().getString(R.string.cm_noConnection), Toast.LENGTH_SHORT).show();
        }
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
        if(isConnected()){
            SendLeagueRequest();
        }else{
            Toast.makeText(this, getResources().getString(R.string.cm_noConnection), Toast.LENGTH_SHORT).show();
        }
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
