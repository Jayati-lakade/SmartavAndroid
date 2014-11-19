package woxi.cvs.activities;

import woxi.cvs.R;
import woxi.cvs.bulk.BulkCustomerAddDetailActivity;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.Visit;
import woxi.cvs.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class VisitActivityNewBulk extends Activity {

	Button btnCaptureVisit, btnVisitHistory;

	private TextView  customerName,address, landmark, telephoneNumber,altNumber,visitNumber,fatherName; 
	private Spinner visitAt;
	private BulkTask bulkTask;
	private Object obj;
	private Visit visit;
	private int visitAtId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("SmartAV - "+Util.CURRENTCUSTOMER);
		getActionBar().setLogo(null);
		setContentView(R.layout.activity_visitform_new_bulk);
		
		customerName = (TextView) this.findViewById(R.id.custName); 	
		address = (TextView) this.findViewById(R.id.billingAddress); 
		landmark = (TextView) this.findViewById(R.id.landmark); 
		telephoneNumber = (TextView) this.findViewById(R.id.MobNo);
		altNumber = (TextView) this.findViewById(R.id.AltNo); 	
		visitNumber = (TextView) this.findViewById(R.id.visitNumber); 
		visitAt = (Spinner)this.findViewById(R.id.visitAt);
//		fatherName = (TextView) this.findViewById(R.id.personMet);
		obj = getIntent().getExtras().get("task");
		visit = (Visit) getIntent().getExtras().get("visit");
		
		if(obj instanceof BulkTask){
			bulkTask = (BulkTask) obj;
			visit.setCaf_no(bulkTask.getCaf_no());
			//visit.setOfr_visit(bulkTask.getOfr_visit());
			//visit.setReav_flag(bulkTask.getReav_flag());
			//visit.setCluster_name(bulkTask.getCluster_name());
			populateBulkTaskFields(bulkTask);
			
		} 
		
		

		btnCaptureVisit = (Button) this.findViewById(R.id.btnCaptureVisit);
//		btnVisitHistory = (Button) this.findViewById(R.id.btnVisitHistory);
		
		btnCaptureVisit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				visitAtId = visitAt.getSelectedItemPosition();
				visit.setVisitAt(""+(++visitAtId));
				Intent intent = new Intent(VisitActivityNewBulk.this, BulkCustomerAddDetailActivity.class);				
		intent.putExtra("task", bulkTask);
				intent.putExtra("visit",visit);
				startActivity(intent);
//				startActivity(new Intent(VisitActivityNew.this, CaptureVisitActivity.class));
			}
		});
		
//		btnVisitHistory.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {visi
//				startActivity(new Intent(VisitActivityNew.this, VisitHistoryActivity.class));
//			}
//		});
	}
	private void populateBulkTaskFields(BulkTask task) {
//		delsAccount.setText(task.getNo_of_dels_under_account()); 
		//visit.setTask_id(task.getTask_id());
		task.setTask_id(task.getTask_id());
	//	visit.setOfr_visit(task.getOfr_visit());
		customerName.setText(task.getCust_name()); 
		address.setText(task.getAddress()); 
	//	landmark.setText(task.getLandmark()); 
		telephoneNumber.setText(task.getTelephone_no());
		altNumber.setText(task.getAlternate_contact_no()); 
		
		/*try{
			int visitNo = Integer.parseInt(task.getOfr_visit());
			visitNumber.setText(""+(visitNo));
			
		}catch(Exception ex){
			visitNumber.setText("1");
		}
		*/
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
