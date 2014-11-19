package woxi.cvs.model;

import java.util.ArrayList;

public class VisitHistory {

	private String visitId;
	private String personMet;
    private String visit_at;
    private String dateTime;
    private String relationship;
    private String customerSign;
    private String ifProof;
    private String personmetProof;
    private String addressProof;
    private String houseProof;
    private String lock_proof;
    private String remark;
    private String appointmentTimeDate;
    private ArrayList comments;
    
	public String getVisitId() {
		return visitId;
	}
	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}
	public String getPersonMet() {
		return personMet;
	}
	public void setPersonMet(String personMet) {
		this.personMet = personMet;
	}
	public String getVisit_at() {
		return visit_at;
	}
	public void setVisit_at(String visit_at) {
		this.visit_at = visit_at;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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
		this.customerSign = customerSign;
	}
	public String getIfProof() {
		return ifProof;
	}
	public void setIfProof(String ifProof) {
		this.ifProof = ifProof;
	}
	public String getPersonmetProof() {
		return personmetProof;
	}
	public void setPersonmetProof(String personmetProof) {
		this.personmetProof = personmetProof;
	}
	public String getAddressProof() {
		return addressProof;
	}
	public void setAddressProof(String addressProof) {
		this.addressProof = addressProof;
	}
	public String getHouseProof() {
		return houseProof;
	}
	public void setHouseProof(String houseProof) {
		this.houseProof = houseProof;
	}
	public String getLock_proof() {
		return lock_proof;
	}
	public void setLock_proof(String lock_proof) {
		this.lock_proof = lock_proof;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAppointmentTimeDate() {
		return appointmentTimeDate;
	}
	public void setAppointmentTimeDate(String appointmentTimeDate) {
		this.appointmentTimeDate = appointmentTimeDate;
	}
	public ArrayList getComments() {
		return comments;
	}
	public void setComments(ArrayList comments) {
		this.comments = comments;
	}
    
    
	
}
