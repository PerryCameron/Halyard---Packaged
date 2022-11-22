package com.ecsail.structures;

import javafx.beans.property.*;

public class InvoiceDTO {
    private final IntegerProperty id;
    private final IntegerProperty msId;
    private final IntegerProperty year;
    private final StringProperty paid;
    private final StringProperty total;
    private final StringProperty credit;
    private final StringProperty balance;
    private final IntegerProperty batch;
    private final BooleanProperty committed;
    private final BooleanProperty closed;
    private final BooleanProperty supplemental;

    private final StringProperty maxCredit;

    public InvoiceDTO(Integer id, Integer msId, Integer year, String paid, String total, String credit, String balance, Integer batch, Boolean committed, Boolean closed, Boolean supplemental, String maxCredit) {
        this.id = new SimpleIntegerProperty(id);
        this.msId = new SimpleIntegerProperty(msId);
        this.year = new SimpleIntegerProperty(year);
        this.paid = new SimpleStringProperty(paid);
        this.total = new SimpleStringProperty(total);
        this.credit = new SimpleStringProperty(credit);
        this.balance = new SimpleStringProperty(balance);
        this.batch = new SimpleIntegerProperty(batch);
        this.committed = new SimpleBooleanProperty(committed);
        this.closed = new SimpleBooleanProperty(closed);
        this.supplemental = new SimpleBooleanProperty(supplemental);
        this.maxCredit = new SimpleStringProperty(maxCredit);
    }

    public final IntegerProperty idProperty() {
        return this.id;
    }


    public final int getId() {
        return this.idProperty().get();
    }


    public final void setId(final int id) {
        this.idProperty().set(id);
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


    public final IntegerProperty yearProperty() {
        return this.year;
    }


    public final int getYear() {
        return this.yearProperty().get();
    }


    public final void setYear(final int year) {
        this.yearProperty().set(year);
    }


    public final StringProperty paidProperty() {
        return this.paid;
    }


    public final String getPaid() {
        return this.paidProperty().get();
    }


    public final void setPaid(final String paid) {
        this.paidProperty().set(paid);
    }


    public final StringProperty totalProperty() {
        return this.total;
    }


    public final String getTotal() {
        return this.totalProperty().get();
    }


    public final void setTotal(final String total) {
        this.totalProperty().set(total);
    }


    public final StringProperty creditProperty() {
        return this.credit;
    }


    public final String getCredit() {
        return this.creditProperty().get();
    }


    public final void setCredit(final String credit) {
        this.creditProperty().set(credit);
    }


    public final StringProperty balanceProperty() {
        return this.balance;
    }


    public final String getBalance() {
        return this.balanceProperty().get();
    }


    public final void setBalance(final String balance) {
        this.balanceProperty().set(balance);
    }


    public final IntegerProperty batchProperty() {
        return this.batch;
    }


    public final int getBatch() {
        return this.batchProperty().get();
    }


    public final void setBatch(final int batch) {
        this.batchProperty().set(batch);
    }


    public final BooleanProperty committedProperty() {
        return this.committed;
    }


    public final boolean isCommitted() {
        return this.committedProperty().get();
    }


    public final void setCommitted(final boolean committed) {
        this.committedProperty().set(committed);
    }


    public final BooleanProperty closedProperty() {
        return this.closed;
    }


    public final boolean isClosed() {
        return this.closedProperty().get();
    }


    public final void setClosed(final boolean closed) {
        this.closedProperty().set(closed);
    }


    public final BooleanProperty supplementalProperty() {
        return this.supplemental;
    }


    public final boolean isSupplemental() {
        return this.supplementalProperty().get();
    }


    public final void setSupplemental(final boolean supplemental) {
        this.supplementalProperty().set(supplemental);
    }

    public final StringProperty maxCreditProperty() {
        return this.maxCredit;
    }


    public final String getMaxCredit() {
        return this.maxCreditProperty().get();
    }


    public final void setMaxCredit(final String maxCredit) {
        this.maxCreditProperty().set(maxCredit);
    }


}
