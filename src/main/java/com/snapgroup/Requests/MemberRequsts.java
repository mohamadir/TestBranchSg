package com.snapgroup.Requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.snapgroup.Models.Member;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MemberRequsts
{

    Context context;
     public  static  Member members;

    public MemberRequsts(Context context)
    {
        this.context= context;

    }
    public static void GetRequsetMember(Context context, ArrayList<Member> members)
    {
        members = new ArrayList<Member>();
        final ArrayList<Member> finalMembers = members;
        String urlGetMmebrs = "http://172.104.150.56/api/members";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlGetMmebrs,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int z= 0;z < response.length();z++){
                                Log.i("hiHe" , "hi");
                             // Loop through the array elements
                            // Get current json object

                                JSONObject tees = response.getJSONObject(z);
                                JSONObject ProfileObejct = tees.getJSONObject("profile");
                                finalMembers.add(new Member(ProfileObejct.getString(("first_name")),ProfileObejct.getString(("last_name")),ProfileObejct.getString(("profile_image"))));

                            //for(int z= 0;z < planArray.length();z++)
                            //members = new Member(ProfileObejct.getString(("first_name")), ProfileObejct.getString(("last_name")), ProfileObejct.getInt(("profile_image")));
                          //  }
                            //Log.i("aray",names.toString());}
                        }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Log.i("catch",e.getMessage().toString());
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.i("listener",error.getMessage().toString());
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
        Log.i("hosen_aaa","aa");
        //Log.i("a777" ,String.valueOf(finalMembers.get(0).getFirst_name()));

    }
}
