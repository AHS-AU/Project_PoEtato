package com.example.admin.projectpoetato.Activities.MainActivity;

import android.content.Intent;
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

import com.example.admin.projectpoetato.Activities.LeagueActivity.LeagueActivity;
import com.example.admin.projectpoetato.Fragments.SettingsFragment;
import com.example.admin.projectpoetato.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Class Variables
    public static final String TAG = MainActivity.class.getSimpleName();

    // UI Variables
    private Button mBtnLeague;
    private DrawerLayout mDrawer;

    // Variables

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    public void StartLeagueActivity(){
        Intent intent = new Intent(this, LeagueActivity.class);
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
        mDrawer = findViewById(R.id.drawer_main);
        NavigationView mNaviView = findViewById(R.id.navi_view);


        // Button Start League Activity
        mBtnLeague.setOnClickListener(v -> StartLeagueActivity());

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.navi_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, new SettingsFragment())
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
