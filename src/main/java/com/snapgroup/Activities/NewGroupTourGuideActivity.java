package com.snapgroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.snapgroup.R;

import java.util.Map;

public class NewGroupTourGuideActivity extends AppCompatActivity {

    TextView nextBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_tour_guide);
        nextBT=(TextView)findViewById(R.id.tour_guide_next_tv);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        printSharedPreferences();

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewGroupTourGuideActivity.this,NewGroupFinishActivity.class);
                startActivity(i);
            }
        });

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
}
