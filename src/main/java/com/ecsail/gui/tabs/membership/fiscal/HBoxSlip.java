package com.ecsail.gui.tabs.membership.fiscal;


import com.ecsail.Launcher;
import com.ecsail.gui.tabs.membership.TabMembership;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlSlip;
import com.ecsail.sql.select.SqlWaitList;
import com.ecsail.structures.SlipDTO;
import com.ecsail.structures.WaitListDTO;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HBoxSlip extends HBox {
    private SlipDTO slip;
    private final Label noSlipLabel;
    private final HBox errorHBox;
    private final RadioButton rb1 = new RadioButton("Sublease Slip");
    private final RadioButton rb2 = new RadioButton("Reassign Slip");
    private final RadioButton rb3 = new RadioButton("Release Sublease");
    private final HBox mainHBox = new HBox();  // holds controls and Image
    private final VBox mainVBox = new VBox();  // holds hbox1,hbox2
    private final TextField membershipIdTextField = new TextField();
    private final HBox r1 = new HBox(); // for clearing contents
    private final HBox r2 = new HBox(); // for clearing contents
    private final HBox r3 = new HBox();
    private final HBox hbox1 = new HBox();  // slip holder or none
    private final HBox hbox2 = new HBox();  // sublease or none
    private final HBox hbox3 = new HBox();  // Holds button and textfield

    private final Button submitButton = new Button("Submit");
    private final ToggleGroup group = new ToggleGroup();

    TabMembership t;

    public HBoxSlip(TabMembership t) {
        this.t = t;

		// error messaging
		this.noSlipLabel = new Label("None");
        VBox surroundVBox = new VBox();
        this.errorHBox = new HBox();
        CheckBox slipWaitCheckBox = new CheckBox("Slip Waitlist");
        CheckBox kayakWaitCheckBox = new CheckBox("Kayak Waitlist");
        CheckBox shedWaitCheckBox = new CheckBox("Shed Waitlist");
        CheckBox wantsToSubleaseCheckBox = new CheckBox("Wants to Sublease");
        CheckBox wantsToReleaseCheckBox = new CheckBox("Wants to Release");
        CheckBox slipChangeCheckBox = new CheckBox("Slip Change");
        VBox vboxWaitA = new VBox();
        VBox vboxWaitB = new VBox();
        HBox vboxWaitFrame = new HBox();
        VBox vboxWaitOuterFrame = new VBox();
        VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        VBox vboxSpacer = new VBox();
        //HBox imageHBox = new BoxSlipImage(slip.getSlipNumber());

        noSlipLabel.setTextFill(Color.DARKCYAN);

        ////////////  ATTRIBUTES //////////////////

        vboxWaitA.setSpacing(10);
        vboxWaitA.setSpacing(5);
        vboxWaitB.setSpacing(10);
        vboxWaitB.setSpacing(5);

        HBox.setHgrow(vboxWaitA, Priority.ALWAYS);
        HBox.setHgrow(vboxWaitB, Priority.ALWAYS);
        HBox.setHgrow(mainVBox, Priority.SOMETIMES);
        HBox.setHgrow(mainHBox, Priority.ALWAYS);
        HBox.setHgrow(vboxWaitOuterFrame, Priority.ALWAYS);
        HBox.setHgrow(vboxWaitFrame, Priority.ALWAYS);

        errorHBox.setSpacing(13);
        hbox1.setSpacing(5);
        // contains radio buttons, textfield and submit button
        VBox assignVBox = new VBox();
        assignVBox.setSpacing(10);

        vboxWaitFrame.setPadding(new Insets(5, 5, 5, 5));
        vboxWaitA.setPadding(new Insets(5, 5, 5, 5));
        vboxWaitB.setPadding(new Insets(5, 5, 5, 5));
        vboxWaitOuterFrame.setAlignment(Pos.BOTTOM_CENTER);
        errorHBox.setPadding(new Insets(5, 15, 5, 15));  // first Name
        hbox1.setPadding(new Insets(0, 15, 5, 15));
        assignVBox.setPadding(new Insets(30, 15, 5, 15));
        mainHBox.setPadding(new Insets(15, 0, 0, 0));
        hbox2.setPadding(new Insets(5, 15, 5, 15));
        surroundVBox.setPadding(new Insets(0, 5, 5, 5));
        this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

        errorHBox.setAlignment(Pos.CENTER);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        assignVBox.setAlignment(Pos.CENTER_LEFT);

        assignVBox.setPrefWidth(300);
        submitButton.setPrefWidth(70);
        membershipIdTextField.setPrefWidth(40);
        vboxSpacer.setPrefHeight(300);

        hbox2.setSpacing(5);
        hbox3.setSpacing(5);
        this.setSpacing(5);

        rb1.setToggleGroup(group); // sublease rb
        rb1.setSelected(true);
        rb2.setToggleGroup(group); // reassign rb
        rb3.setToggleGroup(group);
        rb1.setUserData("Sublease");
        rb2.setUserData("Reassign");
        rb3.setUserData("Release");

        vboxGrey.setId("box-background-light");
        this.setId("custom-tap-pane-frame");
        vboxWaitFrame.setId("box-frame-dark");
        vboxWaitA.setId("box-background-light");
        vboxWaitB.setId("box-background-light");
        ////////////// LISTENERS //////////////////////////

        /// this is for auto screen refreshing
        t.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean focusLost, Boolean focusGained) -> {
            if (focusGained) {  // focus Gained
                refreshScreen();
            }
        });

        // this is for deciding if text field should be displayed
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                String userData = (group.getSelectedToggle().getUserData().toString());
                switch (userData) {
                    case "Sublease", "Reassign" -> membershipIdTextField.setVisible(true);
					case "Release" -> membershipIdTextField.setVisible(false);
                    // this will release the slip when user hits submit button
                    default -> {
                    }
                    // do nothing for now, may add error handling?
                }
            }
        });

        submitButton.setOnAction((event) -> {
            errorHBox.getChildren().clear();
            if (selectedRadioButton("Release"))
                releaseSlip();
            else if (selectedRadioButton("Sublease"))
                subleaseSlip();
            else if (selectedRadioButton("Reassign"))
                reassignSlip(getMsidFromTextField());
            refreshScreen();
        });

        slipWaitCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                SqlUpdate.updateWaitList(t.getMembership().getMsid(), "SLIP_WAIT", newValue));
        kayakWaitCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                SqlUpdate.updateWaitList(t.getMembership().getMsid(), "KAYAK_RACK_WAIT", newValue));
        shedWaitCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                SqlUpdate.updateWaitList(t.getMembership().getMsid(), "SHED_WAIT", newValue));
        wantsToSubleaseCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                SqlUpdate.updateWaitList(t.getMembership().getMsid(), "WANT_SUBLEASE", newValue));
        wantsToReleaseCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                SqlUpdate.updateWaitList(t.getMembership().getMsid(), "WANT_RELEASE", newValue));
        slipChangeCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                SqlUpdate.updateWaitList(t.getMembership().getMsid(), "WANT_SLIP_CHANGE", newValue));

        ///////////// ACTIONS //////////////////////////////

        WaitListDTO waitList;
        if (SqlExists.waitListExists(t.getMembership().getMsid())) {
            waitList = SqlWaitList.getWaitList(t.getMembership().getMsid());
        } else { //it doesn't exist
            waitList = new WaitListDTO(t.getMembership().getMsid(), false,
                    false, false, false, false, false);
            SqlInsert.addWaitList(waitList);
        }
        slipWaitCheckBox.setSelected(waitList.isSlipWait());
        kayakWaitCheckBox.setSelected(waitList.isKayakWait());
        shedWaitCheckBox.setSelected(waitList.isShedWait());
        wantsToSubleaseCheckBox.setSelected(waitList.isWantToSublease());
        slipChangeCheckBox.setSelected(waitList.isWantSlipChange());
        wantsToReleaseCheckBox.setSelected(waitList.isWantsRelease());
        displaySlip();
        addControls();

        /////////////  ASSIGN CHILDREN  /////////////////////

        vboxWaitA.getChildren().addAll(slipWaitCheckBox, kayakWaitCheckBox
                , shedWaitCheckBox);
        vboxWaitB.getChildren().addAll(wantsToSubleaseCheckBox
                , wantsToReleaseCheckBox, slipChangeCheckBox);
        vboxWaitFrame.getChildren().addAll(vboxWaitA, vboxWaitB);
        vboxWaitOuterFrame.getChildren().add(vboxWaitFrame);
        assignVBox.getChildren().addAll(r1, r2, r3, hbox3);
        mainVBox.getChildren().addAll(hbox1, hbox2, assignVBox); // add slip information
        surroundVBox.getChildren().addAll(errorHBox, mainHBox, vboxSpacer, vboxWaitOuterFrame);  // put this in for error messages
        vboxGrey.getChildren().addAll(surroundVBox);
        this.getChildren().addAll(vboxGrey);
    }

    ////////////  CLASS METHODS ///////////////
    private void refreshScreen() {
        clearControls();
        displaySlip();
        addControls();
    }

    private int getMsidFromTextField() {
        if (isInteger(membershipIdTextField.getText()))
            return SqlMembership_Id.getMsidFromMembershipID(Integer.parseInt(membershipIdTextField.getText()));
        else return 0;
    }

    private boolean isInteger(String stringToTest) {
        try {
            Integer.parseInt(stringToTest);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void reassignSlip(int ms_id) {
        if (membershipExists(ms_id)) { // member exists using membershipID
            checkForSublease(ms_id);  // releases sub-lease if it exists
            if (ownsSlip(ms_id)) // this member already has a slip
                printErrorMessage("Membership " + membershipIdTextField.getText() + " already has a slip");
            else {
                SqlUpdate.releaseSlip(t.getMembership()); // Release all sub-leases in SQL
                SqlUpdate.reAssignSlip(ms_id, t.getMembership()); // reassign slip to this ms_id in SQL
                setSlipToNone();
            }
        } else
            printErrorMessage("User " + membershipIdTextField.getText() + " does not exist");
    }

    private void checkForSublease(int ms_id) {
        if (isLeasingSlip(ms_id)) { // OK this member is already sub-leasing a slip, lets release it
            releaseSlipSublease(ms_id);
        }
    }

    private void releaseSlipSublease(int ms_id) {  // overloaded method
        slip = SqlSlip.getSubleasedSlip(ms_id);
        SqlUpdate.subleaserReleaseSlip(ms_id); // this releases the slip
    }

    private boolean membershipExists(int ms_id) {
        return SqlExists.memberShipExists(ms_id);
    }

    private void setSlipToNone() { // for reassignSlip()
        hbox1.getChildren().clear();
        hbox2.getChildren().clear();
        hbox1.getChildren().addAll(new Label("Slip Number:"), noSlipLabel);
        t.getMembership().setSlip("");
    }

    private void setSlipAsNone() { // for displayInformation()
        slip = new SlipDTO(0, t.getMembership().getMsid(), "none", 0, "");
        hbox1.getChildren().addAll(new Label("Slip Number:"), noSlipLabel);
        setRadioButtonVisibility(false, false, false);
        hbox3.setVisible(false);
    }

    private void releaseSlip() {  // overloaded method
        SqlUpdate.releaseSlip(t.getMembership());
        r3.setVisible(false);
    }

    private boolean isLeasingSlip(int ms_id) {
        return SqlExists.slipRentExists(ms_id);
    }

    private void subleaseSlip() {
        int ms_id = getMsidFromTextField();
        if (membershipExists(ms_id)) { // member exists using membershipID
            if (ownsSlip(ms_id))  // this member already has a slip
                printErrorMessage("Membership " + membershipIdTextField.getText() + " already has a slip");
            else {
                SqlUpdate.updateSlip(ms_id, t.getMembership());
                slip = SqlSlip.getSlip(t.getMembership().getMsid()); // added in because sublease changed it
                t.getMembership().setSlip(slip.getSlipNumber());
            }
        } else  // Member does not exist
            printErrorMessage("User " + membershipIdTextField.getText() + " does not exist");
    }

    private void printErrorMessage(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setTextFill(Color.RED);
        errorHBox.getChildren().add(errorLabel);
    }

    private boolean selectedRadioButton(String selection) {
        return group.getSelectedToggle().getUserData().toString().equals(selection);
    }

    private void addControls() {
        hbox3.getChildren().addAll(membershipIdTextField, submitButton);
        r1.getChildren().add(rb1); // in hbox so can remove
        r2.getChildren().add(rb2);
        r3.getChildren().add(rb3);
        mainHBox.getChildren().addAll(mainVBox, new HBoxSlipImage(slip.getSlipNumber()));
    }

    private void clearControls() {
        hbox1.getChildren().clear();
        hbox2.getChildren().clear();
        hbox3.getChildren().clear();
        r1.getChildren().clear();
        r2.getChildren().clear();
        r3.getChildren().clear();
        mainHBox.getChildren().clear();
    }

    private void setMouseListener(Text text, int msid) {
        Color color = (Color) text.getFill();
        if (color == Color.CORNFLOWERBLUE) {
            text.setOnMouseExited(ex -> text.setFill(Color.CORNFLOWERBLUE));
        } else {
            text.setOnMouseExited(ex -> text.setFill(Color.BLACK));
        }
        text.setOnMouseEntered(en -> text.setFill(Color.RED));

        text.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Launcher.createTabForBoxSlip(msid);
            }
        });
    }

    private boolean ownsSlip(int ms_id) {
        return SqlExists.slipExists(ms_id);
    }

    private void displaySlip() {
        if (hasSlip())
            checkIfLeasingOut();
        else if (isSubleasingSlip()) // does not own, but is sub-leasing
            setSubleasedSlip();
        else  // has no slip
            setSlipAsNone();
    }

    private void checkIfLeasingOut() {
        Label slipNumber = setMemberSlip();
        if (isLeasingOutSlip())
            displaySublease(slipNumber);
        else
            displaySlipNumberLabel(slipNumber);
    }

    private void displaySlipNumberLabel(Label slipNumber) {
        hbox1.getChildren().addAll(new Label("Slip Number:"), slipNumber);  // member plus their slip number
    }

    private boolean isSubleasingSlip() {
        return SqlExists.slipRentExists(t.getMembership().getMsid());
    }

    private boolean isLeasingOutSlip() {
        return slip.getSubleased_to() != 0;
    }

    private void setSubleasedSlip() {
        slip = SqlSlip.getSubleasedSlip(t.getMembership().getMsid());  // gets the slip information using the sub-slip attribute
        Label slipNumber = new Label(slip.getSlipNumber());
        slipNumber.setTextFill(Color.DARKCYAN);
        slipNumber.setStyle("-fx-font-weight: bold;");
        Text subLease = new Text("" + SqlMembership_Id.getMembershipIDfromMsid(slip.getMs_id())); // Converts to membership ID
        subLease.setStyle("-fx-font-weight: bold;");
        setMouseListener(subLease, slip.getMs_id()); // need to get msid from
        hbox1.getChildren().addAll(new Text("Slip Number:"), slipNumber);
        hbox2.getChildren().addAll(new Text("Subleased from:"), subLease);
        setRadioButtonVisibility(false, false, false);
        hbox3.setVisible(false);
    }

    private Label setMemberSlip() {
        slip = SqlSlip.getSlip(t.getMembership().getMsid());
        Label slipNumber = new Label(slip.getSlipNumber());
        slipNumber.setTextFill(Color.BLUE);
        slipNumber.setStyle("-fx-font-weight: bold;");
        submitButton.setVisible(true);
        membershipIdTextField.setVisible(true);
        setRadioButtonVisibility(true, true, false);
        hbox3.setVisible(true);
        return slipNumber;
    }

    private void displaySublease(Label slipNumber) {
        Text subLease = new Text("" + SqlMembership_Id.getMembershipIDfromMsid(slip.getSubleased_to()));
        subLease.setStyle("-fx-font-weight: bold;");
        setMouseListener(subLease, t.getMembership().getSubleaser());
        hbox1.getChildren().addAll(new Text("Slip Number:"), slipNumber);  // member plus their slip number
        hbox2.getChildren().addAll(new Text("Subleased to:"), subLease);  // subleased to (member)
        setRadioButtonVisibility(true, true, true);
    }

    private boolean hasSlip() {
        return SqlExists.slipExists((t.getMembership().getMsid()));
    }

    private void setRadioButtonVisibility(boolean rb1, boolean rb2, boolean rb3) {
        r1.setVisible(rb1);
        r2.setVisible(rb2);
        r3.setVisible(rb3);
    }
}
