package com.snapgroup.Adapters;

/**
 * Created by root on 05/09/17.

 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snapgroup.Models.MemberFIle;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyFilesGridAdapter extends BaseAdapter{
    public Context mContext;
    public  ArrayList<MemberFIle> filesArray;

    public MyFilesGridAdapter(Context c,ArrayList<MemberFIle> filesArray ) {
        mContext = c;
        this.filesArray=  filesArray;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return filesArray.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.file_item_layout, null);

        } else {
            grid = (View) convertView;
        }
        ImageView fileIv=(ImageView) grid.findViewById(R.id.fileIv);
        TextView fileNameTv=(TextView)grid.findViewById(R.id.fileNameTv);
        if(filesArray.get(position).mimeType.equals("image/png")||
                filesArray.get(position).mimeType.equals("image/jpeg")||
                filesArray.get(position).mimeType.equals("image/gif")||
                filesArray.get(position).mimeType.equals("image/jpg")||
                filesArray.get(position).mimeType.equals("image/bmp"))
        {
            fileIv.setImageResource(R.drawable.image_icon);
        }
     else   if(filesArray.get(position).mimeType.equals("application/pdf"))
        {
            fileIv.setImageResource(R.drawable.pdf_icon);
        }
    else    if(filesArray.get(position).mimeType.equals("text/plain")||
                filesArray.get(position).mimeType.equals("application/msword")||
                filesArray.get(position).mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
        {
            fileIv.setImageResource(R.drawable.word_icon);
        }
  else      if(filesArray.get(position).mimeType.equals("application/vnd.ms-excel")||
                filesArray.get(position).mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        {
            fileIv.setImageResource(R.drawable.excel_icon);
        }
 else
        {
            fileIv.setImageResource(R.drawable.unknown_file_icon);

        }
        fileNameTv.setText(filesArray.get(position).fileName);
        return grid;
    }
}

