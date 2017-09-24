package com.snapgroup.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.snapgroup.Classes.DayPLan;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
     public  List<DayPLan> dayPlans=new ArrayList<DayPLan>();

    private static final PatternItem DOT = new Dash(20);
    //private static final PatternItem DOT = new Dot();
    private static final int PATTERN_GAP_LENGTH_PX = 10;
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    boolean[] circlesArray;
    List<DayPLan> targets;
    public     static int radiusNumber=0;


    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        targets=new ArrayList<DayPLan>();
        requestJsonArray();
        targets=dayPlans;
        mapFragment.getMapAsync(this);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }

    /*
    * initial the map with a fake points and pollylines (with ormange color)
    * */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        List<LatLng> coordList = new ArrayList<LatLng>();
        circlesArray=new boolean[targets.size()];

        for(int i=0;i<targets.size();i++)
        {
            circlesArray[i]=false;
            String name=targets.get(i).name;
            double lat=targets.get(i).lat;
            double lon=targets.get(i).lon;
            LatLng latlng=new LatLng(lat,lon);
                coordList.add(latlng);

            mMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker_2)));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker) {
                final Marker marker2=marker;
                for(int i=0;i<targets.size();i++){
                    if(distance(marker.getPosition().latitude,marker.getPosition().longitude
                    ,targets.get(i).lat,targets.get(i).lon)==0)
                    {
                        if(circlesArray[i])
                        {

                        }
                        else
                        {

                            View radiusView= LayoutInflater.from(MapActivity.this).inflate(R.layout.add_circle_map_dialog,null);
                            final EditText radiusEt = (EditText) radiusView.findViewById(R.id.radiusEt);
                            radiusEt.setVisibility(View.GONE);
                            final SeekBar seekBar = (SeekBar) radiusView.findViewById(R.id.mapSeekBar);
                            final TextView radiusTv = (TextView) radiusView.findViewById(R.id.radiusTextView);
                            final LinearLayout radius_layout = (LinearLayout) radiusView.findViewById(R.id.radius_layout);
                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                    radiusNumber=seekBar.getProgress();
                                    radiusTv.setText(""+seekBar.getProgress());
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                            builder.setMessage("Enter Radius");
                            builder.setView(radiusView);
                            final int finalI = i;
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String  validateCodeString =radiusEt.getText().toString();
                                    int radiusM=0;
                                    if(radiusTv.getText()!=null&&!radiusTv.getText().equals(""))
                                      radiusM= Integer.parseInt(radiusTv.getText().toString());
                                    Toast.makeText(MapActivity.this, "Radius is:"+radiusM, Toast.LENGTH_SHORT).show();
                                    LatLng latLng = new LatLng(marker2.getPosition().latitude,marker2.getPosition().longitude);
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
                                    circlesArray[finalI]=true;
                                }
                            });

                            builder.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();






                        }
                    }
                }
    /*            int radiusM =50000;
                LatLng latLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
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
*/


                return true;
            }
        });
        int orange = Color.parseColor("#ff6600");

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(coordList));
        polyline1.setEndCap(new RoundCap());
        polyline1.setWidth(15);
        polyline1.setColor(orange);
        polyline1.setJointType(JointType.ROUND);
        polyline1.setPattern(PATTERN_POLYLINE_DOTTED);


// Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1.setTag("A");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.7683,35.2137),7));
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }

//
    public void requestJsonArray(){
        String url = "http://www.mocky.io/v2/596ddd130f00000c032b7fa8/";


        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                                JSONObject mainArray = response.getJSONObject(1);
                                JSONObject groupObject = mainArray.getJSONObject("group");
                                JSONObject planObject = groupObject.getJSONObject("plan");
                                Log.i("ress", "" + planObject.length());
                                for (int j = 1; j < planObject.length(); j++) {
                                   dayPlans.add(new DayPLan(planObject.getJSONObject("" + j + "").getString("name").toString(), toDouble(planObject.getJSONObject("" + j + "").getString("lat")), toDouble(planObject.getJSONObject("" + j + "").getString("long"))));
                                }
                                targets=dayPlans;
                                onMapReady(mMap);

                        }
                         catch (JSONException e) {

                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }

                });

        MySingleton.getInstance(MapActivity.this).addToRequestQueue(jsObjRequest);


    }


    public double toDouble(String str){
        return Double.parseDouble(str);
    }
}
