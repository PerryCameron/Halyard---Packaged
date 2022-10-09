package com.ecsail.structures;

import javafx.beans.property.*;

public class MoneyDTO {

	/// when storing integers it's a multiplier when a String the dollar amount
	private IntegerProperty money_id;
	private IntegerProperty ms_id;
	private IntegerProperty fiscal_year;
	private IntegerProperty batch;
	private StringProperty officer_credit;  // these will be big decimal values, but stored in the POJO as string so observable
	private IntegerProperty extra_key;
	private IntegerProperty kayac_shed_key;
	private IntegerProperty sail_loft_key;
	private IntegerProperty sail_school_loft_key;
	private IntegerProperty beach; // this will need to change to string because they used to charge different for types of boats
	private StringProperty wet_slip;
	private IntegerProperty kayac_rack;
	private IntegerProperty kayak_beach_rack;
	private IntegerProperty kayac_shed;
	private IntegerProperty sail_loft;
	private IntegerProperty sail_school_laser_loft;
	private IntegerProperty winter_storage;  // this will need to change to string because in the old days they went by boat feet
	private StringProperty ysc_donation;
	private StringProperty paid;
	private StringProperty total;
	private StringProperty credit;
	private StringProperty balance;
	private StringProperty dues;
	private BooleanProperty committed;
	private BooleanProperty closed;
	private StringProperty other;
	private StringProperty initiation;
	private BooleanProperty supplemental;
	private IntegerProperty work_credit;
	private StringProperty other_credit;

	public MoneyDTO(Integer money_id, Integer ms_id, Integer fiscal_year, Integer batch,
                    String officer_credit, Integer extra_key, Integer kayac_shed_key,
                    Integer sail_loft_key, Integer sail_school_loft_key, Integer beach,
                    String wet_slip, Integer kayac_rack, Integer kayak_beach_rack, Integer kayac_shed, Integer sail_loft,
                    Integer sail_school_laser_loft, Integer winter_storage, String ysc_donation,
                    String paid, String total, String credit, String balance, String dues,
                    Boolean committed, Boolean closed, String other, String initiation, Boolean supplemental, Integer work_credit, String other_credit) {

		this.money_id = new SimpleIntegerProperty(money_id);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.fiscal_year = new SimpleIntegerProperty(fiscal_year);
		this.batch = new SimpleIntegerProperty(batch);
		this.officer_credit = new SimpleStringProperty(officer_credit);
		this.extra_key = new SimpleIntegerProperty(extra_key);
		this.kayac_shed_key = new SimpleIntegerProperty(kayac_shed_key);
		this.sail_loft_key = new SimpleIntegerProperty(sail_loft_key);
		this.sail_school_loft_key = new SimpleIntegerProperty(sail_school_loft_key);
		this.beach = new SimpleIntegerProperty(beach);
		this.wet_slip = new SimpleStringProperty(wet_slip);
		this.kayac_rack = new SimpleIntegerProperty(kayac_rack);
		this.kayak_beach_rack = new SimpleIntegerProperty(kayak_beach_rack);
		this.kayac_shed = new SimpleIntegerProperty(kayac_shed);
		this.sail_loft = new SimpleIntegerProperty(sail_loft);
		this.sail_school_laser_loft = new SimpleIntegerProperty(sail_school_laser_loft);
		this.winter_storage = new SimpleIntegerProperty(winter_storage);
		this.ysc_donation = new SimpleStringProperty(ysc_donation);
		this.paid = new SimpleStringProperty(paid);
		this.total = new SimpleStringProperty(total);
		this.credit = new SimpleStringProperty(credit);
		this.balance = new SimpleStringProperty(balance);
		this.dues = new SimpleStringProperty(dues);
		this.committed = new SimpleBooleanProperty(committed);
		this.closed = new SimpleBooleanProperty(closed);
		this.other = new SimpleStringProperty(other);
		this.initiation = new SimpleStringProperty(initiation);
		this.supplemental = new SimpleBooleanProperty(supplemental);
		this.work_credit = new SimpleIntegerProperty(work_credit);
		this.other_credit = new SimpleStringProperty(other_credit);
	}

