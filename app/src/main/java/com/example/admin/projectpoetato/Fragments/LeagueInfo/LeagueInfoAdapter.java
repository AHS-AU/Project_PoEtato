package com.example.admin.projectpoetato.Fragments.LeagueInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.projectpoetato.Models.LeagueRules;
import com.example.admin.projectpoetato.R;

import java.util.List;

public class LeagueInfoAdapter extends BaseAdapter {
    // Variables
    private Context mContext;
    private List<LeagueRules> mRulesList;


    /**********************************************************************************************
     *                                    Class Functions                                         *
     *********************************************************************************************/
    /**
     * Constructor
     * @param context : Context
     * @param rules : List of Rules from LeagueRules object
     */
    public LeagueInfoAdapter(Context context, List<LeagueRules> rules){
        this.mContext = context;
        this.mRulesList = rules;
    }

    /**********************************************************************************************
     *                                   Override Functions                                       *
     *********************************************************************************************/
    @Override
    public int getCount() {
        if(mRulesList == null){
            return 0;
        }
        return mRulesList.size();
    }

    @Override
    public Object getItem(int position) {
//        if (mRulesList != null && mRulesList.size() > position){
//            return mRulesList.get(position);
//        }
        return null;
    }

    @Override
    public long getItemId(int position) {
//        if(mRulesList != null){
//            int id = Integer.parseInt(mRulesList.get(position).getId());
//            return (id);
//        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If ConvertView is null, set it up with LayoutInflater
        if(convertView == null){
            LayoutInflater mInflater;
            mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.list_rules, null);
        }

        // Check content of mRulesList and put into the Listview
        if(mRulesList != null && mRulesList.size() > position){
            LeagueRules mRule = mRulesList.get(position);

            // Number
            TextView textRuleNum = convertView.findViewById(R.id.textRuleNum);
            textRuleNum.setText(String.valueOf(position+1));

            // Name
            TextView textRuleName = convertView.findViewById(R.id.textRuleName);
            //textRuleName.setText("Death penalty awarded to slayer");  // Just to test the looks
            textRuleName.setText(mRule.getName());

            // Description
            TextView textRuleDescription = convertView.findViewById(R.id.textRuleDescription);
            textRuleDescription.setText(mRule.getDescription());

            // Return ConvertView
            return convertView;

        }

        // Return Null if no Rules available
        return null;
    }
}
