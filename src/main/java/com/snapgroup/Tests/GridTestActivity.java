package com.snapgroup.Tests;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.snapgroup.R;

import java.util.ArrayList;

public class GridTestActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_test);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(GridTestActivity.this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);


    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i,i));
        }
        return imageItems;
    }
}
