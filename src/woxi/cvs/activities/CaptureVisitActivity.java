package woxi.cvs.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import com.androidquery.AQuery;

import woxi.cvs.R;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.db.DBUtil;
import woxi.cvs.gpstracking.GPSTracker;
import woxi.cvs.model.DataLoader;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import woxi.cvs.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Base64;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import eu.janmuller.android.simplecropimage.CropImage;

public class CaptureVisitActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Spinner spinnerPersonMet, statusAVOutcome, avOutcomeWithoutPending;
	private EditText personMet, remarks;
	private TextView verifierName, agencyName, message1, message2, message3,supervisorid,
			m2;
	private ImageView signature, imgProofId, imgAddressId, imgHousePhoto,
			supervisor_sign, agency_stamp, imgDocId,ofr_sign;
	private Button btnSubmit;
	private LinearLayout finLinLayout1, finLinLayout2;
	private String relationship;
	private boolean cropImage;
	private static String TAG = "CaptureVisitActivity";
	private Visit visit;
	private int personMetId;
	private RadioGroup welcomeLetterStatus;
	private RadioButton welcomeStatusButton;
	private int selectedId;
	private int statusAVOutcomeId;
	private SharedPreferences.Editor editor;
	private Double latitude, longitude;
	private EditText custlandmark, custAlternateContactNo, custEmailId;
	Object taskObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences preferences = this.getSharedPreferences(
				ConstantSmartAV.PREFERENCES, Context.MODE_PRIVATE);
		editor = preferences.edit();

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("SmartAV - " + ConstantSmartAV.CURRENTCUSTOMER);
		getActionBar().setLogo(null);

		visit = (Visit) getIntent().getExtras().get("visit");

		setContentView(R.layout.activity_capture_visit);
		verifierName = (TextView) this.findViewById(R.id.verifierName);
		agencyName = (TextView) this.findViewById(R.id.agencyName);
		message1 = (TextView) this.findViewById(R.id.message1);
		message2 = (TextView) this.findViewById(R.id.message2);
		message3 = (TextView) this.findViewById(R.id.message3);
		ofr_sign=(ImageView) this.findViewById(R.id.ofr_sign);
		supervisorid=(TextView) this.findViewById(R.id.supervisorid);
		m2 = (TextView) findViewById(R.id.m2);
		spinnerPersonMet = (Spinner) this.findViewById(R.id.spinnerPersonMet);
		spinnerPersonMet.setOnItemSelectedListener(this);
		personMet = (EditText) this.findViewById(R.id.personMet);
		remarks = (EditText) this.findViewById(R.id.remarks);
		custAlternateContactNo = (EditText) this
				.findViewById(R.id.custAltContactNumber);
		custlandmark = (EditText) this.findViewById(R.id.custLandmark);
		custEmailId = (EditText) this.findViewById(R.id.custEmailId);
		supervisor_sign = (ImageView) this.findViewById(R.id.supervisorsignature);
		
		agency_stamp = (ImageView) this.findViewById(R.id.agencystamp);
		
		supervisor_sign.setImageResource(R.drawable.cameraplaceholder);

		taskObj = getIntent().getExtras().get("task");
		AQuery agency_seal = new AQuery(agency_stamp);
		AQuery s_sign = new AQuery(supervisor_sign);
		AQuery user_sign=new AQuery(ofr_sign);
		/*
		 * Desc:Fetch the data from JSON
		 * Developed By:Sourabh Shah
		 * Version 1.1
		 */
		if (taskObj instanceof FreshTask) {
			custAlternateContactNo.setText(((FreshTask) taskObj)
					.getCust_alternate_contact_no());
			custlandmark.setText(((FreshTask) taskObj).getCust_landmark());
			custEmailId.setText(((FreshTask) taskObj).getCust_email_id());
			agencyName.setText(((FreshTask) taskObj).getAgency_name());
			String fname=((FreshTask) taskObj).getSup_fname();
			String lname=((FreshTask) taskObj).getSup_lname();
			supervisorid.setText(fname+" "+lname );
			
			
			 agency_seal.id(agency_stamp).image(((FreshTask) taskObj).getAgency_seal(), false,true, 0, R.drawable.cameraplaceholder);
			 s_sign.id(supervisor_sign).image(((FreshTask) taskObj).getSup_signature(), false,true, 0, R.drawable.cameraplaceholder);
			 user_sign.id(ofr_sign).image(((FreshTask) taskObj).getUser_signature(), false,true, 0, R.drawable.cameraplaceholder);
		} else if (taskObj instanceof WLTask) {
			custAlternateContactNo.setText(((WLTask) taskObj)
					.getCust_alternate_contact_no());
			custlandmark.setText(((WLTask) taskObj).getCust_landmark());
			custEmailId.setText(((WLTask) taskObj).getCust_email_id());
			agencyName.setText(((WLTask) taskObj).getAgency_name());;
			supervisorid.setTag(((WLTask) taskObj).getSup_fname());
			
			String fname=((WLTask) taskObj).getSup_fname();
			String lname=((WLTask) taskObj).getSup_lname();
			supervisorid.setText(fname+" "+lname );
			
			 agency_seal.id(agency_stamp).image(((WLTask) taskObj).getAgency_seal(), false,true, 0, R.drawable.cameraplaceholder);
			 s_sign.id(supervisor_sign).image(((WLTask) taskObj).getSup_signature(), false,true, 0, R.drawable.cameraplaceholder);
			 user_sign.id(ofr_sign).image(((WLTask) taskObj).getUser_signature(), false,true, 0, R.drawable.cameraplaceholder);
		}

		// date = (EditText) this.findViewById(R.id.date);

		signature = (ImageView) this.findViewById(R.id.signature);
		signature.setOnClickListener(this);
		imgProofId = (ImageView) this.findViewById(R.id.imgProofId);
		imgProofId.setOnClickListener(this);
		imgAddressId = (ImageView) this.findViewById(R.id.imgAddressId);
		imgAddressId.setOnClickListener(this);
		imgHousePhoto = (ImageView) this.findViewById(R.id.imgHousePhoto);
		imgHousePhoto.setOnClickListener(this);
		imgDocId = (ImageView) this.findViewById(R.id.imgDocId);
		imgDocId.setOnClickListener(this);
		welcomeLetterStatus = (RadioGroup) this
				.findViewById(R.id.welcomeLetterStatus);
		statusAVOutcome = (Spinner) this.findViewById(R.id.statusAVOutcome);
		avOutcomeWithoutPending = (Spinner) this
				.findViewById(R.id.avOutcomeWithoutPending);
		finLinLayout1 = (LinearLayout) this
				.findViewById(R.id.finAvOutLinLayout1);
		finLinLayout2 = (LinearLayout) this
				.findViewById(R.id.finAvOutLinLayout2);
		if (visit.getOfr_visit().equals("3")
				|| visit.getOfr_visit().equals("6")) {
			finLinLayout1.setVisibility(View.GONE);
			finLinLayout2.setVisibility(View.VISIBLE);

		} else {
			finLinLayout1.setVisibility(View.VISIBLE);
			finLinLayout2.setVisibility(View.GONE);
			
		}

	
		/*
		 * avOutcomeWithoutPending =
		 * (Spinner)this.findViewById(R.id.avOutcomeWithoutPending);
		 * avOutcomeWithoutPending.setVisibility(View.VISIBLE); finalAvOutText2
		 * = (TextView) this.findViewById(R.id.finAvOut2);
		 * finalAvOutText2.setVisibility(View.VISIBLE);
		 */

		// finalAvOutText2.setVisibility(View.VISIBLE);
		// Step 2: Create and fill an ArrayAdapter with a bunch of "State"
		// objects
		/*
		 * String[] NoCore_Array = new String [3]; { NoCore_Array[0] =
		 * "Positive"; NoCore_Array[1] = "To be referred"; NoCore_Array[2] =
		 * "Negative"; }
		 * 
		 * ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
		 * R.array.avOutcomeWithoutPending,
		 * android.R.layout.simple_spinner_item);
		 * statusAVOutcome.setAdapter(NoCoreAdapter);
		 */

		verifierName.setText("Verifier Name : "
				+ preferences.getString(ConstantSmartAV.USERNAME_STR, ConstantSmartAV.ERROR_STRING));
	//	agencyName.setText(visit.getAgencyName());
		// message1.setText(message1.getText().toString().concat(Util.CURRENTCUSTOMER));
	//	supervisorid.setText(visit.getSup_fname());
		message2.setText(visit.getAgencyName()+",");
		m2.setText(visit.getAgencyName()+",");

		//+" "+visit.getSup_lname()
		// message3.setText(message3.getText().toString().concat(Util.CURRENTCUSTOMER));
		btnSubmit = (Button) this.findViewById(R.id.submitVisit);
		ArrayAdapter<CharSequence> adapter;
		/*
		 * Desc:Set the adapter according to choice from drop down.
		 * Developed By:Sourabh Shah
		 * Version:1.1
		 */
		if (visit.getVisitAt().equalsIgnoreCase("1")) {
			adapter = ArrayAdapter.createFromResource(this,
					R.array.relationship_Residential,
					android.R.layout.simple_spinner_item);
		} else {
			adapter = ArrayAdapter.createFromResource(this,
					R.array.relationship_Office,
					android.R.layout.simple_spinner_item);
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPersonMet.setAdapter(adapter);

		btnSubmit.setOnClickListener(this);

	}
	/*
	 * Desc:set the values which are previously captured
	 * Developed By:Sourabh Shah
	 * Version 1.1
	 */
	private void setVisitPOJO(Visit visit) {
		// visit.setAddressId(Util.base64PhotoAdd);
		// visit.setCustomerSign(Util.base64PhotoSign);
		// visit.setDocumentId(Util.base64PhotoDocument);
		// visit.setHouseId(Util.base64PhotoHouse);

		visit.setAlternate_contact_no(custAlternateContactNo.getText()
				.toString());
		visit.setLandmark(custlandmark.getText().toString());
		visit.setCustEmailId(custEmailId.getText().toString());

		String personmetName = URLEncoder
				.encode(personMet.getText().toString());
		visit.setPersonName(personmetName);
		// visit.setProofId(Util.base64PhotoProofId);
		visit.setRelationship(relationship);
		String ofrRemark = URLEncoder.encode(remarks.getText().toString());
		visit.setRemark(ofrRemark);
		personMetId = spinnerPersonMet.getSelectedItemPosition();
		if (visit.getVisitAt().equalsIgnoreCase("1")) {
			visit.setPersonMet("" + (++personMetId));
		} else {
			visit.setPersonMet("" + (personMetId + 17));
		}
		selectedId = welcomeLetterStatus.getCheckedRadioButtonId();
		welcomeStatusButton = (RadioButton) findViewById(selectedId);
		visit.setWelcomeLetterStatus(welcomeStatusButton.getTag().toString());
		visit.setVerification_timestamp(ConstantSmartAV.sdf.format(new Date()));
		if (visit.getOfr_visit().equals("3")
				|| visit.getOfr_visit().equals("6")) {
			statusAVOutcomeId = avOutcomeWithoutPending
					.getSelectedItemPosition();
			visit.setStatusAVOutcome("" + (statusAVOutcomeId + 2));

		} else {
			statusAVOutcomeId = statusAVOutcome.getSelectedItemPosition();
			visit.setStatusAVOutcome("" + (statusAVOutcomeId + 2));

		}
		// visit.setPersonMet(personMet.getText().toString());
	}

	/*
	 * private String getVisistJSON(Visit visit){ Gson gson = new Gson(); return
	 * gson.toJson(visit); }
	 */

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

	@Override
	/*
	 * Desc:Capture the images by identifying type of image.
	 * Developed By:Sourabh Shah
	 * Version:1.1
	 * 
	 * */
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.signature:
			Intent intent = new Intent(getApplicationContext(),
					CaptureSignatureActivity.class);
			startActivityForResult(intent, ConstantSmartAV.SIGNATURE_REQUEST_CODE);
			break;
		case R.id.imgProofId:
			capturePhoto(ConstantSmartAV.PHOTO_TYPE.PROOF_ID_PHOTO);
			break;
		case R.id.imgAddressId:
			capturePhoto(ConstantSmartAV.PHOTO_TYPE.ADDRESS_PHOTO);

			break;
		case R.id.imgHousePhoto:
			capturePhoto(ConstantSmartAV.PHOTO_TYPE.HOUSE_PHOTO);
			break;
		case R.id.imgDocId:
			capturePhoto(ConstantSmartAV.PHOTO_TYPE.DOCUMENT_ID_PHOTO);
			break;
		case R.id.submitVisit:
			Object obj = getIntent().getExtras().get("task");
			File rootsd = Environment.getExternalStorageDirectory();
			
			/* Desc:Delete the camera folder from gallery
			 * Developed By::jayati Lakade.
			 * version:1.6
			 */
			File dcim = new File(rootsd.getAbsolutePath() + "/DCIM/Camera");
			System.out.println("Path of image::" + dcim);
			DeleteRecursive(dcim);

			DBUtil dbUtil = new DBUtil(getApplicationContext());
			/*
			 * String provider = Settings.Secure.getString(getContentResolver(),
			 * Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			 * 
			 * if(!provider.contains("gps")){ //if gps is disabled final Intent
			 * poke = new Intent(); poke.setClassName("com.android.settings",
			 * "com.android.settings.widget.SettingsAppWidgetProvider");
			 * poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			 * poke.setData(Uri.parse("3")); sendBroadcast(poke); }
			 */

			GPSTracker gps = new GPSTracker(this);
			if (gps.canGetLocation()) {
				latitude = gps.getLatitude(); // returns latitude
				longitude = gps.getLongitude(); // returns longitude
				System.out.println("******latitude*********" + latitude);
				System.out.println("******longitude*********" + longitude);
				visit.setLatitude(String.valueOf(latitude));
				visit.setLongitude(String.valueOf(longitude));
				// Toast.makeText(getApplicationContext(),"Your Location is - \nLat: "
				// + latitude + "\nLong: "+ longitude,
				// Toast.LENGTH_LONG).show();

			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				visit.setLatitude("0");
				visit.setLongitude("0");
				// gps.showSettingsAlert();
			}
			
			/*desc:fetch data from json.
			 * developed by:Sourabh Shah
			 * Version:1.1
			 *
			 */
			if (obj instanceof FreshTask) {
				// Visit visit = new Visit();
				FreshTask freshTask = (FreshTask) obj;

				visit.setCaf_no(freshTask.getCaf_no());
				visit.setTask_id(freshTask.getTask_id());
				visit.setReav_flag(freshTask.getReav_flag());
				visit.setOfr_visit(freshTask.getOfr_visit());
				visit.setUser_name(freshTask.getUser_name());
				visit.setUser_fname(freshTask.getUser_fname());
				visit.setUser_lname(freshTask.getUser_lname());
				visit.setActivity_type(freshTask.getActivity_type());
				visit.setVersion_id(ConstantSmartAV.VERSION_ID);
				visit.setCluster_name(freshTask.getCluster_name());

				setVisitPOJO(visit);
				// String visitJson = getVisistJSON(visit);
				long retVal = dbUtil.insertVisitIntoDB(visit);
			
				if (retVal != ConstantSmartAV.ERROR_RETURN_VAL) {
					retVal = dbUtil.updateInputTable(visit.getTask_id());
			

					backToMainActivity(true);
				}

			} else if (obj instanceof WLTask) {
				// Visit visit = new Visit();
				WLTask wlTask = (WLTask) obj;
				visit.setCaf_no(wlTask.getCaf_no());
				visit.setTask_id(wlTask.getTask_id());
				visit.setReav_flag(wlTask.getReav_flag());
			//	visit.setOfr_visit(wlTask.getOfr_visit());
				visit.setUser_name(wlTask.getUser_name());
				visit.setUser_fname(wlTask.getUser_fname());
				visit.setUser_lname(wlTask.getUser_lname());
				visit.setVersion_id(ConstantSmartAV.VERSION_ID);
				visit.setActivity_type(wlTask.getActivity_type());
				visit.setCluster_name(wlTask.getCluster_name());

				setVisitPOJO(visit);
				// String visitJson = getVisistJSON(visit);
				long retVal = dbUtil.insertVisitIntoDB(visit);
			
				if (retVal != ConstantSmartAV.ERROR_RETURN_VAL) {
					retVal = dbUtil.updateInputTable(visit.getTask_id());
			

					backToMainActivity(false);
				}
			}
			break;
		default:
			break;
		}
	}
    /*
     * Desc:Load the fresh list on main window.
     * Developed By:Sourabh shah
     * Version:1.1
     */
	private void backToMainActivity(boolean isFreshTask) {

		DataLoader.IS_TASK_COMPLETED = true;
		Intent main = new Intent(getApplicationContext(), MainActivityNew.class);

		if (isFreshTask) {

			DataLoader.freshTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
			main.putExtra(ConstantSmartAV.CHILD_ITEM, ConstantSmartAV.FRESH);
		} else {
	
			DataLoader.wlTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
			main.putExtra(ConstantSmartAV.CHILD_ITEM, ConstantSmartAV.PENDING);
		}

		main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(main);

		Util.showToast(getString(R.string.taskCaptured), this, true);
		finish();
	}
