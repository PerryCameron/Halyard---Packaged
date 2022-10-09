package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MembershipIdDTO {
	private IntegerProperty mid;
	private SimpleStringProperty fiscal_Year;
	private IntegerProperty ms_id;
	private SimpleStringProperty membership_id;
	private SimpleBooleanProperty isRenew;
	private SimpleStringProperty mem_type;
	private SimpleBooleanProperty selected;
	private SimpleBooleanProperty isLateRenew;
	
	public MembershipIdDTO(Integer mid, String fiscal_Year, Integer ms_id, String membership_id,
						   Boolean isRenew, String mem_type, Boolean selected, Boolean isLateRenew) {
		this.mid = new SimpleIntegerProperty(mid);
		this.fiscal_Year = new SimpleStringProperty(fiscal_Year);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.membership_id = new SimpleStringProperty(membership_id);
		this.isRenew = new SimpleBooleanProperty(isRenew);
		this.mem_type = new SimpleStringProperty(mem_type);
		this.selected = new SimpleBooleanProperty(selected);
		this.isLateRenew = new SimpleBooleanProperty(isLateRenew);
	}

	public final IntegerProperty midProperty() {
		return this.mid;
	}
	

	public final int getMid() {
		return this.midProperty().get();
	}
	

	public final void setMid(final int mid) {
		this.midProperty().set(mid);
	}
	

	public final SimpleStringProperty fiscal_YearProperty() {
		return this.fiscal_Year;
	}
	

	public final String getFiscal_Year() {
		return this.fiscal_YearProperty().get();
	}
	

	public final void setFiscal_Year(final String fiscal_Year) {
		this.fiscal_YearProperty().set(fiscal_Year);
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
	

	public final SimpleStringProperty membership_idProperty() {
		return this.membership_id;
	}
	

	public final String getMembership_id() {
		return this.membership_idProperty().get();
	}
	

	public final void setMembership_id(final String membership_id) {
		this.membership_idProperty().set(membership_id);
	}
	

	public final SimpleBooleanProperty isRenewProperty() {
		return this.isRenew;
	}
	

	public final boolean isRenew() {
		return this.isRenewProperty().get();
	}
	

	public final void setIsRenew(final boolean isRenew) {
		this.isRenewProperty().set(isRenew);
	}
	

	public final SimpleStringProperty mem_typeProperty() {
		return this.mem_type;
	}
	

	public final String getMem_type() {
		return this.mem_typeProperty().get();
	}
	

	public final void setMem_type(final String mem_type) {
		this.mem_typeProperty().set(mem_type);
	}
	

	public final SimpleBooleanProperty selectedProperty() {
		return this.selected;
	}
	

	public final boolean isSelected() {
		return this.selectedProperty().get();
	}
	

	public final void setSelected(final boolean selected) {
		this.selectedProperty().set(selected);
	}

	public final SimpleBooleanProperty isLateRenewProperty() {
		return this.isLateRenew;
	}
	

	public final boolean isLateRenew() {
		return this.isLateRenewProperty().get();
	}
	

	public final void setIsLateRenew(final boolean isLateRenew) {
		this.isLateRenewProperty().set(isLateRenew);
	}

	@Override
	public String toString() {
		return "Object_MembershipId{" +
				"mid=" + mid +
				", fiscal_Year=" + fiscal_Year +
				", ms_id=" + ms_id +
				", membership_id=" + membership_id +
				", isRenew=" + isRenew +
				", mem_type=" + mem_type +
				", selected=" + selected +
				", isLateRenew=" + isLateRenew +
				'}';
	}
}
