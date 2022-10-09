package com.ecsail.structures;


import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.sql.select.SqlMoney;

import java.math.BigDecimal;

public class DepositSummaryDTO {
	private DefinedFeeDTO currentDefinedFee;

	private BigDecimal officer_credit;
	private BigDecimal gate_key;
	private BigDecimal kayac_shed_key;
	private BigDecimal sail_loft_key;
	private BigDecimal sail_school_loft_key;
	private BigDecimal beach;
	private BigDecimal wet_slip;
	private BigDecimal kayak_rack;
	private BigDecimal kayak_beach_rack;
	private BigDecimal kayak_shed;
	private BigDecimal sail_loft;
	private BigDecimal sail_school_laser_loft;
	private BigDecimal winter_storage;
	private BigDecimal ysc_donation;
	private BigDecimal credit;
	private BigDecimal dues;
	private BigDecimal other;
	private BigDecimal initiation;
	private BigDecimal balance;
	private int officer_creditNumber;
	private int gate_keyNumber; 
	private int kayac_shed_keyNumber;
	private int sail_loft_keyNumber; 
	private int sail_school_loft_keyNumber; 
	private int beachNumber;
	private int wet_slipNumber;
	private int kayak_rackNumber;
	private int beach_kayak_rackNumber;
	private int kayak_shedNumber;
	private int sail_loftNumber; 
	private int sail_school_laser_loftNumber;
	private int winter_storageNumber;
	private int ysc_donationNumber; 
	private int creditNumber; 
	private int duesNumber; 
	private int otherNumber; 
	private int initiationNumber;
	private int numberOfRecords;
	private int depositNumber;
	private String depositDate;
	private BigDecimal total;
	private BigDecimal paid;

	public DepositSummaryDTO(BigDecimal total, BigDecimal paid, BigDecimal balance, BigDecimal officer_credit, BigDecimal wet_slip, BigDecimal ysc_donation, BigDecimal credit, BigDecimal dues, BigDecimal other, BigDecimal initiation, int gate_keyNumber, int kayac_shed_keyNumber, int sail_loft_keyNumber, int sail_school_loft_keyNumber, int beachNumber, int kayak_rackNumber, int beach_kayak_rackNumber, int kayak_shedNumber, int sail_loftNumber, int sail_school_laser_loftNumber, int winter_storageNumber) {
		this.currentDefinedFee = SqlDefinedFee.getDefinedFeeByYear("2022");
		this.total = total;
		this.paid = paid;
		this.balance = balance;
		this.officer_credit = officer_credit;
		this.wet_slip = wet_slip;
		this.ysc_donation = ysc_donation;
		this.credit = credit;
		this.dues = dues;
		this.other = other;
		this.initiation = initiation;
		this.gate_keyNumber = gate_keyNumber;
		this.kayac_shed_keyNumber = kayac_shed_keyNumber;
		this.sail_loft_keyNumber = sail_loft_keyNumber;
		this.sail_school_loft_keyNumber = sail_school_loft_keyNumber;
		this.beachNumber = beachNumber;
		this.kayak_rackNumber = kayak_rackNumber;
		this.beach_kayak_rackNumber = beach_kayak_rackNumber;
		this.kayak_shedNumber = kayak_shedNumber;
		this.sail_loftNumber = sail_loftNumber;
		this.sail_school_laser_loftNumber = sail_school_laser_loftNumber;
		this.winter_storageNumber = winter_storageNumber;

	}

	public void calculateVariables() {
		this.duesNumber = SqlMoney.getNumberOfMemberDues("2022", "2");
		this.beach = currentDefinedFee.getBeach().multiply(BigDecimal.valueOf(beachNumber));
		this.wet_slipNumber = SqlMoney.getNumberOfMemberDues("2022", "2");
	}

	public DepositSummaryDTO() {
		clear();  // initialized them to 0 except date and number
		this.depositNumber = 0;
		this.depositDate = "";
	}

	public BigDecimal getKayak_beach_rack() {
		return kayak_beach_rack;
	}

	public void setKayak_beach_rack(BigDecimal kayak_beach_rack) {
		this.kayak_beach_rack = kayak_beach_rack;
	}

	public BigDecimal getOfficer_credit() {
		return officer_credit;
	}

	public void setOfficer_credit(BigDecimal officer_credit) {
		this.officer_credit = officer_credit;
	}

	public BigDecimal getGate_key() {
		return gate_key;
	}

	public void setGate_key(BigDecimal gate_key) {
		this.gate_key = gate_key;
	}

	public BigDecimal getKayac_shed_key() {
		return kayac_shed_key;
	}

	public void setKayac_shed_key(BigDecimal kayac_shed_key) {
		this.kayac_shed_key = kayac_shed_key;
	}

	public BigDecimal getSail_loft_key() {
		return sail_loft_key;
	}

	public void setSail_loft_key(BigDecimal sail_loft_key) {
		this.sail_loft_key = sail_loft_key;
	}

	public BigDecimal getSail_school_loft_key() {
		return sail_school_loft_key;
	}

