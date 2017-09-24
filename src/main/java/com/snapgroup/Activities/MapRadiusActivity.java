package com.snapgroup.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.snapgroup.Adapters.GroupLIstAdapter2;
import com.snapgroup.Classes.ActivitiesLocation;
import com.snapgroup.Classes.HoursService;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.Models.Night;
import com.snapgroup.R;
import com.twotoasters.jazzylistview.JazzyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapRadiusActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static ArrayList<ActivitiesLocation> locationsArray;
    public String DateOfDay;
    public static ArrayList<Night>  nightsArray;
    public static int radiusNumber=0;
   public static String lat,ln,date;
    public static int groupId=51;
    Button confirmBt;
    AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    boolean expend=false;
    public String dayNumber="";
    public static ArrayList<HoursService> hoursServiceArray;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_radius);
        locationsArray = new ArrayList<ActivitiesLocation>();
        hoursServiceArray = new ArrayList<HoursService>();
        nightsArray=new ArrayList<Night>();
        initToolbar();
        getAllPointsAndMark();
        appBarLayout=(AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(false);
        appBarLayout.setOutlineProvider(null);
        initFab();
        confirmBt=(Button)findViewById(R.id.confirmBt);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("hihihi","pressed");
                String data=getHoursArrayData();
                Log.i("hihihi","pressed"+"   "+data);
                postAllPointsRequest(data);
            }
        });
        lat=getIntent().getStringExtra("lat").toString();
        ln=getIntent().getStringExtra("lon").toString();
        date=getIntent().getStringExtra("Date").toString();
        dayNumber=getIntent().getStringExtra("dayNumber").toString();
        setAllPointsIfExist();
        getCurrentDay();
        getNightsRequest();
        setFragment();
      //  LinearLayout ln = (LinearLayout) findViewById(R.id.mapRadiusLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }
    private void initFab() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if(!expend) {
                    //  Snackbar.make(mContent, "Collapse down", Snackbar.LENGTH_SHORT).show();
                    appBarLayout.setExpanded(true);
                    expend=true;
                    findViewById(R.id.fab).setBackgroundResource(R.drawable.strip_gold_line_uposh);
                }
                else{
                    //  Snackbar.make(mContent, "Collapse Up", Snackbar.LENGTH_SHORT).show();

                    appBarLayout.setExpanded(false);
                    expend=false;;
                    findViewById(R.id.fab).setBackgroundResource(R.drawable.strip_gold_line_down);

                }

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
    private void getAllPointsAndMark() {

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.POST,"http://172.104.150.56/api/groups/getdayhours/51",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("getAllPointsAndMark", response.toString());
                        for(int i=0;i<response.length();i++)
                        {
                            try {
                                putCircle(response.getJSONObject(i).getString("lat"),response.getJSONObject(i).getString("long"),response.getJSONObject(i).getString("radius"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("getAllPointsAndMark","im in catch ");

                            };
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("getAllPointsAndMark", String.valueOf(error.networkResponse.statusCode));

                    }
                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("date",date);
                return new JSONObject(params2).toString().getBytes();
            }

        };

        MySingleton.getInstance(MapRadiusActivity.this).addToRequestQueue(jsObjRequest);


    }

    private void putCircle(String lat, String lon, String radius) {
        int d = 500;
        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        p.setColor(Color.parseColor("#7a7a7a"));
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        c.drawCircle(d/2, d/2, d/2, p);
        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);
        mMap.addGroundOverlay(new GroundOverlayOptions().
                image(bmD).
                position(new LatLng( Double.parseDouble(lat), Double.parseDouble(lon)),(int)(Double.parseDouble(radius)*1000) *2,(int)(Double.parseDouble(radius)*1000)*2).
                transparency(0.6f));
        Log.i("putCircle",Double.parseDouble(lat)+","+ Double.parseDouble(lon)+","+((int)(Double.parseDouble(radius)*1000) *2)+"");
        getActivitiesRequest(Double.parseDouble(lat), Double.parseDouble(lon),(int)Double.parseDouble(radius));

    }

    private void postAllPointsRequest(final String data) {

        final String data2=data;
        Log.i("postAllPointsRequest",data);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,"http://172.104.150.56/api/groups/confirmlocations/51",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("postAllPointsRequest",response.toString());
                        Intent i=new Intent(MapRadiusActivity.this,DaysPlanListBroadcastActivity.class);
                        i.putExtra("date",date);
                        i.putExtra("dayNumber",dayNumber);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent i=new Intent(MapRadiusActivity.this,DaysPlanListBroadcastActivity.class);
                        i.putExtra("date",date);
                        startActivity(i);
                        Log.i("postAllPointsRequest", String.valueOf(error.networkResponse.statusCode));

                    }
                }){
            @Override
            public byte[] getBody() {
                 HashMap<String, String> params2 = new HashMap<String, String>();
                    params2.put("hours",getHoursArrayData().toString());
                return new JSONObject(params2).toString().getBytes();
            }

        };

        MySingleton.getInstance(MapRadiusActivity.this).addToRequestQueue(jsObjRequest);


    }

    private String getHoursArrayData() {
        String data="[";

        for (int i = 0; i < hoursServiceArray.size(); i++)
        {
            String[] sHour=hoursServiceArray.get(i).getStartHour().split(":");
            String[] eHour=hoursServiceArray.get(i).getEndHour().split(":");
            String sHourString="";
            String eHourString="";

            for(int r=0;r<sHour.length;r++)
            {
                sHourString+=sHour[r];
                eHourString+=eHour[r];
            }

            if(i+1<hoursServiceArray.size())
            {
                Log.i("getHoursArrayData",hoursServiceArray.get(i).getLat()+","+hoursServiceArray.get(i).getLon());
                data += "{ \"lat\": \"" + hoursServiceArray.get(i).getLat()
                        + "\"," + "\"long\": \"" + hoursServiceArray.get(i).getLon()
                        + "\","+"\"start_hour\": \"" + sHourString
                        + "\","+"\"end_hour\": \"" + eHourString
                        +"\"," + "\"radius\": \"" + hoursServiceArray.get(i).getRadius()
                        + "\"," + "\"date\": \"" + hoursServiceArray.get(i).getDate()+ "\"}";
                data += ",";

            }
            else {

                data += "{ \"lat\": \"" + hoursServiceArray.get(i).getLat()
                        + "\"," + "\"long\": \"" + hoursServiceArray.get(i).getLon()
                        + "\","+"\"start_hour\": \"" + sHourString
                        + "\","+"\"end_hour\": \"" + eHourString
                        +"\"," + "\"radius\": \"" + hoursServiceArray.get(i).getRadius()
                        + "\"," + "\"date\": \"" + hoursServiceArray.get(i).getDate()+ "\"}";
            }


        }
        data+="]";

        return data;

    }

    private void setAllPointsIfExist() {


    }

    private void setFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.radiusMap);
        mapFragment.getMapAsync(this);
    }

    private void setNightsMarkers() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(lat),Double.parseDouble(ln)))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.night_marker)));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setNightsMarkers();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                final LatLng latLng2=new LatLng(latLng.latitude,latLng.longitude);
                hoursDialog(latLng2);
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat),Double.parseDouble(ln)),9));
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap = googleMap;
        MapStyleOptions style=MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json);
        mMap.setMapStyle(style);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;

        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.addMarker(new MarkerOptions()
                        .position(marker.getPosition())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
                mMap.clear();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),13));
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude+0.0234,marker.getPosition().longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude+0.0234))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude-0.0234,marker.getPosition().longitude+0.0234))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
                mMap.addMarker(new MarkerOptions()
                        .position(marker.getPosition())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
                mMap.addMarker(new MarkerOptions()
                        .position(marker.getPosition())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
                return true;
            }
        });
        mMap.setMyLocationEnabled(true);
      // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.0853,34.7818),8));

    }

    private void hoursDialog(final LatLng latLng2) {
        final HoursService hourService= new HoursService();

        View hoursView= LayoutInflater.from(MapRadiusActivity.this).inflate(R.layout.map_choose_hours_dialog,null);
        final Spinner hoursFromsp=(Spinner) hoursView.findViewById(R.id.hoursFromSp);
        final Spinner hoursToSp=(Spinner) hoursView.findViewById(R.id.hoursToSp);
        AlertDialog.Builder builder = new AlertDialog.Builder(MapRadiusActivity.this);
        builder.setView(hoursView);
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(hoursFromsp.getSelectedItem().equals("From")||hoursToSp.getSelectedItem().equals("To"))
                {
                    Toast.makeText(MapRadiusActivity.this, "you have to select correct hours ! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    radiusNumber = 0;
                    hourService.setStartHour(hoursFromsp.getSelectedItem().toString());
                    hourService.setEndHour(hoursToSp.getSelectedItem().toString());
                    hourService.setDate(date);
                    Log.i("hoursDialog",latLng2.latitude+","+latLng2.longitude);
                    hourService.setLat(latLng2.latitude+"");
                    hourService.setLon(latLng2.longitude+"");
                    hoursServiceArray.add(hourService);
                    makeRadiusDialog(latLng2,hourService);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void makeRadiusDialog(LatLng latLng, final HoursService hourService) {
         final LatLng latLng2 = latLng;
                final ActivitiesLocation location=new ActivitiesLocation(latLng.latitude,latLng.longitude);
                final double[] locationRadius = new double[1];
              /*  mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
             */   View radiusView= LayoutInflater.from(MapRadiusActivity.this).inflate(R.layout.add_circle_map_dialog,null);
                final EditText radiusEt = (EditText) radiusView.findViewById(R.id.radiusEt);
                radiusEt.setVisibility(View.GONE);
                final SeekBar seekBar = (SeekBar) radiusView.findViewById(R.id.mapSeekBar);
                final TextView radiusTv = (TextView) radiusView.findViewById(R.id.radiusTextView);
                final LinearLayout radius_layout = (LinearLayout) radiusView.findViewById(R.id.radius_layout);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        radiusNumber=seekBar.getProgress();
                        radiusTv.setText(""+(double)((double)seekBar.getProgress()/1000));
                        locationRadius[0] =(double)((double)seekBar.getProgress()/1000);
                        location.setRadius(locationRadius[0]);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(MapRadiusActivity.this);
                builder.setMessage("Enter Radius");
                builder.setView(radiusView);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String  validateCodeString =radiusEt.getText().toString();
                        int radiusM=0;
                        locationsArray.add(location);
                        if(radiusTv.getText()!=null&&!radiusTv.getText().equals(""))
                            radiusM= radiusNumber;
                        Toast.makeText(MapRadiusActivity.this, "Radius is:"+radiusM, Toast.LENGTH_SHORT).show();
                        LatLng latLng = latLng2;
                        int d = 500;
                        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(bm);
                        Paint p = new Paint();
                        p.setColor(getResources().getColor(R.color.orange));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        c.drawCircle(d/2, d/2, d/2, p);

                        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);
                        mMap.addGroundOverlay(new GroundOverlayOptions().
                                image(bmD).
                                position(latLng,radiusM*2,radiusM*2).
                                transparency(0.4f));
                        Toast.makeText(MapRadiusActivity.this, locationsArray.size()+"", Toast.LENGTH_SHORT).show();
                        getActivitiesRequest(location.lat,location.lon,location.radius);
                        radiusNumber=0;
                        hourService.setRadius(""+(double)((double)seekBar.getProgress()/1000));
                        hourService.setLat(""+latLng2.latitude);
                        hourService.setLon(""+latLng2.longitude);
                        Log.i("HourService","---------------------");
                        for(int j=0;j<hoursServiceArray.size();j++)
                        {
                            Log.i("HourService",hoursServiceArray.get(j).getDate()+","+
                                    hoursServiceArray.get(j).getStartHour()+","+
                                    hoursServiceArray.get(j).getEndHour()+","+
                                    hoursServiceArray.get(j).getLat()+","+
                                    hoursServiceArray.get(j).getLon()+","+
                                    hoursServiceArray.get(j).getRadius());

                        }
                    }
                });

                builder.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
    }

    private void printLocationArray() {
        for(int i=0;i<locationsArray.size();i++){
            Log.i("LocationArray",locationsArray.get(i).lat+","+locationsArray.get(i).lon+","+locationsArray.get(i).radius);
        }
    }




    public String makeArrayRequestString()
    {
        String data="[";

        for (int i = 0; i < locationsArray.size(); i++)
        {
            if(i+1<locationsArray.size())
            {

                data += "{ \"lat\": \"" + locationsArray.get(i).lat + "\"," + "\"long\": \"" + locationsArray.get(i).lon + "\"," + "\"radius\": \"" + locationsArray.get(i).radius + "\"}";
                    data += ",";

            }
            else {
                data += "{ \"lat\": \"" + locationsArray.get(i).lat + "\"," + "\"long\": \"" + locationsArray.get(i).lon + "\"," + "\"radius\": \"" + locationsArray.get(i).radius + "\"}";

            }


        }
        data+="]";
        return data;
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
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(laty,longy))
                                        .title(restauransArray.getJSONObject(i).getString("name").toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.rest_marker)));


                            }
                            plcaesArray = response.getJSONObject(0).getJSONArray("places");
                            for(int i=0;i<plcaesArray.length()-1;i++)
                            {
                                double laty= Double.parseDouble(plcaesArray.getJSONObject(i).getString("lat").toString());
                                double longy= Double.parseDouble(plcaesArray.getJSONObject(i).getString("long").toString());
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(laty,longy))
                                        .title(plcaesArray.getJSONObject(i).getString("name").toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.place_marker)));


                            }
                            Log.i("im-before-catch","okkk2");
                            tourGuideArray = response.getJSONObject(0).getJSONArray("tourguides");
                            for(int i=0;i<tourGuideArray.length()-1;i++)
                            {
                                double laty= Double.parseDouble(tourGuideArray.getJSONObject(i).getString("lat").toString());
                                double longy= Double.parseDouble(tourGuideArray.getJSONObject(i).getString("long").toString());
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(laty,longy))
                                        .title(tourGuideArray.getJSONObject(i).getString("first_name").toString())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tourguide_marker)));


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
        MySingleton.getInstance(MapRadiusActivity.this).addToRequestQueue(jsObjRequest);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void getCurrentDay() {
        SharedPreferences userLogInfo=this.getSharedPreferences("DateInMap",MODE_PRIVATE);
        DateOfDay= userLogInfo.getString("date","");
    }

    public void getNightsRequest() {
        nightsArray=new ArrayList<Night>();

        String url = "http://172.104.150.56/api/groups/days/nights/51";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray nightsJsonArray=response.getJSONArray("nights");
                            for(int i=0;i<nightsJsonArray.length();i++)
                            {
                                nightsArray.add(new Night(nightsJsonArray.getJSONObject(i).get("date").toString(),nightsJsonArray.getJSONObject(i).get("city").toString(),nightsJsonArray.getJSONObject(i).get("country").toString()));
                            }

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


        MySingleton.getInstance(MapRadiusActivity.this).addToRequestQueue(jsObjRequest);


    }
}
