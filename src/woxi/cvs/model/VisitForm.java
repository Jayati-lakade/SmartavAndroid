package woxi.cvs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class VisitForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private String visitNumber;
	private ArrayList<Visit> visitsList;

	public String getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(String visitNumber) {
		this.visitNumber = visitNumber;
	}

	public ArrayList<Visit> getVisitsList() {
		return visitsList;
	}

	public void setVisitsList(ArrayList<Visit> visitsList) {
		this.visitsList = visitsList;
	}

}
