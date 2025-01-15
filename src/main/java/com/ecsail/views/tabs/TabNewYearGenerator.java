package com.ecsail.views.tabs;

import com.ecsail.dto.*;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.implementations.SlipRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.interfaces.SlipRepository;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Set;

public class TabNewYearGenerator extends Tab {
    private static final ArrayList<SlipDTO> slips = new ArrayList<>();
    private static final ArrayList<FeeDTO> fees = new ArrayList<>();

    private static MembershipRepository membershipRepository = new MembershipRepositoryImpl();
    private static InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
    private static MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();
    private static SlipRepository slipRepository = new SlipRepositoryImpl();

    public static Logger logger = LoggerFactory.getLogger(TabNewYearGenerator.class);
    int yearToAdd = 2025;
    int id = 1;

    public TabNewYearGenerator(String text) {
        super(text);
        this.slips.addAll(slipRepository.getSlips());
        VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        VBox vboxBlue = new VBox();
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        Button createIdsButton = new Button("Create Membership Id's");
        Button createInvoicesButton = new Button("Create Invoices");

        Text bod = new Text("Make sure that all officers and board members are in for the current year");
        Text membership_ID = new Text("The membership ID entries need to be created, compact or keep?");
        Text fees = new Text("The custom invoices need to be set up for current year");
        this.fees.addAll(invoiceRepository.getFeesFromYear(yearToAdd));
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
        // get a list of active memberships for previous year.
        ArrayList<MembershipListDTO> rosters = (ArrayList<MembershipListDTO>) membershipRepository.getRoster(String.valueOf(yearToAdd - 1), true);
        rosters.forEach(membershipListDTO -> {
						InvoiceDTO invoiceDTO = invoiceRepository.insertInvoice(new InvoiceDTO(membershipListDTO.getMsId(), yearToAdd));
						logger.info("Added invoice for " + membershipListDTO.getFirstName() + " " + membershipListDTO.getLastName());
            createInvoiceItems(invoiceDTO.getId(), yearToAdd, invoiceDTO.getMsId());
        });
        logger.info("Invoice creation complete");
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

    private void createIds() {
        logger.info("Starting invoice creation");
                ArrayList<MembershipListDTO> rosters = (ArrayList<MembershipListDTO>) membershipRepository.getRoster(String.valueOf(yearToAdd - 1), true);
                boolean compactIds = true;
                rosters.forEach(membershipListDTO -> {
                    if(membershipListDTO.getMsId() != 466) {
                        var newMembershipIdDTO = new MembershipIdDTO(String.valueOf(yearToAdd), membershipListDTO.getMsId(),
                                String.valueOf(membershipListDTO.getMembershipId()), membershipListDTO.getMemType());
                        if (compactIds) {
                            newMembershipIdDTO.setMembershipId(String.valueOf(id));
                            id++;
                            if(id == 29) id++;
                        }
                        // create invoice for a specified year for this membership
                        logger.info("Added MembershipId for:" + newMembershipIdDTO.getMembershipId() + " " + membershipListDTO.getFullName());
                    membershipIdRepository.insert(newMembershipIdDTO);
                    }
                });
    }

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
