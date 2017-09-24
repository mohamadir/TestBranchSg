package com.snapgroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServicesActivity extends AppCompatActivity {
   /* Button serviceFly,serviceTrans,serviceGuide,serviceHotels,servicePlaces,serviceRests,createNewGroupBt,signInBt;

    Toolbar mToolBar;
    Button menuBt;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // view settings
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(ServicesActivity.this,R.color.orange));

        Log.i("onCreateMethod","im here");
        // views initial
        serviceFly=(Button)findViewById(R.id.serviceFlightBt);
        serviceTrans=(Button)findViewById(R.id.serviceTransportBt);
        serviceGuide=(Button)findViewById(R.id.serviceGUideBt);
        serviceHotels=(Button)findViewById(R.id.serviceHotelsBt);

        menuBt=(Button)findViewById(R.id.menuBt);
        serviceTrans=(Button)findViewById(R.id.serviceTransportBt);
        servicePlaces=(Button)findViewById(R.id.servicePlacesBt);
        serviceRests=(Button)findViewById(R.id.serviceRestaurantBt);
        createNewGroupBt=(Button)findViewById(R.id.createNewGroupBt);
        serviceGuide=(Button)findViewById(R.id.serviceGUideBt);
        serviceHotels=(Button)findViewById(R.id.serviceHotelsBt);
        //signInBt=(Button)findViewById(R.id.ServicesActivity_SignInBt);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        menuBt=(Button)findViewById(R.id.menuBt);
        mToggle.syncState();
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // clear all createNewGroup data from sharedPreferences
        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
        editor.clear().commit();

        // get the data of the user by gaven token from the server
        if(isSigned()) {
            SharedPreferences userLogInfo=this.getSharedPreferences("UserLog",MODE_PRIVATE);
            token= userLogInfo.getString("token","");
            Log.i("signedIn","ok");
            requsetidByToken();
        }
        // change sign status according the account status from sharedPrefernces

        if(isSigned())
            signInBt.setText("SIGN OUT");
        else {
            Log.i("signed","im here");
            signInBt.setText("SIGN IN");

        }
        initListeners();



    }
    // click listeners initial
public void initListeners(){
    menuBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    });
    signInBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(signInBt.getText().equals("SIGN IN")) {
                Intent i = new Intent(ServicesActivity.this, SignInActivity.class);
                startActivity(i);
            }
            else{
                setNavigateViewSignOut();
                signOut();}
        }
    });
    createNewGroupBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i =new Intent(ServicesActivity.this,NewGroupSettingsPageAtivity.class);
            startActivity(i);
        }
    });
    serviceFly.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goIntent(GroupListActivity.class);
        }
    });
    serviceTrans.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goIntent(GroupListActivity.class);
        }
    });
    servicePlaces.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goIntent(GroupListActivity.class);
        }
    });
    serviceRests.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goIntent(GroupListActivity.class);
        }
    });
    serviceHotels.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(ServicesActivity.this,HotelServiceFilterActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);

        }
    });
    serviceGuide.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goIntent(GroupListActivity.class);
        }
    });
}

//  set the values  of the views  in the NavagationVIew header -- if the user is LOgged IN !!
    private void setNavigateViewSignIn() {

        View viewy=navigationView.getHeaderView(0);
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String firstNameString = settings.getString("first_name","No name");
        String lastNameString = settings.getString("last_name","No name");
        String emailString = settings.getString("email","No name");
        String profileImage= settings.getString("profile_image","No name");
        Log.i("detailsy",firstNameString+","+lastNameString+","+profileImage);
        TextView name=(TextView)viewy.findViewById(R.id.nav_header_nameTv);
        TextView email=(TextView)viewy.findViewById(R.id.nav_header_email);
        TextView signOut=(TextView)viewy.findViewById(R.id.nav_header_SignOut);
        ImageView profileIv=(ImageView)viewy.findViewById(R.id.nav_header_profileImg);
        name.setText(firstNameString+" "+lastNameString);
        email.setText(emailString);
        Picasso.with(getBaseContext()).load(profileImage).into(profileIv);
        signOut.setVisibility(View.VISIBLE);

        *//*TextView tv=(TextView)viewy.findViewById(R.id.textTest);
        tv.setText("hi");*//*
    }
    //  set the values  of the views  in the NavagationVIew header -- if the user is LOgged Out !!

    private void setNavigateViewSignOut() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View viewy=navigationView.getHeaderView(0);
        TextView name=(TextView)viewy.findViewById(R.id.nav_header_nameTv);
        TextView email=(TextView)viewy.findViewById(R.id.nav_header_email);
        TextView signOut=(TextView)viewy.findViewById(R.id.nav_header_SignOut);
        ImageView profileIv=(ImageView)viewy.findViewById(R.id.nav_header_profileImg);
        name.setText("No User Loged");
        email.setText("");
        signOut.setVisibility(View.GONE);
        Picasso.with(getBaseContext()).load("http://science.cs.upc.edu/public/web/img/avatar/no_user_logo.png").into(profileIv);
        *//*TextView tv=(TextView)viewy.findViewById(R.id.textTest);
        tv.setText("hi");*//*
    }

// set the toggle  settings
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    // method to intent transport
    public void goIntent(Class c)
    {
        Intent i=new Intent(ServicesActivity.this,c);
        startActivity(i);
    }

    // check from the sharedPreferences if someone is signed now
    public boolean isSigned(){
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String signed = settings.getString("isSigned","false");
        if(signed.equals("false"))
            return false;
        else
            return true;
    }

    // sign Out method ( changing the text of the button to sign in and update the sharedPreferences)
    public void signOut(){
        signInBt.setText("SIGN IN");
        SharedPreferences.Editor editor=getSharedPreferences("UserLog",MODE_PRIVATE).edit();
        editor.clear();
        editor.putString("isSigned","false");
        editor.commit();
    }

    // ovveride the onResume method in order to refresh the data in SharedPreferences
    @Override
    protected void onResume() {
        if(isSigned())
            signInBt.setText("SIGN OUT");
        else {
            Log.i("signed","im here");
            signInBt.setText("SIGN IN");
        }
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.my_profile_drawer_item)
        {
            Log.i("itemy","im clicked");
            Intent i=new Intent(ServicesActivity.this,ProfileMemberActivity.class);
            startActivity(i);
        }
        return false;
    }

    // request the user profile details from the server in given TOKEN
    public  void requsetidByToken() {
        String tokenUrl = "http://172.104.150.56/api/get_current_member";
        SharedPreferences settings2 =this.getSharedPreferences("UserLog", MODE_PRIVATE);
        final String token2 = settings2.getString("token", "");
        Log.i("requestByToken","hi");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                tokenUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("exceptiony",response.toString());

                        SharedPreferences.Editor editor=getSharedPreferences("UserLog",MODE_PRIVATE).edit();
                        try {
                            JSONObject profile=response.getJSONObject("profile");
                            editor.putString("id",response.getString("id").toString());
                            editor.putString("email",response.getString("email"));
                            editor.putString("first_name",profile.getString("first_name").toString());
                            editor.putString("last_name",profile.getString("last_name"));
                            editor.putString("profile_image",profile.getString("profile_image"));
                            editor.commit();
                            setNavigateViewSignIn();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("exceptiony",e.getMessage().toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error_listener","error");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token2);


                return params;
            }


        };
        MySingleton.getInstance(ServicesActivity.this).addToRequestQueue(jsonObjectRequest);
        setNavigateViewSignIn();


    }*/
}
