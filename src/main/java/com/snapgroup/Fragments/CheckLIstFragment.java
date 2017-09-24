package com.snapgroup.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snapgroup.Activities.ContactsCompletionView;
import com.snapgroup.Activities.Person;
import com.snapgroup.R;
import com.snapgroup.Tests.HosenShapesTests;


public class CheckLIstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    ContactsCompletionView completionView;
    public  Person[] people;
    ArrayAdapter<Person> adapter;

    public CheckLIstFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragmentNew,new CheckLIstFragment()).addToBackStack(null).commit();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_check_list, container, false);

        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar5);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setIcon(null);
        actionBar.setTitle(null);
        actionBar.setBackgroundDrawable(null);


        final LinearLayout linaretLaout = (LinearLayout) view.findViewById(R.id.ln221);
        LinearLayout ln2s21 = (LinearLayout) view.findViewById(R.id.ln2s21);
        final EditText addRequire = (EditText)view.findViewById(R.id.another_requirment);
        Button submit = (Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addRequire.getText().toString().equals("")==false)
                {

                    final CheckBox c = new CheckBox(getActivity().getApplicationContext());
                    c.setText(addRequire.getText().toString());
                    c.setPadding(20, 15, 15, 15);
                    c.setTextSize(16);
                    c.setTextColor(Color.parseColor("#D3D3D3"));
                    c.setButtonDrawable(R.drawable.unchecked);
                    c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                Drawable drawable = getResources().getDrawable(R.drawable.checked_icon);
                                c.setTextColor(Color.BLACK);
                                c.setButtonDrawable(R.drawable.checked);

                            } else {
                                c.setTextColor(Color.parseColor("#D3D3D3"));
                                c.setButtonDrawable(R.drawable.unchecked);

                            }
                        }
                    });
                    linaretLaout.addView(c);
                    addRequire.setText("");
                }
                else
                    Toast.makeText(getActivity(), "You Must Add Requirement", Toast.LENGTH_SHORT).show();
            }
        });

        String [] requirad,recommnd;
        requirad = new String[8];
        requirad[0]="Passport";
        requirad[1]="Warm clothes";
        requirad[2]="Swim suit";
        requirad[3]="Water bootle";
        requirad[4]="Another thing with some explanation and details";
        requirad[5]="A valid";
        requirad[6]="Another thing";
        requirad[7]="Another thing";

        recommnd = new String[8];
        recommnd[0]="Passport";
        recommnd[1]="Warm clothes";
        recommnd[2]="Swim suit";
        recommnd[3]="Water bootle";
        recommnd[4]="Another thing with some explanation and details";
        recommnd[5]="A valid";
        recommnd[6]="Another thing";
        recommnd[7]="Another thing";
        for (int i=0;i<6;i++)
        {
            final CheckBox c = new CheckBox(getActivity().getApplicationContext());
            c.setText(requirad[i]);
            c.setPadding(20,15,15,15);
            c.setTextSize(16);

            c.setTextColor(Color.BLACK);
            c.setButtonDrawable(R.drawable.unchecked);



            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Drawable drawable = getResources().getDrawable(R.drawable.checked_icon);
                        c.setButtonDrawable(R.drawable.checked);
                        c.setTextColor(Color.parseColor("#D3D3D3"));




                    }
                    else {
                        c.setTextColor(Color.BLACK);

                        c.setButtonDrawable(R.drawable.unchecked);


                    }
                    //  c.setButtonDrawable(R.drawable.qqqqqqqq);

                    // c.setButtonDrawable(R.drawable.qqqqqqqq);


                }
            });
            linaretLaout.addView(c);




        }
        for (int i=0;i<4;i++)
        {
            final CheckBox c = new CheckBox(getActivity().getApplicationContext());
            c.setText(recommnd[i]);
            c.setPadding(20,15,15,15);
            c.setTextSize(16);
            c.setTextColor(Color.BLACK);
            c.setButtonDrawable(R.drawable.unchecked);


            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Drawable drawable = getResources().getDrawable(R.drawable.checked_icon);
                        c.setTextColor(Color.parseColor("#D3D3D3"));

                        c.setButtonDrawable(R.drawable.checked);

                    }
                    else {
                        c.setTextColor(Color.BLACK);

                        c.setButtonDrawable(R.drawable.unchecked);

                    }

                }
            });

            ln2s21.addView(c);



        }




        return view;
    }


}
