package com.ecsail.dto;

import com.ecsail.HalyardPaths;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class PaymentDTO {
	private IntegerProperty pay_id;
	private IntegerProperty invoice_id;
	private StringProperty checkNumber;
	private StringProperty paymentType;
	private StringProperty paymentDate;
	private StringProperty PaymentAmount;
	private IntegerProperty deposit_id;
	
	public PaymentDTO(Integer pay_id, Integer invoice_id, String checkNumber, String paymentType, String paymentDate,
					  String paymentAmount, Integer deposit_id) {
		this.pay_id = new SimpleIntegerProperty(pay_id);
		this.invoice_id = new SimpleIntegerProperty(invoice_id);
		this.checkNumber = new SimpleStringProperty(checkNumber);
		this.paymentType = new SimpleStringProperty(paymentType);
		this.paymentDate = new SimpleStringProperty(paymentDate);
		this.PaymentAmount = new SimpleStringProperty(paymentAmount);
		this.deposit_id = new SimpleIntegerProperty(deposit_id);
	}

	public PaymentDTO(Integer invoice_id) {
		this.pay_id = new SimpleIntegerProperty(0);
		this.invoice_id = new SimpleIntegerProperty(invoice_id);
		this.checkNumber = new SimpleStringProperty("");
		this.paymentType = new SimpleStringProperty("CH");
		this.paymentDate = new SimpleStringProperty(LocalDate.now().toString());
		this.PaymentAmount = new SimpleStringProperty("0.00");
		this.deposit_id = new SimpleIntegerProperty(1);
	}

//	PaymentDTO paymentDTO = new PaymentDTO(0, invoice.getId(), "0", "CH",
//			HalyardPaths.date, "0", 1);

	public final IntegerProperty pay_idProperty() {
		return this.pay_id;
	}
	

	public final int getPay_id() {
		return this.pay_idProperty().get();
	}
	

	public final void setPay_id(final int pay_id) {
		this.pay_idProperty().set(pay_id);
	}
	

	public final IntegerProperty invoice_idProperty() {
		return this.invoice_id;
	}
	

	public final int getInvoice_id() {
		return this.invoice_idProperty().get();
	}
	

	public final void setInvoice_id(final int invoice_id) {
		this.invoice_idProperty().set(invoice_id);
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
		return "Object_Payment [pay_id=" + pay_id + ", invoice_id=" + invoice_id + ", checkNumber=" + checkNumber
				+ ", paymentType=" + paymentType + ", paymentDate=" + paymentDate + ", PaymentAmount=" + PaymentAmount
				+ ", deposit_id=" + deposit_id + "]";
	}
}
