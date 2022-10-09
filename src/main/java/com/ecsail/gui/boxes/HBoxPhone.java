package com.ecsail.gui.boxes;

import com.ecsail.EditCell;
import com.ecsail.enums.PhoneType;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlPhone;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.PersonDTO;
import com.ecsail.structures.PhoneDTO;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.regex.Pattern;

public class HBoxPhone extends HBox {
    
    private PersonDTO person;
    private TableView<PhoneDTO> phoneTableView;
    private ObservableList<PhoneDTO> phone;
    
    public HBoxPhone(PersonDTO p) {
        this.person = p;  // the below callback is to allow commit when focus removed, overrides FX default behavior
        this.phone = FXCollections.observableArrayList(new Callback<PhoneDTO, Observable[]>() {
            @Override
            public Observable[] call(PhoneDTO param) {
                return new Observable[] { param.isListedProperty() };

            }
        });
        this.phone.addAll(SqlPhone.getPhoneByPid(person.getP_id()));
        
        /////// OBJECT INSTANCE //////  
        VBox vboxButtons = new VBox(); // holds phone buttons
        Button phoneAdd = new Button("Add");
        Button phoneDelete = new Button("Delete");
        HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        phoneTableView = new TableView<PhoneDTO>();

        //// OBJECT ATTRIBUTES /////
        phoneAdd.setPrefWidth(60);
        phoneDelete.setPrefWidth(60);
        vboxButtons.setPrefWidth(80);
        vboxButtons.setSpacing(5); // spacing between buttons
        //hboxGrey.setPrefWidth(480);
        
        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(phoneTableView, Priority.ALWAYS);
        VBox.setVgrow(phoneTableView, Priority.ALWAYS);
        
        hboxGrey.setSpacing(10);  // spacing in between table and buttons
        this.setId("custom-tap-pane-frame");
        hboxGrey.setId("box-background-light");
//        vboxPink.setId("box-pink");

        hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
        vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
        VBox.setVgrow(phoneTableView, Priority.ALWAYS);

        ///// TABLEVIE INSTANCE CREATION AND ATTRIBUTES /////
        
        phoneTableView.setItems(phone);
        phoneTableView.setFixedCellSize(30);
        phoneTableView.setEditable(true);
        phoneTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
            
        // example for this column found at https://gist.github.com/james-d/be5bbd6255a4640a5357#file-editcell-java-L109
        TableColumn<PhoneDTO, String> Col1 = createColumn("Phone", PhoneDTO::phoneNumberProperty);
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<PhoneDTO, String>>() {
                    @Override
                    public void handle(CellEditEvent<PhoneDTO, String> t) {
                        ((PhoneDTO) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPhoneNumber(t.getNewValue());
                        String processedNumber = processNumber(t.getNewValue());        
                        int phone_id = ((PhoneDTO) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPhone_ID();
                            SqlUpdate.updatePhone("phone", phone_id, processedNumber);
                        phone.stream()
                                .filter(p -> p.getPhone_ID() == phone_id)
                                .forEach(s -> s.setPhoneNumber(processedNumber));
                    }

                    private String processNumber(String newValue) {
                        // adds dashes
                        if(Pattern.matches("\\d{10}", newValue)) {
                            return addDashes(newValue);
                        }
                        // need to add area code
                        else if(Pattern.matches("\\d{7}", newValue)) {
                            return addDashes("317" + newValue);
                        }
                        // perfect no need to change anything
                        else if(Pattern.matches("(?:\\d{3}-){2}\\d{4}", newValue)) {
                            return newValue;
                        }
                        // removes all junk as long as there are 10 numbers
                        else if(keepOnlyNumbers(newValue).length() == 10) {
                            return addDashes(keepOnlyNumbers(newValue));
                        }
                        // removes all junk and adds default area code if there are 7 numbers
                        else if(keepOnlyNumbers(newValue).length() == 7) {
                            return addDashes("317" + keepOnlyNumbers(newValue));
                        }
                        else {
                            return "illformatted number";
                        }
                    }

                    private String addDashes(String newValue) {
                        StringBuffer resString = new StringBuffer(newValue);
                        return resString.insert(3, "-").insert(7,"-").toString();
                    }

                    private String keepOnlyNumbers(String newValue) {
                        return newValue.replaceAll("[^0-9]", "");
                    }
                }
            );
        
        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<PhoneType> phoneTypeList = FXCollections.observableArrayList(PhoneType.values());
        TableColumn<PhoneDTO, PhoneType> Col2 = new TableColumn<PhoneDTO, PhoneType>("Type");
        Col2.setCellValueFactory(new Callback<CellDataFeatures<PhoneDTO, PhoneType>, ObservableValue<PhoneType>>() {
             
            @Override
            public ObservableValue<PhoneType> call(CellDataFeatures<PhoneDTO, PhoneType> param) {
                PhoneDTO thisPhone = param.getValue();
                String phoneCode = thisPhone.getPhoneType();
                PhoneType phoneType = PhoneType.getByCode(phoneCode);
                return new SimpleObjectProperty<PhoneType>(phoneType);
            }
        });
 
        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(phoneTypeList));
 
        Col2.setOnEditCommit((CellEditEvent<PhoneDTO, PhoneType> event) -> {
            TablePosition<PhoneDTO, PhoneType> pos = event.getTablePosition();
            PhoneType newPhoneType = event.getNewValue();
            int row = pos.getRow();
            PhoneDTO thisPhone = event.getTableView().getItems().get(row);
            SqlUpdate.updatePhone("phone_type", thisPhone.getPhone_ID(), newPhoneType.getCode());
            thisPhone.setPhoneType(newPhoneType.getCode());
        });
        
        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        TableColumn<PhoneDTO, Boolean> Col3 = new TableColumn<PhoneDTO, Boolean>("Listed");
        Col3.setCellValueFactory(new Callback<CellDataFeatures<PhoneDTO, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(CellDataFeatures<PhoneDTO, Boolean> param) {
                    PhoneDTO phone = param.getValue();
                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(phone.isIsListed());
                    // Note: singleCol.setOnEditCommit(): Not work for
                    // CheckBoxTableCell.
                    // When "isListed?" column change.
                    booleanProp.addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                Boolean newValue) {
                            phone.setIsListed(newValue);
                            SqlUpdate.updateListed("phone_listed",phone.getPhone_ID(), newValue);
                        }
                    });
                    return booleanProp;
                }
            });
     
        Col3.setCellFactory(new Callback<TableColumn<PhoneDTO, Boolean>, //
            TableCell<PhoneDTO, Boolean>>() {
                @Override
                public TableCell<PhoneDTO, Boolean> call(TableColumn<PhoneDTO, Boolean> p) {
                    CheckBoxTableCell<PhoneDTO, Boolean> cell = new CheckBoxTableCell<PhoneDTO, Boolean>();
                    cell.setAlignment(Pos.CENTER);
                    return cell;
                }
            });
        
        /// sets width of columns by percentage
        Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 50);   // Phone
        Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );  // Type
        Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Listed

        
        /////////////////// LISTENERS //////////////////////////////
        
        phoneAdd.setOnAction((event) -> {
                // return next key id for phone table
                int phone_id = SqlSelect.getNextAvailablePrimaryKey("phone", "phone_id");
                // attempt to add a new record and return if it is successful
                if (SqlInsert.addPhoneRecord(phone_id, person.getP_id(), true, "new phone", ""))
                    // if sucessfully added to SQL then add a new row in the tableview
                    phone.add(new PhoneDTO(phone_id, person.getP_id(), true, "new phone", ""));
                // Now we will sort it to the top
                Collections.sort(phone, Comparator.comparing(PhoneDTO::getPhone_ID).reversed());
                // this line prevents strange buggy behaviour
                phoneTableView.layout();
                // edit the phone number cell after creating
                phoneTableView.edit(0, Col1);
            });
        
        phoneDelete.setOnAction((event) -> {
                int selectedIndex = phoneTableView.getSelectionModel().getSelectedIndex();
                    if(selectedIndex >= 0)
                        if(SqlDelete.deletePhone(phone.get(selectedIndex)))  // if it is properly deleted in our database
                            phoneTableView.getItems().remove(selectedIndex); // remove it from our GUI
        });
        
        ///////////////////  SET CONTENT  ///////////////////////
        
        phoneTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3));
        vboxPink.getChildren().add(phoneTableView);  // adds pink border around table
        vboxButtons.getChildren().addAll(phoneAdd, phoneDelete); // lines buttons up vertically
        hboxGrey.getChildren().addAll(vboxPink,vboxButtons);
        getChildren().add(hboxGrey);
        
    }  // end of constructor
    
    ////////////////////////  CLASS METHODS //////////////////////////
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
} 
