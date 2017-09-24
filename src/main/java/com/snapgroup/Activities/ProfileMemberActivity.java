package com.snapgroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.infteh.comboseekbar.ComboSeekBar;
import com.snapgroup.Adapters.HLVAdapter;
import com.snapgroup.Adapters.ListViewAdapterReviews;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Classes.YoutubeTest;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class ProfileMemberActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    RecyclerView mRecyclerView;
    private ComboSeekBar mSeekBar;

    public SeekBar seekBar;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    RecyclerView.LayoutManager mLayoutManager;
    ListView listviewReviews ;
    EditText messageEditText;
    RecyclerView.Adapter mAdapter;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    public ArrayList<String> alName;
    public ArrayList<String> ratingNumber;
    private ExpandableListView expListView;
    public ArrayList<String> nameProfile;
    public ArrayList<Integer> profileOMages;
    public ArrayList<String> comments;

    Button rightButton,leftButton;
    ExpandableTextView bigdescription;

    public ArrayList<String> alImage;
    TextView profile_member_firstname,profile_member_lastname,profile_member_email,profile_member_role;
    ImageView profile_image_member;

    public String memberId="33";
    TextView signout;
    public ArrayList<GroupInList> groupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_profile_member_actvity);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ImageView profile_image_member = (ImageView)findViewById(R.id.profile_image_member);

        Picasso.with(ProfileMemberActivity.this).load("http://cdn.business2community.com/wp-content/uploads/2014/04/profile-picture.jpg").into(profile_image_member);
        bigdescription = (ExpandableTextView) findViewById(R.id.profile_member_text_view);
        bigdescription.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy\n" +
                "nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut\n" +
                "wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit\n" +
                "lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure\n" +
                "dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore\n" +
                "eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui\n" +
                "blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla");
        bigdescription.setAnimationDuration(400L);
        final Button expandBt=(Button)findViewById(R.id.expand_discriptionTv);
        expandBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (bigdescription.isExpanded())
                {
                    bigdescription.collapse();
                    expandBt.setBackgroundResource(R.drawable.splitline);
                }
                else
                {
                    bigdescription.expand();
                    expandBt.setBackgroundResource(R.drawable.spliteline_up);

                }
            }
        });

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YoutubeTest.API_KEY, this);

        //alName = new ArrayList<>(Arrays.asList("Group Name", "Group Name", "Group Name", "Group Name", "Group Name", "Group Name", "Group Name", "Group Name"));
       // alImage = new ArrayList<>(Arrays.asList(R.drawable.person2, R.drawable.person2, R.drawable.person2, R.drawable.person2, R.drawable.person2, R.drawable.person2, R.drawable.person2, R.drawable.person2));
        alName = new ArrayList<String>();
        alImage = new ArrayList<String>();
        groupList=new ArrayList<GroupInList>();
        // Calling the RecyclerView
        try {
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            mRecyclerView.setHasFixedSize(true);
        }
        catch (Error e) {
        }        /*profile_member_email = (TextView) findViewById(R.id.profile_member_email);
        profile_member_firstname = (TextView) findViewById(R.id.profile_member_firstname);
        profile_member_lastname = (TextView) findViewById(R.id.profile_member_lastname);
        profile_image_member = (ImageView) findViewById(R.id.profile_image_member) ;
        profile_member_role = (TextView) findViewById(R.id.profile_member_role) ;*/
       /* SharedPreferences settings=getSharedPreferences("UserLog",MODE_PRIVATE);
        String image = settings.getString("profile_image","");*/
       /* profile_member_role.setText(settings.getString("role",""));
        profile_member_firstname.setText(settings.getString("first_name","noName"));
        profile_member_lastname.setText(settings.getString("last_name","noName"));
        profile_member_email.setText(settings.getString("email","noEmail"));*/
