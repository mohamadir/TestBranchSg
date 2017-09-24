package com.snapgroup.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.MemberFIle;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by snapgroup2 on 14/09/17.
 */

public class DocsAdapter extends BaseAdapter {
    SharedPreferences filesRequestSP;
    Activity context;
    String[] gLtypes;
    GridView myFilesGv;
    ArrayList<MemberFIle> filesArray;
    ArrayList<MemberFIle> allFiles;
    public String groupId;
    public String memberId;
    public  SharedPreferences settings;

    private MyFilesGridAdapter filesGridAdapter;


    private ProgressDialog pDialog;

    public DocsAdapter(Activity context, String[] gLtypes, ArrayList<MemberFIle> allFiles)
    {

        this.context=context;
        this.gLtypes=gLtypes;
        filesArray=new ArrayList<MemberFIle>();
        this.allFiles=allFiles;
        settings=context.getSharedPreferences("SnapGroup",MODE_PRIVATE);
        groupId=settings.getString("gId","-1");;
        settings=context.getSharedPreferences("UserLog",MODE_PRIVATE);
        memberId=settings.getString("id","-1");
        filesRequestSP = context.getSharedPreferences("filesRequestSP", MODE_PRIVATE);
        filesRequestSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals("finish")) {
                    if (sharedPreferences.getString("finish", "No").equals("Yes"))
                        getFilesRequest();
                }
            }
        });
    }


    @Override
    public int getCount()
    {
        return gLtypes.length;

    }
    public static ProgressDialog progress;
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        DocsAdapter.ViewHolder viewHolder = null;
         final int pos=i;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.group_file_item_miss_layout, null);
            viewHolder = new DocsAdapter.ViewHolder();
            view.setTag(viewHolder);

        } else {
            viewHolder = (DocsAdapter.ViewHolder) view.getTag();
        }
        final int position=i;
        final ViewHolder finalViewHolder = viewHolder;
        finalViewHolder.uploadBt = (Button) view.findViewById(R.id.upload_from_groupBt);
        finalViewHolder.fileTypeTv = (TextView) view.findViewById(R.id.file_type_groupTv);
        finalViewHolder.MediaLinear = (LinearLayout) view.findViewById(R.id.exit_file_linear);
        finalViewHolder.okIconBt = (Button) view.findViewById(R.id.check_statusBt);
        finalViewHolder.deleteFileBt = (Button) view.findViewById(R.id.delete_Bt);
        finalViewHolder.editBt = (Button) view.findViewById(R.id.edit_fileBt);

        MemberFIle currentFile=null;
        for(int k=0;k<allFiles.size();k++)
        {
            if(allFiles.get(k).fileType.equals(gLtypes[pos]))
            {
                currentFile =  allFiles.get(k);
            }
        }
       // finalViewHolder.uploadBt.setVisibility(View.VISIBLE);
        finalViewHolder.MediaLinear.setVisibility(View.VISIBLE);


        viewHolder.fileTypeTv.setText(gLtypes[pos]);
        finalViewHolder.uploadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showDialog();
                View hoursView= LayoutInflater.from(context).inflate(R.layout.upload_from_group_layout,null);
                final Button cancelBt=(Button) hoursView.findViewById(R.id.cancelBt);
                final LinearLayout uploadLinear=(LinearLayout)hoursView.findViewById(R.id.uploadMediaBt) ;
                myFilesGv = (GridView)hoursView.findViewById(R.id.myFilesGv);
                getFilesRequest();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(hoursView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                cancelBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                    }
                });
                uploadLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadLinear.setFocusable(true);
                        uploadLinear.setFocusableInTouchMode(true);
                        uploadLinear.requestFocus();
                        SharedPreferences.Editor editor = context.getSharedPreferences("Files", MODE_PRIVATE).edit();
                        editor.putString("file_type",gLtypes[i]);
                        editor.commit();
                        new MaterialFilePicker()
                                .withActivity(context)
                                .withRequestCode(10)
                                .start();
                        dialog.cancel();
                       /* finalViewHolder.uploadBt.setVisibility(View.GONE);
                        finalViewHolder.MediaLinear.setVisibility(View.VISIBLE);*/
                        getAllGroupFileRequest();

                    }
                });
                myFilesGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String fileId=filesArray.get(i).id;
                        Log.i("UserLog","file: "+fileId+"groupId: "+groupId+"member: "+memberId);
                        progress = new ProgressDialog(context);
                        progress.setMessage("loading");
                        progress.show();
                        dialog.dismiss();
                        Log.i("gltypes",gLtypes[pos]);
                        attachFileRequest(gLtypes[pos],dialog,fileId,memberId,groupId,position);


                    }
                });

            }
        });
        finalViewHolder.editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showDialog();
                View hoursView= LayoutInflater.from(context).inflate(R.layout.upload_from_group_layout,null);
                final Button cancelBt=(Button) hoursView.findViewById(R.id.cancelBt);
                final LinearLayout uploadLinear=(LinearLayout)hoursView.findViewById(R.id.uploadMediaBt) ;
                myFilesGv = (GridView)hoursView.findViewById(R.id.myFilesGv);
                getFilesRequest();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(hoursView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                cancelBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                    }
                });
                uploadLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadLinear.setFocusable(true);
                        uploadLinear.setFocusableInTouchMode(true);
                        uploadLinear.requestFocus();
                        SharedPreferences.Editor editor = context.getSharedPreferences("Files", MODE_PRIVATE).edit();
                        editor.putString("file_type",gLtypes[i]);
                        editor.commit();
                        new MaterialFilePicker()
                                .withActivity(context)
                                .withRequestCode(10)
                                .start();
                        dialog.cancel();
                       /* finalViewHolder.uploadBt.setVisibility(View.GONE);
                        finalViewHolder.MediaLinear.setVisibility(View.VISIBLE);*/
                        getAllGroupFileRequest();

                    }
                });
                myFilesGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String fileId=filesArray.get(i).id;
                        Log.i("UserLog","file: "+fileId+"groupId: "+groupId+"member: "+memberId);
                        progress = new ProgressDialog(context);
                        progress.setMessage("loading");
                        progress.show();
                        dialog.dismiss();
                        Log.i("gltypes",gLtypes[pos]);
                        attachFileRequest(gLtypes[pos],dialog,fileId,memberId,groupId,position);


                    }
                });
            }
        });

        view.setTag(finalViewHolder);
        finalViewHolder.deleteFileBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileId="";
                for(int r=0;r<allFiles.size();r++)
                {
                    if(finalViewHolder.fileTypeTv.getText().toString().equals(allFiles.get(r).fileType.toString()))
                    { fileId=allFiles.get(r).id;
                    break;}
                }
                progress = new ProgressDialog(context);
                progress.setMessage("loading");
                progress.show();
                dettachFileRequest(gLtypes[pos],fileId,groupId,position);
            }
        });
        if(currentFile==null)
        {
            Log.i("nullosh","im null"+"  "+ finalViewHolder.fileTypeTv.getText().toString());
            finalViewHolder.uploadBt.setVisibility(View.VISIBLE);
            finalViewHolder.MediaLinear.setVisibility(View.GONE);
            finalViewHolder.MediaLinear.setVisibility(View.GONE);
            finalViewHolder.MediaLinear.setVisibility(View.GONE);
            finalViewHolder.MediaLinear.setVisibility(View.GONE);
            finalViewHolder.MediaLinear.setVisibility(View.GONE);
            finalViewHolder.okIconBt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.miss_file));
        }
        else if(currentFile.fileType.equals(finalViewHolder.fileTypeTv.getText().toString())){
            Log.i("nullosh",currentFile.fileType.toString()+" = = "+finalViewHolder.fileTypeTv.getText().toString());
            finalViewHolder.MediaLinear.setVisibility(View.VISIBLE);
            finalViewHolder.uploadBt.setVisibility(View.GONE);
            finalViewHolder.uploadBt.setVisibility(View.GONE);
            finalViewHolder.uploadBt.setVisibility(View.GONE);
            finalViewHolder.uploadBt.setVisibility(View.GONE);
            finalViewHolder.okIconBt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ok_icon));

        }
        return view;
    }

    private MemberFIle getCurrentFile(String s) {
        Log.i("getCurrentFile",allFiles.size()+"");

        for(int i=0;i<allFiles.size();i++)
        {
            if(allFiles.get(i).fileType.equals(s))
            {
                return allFiles.get(i);
            }
        }
        return null;
    }

    public void showDialog(){

        View hoursView= LayoutInflater.from(context).inflate(R.layout.upload_from_group_layout,null);
        final Button cancelBt=(Button) hoursView.findViewById(R.id.cancelBt);
        final LinearLayout uploadLinear=(LinearLayout)hoursView.findViewById(R.id.uploadMediaBt) ;
        myFilesGv = (GridView)hoursView.findViewById(R.id.myFilesGv);
        getFilesRequest();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(hoursView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        uploadLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(context)
                        .withRequestCode(10)
                        .start();
                dialog.cancel();

            }
        });
    }
    public class ViewHolder{
        public Button uploadBt;
        public Button deleteFileBt;
        public Button okIconBt;
        public TextView fileTypeTv;
        public LinearLayout MediaLinear;
        public Button editBt;
    }

    public void getFilesRequest() {
        String url = "http://dev.snapgroup.co.il/api/files?member_id=" + 32;
        filesArray.clear();
        SharedPreferences.Editor edit=context.getSharedPreferences("filesRequestSP",MODE_PRIVATE).edit();
        edit.putString("finish","No");
        edit.commit();
        filesGridAdapter = new MyFilesGridAdapter(context, filesArray);
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
                                                filesResponseArray.getJSONObject(i).getString("file_type").toString()));
                                Log.i("filesArray","---"+filesResponseArray.getJSONObject(i).getString("mime").toString());
                                // Group end date

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("JSONException", e.getMessage().toString() + "");

                            }
                        }


                        filesGridAdapter = new MyFilesGridAdapter(context, filesArray);
                        myFilesGv.setAdapter(filesGridAdapter);
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1", "error ");
                    }

                });

        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public void attachFileRequest(final String file_type,final AlertDialog dialog, String fileId, String memberId, String groupId, int position)
    {
        final String url = "http://dev.snapgroup.co.il/api/files/"+fileId+"/member/"+memberId+"/group/"+groupId+"?file_type="+gLtypes[position];
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(context, "File Was attached As "+ file_type, Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        SharedPreferences.Editor editor = context.getSharedPreferences("file_attach", MODE_PRIVATE).edit();
                        editor.putString("attach", "yes");
                        editor.commit();
                        getAllGroupFileRequest();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "error");
                        Toast.makeText(context, "Error attaching ! Please try again", Toast.LENGTH_SHORT).show();
                        progress.dismiss();


                    }
                }
        );

