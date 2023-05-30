package com.ecsail.views.tabs.deposits;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class TabDeposits extends Tab {
	protected InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
	private final ObservableList<InvoiceWithMemberInfoDTO> invoices;
	private final String selectedYear;
	private final InvoicesTableView tableView;
	private final VboxDepositControls vboxControls;
	private final DepositDTO depositDTO = new DepositDTO();

	public TabDeposits(String text) {
		super(text);
		this.selectedYear = BaseApplication.selectedYear;
		this.invoices = FXCollections.observableArrayList(getInvoiceItems(selectedYear));
		// controls on the right hand side of the screen
		this.vboxControls = new VboxDepositControls(this);
		// tableview on left hand side of the screen
		this.tableView = new InvoicesTableView(this);

		var vboxGreen = new VBox(); // this is the vbox for organizing all the widgets
		var vboxYellow = new VBox();
		var mainHBox = new HBox(); // this separates table content from controls
		vboxYellow.setPadding(new Insets(10, 0, 10, 10));

		VBox.setVgrow(vboxYellow, Priority.ALWAYS);
		VBox.setVgrow(vboxGreen, Priority.ALWAYS);
		VBox.setVgrow(mainHBox,Priority.ALWAYS);

		vboxYellow.getChildren().add(vboxGreen);
		vboxGreen.getChildren().add(mainHBox);
		mainHBox.getChildren().addAll(tableView, vboxControls);
		setContent(vboxYellow);
	}

	private List<InvoiceWithMemberInfoDTO> getInvoiceItems(String year) {
//		return SqlInvoice.getInvoicesWithMembershipInfoByYear(year);
		return invoiceRepository.getInvoicesWithMembershipInfoByYear(year);
	}

	public ObservableList<InvoiceWithMemberInfoDTO> getInvoices() {
		return invoices;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public InvoicesTableView getTableView() {
		return tableView;
	}

	public DepositDTO getDepositDTO() {
		return depositDTO;
	}
}