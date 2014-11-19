package woxi.cvs.adapter;

import java.util.ArrayList;

import woxi.cvs.R;
import woxi.cvs.model.Visit;
import woxi.cvs.model.VisitForm;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class VisitListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<VisitForm> parents;

	public VisitListAdapter(Context ctx, ArrayList<VisitForm> parents) {
		this.inflater = LayoutInflater.from(ctx);
		this.parents = parents;
	}

	// This Function used to inflate parent rows view

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parentView) {
		VisitForm parent = parents.get(groupPosition);

		// Inflate grouprow.xml file for parent rows
		convertView = inflater
				.inflate(R.layout.parent_visit, parentView, false);

		// Get grouprow.xml file elements and set values
		((TextView) convertView.findViewById(R.id.visitHeader)).setText(parent
				.getVisitNumber());

		return convertView;
	}

	// This Function used to inflate child rows view
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parentView) {
		// Inflate childrow.xml file for child rows
		convertView = inflater.inflate(R.layout.visit_row, parentView, false);

		VisitForm parent = parents.get(groupPosition);
		Visit child = parent.getVisitsList().get(childPosition);

		((TextView) convertView.findViewById(R.id.personMet)).setText(child
				.getPersonMet());

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return parents.get(groupPosition).getVisitsList().get(childPosition);
	}

	// Call when child row clicked
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		int size = 0;
		if (parents.get(groupPosition).getVisitsList() != null)
			size = parents.get(groupPosition).getVisitsList().size();
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		Log.i("Parent", groupPosition + "=  getGroup ");

		return parents.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return parents.size();
	}

	// Call when parent row clicked
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}