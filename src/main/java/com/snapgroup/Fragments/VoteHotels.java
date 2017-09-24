package com.snapgroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.snapgroup.Adapters.ChildVotingInfo;
import com.snapgroup.Adapters.ExpandlepaleLIstViewVOtes;
import com.snapgroup.Adapters.HeaderVoting;
import com.snapgroup.Adapters.VoteLIstViewAdapter;
import com.snapgroup.R;

import java.util.ArrayList;
import java.util.HashMap;


public class VoteHotels extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ExpandlepaleLIstViewVOtes listAdapter;
    ExpandableListView expListView;
    public ArrayList<HeaderVoting> headerVotingLIst;
    public HashMap<String,ChildVotingInfo> listChildData;
    public int integer=0;
    public ListView listView;
    public VoteLIstViewAdapter voteLIstViewAdapter ;
    public ArrayList<String> actev,ofers,whenLIst,votes,votesMore;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public VoteHotels() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_vote_fragment_tabs, container, false);

        return view;
    }





}
