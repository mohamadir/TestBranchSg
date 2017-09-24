package com.snapgroup.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Adapters.LIstViewAdapterHotels;
import com.snapgroup.Classes.*;
import com.snapgroup.R;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewGroupHotelsServiceActivity extends AppCompatActivity {

    TextView nextBT;
    private ListView mListView;
    public String copyString = "";
    private ArrayAdapter<String> adapterAuto;
    public LIstViewAdapterHotels adapterHotels;
    private  ArrayList<String>daysNumbers;
    private  ArrayList<String>nightsToArray;
    private  ArrayList<String>locationsByNights;

    public  boolean[]daysoffOn;
    public AutoCompleteTextView autoCompleteTextView;

    String[] daysDates;
    String[] locations;
    public ArrayList <HotelServiceLocation> location_and_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_hotels_service);

        SharedPreferences settings=NewGroupHotelsServiceActivity.this.getSharedPreferences("NewGroup",MODE_PRIVATE);
      /*  String dateFrom =  settings.getString("start_date","-1");
        String dataTo =  settings.getString("end_date","-1");*/
        String dateFrom =  "2017-08-12";
        String dataTo =    "2017-08-26";
        int daysOfTrip =getAssignmentDuration(dateFrom,dataTo);

        Log.i("datefromto" , dateFrom + "   "  + dataTo + "  = " + daysOfTrip);

        mListView = (ListView) findViewById(R.id.listview_hotel);
        locations=new String[daysOfTrip];
        for(int i=0;i<locations.length;i++)
            locations[i]="";
        String[] countries=getResources().getStringArray(R.array.countries);
        adapterAuto = new ArrayAdapter<String>(NewGroupHotelsServiceActivity.this,android.R.layout.simple_list_item_1,countries);
        daysNumbers = new ArrayList<String>();
        nightsToArray = new ArrayList<String>();
        locationsByNights = new ArrayList<String>();

        location_and_date = new ArrayList<HotelServiceLocation>();
        for (int i=0;i<daysOfTrip;i++){
            int m = i+1;
            daysNumbers.add("Night"+ m);
        }
        daysDates=new String[daysOfTrip];
        daysDates[0]=dateFrom;
        for(int k=1;k<daysDates.length;k++)
        {
            daysDates[k]=increaseDate(daysDates[k-1]);
        }
        for(int k=0;k<daysDates.length;k++)
        {
           Log.i("dates",daysDates[k]+","+k);
        }
        final ListView listView1 = (ListView) findViewById(R.id.listview_hotel);

        final String[] items = new String[daysNumbers.size()];
        for(int i=0;i<items.length;i++)
            items[i]="Night "+(i+1)+": (You must to choose Location)";

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });


        listView1.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(NewGroupHotelsServiceActivity.this, "pos: "+i, Toast.LENGTH_SHORT).show();

                View copyhotel= LayoutInflater.from(NewGroupHotelsServiceActivity.this).inflate(R.layout.dialog_hotel_copy,null);
                final TextView text1 = (TextView) copyhotel.findViewById(R.id.newHotel);
                final TextView text2 = (TextView) copyhotel.findViewById(R.id.copyFromHotel);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupHotelsServiceActivity.this);
                builder.setView(copyhotel);
                final AlertDialog dialog4 = builder.create();
                dialog4.show();

                final int index=i;


                text1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          View editView= LayoutInflater.from(NewGroupHotelsServiceActivity.this).inflate(R.layout.edittext_dialog,null);
                 final AutoCompleteTextView  edit = (AutoCompleteTextView) editView.findViewById(R.id.autoCompleteTextView233);
                        edit.setAdapter(adapterAuto);
                        edit.setThreshold(1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupHotelsServiceActivity.this);
                builder.setMessage("Enter Hotel Name");
                builder.setView(editView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        items[index]="Night "+(index+1)+": "+edit.getText().toString();
                        copyString =edit.getText().toString();
                        mListView.setAdapter(adapter);

                        // hotelsByDaysRequest(userNameEt.getText().toString(),passwordEt.getText().toString(),firstNameEt.getText().toString(),lastNameEt.getText().toString());
                        String[] mm=items[index].split(":");
                        Log.i("asdasdasd",edit.getText().toString() + " Night"+(index+1));
                        locations[index]=edit.getText().toString();





                    }
                });
                 dialog4.hide();
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                        dialog4.hide();

                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                });

                text2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (copyString.equals("")){
                            dialog4.hide();

                        }
                        else{
                             items[index] = "Night "+(index+1)+": "+copyString;
                            locations[index]=copyString;

                        }

                        mListView.setAdapter(adapter);
                        Log.i("asdkjfhsdkf",items[index]);
                        dialog4.hide();
                    }

                });



            }
        });
        nextBT=(TextView)findViewById(R.id.hotelsActivity_nextBt);
