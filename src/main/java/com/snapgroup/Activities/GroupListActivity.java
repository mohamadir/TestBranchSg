package com.snapgroup.Activities;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.snapgroup.Adapters.GroupLIstAdapter2;
import com.snapgroup.Classes.CircleImage;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Fragments.DetailsF;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.Models.InboxMessage;
import com.snapgroup.Models.NotificationItem;
import com.snapgroup.R;
import com.snapgroup.Services.NotificationService;
import com.snapgroup.Services.NotificationService2;
import com.snapgroup.Tests.HosenShapesTests;
import com.snapgroup.Tests.imageViewPager;
import com.squareup.picasso.Picasso;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public class GroupListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Array of strings...

    public ArrayList<GroupInList> groupList;
    JazzyListView listView;
    Button groupsButton,invitationsButton;
    Button inviteBt;
    ImageView messageBt2;
    TextView groupListActivity_myGroupsTv,groupListActivity_invitesFilterTv;
    Toolbar mToolBar;
    GroupLIstAdapter2 glAdapter;
    String token;
    TourGuide mTourGuideHandler;
    public static String userId="";
    Menu menu;
    EditText expectedNumberEt;
    Button menuBt;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    Button signInBt;
    SharedPreferences notificationSP;
    TextView tv;
    ImageView testIv;
    Button groupsCount,inviteCount;

    ImageView messageBt;
   @RequiresApi(api = Build.VERSION_CODES.O)
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

       if (getIntent().getBooleanExtra("EXIT", false)) {
           finish();
       }
        groupList=new ArrayList<GroupInList>();
        Log.i("onCreateMethod","hi");
       final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
       setSupportActionBar(toolbar);
       tv= (TextView)findViewById(R.id.messageCountTv);

       final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
       actionBar.setIcon(null);
       actionBar.setTitle(null);
       actionBar.setBackgroundDrawable(null);
       FirebaseMessaging.getInstance().subscribeToTopic("NEWS");
       //FirebaseMessaging.getInstance().unsubscribeFromTopic("NEWS");
       groupsCount=(Button)findViewById(R.id.joinCount);
       inviteCount=(Button)findViewById(R.id.inviteCount);
       testIv=(ImageView)findViewById(R.id.testIv);
       Picasso.with(this).load("https://i2.cdn.turner.com/cnnnext/dam/assets/140926165711-john-sutter-profile-image-large-169.jpg").transform(new CircleImage()).into(testIv);


       getNumberOfNotifications();
       notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);

        if(notificationSP.getString("firstTime","yes").equals("yes"))
            startNotify();
       else
        {
            SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
            editor.putString("firstTime","No");
            editor.commit();
        }

      


       
       
       
       
        // Create the GroupInList List Adapter
       groupsButton = (Button) findViewById(R.id.groupsButton);
       invitationsButton=(Button)findViewById(R.id.invitationsButton);
