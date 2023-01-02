package com.ecsail.gui.dialogues;


import com.ecsail.pdf.PDF_Envelope;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Dialogue_EnvelopePDF extends Stage {
	public Dialogue_EnvelopePDF() {
		
		Button createPDFbutton = new Button("Create Envelope PDF");
		ToggleGroup tg1 = new ToggleGroup(); 
		ToggleGroup tg2 = new ToggleGroup(); 
		RadioButton t1r1 = new RadioButton("Print All Envelopes"); 
        RadioButton t1r2 = new RadioButton("Print one Envelope");
        RadioButton t2r1 = new RadioButton("#10 Envelope"); 
        RadioButton t2r2 = new RadioButton("#1 Catalog");
		HBox hboxGrey = new HBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxColumn1 = new VBox();
		VBox vboxColumn2 = new VBox();
		VBox vboxNumberToPrint = new VBox();
		VBox vBoxEnvelopeSize = new VBox();
		HBox hboxMembershipID = new HBox();
		//Horizontal separator
		Separator separator1 = new Separator();

		TextField memberidTextField = new TextField();
		
		Scene scene = new Scene(vboxBlue, 600, 300);
		Image pdf = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pdf.png")));
		ImageView pdfImage = new ImageView(pdf);
		
		/////////////////// ATTRIBUTES ///////////////////
		t1r1.setToggleGroup(tg1); 
        t1r2.setToggleGroup(tg1); 
        t1r2.setSelected(true);
        
        t2r1.setToggleGroup(tg2); 
        t2r2.setToggleGroup(tg2); 
        t2r1.setSelected(true);

		vBoxEnvelopeSize.setSpacing(5);
		vboxColumn1.setPadding(new Insets(0,0,0,15));
		vboxColumn2.setPadding(new Insets(35,0,0,35));
		vBoxEnvelopeSize.setPadding(new Insets(20,0,0,0));
		vboxNumberToPrint.setPadding(new Insets(20,0,15,0));
		vboxNumberToPrint.setSpacing(5);
		vboxColumn1.setSpacing(15);
		hboxMembershipID.setSpacing(5);
        //batchSpinner.setPadding(new Insets(0,0,0,10));
        hboxGrey.setPadding(new Insets(5,0,0,5));
        memberidTextField.setPrefWidth(50);
        vboxColumn1.setSpacing(5);
        vboxColumn2.setSpacing(15);
		vboxBlue.setId("box-frame-dark");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
//		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		hboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		setTitle("Print to PDF");
		hboxMembershipID.setAlignment(Pos.CENTER_LEFT);
		vboxColumn1.setPrefWidth(300);
		////////////  Check to see if batch exists first////////////
		
		
  		/////////////// LISTENERS ///////////////////////
  		
  		
		createPDFbutton.setOnAction(e -> {
			try {
				new PDF_Envelope(t1r2.isSelected(), t2r2.isSelected(), memberidTextField.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		//////////////// ADD CONTENT ///////////////////
		vBoxEnvelopeSize.getChildren().addAll(t2r1,t2r2);
		vboxNumberToPrint.getChildren().addAll(t1r1,t1r2);
		hboxMembershipID.getChildren().addAll(new Text("Membership ID"),memberidTextField);
		vboxColumn1.getChildren().addAll(hboxMembershipID,vboxNumberToPrint,separator1,vBoxEnvelopeSize);
		vboxColumn2.getChildren().addAll(pdfImage,createPDFbutton);
		hboxGrey.getChildren().addAll(vboxColumn1,vboxColumn2);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(hboxGrey);
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
