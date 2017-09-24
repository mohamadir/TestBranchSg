package com.snapgroup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.R;

/**
 * Created by snapgroup3 on 11/07/17.
 */

public class GroupLIstAdapter extends  ArrayAdapter<String> {

    private final Activity context;
    private final String[] titleArray;
    private final String[] reviewArray;
    private final String[] ratingArray;
    private final Integer[] imageId;
    private final String[] destinationArray;
    private final String[] descriptionArray;
    private final String[] strating_at_array;
    private final String[] trip_duration;
    private final String[] member_count_array;
    private final String[][] services;
    private final String[] time_left_array;
    private final String[] price_array;


    public GroupLIstAdapter(Activity context, String[] titleArray, String[] reviewArray, String[] ratingArray, Integer[] imageId, String[] destinationArray, String[] descriptionArray,
                            String[] strating_at_array, String[] trip_duration, String[] member_count_array, String[][] services, String[] time_left_array, String[] price_array) {
        super(context, R.layout.newgroupitemdetails, titleArray);
        this.context = context;
        this.titleArray = titleArray;
        this.reviewArray = reviewArray;
        this.ratingArray = ratingArray;
        this.imageId = imageId;
        this.destinationArray = destinationArray;
        this.descriptionArray = descriptionArray;
        this.strating_at_array = strating_at_array;
        this.trip_duration = trip_duration;
        this.member_count_array = member_count_array;
        this.services = services;
        this.time_left_array = time_left_array;
        this.price_array = price_array;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.newgroupitemdetails, null, true);

        TextView groupName = (TextView) rowView.findViewById(R.id.group_name);
       // TextView review = (TextView) rowView.findViewById(R.id.review);
        ImageView image = (ImageView) rowView.findViewById(R.id.imgParis);
      //  TextView destination = (TextView) rowView.findViewById(R.id.destination);
        TextView description = (TextView) rowView.findViewById(R.id.description);
       // TextView starting_at = (TextView) rowView.findViewById(R.id.starting_at);
        TextView time_left = (TextView) rowView.findViewById(R.id.time_left);
      //  TextView member = (TextView) rowView.findViewById(R.id.member_count);
      //  TextView price = (TextView) rowView.findViewById(R.id.price);
        groupName.setText(titleArray[position]);
      //  review.setText(reviewArray[position]);
        image.setImageResource(imageId[position]);
       // destination.setText(destinationArray[position]);
        description.setText(descriptionArray[position]);
       // starting_at.setText(strating_at_array[position]);
        time_left.setText(time_left_array[position]);
       // member.setText(member_count_array[position]);
       // price.setText(price_array[position]);
        return rowView;
    }

}
