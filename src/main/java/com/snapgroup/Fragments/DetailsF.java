package com.snapgroup.Fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.robohorse.pagerbullet.PagerBullet;
import com.snapgroup.Activities.ProfileMemberActivity;
import com.snapgroup.Activities.ProviderProfile;
import com.snapgroup.Classes.CircleImage;
import com.snapgroup.R;
import com.snapgroup.Tests.GridViewAdapter;
import com.snapgroup.Tests.ImageItem;
import com.snapgroup.Tests.imageViewPager;
import com.snapgroup.planActivityy.PlanActivityy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import at.blogc.android.views.ExpandableTextView;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.GridView.AUTO_FIT;


public class DetailsF extends Fragment   {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String idCurrentRIghtNow="";

    public DetailsF() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("gridosh",""+gridView.getNumColumns());
    }

     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_details, container, false);
        ImageView groupleaderIv=(ImageView)view.findViewById(R.id.groupLeaderIv);

        Picasso.with(getActivity()).load("http://www.american.edu/uploads/profiles/large/chris_palmer_profile_11.jpg").transform(new CircleImage()).into(groupleaderIv);


       eventServices(view);

        SharedPreferences settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String image = settings.getString("gImage"," asdasdasd");
        String from = settings.getString("gOrigin"," asdasdasd");
        String to = settings.getString("gDestination"," asdasdasd");
        String description = settings.getString("gDescription"," asdasdasd");
        String title = settings.getString("gTitle"," asdasdasd");
        String id = settings.getString("gId","-1");


        TextView numNow = (TextView)view.findViewById(R.id.numberNow);
        SharedPreferences settings7=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String group_id = settings7.getString("gId","-1");
        final String max_groups = settings7.getString("max_members","-1");
        Log.i("max_groups" , max_groups);
        ProgressBar customProgressBar = (ProgressBar) view.findViewById(R.id.customProgressBar);
        customProgressBar.setProgress(4%Integer.parseInt(max_groups));
        TextView numTareget = (TextView)view.findViewById(R.id.numTarget);
        numTareget.setText(max_groups+"");
        numNow.setText("4");
        LinearLayout memberfragment = (LinearLayout)view.findViewById(R.id.memberfragment);
        memberfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new MembersFragment()).addToBackStack(null).commit();
            }
        });


        gridView = (GridView) view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Intent intent = new Intent(getActivity(), PlanActivityy.class);
                Bundle bundle = new Bundle();
                bundle.putInt("postionDay" ,i );
                intent.putExtras(bundle);
                startActivity(intent);*/
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new InteniaryFragment()).addToBackStack(null).commit();



            }
        });


        /*final ScrollView sv = (ScrollView)view.findViewById(R.id.detailsScrollView);
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(sv.FOCUS_DOWN);
            }
        });
        */
       // ImageView groupImage=(ImageView)view.findViewById(R.id.newDetails_groupIv);
        PagerBullet pagerBullet = (PagerBullet)view.findViewById(R.id.viewpager2);
        imageViewPager adapter2 = new imageViewPager(getActivity());
        pagerBullet.setAdapter(adapter2);
        pagerBullet.setClickable(false);
      // Picasso.with(getActivity()).load(image).into(groupImage);
        TextView groupName=(TextView)view.findViewById(R.id.newDetails_groupNameTv);

        groupName.setText(title);
        final ExpandableTextView groupDiscription=(ExpandableTextView)view.findViewById(R.id.newDetails_groupDiscriptionTv);
        groupDiscription.setText(description+description+description+description+description+description+description+description+description);
        groupDiscription.setAnimationDuration(400L);
        final Button expandBt=(Button)view.findViewById(R.id.expand_discriptionTv);
        expandBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (groupDiscription.isExpanded())
                {
                    groupDiscription.collapse();
                    expandBt.setBackgroundResource(R.drawable.splitline);
                }
                else
                {
                    groupDiscription.expand();
                    expandBt.setBackgroundResource(R.drawable.spliteline_up);

                }
            }
        });
        initGraph(view);
        return view;

    }
    private int getNumColumnsCompat(GridView gv) {
        if (Build.VERSION.SDK_INT >= 11) {
            return getNumColumnsCompat11(gv);

        } else {
            int columns = 0;
            int children = gv.getChildCount();
            if (children > 0) {
                int width = gv.getChildAt(0).getMeasuredWidth();
                if (width > 0) {
                    columns = gv.getWidth() / width;
                }
            }
            return columns > 0 ? columns : AUTO_FIT;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private int getNumColumnsCompat11(GridView gv) {
        return gv.getNumColumns();
    }
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i,i));
        }
        return imageItems;
    }
    private void initGraph(View view) {
        BarChart chart = (BarChart) view.findViewById(R.id.chart);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }
    private void eventServices(View view) {

        LinearLayout linaretSites,linaretTRansport,linaretFlights,linaretActivity,linaretResturant,linaretHotels;
        linaretActivity = (LinearLayout)view.findViewById(R.id.linaretaActivity);
        linaretSites = (LinearLayout)view.findViewById(R.id.linaretSites);
        linaretTRansport = (LinearLayout)view.findViewById(R.id.linaretTRansport);
        linaretFlights = (LinearLayout)view.findViewById(R.id.linaretFlights);
        linaretResturant = (LinearLayout)view.findViewById(R.id.linaretResturant);
        linaretHotels = (LinearLayout)view.findViewById(R.id.linaretHotels);
        linaretActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ASda2222sd","asdasd");
                Toast.makeText(getActivity(), "activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProviderProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesrc" ,"http://killadanganhouse.com/wp-content/uploads/2016/04/home-iconbox-activity.png" );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        linaretFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "Flight", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProviderProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesrc" ,"https://static.independent.co.uk/s3fs-public/styles/article_small/public/thumbnails/image/2017/03/23/17/electricplane.jpg" );
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        linaretHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Hotel", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProviderProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesrc" ,"https://upload.wikimedia.org/wikipedia/commons/f/fe/Abbasi_Hotel.jpg" );
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        linaretSites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sites", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProviderProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesrc" ,"https://upload.wikimedia.org/wikipedia/en/5/55/Asmara_Intercontinental_Hotel.jpg" );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        linaretTRansport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Transport", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProviderProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesrc" ,"https://upload.wikimedia.org/wikipedia/commons/6/6e/MTC_white_line_bus.jpg" );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        linaretResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Resturant", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProviderProfile.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesrc" ,"http://www.thedrakehotel.ca/media/files/galleries/photos/Hotel-Lisa_Petrole_1.jpg.980x420_q85_crop.jpg" );
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });




    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
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



}
