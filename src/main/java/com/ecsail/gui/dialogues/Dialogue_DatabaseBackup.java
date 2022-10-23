package com.ecsail.gui.dialogues;

import com.ecsail.FileIO;
import com.ecsail.structures.Object_TupleCount;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class Dialogue_DatabaseBackup extends Stage {
	private final List<Object_TupleCount> tuples;
	Object_TupleCount addedTuples; // this is to store how many new tuples have been added since last save
	public Dialogue_DatabaseBackup(Object_TupleCount newTupleCount) {  // newTupleCount is a new object already calculated with the amount of tuples
		this.tuples = FileIO.openTupleCountObjects();
		tuples.add(newTupleCount);
		this.addedTuples = getAddedTuples();
		//for(Object_TupleCount t: tuples) {
		//	System.out.println(t.toString());
		//}
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 400, 300);
		
		/////////////////// ATTRIBUTES ///////////////////
//		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
//		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("css/dark/customdialogue.css");
		setTitle("Database BackUp");
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon_24.png")));
		
		//////////////// ADD CONTENT ///////////////////
		
		vboxGrey.getChildren().add(addInformationVBox());
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
		tuples.get(tuples.size() - 1).clearEdits();
		FileIO.saveTupleCountObjects(tuples);
	}
	
	private VBox addInformationVBox() {
		VBox iBox = new VBox();
		GridPane pane = new GridPane();
		Object_TupleCount at = getAddedTuples();
		int r = tuples.size() - 1;
		Font font1 = Font.font("Verdana", FontWeight.BOLD, 14);
		Text text;
		pane.setHgap(25);
		
		iBox.getChildren().add(new Text("Database sucessfully backed up"));
		
		////////// HEADERS //////////////
		text = new Text("Record Type");
		text.setFont(font1);	
		pane.add(text, 0, 0);
		
		text = new Text("New");
		text.setFont(font1);	
		pane.add(text, 1, 0);
		
		text = new Text("Modified");
		text.setFont(font1);	
		pane.add(text, 2, 0);
		
		text = new Text("Total");
		text.setFont(font1);	
		pane.add(text, 3, 0);
		
		////////// ROW 1 ////////////
		
		text = new Text("Membership");	
		pane.add(text, 0, 1);
		
		text = new Text(at.getMembershipSize() + "");	
		pane.add(text, 1, 1);
		
		text = new Text(tuples.get(r).getMembershipEdits() + "");
		pane.add(text, 2, 1);
		
		text = new Text(tuples.get(r).getMembershipSize() + "");	
		pane.add(text, 3, 1);
		
		////////// ROW 2 ////////////
		
		text = new Text("Id");	
		pane.add(text, 0, 2);
		
		text = new Text(at.getIdSize() + "");	
		pane.add(text, 1, 2);
		
		text = new Text(tuples.get(r).getIdEdits() + "");
		pane.add(text, 2, 2);
		
		text = new Text(tuples.get(r).getIdSize() + "");	
		pane.add(text, 3, 2);
		
		////////// ROW 3 ////////////
		
		text = new Text("People");	
		pane.add(text, 0, 3);
		
		text = new Text(at.getPeopleSize() + "");	
		pane.add(text, 1, 3);
		
		text = new Text(tuples.get(r).getPeopleEdits() + "");
		pane.add(text, 2, 3);
		
		text = new Text(tuples.get(r).getPeopleSize() + "");	
		pane.add(text, 3, 3);
		
		////////// ROW 4 ////////////
		
		text = new Text("Phone");	
		pane.add(text, 0, 4);
		
		text = new Text(at.getPhoneSize() + "");	
		pane.add(text, 1, 4);
		
		text = new Text(tuples.get(r).getPhoneEdits() + "");
		pane.add(text, 2, 4);
		
		text = new Text(tuples.get(r).getPhoneSize() + "");	
		pane.add(text, 3, 4);
		
		////////// ROW 5 ////////////
		
		text = new Text("Boat");	
		pane.add(text, 0, 5);
		
		text = new Text(at.getBoatSize() + "");	
		pane.add(text, 1, 5);
		
		text = new Text(tuples.get(r).getBoatEdits() + "");
		pane.add(text, 2, 5);
		
		text = new Text(tuples.get(r).getBoatSize() + "");	
		pane.add(text, 3, 5);
		
		////////// ROW 6 ////////////
		
		text = new Text("Boat Owner");	
		pane.add(text, 0, 6);
		
		text = new Text(at.getBoatSize() + "");	
		pane.add(text, 1, 6);
		
		text = new Text(tuples.get(r).getBoatOwnerEdits() + "");
		pane.add(text, 2, 6);
		
		text = new Text(tuples.get(r).getBoatOwnerSize() + "");	
		pane.add(text, 3, 6);
		
		////////// ROW 7 ////////////
		
		text = new Text("Slip");	
		pane.add(text, 0, 7);
		
		text = new Text(at.getSlipsSize() + "");	
		pane.add(text, 1, 7);
		
		text = new Text(tuples.get(r).getSlipsEdits() + "");
		pane.add(text, 2, 7);
		
		text = new Text(tuples.get(r).getSlipsSize() + "");	
		pane.add(text, 3, 7);
		
		////////// ROW 8 ////////////
		
		text = new Text("Memo");	
		pane.add(text, 0, 8);
		
		text = new Text(at.getMemosSize() + "");	
		pane.add(text, 1, 8);
		
		text = new Text(tuples.get(r).getMemosEdits() + "");
		pane.add(text, 2, 8);
		
		text = new Text(tuples.get(r).getMemosSize() + "");	
		pane.add(text, 3, 8);
		
		////////// ROW 9 ////////////
		
		text = new Text("Email");	
		pane.add(text, 0, 9);
		
		text = new Text(at.getEmailSize() + "");	
		pane.add(text, 1, 9);
		
		text = new Text(tuples.get(r).getEmailEdits() + "");
		pane.add(text, 2, 9);
		
		text = new Text(tuples.get(r).getEmailSize() + "");	
		pane.add(text, 3, 9);
		
		////////// ROW 10 ////////////
		
		text = new Text("Money");	
		pane.add(text, 0, 10);
		
		text = new Text(at.getMoniesSize() + "");	
		pane.add(text, 1, 10);
		
		text = new Text(tuples.get(r).getMoniesEdits() + "");
		pane.add(text, 2, 10);
		
		text = new Text(tuples.get(r).getMoniesSize() + "");	
		pane.add(text, 3, 10);
		
		////////// ROW 11 ////////////
		
		text = new Text("Deposit");	
		pane.add(text, 0, 11);
		
		text = new Text(at.getDepositsSize() + "");	
		pane.add(text, 1, 11);
		
		text = new Text(tuples.get(r).getDepositsEdits() + "");
		pane.add(text, 2, 11);
		
		text = new Text(tuples.get(r).getDepositsSize() + "");	
		pane.add(text, 3, 11);
		
		////////// ROW 12 ////////////
		
		text = new Text("Payment");	
		pane.add(text, 0, 12);
		
		text = new Text(at.getPaymentsSize() + "");	
		pane.add(text, 1, 12);
		
		text = new Text(tuples.get(r).getPaymentsEdits() + "");
		pane.add(text, 2, 12);
		
		text = new Text(tuples.get(r).getPaymentsSize() + "");	
		pane.add(text, 3, 12);
		
		////////// ROW 13 ////////////
		
		text = new Text("Defined Fee");	
		pane.add(text, 0, 13);
		
		text = new Text(at.getDefinedFeesSize() + "");	
		pane.add(text, 1, 13);
		
		text = new Text(tuples.get(r).getDefinedFeesEdits() + "");
		pane.add(text, 2, 13);
		
		text = new Text(tuples.get(r).getDefinedFeesSize() + "");	
		pane.add(text, 3, 13);
		
		////////// ROW 14 ////////////
		
		text = new Text("Work Credit");	
		pane.add(text, 0, 14);
		
		text = new Text(at.getWorkCreditsSize() + "");	
		pane.add(text, 1, 14);
		
		text = new Text(tuples.get(r).getWorkCreditsEdits() + "");
		pane.add(text, 2, 14);
		
		text = new Text(tuples.get(r).getWorkCreditsSize() + "");	// total size
		pane.add(text, 3, 14);
		
		iBox.getChildren().add(pane);
		return iBox;
	}
	
	private Object_TupleCount getAddedTuples() {
		Object_TupleCount at = new Object_TupleCount();
		int last = tuples.size() -1;
		at.setBoatOwnerSize(tuples.get(last).getBoatOwnerSize() - tuples.get(last - 1).getBoatOwnerSize());
		at.setBoatSize(tuples.get(last).getBoatSize() - tuples.get(last - 1).getBoatSize());
		at.setDefinedFeesSize(tuples.get(last).getDefinedFeesSize() - tuples.get(last - 1).getDefinedFeesSize());
		at.setDepositsSize(tuples.get(last).getDepositsSize() - tuples.get(last - 1).getDepositsSize());
		at.setEmailSize(tuples.get(last).getDepositsSize() - tuples.get(last - 1).getDepositsSize());
		at.setIdSize(tuples.get(last).getIdSize() - tuples.get(last - 1).getIdSize());
		at.setMembershipSize(tuples.get(last).getMembershipSize() - tuples.get(last - 1).getMembershipSize());
		at.setMemosSize(tuples.get(last).getMemosSize() - tuples.get(last - 1).getMemosSize());
		at.setMoniesSize(tuples.get(last).getMoniesSize() - tuples.get(last - 1).getMoniesSize());
		at.setOfficersSize(tuples.get(last).getOfficersSize() - tuples.get(last - 1).getOfficersSize());
		at.setPaymentsSize(tuples.get(last).getPaymentsSize() - tuples.get(last - 1).getPaymentsSize());
		at.setPeopleSize(tuples.get(last).getPeopleSize() - tuples.get(last - 1).getPeopleSize());
		at.setSlipsSize(tuples.get(last).getSlipsSize() - tuples.get(last - 1).getSlipsSize());
		at.setWorkCreditsSize(tuples.get(last).getWorkCreditsSize() - tuples.get(last - 1).getWorkCreditsSize());
		return at;
	}
}
