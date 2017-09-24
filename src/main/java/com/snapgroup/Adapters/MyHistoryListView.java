package com.snapgroup.Adapters;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.snapgroup.Classes.HotelServiceLocation;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 06/09/17.
 */



public class MyHistoryListView extends BaseAdapter {

    private ArrayList<Integer> imagesHistory;
    private Activity context_1;
    public MyHistoryListView(Activity context, ArrayList<Integer> imagesHistory )
    {
        context_1 = context;
        this.imagesHistory = imagesHistory;


    }

    @Override
    public int getCount() {
        return imagesHistory.size();
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
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {


        MyHistoryListView.ViewHolder viewHolder = null;
        // initilize the new view that will convert the simple view

        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.my_history_item, null);
            viewHolder = new MyHistoryListView.ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageHistory);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (MyHistoryListView.ViewHolder) convertView.getTag();

        }
        Picasso.with(context_1).load(imagesHistory.get(position)).into(viewHolder.image);
        //Picasso.with(context_1).load(membersArray.get(i).image).into(viewHolder.profileImage);


        return convertView;

    }


    public class ViewHolder {
        public ImageView image;

    }

}