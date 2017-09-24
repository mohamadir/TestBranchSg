package com.snapgroup.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.Classes.JoinPersonInformation;
import com.snapgroup.R;

import java.util.ArrayList;

/**
 * Created by root on 18/09/17.
 */

public class JoinListViewAdapter extends BaseAdapter {



    ArrayList<JoinPersonInformation> anotherPerosnsss;
    public Activity context_1;
    public JoinListViewAdapter(Activity context,ArrayList<JoinPersonInformation> aanotherPerosnsss) {

        context_1 = context;
        anotherPerosnsss=aanotherPerosnsss;

    }

    @Override
    public int getCount() {
        return anotherPerosnsss.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override

    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        JoinListViewAdapter.JoinAnotherPerson joinAnotherPerson= null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {
            ProgressDialog progress = new ProgressDialog(context_1);
            progress.setTitle("Please Wait!!");
            progress.setMessage("Wait!!");

            convertView = LayoutInflater.from(context_1).inflate(R.layout.join_item_another_person, null);
            joinAnotherPerson = new JoinListViewAdapter.JoinAnotherPerson();
            joinAnotherPerson.firstName = (TextView) convertView.findViewById(R.id.anotherFirstName);
            joinAnotherPerson.lastName = (TextView) convertView.findViewById(R.id.anotherLastName);


            convertView.setTag(joinAnotherPerson);
        }

        joinAnotherPerson.firstName.setText(anotherPerosnsss.get(position).getFirstname());
        joinAnotherPerson.lastName.setText(anotherPerosnsss.get(position).getLastname());

        /*joinAnotherPerson.age.setText(anotherPerosnsss.get(position).getAge());
        joinAnotherPerson.passport_id.setText(anotherPerosnsss.get(position).getPassport_id());
*/
        return convertView;

    }


    class JoinAnotherPerson
    {
        public TextView firstName,lastName,passport_id,age;
    }
}
