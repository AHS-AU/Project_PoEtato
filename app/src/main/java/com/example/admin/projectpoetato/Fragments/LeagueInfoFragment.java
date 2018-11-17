package com.example.admin.projectpoetato.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;
import com.google.gson.JsonArray;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeagueInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeagueInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeagueInfoFragment extends Fragment {
    // Class Variables
    public static final String TAG = LeagueInfoFragment.class.getSimpleName();
    private static final String ARG_LEAGUE = "league";

    // Variables
    private League mLeague;

    // UI Variables
    private TextView mTextLeagueName;
    private TextView mTextDescription;
    private TextView mTextRegister;
    private TextView mTextEvent;
    private TextView mTextUrl;
    private TextView mTextStart;
    private TextView mTextEnd;
    private TextView mTextLeagueEvent;
    private SlidrInterface mSlidr;

    private OnFragmentInteractionListener mListener;

    public LeagueInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param league : League Object
     * @return : Return Fragment
     */
    public static LeagueInfoFragment newInstance(League league) {
        LeagueInfoFragment fragment = new LeagueInfoFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_LEAGUE, league);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLeague = (League)getArguments().getSerializable(ARG_LEAGUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_league_info, container, false);

        // UI Find Views
        mTextLeagueName = mView.findViewById(R.id.textLeagueInfoName);
        mTextDescription = mView.findViewById(R.id.textLeagueInfoDescription);
        mTextRegister = mView.findViewById(R.id.textLeagueInfoRegister);
        mTextEvent = mView.findViewById(R.id.textLeagueInfoEvent);
        mTextUrl = mView.findViewById(R.id.textLeagueInfoUrl);
        mTextStart = mView.findViewById(R.id.textLeagueInfoStart);
        mTextEnd = mView.findViewById(R.id.textLeagueInfoEnd);
        mTextLeagueEvent = mView.findViewById(R.id.textLeagueInfoLeagueEvent);

        // Assign Values to the UI
        mTextLeagueName.setText(mLeague.getId());
        mTextDescription.setText(mLeague.getDescription());
        mTextRegister.setText(mLeague.getRegisterAt());
        mTextEvent.setText(mLeague.getEvent());
        mTextUrl.setText(mLeague.getUrl());
        mTextStart.setText(mLeague.getStartAt());
        mTextEnd.setText(mLeague.getEndAt());
        mTextLeagueEvent.setText(mLeague.getLeagueEvent());
        Log.d(TAG, "Rules = " + mLeague.getRules());


//        Toolbar mToolbar = mView.findViewById(R.id.toolbar_fragment_leagueinfo);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        if(mSlidr == null){
//            mSlidr = Slidr.replace(getView().findViewById(R.id.league_frame), new SlidrConfig.Builder().position(SlidrPosition.LEFT).build());
//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
