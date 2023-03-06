package com.ecsail.views.dialogues;

import com.ecsail.Launcher;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Dialogue_Msid extends Stage {

	public Dialogue_Msid() {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 200, 100);
		HBox hboxControls = new HBox();
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");

		TextField msidTextField = new TextField();
		Button submitButton = new Button("Submit");
		// vboxGrey.setId("slip-box");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");

		setTitle("By MSID");
		msidTextField.setPrefWidth(70);
		hboxControls.setSpacing(7);
		hboxControls.setPadding(new Insets(20,0,0,10));
		/////////////// Listener ///////////////////

		submitButton.setOnAction((event) -> Launcher.createMembershipTabFromPeopleList(Integer.parseInt(msidTextField.getText())));
		
		//////////////// ADD CONTENT ///////////////////
		hboxControls.getChildren().addAll(msidTextField,submitButton);
		vboxGrey.getChildren().addAll(hboxControls);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
