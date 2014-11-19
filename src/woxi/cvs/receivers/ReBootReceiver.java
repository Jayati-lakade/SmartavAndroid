package woxi.cvs.receivers;

import woxi.cvs.util.Util;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
        	Log.i("RebootReceiver", "onReceive.....");
        	Util.initiateAlarmManager(context);
        }
    }
}