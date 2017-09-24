package com.snapgroup.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.snapgroup.Adapters.BroadCastItemAdapter;
import com.snapgroup.Adapters.ItemsAdapter;
import com.snapgroup.Classes.HoursService;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DaysPlanListBroadcastActivity extends AppCompatActivity {
    private String[] list1 = { "Icon", "Icon Creator", "Image", "Image Creator","Icon", "Icon Creator", "Image", "Image Creator","Icon", "Icon Creator", "Image", "Image Creator","Icon", "Icon Creator", "Image", "Image Creator" };
    private String[] list21 = { "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan" };
    private String[] list22 = { "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan" };
    private String[] list23 = { "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan" };
    private String[] list24 = { "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan", "Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan","Abu shokri Resturant", "Israel Musiem", "Swimming pool Navi Elan" };
    public  ArrayList<HoursService> hoursBroadCastArray;
    public  ArrayList<String> hoursString;
    private ListView lister1;
    private ListView lister2;
    int save = -1;
    BroadCastItemAdapter broadCastAdapter;
    ItemsAdapter itemsAdapter;
    public String dayNumber="";
    public static String currentDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_plan_list_broadcast);
        lister1 = (ListView) findViewById(R.id.ListView1);
        lister2 = (ListView) findViewById(R.id.ListView2);
        getHoursByDate();
        lister1.setDivider(null);
        currentDate=getIntent().getExtras().getString("date");
        dayNumber=getIntent().getExtras().getString("dayNumber");
        SharedPreferences settings=this.getSharedPreferences("current_date",MODE_PRIVATE);
        String day = settings.getString("current_date","Day");

        lister1.setDividerHeight(0);
        lister2.setDivider(null);
        lister2.setDividerHeight(0);
        hoursBroadCastArray=new ArrayList<HoursService>();

        lister1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View arg1,
                                    int positon, long arg3) {

                parent.getChildAt(positon).setBackgroundColor(
                        Color.parseColor("#A9BCF5"));

                if (save != -1 && save != positon) {
                    parent.getChildAt(save).setBackgroundColor(
                            Color.parseColor("#FFFFFF"));
                }

                save = positon;

                String[] lister = null;
                switch (positon) {
                    case 0:
                        lister = list21;
                        break;
                    case 1:
                        lister = list22;
                        break;
                    case 2:
                        lister = list23;
                        break;
                    case 3:
                        lister = list24;
                        break;
                    default:
                        lister = list21;
                }
                lister2.setAdapter(broadCastAdapter);
            }
        });
    }
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(null);
        actionBar.setTitle(null);
        actionBar.setBackgroundDrawable(null);

        /*if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.logo2);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
    }
    private void getHoursByDate() {
        String url = "http://172.104.150.56/api/groups/getdayhours/51";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       Log.i("getHoursByDate",response.toString());
                        JSONArray hours=response;
                        for(int i=0;i<hours.length();i++)
                        {
                        try {
                                hoursBroadCastArray.add(new HoursService(hours.getJSONObject(i).getString("start_hour"),
                                        hours.getJSONObject(i).getString("end_hour"),
                                        hours.getJSONObject(i).getString("lat"),
                                        hours.getJSONObject(i).getString("long"),
                                        hours.getJSONObject(i).getString("radius"),
                                        currentDate));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        itemsAdapter = new ItemsAdapter(DaysPlanListBroadcastActivity.this,
                                R.layout.list, list1,hoursBroadCastArray);
                        broadCastAdapter = new BroadCastItemAdapter(DaysPlanListBroadcastActivity.this,
                                R.layout.list, list1,hoursBroadCastArray);
                        lister1.setAdapter(itemsAdapter);
                        lister2.setAdapter(broadCastAdapter);

                        Log.i("getHoursByDate",hoursBroadCastArray.size()+"");


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("getHoursByDate",error.networkResponse.statusCode+"");

                    }

                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();

                    params2.put("date",currentDate);

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(DaysPlanListBroadcastActivity.this).addToRequestQueue(jsObjRequest);

    }



    public void getActivitiesRequest(final double lat, final double lon, final double radius){
        String url = "http://172.104.150.56/api/groups/get_services_in_radius";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray restauransArray = null;
                        JSONArray plcaesArray = null;
                        JSONArray tourGuideArray = null;
                        try {
                            restauransArray = response.getJSONObject(0).getJSONArray("restaurants");
                            for(int i=0;i<restauransArray.length()-1;i++)
                            {
                                double laty= Double.parseDouble(restauransArray.getJSONObject(i).getString("lat").toString());
                                double longy= Double.parseDouble(restauransArray.getJSONObject(i).getString("long").toString());


                            }
                            plcaesArray = response.getJSONObject(0).getJSONArray("places");
                            for(int i=0;i<plcaesArray.length()-1;i++)
                            {
                                double laty= Double.parseDouble(plcaesArray.getJSONObject(i).getString("lat").toString());
                                double longy= Double.parseDouble(plcaesArray.getJSONObject(i).getString("long").toString());


                            }
                            Log.i("im-before-catch","okkk2");
                            tourGuideArray = response.getJSONObject(0).getJSONArray("tourguides");
                            for(int i=0;i<tourGuideArray.length()-1;i++)
                            {
                                double laty= Double.parseDouble(tourGuideArray.getJSONObject(i).getString("lat").toString());
                                double longy= Double.parseDouble(tourGuideArray.getJSONObject(i).getString("long").toString());

                            }
                            Log.i("im-before-catch","okkk3");
                        } catch (JSONException e) {
                            Log.i("errors",e.getMessage().toString());
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1","error ");
                    }

                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("lat", ""+lat);
                params2.put("long", ""+lon);
                params2.put("radius", ""+radius);
                return new JSONObject(params2).toString().getBytes();
            }

        };
        MySingleton.getInstance(DaysPlanListBroadcastActivity.this).addToRequestQueue(jsObjRequest);

    }
}
