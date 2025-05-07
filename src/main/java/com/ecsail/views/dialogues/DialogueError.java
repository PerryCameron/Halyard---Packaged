package com.ecsail.views.dialogues;

import com.ecsail.BaseApplication;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.util.Objects;

public class DialogueError extends Stage implements Builder<Parent> {

	private boolean withButton;
	private ObjectProperty<VBox> mainContent = new SimpleObjectProperty<>();
	Label contentLabel = new Label();

	public DialogueError(String title, String message, boolean withButton, int width, int height) {
		contentLabel.setText(message);
		setTitle(title);
		this.withButton=withButton;
		setScene(width, height);
	}

	public DialogueError(boolean withButton, String text, int width, int height) {
		super();
		contentLabel.setText(text);
		this.withButton=withButton;
		setScene(width, height);
	}

	public void setLabelText(String text) {
		contentLabel.setText(text);
	}

	private void setScene(int width, int height) {
		Scene scene = new Scene(build(), width, height);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
	}

	public void closeDialogue() {
		this.close();
	}

	public void addCloseButton() {
		mainContent.get().getChildren().add(createButton());
	}

	@Override
	public Parent build() {
		double centerXPosition = BaseApplication.primaryStage.getX() + BaseApplication.primaryStage.getWidth() / 2d;
		double centerYPosition = BaseApplication.primaryStage.getY() + BaseApplication.primaryStage.getHeight() / 2d;
		setOnShown(windowEvent -> {
			setX(centerXPosition - getWidth() / 2d);
			setY(centerYPosition - getHeight() / 2d);
		});
		VBox vBox = new VBox();
		vBox.setId("box-frame-dark");
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().add(createInnerBox());
		return vBox;
	}

	private Node createInnerBox() {
		VBox vBox = new VBox(); // this creates a pink border around the table
		VBox.setVgrow(vBox, Priority.ALWAYS);
		HBox.setHgrow(vBox, Priority.ALWAYS);
		vBox.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vBox.getChildren().add(createContentBox());
		return vBox;
	}

	private Node createContentBox() {
		VBox vBox = new VBox(); // this is the vbox for organizing all the widgets
		mainContent.set(vBox);
		vBox.setSpacing(15);
		vBox.setPadding(new Insets(5,5,0,5));
		vBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(vBox, Priority.ALWAYS);
		vBox.getChildren().add(contentLabel);
		if(withButton) vBox.getChildren().add(createButton());
		return vBox;
	}

	private Node createButton() {
		Button closeButton = new Button("Close");
		closeButton.setOnAction((event) -> this.close());
		return closeButton;
	}
}
