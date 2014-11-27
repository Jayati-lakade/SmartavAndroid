package woxi.cvs.adapter;

import woxi.cvs.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TaskListAdapter extends CursorAdapter{
	
	public TaskListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		
		TextView  taskNo = (TextView) view.findViewById(R.id.tvSrNo);
		TextView customerName = (TextView) view.findViewById(R.id.tvCustomerName);
		TextView customerAddress = (TextView) view.findViewById(R.id.tvCustomerAddress);
		
		TextView customerCafNo=(TextView) view.findViewById(R.id.tvCafType);
		TextView status = (TextView) view.findViewById(R.id.tvStatus);
	//	ImageView priority = (ImageView) view.findViewById(R.id.imgPriority);
		
		taskNo.setText(cursor.getString(1));
		customerName.setText(cursor.getString(2));
		customerAddress.setText(cursor.getString(3));
		
		customerCafNo.setText(cursor.getString(4));
		status.setText(cursor.getString(5));

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return inflater.inflate(R.layout.task_list_item, parent, false);
	}

}
