package com.ecsail.gui.tabs.deposits;

import com.ecsail.BaseApplication;
import com.ecsail.sql.select.SqlInvoice;
import com.ecsail.structures.DepositDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabDeposits extends Tab {
	private final ObservableList<InvoiceWithMemberInfoDTO> invoices;
	private final String selectedYear;
	private final TableView<InvoiceWithMemberInfoDTO> tableView;
	private VboxControls vboxControls;
	private final DepositDTO depositDTO = new DepositDTO();

	public TabDeposits(String text) {
		super(text);
		this.selectedYear = BaseApplication.selectedYear;
		this.invoices = getInvoiceItems(selectedYear);
		this.vboxControls = new VboxControls(this);
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

	private ObservableList<InvoiceWithMemberInfoDTO> getInvoiceItems(String year) {
		return SqlInvoice.getInvoicesWithMembershipInfoByYear(year);
	}

	public ObservableList<InvoiceWithMemberInfoDTO> getInvoices() {
		return invoices;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public TableView<InvoiceWithMemberInfoDTO> getTableView() {
		return tableView;
	}

	public VboxControls getVboxControls() {
		return vboxControls;
	}

	public DepositDTO getDepositDTO() {
		return depositDTO;
	}
}
