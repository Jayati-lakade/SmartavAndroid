package woxi.cvs.model;



public class BulkCustomer {

	private String accountNo;
	private String mobileNo;
	private String fullName;
	private String designation;
	private int bulk_id;
	private int index;

	public BulkCustomer() {
		super();
	}

	public BulkCustomer(int index,String accountNo, String mobileNo, String fullName,
			String designation) {
		super();
		this.index = index;
		this.accountNo = accountNo;
		this.mobileNo = mobileNo;
		this.fullName = fullName;
		this.designation = designation;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index2) {
		this.index = index2;
	}

//	public HashMap<String, BulkCustomer> getBulkCustomerMap() {
//		return bulkCustomerMap;
//	}
//
//	public void setBulkCustomerMap(HashMap<String, BulkCustomer> bulkCustomerMap) {
//		this.bulkCustomerMap = bulkCustomerMap;
//	}

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

	public int getBulk_id() {
		return bulk_id;
	}

	public void setBulk_id(int bulk_id) {
		this.bulk_id = bulk_id;
	}

//	/*
//	 * Method to get an particular Customer to edit from a List of Customers
//	 * already added
//	 */
//	public BulkCustomer findBulkCustomerByMobileNo(int index) {
//		return bulkCustomerMap.get(index);
//	}
//
//	/*
//	 * Method to add an customer in the List. Return Parameters : True : If
//	 * customer is not present with same mobile number can be added
//	 * successfully. False :If Mobile Number already exists Customer cannot be
//	 * added in a list.
//	*/
//	public boolean addBulkCustomer(BulkCustomer bulkCustomer) {
//		if (bulkCustomerMap.containsKey(bulkCustomer.getMobileNo())) {
//			return false;
//		}
//		this.bulkCustomerMap.put(bulkCustomer.getMobileNo(), bulkCustomer);
//		return true;
//	}



}
