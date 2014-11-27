package woxi.cvs.db;
/*
 * Desc:Class from creation.deletion of DB
 * Developed By:Sourabh shah
 * Version:Fresh,wl created in version 1.1.Bulk version:1.7
 * 
 */
public class DBContract {

	public enum TABLE_TYPE {
		INPUT_TABLE, OUTPUT_TABLE, BULK_TABLE
	};

	static String INPUT_TABLE_NAME = "inputTable";
	static String OUTPUT_TABLE_NAME = "outputTable";
	static String BULK_TABLE_NAME = "bulkTable";

	static String REQUEST_TYPE_FRESH = "Fresh";
	static String REQUEST_TYPE_PENDING = "Pending";
	static String REQUEST_TYPE_BULK = "Bulk";

	static String COLUMN_TASK_ID = "task_id";
	static String COLUMN_RECORD = "record";
	static String COLUMN_RECORD_TYPE = "recordType";
	static String COLUMN_RECORD_STATUS = "recordStatus";
	static String COLUMN_RECORD_DATE = "recordDate";
	static String COLUMN_PRIORITY = "priority";
	static String COLUMN_PRIORITY_ID = "priority_id";

	// Bulk table column are as follows

	static String COLUMN_BULKTASK_ID = "task_id";
	static String COLUMN_STATUS="status";


	static String CREATE_INPUT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ INPUT_TABLE_NAME + " (" + COLUMN_TASK_ID
			+ " INTEGER PRIMARY KEY, " + COLUMN_RECORD + " TEXT,"
			+ COLUMN_RECORD_TYPE + " VARCHAR(15)," + COLUMN_PRIORITY
			+ " VARCHAR(30)," + COLUMN_PRIORITY_ID + " INTEGER,"
			+ COLUMN_RECORD_STATUS + " 	BOOLEAN," + COLUMN_RECORD_DATE
			+ " DATETIME)";

	static String CREATE_OUTPUT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ OUTPUT_TABLE_NAME + " (" + COLUMN_TASK_ID
			+ " INTEGER PRIMARY KEY, " + COLUMN_RECORD + " TEXT,"
			+ COLUMN_RECORD_DATE + " DATETIME)";

	static String CREATE_BULK_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ BULK_TABLE_NAME + " (" + COLUMN_BULKTASK_ID
			+ " INTEGER, " + COLUMN_RECORD +  " TEXT,"
			+COLUMN_STATUS +  " TEXT" +")";
	
	
	static String DROP_INPUT_TABLE = "DROP TABLE IF EXISTS " + INPUT_TABLE_NAME;
	static String DROP_OUTPUT_TABLE = "DROP TABLE IF EXISTS "
			+ OUTPUT_TABLE_NAME;
	static String DROP_BULK_TABLE="DROP TABLE IF EXISTS "+BULK_TABLE_NAME;
	

	static String DELETE_INPUT_TABLE = "DELETE FROM " + INPUT_TABLE_NAME;
	static String DELETE_OUTPUT_TABLE = "DELETE FROM " + OUTPUT_TABLE_NAME;
	static String DELETE_BULK_TABLE = "DELETE FROM " + BULK_TABLE_NAME;

}
