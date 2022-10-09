package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaidDuesDTO extends MoneyDTO {
	private final StringProperty f_name;
	private final StringProperty l_name;
	private final IntegerProperty membershipId;  // Member ID used in real life
	
	public PaidDuesDTO(Integer money_id, Integer ms_id, Integer fiscal_year, Integer batch, String officer_credit,
                       Integer extra_key, Integer kayak_shed_key, Integer sail_loft_key, Integer sail_school_loft_key,
                       Integer beach, String wet_slip, Integer kayak_rack, Integer kayak_beach_rack, Integer kayak_shed, Integer sail_loft,
                       Integer sail_school_laser_loft, Integer winter_storage, String ysc_donation, String paid, String total,
                       String credit, String balance, String dues, Boolean committed, Boolean closed, String other, String initiation,
                       Boolean supplemental, Integer work_credit, String other_credit, String f_name, String l_name, Integer membershipId) {
		
		super(money_id,
				ms_id,
				fiscal_year,
				batch,
				officer_credit,
				extra_key,
				kayak_shed_key,
				sail_loft_key,
				sail_school_loft_key,
				beach,
				wet_slip,
				kayak_rack,
				kayak_beach_rack,
				kayak_shed,
				sail_loft,
				sail_school_laser_loft,
				winter_storage,
				ysc_donation,
				paid,
				total,
				credit,
				balance,
				dues,
				committed,
				closed,
				other,
				initiation,
				supplemental,
				work_credit,
				other_credit);
		this.membershipId = new SimpleIntegerProperty(membershipId);
		this.f_name = new SimpleStringProperty(f_name);
		this.l_name = new SimpleStringProperty(l_name);
	}

	public final IntegerProperty membershipIdProperty() {
		return this.membershipId;
	}

	public final int getMembershipId() {
		return this.membershipIdProperty().get();
	}

	public final void setMembershipId(final int membershipId) {
		this.membershipIdProperty().set(membershipId);
	}

	public final StringProperty f_nameProperty() {
		return this.f_name;
	}

	public final String getF_name() {
		return this.f_nameProperty().get();
	}

	public final void setF_name(final String f_name) {
		this.f_nameProperty().set(f_name);
	}

	public final StringProperty l_nameProperty() {
		return this.l_name;
	}

	public final String getL_name() {
		return this.l_nameProperty().get();
	}

	public final void setL_name(final String l_name) {
		this.l_nameProperty().set(l_name);
	}

	@Override
	public String toString() {
		return "PaidDuesDTO{" +
				"f_name=" + f_name +
				", l_name=" + l_name +
				", membershipId=" + membershipId +
				"} " + super.toString();
	}
}
