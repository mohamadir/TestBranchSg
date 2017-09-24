package com.snapgroup.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.snapgroup.R;

import java.util.Calendar;

/**
 * Created by root on 25/07/17.
 */

public class DatePickeerFragment2 extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView tv2= (TextView) getActivity().findViewById(R.id.settingsActiivty_dateToTv);
        String m,d;
        int m2=view.getMonth()+1;
        if(view.getMonth()<10)
            m="0"+m2;
        else
            m=""+m2;
        if(view.getDayOfMonth()<10)
            d="0"+view.getDayOfMonth();
        else
            d=""+view.getDayOfMonth();
        tv2.setText(view.getYear()+"-"+m+"-"+d);

    }

}