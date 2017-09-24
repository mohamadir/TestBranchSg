package com.snapgroup.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONObject;

import java.util.HashMap;

public class NewPasswordActivity extends AppCompatActivity {

    EditText newPassword1,newPassword2;
    Button submit;
    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        newPassword1 = (EditText)findViewById(R.id.passwordnew1);
        newPassword2 = (EditText)findViewById(R.id.passwwordnew2);

        pd=new ProgressDialog(NewPasswordActivity.this);


        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPassword1.getText().toString().equals(newPassword2.getText().toString()))
                {

                     pd.setMessage("Wait please.. ");
                     pd.show();
                            String code = getIntent().getStringExtra("code");
                            String email = getIntent().getStringExtra("email");
                            RestPassword(email,code);
                            Intent i = new Intent(NewPasswordActivity.this,SignInActivity.class);
                            startActivity(i);
                    pd.hide();


                }
            }
        });


    }
    public void RestPassword(final String email , final String code){


        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/password/reset", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseee" , response.toString());

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
                String id_user2 = settings2.getString("id","-1");
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", email);
                params2.put("code", code);
                params2.put("password", newPassword2.getText().toString());

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(NewPasswordActivity.this).addToRequestQueue(sr);

    }
}
