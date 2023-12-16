package com.ecsail.views.tabs.membership.information;

import com.ecsail.BaseApplication;
import com.ecsail.LabelPrinter;
import com.ecsail.Launcher;
import com.ecsail.pdf.PDF_Envelope;
import com.ecsail.views.tabs.membership.TabMembership;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.dto.LabelDTO;
import com.ecsail.dto.PersonDTO;
import com.itextpdf.io.exceptions.IOException;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.Optional;


///  this class is for the properties tab for the entire membership
public class HBoxProperties extends HBox implements Builder {

    private final TabMembership parent;

    public HBoxProperties(TabMembership parent) {
        super();
        this.parent = parent;
        build();
    }

    @Override
    public Object build() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 5, 5, 10));
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setId("box-background-light");
        hBox.getChildren().addAll(createLeftVBox(), createRightVBox());
        this.setPadding(new Insets(5, 5, 5, 5));  // creates border
        this.setId("custom-tap-pane-frame");
        this.getChildren().add(hBox);
        return this;
    }

    private Node createRightVBox() {
        VBox vBox = new VBox();
        return vBox;
    }

    private Node createLeftVBox() {
        VBox leftVBox = new VBox();
        leftVBox.setSpacing(20);
        leftVBox.getChildren().addAll(printEnvelope(), printCardLabels(), delMembership());
        return leftVBox;
    }

    private Node printEnvelope() {
        HBox hBox = new HBox();
        RadioButton r1 = new RadioButton("#10 Envelope");
        RadioButton r2 = new RadioButton("#1 Catalog");
        Button button = new Button("Create Envelope");
        ToggleGroup tg = new ToggleGroup();
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r1.setSelected(true);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(new Label("Print Envelope"), button,r1,r2);
        button.setOnAction(e -> {
            try {
                new PDF_Envelope(true, r2.isSelected(), String.valueOf(parent.getModel().getMembership().getMembershipId()));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (java.io.IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return hBox;
    }

    private Node delMembership() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(new Label("Delete Membership"), removeMembershipButton());
        return hBox;
    }

    private Node printCardLabels() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(new Label("Print Membership Card Labels"), printLabelsButton1(), printLabelsButton2());
        return hBox;
    }

    private Node removeMembershipButton() {
        Button button = new Button("Delete");
        button.setOnAction(e -> {
            Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
            conformation.setTitle("Delete Membership");
            conformation.setHeaderText("Membership " + parent.getModel().getMembership().getMembershipId());
            conformation.setContentText("Are sure you want to delete this membership?\n\n");
            DialogPane dialogPane = conformation.getDialogPane();
            dialogPane.getStylesheets().add("css/dark/dialogue.css");
            dialogPane.getStyleClass().add("dialog");
            Optional<ButtonType> result = conformation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteMembership(parent.getModel().getMembership().getMsId());
            }
        });
        return button;
    }

    private Node printLabelsButton2() {
        Button button = new Button("Print Secondary");
        button.setOnAction((actionEvent -> {
            ArrayList< LabelDTO> labels = new ArrayList<>();
            LabelDTO label;
            for(PersonDTO person: parent.getModel().getPeople()) {
                if(person.getMemberType() == 2) {
                    label = new LabelDTO();
                    label.setCity("Indianapolis, Indiana");
                    label.setNameAndMemId(person.getFullName() + " #" + parent.getModel().getMembership().getMembershipId());
                    label.setExpires("Type "+parent.getModel().getMembership().getMemType()+", Expires: " + "03/01/" + getYear());
                    label.setMember("Member: U.S. Sailing ILYA &YCA");
                    labels.add(label);
                    LabelPrinter.printMembershipLabel(label);
                }
            }
        }));
        return button;
    }

    private Node printLabelsButton1() {
        Button button = new Button("Print Primary");
        button.setOnAction((actionEvent -> {
            ArrayList< LabelDTO> labels = new ArrayList<>();
            LabelDTO label;
            for(PersonDTO person: parent.getModel().getPeople()) {
                if(person.getMemberType() == 1) {
                    label = new LabelDTO();
                    label.setCity("Indianapolis, Indiana");
                    label.setNameAndMemId(person.getFullName() + " #" + String.valueOf(parent.getModel().getMembership().getMembershipId()));
                    label.setExpires("Type "+parent.getModel().getMembership().getMemType()+", Expires: " + "03/01/" + getYear());
                    label.setMember("Member: U.S. Sailing ILYA &YCA");
                    labels.add(label);
                    LabelPrinter.printMembershipLabel(label);
                }
            }
        }));
        return button;
    }

    private String getYear() {
        int current = Integer.parseInt(BaseApplication.selectedYear);
        return String.valueOf(current + 1);
    }

    private void deleteMembership(int msId) {
        SqlDelete.deleteBoatOwner(msId);
        SqlDelete.deleteMemos(msId);
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