/*
	private void backToMainActivity(RegularBulkTask regularBulkTaskTask) {

		DataLoader.IS_TASK_COMPLETED = true;
		Intent main = new Intent(getApplicationContext(), MainActivityNew.class);

		DataLoader.regularbulkTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
		main.putExtra(Util.CHILD_ITEM, Util.REGULARBULKTASK);

		main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(main);

		Util.showToast(getString(R.string.taskCaptured), this, true);
		finish();
	}
*/
	/*
	 * Desc:Capture the images
	 * Developed By:Sourabh shah
	 * version:1.1
	 */
	private void capturePhoto(ConstantSmartAV.PHOTO_TYPE type) {

		Uri imageUri = getImageStoreLocation(type);
		ConstantSmartAV.IMAGE_FILE_PATH = imageUri.getPath();

		// ///capture image/////
		Intent cameraIntent = new Intent();
		cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		try {
			switch (type) {
			case PROOF_ID_PHOTO:
				startActivityForResult(cameraIntent,
						ConstantSmartAV.PROOF_ID_PHOTO_REQUEST_CODE);

				// cropImage imagePath :
				// /storage/sdcard0/doco/2665_3_0_proof_id.png

				break;
			case ADDRESS_PHOTO:
				startActivityForResult(cameraIntent,
						ConstantSmartAV.ADDRESS_PHOTO_REQUEST_CODE);
				break;
			case HOUSE_PHOTO:
				startActivityForResult(cameraIntent,
						ConstantSmartAV.HOUSE_PHOTO_REQUEST_CODE);
				break;
			case DOCUMENT_ID_PHOTO:
				startActivityForResult(cameraIntent,
						ConstantSmartAV.DOCUMENT_PHOTO_REQUEST_CODE);
				break;
			default:
				break;
			}
		} catch (Exception e1) {

			Util.showToast("Camera not available", getApplicationContext(),
					true);
			e1.printStackTrace();
		}
	}
	/*
	 * Desc:Store the image with the proper name and extension 
	 * Developed By:Sourabh Shah
	 * Version 1.1
	 */
	private Uri getImageStoreLocation(ConstantSmartAV.PHOTO_TYPE type) {

		File docoImageDir = null;
		File docoImage = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
	
			/*
			 * 02-11 00:48:50.373: I/CaptureIndividualRequest(1041):
			 * capturePhoto : imageUri : file:///storage/sdcard/doco/doco.jpg
			 */

			docoImageDir = new File(Environment.getExternalStorageDirectory()
					+ ConstantSmartAV.IMAGE_FOLDER);

			try {
				if (!docoImageDir.exists()) {
					docoImageDir.mkdir();
				}
				switch (type) {
				case PROOF_ID_PHOTO:

					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-proof_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setProofId(docoImage.getName());
					break;

				case ADDRESS_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-address_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setAddressId(docoImage.getName());
					break;
				case HOUSE_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-house_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setHouseId(docoImage.getName());
					break;
				case DOCUMENT_ID_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-document_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setDocumentId(docoImage.getName());
					break;
				default:
					break;

				}

				return Uri.fromFile(docoImage);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		} else {
		

			docoImageDir = new File(getFilesDir() + ConstantSmartAV.IMAGE_FOLDER);

			try {
				if (!docoImageDir.exists()) {
					docoImageDir.mkdir();
				}

				switch (type) {
				case PROOF_ID_PHOTO:

					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-proof_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setProofId(docoImage.getName());
					break;

				case ADDRESS_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-address_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setAddressId(docoImage.getName());
					break;
				case HOUSE_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-house_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setHouseId(docoImage.getName());
					break;
				case DOCUMENT_ID_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-document_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + ConstantSmartAV.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setDocumentId(docoImage.getName());
					break;
				default:
					break;

				}

				return Uri.fromFile(docoImage);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

	}

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { if (resultCode == 1) { Bitmap b =
	 * BitmapFactory.decodeByteArray( data.getByteArrayExtra("byteArray"), 0,
	 * data.getByteArrayExtra("byteArray").length); signImage.setImageBitmap(b);
	 * } }
	 */

	@Override
	/*
	 * Desc:Crop the captured image
	 * Developed By:Sourabh Shah
	 * Version:1.1
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);



		if (resultCode == Activity.RESULT_OK) {

			// Uri imageUri = data.getData();
			// Log.i(TAG, "Photo path : " + imageUri);

			try {
				if (requestCode == ConstantSmartAV.PROOF_ID_PHOTO_REQUEST_CODE) {
					/*
					 * Uri imageUri = data.getData(); Log.i(TAG, "Photo path : "
					 * + imageUri);
					 */

					cropImage(ConstantSmartAV.IMAGE_FILE_PATH,
							ConstantSmartAV.PHOTO_TYPE.PROOF_ID_PHOTO);

				} else if (requestCode == ConstantSmartAV.ADDRESS_PHOTO_REQUEST_CODE) {
					cropImage(ConstantSmartAV.IMAGE_FILE_PATH,
							ConstantSmartAV.PHOTO_TYPE.ADDRESS_PHOTO);

				} else if (requestCode == ConstantSmartAV.HOUSE_PHOTO_REQUEST_CODE) {
					cropImage(ConstantSmartAV.IMAGE_FILE_PATH, ConstantSmartAV.PHOTO_TYPE.HOUSE_PHOTO);

				} else if (requestCode == ConstantSmartAV.DOCUMENT_PHOTO_REQUEST_CODE) {
					cropImage(ConstantSmartAV.IMAGE_FILE_PATH,
							ConstantSmartAV.PHOTO_TYPE.DOCUMENT_ID_PHOTO);

				}/*
				 * else if (requestCode == Util.SIGNATURE_REQUEST_CODE) {
				 * 
				 * // BitmapDrawable drawable = new BitmapDrawable( //
				 * getResources(), bitmap); //
				 * signImageView.setBackgroundDrawable(drawable);
				 * 
				 * 
				 * byte[] bs = data.getByteArrayExtra("data"); Bitmap bitmap =
				 * BitmapFactory.decodeByteArray(bs,0, bs.length);
				 * signature.setImageBitmap(bitmap); Util.base64PhotoSign =
				 * getImageBase64(bitmap);
				 * 
				 * 
				 * Bitmap b = BitmapFactory.decodeByteArray(
				 * data.getByteArrayExtra("byteArray"), 0,
				 * data.getByteArrayExtra("byteArray").length);
				 * signature.setImageBitmap(b);
				 * 
				 * }
				 */else if (requestCode == ConstantSmartAV.PROOF_ID_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(
							ConstantSmartAV.PROOF_ID_PHOTO_REQUEST_CODE_CROP, data);

				} else if (requestCode == ConstantSmartAV.ADDRESS_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(
							ConstantSmartAV.ADDRESS_PHOTO_REQUEST_CODE_CROP, data);

				} else if (requestCode == ConstantSmartAV.HOUSE_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(ConstantSmartAV.HOUSE_PHOTO_REQUEST_CODE_CROP,
							data);

				} else if (requestCode == ConstantSmartAV.DOCUMENT_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(
							ConstantSmartAV.DOCUMENT_PHOTO_REQUEST_CODE_CROP, data);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (resultCode == 1 && requestCode == 5) {

			String signFileName = null;
			Bitmap b = BitmapFactory.decodeByteArray(
					data.getByteArrayExtra("byteArray"), 0,
					data.getByteArrayExtra("byteArray").length);
			signature.setImageBitmap(b);

			// File docoImageDir = new File(getFilesDir() + Util.IMAGE_FOLDER);
			File docoImageDir = null;
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				docoImageDir = new File(
						Environment.getExternalStorageDirectory()
								+ ConstantSmartAV.IMAGE_FOLDER);
			} else {
				docoImageDir = new File(getFilesDir() + ConstantSmartAV.IMAGE_FOLDER);
			}
			FileOutputStream fos = null;
			try {
				if (!docoImageDir.exists()) {
					docoImageDir.mkdir();
				}

				signFileName = visit.getTask_id() + "-" + visit.getOfr_visit()
						+ "-" + visit.getReav_flag() + "-customer_sign-"
						+ visit.getCaf_no() + "-" + visit.getCluster_name()
						+ ConstantSmartAV.IMAGE_EXTENSION;

				File f = new File(docoImageDir, signFileName);
				f.createNewFile();
				visit.setCustomerSign(f.getName());

				Bitmap bitmap = b;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 0 /* ignored for PNG */,
						bos);
				byte[] bitmapdata = bos.toByteArray();

				// write the bytes in file
				fos = new FileOutputStream(f);
				fos.write(bitmapdata);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
		
				}
			}

			// Util.base64PhotoSign = getImageBase64(b);
			ConstantSmartAV.base64PhotoSign = signFileName;
		}
	}

	private Uri returnURI(Intent data) {

		return Uri.parse(data.getStringExtra(CropImage.IMAGE_PATH));
	}
