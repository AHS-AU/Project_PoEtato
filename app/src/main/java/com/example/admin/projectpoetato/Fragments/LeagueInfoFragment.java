package com.example.admin.projectpoetato.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeagueInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeagueInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeagueInfoFragment extends Fragment {
    public static final String TAG = LeagueInfoFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LEAGUE = "league";;

    // TODO: Rename and change types of parameters
    private League mLeague;

    private OnFragmentInteractionListener mListener;

    public LeagueInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @league : League object
     * @return A new instance of fragment LeagueInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    // TODO: Add necessary UI views here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_league_info, container, false);
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
