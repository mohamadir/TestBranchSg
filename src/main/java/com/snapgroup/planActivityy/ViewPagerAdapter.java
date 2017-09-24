package com.snapgroup.planActivityy;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.snapgroup.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DAT on 8/16/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> dates_houres = new ArrayList<>();
    private final ArrayList<String> placeName = new ArrayList<>();
    private final ArrayList<String> bigDEscription = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    private final ArrayList<Integer> pictures = new ArrayList<>();
    Context context;
    ViewPager viewPager;

    public ViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager) {
        super(manager);
        this.context = context;
        this.viewPager = viewPager;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title, Integer i, String placeString, String date_hour , String bigDescription) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        dates_houres.add(title);
        placeName.add(title);
        bigDEscription.add(title);
        pictures.add(i);
    }

    public void removeFrag(int position) {
        Fragment fragment = mFragmentList.get(position);
        mFragmentList.remove(fragment);
        mFragmentTitleList.remove(position);
        destroyFragmentView(viewPager, position, fragment);
        notifyDataSetChanged();
    }

    public View getTabView(final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.cuustom_tab_item, null);
        TextView tabItemName = (TextView) view.findViewById(R.id.textViewTabItemName);
        CircleImageView tabItemAvatar =
            (CircleImageView) view.findViewById(R.id.imageViewTabItemAvatar);
        ImageButton remove = (ImageButton) view.findViewById(R.id.imageButtonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Remove", "Remove");
                removeFrag(position);
            }
        });

        tabItemName.setText(mFragmentTitleList.get(position));
        tabItemName.setTextColor(context.getResources().getColor(android.R.color.background_light));

        return view;
    }

    public void destroyFragmentView(ViewGroup container, int position, Object object) {
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) object);
        trans.commit();
    }



    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
