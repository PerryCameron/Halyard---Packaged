package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MembershipListDTO extends MembershipDTO {

	private StringProperty lastName;
	private StringProperty firstName;
	private StringProperty slip;
	private IntegerProperty subLeaser;
	private IntegerProperty membershipId;
	private StringProperty selectedYear;

	private ObservableList<BoatDTO> boatDTOS = FXCollections.observableArrayList();
//	private ObservableList<NotesDTO> notesDTOS = FXCollections.observableArrayList();
	private ObservableList<MembershipIdDTO> membershipIdDTOS = FXCollections.observableArrayList();
	private ObservableList<InvoiceDTO> invoiceDTOS = FXCollections.observableArrayList();
	
	public MembershipListDTO(Integer msid, Integer pid, Integer membershipId, String joinDate, String memType,
							 String slip, String lastName, String firstName, Integer subLeaser, String address, String city, String state, String zip, String selectedYear) {
		super(msid, pid, joinDate, memType, address, city, state, zip);
		this.lastName = new SimpleStringProperty(lastName);
		this.firstName = new SimpleStringProperty(firstName);
		this.slip = new SimpleStringProperty(slip);
		this.subLeaser = new SimpleIntegerProperty(subLeaser);
		this.membershipId = new SimpleIntegerProperty(membershipId);
		this.selectedYear = new SimpleStringProperty(selectedYear);
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getMembershipInfo() {
		return "Membership " + getMembershipId() + " (ms_id " + getMsId() + ") ";
	}

	public MembershipListDTO() {
		super();
	}

	public void setLastName(StringProperty lastName) {
		this.lastName = lastName;
	}



	public void setFirstName(StringProperty firstName) {
		this.firstName = firstName;
	}



	public void setSlip(StringProperty slip) {
		this.slip = slip;
	}


	public final StringProperty lastNameProperty() {
		return this.lastName;
	}
	


	public final String getLastName() {
		return this.lastNameProperty().get();
	}
	


	public final void setLname(final String lname) {
		this.lastNameProperty().set(lname);
	}
	


	public final StringProperty firstNameProperty() {
		return this.firstName;
	}
	


	public final String getFirstName() {
		return this.firstNameProperty().get();
	}
	


	public final void setFname(final String fname) {
		this.firstNameProperty().set(fname);
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



	public final IntegerProperty subLeaserProperty() {
		return this.subLeaser;
	}
	



	public final int getSubLeaser() {
		return this.subLeaserProperty().get();
	}
	



	public final void setSubLeaser(final int subLeaser) {
		this.subLeaserProperty().set(subLeaser);
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

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public ObservableList<BoatDTO> getBoatDTOS() {
		return boatDTOS;
	}

	public void setBoatDTOS(ObservableList<BoatDTO> boatDTOS) {
		this.boatDTOS = boatDTOS;
	}

	public ObservableList<MembershipIdDTO> getMembershipIdDTOS() {
		return membershipIdDTOS;
	}

	public void setMembershipIdDTOS(ObservableList<MembershipIdDTO> membershipIdDTOS) {
		this.membershipIdDTOS = membershipIdDTOS;
	}

	public ObservableList<InvoiceDTO> getInvoiceDTOS() {
		return invoiceDTOS;
	}

	public void setInvoiceDTOS(ObservableList<InvoiceDTO> invoiceDTOS) {
		this.invoiceDTOS = invoiceDTOS;
	}

	@Override
	public String toString() {
		return "Object_MembershipList [lname=" + lastName + ", fname=" + firstName + ", slip=" + slip + ", subleaser="
				+ subLeaser + ", membershipId=" + membershipId + ", selectedYear=" + selectedYear + ", msidProperty()="
				+ msIdProperty() + ", getMsid()=" + getMsId() + ", pidProperty()=" + pIdProperty() + ", getPid()="
				+ getpId() + ", joinDateProperty()=" + joinDateProperty() + ", getJoinDate()=" + getJoinDate()
				+ ", memTypeProperty()=" + memTypeProperty() + ", getMemType()=" + getMemType() + ", addressProperty()="
				+ addressProperty() + ", getAddress()=" + getAddress() + ", cityProperty()=" + cityProperty()
				+ ", getCity()=" + getCity() + ", stateProperty()=" + stateProperty() + ", getState()=" + getState()
				+ ", zipProperty()=" + zipProperty() + ", getZip()=" + getZip() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
