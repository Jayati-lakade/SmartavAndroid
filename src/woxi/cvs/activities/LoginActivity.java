package woxi.cvs.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import woxi.cvs.R;
import woxi.cvs.db.DBContract.TABLE_TYPE;
import woxi.cvs.db.DBUtil;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.DataLoader;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import woxi.cvs.util.Util;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginActivity extends Activity {

	// action bar
	private ActionBar actionBar;
	private EditText etUsername, etPassword;
	private Button btnLogin;
	private SharedPreferences.Editor editor;
	private boolean isUserAvailable;
	private static String TAG = "LoginActivity";
	private Visit visit;
	public int taskFlag=999;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		/*actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setLogo(getResources().getDrawable(R.drawable.docomo));*/
		actionBar.hide();
		
		SharedPreferences preferences = this.getSharedPreferences(Util.PREFERENCES, Context.MODE_PRIVATE);
		editor = preferences.edit();
		String username = preferences.getString(Util.USERNAME_STR, Util.ERROR_STRING);
		String password = preferences.getString(Util.PASSWORD_STR, Util.ERROR_STRING);
		
		if(!(username.equals(Util.ERROR_STRING) || password.equals(Util.ERROR_STRING))){
			isUserAvailable = true;
		}
		
		setContentView(R.layout.activity_login);
		
		etUsername = (EditText) this.findViewById(R.id.etUsername);
		etPassword = (EditText) this.findViewById(R.id.etPassword);
		btnLogin = (Button) this.findViewById(R.id.btnLogin);
		
		if(isUserAvailable){
			etUsername.setVisibility(View.GONE);
			etPassword.setVisibility(View.GONE);
			btnLogin.setVisibility(View.GONE);
			
			((TextView)this.findViewById(R.id.loginPageTitle)).setText(getString(R.string.loading));
			
			new FecthDataFromDB().execute(new String[]{});
		}
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(etUsername.getText().toString().trim().length() <= 0){
					Toast.makeText(LoginActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
				} else if(etPassword.getText().toString().trim().length() <= 0){
					Toast.makeText(LoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
				} else {
					if(Util.isConnectingToInternet(getApplicationContext())){
						//new LoginTask().execute(new String[]{"http://ubuntuone.com/2LI5GfYAOfRZLd9t7SGiIV"});
						//new LoginTask().execute(new String[]{"http://www.woxiprogrammers.com/CVSONE/ajax/tablet/getInputTasklist.php?username=ofr&password=ofr"});
						new LoginTask().execute(new String[]{Util.INPUT_JSON_URL + "username="+etUsername.getText().toString()+"&password="+etPassword.getText().toString()+"&version="+Util.VERSION_ID});
					}else{
						Util.showToast(getString(R.string.internetUnavailable), getApplicationContext(),true);
					}
				}
			}
		});
	}
	
	private class FecthDataFromDB extends AsyncTask<String, Integer, String>{

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(!result.equals(Util.ERROR_STRING)){
				startActivity(new Intent(LoginActivity.this,
						MainActivityNew.class));
				finish();
			}else{
				Util.showToast(getString(R.string.errorDB), getApplicationContext(), true);
			}
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			try {
				DBUtil dbUtil = new DBUtil(getApplicationContext());
				
				ArrayList<FreshTask> freshList = (ArrayList<FreshTask>) dbUtil.fetchInputData(Util.FRESHTASK);
				ArrayList<WLTask> wlList = (ArrayList<WLTask>) dbUtil.fetchInputData(Util.WLTASK);
				ArrayList<BulkTask> bulkList = (ArrayList<BulkTask>) dbUtil.fetchInputData(Util.BulkTASK);
				
				if(freshList != null){
					DataLoader.freshTaskList = freshList;
					
					
			//		 taskFlag = visit.setTaskFlag(0);

				}
				if(wlList != null){
					DataLoader.wlTaskList = wlList;
				//	 taskFlag = visit.setTaskFlag(0);
				}	if(bulkList != null){
					DataLoader.bulkTaskList = bulkList;
				//	 taskFlag = visit.setTaskFlag(1);
				}
				
				return "";
			}catch (Exception e) {
				e.printStackTrace();
				return Util.ERROR_STRING;
			}
		}

	}
	
	
	
	private class LoginTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LoginActivity.this);
			//progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading...", false);
			progressDialog.setMessage("Logging...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
				
			try{
				String jsonStr = callWS(params[0]);
				if((jsonStr.trim()).equalsIgnoreCase(Util.FALSE)){
					return Util.FALSE;
				}
				else if(jsonStr != Util.ERROR_STRING){
					processJSON(jsonStr);
				}else{
					Log.i("doInBackground", "error in method fetchJson@@@@@@@");
					progressDialog.dismiss();
				}				
				return jsonStr;
			}catch (Exception e) {
				e.printStackTrace();
				return Util.ERROR_STRING;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			result = result.trim();
			if(result.equalsIgnoreCase(Util.FALSE)){
				Log.i(TAG, "Invalid User");
				Toast.makeText(LoginActivity.this, "Please check Username and Password.", Toast.LENGTH_LONG).show();
			} else if (result.equals(Util.VERSION_STRING)) {
				Log.i("", "Version Not Matched");
				Toast.makeText(LoginActivity.this, "Please upgrade SmartAv version.", Toast.LENGTH_LONG).show();
			} else if (!result.equals(Util.ERROR_STRING)) {

				 Util.initiateAlarmManager(getApplicationContext());
				 
				editor.putString(Util.USERNAME_STR, etUsername.getText().toString());
				editor.putString(Util.PASSWORD_STR, etPassword.getText().toString());
				editor.commit();
	
				startActivity(new Intent(LoginActivity.this,
						MainActivityNew.class));
				finish();

			} else {
				Log.i("", "Exception occured");
				Toast.makeText(LoginActivity.this, "You are not authorized. Please check your credentials.", Toast.LENGTH_LONG).show();
			}
		}
	};
	
	
	
	public String callWS(String urlStr) {

		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url
					.openConnection();
			connection.setReadTimeout(Util.TIMEOUT);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed to fetch data ");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}
			
			return builder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
			return Util.ERROR_STRING;
		} catch (IOException e) {
			e.printStackTrace();
			
			return Util.ERROR_STRING;
		}
		
		finally{
			connection.disconnect();
		}

	}
		
		private void processJSON(String jsonStr)
		{
			ArrayList<FreshTask> freshList = null;
			ArrayList<WLTask> wlList = null;
			ArrayList<BulkTask> bulkList = null;
			
			
			
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(jsonStr);
				Log.i("", "jsonObject.length() : " + jsonObject.length());
				
				JSONArray freshTasksArray = null, wlTasksArray = null,BulkTasksArray = null;
				Gson gson = new Gson();
				
				if(jsonObject.has("Fresh")){
					freshTasksArray = jsonObject.getJSONArray("Fresh");
					freshList = getFreshList(freshTasksArray, gson);
					// taskFlag = visit.setTaskFlag(0);
				}else if(jsonObject.has("FRESH")){
					freshTasksArray = jsonObject.getJSONArray("FRESH");
					freshList = getFreshList(freshTasksArray, gson);
					// taskFlag = visit.setTaskFlag(0);
				}
					
					Log.i("", "freshListArraL : " + freshList);
					DataLoader.freshTaskList = freshList;
				
				
				if(jsonObject.has("WL") ){
					wlTasksArray = jsonObject.getJSONArray("WL");
					wlList = getWLList(wlTasksArray, gson);
				//	 taskFlag = visit.setTaskFlag(0);
				}else if(jsonObject.has("wl")){
					wlTasksArray = jsonObject.getJSONArray("wl");
					wlList = getWLList(wlTasksArray, gson);
					// taskFlag = visit.setTaskFlag(0);
				}
					
					Log.i("", "wlListArrayL : " + wlList);
					DataLoader.wlTaskList = wlList;
					
					if(jsonObject.has("BULK") ){
						BulkTasksArray = jsonObject.getJSONArray("BULK");
						bulkList = getBulkList(BulkTasksArray, gson);
					//	 taskFlag = visit.setTaskFlag(0);
					}else if(jsonObject.has("Bulk")){
						BulkTasksArray = jsonObject.getJSONArray("Bulk");
						bulkList = getBulkList(BulkTasksArray, gson);
						// taskFlag = visit.setTaskFlag(0);
					}
						
						Log.i("", "Bulk : " + bulkList);
						DataLoader.bulkTaskList = bulkList;
				
				DBUtil dbUtil = new DBUtil(getApplicationContext());
				dbUtil.deleteData(TABLE_TYPE.INPUT_TABLE);
				dbUtil.deleteData(TABLE_TYPE.OUTPUT_TABLE);
				
				long retVal = dbUtil.insertIntoInputTable(freshList, wlList,bulkList);
				if(retVal == Util.ERROR_RETURN_VAL){
					Log.i("error !!!", "Error inserting data into DB!!!!!!!");
				}
					
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		private ArrayList<FreshTask> getFreshList(JSONArray freshTasksArray, Gson gson){
			Log.i("","freshTasksArray.length() : "+ freshTasksArray.length());
			Type typeToken1 = new TypeToken<ArrayList<FreshTask>>(){}.getType();
			// taskFlag = visit.setTaskFlag(0);
			return gson.fromJson(freshTasksArray.toString(), typeToken1);
		}
		
		private ArrayList<WLTask> getWLList(JSONArray wlTasksArray, Gson gson){
			Log.i("","wlTasksArray.length() : " + wlTasksArray.length());
			Type typeToken2 = new TypeToken<ArrayList<WLTask>>(){}.getType();
		//	 taskFlag = visit.setTaskFlag(0);
			return gson.fromJson(wlTasksArray.toString(), typeToken2);
		}

		private ArrayList<BulkTask> getBulkList(JSONArray BulkTasksArray, Gson gson){
			Log.i("","TasksArray.length() : " + BulkTasksArray.length());
			Type typeToken3 = new TypeToken<ArrayList<BulkTask>>(){}.getType();
			// taskFlag = visit.setTaskFlag(0);
			return gson.fromJson(BulkTasksArray.toString(), typeToken3);
		}
		/*private String fetchJson(String urlStr) {
		URL url;
		BufferedReader reader = null;
		try {
			url = new URL(urlStr);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}
			return builder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return Util.ERROR_STRING;
		} catch (IOException e) {
			e.printStackTrace();
			return Util.ERROR_STRING;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				return Util.ERROR_STRING;
			}
		}
	}*/
}
