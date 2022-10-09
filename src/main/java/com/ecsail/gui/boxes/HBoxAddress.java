package com.ecsail.gui.boxes;

import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.MembershipListDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HBoxAddress extends HBox {
	MembershipListDTO membership;
	
	public HBoxAddress(MembershipListDTO me) {
		this.membership = me;
			ObservableList<String> states = 
		    FXCollections.observableArrayList(
		    		"AL","AK","AZ","AR","CA","CO","CT","DE","DC","FL","GA","HI","ID","IL","IN","IA","KS","KY"
		    		,"LA","ME","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","MD","MA","MI"
		    		,"MN","MS","MO","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"
		    );
		
		////////////////// OBJECTS ///////////////////////////
        final Label memAddress = new Label("Street");
        final Label memCity = new Label("City");
        //final Label primaryLabel = new Label("Primary Address");
        final ComboBox<String> stateComboBox = new ComboBox<String>(states);
        final Label memZipcode = new Label("Zipcode");
        TextField memAddressTextField = new TextField();
        TextField memCityTextField = new TextField();
        TextField memZipcodeTextField = new TextField();
        TextField smemAddressTextField = new TextField();
        TextField smemCityTextField = new TextField();
        TextField smemZipcodeTextField = new TextField();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox shbox1 = new HBox();
        HBox shbox2 = new HBox();
        HBox shbox3 = new HBox();
        HBox shbox4 = new HBox();
        HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		HBox primaryHBox = new HBox();  // contains viewable children
		HBox secondaryHBox = new HBox();
		VBox mainVBox = new VBox();
        final Label smemAddress = new Label("Street");
        final Label smemCity = new Label("City");
        //final Label secondaryLabel = new Label("Secondary Address");
        final ComboBox<String> sstateComboBox = new ComboBox<String>(states);
        final Label smemZipcode = new Label("Zipcode");
		TitledPane titledPane1 = new TitledPane();
		TitledPane titledPane2 = new TitledPane();

        ///////////////// ATTRIBUTES //////////////////////////
        stateComboBox.setValue(membership.getState());
        hbox1.setSpacing(5);
        hbox2.setSpacing(5);
        hbox3.setSpacing(5);
        hbox4.setSpacing(5);
        shbox1.setSpacing(5);
        shbox2.setSpacing(5);
        shbox3.setSpacing(5);
        shbox4.setSpacing(5);
        mainVBox.setSpacing(10);
        secondaryHBox.setSpacing(30);
        primaryHBox.setSpacing(30);
        
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox4.setAlignment(Pos.CENTER_LEFT);
        shbox1.setAlignment(Pos.CENTER_LEFT);
        shbox2.setAlignment(Pos.CENTER_LEFT);
        shbox3.setAlignment(Pos.CENTER_LEFT);
        shbox4.setAlignment(Pos.CENTER_LEFT);
        mainVBox.setAlignment(Pos.CENTER);
        
        memZipcodeTextField.setPrefWidth(80);
        smemZipcodeTextField.setPrefWidth(80);
        memAddressTextField.setPrefWidth(300);
        smemAddressTextField.setPrefWidth(300);
        memCityTextField.setPrefWidth(180);
        smemCityTextField.setPrefWidth(180);
	
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(titledPane1, Priority.ALWAYS);
		HBox.setHgrow(titledPane2, Priority.ALWAYS);
		
        primaryHBox.setPadding(new Insets(5,0,5,20));
        secondaryHBox.setPadding(new Insets(5,0,5,20));
        hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame

        
		
		secondaryHBox.setId("box-pink");
		primaryHBox.setId("box-pink");
		hboxGrey.setId("custom-tap-pane-frame");
		this.setId("box-frame-dark");
		titledPane1.setId("titled");
		titledPane2.setId("titled");
		
		memAddressTextField.setText(membership.getAddress());
		memCityTextField.setText(membership.getCity());
		memZipcodeTextField.setText(membership.getZip());
		titledPane1.setText("Primary Address");
		titledPane2.setText("Secondary Address");
		titledPane1.setCollapsible(false);
		titledPane2.setCollapsible(false);

        stateComboBox.getSelectionModel().select(14);
		
        /////////////////// LISTENERS /////////////////////////
        memAddressTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
                        membership.setAddress(memAddressTextField.getText());
	            		SqlUpdate.updateAddress(membership);
	            }
	        });
                
        memCityTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
                        membership.setCity(memCityTextField.getText());
	            		SqlUpdate.updateCity(membership);
	            }
	        });
  
        memZipcodeTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
                        membership.setZip(memZipcodeTextField.getText());
	            		SqlUpdate.updateZipcode(membership);
	            }
	        });
        
        stateComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            membership.setState(newValue);
        	SqlUpdate.updateState(membership);
        }); 
        
		///////////// SET CONTENT ////////////////////
        hbox1.getChildren().addAll(memAddress,memAddressTextField);
        hbox2.getChildren().addAll(memCity,memCityTextField);
        hbox3.getChildren().addAll(stateComboBox);
        hbox4.getChildren().addAll(memZipcode,memZipcodeTextField);
        shbox1.getChildren().addAll(smemAddress,smemAddressTextField);
        shbox2.getChildren().addAll(smemCity,smemCityTextField);
        shbox3.getChildren().addAll(sstateComboBox);
        shbox4.getChildren().addAll(smemZipcode,smemZipcodeTextField);
		primaryHBox.getChildren().addAll(hbox1,hbox2,hbox3,hbox4);
		secondaryHBox.getChildren().addAll(shbox1,shbox2,shbox3,shbox4);
		titledPane1.setContent(primaryHBox);
		titledPane2.setContent(secondaryHBox);
		mainVBox.getChildren().addAll(titledPane1, titledPane2);
		hboxGrey.getChildren().addAll(mainVBox);
		getChildren().add(hboxGrey);
	}
	
}
