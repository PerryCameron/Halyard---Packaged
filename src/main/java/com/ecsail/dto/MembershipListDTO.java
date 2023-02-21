package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MembershipListDTO extends MembershipDTO {

	private StringProperty lName;
	private StringProperty fName;
	private StringProperty slip;
	private IntegerProperty subleaser;
	private IntegerProperty membershipId;
	private StringProperty selectedYear;
	
	public MembershipListDTO(Integer msid, Integer pid, Integer membershipId, String joinDate, String memType,
							 String slip, String lName, String fName, Integer subleaser, String address, String city, String state, String zip, String selectedYear) {
		super(msid, pid, joinDate, memType, address, city, state, zip);
		this.lName = new SimpleStringProperty(lName);
		this.fName = new SimpleStringProperty(fName);
		this.slip = new SimpleStringProperty(slip);
		this.subleaser = new SimpleIntegerProperty(subleaser);
		this.membershipId = new SimpleIntegerProperty(membershipId);
		this.selectedYear = new SimpleStringProperty(selectedYear);
	}

	public String getMembershipInfo() {
		return "Membership " + getMembershipId() + " (ms_id " + getMsId() + ") ";
	}

	public MembershipListDTO() {
		super();
	}

	public void setlName(StringProperty lName) {
		this.lName = lName;
	}



	public void setfName(StringProperty fName) {
		this.fName = fName;
	}



	public void setSlip(StringProperty slip) {
		this.slip = slip;
	}


	public final StringProperty lNameProperty() {
		return this.lName;
	}
	


	public final String getlName() {
		return this.lNameProperty().get();
	}
	


	public final void setLname(final String lname) {
		this.lNameProperty().set(lname);
	}
	


	public final StringProperty fNameProperty() {
		return this.fName;
	}
	


	public final String getfName() {
		return this.fNameProperty().get();
	}
	


	public final void setFname(final String fname) {
		this.fNameProperty().set(fname);
	}


	public final StringProperty slipProperty() {
		return this.slip;
	}
	



	public final String getSlip() {
		return this.slipProperty().get();
	}
	



	public final void setSlip(final String slip) {
		this.slipProperty().set(slip);
	}



	public final IntegerProperty subleaserProperty() {
		return this.subleaser;
	}
	



	public final int getSubleaser() {
		return this.subleaserProperty().get();
	}
	



	public final void setSubleaser(final int subleaser) {
		this.subleaserProperty().set(subleaser);
	}


	public final IntegerProperty membershipIdProperty() {
		return this.membershipId;
	}
	



	public final int getMembershipId() {
		return this.membershipIdProperty().get();
	}
	



	public final void setMembershipId(final int membershipId) {
		this.membershipIdProperty().set(membershipId);
	}

	public final StringProperty selectedYearProperty() {
		return this.selectedYear;
	}
	



	public final String getSelectedYear() {
		return this.selectedYearProperty().get();
	}
	



	public final void setSelectedYear(final String selectedYear) {
		this.selectedYearProperty().set(selectedYear);
	}



	@Override
	public String toString() {
		return "Object_MembershipList [lname=" + lName + ", fname=" + fName + ", slip=" + slip + ", subleaser="
				+ subleaser + ", membershipId=" + membershipId + ", selectedYear=" + selectedYear + ", msidProperty()="
				+ msIdProperty() + ", getMsid()=" + getMsId() + ", pidProperty()=" + pIdProperty() + ", getPid()="
				+ getpId() + ", joinDateProperty()=" + joinDateProperty() + ", getJoinDate()=" + getJoinDate()
				+ ", memTypeProperty()=" + memTypeProperty() + ", getMemType()=" + getMemType() + ", addressProperty()="
				+ addressProperty() + ", getAddress()=" + getAddress() + ", cityProperty()=" + cityProperty()
				+ ", getCity()=" + getCity() + ", stateProperty()=" + stateProperty() + ", getState()=" + getState()
				+ ", zipProperty()=" + zipProperty() + ", getZip()=" + getZip() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
