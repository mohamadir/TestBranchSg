package com.snapgroup.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.snapgroup.R;
import com.snapgroup.Adapters.VoteExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ExpandableListView voteListView;
    private VoteExpandableListAdapter voteListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public VotesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VotesFragment newInstance(String param1, String param2) {
        VotesFragment fragment = new VotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_votes, container, false);

        // initialize the static voting data
        initData();

        // Create the Expandable Vote List Adapter
        voteListAdapter = new VoteExpandableListAdapter(getActivity(), listDataHeader, listHash);
        // Get the Expandable List View form the layout
        voteListView = (ExpandableListView) view.findViewById(R.id.vote_list);
        // Set the adapter to the list
        voteListView.setAdapter(voteListAdapter);

        // set footer view to include the vote_footer layout
        View footerView = View.inflate(getActivity(), R.layout.vote_footer, null);
        voteListView.addFooterView(footerView);

        // set group indicator position
        voteListView.setIndicatorBounds(0, 0);

        // Expand and Collapse Click Listener
        voteListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long l) {
                // on click, check if the group list is expanded
                if(voteListView.isGroupExpanded(groupPosition)){
                    // if the group list expanded, collapse it
                    voteListView.collapseGroup(groupPosition);
                    Log.d("ExpdList", "Exit");
                } else {
                    // if the group list collapse, first collapse all the expanded group list if there any
                    int count = voteListView.getCount();
                    Log.d("ExpdList", "Count: " + count);
                    for(int i = 0; i < count; i++){
                        voteListView.collapseGroup(i);
                    }
                    // then expand the this one
                    voteListView.expandGroup(groupPosition);
                    Log.d("ExpdList", "Open");
                }
                return true;
            }
        });

        return view;
    }

    // init static voting data
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Flights");
        listDataHeader.add("Hotels");
        listDataHeader.add("Tour Guides");
        listDataHeader.add("Transportation");
        listDataHeader.add("Resturants");
        listDataHeader.add("Places");

        List<String> filghtVote = new ArrayList<>();
        filghtVote.add("EL-AL (Israel Air)");
        filghtVote.add("UK-AIR (UK Air)");
        filghtVote.add("SDG Moscow (Denmark Air)");
        filghtVote.add("TRY (Turkish Air)");

        List<String> hotelVote = new ArrayList<>();
        hotelVote.add("Mun");
        hotelVote.add("Alinz");

        List<String> guideVote = new ArrayList<>();
        guideVote.add("John Doe");
        guideVote.add("Mike Ross");


        List<String> transVote = new ArrayList<>();
        transVote.add("Bus");
        transVote.add("Train");


        List<String> resturantsVote = new ArrayList<>();
        resturantsVote.add("Dejavo");
        resturantsVote.add("Mondo");


        List<String> placesVote = new ArrayList<>();
        placesVote.add("Mini Palce");
        placesVote.add("Mits");

        listHash.put(listDataHeader.get(0), filghtVote);
        listHash.put(listDataHeader.get(1), hotelVote);
        listHash.put(listDataHeader.get(2), guideVote);
        listHash.put(listDataHeader.get(3), transVote);
        listHash.put(listDataHeader.get(4), resturantsVote);
        listHash.put(listDataHeader.get(5), placesVote);

    }

}
