package woxi.cvs.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import woxi.cvs.R;
import woxi.cvs.model.NavDrawerItem;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CVSExpandableListAdapter extends BaseExpandableListAdapter{

	private Activity context;
	private ArrayList<String> childItems;
	private HashMap<String, Integer> childMap;
	private ArrayList<NavDrawerItem> groupItems;

	public CVSExpandableListAdapter(Activity context, ArrayList<NavDrawerItem> groupItems,
			HashMap<String, Integer> childMap) {
		this.context = context;
		this.childMap = childMap;
		this.groupItems = groupItems;
		
		this.childItems = new ArrayList<String>(childMap.keySet());
		
	}

	public Object getChild(int groupPosition, int childPosition) {
		if(groupPosition == 0){
			return childItems.get(childPosition);
		}else{
			return "";
		}
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = infalInflater.inflate(R.layout.drawer_child_list_item, null);
		}

		TextView item = (TextView) convertView.findViewById(R.id.titleChild);
		item.setText(childItems.get(childPosition));
		
		TextView itemCount = (TextView) convertView.findViewById(R.id.counter);
		itemCount.setText(childMap.get(childItems.get(childPosition)) + "");
		
		
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		if(groupPosition == 0){
			return childMap.size();
		}else{
			return 0;
		}
	}

	public Object getGroup(int groupPosition) {
		return groupItems.get(groupPosition);
	}

	public int getGroupCount() {
		return groupItems.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.drawer_list_item,
					null);
		}
		
		if(groupPosition == 0){
		
			
			convertView.setBackgroundResource(android.R.color.transparent);
			
			ImageView imgIconRight = (ImageView) convertView.findViewById(R.id.iconRight);
			imgIconRight.setBackgroundResource(isExpanded ? R.drawable.ic_navigation_collapse : R.drawable.ic_navigation_expand);
		} 

			ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
			TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
			
//			  TextView txtCount = (TextView)
//			  convertView.findViewById(R.id.counter);
			 

			imgIcon.setImageResource(groupItems.get(groupPosition).getIcon());
			txtTitle.setText(groupItems.get(groupPosition).getTitle());
		
        
        
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/*@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		Log.i("onChildClick", "groupPosition : " + groupPosition + " :: " + "childPosition : " + childPosition );
		if(groupPosition == 0){
			if(childPosition == 0){
				Log.i("", "show fresh task list.......@@@@@");	
			}else if(childPosition == 1){
				Log.i("", "show pending task list.......@@@@@");	
			}
			
			int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
		    parent.setItemChecked(index, true);
			return true;	
		}else{
			return false;
		}
		
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		Log.i("onGroupClick", "groupPosition : " + groupPosition  );
		if(groupPosition == 1){
			Log.i("", "show settings fragment.......@@@@@");
			return true;
		}else if(groupPosition == 2){
			Log.i("", "show About fragment.......@@@@@");
			return true;
		}else if(groupPosition == 3){
			Log.i("", "Logout.......@@@@@");
			return true;
		}else{
			return false;
		}
		
	}*/
	
	
	
	
	
}
