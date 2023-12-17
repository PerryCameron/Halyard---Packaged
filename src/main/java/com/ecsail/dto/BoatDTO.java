package com.ecsail.dto;

import com.ecsail.annotation.ColumnName;
import javafx.beans.property.*;

public class BoatDTO {
	@ColumnName(value = "BOAT_ID")
	protected IntegerProperty boatId;
	protected IntegerProperty msId;
	@ColumnName(value = "MANUFACTURER")
	protected StringProperty manufacturer;
	@ColumnName(value = "MANUFACTURE_YEAR")
	protected StringProperty manufactureYear;
	@ColumnName(value = "REGISTRATION_NUM")
	protected StringProperty registrationNum;
	@ColumnName(value = "MODEL")
	protected StringProperty model;
	@ColumnName(value = "BOAT_NAME")
	protected StringProperty boatName;
	@ColumnName(value = "SAIL_NUMBER")
	protected StringProperty sailNumber;
	@ColumnName(value = "HAS_TRAILER")
	protected BooleanProperty hasTrailer;
	@ColumnName(value = "LENGTH")
	protected StringProperty loa;
	@ColumnName(value = "WEIGHT")
	protected StringProperty displacement;
	@ColumnName(value = "KEEL")
	protected StringProperty keel;
	@ColumnName(value = "PHRF")
	protected StringProperty phrf;
	@ColumnName(value = "DRAFT")
	protected StringProperty draft;
	@ColumnName(value = "BEAM")
	protected StringProperty beam;
	@ColumnName(value = "LWL")
	protected StringProperty lwl;
	@ColumnName(value = "AUX")
	protected BooleanProperty aux;
	
	public BoatDTO(Integer boatId, Integer msId, String manufacturer, String manufactureYear,
				   String registrationNum, String model, String boatName, String sailNumber,
				   Boolean hasTrailer, String loa, String displacement, String keel, String phrf,
				   String draft, String beam, String lwl, Boolean aux) {

		this.boatId = new SimpleIntegerProperty(boatId);
		this.msId = new SimpleIntegerProperty(msId);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.manufactureYear = new SimpleStringProperty(manufactureYear);
		this.registrationNum = new SimpleStringProperty(registrationNum);
		this.model = new SimpleStringProperty(model);
		this.boatName = new SimpleStringProperty(boatName);
		this.sailNumber = new SimpleStringProperty(sailNumber);
		this.hasTrailer = new SimpleBooleanProperty(hasTrailer);
		this.loa = new SimpleStringProperty(loa);
		this.displacement = new SimpleStringProperty(displacement);
		this.keel = new SimpleStringProperty(keel);
		this.phrf = new SimpleStringProperty(phrf);
		this.draft = new SimpleStringProperty(draft);
		this.beam = new SimpleStringProperty(beam);
		this.lwl = new SimpleStringProperty(lwl);
		this.aux = new SimpleBooleanProperty(aux);
	}

	public BoatDTO(Integer msId) {
		this.boatId = new SimpleIntegerProperty(0);
		this.msId = new SimpleIntegerProperty(msId);
		this.manufacturer = new SimpleStringProperty("");
		this.manufactureYear = new SimpleStringProperty("");
		this.registrationNum = new SimpleStringProperty("");
		this.model = new SimpleStringProperty("");
		this.boatName = new SimpleStringProperty("");
		this.sailNumber = new SimpleStringProperty("");
		this.hasTrailer = new SimpleBooleanProperty(false);
		this.loa = new SimpleStringProperty("");
		this.displacement = new SimpleStringProperty("");
		this.keel = new SimpleStringProperty("");
		this.phrf = new SimpleStringProperty("");
		this.draft = new SimpleStringProperty("");
		this.beam = new SimpleStringProperty("");
		this.lwl = new SimpleStringProperty("");
		this.aux = new SimpleBooleanProperty(false);
	}



	public BoatDTO() {
		// TODO Auto-generated constructor stub
	}

	public BooleanProperty auxProperty() {
		return this.aux;
	}

	public boolean isAux() {
		return auxProperty().get();
	}

