package com.snapgroup.Activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.snapgroup.Adapters.ListAdapterFlightService;
import com.snapgroup.Adapters.ListAdapterHotelService;
import com.snapgroup.Adapters.ListAdapterTourGuideService;
import com.snapgroup.Adapters.ListAdapterTransportService;
import com.snapgroup.Fragments.PlanFragment;
import com.snapgroup.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class NewGroupServicesActivity extends AppCompatActivity {

    TextView selectAll, resturant, hotel, transport, tour_guide, site_places, activity, flights, yesText, noText, nextBt,hotelTextViewChange;

    public Button hotelButtonChange;
    public  String name = "";
    boolean[] array = new boolean[7];
    String[] arrayServices = {"flifhts","hotels","transport","tourGuide","sitesAndplaces","resturant","activity"};

    public ArrayList<String> nameHotel;


    private ArrayAdapter<String> adapterAuto;

    public void trueFunc(boolean[] array) {
        for (int i = 0; i < 7; i++) {
            array[i] = true;
        }

    }

    public void falseFunc(boolean[] array) {
        for (int i = 0; i < 7; i++) {
            array[i] = false;
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_services);
        nameHotel = new ArrayList<String>();
        yesText = (TextView) findViewById(R.id.yesTextView);
        hotelButtonChange=(Button)findViewById(R.id.hotelButtonChange);
        hotelTextViewChange = (TextView)findViewById(R.id.hotelTextViewChange);
        // checkedHotel21.setVisibility(View.INVISIBLE);
        flights = (TextView) findViewById(R.id.flightsTextView);




        noText = (TextView) findViewById(R.id.noTextView);
        selectAll = (TextView) findViewById(R.id.selectAllTv);
        resturant = (TextView) findViewById(R.id.restarurantsTextview);
        hotel = (TextView) findViewById(R.id.hotelTextView);
        //printSharedPreferences();
        transport = (TextView) findViewById(R.id.transportTextView);
        tour_guide = (TextView) findViewById(R.id.tour_GuideTextView);
        site_places = (TextView) findViewById(R.id.sites_placesTextView);
        activity = (TextView) findViewById(R.id.activtyTextView);
        nextBt = (TextView) findViewById(R.id.services_next_tv);
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
                editor.putString("hotelsService",array[1]+"");
                editor.putString("flightsService",array[0]+"");
                editor.putString("transportService",array[2]+"");
                editor.putString("resturantService",array[5]+"");
                editor.putString("activityService",array[6]+"");
                editor.putString("tourGuideService",array[3]+"");
                editor.putString("placesService",array[4]+"");
                editor.commit();

                    for (int i = 0; i < 7; i++)
                    {
                        if(array[i]==true)
                        {

                            Log.i("array", "array[" + i + "]" + "=" + arrayServices[i]);
                        }

                    }

                checkSrevic();

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        falseFunc(array);
        selectAll.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (selectAll.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    trueFunc(array);
                    selectAll.setTextColor(Color.parseColor("#fc8130"));
                    flights.setTextColor(Color.parseColor("#fc8130"));
                    hotel.setTextColor(Color.parseColor("#fc8130"));
                    resturant.setTextColor(Color.parseColor("#fc8130"));
                    tour_guide.setTextColor(Color.parseColor("#fc8130"));
                    transport.setTextColor(Color.parseColor("#fc8130"));
                    site_places.setTextColor(Color.parseColor("#fc8130"));
                    activity.setTextColor(Color.parseColor("#fc8130"));
                } else {
                    falseFunc(array);
                    selectAll.setTextColor(Color.parseColor("#009900"));
                    flights.setTextColor(Color.parseColor("#009900"));
                    hotel.setTextColor(Color.parseColor("#009900"));
                    resturant.setTextColor(Color.parseColor("#009900"));
                    tour_guide.setTextColor(Color.parseColor("#009900"));
                    transport.setTextColor(Color.parseColor("#009900"));
                    site_places.setTextColor(Color.parseColor("#009900"));
                    activity.setTextColor(Color.parseColor("#009900"));

                }

            }
        });
        //selectAll.setOnClickListener(new View.OnClickListener() {

        flights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flights.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    array[0] = true;
                    flights.setTextColor(Color.parseColor("#fc8130"));
                } else {
                    flights.setTextColor(Color.parseColor("#009900"));
                    array[0] = false;
                }
            }
        });
        resturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resturant.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    resturant.setTextColor(Color.parseColor("#fc8130"));
                    array[5] = true;
                } else {
                    resturant.setTextColor(Color.parseColor("#009900"));
                    array[5] = false;
                }

            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hotel.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    hotel.setTextColor(Color.parseColor("#fc8130"));
                    array[1] = true;



                } else {
                    hotel.setTextColor(Color.parseColor("#009900"));
                    array[1] = false;


                }
            }
        });
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transport.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    transport.setTextColor(Color.parseColor("#fc8130"));
                    array[2] = true;
                } else {
                    transport.setTextColor(Color.parseColor("#009900"));
                    array[2] = false;
                }
            }
        });
        tour_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tour_guide.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    tour_guide.setTextColor(Color.parseColor("#fc8130"));
                    array[3] = true;
                } else {
                    tour_guide.setTextColor(Color.parseColor("#009900"));
                    array[3] = false;
                }
            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    activity.setTextColor(Color.parseColor("#fc8130"));
                    array[6] = true;
                } else {
                    activity.setTextColor(Color.parseColor("#009900"));
                    array[6] = false;
                }

            }
        });
        site_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (site_places.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    site_places.setTextColor(Color.parseColor("#fc8130"));
                    array[4] = true;

                } else {
                    site_places.setTextColor(Color.parseColor("#009900"));
                    array[4] = false;

                }
            }
        });
        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesText.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    yesText.setTextColor(Color.parseColor("#fc8130"));
                    noText.setTextColor(Color.parseColor("#009900"));
                } else {
                    yesText.setTextColor(Color.parseColor("#009900"));

                }
            }
        });
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noText.getCurrentTextColor() != Color.parseColor("#fc8130")) {
                    noText.setTextColor(Color.parseColor("#fc8130"));
                    yesText.setTextColor(Color.parseColor("#009900"));
                } else {
                    noText.setTextColor(Color.parseColor("#009900"));

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    private void printSharedPreferences() {
        SharedPreferences NewGroupSp = this.getSharedPreferences("NewGroup", MODE_PRIVATE);
        Map<String, ?> allEntries = NewGroupSp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.i("MAPP", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    public void checkSrevic()
    {
        if (array[0]==true){
            Intent i = new Intent(NewGroupServicesActivity.this, NewGroupFlightServiceActivity.class);
            startActivity(i);
            return;

        }
        else
        {
            if (array[1]==true)
            {
                Intent i = new Intent(NewGroupServicesActivity.this, NewGroupHotelsServiceActivity.class);
                startActivity(i);
            }
            else
            {
                if (array[2]==true)
                {
                    Intent i = new Intent(NewGroupServicesActivity.this, NewGroupTransportationActivity.class);
                    startActivity(i);
                }
                else
                {
                    if (array[3]==true)
                    {
                        Intent i = new Intent(NewGroupServicesActivity.this, NewGroupTourGuideActivity.class);
                        startActivity(i);

                    }
                    else
                    {
                        if(array[4]==true)
                        {
                            Intent i = new Intent(NewGroupServicesActivity.this, SignInActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            if ((array[5]==true))
                            {
                                Intent i = new Intent(NewGroupServicesActivity.this, NewGroupSettingsPageAtivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                if (array[6]==true){
                                    Intent i = new Intent(NewGroupServicesActivity.this, NewGroupProfile1Activity.class);
                                    startActivity(i);
                                }
                                else
                                    Toast.makeText(NewGroupServicesActivity.this, "You must to choose service", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }

            }


        }
    }


}