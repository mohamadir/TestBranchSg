package com.snapgroup.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Fragments.DatePickeerFragment2;
import com.snapgroup.Fragments.DatePickerFragment;
import com.snapgroup.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewGroupProfile1Activity extends AppCompatActivity {
    ProgressBar pb;
    TextView profileNextBt;
    EditText groupNameEt,groupDescriptionEt;
    TextView dateFromTv,dateToTv;
    public ProgressDialog pd;
    public static String  title,description,image,dateF,dateT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_profile1);
        pb=(ProgressBar)findViewById(R.id.progressBar5);
        groupNameEt=(EditText)findViewById(R.id.NewGroupProfile1_nameEt);
        groupDescriptionEt=(EditText)findViewById(R.id.NewGroupProfile1_descriptionEt);
        dateFromTv=(TextView)findViewById(R.id.settingsActiivty_dateFromTv);
        dateToTv=(TextView)findViewById(R.id.settingsActiivty_dateToTv);
       // pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#019a01")));
        //printSharedPreferences();
        profileNextBt = (TextView)findViewById(R.id.profileNextTv);
        profileNextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //  onNextClick();

                 title=groupNameEt.getText().toString();
                 description=groupDescriptionEt.getText().toString();
                 dateF=dateFromTv.getText().toString();
                 dateT=dateToTv.getText().toString();
                 image="https://i.ytimg.com/vi/omojFXTxis0/maxresdefault.jpg";
                pd=new ProgressDialog(NewGroupProfile1Activity.this);
                pd.show();
                createGroupRequest();

            }
        });
    }

    private void onNextClick() {
        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
        String groupName=groupNameEt.getText().toString();
        String groupDescription=groupDescriptionEt.getText().toString();
        if(!groupName.equals(""))
            editor.putString("title",groupName);
        else
            editor.putString("title","");
        if(!groupDescription.equals(""))
            editor.putString("description",groupDescription);
        else
            editor.putString("description","");
        editor.commit();
        Intent i = new Intent(NewGroupProfile1Activity.this,NewGroupSettingsPageAtivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
    public void showDatePickerDialog2(View v) {
        DialogFragment newFragment = new DatePickeerFragment2();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private void printSharedPreferences() {
        SharedPreferences NewGroupSp=this.getSharedPreferences("NewGroup",MODE_PRIVATE);
        Map<String, ?> allEntries = NewGroupSp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.i("MAPP", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }


    public void createGroupRequest(){
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/groups", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                Log.i("responsy",response);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile1Activity.this);
                builder.setMessage("Congratulations!! \nGroup Created successfully! ")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                Log.i("ERRORR",statusCode);

                //get response body and parse with appropriate encoding
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile1Activity.this);
                builder.setMessage("Sorry , it seems that you can't create group yet , try again later please..")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                Log.i("myError",title+","+description);
                params2.put("title",title);
                params2.put("description",description);
                params2.put("start_date",dateF);
                params2.put("end_date",dateT);
                params2.put("image",image);

                return new JSONObject(params2).toString().getBytes();
            }

         /*   @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImU2MjFhZTY0OTY5ODBhMmI0OTdiYzVjMzE2Y2FjYTgyOGU3ZTRiNTM1NTUzZDA2ZGZlZjg5ZWIwOGEyNDM0YmEyZTZlNzNmOGIwZGJhMmQwIn0.eyJhdWQiOiIyIiwianRpIjoiZTYyMWFlNjQ5Njk4MGEyYjQ5N2JjNWMzMTZjYWNhODI4ZTdlNGI1MzU1NTNkMDZkZmVmODllYjA4YTI0MzRiYTJlNmU3M2Y4YjBkYmEyZDAiLCJpYXQiOjE1MDA5OTA3NzYsIm5iZiI6MTUwMDk5MDc3NiwiZXhwIjoxNTAwOTkyNTc2LCJzdWIiOiI0Iiwic2NvcGVzIjpbIioiXX0.HpcPxoJUmfuYfTD-AEfYoTsiPwkPd39ZrOTRTsTll7Y-938BEKVVO2y2f7QQi4MheM2qcRLlWD4blSLJvGW_0OX2Xq-endqvny5sweGvFffO1MxDPX32WvpFhrvE52ZHWVgvCCSLw1aJjLLM89sqTKVoITDXHprI0_uFLfCvSk7TmTtvpE7PpJvKa-ZW7j763MeE-ywUGfNaxa214OlfS_UHl5UGWTQ5fhG3blnzOSmkS8u0Fy0c1-1hmkY2Ivmxk_7s5yHTYa-p9NtU0Ati1XLCOlonQdHNCkUSjWTWSwFEaiBV17M5PskR_hwc2jq79v6eHWGy0yJ-NOlrE38WiEwkT4SPTgKgvplz6bqVXFsNGWl-ZUxcz_VMSV23CGD0oImSleZdgp7sMBSFDvmasV0d3wXjXau0Ft8Q2LYtHtC75dYXkUOUkSET3yQg07GG4JFbOK_LWldUIn9GezYtQbvTQe_jxHm42go2yK1meLXxSr0YxpR8sRArkPIrr0kTOpluREVAL0WmHyo8nZEEwtZeIjbi862KTmAdYGaGC709duuasLspsxyJKUifR6Rab8V_Pb2RgNtKy-RJsIpcoabewZSatVM0xzKmpiZ15A0qgY6FUugBEhFJ-V9OeT8wQWLMVkWEx13zvY8Lc0hgrOkFoSFwGooqYGTBeSbzOc4");
                return params;
            }*/

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(NewGroupProfile1Activity.this).addToRequestQueue(sr);

    }
}
