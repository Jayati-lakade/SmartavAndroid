package woxi.cvs.fragments;

import woxi.cvs.R;
import woxi.cvs.activities.MainActivityNew;
import woxi.cvs.activities.TaskDetailActivityBulk;
import woxi.cvs.adapter.TaskListAdapterBulk;
import woxi.cvs.model.Visit;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class TaskDetailFragmentBulk extends Fragment implements
		OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {

	private ListView taskList;
	private MenuItem refreshMenuItem;
	private TaskListAdapterBulk mAdapter;
	private String mCurFilter;
	private MainActivityNew mainactivitynew;
	private Visit visit;
	
	public TaskDetailFragmentBulk() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);
		visit = new Visit();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//visit = new Visit();

		 View rootView = inflater.inflate(R.layout.bulkdataview_list_view, container, false);
		taskList = (ListView) rootView.findViewById(R.id.bulklistview);

		mAdapter = new TaskListAdapterBulk(getActivity(), null, true);
		System.out.println("current activity=" + getActivity());
		taskList.setAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
	

		taskList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		
				Intent intent = new Intent(getActivity(), TaskDetailActivityBulk.class);
				
				startActivity(intent);
				
			}
		});

		taskList.setTextFilterEnabled(true);
		

		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

		searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);

	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mCurFilter = (newText != null) ? newText : null;
        getLoaderManager().restartLoader(0, null, this);
        return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
