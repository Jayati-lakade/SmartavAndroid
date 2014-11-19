package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BulkTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;
//	private int task_id;
	private int bulk_id;
	private String caf_no;
	private String telephone_no;
//	private String cust_name;
	private String address;
	private String no_of_dels_under_account;
	private HashMap<Integer, BulkCustomer> bulkTaskCustomers = new HashMap<Integer, BulkCustomer>();

	private String mobileNo;
	private String fullName;
	private String designation;
	private String company_name;
	private ArrayList<Visit> visitList;// = new ArrayList<Visit>();
	private String alternate_contact_no;
	private String activity_type;
	private String cust_landmark;
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



	public String getCust_landmark() {
		return cust_landmark;
	}

	public void setCust_landmark(String cust_landmark) {
		this.cust_landmark = cust_landmark;
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

	public String getCaf_no() {
		return caf_no;
	}

	public void setCaf_no(String caf_no) {
		this.caf_no = caf_no;
	}

	public String getTelephone_no() {
		return telephone_no;
	}

	public void setTelephone_no(String telephone_no) {
		this.telephone_no = telephone_no;
	}

	public String getAlternate_contact_no() {
		return alternate_contact_no;
	}

	public void setAlternate_contact_no(String alternate_contact_no) {
		this.alternate_contact_no = alternate_contact_no;
	}

//	public String getCust_name() {
//		return cust_name;
//	}
//
//	public void setCust_name(String cust_name) {
//		this.cust_name = cust_name;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNo_of_dels_under_account() {
		return no_of_dels_under_account;
	}

	public void setNo_of_dels_under_account(String no_of_dels_under_account) {
		this.no_of_dels_under_account = no_of_dels_under_account;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public ArrayList<Visit> getVisitList() {
		return visitList;
	}

	public void setVisitList(ArrayList<Visit> visitList) {
		this.visitList = visitList;
	}

//	@Override
//	public String toString() {
//		return this.getCaf_no() + "," + this.getCust_name() + ","
//				+ this.getAddress();
//	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}

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
