package com.ecsail.pdf.directory;

public class Object_SlipInfo {
	String slipNum;
	Integer subleaseMsID;
	String fName;
	String lName;
	
	public Object_SlipInfo(String slipNum, Integer subleasePid, String fName, String lName) {
		super();
		this.slipNum = slipNum;
		this.subleaseMsID = subleasePid;
		this.fName = fName;
		this.lName = lName;
	}

	public String getSlipNum() {
		return slipNum;
	}

	public void setSlipNum(String slipNum) {
		this.slipNum = slipNum;
	}

	public Integer getSubleaseMsID() {
		return subleaseMsID;
	}

	public void setSubleaseMsID(Integer subleaseMsID) {
		this.subleaseMsID = subleaseMsID;
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
		return "Object_SlipInfo [slipNum=" + slipNum + ", subleaseMsID=" + subleaseMsID + ", fName=" + fName
				+ ", lName=" + lName + "]";
	}


}
