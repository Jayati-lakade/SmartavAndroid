package woxi.cvs.activities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import woxi.cvs.R;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.customwidgets.DateTimePicker;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Task;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DecisionActivity extends FragmentActivity implements
		OnClickListener {
	private static String TAG = "DecisionActivity";
	private Switch addressTraced, callCustomer, homeLocked, callForAppointment, callSpoc, noOfLines, plan, product, hardwareDel;
	private Spinner businssType, occupancy, accomodation, area,	negativeShortReason,houseType;
	private ImageView pickDateTime;
	static TextView appointmentDate,csmNo,spocNo,customerNo;// , appointmentTime;
	private LinearLayout set_appointment_ll;
	private Button btnProceed;
	private Object obj;
	private StringBuilder state = new StringBuilder();
	private Visit visit;
	static StringBuilder DEFAULT_YES = new StringBuilder("Yes");
	static StringBuilder DEFAULT_NO = new StringBuilder("No");
	static StringBuilder DEFAULT_Confirm = new StringBuilder("Confirm");
	static StringBuilder DEFAULT_NotConfirm = new StringBuilder("Not Confirm");
	static StringBuilder DEFAULT_Received = new StringBuilder("Received");
	static StringBuilder DEFAULT_NotReceived = new StringBuilder("Not Received");
	private FreshTask freshTask;
	private WLTask wlTask;
	private Task task;
	private static Long CONSTRAINTTIME = Long.valueOf(57600); // 96 HOURS In Minutes Time Constraint.
	private boolean isVisitFresh = false;
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private RadioGroup customerStayAddressStatus;
	private RadioButton customerStayAddressButton;
	private int businssTypeId, occupancyId, accomodationId, areaId,	negativeShortReasonId,houseId;
	private int ofr_visit;//To set master table Ids for Server side

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle("SmartAV - "+ConstantSmartAV.CURRENTCUSTOMER);
		setContentView(R.layout.activity_decision);
		visit = new Visit();
		csmNo = (TextView)this.findViewById(R.id.csmNo);
		spocNo = (TextView)this.findViewById(R.id.spocNo);
		customerNo = (TextView)this.findViewById(R.id.customerNo);
		addressTraced = (Switch) this.findViewById(R.id.addressTraced);
		noOfLines = (Switch) this.findViewById(R.id.noOfLines);
		plan = (Switch) this.findViewById(R.id.plan);
		product = (Switch) this.findViewById(R.id.product);
		hardwareDel = (Switch) this.findViewById(R.id.hardwareDel);
		callCustomer = (Switch) this.findViewById(R.id.callCustomer);
		callSpoc = (Switch) this.findViewById(R.id.callSpoc);
		homeLocked = (Switch) this.findViewById(R.id.homeLocked);		
		customerStayAddressStatus = (RadioGroup) this.findViewById(R.id.customerStayAddressStatus);
		callForAppointment = (Switch) this.findViewById(R.id.callForAppointment);
		pickDateTime = (ImageView) this.findViewById(R.id.pickDateTime);
		appointmentDate = (TextView) this.findViewById(R.id.appointmentDate);		
		set_appointment_ll = (LinearLayout) this.findViewById(R.id.set_appointment_ll);
		businssType = (Spinner) this.findViewById(R.id.businessType);
		occupancy = (Spinner) this.findViewById(R.id.occupancy);
		accomodation = (Spinner) this.findViewById(R.id.accomodation);
		area = (Spinner) this.findViewById(R.id.area);
		houseType = (Spinner) this.findViewById(R.id.houseType);
		negativeShortReason = (Spinner) this.findViewById(R.id.negativeShortReason);
		
		btnProceed = (Button) this.findViewById(R.id.btnProceed);
/*
		 * Desc:following fields are shown on tablet screen for capturing the information. 
		 * Developed By:Sourabh shah
		 * version:1.1
 */
		if (addressTraced != null) {
			addressTraced.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 			 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.setAddressTraced(DEFAULT_NO.toString());
				} else {
					visit.setAddressTraced(DEFAULT_YES.toString());
				}
			 }
		  });
		}
		
		if (noOfLines != null) {
			noOfLines.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 			 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.setNoOfLines(DEFAULT_NotConfirm.toString());
				} else {
					visit.setNoOfLines(DEFAULT_Confirm.toString());
				}
			 }
		  });
		}
		
		if (plan != null) {
			plan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 			 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.setplan(DEFAULT_NotConfirm.toString());
				} else {
					visit.setplan(DEFAULT_Confirm.toString());
				}
			 }
		  });
		}
		
		if (product != null) {
			product.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 			 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.setproduct(DEFAULT_NotConfirm.toString());
				}  else {
					visit.setproduct(DEFAULT_Confirm.toString());
				}
			 }
		  });
		}
		
		if (hardwareDel != null) {
			hardwareDel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 			 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.sethardwareDel(DEFAULT_NotReceived.toString());
				}  else {
					visit.sethardwareDel(DEFAULT_Received.toString());
				}
			 }
		  });
		}
		

		if (callCustomer != null) {
			callCustomer.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.setCallCustome(DEFAULT_NO.toString());
				} else {
					visit.setCallCustome(DEFAULT_YES.toString());
				}
			}
		  });
		}
		if (callSpoc != null) {
			callSpoc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					state.setLength(0);
					if (!isChecked) {
						visit.setCallSpoc(DEFAULT_NO.toString());
					} else {
						visit.setCallSpoc(DEFAULT_YES.toString());
					}
				}
			});
		}
		if (homeLocked != null) {
			homeLocked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				state.setLength(0);
				if (!isChecked) {
					visit.setHomeLocked(DEFAULT_NO.toString());
				} else {
					visit.setHomeLocked(DEFAULT_YES.toString());
				}
			}
			});
		}
		if (callForAppointment != null) {
			callForAppointment.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					state.setLength(0);
					if (!isChecked) {
						visit.setCallForAppointment(DEFAULT_NO.toString());
						set_appointment_ll.setVisibility(View.INVISIBLE);
						pickDateTime.setOnClickListener(null);
					} else {
						visit.setCallForAppointment(DEFAULT_YES.toString());
						set_appointment_ll.setVisibility(View.VISIBLE);
						pickDateTime.setOnClickListener(DecisionActivity.this);
					}
				}
					});
		}

		obj = getIntent().getExtras().get("task");
		if (obj instanceof FreshTask) {
			freshTask = (FreshTask) obj;
			visit.setAgencyName(freshTask.getAgency_name());
			spocNo.setText("SpocNo : "+freshTask.getSpoc_cntc_no());
			csmNo.setText("CsmNo : "+freshTask.getCsm_cntc_no());
			customerNo.setText(freshTask.getTelephone_no());
			
		} else if (obj instanceof WLTask) {
			wlTask = (WLTask) obj;
			visit.setAgencyName(wlTask.getAgency_name());
			spocNo.setText("SpocNo : "+wlTask.getSpoc_cntc_no());
			csmNo.setText("CsmNO : "+wlTask.getCsm_cntc_no());	
			customerNo.setText(wlTask.getTelephone_no());	
		}

		btnProceed.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					if (visit.getCallForAppointment().equalsIgnoreCase(DEFAULT_YES.toString())) {
						checkIfLessThan96Hours();
					} else {
						callCaptureVisitActivity();
					}
				} catch (ParseException e) {
		
				}
			}
		});
	}

	/*
	 * Desc:show the calendar.
	 * Developed By:Sourabh shah
	 * Version:1.1
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pickDateTime:
			// DialogFragment newFragment = new DatePickerFragment();
			// newFragment.show(getSupportFragmentManager(), "datePicker");
			showDateTimeDialog();
			break;
		}
	}

	private void checkIfLessThan96Hours() throws ParseException {

		if(appointmentDate.getText().toString().isEmpty()){
		Toast.makeText(DecisionActivity.this,"Please select Appointment Date..",Toast.LENGTH_LONG).show();
		}
		Date d1 = ConstantSmartAV.sdf.parse(appointmentDate.getText().toString());
		Date d2 = ConstantSmartAV.sdf.parse(ConstantSmartAV.sdf.format(Calendar.getInstance().getTime()));
		
		try {
			if (obj instanceof FreshTask) {

				d2 = ConstantSmartAV.sdf.parse(task.getStart_timestamp());
			} else if (obj instanceof WLTask) {
				d2 = ConstantSmartAV.sdf.parse((task.getStart_timestamp()));
			}

			Long diff = d2.getTime() - d1.getTime();
			Long diffMins = diff / (60 * 1000);
			int diffValue = CONSTRAINTTIME.compareTo(diffMins);
			if (obj instanceof FreshTask) {
				freshTask = (FreshTask) obj;
				visit.setTask_id(freshTask.getTask_id());
				visit.setCaf_no(freshTask.getCaf_no());
				visit.setReav_flag(freshTask.getReav_flag());
				visit.setOfr_visit(freshTask.getOfr_visit());
				visit.setAgencyName(freshTask.getAgency_name());
				isVisitFresh = true;
			} else if (obj instanceof WLTask) {
				wlTask = (WLTask) obj;
				visit.setTask_id(wlTask.getTask_id());
				visit.setCaf_no(wlTask.getCaf_no());
				visit.setReav_flag(wlTask.getReav_flag());
				//visit.setOfr_visit(task.getOfr_visit());
				visit.setAgencyName(wlTask.getAgency_name());
			}
			if (diffValue > 0) {				
				
				popUpDialog(isVisitFresh);
			}
			if (diffValue <= 0) {
		
				callCaptureVisitActivity();
			}
				
//			}
		} catch (ParseException e) {
		
		}
	}
	/*
	 * Desc:According to SLA logic if the appointment exceeds show the message .
	 * Developed By:Sourabh shah
	 * Version:1.1
	 */
	private void popUpDialog(boolean visitType) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Alert");
		alertDialogBuilder.setMessage("Your appointment date is exceeding SLA limit.\n Do You want To proceed");
		
		alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				callCaptureVisitActivity();		
			}
		});
		alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
	}

	private void showDateTimeDialog() {
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(this);
		// Inflate the root layout
		final ScrollView mDateTimeDialogView = (ScrollView) getLayoutInflater()
				.inflate(R.layout.date_time_dialog, null);
		// Grab widget instance
		final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView
				.findViewById(R.id.DateTimePicker);
		// Check is system is set to use 24h time (this doesn't seem to work as
		// expected though)
		// final String timeS = android.provider.Settings.System.getString(
		// getContentResolver(),
		// android.provider.Settings.System.TIME_12_24);
		// final boolean is24h = !(timeS == null || timeS.equals("12"));

		// Update demo TextViews when the "OK" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						mDateTimePicker.clearFocus();
						int month = (mDateTimePicker.get(Calendar.MONTH) + 1);
					
						String mon = "";
						if (month / 10 == 0) {
							mon = "0" + month;
					
						} else {
							mon = String.valueOf(month);
				
						}
						appointmentDate.setText(mDateTimePicker
								.get(Calendar.YEAR)
								+ "-"
								+ mon.toString()
								+ "-"
								+ mDateTimePicker.get(Calendar.DAY_OF_MONTH));

						appointmentDate.setText(appointmentDate.getText()
								.toString()
								+ " "
								+ mDateTimePicker.get(Calendar.HOUR_OF_DAY)
								+ ":"
								+ mDateTimePicker.get(Calendar.MINUTE)
								+ ":" + "00");
						mDateTimeDialog.dismiss();
					
					}

				});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						mDateTimeDialog.cancel();
					}
				});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						mDateTimePicker.reset();
					}
				});

		// Setup TimePicker
		// mDateTimePicker.setIs24HourView(is24h);
		// No title on the dialog window
		mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mDateTimeDialog.setContentView(mDateTimeDialogView);
		// Display the dialog
		mDateTimeDialog.show();
	}

