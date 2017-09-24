package com.snapgroup.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Adapters.JoinListViewAdapter;
import com.snapgroup.Classes.JoinPersonInformation;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class JoinGroupActivity extends AppCompatActivity implements TokenCompleteTextView.TokenListener<Person> {
    Button joinGroupdButtonDialog;
    public TabLayout tabLayout;
    public GridView gridJOin;
    public ArrayList<String>namesJoin;
    public Button goingWithButton2;
    public int io;
    public EditText txtDate;
    public  EditText firstnametext,lastnameText,age,gender,passport_id,anotherperson_date,anotherperson_exp,anotherperson_issue;

    public  EditText anitherfirstNameJoin,anitherLastNameJoin,anitherAgeJoin,anithergenderJoin,anitherpassportJoin;

    public Button joinWithCompanies;

    public EditText expEdittext,issue;
    ContactsCompletionView completionView,acountpersons;
    Person[] people;
    public ArrayList<JoinPersonInformation> another_personss;
    ArrayList <Person> persons,anothersPersons;

    ArrayAdapter<Person> adapter,adapter7;
    public  TextView groupLis3tActivity_invitesFilterTv;
    public TextView groupListActivity_invitesFilterTv,groupListActivity_myGroupsTv;

    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();

    public ViewPager viewPager;

    public static String str;
    public ArrayList<String> id_persons;
    public static ArrayList<Integer> images;
    private int mYear, mMonth, mDay, mHour, mMinute;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);


        /*

         */


        final ScrollView joinMore = (ScrollView) findViewById(R.id.deta3ilsScrollView);
        final ScrollView joinOne = (ScrollView) findViewById(R.id.detailsScrollView);
        groupListActivity_myGroupsTv = (TextView)findViewById(R.id.groupListActivity_myGroupsTv);
        groupListActivity_invitesFilterTv = (TextView)findViewById(R.id.groupListActivity_invitesFilterTv);
        groupLis3tActivity_invitesFilterTv = (TextView)findViewById(R.id.groupLis3tActivity_invitesFilterTv);
        final TextView addAnthoerPerson = (TextView)findViewById(R.id.addAnthoerPerson);
        goingWithButton2 = (Button)findViewById(R.id.joinGroupdButto12nDialog11);
        anotherperson_date = (EditText)findViewById(R.id.another_person_date_birthdayET);
        anotherperson_issue = (EditText)findViewById(R.id.another_person_issue_ET);
        anotherperson_exp = (EditText)findViewById(R.id.another_person_exp_Et);

        String [] mounthes = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Button anotherPersonDateBt = (Button)findViewById(R.id.another_person_date_birthdayBT);
        anotherPersonDateBt.setText(mYear + "\n"  + mounthes[mMonth]);

        anotherPersonDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(JoinGroupActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        anotherperson_date.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });
        Button anotherPersonExpBt = (Button)findViewById(R.id.another_person_exp_BT);
        anotherPersonExpBt.setText(mYear + "\n"  + mounthes[mMonth]);

        anotherPersonExpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(JoinGroupActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        anotherperson_exp.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });
        Button anotherPersonIssueBt = (Button)findViewById(R.id.another_person_issue_Bt);
        anotherPersonIssueBt.setText(mYear + "\n"  + mounthes[mMonth]);

        anotherPersonIssueBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(JoinGroupActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        anotherperson_issue.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });





        groupLis3tActivity_invitesFilterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinOne.setVisibility(View.VISIBLE);
                joinMore.setVisibility(View.GONE);

            }
        });

        groupListActivity_myGroupsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                joinOne.setVisibility(View.GONE);
                joinMore.setVisibility(View.VISIBLE);
            }
        });


        gridJOin = (GridView)findViewById(R.id.gridViewJoin);
        namesJoin = new ArrayList<String>();

         txtDate = (EditText)findViewById(R.id.editText12);

        firstnametext = (EditText) findViewById(R.id.first_name_text);
      lastnameText = (EditText) findViewById(R.id.last_name_text);


        Button settings_service_tvTo = (Button)findViewById(R.id.settings_sssservice_tvTo);

        final Button dateExp = (Button)findViewById(R.id.dateExp);
        final Button dateIssue = (Button)findViewById(R.id.dateIssue);

        dateExp.setText(mYear + "\n"  + mounthes[mMonth]);
         expEdittext = (EditText)findViewById(R.id.expEdittext);

        dateExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(JoinGroupActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        expEdittext.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });


         issue = (EditText)findViewById(R.id.issueEdittext);






        //    public  EditText firstnametext,lastnameText,age,gender,passport_id;

        age = (EditText)findViewById(R.id.ageText);
        gender = (EditText)findViewById(R.id.genderText);
        passport_id = (EditText)findViewById(R.id.passport_id);



        dateIssue.setText(mYear + "\n"  + mounthes[mMonth]);
        dateIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(JoinGroupActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        issue.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });

        settings_service_tvTo.setText(mYear + "\n"  + mounthes[mMonth]);
        settings_service_tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(JoinGroupActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                txtDate.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                }
                }, mYear, mMonth, mDay);
                dpd.show();
            }

        });




        myMemberINfo();



        persons=new ArrayList<Person>();
        anothersPersons=new ArrayList<Person>();



        images = new ArrayList<Integer>();
        images.add(R.drawable.person1);
        images.add(R.drawable.person2);
        images.add(R.drawable.person3);
        images.add(R.drawable.person4);
        images.add(R.drawable.person5);
        images.add(R.drawable.person9);
        images.add(R.drawable.person7);
        images.add(R.drawable.person10);
        images.add(R.drawable.hosen_profile);
        images.add(R.drawable.paris);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);
        images.add(R.drawable.person1);



        adapter = new FilteredArrayAdapter<Person>(this, R.layout.person_layout, persons) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.person_layout, parent, false);
                }

                Person p = getItem(position);
                ImageView imageView = convertView.findViewById(R.id.imageForAutoComplet);
                Picasso.with(JoinGroupActivity.this).load(images.get(position)).into(imageView);

                ((TextView)convertView.findViewById(R.id.name)).setText(p.getName());
                ((TextView)convertView.findViewById(R.id.email)).setText(p.getEmail());
                ((TextView)convertView.findViewById(R.id.id_person)).setText(p.getId());

                return convertView;
            }

            @Override
            protected boolean keepObject(Person person2, String mask) {
                mask = mask.toLowerCase();
                return person2.getName().toLowerCase().startsWith(mask) || person2.getEmail().toLowerCase().startsWith(mask);
            }
        };


        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final  String str=charSequence.toString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMyGroupsRequest(str);
                    }
                },100);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        id_persons=new ArrayList<String>();

        completionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
               /* Log.i("onTextChanged",charSequence.toString());
                final  String str=charSequence.toString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMyGroupsRequest(str);
                    }
                },1000);*/
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                str=charSequence.toString();
                io=i;
                if(charSequence.toString().equals(""))
                {

                }
                else {
                    Log.i("onTextChanged", charSequence.toString());

                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                persons.clear();

                completionView.setAdapter(adapter);
                Log.i("afterTextChanged", completionView.getText().toString());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMyGroupsRequest(str);
                    }
                }, 100);



            }

        });
        anitherfirstNameJoin=  (EditText)findViewById(R.id.anitherfirstNameJoin);
        anitherLastNameJoin=  (EditText)findViewById(R.id.anitherLastNameJoin);
        anitherAgeJoin=  (EditText)findViewById(R.id.anitherAgeJoin);
        anithergenderJoin=  (EditText)findViewById(R.id.anitherGenderJoin);
        anitherpassportJoin=  (EditText)findViewById(R.id.anitherPassportJoin);



        another_personss=new ArrayList<JoinPersonInformation>();

        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        addAnthoerPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                another_personss.add(new JoinPersonInformation(anitherfirstNameJoin.getText().toString(),
                        anitherLastNameJoin.getText().toString(),
                        anitherAgeJoin.getText().toString(),
                        anithergenderJoin.getText().toString(),
                        anitherpassportJoin.getText().toString(),
                        anotherperson_date.getText().toString(),
                        anotherperson_exp.getText().toString()
                        ,anotherperson_issue.getText().toString()));


                anitherLastNameJoin.setText("");
                anitherAgeJoin.setText("");
                anithergenderJoin.setText("");
                anitherpassportJoin.setText("");
                anotherperson_date.setText("");
                anotherperson_exp.setText("");
                anotherperson_issue.setText("");
                anitherfirstNameJoin.setText("");

                final ListView anotherPersonList = (ListView)findViewById(R.id.anotherPersonList);
                final JoinListViewAdapter[] joinadappter = {new JoinListViewAdapter(JoinGroupActivity.this, another_personss)};
                anotherPersonList.setAdapter(joinadappter[0]);
                anotherPersonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        View joinDialog12 = LayoutInflater.from(JoinGroupActivity.this).inflate(R.layout.dialo_joinnnnn, null);
                        final TextView removeBT = (TextView) joinDialog12.findViewById(R.id.removerTextView);
                        final TextView editTextView = (TextView) joinDialog12.findViewById(R.id.editTextView);
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinGroupActivity.this);
                        builder.setView(joinDialog12);
                        final AlertDialog dialog4 = builder.create();
                        dialog4.show();
                        removeBT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                another_personss.remove(i);
                                joinadappter[0] = new JoinListViewAdapter(JoinGroupActivity.this,another_personss);
                                anotherPersonList.setAdapter(joinadappter[0]);
                                dialog4.hide();
                            }
                        });
                        editTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                View dialog_edit = LayoutInflater.from(JoinGroupActivity.this).inflate(R.layout.dialog_edittt, null);
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinGroupActivity.this);
                                builder.setView(dialog_edit);
                                final AlertDialog dilog6 = builder.create();
                                dilog6.show();
                            }
                        });



                    }
                });


            }
        });

        if (savedInstanceState == null) {
            //completionView.setPrefix("");

        }






        //MultiAutoCompleteTextView mt=(MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);

       // mt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.custom_tab_item, android.R.layout.simple_dropdown_item_1line,str);

        //mt.setThreshold(1);
        //mt.setAdapter(adp);

        joinGroupdButtonDialog = (Button)findViewById(R.id.joinGroupdButtonDialog);





        joinGroupdButtonDialog.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    if (firstnametext.getText().toString().equals("")==true||
                            lastnameText.getText().toString().equals("")==true||
                            age.getText().toString().equals("")==true||
                            gender.getText().toString().equals("")==true||
                            passport_id.getText().toString().equals("")==true||
                            expEdittext.getText().toString().equals("")==true||
                            issue.getText().toString().equals("")==true||
                            txtDate.getText().toString().equals("")==true)
                    {
                        Toast.makeText(JoinGroupActivity.this, "you Must Fill All information", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        View joinDialog = LayoutInflater.from(JoinGroupActivity.this).inflate(R.layout.dialog_join, null);
                        final Button goingWithButton = (Button) joinDialog.findViewById(R.id.goingWithButton);
                        final Button joinButton = (Button) joinDialog.findViewById(R.id.joinButton);
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinGroupActivity.this);
                        builder.setView(joinDialog);

                        final AlertDialog dialog4 = builder.create();
                        dialog4.show();
                        UpdatePassport();

                        goingWithButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                joinMore.setVisibility(View.VISIBLE);
                                joinOne.setVisibility(View.GONE);
                                dialog4.hide();


                            }
                        });
                        joinButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog4.hide();
                                Intent i = new Intent(JoinGroupActivity.this, GroupListActivity.class);
                                startActivity(i);

                            }


                        });

                    }
                    }
            });

        goingWithButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences settings2=getSharedPreferences("SnapGroup",MODE_PRIVATE);
                String id_user22 = settings2.getString("gId","-1");

                if (another_personss.size()==0)
                {

                }
                else {
                    String strr = "[";
                    for (int i = 0; i < another_personss.size(); i++)
                        strr = strr + "{\"first_name\":\"" + another_personss.get(i).getFirstname()
                                + "\"," + "\"group_id\":\"" + id_user22 + "\"," +
                                "" + "\"last_name\":\"" + another_personss.get(i).getLastname() + "\"," +
                                "\"" + "passport_id\":\"" + another_personss.get(i).getPassport_id() + "\"," +
                                "\"" + "birth_date\":\"" + another_personss.get(i).getDateBirthday() + "\"," +
                                "\"" + "issue_date\":\"" + another_personss.get(i).getIssue_date() + "\"," +
                                "\"" + "expiration_date\":\"" + another_personss.get(i).getExp_date() + "\"" + "},";

                    StringBuilder sb = new StringBuilder(strr);
                    sb.deleteCharAt(strr.length() - 1);
                    strr = sb.toString();
                    strr = strr + "]";
                    Log.i("qweqweqwe", strr);

                    joinWIthPeople(strr);
                }


                String str =completionView.getStringForAll();
                if (str.equals("")) {
                    Toast.makeText(JoinGroupActivity.this, "isempty elstic search", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("stringssss", str);
                    invitePepole(str);
                    completionView.clear();
                }
                Intent i =new Intent(JoinGroupActivity.this,GroupListActivity.class);
                startActivity(i);

            }
        });

    }

  /*  private void JoinAlone() {

        SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        String str = "{\"gender\":\""+ gender.getText().toString()+"\""+","+
                "\"member_id\":\""+id_user2+"\""+
                ","+"\"passport_id\":\""+passport_id.getText().toString()+
                "\""+","+"\"exp_date\":\""+expEdittext.getText().toString()+
                "\""+","+"\"issue_date\":\""+issue.getText().toString()+"\","+
                "\"birthdate\":\""+txtDate.getText().toString()+"\""+","+
                "\"first_name\":\""+firstnametext.getText().toString()+"\""+","+
                "\"last_name\":\""+lastnameText.getText().toString()+"\""+","+
                "\"age\":\""+age.getText().toString()+"\"}";


        Log.i("asdasdas" , str);
    }
*/

    public void UpdatePassport(){


        SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
        String id_user2 = settings2.getString("id","-1");
        Log.i("responeseee" , "http://172.104.150.56/api/joingroupdetails/"+id_user2);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/joingroupdetails/"+id_user2, new Response.Listener<String>() {
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
                SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
                String id_user2 = settings2.getString("id","-1");
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("gender", gender.getText().toString());
                params2.put("member_id", id_user2);
                params2.put("passport_id",passport_id.getText().toString());
                params2.put("exp_date", expEdittext.getText().toString());
                params2.put("issue_date",issue.getText().toString());
                params2.put("birth_date", txtDate.getText().toString());
                params2.put("first_name", firstnametext.getText().toString());
                params2.put("last_name", lastnameText.getText().toString());
                params2.put("age", age.getText().toString());
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(JoinGroupActivity.this).addToRequestQueue(sr);

    }

    @Override
    public void onTokenAdded(Person token) {

    }

    @Override
    public void onTokenRemoved(Person token) {

    }





    public void getMyGroupsRequest(final String pharse){
        String url = "http://172.104.150.56/api/members/search";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        persons.clear();

                        Log.i("groupsGet1",response.toString());

                        for(int i=0;i<response.length();i++)
                        {
                            try {
                                persons.add(new Person(response.getJSONObject(i).getJSONObject("profile").getString("first_name").toString()+
                                        response.getJSONObject(i).getJSONObject("profile").getString("last_name").toString(),
                                        response.getJSONObject(i).getString("email").toString(),response.getJSONObject(i).getJSONObject("profile").getString("id").toString()));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter = new FilteredArrayAdapter<Person>(JoinGroupActivity.this, R.layout.person_layout, persons) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                if (convertView == null) {

                                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                                    convertView = l.inflate(R.layout.person_layout, parent, false);
                                }

                                Person p = getItem(position);
                                ImageView imageView = convertView.findViewById(R.id.imageForAutoComplet);

                                Picasso.with(JoinGroupActivity.this).load(images.get(position)).into(imageView);

                                ((TextView)convertView.findViewById(R.id.name)).setText(p.getName());
                                ((TextView)convertView.findViewById(R.id.email)).setText(p.getEmail());
                                ((TextView)convertView.findViewById(R.id.id_person)).setText(p.getId());

                                return convertView;
                            }

                            @Override
                            protected boolean keepObject(Person person2, String mask) {
                                mask = mask.toLowerCase();
                                return person2.getName().toLowerCase().startsWith(mask) || person2.getEmail().toLowerCase().startsWith(mask);
                            }
                        };
                        completionView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1","error ");
                    }

                }){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                    params2.put("search", pharse);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };


        MySingleton.getInstance(JoinGroupActivity.this).addToRequestQueue(jsObjRequest);

    }



    public void joinWIthPeople(final String str)
    {


        SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
        String id_user = settings2.getString("id","-1");
        //api/invitefriend/{inviter_id}/{group_id}

        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/addcompanions/"+id_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responsakk",response.toString());

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

                params2.put("companions", str);
                Log.i("companions",params2.toString());

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(JoinGroupActivity.this).addToRequestQueue(sr);

    }



    public void invitePepole(final String str)
    {

        SharedPreferences settings=getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String group_id = settings.getString("gId","-1");
        //api/invitefriend/{inviter_id}/{group_id}
        SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
        String id_user = settings2.getString("id","-1");
        //api/invitefriend/{inviter_id}/{group_id}

        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/invitefriend/"+id_user+"/"+group_id, new Response.Listener<String>() {
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

                params2.put("invitees", str);
                Log.i("days10",params2.toString());

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(JoinGroupActivity.this).addToRequestQueue(sr);

    }
    public void myMemberINfo(){
        SharedPreferences settings2=getSharedPreferences("UserLog",MODE_PRIVATE);
        String id_user = settings2.getString("id","-1");
        String url = "http://172.104.150.56/api/members/"+id_user;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObjec2t = response.getJSONObject("success");
                            JSONObject profile = jsonObjec2t.getJSONObject("profile");
                            firstnametext.setText(profile.getString("first_name").toString());
                            lastnameText.setText(profile.getString("last_name").toString());
                            firstnametext.setSelection(firstnametext.getText().toString().length());
                           /* if (profile.getString("gender")!=null)
                                gender.setText(profile.getString("gender").toString());
                            if (profile.getString("age")!=null)
                                age.setText(profile.getString("age").toString());*/
                            if (jsonObjec2t.getJSONObject("passport")!=null)
                            {
                                passport_id.setText(jsonObjec2t.getJSONObject("passport").getString("passport_id").toString());
                                txtDate.setText(jsonObjec2t.getJSONObject("passport").getString("birth_date").toString());
                                issue.setText(jsonObjec2t.getJSONObject("passport").getString("issue_date").toString());
                                expEdittext.setText(jsonObjec2t.getJSONObject("passport").getString("exp_date").toString());


                            }

                            Log.i("ASda0" , profile.toString());
                        }


                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });


        MySingleton.getInstance(JoinGroupActivity.this).addToRequestQueue(jsObjRequest);

    }
}
