package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.BaseApplication;
import com.ecsail.FixInput;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.structures.DbInvoiceDTO;
import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.InvoiceDTO;
import com.ecsail.structures.InvoiceItemDTO;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvoiceItemRow extends HBox {

    protected String itemName;
    private Text price = new Text();
    protected Text rowTotal = new Text();
    private TextField textField;
    private Spinner<Integer> spinner;

    protected DbInvoiceDTO dbInvoiceDTO;
    private ComboBox<Integer> comboBox;
    protected InvoiceItemDTO invoiceItemDTO;

    protected FeeDTO fee;
    private final InvoiceFooter footer;
    private final VBox vBox1 = new VBox();
    private final VBox vBox2 = new VBox();
    private final VBox vBox3 = new VBox();
    private final VBox vBox4 = new VBox();
    private final VBox vBox5 = new VBox();
    InvoiceDTO invoice;
    Invoice parent;

    public InvoiceItemRow(Invoice invoice, DbInvoiceDTO dbInvoiceDTO, InvoiceFooter footer) {
        this.parent = invoice;
        this.dbInvoiceDTO = dbInvoiceDTO;
        this.itemName = dbInvoiceDTO.getFieldName();
        this.footer = footer;
        this.invoice = footer.getInvoice();
        this.invoiceItemDTO = setItem();
        this.fee = getFee();
        parent.invoiceItemMap.put(dbInvoiceDTO.getFieldName(),this);
        addChildren();
    }

    private void addChildren() {
        setSpacing(15);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        Text feeText = new Text(itemName + ":");
        feeText.setId("invoice-text-light");
        vBox1.getChildren().add(feeText);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        Control control = setControlWidget();
        vBox2.getChildren().add(control);
        vBox3.setAlignment(Pos.CENTER_RIGHT);
        vBox4.setAlignment(Pos.CENTER_RIGHT);
        vBox4.getChildren().add(price);
        vBox5.setAlignment(Pos.CENTER_RIGHT);
        if(!dbInvoiceDTO.isItemized()) // don't set this for itemized rows
            rowTotal.setText(invoiceItemDTO.getValue());
        // OMFG This was a hard bug to find, by binding the itemized rows it caused the category rows
        // to put the value inside them. This caused a doubling of fees in total calculation
        // lesson learned, be very careful using bindings. line below fixes problem
        if(!dbInvoiceDTO.getWidgetType().equals("itemized"))
            invoiceItemDTO.valueProperty().bind(rowTotal.textProperty()); //  value of Text to DTO
        if(this.invoiceItemDTO.isCredit()) rowTotal.setId("invoice-text-credit");
        vBox5.getChildren().add(rowTotal);
    }

    private void setEdit() {
        setVisible(true);
        setManaged(true);
        vBox1.setPrefWidth(140);
        vBox2.setPrefWidth(65);
        vBox3.setPrefWidth(30);
        vBox3.getChildren().clear();
        vBox3.getChildren().add(setX(dbInvoiceDTO));
        vBox4.setPrefWidth(50);
        vBox5.setPrefWidth(70);
        getChildren().addAll(vBox1,vBox2,vBox3,vBox4,vBox5);
        // this row is not like the others - this needs changed here
        if(dbInvoiceDTO.getWidgetType().equals("itemized")) {
            setForTitledPane();
        }
    }

    private void setCommit() {
        if (!invoiceItemDTO.getValue().equals("0.00")) { // list only items in use
            vBox1.setPrefWidth(160);
            vBox3.setPrefWidth(40);
            vBox3.getChildren().clear();
            if (invoiceItemDTO.getQty() != 0) // don't print the 0's
                vBox3.getChildren().add(new Text(String.valueOf(invoiceItemDTO.getQty())));
            vBox5.setPrefWidth(190);
            getChildren().addAll(vBox1, vBox3, vBox5);
        } else {
            setVisible(false);
            setManaged(false);
        }
    }

    public void setCommitMode(boolean setCommit) {
        getChildren().clear();
        if(setCommit)
            setCommit();
        else
            setEdit();
    }

    private Text setX(DbInvoiceDTO i) {
        Text x = new Text("");
        x.setId("invoice-text-light");
        if(i.isMultiplied()) {
            x.setText("X");
            return x;
        }
        return x;
    }

    private Control setControlWidget() {
        switch (dbInvoiceDTO.getWidgetType()) {
            case "text-field" -> {
                textField = new TextField();
                textField.setPrefWidth(dbInvoiceDTO.getWidth());
                textField.textProperty().bindBidirectional(rowTotal.textProperty());
                setTextFieldListener();
                return textField;
            }
            case "spinner" -> {
                spinner = new Spinner<>();
                spinner.setPrefWidth(dbInvoiceDTO.getWidth());
                price.setText(String.valueOf(fee.getFieldValue()));
                setSpinnerListener();
                if (dbInvoiceDTO.isPrice_editable())
                    setPriceChangeListener(new TextField(price.getText()));
                return spinner;
            }
            case "combo-box" -> {
                comboBox = new ComboBox<>();
                comboBox.setPrefWidth(dbInvoiceDTO.getWidth());
                price.setText(String.valueOf(fee.getFieldValue()));
                // fill comboBox
                for (int j = 0; j < dbInvoiceDTO.getMaxQty(); j++) comboBox.getItems().add(j);
                comboBox.getSelectionModel().select(invoiceItemDTO.getQty());
                setComboBoxListener();
                if (dbInvoiceDTO.isPrice_editable())
                    setPriceChangeListener(new TextField(price.getText()));
                return comboBox;
            }
            case "itemized" -> { // more complex so layout and logic in different class
                // setForTitledPane(); <- gets called in setEdit() in normal but here in mock
                TitledPane titledPane = new TitledPane(fee.getFieldName(),new ItemizedCategory(this));
                titledPane.setExpanded(false);
                titledPane.setId("title-pane");
                return titledPane;
            }
            case "none" -> {
                textField = new TextField("none");
                textField.setVisible(false);
                return textField;
            }
        }
        return null;
    }

    private void setForTitledPane() {
        getChildren().remove(vBox1);
        getChildren().remove(vBox3);
        getChildren().remove(vBox4);
        vBox2.setPrefWidth(330);
    }

    private FeeDTO getFee() {
        FeeDTO duesFee;
        if(getDbInvoiceDTO().isAutoPopulate()) {
            duesFee = SqlFee.getFeeByMembershipTypeForFiscalYear(invoice.getYear(), invoice.getMsId());
            if(duesFee == null)
                invoiceItemDTO.setValue("0.00");
            else
            invoiceItemDTO.setValue(duesFee.getFieldValue());
            return duesFee;
        }
        else
            return dbInvoiceDTO.getFee();
    }

    private InvoiceItemDTO setItem() {
        // we will match this db_invoice to invoiceItem, if nothing found then create an invoice item
        InvoiceItemDTO currentInvoiceItem = parent.items.stream()
                .filter(i -> i.getFieldName().equals(itemName)).findFirst().orElse(null);
        if(currentInvoiceItem == null) return addNewInvoiceItem();
        return currentInvoiceItem;
    }

    /**
     * If a db_invoice is created, this creates an invoiceItem. All this occurs if the db_invoice was created
     * after the invoice was created. It is a way to update them. In theory should never be needed.
     */
    private InvoiceItemDTO addNewInvoiceItem() { //
        InvoiceItemDTO newInvoiceItem = new InvoiceItemDTO(invoice.getId(),invoice.getMsId(),invoice.getYear(),itemName);
        parent.items.add(newInvoiceItem);
        SqlInsert.addInvoiceItemRecord(newInvoiceItem);
        return newInvoiceItem;
    }

    private void setSpinnerListener() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, dbInvoiceDTO.getMaxQty(), invoiceItemDTO.getQty());
		spinner.setValueFactory(spinnerValueFactory);
		spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(new BigDecimal(fee.getFieldValue()).multiply(BigDecimal.valueOf(newValue)));
			rowTotal.setText(calculatedTotal);
            invoiceItemDTO.setQty(newValue);
            updateBalance();
		});

        // no need to write to database everytime we click a spinner, lets write when done.
        spinner.focusedProperty().addListener((observable, oldValue, focused) -> {
            if(!focused)
            checkIfNotCommittedAndUpdateSql();
        });
    }

    protected void updateBalance() {
        BigDecimal fees = new BigDecimal("0.00");
        BigDecimal credit = new BigDecimal("0.00");
        for (InvoiceItemDTO i : parent.items) {
            if (i.isCredit()) {
                credit = credit.add(new BigDecimal(i.getValue()));
            }
            else {
                fees = fees.add(new BigDecimal(i.getValue()));
            }
        }
        footer.updateTotals(fees,credit);
    }

    private void setComboBoxListener() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(BigDecimal.valueOf(newValue).multiply(new BigDecimal(fee.getFieldValue())));
            rowTotal.setText(calculatedTotal);
            invoiceItemDTO.setQty(newValue);
            checkIfNotCommittedAndUpdateSql();
            updateBalance();
        });
    }

    private void setTextFieldListener() {
        		textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
                    // fix it or set to 0 if can't
	            	if(!FixInput.isBigDecimal(textField.getText())) textField.setText("0");
	            	BigDecimal item = new BigDecimal(textField.getText());
					textField.setText(String.valueOf(item.setScale(2, RoundingMode.HALF_UP)));
                    invoiceItemDTO.setQty(1);
                    updateBalance();
                    checkIfNotCommittedAndUpdateSql();
	            }
	        });
    }

    private void checkIfNotCommittedAndUpdateSql() {
        if(invoice.isCommitted()) BaseApplication.logger.info("Record is committed: database can not be updated");
        else updateInvoiceItem(invoiceItemDTO);
    }

    protected void checkIfNotCommittedAndUpdateSql(InvoiceItemDTO invoiceItemDTO) {
        if(invoice.isCommitted()) BaseApplication.logger.info("Record is committed: database can not be updated");
        else updateInvoiceItem(invoiceItemDTO);
    }

    private void updateInvoiceItem(InvoiceItemDTO invoiceItemDTO) {
        if(footer.getBoxInvoice().isUpdateAllowed())
            SqlUpdate.updateInvoiceItem(invoiceItemDTO);
    }

    private void setPriceChangeListener(TextField textField) {
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
                if(!FixInput.isBigDecimal(textField.getText())) {
                    textField.setText("0.00");
                }
                BigDecimal calculatedValue = new BigDecimal(textField.getText());
                String stringValue = String.valueOf(calculatedValue.multiply(BigDecimal.valueOf(spinner.getValue())).setScale(2, RoundingMode.HALF_UP));
                textField.setText(stringValue);
                price.setText(stringValue);
                String value = String.valueOf(new BigDecimal(price.getText()).multiply(BigDecimal.valueOf(spinner.getValue())));
                rowTotal.setText(value);
                updateBalance();
                checkIfNotCommittedAndUpdateSql();
                vBox4.getChildren().clear();
                vBox4.getChildren().add(price);
            }
        });

        price.setOnMouseClicked(e -> {
            vBox4.getChildren().clear();
            vBox4.getChildren().add(textField);
        });

        price.setFill(Color.BLUE);
        price.setOnMouseEntered(en -> price.setFill(Color.RED));
        price.setOnMouseExited(ex -> price.setFill(Color.BLUE));
    }


    public Text getPrice() {
        return price;
    }

    public void setPrice(Text price) {
        this.price = price;
    }

    public Text getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(Text rowTotal) {
        this.rowTotal = rowTotal;
    }

    public DbInvoiceDTO getDbInvoiceDTO() {
        return dbInvoiceDTO;
    }

}
