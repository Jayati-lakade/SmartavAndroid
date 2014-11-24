package woxi.cvs.bulk;

import java.util.ArrayList;



import java.util.LinkedHashSet;

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

	ListView bulkCustomerListView;
	CustomAdapter adapter;
	CustomAdapterBulkDetails TextAdapter;
	public BulkCustomerAddDetailActivity customListView = null;
	public LinkedHashSet<BulkCustomer> customListViewValuesSet = new LinkedHashSet<BulkCustomer>();
	public ArrayList<BulkCustomer> customListViewValuesList = new ArrayList<BulkCustomer>();
	public static int count = 0;
	private Button btnAdd;
	private Button btnReset,btnpreview,btncapturevisit;
	
	private EditText accountNo, mobileNo, fullName, designation;
	private BulkTask bulkTask;
	private DBUtil dbUtil ;
	private Visit visit;
	
	private static final int UPDATE = 1;
	private static final int DELETE = 2;
	private Resources res;

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
		
		customListView = this;
		
		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		res = getResources();
		bulkCustomerListView = (ListView) findViewById(R.id.list);
		bulkTask = (BulkTask)getIntent().getExtras().get("task");
		setListData();
		/**************** Create Custom Adapter *********/
		
		adapter = new CustomAdapter(customListView, getBulkCustomerValuesList(customListViewValuesSet),res);
		bulkCustomerListView.setAdapter(adapter);
		btnAdd.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		btnpreview.setOnClickListener(this);
		btncapturevisit.setOnClickListener(this);
	}

	private ArrayList getBulkCustomerValuesList(LinkedHashSet<BulkCustomer> customListViewValuesSet) {
		if( customListViewValuesSet!=null){
//			customListViewValuesList.clear();
			System.gc();
			customListViewValuesList =new ArrayList<BulkCustomer>(customListViewValuesSet);
			return customListViewValuesList;
		}
		return customListViewValuesList;
	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {
		customListViewValuesSet = dbUtil.searchBulKTask(new Integer(bulkTask.getBulk_id()));
		customListViewValuesList = getBulkCustomerValuesList(customListViewValuesSet);
		if(customListViewValuesSet!=null){
			bulkCustomerListView.setAdapter(adapter);
		}else{
			customListViewValuesSet = new LinkedHashSet<BulkCustomer>();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnAdd:		
			btnAdd.setText("ADD");   //Resetting to Add label
			BulkCustomer bulkCustomer = new BulkCustomer();
			/******* Firstly take data in model object ******/
			bulkCustomer.setAccountNo(accountNo.getText().toString());
			bulkCustomer.setMobileNo(mobileNo.getText().toString());
			bulkCustomer.setFullName(fullName.getText().toString());
			bulkCustomer.setDesignation(designation.getText().toString());
			/******** Take Model Object in ArrayList **********/
			if(!customListViewValuesSet.add(bulkCustomer)){
				Toast.makeText(customListView, "Customer already exists with same Mobile No. \n Please try again", Toast.LENGTH_LONG).show();
				break;
			}			
			int count = dbUtil.searchBulKTaskCustomers(bulkTask.getBulk_id());
			if(count>0){
				DBUtil.updateBulkTable(bulkTask.getBulk_id(),getBulkCustomerValuesList(customListViewValuesSet));
			}else{
				DBUtil.insertIntoBulkTable(bulkTask.getBulk_id(), getBulkCustomerValuesList(customListViewValuesSet));
			}
			adapter = new CustomAdapter(customListView, getBulkCustomerValuesList(customListViewValuesSet),res);
			bulkCustomerListView.setAdapter(adapter);			
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
	
	
	public void resetViewFields(){
		accountNo.setText("");
		mobileNo.setText("");
		fullName.setText("");
		designation.setText("");
	}

 /* taskOpeartion : 
   1: To save and update the record.	
   2: To delete the record.
 */
	public void onItemClick(final int mPosition,int taskOpeartion) {
		
		BulkCustomer bulkCustomer = (BulkCustomer) customListViewValuesList.get(mPosition);
		
	 
		switch(taskOpeartion){
	
		 case UPDATE : 			
			customListViewValuesSet.remove(bulkCustomer);
			accountNo.setText(bulkCustomer.getAccountNo());
			mobileNo.setText(bulkCustomer.getMobileNo());
			fullName.setText(bulkCustomer.getFullName());
			designation.setText(bulkCustomer.getDesignation());
			btnAdd.setText("Save");//Changing the Add button label to Save
			break;
			
		 case DELETE :
			 Toast.makeText(customListView, bulkCustomer.toString(),Toast.LENGTH_LONG).show();
			 bulkCustomerListView = (ListView) findViewById(R.id.list);		
			 AlertDialog.Builder alertDialog = new AlertDialog.Builder(BulkCustomerAddDetailActivity.this);
			 alertDialog.setTitle("Confirm Delete...");		            // Setting Dialog Title
			 alertDialog.setMessage("Are you sure you want delete this?");	// Setting Dialog Message
		      
		  // Setting Positive "Yes" Button
		      alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						BulkCustomer bulkCustomer = (BulkCustomer) customListViewValuesList.get(mPosition);
						bulkCustomerListView = (ListView) findViewById(R.id.list);
						customListViewValuesSet.remove(bulkCustomer);
						DBUtil.updateBulkTable(bulkTask.getBulk_id(),getBulkCustomerValuesList(customListViewValuesSet));
						adapter = new CustomAdapter(customListView, getBulkCustomerValuesList(customListViewValuesSet),res);
						bulkCustomerListView.setAdapter(adapter);	
					}
				});
		      
		  // Setting Negative "NO" Button
		      alertDialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),"You clicked on NO", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

        		alertDialog.show();
		break;			
	}
  }
}
