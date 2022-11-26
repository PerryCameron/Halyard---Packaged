package com.ecsail.gui.boxes;


import com.ecsail.BaseApplication;
import com.ecsail.Note;
import com.ecsail.gui.boxes.invoice.HBoxInvoice;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.sql.select.SqlInvoice;
import com.ecsail.sql.select.SqlInvoiceWidget;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class HBoxInvoiceList extends HBox {
	private static ObservableList<InvoiceDTO> invoices = null;
	private static TabPane parentTabPane;
	private static MembershipDTO membership;
	private static ObservableList<PersonDTO> people;
	private static Note note;

	private int id; // used for making records
	String currentYear;

	public HBoxInvoiceList(MembershipDTO membership, TabPane t, ObservableList<PersonDTO> p, Note n) {
		super();
		HBoxInvoiceList.membership = membership;
		this.currentYear = BaseApplication.selectedYear;
		HBoxInvoiceList.note = n;
		HBoxInvoiceList.parentTabPane = t;
		HBoxInvoiceList.people = p;
		
		////////////////////////  OBJECTS   ///////////////////////////////
		var vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		var hbox1 = new HBox();  // holds membershipID, Type and Active
		var vboxPink = new VBox(); // this creates a pink border around the table
		var deleteButtonHBox = new HBox();
		var addFiscalRecord = new Button("Add");
		var deleteFiscalRecord = new Button("Delete");
		HBoxInvoiceList.invoices = SqlInvoice.getInvoicesByMsid(membership.getMsid());
		var fiscalTableView = new TableView<InvoiceDTO>();
		var Col1 = new TableColumn<InvoiceDTO, Integer>("Year");
		var Col2 = new TableColumn<InvoiceDTO, Integer>("Fees");
		var Col3 = new TableColumn<InvoiceDTO, Integer>("Credit");
		var Col4 = new TableColumn<InvoiceDTO, Integer>("Paid");
		var Col5 = new TableColumn<InvoiceDTO, Integer>("Balance");
		var comboBox = new ComboBox<Integer>();
		populateComboBox(comboBox);
		comboBox.getSelectionModel().select(1);
		///////////////////// SORT ///////////////////////////////////////////
		HBoxInvoiceList.invoices.sort((p1, p2) -> Integer.compare(p2.getYear(), (p1.getYear())));
		
		///////////////////// ATTRIBUTES /////////////////////////////////////

		fiscalTableView.setEditable(false);
		fiscalTableView.setFixedCellSize(30);
		fiscalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		Col1.setCellValueFactory(new PropertyValueFactory<>("year"));
		Col2.setCellValueFactory(new PropertyValueFactory<>("total"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("credit"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("paid"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("balance"));

		Col2.setStyle( "-fx-alignment: CENTER;");
		Col2.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col3.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col4.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col5.setStyle( "-fx-alignment: CENTER-RIGHT;");
		
		/////////////  ATTRIBUTES /////////////
		fiscalTableView.getColumns().addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5));
		fiscalTableView.getSortOrder().add(Col1);  // start sorted by membershipID
		fiscalTableView.sort();

		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Year
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Mem Id
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Mem Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Renewed
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Renew Late
		
		vboxGrey.setPrefWidth(460);
		comboBox.setPrefWidth(80);
		
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		vboxGrey.setPadding(new Insets(10, 10, 10, 10));
		deleteButtonHBox.setPadding(new Insets(0,0,0,40));
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hbox1.setSpacing(5);  // membership HBox
        vboxGrey.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER_LEFT);

		vboxGrey.setId("box-background-light");
		setId("custom-tap-pane-frame");
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(fiscalTableView, Priority.ALWAYS);
		HBox.setHgrow(fiscalTableView, Priority.ALWAYS);
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);

        ////////////////  LISTENERS ///////////////////
		addFiscalRecord.setOnAction((event) -> {
				// get the next available key for money_id table
				int invoiceId = SqlSelect.getNextAvailablePrimaryKey("invoice","id");
				// create appropriate money object for this membership
				var newInvoice = new InvoiceDTO(invoiceId, membership.getMsid(),
						comboBox.getValue(), "0.00", "0.00", "0.00", "0.00", 0, false, false, false,"0.00");

				// if a record already exists for this year then this is a supplemental record
				if (SqlExists.invoiceExists(String.valueOf(comboBox.getValue()), membership)) {
					newInvoice.setSupplemental(true);
				}

				// insert the new record into the SQL database
				SqlInsert.addInvoiceRecord(newInvoice);
				// insert items for the invoice
				createInvoiceItems(invoiceId, comboBox.getValue(), membership.getMsid());
				// add new money row to tableview
				invoices.add(newInvoice);
				// send new money row to top
				invoices.sort(Comparator.comparing(InvoiceDTO::getYear).reversed());
				// open a tab for the year we just created
				createTabByYear(newInvoice);
		});
        
		deleteFiscalRecord.setOnAction((event) -> {
			int selectedIndex = fiscalTableView.getSelectionModel().getSelectedIndex();
			var conformation = new Alert(Alert.AlertType.CONFIRMATION);
			conformation.setTitle("Remove Invoice");
			conformation.setHeaderText("Invoice #" + invoices.get(selectedIndex).getId());
			conformation.setContentText("Are sure you want to delete this invoice from " + invoices.get(selectedIndex).getYear() + "?");
			DialogPane dialogPane = conformation.getDialogPane();
			dialogPane.getStylesheets().add("css/dark/dialogue.css");
			dialogPane.getStyleClass().add("dialog");
			var result = conformation.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK){
				BaseApplication.logger.info("deleting fiscal record " + selectedIndex);
				SqlDelete.deletePaymentByMoneyID(invoices.get(selectedIndex).getId());
				// TODO get rid of this when I get rid of work credit table
				SqlDelete.deleteInvoiceItemByInvoiceID(invoices.get(selectedIndex).getId());
				SqlDelete.deleteInvoiceByID(invoices.get(selectedIndex).getId());

				invoices.remove(selectedIndex);
			}
		});
		
		fiscalTableView.setRowFactory(tv -> {
			TableRow<InvoiceDTO> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					int rowIndex = row.getIndex();
					createTab(rowIndex);
				}
			});
			return row;
		});
        
		///////////// SET CONTENT ////////////////////
        fiscalTableView.setItems(invoices);
        deleteButtonHBox.getChildren().add(deleteFiscalRecord);
        vboxPink.getChildren().add(fiscalTableView);
		hbox1.getChildren().addAll(new Label("Create new Fiscal Year Record:"),comboBox,addFiscalRecord,deleteButtonHBox);
		vboxGrey.getChildren().addAll(hbox1,vboxPink);
		getChildren().addAll(vboxGrey);	
	}

	private void createInvoiceItems(int invoiceId, Integer year, int msid) {
		ArrayList<InvoiceWidgetDTO> categories = SqlInvoiceWidget.getInvoiceWidgetsByYear(year);
		id = SqlSelect.getNextAvailablePrimaryKey("invoice_item","ID");
		categories.stream().forEach(c -> {
			InvoiceItemDTO item = new InvoiceItemDTO(id,invoiceId,msid,year,c.getObjectName(),c.isMultiplied(),c.isIs_credit(),"0.00",0);
			id++;
			SqlInsert.addInvoiceItemRecord(item);
		});
	}

	/////////////////////  CLASS METHODS /////////////////////////////
	private void populateComboBox(ComboBox<Integer> comboBox) {
		for(int i = Integer.parseInt(BaseApplication.selectedYear) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
	}

	private BigDecimal getDues(int year) {  // takes the membership type and gets the dues
		DefinedFeeDTO selectedDefinedFee = SqlDefinedFee.getDefinedFeeByYear(String.valueOf(year));
		BigDecimal dues;
		if(membership.getMemType() != null) {
			dues = switch (membership.getMemType()) {
				case "RM" -> selectedDefinedFee.getDues_regular();
				case "SO" -> selectedDefinedFee.getDues_social();
				case "FM" -> selectedDefinedFee.getDues_family();
				case "LA" -> selectedDefinedFee.getDues_lake_associate();
				default -> BigDecimal.valueOf(0);
			};
		} else dues = BigDecimal.valueOf(0);
		return dues;
	}
	
	private static void createTab(int rowIndex) {
		parentTabPane.getTabs().add(new Tab(invoices.get(rowIndex).getYear()+ "", new HBoxInvoice(membership, invoices.get(rowIndex), note))); // current year tab
		for(Tab tab: parentTabPane.getTabs()) {
			if(tab.getText().equals(invoices.get(rowIndex).getYear() + ""))
		parentTabPane.getSelectionModel().select(tab);
		}
	}

	private static void createTabByYear(InvoiceDTO invoice) {
		// create a tab with the correct year
		Tab newTab = new Tab(String.valueOf(invoice.getYear()));
		// add tab to pane
		parentTabPane.getTabs().add(newTab);
		// find the index value of the correct Object_Money in fiscals ArrayList
		int fiscalsIndex = getFiscalIndexByYear(invoice.getId());
		// add appropriate invoice to the tab using the index of fiscals
		newTab.setContent(new HBoxInvoice(membership, invoices.get(fiscalsIndex), note));
		// open the correct tab
		parentTabPane.getSelectionModel().select(newTab);
	}

	// searches through list and counts the index value it finds correct money_id at.
	private static int getFiscalIndexByYear(int money_id) {
//		return (int) fiscals
//				.stream()
//				.filter(fis -> fis.getMoney_id() == money_id).count();
		AtomicInteger i = new AtomicInteger();
		return invoices.stream().sequential().peek(v -> i.incrementAndGet())
				.anyMatch(fis -> fis.getId() == money_id) ? i.get() - 1 : -1;
	}


}
