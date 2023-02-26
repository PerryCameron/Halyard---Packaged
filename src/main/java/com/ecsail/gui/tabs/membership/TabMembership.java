package com.ecsail.gui.tabs.membership;


import com.ecsail.BaseApplication;
import com.ecsail.gui.common.Note;
import com.ecsail.enums.MemberType;
import com.ecsail.gui.tabs.membership.fiscal.HBoxHistory;
import com.ecsail.gui.tabs.membership.fiscal.HBoxInvoiceList;
import com.ecsail.gui.tabs.membership.fiscal.HBoxSlip;
import com.ecsail.gui.tabs.membership.information.*;
import com.ecsail.gui.tabs.membership.people.HBoxPerson;
import com.ecsail.gui.tabs.membership.people.VBoxAddPerson;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.MemoRepositoryImpl;
import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.MemoRepository;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlInvoice;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlMemos;
import com.ecsail.dto.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	protected PersonRepository personRepository = new PersonRepositoryImpl();
	protected BoatRepository boatRepository = new BoatRepositoryImpl();
	protected MemoRepository memoRepository = new MemoRepositoryImpl();
	private final MembershipListDTO membership;

	protected ObservableList<PersonDTO> people;
	private final TabPane fiscalTabPane = new TabPane();
	private final TabPane peopleTabPane = new TabPane();
	private final Note note;
	private final MemLabelsDTO labels = new MemLabelsDTO();
	private final ObservableList<InvoiceDTO> invoices;
	private final ObservableList<MembershipIdDTO> id;
	private final ObservableList<BoatDTO> boats;

	public TabMembership(MembershipListDTO me) {
		super();
		this.membership = me;
		ObservableList<MemoDTO> memos = FXCollections.observableArrayList(memoRepository.getMemosByMsId(membership.getMsId()));
		this.note = new Note(memos,membership.getMsId());
		this.people = FXCollections.observableList(personRepository.getActivePeopleByMsId(membership.getMsId()));
		this.invoices = SqlInvoice.getInvoicesByMsid(membership.getMsId());
		this.id = FXCollections.observableArrayList(param -> new Observable[]{param.isRenewProperty()});
		this.id.addAll(SqlMembership_Id.getIds(membership.getMsId()));
		this.boats = FXCollections.observableArrayList(boatRepository.getBoatsByMsId(membership.getMsId()));
		this.setText(setTabLabel());
		BaseApplication.logger.info("Opening Membership tab for "
				+ membership.getMembershipInfo()
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
        
        fiscalTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
        peopleTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        informationTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        hbox1.setSpacing(100);  // membership HBox
        hbox2.setSpacing(10);   // holds PersonVBox
        
        vboxBlue.setId("box-frame-dark");
        containerVBox.setId("box-pink");
		peopleTabPane.setId("custom-tab-pane");
		informationTabPane.setId("custom-tab-pane");
		fiscalTabPane.setId("custom-tab-pane");
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
		peopleTabPane.setPrefWidth(560);

        ////////// SETTING CONTENT /////////////////
        
		peopleTabPane.getTabs().add(new Tab("Primary", getPrimaryMember(peopleTabPane)));
        if(hasPerson(MemberType.SECONDARY.getCode()))
        	peopleTabPane.getTabs().add(new Tab("Secondary", getSecondaryMember(peopleTabPane)));
		if(hasPerson(MemberType.DEPENDANT.getCode()))
			addDependentTabs(peopleTabPane);	
		peopleTabPane.getTabs().add(new Tab("Add", new VBoxAddPerson(this)));
		fiscalTabPane.getTabs().add(new Tab("Slip", new HBoxSlip(this)));
		fiscalTabPane.getTabs().add(new Tab("History", new HBoxHistory(this)));
		fiscalTabPane.getTabs().add(new Tab("Invoices", new HBoxInvoiceList(this)));
		informationTabPane.getTabs().add(new Tab("Boats", new HBoxBoat(this)));
		informationTabPane.getTabs().add(new Tab("Notes", new HBoxMembershipNotes(this)));
		informationTabPane.getTabs().add(new Tab("Properties", new HBoxProperties(this)));
		informationTabPane.getTabs().add(new Tab("Attachments", new HBoxAttachment(this)));
		informationTabPane.getTabs().add(new Tab("Address", new HBoxAddress(this)));
		hbox2.getChildren().addAll(peopleTabPane, fiscalTabPane);  // new BoxInformation(membership)
		hbox3.getChildren().addAll(informationTabPane);
		containerVBox.getChildren().addAll(hbox1,hbox2,hbox3);
        vboxBlue.getChildren().addAll(containerVBox);  // box blue
        setContent(vboxBlue);
	}
	
	///////////////////////// 	CLASS METHODS //////////////////////////////

	private void addDependentTabs(TabPane peopleTabPane) {
		AtomicInteger i = new AtomicInteger();
		people.stream()
				.filter(personDTO -> personDTO.getMemberType() == MemberType.DEPENDANT.getCode())
				.forEach(personDTO -> peopleTabPane.getTabs().add(new Tab("Dependent " + (i.incrementAndGet()),
						new HBoxPerson(personDTO,membership,peopleTabPane))));
	}
	
	private PersonDTO getPerson(int memberType) {  /// selects a person by memberType
		return people.stream()
				.filter(personDTO -> personDTO.getMemberType() == memberType)
				.findFirst().orElse(null);
	}
	
	private boolean hasPerson(int memberType) {
		return people.stream().anyMatch(person -> person.getMemberType() == memberType);
	}

	private HBoxPerson getSecondaryMember(TabPane peopleTabPane) {
		return new HBoxPerson(getPerson(MemberType.SECONDARY.getCode()), membership,peopleTabPane);
	}
	
	private HBoxPerson getPrimaryMember(TabPane peopleTabPane) {
		HBoxPerson primaryMember;
		if (isNewMembership()) 
			primaryMember = new HBoxPerson(SqlInsert.createUser(membership.getMsId()), membership,peopleTabPane);// create new primary
		else
			primaryMember = new HBoxPerson(getPerson(MemberType.PRIMARY.getCode()), membership,peopleTabPane); // load the primary member
		return primaryMember;
	}
	
	private boolean isNewMembership() {
		return people.size() == 0;
	}
	
	private String setTabLabel() {
		String tabLabel;
		if(membership.getMembershipId() == 0) {
			tabLabel = "MSID " + membership.getMsId();
		} else if (isNewMembership()) { 
		    tabLabel = "New Membership";
		} else {
			tabLabel= "Membership " + membership.getMembershipId();
		}
		return tabLabel;
	}

	public TabPane getFiscalTabPane() {
		return fiscalTabPane;
	}
	public TabPane getPeopleTabPane() {
		return peopleTabPane;
	}
	public MembershipListDTO getMembership() {
		return membership;
	}
	public ObservableList<PersonDTO> getPeople() {
		return people;
	}
	public Note getNote() {
		return note;
	}
	public MemLabelsDTO getLabels() {
		return labels;
	}
	public ObservableList<InvoiceDTO> getInvoices() {
		return invoices;
	}
	public ObservableList<MembershipIdDTO> getMembershipId() {
		return id;
	}
	public ObservableList<BoatDTO> getBoats() {
		return boats;
	}
}
