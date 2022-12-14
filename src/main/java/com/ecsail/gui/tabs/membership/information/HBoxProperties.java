package com.ecsail.gui.tabs.membership.information;

import com.ecsail.BaseApplication;
import com.ecsail.LabelPrinter;
import com.ecsail.Launcher;
import com.ecsail.gui.tabs.membership.TabMembership;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.structures.PersonDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Optional;


///  this class is for the properties tab for the entire membership
public class HBoxProperties extends HBox {

    //private TextField duesText;
    public HBoxProperties(TabMembership tm) {
        super();

        //this.duesText = dt;
        //////////// OBJECTS ///////////////
        HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
        VBox leftVBox = new VBox(); // contains viewable children
        VBox rightVBox = new VBox();
        HBox hbox1 = new HBox();  // holds membershipID, Type and Active
        HBox hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a generic HBox
        HBox hbox3 = new HBox();  // holds address, city, state, zip
        HBox hbox4 = new HBox();  // holds membership type
        HBox hbox5 = new HBox();  // holds delete membership
        Button removeMembershipButton = new Button("Delete");
        Button printLabelsButton = new Button("Print");


//		HalyardAlert alert = new HalyardAlert(AlertType.INFORMATION);

        /////////////  ATTRIBUTES /////////////

        hbox1.setSpacing(5);  // membership HBox
        hbox2.setSpacing(5);  // membership HBox
        hbox4.setSpacing(5);  // membership HBox
        hbox3.setSpacing(5);  // membership HBox
        hbox5.setSpacing(5);  // membership HBox
        leftVBox.setSpacing(10);
        this.setSpacing(10);

        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox4.setAlignment(Pos.CENTER_LEFT);
        hbox5.setAlignment(Pos.CENTER_LEFT);

        hboxGrey.setPadding(new Insets(5, 5, 5, 10));
        this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

        HBox.setHgrow(hboxGrey, Priority.ALWAYS);

        this.setId("custom-tap-pane-frame");
        hboxGrey.setId("box-background-light");

        ///////////// LISTENERS ////////////

        removeMembershipButton.setOnAction(e -> {
            Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
            conformation.setTitle("Delete Membership");
            conformation.setHeaderText("Membership " + tm.getMembership().getMembershipId());
            conformation.setContentText("Are sure you want to delete this membership?\n\n");
            DialogPane dialogPane = conformation.getDialogPane();
            dialogPane.getStylesheets().add("css/dark/dialogue.css");
            dialogPane.getStyleClass().add("dialog");
            Optional<ButtonType> result = conformation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteMembership(tm.getMembership().getMsid());
            }
        });

        printLabelsButton.setOnAction((actionEvent -> tm.getPeople().stream()
				.filter(personDTO -> personDTO.getMemberType() < 3)
				.forEach(p -> {
					String[] lines = {
							p.getFname() + " " + p.getLname()
							, String.valueOf(tm.getMembership().getMembershipId())
							, tm.getMembership().getMemType()
							, "03/01/2023"};
					// TODO fix the date above
					LabelPrinter.printMembershipLabel(lines);
				})));

        ///////////// SET CONTENT ////////////////////
        hbox3.getChildren().addAll(new Label("Print Membership Card Labels"), printLabelsButton);
        hbox5.getChildren().addAll(new Label("Delete Membership"), removeMembershipButton);
        leftVBox.getChildren().addAll(hbox2, hbox3, hbox5);
        hboxGrey.getChildren().addAll(leftVBox, rightVBox);
        getChildren().add(hboxGrey);
    }

    private void deleteMembership(int msId) {
        SqlDelete.deleteBoatOwner(msId);
        SqlDelete.deleteMemos(msId);
        SqlDelete.deleteWorkCredits(msId);
        SqlDelete.deleteAllPaymentsAndInvoicesByMsId(msId);
        SqlDelete.deleteWaitList(msId);
        SqlDelete.deleteMembershipId(msId); // removes all entries
        ObservableList<PersonDTO> people = SqlPerson.getPeople(msId);
        for (PersonDTO p : people) {
            SqlDelete.deletePhones(p.getP_id());
            SqlDelete.deleteEmail(p.getP_id());
            SqlDelete.deleteOfficer(p.getP_id());
            SqlDelete.deletePerson(p.getP_id());
        }
        SqlDelete.deleteMembership(msId);
        Launcher.removeMembershipRow(msId);
        Launcher.closeActiveTab();
        BaseApplication.logger.info("Deleted membership msid: " + msId);
    }
}
