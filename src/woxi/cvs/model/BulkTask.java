package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BulkTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private int bulk_id;




	//private String no_of_dels_under_account;
	private HashMap<Integer, BulkCustomer> bulkTaskCustomers = new HashMap<Integer, BulkCustomer>();

	private String mobileNo;
	private String fullName;
	private String designation;





	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index2) {
		this.index = index2;
	}

	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}





	public BulkTask() {

	}

//	public int getTask_id() {
//		return task_id;
//	}
//
//	public void setTask_id(int task_id) {
//		this.task_id = task_id;
//	}



//	public String getCust_name() {
//		return cust_name;
//	}
//
//	public void setCust_name(String cust_name) {
//		this.cust_name = cust_name;
//	}



//	@Override
//	public String toString() {
//		return this.getCaf_no() + "," + this.getCust_name() + ","
//				+ this.getAddress();
//	}


	public boolean addBulkTask(BulkCustomer bulkCustomer) {
		if (bulkTaskCustomers.containsKey(bulkCustomer.getIndex())) {
			return false;
		}

		this.bulkTaskCustomers.put(bulkCustomer.getIndex(), bulkCustomer);
		return true;
	}

	public HashMap<Integer, BulkCustomer> getBulkTaskCustomers() {
		return bulkTaskCustomers;
	}

	public void setBulkTaskCustomers(HashMap<Integer, BulkCustomer> bulkTaskCustomers) {
		this.bulkTaskCustomers = bulkTaskCustomers;
	}

	public int getBulk_id() {
		return bulk_id;
	}

	public void setBulk_id(int bulk_id) {
		this.bulk_id = bulk_id;
	}
}
