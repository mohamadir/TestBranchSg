package com.snapgroup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snapgroup.Fragments.PlanFragment;
import com.snapgroup.R;

import java.util.HashMap;
import java.util.List;


public  class ExpandlepaleLIstViewVOtes extends BaseExpandableListAdapter  implements ExpandableListView.OnGroupClickListener {

    private Activity activityDay;
    private LayoutInflater inflater;
    public List<HeaderVoting> headerVotingList;
    public List<ChildVotingInfo> childinfo;
    Handler handler = new Handler();

    private HashMap<String,ChildVotingInfo> listDataChild;



    public ExpandlepaleLIstViewVOtes(Activity activityDay,List<HeaderVoting> headerVotingLIst, HashMap<String,ChildVotingInfo> listChildData)
    {
        this.activityDay = activityDay;
        this.listDataChild = listChildData;
        this.headerVotingList = headerVotingLIst;
    }


    public Object getChild(int groupPosition, int childPosititon)
    {
        // listDataChild.get(listDataChild.get(groupPosition));
        return listDataChild.get(listDataChild.get(groupPosition));
    }

    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition; // return the child postion

    }




    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent)
    {

        ViewChildHolder viewChildHolder = null;

        if (convertView == null)
        {
            inflater = (LayoutInflater) activityDay.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // we take the id from the xml of the Plan_list _ Item for  every item we have object

            convertView = inflater.inflate(R.layout.plan_list_item, null);
            viewChildHolder =new ViewChildHolder();
            final ViewChildHolder finalViewChildHolder = viewChildHolder;
            final View finalConvertView = convertView;

                    finalViewChildHolder.votes_more = (TextView) convertView.findViewById(R.id.votes_more);
                    finalViewChildHolder.votes_text = (TextView) convertView.findViewById(R.id.votes_text);
                    finalViewChildHolder.when_text = (TextView) convertView.findViewById(R.id.when_text);
                    finalViewChildHolder.offres = (TextView) convertView.findViewById(R.id.offres);
                    finalViewChildHolder.selct_actev = (TextView) convertView.findViewById(R.id.selct_actev);
                    finalViewChildHolder.background= convertView.findViewById(R.id.linaretBaground);
                    finalViewChildHolder.selct_actev.setText(listDataChild.get(childPosition+"").getSelct_actev());

                    finalViewChildHolder.background.setFocusable(true);
                    finalViewChildHolder.background.requestFocus();

                    if(childPosition%2==0){
                        finalViewChildHolder.background.requestFocus();
                        finalViewChildHolder.background.setBackgroundColor(Color.parseColor("#d5cda0"));
                    }

                    finalConvertView.setTag(finalViewChildHolder);


          }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return this.listDataChild.size();
    }

    @Override
    public Object getGroup(int groupPosition) { // return the object of the group ( header)
        return this.headerVotingList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headerVotingList.size();
    } // return the size of the headers

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent)
    // to write on the header items...
    {
        ViewHeadHolder viewHeadHolder = null;

        final int focus=groupPosition;
        if (convertView == null)
        {

            LayoutInflater infalInflater = (LayoutInflater) this.activityDay.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.plan_list, null); // take the plan_list xml id and set the info to it./
            viewHeadHolder = new ViewHeadHolder();
            viewHeadHolder.headData = (TextView) convertView.findViewById(R.id.headerVoting) ;
            viewHeadHolder.headData.setText(headerVotingList.get(groupPosition).getHeadDate());
        }
        convertView.setTag(viewHeadHolder);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        return true;
    }

    public class ViewChildHolder
    {
        public TextView votes_more,votes_text,when_text,offres,selct_actev,textView176;
        public LinearLayout background;
    }
    public class ViewHeadHolder
    {
        TextView headData;
    }
}

