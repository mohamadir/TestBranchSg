package com.snapgroup.Tests;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.snapgroup.Activities.SignInActivity;
import com.snapgroup.Models.Contact;
import com.snapgroup.R;

public class ContactsActivity extends AppCompatActivity {
     ArrayAdapter<String> arrayAdapter;
    public ProgressDialog pd;

    Button loadBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        pd=new ProgressDialog(ContactsActivity.this);
        pd.setMessage("Wait please.. ");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        arrayAdapter= new ArrayAdapter<String>(ContactsActivity.this, android.R.layout.select_dialog_singlechoice);
        getContacts();
        loadBt=(Button)findViewById(R.id.loadContactsBt);
        loadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog();
            }
        });

    }
    private void loadDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ContactsActivity.this);
        builderSingle.setIcon(R.drawable.logo2);
        builderSingle.setTitle("Select One Name:-");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setPositiveButton("LOAD ALL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ContactsActivity.this);
                builderInner.setTitle("Your Selected All the Items");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
                dialog.dismiss();
            }
        } );
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ContactsActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    public void getContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        arrayAdapter.add(name);
                        Log.i("Contacts222","Name: " + name+ ", Phone No: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        pd.hide();
    }
}
