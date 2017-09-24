package com.snapgroup.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.snapgroup.R;

import java.util.ArrayList;

/**
 * Created by root on 13/08/17.
 */

public class ListAdapterTourGuideService extends BaseAdapter

{


    private Activity context_1;
    ArrayList<String> namehotel;
    public ListAdapterTourGuideService(Activity context,ArrayList<String> nameFLights)
    {
        this.namehotel = namehotel;
        context_1 = context;

    }

    @Override
    public int getCount() {
        return 2;
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

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Log.i("ASdsadsad", "asdasd");

        TourGuide flightService = null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.tour_guide_service_info, null);
            flightService = new TourGuide();
            convertView.setTag(flightService);
        }



        return convertView;

    }


    public class TourGuide
    {
        public String dateStart,dateEnd;
        public String location;
        public int lat, lon;


        public TourGuide() {
        }

        /*public HotelService(String id, String nameHotel, String location, int lat, int lon) {
            this.id = id;
            this.nameHotel = nameHotel;
            this.location = location;
            this.lat = lat;
            this.lon = lon;
        }*/

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getLon() {
            return lon;
        }

        public void setLon(int lon) {
            this.lon = lon;
        }
    }
}

