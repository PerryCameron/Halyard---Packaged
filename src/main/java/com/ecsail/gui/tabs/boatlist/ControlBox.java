package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.sql.select.SqlBoat;
import com.ecsail.sql.select.SqlBoatListRadio;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

//		infoBox8.setStyle("-fx-background-color: #c5c7c1;");  // gray
//                infoBox1.setStyle("-fx-background-color: #4d6955;");  //green
//                infoBox2.setStyle("-fx-background-color: #feffab;");  // yellow
//                infoBox3.setStyle("-fx-background-color: #e83115;");  // red
//                infoBox4.setStyle("-fx-background-color: #201ac9;");  // blue
//                infoBox5.setStyle("-fx-background-color: #e83115;");  // purple
//                infoBox6.setStyle("-fx-background-color: #15e8e4;");  // light blue
//                infoBox7.setStyle("-fx-background-color: #e89715;");  // orange

public class ControlBox extends VBox {
    TabBoats parent;
    ArrayList<BoatListRadioDTO> boatListRadioDTOs;

    Text numberOfRecords;

    public ControlBox(TabBoats tabBoats) {
        this.parent = tabBoats;
        this.boatListRadioDTOs = SqlBoatListRadio.getBoatListRadioDTOs();

        VBox radioButtonBox = setUpRadioButtonBox();
        HBox recordCountBox = setUpRecordCountBox();
        HBox searchBox = setUpSearchBox();

        Button viewBoat = new Button("View Boat");

        viewBoat.setOnAction(event -> {
            Launcher.openBoatViewTab(parent.selectedBoat);
        });

        setPadding(new Insets(0,5,0,15));
        getChildren().addAll(recordCountBox,searchBox,radioButtonBox, viewBoat);
    }

    private HBox setUpSearchBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,0,15,0));
        TextField textField = new TextField();
        Button button = new Button("Search");
        hBox.getChildren().addAll(textField,button);
        return hBox;
    }

    private HBox setUpRecordCountBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5,0,15,0));
        Text label = new Text("Records");
        this.numberOfRecords = new Text(String.valueOf(parent.boats.size()));
        numberOfRecords.setId("invoice-text-number");
        label.setId("invoice-text-label");
        hBox.getChildren().addAll(label,numberOfRecords);
        return hBox;
    }

    private void updateRecordCount(int size) {
        numberOfRecords.setText(String.valueOf(size));
    }

    private VBox setUpRadioButtonBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(0,0,15,0));
        vBox.setPrefWidth(400);
        ToggleGroup tg = new ToggleGroup();
        boatListRadioDTOs.forEach(r -> {
            RadioButton rb = new RadioButton(r.getLabel());
            r.setRadioButton(rb);
            rb.setToggleGroup(tg);
            vBox.getChildren().add(rb);
            setRadioButtonListener(r);
            if(r.getOrder() == 1) rb.setSelected(true);
        });
        return vBox;
    }

    private void setRadioButtonListener(BoatListRadioDTO r) {
        r.getRadioButton().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            parent.boats.clear();
            parent.boats.addAll(SqlBoat.getBoatList(r.getQuery()));
            if(numberOfRecords != null) // have we created the object yet?
            updateRecordCount(parent.boats.size());
        });
    }
}
