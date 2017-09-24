package com.snapgroup.FilePicker;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


public class CsvFilePIcker extends AppCompatActivity {
    public static final int PICKFILE_REQUEST_CODE=221;
    Button chooseBt;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv_file_picker);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        jsonArray=new JSONArray();

        chooseBt=(Button)findViewById(R.id.ChooseBt);
        chooseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCSVFile();
            }
        });
    }
    private void selectCSVFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), PICKFILE_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                switch (requestCode){
                case PICKFILE_REQUEST_CODE: {
                    if (resultCode == RESULT_OK){
                        Log.i("errory333",Environment.getDataDirectory().toPath().toString());

                        try {
                            proImportCSV(new File(data.getData().getPath()));

                        } catch (IOException e) {
                            Log.i("errorr",e.getMessage().toString());
                        }
                        Log.i("csvFile",data.getData().getPath()+"");
                    }
                }
            }

        }
    private void proImportCSV(File from) throws IOException {
        /*CSVReader dataRead = new CSVReader(new FileReader(from)); // <--- This line is key, and why it was reading the wrong file

        String[] vv = null;
        while((vv = dataRead.readNext())!=null) {
            Log.i("dataa",vv[0]);
        }*/
        String Fname=from.getName();

        try
        {
            List<String> list = new ArrayList<String>();
            String line;
            FileReader reader = new FileReader(new File(Environment.getDataDirectory(),Fname));
            BufferedReader bufrdr = new BufferedReader(reader);
            line = bufrdr.readLine();
            while (line != null) {
                list.add(line);
                line = bufrdr.readLine();
            }
            bufrdr.close();
            reader.close();

            String[] array = new String[list.size()];
            list.toArray(array);

            for (int i = 0; i < list.size(); i++) {
                String row=list.get(i).replace(","," ");
                String[] Sarray=row.split(" ");
                JSONObject json2= new JSONObject();
                json2.put("email",Sarray[0]);
                json2.put("first_name",Sarray[1]);
                json2.put("last_name",Sarray[1]);
                json2.put("password",Sarray[1]+"1234");
                jsonArray.put(json2);
               Log.i("hihihi", row);
            }
            Log.i("arrayLenght",jsonArray.length()+"");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject json_data = jsonArray.getJSONObject(i);

                Log.i("log_tag", "email" + json_data.getString("email") +
                        ", first name" + json_data.getString("first_name"));
            }

        }
        catch (Exception e) {
            // TODO: handle exception
           Log.i("errory",e.getMessage().toString()+","+Fname);
        }
    }

}
