package com.snapgroup.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONObject;

import java.util.HashMap;

public class NewGroupFinishActivity extends AppCompatActivity {

    TextView finishTv,statusTv;
    Context context;
    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_finish);
        finishTv=(TextView)findViewById(R.id.finishActivity_finishTv);
        statusTv=(TextView)findViewById(R.id.finishActivity_newGroupStatusTv);
        this.context=getBaseContext();
        pd=new ProgressDialog(NewGroupFinishActivity.this);
        pd.show();

        createGroupRequest();
        finishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NewGroupFinishActivity.this, GroupListActivity.class);
                i.putExtra("Intent","Finish");
                startActivity(i);
            }
        });

    }



    public void createGroupRequest(){
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/groups", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                Log.i("responsy",response);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupFinishActivity.this);
                builder.setMessage("Congratulations!! \nGroup Created successfully! ")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                statusTv.setText("Greate , group created ! :-)");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupFinishActivity.this);
                builder.setMessage("Sorry , it seems that you can't create group yet , try again later please..")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                Log.i("myError",error.getMessage()+" ");
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                SharedPreferences settings=context.getSharedPreferences("NewGroup",MODE_PRIVATE);
                String image = settings.getString("image","no url");
                String title = settings.getString("title","no title");
                String description = settings.getString("description","no description");
                String origin = settings.getString("origin","no origin");
                String destination = settings.getString("destination","no dest");
                String memberOrigin= settings.getString("member_origin","no member origin");
                boolean inAppPrivacy = settings.getBoolean("inapp_privacy",false);
                boolean outAppPrivacy = settings.getBoolean("search_privacy",false);
                String dateFrom = settings.getString("start_date","no date");
                String dateTo = settings.getString("end_date","no date");
                int maxMembers= settings.getInt("max_members",0);
                boolean seniors = settings.getBoolean("semiors",false);
                boolean disabled = settings.getBoolean("disabled",false);
                boolean children = settings.getBoolean("children",false);
                boolean medicalHandicap= settings.getBoolean("medical_handicap",false);
                boolean pleasure= settings.getBoolean("pleasure",false);
                boolean religion= settings.getBoolean("religion",false);
                boolean business= settings.getBoolean("business",false);
                boolean medical= settings.getBoolean("medical",false);
                boolean seminar= settings.getBoolean("seminar",false);
                boolean hebrew= settings.getBoolean("hebrew",false);
                boolean english= settings.getBoolean("english",false);
                boolean french= settings.getBoolean("french",false);
                boolean russian= settings.getBoolean("russian",false);
                params2.put("image",image);
                params2.put("title",title);
                params2.put("description",description);
                params2.put("origin",origin);
                params2.put("destination",destination);
                params2.put("member_origin",memberOrigin);
                params2.put("start_date",dateFrom);
                params2.put("end_date",dateTo);
                params2.put("max_members",maxMembers+"");
                params2.put("inapp_privacy",""+inAppPrivacy);
                params2.put("search_privacy",""+outAppPrivacy);
                params2.put("seniors",""+seniors);
                params2.put("disabled",""+disabled);
                params2.put("children",""+children);
                params2.put("medical_handicap",""+medicalHandicap);
                params2.put("pleasure",""+pleasure);
                params2.put("religion",""+religion);
                params2.put("business",""+business);
                params2.put("medical",""+medical);
                params2.put("seminar",""+seminar);
                params2.put("hebrew",""+hebrew);
                params2.put("english",""+english);
                params2.put("french",""+french);
                params2.put("russian",""+russian);

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

        MySingleton.getInstance(NewGroupFinishActivity.this).addToRequestQueue(sr);

    }

}
