package com.snapgroup.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import com.snapgroup.R;

public class AutoCompleteActivity extends AppCompatActivity {
    AutoCompleteTextView autoComplete;
    MultiAutoCompleteTextView multiComplete;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        String[] countries=getResources().getStringArray(R.array.countries);
        adapter = new ArrayAdapter<String>(AutoCompleteActivity.this,android.R.layout.simple_list_item_1,countries);
        autoComplete= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        multiComplete= (MultiAutoCompleteTextView) findViewById(R.id.multiCompleteEditText);
        autoComplete.setAdapter(adapter);
        multiComplete.setAdapter(adapter);
        autoComplete.setThreshold(1);
        multiComplete.setThreshold(4);
        multiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });



    }
}
