package com.snapgroup.Classes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Adapters.GroupLIstAdapter2;
import com.snapgroup.Adapters.ListAdapterHotelService;
import com.snapgroup.Adapters.ListAdapterTransportService;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.R;
import com.google.gson.JsonObject;
import com.twotoasters.jazzylistview.JazzyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class NewGroupChooseHotel extends AppCompatActivity {

    public ListView listView;
    public String curntBrodcast="";
    public Spinner nigths_spiner;
    public ArrayList<String> locations_array;
    public Boolean broducst=false;
    public ArrayList<String> nightsSpnierrr;
    private Handler handler;
    public ArrayList<String> locationPlaces;
    private ProgressDialog progress;
    public Button searchButtomByNights;
    public ArrayList<String> nameHotel,destnion,origin,id;
    public ArrayList spinnerArray;
    public  ArrayList<Boolean> brodcast;

    ListAdapterHotelService listAdapterHotelService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_choose_hotel);

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        spinnerArray= new ArrayList<String>();
        locationPlaces= new ArrayList<String>();
        nigths_spiner = (Spinner) findViewById(R.id.nigths_spiner);

        Bundle extras = getIntent().getExtras();

        ArrayList<String> nightsSpnierrr = (ArrayList<String>) extras.getSerializable("array2");
        locations_array = (ArrayList<String>) extras.getSerializable("array");
        Log.i("nmnmnm" , nightsSpnierrr.get(0));


        // ArrayList<String> locationsss = (ArrayList<String>) args.getSerializable("location");


        SharedPreferences NewGroupSp=getBaseContext().getSharedPreferences("NewGroup",MODE_PRIVATE);
        String dates_night = NewGroupSp.getString("dataForHotelRequst","");
        String []arrayEndDate_StartDate =dates_night.split("[}]");
        Log.i("qweqvnbvb" , arrayEndDate_StartDate.toString());
        //String startDate = arrayEndDate_StartDate[0].substring(arrayEndDate_StartDate[0].indexOf("start_date\": \""));
        //Log.i("qqqq",startDate.substring(14,24));



            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nightsSpnierrr); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nigths_spiner.setAdapter(spinnerArrayAdapter);



        listView = (ListView)findViewById(R.id.lisVIewChooseHotel);
        nameHotel = new ArrayList<String>();
        destnion = new ArrayList<String>();
        origin = new ArrayList<String>();
        id = new ArrayList<String>();
        brodcast = new ArrayList<Boolean>();
        progress.show();
        nigths_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(NewGroupChooseHotel.this, ""+i, Toast.LENGTH_SHORT).show();
                progress.show();
                hotelsByDaysRequest(locations_array.get(i));
                progress.hide();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        progress.hide();




        ListAdapterHotelService adapterHotelService = new ListAdapterHotelService(NewGroupChooseHotel.this, nameHotel,destnion,origin,brodcast);
        listView.setAdapter(adapterHotelService);
    }


    public void hotelsByDaysRequest(final String location){
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, "http://172.104.150.56/api/hotels/request/10",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.hide();
                Log.i("responsak",response.toString());
                try {
                    nameHotel.clear();
                    destnion.clear();
                    id.clear();
                    origin.clear();
                    brodcast.clear();

                    JSONArray locationarrray = response.getJSONArray(location);

                    for (int k=0 ; k<locationarrray.length();k++) {
                        JSONObject locationObecjt = (JSONObject) locationarrray.get(k);
                        Log.i("JSONOBEJCT", locationObecjt.getString("name"));
                        nameHotel.add(locationObecjt.getString("name"));
                        destnion.add(locationObecjt.getString("location"));
                        id.add(locationObecjt.getString("id"));
                        origin.add(locationObecjt.getString("street_address"));
                        brodcast.add(false);

                    }

                    ListAdapterHotelService adapterHotelService = new ListAdapterHotelService(NewGroupChooseHotel.this, nameHotel,destnion,origin,brodcast);
                    listView.setAdapter(adapterHotelService);

                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                           View editView= LayoutInflater.from(NewGroupChooseHotel.this).inflate(R.layout.dailog_for_brodcast,null);
                           TextView nameofhotel = (TextView) editView.findViewById(R.id.nameofhotl);
                           nameofhotel.setText(nameHotel.get(i));
                           AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupChooseHotel.this);
                           builder.setView(editView);
                           final int index = i;
                           final AlertDialog dialog4 = builder.create();
                           dialog4.show();
                           Button cancelButton = (Button)editView.findViewById(R.id.cancelButtonBrotcast);
                           Button okButtonBrotcast = (Button)editView.findViewById(R.id.okButtonBrotcast);
                           okButtonBrotcast.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   Log.i("asdasdas",id.get(index));
                                   progress.show();
                                   broducstRequset(id.get(index),6,index);
                                   progress.hide();
                                   dialog4.hide();

                                   Log.i("qweqweeqwqwe","http://172.104.150.56/api/hotels/broadcast/"+6+"/"+id.get(index));
                                   ListAdapterHotelService adapterHotelServic2e = new ListAdapterHotelService(NewGroupChooseHotel.this, nameHotel,destnion,origin,brodcast);
                                   listView.setAdapter(adapterHotelServic2e);



                               }


                           });
                           cancelButton.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   dialog4.hide();
                               }
                           });


                       }
                   });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    progress.hide();
                    Log.i("responsak","im in catch "+e.getMessage().toString());

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responsak","Error");
                progress.hide();
            }
        }) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                SharedPreferences NewGroupSp=getBaseContext().getSharedPreferences("NewGroup",MODE_PRIVATE);
                String dataForRequset = NewGroupSp.getString("dataForHotelRequst","");
                Log.i("asdasdas",dataForRequset);
                params2.put("nights", dataForRequset);
                Log.i("days10",params2.toString());

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(NewGroupChooseHotel.this).addToRequestQueue(sr);

    }

    public void broducstRequset(String s, int i, final int index) {
        StringRequest sr = new StringRequest(Request.Method.GET, "http://172.104.150.56/api/hotels/broadcast/"+i+"/"+s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.show();
                Log.i("responsak",response.toString());

                brodcast.set(index,true);
                progress.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                brodcast.set(index,false);
                Log.i("responsak","Error");



            }
        });
        MySingleton.getInstance(NewGroupChooseHotel.this).addToRequestQueue(sr);

    }

}
