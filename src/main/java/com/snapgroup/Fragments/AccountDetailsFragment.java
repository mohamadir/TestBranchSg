package com.snapgroup.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Activities.JoinGroupActivity;
import com.snapgroup.Activities.ProfileMemberActivity;
import com.snapgroup.Adapters.HLVAdapter;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AccountDetailsFragment extends Fragment {


    Button emailEdit,phoneEdit,nameEdit,editPasswordButton ;
    public boolean  check=false;
    TextView account_NameTv, account_PhoneTv,account_EmailTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.account_details_fragment, container, false);
        emailEdit = (Button)view.findViewById(R.id.emailEdit);
        nameEdit = (Button)view.findViewById(R.id.nameEdit);
        phoneEdit = (Button)view.findViewById(R.id.phoneEdit);
        account_NameTv = (TextView)view.findViewById(R.id.account_NameTv) ;
        account_PhoneTv = (TextView)view.findViewById(R.id.account_PhoneTv) ;
        account_EmailTv = (TextView)view.findViewById(R.id.account_EmailTv) ;
        getGroupsRequests();
        SharedPreferences settings2=getActivity().getSharedPreferences("UserLog",getActivity().MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        Log.i("asdaczxc" , id_user2);
        editPasswordButton= (Button)view.findViewById(R.id.editPasswordButton);
        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View joinDialog12 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_password, null);
                final EditText currentPassword = (EditText) joinDialog12.findViewById(R.id.currentPassword);
                final EditText newPassword = (EditText) joinDialog12.findViewById(R.id.newPassword);
                final EditText confirmPassword = (EditText) joinDialog12.findViewById(R.id.confirmPassword);
                Button cancelBT = (Button)joinDialog12.findViewById(R.id.cancelBt);
                Button saveBT = (Button)joinDialog12.findViewById(R.id.saveBtPass);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(joinDialog12);
                final AlertDialog dialog4 = builder.create();
                dialog4.show();
                cancelBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.hide();
                    }
                });
                saveBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    if (newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                        UpdatePassword(currentPassword.getText().toString(),newPassword.getText().toString());
                        dialog4.hide();

                    }



                    }
                });
            }
        });


      /*  phoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View joinDialog12 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_phone, null);
                final EditText phoneEditosh = (EditText) joinDialog12.findViewById(R.id.phoneEditosh);
                Button cancelBT = (Button)joinDialog12.findViewById(R.id.cancelBt);
                Button saveBT = (Button)joinDialog12.findViewById(R.id.saveBt);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(joinDialog12);
                final AlertDialog dialog4 = builder.create();
                dialog4.show();
                cancelBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.hide();
                    }
                });
                saveBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        account_PhoneTv.setText(phoneEditosh.getText().toString());
                        UpdatEmail(phoneEditosh.getText().toString());
                        dialog4.hide();
                    }
                });
            }
        });*/


        emailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View joinDialog12 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_email, null);
                final EditText emailEditosh = (EditText) joinDialog12.findViewById(R.id.emailEditosh);
                Button cancelBT = (Button)joinDialog12.findViewById(R.id.cancelBt);
                Button saveBT = (Button)joinDialog12.findViewById(R.id.saveBt);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(joinDialog12);
                final AlertDialog dialog4 = builder.create();
                dialog4.show();
                cancelBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.hide();
                    }
                });
                saveBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        account_EmailTv.setText(emailEditosh.getText().toString());
                        UpdatEmail(emailEditosh.getText().toString());
                        dialog4.hide();
                    }
                });
            }
        });

        nameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View joinDialog12 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_name, null);
                final EditText namefull = (EditText) joinDialog12.findViewById(R.id.nameEdit);
                Button cancelBT = (Button)joinDialog12.findViewById(R.id.cancelBt);
                Button saveBT = (Button)joinDialog12.findViewById(R.id.saveBt);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(joinDialog12);
                final AlertDialog dialog4 = builder.create();
                dialog4.show();
                cancelBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.hide();
                    }
                });
                saveBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        account_NameTv.setText(namefull.getText().toString());
                        UpdateName(account_NameTv.getText().toString());
                        dialog4.hide();
                    }
                });
            }
        });

    return view;
    }


    public void UpdatePassword(final String oldPass , final String newPassword){


        SharedPreferences settings2=getActivity().getSharedPreferences("UserLog",getActivity().MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/updatepassword/"+id_user2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeea" , response.toString());
                Toast.makeText(getActivity(), "Your Password Change Succeessfuly", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseeea" , error.getMessage().toString());
                Toast.makeText(getActivity(), "Your Old Password not correct ", Toast.LENGTH_SHORT).show();



            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("old_password", oldPass);
                params2.put("new_password", newPassword);

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);

    }


    public void UpdatePhone(final String st){


        SharedPreferences settings2=getActivity().getSharedPreferences("UserLog",getActivity().MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/updatename/"+id_user2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseee" , response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                HashMap<String, String> params2 = new HashMap<String, String>();
                String []name = st.split(" ");
                params2.put("first_name", name[0]);
                params2.put("last_name", name[1]);

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);

    }



    public void UpdateName(final String st){


        SharedPreferences settings2=getActivity().getSharedPreferences("UserLog",getActivity().MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/updatename/"+id_user2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseee" , response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                HashMap<String, String> params2 = new HashMap<String, String>();
                String []name = st.split(" ");
                params2.put("first_name", name[0]);
                params2.put("last_name", name[1]);

                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);

    }



    public void UpdatEmail(final String st){


        SharedPreferences settings2=getActivity().getSharedPreferences("UserLog",getActivity().MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/updateemail/"+id_user2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseee" , response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", st);
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);

    }



    public void getGroupsRequests()
    {


        SharedPreferences settings2=getActivity().getSharedPreferences("UserLog",getActivity().MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        Log.i("asdaczxc" , id_user2);

        String url2 = "http://172.104.150.56/api/members/"+id_user2;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("jsonObectSecuuess" , response.toString());

                        //int _id, String title, String descritption, String image, String origin, String destination, String start_date, String end_date
                        // JSONArray groupsArray = response;
                        try {

                            JSONObject groupsObject = response;
                            JSONObject testtt = groupsObject.getJSONObject("success");
                           /* if (testtt.getString("phone")!=null)
                            {
                                account_PhoneTv.setText("Updat your phone NUmber");
                            }
                            else
                            {
                                account_PhoneTv.setText(testtt.getString("phone"));
                            }*/
                            Log.i("qweqwqweqweqewqewqwe", testtt.toString());
                            account_EmailTv.setText(testtt.getString("email"));
                            JSONObject profile = testtt.getJSONObject("profile");
                            account_NameTv.setText(profile.getString("first_name") + " " +profile.getString("last_name") );

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.i("groupsGet", error.getMessage().toString() + " ");
                    }

                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

}