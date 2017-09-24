package com.snapgroup.Tests;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;
import com.snapgroup.R;


public class FilesActivity2 extends AppCompatActivity {
    private Button buttonClick;
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    File file;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 200;
    String filename;
    String s1,s2;
    private String UPLOAD_URL ="http://dev.snapgroup.co.il/api/upload/32";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "fname";
    private String KEY_ROLL = "roll_no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files2);
        buttonChoose = (Button)findViewById(R.id.ivAttachment);
        //buttonClick = (Button)findViewById(R.id.clickPic);
        buttonUpload = (Button)findViewById(R.id.b_upload);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();

            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

            }
        });
    }

    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);

    /*Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.setType("image*//*;application/pdf");
    intent.addCategory(Intent.CATEGORY_OPENABLE);

    // special intent for Samsung file manager
    Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA_MULTIPLE");
    // if you want any file type, you can skip next line
    sIntent.putExtra("CONTENT_TYPE", "application/pdf");
    sIntent.addCategory(Intent.CATEGORY_DEFAULT);

    Intent chooserIntent;
    if (getPackageManager().resolveActivity(sIntent, 0) != null)
    {
        // it is device with samsung file manager
        chooserIntent = Intent.createChooser(sIntent, "Open file");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
    }
    else
    {
        chooserIntent = Intent.createChooser(intent, "Open file");
    }


    startActivityForResult(chooserIntent, 100);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filePath = data.getData();
            Toast.makeText(FilesActivity2.this, ""+filePath, Toast.LENGTH_SHORT).show();

            filename=filePath.getLastPathSegment();
            Toast.makeText(FilesActivity2.this, ""+filename, Toast.LENGTH_SHORT).show();

            file = new File(filePath.toString());
            getStringFile(file);

        }
    }

    public String getStringFile(File f)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line ;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
                reader.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void uploadImage()
    {
        String doc = getStringFile(file);

        Log.i("uploadImage",doc.length()+"");
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s)
                    {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String doc = getStringFile(file);

                //Getting Image Name
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("file", doc);
                params.put("fname", filename);



                //returning parameters
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(){
                                         @Override
                                         public int getCurrentTimeout() {
                                             return 50000;
                                         }

                                         @Override
                                         public int getCurrentRetryCount() {
                                             return 50000;
                                         }

                                         @Override
                                         public void retry(VolleyError error) throws VolleyError {

                                         }
                                     }
        );

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
}
