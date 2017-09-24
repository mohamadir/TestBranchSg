package com.snapgroup.Activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.snapgroup.Classes.HotelServiceDatePickerTo;
import com.snapgroup.Classes.HotelServiceDatePickerFrom;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONArray;


public class HotelServiceFilterActivity extends AppCompatActivity {
    Button checkButton;
    TextView dateFromTv,dateToTv;
    String dateFrom,dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_service_filter);
        dateFromTv=(TextView)findViewById(R.id.hotel_service_tvFrom);
        dateToTv=(TextView)findViewById(R.id.hotel_service_tvTo);
        checkButton=(Button)findViewById(R.id.checkBt);
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidSelection())
                {
                    printToast("Some of your selection is empty, Please select date ! ");
                    Intent i  = new Intent(HotelServiceFilterActivity.this,GroupListActivity.class);
                    i.putExtra("date","Notok");
                    startActivity(i);


                    return;
                }
                Intent i  = new Intent(HotelServiceFilterActivity.this,GroupListActivity.class);


                dateFrom=dateFromTv.getText().toString();
                dateTo=dateToTv.getText().toString();
                i.putExtra("date","ok");
                String[] dateFromArray=dateFrom.split("-");
                String temp=dateFromArray[0];
                dateFromArray[0]=dateFromArray[2];
                dateFromArray[2]=temp;
                String[] dateToArray=dateTo.split("-");
                String temp2=dateToArray[0];
                dateToArray[0]=dateToArray[2];
                dateToArray[2]=temp2;
                dateFrom= dateFromArray[0]+"-"+dateFromArray[1]+"-"+dateFromArray[2];
                dateTo=dateToArray[0]+"-"+dateToArray[1]+"-"+dateToArray[2];
                i.putExtra("dateFrom",dateFrom);
                i.putExtra("dateTo",dateTo);
                //getMyGroupsRequest();
                startActivity(i);
            }
        });
    }


    public void showDatePickerDialog2(View v) {
        DialogFragment newFragment = new HotelServiceDatePickerTo();

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new HotelServiceDatePickerFrom();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public boolean isValidSelection(){
        if(dateFromTv.getText().toString().equals("00/00/00")||
                dateToTv.getText().toString().equals("00/00/00"))
            return false;
        return true;
    }
    public void printToast(String msg){
        Toast.makeText(HotelServiceFilterActivity.this,msg, Toast.LENGTH_SHORT).show();

    }
    public void getGroupsRequests(){
        dateFrom="2000-02-09";
        dateTo="2006-12-12";
        String url = "http://172.104.150.56/api/hotels"+"/"+dateFrom+"/"+dateTo;
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //int _id, String title, String descritption, String image, String origin, String destination, String start_date, String end_date
                        Log.i("myResponse",response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet",error.getMessage().toString()+" ");
                    }

                });

        MySingleton.getInstance(HotelServiceFilterActivity.this).addToRequestQueue(jsObjRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.slide_down,0);

    }
}
