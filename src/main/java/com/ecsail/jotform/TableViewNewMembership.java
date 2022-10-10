package com.ecsail.jotform;

import com.ecsail.gui.customwidgets.RoundCheckBox;
import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;


public class TableViewNewMembership {
    ObservableList<JotFormSubmissionListDTO> list;
    JotForm client;
    JSONObject response = null;
    public TableViewNewMembership(ObservableList<JotFormSubmissionListDTO> list, JotForm client)  {

        this.list = list;
        this.client = client;
    }

    public TableView<JotFormSubmissionListDTO> getContent() {
        HashMap<String,String> hash = new HashMap<>();
        hash.put("flag","0");
        TableView<JotFormSubmissionListDTO> tableView = new TableView<>();
        // create columns
        TableColumn<JotFormSubmissionListDTO, String> createdCol = new TableColumn<>("Created");
        TableColumn<JotFormSubmissionListDTO, String> statusCol = new TableColumn<>("Status");
        TableColumn<JotFormSubmissionListDTO, Boolean> newCol = new TableColumn<>("Viewed");
        TableColumn<JotFormSubmissionListDTO, Boolean> flagCol = new TableColumn<>("Flag");
        TableColumn<JotFormSubmissionListDTO, String> Col5 = new TableColumn<>("First Name");
        TableColumn<JotFormSubmissionListDTO, String> Col6 = new TableColumn<>("Last Name");
        TableColumn<JotFormSubmissionListDTO, String> Col7 = new TableColumn<>("Address");
        TableColumn<JotFormSubmissionListDTO, String> Col8 = new TableColumn<>("City");
        TableColumn<JotFormSubmissionListDTO, String> Col9 = new TableColumn<>("State");
        TableColumn<JotFormSubmissionListDTO, String> Col10 = new TableColumn<>("Zip");
        // set cell factories for content
        newCol.setCellValueFactory(cellData -> cellData.getValue().isNewProperty());
        flagCol.setCellValueFactory(cellData -> cellData.getValue().isNewProperty());
        createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        Col5.setCellValueFactory(new PropertyValueFactory<>("primaryFirstName"));
        Col6.setCellValueFactory(new PropertyValueFactory<>("primaryLastName"));
        Col7.setCellValueFactory(new PropertyValueFactory<>("address"));
        Col8.setCellValueFactory(new PropertyValueFactory<>("city"));
        Col9.setCellValueFactory(new PropertyValueFactory<>("state"));
        Col10.setCellValueFactory(new PropertyValueFactory<>("postal"));

        newCol.setCellFactory(col -> {
            TableCell<JotFormSubmissionListDTO, Boolean> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null) {
                    Node centreBox = createPriorityGraphic(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }
            });
            return cell;
        });

        flagCol.setCellFactory(col -> {
            TableCell<JotFormSubmissionListDTO, Boolean> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null) {
                    RoundCheckBox roundCheckBox = createFlagChecks(newVal);
                    roundCheckBox.isSelectedProperty().addListener((observableValue, aBoolean, checkBoxSelected) -> {
                        // gets the data object from the row we are clicking on
                        JotFormSubmissionListDTO line = (JotFormSubmissionListDTO) cell.getTableRow().getItem();
                        // changes flag to either 0 or 1
                        hash.replace("flag",booleanToStringNumnber(checkBoxSelected));
                        // sends out the http POST request to change variable
                        response = client.editSubmission(line.getSubmissionId(),hash);
                        // makes sure it changed at server before changing circleCheckBox
                        roundCheckBox.setResponseCode(String.valueOf(response.get("responseCode")));
                    });
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((RoundCheckBox) null).otherwise(roundCheckBox));
                }

            });
            return cell;
        });

        createdCol.setCellFactory(col -> {
            TableCell<JotFormSubmissionListDTO, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null) {
                    Node centreBox = createText(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }

            });
            return cell;
        });



        tableView.setItems(list);
        tableView.setFixedCellSize(30);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
        VBox.setVgrow(tableView, Priority.ALWAYS);

        /// sets width of columns by percentage
        createdCol.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Created
        statusCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // Status
        newCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // new?
        flagCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // flagged?
        Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // First Name
        Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Last Name
        Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Address
        Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // City
        Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // State
        Col10.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // Zip

        tableView.setRowFactory(tv -> {
            TableRow<JotFormSubmissionListDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    // int rowIndex = row.getIndex();
                    JotFormSubmissionListDTO clickedRow = row.getItem();
                    System.out.println(clickedRow.getAddress());
                }
            });
            return row;
        });

        tableView.getColumns()
                .addAll(Arrays.asList(newCol, flagCol, createdCol, statusCol,  Col5, Col6, Col7, Col8, Col9, Col10));
        return tableView;
    }

    private String booleanToStringNumnber(Boolean t1) {
        if (t1) return "1";
        return "0";
    }

    private RoundCheckBox createFlagChecks(Boolean isFlagged) {
        RoundCheckBox roundCheckBox = new RoundCheckBox();
        if(isFlagged) {
            roundCheckBox.setSelected(true);
        }
        return roundCheckBox;
    }

    private Node createPriorityGraphic(Boolean isPriority){
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        if(isPriority){
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/new.png")));
            imageView.setFitHeight(22);
            imageView.setPreserveRatio(true);
            graphicContainer.getChildren().add(imageView);
        }
        graphicContainer.setAlignment(Pos.CENTER);
//        graphicContainer.setStyle("-fx-background-color: #feffab;");  // yellow
        return graphicContainer;
    }

    private Node createText(String text) {
        String[] result = text.split(" ");
        HBox textContainer = new HBox();
        VBox vBoxDate = new VBox();
        VBox vBoxTime = new VBox();

        Text dateText = new Text(result[0]);
        Text timeText = new Text(result[1]);
        dateText.setFill(Color.BLUE);
//        timeText.setFill(Color.CORNFLOWERBLUE);
        vBoxDate.getChildren().add(dateText);
        vBoxTime.getChildren().add(timeText);
        vBoxDate.setPrefWidth(100);
        vBoxTime.setPrefWidth(90);
        vBoxDate.setAlignment(Pos.CENTER_LEFT);
        vBoxTime.setAlignment(Pos.CENTER_LEFT);
        textContainer.setSpacing(5);
//        vBoxDate.setStyle("-fx-background-color: grey;");
//        textContainer.setStyle("-fx-background-color: white;");
        textContainer.getChildren().addAll(vBoxDate,vBoxTime);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setSpacing(3);
        return textContainer;
    }
}
