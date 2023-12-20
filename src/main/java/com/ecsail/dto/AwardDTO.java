package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AwardDTO {
	
	private IntegerProperty awardId;
	private IntegerProperty pId;
	private StringProperty awardYear;
	private StringProperty awardType;


	
	public AwardDTO(Integer awardId, Integer pId, String awardYear, String awardType) {
		this.awardId = new SimpleIntegerProperty(awardId);
		this.pId = new SimpleIntegerProperty(pId);
		this.awardYear = new SimpleStringProperty(awardYear);
		this.awardType = new SimpleStringProperty(awardType);
	}

	public AwardDTO(Integer pId, String awardYear) {
		this.awardId = new SimpleIntegerProperty(0);
		this.pId = new SimpleIntegerProperty(pId);
		this.awardYear = new SimpleStringProperty(awardYear);
		this.awardType = new SimpleStringProperty("New Award");
	}

	public final IntegerProperty awardIdProperty() {
		return this.awardId;
	}

	public final int getAwardId() {
		return this.awardIdProperty().get();
	}

	public final void setAwardId(final int awardId) {
		this.awardIdProperty().set(awardId);
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
	



	public final StringProperty awardYearProperty() {
		return this.awardYear;
	}
	



	public final String getAwardYear() {
		return this.awardYearProperty().get();
	}
	



	public final void setAwardYear(final String awardYear) {
		this.awardYearProperty().set(awardYear);
	}
	



	public final StringProperty awardTypeProperty() {
		return this.awardType;
	}
	



	public final String getAwardType() {
		return this.awardTypeProperty().get();
	}
	



	public final void setAwardType(final String awardType) {
		this.awardTypeProperty().set(awardType);
	}



	@Override
	public String toString() {
		return "Object_Award [awardId=" + awardId + ", pId=" + pId + ", awardYear=" + awardYear + ", awardType="
				+ awardType + "]";
	}

}
