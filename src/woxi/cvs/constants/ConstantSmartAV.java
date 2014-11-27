package woxi.cvs.constants;

import java.text.SimpleDateFormat;

public class ConstantSmartAV {

	/*Pagination Constants : START*/	
	public static int PAGINATION_LIMIT= 6;
	/*Pagination Constants : END*/
	public static String PREFERENCES = "cvsPrefs";
	public static String USERNAME_STR = "USERNAME";
	public static String PASSWORD_STR = "PASSWORD";

	public static String INPUT_JSON_URL = "http://www.universalmanagement.co.in/alpha/ajax/tablet/getInputTasklist1.php?";
	public static String OUTPUT_UPLOAD_DATA_URL = "http://www.universalmanagement.co.in/alpha/ajax/tablet/outputUploadTask.php";
	public static String OUTPUT_UPLOAD_IMAGE_URL = "http://www.universalmanagement.co.in/alpha/ajax/tablet/outputUploadImage.php";
	/*
	  public static String INPUT_JSON_URL = "http://www.universalmanagement.co.in/ajax/tablet/getInputTasklist.php?";
	  public static String OUTPUT_UPLOAD_DATA_URL ="http://www.universalmanagement.co.in/ajax/tablet/outputUploadTask.php";
	  public static String OUTPUT_UPLOAD_IMAGE_URL ="http://www.universalmanagement.co.in/ajax/tablet/outputUploadImage.php";
	 */

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

	/* Task Categories : START */
	public static String FRESHTASK = "FRESH";
	public static String WLTASK = "WL";
	public static String BulkTASK = "BULK";
	public static String BulkCUSTOMER = "BULK_CUSTOMER";

	/* Task Categories : END */

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
	
}
