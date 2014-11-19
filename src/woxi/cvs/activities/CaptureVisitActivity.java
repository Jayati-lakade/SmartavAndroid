package woxi.cvs.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import woxi.cvs.R;
import woxi.cvs.db.DBUtil;
import woxi.cvs.gpstracking.GPSTracker;
import woxi.cvs.model.DataLoader;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import woxi.cvs.util.Util;
import woxi.cvs.util.Util.PHOTO_TYPE;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.util.Log;
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
	private TextView verifierName, agencyName, message;
	private ImageView signature, imgProofId, imgAddressId, imgHousePhoto,
			imgDocId;
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
				Util.PREFERENCES, Context.MODE_PRIVATE);
		editor = preferences.edit();

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("SmartAV - " + Util.CURRENTCUSTOMER);
		getActionBar().setLogo(null);

		visit = (Visit) getIntent().getExtras().get("visit");

		setContentView(R.layout.activity_capture_visit);
		verifierName = (TextView) this.findViewById(R.id.verifierName);
		agencyName = (TextView) this.findViewById(R.id.agencyName);
		message = (TextView) this.findViewById(R.id.message);
		spinnerPersonMet = (Spinner) this.findViewById(R.id.spinnerPersonMet);
		spinnerPersonMet.setOnItemSelectedListener(this);
		personMet = (EditText) this.findViewById(R.id.personMet);
		remarks = (EditText) this.findViewById(R.id.remarks);
		custAlternateContactNo = (EditText) this
				.findViewById(R.id.custAltContactNumber);
		custlandmark = (EditText) this.findViewById(R.id.custLandmark);
		custEmailId = (EditText) this.findViewById(R.id.custEmailId);

		taskObj = getIntent().getExtras().get("task");

		if (taskObj instanceof FreshTask) {
			custAlternateContactNo.setText(((FreshTask) taskObj)
					.getCust_alternate_contact_no());
			custlandmark.setText(((FreshTask) taskObj).getCust_landmark());
			custEmailId.setText(((FreshTask) taskObj).getCust_email_id());
		} else if (taskObj instanceof WLTask) {
			custAlternateContactNo.setText(((WLTask) taskObj)
					.getCust_alternate_contact_no());
			custlandmark.setText(((WLTask) taskObj).getCust_landmark());
			custEmailId.setText(((WLTask) taskObj).getCust_email_id());
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
			Log.i(TAG, "Pending Blocked !!!!");
		} else {
			finLinLayout1.setVisibility(View.VISIBLE);
			finLinLayout2.setVisibility(View.GONE);
			Log.i(TAG, "Pending Allowed !!!!");
		}

		Log.i(TAG, "SmartAV : Ofr Visit : **********" + visit.getOfr_visit());
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
				+ preferences.getString(Util.USERNAME_STR, Util.ERROR_STRING));
		agencyName.setText(visit.getAgencyName());
		message.setText(message.getText().toString()
				.concat(Util.CURRENTCUSTOMER));
		btnSubmit = (Button) this.findViewById(R.id.submitVisit);
		ArrayAdapter<CharSequence> adapter;
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
		visit.setVerification_timestamp(Util.sdf.format(new Date()));
		if (visit.getOfr_visit().equals("3")
				|| visit.getOfr_visit().equals("6")) {
			statusAVOutcomeId = avOutcomeWithoutPending
					.getSelectedItemPosition();
			visit.setStatusAVOutcome("" + (statusAVOutcomeId + 2));
			Log.i(TAG, "Pending Block Count Change Here");
		} else {
			statusAVOutcomeId = statusAVOutcome.getSelectedItemPosition();
			visit.setStatusAVOutcome("" + (statusAVOutcomeId + 2));
			Log.i(TAG, "Without Pending Count Change Here");
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
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.signature:
			Intent intent = new Intent(getApplicationContext(),
					CaptureSignatureActivity.class);
			startActivityForResult(intent, Util.SIGNATURE_REQUEST_CODE);
			break;
		case R.id.imgProofId:
			capturePhoto(PHOTO_TYPE.PROOF_ID_PHOTO);
			break;
		case R.id.imgAddressId:
			capturePhoto(PHOTO_TYPE.ADDRESS_PHOTO);

			break;
		case R.id.imgHousePhoto:
			capturePhoto(PHOTO_TYPE.HOUSE_PHOTO);
			break;
		case R.id.imgDocId:
			capturePhoto(PHOTO_TYPE.DOCUMENT_ID_PHOTO);
			break;
		case R.id.submitVisit:
			Object obj = getIntent().getExtras().get("task");
			File rootsd = Environment.getExternalStorageDirectory();
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
				visit.setVersion_id(Util.VERSION_ID);
				visit.setCluster_name(freshTask.getCluster_name());

				setVisitPOJO(visit);
				// String visitJson = getVisistJSON(visit);
				long retVal = dbUtil.insertVisitIntoDB(visit);
				Log.i(TAG, "insert visit into o/p table retVal: " + retVal);
				if (retVal != Util.ERROR_RETURN_VAL) {
					retVal = dbUtil.updateInputTable(visit.getTask_id());
					Log.i(TAG, "update status of i/p table : " + retVal);

					backToMainActivity(true);
				}

			} else if (obj instanceof WLTask) {
				// Visit visit = new Visit();
				WLTask wlTask = (WLTask) obj;
				visit.setCaf_no(wlTask.getCaf_no());
				visit.setTask_id(wlTask.getTask_id());
				visit.setReav_flag(wlTask.getReav_flag());
				visit.setOfr_visit(wlTask.getOfr_visit());
				visit.setUser_name(wlTask.getUser_name());
				visit.setUser_fname(wlTask.getUser_fname());
				visit.setUser_lname(wlTask.getUser_lname());
				visit.setVersion_id(Util.VERSION_ID);
				visit.setActivity_type(wlTask.getActivity_type());
				visit.setCluster_name(wlTask.getCluster_name());

				setVisitPOJO(visit);
				// String visitJson = getVisistJSON(visit);
				long retVal = dbUtil.insertVisitIntoDB(visit);
				Log.i(TAG, "insert visit into o/p table retVal: " + retVal);
				if (retVal != Util.ERROR_RETURN_VAL) {
					retVal = dbUtil.updateInputTable(visit.getTask_id());
					Log.i(TAG, "update status of i/p table : " + retVal);

					backToMainActivity(false);
				}
			}
			break;
		default:
			break;
		}
	}

	private void backToMainActivity(boolean isFreshTask) {

		DataLoader.IS_TASK_COMPLETED = true;
		Intent main = new Intent(getApplicationContext(), MainActivityNew.class);
		Log.i(TAG, "DataLoader.CLICKED_ITEM_POSITION : "
				+ DataLoader.CLICKED_ITEM_POSITION);
		if (isFreshTask) {
			Log.i(TAG, "DataLoader.freshTaskList.size() : "
					+ DataLoader.freshTaskList.size());
			DataLoader.freshTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
			main.putExtra(Util.CHILD_ITEM, Util.FRESH);
		} else {
			Log.i(TAG, "DataLoader.wlTaskList.size() : "
					+ DataLoader.wlTaskList.size());
			DataLoader.wlTaskList.remove(DataLoader.CLICKED_ITEM_POSITION);
			main.putExtra(Util.CHILD_ITEM, Util.PENDING);
		}

		main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(main);

		Util.showToast(getString(R.string.taskCaptured), this, true);
		finish();
	}

	private void capturePhoto(PHOTO_TYPE type) {

		Uri imageUri = getImageStoreLocation(type);
		Util.IMAGE_FILE_PATH = imageUri.getPath();
		Log.i(TAG, "capturePhoto : Util.IMAGE_FILE_PATH : "
				+ Util.IMAGE_FILE_PATH);
		// ///capture image/////
		Intent cameraIntent = new Intent();
		cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		try {
			switch (type) {
			case PROOF_ID_PHOTO:
				startActivityForResult(cameraIntent,
						Util.PROOF_ID_PHOTO_REQUEST_CODE);

				// cropImage imagePath :
				// /storage/sdcard0/doco/2665_3_0_proof_id.png

				break;
			case ADDRESS_PHOTO:
				startActivityForResult(cameraIntent,
						Util.ADDRESS_PHOTO_REQUEST_CODE);
				break;
			case HOUSE_PHOTO:
				startActivityForResult(cameraIntent,
						Util.HOUSE_PHOTO_REQUEST_CODE);
				break;
			case DOCUMENT_ID_PHOTO:
				startActivityForResult(cameraIntent,
						Util.DOCUMENT_PHOTO_REQUEST_CODE);
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

	private Uri getImageStoreLocation(PHOTO_TYPE type) {

		File docoImageDir = null;
		File docoImage = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			Log.i(TAG, "External storage mounted........");
			/*
			 * 02-11 00:48:50.373: I/CaptureIndividualRequest(1041):
			 * capturePhoto : imageUri : file:///storage/sdcard/doco/doco.jpg
			 */

			docoImageDir = new File(Environment.getExternalStorageDirectory()
					+ Util.IMAGE_FOLDER);

			try {
				if (!docoImageDir.exists()) {
					docoImageDir.mkdir();
				}
				switch (type) {
				case PROOF_ID_PHOTO:

					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-proof_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setProofId(docoImage.getName());
					break;

				case ADDRESS_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-address_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setAddressId(docoImage.getName());
					break;
				case HOUSE_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-house_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setHouseId(docoImage.getName());
					break;
				case DOCUMENT_ID_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-document_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
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
			Log.i(TAG, "Internal storage present in device........");

			docoImageDir = new File(getFilesDir() + Util.IMAGE_FOLDER);

			try {
				if (!docoImageDir.exists()) {
					docoImageDir.mkdir();
				}

				switch (type) {
				case PROOF_ID_PHOTO:

					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-proof_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setProofId(docoImage.getName());
					break;

				case ADDRESS_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-address_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setAddressId(docoImage.getName());
					break;
				case HOUSE_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-house_photo-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
					docoImage.createNewFile();
					visit.setHouseId(docoImage.getName());
					break;
				case DOCUMENT_ID_PHOTO:
					docoImage = new File(docoImageDir, visit.getTask_id() + "-"
							+ visit.getOfr_visit() + "-" + visit.getReav_flag()
							+ "-document_id-" + visit.getCaf_no() + "-"
							+ visit.getCluster_name() + Util.IMAGE_EXTENSION);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.i(TAG, "cropImage : " + cropImage + " -- requestCode : "
				+ requestCode + " -- resultCode : " + resultCode);

		if (resultCode == Activity.RESULT_OK) {

			// Uri imageUri = data.getData();
			// Log.i(TAG, "Photo path : " + imageUri);

			try {
				if (requestCode == Util.PROOF_ID_PHOTO_REQUEST_CODE) {
					/*
					 * Uri imageUri = data.getData(); Log.i(TAG, "Photo path : "
					 * + imageUri);
					 */

					cropImage(Util.IMAGE_FILE_PATH,
							Util.PHOTO_TYPE.PROOF_ID_PHOTO);

				} else if (requestCode == Util.ADDRESS_PHOTO_REQUEST_CODE) {
					cropImage(Util.IMAGE_FILE_PATH,
							Util.PHOTO_TYPE.ADDRESS_PHOTO);

				} else if (requestCode == Util.HOUSE_PHOTO_REQUEST_CODE) {
					cropImage(Util.IMAGE_FILE_PATH, Util.PHOTO_TYPE.HOUSE_PHOTO);

				} else if (requestCode == Util.DOCUMENT_PHOTO_REQUEST_CODE) {
					cropImage(Util.IMAGE_FILE_PATH,
							Util.PHOTO_TYPE.DOCUMENT_ID_PHOTO);

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
				 */else if (requestCode == Util.PROOF_ID_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(
							Util.PROOF_ID_PHOTO_REQUEST_CODE_CROP, data);

				} else if (requestCode == Util.ADDRESS_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(
							Util.ADDRESS_PHOTO_REQUEST_CODE_CROP, data);

				} else if (requestCode == Util.HOUSE_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(Util.HOUSE_PHOTO_REQUEST_CODE_CROP,
							data);

				} else if (requestCode == Util.DOCUMENT_PHOTO_REQUEST_CODE_CROP) {
					showCroppedImagePreview(
							Util.DOCUMENT_PHOTO_REQUEST_CODE_CROP, data);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (resultCode == 1 && requestCode == 5) {
			Log.i(TAG, "inside signature result code ........");
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
								+ Util.IMAGE_FOLDER);
			} else {
				docoImageDir = new File(getFilesDir() + Util.IMAGE_FOLDER);
			}
			FileOutputStream fos = null;
			try {
				if (!docoImageDir.exists()) {
					docoImageDir.mkdir();
				}

				signFileName = visit.getTask_id() + "-" + visit.getOfr_visit()
						+ "-" + visit.getReav_flag() + "-customer_sign-"
						+ visit.getCaf_no() + "-" + visit.getCluster_name()
						+ Util.IMAGE_EXTENSION;

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
					Log.e(TAG, "" + e.getMessage());
				}
			}

			// Util.base64PhotoSign = getImageBase64(b);
			Util.base64PhotoSign = signFileName;
		}
	}

	private Uri returnURI(Intent data) {

		return Uri.parse(data.getStringExtra(CropImage.IMAGE_PATH));
	}

	private void cropImage(String imagePath, PHOTO_TYPE type) {

		Log.i(TAG, "cropImage imagePath : " + imagePath);
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
						Util.PROOF_ID_PHOTO_REQUEST_CODE_CROP);
				break;
			case ADDRESS_PHOTO:
				startActivityForResult(cropIntent,
						Util.ADDRESS_PHOTO_REQUEST_CODE_CROP);
				break;
			case HOUSE_PHOTO:
				startActivityForResult(cropIntent,
						Util.HOUSE_PHOTO_REQUEST_CODE_CROP);
				break;
			case DOCUMENT_ID_PHOTO:
				startActivityForResult(cropIntent,
						Util.DOCUMENT_PHOTO_REQUEST_CODE_CROP);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			cropImage = false;
			e.printStackTrace();
		}
	}

	private void showCroppedImagePreview(int requestCode, Intent data) {

		Uri imageUri = returnURI(data);
		Log.i(TAG, "showCroppedImagePreview : " + imageUri);

		try {
			Bitmap croppedBitmap = BitmapFactory.decodeFile(imageUri.getPath());
			// bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			// croppedBitmap.createScaledBitmap(croppedBitmap, 300, 300, true);

			// croppedBitmap.setHasAlpha(true);
			// croppedBitmap =
			// MediaStore.Images.Media.getBitmap(getContentResolver(), imagUri);
			// commonImageView.setImageBitmap(croppedBitmap);

			// BitmapDrawable drawable = new BitmapDrawable(getResources(),
			// croppedBitmap);

			if (requestCode == Util.PROOF_ID_PHOTO_REQUEST_CODE_CROP) {

				imgProofId.setImageBitmap(croppedBitmap);

				// idImageView.setBackgroundDrawable(drawable);
				Util.base64PhotoProofId = getImageBase64(croppedBitmap);
				Util.base64PhotoProofId = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_proof_id" + Util.IMAGE_EXTENSION;
				// Log.i(TAG, "Util.base64PhotoId : " + Util.base64PhotoId);
			} else if (requestCode == Util.ADDRESS_PHOTO_REQUEST_CODE_CROP) {
				imgAddressId.setImageBitmap(croppedBitmap);
				Util.base64PhotoAdd = getImageBase64(croppedBitmap);
				Util.base64PhotoAdd = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_address_photo" + Util.IMAGE_EXTENSION;
				// Log.i(TAG, "Util.base64PhotoId : " + Util.base64PhotoId);
			} else if (requestCode == Util.HOUSE_PHOTO_REQUEST_CODE_CROP) {
				imgHousePhoto.setImageBitmap(croppedBitmap);
				Util.base64PhotoHouse = getImageBase64(croppedBitmap);
				Util.base64PhotoHouse = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_house_photo" + Util.IMAGE_EXTENSION;
				// Log.i(TAG, "Util.base64PhotoId : " + Util.base64PhotoId);
			} else if (requestCode == Util.DOCUMENT_PHOTO_REQUEST_CODE_CROP) {
				imgDocId.setImageBitmap(croppedBitmap);

				Util.base64PhotoDocument = getImageBase64(croppedBitmap);

				Util.base64PhotoDocument = visit.getTask_id() + "_"
						+ visit.getVisit_id() + "_" + visit.getReav_flag()
						+ "_document_id" + Util.IMAGE_EXTENSION;
				// Log.i(TAG, "Util.base64PhotoId : " + Util.base64PhotoId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getImageBase64(Bitmap bmp) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 0, stream);
		byte[] bs = stream.toByteArray();

		// imageBase64 = Base64.encodeToString(bs, Base64.DEFAULT);
		return Base64.encodeToString(bs, Base64.DEFAULT);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		Log.i(TAG, "onItemClick : " + position);
		relationship = adapterView.getItemAtPosition(position).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

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
