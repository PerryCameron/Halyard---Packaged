package com.ecsail.gui.tabs.roster;

import com.ecsail.dto.DbMembershipListDTO;
import javafx.scene.control.CheckBox;

public class SettingsCheckBox extends CheckBox {

    private ControlBox parent;
    private DbMembershipListDTO db;
    public SettingsCheckBox(ControlBox p, DbMembershipListDTO db, String mode) {
        this.parent = p;
        this.db = db;
        this.setText(db.getName());
        setListener(mode);
    }

    private void setListener(String mode) {
        if(mode.equals("searchable")) {
            this.setSelected(db.isSearchable());
            this.selectedProperty().addListener((observable, oldValue, newValue) -> {
                db.setSearchable(newValue);
            });
        }
        if(mode.equals("exportable")) {
            this.setSelected(db.isExportable());
            this.selectedProperty().addListener((observable, oldValue, newValue) -> {
                db.setExportable(newValue);
            });
        }
    }

    public String getDTOFieldName() {
        return db.getPojo_name();
    }

    public boolean isSearchable() {
        return db.isSearchable();
    }
}
