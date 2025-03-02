package com.ecsail.dto;

import javafx.beans.property.*;

import java.util.List;

public class PersonDTO {
    private IntegerProperty pId;
    private IntegerProperty msId;
    private IntegerProperty memberType; // 1 == primary 2 == secondary 3 == children of
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty occupation;
    private StringProperty business;
    private StringProperty birthday;
    private BooleanProperty active;
    private StringProperty nickName;
    private IntegerProperty oldMsid;
	List<PhoneDTO> phones;
	List<EmailDTO> email;
	List<AwardDTO> awards;
	List<OfficerDTO> positions;



    public PersonDTO(Integer pid, Integer msId, Integer memberType, String firstName, String lastName,
                     String birthday, String occupation, String business, Boolean isActive, String nickName,
                     Integer oldMsid) {
        this.pId = new SimpleIntegerProperty(pid);
        this.msId = new SimpleIntegerProperty(msId);
        this.memberType = new SimpleIntegerProperty(memberType);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthday = new SimpleStringProperty(birthday);
        this.occupation = new SimpleStringProperty(occupation);
        this.business = new SimpleStringProperty(business);
        this.active = new SimpleBooleanProperty(isActive);
        this.nickName = new SimpleStringProperty(nickName);
        this.oldMsid = new SimpleIntegerProperty(oldMsid);
    }

    public PersonDTO(Integer msId, Integer memberType, Boolean isActive) {
        this.pId = new SimpleIntegerProperty(0);
        this.msId = new SimpleIntegerProperty(msId);
        this.memberType = new SimpleIntegerProperty(memberType);
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.birthday = new SimpleStringProperty("1900-01-01");
        this.occupation = new SimpleStringProperty("");
        this.business = new SimpleStringProperty("");
        this.active = new SimpleBooleanProperty(isActive);
        this.nickName = new SimpleStringProperty("");
        this.oldMsid = new SimpleIntegerProperty(0);
    }

    public PersonDTO(Integer msId, Integer memberType, String firstName, String lastName,
                     String birthday, String occupation, String business, Boolean isActive) {
        this.pId = new SimpleIntegerProperty(0);
        this.msId = new SimpleIntegerProperty(msId);
        this.memberType = new SimpleIntegerProperty(memberType);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthday = new SimpleStringProperty(birthday);
        this.occupation = new SimpleStringProperty(occupation);
        this.business = new SimpleStringProperty(business);
        this.active = new SimpleBooleanProperty(isActive);
        this.nickName = new SimpleStringProperty("");
        this.oldMsid = new SimpleIntegerProperty(0);
    }


    public PersonDTO() { // default constructor
        super();
    }


    public String getFullName() {
        if (getFirstName() == null) setFirstName("First");
        if (getLastName() == null) setLastName("Last");
        return getFirstName() + " " + getLastName();
    }

    public String getNameWithInfo() {
        return getFullName() + " (p_id " + getpId() + ")";
    }

	public int getpId() {
		return pId.get();
	}

	public IntegerProperty pIdProperty() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId.set(pId);
	}

	public int getMsId() {
		return msId.get();
	}

	public IntegerProperty msIdProperty() {
		return msId;
	}

	public void setMsId(int msId) {
		this.msId.set(msId);
	}

	public int getMemberType() {
		return memberType.get();
	}

	public IntegerProperty memberTypeProperty() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType.set(memberType);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public String getOccupation() {
		return occupation.get();
	}

	public StringProperty occupationProperty() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation.set(occupation);
	}

	public String getBusiness() {
		return business.get();
	}

	public StringProperty businessProperty() {
		return business;
	}

	public void setBusiness(String business) {
		this.business.set(business);
	}

	public String getBirthday() {
		return birthday.get();
	}

	public StringProperty birthdayProperty() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday.set(birthday);
	}

	public boolean isActive() {
		return active.get();
	}

	public BooleanProperty activeProperty() {
		return active;
	}

	public void setActive(boolean active) {
		this.active.set(active);
	}

	public String getNickName() {
		return nickName.get();
	}

	public StringProperty nickNameProperty() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName.set(nickName);
	}

	public int getOldMsid() {
		return oldMsid.get();
	}

	public IntegerProperty oldMsidProperty() {
		return oldMsid;
	}

	public void setOldMsid(int oldMsid) {
		this.oldMsid.set(oldMsid);
	}

	public List<PhoneDTO> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDTO> phones) {
		this.phones = phones;
	}

	public List<EmailDTO> getEmail() {
		return email;
	}

	public void setEmail(List<EmailDTO> email) {
		this.email = email;
	}

	public List<AwardDTO> getAwards() {
		return awards;
	}

	public void setAwards(List<AwardDTO> awards) {
		this.awards = awards;
	}

	public List<OfficerDTO> getPositions() {
		return positions;
	}

	public void setPositions(List<OfficerDTO> positions) {
		this.positions = positions;
	}

	@Override
    public String toString() {
        return "Object_Person [p_id=" + pId + ", ms_id=" + msId + ", memberType=" + memberType + ", fname=" + firstName
                + ", lname=" + lastName + ", occupation=" + occupation + ", business=" + business + ", birthday="
                + birthday + ", active=" + active + ", nname=" + nickName + "]";
    }

}
