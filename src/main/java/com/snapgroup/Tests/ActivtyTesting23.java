package com.snapgroup.Tests;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.snapgroup.Activities.JoinGroupActivity;
import com.snapgroup.Adapters.VoteLIstViewAdapter;
import com.snapgroup.R;

import java.util.ArrayList;

public class ActivtyTesting23 extends AppCompatActivity {

    public ArrayList<String> selcet_activty, offers_list, when_list,  votes_list,  votes_more_list;
    public ListView listDialog,listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_testing23);
         listView = (ListView)findViewById(R.id.listView12);

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


        selcet_activty = new ArrayList<String>();
        selcet_activty.add("asdasd");
        selcet_activty.add("asdasd");
        selcet_activty.add("asdasd");
        selcet_activty.add("asdasd");
        selcet_activty.add("asdasd");
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







        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text12, my_array);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                View joinDialog= LayoutInflater.from(ActivtyTesting23.this).inflate(R.layout.dialog_vote,null);


                AlertDialog.Builder builder = new AlertDialog.Builder(ActivtyTesting23.this);
                builder.setView(joinDialog);

                listDialog = (ListView) joinDialog.findViewById(R.id.listDialog);
                VoteLIstViewAdapter adapter1 = new VoteLIstViewAdapter(ActivtyTesting23.this,selcet_activty,offers_list,when_list,votes_list,votes_more_list);
                listDialog.setAdapter(adapter1);

                final AlertDialog dialog4 = builder.create();
                dialog4.show();



            }
        });


// custom dialog




        //(Activity context_1, ArrayList<String> selcet_activty, ArrayList<String> offers_list, ArrayList<String> when_list, ArrayList<String> votes_list, ArrayList<String> votes_more_list)


    }
}
