package com.snapgroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.snapgroup.R;
import com.snapgroup.planActivityy.PlanActivityy;
import com.snapgroup.planActivityy.PlanPerDayClass;
import com.snapgroup.planActivityy.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.snapgroup.R.drawable.day_icon;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InteniaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InteniaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InteniaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentParent fragmentParent;
    public Button imageRightBt,imageLeftBt;
    public int progressStatus=0;
    public SeekBar seekBar;

    public ComboSeekBar comboSeekBar;
    public ArrayList<String> points;
    public ArrayList<PlanPerDayClass> planes;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InteniaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InteniaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InteniaryFragment newInstance(String param1, String param2) {
        InteniaryFragment fragment = new InteniaryFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View IntView= inflater.inflate(R.layout.fragment_inteniary, container, false);

        comboSeekBar = (ComboSeekBar) IntView.findViewById(R.id.mapSeekBarr);



        points = new ArrayList<>();
        points.add("Day1");
        points.add("Day2");
        points.add("Day3");
        points.add("Day4");
        points.add("Day5");
        points.add("Day6");
        points.add("Day7");
        points.add("Day8");

        comboSeekBar.setAdapter(points);

        /*String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};


        StateProgressBar stateProgressBar = (StateProgressBar) IntView.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);*/

        printPager();
        planes= new ArrayList<PlanPerDayClass>();
        JesonArrayPlan(IntView);
        Bundle b = getActivity().getIntent().getExtras();

        int x = 0;

        comboSeekBar.setProgress(x);



        imageRightBt = (Button)IntView.findViewById(R.id.imageRightBt);
        imageLeftBt = (Button)IntView.findViewById(R.id.imageLeftBt);
        imageRightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("imageRightBt",progressStatus+"");
                if(progressStatus<8)
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
        final TextView radiusTv = (TextView) IntView.findViewById(R.id.radiusTextVieww);



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





        comboSeekBar.setMax(8);
        //comboSeekBar.setAdapter(points);

        comboSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int radiusNumber = seekBar.getProgress();


                radiusTv.setText("Day "+seekBar.getProgress());
                radiusTv.setTextSize(18);

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




        return IntView;


    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void JesonArrayPlan(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String mJSONURLString="http://172.104.150.56/api/plans";
//        points = new ArrayList<>();
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
                                //points.add(""+(i+1));
                                //    public PlanPerDayClass(String breakfast, String description, String dinner, String lunch, String day_number, String date) {
                               /* planes.add(new PlanPerDayClass(planForTheDay.getString("breakfast")
                                        ,planForTheDay.getString("description")
                                        ,planForTheDay.getString("dinner")
                                        ,planForTheDay.getString("lunch")
                                        ,planForTheDay.getString("day_number"),
                                        planForTheDay.getString("date")));*/

                            }

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


        for (int i = 0; i <6; i++)

        {

            fragmentParent = (FragmentParent)getChildFragmentManager().findFragmentById(R.id.fragmentParent);
            fragmentParent.addPage(("Day " + "" +  i) , ImageDays.get(i) , placesArray.get(i) , date_hour.get(i),bigDescription.get(i));
        }
    }
}
