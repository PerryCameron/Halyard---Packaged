package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MembershipDTO {

	private IntegerProperty msId; /// unique auto key for Membership
	private IntegerProperty pId;  /// pid of Main Member
	//private IntegerProperty membershipId;  // Member ID used in real life
	private StringProperty joinDate;
	private StringProperty memType;  // Type of Membership (Family, Regular, Lake Associate(race fellow), Social
	//private BooleanProperty activeMembership;  // Is the membership active?
	private StringProperty address;
	private StringProperty city;
	private StringProperty state;
	private StringProperty zip;

	public MembershipDTO(Integer msId, Integer pId, String joinDate, String memType
			, String address, String city, String state, String zip) {

		this.msId = new SimpleIntegerProperty(msId);
		this.pId = new SimpleIntegerProperty(pId);
		//this.membershipId = new SimpleIntegerProperty(membershipId);
		this.joinDate = new SimpleStringProperty(joinDate);
		this.memType = new SimpleStringProperty(memType);		
		//this.activeMembership = new SimpleBooleanProperty(activeMembership);
		this.address = new SimpleStringProperty(address);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zip = new SimpleStringProperty(zip);
	}
	
	

	public MembershipDTO() {
		// TODO Auto-generated constructor stub
	}



	public final IntegerProperty msIdProperty() {
		return this.msId;
	}
	

	public final int getMsId() {
		return this.msIdProperty().get();
	}
	

	public final void setMsId(final int msId) {
		this.msIdProperty().set(msId);
	}
	

	public final IntegerProperty pIdProperty() {
		return this.pId;
	}
	

	public final int getpId() {
		return this.pIdProperty().get();
	}
	

	public final void setpId(final int pId) {
		this.pIdProperty().set(pId);
	}
	
	public final StringProperty joinDateProperty() {
		return this.joinDate;
	}
	

	public final String getJoinDate() {
		return this.joinDateProperty().get();
	}
	

	public final void setJoinDate(final String joinDate) {
		this.joinDateProperty().set(joinDate);
	}
	

	public final StringProperty memTypeProperty() {
		return this.memType;
	}
	

	public final String getMemType() {
		return this.memTypeProperty().get();
	}
	

	public final void setMemType(final String memType) {
		this.memTypeProperty().set(memType);
	}
	
	
	public final StringProperty addressProperty() {
		return this.address;
	}
	

	public final String getAddress() {
		return this.addressProperty().get();
	}
	

	public final void setAddress(final String address) {
		this.addressProperty().set(address);
	}
	

	public final StringProperty cityProperty() {
		return this.city;
	}
	

	public final String getCity() {
		return this.cityProperty().get();
	}
	

	public final void setCity(final String city) {
		this.cityProperty().set(city);
	}
	

	public final StringProperty stateProperty() {
		return this.state;
	}
	

	public final String getState() {
		return this.stateProperty().get();
	}
	

	public final void setState(final String state) {
		this.stateProperty().set(state);
	}
	

	public final StringProperty zipProperty() {
		return this.zip;
	}
	

	public final String getZip() {
		return this.zipProperty().get();
	}
	

	public final void setZip(final String zip) {
		this.zipProperty().set(zip);
	}


	@Override
	public String toString() {
		return "Object_Membership [msid=" + msId + ", pid=" + pId + ", joinDate=" + joinDate + ", memType=" + memType
				+ ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip + "]";
	}

}
