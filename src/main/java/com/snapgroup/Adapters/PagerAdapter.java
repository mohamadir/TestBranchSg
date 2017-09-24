package com.snapgroup.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snapgroup.Fragments.AccountDetailsFragment;
import com.snapgroup.Fragments.MyHistoryFragment;
import com.snapgroup.Fragments.MyFilesFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }



    @Override
    public Fragment getItem(int position) {
        MyFilesFragment tab3 = new MyFilesFragment();

        switch (position) {
            case 0:
                AccountDetailsFragment tab1 = new AccountDetailsFragment();
                return tab1;
            case 1:
                MyHistoryFragment tab2 = new MyHistoryFragment();
                return tab2;
            case 2:
                tab3 = new MyFilesFragment();
                return tab3;
            case 3:
                tab3 = new MyFilesFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}