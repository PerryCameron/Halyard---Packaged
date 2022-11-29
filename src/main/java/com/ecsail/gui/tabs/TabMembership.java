package com.ecsail.gui.tabs;


import com.ecsail.BaseApplication;
import com.ecsail.Note;
import com.ecsail.enums.MemberType;
import com.ecsail.gui.boxes.*;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlPerson;
import com.ecsail.sql.select.SqlMemos;
import com.ecsail.structures.MemLabelsDTO;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabMembership extends Tab {
	private final MembershipListDTO membership;
	private final ObservableList<PersonDTO> people;  // has to be in this class because we pull up two instances
	private int count = 1;
	private TabPane fiscalTabPane = new TabPane();
	private Note note;
	private MemLabelsDTO labels = new MemLabelsDTO();
	
	public TabMembership(MembershipListDTO me) {
		super();
		this.membership = me;
		var memos = SqlMemos.getMemos(membership.getMsid());
		this.note = new Note(memos,membership.getMsid());
		// allows labels to be easily changed from another class

        this.people = SqlPerson.getPeople(membership.getMsid());
		this.setText(setTabLabel());
		BaseApplication.logger.info("Opening Membership tab for "
				+ membership.getMembershipInfo()
				+ getPerson(MemberType.PRIMARY.getCode()).getNameWithInfo()
		);

		////////// OBJECTS /////////////

		var containerVBox = new VBox();
		var vboxBlue = new VBox();
        var hbox1 = new HBoxMembership(this);  // holds membershipID, Type and Active
		var hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a generic HBox
		var hbox3 = new HBox();
		var peopleTabPane = new TabPane();
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

        ////////// SETTING CONTENT /////////////////
        
		peopleTabPane.getTabs().add(new Tab("Primary", getPrimaryMember(peopleTabPane)));
        if(hasPerson(MemberType.SECONDARY.getCode()))
        	peopleTabPane.getTabs().add(new Tab("Secondary", getSecondaryMember(peopleTabPane)));
		if(hasPerson(MemberType.DEPENDANT.getCode()))
			addDependentTabs(peopleTabPane);	
		peopleTabPane.getTabs().add(new Tab("Add", new VBoxAddPerson(peopleTabPane, note, membership)));
		fiscalTabPane.getTabs().add(new Tab("Slip", new HBoxSlip(membership, this)));
		fiscalTabPane.getTabs().add(new Tab("History", new HBoxHistory(membership, labels)));
		fiscalTabPane.getTabs().add(new Tab("Invoices", new HBoxInvoiceList(this)));
		informationTabPane.getTabs().add(new Tab("Boats", new HBoxBoat(membership)));
		informationTabPane.getTabs().add(new Tab("Notes", new HBoxMembershipNotes(note)));
		informationTabPane.getTabs().add(new Tab("Properties", new HBoxProperties(people, membership)));
		informationTabPane.getTabs().add(new Tab("Attachments", new HBoxAttachment(membership)));
		informationTabPane.getTabs().add(new Tab("Address", new HBoxAddress(membership)));
		hbox2.getChildren().addAll(peopleTabPane, fiscalTabPane);  // new BoxInformation(membership)
		hbox3.getChildren().addAll(informationTabPane);
		containerVBox.getChildren().addAll(hbox1,hbox2,hbox3);
        vboxBlue.getChildren().addAll(containerVBox);  // box blue
        setContent(vboxBlue);
	}
	
	///////////////////////// 	CLASS METHODS //////////////////////////////

	private void addDependentTabs(TabPane peopleTabPane) {
		people.stream()
				.filter(personDTO -> personDTO.getMemberType() == MemberType.DEPENDANT.getCode())
				.forEach(personDTO -> {
					peopleTabPane.getTabs().add(new Tab("Dependent " + (count), new HBoxPerson(personDTO,membership,peopleTabPane)));
					count++; // child number
				});
		count = 1;
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
			primaryMember = new HBoxPerson(SqlInsert.createUser(membership.getMsid()), membership,peopleTabPane);// create new primary
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
			tabLabel = "MSID " + membership.getMsid();
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
}
