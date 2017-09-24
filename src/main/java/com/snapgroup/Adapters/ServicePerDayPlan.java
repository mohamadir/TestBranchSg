package com.snapgroup.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 30/08/17.
 */

public class ServicePerDayPlan  extends BaseAdapter
{

    private Activity context_1;
    ArrayList<String> nameService;
    ArrayList<Integer> serviceImage;
    public ServicePerDayPlan(Activity context,ArrayList<String> nameService,ArrayList<Integer> serviceImage)
    {
        this.nameService = nameService;
        this.serviceImage = serviceImage;
        context_1 = context;

    }

    @Override
    public int getCount() {
        return nameService.size();
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

        ServicePerDayPlan.ServicePerDay servicePerDay = null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.plan_service_per_day, null);
            servicePerDay = new ServicePerDayPlan.ServicePerDay();
            servicePerDay.servicePerDayDescription = (TextView) convertView.findViewById(R.id.serviceTodayTextView) ;
            servicePerDay.serviceImage = (ImageView) convertView.findViewById(R.id.serviceImage) ;


            convertView.setTag(servicePerDay);
        }
        servicePerDay.servicePerDayDescription.setText(nameService.get(position));
        Picasso.with(context_1).load(serviceImage.get(position)).into(servicePerDay.serviceImage);



        return convertView;

    }


    public class ServicePerDay
    {
        public TextView servicePerDayDescription;
        public ImageView serviceImage;

        public ServicePerDay() {
        }

        public ImageView getServiceImage() {
            return serviceImage;
        }

        public void setServiceImage(ImageView serviceImage) {
            this.serviceImage = serviceImage;
        }

        public TextView getServicePerDayDescription() {
            return servicePerDayDescription;
        }

        public void setServicePerDayDescription(TextView servicePerDayDescription) {
            this.servicePerDayDescription = servicePerDayDescription;
        }

        /*public HotelService(String id, String nameHotel, String location, int lat, int lon) {
            this.id = id;
            this.nameHotel = nameHotel;
            this.location = location;
            this.lat = lat;
            this.lon = lon;
        }*/


    }
}