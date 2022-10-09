package com.ecsail.structures;

import javafx.beans.property.*;

public class BoatDTO {
	
	private IntegerProperty boat_id;
	private IntegerProperty ms_id;
	private StringProperty manufacturer;
	private StringProperty manufacture_year;
	private StringProperty registration_num;
	private StringProperty model;
	private StringProperty boat_name;
	private StringProperty sail_number;
	private BooleanProperty hasTrailer;
	private StringProperty length;
	private StringProperty weight;
	private StringProperty keel;
	private StringProperty phrf;
	private StringProperty draft;
	private StringProperty beam;
	private StringProperty lwl;
	private BooleanProperty aux;
	
	public BoatDTO(Integer boat_id, Integer ms_id, String manufacturer, String manufacture_year,
                   String registration_num, String model, String boat_name, String sail_number,
                   Boolean hasTrailer, String length, String weight, String keel, String phrf,
                   String draft, String beam, String lwl, Boolean aux) {

		this.boat_id = new SimpleIntegerProperty(boat_id);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.manufacture_year = new SimpleStringProperty(manufacture_year);
		this.registration_num = new SimpleStringProperty(registration_num);
		this.model = new SimpleStringProperty(model);
		this.boat_name = new SimpleStringProperty(boat_name);
		this.sail_number = new SimpleStringProperty(sail_number);
		this.hasTrailer = new SimpleBooleanProperty(hasTrailer);
		this.length = new SimpleStringProperty(length);
		this.weight = new SimpleStringProperty(weight);
		this.keel = new SimpleStringProperty(keel);
		this.phrf = new SimpleStringProperty(phrf);
		this.draft = new SimpleStringProperty(draft);
		this.beam = new SimpleStringProperty(beam);
		this.lwl = new SimpleStringProperty(lwl);
		this.aux = new SimpleBooleanProperty(aux);
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

	public final IntegerProperty boat_idProperty() {
		return this.boat_id;
	}
	

	public final int getBoat_id() {
		return this.boat_idProperty().get();
	}
	

	public final void setBoat_id(final int boat_id) {
		this.boat_idProperty().set(boat_id);
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
	

	public final StringProperty manufacturerProperty() {
		return this.manufacturer;
	}
	

	public final String getManufacturer() {
		return this.manufacturerProperty().get();
	}
	

	public final void setManufacturer(final String manufacturer) {
		this.manufacturerProperty().set(manufacturer);
	}
	

	public final StringProperty manufacture_yearProperty() {
		return this.manufacture_year;
	}
	

	public final String getManufacture_year() {
		return this.manufacture_yearProperty().get();
	}
	

	public final void setManufacture_year(final String manufacture_year) {
		this.manufacture_yearProperty().set(manufacture_year);
	}
	

	public final StringProperty registration_numProperty() {
		return this.registration_num;
	}
	

	public final String getRegistration_num() {
		return this.registration_numProperty().get();
	}
	

	public final void setRegistration_num(final String registration_num) {
		this.registration_numProperty().set(registration_num);
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
	

	public final StringProperty boat_nameProperty() {
		return this.boat_name;
	}
	

	public final String getBoat_name() {
		return this.boat_nameProperty().get();
	}
	

	public final void setBoat_name(final String boat_name) {
		this.boat_nameProperty().set(boat_name);
	}
	

	public final StringProperty sail_numberProperty() {
		return this.sail_number;
	}
	

	public final String getSail_number() {
		return this.sail_numberProperty().get();
	}
	

	public final void setSail_number(final String sail_number) {
		this.sail_numberProperty().set(sail_number);
	}
	

	public final BooleanProperty hasTrailerProperty() {
		return this.hasTrailer;
	}
	

	public final boolean isHasTrailer() {
		return this.hasTrailerProperty().get();
	}
	

	public final void setHasTrailer(final boolean hasTrailer) {
		this.hasTrailerProperty().set(hasTrailer);
	}
	

	public final StringProperty lengthProperty() {
		return this.length;
	}
	

	public final String getLength() {
		return this.lengthProperty().get();
	}
	

	public final void setLength(final String length) {
		this.lengthProperty().set(length);
	}
	

	public final StringProperty weightProperty() {
		return this.weight;
	}
	

	public final String getWeight() {
		return this.weightProperty().get();
	}
	

	public final void setWeight(final String weight) {
		this.weightProperty().set(weight);
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

	@Override
	public String toString() {
		return "Object_Boat [boat_id=" + boat_id + ", ms_id=" + ms_id + ", manufacturer=" + manufacturer
				+ ", manufacture_year=" + manufacture_year + ", registration_num=" + registration_num + ", model="
				+ model + ", boat_name=" + boat_name + ", sail_number=" + sail_number + ", hasTrailer=" + hasTrailer
				+ ", length=" + length + ", weight=" + weight + ", keel=" + keel + ", phrf=" + phrf + ", draft=" + draft
				+ ", beam=" + beam + ", lwl=" + lwl + "]";
	}

}
