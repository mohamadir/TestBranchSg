package com.snapgroup.Classes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.snapgroup.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jahid on 12/10/15.
 */
public class HotelServiceDatePickerFrom extends DialogFragment
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
        TextView tv1= (TextView) getActivity().findViewById(R.id.hotel_service_tvFrom);
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
        tv1.setText(d + "-" +m+"-"+view.getYear());
        Calendar c = Calendar.getInstance();      c.add(Calendar.DATE, 1);
        Date newDate = c.getTime();

        view.setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));

    }

}