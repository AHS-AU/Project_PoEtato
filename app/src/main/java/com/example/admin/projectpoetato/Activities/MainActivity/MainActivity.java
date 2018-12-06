package com.example.admin.projectpoetato.Activities.MainActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.admin.projectpoetato.Activities.LadderActivity.LadderActivity;
import com.example.admin.projectpoetato.Activities.LeagueActivity.LeagueActivity;
import com.example.admin.projectpoetato.Database.Room.Ladder.LadderViewModel;
import com.example.admin.projectpoetato.Dialogs.LadderInfoDialog;
import com.example.admin.projectpoetato.Fragments.Settings.SettingsFragment;
import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.R;
import com.example.admin.projectpoetato.Services.LadderService;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Class Variables
    public static final String TAG = MainActivity.class.getSimpleName();

    // UI Variables
    private Button mBtnLeague;
    private Button mBtnLadder;
    private DrawerLayout mDrawer;

    // Variables
    private ConnectivityManager mConnMan;
    private NetworkInfo mActiveNetworkInfo;
    private boolean isConnected;

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    public void StartLeagueActivity(){
        Intent intent = new Intent(this, LeagueActivity.class);
        startActivity(intent);
    }

    public void StartLadderActivity(){
        Intent intent = new Intent(this, LadderActivity.class);
        startActivity(intent);
    }

    public void StartLadderService(){
        Intent ladderServiceIntent = new Intent(this, LadderService.class);
        ContextCompat.startForegroundService(this,ladderServiceIntent);
        startService(ladderServiceIntent);
    }

    public void StopLadderService(){
        Intent ladderServiceIntent = new Intent(this, LadderService.class);
        stopService(ladderServiceIntent);
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Find View
        mBtnLeague = findViewById(R.id.btnMainLeague);
        mBtnLadder = findViewById(R.id.btnMainLadder);
        mDrawer = findViewById(R.id.drawer_main);
        NavigationView mNaviView = findViewById(R.id.navi_view);


        // Button Start League Activity
        mBtnLeague.setOnClickListener(v -> StartLeagueActivity());
        //mBtnLadder.setOnClickListener(v -> StartLadderActivity());
        mBtnLadder.setOnClickListener(v -> StartLadderActivity());

        // Navigation View
        mNaviView.setNavigationItemSelectedListener(this);

        // ToolBar
        Toolbar mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // ActionBarDrawerToggle
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this,mDrawer,mToolbar,
                R.string.navi_drawer_open,R.string.navi_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        // TODO: Debug Database, DELETE THIS LATER
        LadderViewModel ladderViewModel = ViewModelProviders.of(this).get(LadderViewModel.class);
        ladderViewModel.getAllLadders().observe(this, new Observer<List<Ladder>>()  {
            @Override
            public void onChanged(@Nullable List<Ladder> ladderList) {
                for(int i = 0; i < ladderList.size(); i++){
                    Log.d(TAG, "Ladder["+i+"]" + " name = " + ladderList.get(i).getCharacterName());
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        // Set up the ConnectivityManager to ensure Connection to the Internet.
        mConnMan = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMan != null){
            mActiveNetworkInfo = mConnMan.getActiveNetworkInfo();
        }
        isConnected = mActiveNetworkInfo != null && mActiveNetworkInfo.isConnectedOrConnecting();

        // Bind to LadderService
        StartLadderService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        //StopLadderService();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + " isConnected to Internet = " + isConnected);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.navi_settings:
                // TODO: Add settings when app is almost fully completed
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.main_frame, new SettingsFragment())
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                        .commit();
                break;
            case R.id.navi_theme:
                // TODO: Add Light Theme at some point
                Log.d(TAG, "Navi_Theme Triggered!");
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }
}
