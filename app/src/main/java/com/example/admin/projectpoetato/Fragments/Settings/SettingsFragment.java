package com.example.admin.projectpoetato.Fragments.Settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projectpoetato.R;

public class SettingsFragment extends Fragment {
    // Class Variables
    public static final String TAG = SettingsFragment.class.getSimpleName();

    // UI Variables
    View mView;


    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    private void CloseFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Find View from Inflater
        mView = inflater.inflate(R.layout.fragment_settings, container, false);

        // UI Find View
        Toolbar mToolbar = mView.findViewById(R.id.toolbar_fragment_settings);

        // Set up the Toolbar for the Fragment
        mToolbar.setNavigationOnClickListener(v -> CloseFragment());

        return mView;
    }


}
