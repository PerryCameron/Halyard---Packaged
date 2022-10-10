package com.ecsail.pdf.directory;

public class Object_Sportsmen {
	String year;
	String fName;
	String lName;

	public Object_Sportsmen(String year, String fName, String lName) {
		super();
		this.year = year;
		this.fName = fName;
		this.lName = lName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	@Override
	public String toString() {
		return "Object_Sportsmen [year=" + year + ", fName=" + fName + ", lName=" + lName + "]";
	}
	
}
