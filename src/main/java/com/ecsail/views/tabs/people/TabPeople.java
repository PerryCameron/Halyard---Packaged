package com.ecsail.views.tabs.people;

import com.ecsail.Launcher;
import com.ecsail.enums.MemberType;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlPerson;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PersonDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabPeople extends Tab {

	//private Object_MembershipList membership;
	public static ObservableList<PersonDTO> people;
	private static HBox personHBox = new HBox();
	TableColumn<PersonDTO, Integer> Col1;
	TableColumn<PersonDTO, Integer> Col2;
	TableColumn<PersonDTO, MemberType> Col3;
	static TableColumn<PersonDTO, String> Col4;
	static TableColumn<PersonDTO, String> Col5;
	TableColumn<PersonDTO, String> Col6;
	TableColumn<PersonDTO, String> Col8;
	static TableView<PersonDTO> personTableView;
	static int pick = 1;

	MembershipRepository membershipRepository;

	public TabPeople(String text) {
		super(text);
		TabPeople.people = SqlPerson.getPeople();
		this.membershipRepository = new MembershipRepositoryImpl();
		
		VBox vboxBlue = new VBox(); // main vbox
		VBox vbox2 = new VBox(); // sepearates box search and box people
		HBox hboxPink = new HBox();  // main hbox

		vboxBlue.setId("box-frame-dark");
		hboxPink.setId("box-pink");
		vboxBlue.setPadding(new Insets(12,12,15,12));
		hboxPink.setPadding(new Insets(3,3,5,3));
		vboxBlue.setAlignment(Pos.TOP_CENTER);
		//vbox1.setPrefHeight(768);
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(hboxPink, Priority.ALWAYS);
		hboxPink.setSpacing(10);
		vbox2.setSpacing(5);
		
		personTableView = new TableView<>();
		personTableView.setItems(people);
		personTableView.setPrefWidth(518);
		personTableView.setFixedCellSize(30);
		personTableView.setPrefHeight(680);
		
		Col1 = new TableColumn<>("P ID");
		Col1.setCellValueFactory(new PropertyValueFactory<>("p_id"));
		
		Col2 = new TableColumn<>("MSID");
		Col2.setCellValueFactory(new PropertyValueFactory<>("ms_id"));
		
		Col3 = new TableColumn<>("Member Type");
		Col3.setPrefWidth(120);
		Col3.setEditable(false);
		Col3.setCellValueFactory(param -> {
			PersonDTO person = param.getValue();
			int memberCode = person.getMemberType();
			MemberType keel = MemberType.getByCode(memberCode);
			return new SimpleObjectProperty<MemberType>(keel);
		});
			
		Col4 = new TableColumn<>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<>("fname"));
		
		Col5 = new TableColumn<>("Last Name");
		Col5.setCellValueFactory(new PropertyValueFactory<>("lname"));

		Col6 = new TableColumn<>("Occupation");
		Col6.setCellValueFactory(new PropertyValueFactory<>("occupation"));
		
		Col8 = new TableColumn<>("Active");
		Col8.setCellValueFactory(new PropertyValueFactory<>("active"));
		
		personTableView.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6, Col8);
		
		personTableView.setRowFactory(tv -> {
	        TableRow<PersonDTO> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
	                 && event.getClickCount() == 2) {
	                PersonDTO clickedRow = row.getItem();
					Launcher.createMembershipTabFromPeopleList(clickedRow.getMsId());
	            }
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		                 && event.getClickCount() == 1) {
		                PersonDTO clickedRow = row.getItem();
		                createPersonBox(clickedRow);
		        }
	        });
	        return row ;
	    });
		
		vboxBlue.getChildren().add(hboxPink);
		vbox2.getChildren().addAll(new HBoxSearch(personTableView), personHBox);
		hboxPink.getChildren().addAll(personTableView,vbox2);
		setContent(vboxBlue);
		
	}
	// creates array list of people objects populated from SQL database

	private void createPersonBox(PersonDTO person)  {
		MembershipListDTO membership = null;
		if(SqlExists.currentMembershipIdExists(person.getMsId())) {
		membership = membershipRepository.getMembershipByMsIdAndYear(person.getMsId(), String.valueOf(Year.now().getValue()));
		} else {
		membership = membershipRepository.getMembershipFromListWithoutMembershipId(person.getMsId());
		}
		personHBox.getChildren().clear();  // remove if exists
	}

	public PersonDTO getPersonByPid(int pid) {
		int index = 0;
		int count = 0;
		for(PersonDTO person : people) {
			if(person.getpId() == pid) {
				//System.out.println("Found pid " + pid);
				index = count; 
			}
			count++;
		}
		return people.get(index);  
	}
	
	public static int getIndexByPid(int pid) {
		int index = 0;
		int count = 0;
		for(PersonDTO person : people) {
			if(person.getpId() == pid) {
				//System.out.println("Found pid " + pid);
				index = count; 
			}
			count++;
		}
		return index; 
	}
	
	public static void searchLastName(String searchString) {
		int count = 0;
		Pattern p = Pattern.compile("^" +searchString, Pattern.MULTILINE);
		boolean flag = true;
		for (PersonDTO o : personTableView.getItems()) {
		Matcher m = p.matcher(o.getLastName().toLowerCase());
		
			while(m.find()) {
				//System.out.println(m.group() + " found on row " + count);
				if(flag) {
					pick = count;
					flag = false;
				}
			}
		count++;
			  //System.out.println(Col5.getCellData(o));
		}
		Platform.runLater(() -> {
			personTableView.requestFocus();
			personTableView.getSelectionModel().select(pick);
			personTableView.scrollTo(pick);
		});
	}
}
