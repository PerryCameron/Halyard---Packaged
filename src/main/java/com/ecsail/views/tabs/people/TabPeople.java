package com.ecsail.views.tabs.people;

import com.ecsail.BaseApplication;
import com.ecsail.Launcher;
import com.ecsail.enums.MemberType;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlPerson;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PersonDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
		
		Col1 = new TableColumn<PersonDTO, Integer>("P ID");
		Col1.setCellValueFactory(new PropertyValueFactory<PersonDTO, Integer>("p_id"));
		
		Col2 = new TableColumn<PersonDTO, Integer>("MSID");
		Col2.setCellValueFactory(new PropertyValueFactory<PersonDTO, Integer>("ms_id"));
		
		Col3 = new TableColumn<PersonDTO, MemberType>("Member Type");
		Col3.setPrefWidth(120);
		Col3.setEditable(false);
		Col3.setCellValueFactory(new Callback<CellDataFeatures<PersonDTO, MemberType>, ObservableValue<MemberType>>() {
			 
	        @Override
	        public ObservableValue<MemberType> call(CellDataFeatures<PersonDTO, MemberType> param) {
	        	PersonDTO person = param.getValue();
	            int memberCode = person.getMemberType();
	            MemberType keel = MemberType.getByCode(memberCode);
	            return new SimpleObjectProperty<MemberType>(keel);
	        }
	    });
			
		Col4 = new TableColumn<PersonDTO, String>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<PersonDTO, String>("fname"));
		
		Col5 = new TableColumn<PersonDTO, String>("Last Name");
		Col5.setCellValueFactory(new PropertyValueFactory<PersonDTO, String>("lname"));

		Col6 = new TableColumn<PersonDTO, String>("Occupation");
		Col6.setCellValueFactory(new PropertyValueFactory<PersonDTO, String>("occupation"));
		
		Col8 = new TableColumn<PersonDTO, String>("Active");
		Col8.setCellValueFactory(new PropertyValueFactory<PersonDTO, String>("active"));
		
		personTableView.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6, Col8);
		
		personTableView.setRowFactory(tv -> {
	        TableRow<PersonDTO> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
	                 && event.getClickCount() == 2) {
	                PersonDTO clickedRow = row.getItem();
					Launcher.createMembershipTabFromPeopleList(clickedRow.getMs_id());
					//System.out.println("clickedrow= " + clickedRow.getMs_id());
	            }
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		                 && event.getClickCount() == 1) {
		                PersonDTO clickedRow = row.getItem();
		                createPersonBox(clickedRow);
						//System.out.println("clickedrow= " + clickedRow.getP_id());
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
		if(SqlExists.currentMembershipIdExists(person.getMs_id())) {
		membership = membershipRepository.getMembershipByMsIdAndYear(person.getMs_id(), BaseApplication.selectedYear);
		} else {
		membership = membershipRepository.getMembershipFromListWithoutMembershipId(person.getMs_id());
		}
		personHBox.getChildren().clear();  // remove if exists
	}

	public PersonDTO getPersonByPid(int pid) {
		int index = 0;
		int count = 0;
		for(PersonDTO person : people) {
			if(person.getP_id() == pid) {
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
			if(person.getP_id() == pid) {
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
		Matcher m = p.matcher(o.getLname().toLowerCase());
		
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
		Platform.runLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	personTableView.requestFocus();
		    	personTableView.getSelectionModel().select(pick);
		    	personTableView.scrollTo(pick);
		    }
		});
	}
}
