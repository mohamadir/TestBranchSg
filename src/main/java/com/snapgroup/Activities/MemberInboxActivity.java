package com.snapgroup.Activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.snapgroup.Adapters.GroupLIstAdapter2;
import com.snapgroup.Adapters.MessagesAdapter;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Classes.Paginator;
import com.snapgroup.Models.GroupInList;
import com.snapgroup.Models.InboxMessage;
import com.snapgroup.Models.NotificationItem;
import com.snapgroup.R;
import com.srx.widget.PullToLoadView;
import com.twotoasters.jazzylistview.JazzyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MemberInboxActivity extends AppCompatActivity {

    ArrayList<NotificationItem> messagesArray;
    ListView messageLv;
    MessagesAdapter messagesAdapter;
    SharedPreferences notificationSP;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_inbox);
        messageLv=(ListView)findViewById(R.id.inboxLv);
        messagesArray=new ArrayList<NotificationItem>();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(null);
        actionBar.setTitle(null);
        actionBar.setBackgroundDrawable(null);
        setNotificationCount();
        PullToLoadView pullToLoadView= (PullToLoadView) findViewById(R.id.pullToLoadView);
        new Paginator(this,pullToLoadView).initializePaginator();
        SharedPreferences notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
        int NotCount=notificationSP.getInt("notification",0);
        Log.i("Emitter", NotCount+"");

        getNotificationsRequest();
      /*  messagesArray.add(new InboxMessage("David Cohen","London To Israel","19/3/2015",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",1));
        messagesArray.add(new InboxMessage("Rafi Arnold","Gara To Israel","09/3/2018",
                "sfgdhjkgj sfdgh fhjpdsfj dlgjfh fdfs lkdgfldtsg ;dlfkgj ;lgfhk\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",0));
        messagesArray.add(new InboxMessage("David Cohen","London To Israel","19/3/2015",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",0));
        messagesArray.add(new InboxMessage("Rafi Arnold","Gara To Israel","09/3/2018",
                "sfgdhjkgj sfdgh fhjpdsfj dlgjfh fdfs lkdgfldtsg ;dlfkgj ;lgfhk\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",1));
        messagesArray.add(new InboxMessage("David Cohen","London To Israel","19/3/2015",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",1));
        messagesArray.add(new InboxMessage("David Cohen","London To Israel","19/3/2015",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",0));
        messagesArray.add(new InboxMessage("David Cohen","London To Israel","19/3/2015",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",0));
        messagesArray.add(new InboxMessage("Rafi Arnold","Gara To Israel","09/3/2018",
                "sfgdhjkgj sfdgh fhjpdsfj dlgjfh fdfs lkdgfldtsg ;dlfkgj ;lgfhk\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",0));
        messagesArray.add(new InboxMessage("David Cohen","London To Israel","19/3/2015",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam\n" +
                        "nonummy nibh euismod tincidunt ut laoreet dolore....",1));
        messagesAdapter=new MessagesAdapter(this,messagesArray);
        messageLv.setAdapter(messagesAdapter);*/

    }

    private void setNotificationCount() {
        notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
        tv= (TextView)findViewById(R.id.messageCountTv);
        int NotCount=notificationSP.getInt("notification",-1);
        SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
        editor.putInt("notification",++NotCount);
        editor.commit();

        if(NotCount<=99) {
            tv.setTextSize(10);
            tv.setText("" + NotCount);
        }
        else
        {
            tv.setTextSize(8);
            tv.setText("99+");
        }
        notificationSP.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("notification"))
                {
                    Log.i("notificationSP",sharedPreferences.getInt("notification",0)+"");
                    int NotCount=sharedPreferences.getInt("notification",0);
                    if(NotCount<=99) {
                        tv.setTextSize(10);
                        tv.setText("" + NotCount);
                    }
                    else
                    {
                        tv.setTextSize(8);
                        tv.setText("99+");
                    }
                    ShortcutBadger.applyCount(getBaseContext(), NotCount);
                }
            }
        });
    }

    public void getNotificationsRequest(){
        String url = "http://172.104.150.56/api/getnotifications/32";
        messagesArray.clear();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray notificationsArray = response;
                        for(int i=0;i<notificationsArray.length();i++)
                        {
 // String id, String sender, String group_id, String subject, String body, String createdAt, String notificationId, boolean status) {

                            try {
                                if(notificationsArray.getJSONObject(i).getString("read").equals("0")) {
                                    if (notificationsArray.getJSONObject(i).getString("accepted") != null &&
                                            notificationsArray.getJSONObject(i).getString("accepted").equals("false")
                                            )
                                        messagesArray.add(
                                                new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                        notificationsArray.getJSONObject(i).getString("body").toString(),
                                                        notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                        notificationsArray.getJSONObject(i).getString("id").toString(),
                                                        false,
                                                        notificationsArray.getJSONObject(i).getString("type").toString(),
                                                       "accepted"
                                                ));
                                    else
                                        if(notificationsArray.getJSONObject(i).getString("accepted") != null &&
                                                notificationsArray.getJSONObject(i).getString("accepted").equals("true")
                                                )
                                            messagesArray.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("id").toString(),
                                                            false,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "declined"
                                                    ));
                                    else
                                            messagesArray.add(
                                                    new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                            notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                            notificationsArray.getJSONObject(i).getString("body").toString(),
                                                            notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                            notificationsArray.getJSONObject(i).getString("id").toString(),
                                                            false,
                                                            notificationsArray.getJSONObject(i).getString("type").toString(),
                                                            "none"
                                                    ));
                                }
                                else {
                                    if (notificationsArray.getJSONObject(i).getString("accepted") != null &&
                                            notificationsArray.getJSONObject(i).getString("accepted").equals("false")
                                            )
                                        messagesArray.add(
                                                new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                        notificationsArray.getJSONObject(i).getString("body").toString(),
                                                        notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                        notificationsArray.getJSONObject(i).getString("id").toString(),
                                                        true,
                                                        notificationsArray.getJSONObject(i).getString("type").toString(),
                                                        "accepted"
                                                ));
                                    else
                                    if(notificationsArray.getJSONObject(i).getString("accepted") != null &&
                                            notificationsArray.getJSONObject(i).getString("accepted").equals("true")
                                            )
                                        messagesArray.add(
                                                new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                        notificationsArray.getJSONObject(i).getString("body").toString(),
                                                        notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                        notificationsArray.getJSONObject(i).getString("id").toString(),
                                                        true,
                                                        notificationsArray.getJSONObject(i).getString("type").toString(),
                                                        "declined"
                                                ));
                                    else
                                        messagesArray.add(
                                                new NotificationItem(notificationsArray.getJSONObject(i).getString("sender_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("group_id").toString(),
                                                        notificationsArray.getJSONObject(i).getString("subject").toString(),
                                                        notificationsArray.getJSONObject(i).getString("body").toString(),
                                                        notificationsArray.getJSONObject(i).getString("created_at").toString(),
                                                        notificationsArray.getJSONObject(i).getString("id").toString(),
                                                        true,
                                                        notificationsArray.getJSONObject(i).getString("type").toString(),
                                                        "none"
                                                ));


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        MessagesAdapter glAdapter= new MessagesAdapter(MemberInboxActivity.this,messagesArray);
                        messageLv.setAdapter(glAdapter);
                        Log.i("MyGroups",messagesArray.size()+"");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("groupsGet1","error ");
                    }

                });


        MySingleton.getInstance(MemberInboxActivity.this).addToRequestQueue(jsObjRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }
}
