package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.formula.functions.T;

public class ControlBox extends VBox {
    TabBoats parent;
    public ControlBox(TabBoats tabBoats) {
        this.parent = tabBoats;

        setPadding(new Insets(0,5,0,5));
        VBox vBoxCheckBox = new VBox();
        vBoxCheckBox.setSpacing(5);
        vBoxCheckBox.setPrefWidth(400);
        ToggleGroup tg = new ToggleGroup();
        RadioButton allRadio = new RadioButton("All Boats");
        RadioButton activeRadio = new RadioButton("Active Boats");
        RadioButton inactiveRadio = new RadioButton("Inactive Boats");
        allRadio.setToggleGroup(tg);
        activeRadio.setToggleGroup(tg);
        inactiveRadio.setToggleGroup(tg);
        Button viewBoat = new Button("View Boat");

        viewBoat.setOnAction(event -> {
            Launcher.openBoatViewTab(parent.selectedBoat);
        });
        vBoxCheckBox.getChildren().addAll(allRadio,activeRadio,inactiveRadio);
        getChildren().addAll(vBoxCheckBox,viewBoat);
    }
}
