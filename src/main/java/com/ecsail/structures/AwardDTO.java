package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AwardDTO {
	
	private IntegerProperty awardId;
	private IntegerProperty pid;
	private StringProperty awardYear;
	private StringProperty awardType;


	
	public AwardDTO(Integer awardId, Integer pid, String awardYear, String awardType) {
		this.awardId = new SimpleIntegerProperty(awardId);
		this.pid = new SimpleIntegerProperty(pid);
		this.awardYear = new SimpleStringProperty(awardYear);
		this.awardType = new SimpleStringProperty(awardType);
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
	



	public final IntegerProperty pidProperty() {
		return this.pid;
	}
	



	public final int getPid() {
		return this.pidProperty().get();
	}
	



	public final void setPid(final int pid) {
		this.pidProperty().set(pid);
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
		return "Object_Award [awardId=" + awardId + ", pid=" + pid + ", awardYear=" + awardYear + ", awardType="
				+ awardType + "]";
	}

}
