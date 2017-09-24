package com.snapgroup.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.UiThread;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.snapgroup.Activities.MemberInboxActivity;
import com.snapgroup.Classes.Constants;
import com.snapgroup.Models.InboxMessage;
import com.snapgroup.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import io.socket.emitter.Emitter;

public class NotificationService2 extends Service implements Runnable {

    private static final int NOTIFICATION_ID = 1;

    private boolean mRunning = false;
    private Thread mThread;
    public Context context;
    private Socket mSocket;
    private Boolean isConnected = false;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private InputStream mInputStream;
    public NotificationService2(Context context){
        this.context=context;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Constants.TAG,"connected");
        mSocket.on(io.socket.client.Socket.EVENT_CONNECT,onConnect);
        mSocket.on(io.socket.client.Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(io.socket.client.Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(io.socket.client.Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.emit("subscribe", "notification-3");

        // mSocket.emit("subscribe", "member-32");
        mSocket.on("notification-3:notifier", onGetNotification);
        mSocket.on("member-32:member-channel", onGetInvite);
        //mSocket.on("typing", onTyping);
//        mSocket.on(" typing", onStopTyping);`
        mSocket.connect();

        mRunning = true;

        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(Constants.TAG,"connected");


        mThread = new Thread(this);
        mThread.start();
        return START_NOT_STICKY;

    }

    @Override
    public void run() {
        Log.i(Constants.TAG,"running..");
            mSocket.on(io.socket.client.Socket.EVENT_CONNECT, onConnect);
            mSocket.on(io.socket.client.Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on(io.socket.client.Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(io.socket.client.Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            mSocket.emit("subscribe", "notification-3");
            // mSocket.emit("subscribe", "member-32");
            mSocket.on("notification-3:notifier", onGetNotification);
            mSocket.on("member-32:member-channel", onGetInvite);
            //mSocket.on("typing", onTyping);
//        mSocket.on(" typing", onStopTyping);`
            mSocket.connect();

            mRunning = true;

        //mSocket.on("typing", onTyping);
//        mSocket.on(" typing", onStopTyping);`



    }

    private void setNotificationMessage(CharSequence message) {
        Log.i("finallosh", "NOttttti");
        /*Intent intent = new Intent(NotificationService2.this,MemberInboxActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logo2);
        builder.setContentTitle("Testosh");
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.logo2);
        builder.setSound(Uri.parse("android.resource://com.snapgroup/"+R.raw.ding));
        builder.setPriority(Notification.PRIORITY_MAX);
        NotificationManagerCompat nm = NotificationManagerCompat.from(context.getApplicationContext());
        nm.notify(NOTIFICATION_ID, builder.build());
    }
    private void makeNotification(String s, String subject, String body) {
        Intent intent = new Intent(NotificationService2.this,InboxMessage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotificationService2.this)
                        .setSmallIcon(R.drawable.logo2)
                        .setContentTitle(subject)
                        .setContentIntent(pendingIntent)
                        .setContentText(body)
                        .setSound(Uri.parse("android.resource://com.snapgroup/"+R.raw.ding))
                        //  .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_MAX);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }



    @Override
    public void onDestroy() {
     /*   if (mThread != null) {
            mRunning = false;
            setNotificationMessage("Service destroyed");
            while (true) {
                try {
                    mThread.interrupt();
                    mThread.join();
                    mThread = null;
                    break;
                } catch (InterruptedException ignored) {
                    setNotificationMessage("Service destroyed");

                }
            }
        }

        setNotificationMessage("Service destroyed");

        super.onDestroy();*/
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
          //      makeNotification(subject,body,type);
                setNotificationMessage("Test 1");

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("notifier", "error push "+e.getMessage().toString());
            }


            SharedPreferences notificationSP=context.getSharedPreferences("Notification",MODE_PRIVATE);
            int NotCount=notificationSP.getInt("notification",0);
            Log.i("Emitter", NotCount+"");
            SharedPreferences.Editor editor=context.getSharedPreferences("Notification",MODE_PRIVATE).edit();
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
       //     makeNotification("invite","mohamd","test");
            setNotificationMessage("Test 1");


            SharedPreferences notificationSP=context.getSharedPreferences("Notification",MODE_PRIVATE);
            int NotCount=notificationSP.getInt("notification",0);
            Log.i("Emitter", NotCount+"");
            SharedPreferences.Editor editor=context.getSharedPreferences("Notification",MODE_PRIVATE).edit();
            editor.putInt("notification",++NotCount);
            editor.commit();


        }
    };

}