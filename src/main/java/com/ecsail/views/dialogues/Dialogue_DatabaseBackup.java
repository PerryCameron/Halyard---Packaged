package com.ecsail.views.dialogues;

import com.ecsail.views.tabs.database.HBoxTableChanges;
import com.ecsail.sql.select.SqlDbTableChanges;
import com.ecsail.dto.DbTableChangesDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Dialogue_DatabaseBackup extends Stage {
	ArrayList<DbTableChangesDTO> thisDbTableChanges;
	public Dialogue_DatabaseBackup() {  // newTupleCount is a new object already calculated with the amount of tuples
		this.thisDbTableChanges = SqlDbTableChanges.getDbTableChanges();
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		ScrollPane scrollPane = new ScrollPane();

		Scene scene = new Scene(vboxBlue, 400, 300);
		
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		setTitle("Database BackUp");

		//////////////// ADD CONTENT ///////////////////

		VBox.setVgrow(scrollPane, Priority.ALWAYS);
		scrollPane.setContent(addInformationVBox());
		vboxGrey.getChildren().addAll(scrollPane,addButtonBox());
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
	private VBox addInformationVBox() {
		VBox vboxInner = new VBox();
		vboxInner.getChildren().add(new HBoxTableChanges());
		for(DbTableChangesDTO d: thisDbTableChanges) {
			vboxInner.getChildren().add(new HBoxTableChanges(d));
		}
		return vboxInner;
	}

	private HBox addButtonBox() {
		HBox buttonBox = new HBox();
		Button buttonOk = new Button("Ok");
		Button buttonReset = new Button("Reset Stats");
		buttonBox.setSpacing(5);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(buttonReset,buttonOk);
		buttonBox.setPadding(new Insets(20,0,0,0));
		buttonBox.setPrefHeight(50);
		return buttonBox;
	}
}
