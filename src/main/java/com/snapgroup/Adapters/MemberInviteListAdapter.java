package com.snapgroup.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.Activities.ProfileMemberActivity;
import com.snapgroup.Classes.MemberInviteItem;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by snapgroup2 on 10/08/17.
 */

public class MemberInviteListAdapter extends BaseAdapter {
    private ArrayList<MemberInviteItem> membersArray;
    private Activity context_1;
    public MemberInviteListAdapter(Activity context,ArrayList<MemberInviteItem> membersArray)
    {
        context_1= context;
        this.membersArray=membersArray;
    }
    @Override
    public int getCount()
    {
        return membersArray.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {



        MemberInviteListAdapter.ViewHolder viewHolder = null;
        // initilize the new view that will convert the simple view

        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.member_choose_item_layout, null);
            viewHolder = new MemberInviteListAdapter.ViewHolder();
            viewHolder.name=(TextView)convertView.findViewById(R.id.chooseItemNameTv);
            viewHolder.profileImage=(ImageView) convertView.findViewById(R.id.member_choose_iv);
            viewHolder.checkIv=(ImageView) convertView.findViewById(R.id.checkIv);

            convertView.setTag(viewHolder);
        } else {
            /**
             * Once the instance of the row item's control it will use from
             * already created controls which are stored in convertView as a
             * ViewHolder Instance
             * */
            viewHolder = (MemberInviteListAdapter.ViewHolder) convertView.getTag();
        }
        // set the text of all the views in the item to be the values from the newsArray
        //
        viewHolder.name.setText(membersArray.get(i).firstName+membersArray.get(i).lastName);
        if(membersArray.get(i).image.equals("aaaaa"))
        {
            Picasso.with(context_1).load("http://www.wiki.sc4devotion.com/images/6/62/Wiki_no_image.png").into(viewHolder.profileImage);
        }
        else
            Picasso.with(context_1).load(membersArray.get(i).image).into(viewHolder.profileImage);

        return convertView;


    }
    public class ViewHolder{
        public TextView name;
        public ImageView profileImage,checkIv;
    }
}
