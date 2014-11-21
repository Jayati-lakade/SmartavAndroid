package woxi.cvs.bulk;

import java.util.ArrayList;



import woxi.cvs.R;
import woxi.cvs.activities.CaptureVisitActivityBulk;
import woxi.cvs.adapter.CustomAdapter;
import woxi.cvs.adapter.CustomAdapterBulkDetails;
import woxi.cvs.db.DBUtil;
import woxi.cvs.model.BulkCustomer;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.Visit;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BulkCustomerAddDetailActivity extends Activity implements OnClickListener {

	ListView list,list_TextView;
	CustomAdapter adapter;
	CustomAdapterBulkDetails TextAdapter;
	public BulkCustomerAddDetailActivity CustomListView = null;
	public ArrayList<BulkCustomer> customListViewValuesArr = new ArrayList<BulkCustomer>();
	public static int count = 0;
	private Button btnAdd;
	private Button btnReset,btnpreview,btncapturevisit;
	
	private EditText accountNo, mobileNo, fullName, designation;
	private BulkTask bulkTask;
	private DBUtil dbUtil ;
	private Visit visit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbUtil = new DBUtil(getApplicationContext());
		setContentView(R.layout.activity_custom_list_view_bulk);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnReset = (Button) findViewById(R.id.btnReset);
		btnpreview=(Button) findViewById(R.id.btnpreview);
		btncapturevisit=(Button) findViewById(R.id.btnCaptureVisit);		
		accountNo = (EditText) findViewById(R.id.accountNo);
		mobileNo = (EditText) findViewById(R.id.mobileNo);
		fullName = (EditText) findViewById(R.id.fullName);
		designation = (EditText) findViewById(R.id.designation);
		
		CustomListView = this;
		
		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		Resources res = getResources();
		list = (ListView) findViewById(R.id.list);
		bulkTask = (BulkTask)getIntent().getExtras().get("task");
		setListData();
		/**************** Create Custom Adapter *********/
		adapter = new CustomAdapter(CustomListView, customListViewValuesArr,res);
		list.setAdapter(adapter);
		btnAdd.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		btnpreview.setOnClickListener(this);
		btncapturevisit.setOnClickListener(this);
	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {
		customListViewValuesArr = dbUtil.searchBulKTask(new Integer(bulkTask.getBulk_id()));
		if(customListViewValuesArr!=null){
			list.setAdapter(adapter);
		}else{
			customListViewValuesArr = new ArrayList<BulkCustomer>();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnAdd:
		
			BulkCustomer bulkCustomer = new BulkCustomer();

			/******* Firstly take data in model object ******/
			bulkCustomer.setAccountNo(accountNo.getText().toString());
			bulkCustomer.setMobileNo(mobileNo.getText().toString());
			bulkCustomer.setFullName(fullName.getText().toString());
			bulkCustomer.setDesignation(designation.getText().toString());


			/******** Take Model Object in ArrayList **********/
			customListViewValuesArr.add(bulkCustomer);
			
			
			
			list = (ListView) findViewById(R.id.list);
			/**************** Create Custom Adapter *********/
			list.setAdapter(adapter);
			int count = dbUtil.searchBulKTaskCustomers(bulkTask.getBulk_id());
			if(count>0){
				DBUtil.updateBulkTable(bulkTask.getBulk_id(),customListViewValuesArr);
			}else{
				DBUtil.insertIntoBulkTable(bulkTask.getBulk_id(), customListViewValuesArr);
			}			
			resetViewFields();
		
			break;

		case R.id.btnReset:
			resetViewFields();
			break;
			
		case R.id.btnpreview:
	
			Intent intent = new Intent(BulkCustomerAddDetailActivity.this, BulkCustomerViewDetailActivity.class);
			
			intent.putExtra("task", bulkTask);
			startActivity(intent);
			break;
			
		case R.id.btnCaptureVisit:
			Intent intentCapture = new Intent(BulkCustomerAddDetailActivity.this, CaptureVisitActivityBulk.class);				
			intentCapture.putExtra("task", bulkTask);
			intentCapture.putExtra("visit",visit);
			startActivity(intentCapture);
			break;
	
		default:
			break;
		}
	}
	
	public void SaveUpdate(View v)
	{

		Toast.makeText(getApplicationContext(), "Clicked...", Toast.LENGTH_LONG).show();
	}
	public void resetViewFields(){
		accountNo.setText("");
		mobileNo.setText("");
		fullName.setText("");
		designation.setText("");
	}

 /*
   1: To save and update the record.	
   2: To delete the record.
 */
	public void onItemClick(final int mPosition,int task) {
		
		switch(task){
		case 1 : 
		
		BulkCustomer bulkCustomer = (BulkCustomer) customListViewValuesArr
				.get(mPosition);

		Toast.makeText(CustomListView, bulkCustomer.toString(),
				Toast.LENGTH_LONG).show();

		list = (ListView) findViewById(R.id.list);

		//
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				BulkCustomerAddDetailActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Confirm Delete...");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want delete this?");

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.delete);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						BulkCustomer bulkCustomer = (BulkCustomer) customListViewValuesArr
								.get(mPosition);

				//		Toast.makeText(CustomListView, bulkCustomer.toString(),Toast.LENGTH_LONG).show();

						list = (ListView) findViewById(R.id.list);
						customListViewValuesArr.remove(mPosition);
						DBUtil.updateBulkTable(bulkTask.getBulk_id(),customListViewValuesArr);
						/**************** Create Custom Adapter *********/
						list.setAdapter(adapter);
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						Toast.makeText(getApplicationContext(),
								"You clicked on NO", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
		//

		// CustomListViewValuesArr.remove(mPosition);
		/**************** Create Custom Adapter *********/
		// list.setAdapter(adapter);
		break;
		
		case 2 : 
			Toast.makeText(getApplicationContext(), "Clicked...", Toast.LENGTH_LONG).show();
			break;
	}
	}
}
