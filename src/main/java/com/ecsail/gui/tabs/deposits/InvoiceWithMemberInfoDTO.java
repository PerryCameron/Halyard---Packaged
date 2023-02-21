package com.ecsail.gui.tabs.deposits;

import com.ecsail.dto.InvoiceDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceWithMemberInfoDTO extends InvoiceDTO {

    private final IntegerProperty membershipId;  // Member ID used in real life
    private final StringProperty f_name;
    private final StringProperty l_name;


    public InvoiceWithMemberInfoDTO(Integer id, Integer msId, Integer year, String paid, String total, String credit,
                                    String balance, Integer batch, Boolean committed, Boolean closed,
                                    Boolean supplemental, String maxCredit, Integer membershipId,
                                    String firstName, String lastName) {
        super(id, msId, year, paid, total, credit, balance, batch, committed, closed, supplemental, maxCredit);
        this.membershipId = new SimpleIntegerProperty(membershipId);
        this.f_name = new SimpleStringProperty(firstName);
        this.l_name = new SimpleStringProperty(lastName);
    }

    public final IntegerProperty membershipIdProperty() {
        return this.membershipId;
    }

    public final int getMembershipId() {
        return this.membershipIdProperty().get();
    }

    public final void setMembershipId(final int membershipId) {
        this.membershipIdProperty().set(membershipId);
    }

    public final StringProperty f_nameProperty() {
        return this.f_name;
    }

    public final String getF_name() {
        return this.f_nameProperty().get();
    }

    public final void setF_name(final String f_name) {
        this.f_nameProperty().set(f_name);
    }

    public final StringProperty l_nameProperty() {
        return this.l_name;
    }

    public final String getL_name() {
        return this.l_nameProperty().get();
    }

    public final void setL_name(final String l_name) {
        this.l_nameProperty().set(l_name);
    }

    @Override
    public String toString() {
        return "InvoiceWithMemberInfoDTO{" +
                "membershipId=" + membershipId +
                ", f_name=" + f_name +
                ", l_name=" + l_name +
                "} " + super.toString();
    }
}
