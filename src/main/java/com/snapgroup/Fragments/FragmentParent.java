package com.snapgroup.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapgroup.R;
import com.snapgroup.planActivityy.FragmentChild;
import com.snapgroup.planActivityy.ViewPagerAdapter;

/**
 * Created by DAT on 9/1/2015.
 */
public class FragmentParent extends Fragment {
    public  static ViewPager viewPager;
    private ViewPagerAdapter adapter;
    public static int Current_Ittem=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        getIDs(view);

        return view;
    }

    private void getIDs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
        adapter = new ViewPagerAdapter(getFragmentManager(), getActivity(), viewPager);
        viewPager.setAdapter(adapter);
    }

    int selectedTabPosition;


    public void addPage(String pagename, Integer imageDays , String places , String date_hour , String bigDescripyion) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putString("data3", places);
        bundle.putString("data4", date_hour);
        bundle.putString("data5", bigDescripyion);
        bundle.putInt("data2", imageDays);
        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename,imageDays,places,date_hour,bigDescripyion);
        adapter.notifyDataSetChanged();

    }
    public void setItem(int i){
        this.viewPager.setCurrentItem(i);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
