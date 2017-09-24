package com.snapgroup.planActivityy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.R;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by DAT on 9/1/2015.
 */
public class FragmentChild extends Fragment {
    String childname,placeString,date_hourString,bigDescriptionString;
    ExpandableTextView bigdescription;
    Integer picture;
    TextView textViewChildName,placetext,date_hour;
    ImageView newDetails_groupIv;

    EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        Bundle bundle = getArguments();

        childname = bundle.getString("data");
        picture = bundle.getInt("data2");
        //placeString,date_hourString,bigDescriptionString;
        placeString = bundle.getString("data3");
        date_hourString = bundle.getString("data4");
        bigDescriptionString = bundle.getString("data5");

        getIDs(view);
        setEvents();

        return view;
    }

    private void getIDs(View view) {

        //placetext,date_hour,bigdescription;
        textViewChildName = (TextView) view.findViewById(R.id.textViewChild);
        placetext = (TextView) view.findViewById(R.id.placeTextView);
        date_hour = (TextView) view.findViewById(R.id.dateHourTextView);
        bigdescription = (ExpandableTextView) view.findViewById(R.id.newDetails_groupDiscriptionTv);
        newDetails_groupIv = (ImageView) view.findViewById(R.id.newDetails_groupIv);


        bigdescription.setText(bigDescriptionString);
        bigdescription.setAnimationDuration(400L);
        final Button expandBt=(Button)view.findViewById(R.id.expand_discriptionTv);
        expandBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (bigdescription.isExpanded())
                {
                    bigdescription.collapse();
                    expandBt.setBackgroundResource(R.drawable.splitline);
                }
                else
                {
                    bigdescription.expand();
                    expandBt.setBackgroundResource(R.drawable.spliteline_up);

                }
            }
        });




        newDetails_groupIv.setImageResource(picture);

        textViewChildName.setText(childname);
        placetext.setText(placeString);
        date_hour.setText(date_hourString);
       /* editText = (EditText) view.findViewById(R.id.editText);
        editText.setText("");*/
    }

    private void setEvents() {

    }
}
