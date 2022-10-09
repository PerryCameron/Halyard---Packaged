package com.ecsail.gui.tabs;


import com.ecsail.Note;
import com.ecsail.gui.boxes.*;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlPerson;
import com.ecsail.sql.select.SqlMemos;
import com.ecsail.structures.MemLabelsDTO;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.MemoDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabMembership extends Tab {
	private MembershipListDTO membership;
	private MemLabelsDTO labels; // allows labels to be easily changed from another class
	private ObservableList<MemoDTO> memos;
	private ObservableList<PersonDTO> people;  // has to be in this class because we pull up two instances
	private final int PRIMARY = 1;
	private final int SECONDARY = 2;
	private final int DEPENDANT = 3;
	
	public TabMembership(MembershipListDTO me) {
		super();
		this.membership = me;
        this.memos = SqlMemos.getMemos(membership.getMsid());
        this.labels = new MemLabelsDTO();
        this.people = SqlPerson.getPeople(membership.getMsid());
		this.setText(setTabLabel());
		
		////////// OBJECTS /////////////
        Note note = new Note(memos,membership.getMsid());
		TextField duesText = new TextField();
		VBox containerVBox = new VBox();
        VBox vboxBlue = new VBox();
        HBox hbox1 = new HBoxMembership(membership, labels);  // holds membershipID, Type and Active
        HBox hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a genereic HBox
        HBox hbox3 = new HBox();
        TabPane peopleTabPane = new TabPane();
        TabPane fiscalTabPane = new TabPane();
        TabPane informationTabPane = new TabPane();

        //////////// PROPERTIES ///////////////
        
        fiscalTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
        peopleTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        informationTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        hbox1.setSpacing(100);  // membership HBox
        hbox2.setSpacing(10);   // holds PersonVBox
        
        vboxBlue.setId("box-blue");
        containerVBox.setId("box-pink");
        hbox1.setAlignment(Pos.TOP_CENTER);
        hbox2.setAlignment(Pos.TOP_CENTER);
        hbox3.setAlignment(Pos.TOP_CENTER);
        
		/*  // for testing
		infoBox8.setStyle("-fx-background-color: #c5c7c1;");  // gray
		infoBox1.setStyle("-fx-background-color: #4d6955;");  //green
		infoBox2.setStyle("-fx-background-color: #feffab;");  // yellow
		infoBox3.setStyle("-fx-background-color: #e83115;");  // red
		infoBox4.setStyle("-fx-background-color: #201ac9;");  // blue
		infoBox5.setStyle("-fx-background-color: #e83115;");  // purble
		infoBox6.setStyle("-fx-background-color: #15e8e4;");  // light blue
		infoBox7.setStyle("-fx-background-color: #e89715;");  // orange
		*/
        
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
        
        //vboxBlue.setPrefWidth(1024);
        VBox.setVgrow(vboxBlue, Priority.ALWAYS);
        //peopleTabPane.setPrefWidth(472);
        
        ////////// LISTENERS ///////////////////////
        

        ////////// SETTING CONTENT /////////////////
        
		peopleTabPane.getTabs().add(new Tab("Primary", getPrimaryMember(peopleTabPane)));
        if(hasPerson(SECONDARY)) 
        	peopleTabPane.getTabs().add(new Tab("Secondary", getSecondaryMember(peopleTabPane)));
		if(hasPerson(DEPENDANT)) 
			addDependentTabs(peopleTabPane);	
		peopleTabPane.getTabs().add(new Tab("Add", new VBoxAddPerson(peopleTabPane, note, membership)));
		fiscalTabPane.getTabs().add(new Tab("Slip", new HBoxSlip(membership, this)));
		fiscalTabPane.getTabs().add(new Tab("History", new HBoxHistory(membership, labels)));
		fiscalTabPane.getTabs().add(new Tab("Invoices", new HBoxInvoiceList(membership, fiscalTabPane, people, note)));
		informationTabPane.getTabs().add(new Tab("Boats", new HBoxBoat(membership)));
		informationTabPane.getTabs().add(new Tab("Notes", new HBoxMembershipNotes(note)));
		informationTabPane.getTabs().add(new Tab("Properties", new HBoxProperties(people, membership, this)));
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
		int count = 0;
		int count2 = 1;
		for (PersonDTO per : people) {
			if (per.getMemberType() == DEPENDANT) { // if this record is a child of the primary member
				peopleTabPane.getTabs().add(new Tab("Dependent " + count2, new HBoxPerson(people.get(count),membership,peopleTabPane)));
				count2++; // child number
			}
			count++; // array element
		}
	}
	
	private int getPerson(int membertype) {  /// selects a person by membertype
		int element = 0;
		int loop = 0;
		for(PersonDTO per : people) {
			if(per.getMemberType() == membertype) 
				element=loop;
			loop++;
		}
		return element;
	}
	
	private boolean hasPerson(int memberType) {
		boolean type = false;
		for(PersonDTO per : people) {
			if(per.getMemberType() == memberType) 
				type = true;
		}
		return type;
	}

	private HBoxPerson getSecondaryMember(TabPane peopleTabPane) {
		HBoxPerson secondaryMember = new HBoxPerson(people.get(getPerson(SECONDARY)), membership,peopleTabPane); // gets by membertype
		return secondaryMember;
	}
	
	private HBoxPerson getPrimaryMember(TabPane peopleTabPane) {
		HBoxPerson primaryMember;
		if (isNewMembership()) 
			primaryMember = new HBoxPerson(SqlInsert.createUser(membership.getMsid()), membership,peopleTabPane);// create new primary
		else
			primaryMember = new HBoxPerson(people.get(getPerson(PRIMARY)), membership,peopleTabPane); // load the primary member
		return primaryMember;
	}
	
	private boolean isNewMembership() {
		boolean newMembership = false;
		if(people.size() == 0)
			newMembership = true;
		return newMembership;
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

}
