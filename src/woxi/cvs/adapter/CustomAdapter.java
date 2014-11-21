package woxi.cvs.adapter;

import java.util.ArrayList;

import woxi.cvs.R;
import woxi.cvs.bulk.BulkCustomerAddDetailActivity;
import woxi.cvs.model.BulkCustomer;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter {// implements OnClickListener {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList data;
	private static LayoutInflater inflater = null;
	public Resources res;
	BulkCustomer empValues = null;
	public BulkCustomerAddDetailActivity CustomListView;
	public ArrayList<BulkCustomer> customListViewValuesArr = new ArrayList<BulkCustomer>();
	Context context;

	/************* CustomAdapter Constructor *****************/
	public CustomAdapter(Activity a, ArrayList d, Resources resLocal) {

		/********** Take passed values **********/
		activity = a;
		data = d;
		res = resLocal;

		/*********** Layout inflator to call external xml layout () **********************/
		inflater = (LayoutInflater) activity
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
		public EditText accountNo;
		public EditText mobileNo;
		public EditText fullName;
		public EditText designation;
		public Button btnsave;
		public Button btnDelete;

	}

	/*********** Depends upon data size called for each row , Create each ListView row ***********/
	public View getView(final int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {

			/********** Inflate tabitem.xml file for each row ( Defined below ) ************/
			vi = inflater.inflate(R.layout.tabitem, null);

			/******** View Holder Object to contain tabitem.xml file elements ************/
			holder = new ViewHolder();
			holder.accountNo = (EditText) vi.findViewById(R.id.accountNo);
			holder.mobileNo = (EditText) vi.findViewById(R.id.mobileNo);
			holder.fullName = (EditText) vi.findViewById(R.id.fullName);
			holder.designation = (EditText) vi.findViewById(R.id.designation);
			holder.btnsave = (Button) vi.findViewById(R.id.btnSave);
			holder.btnDelete = (Button) vi.findViewById(R.id.btnDelete);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.accountNo.setText("No Data");

		} else {
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
//
//			/*
//			 * holder.btnsave.setOnClickListener(new View.OnClickListener() {
//			 * 
//			 * @Override public void onClick(View v) { switch (v.getId()) { case
//			 * R.id.btnsave:
//			 * 
//			 * BulkCustomer bulkcustomer=new BulkCustomer();
//			 * bulkcustomer.setAccountNo(holder.accountNo.getText().toString());
//			 * bulkcustomer.setMobileNo(holder.mobileNo.getText().toString());
//			 * bulkcustomer.setFullName(holder.fullName.getText().toString());
//			 * bulkcustomer
//			 * .setDesignation(holder.designation.getText().toString());
//			 * 
//			 * //////////////////////////////////////////////////////////////////
//			 * ///
//			 * 
//			 * 
//			 * /******** Take Model Object in ArrayList **********
//			 * customListViewValuesArr.add(bulkcustomer);
//			 * 
//			 * 
//			 * 
//			 * // list = (ListView) findViewById(R.id.list); /****************
//			 * Create Custom Adapter ********* // list.setAdapter(adapter);
//			 * DBUtil
//			 * .updateBulkTable(bulkcustomer.getBulk_id(),customListViewValuesArr
//			 * );
//			 * 
//			 * 
//			 * break;
//			 * 
//			 * default: break; }
//			 * 
//			 * }
//			 * 
//			 * });
//			 */

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
		public void onClick(View view) {

			switch (view.getId()) {
			case R.id.btnSave:

				BulkCustomerAddDetailActivity bulkCustomerAddDetailActivity = (BulkCustomerAddDetailActivity) activity;
				bulkCustomerAddDetailActivity.onItemClick(mPosition,1);
				System.out.println("BtnSave Clicked");
				break;

			case R.id.btnDelete:
				BulkCustomerAddDetailActivity bulkCustomerAddDetailActivity1 = (BulkCustomerAddDetailActivity) activity;
				bulkCustomerAddDetailActivity1.onItemClick(mPosition,2);

			}
		}

	}
}