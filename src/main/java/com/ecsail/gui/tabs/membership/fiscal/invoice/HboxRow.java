package com.ecsail.gui.tabs.membership.fiscal.invoice;

import com.ecsail.BaseApplication;
import com.ecsail.FixInput;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HboxRow extends HBox {

    String itemName;
    private Text price = new Text();
    private Text total = new Text();
    private TextField textField;
    private Spinner<Integer> spinner;
    private final DbInvoiceDTO invoiceWidget;
    private ComboBox<Integer> comboBox;
    private final InvoiceItemDTO invoiceItem;
    private final FeeDTO fee;
    private final VboxFooter footer;
    private final ObservableList<InvoiceItemDTO> items;
    private final VBox vBox1 = new VBox();
    private final VBox vBox2 = new VBox();
    private final VBox vBox3 = new VBox();
    private final VBox vBox4 = new VBox();
    private final VBox vBox5 = new VBox();

    InvoiceDTO invoice;

    public HboxRow(DbInvoiceDTO invoiceWidget, VboxFooter footer) {

        this.invoiceWidget = invoiceWidget;
        this.itemName = invoiceWidget.getFieldName();
        this.invoiceItem = setItem();
        this.footer = footer;
        this.invoice = footer.getInvoice();
        this.fee = invoiceWidget.getFee();
        this.items = invoiceWidget.getItems();

        // get officer credit
        if (invoiceWidget.getFieldName().equals("Position Credit")) {
            if (!invoice.isCommitted())  // if not committed
                if (getOfficerCredit()) { // has an officer
                    invoiceItem.setValue(items.get(0).getValue()); // is putting dues value into here
                }
            total.setText(items.get(0).getValue());
            SqlUpdate.updateInvoiceItem(invoiceItem);
            // TODO maybe find better way than putting element 0 in (not dynamic or robust)
        }

        setSpacing(15);

        // settings common to edit and commit modes
        vBox1.setAlignment(Pos.CENTER_LEFT);
        Text feeText = new Text(itemName + ":");
        feeText.setId("invoice-text-light");
        vBox1.getChildren().add(feeText);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        Control control = setControlWidget(invoiceWidget);
        vBox2.getChildren().add(control);
        vBox3.setAlignment(Pos.CENTER_RIGHT);
        vBox4.setAlignment(Pos.CENTER_RIGHT);
        vBox4.getChildren().add(price);
        vBox5.setAlignment(Pos.CENTER_RIGHT);
        total.setText(invoiceItem.getValue());
        vBox5.getChildren().add(total);
    }

    private void setEdit() {
        setVisible(true);
        setManaged(true);
        vBox1.setPrefWidth(140);
        vBox2.setPrefWidth(65);
        vBox3.setPrefWidth(30);
        vBox3.getChildren().clear();
        vBox3.getChildren().add(setX(invoiceWidget));
        vBox4.setPrefWidth(50);
        vBox5.setPrefWidth(70);
        getChildren().addAll(vBox1,vBox2,vBox3,vBox4,vBox5);
    }

    private void setCommit() {
        if (!invoiceItem.getValue().equals("0.00")) { // list only items in use
            vBox1.setPrefWidth(160);
            vBox3.setPrefWidth(40);
            vBox3.getChildren().clear();
            if (invoiceItem.getQty() != 0) // don't print the 0's
                vBox3.getChildren().add(new Text(String.valueOf(invoiceItem.getQty())));
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

    private boolean getOfficerCredit() {
        boolean hasOfficer = SqlExists.membershipHasOfficerForYear(invoiceItem.getMsId(), invoiceItem.getYear());
        BaseApplication.logger.info("Membership has officer: " + hasOfficer);
        return hasOfficer && !invoice.isSupplemental();
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

    private Control setControlWidget(DbInvoiceDTO i) {
        switch (i.getWidgetType()) {
            case "text-field" -> {
                textField = new TextField();
                textField.setPrefWidth(i.getWidth());
                autoPopulateField();
                setTextFieldListener();
                return textField;
            }
            case "spinner" -> {
                spinner = new Spinner<>();
                spinner.setPrefWidth(i.getWidth());
                price.setText(String.valueOf(fee.getFieldValue()));
                setSpinnerListener();
                if (invoiceWidget.isPrice_editable())
                    setPriceChangeListener(new TextField(price.getText()));
                return spinner;
            }
            case "combo-box" -> {
                comboBox = new ComboBox<>();
                comboBox.setPrefWidth(i.getWidth());
                price.setText(String.valueOf(fee.getFieldValue()));
                // fill comboBox
                for (int j = 0; j < invoiceWidget.getMaxQty(); j++) comboBox.getItems().add(j);
                comboBox.getSelectionModel().select(invoiceItem.getQty());
                setComboBoxListener();
                if (invoiceWidget.isPrice_editable())
                    setPriceChangeListener(new TextField(price.getText()));
                return comboBox;
            }
            case "none" -> {
                textField = new TextField("none");
                textField.setVisible(false);
                return textField;
            }
        }
        return null;
    }

    private void autoPopulateField() {
        // if it is a row that can autoPopulate i.e. dues, and the record is not committed
        if(invoiceWidget.isAutoPopulate() && !invoice.isCommitted()) {
            if (!invoice.isSupplemental()) {
                textField.setText(String.valueOf(fee.getFieldValue()));
                invoiceItem.setValue(String.valueOf(fee.getFieldValue()));
                updateBalance();
            }
            else
                textField.setText(invoiceItem.getValue());
        } else
            textField.setText(invoiceItem.getValue());
    }

    private InvoiceItemDTO setItem() {
        return invoiceWidget.getItems().stream().filter(i -> i.getItemType().equals(itemName)).findFirst().orElse(null);
    }

    private void setSpinnerListener() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, invoiceWidget.getMaxQty(), invoiceItem.getQty());
		spinner.setValueFactory(spinnerValueFactory);
		spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(new BigDecimal(fee.getFieldValue()).multiply(BigDecimal.valueOf(newValue)));
			total.setText(calculatedTotal);
            invoiceItem.setQty(newValue);
            invoiceItem.setValue(calculatedTotal);
            checkIfNotCommittedAndUpdateSql();
			updateBalance();
		});
    }

    private void setComboBoxListener() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(BigDecimal.valueOf(newValue).multiply(new BigDecimal(fee.getFieldValue())));
            total.setText(calculatedTotal);
            invoiceItem.setQty(newValue);
            invoiceItem.setValue(calculatedTotal);
            checkIfNotCommittedAndUpdateSql();
            updateBalance();
        });
    }

    private void setTextFieldListener() {
        		textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
                    // fix it or set to 0 if can't
	            	if(!FixInput.isBigDecimal(textField.getText())) {
						textField.setText("0");
	            	}
	            	BigDecimal item = new BigDecimal(textField.getText());
					textField.setText(String.valueOf(item.setScale(2, RoundingMode.HALF_UP)));
                    invoiceItem.setQty(1);
                    invoiceItem.setValue(textField.getText());
                    total.setText(textField.getText());
                    checkIfNotCommittedAndUpdateSql();
	            	updateBalance();
	            }
	        });
    }

    private void checkIfNotCommittedAndUpdateSql() {
        if(invoice.isCommitted()) BaseApplication.logger.info("Record is committed: database can not be updated");
        else SqlUpdate.updateInvoiceItem(invoiceItem);
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
                invoiceItem.setValue(value);
                total.setText(value);
                checkIfNotCommittedAndUpdateSql();
                SqlUpdate.updateInvoiceItem(invoiceItem);
                updateBalance();
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
    private void updateBalance() {
        BigDecimal fees = new BigDecimal("0.00");
        BigDecimal credit = new BigDecimal("0.00");
        for (InvoiceItemDTO i : items) {
            if (i.isCredit())
                credit = credit.add(new BigDecimal(i.getValue()));
            else
                fees = fees.add(new BigDecimal(i.getValue()));
        }
        footer.updateTotals(fees,credit);
    }

    public Text getPrice() {
        return price;
    }

    public void setPrice(Text price) {
        this.price = price;
    }

    public Text getTotal() {
        return total;
    }

    public void setTotal(Text total) {
        this.total = total;
    }

    public DbInvoiceDTO getInvoiceWidget() {
        return invoiceWidget;
    }
}
