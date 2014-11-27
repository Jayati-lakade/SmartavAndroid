package woxi.cvs.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.db.DBUtil;
import woxi.cvs.receivers.AlarmReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;

import android.widget.Toast;

public class Util {
/*
	public static String PREFERENCES = "cvsPrefs";
	public static String USERNAME_STR = "USERNAME";
	public static String PASSWORD_STR = "PASSWORD";

	public static String INPUT_JSON_URL = "http://www.universalmanagement.co.in/alpha/ajax/tablet/getInputTasklist1.php?";
	public static String OUTPUT_UPLOAD_DATA_URL = "http://www.universalmanagement.co.in/alpha/ajax/tablet/outputUploadTask.php";
	public static String OUTPUT_UPLOAD_IMAGE_URL = "http://www.universalmanagement.co.in/alpha/ajax/tablet/outputUploadImage.php";
	/*
	  public static String INPUT_JSON_URL = "http://www.universalmanagement.co.in/ajax/tablet/getInputTasklist.php?";
	  public static String OUTPUT_UPLOAD_DATA_URL ="http://www.universalmanagement.co.in/ajax/tablet/outputUploadTask.php";
	//  public static String OUTPUT_UPLOAD_IMAGE_URL ="http://www.universalmanagement.co.in/ajax/tablet/outputUploadImage.php";
	 

	public static String VERSION_ID = "SmartAV1.6";
	public static String VERSION_STRING = "versionnotmatched";

	public static int ALARM_INTENT_REQUEST_CODE = 24;

	public static long ERROR_RETURN_VAL = -100l;
	public static String ERROR_STRING = "EXCEPTION";
	public static String SUCCESS = "SUCCESS";
	public static String NO_RECORDS_FOUND = "NO_RECORDS_FOUND";

	public static String CHILD_ITEM = "CHILD_ITEM";
	public static String FRESH = "FRESH";
	public static String PENDING = "PENDING";
	public static String TRUE = "TRUE";
	public static String FALSE = "FALSE";

	/* Task Categories : START *
	public static String FRESHTASK = "FRESH";
	public static String WLTASK = "WL";
	public static String BulkTASK = "BULK";
	public static String BulkCUSTOMER = "BULK_CUSTOMER";

	/* Task Categories : END *

	public static int TIMEOUT = 0000; // 15 seconds of timeout for http
										// connection
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static String CURRENTCUSTOMER;// To store the current customers name
											// on all screens

	public static enum PHOTO_TYPE {
		PROOF_ID_PHOTO, ADDRESS_PHOTO, HOUSE_PHOTO, DOCUMENT_ID_PHOTO
	};

	public static int SIGNATURE_RESULT_CODE = -11;

	public static int PROOF_ID_PHOTO_REQUEST_CODE = 1;
	public static int ADDRESS_PHOTO_REQUEST_CODE = 2;
	public static int HOUSE_PHOTO_REQUEST_CODE = 3;
	public static int DOCUMENT_PHOTO_REQUEST_CODE = 4;
	public static int SIGNATURE_REQUEST_CODE = 5;

	public static int PROOF_ID_PHOTO_REQUEST_CODE_CROP = 6;
	public static int ADDRESS_PHOTO_REQUEST_CODE_CROP = 7;
	public static int HOUSE_PHOTO_REQUEST_CODE_CROP = 8;
	public static int DOCUMENT_PHOTO_REQUEST_CODE_CROP = 9;

	public static String IMAGE_FOLDER = "/doco";
	public static String IMAGE_EXTENSION = ".jpg";
	public static String IMAGE_FILE_PATH;

	public static final long INTERVAL_FOR_STARTUP = 1 * 60 * 1000;
	public static final long INTERVAL_IN_MINUTES = 2 * 60 * 1000;
	public static String base64PhotoProofId, base64PhotoAdd, base64PhotoHouse,
			base64PhotoDocument, base64PhotoSign;
*/
	public static void showToast(String message, Context context,
			boolean lengthShort) {
		if (lengthShort)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		Date d = new Date();
		return dateFormat.format(d);
	}

	// <editor-fold defaultstate="collapsed" desc="Net Connectivity">
	// </editor-fold>
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public static void initiateAlarmManager(Context context) {

		AlarmManager alarmManager;
		PendingIntent alarmIntent;

		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context,
				ConstantSmartAV.ALARM_INTENT_REQUEST_CODE, intent, 0);

		alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				ConstantSmartAV.INTERVAL_FOR_STARTUP, ConstantSmartAV.INTERVAL_IN_MINUTES, alarmIntent);

	}
	/*
	 * desc:Sync the data when capture
	 * Developed By:Sourabh Shah
	 * version:1.1
	 */
	public static synchronized boolean synchroniseData(Context context) {

		DBUtil dbUtil = new DBUtil(context);
		String visitJson = dbUtil.fetchOutputData();//takes data from output table
		visitJson = encodeData(visitJson);
		if (visitJson.equals(ConstantSmartAV.NO_RECORDS_FOUND)) {
			return false;
		} else {
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(ConstantSmartAV.OUTPUT_UPLOAD_DATA_URL);//"http://www.universalmanagement.co.in/alpha/ajax/tablet/outputUploadTask.php"
				MultipartEntity reqEntity = new MultipartEntity();//upload file using multipartEntity
				reqEntity.addPart("action", new StringBody(visitJson));
				post.setEntity(reqEntity);
				HttpResponse response = client.execute(post);
				HttpEntity resEntity = response.getEntity();
				final String response_str = EntityUtils.toString(resEntity);
				if (resEntity != null) {
		
					System.out.println("Response :" + response_str);
					return true;
				} else {
					return false;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/*
	 * desc:Sync the image when capture
	 * Developed By:Sourabh Shah
	 * version:1.1
	 */
	public static synchronized boolean synchroniseImage(File file,
			Context context) {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(ConstantSmartAV.OUTPUT_UPLOAD_IMAGE_URL);
		MultipartEntity reqEntity = new MultipartEntity();
		HttpResponse response;
		HttpEntity resEntity;
		File docoImageDir = null;
		File[] files = null;
		File f;
		int fileCounter = 0;
		boolean fileDeleteStatus;
		WeakHashMap<String, File> fileMap = new WeakHashMap<String, File>();
		int fileLength = 0;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			docoImageDir = new File(Environment.getExternalStorageDirectory()
					+ ConstantSmartAV.IMAGE_FOLDER);
		} else {
			docoImageDir = new File(file + ConstantSmartAV.IMAGE_FOLDER);
		}
		if (docoImageDir.isDirectory()) {
			files = docoImageDir.listFiles();
			fileLength = files.length;
		}
		if (fileLength > 0) {

			try {
				reqEntity.addPart("imageLength", new StringBody(""
						+ files.length));
				if (fileLength < 5) {
					fileCounter = fileLength;
				} else {
					fileCounter = 5;
				}

				for (int i = 0; i < fileCounter; i++) {
					f = files[i];
					fileMap.put(f.getName(), f);
					// Log.i("File Name", "Uploading file : "+
					// f.getName().replace(".png", ""));
					reqEntity.addPart("UploadFile" + i, new FileBody(f));
					System.out.println("Upload File mainList : " + "UploadFile"
							+ i);
				}
				reqEntity.addPart("imagesList", new StringBody(fileMap.keySet()
						.toString()));
				System.out
						.println("ImageList : " + fileMap.keySet().toString());
				post.setEntity(reqEntity);
				response = client.execute(post);
				resEntity = response.getEntity();
				String response_str = EntityUtils.toString(resEntity);
				System.out.println("ResponseString : " + response_str);

				/*
				 * File path = Environment.getExternalStoragePublicDirectory(
				 * Environment.DIRECTORY_PICTURES);
				 * System.out.println("Path 1 : " + path); if (path.exists()) {
				 * File test1 = new File(path, "100MEDIA/"); if (test1.exists())
				 * { path = test1; System.out.println("Cam Path1 :" + test1); }
				 * else { File test2 = new File(path, "100ANDRO/"); if
				 * (test2.exists()) { path = test2;
				 * System.out.println("Cam Path2 :" + test2); } else { File
				 * test3 = new File(path, "Camera/"); path = test3;
				 * System.out.println("Cam Path3 :" + test3); if
				 * (test3.isDirectory()) { File[] camFile = test3.listFiles();
				 * for (int i = 0; i < camFile.length; i++) {
				 * System.out.println("File :"+ i + " : " + camFile[i]); } } } }
				 * } else { System.out.println("Path 2 : " + path); }
				 */

				if (resEntity != null) {
			
					System.out.println("Response :" + response_str);
					response_str = response_str.replace("[", "");
					response_str = response_str.replace("]", "");
					List<String> list = new ArrayList<String>(
							Arrays.asList(response_str.split(",")));
					for (String successFile : list) {
						f = fileMap.get(successFile);
						System.out.println("File :-" + f);
						if (f != null) {
							if (f.exists()) {

								fileDeleteStatus = f.delete();
								context.sendBroadcast(new Intent(
										Intent.ACTION_MEDIA_MOUNTED,
										Uri.parse("file://"
												+ Environment
														.getExternalStorageDirectory())));

							}
						}

					}
					context.sendBroadcast(new Intent(
							Intent.ACTION_MEDIA_MOUNTED,
							Uri.parse("file://"
									+ Environment.getExternalStorageDirectory())));
					return true;
				} else {
					System.out.println("Negative Response : " + response_str);
					return false;
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public static String encodeData(String name) {
		// StringBuilder temp = new StringBuilder(name);
		// StringBuilder temp1 = new StringBuilder();
		// temp1.setLength(0);
		// temp1 = temp1.append(temp.toString().replace("{", "APPLEATE"));
		// temp.setLength(0);
		// temp = temp.append(temp1.toString().replace("}", "BALLBAT"));
		// temp1.setLength(0);
		// temp1 = temp1.append(temp.toString().replace("\"", "CATCOPY"));
		// temp.setLength(0);
		// temp = temp.append(temp1.toString().replace("{", "DOGDUMP"));
		// temp1.setLength(0);
		// temp1 = temp1.append(temp.toString().replace(":", "EAGLEEGG"));
		// temp.setLength(0);
		// temp = temp.append(temp1.toString().replace("\n", "FROG"));
		// temp1.setLength(0);
		// temp1 = temp1.append(temp.toString().replace("+", "HOUSE"));
		// temp.setLength(0);
		// temp = temp.append(temp.toString().replace("\\u", "IAMTOPPER"));
		// temp1.setLength(0);
		// temp1 = temp1.append(temp.toString().replace(",", "JACK"));
		name = name.replace("{", "APPLEATE");
		name = name.replace("}", "BALLBAT");
		name = name.replace("\"", "CATCOPY");
		name = name.replace("/", "DOGDUMP");
		name = name.replace(":", "EAGLEEGG");
		name = name.replace("\n", "FROG");
		name = name.replace("+", "HOUSE");
		name = name.replace("\\u", "IAMTOPPER");
		name = name.replace(",", "JACK");
		return name;
	}

}
