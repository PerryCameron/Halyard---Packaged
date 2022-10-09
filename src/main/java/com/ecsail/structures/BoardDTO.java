package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
public class BoardDTO {
	
	private IntegerProperty person_id;
	private IntegerProperty ms_id;
	private IntegerProperty officer_id;  // probably don't need this but can't hurt
    private StringProperty fname;
    private StringProperty lname;
    private StringProperty fiscal_year;  // the year they are an officer
	private StringProperty board_year;  // when their board seat expires
	private StringProperty officer_type;  // the type of officer

	public BoardDTO(Integer person_id, Integer ms_id, Integer officer_id,
					String fname, String lname, String fiscal_year, String board_year,
					String officer_type) {
		this.person_id = new SimpleIntegerProperty(person_id);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.officer_id = new SimpleIntegerProperty(officer_id);
		this.fname = new SimpleStringProperty(fname);
		this.lname = new SimpleStringProperty(lname);
		this.fiscal_year = new SimpleStringProperty(fiscal_year);
		this.board_year = new SimpleStringProperty(board_year);
		this.officer_type = new SimpleStringProperty(officer_type);
	}

	public final IntegerProperty person_idProperty() {
		return this.person_id;
	}
	

	public final int getPerson_id() {
		return this.person_idProperty().get();
	}
	

	public final void setPerson_id(final int person_id) {
		this.person_idProperty().set(person_id);
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
	

	public final IntegerProperty officer_idProperty() {
		return this.officer_id;
	}
	

	public final int getOfficer_id() {
		return this.officer_idProperty().get();
	}
	

	public final void setOfficer_id(final int officer_id) {
		this.officer_idProperty().set(officer_id);
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
	

	public final StringProperty fiscal_yearProperty() {
		return this.fiscal_year;
	}
	

	public final String getFiscal_year() {
		return this.fiscal_yearProperty().get();
	}
	

	public final void setFiscal_year(final String fiscal_year) {
		this.fiscal_yearProperty().set(fiscal_year);
	}
	

	public final StringProperty board_yearProperty() {
		return this.board_year;
	}
	

	public final String getBoard_year() {
		return this.board_yearProperty().get();
	}
	

	public final void setBoard_year(final String board_year) {
		this.board_yearProperty().set(board_year);
	}
	

	public final StringProperty officer_typeProperty() {
		return this.officer_type;
	}
	

	public final String getOfficer_type() {
		return this.officer_typeProperty().get();
	}
	

	public final void setOfficer_type(final String officer_type) {
		this.officer_typeProperty().set(officer_type);
	}
}
