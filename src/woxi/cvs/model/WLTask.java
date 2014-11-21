package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class WLTask extends Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534340201290479151L;
	private int wl_id;
	private String father_name;
	
	private String acct_category_desc;
	private String service_type;
	private String visit_at;
	private String printed_date;
	private String printed_time;
	private String no_of_dels_under_account;
	//private String company_name;

	//private ArrayList<Visit> visitList;// //Used for Last rescent Visit History
										// details



	public WLTask() {
	}

	public int getWl_id() {
		return wl_id;
	}

	public void setWl_id(int wl_id) {
		this.wl_id = wl_id;
	}

	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}



	public String getAcct_category_desc() {
		return acct_category_desc;
	}

	public void setAcct_category_desc(String acct_category_desc) {
		this.acct_category_desc = acct_category_desc;
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


/*
	public ArrayList<Visit> getVisitList() {
		return visitList;
	}

	public void setVisitList(ArrayList<Visit> visitList) {
		this.visitList = visitList;
	}

	public ArrayList<Visit> getVISITLIST() {
		return visitList;
	}

	public void setVISITLIST(ArrayList<Visit> vISITLIST) {
		visitList = vISITLIST;
	}
*/
}
