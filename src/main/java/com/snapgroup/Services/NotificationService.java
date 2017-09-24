package com.snapgroup.Services;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.snapgroup.Classes.Constants;
import com.snapgroup.Models.InboxMessage;
import com.snapgroup.R;
import com.snapgroup.Tests.NotificationTestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * Created by snapgroup2 on 07/09/17.
 */

public class NotificationService extends Service {
    Context context;
    private Boolean isConnected = false;
    private static final String TAG = "ChatFragment";
    private Socket mSocket;
    public static  int TOTAL_CREATE_SERVICE=0;

    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public NotificationService()
    {
        Log.i("serviceNoti","NotificationService");

    }

    @Override
    public void onDestroy() {
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.emit("subscribe", "notification-3");
        mSocket.emit("subscribe", "member-32");
        mSocket.on("notification-3:notifier", onGetNotification);
        mSocket.on("member-32:member-channel", onGetNotification);
        //mSocket.on("typing", onTyping);
//        mSocket.on(" typing", onStopTyping);`
        mSocket.connect();

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TOTAL_CREATE_SERVICE++;
        Log.i("TOTAL_CREATE_SERVICE",""+TOTAL_CREATE_SERVICE);
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.emit("subscribe", "notification-3");
       // mSocket.emit("subscribe", "member-32");
        mSocket.on("notification-3:notifier", onGetNotification);
        mSocket.on("member-32:member-channel", onGetInvite);
        //mSocket.on("typing", onTyping);
//        mSocket.on(" typing", onStopTyping);`
        mSocket.connect();
       return START_NOT_STICKY;
    }
    Handler handler;
    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
    // On Connect
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

                    //JSONObject data = (JSONObject) args[0];
                    // Log.e(TAG, data.toString());
                    if(!isConnected) {
                        Log.i(Constants.TAG, "Connected.");
                        isConnected = true;
                    }
                 /*   Context context=getApplicationContext();
                    Intent intent = new Intent(NotificationService.this,InboxMessage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(NotificationService.this)
                                    .setSmallIcon(R.drawable.logo2)
                                    .setContentTitle("SnapGroup First Noti Test")
                                    .setContentIntent(pendingIntent)
                                    .setContentText("Click to see SnapMessages");

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(
                                    Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());*/
                }

    };
    // On Disconnect
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(Constants.TAG, "Disconnect..");

                    Log.i(Constants.TAG, "diconnected");
                    isConnected = false;

        }
    };
    // On Connect Error
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

                    Log.e(Constants.TAG, args[0] +"Error connecting");
                    //JSONObject data = (JSONObject) args[0];
                    // Log.e(TAG, data.toString());

        }
    };
    // On New Message
    private Emitter.Listener onGetNotification = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.i(Constants.TAG, "Notification");
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject notificationJsonObject=data.getJSONObject("notification");
                        String subject=notificationJsonObject.getString("subject");
                        String body=notificationJsonObject.getString("body");
                        String type=notificationJsonObject.getString("type");
                        makeNotification(subject,body,type);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("notifier", "error push "+e.getMessage().toString());
                    }


            SharedPreferences notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
            int NotCount=notificationSP.getInt("notification",0);
            Log.i("Emitter", NotCount+"");
            SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
            editor.putInt("notification",++NotCount);
            editor.commit();


        }
    };

    // On New Message
    private Emitter.Listener onGetInvite = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.i(Constants.TAG, "Notification");
            JSONObject data = (JSONObject) args[0];
            makeNotification("invite","mohamd","test");



            SharedPreferences notificationSP=getSharedPreferences("Notification",MODE_PRIVATE);
            int NotCount=notificationSP.getInt("notification",0);
            Log.i("Emitter", NotCount+"");
            SharedPreferences.Editor editor=getSharedPreferences("Notification",MODE_PRIVATE).edit();
            editor.putInt("notification",++NotCount);
            editor.commit();


        }
    };
    private void makeNotification(String s, String subject, String body) {
        Context context=getApplicationContext();
        Intent intent = new Intent(NotificationService.this,InboxMessage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotificationService.this)
                        .setSmallIcon(R.drawable.logo2)
                        .setContentTitle(subject)
                        .setContentIntent(pendingIntent)
                        .setContentText(body)
                        .setSound(Uri.parse("android.resource://com.snapgroup/"+R.raw.dog2))
                      //  .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_MAX);
        ;

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}