	public MoneyDTO() {
		this.money_id = new SimpleIntegerProperty(0);
		this.ms_id = new SimpleIntegerProperty(0);
		this.fiscal_year = new SimpleIntegerProperty(0);
		this.batch = new SimpleIntegerProperty(0);
		this.officer_credit = new SimpleStringProperty("0.00");
		this.extra_key = new SimpleIntegerProperty(0);
		this.kayac_shed_key = new SimpleIntegerProperty(0);
		this.sail_loft_key = new SimpleIntegerProperty(0);
		this.sail_school_loft_key = new SimpleIntegerProperty(0);
		this.beach = new SimpleIntegerProperty(0);
		this.wet_slip = new SimpleStringProperty("0.00");
		this.kayac_rack = new SimpleIntegerProperty(0);
		this.kayak_beach_rack = new SimpleIntegerProperty(0);
		this.kayac_shed = new SimpleIntegerProperty(0);
		this.sail_loft = new SimpleIntegerProperty(0);
		this.sail_school_laser_loft = new SimpleIntegerProperty(0);
		this.winter_storage = new SimpleIntegerProperty(0);
		this.ysc_donation = new SimpleStringProperty("0.00");
		this.paid = new SimpleStringProperty("0.00");
		this.total = new SimpleStringProperty("0.00");
		this.credit = new SimpleStringProperty("0.00");
		this.balance = new SimpleStringProperty("0.00");
		this.dues = new SimpleStringProperty("0.00");
		this.committed = new SimpleBooleanProperty(false);
		this.closed = new SimpleBooleanProperty(false);
		this.other = new SimpleStringProperty("0.00");
		this.initiation = new SimpleStringProperty("0.00");
		this.supplemental = new SimpleBooleanProperty(false);
		this.work_credit = new SimpleIntegerProperty(0);
		this.other_credit = new SimpleStringProperty("0.00");
	}

	public int getMoney_id() {
		return money_id.get();
	}

	public IntegerProperty money_idProperty() {
		return money_id;
	}

	public void setMoney_id(int money_id) {
		this.money_id.set(money_id);
	}

	public int getMs_id() {
		return ms_id.get();
	}

	public IntegerProperty ms_idProperty() {
		return ms_id;
	}

	public void setMs_id(int ms_id) {
		this.ms_id.set(ms_id);
	}

	public int getFiscal_year() {
		return fiscal_year.get();
	}

	public IntegerProperty fiscal_yearProperty() {
		return fiscal_year;
	}

	public void setFiscal_year(int fiscal_year) {
		this.fiscal_year.set(fiscal_year);
	}

	public int getBatch() {
		return batch.get();
	}

