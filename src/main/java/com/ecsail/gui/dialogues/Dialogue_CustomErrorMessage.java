package com.ecsail.gui.dialogues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_CustomErrorMessage extends Stage {

	public Dialogue_CustomErrorMessage(String message, String title) {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 600, 300);
		Button closeButton = new Button("Close");
		TextArea textArea = new TextArea();
		
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-frame-dark");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
//		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setSpacing(15);
		vboxGrey.setPadding(new Insets(5,5,0,5));
		vboxGrey.setAlignment(Pos.CENTER);
		textArea.setText(message);
		textArea.setEditable(false);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		scene.getStylesheets().add("css/dark/customdialogue.css");
		vboxGrey.getChildren().addAll(textArea, closeButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setTitle(title);
		Image mainIcon = new Image(getClass().getResourceAsStream("/logo_24.png"));

		//////////////// LISTENERS ////////////////

		closeButton.setOnAction((event) -> {
			this.close();
		});
		
		//////////////// ADD CONTENT ///////////////////
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
