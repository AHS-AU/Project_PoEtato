package com.example.admin.projectpoetato.Activities.LeagueActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.League;
import com.example.admin.projectpoetato.R;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueAdapterViewHolder> {
    private List<League> mLeagueList;

    /**
     * ViewHolder to LeagueAdapter
     */
    public static class LeagueAdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextId;
        public TextView mTextStart;
        public TextView mTextEnd;
        public TextView mTextTrack;

        public LeagueAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            // UI Find Views
            mTextId = itemView.findViewById(R.id.textLeagueId);
            mTextStart = itemView.findViewById(R.id.textLeagueStart);
            mTextEnd = itemView.findViewById(R.id.textLeagueEnd);
            mTextTrack = itemView.findViewById(R.id.textLeagueTrack);

        }
    }

    /**
     * Constructor
     * @param leagueList : List of League passed to constructor
     */
    public LeagueAdapter(List<League> leagueList){
        this.mLeagueList = leagueList;
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @NonNull
    @Override
    public LeagueAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_leagues, viewGroup, false);
        return new LeagueAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueAdapterViewHolder leagueAdapterViewHolder, int position) {
        League mLeague = mLeagueList.get(position);

        // League Id
        leagueAdapterViewHolder.mTextId.setText(mLeague.getId());

        // League Start
        leagueAdapterViewHolder.mTextStart.setText(mLeague.getStartAt());

        // League End
        leagueAdapterViewHolder.mTextEnd.setText(mLeague.getEndAt());

        // League Track
        leagueAdapterViewHolder.mTextTrack.setText("TMP");
    }

    @Override
    public int getItemCount() {
        return mLeagueList.size();
    }
}

