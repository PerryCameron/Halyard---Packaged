package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
public class BoardDTO {
	
	private IntegerProperty pId;
	private IntegerProperty msId;
	private IntegerProperty officerId;  // probably don't need this but can't hurt
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty fiscalYear;  // the year they are an officer
	private StringProperty boardYear;  // when their board seat expires
	private StringProperty officerType;  // the type of officer

	public BoardDTO(Integer pId, Integer msId, Integer officerId,
					String firstName, String lastName, String fiscalYear, String boardYear,
					String officerType) {
		this.pId = new SimpleIntegerProperty(pId);
		this.msId = new SimpleIntegerProperty(msId);
		this.officerId = new SimpleIntegerProperty(officerId);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
		this.boardYear = new SimpleStringProperty(boardYear);
		this.officerType = new SimpleStringProperty(officerType);
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
	

	public final IntegerProperty msIdProperty() {
		return this.msId;
	}
	

	public final int getMsId() {
		return this.msIdProperty().get();
	}
	

	public final void setMsId(final int msId) {
		this.msIdProperty().set(msId);
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
	

	public final StringProperty firstNameProperty() {
		return this.firstName;
	}
	

	public final String getFirstName() {
		return this.firstNameProperty().get();
	}
	

	public final void setFirstName(final String firstName) {
		this.firstNameProperty().set(firstName);
	}
	

	public final StringProperty lastNameProperty() {
		return this.lastName;
	}
	

	public final String getLastName() {
		return this.lastNameProperty().get();
	}
	

	public final void setLastName(final String lastName) {
		this.lastNameProperty().set(lastName);
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
}
