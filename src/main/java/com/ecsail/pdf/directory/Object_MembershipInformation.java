package com.ecsail.pdf.directory;

import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.EmailRepositoryImpl;
import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.implementations.PhoneRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.EmailRepository;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.repository.interfaces.PhoneRepository;

import java.util.ArrayList;
import java.util.List;

public class Object_MembershipInformation {
	protected BoatRepository boatRepository = new BoatRepositoryImpl();
	protected EmailRepository emailRepository = new EmailRepositoryImpl();
	protected PersonRepository personRepository = new PersonRepositoryImpl();
	protected PhoneRepository phoneRepository = new PhoneRepositoryImpl();
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
		this.primary = personRepository.getPersonByPid(m.getpId());
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
		ArrayList<PersonDTO> dependants = personRepository.getDependants(m);
		for(PersonDTO d: dependants) {
			children += d.getFirstName();
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
		if (personRepository.activePersonExists(m.getMsId(), 2)) {
			s = personRepository.getPerson(m.getMsId(), 2);
			this.secondaryExists = true;
		}
		return s;
	}
	
	private void getSecondaryPhoneAndEmail() {
		this.secondaryEmail = "";
		this.secondaryPhone = "";
		if(secondaryExists) {
			if (emailRepository.emailExists(secondary))
				this.secondaryEmail = emailRepository.getEmail(secondary);
			if (phoneRepository.listedPhoneOfTypeExists(secondary, "C"))
				this.secondaryPhone = phoneRepository.getListedPhoneByType(secondary, "C") + " Cell";
		}
	}
	
	private void getPrimaryPhoneAndEmail() {
		this.primaryEmail = "";
		this.primaryPhone = "";
		if (emailRepository.emailExists(primary))
			this.primaryEmail = emailRepository.getEmail(primary);
		if (phoneRepository.listedPhoneOfTypeExists(primary, "C")) {
			this.primaryPhone = phoneRepository.getListedPhoneByType(primary, "C") + " Cell";
		} else {
			if (phoneRepository.listedPhoneOfTypeExists(primary, "H")) {
				this.primaryPhone = phoneRepository.getListedPhoneByType(primary, "H") + " Home";
			}
		}
	}
	
	private void getEmergencyPhoneString() {
		this.emergencyPhone = "";
		if (phoneRepository.listedPhoneOfTypeExists(primary, "E"))
			this.emergencyPhone = "Emergency: " + phoneRepository.getListedPhoneByType(primary, "E");
	}
	
	private String getBoatsString(MembershipListDTO m) {
		String memberBoats = "";
		List<BoatDTO> boats;
		boats = boatRepository.getBoatsByMsId(m.getMsId());
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
