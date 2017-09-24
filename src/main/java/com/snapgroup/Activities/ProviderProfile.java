package com.snapgroup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.snapgroup.Classes.YoutubeTest;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import at.blogc.android.views.ExpandableTextView;

public class ProviderProfile extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    public  YouTubePlayerView youtube_visssew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile);

        ImageView danhotel = (ImageView)findViewById(R.id.provider_profile_image);
        ImageView imageView = (ImageView)findViewById(R.id.danhotel);

        Bundle b = getIntent().getExtras();

        String x = b.getString("imagesrc","-1");
        Log.i("qqqqqq",x);
        Picasso.with(ProviderProfile.this).load(x).into(imageView);
        //youtube_visew
         youtube_visssew = (YouTubePlayerView) findViewById(R.id.youtube_visssew);
        youtube_visssew.initialize(YoutubeTest.API_KEY, this);


        Picasso.with(ProviderProfile.this).load("http://www.danhotel.dk/Files/Images/Delika2Aps/danhotel-logo.jpg").into(danhotel);

        final ExpandableTextView bigdescription = (ExpandableTextView) findViewById(R.id.profile_sssmember_text_view);
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
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("t5ELmZS9CPY"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
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
            youtube_visssew.initialize(YoutubeTest.API_KEY,this);
        }

    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youtube_visssew;
    }
}
