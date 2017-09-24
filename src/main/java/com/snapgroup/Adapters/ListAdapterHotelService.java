package com.snapgroup.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.snapgroup.R;

import java.util.ArrayList;




public class ListAdapterHotelService extends BaseAdapter {

    private Activity context_1;
    ArrayList<String> namehotel,destntion,origin;
    public ArrayList<Boolean> brodcast;

    public boolean clickedbool=false;
    public String name;
    public ListAdapterHotelService(Activity context,ArrayList<String> namehotel,ArrayList<String> destention,ArrayList<String> origin, ArrayList<Boolean> brodcast) {
        this.destntion = destention;
        this.origin = origin;
        this.namehotel = namehotel;
        context_1 = context;
        this.brodcast=brodcast;

    }

    @Override
    public int getCount() {
        return namehotel.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void click()
    {
        clickedbool = true;

    }
    @Override

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Log.i("ASdsadsad", "asdasd");

        HotelService hotelService = null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {
            ProgressDialog progress = new ProgressDialog(context_1);
            progress.setTitle("Please Wait!!");
            progress.setMessage("Wait!!");

            convertView = LayoutInflater.from(context_1).inflate(R.layout.group_item, null);
            hotelService = new HotelService();
            hotelService.nameHotel = (TextView) convertView.findViewById(R.id.group_name_id);
            hotelService.destination = (TextView) convertView.findViewById(R.id.destination);
            hotelService.origin = (TextView) convertView.findViewById(R.id.origin);
            hotelService.broducastButton = (TextView) convertView.findViewById(R.id.broducastButton);
            if (brodcast.get(position)==true){
                progress.show();

                hotelService.broducastButton.setBackground((context_1.getResources().getDrawable(R.drawable.checked)));
                progress.hide();

            }
            else
                {
                if (brodcast.get(position) == false)
                    progress.show();
                    hotelService.broducastButton.setBackground((context_1.getResources().getDrawable(R.drawable.brodcast)));
                    progress.hide();

                }


            convertView.setTag(hotelService);
        }
        hotelService.nameHotel.setText(namehotel.get(position));
        hotelService.destination.setText(destntion.get(position));
        hotelService.origin.setText(origin.get(position));



        return convertView;

    }


    public class HotelService
    {
        public String id;
        public TextView nameHotel;
        public TextView destination,origin,broducastButton;
        public String location;
        public int lat, lon;


        public void setNameHotel(TextView nameHotel) {
            this.nameHotel = nameHotel;
        }

        public HotelService() {
        }

        /*public HotelService(String id, String nameHotel, String location, int lat, int lon) {
            this.id = id;
            this.nameHotel = nameHotel;
            this.location = location;
            this.lat = lat;
            this.lon = lon;
        }*/

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public TextView getNameHotel() {
            return nameHotel;
        }


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
