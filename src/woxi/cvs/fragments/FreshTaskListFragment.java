package woxi.cvs.fragments;

import java.util.ArrayList;

import woxi.cvs.R;
import woxi.cvs.activities.TaskDetailActivity;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.model.DataLoader;
import woxi.cvs.model.FreshTask;
import woxi.cvs.util.Util;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class FreshTaskListFragment extends Fragment {

	private ListView taskList;
	private ArrayList<FreshTask> originalList;
	private ArrayList<FreshTask> tasksList;
	private ListAdapter adapter;
//	private MenuItem refreshMenuItem;
	private static String TAG = "FreshTaskListFragment";
	
	public FreshTaskListFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		adapter = new ListAdapter(DataLoader.freshTaskList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = null;
		
			rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
			taskList = (ListView) rootView.findViewById(R.id.taskList);
			taskList.setAdapter(adapter);
			taskList.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
					Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
					intent.putExtra("task", tasksList.get(pos));
					ConstantSmartAV.CURRENTCUSTOMER = tasksList.get(pos).getCust_name();
					startActivity(intent);
					
					DataLoader.CLICKED_ITEM_POSITION = pos;
				}
			});
	
			taskList.setTextFilterEnabled(true);
		//} else {
		//	rootView = inflater.inflate(R.layout.no_records, container, false);
		//}

		return rootView;
	}
	
	/*@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
		if(DataLoader.IS_TASK_COMPLETED && DataLoader.CLICKED_ITEM_POSITION != DataLoader.INVALID_ITEM_POSITION){
			DataLoader.freshTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
			adapter.notifyDataSetChanged();
//			MainActivityNew mainActivityNew = (MainActivityNew) getActivity();
			((MainActivityNew) getActivity()).notifyAdapterDataSetChanged();
			
			DataLoader.IS_TASK_COMPLETED = false;
			DataLoader.CLICKED_ITEM_POSITION = DataLoader.INVALID_ITEM_POSITION;
		}
	}*/

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getActivity()
				.getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getActivity().getComponentName()));

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				adapter.getFilter().filter(newText);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				adapter.getFilter().filter(query);
				return true;
			}
		};

		searchView.setOnQueryTextListener(queryTextListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;

		/*case R.id.action_refresh:
			// refresh
			refreshMenuItem = item;
			// load the data from server
			new SyncData().execute();
			return true;*/

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class ListAdapter extends BaseAdapter implements Filterable {

		public ListAdapter(ArrayList<FreshTask> list) {
			originalList = list;
			tasksList = list;
		}

		@Override
		public int getCount() {
			return tasksList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		private class ViewHolder {
			TextView taskNo;
			TextView customerName;
			TextView customerAddress;
			TextView customerCafType;
			TextView status;
			ImageView priority;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parentView) {

			ViewHolder holder = null;
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.task_list_item, null);
				holder = new ViewHolder();
				holder.taskNo = (TextView) convertView
						.findViewById(R.id.tvSrNo);
				holder.customerName = (TextView) convertView
						.findViewById(R.id.tvCustomerName);
				holder.customerAddress = (TextView) convertView
						.findViewById(R.id.tvCustomerAddress);
			//	holder.customerContactNo = (TextView) convertView.findViewById(R.id.tvCustomerContactNo);
				holder.customerCafType=(TextView) convertView.findViewById(R.id.tvCafType);
				holder.status = (TextView) convertView
						.findViewById(R.id.tvStatus);
				holder.priority = (ImageView) convertView
						.findViewById(R.id.imgPriority);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			FreshTask task = tasksList.get(pos);
			holder.taskNo.setText("" + task.getCaf_no());
			holder.customerName.setText(task.getCust_name());
			holder.customerAddress.setText(task.getAddress());
			holder.customerCafType.setText("     " +task.getCaf_type());
			//holder.customerContactNo.setText("" + task.getTelephone_no());
			
			holder.status.setText(task.getStatus_av());
			
			/*if (task.getPriority() == 0) {
				holder.priority.setBackgroundColor(getResources().getColor(R.color.red));
			} else if (task.getPriority() == 1) {
				holder.priority.setBackgroundColor(getResources().getColor(R.color.green));
			} else if (task.getPriority() == 2) {
				holder.priority.setBackgroundColor(getResources().getColor(R.color.orange));
			}*/
			
			if (task.getPriority().equalsIgnoreCase("critical")) {
				holder.priority.setBackgroundResource(R.drawable.red);
			} else if (task.getPriority().equalsIgnoreCase("high")) {
				holder.priority.setBackgroundResource(R.drawable.yellow);
			} else if (task.getPriority().equalsIgnoreCase("deadline missed")) {
				holder.priority.setBackgroundResource(R.drawable.blue);
			}else if (task.getPriority().equalsIgnoreCase("low")) {
				holder.priority.setBackgroundResource(R.drawable.green);
			}			
			return convertView;
		}

		@Override
		public android.widget.Filter getFilter() {
			return new android.widget.Filter() {
				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					tasksList = (ArrayList<FreshTask>) results.values;
					notifyDataSetChanged();

				}

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					constraint = constraint.toString().toLowerCase();
					FilterResults result = new FilterResults();
					if (constraint != null
							&& constraint.toString().length() > 0) {
						ArrayList<FreshTask> filteredItems = new ArrayList<FreshTask>();
						for (int i = 0, l = originalList.size(); i < l; i++) {
							FreshTask task = originalList.get(i);
							if (task.toString().toLowerCase().contains("" + constraint)) {
								filteredItems.add(task);
							}
						}
						result.count = filteredItems.size();
						result.values = filteredItems;
					} else {
						result.values = originalList;
						result.count = originalList.size();
					}
					return result;
				}
			};
		}

	}
	
	/*private class SyncData extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// set the progress bar view
			refreshMenuItem.setActionView(R.layout.action_progressbar);
			refreshMenuItem.expandActionView();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			refreshMenuItem.collapseActionView();
			refreshMenuItem.setActionView(null);
			
			originalList.addAll(buildDummyData());
			adapter = new ListAdapter(originalList);
			taskList.setAdapter(adapter);
		}
	}*/

}
