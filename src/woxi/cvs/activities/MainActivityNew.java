/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package woxi.cvs.activities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import woxi.cvs.R;
import woxi.cvs.adapter.CVSExpandableListAdapter;
import woxi.cvs.db.DBContract.TABLE_TYPE;
import woxi.cvs.db.DBUtil;
import woxi.cvs.fragments.AboutFragment;
import woxi.cvs.fragments.BulkTaskListFragment;
import woxi.cvs.fragments.FreshTaskListFragment;
import woxi.cvs.fragments.SettingsFragment;
import woxi.cvs.fragments.WLTaskListFragment;
import woxi.cvs.model.DataLoader;
import woxi.cvs.model.NavDrawerItem;
import woxi.cvs.model.Visit;
import woxi.cvs.util.Util;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class MainActivityNew extends Activity implements OnGroupClickListener,
		OnChildClickListener, OnGroupExpandListener {
	private DrawerLayout mDrawerLayout;
	private ExpandableListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private SharedPreferences.Editor editor;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private Visit visit;
	// slide menu items
	private String[] navMenuTitles;
	private String[] navMenuTitlesChild;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private CVSExpandableListAdapter adapter;
	private static int SELECTED_CHILD_POSITION;

	public static enum FRAGMENT_POSITION {
		FRESH, PENDING, BULK, SETTINGS, ABOUT
	};

	private static String TAG = "MainActivityNew";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate MainActivity");

		SharedPreferences preferences = this.getSharedPreferences(
				Util.PREFERENCES, Context.MODE_PRIVATE);
		editor = preferences.edit();

		setContentView(R.layout.activity_main_new);
		visit = new Visit();

		String childItem = getIntent().getStringExtra(Util.CHILD_ITEM);

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuTitlesChild = getResources().getStringArray(
				R.array.nav_drawer_child_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
		// .getResourceId(2, -1)));
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
		// .getResourceId(3, -1)));

		navMenuIcons.recycle();

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// mDrawerList.setAdapter(new
		// NavDrawerListAdapter(getApplicationContext(), navDrawerItems));
		adapter = new CVSExpandableListAdapter(this, navDrawerItems,
				getChildMap());
		mDrawerList.setAdapter(adapter);
		// mDrawerList.setGroupIndicator(getResources().getDrawable(R.drawable.expand_collapse));
		mDrawerList.setGroupIndicator(null);
		// setGroupIndicatorToRight();
		mDrawerList.setOnGroupClickListener(this);
		mDrawerList.setOnChildClickListener(this);
		mDrawerList.setOnGroupExpandListener(this);
		// mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.app_name, /* "open drawer" description for accessibility */
		R.string.app_name /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()

			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			if (childItem != null && !childItem.equals("")) {
				if (childItem.equals(Util.FRESH)) {
					selectItem(FRAGMENT_POSITION.FRESH);
					mDrawerList.setItemChecked(1, true);

				} else if (childItem.equals(Util.PENDING)) {
					selectItem(FRAGMENT_POSITION.PENDING);
					mDrawerList.setItemChecked(2, true);

				} else {
					selectItem(FRAGMENT_POSITION.BULK);
					mDrawerList.setItemChecked(3, true);

				}
				DataLoader.CLICKED_ITEM_POSITION = DataLoader.INVALID_ITEM_POSITION;
				DataLoader.IS_TASK_COMPLETED = false;
			} else {
				selectItem(FRAGMENT_POSITION.BULK);
			}

			mDrawerList.expandGroup(0);
			mDrawerList.setItemChecked(1, true);
		}

		// calling gps settings
		/*
		 * GPSTracker gps = new GPSTracker(this); if(!gps.canGetLocation()){
		 * gps.showSettingsAlert(); }
		 */

	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.i(TAG, "onResume");
	}

	public void notifyAdapterDataSetChanged() {
		adapter.notifyDataSetChanged();
	}

	private LinkedHashMap<String, Integer> getChildMap() {
		LinkedHashMap<String, Integer> childMap = new LinkedHashMap<String, Integer>();
		childMap.put(navMenuTitlesChild[0], DataLoader.freshTaskList.size());

		childMap.put(navMenuTitlesChild[1], DataLoader.wlTaskList.size());
		childMap.put(navMenuTitlesChild[2], DataLoader.bulkTaskList.size());

		return childMap;
	}

	/*
	 * @Override public void onBackPressed() { super.onBackPressed(); Log.i("",
	 * "onBackPressed : "); }
	 */

	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		mDrawerList.setIndicatorBounds(getDipsFromPixel(35),
				getDipsFromPixel(40));
	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.main, menu);
	 * 
	 * // Associate searchable configuration with the SearchView SearchManager
	 * searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	 * SearchView searchView = (SearchView)
	 * menu.findItem(R.id.action_search).getActionView();
	 * searchView.setSearchableInfo(
	 * searchManager.getSearchableInfo(getComponentName()));
	 * 
	 * 
	 * return true; }
	 */

	/* Called whenever we call invalidateOptionsMenu() */
	/*
	 * @Override public boolean onPrepareOptionsMenu(Menu menu) { // If the nav
	 * drawer is open, hide action items related to the content view boolean
	 * drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	 * menu.findItem(R.id.action_search).setVisible(!drawerOpen); return
	 * super.onPrepareOptionsMenu(menu); }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_search:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	/*
	 * private class DrawerItemClickListener implements
	 * ListView.OnItemClickListener {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { selectItem(position); } }
	 */

	private void selectItem(FRAGMENT_POSITION position) {
		// update the main content by replacing fragments
		Log.i("MainActivityNew", "selectItem  position : " + position);
		Fragment fragment = null;
		switch (position) {
		case FRESH:

			fragment = new FreshTaskListFragment();
			setTitle(getResources().getStringArray(
					R.array.nav_drawer_child_items)[0]);

			proceed(fragment);
			break;
		case PENDING:

			fragment = new WLTaskListFragment();
			setTitle(getResources().getStringArray(
					R.array.nav_drawer_child_items)[1]);

			proceed(fragment);
			break;

		case BULK:

			fragment = new BulkTaskListFragment();
			setTitle(getResources().getStringArray(
					R.array.nav_drawer_child_items)[2]);

			proceed(fragment);
			break;
		case SETTINGS:
			fragment = new SettingsFragment();
			setTitle(navMenuTitles[1]);
			proceed(fragment);
			break;
		case ABOUT:
			fragment = new AboutFragment();
			setTitle(navMenuTitles[2]);
			proceed(fragment);
			break;

		default:
			break;
		}
	}

	private void proceed(Fragment fragment) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		// mDrawerList.setItemChecked(position, true);

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Log.i("onChildClick", "groupPosition : " + groupPosition + " :: "
				+ "childPosition : " + childPosition);
		if (groupPosition == 0) {
			MainActivityNew.SELECTED_CHILD_POSITION = childPosition;
			if (childPosition == 0) {
				Log.i("", "show fresh task list.......@@@@@");
				selectItem(FRAGMENT_POSITION.FRESH);

			} else if (childPosition == 1) {
				Log.i("", "show pending task list.......@@@@@");
				selectItem(FRAGMENT_POSITION.PENDING);

			} else if (childPosition == 2) {
				Log.i("", "show pending task list......" + ".@@@@@");

				selectItem(FRAGMENT_POSITION.BULK);

			}
			setCheckedState(parent, groupPosition, childPosition, false);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		Log.i("onGroupClick", "groupPosition : " + groupPosition);
		// if (groupPosition == 1) {
		// Log.i("", "show settings fragment.......@@@@@");
		// selectItem(FRAGMENT_POSITION.SETTINGS);
		// setCheckedState(parent, groupPosition, 0, true);
		// return true;
		// } else if (groupPosition == 2) {
		// Log.i("", "show About fragment.......@@@@@");
		// selectItem(FRAGMENT_POSITION.ABOUT);
		// setCheckedState(parent, groupPosition, 0, true);
		// return true;
		// } else if (groupPosition == 3) { //Depending on the position of menu
		// items
		if (groupPosition == 1) {
			Log.i("", "Logout.......@@@@@");
			setCheckedState(parent, groupPosition, 0, true);
			/*
			 * editor.clear(); editor.commit();
			 */
			// performLogout();

			if (Util.isConnectingToInternet(getApplicationContext())) {
				new SyncVisitData()
						.execute(new String[] { Util.OUTPUT_UPLOAD_DATA_URL });
				new SyncVisitImages()
						.execute(new String[] { Util.OUTPUT_UPLOAD_DATA_URL });

			} else {
				Util.showToast(getString(R.string.internetUnavailable),
						getApplicationContext(), true);
			}

			return true;
		} else {
			return false;
		}

	}

	private void setCheckedState(ExpandableListView expandableListView,
			int groupPosition, int childPosition, boolean isGroup) {
		if (isGroup) {
			int index = expandableListView
					.getFlatListPosition(ExpandableListView
							.getPackedPositionForGroup(groupPosition));
			expandableListView.setItemChecked(index, true);
		} else {
			int index = expandableListView
					.getFlatListPosition(ExpandableListView
							.getPackedPositionForChild(groupPosition,
									childPosition));
			expandableListView.setItemChecked(index, true);
		}
	}

	@Override
	public void onGroupExpand(int groupPosition) {
		setCheckedState(mDrawerList, groupPosition,
				MainActivityNew.SELECTED_CHILD_POSITION, false);
	}

	private class SyncVisitData extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		DBUtil dbUtil = new DBUtil(getApplicationContext());

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MainActivityNew.this);
			progressDialog.setCancelable(isRestricted());
			progressDialog.setMessage("Performing Sync...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			progressDialog.dismiss();

			if (result.equals(Util.NO_RECORDS_FOUND)) {
				Log.i("AlarmReceiver", "No Records to sync!!!");
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				editor.clear();
				editor.commit();

				startActivity(intent);
				finish();

			} else if (!result.equals(Util.ERROR_STRING)) {
				Util.showToast("Data Sync Successfull",
						getApplicationContext(), true);

				editor.clear();
				editor.commit();

				dbUtil.deleteData(TABLE_TYPE.OUTPUT_TABLE);
				dbUtil.deleteData(TABLE_TYPE.INPUT_TABLE);

				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
				finish();
				// onBackPressed();
			} else if (result.equals(Util.ERROR_STRING))
				Util.showToast("Data Sync Error", getApplicationContext(), true);
		}

		@Override
		protected String doInBackground(String... params) {
			URL url = null;
			try {
				url = new URL(params[0]);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				return Util.ERROR_STRING;
			}

			String visitJson = dbUtil.fetchOutputData();
			visitJson = encodeData(visitJson);
			Boolean output;
			if (visitJson.equals(Util.NO_RECORDS_FOUND)) {
				return Util.NO_RECORDS_FOUND;
			} else if (!visitJson.equals(Util.ERROR_STRING)) {
				// output =
				// Util.synchroniseData(visitJson,getApplicationContext());
				output = Util.synchroniseData(getApplicationContext());
				if (output) {
					dbUtil.deleteData(TABLE_TYPE.OUTPUT_TABLE);
					return Util.SUCCESS;
				} else {
					return Util.ERROR_STRING;
				}
			} else {
				return Util.ERROR_STRING;
			}
		}
	}

	private class SyncVisitImages extends AsyncTask<String, Void, String> {
		DBUtil dbUtil = new DBUtil(getApplicationContext());

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "Inside PreExecute Method of SyncVisitImages");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(TAG, "Inside PostExecute Method of SyncVisitImages");
		}

		@Override
		protected String doInBackground(String... params) {
			Log.i(TAG, "Inside BAckground Method of SyncVisitImages");
			boolean result = Util.synchroniseImage(getFilesDir(),
					getApplicationContext());
			return "" + result;
		}

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

}