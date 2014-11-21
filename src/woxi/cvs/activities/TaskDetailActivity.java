package woxi.cvs.activities;

import woxi.cvs.R;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.WLTask;
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

public class TaskDetailActivity extends Activity implements OnClickListener {

	FreshTask freshTask;
	WLTask wlTask;

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
		
		setContentView(R.layout.activity_taskdetail);
		
//		acctDesc = (TextView) this.findViewById(R.id.acctDesc); 
//		delsAccount = (TextView) this.findViewById(R.id.delsAccount); 
		customerName = (TextView) this.findViewById(R.id.customerName); 
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
		noOfLines = (TextView) this.findViewById(R.id.noOfLines);
		btnAttend = (Button) this.findViewById(R.id.btnAttend);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnHistory = (Button) this.findViewById(R.id.btnHistory);
		//btnHistory.setVisibility(View.INVISIBLE);
		obj = getIntent().getExtras().get("task");
		
		if(obj instanceof FreshTask){
			freshTask = (FreshTask) obj;
			populateFreshTaskFields(freshTask);
		} else if(obj instanceof WLTask){
			wlTask = (WLTask) obj;
			populateWLTaskFields(wlTask);
		}	

		btnAttend.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnHistory.setOnClickListener(this);
	}
	

	private void populateWLTaskFields(WLTask task) {
//		acctDesc.setText(task.getAcct_category_desc()); 
		customerName.setText(task.getCust_name()); 
		cafNumber.setText(task.getCaf_no()); 
		address.setText(task.getAddress()); 
		landmark.setText(task.getLandmark()); 
		telephoneNumber.setText(task.getTelephone_no());
		altNumber.setText(task.getAlternate_contact_no()); 
		statusAv.setText(task.getStatus_av()); 
		priority.setText(task.getPriority()); 
		billPlan.setText(task.getBill_plan()); 
		dealerName.setText(task.getDealer_name());
		cafType.setText(task.getCaf_type());
		dealerCode.setText(task.getDealerCode());
		productType.setText(task.getProduct_type());//Not available for WL TASK
		csm.setText(task.getCsm());
		noOfLines.setText(task.getNo_of_dels_under_account());
	}

	private void populateFreshTaskFields(FreshTask task) {
		customerName.setText(task.getCust_name()); 
		cafNumber.setText(task.getCaf_no()); 
		address.setText(task.getAddress()); 
		landmark.setText(task.getLandmark()); 
		telephoneNumber.setText("" + task.getTelephone_no());
		altNumber.setText("" + task.getAlternate_contact_no()); 
		statusAv.setText(task.getStatus_av()); 
		priority.setText(task.getPriority()); 
		billPlan.setText(task.getBill_plan()); 
		cafType.setText(task.getCaf_type());
		dealerCode.setText(task.getDealerCode());
		productType.setText(task.getProduct_type());
		csm.setText(task.getCsm());
		noOfLines.setText(task.getNo_of_dels_under_account());
		dealerName.setText(task.getDealer_name());
	}
	private void populateBulkTaskFields(BulkTask task) {
		customerName.setText(task.getCust_name()); 
//		cafNumber.setText(task.getCaf_no()); 
		address.setText(task.getAddress()); 
//		landmark.setText(task.getLandmark()); 
		telephoneNumber.setText("" + task.getTelephone_no());
		altNumber.setText("" + task.getAlternate_contact_no()); 
//		statusAv.setText(task.getStatus_av()); 
//		priority.setText(task.getPriority()); 
//		billPlan.setText(task.getPlan()); 
//		cafType.setText(task.getCaf_type());
//		dealerCode.setText(task.getDealerCode());
//		productType.setText(task.getProduct_type());
//		csm.setText(task.getCsm());
//		noOfLines.setText(task.getNo_of_dels_under_account());
//		dealerName.setText(task.getDealer_name());
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
			Intent intent = new Intent(TaskDetailActivity.this, DecisionActivity.class);
			intent.putExtra("task", (freshTask != null) ? freshTask : wlTask);
			startActivity(intent);

			break;
		
		case R.id.btnHistory:
			Intent intent2 = new Intent(TaskDetailActivity.this, VisitHistoryActivity.class);
			intent2.putExtra("task", (freshTask != null) ? freshTask : wlTask);
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
