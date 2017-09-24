package com.snapgroup.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.snapgroup.Fragments.BlankFragment;
import com.snapgroup.Fragments.ChatFragment;
import com.snapgroup.Fragments.CheckLIstFragment;
import com.snapgroup.Fragments.DetailsF;
import com.snapgroup.Fragments.DocsFragment;
import com.snapgroup.Fragments.InteniaryFragment;
import com.snapgroup.Fragments.MembersFragment;
import com.snapgroup.Fragments.NewFragmentTest;
import com.snapgroup.Fragments.NewTest2Fragment;
import com.snapgroup.Fragments.NewVotingsFragmentTabs;
import com.snapgroup.Fragments.PlanFragment;
import com.snapgroup.Fragments.VotesFragment;
import com.snapgroup.Models.NewGroup;
import com.snapgroup.R;
import com.snapgroup.Tests.imageViewPager;
import com.snapgroup.planActivityy.PlanActivityy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewGroupDetailsActitivty extends AppCompatActivity
        implements
        MembersFragment.OnFragmentInteractionListener,
        NewFragmentTest.OnFragmentInteractionListener,
        NewTest2Fragment.OnFragmentInteractionListener,
        CheckLIstFragment.OnFragmentInteractionListener,
        BlankFragment.OnFragmentInteractionListener,
        DocsFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        NewVotingsFragmentTabs.OnFragmentInteractionListener,
        InteniaryFragment.OnFragmentInteractionListener{
    private View content;
    NestedScrollView mContent;
    AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    boolean expend=false;
    Button joinThisGroup;
    public static String groupNameTitle="";
    Button chatBt,paymentsBt,voteBt,pricesBt,mapBt,planBt,membersBt,documentsBt,checklistButton,homePageButton;
    LinearLayout homeLinaretPage,planeLinaret,mapLinret,memberLinaret,voteLinaret,chatLInaret,docLinaret,paymentLinaret,checkLIstLinaret;
    TextView titleTv;
    Button confirmBt;
    SharedPreferences notificationSP;
    TextView tv;
    final static long REFRESH_TIME = 100; // miliseconds
    public static int offset=(-600);
    final Handler handler = new Handler();

    public static boolean isTouch=false;
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_group_details_actitivty);
        initGroupDetails();

        joinThisGroup = (Button)findViewById(R.id.joinThisGroup);
        joinThisGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewGroupDetailsActitivty.this, JoinGroupActivity.class);
                startActivity(i);
            }
        });


        mContent = (NestedScrollView)findViewById(R.id.nested_view);
        appBarLayout=(AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(false);
        appBarLayout.setOutlineProvider(null);

        /*appBarLayout.setExpanded(false);
>>>>>>> 57081ad95ce92ce5ea33b1778a7f693e391bc8cf
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(0);
            appBarLayout.setOutlineProvider(null);

<<<<<<< HEAD
        }
=======
        }*/
        getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new DetailsF()).addToBackStack(null).commit();
        initToolbar();
        initButtons();
        initListeners();
        /*notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
        tv= (TextView)findViewById(R.id.messageCountTv);
        int NotCount=notificationSP.getInt("notification",654);
        if(NotCount<=99) {
            tv.setTextSize(10);
            tv.setText("" + NotCount);
        }
        else
        {
            tv.setTextSize(8);
            tv.setText("99+");
        }
        notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("newDetails",sharedPreferences.getInt("notification",0)+"");
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
        });*/

        titleTv=(TextView)findViewById(R.id.toolbarTitleTv);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setTitleEnabled(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    offset=verticalOffset;

            }

        });


       /* findViewById(R.id.fab).setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (offset > (-380))
                        appBarLayout.setExpanded(true);
                    if (offset <= (-380))
                        appBarLayout.setExpanded(false);
                    return true;

                }
                if(event.getAction()== MotionEvent.ACTION_DOWN)
                {
                    return true;

                }
                return false;
            }
        });*/
       /* final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mContent.canScrollVertically(DOWN) || mContent.canScrollVertically(UP)) {
                   disableScroll();
                } else {
                    enableScroll();
                }
            }
        }, 100);*/
        initFab();

        collapseOff();
   /*     BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();*/
    }

    ProgressDialog progress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult","--onActivityRekllkjjklhkljhjlkhklsult");
        final Intent data2=data;
        Log.i("onActivityResult","--onActivityResult");
        progress = new ProgressDialog(NewGroupDetailsActitivty.this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait...");
        progress.show();
        if(requestCode == 10 && resultCode == -1){
            try {

            }
            catch (Exception e)
            {
                if(progress!=null)
                    progress.dismiss();
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        File f = new File(data2.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                        String content_type = getMimeType(f.getPath());

                        String file_path = f.getAbsolutePath().replaceAll(" ", "").toString();

                        OkHttpClient client = new OkHttpClient();
                        RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                        RequestBody request_body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("type", content_type)
                                .addFormDataPart("file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                                .build();
                        SharedPreferences prefs = getBaseContext().getSharedPreferences("Files", MODE_PRIVATE);
                        String file_type=prefs.getString("file_type","");
                        Log.i("File-Disc","---------"+file_type);
                        prefs = getBaseContext().getSharedPreferences("SnapGroup", MODE_PRIVATE);
                        String groupId=prefs.getString("gId","-1");
                        Request request = new Request.Builder()
                                .url("http://dev.snapgroup.co.il/api/upload/32?upload_type=group&group_id="+groupId+"&file_type="+file_type)
                                .post(request_body)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();

                            if (!response.isSuccessful()) {
                                throw new IOException("Error : " + response);
                            }
                            SharedPreferences.Editor edit = getBaseContext().getSharedPreferences("filesRequestSP", MODE_PRIVATE).edit();
                            edit.putString("finish", "Yes");
                            edit.commit();
                            progress.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            NewGroupDetailsActitivty.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(NewGroupDetailsActitivty.this, "Cannot upload\n Please trye another file!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        SharedPreferences.Editor edit = getSharedPreferences("filesRequestSP", MODE_PRIVATE).edit();
                        edit.putString("finish", "Yes");
                        edit.commit();
                    }
                    catch (Exception e)
                    {
                        progress.dismiss();

                        final String message=e.getMessage().toString().replaceAll(" ","").replaceAll("\n","");
                        if(progress!=null) {
                            progress.dismiss();
                            NewGroupDetailsActitivty.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(NewGroupDetailsActitivty.this, "Cannot upload\n Please trye another file!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }
            });

            t.start();




        }
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
    private void postPointsRequest() {


    }

    private void initGroupDetails() {
        SharedPreferences settings=getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String image = settings.getString("gImage"," asdasdasd");
        String from = settings.getString("gOrigin"," asdasdasd");
        String to = settings.getString("gDestination"," asdasdasd");
        String description = settings.getString("gDescription"," asdasdasd");
        String title = settings.getString("gTitle"," asdasdasd");
        String id = settings.getString("gId","-1");
        groupNameTitle=title;

    }

    private void collapseOff(){
        appBarLayout.setExpanded(false);
        expend=false;
        findViewById(R.id.fab).setBackgroundResource(R.drawable.splitline);

    }
    private void initListeners() {


        /// homeLinaretPage,planeLinaret,mapLinret,memberLinaret,voteLinaret,chatLInaret,docLinaret,paymentLinaret,checkLIstLinaret;


        homeLinaretPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new DetailsF()).addToBackStack(null).commit();
                collapseOff();
            }
        });

        chatLInaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Chat of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new ChatFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        paymentLinaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Payments of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new BlankFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        docLinaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Documents of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new DocsFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        planeLinaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    /*            Intent i=new Intent(NewGroupDetailsActitivty.this,PlanActivityy.class);
                startActivity(i);*/
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new InteniaryFragment()).addToBackStack(null).commit();

                collapseOff();
            }
        });
        voteLinaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Votes of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new NewVotingsFragmentTabs()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        mapLinret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NewGroupDetailsActitivty.this,MapActivity.class);
                startActivity(i);
                collapseOff();
            }
        });
        memberLinaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Members of: "+groupNameTitle);

                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new MembersFragment()).addToBackStack(null).commit();
                collapseOff();

            }
        });
        checkLIstLinaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                titleTv.setText("checkList of: "+groupNameTitle);

                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new CheckLIstFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });



        ///buttonsss
