package com.snapgroup.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.hitomi.cmlibrary.CircleMenu;
import com.snapgroup.Adapters.MemberInviteListAdapter;
import com.snapgroup.Classes.MemberInviteItem;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Fragments.DatePickeerFragment2;
import com.snapgroup.Fragments.DatePickerFragment;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewGroupProfile2Activity extends AppCompatActivity {

    ListView list;
    Spinner maxMembersSp;
    ListView inviteMembersLv;
    TextView btNext,dateFromTv,dateToTv;
    public static final int PICKFILE_REQUEST_CODE=221;
    ArrayList<MemberInviteItem> membersArrayForAdapter;
    JSONArray jsonArray;
    Button chooseCsvBt,inviteBt,addNewMemberBt;
    ArrayAdapter<String> arrayAdapter;
    CircleMenu circleMenu;
    ProgressDialog   contactsPd;
    public ProgressDialog pd;
    String[] lastName ={
            "  Anglena basdhkjlk",
            "   David lasadasd"
    };
    // the photos of the memebes assistant
    Integer[] imgid={
            R.drawable.girl1,
            R.drawable.person3
    };
    int maxMembers;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_profile2);
        //printSharedPreferences();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
        membersArrayForAdapter=new ArrayList<MemberInviteItem>();
       /* inviteMembersLv=(ListView)findViewById(R.id.inviteMembersLv);
        inviteMembersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("memberInviteId",membersArrayForAdapter.get(i).id);
            }
        });*/
       /* circleMenu=(CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setVisibility(View.GONE);
        circleMenu.setMainMenu(Color.parseColor("#999900"),R.drawable.add_group,R.drawable.close);
        circleMenu.addSubMenu(Color.parseColor("#999900"),R.drawable.contacts);
        circleMenu.addSubMenu(Color.parseColor("#999900"),R.drawable.gmail_icon);
        circleMenu.addSubMenu(Color.parseColor("#999900"),R.drawable.sms_logo);
        circleMenu.addSubMenu(Color.parseColor("#999900"),R.drawable.facebook);
        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {

            }
        });*/
        AssistantsAdapter adapter=new AssistantsAdapter(this, lastName, imgid);
        dateFromTv=(TextView)findViewById(R.id.settingsActiivty_dateFromTv);
        dateToTv=(TextView)findViewById(R.id.settingsActiivty_dateToTv);
        chooseCsvBt=(Button)findViewById(R.id.profile2Activity_chooseCsvBt);
        addNewMemberBt=(Button)findViewById(R.id.add_single_member);
        pd=new ProgressDialog(NewGroupProfile2Activity.this);

        addNewMemberBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMember();
            }
        });
        inviteBt=(Button)findViewById(R.id.profile2Activity_inviteMembers);
        inviteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View inviteOptionsView= LayoutInflater.from(NewGroupProfile2Activity.this).inflate(R.layout.invite_options_dialog,null);
                Button contactsBt=(Button)inviteOptionsView.findViewById(R.id.invite_options_calendarBt);
                Button membersBt=(Button)inviteOptionsView.findViewById(R.id.invite_options_MembersBt);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builder.setView(inviteOptionsView);
                builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("sfdgh","fghjk");
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                contactsBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.hide();
                        arrayAdapter= new ArrayAdapter<String>(NewGroupProfile2Activity.this, android.R.layout.select_dialog_singlechoice);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // code runs in a thread
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            contactsPd=new ProgressDialog(NewGroupProfile2Activity.this);
                                            contactsPd.setTitle("Wait Please..");
                                            contactsPd.show();
                                            getContacts();
                                        }
                                    });
                                } catch (final Exception ex) {
                                    Log.i("---","Exception in thread");
                                }
                            }
                        }).start();

                    }
                });
                membersBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                        requestAllMembers();
                    }
                });

                // requestAllMembers();
                /*       Get Contacts

                pd=new ProgressDialog(NewGroupProfile2Activity.this);
                pd.setMessage("Wait please.. ");
                pd.show();
                pd.show();
                arrayAdapter= new ArrayAdapter<String>(NewGroupProfile2Activity.this, android.R.layout.select_dialog_singlechoice);
                getContacts();*/


            }
        });
        jsonArray=new JSONArray();
        chooseCsvBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCSVFile();
            }
        });

        maxMembersSp=(Spinner) findViewById(R.id.profile2Activity_maxMembersSp);
        maxMembersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                maxMembers= Integer.parseInt(maxMembersSp.getSelectedItem().toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        list.setAdapter(adapter);
        btNext=(TextView)findViewById(R.id.profile2Activity_nextBt);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NewGroupProfile2Activity.this
                ,NewGroupServicesActivity.class);
                updateSharedPreferences();
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }

    private void addNewMember() {
        View addNewMemberView= LayoutInflater.from(NewGroupProfile2Activity.this).inflate(R.layout.add_new_member_layout,null);
        final EditText phoneNumberEt = (EditText) addNewMemberView.findViewById(R.id.phoneNumberEt);
        AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
        builder.setView(addNewMemberView);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void requestAllMembers(){
        String url = "http://172.104.150.56/api/members";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray membersArray = response;
                        for(int i=0;i<response.length();i++)
                        {
                            try {
                                JSONObject profile=response.getJSONObject(i).getJSONObject("profile");
                                if(profile.getString("profile_image")==null||profile.getString("profile_image").equals(""))
                                    membersArrayForAdapter.add(i,new MemberInviteItem("http://www.wiki.sc4devotion.com/images/6/62/Wiki_no_image.png",profile.getString("first_name"),profile.getString("last_name"),profile.getString("id")));
                                else
                                    membersArrayForAdapter.add(i,new MemberInviteItem(profile.getString("profile_image"),profile.getString("first_name"),profile.getString("last_name"),profile.getString("id")));

                               Log.i("memberI",membersArrayForAdapter.get(i).toString());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }





                        MemberInviteListAdapter adapter=new MemberInviteListAdapter(NewGroupProfile2Activity.this, membersArrayForAdapter);
                      //  inviteMembersLv.setAdapter(adapter);
                        View membersListView= LayoutInflater.from(NewGroupProfile2Activity.this).inflate(R.layout.member_list_dialog,null);
                        final ListView membersLv = (ListView) membersListView.findViewById(R.id.members_lv_invite);
                        membersLv.setAdapter(adapter);
                        membersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ImageView checkIv=view.findViewById(R.id.checkIv);
                                if(checkIv.getVisibility()==View.GONE)
                                checkIv.setVisibility(View.VISIBLE);
                                else
                                    checkIv.setVisibility(View.GONE);

                            }
                        });
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                        builder.setView(membersListView);
                        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("sfdgh","fghjk");
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                           Log.i("memberI","error");
                    }

                });


        MySingleton.getInstance(NewGroupProfile2Activity.this).addToRequestQueue(jsObjRequest);

    }
    private void updateSharedPreferences() {
        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
        String dateFrom=dateFromTv.getText().toString();
        String dateTo=dateToTv.getText().toString();
        if(!dateFrom.equals(""))
            editor.putString("start_date",dateFrom);
        else
            editor.putString("start_date","");
        if(!dateTo.equals(""))
            editor.putString("end_date",dateTo);
        else
            editor.putString("end_date","");
        editor.putInt("max_members",maxMembers);
        editor.commit();
    }

    public void showDatePickerDialog2(View v) {
        DialogFragment newFragment = new DatePickeerFragment2();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private void validateCode(MemberInviteListAdapter adapter) {


        View membersListView= LayoutInflater.from(NewGroupProfile2Activity.this).inflate(R.layout.member_list_dialog,null);
        final ListView membersLv = (ListView) membersListView.findViewById(R.id.members_lv_invite);
        membersLv.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
        builder.setMessage("Members To Invite");
        builder.setView(membersListView);
        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void loadDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(NewGroupProfile2Activity.this);
        builderSingle.setIcon(R.drawable.logo2);
        builderSingle.setTitle("Select One Name:-");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setPositiveButton("LOAD ALL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builderInner = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builderInner.setTitle("Your Selected All the Items");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
                dialog.dismiss();
            }
        } );
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    public void getContacts() {


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        arrayAdapter.add(name);
                        Log.i("Contacts222","Name: " + name+ ", Phone No: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        contactsPd.hide();
        loadDialog();

    }

    public class AssistantsAdapter extends ArrayAdapter<String> {
        // class for Assistant group i use customlidtadapter  that extandsa from the adapter for the liostview
        //
        private final Activity context; // the activity/
        private final String[] itemname; // names of the Assistant group
        private final Integer[] imgid;// image for every one

        // constructor
        public AssistantsAdapter(Activity context, String[] itemname,Integer[] imgid) {
            super(context, R.layout.profilephoto, itemname);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.itemname=itemname;
            this.imgid=imgid;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.profilephoto, null,true); // set the id in the xml to be for every list vview

            TextView txtTitle = (TextView) rowView.findViewById(R.id.textView4);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView4);

            txtTitle.setText(itemname[position]);
            imageView.setImageResource(imgid[position]);
            return rowView; // return view for everyitem//

        };
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }
    private void printSharedPreferences() {
        SharedPreferences NewGroupSp=this.getSharedPreferences("NewGroup",MODE_PRIVATE);
        Map<String, ?> allEntries = NewGroupSp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.i("MAPP", entry.getKey() + ": " + entry.getValue().toString());
        }
    }
    private void selectCSVFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), PICKFILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICKFILE_REQUEST_CODE: {
                if (resultCode == RESULT_OK){
                    try {
                        proImportCSV(new File(data.getData().getPath()));
                    } catch (IOException e) {
                        Log.i("errorr",e.getMessage().toString());
                    }
                    Log.i("csvFile",data.getData().getPath()+"");
                }
            }
        }

    }
    private void proImportCSV(File from) throws IOException {
        /*CSVReader dataRead = new CSVReader(new FileReader(from)); // <--- This line is key, and why it was reading the wrong file

        String[] vv = null;
        while((vv = dataRead.readNext())!=null) {
            Log.i("dataa",vv[0]);
        }*/
        File fileDirectory = new File(Environment.getExternalStorageDirectory()+"/DCIM/");
// lists all the files into an array
        File[] dirFiles = fileDirectory.listFiles();

        if (dirFiles.length != 0) {
            // loops through the array of files, outputing the name to console
            for (int ii = 0; ii < dirFiles.length; ii++) {
                String fileOutput = dirFiles[ii].toString();
                Log.i("loggey",fileOutput);
            }
        }
        String Fname=from.getName();
        try
        {
            List<String> list = new ArrayList<String>();
            String line;
            FileReader reader = new FileReader(new File(Environment.getExternalStorageDirectory(),Fname));
            BufferedReader bufrdr = new BufferedReader(reader);
            line = bufrdr.readLine();
            while (line != null) {
                list.add(line);
                line = bufrdr.readLine();
            }
            bufrdr.close();
            reader.close();

            String[] array = new String[list.size()];
            list.toArray(array);
            String data="[";
            for (int i = 0; i < list.size(); i++) {
                String row=list.get(i).replace(","," ");
                String[] Sarray=row.split(" ");
                JSONObject json2= new JSONObject();
               // if(i==0)
                data+="{ \"email\": \""+Sarray[0]+"\","+"\"first_name\": \""+Sarray[1]+"\","+"\"last_name\": \""+Sarray[1]+"\","+"\"password\": \""+"123456\""+","+"\"profile_image\": \"aaaaa\" }";
                json2.put("email",Sarray[0]);
                json2.put("first_name",Sarray[1]);
                json2.put("last_name",Sarray[1]);
                json2.put("password",Sarray[1]+"1234");
                json2.put("profile_image","sfgdgsg");
                jsonArray.put(json2);
                if(i+1<list.size())
                    data+=",";
                Log.i("hihihi", row);
            }
            data+="]";
            Log.i("arrayLenght",data);
            pd.show();
            requestAddMembers(data);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject json_data = jsonArray.getJSONObject(i);

                Log.i("log_tag", "email: " + json_data.getString("email") +
                        ", first name: " + json_data.getString("profile_image"));
            }
         //   requestAddMembers();
        }
        catch (Exception e) {
            // TODO: handle exception
          //  Log.i("errory",e.getMessage().toString());
        }
    }

    private void requestAddMembers(final String data) {

        Log.i("data_abd",data);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/members/add_many/1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Send_Request","Success");
                Log.i("responsy",response);
                pd.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builder.setMessage("load csv file has Successfull")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("erroranosh","error");
                pd.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builder.setMessage("load csv file Faild!! ")
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
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("members", data);
                return new JSONObject(params2).toString().getBytes();
            }

         /*   @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImU2MjFhZTY0OTY5ODBhMmI0OTdiYzVjMzE2Y2FjYTgyOGU3ZTRiNTM1NTUzZDA2ZGZlZjg5ZWIwOGEyNDM0YmEyZTZlNzNmOGIwZGJhMmQwIn0.eyJhdWQiOiIyIiwianRpIjoiZTYyMWFlNjQ5Njk4MGEyYjQ5N2JjNWMzMTZjYWNhODI4ZTdlNGI1MzU1NTNkMDZkZmVmODllYjA4YTI0MzRiYTJlNmU3M2Y4YjBkYmEyZDAiLCJpYXQiOjE1MDA5OTA3NzYsIm5iZiI6MTUwMDk5MDc3NiwiZXhwIjoxNTAwOTkyNTc2LCJzdWIiOiI0Iiwic2NvcGVzIjpbIioiXX0.HpcPxoJUmfuYfTD-AEfYoTsiPwkPd39ZrOTRTsTll7Y-938BEKVVO2y2f7QQi4MheM2qcRLlWD4blSLJvGW_0OX2Xq-endqvny5sweGvFffO1MxDPX32WvpFhrvE52ZHWVgvCCSLw1aJjLLM89sqTKVoITDXHprI0_uFLfCvSk7TmTtvpE7PpJvKa-ZW7j763MeE-ywUGfNaxa214OlfS_UHl5UGWTQ5fhG3blnzOSmkS8u0Fy0c1-1hmkY2Ivmxk_7s5yHTYa-p9NtU0Ati1XLCOlonQdHNCkUSjWTWSwFEaiBV17M5PskR_hwc2jq79v6eHWGy0yJ-NOlrE38WiEwkT4SPTgKgvplz6bqVXFsNGWl-ZUxcz_VMSV23CGD0oImSleZdgp7sMBSFDvmasV0d3wXjXau0Ft8Q2LYtHtC75dYXkUOUkSET3yQg07GG4JFbOK_LWldUIn9GezYtQbvTQe_jxHm42go2yK1meLXxSr0YxpR8sRArkPIrr0kTOpluREVAL0WmHyo8nZEEwtZeIjbi862KTmAdYGaGC709duuasLspsxyJKUifR6Rab8V_Pb2RgNtKy-RJsIpcoabewZSatVM0xzKmpiZ15A0qgY6FUugBEhFJ-V9OeT8wQWLMVkWEx13zvY8Lc0hgrOkFoSFwGooqYGTBeSbzOc4");
                return params;
            }*/

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(NewGroupProfile2Activity.this).addToRequestQueue(sr);

    }



    public void registerRequest(){
        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/members/add_many/1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responsy",response);
                /*pd.hide();
                Log.i("responsy",response);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builder.setMessage("Register Successfully")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                Intent i = new Intent(NewGroupProfile2Activity.this,SignInActivity.class);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*pd.hide();
                Log.i("responsy","error");

                AlertDialog.Builder builder = new AlertDialog.Builder(NewGroupProfile2Activity.this);
                builder.setMessage("Register faild , it could be caused by wrong inputs \n\n" +
                        "Or user already exist")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                Log.i("myError",error.getMessage()+" ");*/
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                String data="";
                return null;
            }

         /*   @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImU2MjFhZTY0OTY5ODBhMmI0OTdiYzVjMzE2Y2FjYTgyOGU3ZTRiNTM1NTUzZDA2ZGZlZjg5ZWIwOGEyNDM0YmEyZTZlNzNmOGIwZGJhMmQwIn0.eyJhdWQiOiIyIiwianRpIjoiZTYyMWFlNjQ5Njk4MGEyYjQ5N2JjNWMzMTZjYWNhODI4ZTdlNGI1MzU1NTNkMDZkZmVmODllYjA4YTI0MzRiYTJlNmU3M2Y4YjBkYmEyZDAiLCJpYXQiOjE1MDA5OTA3NzYsIm5iZiI6MTUwMDk5MDc3NiwiZXhwIjoxNTAwOTkyNTc2LCJzdWIiOiI0Iiwic2NvcGVzIjpbIioiXX0.HpcPxoJUmfuYfTD-AEfYoTsiPwkPd39ZrOTRTsTll7Y-938BEKVVO2y2f7QQi4MheM2qcRLlWD4blSLJvGW_0OX2Xq-endqvny5sweGvFffO1MxDPX32WvpFhrvE52ZHWVgvCCSLw1aJjLLM89sqTKVoITDXHprI0_uFLfCvSk7TmTtvpE7PpJvKa-ZW7j763MeE-ywUGfNaxa214OlfS_UHl5UGWTQ5fhG3blnzOSmkS8u0Fy0c1-1hmkY2Ivmxk_7s5yHTYa-p9NtU0Ati1XLCOlonQdHNCkUSjWTWSwFEaiBV17M5PskR_hwc2jq79v6eHWGy0yJ-NOlrE38WiEwkT4SPTgKgvplz6bqVXFsNGWl-ZUxcz_VMSV23CGD0oImSleZdgp7sMBSFDvmasV0d3wXjXau0Ft8Q2LYtHtC75dYXkUOUkSET3yQg07GG4JFbOK_LWldUIn9GezYtQbvTQe_jxHm42go2yK1meLXxSr0YxpR8sRArkPIrr0kTOpluREVAL0WmHyo8nZEEwtZeIjbi862KTmAdYGaGC709duuasLspsxyJKUifR6Rab8V_Pb2RgNtKy-RJsIpcoabewZSatVM0xzKmpiZ15A0qgY6FUugBEhFJ-V9OeT8wQWLMVkWEx13zvY8Lc0hgrOkFoSFwGooqYGTBeSbzOc4");
                return params;
            }*/

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(NewGroupProfile2Activity.this).addToRequestQueue(sr);

    }
}
