package com.snapgroup.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.snapgroup.Adapters.MyHistoryListView;
import com.snapgroup.R;

import java.util.ArrayList;

public class MyHistoryFragment extends Fragment {

    public ArrayList<Integer> pictures;
    public ListView historyListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_history_fragment, container, false);
        historyListView = (ListView) view.findViewById(R.id.historyListView);


        pictures = new ArrayList<Integer>();
        pictures.add(R.drawable.paris);
        pictures.add(R.drawable.london);
        pictures.add(R.drawable.nackotand);
        MyHistoryListView adapter = new MyHistoryListView(getActivity(),pictures);

        historyListView.setAdapter(adapter);

        return view;
    }
}