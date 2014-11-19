package woxi.cvs.receivers;

import woxi.cvs.db.DBContract.TABLE_TYPE;
import woxi.cvs.db.DBUtil;
import woxi.cvs.util.Util;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiver extends WakefulBroadcastReceiver{

	private Context context;
	private static String TAG = "AlarmReceiver";
	DBUtil dbUtil;   

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "inside onReceive");
		this.context = context;
		 dbUtil = new DBUtil(context);
		if (Util.isConnectingToInternet(context)) {
			new SyncVisitData().execute(new String[] {Util.OUTPUT_UPLOAD_DATA_URL});
			new SyncVisitImages().execute(new String[] {Util.OUTPUT_UPLOAD_IMAGE_URL});
			new UpdateDataPriority();
		}
	}
	private class SyncVisitData extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);			
			}

		@Override
		protected String doInBackground(String... params) {
			Boolean output;
			output = Util.synchroniseData(context);
			if (output) {
				Log.i(TAG, "Data Synchronised By Scheduler");
				dbUtil.deleteData(TABLE_TYPE.OUTPUT_TABLE);
				return Util.SUCCESS;
			} else {
				Log.i(TAG, "Data NOT Synchronised By Scheduler");
				return Util.ERROR_STRING;
			}
		}
	}
	
	private class SyncVisitImages extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			Log.i(TAG, "Inside PreExecute Method of SyncVisitImages");
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(TAG, "Inside PostExecute Method of SyncVisitImages");
			if(result.equalsIgnoreCase(Util.TRUE)){
				Log.i(TAG, "Images Synched Successfully");
			}else{
				Log.i(TAG, "Images NOT Synched Successfully");
			}
		}

		@Override
		protected String doInBackground(String... params) {
			Log.i(TAG, "Inside Background Method of SyncVisitImages");
	            boolean result = Util.synchroniseImage(context.getFilesDir(),context);
	            return ""+result;
			}

		}
	

	private class UpdateDataPriority extends AsyncTask<String, Void, String> {
		DBUtil dbUtil = new DBUtil(context);

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "UpdateDataPriority :PreExecute");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(TAG, "UpdateDataPriority :PostExecute");
		}

		@Override
		protected String doInBackground(String... params) {
			Log.i(TAG, "UpdateDataPriority :Background");
			dbUtil.updateInputPriorities();			
			return "";
		}
	}

}
