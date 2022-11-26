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
	private String selectedYear;

	public TabDeposits(String text) {
		super(text);
		this.selectedYear = BaseApplication.selectedYear;
		this.invoices = SqlInvoice.getInvoicesWithMembershipInfoByYear(selectedYear);
		TableView tableView = new InvoicesTableView(this);


		var controlsHBox = new HBox(); // outer blue box
		var vboxGreen = new VBox(); // this is the vbox for organizing all the widgets
		var vboxYellow = new VBox();
//		var vboxRed = new VBox(); // t
		var mainHBox = new HBox(); // this separates table content from controls
		vboxGreen.setStyle("-fx-background-color: #4d6955;");  //green
		vboxYellow.setStyle("-fx-background-color: #feffab;");  // yellow
//		vboxRed.setStyle("-fx-background-color: #e83115;");  // red
		mainHBox.setStyle("-fx-background-color: #201ac9;");  // blue
//		infoBox5.setStyle("-fx-background-color: #e83115;");  // purple
		vboxYellow.setPadding(new Insets(10, 10, 10, 10));

		VBox.setVgrow(vboxYellow, Priority.ALWAYS);
		VBox.setVgrow(vboxGreen, Priority.ALWAYS);
		VBox.setVgrow(mainHBox,Priority.ALWAYS);

		vboxYellow.getChildren().add(vboxGreen);
		vboxGreen.getChildren().add(mainHBox);
		mainHBox.getChildren().addAll(tableView, new VboxControls());
		setContent(vboxYellow);
	}

	public ObservableList<InvoiceWithMemberInfoDTO> getInvoices() {
		return invoices;
	}

	public String getSelectedYear() {
		return selectedYear;
	}
}
