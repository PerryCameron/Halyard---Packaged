package com.ecsail.views.dialogues;

import com.ecsail.BaseApplication;
import com.ecsail.pdf.PDF_Renewal_Form;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Year;
import java.util.Objects;

public class Dialogue_RenewalForm extends Stage {

	private String selectedYear;
	
	public Dialogue_RenewalForm() {
		this.selectedYear= String.valueOf(Year.now().getValue());
		double centerXPosition = BaseApplication.stage.getX() + BaseApplication.stage.getWidth() / 2d;
		double centerYPosition = BaseApplication.stage.getY() + BaseApplication.stage.getHeight() / 2d;
		setOnShown(windowEvent -> {
			setX(centerXPosition - getWidth() / 2d);
			setY(centerYPosition - getHeight() / 2d);
		});
		Button createPDFbutton = new Button("Create PDF");
		ToggleGroup tg1 = new ToggleGroup();  
		RadioButton r1 = new RadioButton("Print All Memberships"); 
        RadioButton r2 = new RadioButton("Print Only Membership Number"); 
        CheckBox c1 = new CheckBox("Send in email");
        CheckBox c2 = new CheckBox("Print individual copies"); 
		HBox hboxGrey = new HBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxColumn1 = new VBox();
		VBox vboxColumn2 = new VBox();
		TextField memberidTextField = new TextField();
		Scene scene = new Scene(vboxBlue, 600, 300);
		final var yearSpinner = new Spinner<Integer>();
		Image pdf = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pdf.png")));
		ImageView pdfImage = new ImageView(pdf);
		
		SpinnerValueFactory<Integer> batchSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 3000, 0);
		yearSpinner.setValueFactory(batchSlipValueFactory);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  yearSpinner.increment(0); // won't change value, but will commit editor
				  selectedYear = yearSpinner.getEditor().getText();	
			  }
			});
		
		/////////////////// ATTRIBUTES ///////////////////
		memberidTextField.setText("92");
		r1.setToggleGroup(tg1); 
        r2.setToggleGroup(tg1); 
        r2.setSelected(true);
        c1.setSelected(false);
        c2.setSelected(true);
        //yearSpinner.setPadding(new Insets(0,0,0,10));
        hboxGrey.setPadding(new Insets(5,0,0,5));
        yearSpinner.setPrefWidth(80);
        memberidTextField.setPrefWidth(60);
        vboxColumn1.setSpacing(5);
        vboxColumn2.setSpacing(15);
		vboxBlue.setId("box-frame-dark");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		hboxGrey.setPrefHeight(688);
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");
		setTitle("Print to PDF");
		////////////  Check to see if batch exists first////////////
		

  		yearSpinner.getValueFactory().setValue(Integer.parseInt(selectedYear));	
		
		createPDFbutton.setOnAction(e -> {
			try {
				new PDF_Renewal_Form(selectedYear, memberidTextField.getText(), r2.isSelected(), c1.isSelected(), c2.isSelected());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 });
		
		//////////////// ADD CONTENT ///////////////////
		vboxColumn1.getChildren().addAll(yearSpinner,r1,r2,memberidTextField,c1,c2);
		vboxColumn2.getChildren().addAll(pdfImage,createPDFbutton);
		hboxGrey.getChildren().addAll(vboxColumn1,vboxColumn2);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(hboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
