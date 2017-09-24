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

import com.snapgroup.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GridViewJoin extends BaseAdapter{
    public Context mContext;
    public  ArrayList<String> names;

    public GridViewJoin(Context c,ArrayList<String> names ) {
        mContext = c;
      this.names=  names;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.size();
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
            grid = inflater.inflate(R.layout.custom_tab_item, null);
            TextView nameButton= (TextView) grid.findViewById(R.id.nameButton);
            ImageView remiveButton = (ImageView) grid.findViewById(R.id.remiveButton);
            nameButton.setText(names.get(position));
            /*remiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
                }
            });*/


        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}

