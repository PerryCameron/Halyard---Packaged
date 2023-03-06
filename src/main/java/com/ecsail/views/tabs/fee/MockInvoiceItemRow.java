package com.ecsail.views.tabs.fee;

import com.ecsail.StringTools;
import com.ecsail.dto.DbInvoiceDTO;
import com.ecsail.dto.FeeDTO;
import com.ecsail.dto.InvoiceDTO;
import com.ecsail.dto.InvoiceItemDTO;
import javafx.beans.value.ObservableValue;
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
    private final DbInvoiceDTO dbInvoiceDTO;
    private ComboBox<Integer> comboBox;
    private final InvoiceItemDTO invoiceItem;
    private final FeeDTO fee;
    private final VBox vBox1 = new VBox();
    private final VBox vBox2 = new VBox();
    private final VBox vBox3 = new VBox();
    private final VBox vBox4 = new VBox();
    private final VBox vBox5 = new VBox();

    protected final FeeEditControls parent;

    InvoiceDTO invoice;

    public MockInvoiceItemRow(FeeEditControls feeEditControls, FeeDTO feeDTO) {  // For Mocking
        this.parent = feeEditControls;
        this.dbInvoiceDTO = parent.parent.selectedFeeRow.dbInvoiceDTO;
        this.itemName = parent.parent.selectedFeeRow.dbInvoiceDTO.getFieldName();
        this.fee = feeDTO;
        this.invoiceItem = feeToMockInvoiceItem(feeDTO);
        this.invoice = new InvoiceDTO();

//        vBox1.setStyle("-fx-background-color: #c5c7c1;");  // gray
//        vBox2.setStyle("-fx-background-color: #4d6955;");  //green
//        vBox3.setStyle("-fx-background-color: #feffab;");  // yellow
//        vBox4.setStyle("-fx-background-color: #e83115;");  // red
//        vBox5.setStyle("-fx-background-color: #201ac9;");  // blue
        setEdit();
        addChildren(parent.parent.selectedFeeRow.dbInvoiceDTO);
    }

    protected InvoiceItemDTO feeToMockInvoiceItem(FeeDTO feeDTO) {
        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(
                0,
                0,
                0,
                Integer.parseInt(dbInvoiceDTO.getFiscalYear()),
                dbInvoiceDTO.getFieldName(),
                dbInvoiceDTO.isCredit(),
                "0.00",
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
        vBox3.getChildren().add(setX(dbInvoiceDTO));
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
                if (dbInvoiceDTO.isPrice_editable())
                    setPriceChangeListener(new TextField(price.getText()));
                return spinner;
            }
            case "combo-box" -> {
                comboBox = new ComboBox<>();
                comboBox.setPrefWidth(i.getWidth());
                price.setText(String.valueOf(fee.getFieldValue()));
                // fill comboBox
                for (int j = 0; j < dbInvoiceDTO.getMaxQty() + 1; j++) comboBox.getItems().add(j);
                comboBox.getSelectionModel().select(invoiceItem.getQty());
                setComboBoxListener();
                if (dbInvoiceDTO.isPrice_editable())
                    setPriceChangeListener(new TextField(price.getText()));
                return comboBox;
            }
            case "itemized" -> { // more complex so layout and logic in different class
                setForTitledPane(); // don't need this for others because new set of boxes every time
                TitledPane titledPane = new TitledPane(fee.getFieldName(),new MockItemizedCategory(this));
                titledPane.setId("title-pane");
                titledPane.setExpanded(false);
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

//    private InvoiceItemDTO setItem() {
//        return dbInvoiceDTO.getItems().stream().filter(i -> i.getFieldName().equals(itemName)).findFirst().orElse(null);
//    }

    private void setSpinnerListener() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, dbInvoiceDTO.getMaxQty(), invoiceItem.getQty());
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
	            	if(!StringTools.isBigDecimal(textField.getText())) {
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
                if(!StringTools.isBigDecimal(textField.getText())) {
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

    public DbInvoiceDTO getDbInvoiceDTO() {
        return dbInvoiceDTO;
    }
}
