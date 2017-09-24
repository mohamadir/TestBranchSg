package com.snapgroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snapgroup.R;
import com.snapgroup.Tests.HosenShapesTests;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewVotingsFragmentTabs.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewVotingsFragmentTabs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewVotingsFragmentTabs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private OnFragmentInteractionListener mListener;

    public NewVotingsFragmentTabs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewVotingsFragmentTabs.
     */
    // TODO: Rename and change types and number of parameters
    public static NewVotingsFragmentTabs newInstance(String param1, String param2) {
        NewVotingsFragmentTabs fragment = new NewVotingsFragmentTabs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_votings_fragment_tabs, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons(view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void createTabIcons(View view) {


        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("Tab 1");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.t1, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Tab 2");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.t2, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar5);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("Tab 3");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.t3, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFore = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabFore.setText("Tab 4");
        tabFore.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.t4, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFore);

        TextView tabFive = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabFive.setText("Tab 5");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.t5, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        TextView tabSex = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabSex.setText("Tab 6");
        tabSex.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.t6, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSex);

    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new VoteFragmentTabs(), "Tab 1");
        adapter.addFrag(new VoteFragmentTabs(), "Tab 2");
        adapter.addFrag(new VoteFragmentTabs(), "Tab 3");
        adapter.addFrag(new VoteFragmentTabs(), "Tab 4");
        adapter.addFrag(new VoteFragmentTabs(), "Tab 5");
        adapter.addFrag(new VoteFragmentTabs(), "Tab 6");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
