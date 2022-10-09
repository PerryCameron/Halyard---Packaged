package com.ecsail.structures;

public class OfficerWithNameDTO {
	String lName;
	String fName;
	String year;

	public OfficerWithNameDTO(String lName, String fName, String year) {
		super();
		this.lName = lName;
		this.fName = fName;
		this.year = year;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Object_OfficerWithName [lName=" + lName + ", fName=" + fName + ", year=" + year + "]";
	}

}
