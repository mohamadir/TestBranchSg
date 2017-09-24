package com.snapgroup.Tests;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.snapgroup.Classes.Constants;
import com.snapgroup.Models.InboxMessage;
import com.snapgroup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NotificationTestActivity extends AppCompatActivity {
    private Boolean isConnected = false;
    private static final String TAG = "ChatFragment";
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.emit("subscribe", "notification-3");
        mSocket.on("notification-3:notifier", onGetNotification);

        //mSocket.on("typing", onTyping);
//        mSocket.on("stop typing", onStopTyping);
        mSocket.connect();
    }
    // On Connect
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            NotificationTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    // Log.e(TAG, data.toString());
                    if(!isConnected) {
                        Log.i(Constants.TAG, "Connected.");
                        Toast.makeText(NotificationTestActivity.this, R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                    Context context=getApplicationContext();
                    Intent intent = new Intent(NotificationTestActivity.this,InboxMessage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(NotificationTestActivity.this, 0, intent, 0);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(NotificationTestActivity.this)
                                    .setSmallIcon(R.drawable.logo2)
                                    .setContentTitle("SnapGroup First Noti Test")
                                    .setContentIntent(pendingIntent)
                                    .setContentText("Click to see SnapMessages");

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(
                                    Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());
                }
            });
        }
    };
    // On Disconnect
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(Constants.TAG, "Disconnect..");
            NotificationTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(Constants.TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(NotificationTestActivity.this.getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    // On Connect Error
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            NotificationTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(Constants.TAG, "Error connecting");
                    //JSONObject data = (JSONObject) args[0];
                    // Log.e(TAG, data.toString());
                    Toast.makeText( NotificationTestActivity.this, R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    // On New Message
    private Emitter.Listener onGetNotification = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.i(Constants.TAG, "Notification");
            NotificationTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject notificationJsonObject=data.getJSONObject("notification");
                        String subject=notificationJsonObject.getString("subject");
                        String body=notificationJsonObject.getString("body");
                        makeNotification(subject,body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("notifier", (String) args[0]);
                    }
                }
            });
        }
    };

    private void makeNotification(String subject, String body) {
        Context context=getApplicationContext();
        Intent intent = new Intent(NotificationTestActivity.this,InboxMessage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationTestActivity.this, 0, intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotificationTestActivity.this)
                        .setSmallIcon(R.drawable.logo2)
                        .setContentTitle(subject)
                        .setContentIntent(pendingIntent)
                        .setContentText(body);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
