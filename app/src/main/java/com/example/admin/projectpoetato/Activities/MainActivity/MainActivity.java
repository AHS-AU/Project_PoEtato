package com.example.admin.projectpoetato.Activities.MainActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.admin.projectpoetato.Activities.LeagueActivity.LeagueActivity;
import com.example.admin.projectpoetato.R;


public class MainActivity extends AppCompatActivity {
    // Class Variables
    public static final String TAG = MainActivity.class.getSimpleName();

    // UI Variables
    private Button mBtnLeague;

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

        // Button Start League Activity
        mBtnLeague.setOnClickListener(v -> StartLeagueActivity());

        // Action Bar
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primaryLightColor)));
//        }


    }


}
