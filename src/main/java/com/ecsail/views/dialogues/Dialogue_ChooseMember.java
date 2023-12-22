package com.ecsail.views.dialogues;

import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
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

import java.util.Objects;

public class Dialogue_ChooseMember extends Stage {
	private MembershipListDTO newOwner;
	private MembershipRepository membershipRepository = new MembershipRepositoryImpl();
	private BoatRepository boatRepository = new BoatRepositoryImpl();
	
	public Dialogue_ChooseMember(ObservableList<MembershipListDTO> boatOwners, int boatId) {
		double centerXPosition = BaseApplication.stage.getX() + BaseApplication.stage.getWidth() / 2d;
		double centerYPosition = BaseApplication.stage.getY() + BaseApplication.stage.getHeight() / 2d;
		setOnShown(windowEvent -> {
			setX(centerXPosition - getWidth() / 2d);
			setY(centerYPosition - getHeight() / 2d);
		});
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
        
		vboxGrey.setPadding(new Insets(20, 20, 20, 20));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");

		this.setTitle("Choose Member");

		//////////////// LISTENERS   ///////////////////
		
		memIdTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            	newOwner = membershipRepository.getMembershipByMembershipId(memIdTextField.getText());
        		fnameTextField.setText(newOwner.getFirstName());
                lnameTextField.setText(newOwner.getLastName());
            }
        });
		
		okButton.setOnAction((event) -> {
			boatRepository.insertBoatOwner(newOwner.getMsId(), boatId);
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
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);		this.setScene(scene);
		this.show();
	}
}