	public void setAux(boolean aux) {
		this.auxProperty().set(aux);
	}

	public final IntegerProperty boatIdProperty() {
		return this.boatId;
	}

	public final int getBoatId() {
		return this.boatIdProperty().get();
	}
	

	public final void setBoatId(final int boatId) {
		this.boatIdProperty().set(boatId);
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
	

	public final StringProperty manufacturerProperty() {
		return this.manufacturer;
	}
	

	public final String getManufacturer() {
		return this.manufacturerProperty().get();
	}
	

	public final void setManufacturer(final String manufacturer) {
		this.manufacturerProperty().set(manufacturer);
	}
	

	public final StringProperty manufactureYearProperty() {
		return this.manufactureYear;
	}
	

	public final String getManufactureYear() {
		return this.manufactureYearProperty().get();
	}
	

	public final void setManufactureYear(final String manufactureYear) {
		this.manufactureYearProperty().set(manufactureYear);
	}
	

	public final StringProperty registrationNumProperty() {
		return this.registrationNum;
	}
	

	public final String getRegistrationNum() {
		return this.registrationNumProperty().get();
	}
	

	public final void setRegistrationNum(final String registrationNum) {
		this.registrationNumProperty().set(registrationNum);
	}
	

	public final StringProperty modelProperty() {
		return this.model;
	}
	

	public final String getModel() {
		return this.modelProperty().get();
	}
	

	public final void setModel(final String model) {
		this.modelProperty().set(model);
	}
	

	public final StringProperty boatNameProperty() {
		return this.boatName;
	}
	

	public final String getBoatName() {
		return this.boatNameProperty().get();
	}
	

	public final void setBoatName(final String boatName) {
		this.boatNameProperty().set(boatName);
	}
	

	public final StringProperty sailNumberProperty() {
		return this.sailNumber;
	}
	

	public final String getSailNumber() {
		return this.sailNumberProperty().get();
	}
	

	public final void setSailNumber(final String sailNumber) {
		this.sailNumberProperty().set(sailNumber);
	}
	

	public final BooleanProperty hasTrailerProperty() {
		return this.hasTrailer;
	}
	

	public final boolean hasTrailer() {
		return this.hasTrailerProperty().get();
	}
	

	public final void setHasTrailer(final boolean hasTrailer) {
		this.hasTrailerProperty().set(hasTrailer);
	}
	

	public final StringProperty loaProperty() {
		return this.loa;
	}
	

	public final String getLoa() {
		return this.loaProperty().get();
	}
	

	public final void setLoa(final String loa) {
		this.loaProperty().set(loa);
	}
	

	public final StringProperty displacementProperty() {
		return this.displacement;
	}
	

	public final String getDisplacement() {
		return this.displacementProperty().get();
	}
	

	public final void setDisplacement(final String displacement) {
		this.displacementProperty().set(displacement);
	}
	

	public final StringProperty keelProperty() {
		return this.keel;
	}
	

	public final String getKeel() {
		return this.keelProperty().get();
	}
	

	public final void setKeel(final String keel) {
		this.keelProperty().set(keel);
	}
	

	public final StringProperty phrfProperty() {
		return this.phrf;
	}
	

	public final String getPhrf() {
		return this.phrfProperty().get();
	}
	

	public final void setPhrf(final String phrf) {
		this.phrfProperty().set(phrf);
	}
	

	public final StringProperty draftProperty() {
		return this.draft;
	}
	

	public final String getDraft() {
		return this.draftProperty().get();
	}
	

	public final void setDraft(final String draft) {
		this.draftProperty().set(draft);
	}
	

	public final StringProperty beamProperty() {
		return this.beam;
	}
	

	public final String getBeam() {
		return this.beamProperty().get();
	}
	

	public final void setBeam(final String beam) {
		this.beamProperty().set(beam);
	}
	

	public final StringProperty lwlProperty() {
		return this.lwl;
	}
	

	public final String getLwl() {
		return this.lwlProperty().get();
	}
	

	public final void setLwl(final String lwl) {
		this.lwlProperty().set(lwl);
	}

}
