package com.snapgroup.Activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    Button signInBt,signInVolleyBt;
    EditText usernameEt,passwordEt;
    String token;
    SignInButton signInGoogleBt;

    TextView signupTv,forgetPaass;
    public ProgressDialog pd;
    GoogleApiClient googleApiClient;

    public static final int REQ_CODE=9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String signed = settings.getString("isSigned","false");
        if(signed.equals("true")) {
            Intent i = new Intent(SignInActivity.this, GroupListActivity.class);
            startActivity(i);
        }
        signInGoogleBt=(SignInButton)findViewById(R.id.btLogin);
        forgetPaass = (TextView)findViewById(R.id.forgetPass);
        forgetPaass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this,RestorePasswordActivty.class);
                startActivity(i);
            }
        });
        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions ).build();
        signupTv=(TextView) findViewById(R.id.signupTv);
        usernameEt=(EditText)findViewById(R.id.signInUsernameEt);
        passwordEt=(EditText)findViewById(R.id.signInPasswordEt);
        pd=new ProgressDialog(SignInActivity.this);
        signInVolleyBt=(Button) findViewById(R.id.signInVolleyBt);
        signInGoogleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,REQ_CODE);
            }
        });
        setListeners();

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            Log.i("googleApi",result.toString());
            handleRequest(result);
        }
        else
            Log.i("googleApi",resultCode+"");

    }
    public void handleRequest(GoogleSignInResult result){
        if(result.isSuccess())
        {
            GoogleSignInAccount account=result.getSignInAccount();
            String name=account.getDisplayName().toString();
            String email=account.getEmail().toString();
            String imgUrl=account.getPhotoUrl().toString();
            Log.i("Gdetails",name+","+email);
        }

    }

    public void setListeners(){
        signInVolleyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Wait please.. ");
                pd.show();
                LogInPostRequest(usernameEt.getText().toString(),passwordEt.getText().toString());
            }
        });
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    // volley request , override  "getBody" method in order to send "user name " and "password" with
    // the post request .
    /*public void volleyRequest(final String username,final String password){
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setMessage("Login Successfully")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i=new Intent(SignInActivity.this,ServicesActivity.class);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                Log.i("responsss",""+response.toString());
                try {

                    JSONObject jsonObj = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
                    Log.i("myTokenTry",jsonObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("myToken",e.getMessage().toString());
                }
                SharedPreferences.Editor editor=getSharedPreferences("UserLog",MODE_PRIVATE).edit();
                editor.putString("isSigned","true");
                editor.putString("email",username);
                editor.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setMessage("Login Faild ! \n\nPlease try again.. ")
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
                params2.put("email", username);
                params2.put("password", password);
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(SignInActivity.this).addToRequestQueue(sr);

    }*/
    //  TO DO

    @Override
    public void onBackPressed() {

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
                    Intent i=new Intent(SignInActivity.this,GroupListActivity.class);
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
        MySingleton.getInstance(SignInActivity.this).addToRequestQueue(jsonRequest);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}