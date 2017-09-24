package com.snapgroup.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.snapgroup.FilePicker.CsvFilePIcker;
import com.snapgroup.R;
import com.snapgroup.Tests.FilesActivity;
import com.snapgroup.Tests.HosenShapesTests;
import com.snapgroup.Tests.NotificationTestActivity;
import com.snapgroup.Tests.RotateViewsActivity;
import com.snapgroup.Tests.ShapesTests3Activity;
import com.snapgroup.Tests.YoutubeTestActivity;

public class TestsPagesActivity extends AppCompatActivity {


    Button ServicesBt,BroadcastBt,tabsPageBt,MapRaduisBt,hosenShape,rotateBt,
    CsvUploadBt,DaysLocationBt,DaysPlanBt,notificationBt,filesBt,daysListBroadCastBt,youtubeBt,groupLeaderProfileBt,joinPageBt,groupMembersBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_pages);


        ServicesBt=(Button)findViewById(R.id.ServicesChooseBt);
        filesBt=(Button)findViewById(R.id.filesBt);
        hosenShape=(Button)findViewById(R.id.hosenShape);
        BroadcastBt=(Button)findViewById(R.id.HotlesBroadCastBt);
        MapRaduisBt=(Button)findViewById(R.id.MapsRadiusBt);
        DaysLocationBt=(Button)findViewById(R.id.DaysLocationsBt);
        DaysPlanBt=(Button)findViewById(R.id.daysListPlanBt);
        daysListBroadCastBt=(Button)findViewById(R.id.daysListBroadCastBt);
        youtubeBt=(Button)findViewById(R.id.youtubeBt);
        tabsPageBt=(Button)findViewById(R.id.tabsPageBt);
        CsvUploadBt=(Button)findViewById(R.id.CsvUploadBt);
        groupLeaderProfileBt=(Button)findViewById(R.id.groupLeaderProfileBt);
        joinPageBt=(Button)findViewById(R.id.joinPageBt);
        groupMembersBt=(Button)findViewById(R.id.groupMembersBt);
        notificationBt=(Button)findViewById(R.id.notificationBt);
        rotateBt=(Button)findViewById(R.id.rotateBt);
        setOnClickListeners();
    }

        private void setOnClickListeners() {
            DaysLocationBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                     //   Intent i = new Intent(TestsPagesActivity.this, NewGroupHotelsServiceActivity.class);
                        Intent i = new Intent(TestsPagesActivity.this, ShapesTests3Activity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            joinPageBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(TestsPagesActivity.this, HosenShapesTests.class);
                    startActivity(i);

                    try {
                        Intent io = new Intent(TestsPagesActivity.this, JoinGroupActivity.class);
                        startActivity(io);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
          hosenShape.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(TestsPagesActivity.this, HosenShapesTests.class);
                    startActivity(i);

                    try {
                        Intent io = new Intent(TestsPagesActivity.this, HosenShapesTests.class);
                        startActivity(io);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            notificationBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, NotificationTestActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            filesBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, FilesActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            rotateBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, RotateViewsActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            MapRaduisBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent i = new Intent(TestsPagesActivity.this, MapRadiusActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            groupLeaderProfileBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent i = new Intent(TestsPagesActivity.this, ProfileMemberActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            BroadcastBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, NewGroupChooseHotel.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            DaysPlanBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, DaysPlanBrodCastActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            daysListBroadCastBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, DaysPlanListBroadcastActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            youtubeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, YoutubeTestActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            tabsPageBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, AccountActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            CsvUploadBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent i = new Intent(TestsPagesActivity.this, CsvFilePIcker.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TestsPagesActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

