package com.snapgroup.Tests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.snapgroup.R;

public class RotateViewsActivity extends AppCompatActivity {

    EditText seekBar,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_views);
        seekBar=(EditText)findViewById(R.id.seekBar7);
        et2=(EditText)findViewById(R.id.seekBar8);
        RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.rotate);
        seekBar.setAnimation(rotate);
        et2.setAnimation(rotate);
    }
}