/*       inviteBt = (Button) findViewById(R.id.button27);
       inviteBt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.i("clicked","im here");
               scrollToBottom();
           }
       });*/
      // groupsButton.setVisibility(View.INVISIBLE);
       invitationsButton.setVisibility(View.INVISIBLE);
       signInBt=(Button)findViewById(R.id.GroupListActivity_SignInBt);
       if(isSigned()) {
           SharedPreferences userLogInfo=this.getSharedPreferences("UserLog",MODE_PRIVATE);
           token= userLogInfo.getString("token","");
           Log.i("signedIn","ok");
           userId=userLogInfo.getString("id","");
           setNavigateViewSignIn();
         //  requsetidByToken();
       }
        //for 1.1.4+
       menuBt=(Button)findViewById(R.id.menuBt);
       messageBt2=(ImageView)findViewById(R.id.messageBt2);
       messageBt2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent browserIntent = new Intent(GroupListActivity.this, HosenShapesTests.class);
               startActivity(browserIntent);
           }
       });
       expectedNumberEt=(EditText)findViewById(R.id.expectedNumberEt);
       groupListActivity_myGroupsTv = (TextView)findViewById(R.id.groupListActivity_myGroupsTv);
       groupListActivity_invitesFilterTv = (TextView)findViewById(R.id.groupListActivity_invitesFilterTv);
       mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
       mToggle=new ActionBarDrawerToggle(GroupListActivity.this,mDrawerLayout,R.string.open,R.string.close);
       mDrawerLayout.addDrawerListener(mToggle);
       mToggle.syncState();
       navigationView= (NavigationView) findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);
      /*  mTourGuideHandler = TourGuide.init(this).with(TourGuide.Technique.Click)
              .setPointer(new Pointer())
               .setToolTip(new ToolTip().setTitle("My Groups").setDescription("Click here to see \nfor which groups you joinded!"))
               .setOverlay(new Overlay())
               .playOn(groupListActivity_invitesFilterTv);*/
       groupListActivity_invitesFilterTv.setTextColor(Color.parseColor("#4d4d4d"));
       groupListActivity_myGroupsTv.setTextColor(Color.parseColor("#cccccc"));

       groupListActivity_myGroupsTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (mTourGuideHandler != null) {
                   mTourGuideHandler.cleanUp();
               }
              /* SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
               editor.putInt("notification",90);
               editor.commit();
               tv.setText(""+90);*/
               groupsButton.setVisibility(View.INVISIBLE);
               invitationsButton.setVisibility(View.VISIBLE);
               groupListActivity_invitesFilterTv.setTextColor(Color.parseColor("#cccccc"));
               groupListActivity_myGroupsTv.setTextColor(Color.parseColor("#4d4d4d"));
               groupList.clear();
               glAdapter=new GroupLIstAdapter2(GroupListActivity.this,groupList);
               glAdapter.notifyDataSetChanged();

               getInvitedGroupsRequest();


           }
       });
       messageBt=(ImageView)findViewById(R.id.messageBt);
       messageBt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               clearNumOfNotification();
               Intent i=new Intent(GroupListActivity.this, MemberInboxActivity.class);
               startActivity(i);
           }
       });

       groupListActivity_invitesFilterTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (mTourGuideHandler != null) {
                   mTourGuideHandler.cleanUp();
               }
               /*mTourGuideHandler = TourGuide.init(GroupListActivity.this).with(TourGuide.Technique.Click)
                       .setPointer(new Pointer())
                       .setToolTip(new ToolTip().setTitle("Invitatines").setDescription("Click here to see \nfor which groups you invited!"))
                       .setOverlay(new Overlay())
                       .playOn(groupListActivity_myGroupsTv);*/
               groupsButton.setVisibility(View.VISIBLE);
               invitationsButton.setVisibility(View.INVISIBLE);
               groupListActivity_invitesFilterTv.setTextColor(Color.parseColor("#4d4d4d"));
               groupListActivity_myGroupsTv.setTextColor(Color.parseColor("#cccccc"));

               groupList.clear();
               glAdapter=new GroupLIstAdapter2(GroupListActivity.this,groupList);
               glAdapter.notifyDataSetChanged();
               getMyGroupsRequest();


           }
       });
       signInBt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(signInBt.getText().equals("SIGN IN")) {
                   Intent i = new Intent(GroupListActivity.this, SignInActivity.class);
                   startActivity(i);
               }
               else{
                   setNavigateViewSignOut();
                   signOut();}
           }
       });
       if(isSigned())
           signInBt.setText("SIGN OUT");
       else {
           Log.i("signed","im here");
           signInBt.setText("SIGN IN");

       }
        listView = (JazzyListView) findViewById(R.id.grouoLv1);
       //listView.setItemClick
       listView.setDivider(null);
       listView.setDividerHeight(0);
        listView.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("group_func_onclick", "clickedd");
                SharedPreferences.Editor editor=getSharedPreferences("SnapGroup",MODE_PRIVATE).edit();
                Log.i("GROOPY2",groupList.get(i).getImage());
                editor.putString("gImage", groupList.get(i).getImage());
                editor.putString("gDescription", groupList.get(i).getDescritption());
                editor.putString("gDestination", groupList.get(i).getDestination());
                editor.putString("gId", String.valueOf(groupList.get(i).get_id()));
                editor.putString("gOrigin", groupList.get(i).getOrigin());
                editor.putString("gStart", groupList.get(i).getStart_date());
                editor.putString("gEnd", groupList.get(i).getEnd_date());
                editor.putString("gTitle", groupList.get(i).getTitle());
                editor.putString("max_members", String.valueOf(groupList.get(i).getGrpuMax()));
                editor.commit();
                Intent intent=new Intent(GroupListActivity.this,NewGroupDetailsActitivty.class);
                startActivity(intent);
            }
        });

        String type=getIntent().getStringExtra("date");
        String dateFrom=getIntent().getStringExtra("dateFrom");
        String dateTo=getIntent().getStringExtra("dateTo");
       menuBt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mDrawerLayout.openDrawer(Gravity.LEFT);
           }
       });

      /* getGroupsByHotelsRequest(dateFrom,dateTo);
        if(!(type==null || type.equals(""))) {
            if(type.equals("ok")) {
                getGroupsByHotelsRequest(dateFrom, dateTo);
            }
            else

        }*/
       getMyGroupsRequest();
       getInviteCount();

       listView.smoothScrollToPosition(5);
       if(getIntent()!=null)
           if((getIntent().getExtras()!=null) &&getIntent().getExtras().getString("Intent").equals("Finish"))
           {
               scrollToBottom();
           }



        /*
        *
        * Copy here
        *
        * */

          overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startNotify() {
        Log.i("serviceNoti","startNotify");
        Intent intent = new Intent(GroupListActivity.this, NotificationService2.class);
        startService(intent);
        new Thread(new NotificationService2(getBaseContext())).start();

        int NotCount=notificationSP.getInt("notification",0);
        Log.i("NotCount",""+NotCount);
        ShortcutBadger.applyCount(getBaseContext(), NotCount);
        SharedPreferences notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
        tv.setText(notificationSP.getInt("notification",0)+"");
        notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("notificationSP",sharedPreferences.getInt("notification",0)+"");
                    int NotCount=sharedPreferences.getInt("notification",0);
                    if(NotCount<=99) {
                        tv.setTextSize(10);
                        tv.setText("" + NotCount);
                    }
                    else
                    {
                        tv.setTextSize(8);
                        tv.setText("99+");
                    }
                }
            }
        });

    }

    private void scrollToBottom() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void run() {
                try {
                    Thread.sleep(3000);
                    listView.smoothScrollToPosition(listView.getScrollBarSize());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void setNavigateViewSignOut() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View viewy=navigationView.getHeaderView(0);
        TextView name=(TextView)viewy.findViewById(R.id.nav_header_nameTv);
        TextView email=(TextView)viewy.findViewById(R.id.nav_header_email);
        TextView signOut=(TextView)viewy.findViewById(R.id.nav_header_SignOut);
        ImageView profileIv=(ImageView)viewy.findViewById(R.id.nav_header_profileImg);
        name.setText("No User Loged");
        email.setText("");
        signOut.setVisibility(View.GONE);
        Picasso.with(getBaseContext()).load("http://science.cs.upc.edu/public/web/img/avatar/no_user_logo.png").into(profileIv);
        /*TextView tv=(TextView)viewy.findViewById(R.id.textTest);
        tv.setText("hi");*/
    }
    public boolean isSigned(){
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String signed = settings.getString("isSigned","false");
        if(signed.equals("false"))
            return false;
        else
            return true;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void clearNumOfNotification(){
        SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
        editor.putInt("notification",0);
        editor.commit();
        tv.setText(""+0);
    }


    public void getMyGroupsRequest(){
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String idUser=settings.getString("id","");
        Log.i("requestUrl",idUser);

        String url = "http://172.104.150.56/api/members/"+idUser+"/groups/member";
        groupList.clear();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                    JSONArray groupsArray = response;
                    for(int i=0;i<groupsArray.length();i++)
                    {
                        try {
                            groupList.add(i, new GroupInList(Integer.parseInt(
                                    groupsArray.getJSONObject(i).getString("id").toString()),// group ID
                                    groupsArray.getJSONObject(i).getString("title").toString(),// Group Title
                                    groupsArray.getJSONObject(i).getString("description").toString(), // Group description
                                    /*groupsArray.getJSONObject(i).getString("image").toString()*/
                                    "https://media.timeout.com/images/103042848/image.jpg",// Group image
                                    groupsArray.getJSONObject(i).getString("origin").toString(),// Group origin
                                    groupsArray.getJSONObject(i).getString("destination").toString(),// Group destination
                                    groupsArray.getJSONObject(i).getString("start_date").toString(),// Group start date
                                    groupsArray.getJSONObject(i).getString("end_date").toString(),
                                    /*Integer.parseInt(groupsArray.getJSONObject(i).getString("target_members").toString())*/50));// Group end date


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("MyGroups",e.getMessage().toString()+"");

                        }
                    }
                    groupsCount.setText(""+groupList.size());

//                        Log.i("Length",groupList.get(3).getOrigin().toString());
                        GroupLIstAdapter2 glAdapter= new GroupLIstAdapter2(GroupListActivity.this,groupList);
                        // Set the adapter to the list
                        listView.setAdapter(glAdapter);
        /*listView.setTransitionEffect(JazzyHelper.TWIRL);
        listView.setTranscriptMode(JazzyHelper.CARDS);*/
                     // a7la    listView.setTransitionEffect(JazzyHelper.ZIPPER);
                     listView.setTransitionEffect(JazzyHelper.TILT);

                        listView.setScrollBarFadeDuration(100);
                        listView.smoothScrollToPosition(11);
                    Log.i("MyGroups",response.length()+"");


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1","error ");
                    }

                }){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };


        MySingleton.getInstance(GroupListActivity.this).addToRequestQueue(jsObjRequest);

    }
    public void getInvitedGroupsRequest(){
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String idUser=settings.getString("id","");
        String url = "http://172.104.150.56/api/members/"+idUser+"/groups/observer";
        Log.i("requestUrl",url);
        groupList.clear();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {JSONArray groupsArray = response;
                        Log.i("GroupListStatus",groupList.size()+"");
                        for(int i=0;i<groupsArray.length();i++)
                        {
                            try {
                                groupList.add(i, new GroupInList(Integer.parseInt(
                                        groupsArray.getJSONObject(i).getString("id").toString()),// group ID
                                        groupsArray.getJSONObject(i).getString("title").toString(),// Group Title
                                        groupsArray.getJSONObject(i).getString("description").toString(), // Group description
                                        /*groupsArray.getJSONObject(i).getString("image").toString()*/
                                        "http://www.telegraph.co.uk/content/dam/video_previews/x/5/x5cgi0ode66q6vuxezqmehmexwer6bt-xlarge.jpg",// Group image
                                        groupsArray.getJSONObject(i).getString("origin").toString(),// Group origin
                                        groupsArray.getJSONObject(i).getString("destination").toString(),// Group destination
                                        groupsArray.getJSONObject(i).getString("start_date").toString(),// Group start date
                                        groupsArray.getJSONObject(i).getString("end_date").toString(),Integer.parseInt(
                                        groupsArray.getJSONObject(i).getString("target_members").toString())));// Group end date

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.i("GroupListStatus",groupList.size()+"");
                        for(int i=0;i<groupList.size();i++)
                            Log.i("Idd",groupList.get(i).get_id()+","+groupList.get(i).getTitle());

//                        Log.i("Length",groupList.get(3).getOrigin().toString());
                        glAdapter= new GroupLIstAdapter2(GroupListActivity.this,groupList);
                        glAdapter.notifyDataSetChanged();
                        // Set the adapter to the list
                        listView.setAdapter(glAdapter);
        /*listView.setTransitionEffect(JazzyHelper.TWIRL);
        listView.setTranscriptMode(JazzyHelper.CARDS);*/
                        // a7la    listView.setTransitionEffect(JazzyHelper.ZIPPER);
                        listView.setTransitionEffect(JazzyHelper.TILT);

                        listView.setScrollBarFadeDuration(100);
                        listView.smoothScrollToPosition(11);

                        inviteCount.setText(""+groupList.size());


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                           Log.i("groupsGet",error.networkResponse.statusCode+"");
                    }

                }){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };


        MySingleton.getInstance(GroupListActivity.this).addToRequestQueue(jsObjRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("notificationSP",sharedPreferences.getInt("notification",0)+"");
                    int NotCount=sharedPreferences.getInt("notification",0);
                    if(NotCount<=99) {
                        tv.setTextSize(10);
                        tv.setText("" + NotCount);
                    }
                    else
                    {
                        tv.setTextSize(8);
                        tv.setText("99+");
                    }
                    ShortcutBadger.applyCount(getBaseContext(), NotCount);
                }
            }
        });
       /* notificationSP.unregisterOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

            }
        });*/
       /* notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("notificationSP","hi im onResume");
                    int count=sharedPreferences.getInt("notification",-1);
                    tv.setText(""+count);
                }
            }
        });*/
        int count=notificationSP.getInt("notification",-1);
        tv.setText(""+count);
    }

    public void getGroupsByHotelsRequest(String dateFrom, String dateTo){

        String url = "http://172.104.150.56/api/hotels"+"/"+dateFrom+"/"+dateTo;


        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                        //int _id, String title, String descritption, String image, String origin, String destination, String start_date, String end_date
                        JSONArray groupsArray = response;
                        for(int i=0;i<groupsArray.length();i++)
                        {
                            try {
                                groupList.add(i, new GroupInList(Integer.parseInt(
                                        groupsArray.getJSONObject(i).getString("id").toString()),// group ID
                                        groupsArray.getJSONObject(i).getString("title").toString(),// Group Title
                                        groupsArray.getJSONObject(i).getString("description").toString(), // Group description
                                        groupsArray.getJSONObject(i).getString("image").toString(),// Group image
                                        groupsArray.getJSONObject(i).getString("origin").toString(),// Group origin
                                        groupsArray.getJSONObject(i).getString("destination").toString(),// Group destination
                                        groupsArray.getJSONObject(i).getString("start_date").toString(),// Group start date
                                        groupsArray.getJSONObject(i).getString("end_date").toString(),Integer.parseInt(
                                        groupsArray.getJSONObject(i).getString("target_members").toString())));// Group end date

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        Log.i("Length",groupList.get(3).getOrigin().toString());
                         glAdapter= new GroupLIstAdapter2(GroupListActivity.this,groupList);
                        glAdapter.notifyDataSetChanged();

                        // Set the adapter to the list
                        listView.setAdapter(glAdapter);
        /*listView.setTransitionEffect(JazzyHelper.TWIRL);
        listView.setTranscriptMode(JazzyHelper.CARDS);*/
                        // a7la    listView.setTransitionEffect(JazzyHelper.ZIPPER);
                        listView.setTransitionEffect(JazzyHelper.WAVE);

                        listView.setScrollBarFadeDuration(100);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }

                });

        MySingleton.getInstance(GroupListActivity.this).addToRequestQueue(jsObjRequest);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
            return true;
        if(item.getItemId()==R.id.signOut)
        {
            signOut();
        }
        if(item.getItemId()==R.id.my_profile_drawer_item)
        {
            Log.i("getClasosh",item.getClass().toString());
            Intent i=new Intent(GroupListActivity.this,AccountActivity.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.my_account_drawer_item)
        {
            Intent i=new Intent(GroupListActivity.this,AccountActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.my_profile_drawer_item)
        {
            Log.i("itemy","im clicked");
            Intent i=new Intent(GroupListActivity.this,ProfileMemberActivity.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.test_pages)
        {
            Intent i=new Intent(GroupListActivity.this,TestsPagesActivity.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.signOut)
        {
            signOut();
        }
      /*  if(item.getItemId()==R.id.my_groups_drawer_item)
        {
            Log.i("itemy","im clicked");
            Intent i=new Intent(GroupListActivity.this,NewGroupProfile1Activity.class);
            startActivity(i);
        }*/
        return false;
    }

    public  void requsetidByToken() {
        String tokenUrl = "http://172.104.150.56/api/get_current_member";
        SharedPreferences settings2 =this.getSharedPreferences("UserLog", MODE_PRIVATE);
        final String token2 = settings2.getString("token", "");
        Log.i("requestByToken",token2);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                tokenUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("exceptiony-profile",response.getJSONObject("profile").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SharedPreferences.Editor editor=getSharedPreferences("UserLog",MODE_PRIVATE).edit();
                        try {
                            JSONObject profile=response;
                            userId=response.getJSONObject("profile").getString("id").toString();
                            editor.putString("id",response.getJSONObject("profile").getString("id").toString());
                            editor.putString("email",profile.getJSONObject("profile").getString("email"));
                            editor.putString("first_name",profile.getJSONObject("profile").getString("first_name").toString());
                            editor.putString("last_name",profile.getJSONObject("profile").getString("last_name"));
                            editor.putString("profile_image",profile.getJSONObject("profile").getString("profile_image"));
                            editor.commit();
                            Log.i("userId","im after shared preferences");

                            setNavigateViewSignIn();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("exceptiony",e.getMessage().toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error_listener","error");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token2);


                return params;
            }


        };
        MySingleton.getInstance(GroupListActivity.this).addToRequestQueue(jsonObjectRequest);
        setNavigateViewSignIn();


    }
    private void setNavigateViewSignIn() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View viewy=navigationView.getHeaderView(0);
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String firstNameString = settings.getString("first_name","No name");
        String lastNameString = settings.getString("last_name","No name");
        String emailString = settings.getString("email","No name");
        String profileImage= settings.getString("profile_image","No name");
        Log.i("detailsy",firstNameString+","+lastNameString+","+profileImage);
        TextView name=(TextView)viewy.findViewById(R.id.nav_header_nameTv);
        TextView email=(TextView)viewy.findViewById(R.id.nav_header_email);
        TextView signOut=(TextView)viewy.findViewById(R.id.nav_header_SignOut);
        ImageView profileIv=(ImageView)viewy.findViewById(R.id.nav_header_profileImg);
        name.setText(firstNameString+" "+lastNameString);
        email.setText(emailString);
        Picasso.with(getBaseContext()).load(profileImage).into(profileIv);
        signOut.setVisibility(View.VISIBLE);

        /*TextView tv=(TextView)viewy.findViewById(R.id.textTest);
        tv.setText("hi");*/
    }
    public void signOut(){
        signInBt.setText("SIGN IN");
        SharedPreferences.Editor editor=getSharedPreferences("UserLog",MODE_PRIVATE).edit();
        editor.clear();
        editor.putString("isSigned","false");
        editor.commit();
        FirebaseMessaging.getInstance().unsubscribeFromTopic("NEWS");
        Intent i=new Intent(GroupListActivity.this,SignInActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        menu.getItem(0).getSubMenu().getItem(0).setIcon(R.drawable.person2);
        menu.getItem(0).getSubMenu().getItem(0).setTitle("Zohar Ohana");

        return true;
    }
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }
    @Override
    protected void onPause() {
        Log.i("status","onPause");
        stopNotificationService();
        int NotCount=notificationSP.getInt("notification",0);
        tv.setText(""+NotCount);
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("status","onRestart");
        stopNotificationService();
        int NotCount=notificationSP.getInt("notification",0);
        tv.setText(""+NotCount);
        notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("notificationSP",sharedPreferences.getInt("notification",0)+"");
                    int NotCount=sharedPreferences.getInt("notification",0);
                    if(NotCount<=99) {
                        tv.setTextSize(10);
                        tv.setText("" + NotCount);
                    }
                    else
                    {
                        tv.setTextSize(8);
                        tv.setText("99+");
                    }
                    ShortcutBadger.applyCount(getBaseContext(), NotCount);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        stopNotificationService();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopNotificationService();
        Log.i("status","onPause");


    }
    public void stopNotificationService(){
        Intent i=new Intent(GroupListActivity.this,NotificationService.class);
        stopService(i);
    }


    public void getInviteCount(){
        SharedPreferences settings=this.getSharedPreferences("UserLog",MODE_PRIVATE);
        String idUser=settings.getString("id","");
        String url = "http://172.104.150.56/api/members/"+idUser+"/groups/observer";
        Log.i("requestUrl",url);
        groupList.clear();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {JSONArray groupsArray = response;
                        inviteCount.setText(""+groupsArray.length());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet","error");
                    }

                });


        MySingleton.getInstance(GroupListActivity.this).addToRequestQueue(jsObjRequest);

    }


    public void getNumberOfNotifications(){
        final String url = "http://172.104.150.56/api/getnumberofnotifications/32";
        final JsonObjectRequest jsObjRequest =
        new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("numberNt",Integer.parseInt(response.getString("number").toString())+"");
                            SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
                            editor.putInt("notification",Integer.parseInt(response.getString("number").toString()));
                            editor.commit();
                            tv.setText(Integer.parseInt(response.getString("number").toString())+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("numberNt","error");

                    }
                }) ;
        MySingleton.getInstance(GroupListActivity.this).addToRequestQueue(jsObjRequest);
    }
}
