package com.snapgroup.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snapgroup.R;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;

/**
 * Sample token completion view for basic contact info
 *
 * Created on 9/12/13.
 * @author mgod
 */
public class ContactsCompletionView extends TokenCompleteTextView<Person> {
    ArrayList<String> persons_id  = new ArrayList<String>();

    public ArrayList<String> getPersons_id() {
        return persons_id;
    }
    public String getStringForAll ()
    {
        if (persons_id.size()==0)
        {
            return "";
            //Toast.makeText(JoinGroupActivity.class, "persons is emplty", Toast.LENGTH_SHORT).show();
        }
        else {
            String str = "[";
            for (int i = 0; i < persons_id.size(); i++)
                str = str + "{\"id\":\"" + persons_id.get(i) + "\"},";

            StringBuilder sb = new StringBuilder(str);
            sb.deleteCharAt(str.length() - 1);
            str = sb.toString();
            str = str + "]";


//  "invitees":"[{"id":"1"},{"id":"2"}]"
            return str;
        }

    }

    public void setPersons_id(ArrayList<String> persons_id) {
        this.persons_id = persons_id;
    }


    public ContactsCompletionView(Context context ) {
        super(context);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(Person Person)
    {
        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TokenTextView token = (TokenTextView) l.inflate(R.layout.contact_token, (ViewGroup) getParent(), false);
        token.setText(Person.getName());


        if (Person.getId().matches("[0-9]+") )
        {

            persons_id.add(Person.getId());
            token.setText(Person.getName());
            return token;
        }
        else {
            return token;

        }


    }

    @Override
    protected Person defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new Person(completionText, completionText.replace("", ""),completionText);
        } else {
            return new Person(completionText.substring(0, index), completionText,completionText);
        }
    }


}