//        printSharedPreferences();
        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.i("gettextasdasd" , adapterHotels.getDaysArray().size()+"");
                Intent i=new Intent(NewGroupHotelsServiceActivity.this
                        ,NewGroupTransportationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);*/
                makeArrayRequestString();
                Intent i = new Intent(NewGroupHotelsServiceActivity.this, NewGroupChooseHotel.class);
                i.putExtra("array",locationsByNights);
                i.putExtra("array2",nightsToArray);
                startActivity(i);
                /*Log.i("countdays","aaa"+countNIghts(daysDates[0],0));

                hotelsByDaysRequest();


                for (int i=0;i<locations.length;i++)
                    Log.i("asdasdfasdf",locations[i]);
                Log.i("SHARAT",makeArrayRequestString());*/

            }
        });
    }

    public void onClick (View v){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();
       // adapterHotels.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }
    public String makeArrayRequestString()
    {
        String data="[";
        int cont=1;
        nightsToArray.clear();
        String [] example ;
        String dateENd="";
        int m=0;
        for (int i = cont; i <= locations.length; i++)
        {
            if(i+1<locations.length&&locations[i].equals(locations[i+1]))
            {
                example = countNIghts(daysDates[i],i).split("_");
                cont = Integer.parseInt(example[0]);
                dateENd =example[1];
                Log.i("nightsarray" ,"night " + i + " -- "+ "night  " +  (cont)+"");
                nightsToArray.add("night " + i + " -- "+ "night  " +  (cont)+" " + "Location " + locations[i]);
                Log.i("Loggy",i+","+cont);
                locationsByNights.add(locations[i]);
                data += "{ \"location\": \"" + locations[i] + "\"," + "\"start_date\": \"" + daysDates[i] + "\"," + "\"end_date\": \"" + dateENd + "\"}";
                if (i + 1 != locations.length-1)
                    data += ",";
                i=cont;

            }
            else {
                if (i+1<locations.length)
                {
                    ///Log.i("qwwe",i + " - " +(i+1));
                    Log.i("nightsarray","night " + i + " -- "+ "night  " +  (i+1)+"");
                    nightsToArray.add("night " + i + " -- "+ "night  " +  (i+1)+" "+ "Location " + locations[i]);
                    locationsByNights.add(locations[i]);
                    data += "{ \"location\": \"" + locations[i] + "\"," + "\"start_date\": \"" + daysDates[i] + "\"," + "\"end_date\": \"" + daysDates[i+1] + "\"}";
                    if (i + 1 != locations.length-1)
                        data += ",";

                }


                }
                m++;
        }
        Log.i("countoflocation" , m+"");
        data+="]";
        if(data.length()>2)
        {
            if(data.charAt(data.length()-2)==',')
            {
                StringBuilder sb = new StringBuilder(data);
                sb.deleteCharAt(data.length()-2);
                data=sb.toString();

            }
        }
        Log.i("Loggy-data",data);

        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
        editor.putString("dataForHotelRequst",data);
        editor.commit();
        return data;
    }

    public String countNIghts(String date,int i)
    {
        int k=1;
        String dateENd=date;
        String curntLOcation = locations[i];
        int b=1;
        boolean ifEqual=false;
        while ((k+i<locations.length)&&curntLOcation.equals(locations[i+k]))
            {
                ifEqual=true;
                dateENd = increaseDate(daysDates[i+b]);
                k++;
                b++;


            }
            if(ifEqual)
                 return (k+i)+"_"+dateENd;
        else
            return (i+1)+"_"+increaseDate(daysDates[i+b]);
    }
    private void printSharedPreferences() {
        SharedPreferences NewGroupSp=this.getSharedPreferences("NewGroup",MODE_PRIVATE);
        Map<String, ?> allEntries = NewGroupSp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.i("MAPP", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    public String increaseDate(String date){
        String[] daysArray=date.split("-");
        int[] daysArrayInt=new int[daysArray.length];

        for(int i=0;i<daysArray.length;i++)
        {
            daysArrayInt[i]= Integer.parseInt(daysArray[i]);
        }
        int DaysinMonth[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int daysMOinth = daysArrayInt[1];
        //Log.i("daysmooos",daysMOinth+"");
        if (daysArrayInt[daysArrayInt.length-1]+1>DaysinMonth[daysMOinth+1]) {
            if(daysMOinth>12)
                daysMOinth=1;
            else
                daysMOinth++;
            daysArrayInt[1]=daysMOinth;
            daysArrayInt[daysArrayInt.length - 1] = 1;
        }
        else
            daysArrayInt[daysArrayInt.length-1]++;


        //{
        //}
      /*  else
        {
            daysArrayInt[daysArrayInt.length-1]=1;
        }
*/
        for(int i=0;i<daysArray.length;i++)
        {

            daysArray[i]= daysArrayInt[i]+"";

        }
        String finalDate="";
        if(daysArrayInt[1]<10&&daysArrayInt[2]<10)
             finalDate =daysArray[0]+"-0"+daysArray[1]+"-0"+daysArray[2];
        else if(daysArrayInt[1]<10&&daysArrayInt[0]>=10)
             finalDate= daysArray[0]+"-0"+daysArray[1]+"-"+daysArray[2];
        else if(daysArrayInt[1]>=10&&daysArrayInt[0]<10)
            finalDate= daysArray[0]+"-"+daysArray[1]+"-0"+daysArray[2];
        else
            finalDate= daysArray[0]+"-"+daysArray[1]+"-"+daysArray[2];
        return finalDate;




    }






    public void hotelsByDaysRequest(){
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/hotels/request/11", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responsak",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responsak","Error");


            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();

                    params2.put("nights", makeArrayRequestString());
                Log.i("days10",params2.toString());

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(NewGroupHotelsServiceActivity.this).addToRequestQueue(sr);

    }

 private int getAssignmentDuration(String dateFrom,String dateTo)
    {
        String []dateFrm =dateFrom.split("-");
       // int yearForDate1 = Integer.parseInt(dateFrm[0]);
        int mounthForDate1 = Integer.parseInt(dateFrm[1]);
        int dayForDate1 = Integer.parseInt(dateFrm[2]);
        Date oldDate = new Date(117, mounthForDate1, dayForDate1);
        Calendar c1 = Calendar.getInstance();
        c1.set(2017,mounthForDate1,dayForDate1);
        String []dateToo =dateTo.split("-");
        //int yearForDate2 = Integer.parseInt(dateToo[0]);
        int mounthForDate2 = Integer.parseInt(dateToo[1]);
        int dayForDate2 = Integer.parseInt(dateToo[2]);
        Date newDate = new Date(117, mounthForDate2, dayForDate2);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,mounthForDate2,dayForDate2);

        Log.i("daysmonuth", c1.getTime()+","+c2.getTime());
       if (oldDate.compareTo(newDate) > 0) {
            c1.setTime(newDate);
            c2.setTime(oldDate);
        }
        int year = 0;
        int month = 0;
        int days = 0;
        boolean doneMonth = false;
        boolean doneYears = false;
        while (c1.before(c2)) {
            //log.debug("Still in Loop");
            if (!doneYears) {
                c1.add(Calendar.YEAR, 1);
                year++;
            }
            if (c1.after(c2) || doneYears) {
                if (!doneYears) {
                    doneYears = true;
                    year--;
                    c1.add(Calendar.YEAR, -1);
                }
                if (!doneMonth) {
                    c1.add(Calendar.MONTH, 1);
                    month++;
                }
                if (c1.after(c2) || doneMonth) {
                    if (!doneMonth) {
                        doneMonth = true;
                        month--;
                        c1.add(Calendar.MONTH, -1);
                    }
                    c1.add(Calendar.DATE, 1);
                    days++;
                    if (c1.after(c2)) {
                        days--;
                    }
                    // this will not be executed
                    if (days == 31 || month==12) {
                        break;
                    }
                }
            }
        }
        //
        int DaysinMonth[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int daysOfTrip = 365*year + 30 * month + days+1 ;
        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
        editor.putInt("daysoftrip" , daysOfTrip);
        editor.commit();
        Log.i("daysofnumberrr" , String.valueOf(daysOfTrip));
        return daysOfTrip;
    }

}
