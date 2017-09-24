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

public class ListAdapterTransportService extends BaseAdapter {


    private Activity context_1;
    ArrayList<String> namehotel;
    public ListAdapterTransportService(Activity context,ArrayList<String> nameFLights)
    {
        this.namehotel = namehotel;
        context_1 = context;

    }

    public int getCount()
    {
        return 2;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }



    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Log.i("ASdsadsad", "asdasd");

        TransportService transportService = null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.transport_service_info, null);
            transportService = new TransportService();
            convertView.setTag(transportService);
        }



        return convertView;

    }


    public class TransportService
    {
        public String dateStart,dateEnd;
        public String location;
        public int lat, lon;


        public TransportService() {
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