	public void setSail_school_loft_key(BigDecimal sail_school_loft_key) { this.sail_school_loft_key = sail_school_loft_key; }

	public BigDecimal getBeach() {
		return beach;
	}

	public void setBeach(BigDecimal beach) {
		this.beach = beach;
	}

	public BigDecimal getWet_slip() {
		return wet_slip;
	}

	public void setWet_slip(BigDecimal wet_slip) {
		this.wet_slip = wet_slip;
	}

	public BigDecimal getKayak_rack() {
		return kayak_rack;
	}

	public void setKayak_rack(BigDecimal kayak_rack) {
		this.kayak_rack = kayak_rack;
	}

	public BigDecimal getKayak_shed() {
		return kayak_shed;
	}

	public void setKayak_shed(BigDecimal kayak_shed) {
		this.kayak_shed = kayak_shed;
	}

	public BigDecimal getSail_loft() {
		return sail_loft;
	}

	public void setSail_loft(BigDecimal sail_loft) {
		this.sail_loft = sail_loft;
	}

	public BigDecimal getSail_school_laser_loft() {
		return sail_school_laser_loft;
	}

	public void setSail_school_laser_loft(BigDecimal sail_school_laser_loft) {
		this.sail_school_laser_loft = sail_school_laser_loft;
	}

	public BigDecimal getWinter_storage() {
		return winter_storage;
	}

	public void setWinter_storage(BigDecimal winter_storage) {
		this.winter_storage = winter_storage;
	}

	public BigDecimal getYsc_donation() {
		return ysc_donation;
	}

