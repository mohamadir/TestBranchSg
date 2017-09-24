package com.snapgroup.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.snapgroup.Classes.paymentItem;
import com.snapgroup.R;

import java.util.ArrayList;


/**
 * Created by snapgroup2 on 19/07/17.
 */

public class PaymentsAdapter extends BaseAdapter {

    private ArrayList<paymentItem> paymentArray;
    private Activity context_1;
    public PaymentsAdapter(Activity context,
                           ArrayList<paymentItem> paymentArray) {
        context_1 = context;
        this.paymentArray = paymentArray;
    }
    @Override
    public int getCount()
    {
        return paymentArray.size();

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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PaymentsAdapter.ViewHolder viewHolder = null;
        // initilize the new view that will convert the simple view

        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(
                    R.layout.payment_row, null);
            viewHolder = new PaymentsAdapter.ViewHolder();
            viewHolder.type = (TextView) convertView.findViewById(R.id.paymentTypeTv);
            viewHolder.price= (TextView) convertView.findViewById(R.id.paymentPriceTv);
            viewHolder.cardNumber = (TextView) convertView.findViewById(R.id.cardNumberTv);
            viewHolder.editCard = (Button) convertView.findViewById(R.id.paymentEditBt);
            viewHolder.status=(TextView) convertView.findViewById(R.id.paymentStatusTv);


            convertView.setTag(viewHolder);
        } else {
            /**
             * Once the instance of the row item's control it will use from
             * already created controls which are stored in convertView as a
             * ViewHolder Instance
             * */
            viewHolder = (PaymentsAdapter.ViewHolder) convertView.getTag();
        }
        // set the text of all the views in the item to be the values from the newsArray
        Log.i("payy","im here");
        viewHolder.type.setText(paymentArray.get(position).type);
        viewHolder.price.setText(paymentArray.get(position).price);
        viewHolder.status.setText(paymentArray.get(position).cardStatus);
        viewHolder.cardNumber.setText(paymentArray.get(position).cardNumber);
        if(paymentArray.get(position).cardStatus.equals("None")) {
            viewHolder.editCard.setText("Pay");
            viewHolder.editCard.setBackgroundResource(R.drawable.button_border_pay);
        }
        else {
            viewHolder.editCard.setText("Edit");
            viewHolder.editCard.setBackgroundResource(R.drawable.button_border);
        }

        return convertView;

    }



    public class ViewHolder{
        public TextView type;
        public  TextView price;
        public  TextView status;
        public  TextView cardNumber;
        public Button editCard;

    }

}
