package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OfficerDTO {

	private IntegerProperty officer_id;
	private IntegerProperty person_id;
	private StringProperty board_year;
	private StringProperty officer_type;
	private StringProperty fiscal_year;
	
	public OfficerDTO(Integer officer_id, Integer person_id, String board_year,
					  String officer_type, String fiscal_year) {

		this.officer_id = new SimpleIntegerProperty(officer_id);
		this.person_id = new SimpleIntegerProperty(person_id);
		this.board_year = new SimpleStringProperty(board_year);
		this.officer_type = new SimpleStringProperty(officer_type);
		this.fiscal_year = new SimpleStringProperty(fiscal_year);
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
	

	public final IntegerProperty person_idProperty() {
		return this.person_id;
	}
	

	public final int getPerson_id() {
		return this.person_idProperty().get();
	}
	

	public final void setPerson_id(final int person_id) {
		this.person_idProperty().set(person_id);
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
	

	public final StringProperty fiscal_yearProperty() {
		return this.fiscal_year;
	}
	

	public final String getFiscal_year() {
		return this.fiscal_yearProperty().get();
	}
	

	public final void setFiscal_year(final String fiscal_year) {
		this.fiscal_yearProperty().set(fiscal_year);
	}

	@Override
	public String toString() {
		return "Object_Officer [officer_id=" + officer_id + ", person_id=" + person_id + ", board_year=" + board_year
				+ ", officer_type=" + officer_type + ", fiscal_year=" + fiscal_year + "]";
	}
}
