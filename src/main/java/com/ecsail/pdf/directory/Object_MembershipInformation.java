package com.ecsail.pdf.directory;

import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlBoat;
import com.ecsail.sql.select.SqlEmail;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.sql.select.SqlPhone;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public class Object_MembershipInformation {
	PersonDTO primary;
	PersonDTO secondary;
	String primaryEmail;
	String secondaryEmail;
	String primaryPhone;
	String secondaryPhone;
	String emergencyPhone;
	Boolean secondaryExists;
	String children;
	String slip;
	String boats;
	
	public Object_MembershipInformation(MembershipListDTO m) {
		this.primary = SqlPerson.getPersonByPid(m.getpId());
		this.secondary = getSecondaryPerson(m);
		this.children = getChildrenString(m);
		getSecondaryPhoneAndEmail();
		getPrimaryPhoneAndEmail();
		getEmergencyPhoneString();
		this.slip = getSlipString(m);
		this.boats = getBoatsString(m);
	}
	
	private String getChildrenString(MembershipListDTO m) {
		String children = "Children: ";
		int count = 0;
		ArrayList<PersonDTO> dependants = SqlPerson.getDependants(m);
		for(PersonDTO d: dependants) {
			children += d.getFname();
			count++;
			if(count < dependants.size()) children += ",";
		}
		if(children.equals("Children: ")) children = "";  // take the label out if there are no children
		return children;
	}
	
	private String getSlipString(MembershipListDTO m) {
	if (m.getSlip() != null)
		slip = "Slip: " + m.getSlip();
	else
		slip = "";
	return slip;
	}
	
	
	private PersonDTO getSecondaryPerson(MembershipListDTO m) {
		PersonDTO s = new PersonDTO();
		this.secondaryExists = false;
		if (SqlExists.activePersonExists(m.getMsId(), 2)) {
			s = SqlPerson.getPerson(m.getMsId(), 2);
			this.secondaryExists = true;
		}
		return s;
	}
	
	private void getSecondaryPhoneAndEmail() {
		this.secondaryEmail = "";
		this.secondaryPhone = "";
		if(secondaryExists) {
			if (SqlExists.emailExists(secondary))
				this.secondaryEmail = SqlEmail.getEmail(secondary);
			if (SqlExists.listedPhoneOfTypeExists(secondary, "C"))
				this.secondaryPhone = SqlPhone.getListedPhoneByType(secondary, "C") + " Cell";
		}
	}
	
	private void getPrimaryPhoneAndEmail() {
		this.primaryEmail = "";
		this.primaryPhone = "";
		if (SqlExists.emailExists(primary))
			this.primaryEmail = SqlEmail.getEmail(primary);
		if (SqlExists.listedPhoneOfTypeExists(primary, "C")) {
			this.primaryPhone = SqlPhone.getListedPhoneByType(primary, "C") + " Cell";
		} else {
			if (SqlExists.listedPhoneOfTypeExists(primary, "H")) {
				this.primaryPhone = SqlPhone.getListedPhoneByType(primary, "H") + " Home";
			}
		}
	}
	
	private void getEmergencyPhoneString() {
		this.emergencyPhone = "";
		if (SqlExists.listedPhoneOfTypeExists(primary, "E"))
			this.emergencyPhone = "Emergency: " + SqlPhone.getListedPhoneByType(primary, "E");
	}
	
	private String getBoatsString(MembershipListDTO m) {
		String memberBoats = "";
		List<BoatDTO> boats = new ArrayList<BoatDTO>();
		boats = SqlBoat.getBoats(m.getMsId());
		int count = 0;
		if (boats.size() > 0) {  // there are some boats
			for (BoatDTO b : boats) {
				count++;
				memberBoats += b.getModel();
				if (b.getRegistrationNum() != null) {  // this boat has registration
					memberBoats += "(" + b.getRegistrationNum() + ")";
				}
				if (count < boats.size()) memberBoats += ", ";
			}
		}
		return memberBoats;
	}

	public PersonDTO getPrimary() {
		return primary;
	}

	public void setPrimary(PersonDTO primary) {
		this.primary = primary;
	}

	public PersonDTO getSecondary() {
		return secondary;
	}

	public void setSecondary(PersonDTO secondary) {
		this.secondary = secondary;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public Boolean getSecondaryExists() {
		return secondaryExists;
	}

	public void setSecondaryExists(Boolean secondaryExists) {
		this.secondaryExists = secondaryExists;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getSlip() {
		return slip;
	}

	public void setSlip(String slip) {
		this.slip = slip;
	}

	public String getBoats() {
		return boats;
	}

	public void setBoats(String boats) {
		this.boats = boats;
	}

}
