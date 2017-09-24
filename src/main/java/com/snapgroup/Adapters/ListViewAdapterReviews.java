package com.snapgroup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 04/09/17.
 */

public class ListViewAdapterReviews  extends BaseAdapter
{

    private Activity context_1;
    ArrayList<String> nameProfile;
    ArrayList<Integer> imageProfile;
    ArrayList<String> reviewText;
    ArrayList<String> ratingNumber;

    public ListViewAdapterReviews(Activity context_1, ArrayList<String> nameProfile, ArrayList<Integer> imageProfile, ArrayList<String> reviewText, ArrayList<String> ratingNumber) {
        this.context_1 = context_1;
        this.nameProfile = nameProfile;
        this.imageProfile = imageProfile;
        this.reviewText = reviewText;
        this.ratingNumber = ratingNumber;
    }

    @Override
    public int getCount() {
        return nameProfile.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public View getViewByPostion(int postion, View convertView, ViewGroup viewGroup)
    {
        View view = getView(postion,convertView,viewGroup);
        return view;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {

        ListViewAdapterReviews.ItemReview servicePerDay = null;
        // initilize the new view that will convert the simple view

        convertView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.profile_member_list_item, null);
            servicePerDay = new ListViewAdapterReviews.ItemReview();
            servicePerDay.firstNameTextViewProfile = (TextView) convertView.findViewById(R.id.firstNameTextViewProfile) ;
            servicePerDay.reviewTextProfile = (TextView) convertView.findViewById(R.id.reviewTextProfile) ;
            servicePerDay.reatingReviewNUmber = (TextView) convertView.findViewById(R.id.reatingReviewNUmber) ;
            servicePerDay.imageViewReview = (ImageView) convertView.findViewById(R.id.imageViewReview) ;
            convertView.setTag(servicePerDay);
        }
        servicePerDay.firstNameTextViewProfile.setText(nameProfile.get(position));
        servicePerDay.reviewTextProfile.setText(reviewText.get(position));
        servicePerDay.reatingReviewNUmber.setText(ratingNumber.get(position));
        Picasso.with(context_1).load(imageProfile.get(position)).into(servicePerDay.imageViewReview);



        return convertView;

    }


    public class ItemReview {
        public TextView firstNameTextViewProfile, reviewTextProfile, reatingReviewNUmber;
        public ImageView imageViewReview;

        public ItemReview() {
        }

        public TextView getFirstNameTextViewProfile() {
            return firstNameTextViewProfile;
        }

        public void setFirstNameTextViewProfile(TextView firstNameTextViewProfile) {
            this.firstNameTextViewProfile = firstNameTextViewProfile;
        }

        public TextView getReviewTextProfile() {
            return reviewTextProfile;
        }

        public void setReviewTextProfile(TextView reviewTextProfile) {
            this.reviewTextProfile = reviewTextProfile;
        }

        public TextView getReatingReviewNUmber() {
            return reatingReviewNUmber;
        }

        public void setReatingReviewNUmber(TextView reatingReviewNUmber) {
            this.reatingReviewNUmber = reatingReviewNUmber;
        }

    }
}