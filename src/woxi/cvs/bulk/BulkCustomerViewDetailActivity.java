package woxi.cvs.bulk;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import woxi.cvs.R;
import woxi.cvs.adapter.CustomAdapterBulkDetails;
import woxi.cvs.constants.ConstantSmartAV;
import woxi.cvs.db.DBUtil;
import woxi.cvs.fragments.Interface.PaginationInterface;
import woxi.cvs.model.BulkCustomer;
import woxi.cvs.model.BulkTask;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class BulkCustomerViewDetailActivity extends Activity implements PaginationInterface{

	ListView bulkCustomerListView;
	// CustomAdapter adapter;
	CustomAdapterBulkDetails bulkCustomerTextAdapter;

	public  BulkCustomerViewDetailActivity customListView = null;
	public LinkedHashSet<BulkCustomer> bulkCustomerSet = new LinkedHashSet<BulkCustomer>();
	public ArrayList<BulkCustomer> bulkCustomerList = new ArrayList<BulkCustomer>();
	private LinearLayout paginationLinearScroll;
	private ArrayList<BulkCustomer> paginationBulkCustomerList;
	private static final int paginationRowLimit = ConstantSmartAV.PAGINATION_LIMIT; //Set the limit of Pagination Size

	public static int count = 0;
	private TextView accountNoText, mobileNotext, fullnameText,	designationText;;
	private BulkTask bulkTask;
	private DBUtil dbUtil;
	private Resources res;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		paginationBulkCustomerList = new ArrayList<BulkCustomer>();	
		dbUtil = new DBUtil(getApplicationContext());
		setContentView(R.layout.bulkdataview_list_view);
		fullnameText = (TextView) findViewById(R.id.fullNameText);
		designationText = (TextView) findViewById(R.id.designationText);
		mobileNotext = (TextView) findViewById(R.id.mobileNoText);
		accountNoText = (TextView) findViewById(R.id.accountNoText);
		customListView = this;
		paginationLinearScroll = (LinearLayout) findViewById(R.id.linear_scroll);
		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		res = getResources();
		bulkCustomerListView = (ListView) findViewById(R.id.bulklistview);
		bulkTask = (BulkTask) getIntent().getExtras().get("task");
		bulkCustomerSet = dbUtil.searchBulKTask(Integer.valueOf(bulkTask.getBulk_id()));
		bulkCustomerList = getBulkCustomerValuesList(bulkCustomerSet);
		int size = bulkCustomerSet.size()/paginationRowLimit;
		for (int j = 0; j < size; j++) {
			final int k;
			k = j;
			Button nextBtnPage = new Button(BulkCustomerViewDetailActivity.this);
			LayoutParams lp = new LinearLayout.LayoutParams(120,
					LayoutParams.WRAP_CONTENT);
			lp.setMargins(5, 2, 2, 2);
			nextBtnPage.setTextColor(Color.BLACK);
			nextBtnPage.setTextSize(26.0f);
			nextBtnPage.setId(j);
			nextBtnPage.setText(String.valueOf(j + 1));
			paginationLinearScroll.addView(nextBtnPage, lp);

			nextBtnPage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				onNextPagebuttonClick(k);
				}
			});
		}		
		
		
		
		/**************** Create Custom Adapter *********/
		bulkCustomerTextAdapter = new CustomAdapterBulkDetails(customListView,
				getBulkCustomerValuesList(bulkCustomerSet), res);
		bulkCustomerListView.setAdapter(bulkCustomerTextAdapter);

	}

	@Override
	public void onNextPagebuttonClick(int posStart) {
		
		paginationBulkCustomerList.clear();
		posStart = posStart*paginationRowLimit;
		for(int i = 0; i<paginationRowLimit;i++)
		{
			paginationBulkCustomerList.add(i,bulkCustomerList.get(posStart));
			posStart = posStart+1;
		}		
		
		bulkCustomerTextAdapter = new CustomAdapterBulkDetails(customListView,
				paginationBulkCustomerList, res);
		bulkCustomerListView.setAdapter(bulkCustomerTextAdapter);
		}
	
	private ArrayList getBulkCustomerValuesList(LinkedHashSet<BulkCustomer> customListViewValuesSet) {
		if( bulkCustomerSet!=null){

			bulkCustomerList.clear();
			System.gc();
			bulkCustomerList =new ArrayList<BulkCustomer>(customListViewValuesSet);
			return bulkCustomerList;
		}
		return bulkCustomerList;
	}

}
