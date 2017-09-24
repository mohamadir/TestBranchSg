package com.snapgroup.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String[] names;
    String st = " ";
    public ProgressDialog pd;
    //public static ArrayList<DayPlanInfo> dayPlanInfos;
    private String mJSONURLString = " http://www.mocky.io/v2/59704b73100000b90371d8c5";
 /*   ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<HeaderData> listDataHeader;
    DayPlanInfo daysArray;
    HeaderData headersArr;
    Button mapBt;

    public   HashMap<String,DayPlanInfo> listDataChild= new HashMap<String, DayPlanInfo>();*/

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PlanFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
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
        View view =  inflater.inflate(R.layout.fragment_plan, container, false);        // Inflate the layout for this fragment
     /*   expListView = (ExpandableListView)view.findViewById(R.id.lvExp1);
        listDataHeader=new ArrayList<HeaderData>();

        //TODO
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        pd=new ProgressDialog(getActivity());
        pd.setMessage("Just A while ...auto ");
        pd.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            // Loop through the array elements
                            Log.i("hosen", "askjd");
                            // Get current json object
                            JSONObject tees= response.getJSONObject(0);
                            JSONObject groupObject=tees.getJSONObject("group");
                            JSONArray planArray = groupObject.getJSONArray("plan");
                            dayPlanInfos = new ArrayList<DayPlanInfo>();
                            for(int z= 0;z < planArray.length();z++)
                            {
                                JSONObject namez=planArray.getJSONObject(z);
                                daysArray = new DayPlanInfo(namez.getString("lunch").toString(),namez.getString("place1").toString(),namez.getString("description").toString(),namez.getString("breakfast").toString(),namez.getString("dinner").toString(),z);
                                headersArr = new HeaderData(namez.getString("date").toString(),namez.getString("day").toString(),namez.getString("hotel").toString(),namez.getString("location").toString(),namez.getString("arrivals").toString());
                                listDataChild.put(""+z, daysArray);
                                listDataHeader.add(headersArr);
                                Log.i("list-error","error");
                            }
                            Log.i("Hash",listDataHeader.size()+"");
                            //Log.i("aray",names.toString());
                            pd.hide();


                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("catch",e.getMessage().toString());
                            pd.hide();
                        }
                        listAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.i("listener",error.getMessage().toString());
                        // Do something when error occurred
                        pd.hide();
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long l) {
                // on click, check if the group list is expanded
                if(expListView.isGroupExpanded(groupPosition)){
                    // if the group list expanded, collapse it
                    expListView.collapseGroup(groupPosition);
                    Log.d("ExpdList", "Exit");
                } else {
                    // if the group list collapse, first collapse all the expanded group list if there any
                    int count = expListView.getCount();
                    Log.d("ExpdList", "Count: " + count);
                    for(int i = 0; i < count; i++){
                        if(i!=groupPosition)
                            expListView.collapseGroup(i);
                        else {
                            if(expListView.getChildAt(0)!=null)
                                expListView.expandGroup(i);
                        }
                    }
                    // then expand the this one
                    Log.d("ExpdList", "Open");
                }
                return true;
            }



        });
        expListView.setAdapter(listAdapter);*/


        return view;
    }
   /* public  class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity activityDay;
        private LayoutInflater inflater;
        private List<HeaderData> _listDataHeader_data; // list of the headers names
        private HashMap<String, DayPlanInfo> dayPlanInfos; // hassh map to use every item in the list to be view....
        public ExpandableListAdapter(Activity activityDay, List<HeaderData> listDataHeader, HashMap<String,DayPlanInfo> listChildData)
        {
            this.activityDay = activityDay;
            this._listDataHeader_data = listDataHeader;
            this.dayPlanInfos = listChildData;
        }

        public Object getChild(int groupPosition, int childPosititon)
        {
            return listDataChild.get(listDataHeader.get(groupPosition));        }

        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition; // return the child postion

        }

        public View getChildView(int groupPosition, final int childPosition,     /// how the child works )
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {
            Log.i("dayy","aaaaa");

            if (convertView == null)
            {
                inflater = (LayoutInflater) activityDay.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // we take the id from the xml of the Plan_list _ Item for  every item we have object
                convertView = inflater.inflate(R.layout.plan_list_item, null);
            }

            TextView lunchh, place11, descriptionn, breakfastt, dinerr;
            descriptionn = (TextView) convertView.findViewById((R.id.description));
            lunchh = (TextView) convertView.findViewById((R.id.lunch));
            place11 = (TextView) convertView.findViewById((R.id.place1));
            breakfastt = (TextView) convertView.findViewById((R.id.breakfast));
            dinerr = (TextView) convertView.findViewById((R.id.dinner));
            lunchh.setText(dayPlanInfos.get(""+groupPosition).lunch);
            place11.setText(dayPlanInfos.get(""+groupPosition).place1);
            breakfastt.setText(dayPlanInfos.get(""+groupPosition).breakfast);
            descriptionn.setText(dayPlanInfos.get(""+groupPosition).description);
            dinerr.setText(dayPlanInfos.get(""+groupPosition).diner);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) { // return the object of the group ( header)
            return this._listDataHeader_data.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader_data.size();
        } // return the size of the headers

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        // to write on the header items...
        {

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.activityDay
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.plan_list, null); // take the plan_list xml id and set the info to it./
            }
            //TextView date = (TextView) convertView.findViewById(R.id.dateTV);
            TextView arrivls = (TextView) convertView.findViewById(R.id.textView10);
            TextView day = (TextView) convertView.findViewById(R.id.dayTV);
            TextView location = (TextView) convertView.findViewById(R.id.location) ;
            TextView hotel = (TextView) convertView.findViewById(R.id.hotel);
            location.setText(_listDataHeader_data.get(groupPosition).location);
            hotel.setText(_listDataHeader_data.get(groupPosition).hotel);
            day.setText(_listDataHeader_data.get(groupPosition).date + " " + _listDataHeader_data.get(groupPosition).day);
            //date.setText(_listDataHeader_data.get(groupPosition).date);
            arrivls.setText(_listDataHeader_data.get(groupPosition).arrivals);
            // herer what we wants to set the info to the group view
            arrivls.setTypeface(null, Typeface.BOLD);
            if(groupPosition==0)
                arrivls.setText("Arival");
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    public class HeaderData
    {
        public String arrivals;
        public String date;
        public String day;
        public String hotel;
        public String location;


        public HeaderData(String date, String day, String hotel, String location, String arrivals)
        {
            this.date = date;
            this.arrivals=arrivals;
            this.day = day;
            this.hotel = hotel;
            this.location = location;
        }


        public String getArrivals() {
            return arrivals;
        }

        public void setArrivals(String arrivals) {
            this.arrivals = arrivals;
        }




        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getHotel() {
            return hotel;
        }

        public void setHotel(String hotel) {
            this.hotel = hotel;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
    public class DayPlanInfo {
        public String lunch;
        public String place1;
        public String description;
        public String breakfast;
        public String diner;
        public  HeaderData hd;
        private int dayNumbers;
        public int getDayNumbers() {
            return dayNumbers;
        }

        public void setDayNumbers(int dayNumbers) {
            this.dayNumbers = dayNumbers;
        }

        public DayPlanInfo(String lunch, String place1, String description, String breakfast, String diner, int dayNumbers) {
            this.lunch = lunch;
            this.place1 = place1;
            this.description = description;
            this.breakfast = breakfast;
            this.diner = diner;
            this.dayNumbers = dayNumbers;
        }

        public String getLunch() {
            return lunch;
        }

        public void setLunch(String lunch) {
            this.lunch = lunch;
        }

        public String getPlace1() {
            return place1;
        }

        public void setPlace1(String place1) {
            this.place1 = place1;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBreakfast() {
            return breakfast;
        }

        public void setBreakfast(String breakfast) {
            this.breakfast = breakfast;
        }

        public String getDiner() {
            return diner;
        }

        public void setDiner(String diner) {
            this.diner = diner;
        }
    }
*/

}
