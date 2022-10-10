package com.ecsail.pdf.directory;

public class PDF_Object_Officer {
String fname;
String lname;
String officerType;
String boardTermEndYear;
String fiscalYear;

public PDF_Object_Officer(String fname, String lname, String officerType, String boardTermEndYear, String fiscalYear) {
	super();
	this.fname = fname;
	this.lname = lname;
	this.officerType = officerType;
	this.boardTermEndYear = boardTermEndYear;
	this.fiscalYear = fiscalYear;
}

public String getFname() {
	return fname;
}

public void setFname(String fname) {
	this.fname = fname;
}

public String getLname() {
	return lname;
}

public void setLname(String lname) {
	this.lname = lname;
}

public String getOfficerType() {
	return officerType;
}

public void setOfficerType(String officerType) {
	this.officerType = officerType;
}

public String getBoardTermEndYear() {
	return boardTermEndYear;
}

public void setBoardTermEndYear(String boardTermEndYear) {
	this.boardTermEndYear = boardTermEndYear;
}

public String getFiscalYear() {
	return fiscalYear;
}

public void setFiscalYear(String fiscalYear) {
	this.fiscalYear = fiscalYear;
}



}