	public IntegerProperty batchProperty() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch.set(batch);
	}

	public String getOfficer_credit() {
		return officer_credit.get();
	}

	public StringProperty officer_creditProperty() {
		return officer_credit;
	}

	public void setOfficer_credit(String officer_credit) {
		this.officer_credit.set(officer_credit);
	}

	public int getExtra_key() {
		return extra_key.get();
	}

	public IntegerProperty extra_keyProperty() {
		return extra_key;
	}

	public void setExtra_key(int extra_key) {
		this.extra_key.set(extra_key);
	}

	public int getKayac_shed_key() {
		return kayac_shed_key.get();
	}

	public IntegerProperty kayac_shed_keyProperty() {
		return kayac_shed_key;
	}

	public void setKayac_shed_key(int kayac_shed_key) {
		this.kayac_shed_key.set(kayac_shed_key);
	}

	public int getSail_loft_key() {
		return sail_loft_key.get();
	}

	public IntegerProperty sail_loft_keyProperty() {
		return sail_loft_key;
	}

	public void setSail_loft_key(int sail_loft_key) {
		this.sail_loft_key.set(sail_loft_key);
	}

	public int getSail_school_loft_key() {
		return sail_school_loft_key.get();
	}

	public IntegerProperty sail_school_loft_keyProperty() {
		return sail_school_loft_key;
	}

	public void setSail_school_loft_key(int sail_school_loft_key) {
		this.sail_school_loft_key.set(sail_school_loft_key);
	}

	public int getBeach() {
		return beach.get();
	}

	public IntegerProperty beachProperty() {
		return beach;
	}

	public void setBeach(int beach) {
		this.beach.set(beach);
	}

	public String getWet_slip() {
		return wet_slip.get();
	}

	public StringProperty wet_slipProperty() {
		return wet_slip;
	}

	public void setWet_slip(String wet_slip) {
		this.wet_slip.set(wet_slip);
	}


	public int getKayac_rack() {
		return kayac_rack.get();
	}

	public IntegerProperty kayac_rackProperty() {
		return kayac_rack;
	}

	public void setKayac_rack(int kayac_rack) {
		this.kayac_rack.set(kayac_rack);
	}


	public int getKayak_beach_rack() {
		return kayak_beach_rack.get();
	}

	public IntegerProperty kayak_beach_rackProperty() {
		return kayak_beach_rack;
	}

	public void setKayak_beach_rack(int kayak_beach_rack) { this.kayak_beach_rack.set(kayak_beach_rack); }


	public int getKayac_shed() {
		return kayac_shed.get();
	}

	public IntegerProperty kayac_shedProperty() {
		return kayac_shed;
	}

	public void setKayac_shed(int kayac_shed) {
		this.kayac_shed.set(kayac_shed);
	}

	public int getSail_loft() {
		return sail_loft.get();
	}

	public IntegerProperty sail_loftProperty() {
		return sail_loft;
	}

	public void setSail_loft(int sail_loft) {
		this.sail_loft.set(sail_loft);
	}

	public int getSail_school_laser_loft() {
		return sail_school_laser_loft.get();
	}

	public IntegerProperty sail_school_laser_loftProperty() {
		return sail_school_laser_loft;
	}

	public void setSail_school_laser_loft(int sail_school_laser_loft) {
		this.sail_school_laser_loft.set(sail_school_laser_loft);
	}

	public int getWinter_storage() {
		return winter_storage.get();
	}

	public IntegerProperty winter_storageProperty() {
		return winter_storage;
	}

	public void setWinter_storage(int winter_storage) {
		this.winter_storage.set(winter_storage);
	}

	public String getYsc_donation() {
		return ysc_donation.get();
	}

	public StringProperty ysc_donationProperty() {
		return ysc_donation;
	}

	public void setYsc_donation(String ysc_donation) {
		this.ysc_donation.set(ysc_donation);
	}

	public String getPaid() {
		return paid.get();
	}

	public StringProperty paidProperty() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid.set(paid);
	}

	public String getTotal() {
		return total.get();
	}

	public StringProperty totalProperty() {
		return total;
	}

	public void setTotal(String total) {
		this.total.set(total);
	}

	public String getCredit() {
		return credit.get();
	}

	public StringProperty creditProperty() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit.set(credit);
	}

	public String getBalance() {
		return balance.get();
	}

	public StringProperty balanceProperty() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance.set(balance);
	}

	public String getDues() {
		return dues.get();
	}

	public StringProperty duesProperty() {
		return dues;
	}

	public void setDues(String dues) {
		this.dues.set(dues);
	}

	public boolean isCommitted() {
		return committed.get();
	}

	public BooleanProperty committedProperty() {
		return committed;
	}

	public void setCommitted(boolean committed) {
		this.committed.set(committed);
	}

	public boolean isClosed() {
		return closed.get();
	}

	public BooleanProperty closedProperty() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed.set(closed);
	}

	public String getOther() {
		return other.get();
	}

	public StringProperty otherProperty() {
		return other;
	}

	public void setOther(String other) {
		this.other.set(other);
	}

	public String getInitiation() {
		return initiation.get();
	}

	public StringProperty initiationProperty() {
		return initiation;
	}

	public void setInitiation(String initiation) {
		this.initiation.set(initiation);
	}

	public boolean isSupplemental() {
		return supplemental.get();
	}

	public BooleanProperty supplementalProperty() {
		return supplemental;
	}

	public void setSupplemental(boolean supplemental) {
		this.supplemental.set(supplemental);
	}

	public int getWork_credit() {
		return work_credit.get();
	}

	public IntegerProperty work_creditProperty() {
		return work_credit;
	}

	public void setWork_credit(int work_credit) {
		this.work_credit.set(work_credit);
	}

	public String getOther_credit() {
		return other_credit.get();
	}

	public StringProperty other_creditProperty() {
		return other_credit;
	}

	public void setOther_credit(String other_credit) {
		this.other_credit.set(other_credit);
	}

	public final void clear() {
		setMoney_id(0);
		setMs_id(0);
		setFiscal_year(0);
		setBatch(0);
		setOfficer_credit("0.00");
		setExtra_key(0);
		setKayac_shed_key(0);
		setSail_loft_key(0);
		setSail_school_loft_key(0);
		setBeach(0);
		setWet_slip("0.00");
		setKayac_rack(0);
		setKayak_beach_rack(0);
		setKayac_shed(0);
		setSail_loft(0);
		setSail_school_laser_loft(0);
		setWinter_storage(0);
		setYsc_donation("0.00");
		setPaid("0.00");
		setTotal("0.00");
		setCredit("0.00");
		setBalance("0.00");
		setDues("0.00");
		setCommitted(false);
		setClosed(false);
		setOther("0.00");
		setInitiation("0.00");
		setSupplemental(false);
		setWork_credit(0);
		setOther_credit("0.00");
	}

	@Override
	public String toString() {
		return "MoneyDTO{" +
				"money_id=" + money_id +
				", ms_id=" + ms_id +
				", fiscal_year=" + fiscal_year +
				", batch=" + batch +
				", officer_credit=" + officer_credit +
				", extra_key=" + extra_key +
				", kayac_shed_key=" + kayac_shed_key +
				", sail_loft_key=" + sail_loft_key +
				", sail_school_loft_key=" + sail_school_loft_key +
				", beach=" + beach +
				", wet_slip=" + wet_slip +
				", kayac_rack=" + kayac_rack +
				", kayak_beach_rack=" + kayak_beach_rack +
				", kayac_shed=" + kayac_shed +
				", sail_loft=" + sail_loft +
				", sail_school_laser_loft=" + sail_school_laser_loft +
				", winter_storage=" + winter_storage +
				", ysc_donation=" + ysc_donation +
				", paid=" + paid +
				", total=" + total +
				", credit=" + credit +
				", balance=" + balance +
				", dues=" + dues +
				", committed=" + committed +
				", closed=" + closed +
				", other=" + other +
				", initiation=" + initiation +
				", supplemental=" + supplemental +
				", work_credit=" + work_credit +
				", other_credit=" + other_credit +
				'}';
	}
}
