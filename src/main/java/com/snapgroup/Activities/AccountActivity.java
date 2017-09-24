package com.snapgroup.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.snapgroup.Adapters.PagerAdapter;
import com.snapgroup.R;

import java.io.File;
import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class AccountActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Account"));
        tabLayout.addTab(tabLayout.newTab().setText("My History"));
        tabLayout.addTab(tabLayout.newTab().setText("My Files"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTextDirection(View.TEXT_DIRECTION_FIRST_STRONG);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        SharedPreferences.Editor edit=getSharedPreferences("filesRequestSP",MODE_PRIVATE).edit();
        edit.putString("finish","No");
        edit.commit();
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        findViewById(R.id.GroupListActivity_SignInBt).setVisibility(View.GONE);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(null);
        actionBar.setTitle(null);
        actionBar.setBackgroundDrawable(null);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }
    ProgressDialog progress;

    @Override
    protected void onPause() {
        super.onPause();
        if(progress!=null)
            progress.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult","--onActivityResult");
         final Intent data2=data;
        Log.i("onActivityResult","--onActivityResult");
        progress = new ProgressDialog(AccountActivity.this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait...");
        progress.show();
        if(requestCode == 10 && resultCode == -1){
            try {

            }
            catch (Exception e)
            {
                if(progress!=null)
                    progress.dismiss();
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        File f = new File(data2.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                        String content_type = getMimeType(f.getPath());

                        String file_path = f.getAbsolutePath().replaceAll(" ", "").toString();

                        OkHttpClient client = new OkHttpClient();
                        RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                        RequestBody request_body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("type", content_type)
                                .addFormDataPart("file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://dev.snapgroup.co.il/api/upload/32")
                                .post(request_body)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();

                            if (!response.isSuccessful()) {
                                throw new IOException("Error : " + response);
                            }
                            SharedPreferences.Editor edit = getSharedPreferences("filesRequestSP", MODE_PRIVATE).edit();
                            edit.putString("finish", "Yes");
                            edit.commit();
                            progress.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SharedPreferences.Editor edit = getSharedPreferences("filesRequestSP", MODE_PRIVATE).edit();
                        edit.putString("finish", "Yes");
                        edit.commit();
                    }
                    catch (Exception e)
                    {
                        progress.dismiss();

                        final String message=e.getMessage().toString().replaceAll(" ","").replaceAll("\n","");
                        if(progress!=null) {
                            progress.dismiss();
                            AccountActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(AccountActivity.this, "Cannot upload\n Please trye another file!", Toast.LENGTH_SHORT).show();
                                }
                            });                        }
                        }

                }
            });

            t.start();




        }
    }
    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
