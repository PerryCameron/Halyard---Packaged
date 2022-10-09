package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class WorkCreditDTO {

	private IntegerProperty money_id;
	private IntegerProperty msid;
	private IntegerProperty racing;
	private IntegerProperty harbor;
	private IntegerProperty social;
	private IntegerProperty other;
	
	public WorkCreditDTO(Integer money_id, Integer msid, Integer racing,
						 Integer harbor, Integer social, Integer other) {

		this.money_id = new SimpleIntegerProperty(money_id);
		this.msid = new SimpleIntegerProperty(msid);
		this.racing = new SimpleIntegerProperty(racing);
		this.harbor = new SimpleIntegerProperty(harbor);
		this.social = new SimpleIntegerProperty(social);
		this.other = new SimpleIntegerProperty(other);
	}


	public final IntegerProperty money_idProperty() {
		return this.money_id;
	}

	public final int getMoney_id() {
		return this.money_idProperty().get();
	}
	
	public final void setMoney_id(final int money_id) {
		this.money_idProperty().set(money_id);
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
	

	public final IntegerProperty racingProperty() {
		return this.racing;
	}
	

	public final int getRacing() {
		return this.racingProperty().get();
	}
	

	public final void setRacing(final int racing) {
		this.racingProperty().set(racing);
	}
	

	public final IntegerProperty harborProperty() {
		return this.harbor;
	}
	

	public final int getHarbor() {
		return this.harborProperty().get();
	}
	

	public final void setHarbor(final int harbor) {
		this.harborProperty().set(harbor);
	}
	

	public final IntegerProperty socialProperty() {
		return this.social;
	}
	

	public final int getSocial() {
		return this.socialProperty().get();
	}
	

	public final void setSocial(final int social) {
		this.socialProperty().set(social);
	}
	

	public final IntegerProperty otherProperty() {
		return this.other;
	}
	

	public final int getOther() {
		return this.otherProperty().get();
	}
	

	public final void setOther(final int other) {
		this.otherProperty().set(other);
	}

	@Override
	public String toString() {
		return "Object_WorkCredit [money_id=" + money_id + ", msid=" + msid + ", racing=" + racing + ", harbor=" + harbor
				+ ", social=" + social + ", other=" + other + "]";
	}
		
}
