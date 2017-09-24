package com.snapgroup.Classes;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.snapgroup.Activities.MemberInboxActivity;
import com.snapgroup.Adapters.MessagesAdapter;
import com.snapgroup.Adapters.NewMessageAdapter;
import com.snapgroup.Models.NotificationItem;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Oclemy on 12/8/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class Paginator {

    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    private NewMessageAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    int first_time=0;


    public Paginator(Context c, PullToLoadView pullToLoadView) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        pullToLoadView.setDrawingCacheBackgroundColor(Color.RED);
        //RECYCLERVIEW
        RecyclerView rv=pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL,false));
        adapter=new NewMessageAdapter(c,new ArrayList<NotificationItem>());
        rv.setAdapter(adapter);
        first_time=0;
        initializePaginator();
    }

    /*
    PAGE DATA
     */
    public void initializePaginator()
    {
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {

            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                Log.i("loadMore","onLoadMore- im here");
                isLoading=true;
                    loadData(nextPage);
            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll=false;
                isLoading=true;
                nextPage=1;
                Log.i("loadMore","onRefresh- im here");
                if(first_time==0){
                    first_time=1;
                }
                else {
                    loadData(nextPage);

                }
            }

            //IS LOADING
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {

                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }

    /*
     LOAD MORE DATA
     SIMULATE USING HANDLERS
     */
    public void loadData(final int page)
    {
        isLoading=true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNotificationsRequest(nextPage,page);
                //UPDATE PROPETIES
             //   first_time=1;

            }
        },3000);
    }


    public void getNotificationsRequest(final int next_Page, final int page){
        final String url = "http://172.104.150.56/api/getnotifications/32/"+next_Page;

        final JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray notificationsArray = response;
                        Log.i("groupsGet1"+url+":---",response.toString());
                        if(response.length()==0)
                            hasLoadedAll=true;
                        else
                            for(int i=0;i<notificationsArray.length();i++) {

                                try {
                                    if(notificationsArray.getJSONObject(i).getString("read").equals("0")) {
                                        if (
                                                notificationsArray.getJSONObject(i).getString("accepted").equals("1")
                                                )
                                            adapter.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("notification_id").toString(),
                                                            false,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "accepted"
                                                    ));
                                        else
                                        if(
                                                notificationsArray.getJSONObject(i).getString("accepted").equals("0")
                                                )
                                            adapter.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("notification_id").toString(),
                                                            false,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "declined"
                                                    ));
                                        else {
                                            Log.i("notititi","nonoe");
                                            adapter.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("notification_id").toString(),
                                                            false,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "none"
                                                    ));
                                        }
                                    }
                                    else {
                                        if (
                                                notificationsArray.getJSONObject(i).getString("accepted").equals("1")
                                                )
                                            adapter.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("notification_id").toString(),
                                                            true,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "accepted"
                                                    ));
                                        else
                                        if(
                                                notificationsArray.getJSONObject(i).getString("accepted").equals("0")
                                                )
                                            adapter.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("notification_id").toString(),
                                                            true,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "declined"
                                                    ));
                                        else
                                            adapter.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("notification_id").toString(),
                                                            true,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "none"
                                                    ));


                                    }

                                } catch (JSONException e) {
                                    isLoading=false;
                                    hasLoadedAll=true;
                                 //   nextPage=1;
                                    e.printStackTrace();

                                }
                            }
                        pullToLoadView.setComplete();
                        nextPage=page+1;
                        isLoading=false;
                        first_time=1;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1","error ");
                       // nextPage=1;
                        hasLoadedAll=true;
                    }

                });


        MySingleton.getInstance(c).addToRequestQueue(jsObjRequest);
    }
}