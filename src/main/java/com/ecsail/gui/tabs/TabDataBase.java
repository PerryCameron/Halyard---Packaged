package com.ecsail.gui.tabs;

import com.ecsail.gui.boxes.HBoxTableChanges;
import com.ecsail.sql.select.SqlDbTableChanges;
import com.ecsail.structures.DbTableChangesDTO;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/// this tab is for visual purposes when logging on.  it goes away after logon
public class TabDataBase extends Tab {
	ArrayList<DbTableChangesDTO> thisDbTableChanges;
	public TabDataBase(String text) {
		super(text);
		this.thisDbTableChanges = SqlDbTableChanges.getDbtableChanges();
		VBox inner = new VBox();
		HBox vboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table

		inner.setSpacing(2);
//		inner.setId("hbox-inner");
		inner.getChildren().add(new HBoxTableChanges());
		for(DbTableChangesDTO d: thisDbTableChanges) {
			inner.getChildren().add(new HBoxTableChanges(d));
		}


		vboxBlue.setId("box-frame-dark");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		//vboxGrey.setPrefWidth(1003);

		vboxPink.setPrefHeight(1000);
		vboxGrey.getChildren().add(inner);
		vboxPink.getChildren().add(vboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
	}
	
}