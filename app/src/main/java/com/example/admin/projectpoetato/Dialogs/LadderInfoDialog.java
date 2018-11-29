package com.example.admin.projectpoetato.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.admin.projectpoetato.Utilities.GlobalVariables.ARG_CHARACTER_INFO;

public class LadderInfoDialog extends AppCompatDialogFragment {
    public static final String TAG = LadderInfoDialog.class.getSimpleName();

    private TextView mTextStatus;
    private TextView mTextRank;
    private TextView mTextCharacter;
    private TextView mTextAccount;
    private TextView mTextLevel;
    private TextView mTextClass;
    private TextView mTextDelveParty;
    private TextView mTextDelveSolo;
    private TextView mTextExperience;
    private TextView mTextChallenges;
    private TextView mTextTwitch;



    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    private String getStatus(Ladder Character){
        if(Character.isDead()){
            return "Dead";
        } else if (Character.isRetired()){
            return "Retired";
        } else if (Character.isOnline()){
            return "Online";
        }else{
            return "Offline";
        }
    }

    /**
     * Set Character Info for the chosen Character from the Ladder List
     * @param Character : Ladder object
     */
    private void setCharacterInfo(Ladder Character){
        // Status
        mTextStatus.setText(getStatus(Character));

        // Rank
        mTextRank.setText(String.valueOf(Character.getRank()));

        // Character
        mTextCharacter.setText(Character.getCharacterName());

        // Account
        mTextAccount.setText(Character.getAccountName());

        // Level
        mTextLevel.setText(String.valueOf(Character.getLevel()));

        // Class
        mTextClass.setText(Character.getClassId());

        // Delve Party
        mTextDelveParty.setText(String.valueOf(Character.getDelveParty()));

        // Delve Solo
        mTextDelveSolo.setText(String.valueOf(Character.getDelveSolo()));

        // Experience, first Formatting the number to Local
        String formattedExperience = String.valueOf(NumberFormat.getNumberInstance(Locale.getDefault()).format(Character.getExperience()));
        mTextExperience.setText(formattedExperience);

        // Challenges
        mTextChallenges.setText(String.valueOf(Character.getChallenges()));

        // Twitch.tv link
        if(Character.getTwitch() != null){
            mTextTwitch.setText("twitch.tv/" + Character.getTwitch());
        }
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Ladder mCharacterInfo = getArguments().getParcelable(ARG_CHARACTER_INFO);
        // Prepare the AlertDialog Builder
        AlertDialog.Builder mADBuilder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);

        // Set up LayoutInflater and View
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        View mView = mLayoutInflater.inflate(R.layout.dialog_ladder_character, null);

        // UI Find View
        mTextStatus = mView.findViewById(R.id.textStatus);
        mTextRank = mView.findViewById(R.id.textRank);
        mTextCharacter = mView.findViewById(R.id.textCharName);
        mTextAccount = mView.findViewById(R.id.textAccount);
        mTextLevel = mView.findViewById(R.id.textLevel);
        mTextClass = mView.findViewById(R.id.textClass);
        mTextDelveParty = mView.findViewById(R.id.textDelveParty);
        mTextDelveSolo = mView.findViewById(R.id.textDelveSolo);
        mTextExperience = mView.findViewById(R.id.textExperience);
        mTextChallenges = mView.findViewById(R.id.textChallenges);
        mTextTwitch = mView.findViewById(R.id.textTwitch);

        // Set Character Info
        setCharacterInfo(mCharacterInfo);

        // Set up the AlertDialog Builder
        mADBuilder.setView(mView)
                .setTitle(R.string.cid_title);
        return mADBuilder.create();
    }
}
