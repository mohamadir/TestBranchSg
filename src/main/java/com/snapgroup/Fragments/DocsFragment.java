package com.snapgroup.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.angads25.filepicker.model.FileListItem;
import com.snapgroup.Adapters.DocsAdapter;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.MemberFIle;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DocsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String[] gLtypes=new String[]{"Passport","Signed Contract","Other","File 2", "File 3","File 4","File 5"};
    ListView docsLv;
    DocsAdapter docsAdapter;
    ArrayList<MemberFIle> allFiles;
    public String groupId;
    public String memberId;
    public SharedPreferences settings;
    ArrayList<FileListItem> filesArray;

    public DocsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("file_attach", MODE_PRIVATE).edit();
        editor.putString("attach", "no");
        editor.commit();
        SharedPreferences prefs = getActivity().getSharedPreferences("file_attach", MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Log.i("spRegister","yes im here");

                if(s.equals("attach"))
                {
                    getAllGroupFileRequest();
                }
            }
        });
        View view= inflater.inflate(R.layout.fragment_docs, container, false);
        docsLv=(ListView)view.findViewById(R.id.docsLv);
        allFiles=new ArrayList<MemberFIle>();
        settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        groupId=settings.getString("gId","-1");;
        settings=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE);
        memberId=settings.getString("id","-1");
        getAllGroupFileRequest();


        return view;

    }
    public void getAllGroupFileRequest() {
        String url = "http://dev.snapgroup.co.il/api/files/member/"+memberId+"/group/"+groupId;
        allFiles.clear();
        Log.i("Urly",url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray filesResponseArray = response;
                        for (int i = 0; i < filesResponseArray.length(); i++) {
                            try {
                                //String id, String fileName, String mimeType, String path, String size
                                allFiles.add(
                                        new MemberFIle(filesResponseArray.getJSONObject(i).getString("id").toString(),
                                                filesResponseArray.getJSONObject(i).getString("filename").toString(),
                                                filesResponseArray.getJSONObject(i).getString("mime").toString(),
                                                filesResponseArray.getJSONObject(i).getString("path").toString(),
                                                filesResponseArray.getJSONObject(i).getString("size").toString(),
                                                filesResponseArray.getJSONObject(i).getString("file_type").toString()));    // Group end date

                                Log.i("file_typosh",filesResponseArray.getJSONObject(i).getString("file_type").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();

                                Log.i("JSONException", e.getMessage().toString() + "");

                            }
                        }
                        docsAdapter=new DocsAdapter(getActivity(),gLtypes,allFiles);
                        docsAdapter.notifyDataSetChanged();
                        docsLv.setAdapter(docsAdapter);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1", "error ");
                    }

                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
    }

}
