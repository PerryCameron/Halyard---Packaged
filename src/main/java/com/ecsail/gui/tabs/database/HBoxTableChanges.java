package com.ecsail.gui.tabs.database;

import com.ecsail.structures.DbTableChangesDTO;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HBoxTableChanges extends HBox {
    DbTableChangesDTO tableChange;

    public HBoxTableChanges(DbTableChangesDTO tableChange) {
        setId("table-change");
        this.tableChange = tableChange;
        var tableText = new Text(tableChange.getTableChanged());
        var insertText = new Text(String.valueOf(tableChange.getTableInsert()));
        var deleteText = new Text(String.valueOf(tableChange.getTableDelete()));
        var updateText = new Text(String.valueOf(tableChange.getTableUpdate()));
        extracted(tableText, insertText, deleteText, updateText);
    }

    // this is for the header
    public HBoxTableChanges() {
        setId("table-change-header");
        var tableText = new Text("Table");
        var insertText = new Text("Insert");
        var deleteText = new Text("Delete");
        var updateText = new Text("Update");
        extracted(tableText, insertText, deleteText, updateText);
    }

    private void extracted(Text tableText, Text insertText, Text deleteText, Text updateText) {
        var vboxTable = new VBox();
        var vboxInsert = new VBox();
        var vboxDelete = new VBox();
        var vboxUpdate = new VBox();
        setPadding(new Insets(0,5,0,5));
        vboxTable.getChildren().add(tableText);
        vboxTable.setPrefWidth(160);
        vboxInsert.getChildren().add(insertText);
        vboxInsert.setPrefWidth(45);
        vboxDelete.getChildren().add(deleteText);
        vboxDelete.setPrefWidth(45);
        vboxUpdate.getChildren().add(updateText);
        vboxUpdate.setPrefWidth(45);
        setSpacing(10);
        getChildren().addAll(vboxTable,vboxInsert,vboxDelete,vboxUpdate);
    }


}
