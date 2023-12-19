package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OfficerDTO {

	private IntegerProperty officerId;
	private IntegerProperty personId;
	private StringProperty boardYear;
	private StringProperty officerType;
	private StringProperty fiscalYear;
	
	public OfficerDTO(Integer officerId, Integer personId, String boardYear,
					  String officerType, String fiscalYear) {

		this.officerId = new SimpleIntegerProperty(officerId);
		this.personId = new SimpleIntegerProperty(personId);
		this.boardYear = new SimpleStringProperty(boardYear);
		this.officerType = new SimpleStringProperty(officerType);
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
	}

	public OfficerDTO(Integer personId, String fiscalYear) {
		this.officerId = new SimpleIntegerProperty(0);
		this.personId = new SimpleIntegerProperty(personId);
		this.boardYear = new SimpleStringProperty("0");
		this.officerType = new SimpleStringProperty("BM");
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
	}

	public final IntegerProperty officerIdProperty() {
		return this.officerId;
	}
	

	public final int getOfficerId() {
		return this.officerIdProperty().get();
	}
	

	public final void setOfficerId(final int officerId) {
		this.officerIdProperty().set(officerId);
	}
	

	public final IntegerProperty personIdProperty() {
		return this.personId;
	}
	

	public final int getPersonId() {
		return this.personIdProperty().get();
	}
	

	public final void setPersonId(final int personId) {
		this.personIdProperty().set(personId);
	}
	

	public final StringProperty boardYearProperty() {
		return this.boardYear;
	}
	

	public final String getBoardYear() {
		return this.boardYearProperty().get();
	}
	

	public final void setBoardYear(final String boardYear) {
		this.boardYearProperty().set(boardYear);
	}
	

	public final StringProperty officerTypeProperty() {
		return this.officerType;
	}
	

	public final String getOfficerType() {
		return this.officerTypeProperty().get();
	}
	

	public final void setOfficerType(final String officerType) {
		this.officerTypeProperty().set(officerType);
	}
	

	public final StringProperty fiscalYearProperty() {
		return this.fiscalYear;
	}
	

	public final String getFiscalYear() {
		return this.fiscalYearProperty().get();
	}
	

	public final void setFiscalYear(final String fiscalYear) {
		this.fiscalYearProperty().set(fiscalYear);
	}

	@Override
	public String toString() {
		return "Object_Officer [officer_id=" + officerId + ", person_id=" + personId + ", board_year=" + boardYear
				+ ", officer_type=" + officerType + ", fiscal_year=" + fiscalYear + "]";
	}
}
