package com.ecsail.gui.tabs.membership;


import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.enums.MemberType;
import com.ecsail.gui.tabs.membership.fiscal.HBoxHistory;
import com.ecsail.gui.tabs.membership.fiscal.HBoxInvoiceList;
import com.ecsail.gui.tabs.membership.fiscal.HBoxSlip;
import com.ecsail.gui.tabs.membership.information.*;
import com.ecsail.gui.tabs.membership.people.HBoxPerson;
import com.ecsail.gui.tabs.membership.people.VBoxAddPerson;
import com.ecsail.models.MembershipTabModel;
import com.ecsail.sql.SqlInsert;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicInteger;

public class TabMembership extends Tab {
	MembershipTabModel model;
	public TabMembership(MembershipListDTO me) {
		super();
		this.model = new MembershipTabModel(me);
		this.setText(setTabLabel());
		BaseApplication.logger.info("Opening Membership tab for "
				+ model.getMembership().getMembershipInfo()
				+ getPerson(MemberType.PRIMARY.getCode()).getNameWithInfo()
		);

		////////// OBJECTS /////////////

		var containerVBox = new VBox();
		var vboxBlue = new VBox();
        var hbox1 = new HBoxHeader(this);  // holds membershipID, Type and Active
		var hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a generic HBox
		var hbox3 = new HBox();

		var informationTabPane = new TabPane();

        //////////// PROPERTIES ///////////////
        
        model.getFiscalTabPane().setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
        model.getPeopleTabPane().setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        informationTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        hbox1.setSpacing(100);  // membership HBox
        hbox2.setSpacing(10);   // holds PersonVBox
        
        vboxBlue.setId("box-frame-dark");
        containerVBox.setId("box-pink");
		model.getPeopleTabPane().setId("custom-tab-pane");
		informationTabPane.setId("custom-tab-pane");
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
        HBox.setHgrow(informationTabPane, Priority.ALWAYS);
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
		informationTabPane.getTabs().add(new Tab("Boats", new HBoxBoat(this)));
		informationTabPane.getTabs().add(new Tab("Notes", new HBoxMembershipNotes(this)));
		informationTabPane.getTabs().add(new Tab("Properties", new HBoxProperties(this)));
		informationTabPane.getTabs().add(new Tab("Attachments", new HBoxAttachment(this)));
		informationTabPane.getTabs().add(new Tab("Address", new HBoxAddress(this)));
		hbox2.getChildren().addAll(model.getPeopleTabPane(), model.getFiscalTabPane());  // new BoxInformation(membership)
		hbox3.getChildren().addAll(informationTabPane);
		containerVBox.getChildren().addAll(hbox1,hbox2,hbox3);
        vboxBlue.getChildren().addAll(containerVBox);  // box blue
        setContent(vboxBlue);
	}
	
	///////////////////////// 	CLASS METHODS //////////////////////////////

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
		HBoxPerson primaryMember;
		if (isNewMembership()) 
			primaryMember = new HBoxPerson(SqlInsert.createUser(model.getMembership().getMsId()), this);// create new primary
		else
			primaryMember = new HBoxPerson(getPerson(MemberType.PRIMARY.getCode()), this); // load the primary member
		return primaryMember;
	}
	
	private boolean isNewMembership() {
		return model.getPeople().size() == 0;
	}
	
	private String setTabLabel() {
		String tabLabel;
		if(model.getMembership().getMembershipId() == 0) {
			tabLabel = "MSID " + model.getMembership().getMsId();
		} else if (isNewMembership()) { 
		    tabLabel = "New Membership";
		} else {
			tabLabel= "Membership " + model.getMembership().getMembershipId();
		}
		return tabLabel;
	}

	public MembershipTabModel getModel() {
		return model;
	}
}
