package com.snapgroup.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;

import com.snapgroup.R;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadActivity extends AppCompatActivity {

    private Button button;
    TextView tv,tvSize;
    public static String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        button = (Button) findViewById(R.id.button);
        tv=(TextView)findViewById(R.id.textView);
        tvSize=(TextView)findViewById(R.id.tvSize);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_button();
    }

    private void enable_button() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(UploadActivity.this)
                        .withRequestCode(10)
                        .start();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enable_button();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    ProgressDialog progress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        /*if (requestCode == 10 && resultCode == RESULT_OK) {
            File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            String content_type = getMimeType(f.getPath());
            Log.i("content_type",getMimeType(f.getPath()));
            tv.setText(f.getPath());
            tvSize.setText(f.length()+" Bytes");

            InputStream inputStream = null;//You can get an inputStream using any IO API
            try {
                inputStream = new FileInputStream(f.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output64.write(buffer, 0, bytesRead);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output64.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        if(requestCode == 10 && resultCode == RESULT_OK){

            progress = new ProgressDialog(UploadActivity.this);
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type  = getMimeType(f.getPath());

                    String file_path = f.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);
                    Log.i("typosh",content_type);
                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",content_type)
                            .addFormDataPart("file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://172.104.150.56/api/upload")
                            .post(request_body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        Log.i("errore",response.body().string());

                        str=response.toString();
//                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        if(!response.isSuccessful()){
                            throw new IOException("Error : "+response);
                        }
                        else

                            progress.dismiss();

                    } catch (IOException e) {
                        Log.i("errore",e.getMessage());
                        e.printStackTrace();
                    }


                }
            });

            t.start();

           // Toast.makeText(UploadActivity.this, str, Toast.LENGTH_SHORT).show();


        }
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}