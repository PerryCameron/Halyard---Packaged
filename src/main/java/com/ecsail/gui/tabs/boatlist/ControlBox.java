package com.ecsail.gui.tabs.boatlist;

import com.ecsail.Launcher;
import com.ecsail.sql.select.SqlBoat;
import com.ecsail.sql.select.SqlBoatListRadio;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ControlBox extends VBox {
    TabBoats parent;
    ArrayList<BoatListRadioDTO> boatListRadioDTOs;
    public ControlBox(TabBoats tabBoats) {
        this.parent = tabBoats;
        this.boatListRadioDTOs = SqlBoatListRadio.getBoatListRadioDTOs();

        VBox vBoxCheckBox = new VBox();
        vBoxCheckBox.setSpacing(5);
        vBoxCheckBox.setPrefWidth(400);

        setUpRadioButtons(vBoxCheckBox);

        Button viewBoat = new Button("View Boat");

        viewBoat.setOnAction(event -> {
            Launcher.openBoatViewTab(parent.selectedBoat);
        });
        setPadding(new Insets(0,5,0,5));

        getChildren().addAll(vBoxCheckBox,viewBoat);
    }

    private void setUpRadioButtons(VBox vBoxCheckBox) {
        ToggleGroup tg = new ToggleGroup();
        boatListRadioDTOs.forEach(r -> {
            RadioButton rb = new RadioButton(r.getLabel());
            r.setRadioButton(rb);
            rb.setToggleGroup(tg);
            vBoxCheckBox.getChildren().add(rb);
            setRadioButtonListener(r);
            if(r.getOrder() == 1) rb.setSelected(true);
        });
    }

    private void setRadioButtonListener(BoatListRadioDTO r) {
        r.getRadioButton().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            parent.boats.clear();
            parent.boats.addAll(SqlBoat.getBoatList(r.getQuery()));
        });
    }
}
