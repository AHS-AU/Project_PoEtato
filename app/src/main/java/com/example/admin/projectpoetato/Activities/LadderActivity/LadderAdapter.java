package com.example.admin.projectpoetato.Activities.LadderActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.Ladder;
import com.example.admin.projectpoetato.R;
import com.example.admin.projectpoetato.Utilities.GlobalFunctions;

import java.util.List;

public class LadderAdapter extends RecyclerView.Adapter<LadderAdapter.LadderAdapterViewHolder> {
    private List<Ladder> mLadderList;
    private Context mContext;
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
    public LadderAdapter(List<Ladder> ladderList, Context context) {
        this.mLadderList = ladderList;
        this.mContext = context;
    }

    // ViewHolder to LadderAdapter
    public static class LadderAdapterViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImgStatus;
        private TextView mTextRank;
        private TextView mTextCharacter;
        private TextView mTextAccount;
        private TextView mTextLevel;
        private TextView mTextClass;
        private TextView mTextRawExp;
        private boolean isTablet;
        //private TextView mProgressExp;


        public LadderAdapterViewHolder(@NonNull View itemView, OnItemClickListener itemListener, Context context) {
            super(itemView);
            // Create variable to check if device is Tablet
            isTablet = context.getResources().getBoolean(R.bool.isTablet);

            // UI Find views
            mImgStatus = itemView.findViewById(R.id.imgLadderStatus);
            mTextRank = itemView.findViewById(R.id.textLadderRank);
            mTextCharacter = itemView.findViewById(R.id.textLadderCharacter);
            mTextLevel = itemView.findViewById(R.id.textLadderLevel);
            if(isTablet){
                mTextAccount = itemView.findViewById(R.id.textLadderAccount);
            }
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
        return new LadderAdapterViewHolder(mView, mItemListener, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull LadderAdapterViewHolder ladderAdapterViewHolder, int position) {
        Ladder mLadder = mLadderList.get(position);

        // Ladder Status
        if(!mLadder.isDead()){
            if(mLadder.isRetired()){
                ladderAdapterViewHolder.mImgStatus.setImageResource(R.drawable.ic_retired);
            }
            else if(!mLadder.isOnline()){
                ladderAdapterViewHolder.mImgStatus.setImageResource(R.drawable.ic_offline);
            }else{
                ladderAdapterViewHolder.mImgStatus.setImageResource(R.drawable.ic_online);
            }
        }else{
            ladderAdapterViewHolder.mImgStatus.setImageResource(R.drawable.ic_dead_skull);
        }


        // Ladder Rank
        ladderAdapterViewHolder.mTextRank.setText(String.valueOf(mLadder.getRank()));

        // Ladder Character
        ladderAdapterViewHolder.mTextCharacter.setText(mLadder.getCharacterName());

        // Ladder Account Name (Tablet only)
        if(ladderAdapterViewHolder.isTablet){
            ladderAdapterViewHolder.mTextAccount.setText(mLadder.getAccountName());
        }

        // Ladder Level
        ladderAdapterViewHolder.mTextLevel.setText(String.valueOf(mLadder.getLevel()));

    }

    @Override
    public int getItemCount() {
        return mLadderList.size();
    }
}
