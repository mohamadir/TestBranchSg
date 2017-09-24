package com.snapgroup.Tests;

/**
 * Created by root on 11/09/17.
 */


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snapgroup.Activities.GroupListActivity;
import com.snapgroup.Activities.NewGroupDetailsActitivty;
import com.snapgroup.Activities.NewGroupHotelsServiceActivity;
import com.snapgroup.R;
import com.squareup.picasso.Picasso;

public class imageViewPager extends PagerAdapter {
    Context context;
    public boolean zoomOut=false;
    private int[] GalImages = new int[] {
            R.drawable.london,
            R.drawable.paris,
            R.drawable.paris
    };

    LayoutInflater mLayoutInflater;

    public imageViewPager(Context context){
        this.context=context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imgParis);
       // imageView.setImageResource(GalImages[position]);
        Picasso.with(context).load(GalImages[position]).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}