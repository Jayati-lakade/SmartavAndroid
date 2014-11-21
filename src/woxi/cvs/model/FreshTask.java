package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FreshTask extends Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int fresh_id;

	private String assigned_date;

	private String del_no;

	private String no_of_dels_under_account;

	private String segment;

	private String sr_no;
	private String av_agency;

	public FreshTask() {

	}

	public int getFresh_id() {
		return fresh_id;
	}

	public void setFresh_id(int fresh_id) {
		this.fresh_id = fresh_id;
	}


	public String getAssigned_date() {
		return assigned_date;
	}

	public void setAssigned_date(String assigned_date) {
		this.assigned_date = assigned_date;
	}

	public String getDel_no() {
		return del_no;
	}

	public void setDel_no(String del_no) {
		this.del_no = del_no;
	}


	public String getNo_of_dels_under_account() {
		return no_of_dels_under_account;
	}

	public void setNo_of_dels_under_account(String no_of_dels_under_account) {
		this.no_of_dels_under_account = no_of_dels_under_account;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getSr_no() {
		return sr_no;
	}

	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}

	public String getAv_agency() {
		return av_agency;
	}

	public void setAv_agency(String av_agency) {
		this.av_agency = av_agency;
	}


}
