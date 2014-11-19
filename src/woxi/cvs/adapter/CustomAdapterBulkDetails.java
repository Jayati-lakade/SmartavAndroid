package woxi.cvs.adapter;

import java.util.ArrayList;

import woxi.cvs.R;
import woxi.cvs.bulk.BulkCustomerViewDetailActivity;
import woxi.cvs.model.BulkCustomer;
import woxi.cvs.model.BulkTask;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapterBulkDetails extends BaseAdapter  {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList data;
	private static LayoutInflater inflaterNew = null;
	public Resources res;
	BulkCustomer empValues = null;
	public BulkCustomerViewDetailActivity CustomListView;
	private BulkTask bulkmodel;

	Context context;

	/************* CustomAdapter Constructor *****************/
	public CustomAdapterBulkDetails(Activity a, ArrayList d, Resources resLocal) {

		/********** Take passed values **********/
		activity = a;
		data = d;
		res = resLocal;

		/*********** Layout inflator to call external xml layout () **********************/
		inflaterNew = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder to contain inflated xml file elements ***********/
	public static class ViewHolder {
		public TextView accountNo;
		public TextView mobileNo;
		public TextView fullName;
		public TextView designation;
		public Button btnnext;

	}

	/*********** Depends upon data size called for each row , Create each ListView row ***********/
	public View getView(final int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {

			/********** Inflate tabitemtext.xml file for each row ( Defined below ) ************/
			vi = inflaterNew.inflate(R.layout.tabitemtext, null);

			/******** View Holder Object to contain tabitem.xml file elements ************/
			holder = new ViewHolder();
			holder.accountNo = (TextView) vi.findViewById(R.id.accountNoText);
			holder.mobileNo = (TextView) vi.findViewById(R.id.mobileNoText);
			holder.fullName = (TextView) vi.findViewById(R.id.fullNameText);
			holder.designation = (TextView) vi.findViewById(R.id.designationText);
			holder.btnnext=(Button) vi.findViewById(R.id.btnnext);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		/*
		 * if(data.size() <= 0) { holder.accountNo.setText("No Data");
		 * 
		 * }
		 */
		if (data.size() > 0) {
			/***** Get each Model object from Arraylist ********/
			empValues = null;
			empValues = (BulkCustomer) data.get(position);

			/************ Set Model values in Holder elements ***********/
			holder.accountNo.setText(empValues.getAccountNo());
			holder.mobileNo.setText(empValues.getMobileNo());
			holder.fullName.setText(empValues.getFullName());
			holder.designation.setText(empValues.getDesignation());

			/******** Set Item Click Listner for LayoutInflater for each row ***********/
			vi.setOnClickListener(new OnItemClickListener(position));


		}
		return vi;
	}

	/********* Called when Item click in ListView ************/
	private class OnItemClickListener implements OnClickListener {
		private int mPosition;

		OnItemClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View arg0) {

		}

	}



}