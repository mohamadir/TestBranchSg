package com.snapgroup.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.snapgroup.Activities.NonScrollListView;
import com.snapgroup.Adapters.ChildVotingInfo;
import com.snapgroup.Adapters.ExpandlepaleLIstViewVOtes;
import com.snapgroup.Adapters.HeaderVoting;
import com.snapgroup.Adapters.VoteLIstViewAdapter;
import com.snapgroup.R;
import com.snapgroup.Tests.ActivtyTesting23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VoteFragmentTabs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<String> selcet_activty, offers_list, when_list,  votes_list,  votes_more_list;
    public ListView listDialog,listView;

    ExpandlepaleLIstViewVOtes listAdapter;
    ExpandableListView expListView;
    public ArrayList<HeaderVoting> headerVotingLIst;
    public HashMap<String,ChildVotingInfo> listChildData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public VoteLIstViewAdapter voteLIstViewAdapter ;
    public ArrayList<String> actev,ofers,whenLIst,votes,votesMore;



    public VoteFragmentTabs()
    {}


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


        listView = (ListView)view.findViewById(R.id.listView12);

        ArrayList<String> my_array=new ArrayList<String>();
        my_array.add("\n" +
                "Activities avalible on day 1 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 2 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 3 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 4 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 5 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 6 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 7 (25/10/17)");

        my_array.add("\n" +
                "Activities avalible on day 8 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 9 (25/10/17)");

        my_array.add("\n" +
                "Activities avalible on day 10 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 11 (25/10/17)");
        my_array.add("\n" +
                "Activities avalible on day 12 (25/10/17)");

        my_array.add("\n" +
                "Activities avalible on day 13 (25/10/17)");

        my_array.add("\n" +
                "Activities avalible on day 14 (25/10/17)");

        selcet_activty = new ArrayList<String>();
        selcet_activty.add("1");
        selcet_activty.add("2");
        selcet_activty.add("3");
        selcet_activty.add("4");
        selcet_activty.add("5");
        selcet_activty.add("6");
        selcet_activty.add("7");

        offers_list = new ArrayList<String>();
        offers_list.add("asdasd");
        offers_list.add("asdasd");
        when_list = new ArrayList<String>();
        when_list.add("Asdasd");
        when_list.add("Asdasd");
        votes_list = new ArrayList<String>();
        votes_list.add("asd");
        votes_list.add("asd");
        votes_more_list = new ArrayList<String>();
        votes_more_list.add("asdasd");
        votes_more_list.add("asdasd");
        votes_more_list.add("asdasd");
        votes_more_list.add("asdasd");







        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.text12, my_array);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                View joinDialog= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_vote,null);


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(joinDialog);

                listDialog = (ListView) joinDialog.findViewById(R.id.listDialog);
                VoteLIstViewAdapter adapter1 = new VoteLIstViewAdapter(getActivity(),selcet_activty,offers_list,when_list,votes_list,votes_more_list);
                listDialog.setAdapter(adapter1);
                final AlertDialog dialog4 = builder.create();
                dialog4.show();



            }
        });



        return view;
    }


}


/*
 expListView = (ExpandableListView)view.findViewById(R.id.activity_expandable_list_view);
       // ExpandlepaleLIstViewVOtes(Activity activityDay,List<HeaderVoting> headerVotingLIst, HashMap<String,ChildVotingInfo> listChildData)

        headerVotingLIst =new ArrayList<HeaderVoting>();
        headerVotingLIst.add(new HeaderVoting("Activities for Day 4 (25/10/17)",""));
        headerVotingLIst.add(new HeaderVoting("Activities for Day 5 (25/10/17)",""));
        headerVotingLIst.add(new HeaderVoting("Activities for Day 6 (25/10/17)",""));
        headerVotingLIst.add(new HeaderVoting("Activities for Day 7 (25/10/17)",""));
        headerVotingLIst.add(new HeaderVoting("Activities for Day 8 (25/10/17)",""));


        listChildData= new HashMap<String, ChildVotingInfo>();
        listChildData.put(0+"",new ChildVotingInfo("Race Cars","23","123","123","1 Cars","123"));
        listChildData.put(1+"",new ChildVotingInfo("asd Cars","","","","2 Cars",""));
        listChildData.put(2+"",new ChildVotingInfo("ewq Cars","","","","3 Cars",""));
        listChildData.put(3+"",new ChildVotingInfo("ryt Cars","","","","4 Cars",""));
        listChildData.put(4+"",new ChildVotingInfo("cbv Cars","","","","5 Cars",""));




        actev=new ArrayList<String>();
        actev.add("Rasdace car");
        actev.add("asd car");
        actev.add("asd car");
        actev.add("asd car");
        actev.add("asd car");
        ofers=new ArrayList<String>();
        whenLIst=new ArrayList<String>();
        votes=new ArrayList<String>();
        votesMore=new ArrayList<String>();



        listAdapter = new ExpandlepaleLIstViewVOtes(getActivity(), headerVotingLIst, listChildData);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, final int groupPosition, long l) {

                if(expListView.isGroupExpanded(groupPosition)){
                    // if the group list expanded, collapse it
                    expListView.collapseGroup(groupPosition);
                    Log.d("ExpdList", "Exit");
                } else {
                    // if the group list collapse, first collapse all the expanded group list if there any
                    int count = expListView.getCount();
                    Log.d("ExpdList", "Count: " + count);
                    for(int i = 0; i < count; i++){
                        if(i!=groupPosition)
                            expListView.collapseGroup(i);
                        else {
                            if(expListView.getChildAt(0)!=null)
                                expListView.expandGroup(i);
                        }
                    }
                    // then expand the this one
                    Log.d("ExpdList", "Open");
                }
                return true;
            }

        });
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
 */
