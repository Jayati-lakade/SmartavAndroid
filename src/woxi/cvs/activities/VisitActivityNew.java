package woxi.cvs.activities;

import woxi.cvs.R;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Visit;
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
import android.widget.Spinner;
import android.widget.TextView;

public class VisitActivityNew extends Activity {

	Button btnCaptureVisit, btnVisitHistory;

	private TextView  customerName,address, landmark, telephoneNumber,altNumber,visitNumber; 
	private Spinner visitAt;
	private FreshTask freshTask;
	private WLTask wlTask;
	private Object obj;
	private Visit visit;
	private int visitAtId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("SmartAV - "+Util.CURRENTCUSTOMER);
		getActionBar().setLogo(null);
		setContentView(R.layout.activity_visitform_new);
		
		customerName = (TextView) this.findViewById(R.id.custName); 	
		address = (TextView) this.findViewById(R.id.billingAddress); 
		landmark = (TextView) this.findViewById(R.id.landmark); 
		telephoneNumber = (TextView) this.findViewById(R.id.MobNo);
		altNumber = (TextView) this.findViewById(R.id.AltNo); 	
		visitNumber = (TextView) this.findViewById(R.id.visitNumber); 
		visitAt = (Spinner)this.findViewById(R.id.visitAt);
		obj = getIntent().getExtras().get("task");
		visit = (Visit) getIntent().getExtras().get("visit");
		
		if(obj instanceof FreshTask){
			freshTask = (FreshTask) obj;
			visit.setOfr_visit(freshTask.getOfr_visit());
			visit.setReav_flag(freshTask.getReav_flag());
			visit.setCaf_no(freshTask.getCaf_no());
			visit.setCluster_name(freshTask.getCluster_name());
			populateFreshTaskFields(freshTask);
			
		} else if(obj instanceof WLTask){
			wlTask = (WLTask) obj;
			visit.setOfr_visit(wlTask.getOfr_visit());
			visit.setReav_flag(wlTask.getReav_flag());
			visit.setCaf_no(wlTask.getCaf_no());
			visit.setCluster_name(wlTask.getCluster_name());
			populateWLTaskFields(wlTask);			
		}		

		btnCaptureVisit = (Button) this.findViewById(R.id.btnCaptureVisit);		
		btnCaptureVisit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				visitAtId = visitAt.getSelectedItemPosition();
				visit.setVisitAt(""+(++visitAtId));
				Intent intent = new Intent(VisitActivityNew.this, CaptureVisitActivity.class);				
				intent.putExtra("task", (freshTask != null) ? freshTask : wlTask);
				intent.putExtra("visit",visit);
				startActivity(intent);
			}
		});
		
	}
	private void populateWLTaskFields(WLTask task) {
		visit.setTask_id(task.getTask_id());
		visit.setOfr_visit(task.getOfr_visit());
		customerName.setText(task.getCust_name()); 
		address.setText(task.getAddress()); 
		landmark.setText(task.getLandmark()); 
		telephoneNumber.setText(task.getTelephone_no());
		altNumber.setText(task.getAlternate_contact_no()); 
		try{
			int visitNo = Integer.parseInt(task.getOfr_visit());
			visitNumber.setText(""+(visitNo));
			
		}catch(Exception ex){
			visitNumber.setText("1");
		}
		
	}

	private void populateFreshTaskFields(FreshTask task) {
		visit.setTask_id(task.getTask_id());
		visit.setOfr_visit(task.getOfr_visit());
		customerName.setText(task.getCust_name()); 
		address.setText(task.getAddress()); 
		landmark.setText(task.getLandmark()); 
		telephoneNumber.setText("" + task.getTelephone_no());
		altNumber.setText("" + task.getAlternate_contact_no()); 
		try{
			int visitNo = Integer.parseInt(task.getOfr_visit());
			visitNumber.setText(""+visitNo);
			
		}catch(Exception ex){
			visitNumber.setText("1");
		}
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
