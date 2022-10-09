package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaymentDTO {
	private IntegerProperty pay_id;
	private IntegerProperty money_id;
	private StringProperty checkNumber;
	private StringProperty paymentType;
	private StringProperty paymentDate;
	private StringProperty PaymentAmount;
	private IntegerProperty deposit_id;
	
	public PaymentDTO(Integer pay_id, Integer money_id, String checkNumber, String paymentType, String paymentDate,
					  String paymentAmount, Integer deposit_id) {
		this.pay_id = new SimpleIntegerProperty(pay_id);
		this.money_id = new SimpleIntegerProperty(money_id);
		this.checkNumber = new SimpleStringProperty(checkNumber);
		this.paymentType = new SimpleStringProperty(paymentType);
		this.paymentDate = new SimpleStringProperty(paymentDate);
		this.PaymentAmount = new SimpleStringProperty(paymentAmount);
		this.deposit_id = new SimpleIntegerProperty(deposit_id);
	}

	public final IntegerProperty pay_idProperty() {
		return this.pay_id;
	}
	

	public final int getPay_id() {
		return this.pay_idProperty().get();
	}
	

	public final void setPay_id(final int pay_id) {
		this.pay_idProperty().set(pay_id);
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
	

	public final StringProperty checkNumberProperty() {
		return this.checkNumber;
	}
	

	public final String getCheckNumber() {
		return this.checkNumberProperty().get();
	}
	

	public final void setCheckNumber(final String checkNumber) {
		this.checkNumberProperty().set(checkNumber);
	}
	

	public final StringProperty paymentTypeProperty() {
		return this.paymentType;
	}
	

	public final String getPaymentType() {
		return this.paymentTypeProperty().get();
	}
	

	public final void setPaymentType(final String paymentType) {
		this.paymentTypeProperty().set(paymentType);
	}
	

	public final StringProperty paymentDateProperty() {
		return this.paymentDate;
	}
	

	public final String getPaymentDate() {
		return this.paymentDateProperty().get();
	}
	

	public final void setPaymentDate(final String paymentDate) {
		this.paymentDateProperty().set(paymentDate);
	}
	

	public final StringProperty PaymentAmountProperty() {
		return this.PaymentAmount;
	}
	

	public final String getPaymentAmount() {
		return this.PaymentAmountProperty().get();
	}
	

	public final void setPaymentAmount(final String PaymentAmount) {
		this.PaymentAmountProperty().set(PaymentAmount);
	}


	public final IntegerProperty deposit_idProperty() {
		return this.deposit_id;
	}
	

	public final int getDeposit_id() {
		return this.deposit_idProperty().get();
	}
	

	public final void setDeposit_id(final int deposit_id) {
		this.deposit_idProperty().set(deposit_id);
	}

	@Override
	public String toString() {
		return "Object_Payment [pay_id=" + pay_id + ", money_id=" + money_id + ", checkNumber=" + checkNumber
				+ ", paymentType=" + paymentType + ", paymentDate=" + paymentDate + ", PaymentAmount=" + PaymentAmount
				+ ", deposit_id=" + deposit_id + "]";
	}
}
