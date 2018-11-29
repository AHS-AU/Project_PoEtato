package com.example.admin.projectpoetato.Activities.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.example.admin.projectpoetato.Activities.LadderActivity.LadderActivity;
import com.example.admin.projectpoetato.Activities.LeagueActivity.LeagueActivity;
import com.example.admin.projectpoetato.Dialogs.LadderInfoDialog;
import com.example.admin.projectpoetato.Fragments.Settings.SettingsFragment;
import com.example.admin.projectpoetato.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    ConnectivityManager mConnMan;
    NetworkInfo mActiveNetworkInfo;
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set up the ConnectivityManager to ensure Connection to the Internet.
        mConnMan = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMan != null){
            mActiveNetworkInfo = mConnMan.getActiveNetworkInfo();
        }
        isConnected = mActiveNetworkInfo != null && mActiveNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() isConnected to Internet? " + isConnected);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.navi_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, new SettingsFragment())
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                        .commit();
                break;
            case R.id.navi_theme:
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
