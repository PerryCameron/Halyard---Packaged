package com.ecsail.gui.tabs.deposits;


import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.DepositDTO;
import com.ecsail.structures.InvoiceDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvoicesTableView extends TableView {
    TabDeposits tabParent;
    DepositDTO depositDTO;

//    Map<InvoiceWithMemberInfoDTO, BooleanProperty> checkedRows = new HashMap<>();

    public InvoicesTableView(TabDeposits tabParent) {
    this.tabParent = tabParent;
    this.depositDTO = tabParent.getDepositDTO();

        setItems(tabParent.getInvoices());
        setFixedCellSize(30);
        setEditable(true);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this,Priority.ALWAYS);
        var Col1 = new TableColumn<InvoiceWithMemberInfoDTO, Boolean>("Select");
        Col1.setPrefWidth(50);
        Col1.setCellValueFactory(param -> {
            InvoiceWithMemberInfoDTO invoiceM = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(invoiceM.isClosed());
            booleanProp.addListener((observable, oldValue, newValue) -> {
                invoiceM.setClosed(newValue); // sets checkbox value in table
                InvoiceDTO invoiceDTO = convertToProperInvoice(invoiceM);
                if (newValue) { // if checked
                    invoiceM.setBatch(depositDTO.getBatch()); // updates tableview batch
                    invoiceDTO.setBatch(depositDTO.getBatch()); // sets batch to invoice for saving to db
                    invoiceDTO.setClosed(true);
                    SqlUpdate.updateInvoice(invoiceDTO);
                } else { // if unchecked
                    invoiceM.setBatch(0);  // updates tableview batch
                    invoiceDTO.setBatch(0); // sets batch to invoice for saving to db
                    invoiceDTO.setClosed(false);
                    SqlUpdate.updateInvoice(invoiceDTO);
                }
            });
            return booleanProp;
        });

        /// allows to select all checkboxes
//        Col1.setCellFactory(CheckBoxTableCell.forTableColumn(i ->
//                checkedRows.computeIfAbsent((InvoiceWithMemberInfoDTO) getItems().get(i), p -> new SimpleBooleanProperty())));

        Col1.setCellFactory(p -> {
            CheckBoxTableCell<InvoiceWithMemberInfoDTO, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        var Col2 = new TableColumn<InvoiceWithMemberInfoDTO, Integer>("Deposit");
        Col2.setCellValueFactory(new PropertyValueFactory<>("batch"));

        var Col3 = new TableColumn<InvoiceWithMemberInfoDTO, Integer>("Mem ID");
        Col3.setCellValueFactory(new PropertyValueFactory<>("membershipId"));

        var Col4 = new TableColumn<InvoiceWithMemberInfoDTO, String>("Last Name");
        Col4.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        Col4.setPrefWidth(80);

        var Col5 = new TableColumn<InvoiceWithMemberInfoDTO, String>("First Name");
        Col5.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        Col5.setPrefWidth(80);

        var Col6 = new TableColumn<InvoiceWithMemberInfoDTO, Integer>("Fees");
        Col6.setCellValueFactory(new PropertyValueFactory<>("total"));
        Col6.setPrefWidth(50);

        var Col7 = new TableColumn<InvoiceWithMemberInfoDTO, Integer>("Credit");
        Col7.setCellValueFactory(new PropertyValueFactory<>("credit"));
        Col7.setPrefWidth(50);

        var Col8 = new TableColumn<InvoiceWithMemberInfoDTO, Integer>("Paid");
        Col8.setCellValueFactory(new PropertyValueFactory<>("paid"));
        Col8.setPrefWidth(50);

        var Col9 = new TableColumn<InvoiceWithMemberInfoDTO, Integer>("Balance");
        Col9.setCellValueFactory(new PropertyValueFactory<>("balance"));
        Col9.setPrefWidth(50);

        Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );    // check box
        Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );    // batch
        Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // Mem ID
        Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // last
        Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // first
        Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // fees
        Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // credit
        Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // paid
        Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );    // balance

        getColumns()
                .addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9));
    }

    private InvoiceDTO convertToProperInvoice(InvoiceWithMemberInfoDTO i) {
        return new InvoiceDTO(i.getId(),i.getMsId(),i.getYear(),i.getPaid(),i.getTotal(), i.getCredit(),
                i.getBalance(),i.getBatch(),i.isCommitted(),i.isClosed(),i.isSupplemental(),i.getMaxCredit());
    }

//    public Map<InvoiceWithMemberInfoDTO, BooleanProperty> getCheckedRows() {
//        return checkedRows;
//    }
}
