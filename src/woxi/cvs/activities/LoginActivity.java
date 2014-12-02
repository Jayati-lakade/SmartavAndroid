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
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.db.DBContract.TABLE_TYPE;
import woxi.cvs.db.DBHelper;
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
		actionBar.hide();
		SharedPreferences preferences = this.getSharedPreferences(ConstantSmartAV.PREFERENCES, Context.MODE_PRIVATE);
		editor = preferences.edit();
		String username = preferences.getString(ConstantSmartAV.USERNAME_STR, ConstantSmartAV.ERROR_STRING);
		String password = preferences.getString(ConstantSmartAV.PASSWORD_STR, ConstantSmartAV.ERROR_STRING);
		
		if(!(username.equals(ConstantSmartAV.ERROR_STRING) || password.equals(ConstantSmartAV.ERROR_STRING))){
			isUserAvailable = true;
		}

		setContentView(R.layout.activity_login);

		etUsername = (EditText) this.findViewById(R.id.etUsername);
		etPassword = (EditText) this.findViewById(R.id.etPassword);
		btnLogin = (Button) this.findViewById(R.id.btnLogin);

		if (isUserAvailable) {
			etUsername.setVisibility(View.GONE);
			etPassword.setVisibility(View.GONE);
			btnLogin.setVisibility(View.GONE);
			
			((TextView)this.findViewById(R.id.loginPageTitle)).setText(getString(R.string.loading));
			
			new FecthDataFromDB().execute(new String[]{});
		}
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (etUsername.getText().toString().trim().length() <= 0) {
					Toast.makeText(LoginActivity.this, "Please enter Username",
							Toast.LENGTH_SHORT).show();
				} else if (etPassword.getText().toString().trim().length() <= 0) {
					Toast.makeText(LoginActivity.this, "Please enter Password",
							Toast.LENGTH_SHORT).show();
				} else {
					if (Util.isConnectingToInternet(getApplicationContext())) {
						new LoginTask()
								.execute(new String[] { ConstantSmartAV.INPUT_JSON_URL
										+ "username="
										+ etUsername.getText().toString()
										+ "&password="
										+ etPassword.getText().toString()
										+ "&version="
										+ ConstantSmartAV.VERSION_ID });
					} else {
						Util.showToast(getString(R.string.internetUnavailable),
								getApplicationContext(), true);
					}
				}
			}
		});
	}

	/*
	 * Desc:fetch the data from inout table according to drwaer selection
	 * Developed By:Sourabh shah version:1.1
	 */
	private class FecthDataFromDB extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (!result.equals(ConstantSmartAV.ERROR_STRING)) {
				startActivity(new Intent(LoginActivity.this,
						MainActivityNew.class));
				finish();
			} else {
				Util.showToast(getString(R.string.errorDB),
						getApplicationContext(), true);
			}
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				DBUtil dbUtil = new DBUtil(getApplicationContext());
				ArrayList<FreshTask> freshList = (ArrayList<FreshTask>) dbUtil.fetchInputData(ConstantSmartAV.FRESHTASK);
				ArrayList<WLTask> wlList = (ArrayList<WLTask>) dbUtil.fetchInputData(ConstantSmartAV.WLTASK);
				ArrayList<BulkTask> bulkList = (ArrayList<BulkTask>) dbUtil.fetchInputData(ConstantSmartAV.BulkTASK);

				if (freshList != null) {
					DataLoader.freshTaskList = freshList;					
					}
				if (wlList != null) {
					DataLoader.wlTaskList = wlList;
				}
				if (bulkList != null) {
					DataLoader.bulkTaskList = bulkList;
				}
				return "";
			} catch (Exception e) {
				e.printStackTrace();
				return ConstantSmartAV.ERROR_STRING;
			}
		}

	}

	private class LoginTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LoginActivity.this);
			// progressDialog = ProgressDialog.show(LoginActivity.this, "",
			// "Loading...", false);
			progressDialog.setMessage("Logging...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				String jsonStr = callWS(params[0]);
				if ((jsonStr.trim()).equalsIgnoreCase(ConstantSmartAV.FALSE)) {
					return ConstantSmartAV.FALSE;
				} else if (jsonStr != ConstantSmartAV.ERROR_STRING) {
					processJSON(jsonStr);
				} else {

					progressDialog.dismiss();
				}
				return jsonStr;
			} catch (Exception e) {
				e.printStackTrace();
				return ConstantSmartAV.ERROR_STRING;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			result = result.trim();
			if(result.equalsIgnoreCase(ConstantSmartAV.FALSE)){
			
				Toast.makeText(LoginActivity.this, "Please check Username and Password.", Toast.LENGTH_LONG).show();
			} else if (result.equals(ConstantSmartAV.VERSION_STRING)) {
			
				Toast.makeText(LoginActivity.this, "Please upgrade SmartAV version.", Toast.LENGTH_LONG).show();
			} else if (!result.equals(ConstantSmartAV.ERROR_STRING)) {

				 Util.initiateAlarmManager(getApplicationContext());
				 
				editor.putString(ConstantSmartAV.USERNAME_STR, etUsername.getText().toString());
				editor.putString(ConstantSmartAV.PASSWORD_STR, etPassword.getText().toString());
				editor.commit();

				startActivity(new Intent(LoginActivity.this,
						MainActivityNew.class));
				finish();

			} else {
			
				Toast.makeText(LoginActivity.this, "You are not authorized. Please check your credentials.", Toast.LENGTH_LONG).show();
			}
		}
	};

	public String callWS(String urlStr) {

		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(ConstantSmartAV.TIMEOUT);
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

			return ConstantSmartAV.ERROR_STRING;
		} catch (IOException e) {
			e.printStackTrace();

			return ConstantSmartAV.ERROR_STRING;
		}

		finally {
			connection.disconnect();
		}

	}

	private void processJSON(String jsonStr) {
		ArrayList<FreshTask> freshList = null;
		ArrayList<WLTask> wlList = null;
		ArrayList<BulkTask> bulkList = null;

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonStr);

			JSONArray freshTasksArray = null, wlTasksArray = null, BulkTasksArray = null;
			Gson gson = new Gson();

			if (jsonObject.has("Fresh")) {
				freshTasksArray = jsonObject.getJSONArray("Fresh");
				freshList = getFreshList(freshTasksArray, gson);
			} else if (jsonObject.has("FRESH")) {
				freshTasksArray = jsonObject.getJSONArray("FRESH");
				freshList = getFreshList(freshTasksArray, gson);
			}

			DataLoader.freshTaskList = freshList;

			if (jsonObject.has("WL")) {
				wlTasksArray = jsonObject.getJSONArray("WL");
				wlList = getWLList(wlTasksArray, gson);
			} else if (jsonObject.has("wl")) {
				wlTasksArray = jsonObject.getJSONArray("wl");
				wlList = getWLList(wlTasksArray, gson);
			}
			DataLoader.wlTaskList = wlList;

			if (jsonObject.has("BULK")) {
				BulkTasksArray = jsonObject.getJSONArray("BULK");
				bulkList = getBulkList(BulkTasksArray, gson);
			} else if (jsonObject.has("Bulk")) {
				BulkTasksArray = jsonObject.getJSONArray("Bulk");
				bulkList = getBulkList(BulkTasksArray, gson);
			}
			DataLoader.bulkTaskList = bulkList;

			DBUtil dbUtil = new DBUtil(getApplicationContext());
			dbUtil.deleteData(TABLE_TYPE.INPUT_TABLE);
			/*
			 * Desc:To sync the data on Login button click DeveLoped By:Jayati
			 * Lakade. Version:1.6
			 */
			String visitJson = dbUtil.fetchOutputData();
			visitJson = encodeData(visitJson);
			Boolean output;
			DBHelper dbhelper;

			if (visitJson.equals(ConstantSmartAV.NO_RECORDS_FOUND)
					|| (!visitJson.equals((ConstantSmartAV.NO_RECORDS_FOUND)) || (!visitJson
							.equals((ConstantSmartAV.ERROR_STRING))))) {
				output = Util.synchroniseData(getApplicationContext());
				System.out
						.println("In Login------------------------------------------------------------------------------------");
				if (output) {
					dbUtil.deleteData(TABLE_TYPE.OUTPUT_TABLE);
					System.out
							.println("Deleted from login ************************************************************************");
				}

			}
			long retVal = dbUtil.insertIntoInputTable(freshList, wlList,
					bulkList);
			if (retVal == ConstantSmartAV.ERROR_RETURN_VAL) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ArrayList<FreshTask> getFreshList(JSONArray freshTasksArray,
			Gson gson) {

		Type typeToken1 = new TypeToken<ArrayList<FreshTask>>() {
		}.getType();
		return gson.fromJson(freshTasksArray.toString(), typeToken1);
	}

	private ArrayList<WLTask> getWLList(JSONArray wlTasksArray, Gson gson) {

		Type typeToken2 = new TypeToken<ArrayList<WLTask>>() {
		}.getType();
		return gson.fromJson(wlTasksArray.toString(), typeToken2);
	}

	private String encodeData(String name) {

		name = name.replace("{", "APPLEATE");
		name = name.replace("}", "BALLBAT");
		name = name.replace("\"", "CATCOPY");
		name = name.replace("/", "DOGDUMP");
		name = name.replace(":", "EAGLEEGG");
		name = name.replace("\n", "FROG");
		name = name.replace("+", "HOUSE");
		name = name.replace("\\u", "IAMTOPPER");
		name = name.replace(",", "JACK");
		return name;

	}

	private ArrayList<BulkTask> getBulkList(JSONArray BulkTasksArray, Gson gson) {

		Type typeToken3 = new TypeToken<ArrayList<BulkTask>>() {
		}.getType();
		return gson.fromJson(BulkTasksArray.toString(), typeToken3);
	}

}