//        Picasso.with(ProfileMemberActivity.this).load(settings.getString("profile_image","https://uploads.toptal.io/user/photo/15919/small_profile_photo.jpg")).into(profile_image_member);
        getGroupsRequests();




        rightButton = (Button)findViewById(R.id.rightButton);
        leftButton = (Button)findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onclickedrecyclleview" , "asd");

                mLayoutManager = new LinearLayoutManager(ProfileMemberActivity.this, LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new HLVAdapter(ProfileMemberActivity.this, alName, alImage,groupList);
                mRecyclerView.setAdapter(mAdapter);

            }
        });


        seekBar = (SeekBar)findViewById(R.id.seekbarReview);
        final TextView review = (TextView) findViewById(R.id.radiusTextVieww);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });
        Button sendButtonReview = (Button) findViewById(R.id.sendButtonReview);
        messageEditText = (EditText)findViewById(R.id.messageEditText);
        listviewReviews  = (ListView)findViewById(R.id.listviewReviews);



        comments = new ArrayList<String>();
        nameProfile = new ArrayList<String>();
        profileOMages = new ArrayList<Integer>();
        ratingNumber = new ArrayList<String>();


        //(Activity context_1, ArrayList<String> nameProfile, ArrayList<Integer> imageProfile, ArrayList<String> reviewText, ArrayList<String> ratingNumber)
        ListViewAdapterReviews adapterCOmments = new ListViewAdapterReviews(this,nameProfile,profileOMages,comments,ratingNumber);
        listviewReviews.setAdapter(adapterCOmments);
        final TextView allComments = (TextView)findViewById(R.id.allComments);

        sendButtonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (comments.size()>=1)
                    allComments.setVisibility(View.VISIBLE);


                listviewReviews.setVisibility(View.VISIBLE);
                DecimalFormat df = new DecimalFormat("0.00");
                double x = (seekBar.getProgress()/5000.00);
                String dx=df.format(x);
                x=Double.valueOf(dx);
                    comments.add(messageEditText.getText().toString());
                    nameProfile.add("hosen");
                    ratingNumber.add(x+"");
                    profileOMages.add(R.drawable.person3);
                    ListViewAdapterReviews adapterCOmmentss = new ListViewAdapterReviews(ProfileMemberActivity.this, nameProfile, profileOMages, comments, ratingNumber);
                    listviewReviews.setAdapter(adapterCOmmentss);

                       // Toast.makeText(ProfileMemberActivity.this, "" + x, Toast.LENGTH_SHORT).show();
                messageEditText.setText("");
            }
        });










        // The number of Columns


    }


    public void getGroupsRequests()
    {


        SharedPreferences settings=getSharedPreferences("UserLog",MODE_PRIVATE);
        String id = settings.getString("id","-1");
        memberId = id;
        String url2 = "http://172.104.150.56/api/members/"+"34";
        Log.i("asd",url2);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("jsonObectSecuuess" , response.toString());

                        //int _id, String title, String descritption, String image, String origin, String destination, String start_date, String end_date
                       // JSONArray groupsArray = response;
                        try {

                            JSONObject groupsObject = response;
                            JSONObject testtt = groupsObject.getJSONObject("success");
                            Log.i("qweqwqweqweqewqewqwe", testtt.toString());
                            JSONArray groupsArray = testtt.getJSONArray("groups");
                            Log.i("qweqwfghgghf",groupsArray.toString());
                           // JSONObject profileINfoaa = testtt.getJSONObject("profile");
                            Log.i("qwecxz" , "seccuess");
//                            profile_member_lastname.setText(profileINfoaa.getString("last_name").toString());
//                            profile_member_firstname.setText(profileINfoaa.getString("first_name").toString());
//                            profile_member_email.setText(groupsObject.getString("email"));
                            for (int i = 0; i < groupsArray.length(); i++) {

                                //Log.i("Asd","Asd");
                                alImage.add(i,groupsArray.getJSONObject(i).getString("image").toString());
                                alName.add(i,groupsArray.getJSONObject(i).getString("title").toString());
                                Log.i("Asd",groupsArray.getJSONObject(i).getString("id").toString());
                                Log.i("Asd",groupsArray.getJSONObject(i).getString("image").toString());


                                groupList.add(i, new GroupInList(Integer.parseInt(
                                        groupsArray.getJSONObject(i).getString("id").toString()),// group ID
                                        groupsArray.getJSONObject(i).getString("title").toString(),// Group Title
                                        groupsArray.getJSONObject(i).getString("description").toString(), // Group description
                                        groupsArray.getJSONObject(i).getString("image").toString(),// Group image
                                        groupsArray.getJSONObject(i).getString("origin").toString(),// Group origin
                                        groupsArray.getJSONObject(i).getString("destination").toString(),// Group destination
                                        groupsArray.getJSONObject(i).getString("start_date").toString(),// Group start date
                                        groupsArray.getJSONObject(i).getString("end_date").toString(),Integer.parseInt(
                                        groupsArray.getJSONObject(i).getString("target_members").toString())));





                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                        mLayoutManager = new LinearLayoutManager(ProfileMemberActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new HLVAdapter(ProfileMemberActivity.this, alName, alImage,groupList);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.i("groupsGet", error.getMessage().toString() + " ");
                    }

                });

        MySingleton.getInstance(ProfileMemberActivity.this).addToRequestQueue(jsObjRequest);

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            youTubeView.initialize(YoutubeTest.API_KEY,this);
        }

    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
