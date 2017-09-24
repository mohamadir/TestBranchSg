package com.snapgroup.ServiceForever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.snapgroup.Services.NotificationService;

/**
 * Created by snapgroup2 on 19/09/17.
 */

public class AlarmReceiverLifeLog extends BroadcastReceiver {

    private static final String TAG = "LL24";
    static Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm for LifeLog...", Toast.LENGTH_SHORT).show();
        Intent ll24Service = new Intent(context, NotificationService.class);
        context.startService(ll24Service);
    }
}