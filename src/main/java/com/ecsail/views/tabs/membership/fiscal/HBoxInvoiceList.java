package com.ecsail.views.tabs.membership.fiscal;


import com.ecsail.BaseApplication;

import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.views.tabs.membership.fiscal.invoice.Invoice;
import com.ecsail.dto.DbInvoiceDTO;
import com.ecsail.dto.FeeDTO;
import com.ecsail.dto.InvoiceDTO;
import com.ecsail.dto.InvoiceItemDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class HBoxInvoiceList extends HBox {
	String currentYear;
	TabMembership parent;

	private static final InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
	public HBoxInvoiceList(TabMembership parent) {
		super();
		this.parent = parent;
		this.currentYear = String.valueOf(Year.now().getValue());

		////////////////////////  OBJECTS   ///////////////////////////////
		var vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		var hbox1 = new HBox();  // holds membershipID, Type and Active
		var vboxPink = new VBox(); // this creates a pink border around the table
		var deleteButtonHBox = new HBox();
		var addFiscalRecord = new Button("Add");
		var deleteFiscalRecord = new Button("Delete");
		var fiscalTableView = new TableView<InvoiceDTO>();
		var Col1 = new TableColumn<InvoiceDTO, Integer>("Year");
		var Col2 = new TableColumn<InvoiceDTO, Integer>("Fees");
		var Col3 = new TableColumn<InvoiceDTO, Integer>("Credit");
		var Col4 = new TableColumn<InvoiceDTO, Integer>("Paid");
		var Col5 = new TableColumn<InvoiceDTO, Integer>("Balance");
		var comboBox = new ComboBox<Integer>();
		populateComboBox(comboBox);
		
		///////////////////// SORT ///////////////////////////////////////////
		parent.getModel().getInvoices().sort((p1, p2) -> Integer.compare(p2.getYear(), (p1.getYear())));
		
		///////////////////// ATTRIBUTES /////////////////////////////////////

		fiscalTableView.setEditable(false);
		fiscalTableView.setFixedCellSize(30);
		fiscalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		Col1.setCellValueFactory(new PropertyValueFactory<>("year"));
		Col2.setCellValueFactory(new PropertyValueFactory<>("total"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("credit"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("paid"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("balance"));


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
			// create invoice for a specified year for this membership
			var newInvoice = new InvoiceDTO(parent.getModel().getMembership().getMsId(), comboBox.getValue());
			// if a record already exists for this year then this is a supplemental record
			if (invoiceRepository.invoiceExists(String.valueOf(comboBox.getValue()), parent.getModel().getMembership())) {
				newInvoice.setSupplemental(true);
			}
			// insert the new record into the SQL database
			invoiceRepository.insertInvoice(newInvoice);
			// insert items for the invoice
			createInvoiceItems(newInvoice.getId(), comboBox.getValue(), parent.getModel().getMembership().getMsId());
			// add new money row to tableview
			parent.getModel().getInvoices().add(newInvoice);
			// send new money row to top
			parent.getModel().getInvoices().sort(Comparator.comparing(InvoiceDTO::getYear).reversed());
			// open a tab for the year we just created
			createTabByYear(newInvoice);
		});
        
		deleteFiscalRecord.setOnAction((event) -> {
			int selectedIndex = fiscalTableView.getSelectionModel().getSelectedIndex();
			var conformation = new Alert(Alert.AlertType.CONFIRMATION);
			conformation.setTitle("Remove Invoice");
			conformation.setHeaderText("Invoice #" + parent.getModel().getInvoices().get(selectedIndex).getId());
			conformation.setContentText("Are sure you want to delete this invoice from " + parent.getModel().getInvoices().get(selectedIndex).getYear() + "?");
			DialogPane dialogPane = conformation.getDialogPane();
			dialogPane.getStylesheets().add("css/dark/dialogue.css");
			dialogPane.getStyleClass().add("dialog");
			var result = conformation.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK){
				BaseApplication.logger.info("deleting fiscal record " + selectedIndex);
				invoiceRepository.deletePaymentByInvoiceID(parent.getModel().getInvoices().get(selectedIndex).getId());
				// TODO get rid of this when I get rid of work credit table
				invoiceRepository.deleteInvoiceItemByInvoiceID(parent.getModel().getInvoices().get(selectedIndex).getId());
				invoiceRepository.deleteInvoiceByID(parent.getModel().getInvoices().get(selectedIndex).getId());
				parent.getModel().getInvoices().remove(selectedIndex);
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
        fiscalTableView.setItems(parent.getModel().getInvoices());
        deleteButtonHBox.getChildren().add(deleteFiscalRecord);
        vboxPink.getChildren().add(fiscalTableView);
		hbox1.getChildren().addAll(new Label("Create new Fiscal Year Record:"),comboBox,addFiscalRecord,deleteButtonHBox);
		vboxGrey.getChildren().addAll(hbox1,vboxPink);
		getChildren().addAll(vboxGrey);	
	}

	private void createInvoiceItems(int invoiceId, Integer year, int msid) {
		// gets db_invoices for selected year
		ArrayList<DbInvoiceDTO> categories = (ArrayList<DbInvoiceDTO>) invoiceRepository.getDbInvoiceByYear(year);
		for (DbInvoiceDTO dbInvoiceDTO : categories) {
			if (dbInvoiceDTO.isItemized()) {
				createItemizedCategories(dbInvoiceDTO, invoiceId, msid, year);
			} else {
				createNonItemizedCategories(invoiceId, year, msid, dbInvoiceDTO);
			}
		}
	}

	private static void createNonItemizedCategories(int invoiceId, Integer year, int msid, DbInvoiceDTO dbInvoiceDTO) {
		invoiceRepository.insertInvoiceItem(new InvoiceItemDTO(invoiceId, msid, year, dbInvoiceDTO.getFieldName()
				, dbInvoiceDTO.isCredit()));
	}

	// creates itemized invoice items
	private static void createItemizedCategories(DbInvoiceDTO dbInvoiceDTO, int invoiceId, int msid, int year) {
		Set<FeeDTO> fees = invoiceRepository.getRelatedFeesAsInvoiceItems(dbInvoiceDTO);
		fees.forEach(feeDTO -> {
			invoiceRepository.insertInvoiceItem(
					new InvoiceItemDTO(invoiceId, msid, year, feeDTO.getDescription(), dbInvoiceDTO.isCredit()));
		});
	}

	/////////////////////  CLASS METHODS /////////////////////////////
	private void populateComboBox(ComboBox<Integer> comboBox) {
		int selectedElement = 0;
		// fill up combo box
		for (int i = Integer.parseInt(String.valueOf(Year.now().getValue())) + 1; i > 1969; i--) {
			// If we do not have a specific year
			if (!parent.getModel().getLabels().getSelectedYear().getText().equals("No Year")) // do we have a current year?
				if (i == Integer.parseInt(parent.getModel().getLabels().getSelectedYear().getText())) selectedElement = i;
			comboBox.getItems().add(i);
		}
		comboBox.setValue(selectedElement); // sets year for combo box to record year
	}

	private void createTab(int rowIndex) {
		// open new invoice tab
		parent.getModel().getFiscalTabPane().getTabs().add(new Tab(String.valueOf(parent.getModel().getInvoices().get(rowIndex).getYear()),
				new Invoice(this, rowIndex))); // current year tab
		// select the new tab
		for(Tab tab: parent.getModel().getFiscalTabPane().getTabs()) {
			if(tab.getText().equals(String.valueOf(parent.getModel().getInvoices().get(rowIndex).getYear())))
				parent.getModel().getFiscalTabPane().getSelectionModel().select(tab);
		}
	}

	private void createTabByYear(InvoiceDTO invoice) {
		// create a tab with the correct year
		Tab newTab = new Tab(String.valueOf(invoice.getYear()));
		// add tab to pane
		parent.getModel().getFiscalTabPane().getTabs().add(newTab);
		// find the index value of the correct Object_Money in fiscals ArrayList
		int fiscalsIndex = getFiscalIndexByYear(invoice.getId());
		// add appropriate invoice to the tab using the index of fiscals
		newTab.setContent(new Invoice(this, fiscalsIndex));
		// open the correct tab
		parent.getModel().getFiscalTabPane().getSelectionModel().select(newTab);
	}

	// searches through list and counts the index value it finds correct INVOICE_ID at.
	private int getFiscalIndexByYear(int invoice_id) {
		AtomicInteger i = new AtomicInteger();
		return parent.getModel().getInvoices().stream().sequential().peek(v -> i.incrementAndGet())
				.anyMatch(fis -> fis.getId() == invoice_id) ? i.get() - 1 : -1;
	}

	public TabMembership getTabMembership() {
		return parent;
	}
}
