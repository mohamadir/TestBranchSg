package com.snapgroup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.Classes.HoursService;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by snapgroup2 on 30/08/17.
 */

 public class  ItemsAdapter extends BaseAdapter {
    String[] items;
    Context context;
    ArrayList<HoursService> hoursArray;
    public ItemsAdapter(Context context, int textViewResourceId,
                        String[] items, ArrayList<HoursService> hoursArray) {
        this.context=context;
        this.items = items;
        this.hoursArray=hoursArray;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        TextView hoursTv;
        View view = convertView;
        ImageView image;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list, null);
        }

        hoursTv = (TextView) view.findViewById(R.id.hoursTv);
        String startHour=hoursArray.get(position).getStartHour();
        String endHour=hoursArray.get(position).getEndHour();
        endHour=Character.toString(endHour.charAt(0))+Character.toString(endHour.charAt(1))+":"+Character.toString(endHour.charAt(2))+Character.toString(endHour.charAt(3));
        startHour=Character.toString(startHour.charAt(0))+Character.toString(startHour.charAt(1))+":"+Character.toString(startHour.charAt(2))+Character.toString(startHour.charAt(3));
        hoursTv.setText(startHour+" - "+endHour);
       /* image = (ImageView) view.findViewById(R.id.image);
        mDescription.setText("\nDay \n  "+position);
        Picasso.with(context).load("https://user-images.githubusercontent.com/17565537/29669849-73bc8cac-88ed-11e7-85d8-51155e39487e.png").into(image);
        */
       return view;
    }

    public int getCount() {
        return hoursArray.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
}