// add it to the RequestQueue
        MySingleton.getInstance(context).addToRequestQueue(getRequest);
    }


    public void dettachFileRequest(final String file_type, String fileId, String groupId, int position)
    {
        final String url = "http://dev.snapgroup.co.il/api/files/"+fileId+"/group/"+groupId+"/file_type/"+gLtypes[position];
        Log.i("detaching",url);
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(context, "File Was dettached As "+ file_type, Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        SharedPreferences.Editor editor = context.getSharedPreferences("file_attach", MODE_PRIVATE).edit();
                        editor.putString("attach", "yes");
                        editor.commit();
                        notifyDataSetChanged();
                        getAllGroupFileRequest();
                        notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "error");
                        Toast.makeText(context, "Error attaching ! Please try again", Toast.LENGTH_SHORT).show();
                        progress.dismiss();


                    }
                }
        );

// add it to the RequestQueue
        MySingleton.getInstance(context).addToRequestQueue(getRequest);
    }



    public void getAllGroupFileRequest() {
        String url = "http://dev.snapgroup.co.il/api/files/member/"+memberId+"/group/"+groupId;
        allFiles.clear();
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
                                notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                notifyDataSetChanged();

                                Log.i("JSONException", e.getMessage().toString() + "");

                            }
                            notifyDataSetChanged();

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1", "error ");
                        notifyDataSetChanged();

                    }

                });

        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

}
