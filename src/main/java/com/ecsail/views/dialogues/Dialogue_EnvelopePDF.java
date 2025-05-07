package com.ecsail.views.dialogues;


import com.ecsail.BaseApplication;
import com.ecsail.pdf.PDF_Envelope;
import com.ecsail.pdf.PDF_Envelope_many;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.io.IOException;
import java.util.Objects;

public class Dialogue_EnvelopePDF extends Stage implements Builder<Scene> {

	private boolean printOne = true;
	private boolean printCatalogue = false;
	private final StringProperty membershipId = new SimpleStringProperty();

	public Dialogue_EnvelopePDF() {
		this.setScene((Scene) build());
	}

	@Override
	public Scene build() {
		VBox vboxBlue = new VBox();
		vboxBlue.setId("box-frame-dark");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		double centerXPosition = BaseApplication.primaryStage.getX() + BaseApplication.primaryStage.getWidth() / 2d;
		double centerYPosition = BaseApplication.primaryStage.getY() + BaseApplication.primaryStage.getHeight() / 2d;
		setOnShown(windowEvent -> {
			setX(centerXPosition - getWidth() / 2d);
			setY(centerYPosition - getHeight() / 2d);
		});
		Scene scene = new Scene(vboxBlue, 600, 300);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		vboxBlue.getChildren().add(createContent());
		this.getIcons().add(mainIcon);
		this.setTitle("Print to PDF");
		this.show();
		return scene;
	}

	private Node createContent() {
		VBox vBox = new VBox();
		HBox hboxGrey = new HBox();
		hboxGrey.setPadding(new Insets(5,0,0,5));
		hboxGrey.setPrefHeight(688);
		hboxGrey.getChildren().addAll(creatLeft(),createRight());
		vBox.getChildren().add(hboxGrey);
		vBox.setPadding(new Insets(3, 3, 3, 3));
		return vBox;
	}

	private Node createRight() {
		VBox vBox = new VBox();
		Image pdf = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pdf.png")));
		ImageView pdfImage = new ImageView(pdf);
		vBox.setSpacing(15);
		vBox.setPadding(new Insets(35,0,0,35));
		vBox.getChildren().addAll(pdfImage,createPDFButton());
		return vBox;
	}


	private Node createPDFButton() {
		Button button = new Button("Create Envelope PDF");
		button.setOnAction(e -> {
			try {
				if(printOne)
					new PDF_Envelope(printCatalogue, membershipId.get());
				else
					new PDF_Envelope_many(printCatalogue);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		return button;
	}

	private Node creatLeft() {
		VBox vBox = new VBox();
		Separator separator1 = new Separator();
		vBox.setPrefWidth(300);
		vBox.setSpacing(5);
		vBox.setSpacing(15);
		vBox.setPadding(new Insets(0,0,0,15));
		vBox.getChildren().addAll(membershipIdText(),numberToPrint(),separator1,chooseEnvelopeSize());
		return vBox;
	}

	private Node membershipIdText() {
		HBox hBox = new HBox();
		TextField textField = new TextField();
		textField.setPrefWidth(50);
		// Bind the membershipId StringProperty to the TextField's text property
		membershipId.bind(textField.textProperty());
		hBox.setSpacing(5);
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.getChildren().addAll(new Text("Membership ID"),textField);
		return hBox;
	}

	private Node numberToPrint() {
		VBox vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(20,0,15,0));
		ToggleGroup tg = new ToggleGroup();
		RadioButton r1 = new RadioButton("Print All Envelopes");
		RadioButton r2 = new RadioButton("Print one Envelope");
		r1.setUserData(false); // Associate false with r1
		r2.setUserData(true);  // Associate true with r2
		r1.setToggleGroup(tg);
		r2.setToggleGroup(tg);
		r2.setSelected(true);
		tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				printOne = (boolean) newValue.getUserData(); // Use userData for true/false
			}
		});;
		vBox.getChildren().addAll(r1,r2);
		return vBox;
	}

	private Node chooseEnvelopeSize() {
		VBox vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(20, 0, 0, 0));
		ToggleGroup tg = new ToggleGroup();
		RadioButton r1 = new RadioButton("#10 Envelope");
		RadioButton r2 = new RadioButton("#1 Catalog");
		r1.setUserData(false); // Associate false with r1
		r2.setUserData(true);  // Associate true with r2
		r1.setToggleGroup(tg);
		r2.setToggleGroup(tg);
		r1.setSelected(true);
		tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				printCatalogue = (boolean) newValue.getUserData();
			}
		});
		vBox.getChildren().addAll(r1, r2);
		return vBox;
	}

}
