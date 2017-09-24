package com.snapgroup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snapgroup.Activities.GroupListActivity;
import com.snapgroup.Activities.MainActivity;
import com.snapgroup.Activities.ProfileMemberActivity;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.R;
import com.snapgroup.Models.GroupInList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

    public ArrayList<String> alName;
    public ArrayList<String> alImage;
    public ArrayList<GroupInList> groupList;


    Context context;

    public HLVAdapter(Context context, ArrayList<String> alName, ArrayList<String> alImage,ArrayList<GroupInList> groupList) {
        super();
        this.context = context;
        this.alName = alName;
        this.alImage = alImage;
        this.groupList = groupList;
    }
  /*  public View currentItem(int i){


    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public  void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.destiantion.setText(groupList.get(i).getDestination());
        viewHolder.origin.setText(groupList.get(i).getOrigin());
        Log.i("reasdae",i+ "    Aaa");
        String st = String.valueOf(alImage.get(i));
        Picasso.with(context).load(alImage.get(i)).into(viewHolder.imgThumbnail);



       // viewHolder.imgThumbnail.setImageResource((Integer.parseInt(alImage.get(i))));
        /*viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, ProfileMemberActivity.class));
                } else {

                    //SnapGroup

                    Log.i("GROOPY2","asd");


                    SharedPreferences.Editor editor=context.getSharedPreferences("SnapGroup",MODE_PRIVATE).edit();
                    Log.i("GROOPY2",groupList.get(position).getImage());
                    editor.putString("gImage", groupList.get(position).getImage());
                    editor.putString("gDescription", groupList.get(position).getDescritption());
                    editor.putString("gDestination", groupList.get(position).getDestination());
                    editor.putString("gId", String.valueOf(groupList.get(position).get_id()));
                    editor.putString("gOrigin", groupList.get(position).getOrigin());
                    editor.putString("gStart", groupList.get(position).getStart_date());
                    editor.putString("gEnd", groupList.get(position).getEnd_date());
                    editor.putString("gTitle", groupList.get(position).getTitle());
                    editor.commit();



                    Intent intent=new Intent(context,MainActivity.class);
                    context.startActivity(intent);


                    Toast.makeText(context, "#" + position + " - " + alName.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return alName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgThumbnail;
        public TextView destiantion,origin;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            destiantion = (TextView)itemView.findViewById(R.id.destiantion);
            origin = (TextView)itemView.findViewById(R.id.origin);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }



        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }



        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}

