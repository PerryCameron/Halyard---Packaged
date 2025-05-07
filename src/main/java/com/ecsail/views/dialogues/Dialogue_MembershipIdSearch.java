package com.ecsail.views.dialogues;

import com.ecsail.BaseApplication;
import com.ecsail.Launcher;
import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Year;
import java.util.Objects;

public class Dialogue_MembershipIdSearch extends Stage {

	MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();

	public Dialogue_MembershipIdSearch() {
		double centerXPosition = BaseApplication.primaryStage.getX() + BaseApplication.primaryStage.getWidth() / 2d;
		double centerYPosition = BaseApplication.primaryStage.getY() + BaseApplication.primaryStage.getHeight() / 2d;
		setOnShown(windowEvent -> {
			setX(centerXPosition - getWidth() / 2d);
			setY(centerYPosition - getHeight() / 2d);
		});
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 290, 100);
		HBox hboxControls = new HBox();
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");

		TextField msidTextField = new TextField();
		Button submitButton = new Button("Submit");
		ComboBox<Integer> comboBox = new ComboBox<>();
		for(int i = Integer.parseInt(String.valueOf(Year.now().getValue())) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);
		// vboxGrey.setId("slip-box");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");

		setTitle("By Membership ID");
		msidTextField.setPrefWidth(70);
		hboxControls.setSpacing(7);
		hboxControls.setPadding(new Insets(20,0,0,10));
		/////////////// Listener ///////////////////

		submitButton.setOnAction((event) -> {
			int msid = membershipIdRepository.getMsidFromYearAndMembershipId(comboBox.getValue(), msidTextField.getText());
			Launcher.createMembershipTabForRoster(Integer.parseInt(msidTextField.getText()), msid);
		});
		
		//////////////// ADD CONTENT ///////////////////
		hboxControls.getChildren().addAll(msidTextField,comboBox, submitButton);
		vboxGrey.getChildren().addAll(hboxControls);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
