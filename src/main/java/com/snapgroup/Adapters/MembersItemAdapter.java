package com.snapgroup.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Activities.JoinGroupActivity;
import com.snapgroup.Classes.CircleImage;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by root on 27/07/17.
 */
public class MembersItemAdapter extends ArrayAdapter<String> {
    // class for Assistant group i use customlidtadapter  that extandsa from the adapter for the liostview
    //
    private final Activity context; // the activity/
    private final ArrayList<String>itemname; // names of the Assistant group
    private final ArrayList<String>imgid; // names of the Assistant group
    private final ArrayList<String>lastName; // names of the Assistant group
    private final ArrayList<String>numOfPassenger; // names of the Assistant group
    private final ArrayList<String>id_members; // names of the Assistant group


    // constructor
    public MembersItemAdapter(Activity context, ArrayList<String> itemname, ArrayList<String>lastName, ArrayList<String> imgid, ArrayList<String> numOfPassenger,ArrayList<String> id_Members) {
        super(context, R.layout.listview_activity, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.lastName=lastName;
        this.numOfPassenger = numOfPassenger;
        this.id_members = id_Members;


    }

    public View getView(final int position, View view, ViewGroup parent)
    {

        ViewHolder viewHolder = null;
        view=null;
        if (view == null)
        {
            view = LayoutInflater.from(this.context).inflate(R.layout.listview_activity, null);
            viewHolder = new ViewHolder();
            viewHolder.pairWIthLin = (LinearLayout) view.findViewById(R.id.linaretParingWith);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.item);
            viewHolder.numOfPassengers = (TextView) view.findViewById(R.id.numberOfPassenger);
            viewHolder.extratxt = (TextView) view.findViewById(R.id.textView1);
            viewHolder.pairing_with = (TextView) view.findViewById(R.id.pairing_with);
            viewHolder.pairTv = (TextView) view.findViewById(R.id.pairTv);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.item);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            viewHolder.eyepair = (ImageView) view.findViewById(R.id.eyepair);

            view.setTag(viewHolder);
        }
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.eyepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPairDialog(finalViewHolder,"Pair with "+lastName.get(position),id_members.get(position),position);
            }
        });

        viewHolder.pairTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPairDialog(finalViewHolder,"Pair with "+lastName.get(position),id_members.get(position),position);
            }
        });
        viewHolder.txtTitle.setText(itemname.get(position));
        viewHolder.numOfPassengers.setText(numOfPassenger.get(position));
        Picasso.with(context).load(imgid.get(position)).transform(new CircleImage()).into(viewHolder.imageView);
        viewHolder.extratxt.setText(lastName.get(position));

        return view; // return view for everyitem//

    };
    public void showPairDialog(final ViewHolder viewHolder, String name, final String pos, final int postion) {

        View pairDialog = LayoutInflater.from(context).inflate(R.layout.pair_dialog, null);
        final Button pair = (Button) pairDialog.findViewById(R.id.goingWithButton);
        final Button cancel = (Button) pairDialog.findViewById(R.id.joinButton);
        TextView nameOfParng = (TextView) pairDialog.findViewById(R.id.nameOfParng);

        nameOfParng.setText(name);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(pairDialog);


        final AlertDialog dialog4 = builder.create();
        dialog4.show();
        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invitePepole(viewHolder,pos,postion);
                dialog4.hide();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog4.hide();

            }
        });

    }


    public void invitePepole(final ViewHolder viewHolder, final String pos, final int postion)
    {

        SharedPreferences settings=context.getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String group_id = settings.getString("gId","-1");
        SharedPreferences settings2=context.getSharedPreferences("UserLog",MODE_PRIVATE);
        String id_user = settings2.getString("id","-1");
        //api/invitefriend/{inviter_id}/{group_id}

        //api/invitefriend/{inviter_id}/{group_id}


        StringRequest sr = new StringRequest(Request.Method.POST, "http://172.104.150.56/api/sendnotification", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {



                viewHolder.pairWIthLin.setVisibility(View.VISIBLE);
                viewHolder.pairing_with.setText(" " + lastName.get(postion)+ " ");
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                Log.i("responsak",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Eror", Toast.LENGTH_SHORT).show();
                Log.i("responsak","Error");


            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();

                String str = "[";
                str = str + "{\"id\":\"" + pos + "\"}]";
                SharedPreferences settings2=context.getSharedPreferences("UserLog",MODE_PRIVATE);
                String id_user = settings2.getString("id","-1");
                SharedPreferences settings=context.getSharedPreferences("SnapGroup",MODE_PRIVATE);
                String group_id = settings.getString("gId","-1");
                params2.put("sender_id", id_user);
                params2.put("type", "invite");
                params2.put("group_id", group_id);
                params2.put("invitees", str);


                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(sr);

    }


    public class ViewHolder{
        LinearLayout pairWIthLin;
        TextView txtTitle ;
        TextView numOfPassengers;
        ImageView imageView;
        ImageView eyepair;
        TextView extratxt ;
        TextView pairTv;
        TextView pairing_with;
    }







}