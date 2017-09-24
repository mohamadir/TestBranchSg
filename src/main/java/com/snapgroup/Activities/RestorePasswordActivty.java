package com.snapgroup.Activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RestorePasswordActivty extends AppCompatActivity {
    Button sumbitBt ;
    public String randomCode;
    EditText emailRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);




        emailRest = (EditText)findViewById(R.id.phoneNUmbers);
        sumbitBt = (Button)findViewById(R.id.submit);
        sumbitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword(emailRest.getText().toString());

            }
        });




    }


    private void validateCode(String s) {


        View phoneNumberView= LayoutInflater.from(RestorePasswordActivty.this).inflate(R.layout.enter_code_number_dialog,null);
        final EditText validateCodeEt = (EditText) phoneNumberView.findViewById(R.id.validateCodeEt);
        AlertDialog.Builder builder = new AlertDialog.Builder(RestorePasswordActivty.this);
        builder.setMessage("Validation");
        builder.setView(phoneNumberView);
        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String  validateCodeString =validateCodeEt.getText().toString();
                if(!validateCodeString.equals("")&&validateCodeString.equals(randomCode)){
                    Intent i = new Intent(RestorePasswordActivty.this,NewPasswordActivity.class);
                    Log.i("asdasdasdasd",randomCode);
                    i.putExtra("code" , randomCode);
                    i.putExtra("email" ,emailRest.getText().toString() );
                    startActivity(i);

                }
                else
                    Toast.makeText(RestorePasswordActivty.this, "Not Ok", Toast.LENGTH_SHORT).show();
                

            }
        });

        builder.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

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


    public void ResetPassword(final String email){

        Log.i("responseee" ,"im in ResetPassword");

        Log.i("responeseee" , "http://172.104.150.56/api/password/getresetcode");
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, "http://172.104.150.56/api/password/getresetcode", null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("responseee" , response.toString());
                try {
                    randomCode = response.getString("code").toString();
                    sendSMS("0525662875", randomCode);
                    validateCode(randomCode);
                    Log.i("responesecode" , randomCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseee" , "Im in error "+error.networkResponse.statusCode);

            }
        }) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", email);

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            /* @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", email);

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }*/
        };

        MySingleton.getInstance(RestorePasswordActivty.this).addToRequestQueue(sr);

    }
}
