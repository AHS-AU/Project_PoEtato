package com.example.admin.projectpoetato.Activities.LadderActivity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.projectpoetato.API.Resources.LadderApi;
import com.example.admin.projectpoetato.API.Resources.LeagueApi;
import com.example.admin.projectpoetato.Dialogs.LadderInfoDialog;
import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.projectpoetato.Utilities.GlobalVariables.ARG_CHARACTER_INFO;
import static com.example.admin.projectpoetato.Utilities.GlobalVariables.URL_API_PATHOFEXILE;

/**
 * For the Reviewer's information; this activity was Autogenerated from Android Studio
 */
public class LadderActivity extends AppCompatActivity {
    // Class Variables
    public static final String TAG = LadderActivity.class.getSimpleName();

    // UI Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public Spinner mSpinnerLeagues;

    // Variables
    private int LADDER_PAGE_COUNT = 50; // TODO: Replace with real number
    private ArrayAdapter<String> mLeagueArrayAdapter;
    private List<String> mListOfLeagues = new ArrayList<>();

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    /**
     * Sets up the League Array to be used in the Spinner
     * @param listLeagues : List of Leagues retrieved from the API
     */
    private void SetUpLeagueArray(List<String> listLeagues){
        mLeagueArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLeagues){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0){
                    return false;
                } else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView mHintLabel = (TextView) view;
                if(position == 0){
                    mHintLabel.setTextColor(getResources().getColor(R.color.materialUnclickableObject));
                }
                return view;
            }
        };
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
            Log.d(TAG, "LeagueOnResponse() onResponse Code: " + response.code());
            return;
        }
        List<League> leagues = response.body();

        // Add the Retrieved List of Leagues to the Adapter & Set the SpinnerLeague
        for(League league : leagues){
            mListOfLeagues.add(league.getId());
        }
        SetUpLeagueArray(mListOfLeagues);
        mLeagueArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLeagues.setAdapter(mLeagueArrayAdapter);
        mSpinnerLeagues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mSelectedLeague = (String) parent.getItemAtPosition(position);
                // First item is disabled and used for hints
                // When the user changes the default hint, the spinner picks the League to retrieve.
                if(position > 0){
                    // Create the adapter that will return a fragment for each League
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),mSelectedLeague);
                    // Set up the ViewPager with the sections adapter.
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    //SendLadderRequest(mSelectedLeague);
                    Log.d(TAG, "League Selected: " + mSelectedLeague);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // ignore this
            }
        });
    }


    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder);
        // Add the Hint to Spinner League
        mListOfLeagues.add("Select a League ...");
        // UI Find View
        mViewPager = findViewById(R.id.container);
        mSpinnerLeagues = findViewById(R.id.spinner_leagues);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Send League Request & set up the Spinner League
        SendLeagueRequest();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ladder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Calls onBackPressed to return to previous Activity
     * @return :
     */
    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onBackPressed");
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**********************************************************************************************
     *                                 Ladder List Fragment                                       *
     *********************************************************************************************/
    public static class PlaceholderFragment extends Fragment {
        // Variables for Fragment
        private List<Ladder> mLadderList = new ArrayList<>();
        private RecyclerView.LayoutManager mLadderLayoutManager;
        private LadderAdapter mLadderAdapter;
        private RecyclerView mRvLadder;


        public void SendLadderRequest(String leagueId, int limit, int offset, String type,
                                      String track, String accountName, String difficulty, String start){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_API_PATHOFEXILE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LadderApi mLadder = retrofit.create(LadderApi.class);

            Call<Ladder> mCallLadder = mLadder.getLadders(leagueId,String.valueOf(limit),String.valueOf(offset),"league","false","","","");
            mCallLadder.enqueue(new Callback<Ladder>() {
                @Override
                public void onResponse(Call<Ladder> call, Response<Ladder> response) {
                    LadderOnResponse(call, response);
                }

                @Override
                public void onFailure(Call<Ladder> call, Throwable t) {

                }
            });

        }

        public void LadderOnResponse(Call<Ladder> call, Response<Ladder> response){
            if(!response.isSuccessful()){
                Log.d(TAG, "LadderOnResponse() onResponse Code: " + response.code());
                if(response.code() == 429){
                    call.clone();
                }
                return;
            }
            List<Ladder> mLadderList = new ArrayList<>();

            JSONArray mLadderEntries = new JSONArray(response.body().getEntries());
            for(int i = 0; i < mLadderEntries.length(); i++){
                try {
                    JSONObject mCharacterEntry = mLadderEntries.getJSONObject(i);
                    JSONObject mCharacterObject = mCharacterEntry.getJSONObject("character");
                    JSONObject mAccountObject = mCharacterEntry.getJSONObject("account");
                    JSONObject mChallengesObject = mAccountObject.getJSONObject("challenges");
                    Ladder mLadder = new Ladder(
                            mCharacterEntry.getInt("rank"),
                            mCharacterEntry.getBoolean("dead"),
                            false,
                            mCharacterEntry.getBoolean("online"),
                            mCharacterObject.getString("name"),
                            mCharacterObject.getInt("level"),
                            mCharacterObject.getString("class"),
                            mCharacterObject.getString("id"),
                            mCharacterObject.getLong("experience"),
                            0,
                            0,
                            mAccountObject.getString("name"),
                            mChallengesObject.getInt("total"),
                            null );
                    // Error Handling for Retired in case non-existent
                    if(mCharacterEntry.has("retired")){
                        mLadder.setRetired(mCharacterEntry.getBoolean("retired"));
                    }
                    // Error Handling for Delve in case non-existent
                    if(mCharacterObject.has("depth")){
                        JSONObject mDelveObject = mCharacterObject.getJSONObject("depth");
                        mLadder.setDelveParty(mDelveObject.getInt("default"));
                        mLadder.setDelveSolo(mDelveObject.getInt("solo"));
                    }
                    // Error Handling for Twitch in case non-existent
                    if(mAccountObject.has("twitch")){
                        JSONObject mTwitchObject = mAccountObject.getJSONObject("twitch");
                        mLadder.setTwitch(mTwitchObject.getString("name"));
                    }
                    // Start fragment
                    mLadderList.add(mLadder);
                    //Log.d(TAG, "CharEntry[" + i + "]: " + mLadder.PrintLadderInfo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            CreateRecyclerView(mRvLadder, mLadderList);
            Log.d(TAG, "LadderOnResponse Done");

        }


        /**
         * Create LadderInfoDialog with Ladder object in Bundle
         * @param position : Position of the list to retrieve the Ladder Object
         */
        public void OnAdapterItemClick(int position){
            LadderInfoDialog dialog = new LadderInfoDialog();
            Bundle args = new Bundle();
            args.putParcelable(ARG_CHARACTER_INFO, mLadderList.get(position));
            dialog.setArguments(args);
            dialog.show(getActivity().getSupportFragmentManager(), dialog.getTag());
        }


        // CreateRecyclerView
        public void CreateRecyclerView(RecyclerView recyclerView, List<Ladder> ladderList){
            recyclerView.setHasFixedSize(true);
            this.mLadderList = ladderList;
            mLadderLayoutManager = new LinearLayoutManager(getContext());
            mLadderAdapter = new LadderAdapter(mLadderList);
            recyclerView.setLayoutManager(mLadderLayoutManager);
            recyclerView.setAdapter(mLadderAdapter);
            mLadderAdapter.setOnItemClickListener(this::OnAdapterItemClick);
        }


        // The fragment argument representing the section number for this fragment
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static final String ARG_LADDER_ID = "ARG_LADDER_ID";

        // Empty Constructor
        public PlaceholderFragment() {
        }


        // Returns a new instance of this fragment for the given section number.
        public static PlaceholderFragment newInstance(int sectionNumber, String leagueId) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_LADDER_ID,leagueId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ladder, container, false);
            SendLadderRequest(getArguments().getString(ARG_LADDER_ID),50,getArguments().getInt(ARG_SECTION_NUMBER)*50,"league","false","","","");
            mRvLadder = rootView.findViewById(R.id.listLadder);
//            CreateRecyclerView(mRvLadder, getArguments().getParcelableArrayList(ARG_LADDER_LIST));

            return rootView;
        }
    }

    /**********************************************************************************************
     *                                 FragmentPagerAdapter                                       *
     *********************************************************************************************/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<Ladder> mLadderList = new ArrayList<>();
        private String mLeagueId;

        public SectionsPagerAdapter(FragmentManager fm, String leagueId) {
            super(fm);
            this.mLeagueId = leagueId;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.d(TAG, "SectionPage " + position);
            return PlaceholderFragment.newInstance(position, mLeagueId);


        }

        @Override
        public int getCount() {
            return LADDER_PAGE_COUNT;
        }
    }
}
