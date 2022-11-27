package com.ecsail.gui.tabs.deposits;

import com.ecsail.BaseApplication;
import com.ecsail.sql.select.SqlInvoice;
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

	public TabDeposits(String text) {
		super(text);
		this.selectedYear = BaseApplication.selectedYear;
		this.invoices = getInvoiceItems(selectedYear);
		VboxControls vboxControls = new VboxControls(this);

		TableView<InvoiceWithMemberInfoDTO> tableView = new InvoicesTableView(this);
		var vboxGreen = new VBox(); // this is the vbox for organizing all the widgets
		var vboxYellow = new VBox();
		var mainHBox = new HBox(); // this separates table content from controls
		vboxYellow.setPadding(new Insets(10, 10, 10, 10));

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


}
