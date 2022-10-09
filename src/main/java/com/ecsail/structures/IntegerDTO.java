package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntegerDTO {
	
	private IntegerProperty integer;

	public IntegerDTO(Integer integer) {
		this.integer = new SimpleIntegerProperty(integer);
	}

	public final IntegerProperty integerProperty() {
		return this.integer;
	}
	

	public final int getInteger() {
		return this.integerProperty().get();
	}
	

	public final void setInteger(final int integer) {
		this.integerProperty().set(integer);
	}
}
