package com.snapgroup.Activities;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.snapgroup.R;
import com.squareup.seismic.ShakeDetector;

public class ShakeTestActivity extends AppCompatActivity implements ShakeDetector.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_test);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void hearShake() {
        Toast.makeText(this, "Don't shake me, bro!", Toast.LENGTH_SHORT).show();
    }
}
