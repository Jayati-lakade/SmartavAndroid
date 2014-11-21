package woxi.cvs.activities;

import woxi.cvs.R;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.model.BulkTask;
import woxi.cvs.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetailActivityBulk extends Activity implements OnClickListener {


	BulkTask bulkTask;
	Object obj;
	TextView customerName, cafNumber, address, landmark, telephoneNumber,
		altNumber, statusAv, priority, billPlan, serviceType, dealerName,cafType,dealerCode,productType,csm,noOfLines; 
	
	Button btnAttend,btnHistory,btnCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("SmartAV - "+ConstantSmartAV.CURRENTCUSTOMER);
		getActionBar().setLogo(null);
		
		setContentView(R.layout.activity_taskdetail_bulk);
		

		customerName = (TextView) this.findViewById(R.id.customerName); 
		/*
		cafNumber = (TextView) this.findViewById(R.id.cafNumber); 
		address = (TextView) this.findViewById(R.id.address);
		landmark = (TextView) this.findViewById(R.id.landmark); 
		telephoneNumber = (TextView) this.findViewById(R.id.telephoneNumber);
		altNumber = (TextView) this.findViewById(R.id.altNumber); 
		statusAv = (TextView) this.findViewById(R.id.statusAv); 
		
		priority = (TextView) this.findViewById(R.id.priority); 
		billPlan = (TextView) this.findViewById(R.id.billPlan); 
		serviceType = (TextView) this.findViewById(R.id.serviceType); 
		dealerName = (TextView) this.findViewById(R.id.dealerName); 
		cafType = (TextView) this.findViewById(R.id.cafType);
		dealerCode = (TextView) this.findViewById(R.id.dealerCode);
		productType = (TextView) this.findViewById(R.id.productType);
		csm = (TextView) this.findViewById(R.id.csm);	
		*/

		noOfLines = (TextView) this.findViewById(R.id.noOfLines);
		btnAttend = (Button) this.findViewById(R.id.btnAttend);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnHistory = (Button) this.findViewById(R.id.btnHistory);
		
		//btnHistory.setVisibility(View.INVISIBLE);
		obj = getIntent().getExtras().get("task");

		if(obj instanceof BulkTask){
			bulkTask = (BulkTask) obj;
			populateBulkTaskFields(bulkTask);
		}		
		
		btnAttend.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnHistory.setOnClickListener(this);
	}
	

	private void populateBulkTaskFields(BulkTask task) {

		customerName.setText(task.getCust_name()); 
		/*
		cafNumber.setText(task.getCaf_no()); 
		address.setText(task.getAddress()); 
		address.setSelected(true);
		 address.setMovementMethod(new ScrollingMovementMethod());
		landmark.setText(task.getLandmark()); 
		telephoneNumber.setText(task.getTelephone_no());
		altNumber.setText(task.getAlternate_contact_no()); 
		statusAv.setText(task.getStatus_av()); 
		priority.setText(task.getPriority()); 
		productType.setText(task.getProduct_type());
		*/

	}
	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnAttend:
			Intent intent = new Intent(TaskDetailActivityBulk.this, DecisionActivityBulk.class);
			intent.putExtra("task", bulkTask);
			startActivity(intent);

			break;
		
		case R.id.btnHistory:
			Intent intent2 = new Intent(TaskDetailActivityBulk.this, VisitHistoryActivityBulk.class);
			intent2.putExtra("task", bulkTask);
			startActivity(intent2);
//			startActivity(new Intent(TaskDetailActivity.this, VisitHistoryActivity.class));

			break;
	
		case R.id.btnCancel:
			finish();

		default:
			break;
		}

	}
}
