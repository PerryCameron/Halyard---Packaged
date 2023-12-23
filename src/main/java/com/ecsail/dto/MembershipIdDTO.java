package com.ecsail.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MembershipIdDTO {
	private IntegerProperty mId;
	private SimpleStringProperty fiscalYear;
	private IntegerProperty msId;
	private SimpleStringProperty membershipId;
	private SimpleBooleanProperty isRenew;
	private SimpleStringProperty memType;
	private SimpleBooleanProperty selected;
	private SimpleBooleanProperty isLateRenew;
	
	public MembershipIdDTO(Integer mId, String fiscalYear, Integer msId, String membershipId,
						   Boolean isRenew, String memType, Boolean selected, Boolean isLateRenew) {
		this.mId = new SimpleIntegerProperty(mId);
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
		this.msId = new SimpleIntegerProperty(msId);
		this.membershipId = new SimpleStringProperty(membershipId);
		this.isRenew = new SimpleBooleanProperty(isRenew);
		this.memType = new SimpleStringProperty(memType);
		this.selected = new SimpleBooleanProperty(selected);
		this.isLateRenew = new SimpleBooleanProperty(isLateRenew);
	}

	public MembershipIdDTO(String fiscalYear, Integer msId, String membershipId, String memType) {
		this.mId = new SimpleIntegerProperty(0);
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
		this.msId = new SimpleIntegerProperty(msId);
		this.membershipId = new SimpleStringProperty(membershipId);
		this.isRenew = new SimpleBooleanProperty(false);
		this.memType = new SimpleStringProperty(memType);
		this.selected = new SimpleBooleanProperty(false);
		this.isLateRenew = new SimpleBooleanProperty(false);
	}

	public MembershipIdDTO(String fiscalYear, Integer msId, String membershipId) {
		this.mId = new SimpleIntegerProperty(0);
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
		this.msId = new SimpleIntegerProperty(msId);
		this.membershipId = new SimpleStringProperty(membershipId);
		this.isRenew = new SimpleBooleanProperty(true);
		this.memType = new SimpleStringProperty("RM");
		this.selected = new SimpleBooleanProperty(false);
		this.isLateRenew = new SimpleBooleanProperty(false);
	}
//	(0, String.valueOf(Year.now().getValue()), membershipListDTO.getMsId(), membership_id + "", true,
//			"RM", false, false)


	public final IntegerProperty mIdProperty() {
		return this.mId;
	}
	

	public final int getmId() {
		return this.mIdProperty().get();
	}
	

	public final void setmId(final int mId) {
		this.mIdProperty().set(mId);
	}
	

	public final SimpleStringProperty fiscalYearProperty() {
		return this.fiscalYear;
	}
	

	public final String getFiscalYear() {
		return this.fiscalYearProperty().get();
	}
	

	public final void setFiscalYear(final String fiscalYear) {
		this.fiscalYearProperty().set(fiscalYear);
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
	

	public final SimpleStringProperty membershipIdProperty() {
		return this.membershipId;
	}
	

	public final String getMembershipId() {
		return this.membershipIdProperty().get();
	}
	

	public final void setMembershipId(final String membershipId) {
		this.membershipIdProperty().set(membershipId);
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
	

	public final SimpleStringProperty memTypeProperty() {
		return this.memType;
	}
	

	public final String getMemType() {
		return this.memTypeProperty().get();
	}
	

	public final void setMemType(final String memType) {
		this.memTypeProperty().set(memType);
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
				"mid=" + mId +
				", fiscal_Year=" + fiscalYear +
				", ms_id=" + msId +
				", membership_id=" + membershipId +
				", isRenew=" + isRenew +
				", mem_type=" + memType +
				", selected=" + selected +
				", isLateRenew=" + isLateRenew +
				'}';
	}
}