//    Button chatBt,paymentsBt,voteBt,pricesBt,mapBt,planBt,membersBt,documentsBt,checklistButton,homePageButton;


        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new DetailsF()).addToBackStack(null).commit();
                collapseOff();
            }
        });

        chatBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Chat of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new ChatFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        paymentsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Payments of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new BlankFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        documentsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Documents of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new DocsFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        planBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /*     Intent i=new Intent(NewGroupDetailsActitivty.this,PlanActivityy.class);
                startActivity(i);*/
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new InteniaryFragment()).addToBackStack(null).commit();

                collapseOff();
            }
        });
        voteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Votes of: "+groupNameTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new NewVotingsFragmentTabs()).addToBackStack(null).commit();
                collapseOff();
            }
        });
        mapBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NewGroupDetailsActitivty.this,MapActivity.class);
                startActivity(i);
                collapseOff();
            }
        });
        membersBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setText("Members of: "+groupNameTitle);

                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new MembersFragment()).addToBackStack(null).commit();
                collapseOff();

            }
        });
        checklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                titleTv.setText("checkList of: "+groupNameTitle);

                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new CheckLIstFragment()).addToBackStack(null).commit();
                collapseOff();
            }
        });

    }

    private void setNotificationCount() {
        notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
        tv= (TextView)findViewById(R.id.messageCountTv);
        int NotCount=notificationSP.getInt("notification",654);
        if(NotCount<=99) {
            tv.setTextSize(10);
            tv.setText("" + NotCount);
        }
        else
        {
            tv.setTextSize(8);
            tv.setText("99+");
        }
        notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("newDetails",sharedPreferences.getInt("notification",0)+"");
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

    private void initButtons() {

        membersBt=(Button)findViewById(R.id.newDetails_membersBt);
        paymentsBt=(Button)findViewById(R.id.newDetails_paymentsBt);
        chatBt=(Button)findViewById(R.id.newDetails_chatBt);
        voteBt=(Button)findViewById(R.id.newDetails_voteBt);
        planBt=(Button)findViewById(R.id.newDetails_planBt);
        documentsBt=(Button)findViewById(R.id.newDetails_documentsBt);
        mapBt=(Button)findViewById(R.id.newDetails_mapBt);
        checklistButton=(Button)findViewById(R.id.checklistButton);
        homePageButton=(Button)findViewById(R.id.homePageButton);



        homeLinaretPage = (LinearLayout)findViewById(R.id.homeLinaretPage);
        planeLinaret = (LinearLayout)findViewById(R.id.planeLinaret);
        mapLinret = (LinearLayout)findViewById(R.id.mapLinret);
        voteLinaret = (LinearLayout)findViewById(R.id.voteLinaret);
        chatLInaret = (LinearLayout)findViewById(R.id.chatLInaret);
        docLinaret = (LinearLayout)findViewById(R.id.docLinaret);
        paymentLinaret = (LinearLayout)findViewById(R.id.paymentLinaret);
        checkLIstLinaret = (LinearLayout)findViewById(R.id.checkLIstLinaret);
        memberLinaret = (LinearLayout)findViewById(R.id.memberLinaret);

    }

    private void enableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                collapsingToolbarLayout.getLayoutParams();
        params.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        );
        collapsingToolbarLayout.setLayoutParams(params);
    }

    private void disableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                collapsingToolbarLayout.getLayoutParams();
        params.setScrollFlags(0);
        collapsingToolbarLayout.setLayoutParams(params);
    }
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(null);
        actionBar.setTitle(null);
        actionBar.setBackgroundDrawable(null);

        /*if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.logo2);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
    }
    private void initFab() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if(!expend) {
                  //  Snackbar.make(mContent, "Collapse down", Snackbar.LENGTH_SHORT).show();
                    appBarLayout.setExpanded(true);
                    expend=true;
                    findViewById(R.id.fab).setBackgroundResource(R.drawable.strip_gold_line_uposh);
                }
                else{
                  //  Snackbar.make(mContent, "Collapse Up", Snackbar.LENGTH_SHORT).show();
                    appBarLayout.setExpanded(false);
                    expend=false;;
                    findViewById(R.id.fab).setBackgroundResource(R.drawable.strip_gold_line_down);
                }
            }
        });
    }

    
    @Override
    public void onBackPressed() {
            getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new DetailsF()).addToBackStack(null).commit();
            startActivity(new Intent(NewGroupDetailsActitivty.this,GroupListActivity.class));
            super.onBackPressed();
            return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
