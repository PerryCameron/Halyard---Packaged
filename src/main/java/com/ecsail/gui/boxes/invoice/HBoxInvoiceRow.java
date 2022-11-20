package com.ecsail.gui.boxes.invoice;

import com.ecsail.FixInput;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class HBoxInvoiceRow extends HBox {

    String itemName;
    private Text price = new Text();
    private Text total = new Text();
    private TextField textField;
    private Spinner<Integer> spinner;
    private final InvoiceWidgetDTO invoiceWidget;
    private ComboBox<Integer> comboBox;
    private InvoiceDTO invoice;
    private InvoiceItemDTO invoiceItem;
    private FeeDTO fee;
    private VBoxInvoiceFooter footer;
    private ObservableList<InvoiceItemDTO> items;


    public HBoxInvoiceRow(InvoiceWidgetDTO invoiceWidget, VBoxInvoiceFooter footer) {
        this.invoice = footer.getInvoice();
        this.invoiceWidget = invoiceWidget;
        this.itemName = invoiceWidget.getObjectName();
        this.invoiceItem = setItem();
        this.footer = footer;
        this.fee = invoiceWidget.getFee();
        this.items = invoiceWidget.getItems();

        setSpacing(15);
        // column 1
        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(140);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        Text feeText = new Text(itemName + ":");
        feeText.setId("invoice-text-light");
        vBox1.getChildren().add(feeText);

        // column 2
        VBox vBox2 = new VBox();
        vBox2.setPrefWidth(65);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        Control control = setControlWidget(invoiceWidget);
        vBox2.getChildren().add(control);

        // column 3
        VBox vBox3 = new VBox();
        vBox3.setPrefWidth(30);
        vBox3.setAlignment(Pos.CENTER_RIGHT);
        vBox3.getChildren().add(setX(invoiceWidget));

        // column 4
        VBox vBox4 = new VBox();
        vBox4.setPrefWidth(50);
        vBox4.setAlignment(Pos.CENTER_RIGHT);
        vBox4.getChildren().add(price);

        // column 5
        VBox vBox5 = new VBox();
        vBox5.setPrefWidth(70);
        vBox5.setAlignment(Pos.CENTER_RIGHT);
        total.setText(invoiceItem.getValue());
        vBox5.getChildren().add(total);

        getChildren().addAll(vBox1,vBox2,vBox3,vBox4,vBox5);
    }

    private Text setX(InvoiceWidgetDTO i) {
        Text x = new Text("");
        x.setId("invoice-text-light");
        if(i.isMultiplied()) {
            x.setText("X");
            return x;
        }
        return x;
    }

    private Control setControlWidget(InvoiceWidgetDTO i) {
        switch (i.getWidgetType()) {
            case "text-field":
                textField = new TextField();
                textField.setPrefWidth(i.getWidth());
                textField.setText(invoiceItem.getValue());
                setTextFieldListener();
                return textField;

            case "spinner":
                spinner = new Spinner<>();
                spinner.setPrefWidth(i.getWidth());
                price.setText(String.valueOf(fee.getFieldValue()));
                setSpinnerListener();
                return spinner;

            case "combo-box":
                comboBox = new ComboBox<>();
                comboBox.setPrefWidth(i.getWidth());
                // fill comboBox
                for (int j = 0; j < 100; j++) comboBox.getItems().add(j);
                comboBox.getSelectionModel().selectFirst();
                setComboBoxListener();
                return comboBox;

            case "none":
                textField = new TextField("none");
                textField.setVisible(false);
                return textField;
        }
        return null;
    }

    private InvoiceItemDTO setItem() {
        return invoiceWidget.getItems().stream().filter(i -> i.getItemType().equals(itemName)).findFirst().orElse(null);
    }

    private void setSpinnerListener() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, invoiceItem.getQty());
		spinner.setValueFactory(spinnerValueFactory);
		spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(fee.getFieldValue().multiply(BigDecimal.valueOf(newValue)));
			total.setText(calculatedTotal);
            invoiceItem.setQty(newValue);
            invoiceItem.setValue(calculatedTotal);
            SqlUpdate.updateInvoiceItem(invoiceItem);
			updateBalance();
		});
    }

    private void setComboBoxListener() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(BigDecimal.valueOf(newValue).multiply(fee.getFieldValue()));
            total.setText(calculatedTotal);
            invoiceItem.setQty(newValue);
            invoiceItem.setValue(calculatedTotal);
            SqlUpdate.updateInvoiceItem(invoiceItem);
            updateBalance();
        });
    }

    private void setTextFieldListener() {
        		textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!FixInput.isBigDecimal(textField.getText())) {
						textField.setText("0");
	            	}
	            	BigDecimal dues = new BigDecimal(textField.getText());
					textField.setText(String.valueOf(dues.setScale(2, RoundingMode.HALF_UP)));
                    invoiceItem.setQty(1);
                    invoiceItem.setValue(textField.getText());
                    total.setText(textField.getText());
                    SqlUpdate.updateInvoiceItem(invoiceItem);
	            	updateBalance();
	            }
	        });
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

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public Spinner<Integer> getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner<Integer> spinner) {
        this.spinner = spinner;
    }

    public Text getTotal() {
        return total;
    }

    public void setTotal(Text total) {
        this.total = total;
    }

    public ComboBox<Integer> getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox<Integer> comboBox) {
        this.comboBox = comboBox;
    }

    public InvoiceWidgetDTO getInvoiceWidget() {
        return invoiceWidget;
    }
}