	public void setYsc_donation(BigDecimal ysc_donation) {
		this.ysc_donation = ysc_donation;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getDues() {
		return dues;
	}

	public void setDues(BigDecimal dues) {
		this.dues = dues;
	}

	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	public BigDecimal getInitiation() {
		return initiation;
	}

	public void setInitiation(BigDecimal initiation) {
		this.initiation = initiation;
	}

	public int getOfficer_creditNumber() {
		return officer_creditNumber;
	}

	public void setOfficer_creditNumber(int officer_creditNumber) {
		this.officer_creditNumber = officer_creditNumber;
	}

	public int getGate_keyNumber() {
		return gate_keyNumber;
	}

	public void setGate_keyNumber(int gate_keyNumber) {
		this.gate_keyNumber = gate_keyNumber;
	}

	public int getKayac_shed_keyNumber() {
		return kayac_shed_keyNumber;
	}

	public void setKayac_shed_keyNumber(int kayac_shed_keyNumber) {
		this.kayac_shed_keyNumber = kayac_shed_keyNumber;
	}

	public int getSail_loft_keyNumber() {
		return sail_loft_keyNumber;
	}

	public void setSail_loft_keyNumber(int sail_loft_keyNumber) {
		this.sail_loft_keyNumber = sail_loft_keyNumber;
	}

	public int getSail_school_loft_keyNumber() {
		return sail_school_loft_keyNumber;
	}

	public void setSail_school_loft_keyNumber(int sail_school_loft_keyNumber) {
		this.sail_school_loft_keyNumber = sail_school_loft_keyNumber;
	}

	public int getBeachNumber() {
		return beachNumber;
	}

	public void setBeachNumber(int beachNumber) {
		this.beachNumber = beachNumber;
	}

	public int getWet_slipNumber() {
		return wet_slipNumber;
	}

	public void setWet_slipNumber(int wet_slipNumber) {
		this.wet_slipNumber = wet_slipNumber;
	}

	public int getKayak_rackNumber() {
		return kayak_rackNumber;
	}

	public int getBeach_kayak_rackNumber() {
		return beach_kayak_rackNumber;
	}

	public void setBeach_kayak_rackNumber(int beach_kayak_rackNumber) {
		this.beach_kayak_rackNumber = beach_kayak_rackNumber;
	}

	public void setKayak_rackNumber(int kayak_rackNumber) {
		this.kayak_rackNumber = kayak_rackNumber;
	}

	public int getKayak_shedNumber() {
		return kayak_shedNumber;
	}

	public void setKayak_shedNumber(int kayak_shedNumber) {
		this.kayak_shedNumber = kayak_shedNumber;
	}

	public int getSail_loftNumber() {
		return sail_loftNumber;
	}

	public void setSail_loftNumber(int sail_loftNumber) {
		this.sail_loftNumber = sail_loftNumber;
	}

	public int getSail_school_laser_loftNumber() {
		return sail_school_laser_loftNumber;
	}

	public void setSail_school_laser_loftNumber(int sail_school_laser_loftNumber) {
		this.sail_school_laser_loftNumber = sail_school_laser_loftNumber;
	}

	public int getWinter_storageNumber() {
		return winter_storageNumber;
	}

	public void setWinter_storageNumber(int winter_storageNumber) {
		this.winter_storageNumber = winter_storageNumber;
	}

	public int getYsc_donationNumber() {
		return ysc_donationNumber;
	}

	public void setYsc_donationNumber(int ysc_donationNumber) {
		this.ysc_donationNumber = ysc_donationNumber;
	}

	public int getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(int creditNumber) {
		this.creditNumber = creditNumber;
	}

	public int getDuesNumber() {
		return duesNumber;
	}

	public void setDuesNumber(int duesNumber) {
		this.duesNumber = duesNumber;
	}

	public int getOtherNumber() {
		return otherNumber;
	}

	public void setOtherNumber(int otherNumber) {
		this.otherNumber = otherNumber;
	}

	public int getInitiationNumber() {
		return initiationNumber;
	}

	public void setInitiationNumber(int initiationNumber) {
		this.initiationNumber = initiationNumber;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public int getDepositNumber() {
		return depositNumber;
	}

	public void setDepositNumber(int depositNumber) {
		this.depositNumber = depositNumber;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public void clear() {
		this.officer_credit = BigDecimal.valueOf(0.00);
		this.gate_key = BigDecimal.valueOf(0.00);
		this.kayac_shed_key = BigDecimal.valueOf(0.00);
		this.sail_loft_key = BigDecimal.valueOf(0.00);
		this.sail_school_loft_key = BigDecimal.valueOf(0.00);
		this.beach = BigDecimal.valueOf(0.00);
		this.wet_slip = BigDecimal.valueOf(0.00);
		this.kayak_rack = BigDecimal.valueOf(0.00);
		this.kayak_beach_rack = BigDecimal.valueOf(0.00);
		this.kayak_shed = BigDecimal.valueOf(0.00);
		this.sail_loft = BigDecimal.valueOf(0.00);
		this.sail_school_laser_loft = BigDecimal.valueOf(0.00);
		this.winter_storage = BigDecimal.valueOf(0.00);
		this.ysc_donation = BigDecimal.valueOf(0.00);
		this.credit = BigDecimal.valueOf(0.00);
		this.dues = BigDecimal.valueOf(0.00);
		this.other = BigDecimal.valueOf(0.00);
		this.initiation = BigDecimal.valueOf(0.00);
		this.officer_creditNumber = 0;
		this.gate_keyNumber = 0;
		this.kayac_shed_keyNumber = 0;
		this.sail_loft_keyNumber = 0;
		this.sail_school_loft_keyNumber = 0;
		this.beachNumber = 0;
		this.wet_slipNumber = 0;
		this.kayak_rackNumber = 0;
		this.kayak_shedNumber = 0;
		this.sail_loftNumber = 0;
		this.sail_school_laser_loftNumber = 0;
		this.winter_storageNumber = 0;
		this.ysc_donationNumber = 0;
		this.creditNumber = 0;
		this.duesNumber = 0;
		this.otherNumber = 0;
		this.initiationNumber = 0;
		this.numberOfRecords =0;
		this.paid = BigDecimal.valueOf(0.00);;
		//this.depositNumber = 0;
		//this.depositDate = "";
		this.total = BigDecimal.valueOf(0.00);;
	}

	@Override
	public String toString() {
		return "DepositSummaryDTO{" +
				"officer_credit=" + officer_credit +
				", gate_key=" + gate_key +
				", kayac_shed_key=" + kayac_shed_key +
				", sail_loft_key=" + sail_loft_key +
				", sail_school_loft_key=" + sail_school_loft_key +
				", beach=" + beach +
				", wet_slip=" + wet_slip +
				", kayak_rack=" + kayak_rack +
				", kayak_beach_rack=" + kayak_beach_rack +
				", kayak_shed=" + kayak_shed +
				", sail_loft=" + sail_loft +
				", sail_school_laser_loft=" + sail_school_laser_loft +
				", winter_storage=" + winter_storage +
				", ysc_donation=" + ysc_donation +
				", credit=" + credit +
				", dues=" + dues +
				", other=" + other +
				", initiation=" + initiation +
				", officer_creditNumber=" + officer_creditNumber +
				", gate_keyNumber=" + gate_keyNumber +
				", kayac_shed_keyNumber=" + kayac_shed_keyNumber +
				", sail_loft_keyNumber=" + sail_loft_keyNumber +
				", sail_school_loft_keyNumber=" + sail_school_loft_keyNumber +
				", beachNumber=" + beachNumber +
				", wet_slipNumber=" + wet_slipNumber +
				", kayak_rackNumber=" + kayak_rackNumber +
				", kayak_shedNumber=" + kayak_shedNumber +
				", sail_loftNumber=" + sail_loftNumber +
				", sail_school_laser_loftNumber=" + sail_school_laser_loftNumber +
				", winter_storageNumber=" + winter_storageNumber +
				", ysc_donationNumber=" + ysc_donationNumber +
				", creditNumber=" + creditNumber +
				", duesNumber=" + duesNumber +
				", otherNumber=" + otherNumber +
				", initiationNumber=" + initiationNumber +
				", numberOfRecords=" + numberOfRecords +
				", depositNumber=" + depositNumber +
				", depositDate='" + depositDate + '\'' +
				", total=" + total +
				", paid=" + paid +
				'}';
	}
}
