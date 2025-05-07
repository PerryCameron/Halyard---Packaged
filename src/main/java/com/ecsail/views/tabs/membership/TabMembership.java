package com.ecsail.views.tabs.membership;


import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.enums.MemberType;
import com.ecsail.views.tabs.membership.fiscal.HBoxHistory;
import com.ecsail.views.tabs.membership.fiscal.HBoxInvoiceList;
import com.ecsail.views.tabs.membership.fiscal.HBoxSlip;
import com.ecsail.views.tabs.membership.information.*;
import com.ecsail.views.tabs.membership.people.HBoxPerson;
import com.ecsail.views.tabs.membership.people.VBoxAddPerson;
import com.ecsail.models.MembershipTabModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class TabMembership extends Tab {

	public static Logger logger = LoggerFactory.getLogger(TabMembership.class);
	MembershipTabModel model;
	HBoxMembershipNotes membershipNotes;
	public TabMembership(MembershipListDTO me) {
		super();
		this.model = new MembershipTabModel(me);
		this.setText(setTabLabel());
        logger.info("Opening Membership tab for {}{}", model.getMembership().getMembershipInfo(), getPerson(MemberType.PRIMARY.getCode()).getNameWithInfo());
		////////// OBJECTS /////////////

		var containerVBox = new VBox();
		var vboxBlue = new VBox();
        var hbox1 = new HBoxHeader(this);  // holds membershipID, Type and Active
		var hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a generic HBox
		var hbox3 = new HBox();


        //////////// PROPERTIES ///////////////
        
        model.getFiscalTabPane().setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
        model.getPeopleTabPane().setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        model.getInformationTabPane().setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        hbox1.setSpacing(100);  // membership HBox
        hbox2.setSpacing(10);   // holds PersonVBox
        
        vboxBlue.setId("box-frame-dark");
        containerVBox.setId("box-pink");
		model.getPeopleTabPane().setId("custom-tab-pane");
		model.getInformationTabPane().setId("custom-tab-pane");
		model.getFiscalTabPane().setId("custom-tab-pane");
        hbox1.setAlignment(Pos.TOP_CENTER);
        hbox2.setAlignment(Pos.TOP_CENTER);
        hbox3.setAlignment(Pos.TOP_CENTER);
        
        hbox1.setPadding(new Insets(15,15,0,15));
        hbox2.setPadding(new Insets(5,15,15,15));
        hbox3.setPadding(new Insets(0,15,10,15));
        vboxBlue.setPadding(new Insets(10,10,10,10));
        
        VBox.setVgrow(vboxBlue, Priority.ALWAYS);
        VBox.setVgrow(hbox1, Priority.ALWAYS);
        VBox.setVgrow(hbox2, Priority.ALWAYS);
        VBox.setVgrow(hbox3, Priority.ALWAYS);
        VBox.setVgrow(containerVBox, Priority.ALWAYS);
        HBox.setHgrow(hbox3, Priority.ALWAYS);
        HBox.setHgrow(model.getInformationTabPane(), Priority.ALWAYS);
        VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		model.getPeopleTabPane().setPrefWidth(560);

        ////////// SETTING CONTENT /////////////////
        
		model.getPeopleTabPane().getTabs().add(new Tab("Primary", getPrimaryMember()));
        if(hasPerson(MemberType.SECONDARY.getCode()))
        	model.getPeopleTabPane().getTabs().add(new Tab("Secondary", getSecondaryMember()));
		if(hasPerson(MemberType.DEPENDANT.getCode()))
			addDependentTabs();
		model.getPeopleTabPane().getTabs().add(new Tab("Add", new VBoxAddPerson(this)));
		model.getFiscalTabPane().getTabs().add(new Tab("Slip", new HBoxSlip(this)));
		model.getFiscalTabPane().getTabs().add(new Tab("History", new HBoxHistory(this)));
		model.getFiscalTabPane().getTabs().add(new Tab("Invoices", new HBoxInvoiceList(this)));
		model.getInformationTabPane().getTabs().add(new Tab("Boats", new HBoxBoat(this)));
		this.membershipNotes = new HBoxMembershipNotes(this);
		model.getInformationTabPane().getTabs().add(new Tab("Notes", membershipNotes));
		model.getInformationTabPane().getTabs().add(new Tab("Properties", new HBoxProperties(this)));
		model.getInformationTabPane().getTabs().add(new Tab("Attachments", new HBoxAttachment(this)));
		model.getInformationTabPane().getTabs().add(new Tab("Address", new HBoxAddress(this)));
		hbox2.getChildren().addAll(model.getPeopleTabPane(), model.getFiscalTabPane());  // new BoxInformation(membership)
		hbox3.getChildren().addAll(model.getInformationTabPane());
		containerVBox.getChildren().addAll(hbox1,hbox2,hbox3);
        vboxBlue.getChildren().addAll(containerVBox);  // box blue
        setContent(vboxBlue);
	}
	
	///////////////////////// 	CLASS METHODS //////////////////////////////

	public void selectTab(String tabName) {
		model.getInformationTabPane().getTabs().stream()
				.filter(tab -> tabName.equals(tab.getText())) // Find the tab with the label "Notes"
				.findFirst() // Get the first occurrence
				.ifPresent(model.getInformationTabPane().getSelectionModel()::select);
	}

	public void editRow() {
		membershipNotes.editNewRow();
	}

	private void addDependentTabs() {
		AtomicInteger i = new AtomicInteger();
		model.getPeople().stream()
				.filter(personDTO -> personDTO.getMemberType() == MemberType.DEPENDANT.getCode())
				.forEach(personDTO -> model.getPeopleTabPane().getTabs().add(new Tab("Dependent " + (i.incrementAndGet()),
						new HBoxPerson(personDTO,this))));
	}
	
	private PersonDTO getPerson(int memberType) {  /// selects a person by memberType
		return model.getPeople().stream()
				.filter(personDTO -> personDTO.getMemberType() == memberType)
				.findFirst().orElse(null);
	}
	
	private boolean hasPerson(int memberType) {
		return model.getPeople().stream().anyMatch(person -> person.getMemberType() == memberType);
	}

	private HBoxPerson getSecondaryMember() {
		return new HBoxPerson(getPerson(MemberType.SECONDARY.getCode()), this);
	}
	
	private HBoxPerson getPrimaryMember() {
		HBoxPerson primaryMember = new HBoxPerson(getPerson(MemberType.PRIMARY.getCode()), this); // load the primary member
		return primaryMember;
	}
	
	private String setTabLabel() {
		String tabLabel;
		if(model.getMembership().getMembershipId() == 0) {
			tabLabel = "MSID " + model.getMembership().getMsId();
		} else {
			tabLabel= "Membership " + model.getMembership().getMembershipId();
		}
		return tabLabel;
	}

	public MembershipTabModel getModel() {
		return model;
	}
}
