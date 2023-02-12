package com.ecsail.structures;

import com.ecsail.annotation.ColumnName;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BoatListDTO extends BoatDTO {
	private IntegerProperty membership_id;
	private StringProperty lName;
	private StringProperty fName;
	private IntegerProperty numberOfImages;
	
	public BoatListDTO(Integer boat_id, Integer ms_id, String manufacturer, String manufacture_year,
					   String registration_num, String model, String boat_name, String sail_number,
					   Boolean hasTrailer, String length, String weight, String keel, String phrf,
					   String draft, String beam, String lwl, Boolean aux, Integer membership_id,
					   String lName, String fName, Integer numberOfImages) {
		
		super(boat_id, ms_id, manufacturer, manufacture_year, registration_num, model, boat_name,
				sail_number, hasTrailer, length, weight, keel, phrf, draft, beam, lwl, aux);
		this.membership_id = new SimpleIntegerProperty(membership_id);
		this.lName = new SimpleStringProperty(lName);
		this.fName = new SimpleStringProperty(fName);
		this.numberOfImages = new SimpleIntegerProperty(numberOfImages);
	}

	public final IntegerProperty membership_idProperty() {
		return this.membership_id;
	}
	

	public final int getMembership_id() {
		return this.membership_idProperty().get();
	}
	

	public final void setMembership_id(final int membership_id) {
		this.membership_idProperty().set(membership_id);
	}
	

	public final StringProperty lNameProperty() {
		return this.lName;
	}
	

	public final String getlName() {
		return this.lNameProperty().get();
	}
	

	public final void setlName(final String lName) {
		this.lNameProperty().set(lName);
	}
	

	public final StringProperty fNameProperty() {
		return this.fName;
	}
	

	public final String getfName() {
		return this.fNameProperty().get();
	}
	

	public final void setfName(final String fName) {
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
