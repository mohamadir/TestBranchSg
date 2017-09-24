package com.snapgroup.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    String username,firstName,lastName,password,phoneNum;
    EditText userNameEt,firstNameEt,lastNameEt,passwordEt,phoneNumbereditText;
    Button registerBt;
    String randomCode;
    String phoneNumberString="";
    public  ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_register);
        initViews();
        // get the texts from the edit texts
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        registerBt=(Button)findViewById(R.id.signUpVolleyBt);
        pd=new ProgressDialog(RegisterActivity.this);
        userNameEt=(EditText) findViewById(R.id.email_Et);
        phoneNumbereditText=(EditText) findViewById(R.id.phoneNumbereditText);
        firstNameEt=(EditText) findViewById(R.id.firstName_Et);
        lastNameEt=(EditText) findViewById(R.id.lastName_Et);
        passwordEt=(EditText) findViewById(R.id.password_Et);
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (passwordEt.getText().toString().equals("")||
                        phoneNumbereditText.getText().toString().equals("")||
                        firstNameEt.getText().toString().equals("")||
                        lastNameEt.getText().toString().equals("")||
                        userNameEt.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "You Must To fill all Information", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.setMessage("Wait please.. ");
                    pd.show();
             /*   View phoneNumberView= LayoutInflater.from(RegisterActivity.this).inflate(R.layout.enter_phone_number_dialog,null);
                final EditText phoneNumberEt = (EditText) phoneNumberView.findViewById(R.id.phoneNumberEt);
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Validation");
                builder.setView(phoneNumberView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       // registerRequest(userNameEt.getText().toString(),passwordEt.getText().toString(),firstNameEt.getText().toString(),lastNameEt.getText().toString());

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pd.hide();
                    }
                });*/
            /*    final AlertDialog dialog = builder.create();
                dialog.show();*/

                    phoneNumberString = phoneNumbereditText.getText().toString();
                    randomCode = randomCode();
                    sendSMS(phoneNumberString, randomCode);
                    validateCode(randomCode);


                }

            }

        });



    }

    private void validateCode(String s) {


        View phoneNumberView= LayoutInflater.from(RegisterActivity.this).inflate(R.layout.enter_code_number_dialog,null);
        final EditText validateCodeEt = (EditText) phoneNumberView.findViewById(R.id.validateCodeEt);
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage("Validation");
        builder.setView(phoneNumberView);
        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              String  validateCodeString =validateCodeEt.getText().toString();
              if(!validateCodeString.equals("")&&validateCodeString.equals(randomCode))
               registerRequest(userNameEt.getText().toString(),passwordEt.getText().toString(),
                       firstNameEt.getText().toString(),lastNameEt.getText().toString(),
                       phoneNumbereditText.getText().toString());

            }
        });

        builder.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pd.hide();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String randomCode() {

        int randomPIN = (int)(Math.random()*9000)+1000;
        String val = ""+randomPIN;
        return val;
    }

    public void initViews(){

    }

     // register post request with the relevant variables

    public void registerRequest(final String username, final String password, final String firstname, final String lastname,final String phoneNum){
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();

            LogInPostRequest(userNameEt.getText().toString(),passwordEt.getText().toString());





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.i("responsy","error");

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Register faild , it could be caused by wrong inputs \n\n" +
                        "Or user already exist")
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
                if(username==null)
                    Log.i("imnull","imnull");
                else {

                    params2.put("email", username);
                    params2.put("password", password);
                    params2.put("first_name", firstname);
                    params2.put("last_name", lastname);
                    params2.put("phone", phoneNum);

                  //  params2.put("profile_image", "http://devilsworkshop.org/files/2013/01/small-facebook-profile-picture.jpg");
                }
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

        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(sr);

    }
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, "PIN code: "+message, sentPI, deliveredPI);
    }



    public void LogInPostRequest(String email, String password){
        String url = "http://172.104.150.56/api/login";
        Map<String, String> params = new HashMap();
        params.put("email", email);
        params.put("password",password );
        Log.i("tokeny","Im in the LoginRequest");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject js=response.getJSONObject("token");
                    Log.i("tokeny3",js.getString("access_token").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO: handle success
                try {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("Login Successfully")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i=new Intent(SignInActivity.this,GroupListActivity.class);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    Intent i=new Intent(RegisterActivity.this,GroupListActivity.class);
                    startActivity(i);




                    SharedPreferences.Editor editor=getSharedPreferences("UserLog",MODE_PRIVATE).edit();
                    editor.putString("isSigned","true");
                    editor.putString("token",response.getJSONObject("token").getString("access_token").toString());
                    editor.putString("id",response.getJSONObject("member").getJSONObject("profile").getString("member_id").toString());
                    editor.putString("email",response.getJSONObject("member").getString("email"));
                    editor.putString("first_name",response.getJSONObject("member").getJSONObject("profile").getString("first_name").toString());
                    editor.putString("last_name",response.getJSONObject("member").getJSONObject("profile").getString("last_name"));
                    editor.putString("profile_image","https://organicthemes.com/demo/profile/files/2012/12/profile_img.png");
                    editor.commit();


                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("tokeny",e.getMessage().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("tokeny","No No !! ");
                //TODO: handle failure
            }
        });
        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(jsonRequest);

    }

}
