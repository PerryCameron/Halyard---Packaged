package com.ecsail.gui.dialogues;

import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.MembershipListDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_ChooseMember extends Stage {
	//private ObservableList<Object_MembershipList> boatOwners;
	private MembershipListDTO newOwner;
	private String boat_id;
	
	public Dialogue_ChooseMember(ObservableList<MembershipListDTO> boatOwners, int boat_id) {
		
		//this.boatOwners=boatOwners;
		//////////////// ADD OBJECTS ///////////////////
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox textFieldVBox = new VBox();
		VBox labelVBox0 = new VBox();
		VBox labelVBox1 = new VBox();
		VBox labelVBox2 = new VBox();
		VBox labelVBox3 = new VBox();
		HBox containerHBox = new HBox();
		HBox buttonHBox = new HBox();
		
		Scene scene = new Scene(vboxBlue, 310, 190);
		TextField fnameTextField = new TextField();
        TextField lnameTextField = new TextField();
        TextField memIdTextField = new TextField();
        
        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");
        
		/////////////////// ATTRIBUTES ///////////////////
        labelVBox1.setPrefHeight(25);
        labelVBox2.setPrefHeight(25);
        labelVBox3.setPrefHeight(25);
		fnameTextField.setPrefHeight(10);
        lnameTextField.setPrefHeight(10);
        memIdTextField.setPrefHeight(10);
        
        //labelVBox1.setStyle("-fx-background-color: #e83115;");  // red
        //labelVBox2.setStyle("-fx-background-color: #201ac9;");  // blue
        //labelVBox3.setStyle("-fx-background-color: #c5c7c1;");  // gray
        
        labelVBox0.setSpacing(5);
        textFieldVBox.setSpacing(5);
        containerHBox.setSpacing(10);
        buttonHBox.setSpacing(10);
        vboxGrey.setSpacing(10);
        
        buttonHBox.setAlignment(Pos.CENTER);
        
		vboxBlue.setId("box-blue");
		vboxGrey.setPadding(new Insets(20, 20, 20, 20));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		scene.getStylesheets().add("stylesheet.css");

		this.setTitle("Choose Member");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		//////////////// LISTENERS   ///////////////////
		
		memIdTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            	newOwner = SqlMembershipList.getMembershipByMembershipId(memIdTextField.getText());
        		fnameTextField.setText(newOwner.getFname());
                lnameTextField.setText(newOwner.getLname());
            }
        });
		
		okButton.setOnAction((event) -> {
			SqlInsert.addBoatOwner(boat_id, newOwner.getMsid());
			boatOwners.add(newOwner);
			this.close();
		});
		
		cancelButton.setOnAction((event) -> {
			this.close();
		});
		
		//////////////// ADD CONTENT ///////////////////
		buttonHBox.getChildren().addAll(okButton,cancelButton);
		labelVBox1.getChildren().addAll(new Label("Membership ID"));
		labelVBox2.getChildren().addAll(new Label("First Name"));
		labelVBox3.getChildren().addAll(new Label("Last Name"));
		labelVBox0.getChildren().addAll(labelVBox1,labelVBox2,labelVBox3);
		textFieldVBox.getChildren().addAll(memIdTextField,fnameTextField,lnameTextField);
		containerHBox.getChildren().addAll(labelVBox0,textFieldVBox);
		
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		vboxGrey.getChildren().addAll(containerHBox,buttonHBox);
		this.getIcons().add(mainIcon);
		this.setScene(scene);
		this.show();
	}
}
