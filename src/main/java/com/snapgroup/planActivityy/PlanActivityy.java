package com.snapgroup.planActivityy;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.infteh.comboseekbar.ComboSeekBar;
import com.snapgroup.Fragments.FragmentParent;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlanActivityy extends AppCompatActivity {
    FragmentParent fragmentParent;
    public Button imageRightBt,imageLeftBt;
    public int progressStatus=0;
    public SeekBar seekBar;

    public ComboSeekBar comboSeekBar;
    public ArrayList<String> points;
    public ArrayList<PlanPerDayClass> planes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test12);
        printPager();
        planes= new ArrayList<PlanPerDayClass>();
        JesonArrayPlan();
        Bundle b = getIntent().getExtras();

        int x = 0;


        comboSeekBar.setProgress(x);



        imageRightBt = (Button)findViewById(R.id.imageRightBt);
        imageLeftBt = (Button)findViewById(R.id.imageLeftBt);
        imageRightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("imageRightBt",progressStatus+"");
                if(progressStatus<6)
                {
                    comboSeekBar.setProgress(++progressStatus);
                }
            }
        });
        imageLeftBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressStatus>0)
                {
                    comboSeekBar.setProgress(--progressStatus);
                }
            }
        });


        //seekBar = (SeekBar) findViewById(R.id.mapSeekBarr);
        final TextView radiusTv = (TextView) findViewById(R.id.radiusTextVieww);



        //FragmentManager manager, Context context, ViewPager viewPager) {
        final ViewPagerAdapter view = new ViewPagerAdapter(getSupportFragmentManager(),getBaseContext(),fragmentParent.getViewPager());
        fragmentParent.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                Log.i("setOnPageChangeListener",fragmentParent.viewPager.getCurrentItem()+"");
                comboSeekBar.setProgress(fragmentParent.viewPager.getCurrentItem());


            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });







        comboSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int radiusNumber = seekBar.getProgress();
                radiusTv.setText("Day "+seekBar.getProgress());
                progressStatus=seekBar.getProgress();
               fragmentParent.setItem(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });


        // getIDs();
        //setEvents();

    }

    private void JesonArrayPlan() {

        RequestQueue requestQueue = Volley.newRequestQueue(PlanActivityy.this);
        String mJSONURLString="http://172.104.150.56/api/plans";

        comboSeekBar = (ComboSeekBar) findViewById(R.id.mapSeekBarr);


         points = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Log.i("responeSuccess", "" + response.length());

                                JSONObject planForTheDay = response.getJSONObject(i);
                                points.add("");
                                //    public PlanPerDayClass(String breakfast, String description, String dinner, String lunch, String day_number, String date) {
                                planes.add(new PlanPerDayClass(planForTheDay.getString("breakfast")
                                        ,planForTheDay.getString("description")
                                        ,planForTheDay.getString("dinner")
                                        ,planForTheDay.getString("lunch")
                                        ,planForTheDay.getString("day_number"),
                                        planForTheDay.getString("date")));

                            }

                            comboSeekBar.setMax(planes.size());
                            comboSeekBar.setAdapter(points);
                        }catch (JSONException e) {
                                e.printStackTrace();
                            Log.i("listener",e.getMessage().toString());
                            }


                        }

                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
//                        Log.i("listener",error.getMessage().toString());
                        // Do something when error occurred
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);


    }

    public void printPager() {
        ArrayList<Integer> ImageDays=new ArrayList<>();
        ImageDays.add(R.drawable.paris);
        ImageDays.add(R.drawable.london);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.hotelimage);
        ImageDays.add(R.drawable.paris);
        ImageDays.add(R.drawable.london);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);
        ImageDays.add(R.drawable.nackotand);

        ArrayList<String> placesArray = new ArrayList<String>();
        placesArray.add("jurslem musem " );
        placesArray.add("tel aviv musem " );
        placesArray.add("Haifaa  musem " );
        placesArray.add("portgalll musem " );
        placesArray.add("spaoinnn musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );
        placesArray.add("adssdasdas musem " );


        ArrayList<String> date_hour = new ArrayList<String>();
        date_hour.add("Sun  12/05/2017");
        date_hour.add("Mon  13/05/2017");
        date_hour.add("Thus 14/05/2017");
        date_hour.add("Wen  15/05/2017");
        date_hour.add("Tha  16/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");
        date_hour.add("Fri  17/05/2017");



        ArrayList<String> bigDescription = new ArrayList<String>();

        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");
        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");
        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");
        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");
        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");
        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");


        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n");
        bigDescription.add("Drive through the Judean desert to the Dead Sea, the lowest\n" +
                "point on earth, almost 1300 feet (400 meters) below sea\n" +
                "level.Stop at Qumeran to visit the site where the Dead Sea\n" +
                "scrolls were Found. Drive along the shores of the Dead Sea to\n" +
                "Massada. Ascend by cablecar and tour the ancient fortress\n" +
                "where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable");

        bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");
        bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");
        bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");
        bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");
        bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");
        bigDescription.add("where the Zealots made their last stand against the Romans\n" +
                "before committing mass suicide in 73 AD. Descend by cable\n" +
                "car. Continue to the Dead Sea; Cover yourself with the mineral\n" +
                "rich mud and experience a swim in the Dead Sea** that many\n" +
                "believe has its therapeutic effects. Back to Jerusalem...");

        bigDescription.add("abddd is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("fozyyy is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("mo3tsm is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");
        bigDescription.add("3224234 is a good plasdhdjaskld ;lkasdk; asl;dkasd l;asdkfas ");


        for (int i = 0; i <=13; i++)

        {

            fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);
            fragmentParent.addPage(("Day " + "" +  i) , ImageDays.get(i) , placesArray.get(i) , date_hour.get(i),bigDescription.get(i));
        }
    }



}
