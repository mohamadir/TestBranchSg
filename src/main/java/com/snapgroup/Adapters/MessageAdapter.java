package com.snapgroup.Adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.snapgroup.Models.Message;
import com.snapgroup.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;
    Context context;
    public static String nickName="guest";
    public MessageAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        this.context=context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("chattosh","onCreateViewHolder");
        SharedPreferences settings=this.context.getSharedPreferences("ChatUser",MODE_PRIVATE);
        String whoChat=settings.getString("who","10");
        nickName=settings.getString("nick_name","guest");
        int layout = -1;

        if(viewType==Message.TYPE_MESSAGE)
        {
            if(whoChat.equals("32")) // replace the 31 with the id from sharedpreferences UserLog
                layout = R.layout.chat_item_right;

            else
                layout = R.layout.chat_item_left;
        }
        switch (viewType) {
            case Message.TYPE_ACTION:
                layout = R.layout.chat_item_typing;
                break;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Message message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
        viewHolder.setUsername(nickName);

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mMessageView;
        private RoundedImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsernameView = (TextView) itemView.findViewById(R.id.username);
            mMessageView = (TextView) itemView.findViewById(R.id.message);
            profileImage = (RoundedImageView) itemView.findViewById(R.id.chat_profile_iv);

        }

        public void setUsername(String username) {
            if (null == mUsernameView) return;
            mUsernameView.setText(username);
        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }

        public void setProfileImage(RoundedImageView profileImage) {
            this.profileImage = profileImage;
        }
    }
}
