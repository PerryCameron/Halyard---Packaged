package com.ecsail.views.dialogues;


import com.ecsail.BaseApplication;
import com.ecsail.pdf.directory.PDF_Directory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Year;
import java.util.Objects;

public class Dialogue_DirectoryCreation extends Stage {

	public Dialogue_DirectoryCreation() {
		double centerXPosition = BaseApplication.primaryStage.getX() + BaseApplication.primaryStage.getWidth() / 2d;
		double centerYPosition = BaseApplication.primaryStage.getY() + BaseApplication.primaryStage.getHeight() / 2d;
		setOnShown(windowEvent -> {
			setX(centerXPosition - getWidth() / 2d);
			setY(centerYPosition - getHeight() / 2d);
		});
		TextArea textArea = new TextArea();
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Button createDirectory = new Button("Create");
		Scene scene = new Scene(vboxBlue, 600, 300);
		
		
		/////////////////// ATTRIBUTES ///////////////////
//		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
//		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		vboxGrey.getChildren().addAll(textArea, createDirectory);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setTitle("Create Directory");

		
		//////////////// LISTENERS ////////////////
		createDirectory.setOnAction((event) -> new PDF_Directory(String.valueOf(Year.now().getValue()),textArea));
		
		//////////////// ADD CONTENT ///////////////////
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
		//new PDF_Directory("2021")
	}
}
