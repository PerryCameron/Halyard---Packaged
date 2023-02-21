package com.ecsail.dto;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class PhoneDTO {
	
	private IntegerProperty phone_ID;
	private IntegerProperty pid;
	private BooleanProperty isListed;
	private StringProperty phoneNumber;
	private StringProperty phoneType;
	private ObservableList<String> membershiptypes; 
	private ComboBox<String> combo_box;
	public PhoneDTO(Integer phone_ID, Integer pid, Boolean isListed, String phoneNumber, String phoneType) {
		this.phone_ID = new SimpleIntegerProperty(phone_ID);
		this.pid = new SimpleIntegerProperty(pid);
		this.isListed = new SimpleBooleanProperty(isListed);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.phoneType = new SimpleStringProperty(phoneType);
		this.membershiptypes = FXCollections.observableArrayList("Home", "Cell", "Work", "Emergency");
		this.combo_box = new ComboBox<String>(membershiptypes);
		combo_box.setValue("none");
	}
	
	//final ComboBox<String> combo_box

	
	public final IntegerProperty phone_IDProperty() {
		return this.phone_ID;
	}
	

	public ComboBox<String> getCombo_box() {
		return combo_box;
	}

	public void setCombo_box(ComboBox<String> combo_box) {
		this.combo_box = combo_box;
	}

	public final int getPhone_ID() {
		return this.phone_IDProperty().get();
	}
	

	public final void setPhone_ID(final int phone_ID) {
		this.phone_IDProperty().set(phone_ID);
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
	

	public final BooleanProperty isListedProperty() {
		return this.isListed;
	}
	

	public final boolean isIsListed() {
		return this.isListedProperty().get();
	}
	

	public final void setIsListed(final boolean isListed) {
		this.isListedProperty().set(isListed);
	}
	

	public final StringProperty phoneNumberProperty() {
		return this.phoneNumber;
	}
	

	public final String getPhoneNumber() {
		return this.phoneNumberProperty().get();
	}
	

	public final void setPhoneNumber(final String phoneNumber) {
		this.phoneNumberProperty().set(phoneNumber);
	}

	public final StringProperty phoneTypeProperty() {
		return this.phoneType;
	}
	

	public final String getPhoneType() {
		return this.phoneTypeProperty().get();
	}
	

	public final void setPhoneType(final String phoneType) {
		this.phoneTypeProperty().set(phoneType);
	}

	@Override
	public String toString() {
		return "Object_Phone [phone_ID=" + phone_ID + ", pid=" + pid + ", isListed=" + isListed + ", phoneNumber="
				+ phoneNumber + ", phoneType=" + phoneType + ", membershiptypes=" + membershiptypes + ", combo_box="
				+ combo_box + "]";
	}
	
}
