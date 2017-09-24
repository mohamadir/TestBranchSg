package com.snapgroup.Adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.snapgroup.Activities.RegisterActivity;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.NotificationItem;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 12/9/2016 for ProgrammingWizards Channel and http://www.Camposha.info.
 */
public class NewMessageAdapter extends RecyclerView.Adapter<NewMessageAdapter.MyHolder> {

    Context c;
    ArrayList<NotificationItem> messagesArray;
    public int memberId=-1;
    /*
    CONSTRUCTOR
     */
    public NewMessageAdapter(Context c, ArrayList<NotificationItem> messagesArray) {
        this.c = c;
        this.messagesArray = messagesArray;
        SharedPreferences prefs = c.getSharedPreferences(
                "UserLog", Context.MODE_PRIVATE);
         memberId = Integer.parseInt(prefs.getString("id","-1"));
    }

    //INITIALIE VH
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_message_list_item_unread,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.fromTv.setText(messagesArray.get(position).sender+"");
        holder.groupTv.setText(messagesArray.get(position).group_id+"");
        if(messagesArray.get(position).status)
             holder.rLayout.setBackgroundColor(Color.WHITE);
        else
            holder.rLayout.setBackgroundColor(Color.parseColor("#eae6cd"));

        holder.createdAtTv.setText(messagesArray.get(position).createdAt);
        if(messagesArray.get(position).type.equals("invite"))
        {
            holder.subjectTv.setText(messagesArray.get(position).sender+" wants to pair with you");
            holder.inviteLinear.setVisibility(View.VISIBLE);
            holder.messageTv.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                    "nonummy nibh euismod tincidunt ut laoreet dolore....");
            if(messagesArray.get(position).accepted.equals("accepted"))
            {
                holder.inviteLinear.setVisibility(View.GONE);
                holder.acceptedTv.setVisibility(View.VISIBLE);
                holder.acceptedTv.setText("Approved !");
            }
            if(messagesArray.get(position).accepted.equals("declined"))
            {
                holder.acceptedTv.setVisibility(View.VISIBLE);
                holder.inviteLinear.setVisibility(View.GONE);
                holder.acceptedTv.setText("Declined !");
            }
            if(messagesArray.get(position).accepted.equals("none"))
            {
                holder.inviteLinear.setVisibility(View.VISIBLE);
                holder.acceptedTv.setVisibility(View.GONE);
            }
            holder.approveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i("inviteDetails","inviter: "+messagesArray.get(position).sender+",invitee : "+memberId+", groupId: "
                            +messagesArray.get(position).group_id);
                    holder.acceptedTv.setVisibility(View.VISIBLE);
                    holder.acceptedTv.setText("Approved");
                    holder.inviteLinear.setVisibility(View.GONE);
                    holder.rLayout.setBackgroundColor(Color.WHITE);
                    markReadRequest(messagesArray.get(position).notificationId);
                    approveRequest(messagesArray.get(position).sender,messagesArray.get(position).group_id);
                }
            });
            holder.declineBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i("inviteDetails","inviter: "+messagesArray.get(position).sender+",invitee : "+memberId+", groupId: "
                            +messagesArray.get(position).group_id);
                    holder.acceptedTv.setVisibility(View.VISIBLE);
                    holder.acceptedTv.setText("Declined !");
                    holder.inviteLinear.setVisibility(View.GONE);
                    holder.rLayout.setBackgroundColor(Color.WHITE);
                    markReadRequest(messagesArray.get(position).notificationId);
                    holder.rLayout.setBackgroundColor(Color.WHITE);
                    declineRequest(messagesArray.get(position).sender,messagesArray.get(position).group_id);
                }
            });
        }
        else {
            holder.subjectTv.setText(messagesArray.get(position).subject);
            holder.messageTv.setText(messagesArray.get(position).body);
            holder.inviteLinear.setVisibility(View.GONE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markReadRequest(messagesArray.get(position).notificationId+"");
                holder.rLayout.setBackgroundColor(Color.WHITE);
                showDialog(
                        messagesArray.get(position).group_id,
                        messagesArray.get(position).sender,
                        messagesArray.get(position).subject,
                        messagesArray.get(position).body,
                        messagesArray.get(position).createdAt
                                );
            }
        });
    }

    private void showDialog(String group_id, String sender, String subject, String body, String createdAt)
    {
        View notificationView= LayoutInflater.from(c).inflate(R.layout.notification_dialog,null);
        final TextView fromTv = (TextView) notificationView.findViewById(R.id.fromTv);
        final TextView groupTv = (TextView) notificationView.findViewById(R.id.groupTv);
        final TextView messageTv = (TextView) notificationView.findViewById(R.id.messageTv);
        final TextView subjectTv = (TextView) notificationView.findViewById(R.id.sybjectTv);
        final TextView createdAtTv = (TextView) notificationView.findViewById(R.id.createdAt);

        fromTv.setText(sender);

        groupTv.setText(group_id);

        subjectTv.setText(subject);

        messageTv.setText(body);

        createdAtTv.setText(createdAt);

        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setView(notificationView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void markReadRequest(String notificationId){
        final String url = "http://172.104.150.56/api/markread/32/"+notificationId;
        Log.i("markReadRequest",url);
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("markReadRequest",response.toString());

                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("markReadRequest","error ");
                    }

                });
        MySingleton.getInstance(c).addToRequestQueue(jsObjRequest);
    }
    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        return messagesArray.size();

    }
   public void  approveRequest(String senderId,String groupId)
    {
        String url="http://172.104.150.56/api/confirmfriend"+"/"+senderId+"/"+memberId+"/"+groupId;
        Log.i("approveRequest",url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("approveRequest","Response: " + response.toString());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub

                }
            });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(c).addToRequestQueue(jsObjRequest);

    }

    public void  declineRequest(String senderId,String groupId)
    {
        String url="http://172.104.150.56/api/declinefriend"+"/"+senderId+"/"+memberId+"/"+groupId;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("declineRequest","Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(c).addToRequestQueue(jsObjRequest);

    }
    /*
    ADD DATA TO ADAPTER
     */
    public void add(NotificationItem s) {
        messagesArray.add(s);
        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        messagesArray.clear();
        notifyDataSetChanged();
    }

    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder {
        TextView fromTv,groupTv,messageTv,createdAtTv,subjectTv,acceptedTv;
        RelativeLayout rLayout;
        LinearLayout inviteLinear;
        Button approveBt;
        Button declineBt;

        public MyHolder(View itemView) {
            super(itemView);
            approveBt= (Button)itemView.findViewById(R.id.approveBt);
            declineBt= (Button)itemView.findViewById(R.id.declineBt);
            rLayout=(RelativeLayout)itemView.findViewById(R.id.notificationLayout);
            fromTv = (TextView) itemView.findViewById(R.id.fromTv);
            groupTv= (TextView) itemView.findViewById(R.id.groupTv);
            messageTv = (TextView) itemView.findViewById(R.id.messageTv);
            createdAtTv = (TextView) itemView.findViewById(R.id.createdAt);
            subjectTv = (TextView) itemView.findViewById(R.id.sybjectTv);
            acceptedTv = (TextView) itemView.findViewById(R.id.acceptedTv);
            inviteLinear = (LinearLayout) itemView.findViewById(R.id.inviteLinear);
        }
    }

}