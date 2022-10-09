package com.ecsail.structures;


import java.math.BigDecimal;

public class DefinedFeeDTO {

	private Integer fiscal_year;
	private BigDecimal dues_regular;
	private BigDecimal dues_family;
	private BigDecimal dues_lake_associate;
	private BigDecimal dues_social;
	private BigDecimal initiation;
	private BigDecimal wet_slip;
	private BigDecimal beach;
	private BigDecimal winter_storage;
	private BigDecimal main_gate_key;
	private BigDecimal sail_loft;
	private BigDecimal sail_loft_key;
	private BigDecimal sail_school_laser_loft;
	private BigDecimal sail_school_loft_key;
	private BigDecimal kayak_rack;
	private BigDecimal kayak_beach_rack;
	private BigDecimal kayak_shed;
	private BigDecimal kayak_shed_key;
	private BigDecimal work_credit;

	public DefinedFeeDTO(Integer fiscal_year, BigDecimal dues_regular, BigDecimal dues_family, BigDecimal dues_lake_associate, BigDecimal dues_social, BigDecimal initiation, BigDecimal wet_slip, BigDecimal beach, BigDecimal winter_storage, BigDecimal main_gate_key, BigDecimal sail_loft, BigDecimal sail_loft_key, BigDecimal sail_school_laser_loft, BigDecimal sail_school_loft_key, BigDecimal kayak_rack, BigDecimal kayak_beach_rack, BigDecimal kayak_shed, BigDecimal kayak_shed_key, BigDecimal work_credit) {
		this.fiscal_year = fiscal_year;
		this.dues_regular = dues_regular;
		this.dues_family = dues_family;
		this.dues_lake_associate = dues_lake_associate;
		this.dues_social = dues_social;
		this.initiation = initiation;
		this.wet_slip = wet_slip;
		this.beach = beach;
		this.winter_storage = winter_storage;
		this.main_gate_key = main_gate_key;
		this.sail_loft = sail_loft;
		this.sail_loft_key = sail_loft_key;
		this.sail_school_laser_loft = sail_school_laser_loft;
		this.sail_school_loft_key = sail_school_loft_key;
		this.kayak_rack = kayak_rack;
		this.kayak_beach_rack = kayak_beach_rack;
		this.kayak_shed = kayak_shed;
		this.kayak_shed_key = kayak_shed_key;
		this.work_credit = work_credit;
	}

	public DefinedFeeDTO() {
		// default constructor
	}

	public BigDecimal getKayak_beach_rack() {
		return kayak_beach_rack;
	}

	public void setKayak_beach_rack(BigDecimal kayak_beach_rack) {
		this.kayak_beach_rack = kayak_beach_rack;
	}

	public Integer getFiscal_year() {
		return fiscal_year;
	}

	public void setFiscal_year(Integer fiscal_year) {
		this.fiscal_year = fiscal_year;
	}

	public BigDecimal getDues_regular() {
		return dues_regular;
	}

	public void setDues_regular(BigDecimal dues_regular) {
		this.dues_regular = dues_regular;
	}

	public BigDecimal getDues_family() {
		return dues_family;
	}

	public void setDues_family(BigDecimal dues_family) {
		this.dues_family = dues_family;
	}

	public BigDecimal getDues_lake_associate() {
		return dues_lake_associate;
	}

	public void setDues_lake_associate(BigDecimal dues_lake_associate) {
		this.dues_lake_associate = dues_lake_associate;
	}

	public BigDecimal getDues_social() {
		return dues_social;
	}

	public void setDues_social(BigDecimal dues_social) {
		this.dues_social = dues_social;
	}

	public BigDecimal getInitiation() {
		return initiation;
	}

	public void setInitiation(BigDecimal initiation) {
		this.initiation = initiation;
	}

	public BigDecimal getWet_slip() {
		return wet_slip;
	}

	public void setWet_slip(BigDecimal wet_slip) {
		this.wet_slip = wet_slip;
	}

	public BigDecimal getBeach() {
		return beach;
	}

	public void setBeach(BigDecimal beach) {
		this.beach = beach;
	}

	public BigDecimal getWinter_storage() {
		return winter_storage;
	}

	public void setWinter_storage(BigDecimal winter_storage) {
		this.winter_storage = winter_storage;
	}

	public BigDecimal getMain_gate_key() {
		return main_gate_key;
	}

	public void setMain_gate_key(BigDecimal main_gate_key) {
		this.main_gate_key = main_gate_key;
	}

	public BigDecimal getSail_loft() {
		return sail_loft;
	}

	public void setSail_loft(BigDecimal sail_loft) {
		this.sail_loft = sail_loft;
	}

	public BigDecimal getSail_loft_key() {
		return sail_loft_key;
	}

