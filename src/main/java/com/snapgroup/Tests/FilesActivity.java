package com.snapgroup.Tests;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.snapgroup.R;

import java.io.FileDescriptor;
import java.io.IOException;

public class FilesActivity extends AppCompatActivity {
    Button uploadBt,pdfUploadBt;
    ImageView testIv;
    Uri path;

    private static final int READ_REQUEST_CODE = 42;
    private static final int WRITE_REQUEST_CODE = 43;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        testIv=(ImageView)findViewById(R.id.testIv);
        uploadBt=(Button)findViewById(R.id.uploadBt);
        pdfUploadBt=(Button)findViewById(R.id.pdfUploadBt);
        pdfUploadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Upload(FilesActivity.this, path)).execute();

            }
        });
        uploadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
            }
        });

    }

    public String getStringFile(File f)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            FileInputStream fileInputStream = new FileInputStream(f);

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

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                path = resultData.getData();
                Uri filePath = resultData.getData();
                String   filename=filePath.getLastPathSegment();
                File  file = new File(filePath.getPath().toString());
                String st= getStringFile(file);
                Log.i("onActivityResult",file.getPath()+",,,,,"+filename);
            }
        }



        /*// The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == FilesActivity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            if (resultData != null) {
                path = resultData.getData();
                (new Upload(FilesActivity.this, path)).execute();
*//*
                ContentResolver cr = this.getContentResolver();
                String mime = cr.getType(uri);
                Log.i("IMGURL", mime);*//*

               *//* //showImage(uri);
                try {
                    testIv.setImageBitmap(getBitmapFromUri(uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }*//*

            }
        }*/
    }
    private void createFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Create a file with the requested MIME type.
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    private void openPdf(){

    }

    class Upload extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pd;
        private Context c;
        private Uri path;

        public Upload(Context c, Uri path) {
            this.c = c;
            this.path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(c, "Uploading", "Please Wait");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
        }

        public String getStringFile(File f)
        {
            StringBuilder sb = new StringBuilder();
            try
            {
                FileInputStream fileInputStream = new FileInputStream(f);

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

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("FilesAt",path.toString());

            String url_path = "http://dev.snapgroup.co.il/api/upload/32";
            HttpURLConnection conn = null;

            int maxBufferSize = 1024;
            try {
                URL url = new URL(url_path);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(1024);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data");
                OutputStream outputStream = conn.getOutputStream();
                InputStream inputStream = c.getContentResolver().openInputStream(path);

                int bytesAvailable = inputStream.available();
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer, 0, bufferSize)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i("AAABBBCCC", line);
                }
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("AAABBBCCC", "Error"+e.getMessage().toString());

            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

}
