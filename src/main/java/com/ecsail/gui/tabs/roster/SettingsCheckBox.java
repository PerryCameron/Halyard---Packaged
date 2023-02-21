package com.ecsail.gui.tabs.roster;

import com.ecsail.dto.DbMembershipListDTO;
import javafx.scene.control.CheckBox;

public class SettingsCheckBox extends CheckBox {

    private ControlBox parent;
    private DbMembershipListDTO db;
    public SettingsCheckBox(ControlBox p, DbMembershipListDTO db) {
        this.parent = p;
        this.db = db;
        this.setText(db.getName());
        this.setSelected(db.isSearchable());

        this.selectedProperty().addListener((observable, oldValue, newValue) -> {
            db.setSearchable(newValue);
        });
    }

    public String getDTOFieldName() {
        return db.getPojo_name();
    }

    public boolean isSearchable() {
        return db.isSearchable();
    }
}
