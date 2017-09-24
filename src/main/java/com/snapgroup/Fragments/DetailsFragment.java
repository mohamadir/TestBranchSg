package com.snapgroup.Fragments;




import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.snapgroup.Activities.GroupListActivity;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;
/*import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;*/
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;
import com.squareup.seismic.ShakeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class DetailsFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LineGraphSeries<DataPoint> series;
    Button joinBt;
    public static String isJoind="";
    public String idCurrentRIghtNow="";
    public static String groupID;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button flyBt;
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment4

        View view= inflater.inflate(R.layout.fragment_details_fragmetn, container, false);

        SharedPreferences settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String image = settings.getString("gImage"," asdasdasd");
        String from = settings.getString("gOrigin"," asdasdasd");
        String to = settings.getString("gDestination"," asdasdasd");
        String description = settings.getString("gDescription"," asdasdasd");
        String title = settings.getString("gTitle"," asdasdasd");
        String id = settings.getString("gId","-1");
        requsetidByToken();
        Log.i("idString",id);
        groupID=id;
        Log.i("GROOPY",id);
        joinBt=(Button)view.findViewById(R.id.detailsFragment_joinBt);
        setJoinStatus();


        //if (idCurrentRIghtNow)
        SharedPreferences id_current = getActivity().getSharedPreferences("UserLog", MODE_PRIVATE);
        String id_Group = id_current.getString("id_current_user", "-1");
        String isJoined = id_current.getString("isJoined","false");
       // String signed = id_current.getString("isSigned","false");

        Log.i("isJOined",isJoined + "   " + id_Group);

       /* SensorManager sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(new ShakeDetector.Listener(){

            @Override
            public void hearShake() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to join this group?")
                        .setCancelable(false)
                        .setIcon(R.drawable.logo)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences settings=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE);
                                String signed = settings.getString("isSigned","false");
                                if(!signed.equals("false"))
                                {
                                    String email = settings.getString("email","false");
                                    joinRequest(email);
                                }
                                else
                                {
                                }
                            }
                        }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        sd.start(sensorManager);*/
        joinBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE);
                String signed = settings.getString("isSigned","false");
                Log.i("joinBt",signed);
                if(signed.equals("false"))
                {
                    Toast.makeText(getActivity(), "PLease sign in before you can join ! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(joinBt.getText().toString().equals("UNJOIN")) {
                        String id=getMemberId();
                        String groupId=getGroupId();
                        unJoinGrouo(groupId,id);
                    }
                    else
                        joinGroup();
                }
            }
        });
        ImageView groupImage= (ImageView) view.findViewById(R.id.details_groupIv);
        TextView fromTv=(TextView)view.findViewById(R.id.details_fromTv);
        TextView toTv=(TextView)view.findViewById(R.id.detilas_toTv);
        TextView descTv=(TextView)view.findViewById(R.id.details_descriptionTv);
        TextView titleTv=(TextView)view.findViewById(R.id.details_titleTv);
        Animation translatebu= AnimationUtils.loadAnimation(getActivity(), R.anim.text_move);
        TextView newsTv=(TextView)view.findViewById(R.id.detailsFragment_newsTv);
        newsTv.startAnimation(translatebu);
        fromTv.setText(from);
        toTv.setText(to);
        descTv.setText(description);
        titleTv.setText(title);
        Picasso.with(getActivity()).load(image).into(groupImage);
       double x,y;
        x=-5.0;
        GraphView graphView = (GraphView)view.findViewById(R.id.graph3);
        series= new LineGraphSeries<DataPoint>();
        for(int i=0;i<1000;i++)
        {
            x+=0.01;
            y=Math.pow(x,2);
            series.appendData(new DataPoint(x,y),true,500);

        }

        graphView.addSeries(series);

       /* flyBt=(Button)view.findViewById(R.id.flyBtt);
        flyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("hello","hello");
            }
        });*/
        return view;

    }



    private void joinGroup() {
        String id=getMemberId();
        String groupId=getGroupId();
        Log.i("group and member",id+",Group Id :"+groupId);
        joinRequest(groupId,id);

    }

/*    @Override
    public void onResume() {
        super.onResume();
        setJoinStatus();
    }

    @Override
    public void onPause() {
        super.onPause();
        setJoinStatus();
    }*/

    private void unJoinGrouo(String group_Id, String memberID) {
        Log.i("currentUserInJoin" , idCurrentRIghtNow);
        String url="http://172.104.150.56/api/groups/leave/" +group_Id+"/"+memberID;
        Log.i("hihihi",url);
        StringRequest sr = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("UnJoin Successfully")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                joinBt.setText("JOIN");
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isJoind="NOTOK";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                Log.i("errorr","error");
                builder.setMessage("Login Faild")
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
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);


    }
    public String  getMemberId(){
        SharedPreferences settings=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE);
        String signed = settings.getString("isSigned","false");
        if(signed.equals("false"))
        {
            return "";

        }
        else
        {
            requsetidByToken();
            settings=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE);
            String id=settings.getString("id_current_user","-1");
            return id;

        }
    }

    public void joinRequest(String group_Id,String memberID){

        String url="http://172.104.150.56/api/groups/join/" +group_Id+"/"+memberID+"/member";
        Log.i("hihihi",url);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Join Successfully")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                joinBt.setText("UNJOIN");
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                Log.i("errorr","error");
                builder.setMessage("Login Faild")
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
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);

    }

    public  void requsetidByToken()
    {
        SharedPreferences settings2 = getActivity().getSharedPreferences("UserLog", MODE_PRIVATE);
        final String token = settings2.getString("token","");
        String tokenUrl = "http://172.104.150.56/api/get_current_member";
        Log.i("tokenFIles", token);
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                tokenUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("responseobject", response.toString());
                        try {
                            idCurrentRIghtNow = response.getString("id");
                            SharedPreferences.Editor editor=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE).edit();
                            editor.putString("id_current_user",idCurrentRIghtNow);
                            editor.commit();
                            Log.i("currentuser",idCurrentRIghtNow);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token);


                return params;
            }


        };
        requestQueue1.add(jsonArrayRequest);
    }


    public void setJoinStatus()
    {
        SharedPreferences settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String id = settings.getString("gId","-1");
        String urlGetMmebrs = "http://172.104.150.56/api/groups/"+id+"/members";
        Log.i("ASdasd",urlGetMmebrs);
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlGetMmebrs,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        boolean b=false;
                        try {
                            for (int z = 0; z < response.length(); z++)
                            {

                                JSONObject tees = response.getJSONObject(z);
                                Log.i("responseAnswer" , response.toString());

                                JSONObject ProfileObejct = tees.getJSONObject("profile");
                                if(ProfileObejct.getString(("id")).equals(idCurrentRIghtNow))
                                {
                                    joinBt.setText("UNJOIN");

                                }


                            }

                            // not in group
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Log.i("catch",e.getMessage().toString());
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.i("listener",error.getMessage().toString());
                        // Do something when error occurred
                    }
                }
        );

        requestQueue1.add(jsonArrayRequest);

    }

    public String getGroupId() {
        SharedPreferences settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String groupId=settings.getString("gId","-1");
        return groupId;

    }
}