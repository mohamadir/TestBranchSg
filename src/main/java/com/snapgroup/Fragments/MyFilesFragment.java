package com.snapgroup.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.snapgroup.Adapters.MyFilesGridAdapter;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.MemberFIle;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyFilesFragment extends Fragment {
    LinearLayout uploadMediaBt;
    GridView myFilesGv;
    private MyFilesGridAdapter filesGridAdapter;
    ArrayList<MemberFIle> filesArray;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    SharedPreferences filesRequestSP;
    ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_files_fragment, container, false);
        uploadMediaBt = (LinearLayout) view.findViewById(R.id.uploadMediaBt);
        ;
        filesRequestSP = getActivity().getSharedPreferences("filesRequestSP", MODE_PRIVATE);
        filesRequestSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals("finish")) {
                    if (sharedPreferences.getString("finish", "No").equals("Yes"))
                        getFilesRequest();
                }
            }
        });
        try {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
            }
        } catch (Exception e)
        {

        }
        enable_button();
        filesArray=new ArrayList<MemberFIle>();
        myFilesGv=(GridView)view.findViewById(R.id.myFilesGv);
        getFilesRequest();
        myFilesGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




                if(   filesArray.get(i).mimeType.equals("image/jpeg")||
                        filesArray.get(i).mimeType.equals("image/gif")||
                        filesArray.get(i).mimeType.equals("image/jpg")||
                        filesArray.get(i).mimeType.equals("image/png")||
                        filesArray.get(i).mimeType.equals("image/bmp")) {
                    View dialog_full_screen = LayoutInflater.from(getActivity()).inflate(R.layout.image_layout, null);
                    final ImageView text2 = (ImageView) dialog_full_screen.findViewById(R.id.dialog_iv);
                    Picasso.with(getActivity()).load("http://dev.snapgroup.co.il" + filesArray.get(i).path).into(text2);
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder.setView(dialog_full_screen);
                    final android.support.v7.app.AlertDialog dialog4 = builder.create();
                 //   dialog4.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog4.show();
                }
                else if(filesArray.get(i).mimeType.equals("text/plain")||
                        filesArray.get(i).mimeType.equals("application/pdf")) {

                    Log.i("dialog_full_screen","im here");

                    View dialog_full_screen = LayoutInflater.from(getActivity()).inflate(R.layout.doc_view_dialog_layout, null);
                    String pdf = "http://www.pc-hardware.hu/PDF/konfig.pdf";
                    //  String doc="<iframe src='http://docs.google.com/gview?embedded=true&url=http://www.pc-hardware.hu/PDF/konfig.pdf' width='100%' height='100%' style='border: none;'></iframe>";
                    String doc = "<iframe src='http://docs.google.com/gview?embedded=true&url=" + "http://dev.snapgroup.co.il" + filesArray.get(i).path + "&embedded=true' " +
                            "width='100%' height='100%' " +
                            "style='border: none;'></iframe>";
                  /*  String doc="<iframe src='http://docs.google.com/gview?embedded=true&url=http://www.iasted.org/conferences/formatting/presentations-tips.ppt&embedded=true' "+
                            "width='100%' height='100%' "+
                            "style='border: none;'></iframe>";*/
                    WebView webView = (WebView) dialog_full_screen.findViewById(R.id.webView1);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    webView.getSettings().setAllowFileAccess(true);
                    webView.loadData( doc , "text/html", "UTF-8");
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder.setView(dialog_full_screen);
                    final android.support.v7.app.AlertDialog dialog4 = builder.create();
                    //dialog4.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                    dialog4.show();

                }
                else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(("http://dev.snapgroup.co.il"+filesArray.get(i).path)));
                    startActivity(browserIntent);
                }
 /*
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setView(dialog_full_screen);
                final android.support.v7.app.AlertDialog dialog4 = builder.create();
                //dialog4.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                dialog4.show();







               Log.i("GvFiles","item number:"+i+" cliecked");
                if(   filesArray.get(i).mimeType.equals("image/jpeg")||
                        filesArray.get(i).mimeType.equals("image/gif")||
                        filesArray.get(i).mimeType.equals("image/jpg")||
                        filesArray.get(i).mimeType.equals("image/png")||
                        filesArray.get(i).mimeType.equals("image/bmp"))
                {
                    View dialog_full_screen= LayoutInflater.from(getActivity()).inflate(R.layout.image_layout,null);
                    final ImageView text2 = (ImageView) dialog_full_screen.findViewById(R.id.dialog_iv);
                    Picasso.with(getActivity()).load("http://dev.snapgroup.co.il"+filesArray.get(i).path).into(text2);
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder.setView(dialog_full_screen);
                    final android.support.v7.app.AlertDialog dialog4 = builder.create();
                    dialog4.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                    dialog4.show();



                    *//*Dialog settingsDialog = new Dialog(getActivity());
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);


                    View dialog_full_screen= LayoutInflater.from(getActivity()).inflate(R.layout.image_layout,null);
                    final ImageView text2 = (ImageView) dialog_full_screen.findViewById(R.id.dialog_iv);
                    Picasso.with(getActivity()).load("http://dev.snapgroup.co.il"+filesArray.get(i).path).into(text2);
                    settingsDialog.setContentView(dialog_full_screen, null);
                    settingsDialog.show();*//*
                   // new DownloadFileFromURL().execute("http://dev.snapgroup.co.il"+filesArray.get(i).path,filesArray.get(i).fileName);

                }
                else if(filesArray.get(i).mimeType.equals("application/pdf"))
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(("http://dev.snapgroup.co.il"+filesArray.get(i).path)));
                    startActivity(browserIntent);
                }
                else
                new DownloadFileFromURL().execute("http://dev.snapgroup.co.il"+filesArray.get(i).path,filesArray.get(i).fileName);*/

            }
        });
        myFilesGv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                deleteDialog(i,filesArray.get(i).id);
               // getFilesRequest();
                return true;
            }
        });
        return view;
    }

    public void deleteFile(final int index, String id){
       String  url = "http://172.104.150.56/api/files/"+id;
        StringRequest dr = new StringRequest(com.android.volley.Request.Method.DELETE, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        filesArray.remove(index);
                        filesGridAdapter=new MyFilesGridAdapter(getActivity(),filesArray);
                        myFilesGv.setAdapter(filesGridAdapter);
                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        MySingleton.getInstance(getActivity()).addToRequestQueue(dr);
    }
    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void enable_button() {

        uploadMediaBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(getActivity())
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
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

            }
        }
    }
    ProgressDialog progress;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFilesRequest();
    }

    public void getFilesRequest() {
        String url = "http://dev.snapgroup.co.il/api/files?member_id=" + 32;
        filesArray.clear();
        SharedPreferences.Editor edit=getActivity().getSharedPreferences("filesRequestSP",MODE_PRIVATE).edit();
        edit.putString("finish","No");
        edit.commit();
        filesGridAdapter = new MyFilesGridAdapter(getActivity(), filesArray);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray filesResponseArray = response;
                        for (int i = 0; i < filesResponseArray.length(); i++) {
                            try {
                                //String id, String fileName, String mimeType, String path, String size
                                filesArray.add(
                                        new MemberFIle(filesResponseArray.getJSONObject(i).getString("id").toString(),
                                                filesResponseArray.getJSONObject(i).getString("filename").toString(),
                                                filesResponseArray.getJSONObject(i).getString("mime").toString(),
                                                filesResponseArray.getJSONObject(i).getString("path").toString(),
                                                filesResponseArray.getJSONObject(i).getString("size").toString(),
                                                filesResponseArray.getJSONObject(i).getString("file_type").toString()));     Log.i("filesArray","---"+filesResponseArray.getJSONObject(i).getString("mime").toString());
                                // Group end date

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("JSONException", e.getMessage().toString() + "");

                            }
                        }


                        filesGridAdapter = new MyFilesGridAdapter(getActivity(), filesArray);
                        myFilesGv.setAdapter(filesGridAdapter);
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1", "error ");
                    }

                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        String str;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                str=f_url[1];
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Snap Group";
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                OutputStream fOut = null;
                File file = new File(fullPath, f_url[1]);
                if(file.exists())
                    file.delete();
                file.createNewFile();
                fOut = new FileOutputStream(file);
                file.createNewFile();
                fOut = new FileOutputStream(file);
                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + f_url[1]);
                Log.i("downloady",Environment.getExternalStorageDirectory().toString());

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    fOut.write(data, 0, count);
                }

                // flushing output
                fOut.flush();

                // closing streams
                fOut.close();
                input.close();


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }
        public void openFolder()
        {
         /*   Uri path= Uri.parse(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(path, "resource/folder");
            startActivity(Intent.createChooser(intent, "Open folder"));*/
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
            intent.setDataAndType(uri, "*/*");
            startActivity(Intent.createChooser(intent, "Open folder"));
            startActivity(intent);
        }
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
                 showOkDialog(Environment.getExternalStorageDirectory().getAbsolutePath());
            pDialog.dismiss();

        }

        public  void showOkDialog(String s) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle("You have downloaded the file successfully!");
            builder1.setMessage(s);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Open Directory",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            openFolder();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }

   public void  deleteDialog(final int index,final String file_id){
       AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
       builder1.setTitle("Do you want to delte the file ?");
       builder1.setCancelable(true);

       builder1.setPositiveButton(
               "Delete",
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       deleteFile(index,file_id);
                       dialog.cancel();
                   }
               });

       builder1.setNegativeButton(
               "Cancel",
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                   }
               });

       AlertDialog alert11 = builder1.create();
       alert11.show();
   }


}