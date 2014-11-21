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

	@Override
	public boolean equals(Object obj) {
		BulkCustomer other = (BulkCustomer) obj;
		if ((!accountNo.equals(other.accountNo)) &&(!mobileNo.equals(other.mobileNo)))
			return false;		
		return true;
	}	
}
