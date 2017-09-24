package com.snapgroup.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snapgroup.R;

import java.util.ArrayList;

/**
 * Created by root on 13/09/17.
 */

public class VoteLIstViewAdapter extends BaseAdapter{



    private Activity context_1;
    ArrayList<String> selcet_activty,offers_list,when_list,votes_list,votes_more_list;


    public VoteLIstViewAdapter(Activity context_1, ArrayList<String> selcet_activty, ArrayList<String> offers_list, ArrayList<String> when_list, ArrayList<String> votes_list, ArrayList<String> votes_more_list) {
        this.context_1 = context_1;
        this.selcet_activty = selcet_activty;
        this.offers_list = offers_list;
        this.when_list = when_list;
        this.votes_list = votes_list;
        this.votes_more_list = votes_more_list;
    }

    @Override
    public int getCount() {
        return selcet_activty.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override

    public View getView(int position, View convertView, ViewGroup viewGroup) {

        VoteLIstViewAdapter.voteItem voteItem = null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {


            convertView = LayoutInflater.from(context_1).inflate(R.layout.vote_item, null);
            voteItem = new VoteLIstViewAdapter.voteItem();
            voteItem.votes_more = (TextView) convertView.findViewById(R.id.votes_more);
            voteItem.linaretBaground = (LinearLayout)convertView.findViewById(R.id.linaretBaground);
            voteItem.votes_text = (TextView) convertView.findViewById(R.id.votes_text);
            voteItem.when_text = (TextView) convertView.findViewById(R.id.when_text);
            voteItem.offres = (TextView) convertView.findViewById(R.id.offres);
            voteItem.selct_actev = (TextView) convertView.findViewById(R.id.selct_actev);


            convertView.setTag(voteItem);
        }

        voteItem.selct_actev.setText(selcet_activty.get(position));
        if (position%2==0)
            voteItem.linaretBaground.setBackgroundColor(Color.parseColor("#d5cd9c"));




        return convertView;

    }


    public class voteItem
    {
        public TextView votes_more,votes_text,when_text,offres,selct_actev;
        public LinearLayout linaretBaground;
        public voteItem(){

        }

        public TextView getVotes_more() {
            return votes_more;
        }

        public void setVotes_more(TextView votes_more) {
            this.votes_more = votes_more;
        }

        public TextView getVotes_text() {
            return votes_text;
        }

        public void setVotes_text(TextView votes_text) {
            this.votes_text = votes_text;
        }

        public TextView getWhen_text() {
            return when_text;
        }

        public void setWhen_text(TextView when_text) {
            this.when_text = when_text;
        }

        public TextView getOffres() {
            return offres;
        }

        public void setOffres(TextView offres) {
            this.offres = offres;
        }

        public TextView getSelct_actev() {
            return selct_actev;
        }

        public void setSelct_actev(TextView selct_actev) {
            this.selct_actev = selct_actev;
        }
    }




}
