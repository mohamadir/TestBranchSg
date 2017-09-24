package com.snapgroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import com.snapgroup.R;

public class NewGroupFlightServiceActivity extends AppCompatActivity {
    TextView nextFlightBt;
    Spinner flyFromSp,flyToSp;
    String flyFrom="",flyTo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_flight_service);
        flyFromSp=(Spinner)findViewById(R.id.flightServiceActivity_flyFromSp);
        flyToSp=(Spinner)findViewById(R.id.flightServiceActivity_flyToSp);
        setSpinnerListener();
        nextFlightBt=(TextView)findViewById(R.id.flightsNextTv);
        nextFlightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewGroupFlightServiceActivity.this,NewGroupHotelsServiceActivity.class);
                updateSharedPreferences();
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }
    private void setSpinnerListener() {
        flyFromSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                flyFrom=flyFromSp.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        flyToSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                flyTo=flyToSp.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void updateSharedPreferences() {
        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
        if(!flyFrom.equals(""))
            editor.putString("origin",flyFrom);
        else
            editor.putString("origin","");
        if(!flyTo.equals(""))
            editor.putString("destination",flyTo);
        else
            editor.putString("destination","");
        editor.commit();
    }
}
