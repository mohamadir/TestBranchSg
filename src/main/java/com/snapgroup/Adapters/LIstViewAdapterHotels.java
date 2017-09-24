package com.snapgroup.Adapters;

/**
 * Created by root on 07/08/17.
 */

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snapgroup.Activities.RegisterActivity;
import com.snapgroup.Classes.HotelServiceLocation;
import com.snapgroup.R;

import java.util.ArrayList;


public class LIstViewAdapterHotels extends BaseAdapter {
    boolean use_last_focus=false;
    int currentlyFocusedRow=-1;
    String currentlyFocusedField="";
    private ArrayAdapter<String> adapterAuto;
    private int lastFocussedPosition = -1;
    private Handler handler = new Handler();
    private ArrayList<String> daysNumbeer;
    public ArrayList<HotelServiceLocation> location_and_date;
    private ArrayList<String> numbersOfDyas;

    int a;

    private Activity context_1;
    private boolean[]daysoffOn;
    public LIstViewAdapterHotels(Activity context,ArrayAdapter<String> adapterAuto, ArrayList<String> daysNumber,ArrayList<HotelServiceLocation> location_and_date,int a)
    {
        context_1 = context;
        this.adapterAuto = adapterAuto;
        this.daysNumbeer = daysNumber;
        this.location_and_date= location_and_date;
        this.daysoffOn = daysoffOn;
        this.numbersOfDyas = numbersOfDyas;
        this.a=a;
        this.a=4;

    }

    @Override
    public int getCount() {
        return daysNumbeer.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {

        Log.i("getViewMethod",position+"");

        ViewHolder viewHolder = null;
        // initilize the new view that will convert the simple view

        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.selcethotelitem, null);
            viewHolder = new ViewHolder();
            viewHolder.dayNumber = (TextView) convertView.findViewById(R.id.hotelDayNumber);
           viewHolder.text = (AutoCompleteTextView)convertView.findViewById(R.id.autoCompleteTextView23);
            viewHolder.nightbuttonclick = (Button)convertView.findViewById(R.id.nightbuttonclick);
            viewHolder.applyButton = (Button)convertView.findViewById(R.id.applyButton);
            viewHolder.applyButton = (Button)convertView.findViewById(R.id.applyButton);

            final ViewHolder finalViewHolder = viewHolder;
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Handler handler = new Handler();
        final ViewHolder finalViewHolder2 = viewHolder;
        handler.postDelayed(new Runnable() {
            public void run() {
         //       if (finalViewHolder2.text != null) {
       //        }
            }
        }, 100);
        viewHolder.text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                currentlyFocusedRow=position;

            }
        });
        if(position==0){

            viewHolder.nightbuttonclick.setVisibility(View.GONE);
            viewHolder.nightbuttonclick.setTag(0);
       //     location_and_date.add(0,new HotelServiceLocation(viewHolder.text.getText().toString(), viewHolder.dayNumber.getText().toString()));
         //   Log.i("asdfsadwerwer",location_and_date.get(position).toString());
           /* viewHolder.text.requestFocus();
            viewHolder.text.setFocusable(true);*/
            final ViewHolder finalViewHolder1 = viewHolder;

        }
        else {

       //     viewHolder.text.requestFocus();

            final View finalConvertView1 = convertView;
            viewHolder.nightbuttonclick.setVisibility(View.VISIBLE);
            viewHolder.nightbuttonclick.setTag(position);
            final ViewHolder finalViewHolder1 = viewHolder;


            viewHolder.nightbuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final View viewItemFirst = viewGroup.getChildAt(position-1);
                AutoCompleteTextView auto = viewItemFirst.findViewById(R.id.autoCompleteTextView23);
                final String st = auto.getText().toString();
//                Log.i("asdfsadwerwer",location_and_date.get(position).toString());

                Button bt = finalConvertView1.findViewById(R.id.nightbuttonclick);
               //For passing the list item index
                bt.setTag(position);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      /*  ClipData.Item  item= (ClipData.Item) getItem((int)finalConvertView1.getTag());
                        Log.i("postionOfAutoComplet", position+"");
                        AutoCompleteTextView editte=(AutoCompleteTextView)finalConvertView1.findViewById(R.id.autoCompleteTextView23);
                        TextView daynumbver12 = (TextView)finalConvertView1.findViewById(R.id.hotelDayNumber);

                        editte.setText(st);*/



                    }
                });

            }


        });



        }
       /* final ViewHolder finalViewHolder123 = viewHolder;
        viewHolder.applyButton.setTag(position);
        viewHolder.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              *//*  location_and_date.add(new HotelServiceLocation(finalViewHolder123.text.getText().toString(), finalViewHolder123.dayNumber.getText().toString()));
                Log.i("etoriutuetruoitr",location_and_date.get(position).toString());
*//*               View edittextView= LayoutInflater.from(context_1).inflate(R.layout.edittext_dialog,null);
                final EditText editTextD = (EditText) edittextView.findViewById(R.id.edittext_dialogEt);

                AlertDialog.Builder builder = new AlertDialog.Builder(context_1);
                builder.setMessage("Validation");
                builder.setView(edittextView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context_1, editTextD.getText()+"---", Toast.LENGTH_SHORT).show();
                        // hotelsByDaysRequest(userNameEt.getText().toString(),passwordEt.getText().toString(),firstNameEt.getText().toString(),lastNameEt.getText().toString());

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        viewHolder.dayNumber.setText(daysNumbeer.get(position));
       viewHolder.text.setAdapter(adapterAuto);
       viewHolder.text.setThreshold(1);*/


       // notifyDataSetChanged();
      //  notifyDataSetChanged();
        return convertView;

    }
    public ArrayList<HotelServiceLocation> getDaysArray(){
        return this.location_and_date;
    }


    public class ViewHolder {
        public TextView dayNumber;
        public  TextView nearme;
        public  TextView LocationTv;
        public Button nightbuttonclick,applyButton;
       public AutoCompleteTextView text;
        Spinner spinner;

    }

}