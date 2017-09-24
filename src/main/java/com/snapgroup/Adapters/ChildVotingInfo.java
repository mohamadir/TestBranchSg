package com.snapgroup.Adapters;

import android.widget.LinearLayout;
import android.widget.TextView;

public class ChildVotingInfo {

    public String votes_more,votes_text,when_text,offres,selct_actev,textView176;
    public LinearLayout background;


    public String getVotes_more() {
        return votes_more;
    }

    public void setVotes_more(String votes_more) {
        this.votes_more = votes_more;
    }

    public String getVotes_text() {
        return votes_text;
    }

    public void setVotes_text(String votes_text) {
        this.votes_text = votes_text;
    }

    public String getWhen_text() {
        return when_text;
    }

    public void setWhen_text(String when_text) {
        this.when_text = when_text;
    }

    public String getOffres() {
        return offres;
    }

    public void setOffres(String offres) {
        this.offres = offres;
    }

    public String getSelct_actev() {
        return selct_actev;
    }

    public void setSelct_actev(String selct_actev) {
        this.selct_actev = selct_actev;
    }

    public ChildVotingInfo(String votes_more, String votes_text, String when_text, String offres, String selct_actev,String textView176) {
        this.votes_more = votes_more;
        this.textView176=textView176;
        this.votes_text = votes_text;
        this.when_text = when_text;
        this.offres = offres;
        this.selct_actev = selct_actev;
    }
}
