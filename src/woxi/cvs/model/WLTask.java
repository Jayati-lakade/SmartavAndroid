package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class WLTask extends Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534340201290479151L;
	private int wl_id;
	private int priority_id;
	private String caf_no;
	private String caf_type;
	private String telephone_no;
	private String alternate_contact_no;
	private String father_name;
	private String priority_timestamp;
	private String address;
	private String status_av;
	private String priority;
	private String product_type;
	private String acct_category_desc;
	private String bill_plan;
	private String dealer_name;
	private String spoc_cntc_no;
	private String csm_cntc_no;
	private String csm;
	private String service_type;
	private String visit_at;
	private String printed_date;
	private String printed_time;
	private String no_of_dels_under_account;
	private String company_name;
	private String landmark;
	private String dealer_re_av_comment1;
	private String dealer_re_av_comment2;
	private String dealer_re_av_comment3;
	private String agency_name;
	private String start_timestamp;
	private String reav_flag;
	private String ofr_visit;
	private String dealerCode;
	private String user_name;
	private String user_fname;
	private String user_lname;
	private String activity_type;
	private String cust_landmark;
	private String cust_alternate_contact_no;
	private String cust_email_id;
	private String cluster_name;
	
	public String getCluster_name() {
		return cluster_name;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public String getCust_landmark() {
		return cust_landmark;
	}

	public void setCust_landmark(String cust_landmark) {
		this.cust_landmark = cust_landmark;
	}

	public String getCust_alternate_contact_no() {
		return cust_alternate_contact_no;
	}

	public void setCust_alternate_contact_no(String cust_alternate_contact_no) {
		this.cust_alternate_contact_no = cust_alternate_contact_no;
	}

	public String getCust_email_id() {
		return cust_email_id;
	}

	public void setCust_email_id(String cust_email_id) {
		this.cust_email_id = cust_email_id;
	}

	private ArrayList<Visit> visitList;// //Used for Last rescent Visit History
										// details

	private int ofr_visit_next;

	public WLTask() {
	}

	public int getWl_id() {
		return wl_id;
	}

	public void setWl_id(int wl_id) {
		this.wl_id = wl_id;
	}

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


	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	public String getPriority_timestamp() {
		return priority_timestamp;
	}

	public void setPriority_timestamp(String priority_timestamp) {
		this.priority_timestamp = priority_timestamp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus_av() {
		return status_av;
	}

	public void setStatus_av(String status_av) {
		this.status_av = status_av;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getAcct_category_desc() {
		return acct_category_desc;
	}

	public void setAcct_category_desc(String acct_category_desc) {
		this.acct_category_desc = acct_category_desc;
	}

	public String getBill_plan() {
		return bill_plan;
	}

	public void setBill_plan(String bill_plan) {
		this.bill_plan = bill_plan;
	}

	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}

	public int getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(int priority_id) {
		this.priority_id = priority_id;
	}

	public String getSpoc_cntc_no() {
		return spoc_cntc_no;
	}

	public void setSpoc_cntc_no(String spoc_cntc_no) {
		this.spoc_cntc_no = spoc_cntc_no;
	}

	public String getCsm_cntc_no() {
		return csm_cntc_no;
	}

	public void setCsm_cntc_no(String csm_cntc_no) {
		this.csm_cntc_no = csm_cntc_no;
	}

	public String getCsm() {
		return csm;
	}

	public void setCsm(String csm) {
		this.csm = csm;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getVisit_at() {
		return visit_at;
	}

	public void setVisit_at(String visit_at) {
		this.visit_at = visit_at;
	}

	public String getPrinted_date() {
		return printed_date;
	}

	public void setPrinted_date(String printed_date) {
		this.printed_date = printed_date;
	}

	public String getPrinted_time() {
		return printed_time;
	}

	public void setPrinted_time(String printed_time) {
		this.printed_time = printed_time;
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

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getDealer_re_av_comment1() {
		return dealer_re_av_comment1;
	}

	public void setDealer_re_av_comment1(String dealer_re_av_comment1) {
		this.dealer_re_av_comment1 = dealer_re_av_comment1;
	}

	public String getDealer_re_av_comment2() {
		return dealer_re_av_comment2;
	}

	public void setDealer_re_av_comment2(String dealer_re_av_comment2) {
		this.dealer_re_av_comment2 = dealer_re_av_comment2;
	}

	public String getDealer_re_av_comment3() {
		return dealer_re_av_comment3;
	}

	public void setDealer_re_av_comment3(String dealer_re_av_comment3) {
		this.dealer_re_av_comment3 = dealer_re_av_comment3;
	}

	public String getAgency_name() {
		return agency_name;
	}

	public void setAgency_name(String agency_name) {
		this.agency_name = agency_name;
	}

	public ArrayList<Visit> getVisitList() {
		return visitList;
	}

	public void setVisitList(ArrayList<Visit> visitList) {
		this.visitList = visitList;
	}

	public String getStart_timestamp() {
		return start_timestamp;
	}

	public void setStart_timestamp(String start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	public String getReav_flag() {
		return reav_flag;
	}

	public void setReav_flag(String reav_flag) {
		this.reav_flag = reav_flag;
	}

	public String getOfr_visit() {
		/*
		 * if(ofr_visit.length() > 0){ ofr_visit_next =
		 * Integer.parseInt(ofr_visit); }else{ ofr_visit_next=0; } return
		 * ""+(++ofr_visit_next);
		 */
		ofr_visit_next = Integer.parseInt(ofr_visit);
		return "" + (++ofr_visit_next);
	}

	public void setOfr_visit(String ofr_visit) {
		this.ofr_visit = ofr_visit;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public ArrayList<Visit> getVISITLIST() {
		return visitList;
	}

	public void setVISITLIST(ArrayList<Visit> vISITLIST) {
		visitList = vISITLIST;
	}

	public int getOfr_visit_next() {
		return ofr_visit_next;
	}

	public void setOfr_visit_next(int ofr_visit_next) {
		this.ofr_visit_next = ofr_visit_next;
	}

	public String getCaf_type() {
		return caf_type;
	}

	public void setCaf_type(String caf_type) {
		this.caf_type = caf_type;
	}
//
//	@Override
//	public String toString() {
//		return this.getCaf_no() + "," + this.getCust_name() + ","
//				+ this.getAddress();
//	}

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

}
