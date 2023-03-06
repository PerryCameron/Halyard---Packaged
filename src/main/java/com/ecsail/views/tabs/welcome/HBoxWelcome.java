package com.ecsail.views.tabs.welcome;

import com.ecsail.BaseApplication;
import com.ecsail.CreateMembership;
import com.ecsail.Launcher;
import com.ecsail.customwidgets.BigButton;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// this is the contents inside tabWelcome() launched from ConnectDatabase() about line 229
public class HBoxWelcome extends HBox {

	public HBoxWelcome() {
		BaseApplication.logger.info("Opening welcome tab");
		int width = 400;

		var vboxLeft = new VBoxCharts();
		var vboxRight = new VBox();

		var peopleListButton = new BigButton("People");
		var slipListButton = new BigButton("Slips");
		var bodButton = new BigButton("Board of Directors");
		var newButton = new BigButton("Create New Membership");
		var batchesButton = new BigButton("Deposits");
		var rosterButton = new BigButton("Rosters");
		var boatsButton = new BigButton("Boats");
		var notesButton = new BigButton("Notes");
		var jotFormButton = new BigButton("JotForm");

		
		////////////////  ATTRIBUTES //////////////////////////////
		vboxRight.setPrefWidth(width);
		vboxRight.setMinWidth(350);
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(15,0,0,0));
		this.setPadding(new Insets(0,10,0,0));
		this.setSpacing(10);
		VBox.setVgrow(vboxRight, Priority.ALWAYS);
		VBox.setVgrow(vboxLeft, Priority.ALWAYS);
		
		///////////////// LISTENERS  /////////////////////////


		rosterButton.setOnAction((event) -> Launcher.openRosterTab());
		peopleListButton.setOnAction((event) -> Launcher.openPeopleTab());
		slipListButton.setOnAction((event) -> Launcher.openSlipsTab());
		bodButton.setOnAction((event) -> Launcher.openBoardTab());
		newButton.setOnAction((event) -> CreateMembership.Create());
		batchesButton.setOnAction((event) -> Launcher.openDepositsTab());
		boatsButton.setOnAction((event) -> Launcher.openBoatsTab());
		notesButton.setOnAction((event) -> Launcher.openNotesTab());
		jotFormButton.setOnAction((event)-> Launcher.openJotFormTab());

		////////////////  SET CONTENT ////////////////////////

		vboxRight.getChildren().addAll(rosterButton,peopleListButton,slipListButton,bodButton,newButton,batchesButton,boatsButton,notesButton,jotFormButton);
		getChildren().addAll(vboxLeft,vboxRight);

	}
}

