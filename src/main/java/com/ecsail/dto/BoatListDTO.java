package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BoatListDTO extends BoatDTO {
	protected IntegerProperty membershipId;
	protected StringProperty lName;
	protected StringProperty fName;
	protected IntegerProperty numberOfImages;
	
	public BoatListDTO(Integer boat_id, Integer ms_id, String manufacturer, String manufacture_year,
					   String registration_num, String model, String boat_name, String sail_number,
					   Boolean hasTrailer, String length, String weight, String keel, String phrf,
					   String draft, String beam, String lwl, Boolean aux, Integer membershipId,
					   String lName, String fName, Integer numberOfImages) {
		
		super(boat_id, ms_id, manufacturer, manufacture_year, registration_num, model, boat_name,
				sail_number, hasTrailer, length, weight, keel, phrf, draft, beam, lwl, aux);
		this.membershipId = new SimpleIntegerProperty(membershipId);
		this.lName = new SimpleStringProperty(lName);
		this.fName = new SimpleStringProperty(fName);
		this.numberOfImages = new SimpleIntegerProperty(numberOfImages);
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
	

	public final StringProperty lNameProperty() {
		return this.lName;
	}
	

	public final String getLastName() {
		return this.lNameProperty().get();
	}
	

	public final void setLastName(final String lName) {
		this.lNameProperty().set(lName);
	}
	

	public final StringProperty fNameProperty() {
		return this.fName;
	}
	

	public final String getFirstName() {
		return this.fNameProperty().get();
	}
	

	public final void setFirstName(final String fName) {
		this.fNameProperty().set(fName);
	}

	public final IntegerProperty numberOfImagesProperty() {
		return this.numberOfImages;
	}


	public final int getNumberOfImages() {
		return this.numberOfImagesProperty().get();
	}


	public final void setNumberOfImages(final int numberOfImages) {
		this.numberOfImagesProperty().set(numberOfImages);
	}

}