	public void setSail_loft_key(BigDecimal sail_loft_key) {
		this.sail_loft_key = sail_loft_key;
	}

	public BigDecimal getSail_school_laser_loft() {
		return sail_school_laser_loft;
	}

	public void setSail_school_laser_loft(BigDecimal sail_school_laser_loft) {
		this.sail_school_laser_loft = sail_school_laser_loft;
	}

	public BigDecimal getSail_school_loft_key() {
		return sail_school_loft_key;
	}

	public void setSail_school_loft_key(BigDecimal sail_school_loft_key) {
		this.sail_school_loft_key = sail_school_loft_key;
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

	public BigDecimal getKayak_shed_key() {
		return kayak_shed_key;
	}

	public void setKayak_shed_key(BigDecimal kayak_shed_key) {
		this.kayak_shed_key = kayak_shed_key;
	}

	public BigDecimal getWork_credit() {
		return work_credit;
	}

	public void setWork_credit(BigDecimal work_credit) {
		this.work_credit = work_credit;
	}

	public final void set(Integer fiscal_year, BigDecimal dues_regular, BigDecimal dues_family,
						  BigDecimal dues_lake_associate, BigDecimal dues_social, BigDecimal initiation, BigDecimal wet_slip, BigDecimal beach,
						  BigDecimal winter_storage, BigDecimal main_gate_key, BigDecimal sail_loft, BigDecimal sail_loft_key, BigDecimal sail_school_laser_loft,
						  BigDecimal sail_school_loft_key, BigDecimal kayak_rack, BigDecimal kayak_beach_rack, BigDecimal kayak_shed, BigDecimal kayak_shed_key, BigDecimal work_credit) {
		
		setFiscal_year(fiscal_year);
		setDues_regular(dues_regular);
		setDues_family(dues_family);
		setDues_lake_associate(dues_lake_associate);
		setDues_social(dues_social);
		setInitiation(initiation);
		setWet_slip(wet_slip);
		setBeach(beach);
		setWinter_storage(winter_storage);
		setMain_gate_key(main_gate_key);
		setSail_loft(sail_loft);
		setSail_loft_key(sail_loft_key);
		setSail_school_laser_loft(sail_school_laser_loft);
		setSail_school_loft_key(sail_school_loft_key);
		setKayak_rack(kayak_rack);
		setKayak_beach_rack(kayak_beach_rack);
		setKayak_shed(kayak_shed);
		setKayak_shed_key(kayak_shed_key);
		setWork_credit(work_credit);
	}
	
	public final void clear() {
		setFiscal_year(0);
		setDues_regular(BigDecimal.valueOf(0.00));
		setDues_family(BigDecimal.valueOf(0.00));
		setDues_lake_associate(BigDecimal.valueOf(0.00));
		setDues_social(BigDecimal.valueOf(0.00));
		setInitiation(BigDecimal.valueOf(0.00));
		setWet_slip(BigDecimal.valueOf(0.00));
		setBeach(BigDecimal.valueOf(0.00));
		setWinter_storage(BigDecimal.valueOf(0.00));
		setMain_gate_key(BigDecimal.valueOf(0.00));
		setSail_loft(BigDecimal.valueOf(0.00));
		setSail_loft_key(BigDecimal.valueOf(0.00));
		setSail_school_laser_loft(BigDecimal.valueOf(0.00));
		setSail_school_loft_key(BigDecimal.valueOf(0.00));
		setKayak_rack(BigDecimal.valueOf(0.00));
		setKayak_beach_rack(BigDecimal.valueOf(0.00));
		setKayak_shed(BigDecimal.valueOf(0.00));
		setKayak_shed_key(BigDecimal.valueOf(0.00));
		setWork_credit(BigDecimal.valueOf(0.00));
	}

	@Override
	public String toString() {
		return "DefinedFeeDTO{" +
				"fiscal_year=" + fiscal_year +
				", dues_regular=" + dues_regular +
				", dues_family=" + dues_family +
				", dues_lake_associate=" + dues_lake_associate +
				", dues_social=" + dues_social +
				", initiation=" + initiation +
				", wet_slip=" + wet_slip +
				", beach=" + beach +
				", winter_storage=" + winter_storage +
				", main_gate_key=" + main_gate_key +
				", sail_loft=" + sail_loft +
				", sail_loft_key=" + sail_loft_key +
				", sail_school_laser_loft=" + sail_school_laser_loft +
				", sail_school_loft_key=" + sail_school_loft_key +
				", kayak_rack=" + kayak_rack +
				", kayak_beach_rack=" + kayak_beach_rack +
				", kayak_shed=" + kayak_shed +
				", kayak_shed_key=" + kayak_shed_key +
				", work_credit=" + work_credit +
				'}';
	}
}
