package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BoatMemoDTO {
	
	private IntegerProperty boat_memo_id;
	private IntegerProperty boat_id;
	private StringProperty memo_date;
	private StringProperty memo;

	
	public BoatMemoDTO(Integer boat_memo_id, Integer boat_id, String memo_date,
					   String memo) {
		super();
		this.boat_memo_id = new SimpleIntegerProperty(boat_memo_id);
		this.boat_id = new SimpleIntegerProperty(boat_id);
		this.memo_date = new SimpleStringProperty(memo_date);
		this.memo = new SimpleStringProperty(memo);
	}


	public final IntegerProperty boat_memo_idProperty() {
		return this.boat_memo_id;
	}
	


	public final int getBoat_memo_id() {
		return this.boat_memo_idProperty().get();
	}
	


	public final void setBoat_memo_id(final int boat_memo_id) {
		this.boat_memo_idProperty().set(boat_memo_id);
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
	


	public final StringProperty memo_dateProperty() {
		return this.memo_date;
	}
	


	public final String getMemo_date() {
		return this.memo_dateProperty().get();
	}
	


	public final void setMemo_date(final String memo_date) {
		this.memo_dateProperty().set(memo_date);
	}
	


	public final StringProperty memoProperty() {
		return this.memo;
	}
	


	public final String getMemo() {
		return this.memoProperty().get();
	}
	


	public final void setMemo(final String memo) {
		this.memoProperty().set(memo);
	}

}