/*
 * Desc:Set the crop image in the given coordinates
 * Developed By:Sourabh Shah
 * Version 1.6
 */
	private void cropImage(String imagePath, ConstantSmartAV.PHOTO_TYPE type) {


		try {
			Intent cropIntent = new Intent(getApplicationContext(),
					CropImage.class);
			cropIntent.putExtra(CropImage.IMAGE_PATH, imagePath);
			cropIntent.putExtra(CropImage.SCALE, true);
			cropIntent.putExtra(CropImage.ASPECT_X, 3);
			cropIntent.putExtra(CropImage.ASPECT_Y, 2);

			cropImage = true;

			switch (type) {
			case PROOF_ID_PHOTO:
				startActivityForResult(cropIntent,
						ConstantSmartAV.PROOF_ID_PHOTO_REQUEST_CODE_CROP);
				break;
			case ADDRESS_PHOTO:
				startActivityForResult(cropIntent,
						ConstantSmartAV.ADDRESS_PHOTO_REQUEST_CODE_CROP);
				break;
			case HOUSE_PHOTO:
				startActivityForResult(cropIntent,
						ConstantSmartAV.HOUSE_PHOTO_REQUEST_CODE_CROP);
				break;
			case DOCUMENT_ID_PHOTO:
				startActivityForResult(cropIntent,
						ConstantSmartAV.DOCUMENT_PHOTO_REQUEST_CODE_CROP);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			cropImage = false;
			e.printStackTrace();
		}
	}
/*
 * Desc:Show the crop image as preview
 * Developed By:Sourabh Shah
 * Version:1.1
 *
 */
	@SuppressWarnings("unused")
	private void showCroppedImagePreview(int requestCode, Intent data) {

		Uri imageUri = returnURI(data);

		// options = new BitmapFactory.Options();
		// set the size to option, the images we will load by using this option

		Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inMutable = true;
		File f = new File(imageUri.getPath());
		long size = f.length();
		File docoImageDir = new File(Environment.getExternalStorageDirectory()
				+ ConstantSmartAV.IMAGE_FOLDER);
		try {
			// options.inJustDecodeBounds = true;
			// options.inMutable = true;

			Bitmap croppedBitmap = BitmapFactory.decodeFile(imageUri.getPath());

			// Bitmap
			// croppedBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.crpt
			// );
			Bitmap corruptImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.corrupt);

			// Bitmap
			// corruptImage=BitmapFactory.decodeFile(R.drawable.corrupt_image);
			// croppedBitmap =
			// MediaStore.Images.Media.getBitmap(getContentResolver(), imagUri);
			// commonImageView.setImageBitmap(croppedBitmap);

			// BitmapDrawable drawable = new BitmapDrawable(getResources(),
			// croppedBitmap);
			// http://stackoverflow.com/questions/22355566/bitmap-allocation-using-bitmapfactory-options-inbitmap-throws-illegalargumentex
	/*
			 * Desc:set corrupt image if image size is null else set the captured image in the respective image view.
			 * Developed By:Jayati Lakade
			 * version:1.6
	 */
			if (requestCode == ConstantSmartAV.PROOF_ID_PHOTO_REQUEST_CODE_CROP) {

				if (croppedBitmap == null || f.length() == 0) {
					imgProofId.setImageBitmap(corruptImage);
					f.delete();

				} else {
					imgProofId.setImageBitmap(croppedBitmap);

				}

				// Util.base64PhotoProofId = getImageBase64(croppedBitmap);
				ConstantSmartAV.base64PhotoProofId = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_proof_id" + ConstantSmartAV.IMAGE_EXTENSION;
				// Log.i(TAG, "Util.base64PhotoId : " + Util.base64PhotoId);

				options.inJustDecodeBounds = false;
				options.inSampleSize = 1;
				options.inBitmap = croppedBitmap;
			} else if (requestCode == ConstantSmartAV.ADDRESS_PHOTO_REQUEST_CODE_CROP) {

				// imgAddressId.setImageBitmap(croppedBitmap);

				if (croppedBitmap == null || f.length() == 0) {
					imgAddressId.setImageBitmap(corruptImage);
					f.delete();

				} else {
					imgAddressId.setImageBitmap(croppedBitmap);

				}
				// imgAddressId.setImageBitmap(croppedBitmap);
				// Util.base64PhotoAdd = getImageBase64(croppedBitmap);
				ConstantSmartAV.base64PhotoAdd = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_address_photo" + ConstantSmartAV.IMAGE_EXTENSION;

			} else if (requestCode == ConstantSmartAV.HOUSE_PHOTO_REQUEST_CODE_CROP) {

				// imgHousePhoto.setImageBitmap(croppedBitmap);

				if (croppedBitmap == null || f.length() == 0) {
					imgHousePhoto.setImageBitmap(corruptImage);
					f.delete();

				} else {
					imgHousePhoto.setImageBitmap(croppedBitmap);

				}

				ConstantSmartAV.base64PhotoHouse = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_house_photo" + ConstantSmartAV.IMAGE_EXTENSION;

			} else if (requestCode == ConstantSmartAV.DOCUMENT_PHOTO_REQUEST_CODE_CROP) {

				// imgDocId.setImageBitmap(croppedBitmap);

				if (croppedBitmap == null || f.length() == 0) {
					imgDocId.setImageBitmap(corruptImage);
					f.delete();

				} else {
					imgDocId.setImageBitmap(croppedBitmap);

				}

				ConstantSmartAV.base64PhotoDocument = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_document_id" + ConstantSmartAV.IMAGE_EXTENSION;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	  private String getImageBase64(Bitmap bmp) {
	  
	 ByteArrayOutputStream stream = new ByteArrayOutputStream();
	  bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream); byte[] bs =
	  stream.toByteArray();
	  
	  return Base64.encodeToString(bs, Base64.DEFAULT);
	  
	  }
	 

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {

		relationship = adapterView.getItemAtPosition(position).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
	/* Desc:Delete the camera folder from gallery.
	 * Developed By::jayati Lakade.
	 * version:1.6
	 */
	void DeleteRecursive(File dcim) {
		if (dcim.isDirectory())
			for (File child : dcim.listFiles())
				DeleteRecursive(child);

		dcim.delete();
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}
}
