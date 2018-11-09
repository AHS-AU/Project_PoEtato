package com.example.admin.projectpoetato.Activities.MainActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;

import com.example.admin.projectpoetato.Activities.LeagueActivity.LeagueActivity;
import com.example.admin.projectpoetato.R;


public class MainActivity extends AppCompatActivity {
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

        // Button Start League Activity
        mBtnLeague.setOnClickListener(v -> StartLeagueActivity());

        // Action Bar
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primaryLightColor)));
//        }

       Toolbar toolbar = findViewById(R.id.toolbar_main);
       setSupportActionBar(toolbar);

        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,
                R.string.navi_drawer_open,R.string.navi_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();


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
