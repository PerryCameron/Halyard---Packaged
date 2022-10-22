package com.ecsail.gui.boxes;


import com.ecsail.BaseApplication;
import com.ecsail.EditCell;
import com.ecsail.enums.PhoneType;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlEmail;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.EmailDTO;
import com.ecsail.structures.PersonDTO;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class HBoxEmail extends HBox {
	
	private final PersonDTO person;
	private final ObservableList<EmailDTO> email;
	private final TableView<EmailDTO> emailTableView;

	public HBoxEmail(PersonDTO p) {
		this.person = p;
		this.email =  FXCollections.observableArrayList(param -> new Observable[] { param.isPrimaryUseProperty() });
		this.email.addAll(SqlEmail.getEmail(person.getP_id()));
		
		//////////////// OBJECTS //////////////////////
		
		Button emailAdd = new Button("Add");
		Button emailDelete = new Button("Delete");
		VBox vboxButtons = new VBox(); // holds email buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice appearance
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		emailTableView = new TableView<>();

		/////////////////  ATTRIBUTES  /////////////////////
		emailAdd.setPrefWidth(60);
		emailDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);

		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(emailTableView, Priority.ALWAYS);
		VBox.setVgrow(emailTableView, Priority.ALWAYS);
		
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vboxButtons.setSpacing(5);
		
		hboxGrey.setId("box-background-light");
		vboxPink.setId("box-pink");
		this.setId("box-background-light");
		
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table

		
		///////////////// TABLE VIEW ///////////////////////
		
			
			emailTableView.setItems(email);
			emailTableView.setFixedCellSize(30);
			emailTableView.setEditable(true);
			emailTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
			
			TableColumn<EmailDTO, String> Col1 = createColumn("Email", EmailDTO::emailProperty);
			Col1.setPrefWidth(137);
			Col1.setOnEditCommit(t -> {
				t.getTableView().getItems().get(t.getTablePosition().getRow())
						.setEmail(t.getNewValue());
				int email_id = t.getTableView().getItems().get(t.getTablePosition().getRow())
						.getEmail_id();
				SqlUpdate.updateEmail(email_id, t.getNewValue());
			});
			
			// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
			TableColumn<EmailDTO, Boolean> Col2 = new TableColumn<>("Primary");
			Col2.setCellValueFactory(param -> {
				EmailDTO email = param.getValue();
				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(email.isIsPrimaryUse());
				// Note: singleCol.setOnEditCommit(): Not work for
				// CheckBoxTableCell.

				// When "Primary Use?" column change.
				booleanProp.addListener((observable, oldValue, newValue) -> {
					email.setIsPrimaryUse(newValue);
					SqlUpdate.updateEmail("primary_use",email.getEmail_id(), newValue);
				});
				return booleanProp;
			});

		//
		Col2.setCellFactory(p1 -> {
			CheckBoxTableCell<EmailDTO, Boolean> cell = new CheckBoxTableCell<>();
			cell.setAlignment(Pos.CENTER);
			return cell;
		});
			
			// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
			TableColumn<EmailDTO, Boolean> Col3 = new TableColumn<>("Listed");
			Col3.setCellValueFactory(param -> {
				EmailDTO email = param.getValue();
				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(email.isIsListed());
				// Note: singleCol.setOnEditCommit(): Not work for
				// CheckBoxTableCell.

				// When "Listed?" column change.
				booleanProp.addListener((observable, oldValue, newValue) -> {
					email.setIsListed(newValue);
					SqlUpdate.updateEmail("email_listed",email.getEmail_id(), newValue);
				});
				return booleanProp;
			});

		//
		Col3.setCellFactory(p12 -> {
			CheckBoxTableCell<EmailDTO, Boolean> cell = new CheckBoxTableCell<>();
			cell.setAlignment(Pos.CENTER);
			return cell;
		});
			
			/// sets width of columns by percentage
			Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 50);   // Phone
			Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Type
			Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Listed
			
			/////////////////  LISTENERS ////////////////////////

	        emailAdd.setOnAction((event) -> {
				BaseApplication.logger.info("Added new email entry for " + person.getNameWithInfo());
				// get the next available primary key for table email
				int email_id = SqlSelect.getNextAvailablePrimaryKey("email","email_id"); // gets last memo_id number
				// add record to SQL and return success or not
				if(SqlInsert.addEmailRecord(email_id,person.getP_id(),true,"new email",true))
					// if we have added it to SQL we need to create a new row in tableview to match
	            	email.add(new EmailDTO(email_id,person.getP_id(),true,"new email",true));
				// Now we will sort it to the top
				email.sort(Comparator.comparing(EmailDTO::getEmail_id).reversed());
				// this line prevents strange buggy behaviour
				emailTableView.layout();
				// edit the phone number cell after creating
				emailTableView.edit(0, Col1);
	        });

		emailDelete.setOnAction((event) -> {
			int selectedIndex = emailTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {// make sure something is selected
				EmailDTO emailDTO = email.get(selectedIndex);
				Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
				conformation.setTitle("Delete Email Entry");
				conformation.setHeaderText(emailDTO.getEmail());
				conformation.setContentText("Are sure you want to delete this email entry?");
				DialogPane dialogPane = conformation.getDialogPane();
				dialogPane.getStylesheets().add("css/dark/dialogue.css");
				dialogPane.getStyleClass().add("dialog");
				Optional<ButtonType> result = conformation.showAndWait();
				if (result.get() == ButtonType.OK) {
					if (SqlDelete.deleteEmail(emailDTO)) {  // if deleted in database
						emailTableView.getItems().remove(selectedIndex); // remove from GUI
						BaseApplication.logger.info("Deleted "
								+ emailDTO.getEmail() + " from "
								+ person.getNameWithInfo());
					}
				}
			}
		});
	        
	        ///////////////////  SET CONTENT ////////////////////
	        
			vboxButtons.getChildren().addAll(emailAdd, emailDelete);
			emailTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3));
			vboxPink.getChildren().add(emailTableView);
			hboxGrey.getChildren().addAll(vboxPink,vboxButtons);
			getChildren().add(hboxGrey);
		
	} // CONSTRUCTOR END
	
	///////////////// CLASS METHODS /////////////////
	
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }

} 
