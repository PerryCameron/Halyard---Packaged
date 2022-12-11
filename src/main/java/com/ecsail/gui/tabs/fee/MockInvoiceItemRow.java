package com.ecsail.gui.tabs.fee;

import com.ecsail.FixInput;
import com.ecsail.gui.tabs.membership.fiscal.invoice.InvoiceFooter;
import com.ecsail.structures.DbInvoiceDTO;
import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.InvoiceDTO;
import com.ecsail.structures.InvoiceItemDTO;
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

public class MockInvoiceItemRow extends HBox {

    String itemName;
    private Text price = new Text();
    private Text total = new Text();
    private TextField textField;
    private Spinner<Integer> spinner;
    private final DbInvoiceDTO invoiceWidget;
    private ComboBox<Integer> comboBox;
    private final InvoiceItemDTO invoiceItem;
    private final FeeDTO fee;
    private final InvoiceFooter footer;
    private final ObservableList<InvoiceItemDTO> items;
    private final VBox vBox1 = new VBox();
    private final VBox vBox2 = new VBox();
    private final VBox vBox3 = new VBox();
    private final VBox vBox4 = new VBox();
    private final VBox vBox5 = new VBox();

    InvoiceDTO invoice;

    public MockInvoiceItemRow(DbInvoiceDTO dbInvoiceDTO, FeeDTO feeDTO) {  // For Mocking
        this.invoiceWidget = dbInvoiceDTO;
        this.itemName = dbInvoiceDTO.getFieldName();
        this.invoiceItem = feeToMockInvoiceItem(feeDTO,dbInvoiceDTO);
        this.footer = null;
        this.invoice = new InvoiceDTO();
        this.fee = feeDTO;
        System.out.println(feeDTO);
        this.items = dbInvoiceDTO.getItems();
        addChildren(dbInvoiceDTO);
        setEdit();
    }

    private InvoiceItemDTO feeToMockInvoiceItem(FeeDTO feeDTO, DbInvoiceDTO dbInvoiceDTO) {
        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(
                0,
                0,
                0,
                Integer.parseInt(dbInvoiceDTO.getFiscalYear()),
                dbInvoiceDTO.getFieldName(),
                dbInvoiceDTO.isCredit(),
                feeDTO.getFieldValue(),
                0
        );
        return invoiceItemDTO;
    }


    private void addChildren(DbInvoiceDTO invoiceWidget) {
        setSpacing(15);
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
        if(this.invoiceItem.isCredit()) total.setId("invoice-text-credit");
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
                textField.setText(fee.getFieldValue());
                textField.setPrefWidth(i.getWidth());
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

    private InvoiceItemDTO setItem() {
        return invoiceWidget.getItems().stream().filter(i -> i.getFieldName().equals(itemName)).findFirst().orElse(null);
    }

    private void setSpinnerListener() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, invoiceWidget.getMaxQty(), invoiceItem.getQty());
		spinner.setValueFactory(spinnerValueFactory);
		spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(new BigDecimal(fee.getFieldValue()).multiply(BigDecimal.valueOf(newValue)));
			total.setText(calculatedTotal);
            invoiceItem.setQty(newValue);
            invoiceItem.setValue(calculatedTotal);
		});
    }

    private void setComboBoxListener() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String calculatedTotal = String.valueOf(BigDecimal.valueOf(newValue).multiply(new BigDecimal(fee.getFieldValue())));
            total.setText(calculatedTotal);
            invoiceItem.setQty(newValue);
            invoiceItem.setValue(calculatedTotal);
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
	            }
	        });
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
                total.setText(value);;
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
