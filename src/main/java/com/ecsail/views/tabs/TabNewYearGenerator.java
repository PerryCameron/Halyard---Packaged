package com.ecsail.views.tabs;

import com.ecsail.dto.*;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.sql.select.SqlSlip;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TabNewYearGenerator extends Tab {
    private static final ArrayList<SlipDTO> slips = new ArrayList<>();
    private static final ArrayList<FeeDTO> fees = new ArrayList<>();

    private static MembershipRepository membershipRepository = new MembershipRepositoryImpl();
    private static InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
    private static MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();

    public static Logger logger = LoggerFactory.getLogger(TabNewYearGenerator.class);
    int yearToAdd = 2024;


    public TabNewYearGenerator(String text) {
        super(text);
        this.slips.addAll(SqlSlip.getSlips());
        VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        VBox vboxBlue = new VBox();
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        Button createIdsButton = new Button("Create Membership Id's");
        Button createInvoicesButton = new Button("Create Invoices");

        Text bod = new Text("Make sure that all officers and board members are in for the current year");
        Text membership_ID = new Text("The membership ID entries need to be created, compact or keep?");
        Text fees = new Text("The custom invoices need to be set up for current year");
        this.fees.addAll(SqlFee.getFeesFromYear(yearToAdd));
        Text invoices = new Text("Create a new invoice for each person");
        Text credits = new Text("Add credit, wet slip data to invoices");
        vboxBlue.setId("box-blue");
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
        vboxPink.setId("box-pink");

        createIdsButton.setOnAction(event -> createIds());
        createInvoicesButton.setOnAction(event -> createInvoices());

        VBox.setVgrow(vboxGrey, Priority.ALWAYS);
        VBox.setVgrow(vboxPink, Priority.ALWAYS);

        vboxGrey.getChildren().addAll(bod, fees, invoices, createIdsButton, createInvoicesButton, credits);
        vboxBlue.getChildren().add(vboxPink);
        vboxPink.getChildren().add(vboxGrey);
        setContent(vboxBlue);
    }

    private void createInvoices() {
        ArrayList<MembershipListDTO> rosters = (ArrayList<MembershipListDTO>) membershipRepository.getRoster(String.valueOf(yearToAdd - 1), true);
        rosters.forEach(membershipListDTO -> {
						InvoiceDTO invoiceDTO = invoiceRepository.insertInvoice(new InvoiceDTO(membershipListDTO.getMsId(), yearToAdd));
						logger.info("Added invoice for " + membershipListDTO.getFirstName() + " " + membershipListDTO.getLastName());
						// insert items for the invoice
//						createInvoiceItems(invoiceDTO.getId(), yearToAdd, membershipListDTO.getMsId());
        });
        logger.info("Invoice creation complete");
    }

    private void createIds() {
        logger.info("Starting invoice creation");
                ArrayList<MembershipListDTO> rosters = (ArrayList<MembershipListDTO>) membershipRepository.getRoster(String.valueOf(yearToAdd - 1), true);
                rosters.forEach(membershipListDTO -> {
                    // TODO Add ability to compact records
                    // create a new membershipID for user
                    var newMembershipIdDTO = new MembershipIdDTO(String.valueOf(yearToAdd), membershipListDTO.getMsId(),
                            String.valueOf(membershipListDTO.getMembershipId()), membershipListDTO.getMemType());
                    // create invoice for a specified year for this membership
                    logger.info("Added MembershipId for:" + membershipListDTO.getFullName());
                    membershipIdRepository.insert(newMembershipIdDTO);
                });
    }

//						var newInvoice = new InvoiceDTO(membershipListDTO.getMsId(), yearToAdd);
//						// insert the new record into the SQL database
//						SqlInsert.addInvoiceRecord(newInvoice);
//						logger.info("Added invoice for " + membershipListDTO.getFirstName() + " " + membershipListDTO.getLastName());
//						// insert items for the invoice
//						// TODO change dues for each record
//						createInvoiceItems(newInvoice.getId(), yearToAdd, membershipListDTO.getMsId());



    private static void createNonItemizedCategories(int invoiceId, Integer year, int msid, DbInvoiceDTO dbInvoiceDTO) {
        InvoiceItemDTO invoiceItemDTO = invoiceRepository.insertInvoiceItem(
                new InvoiceItemDTO(0, invoiceId, msid, year, dbInvoiceDTO.getFieldName()
                , dbInvoiceDTO.isCredit(), "0.00", 0));
    }

    // creates itemized invoice items

    private static String getFieldName(String description, InvoiceItemDTO item) {
        if (description.equals("Wet Slip")) {
            for (SlipDTO slipDTO : slips) {  // Assuming slips is a collection of SlipDTO
                if (item.getMsId() == slipDTO.getMs_id()) {
                    return "500.00";
                }
            }
        }
        return "";
    }



}
