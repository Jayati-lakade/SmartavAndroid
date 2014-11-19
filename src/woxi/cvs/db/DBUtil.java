package woxi.cvs.db;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import woxi.cvs.db.DBContract.TABLE_TYPE;
import woxi.cvs.model.BulkCustomer;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.PriorityChanger;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import woxi.cvs.util.Util;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DBUtil {

	private static Context context;
	private static SQLiteDatabase db;
	private static DBHelper dbHelper;
	private static String TAG = "DBUtil";
	// hashmap like key->value and in value again key value pair.
	private HashMap<Integer, String> bulkTaskMap = new HashMap<Integer, String>();

	public DBUtil(Context context) {
		this.context = context;
	}

	public static void initiateDB() {

		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public int deleteData(TABLE_TYPE type) {
		initiateDB();

		try {
			switch (type) {
			case INPUT_TABLE:
				return dbHelper.delete(TABLE_TYPE.INPUT_TABLE, db);
			case OUTPUT_TABLE:
				return dbHelper.delete(TABLE_TYPE.OUTPUT_TABLE, db);
			case BULK_TABLE:
				return dbHelper.delete(TABLE_TYPE.BULK_TABLE, db);
			default:
				return (int) Util.ERROR_RETURN_VAL;

			}
		} catch (Exception e) {
			Log.e(TAG + ":deleteData:", e.getLocalizedMessage());
			return (int) Util.ERROR_RETURN_VAL;
		} finally {
			db.close();
		}
	}

	// function for inserting data to DB's Bulk table

	// Here ContentValues will have the record json.and inner_index will put
	// those recodrs under 1 task id.

	public static long insertIntoBulkTable(int bulkTaskId,ArrayList<BulkCustomer> bulkCustomerList) {
		initiateDB();

		Gson gson = new Gson();
		long val = 0;
		ContentValues values = new ContentValues();

		values.put(DBContract.COLUMN_BULKTASK_ID,bulkTaskId);
		values.put(DBContract.COLUMN_RECORD, gson.toJson(bulkCustomerList));

		values.put(DBContract.COLUMN_STATUS, "null");
		val = dbHelper.insertRecord(TABLE_TYPE.BULK_TABLE, values, db);
		
		if (val != Util.ERROR_RETURN_VAL) {
		//	val = DBUtil.updateBulkTable(bulkCustomer.getBulk_id());
//			val = DBUtil.updateBulkTable("123");
		}
		
		return val;
	}

	public long insertIntoInputTable(ArrayList<FreshTask> freshTasks,
			ArrayList<WLTask> wlTasks, ArrayList<BulkTask> bulkTasks) {
		initiateDB();
		Gson gson = new Gson();
		long retVal = 0;

		db.beginTransaction();

		try {
			if (wlTasks != null) {
				Iterator<WLTask> wlIterator = wlTasks.iterator();
				while (wlIterator.hasNext()) {
					WLTask wlTask = wlIterator.next();

					ContentValues values = new ContentValues();
					values.put(DBContract.COLUMN_TASK_ID, wlTask.getTask_id());
					values.put(DBContract.COLUMN_RECORD, gson.toJson(wlTask));
					values.put(DBContract.COLUMN_RECORD_TYPE,
							DBContract.REQUEST_TYPE_PENDING);
					values.put(DBContract.COLUMN_PRIORITY, wlTask.getPriority());
					values.put(DBContract.COLUMN_PRIORITY_ID,
							wlTask.getPriority_id());
					values.put(DBContract.COLUMN_RECORD_STATUS, false);
					values.put(DBContract.COLUMN_RECORD_DATE,
							wlTask.getPriority_timestamp());
					retVal = dbHelper.insertRecord(TABLE_TYPE.INPUT_TABLE,
							values, db);
					Log.i(TAG, "insertIntoDB wlTASK : " + retVal);
				}
			}

			if (freshTasks != null) {
				Iterator<FreshTask> freshIterator = freshTasks.iterator();
				while (freshIterator.hasNext()) {
					FreshTask freshTask = freshIterator.next();

					ContentValues values = new ContentValues();
					values.put(DBContract.COLUMN_TASK_ID,
							freshTask.getTask_id());
					values.put(DBContract.COLUMN_RECORD, gson.toJson(freshTask));
					values.put(DBContract.COLUMN_RECORD_TYPE,
							DBContract.REQUEST_TYPE_FRESH);
					values.put(DBContract.COLUMN_PRIORITY,
							freshTask.getPriority());
					values.put(DBContract.COLUMN_PRIORITY_ID,
							freshTask.getPriority_id());
					values.put(DBContract.COLUMN_RECORD_STATUS, false);
					values.put(DBContract.COLUMN_RECORD_DATE,
							freshTask.getPriority_timestamp());
					retVal = dbHelper.insertRecord(TABLE_TYPE.INPUT_TABLE,
							values, db);
					Log.i("", "insertIntoDB freshTASK : " + retVal);
				}
			}

			if (bulkTasks != null) {
				Iterator<BulkTask> BulkIterator = bulkTasks.iterator();
				while (BulkIterator.hasNext()) {
					BulkTask bulkTask = BulkIterator.next();

					ContentValues values = new ContentValues();
					values.put(DBContract.COLUMN_TASK_ID, bulkTask.getTask_id());
					values.put(DBContract.COLUMN_RECORD, gson.toJson(bulkTask));
					values.put(DBContract.COLUMN_RECORD_TYPE,
							DBContract.REQUEST_TYPE_BULK);
					// values.put(DBContract.COLUMN_PRIORITY,
					// bulkTask.getPriority());
					// values.put(DBContract.COLUMN_PRIORITY_ID,
					// bulkTask.getPriority_id());
					values.put(DBContract.COLUMN_RECORD_STATUS, false);
					// values.put(DBContract.COLUMN_RECORD_DATE,bulkTask.getPriority_timestamp());
					retVal = dbHelper.insertRecord(TABLE_TYPE.INPUT_TABLE,
							values, db);
					Log.i(TAG, "insertIntoDB BulkTASK : " + retVal);
				}
			}

			db.setTransactionSuccessful();
			return retVal;
		} catch (Exception e) {
			Log.e(TAG + ":insertIntoInputTable:", e.getLocalizedMessage());
			return Util.ERROR_RETURN_VAL; // -100 indicates exception
		}

		finally {
			db.endTransaction();
			db.close();
		}

	}

	public long insertVisitIntoDB(Visit visit) {
		initiateDB();
		Gson gson = new Gson();
		long retVal = 0;
		try {
			if (visit != null) {
				ContentValues values = new ContentValues();

				values.put(DBContract.COLUMN_TASK_ID, visit.getTask_id());
				values.put(DBContract.COLUMN_RECORD, gson.toJson(visit));
				values.put(DBContract.COLUMN_RECORD_DATE, Util.getDate());
				retVal = dbHelper.insertRecord(TABLE_TYPE.OUTPUT_TABLE, values,
						db);
				Log.i("", "insertIntoDB visit : " + retVal);

				return retVal;
			} else {
				return Util.ERROR_RETURN_VAL;
			}
		} catch (Exception e) {
			Log.e(TAG + ":insertVisitIntoDB:", e.getLocalizedMessage());
			return Util.ERROR_RETURN_VAL;
		}

		finally {
			db.close();
		}
	}

	public int updateInputTable(int task_id) {
		initiateDB();
		try {
			String whereClause = DBContract.COLUMN_TASK_ID + " = ?";
			String[] whereArgs = { task_id + "" };

			ContentValues values = new ContentValues();
			values.put(DBContract.COLUMN_RECORD_STATUS, true);
			return db.update(DBContract.INPUT_TABLE_NAME, values, whereClause,
					whereArgs);
		} catch (Exception e) {
			Log.e(TAG + ":updateInputTable:", e.getLocalizedMessage());
			return (int) Util.ERROR_RETURN_VAL;
		}

		finally {
			db.close();
		}
	}

	public static int updateBulkTable(int bulk_id,ArrayList<BulkCustomer> bulkCustomerList) {
		initiateDB();
		try {
			Gson gson = new Gson();
			String whereClause = DBContract.COLUMN_BULKTASK_ID + " = ?";
			String[] whereArgs = { bulk_id + "" };

			ContentValues values = new ContentValues();
			values.put(DBContract.COLUMN_RECORD, gson.toJson(bulkCustomerList));
			values.put(DBContract.COLUMN_STATUS, "null");
			return db.update(DBContract.BULK_TABLE_NAME, values, whereClause,
					whereArgs);
		} catch (Exception e) {
			Log.e(TAG + ":updateBulkTable:", e.getLocalizedMessage());
			return (int) Util.ERROR_RETURN_VAL;
		}

		finally {
			db.close();
		}
	}

	public Object fetchInputData(String taskType) {
		initiateDB();
		String[] columns = { DBContract.COLUMN_RECORD,
				DBContract.COLUMN_PRIORITY };
		String selection = DBContract.COLUMN_RECORD_TYPE + " = ? and "
				+ DBContract.COLUMN_RECORD_STATUS + " = ?";

		try {
			if (taskType.equalsIgnoreCase(Util.FRESHTASK)) {
				String[] selectionArgs = { DBContract.REQUEST_TYPE_FRESH,
						0 + "" };
				Cursor c = db.query(DBContract.INPUT_TABLE_NAME, columns,
						selection, selectionArgs, null, null, null);
				if (c != null)
					return cursorToArrayList(c, taskType);
				else
					return null;
			} else if (taskType.equalsIgnoreCase(Util.WLTASK)) {
				String[] selectionArgs = { DBContract.REQUEST_TYPE_PENDING,
						0 + "" };
				Cursor c = db.query(DBContract.INPUT_TABLE_NAME, columns,
						selection, selectionArgs, null, null, null);
				if (c != null)
					return cursorToArrayList(c, taskType);
				else
					return null;
			} else if (taskType.equalsIgnoreCase(Util.BulkTASK)) {
				String[] selectionArgs = { DBContract.REQUEST_TYPE_BULK, 0 + "" };
				Cursor c = db.query(DBContract.INPUT_TABLE_NAME, columns,
						selection, selectionArgs, null, null, null);
				if (c != null)
					return cursorToArrayList(c, taskType);
				else
					return null;
			}

			else {
				return null;
			}
		} catch (Exception e) {
			Log.e(TAG + ":fetchInputData:", e.getLocalizedMessage());
			return null;
		} finally {
			db.close();
		}
	}

	// Used to fetch all records to change there priorities
	public int updateInputPriorities() {
		initiateDB();
		String[] columns = { DBContract.COLUMN_TASK_ID,
				DBContract.COLUMN_RECORD_DATE, DBContract.COLUMN_PRIORITY_ID,
				DBContract.COLUMN_PRIORITY };
		String selection = DBContract.COLUMN_RECORD_STATUS + " = ?";

		try {
			String[] selectionArgs = { 0 + "" };
			Cursor c = db.query(DBContract.INPUT_TABLE_NAME, columns,
					selection, selectionArgs, null, null, null);
			if (c != null)
				return cursorToArrayLisForInput(c);
			else
				return 1;

		} catch (Exception e) {
			Log.e(TAG + ":fetchInputData:", e.getLocalizedMessage());
			return 0;
		} finally {
			db.close();
		}
	}

	private Object cursorToArrayList(Cursor cursor, String taskType) {
		ArrayList<FreshTask> freshTasksLists = null;
		ArrayList<WLTask> wlTasksLists = null;
		ArrayList<BulkTask> bulkTasksLists = null;
		ArrayList<BulkCustomer> bulkCustomerLists = null;
		StringBuilder record = new StringBuilder();
		StringBuilder priority = new StringBuilder();
		Gson gson = null;
		gson = new Gson();
		if (taskType.equalsIgnoreCase(Util.FRESHTASK)) {
			freshTasksLists = new ArrayList<FreshTask>();
		} else if (taskType.equalsIgnoreCase(Util.WLTASK)) {
			wlTasksLists = new ArrayList<WLTask>();
		} else if (taskType.equalsIgnoreCase(Util.BulkTASK)) {
			bulkTasksLists = new ArrayList<BulkTask>();
		}else if (taskType.equalsIgnoreCase(Util.BulkCUSTOMER)) {
			bulkCustomerLists = new ArrayList<BulkCustomer>();
		}
		
		cursor.moveToFirst();
		int count = cursor.getCount();
		for (int i = 0; i < count; i++) {

			record.setLength(0);// Resetting record for each record.
			record = record.append(cursor.getString(cursor
					.getColumnIndex(DBContract.COLUMN_RECORD)));
			if(!taskType.equalsIgnoreCase(Util.BulkCUSTOMER)){			
			priority.setLength(0);// Resetting priority for each record.			
			priority = priority.append(cursor.getString(cursor
					.getColumnIndex(DBContract.COLUMN_PRIORITY)));
			}
			
			if (taskType.equalsIgnoreCase(Util.FRESHTASK)) {
				FreshTask freshTask = gson.fromJson(record.toString(),
						FreshTask.class);
				freshTask.setPriority(priority.toString());
				freshTasksLists.add(freshTask);
			} else if (taskType.equalsIgnoreCase(Util.WLTASK)) {
				WLTask wlTask = gson.fromJson(record.toString(), WLTask.class);
				wlTask.setPriority(priority.toString());
				wlTasksLists.add(wlTask);
			} else if (taskType.equalsIgnoreCase(Util.BulkTASK)) {
				BulkTask bulkTask = gson.fromJson(record.toString(),
						BulkTask.class);
				// bulkTask.setPriority(priority.toString());
				bulkTasksLists.add(bulkTask);
			}else if (taskType.equalsIgnoreCase(Util.BulkCUSTOMER)) {			
						Type typeToken3 = new TypeToken<ArrayList<BulkCustomer>>(){}.getType();
						return gson.fromJson(record.toString(), typeToken3);				
			}

			cursor.moveToNext();
		}
		if (taskType.equalsIgnoreCase(Util.FRESHTASK)) {
			return freshTasksLists;
		} else if (taskType.equalsIgnoreCase(Util.WLTASK)) {
			return wlTasksLists;
		} else if (taskType.equalsIgnoreCase(Util.BulkTASK)) {
			return bulkTasksLists;
		} else {
			return null;
		}

	}

	public String fetchOutputData() {
		initiateDB();
		String[] columns = { DBContract.COLUMN_RECORD };
		try {
			Cursor c = db.query(DBContract.OUTPUT_TABLE_NAME, columns, null,
					null, null, null, null);
			if (c != null) {
				return cursorToArrayLisForOutput(c);
			} else {
				return Util.ERROR_STRING;
			}
		} catch (Exception e) {
			Log.e(TAG + ":fetchOutputData:", e.getLocalizedMessage());
			return Util.ERROR_STRING;
		} finally {
			db.close();
		}
	}

	private String cursorToArrayLisForOutput(Cursor cursor) {
		try {
			int count = cursor.getCount();
			Gson gson = null;
			if (count > 0) {
				gson = new Gson();
				ArrayList<Visit> visitList = new ArrayList<Visit>();
				cursor.moveToFirst();
				StringBuilder record = new StringBuilder();
				for (int i = 0; i < count; i++) {
					record.setLength(0);// Resetting record for each record.
					record = record.append(cursor.getString(cursor
							.getColumnIndex(DBContract.COLUMN_RECORD)));
					Visit visit = gson.fromJson(record.toString(), Visit.class);
					visitList.add(visit);
					cursor.moveToNext();
				}
				Log.i(TAG, "OUTPUT JSON : " + gson.toJson(visitList));
				return gson.toJson(visitList);
			} else {
				return Util.NO_RECORDS_FOUND;
			}
		} catch (Exception e) {
			Log.e(TAG + ":cursorToArrayList:", e.getLocalizedMessage());
			return Util.ERROR_STRING;
		}
	}

	private int cursorToArrayLisForInput(Cursor cursor) {
		try {
			int count = cursor.getCount();
			if (count > 0) {
				ArrayList<PriorityChanger> priorityChangerList = new ArrayList<PriorityChanger>();
				PriorityChanger priorityChanger;
				cursor.moveToFirst();
				for (int i = 0; i < count; i++) {
					priorityChanger = new PriorityChanger();
					priorityChanger.setTask_id(cursor.getInt(cursor
							.getColumnIndex(DBContract.COLUMN_TASK_ID)));
					priorityChanger.setPriority_id(cursor.getInt((cursor
							.getColumnIndex(DBContract.COLUMN_PRIORITY_ID))));
					priorityChanger.setPriority(cursor.getString((cursor
							.getColumnIndex(DBContract.COLUMN_PRIORITY))));
					priorityChanger
							.setPriority_timestamp(cursor.getString((cursor
									.getColumnIndex(DBContract.COLUMN_RECORD_DATE))));
					priorityChangerList.add(priorityChanger);
					updateRecord(priorityChanger);
					cursor.moveToNext();
				}

				Log.i(TAG, "Priority Updation Input Size : "
						+ priorityChangerList.size());
				return 1;
			} else {
				return 1;
			}
		} catch (Exception e) {
			Log.e(TAG + ":cursorToArrayList:", e.getLocalizedMessage());
			return 0;
		}
	}

	private int updateRecord(PriorityChanger priorityChanger) {

		priorityChanger = priorityLogic(priorityChanger);
		initiateDB();
		try {
			String whereClause = DBContract.COLUMN_TASK_ID + " = ?";
			String[] whereArgs = { priorityChanger.getTask_id() + "" };

			ContentValues values = new ContentValues();
			values.put(DBContract.COLUMN_PRIORITY,
					priorityChanger.getPriority());
			values.put(DBContract.COLUMN_PRIORITY_ID,
					priorityChanger.getPriority_id());
			return db.update(DBContract.INPUT_TABLE_NAME, values, whereClause,
					whereArgs);
		} catch (Exception e) {
			Log.e(TAG + ":updateInputTable:", e.getLocalizedMessage());
			return (int) Util.ERROR_RETURN_VAL;
		} finally {
			db.close();
		}

	}

	private PriorityChanger priorityLogic(PriorityChanger priorityChanger) {

		String pTime = priorityChanger.getPriority_timestamp();
		int reavflag = 0;
		if (priorityChanger.getPriority_id() <= 11
				&& (priorityChanger.getPriority_id() >= 9)) {
			reavflag = 1;
		}
		if (priorityChanger.getPriority_id() <= 18
				&& (priorityChanger.getPriority_id() >= 16)) {
			reavflag = 2;
		}
		// String priority = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dataTime = null, currentTime = null;

		try {
			dataTime = dateFormat.parse(pTime);
			currentTime = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = (currentTime.getTime() - dataTime.getTime()) / 1000;

		if (reavflag == 0) {
			diff = 172800 - diff; // remaning timestamp
			if (diff < 0) { // if Diff is less than or equal to zero => Deadline
							// Missed
				priorityChanger.setPriority("DEADLINE MISSED");
				priorityChanger.setPriority_id(4);
			} else if (diff <= 43200 && diff > 0) { // if Diff is less than or
													// equal to 43200 and
													// greater than 0 =>
													// critical
				priorityChanger.setPriority("CRITICAL");
				priorityChanger.setPriority_id(4);
			} else if (diff <= 86400 && diff > 43200) { // if Diff is less than
														// or equal to 86400 and
														// greater than 43200 =>
														// high
				priorityChanger.setPriority("HIGH");
				priorityChanger.setPriority_id(2);
			} else if (diff <= 172800 && diff > 86400) { // if Diff is less than
															// or equal to
															// 172800 and
															// greater than
															// 86400 => Low
				priorityChanger.setPriority("LOW");
				priorityChanger.setPriority_id(1);
			}
		} else if (reavflag == 1) {
			diff = 86400 - diff; // remaning timestamp
			if (diff < 0) { // if Diff is less than or equal to zero => Deadline
							// Missed
				priorityChanger.setPriority("DEADLINE MISSED");
				priorityChanger.setPriority_id(11);
			} else if (diff <= 43200 && diff > 0) { // if Diff is less than or
													// equal to 43200 and
													// greater than 0 =>
													// critical
				priorityChanger.setPriority("CRITICAL");
				priorityChanger.setPriority_id(10);
			} else if (diff <= 86400 && diff > 43200) { // if Diff is less than
														// or equal to 86400 and
														// greater than 43200 =>
														// Low
				priorityChanger.setPriority("HIGH");
				priorityChanger.setPriority_id(9);
			}
		} else if (reavflag == 2) {
			diff = 86400 - diff; // remaning timestamp
			if (diff < 0) { // if Diff is less than or equal to zero => Deadline
							// Missed
				priorityChanger.setPriority("DEADLINE MISSED");
				priorityChanger.setPriority_id(18);
			} else if (diff <= 43200 && diff > 0) { // if Diff is less than or
													// equal to 43200 and
													// greater than 0 =>
													// critical
				priorityChanger.setPriority("CRITICAL");
				priorityChanger.setPriority_id(17);
			} else if (diff <= 86400 && diff > 43200) { // if Diff is less than
														// or equal to 86400 and
														// greater than 43200 =>
														// Low
				priorityChanger.setPriority("HIGH");
				priorityChanger.setPriority_id(16);
			}
		}

		return priorityChanger;
	}

	public  ArrayList<BulkCustomer> searchBulKTask(int bulk_id) {	
		initiateDB();
		Cursor cursor= dbHelper.readRecordData(TABLE_TYPE.BULK_TABLE, db, bulk_id);
		ArrayList<BulkCustomer> bulkCustomers = new ArrayList<BulkCustomer>();
		bulkCustomers = (ArrayList<BulkCustomer>) cursorToArrayList(cursor,Util.BulkCUSTOMER);
		return bulkCustomers;
	}
	
	public  int searchBulKTaskCustomers(int bulk_id) {	
		initiateDB(); 
		return dbHelper.readRecordData(TABLE_TYPE.BULK_TABLE, db, bulk_id).getCount();
		
	}
}
