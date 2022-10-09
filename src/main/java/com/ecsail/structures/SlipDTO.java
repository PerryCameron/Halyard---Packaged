package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SlipDTO {
	
	private IntegerProperty slip_id;
	private IntegerProperty ms_id;
	private StringProperty slipNumber;
	private IntegerProperty subleased_to;
	private StringProperty altText;
	
	public SlipDTO(Integer slip_id, Integer ms_id, String slipNumber,
				   Integer subleased_to, String altText) {
		super();
		this.slip_id = new SimpleIntegerProperty(slip_id);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.slipNumber = new SimpleStringProperty(slipNumber);
		this.subleased_to = new SimpleIntegerProperty(subleased_to);
		this.altText = new SimpleStringProperty(altText);
	}

	public final IntegerProperty slip_idProperty() {
		return this.slip_id;
	}
	public final int getSlip_id() {
		return this.slip_idProperty().get();
	}
	public final void setSlip_id(final int slip_id) {
		this.slip_idProperty().set(slip_id);
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
	

	public final StringProperty slipNumberProperty() {
		return this.slipNumber;
	}
	public final String getSlipNumber() {
		return this.slipNumberProperty().get();
	}
	public final void setSlipNumber(final String slipNumber) {
		this.slipNumberProperty().set(slipNumber);
	}
	

	public final IntegerProperty subleased_toProperty() {
		return this.subleased_to;
	}
	public final int getSubleased_to() {
		return this.subleased_toProperty().get();
	}
	public final void setSubleased_to(final int subleased_to) {
		this.subleased_toProperty().set(subleased_to);
	}

	public final StringProperty getAltTextProperty() { return this.altText; }
	public final String getAltText() { return  this.getAltTextProperty().get(); }
	public final void setAltText(final String altText) { this.getAltTextProperty().set(altText);}

	@Override
	public String toString() {
		return "SlipDTO{" +
				"slip_id=" + slip_id +
				", ms_id=" + ms_id +
				", slipNumber=" + slipNumber +
				", subleased_to=" + subleased_to +
				", altText=" + altText +
				'}';
	}
}
