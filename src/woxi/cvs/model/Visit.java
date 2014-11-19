package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Visit implements Serializable {

	private String caf_no;
	private int task_id;
	private int visit_id;
	private String personMet = "";
	private String personName = "";
	private String verification_timestamp = "";
	private String relationship = "";
	/*Images Variable :Start*/
	private String customerSign = "";
	private String proofId = "";
	private String addressId = "";
	private String houseId = "";
	private String documentId = "";
	/*Images Variable :End*/
	private String remark = "";
	private String appointment = "";
	private String appointmentTimeDate = "";
	// Decision Making Data
	private String addressTraced = "NO";
	private String callCustome = "NO";
	private String callSpoc = "NO";
	private String homeLocked = "NO";
	private String callForAppointment = "NO";
	private String noOfLines = "Not Confirm";
	private String plan = "Not Confirm";
	private String product = "Not Confirm";
	private String hardwareDel = "Not Received";
	private String businssType;
	private String occupancy;
	private String accomodation;
	private String area;
	private String negativeShortReason;
	private String customerStayAddressStatus;
	private String welcomeLetterStatus = "";
	private String statusAVOutcome = "";
	private String reav_flag;
	private String houseType="";
	private String visitAt;
	private String ofr_visit;
	private String agencyName;
	private String longitude;
	private String latitude;
	private String user_name;
	private String user_fname;
	private String user_lname;
	private String activity_type;
	private String cust_landmark;
	private String cust_alternate_contact_no;
	private String cust_email_id;
	private String version_id;
	private String cluster_name;
	private int    taskFlag;


	public int getTaskFlag() {
		return taskFlag;
	}

	public int setTaskFlag(int taskFlag) {
		 return this.taskFlag = taskFlag;
	}



	private ArrayList<String>comments;  //Used for Visit History details  :Comments 
	public Visit() {
		super();
	}

	public String getVersion_id() {
		return version_id;
	}

	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}

	public String getCluster_name() {
		return cluster_name;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public String getCaf_no() {
		return caf_no;
	}

	public void setCaf_no(String caf_no) {
		this.caf_no = caf_no;
	}

	public String getPersonMet() {
		return personMet;
	}

	public void setPersonMet(String personMet) {
		this.personMet = personMet;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getVerification_timestamp() {
		return verification_timestamp;
	}

	public void setVerification_timestamp(String verification_timestamp) {
		this.verification_timestamp = verification_timestamp;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getCustomerSign() {
		return customerSign;
	}

	public void setCustomerSign(String customerSign) {
		this.customerSign =customerSign;
	}

	public String getProofId() {
		return proofId;
	}

	public void setProofId(String proofId) {
		this.proofId=proofId ;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getAppointmentTimeDate() {
		return appointmentTimeDate;
	}

	public void setAppointmentTimeDate(String appointmentTimeDate) {
		this.appointmentTimeDate = appointmentTimeDate;
	}

	public String getAddressTraced() {
		return addressTraced;
	}

	public void setAddressTraced(String addressTraced) {
		this.addressTraced = addressTraced;
	}
	
	public String getNoOfLines() {
		return noOfLines;
	}

	public void setNoOfLines(String noOflines) {
		this.noOfLines = noOflines;
	}
	
	public String getplan() {
		return plan;
	}

	public void setplan(String plan) {
		this.plan = plan;
	}
	
	public String getproduct() {
		return product;
	}

	public void setproduct(String product) {
		this.product = product;
	}
	
	public String gethardwareDel() {
		return hardwareDel;
	}

	public void sethardwareDel(String hardwareDel) {
		this.hardwareDel = hardwareDel;
	}

	public String getCallCustome() {
		return callCustome;
	}

	public void setCallCustome(String callCustome) {
		this.callCustome = callCustome;
	}

	public String getCallSpoc() {
		return callSpoc;
	}

	public void setCallSpoc(String callSpoc) {
		this.callSpoc = callSpoc;
	}

	public String getHomeLocked() {
		return homeLocked;
	}

	public void setHomeLocked(String homeLocked) {
		this.homeLocked = homeLocked;
	}

	public String getCallForAppointment() {
		return callForAppointment;
	}

	public void setCallForAppointment(String callForAppointment) {
		this.callForAppointment = callForAppointment;
	}

	public String getBusinssType() {
		return businssType;
	}

	public void setBusinssType(String businssType) {
		this.businssType = businssType;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	public String getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(String accomodation) {
		this.accomodation = accomodation;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getNegativeShortReason() {
		return negativeShortReason;
	}

	public void setNegativeShortReason(String negativeShortReason) {
		this.negativeShortReason = negativeShortReason;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getVisit_id() {
		return visit_id;
	}

	public void setVisit_id(int visit_id) {
		this.visit_id = visit_id;
	}

	public String getCustomerStayAddressStatus() {
		return customerStayAddressStatus;
	}

	public void setCustomerStayAddressStatus(String customerStayAddressStatus) {
		this.customerStayAddressStatus = customerStayAddressStatus;
	}

	public String getWelcomeLetterStatus() {
		return welcomeLetterStatus;
	}

	public void setWelcomeLetterStatus(String welcomeLetterStatus) {
		this.welcomeLetterStatus = welcomeLetterStatus;
	}

	public String getStatusAVOutcome() {
		return statusAVOutcome;
	}

	public void setStatusAVOutcome(String statusAVOutcome) {
		this.statusAVOutcome = statusAVOutcome;
	}

	public String getReav_flag() {
		return reav_flag;
	}

	public void setReav_flag(String reav_flag) {
		this.reav_flag = reav_flag;
	}
	

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	

	public String getVisitAt() {
		return visitAt;
	}

	public void setVisitAt(String visitAt) {
		this.visitAt = visitAt;
	}
	

	public String getOfr_visit() {
		return ofr_visit;
	}

	public void setOfr_visit(String ofr_visit) {
		this.ofr_visit = ofr_visit;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}
	

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_fname() {
		return user_fname;
	}

	public void setUser_fname(String user_fname) {
		this.user_fname = user_fname;
	}

	public String getUser_lname() {
		return user_lname;
	}

	public void setUser_lname(String user_lname) {
		this.user_lname = user_lname;
	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}

	public String getLandmark() {
		return cust_landmark;
	}

	public void setLandmark(String cust_landmark) {
		this.cust_landmark = cust_landmark;
	}

	public String getAlternate_contact_no() {
		return cust_alternate_contact_no;
	}

	public void setAlternate_contact_no(String cust_alternate_contact_no) {
		this.cust_alternate_contact_no = cust_alternate_contact_no;
	}

	public String getCustEmailId() {
		return cust_email_id;
	}

	public void setCustEmailId(String cust_email_id) {
		this.cust_email_id = cust_email_id;
	}
	
	
	
	@Override
	public String toString() {
		return "Visit [caf_no=" + caf_no + ", taskId=" + task_id + ", visitId="
				+ visit_id + ", personMet=" + personMet + ", DateTime="
				+ verification_timestamp + ", relationship=" + relationship
				+ ", customerSign=" + customerSign + ", proofId=" + proofId
				+ ", addressId=" + addressId + ", houseId=" + houseId
				+ ", documentId=" + documentId + ", remark=" + remark
				+ ", appointment=" + appointment + ", appointmentTimeDate="
				+ appointmentTimeDate + ", addressTraced=" + addressTraced
				+ ", callCustome=" + callCustome + ", callSpoc=" + callSpoc
				+ ", homeLocked=" + homeLocked + ", callForAppointment="
				+ callForAppointment + ", businssType=" + businssType
				+ ", occupancy=" + occupancy + ", accomodation=" + accomodation
				+ ", area=" + area + ", negativeShortReason="
				+ negativeShortReason + ", latitude=" + latitude + ", longitude=" + longitude + ", user_name=" + user_name + ", user_fname="
				+ user_fname + ", user_lname=" + user_lname + ", activity_type=" + activity_type + ", cust_landmark="
						+ cust_landmark + ", cust_alternate_contact_no=" + cust_alternate_contact_no + ", cust_email_id=" + cust_email_id +", version_id=" + version_id + ", cluster_name=" + cluster_name +  "]";
	}

	
}
