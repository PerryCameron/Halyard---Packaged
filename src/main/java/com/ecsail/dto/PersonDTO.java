package com.ecsail.dto;

import javafx.beans.property.*;

public class PersonDTO {
	private IntegerProperty p_id;
	private IntegerProperty ms_id;
	private IntegerProperty memberType; // 1 == primary 2 == secondary 3 == children of
	private StringProperty fname;
	private StringProperty lname;
	private StringProperty occupation;
	private StringProperty business;
	private StringProperty birthday;
	private BooleanProperty active;
	private StringProperty nname;
	private IntegerProperty oldMsid;

	public PersonDTO(Integer pid, Integer ms_id, Integer memberType, String firstName, String lastName,
					 String birthday, String occupation,
                     String business, Boolean isActive, String nickName, Integer oldMsid) {
		this.p_id = new SimpleIntegerProperty(pid);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.memberType = new SimpleIntegerProperty(memberType);
		this.fname = new SimpleStringProperty(firstName);
		this.lname = new SimpleStringProperty(lastName);
		this.birthday = new SimpleStringProperty(birthday);
		this.occupation = new SimpleStringProperty(occupation);
		this.business = new SimpleStringProperty(business);
		this.active = new SimpleBooleanProperty(isActive);
		this.nname = new SimpleStringProperty(nickName);
		this.oldMsid = new SimpleIntegerProperty(oldMsid);
	}

	public PersonDTO() { // default constructor

	}

	public String getFullName() {
		if(getFname() == null) setFname("First");
		if(getLname() == null) setLname("Last");
		return getFname() + " " + getLname();
	}

	public String getNameWithInfo() {
		return getFullName() + " (p_id " + getP_id() + ")";
	}

	public final IntegerProperty oldMsidProperty( ) {
		return this.oldMsid;
	}

	public final int getOldMsid() {
		return this.oldMsidProperty().get();
	}

	public final void setOldMsid(final int oldMsid) {
		this.oldMsidProperty().set(oldMsid);
	}

	public final IntegerProperty p_idProperty() {
		return this.p_id;
	}

	public final int getP_id() {
		return this.p_idProperty().get();
	}

	public final void setP_id(final int p_id) {
		this.p_idProperty().set(p_id);
	}

	public final IntegerProperty ms_idProperty() {
		return this.ms_id;
	}

	public final int getMs_id() {
		return this.ms_idProperty().get();
	}

	public final void setMs_id(final int ms_id) {
		this.ms_idProperty().set(ms_id);
	}

	public final IntegerProperty memberTypeProperty() {
		return this.memberType;
	}

	public final int getMemberType() {
		return this.memberTypeProperty().get();
	}

	public final void setMemberType(final int memberType) {
		this.memberTypeProperty().set(memberType);
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

	public final StringProperty lnameProperty() {
		return this.lname;
	}

	public final String getLname() {
		return this.lnameProperty().get();
	}

	public final void setLname(final String lname) {
		this.lnameProperty().set(lname);
	}

	public final StringProperty occupationProperty() {
		return this.occupation;
	}

	public final String getOccupation() {
		return this.occupationProperty().get();
	}

	public final void setOccupation(final String occupation) {
		this.occupationProperty().set(occupation);
	}

	public final StringProperty businessProperty() {
		return this.business;
	}

	public final String getBusiness() {
		return this.businessProperty().get();
	}

	public final void setBusiness(final String business) {
		this.businessProperty().set(business);
	}

	public final StringProperty birthdayProperty() {
		return this.birthday;
	}

	public final String getBirthday() {
		return this.birthdayProperty().get();
	}

	public final void setBirthday(final String birthday) {
		this.birthdayProperty().set(birthday);
	}

	public final BooleanProperty activeProperty() {
		return this.active;
	}

	public final boolean isActive() {
		return this.activeProperty().get();
	}

	public final void setActive(final boolean active) {
		this.activeProperty().set(active);
	}

	public final StringProperty nnameProperty() {
		return this.nname;
	}

	public final String getNname() {
		return this.nnameProperty().get();
	}

	public final void setNname(final String nname) {
		this.nnameProperty().set(nname);
	}

	@Override
	public String toString() {
		return "Object_Person [p_id=" + p_id + ", ms_id=" + ms_id + ", memberType=" + memberType + ", fname=" + fname
				+ ", lname=" + lname + ", occupation=" + occupation + ", business=" + business + ", birthday="
				+ birthday + ", active=" + active + ", nname=" + nname + "]";
	}

}
