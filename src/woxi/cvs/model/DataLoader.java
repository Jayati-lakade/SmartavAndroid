package woxi.cvs.model;

import java.util.ArrayList;

public class DataLoader {

	public static ArrayList<FreshTask> freshTaskList = new ArrayList<FreshTask>();
	public static ArrayList<WLTask> wlTaskList = new ArrayList<WLTask>();
	public static ArrayList<BulkTask> bulkTaskList = new ArrayList<BulkTask>();
	
	public static int CLICKED_ITEM_POSITION = -100;		//invalid item position
	public static final int INVALID_ITEM_POSITION = -100;
	public static boolean IS_TASK_COMPLETED = false;
}
