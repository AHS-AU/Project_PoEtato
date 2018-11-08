package com.example.admin.projectpoetato.Activities.MainActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Find View
        mBtnLeague = findViewById(R.id.btnMainLeague);

        // Button Start League Activity
        mBtnLeague.setOnClickListener(v -> StartLeagueActivity());

    }

    public void StartLeagueActivity(){
        Intent intent = new Intent(this, LeagueActivity.class);
        startActivity(intent);
    }


}
