package com.snapgroup.Tests;

        import java.util.ArrayList;

        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.snapgroup.R;
        import com.squareup.picasso.Picasso;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
           // holder.dayNumberTv = (TextView) row.findViewById(R.id.text_daynumber);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        ImageItem item = data.get(position);
        if(position!=0&&((position+1)%4==0)|| position==data.size()-1){
            Picasso.with(context).load("https://user-images.githubusercontent.com/17565537/29669849-73bc8cac-88ed-11e7-85d8-51155e39487e.png").into(holder.image);
            holder.imageTitle.setText("Day \n" +
                    ""+position );
        }

else {
            Picasso.with(context).load("https://user-images.githubusercontent.com/17565537/29669235-6da0edd8-88eb-11e7-8885-fbd3be081846.png").into(holder.image);
            holder.imageTitle.setText("Day \n" + position);
        }


            //   holder.image.setImageBitmap(item.getImage());
        //holder.dayNumberTv.setText(""+(position+1));

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
        //TextView dayNumberTv;
    }
}