//	private void backToMainActivity() {
//		DataLoader.IS_TASK_COMPLETED = true;
//		Intent main = new Intent(getApplicationContext(), MainActivityNew.class);
//		Log.i(TAG, "DataLoader.CLICKED_ITEM_POSITION : "+ DataLoader.CLICKED_ITEM_POSITION);
//		if (isVisitFresh) {
//			Log.i(TAG, "DataLoader.freshTaskList.size() : "+ DataLoader.freshTaskList.size());
//			DataLoader.freshTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
//			main.putExtra(Util.CHILD_ITEM, Util.FRESH);
//		} else {
//			Log.i(TAG, "DataLoader.wlTaskList.size() : "+ DataLoader.wlTaskList.size());
//			DataLoader.wlTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
//			main.putExtra(Util.CHILD_ITEM, Util.PENDING);
//		}
//		main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(main);
//		Util.showToast(getString(R.string.taskCaptured), this, true);
//		finish();
//	}

	private void setVisitPOJO(Visit visit) {

		businssTypeId = businssType.getSelectedItemPosition();
		visit.setBusinssType("" + (++businssTypeId));		
		occupancyId = occupancy.getSelectedItemPosition();
		visit.setOccupancy("" + (++occupancyId));		
		accomodationId = accomodation.getSelectedItemPosition();
		visit.setAccomodation("" + (++accomodationId));		
		areaId = area.getSelectedItemPosition();
		visit.setArea("" + (++areaId));		
		negativeShortReasonId = negativeShortReason.getSelectedItemPosition();		
		visit.setNegativeShortReason("" + (++negativeShortReasonId));	
		houseId = houseType.getSelectedItemPosition();
		visit.setHouseType(""+(++houseId));
		int selectedId = customerStayAddressStatus.getCheckedRadioButtonId();
		customerStayAddressButton = (RadioButton) findViewById(selectedId);
		
		visit.setCustomerStayAddressStatus(""+ customerStayAddressButton.getTag().toString());
		visit.setVerification_timestamp(ConstantSmartAV.sdf.format(new Date()));
		visit.setAppointmentTimeDate(appointmentDate.getText().toString());
	}
	public void callCaptureVisitActivity(){

		Intent intent = new Intent(DecisionActivity.this,VisitActivityNew.class);

		if (obj instanceof FreshTask) {
			visit.setOfr_visit(freshTask.getOfr_visit());
			intent.putExtra("task", freshTask);
		} else if (obj instanceof WLTask) {
			//visit.setOfr_visit(wlTask.getOfr_visit());
			intent.putExtra("task", wlTask);
		}

		setVisitPOJO(visit);
		intent.putExtra("visit", visit);
		startActivity(intent);	
	}
}