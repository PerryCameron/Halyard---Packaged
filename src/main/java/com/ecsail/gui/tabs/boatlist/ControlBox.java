package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;

public class ControlBox extends VBox {
    TabBoats parent;
    public ControlBox(TabBoats tabBoats) {
        this.parent = tabBoats;
        String[] lists = {"Active Sailboats","Active Auxiliary Boats","All Sailboats","All Auxiliary","All Boats"};
        HashMap<String,RadioButton> radios = new HashMap<>();
        ToggleGroup tg = new ToggleGroup();

        setPadding(new Insets(0,5,0,5));
        VBox vBoxCheckBox = new VBox();
        vBoxCheckBox.setSpacing(5);
        vBoxCheckBox.setPrefWidth(400);

        for(String r: lists) {
            RadioButton rb = new RadioButton(r);
            rb.setToggleGroup(tg);
            vBoxCheckBox.getChildren().add(rb);
        }

        Button viewBoat = new Button("View Boat");

        viewBoat.setOnAction(event -> {
            Launcher.openBoatViewTab(parent.selectedBoat);
        });
        getChildren().addAll(vBoxCheckBox,viewBoat);
    }
}
