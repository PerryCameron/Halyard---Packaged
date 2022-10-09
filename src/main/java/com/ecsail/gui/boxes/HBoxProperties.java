package com.ecsail.gui.boxes;

import com.ecsail.LabelPrinter;
import com.ecsail.Launcher;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Optional;

///  this class is for the properties tab in membership view
public class HBoxProperties extends HBox {
	private final MembershipListDTO membership;
	private final ObservableList<PersonDTO> people;
	//private TextField duesText;
	public HBoxProperties(ObservableList<PersonDTO> people, MembershipListDTO m, Tab membershipTab) {
		super();
		this.membership = m;
		this.people = people;
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
		Button printLabelsButton = new Button("Print Card Labels");
//		HalyardAlert alert = new HalyardAlert(HalyardAlert.AlertType.CONFIRMATION);
//		DialogPane dialogPane = alert.getDialogPane();

//		HalyardAlert alert = new HalyardAlert(AlertType.INFORMATION);

		/////////////  ATTRIBUTES /////////////

        hbox1.setSpacing(5);  // membership HBox
        hbox2.setSpacing(5);  // membership HBox
        hbox4.setSpacing(5);  // membership HBox
        hbox5.setSpacing(5);  // membership HBox
        leftVBox.setSpacing(10);
        this.setSpacing(10);
        
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox4.setAlignment(Pos.CENTER_LEFT);
        hbox5.setAlignment(Pos.CENTER_LEFT);

		hboxGrey.setPadding(new Insets(5, 5, 5, 10));
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		
		this.setId("custom-tap-pane-frame");
		hboxGrey.setId("box-background-light");
		
		///////////// LISTENERS ////////////

		removeMembershipButton.setOnAction(e -> {
//			alert.setTitle("Remove Membership");
//			alert.setHeaderText("Membership " + membership.getMembershipId());
//			alert.setContentText("Are sure you want to delete membership " + membership.getMembershipId() + "?");
//			Optional<ButtonType> result = alert.showAndWait();
//			if (result.get() == ButtonType.OK){
//			   deleteMembership(membership.getMsid());
//			}
		});

		printLabelsButton.setOnAction((actionEvent -> {
			people.stream()
					.filter(personDTO -> personDTO.getMemberType() < 3)
					.forEach(p -> {
						String lines[] = {
								p.getFname() + " " + p.getLname()
								, String.valueOf(membership.getMembershipId())
								, membership.getMemType()
								, "03/01/2023"};
						LabelPrinter.printMembershipLabel(lines);
					});
		}));
		
		///////////// SET CONTENT ////////////////////

		hbox5.getChildren().addAll(new Label("Remove Membership"),removeMembershipButton, printLabelsButton);
		leftVBox.getChildren().addAll(hbox2,hbox3,hbox5);
		hboxGrey.getChildren().addAll(leftVBox,rightVBox);
		getChildren().add(hboxGrey);
	}
	
	private void deleteMembership(int ms_id) {
		if (!SqlExists.paymentsExistForMembership(ms_id)) {
			SqlDelete.deleteBoatOwner(ms_id);
			SqlDelete.deleteMemos(ms_id);
			SqlDelete.deleteWorkCredits(ms_id);
			SqlDelete.deleteMonies(ms_id);
			SqlDelete.deleteWaitList(ms_id);
			SqlDelete.deleteMembershipId(ms_id); // removes all entries
			ObservableList<PersonDTO> people = SqlPerson.getPeople(ms_id);
			for (PersonDTO p : people) {
				SqlDelete.deletePhones(p.getP_id());
				SqlDelete.deleteEmail(p.getP_id());
				SqlDelete.deleteOfficer(p.getP_id());
				SqlDelete.deletePerson(p.getP_id());
			}
			SqlDelete.deleteMembership(ms_id);
			Launcher.removeMembershipRow(ms_id);
			Launcher.closeActiveTab();
			System.out.println("Deleting Membership.");
		} else {
			// do not delete the membership
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("There is a problem");
			alert.setHeaderText("This membership contains payment entries");
			alert.setContentText("Before deleting this membership you need to manually remove the payment entries.");
			alert.showAndWait();
		}
	}
}
