package woxi.cvs.receivers;

import woxi.cvs.util.Util;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MediaMountedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")){
			Log.i("MediaMountedReceiver", "onReceive");
			
			Util.initiateAlarmManager(context);
		}
	}

	
}
