package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BoatOwnerDTO {
	private IntegerProperty msid;
	private IntegerProperty boat_id;
	
	public BoatOwnerDTO(Integer msid, Integer boat_id) {
		super();
		this.msid = new SimpleIntegerProperty(msid);
		this.boat_id = new SimpleIntegerProperty(boat_id);
		
	}

	public final IntegerProperty msidProperty() {
		return this.msid;
	}
	

	public final int getMsid() {
		return this.msidProperty().get();
	}
	

	public final void setMsid(final int msid) {
		this.msidProperty().set(msid);
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
	
}
