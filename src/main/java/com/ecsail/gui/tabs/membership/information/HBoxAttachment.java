package com.ecsail.gui.tabs.membership.information;

import com.ecsail.gui.tabs.membership.TabMembership;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HBoxAttachment extends HBox {
	TabMembership tm;

	public HBoxAttachment(TabMembership tm) {
		this.tm = tm;

		//////////// OBJECTS ///////////////
		var hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		//VBox vboxPink = new VBox(); // this creates a pink border around the table
		var mainVBox = new VBox(); // contains viewable children

		/////////////  ATTRIBUTES /////////////

		hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		mainVBox.setSpacing(5);

		this.setId("custom-tap-pane-frame");
		hboxGrey.setId("box-background-light");
		
		///////////// SET CONTENT ////////////////////
		mainVBox.getChildren().addAll(new Label("Attachments will go here"));
		hboxGrey.getChildren().addAll(mainVBox);
		getChildren().add(hboxGrey);
	}
	
}
