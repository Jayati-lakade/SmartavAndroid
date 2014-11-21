package woxi.cvs.db;

import static woxi.cvs.db.DBContract.BULK_TABLE_NAME;
import static woxi.cvs.db.DBContract.COLUMN_RECORD;
import static woxi.cvs.db.DBContract.COLUMN_TASK_ID;
import static woxi.cvs.db.DBContract.CREATE_BULK_TABLE;
import static woxi.cvs.db.DBContract.CREATE_INPUT_TABLE;
import static woxi.cvs.db.DBContract.CREATE_OUTPUT_TABLE;
import static woxi.cvs.db.DBContract.DROP_BULK_TABLE;
import static woxi.cvs.db.DBContract.DROP_INPUT_TABLE;
import static woxi.cvs.db.DBContract.DROP_OUTPUT_TABLE;
import static woxi.cvs.db.DBContract.INPUT_TABLE_NAME;
import static woxi.cvs.db.DBContract.OUTPUT_TABLE_NAME;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.db.DBContract.TABLE_TYPE;
import woxi.cvs.util.Util;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static String DATABASE_NAME = "cvs.db";
	private static int DATABASE_VERSION = 1;
	private static String TAG = "DBHelper";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "onCreate DBHelper");
		
		db.execSQL(CREATE_INPUT_TABLE);
		db.execSQL(CREATE_OUTPUT_TABLE);
		db.execSQL(CREATE_BULK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL(DROP_INPUT_TABLE);
		db.execSQL(DROP_OUTPUT_TABLE);
		db.execSQL(DROP_BULK_TABLE);
		onCreate(db);
	}

	
	public int delete(TABLE_TYPE type,SQLiteDatabase db) {
		switch (type) {
		case INPUT_TABLE:
			return db.delete(INPUT_TABLE_NAME, null, null);
		case OUTPUT_TABLE:
			return db.delete(OUTPUT_TABLE_NAME, null, null);
		case BULK_TABLE:return db.delete(BULK_TABLE_NAME, null, null);
			
		default:
			return (int) ConstantSmartAV.ERROR_RETURN_VAL; // -100 indicates error
		}
	}
	
	public long insertRecord(TABLE_TYPE type, ContentValues values,
			SQLiteDatabase db) {
		switch (type) {
		case INPUT_TABLE:
			return db.insert(INPUT_TABLE_NAME, null, values);
		case OUTPUT_TABLE:
			return db.insert(OUTPUT_TABLE_NAME, null, values);
			
		case BULK_TABLE:return db.insert(BULK_TABLE_NAME,null,values);
		default:
			return ConstantSmartAV.ERROR_RETURN_VAL; // -100 indicates error
		}
	}

	public Cursor readRecordData(TABLE_TYPE type, SQLiteDatabase db, int task_id) {
		String[] cols = { COLUMN_RECORD };
		String sel = COLUMN_TASK_ID + "=?";
		String[] selArgs = { task_id + "" };
		switch (type) {
		case INPUT_TABLE:
			return db.query(INPUT_TABLE_NAME, cols, sel, selArgs, null, null,
					null);
		case OUTPUT_TABLE:
			return db.query(OUTPUT_TABLE_NAME, cols, sel, selArgs, null, null,
					null);
			
		case BULK_TABLE:return db.query(BULK_TABLE_NAME, cols, sel, selArgs, null, null, null);
		default:
			return null; 
		}
	}
	
	
	
	
}
