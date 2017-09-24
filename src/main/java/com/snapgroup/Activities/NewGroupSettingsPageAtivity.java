package com.snapgroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.snapgroup.Models.NewGroup;
import com.snapgroup.R;
import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;

public class NewGroupSettingsPageAtivity extends AppCompatActivity {
    TextView nextTv;
    private MultiSpinner languageSpinner,physicalSpinner,natureSpinner;
    private ArrayAdapter<String> languageAdapter,physicalAdapter,natureAdapter;
    Spinner inPrivacy,outPrivacy,memberOriginSp;
    String[] languagesString,natureString,physicalString;
    boolean[] languagesBool,natureBool,physicalBool;
    NewGroup newGroup;
    String inPrivacyString="Hidden",outPrivacyString="Hidden",memberOrigin="Israel";
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_settings_page_ativity);
        pb=(ProgressBar)findViewById(R.id.progressBar4);
        // arrays boolean and string for each multiSpinner in order
        // to be updated with the multispinner
        languagesBool=new boolean[4];
        natureBool=new boolean[5];
        physicalBool=new boolean[4];
        languagesString=new String[4];
        physicalString=new String[4];
        natureString=new String[5];
        inPrivacy=(Spinner)findViewById(R.id.NewGroupSettings_inAppPrivacySp);
        outPrivacy=(Spinner)findViewById(R.id.NewGroupSettings_outSidePrivacySp);
        memberOriginSp=(Spinner)findViewById(R.id.NewGroupSettings_memberOriginSp);
        setSpinnerListener();
        initArrays(languagesString,languagesBool);
        final ArrayList<String> physicals,natures,languages;
        newGroup=new NewGroup();
        //pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#019a01")));
        nextTv =(TextView)findViewById(R.id.SettingsNextTv);
        languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        physicalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        natureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        addPhysical(physicalAdapter);
        addLanguages(languageAdapter);
        addNatures(natureAdapter);
        // init views and set adapters for spinners
        languageSpinner = (MultiSpinner) findViewById(R.id.spinner_language);
        physicalSpinner = (MultiSpinner) findViewById(R.id.spinner_physical);
        natureSpinner = (MultiSpinner) findViewById(R.id.spinner_pleasure);
        languageSpinner.setAdapter(languageAdapter, false, onSelectedListener);
        physicalSpinner.setAdapter(physicalAdapter, false, onPhysicalSelectedListener);
        natureSpinner.setAdapter(natureAdapter, false, onNatureSelectedListener);
        // set initial selection
        boolean[] selectedItems = new boolean[languageAdapter.getCount()];
        //selectedItems[1] = true; // select second item
        languageSpinner.setSelected(selectedItems);
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextBtClick();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }

    private void setSpinnerListener() {
        inPrivacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                inPrivacyString=inPrivacy.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        outPrivacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                outPrivacyString=outPrivacy.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        memberOriginSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                memberOrigin=memberOriginSp.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void onNextBtClick() {
        String str="";
        for(int k=0;k<languagesBool.length;k++)
        {
            if(languagesBool[k])
                str+=languagesString[k]+",";
        }
        for(int k=0;k<natureBool.length;k++)
        {
            if(natureBool[k])
                str+=natureString[k]+",";
        }
        for(int k=0;k<physicalBool.length;k++)
        {
            if(physicalBool[k])
                str+=physicalString[k]+",";
        }
        Log.i("physical",str+","+inPrivacyString+","+outPrivacyString);

        updateSharedPreferences();
        Intent i=new Intent(NewGroupSettingsPageAtivity.this,NewGroupProfile2Activity.class);
        startActivity(i);
    }



    private void addNatures(ArrayAdapter<String> natureAdapter) {
        natureAdapter.add("pleasure");
        natureAdapter.add("religion");
        natureAdapter.add("business");
        natureAdapter.add("medical");
        natureAdapter.add("seminar");
    }

    private void addPhysical(ArrayAdapter<String> physicalAdapter) {
        physicalAdapter.add("olders");
        physicalAdapter.add("disabled");
        physicalAdapter.add("childern");
        physicalAdapter.add("patients");
    }

    private void initArrays(String[] languagesString, boolean[] languagesBool) {
        for(int i=0;i<languagesBool.length;i++)
        {
            languagesBool[i]=false;
        }
        languagesString[0]="russian";
        languagesString[1]="hebrew";
        languagesString[2]="english";
        languagesString[3]="french";
        for(int i=0;i<natureBool.length;i++)
        {
            natureBool[i]=false;
        }
        natureString[0]="pleasure";
        natureString[1]="religion";
        natureString[2]="business";
        natureString[3]="medical";
        natureString[4]="seminar";
        for(int i=0;i<physicalBool.length;i++)
        {
            physicalBool[i]=false;
        }
        physicalString[0]="olders";
        physicalString[1]="disabled";
        physicalString[2]="children";
        physicalString[3]="patients";



    }

    private void addLanguages(ArrayAdapter<String> adapter) {
        adapter.add("russian");
        adapter.add("hebrew");
        adapter.add("english");
        adapter.add("french");
    }

    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {

        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items

            StringBuilder builder = new StringBuilder();
            for(int i=0;i<languagesBool.length;i++)
            {
                languagesBool[i]=false;
            }
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    builder.append(languageAdapter.getItem(i)).append(" ");
                    languagesBool[i]=true;
                }
            }

        }
    };
    private MultiSpinner.MultiSpinnerListener onNatureSelectedListener = new MultiSpinner.MultiSpinnerListener() {

        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items
            for(int i=0;i<natureBool.length;i++)
            {
                natureBool[i]=false;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    builder.append(natureAdapter.getItem(i)).append(" ");
                    natureBool[i]=true;
                }
            }

        }
    };
    private MultiSpinner.MultiSpinnerListener onPhysicalSelectedListener = new MultiSpinner.MultiSpinnerListener() {

        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items
            for(int i=0;i<physicalBool.length;i++)
            {
                physicalBool[i]=false;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    builder.append(physicalAdapter.getItem(i)).append(" ");
                     physicalBool[i]=true;
                }
            }

        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }


    private void updateSharedPreferences() {
        SharedPreferences.Editor editor=getSharedPreferences("NewGroup",MODE_PRIVATE).edit();
         /*                                                  Check Privacy Status               */
        if(inPrivacyString.equals("Hidden"))
            editor.putBoolean("inapp_privacy",true);
        else
            editor.putBoolean("inapp_privacy",false);
        if(outPrivacyString.equals("Hidden"))
            editor.putBoolean("search_privacy",true);
        else
            editor.putBoolean("search_privacy",false);
        if(languagesBool[0])
            editor.putBoolean("russian",true);
        else
            editor.putBoolean("russian",false);
        if(languagesBool[1])
            editor.putBoolean("hebrew",true);
        else
            editor.putBoolean("hebrew",false);
        if(languagesBool[2])
            editor.putBoolean("english",true);
        else
            editor.putBoolean("english",false);
        if(languagesBool[3])
            editor.putBoolean("french",true);
        else
            editor.putBoolean("french",false);
        /*                                              Check Physical Status               */
        if(physicalBool[0])
            editor.putBoolean("seniors",true);
        else
            editor.putBoolean("seniors",false);
        if(physicalBool[1])
            editor.putBoolean("disabled",true);
        else
            editor.putBoolean("disabled",false);
        if(physicalBool[2])
            editor.putBoolean("children",true);
        else
            editor.putBoolean("children",false);
        if(physicalBool[3])
            editor.putBoolean("medical_handicap",true);
        else
            editor.putBoolean("medical_handicap",false);
                /*                                        Check Nature Status               */
        if(natureBool[0])
            editor.putBoolean("pleasure",true);
        else
            editor.putBoolean("pleasure",false);
        if(natureBool[1])
            editor.putBoolean("religion",true);
        else
            editor.putBoolean("religion",false);
        if(natureBool[2])
            editor.putBoolean("business",true);
        else
            editor.putBoolean("business",false);
        if(natureBool[3])
            editor.putBoolean("medical",true);
        else
            editor.putBoolean("medical",false);
        if(natureBool[4])
            editor.putBoolean("seminar",true);
        else
            editor.putBoolean("seminar",false);
        editor.putString("member_origin",memberOrigin);
        editor.putString("image","https://walkingadventures.com/wp-content/uploads/Eastern-Europe-Senior-Walking-Tour-Group-800x401.jpg");

        editor.commit();
    }
}
