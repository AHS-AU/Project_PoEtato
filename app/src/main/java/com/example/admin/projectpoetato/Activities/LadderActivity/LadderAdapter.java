package com.example.admin.projectpoetato.Activities.LadderActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.R;
import com.example.admin.projectpoetato.Utilities.GlobalFunctions;

import java.util.List;

public class LadderAdapter extends RecyclerView.Adapter<LadderAdapter.LadderAdapterViewHolder> {
    private List<Ladder> mLadderList;
    private OnItemClickListener mItemListener;

    // When item gets clicked
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    // Set the ItemListener
    public void setOnItemClickListener(OnItemClickListener itemListener){
        this.mItemListener = itemListener;
    }

    // When an item gets clicked
    private static void ItemClicked(View itemView, OnItemClickListener itemListener, int position){
        if(itemListener != null && position != RecyclerView.NO_POSITION){
            itemListener.onItemClick(position);
        }
    }

    // Constructor
    public LadderAdapter(List<Ladder> ladderList) {
        this.mLadderList = ladderList;
    }

    // ViewHolder to LadderAdapter
    public static class LadderAdapterViewHolder extends RecyclerView.ViewHolder{
        //private TextView mTextStatus;
        private TextView mTextRank;
        private TextView mTextCharacter;
        private TextView mTextAccount;
        private TextView mTextLevel;
        private TextView mTextClass;
        private TextView mTextRawExp;
        //private TextView mProgressExp;

        public LadderAdapterViewHolder(@NonNull View itemView, OnItemClickListener itemListener) {
            super(itemView);

            // UI Find views
            // Imageview Status
            mTextRank = itemView.findViewById(R.id.textLadderRank);
            mTextCharacter = itemView.findViewById(R.id.textLadderCharacter);
            mTextLevel = itemView.findViewById(R.id.textLadderLevel);

            itemView.setOnClickListener(v -> LadderOnItemClick(itemListener));
        }

        private void LadderOnItemClick( OnItemClickListener itemListener){
            if (itemListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    itemListener.onItemClick(position);
                }
            }
        }

    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @NonNull
    @Override
    public LadderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_ladder, viewGroup, false);
        return new LadderAdapterViewHolder(mView, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LadderAdapterViewHolder ladderAdapterViewHolder, int position) {
        Ladder mLadder = mLadderList.get(position);

        // Ladder Status

        // Ladder Rank
        ladderAdapterViewHolder.mTextRank.setText(mLadder.getRank());

        // Ladder Character
        ladderAdapterViewHolder.mTextCharacter.setText(mLadder.getCharacterName());

        // Ladder Level
        ladderAdapterViewHolder.mTextLevel.setText(mLadder.getLevel());

    }

    @Override
    public int getItemCount() {
        return mLadderList.size();
    }
}
