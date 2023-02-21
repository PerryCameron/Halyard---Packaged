package com.ecsail.gui.tabs.roster;

import com.ecsail.dto.MembershipListDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

public class RosterTableView extends TableView<MembershipListDTO> {
    TabRoster parent;

    public RosterTableView(TabRoster p) {
        this.parent = p;
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);


        TableColumn<MembershipListDTO, Integer> Col1 = new TableColumn<>("ID");
        TableColumn<MembershipListDTO, String> Col2 = new TableColumn<>("Join Date");
        TableColumn<MembershipListDTO, Text> Col3 = new TableColumn<>("Type");
        TableColumn<MembershipListDTO, Text> Col4 = new TableColumn<>("Slip");
        TableColumn<MembershipListDTO, String> Col5 = new TableColumn<>("First Name");
        TableColumn<MembershipListDTO, String> Col6 = new TableColumn<>("Last Name");
        TableColumn<MembershipListDTO, String> Col9 = new TableColumn<>("State");
        TableColumn<MembershipListDTO, String> Col11 = new TableColumn<>("MSID");

        this.setItems(parent.rosters);
        this.setFixedCellSize(30);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Col1.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        Col2.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        Col3.setCellValueFactory(new PropertyValueFactory<>("memType"));
        Col3.setStyle("-fx-alignment: top-center");
        Col3.setCellValueFactory(param -> {  // don't need this now but will use for subleases
            MembershipListDTO m = param.getValue();
            Text text = new Text();
            String valueDisplayed = "";
            if (m.getMemType() != null) {
                valueDisplayed = m.getMemType();
            }
            if (valueDisplayed.equals("SO")) text.setFill(Color.GREEN);
            else if (valueDisplayed.equals("FM")) text.setFill(Color.BLUE);
            else if (valueDisplayed.equals("RM")) text.setFill(Color.RED);
//			else if(valueDisplayed.equals("LA")) text.setFill(Color.KHAKI);
            text.setText(valueDisplayed);
            return new SimpleObjectProperty<>(text);
        });
        Col4.setCellValueFactory(new PropertyValueFactory<>("slip"));
        Col4.setStyle("-fx-alignment: top-center");
        // erasing code below will change nothing
        Col4.setCellValueFactory(param -> {  // don't need this now but will use for subleases
            MembershipListDTO m = param.getValue();

            String valueDisplayed = "";
            if (m.getSlip() != null) {
                valueDisplayed = m.getSlip();
            }
            Text text = new Text(valueDisplayed);
            text.setFill(Color.CHOCOLATE);
            return new SimpleObjectProperty<>(text);
        });

        Col5.setCellValueFactory(new PropertyValueFactory<>("fName"));
        Col6.setCellValueFactory(new PropertyValueFactory<>("lName"));
        Col9.setCellValueFactory(new PropertyValueFactory<>("state"));
        Col9.setStyle("-fx-alignment: top-center");
        Col11.setCellValueFactory(new PropertyValueFactory<>("msId"));


        /// sets width of columns by percentage
        Col1.setMaxWidth(1f * Integer.MAX_VALUE * 5);   // Mem 5%
        Col2.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Join Date 15%
        Col3.setMaxWidth(1f * Integer.MAX_VALUE * 10);   // Type
        Col4.setMaxWidth(1f * Integer.MAX_VALUE * 10);   // Slip
        Col5.setMaxWidth(1f * Integer.MAX_VALUE * 15);   // First Name
        Col6.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // Last Name
        Col9.setMaxWidth(1f * Integer.MAX_VALUE * 15);  // State
        Col11.setMaxWidth(1f * Integer.MAX_VALUE * 10); // MSID

        this.getColumns()
                .addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col9, Col11));
    }
}