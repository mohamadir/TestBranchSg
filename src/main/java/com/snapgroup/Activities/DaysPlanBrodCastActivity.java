package com.snapgroup.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.Night;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DaysPlanBrodCastActivity extends AppCompatActivity {
  public static   ArrayList<Night> nightsArray = new ArrayList<Night>();
    ListView daysLv;
    String[] dates;
    String lat,lon;
   public  String dayNumber="";
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_plan_brod_cast);
        getNightsRequest();
        daysLv=(ListView)findViewById(R.id.daysListView);
        datesLvListener();

    }

    private void datesLvListener() {
        daysLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String city=nightsArray.get(i).cit;
                String country=nightsArray.get(i).country;
                Log.i("city-country",city+","+country);
                dayNumber=""+(i+1);
                goToMap(i,city,country);

            }
        });
    }

    private void goToMap(int index, String city, String country) {
        final int index2=index;
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=+"+city+","+country+"&key= AIzaSyBmZpEBI_InkDV1t3-FZ7myRSZIfVafs10";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String lat=response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat").toString();
                            String lon=response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng").toString();
                            Log.i("goToMap",lat+","+lon+","+dates[index2].split("[(]")[1].split("[)]")[0]);
                            String date=dates[index2].split("[(]")[1].split("[)]")[0];
                            Intent intent=new Intent(DaysPlanBrodCastActivity.this,MapRadiusActivity.class);
                            intent.putExtra("Date",date);
                            intent.putExtra("lat",lat);
                            intent.putExtra("lon",lon);
                            intent.putExtra("dayNumber",dayNumber);
                            SharedPreferences.Editor editor=getSharedPreferences("current_date",MODE_PRIVATE).edit();
                            editor.putString("current_date","Day "+dayNumber);
                            editor.commit();
                            Log.i("MyDayNumber",dayNumber);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1","error ");

                    }

                });


        MySingleton.getInstance(DaysPlanBrodCastActivity.this).addToRequestQueue(jsObjRequest);

    }

    public void getNightsRequest() {

        String url = "http://172.104.150.56/api/groups/days/nights/51";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray nightsJsonArray=response.getJSONArray("nights");
                            JSONArray daysJsonArray=response.getJSONArray("days");
                            for(int i=0;i<nightsJsonArray.length();i++)
                            {
                                nightsArray.add(new Night(nightsJsonArray.getJSONObject(i).get("date").toString(),nightsJsonArray.getJSONObject(i).get("city").toString(),nightsJsonArray.getJSONObject(i).get("country").toString()));
                            }
                            dates=new String[daysJsonArray.length()];
                            for(int i=0;i<daysJsonArray.length();i++){
                                dates[i]="Day "+(i+1)+"("+daysJsonArray.getJSONObject(i).getString("date").toString()+")";
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DaysPlanBrodCastActivity.this, android.R.layout.simple_list_item_1, dates); //selected item will look like a spinner set from XML
                            daysLv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MySingleton.getInstance(DaysPlanBrodCastActivity.this).addToRequestQueue(jsObjRequest);


    }
}
