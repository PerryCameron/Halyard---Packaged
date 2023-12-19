package com.ecsail.views.dialogues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.util.Objects;

public class Dialogue_CustomErrorMessage extends Stage implements Builder {
	TextArea textArea;

	public Dialogue_CustomErrorMessage(String title, String message) {
		setText(message);
		setTitle(title);
		setScene();
	}

	public Dialogue_CustomErrorMessage() {
		super();
		setScene();
	}

	private void setScene() {
		Scene scene = new Scene(build(), 600, 300);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
	}

	public void setText(String message) {
		this.textArea.appendText(message + "\n");
	}

	@Override
	public Parent build() {
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
		this.textArea = new TextArea();
		textArea.setEditable(false);
		vBox.setSpacing(15);
		vBox.setPadding(new Insets(5,5,0,5));
		vBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(vBox, Priority.ALWAYS);
		vBox.getChildren().addAll(textArea, createButton());
		return vBox;
	}

	private Node createButton() {
		Button closeButton = new Button("Close");
		closeButton.setOnAction((event) -> this.close());
		return closeButton;
	}
}
