package com.ecsail.views.tabs;

import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlDbInvoice;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlSlip;
import com.ecsail.dto.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Set;

public class TabNewYearGenerator extends Tab {
	private static final ArrayList<SlipDTO> slips = new ArrayList<>();
	private static final ArrayList<FeeDTO> fees = new ArrayList<>();


	public TabNewYearGenerator(String text) {
		super(text);
		this.slips.addAll(SqlSlip.getSlips());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Button createInvoicesButton = new Button("Create Invoices");

		Text bod = new Text("Make sure that all officers and board members are in for the current year");
		Text membership_ID = new Text("The membership ID entries need to be created, compact or keep?");
		Text fees = new Text("The custom invoices need to be set up for current year");
		this.fees.addAll(SqlFee.getFeesFromYear(2023));
		Text invoices = new Text("Create a new invoice for each person");
		Text credits = new Text("Add credit, wet slip data to invoices");
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");

		createInvoicesButton.setOnAction(event -> {
			ObservableList<MembershipListDTO> rosters = SqlMembershipList.getRoster("2022",true);
			rosters.forEach(membershipListDTO -> {
				// TODO Add ability to compact records
				// create a new membershipID for user
				var newMembershipIdDTO = new MembershipIdDTO("2023",membershipListDTO.getMsId(),
						String.valueOf(membershipListDTO.getMembershipId()), membershipListDTO.getMemType());
				// create invoice for a specified year for this membership
				SqlInsert.addMembershipId(newMembershipIdDTO);
				var newInvoice = new InvoiceDTO(membershipListDTO.getMsId(), 2023);
				// insert the new record into the SQL database
				SqlInsert.addInvoiceRecord(newInvoice);
				// insert items for the invoice
				// TODO change dues for each record
				createInvoiceItems(newInvoice.getId(), 2023, membershipListDTO.getMsId());
			});
		});

		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink,Priority.ALWAYS);

		vboxGrey.getChildren().addAll(bod,fees,invoices, createInvoicesButton,credits);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}

	private void createInvoiceItems(int invoiceId, Integer year, int msid) {
		// gets db_invoices for selected year
		ArrayList<DbInvoiceDTO> categories = SqlDbInvoice.getDbInvoiceByYear(year);
		for (DbInvoiceDTO dbInvoiceDTO : categories) {
			if (dbInvoiceDTO.isItemized()) {
				createItemizedCategories(invoiceId, year, msid, dbInvoiceDTO);
			} else {
				createNonItemizedCategories(invoiceId, year, msid, dbInvoiceDTO);
			}
		}
	}

	private static void createNonItemizedCategories(int invoiceId, Integer year, int msid, DbInvoiceDTO dbInvoiceDTO) {
		InvoiceItemDTO item;
		item = new InvoiceItemDTO(0, invoiceId, msid, year, dbInvoiceDTO.getFieldName()
				, dbInvoiceDTO.isCredit(), "0.00", 0, false);
		updateItem(item);
		SqlInsert.addInvoiceItemRecord(item);
	}

	// creates itemized invoice items
	private static void createItemizedCategories(int invoiceId, Integer year, int msid, DbInvoiceDTO dbInvoiceDTO) {
		Set<FeeDTO> fees = SqlFee.getRelatedFeesAsInvoiceItems(dbInvoiceDTO);
		fees.forEach(feeDTO -> {
			InvoiceItemDTO item = new InvoiceItemDTO(0, invoiceId, msid, year, feeDTO.getDescription()
					, dbInvoiceDTO.isCredit(), "0.00", 0, false);
			updateItem(item);
			SqlInsert.addInvoiceItemRecord(item);
		});
	}

	private static void updateItem(InvoiceItemDTO item) {
		System.out.println(item);
		if(item.getFieldName().equals("Wet Slip")) {
			System.out.println("Matched Wet Slip");
			slips.forEach(slipDTO -> {
				if (item.getMsId() == slipDTO.getMs_id()) item.setValue("450.00");
			});
		}
	}


}
