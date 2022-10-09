package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MembershipListDTO extends MembershipDTO {

	private StringProperty lname;
	private StringProperty fname;
	private StringProperty slip;
	private IntegerProperty subleaser;
	private IntegerProperty membershipId;
	private StringProperty selectedYear;
	
	public MembershipListDTO(Integer msid, Integer pid, Integer membershipId, String joinDate, String memType,
							 String slip, String lname, String fname, Integer subleaser, String address, String city, String state, String zip, String selectedYear) {
		super(msid, pid, joinDate, memType, address, city, state, zip);
		this.lname = new SimpleStringProperty(lname);
		this.fname = new SimpleStringProperty(fname);
		this.slip = new SimpleStringProperty(slip);
		this.subleaser = new SimpleIntegerProperty(subleaser);
		this.membershipId = new SimpleIntegerProperty(membershipId);
		this.selectedYear = new SimpleStringProperty(selectedYear);
	}

	public MembershipListDTO() {
		super();
	}

	public void setLname(StringProperty lname) {
		this.lname = lname;
	}



	public void setFname(StringProperty fname) {
		this.fname = fname;
	}



	public void setSlip(StringProperty slip) {
		this.slip = slip;
	}



	public void setSubleaser(IntegerProperty subleaser) {
		this.subleaser = subleaser;
	}



	public final StringProperty lnameProperty() {
		return this.lname;
	}
	


	public final String getLname() {
		return this.lnameProperty().get();
	}
	


	public final void setLname(final String lname) {
		this.lnameProperty().set(lname);
	}
	


	public final StringProperty fnameProperty() {
		return this.fname;
	}
	


	public final String getFname() {
		return this.fnameProperty().get();
	}
	


	public final void setFname(final String fname) {
		this.fnameProperty().set(fname);
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
		return "Object_MembershipList [lname=" + lname + ", fname=" + fname + ", slip=" + slip + ", subleaser="
				+ subleaser + ", membershipId=" + membershipId + ", selectedYear=" + selectedYear + ", msidProperty()="
				+ msidProperty() + ", getMsid()=" + getMsid() + ", pidProperty()=" + pidProperty() + ", getPid()="
				+ getPid() + ", joinDateProperty()=" + joinDateProperty() + ", getJoinDate()=" + getJoinDate()
				+ ", memTypeProperty()=" + memTypeProperty() + ", getMemType()=" + getMemType() + ", addressProperty()="
				+ addressProperty() + ", getAddress()=" + getAddress() + ", cityProperty()=" + cityProperty()
				+ ", getCity()=" + getCity() + ", stateProperty()=" + stateProperty() + ", getState()=" + getState()
				+ ", zipProperty()=" + zipProperty() + ", getZip()=" + getZip() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
