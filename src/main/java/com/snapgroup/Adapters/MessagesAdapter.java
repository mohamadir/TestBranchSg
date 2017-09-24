package com.snapgroup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snapgroup.Models.InboxMessage;
import com.snapgroup.Models.NotificationItem;
import com.snapgroup.R;

import java.util.ArrayList;


/**
 * Created by snapgroup2 on 19/07/17.
 */

public class MessagesAdapter extends BaseAdapter {

    private ArrayList<NotificationItem> messagesArray;
    private Activity context_1;
    public MessagesAdapter(Activity context,
                           ArrayList<NotificationItem> messagesArray) {
        context_1 = context;
        this.messagesArray = messagesArray;
    }
    @Override
    public int getCount()
    {
        return messagesArray.size();

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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MessagesAdapter.ViewHolder viewHolder = null;
        // initilize the new view that will convert the simple view

        if (convertView == null) {
            if(!messagesArray.get(position).status)
            convertView = LayoutInflater.from(context_1).inflate(
                    R.layout.inbox_message_list_item_unread, null);
            else
                convertView = LayoutInflater.from(context_1).inflate(
                        R.layout.inbox_message_list_item_readed, null);

            viewHolder = new MessagesAdapter.ViewHolder();
            viewHolder.fromTv = (TextView) convertView.findViewById(R.id.fromTv);
            viewHolder.groupTv= (TextView) convertView.findViewById(R.id.groupTv);
            viewHolder.messageTv = (TextView) convertView.findViewById(R.id.messageTv);
            viewHolder.createdAtTv = (TextView) convertView.findViewById(R.id.createdAt);
            viewHolder.subjectTv = (TextView) convertView.findViewById(R.id.sybjectTv);

            convertView.setTag(viewHolder);
        } else {
            /**
             * Once the instance of the row item's control it will use from
             * already created controls which are stored in convertView as a
             * ViewHolder Instance
             * */
            viewHolder = (MessagesAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.fromTv.setText(messagesArray.get(position).sender+"");
        viewHolder.groupTv.setText(messagesArray.get(position).group_id+"");
        viewHolder.createdAtTv.setText(messagesArray.get(position).createdAt);
        viewHolder.subjectTv.setText(messagesArray.get(position).subject);
        viewHolder.messageTv.setText(messagesArray.get(position).body);
        return convertView;

    }



    public class ViewHolder{
        public TextView fromTv;
        public  TextView groupTv;
        public  TextView createdAtTv;
        public  TextView messageTv;
        public  TextView subjectTv;

    }

}
