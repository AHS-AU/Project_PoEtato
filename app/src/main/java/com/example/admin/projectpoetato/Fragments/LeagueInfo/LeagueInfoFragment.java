package com.example.admin.projectpoetato.Fragments.LeagueInfo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.Models.LeagueRules;
import com.example.admin.projectpoetato.R;
import com.example.admin.projectpoetato.Utilities.GlobalFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

    // UI Variables
    private TextView mTextLeagueName;
    private TextView mTextDescription;
    private TextView mTextRegister;
    private TextView mTextEvent;
    private TextView mTextUrl;
    private TextView mTextStart;
    private TextView mTextEnd;
    private TextView mTextLeagueEvent;
    private OnFragmentInteractionListener mListener;
    private ListView lvRules;

    // Variables
    private League mLeague;
    private LeagueInfoAdapter mLeagueInfoAdapter;
    private List<LeagueRules> mLeagueRules = new ArrayList<>();
    private GlobalFunctions mGlobalFunctions = new GlobalFunctions();

    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
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

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
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
        lvRules = mView.findViewById(R.id.listLeagueRules);

        // Assign Values to the UI
        mTextLeagueName.setText(mLeague.getId());
        mTextDescription.setText(mLeague.getDescription());
        mTextRegister.setText(mGlobalFunctions.ConvertUtcToLocal(mLeague.getRegisterAt()));
        mTextEvent.setText(mLeague.getEvent());
        mTextUrl.setText(mLeague.getUrl());
        mTextStart.setText(mGlobalFunctions.ConvertUtcToLocal(mLeague.getStartAt()));
        mTextEnd.setText(mGlobalFunctions.ConvertUtcToLocal(mLeague.getEndAt()));
        mTextLeagueEvent.setText(mLeague.getLeagueEvent());

        // Handle the List of League Rules
        JSONArray jsonArray = new JSONArray(mLeague.getRules());
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                LeagueRules rule = new LeagueRules(jsonObj.get("id").toString(), jsonObj.get("name").toString(), jsonObj.get("description").toString());
                mLeagueRules.add(rule);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Set up Rules in Listview with help of LeagueInfoAdapter
        mLeagueInfoAdapter = new LeagueInfoAdapter(getContext(), mLeagueRules);
        lvRules.setAdapter(mLeagueInfoAdapter);


        // Toolbar fuck this shit, for some fucking reason Toolbar from Activity follows into this retarded fragment
//        Toolbar mToolbar = mView.findViewById(R.id.toolbar_fragment_leagueinfo);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        mToolbar.setNavigationOnClickListener(v -> CloseFragment());
//        mToolbar.bringToFront();

        // Return View
        return mView;
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
        void onFragmentInteraction(Uri uri);
    }
}
