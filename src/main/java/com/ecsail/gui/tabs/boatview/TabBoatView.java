package com.ecsail.gui.tabs.boatview;


import com.ecsail.BaseApplication;
import com.ecsail.FileIO;
import com.ecsail.HalyardPaths;
import com.ecsail.ImageViewPane;
import com.ecsail.connection.Sftp;
import com.ecsail.gui.dialogues.Dialogue_ChooseMember;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlBoatPhotos;
import com.ecsail.sql.select.SqlDbBoat;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.BoatPhotosDTO;
import com.ecsail.structures.DbBoatDTO;
import com.ecsail.structures.MembershipListDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class TabBoatView extends Tab {
    private final ObservableList<MembershipListDTO> boatOwners;
    private Sftp scp;
    private final ObservableList<DbBoatDTO> dbBoatDTOS;
    // TODO need to add history to boat_owner table
    protected BoatDTO boatDTO;
    protected ArrayList<BoatPhotosDTO> images;
    protected BoatPhotosDTO selectedImage;
    String remotePath = "/home/ecsc/ecsc_files/boat_images/";
    String localPath = System.getProperty("user.home") + "/.ecsc/boat_images/";
    String[] extensionsAllowed = {"jpg","jpeg","png","bmp","gif"};
    // this is the group number for ecsc
    int groupId = 1006;

    public TabBoatView(String text, BoatDTO boat) {
        super(text);
        this.boatDTO = boat;
        this.boatOwners = SqlMembershipList.getBoatOwnerRoster(boatDTO.getBoat_id());
        this.dbBoatDTOS = SqlDbBoat.getDbBoat();
        this.scp = BaseApplication.connect.getScp();
        this.images = SqlBoatPhotos.getImagesByBoatId(boatDTO.getBoat_id());

        // make sure directory exists, and create it if it does not
        this.selectedImage = getDefaultBoatPhotoDTO();
        String localFile = localPath + selectedImage.getFilename();
        String remoteFile = remotePath + selectedImage.getFilename();
        Image image = null;

        if(selectedImage.getFilename().equals("no_image.png")) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/no_image.png")));
        } else if(HalyardPaths.fileExists(localFile)) {
            image = new Image("file:" + localFile);
        } else {
            scp.getFile(remoteFile,localFile);
            image = new Image("file:" + localFile);
        }

        TableView<MembershipListDTO> boatOwnerTableView = new TableView<>();
        var vboxGrey = new VBox(); // this is the hbox for holding all content
        var vboxBlue = new VBox(); // creates blue boarder around content
        var vboxPink = new VBox(); // this creates a pink border around the table
        var hboxContainer = new HBox();
        var vboxLeftContainer = new VBox(); // contains boxes on left side
        var vboxRightContainer = new VBox(); // contains boxes on left side
        var vboxButtons = new VBox(); // holds phone buttons
        var vboxTableFrame = new VBox(); // holds table
        var vboxInformationBackgroundColor = new VBox();
        var vboxTableBackgroundColor = new VBox();

        for(DbBoatDTO dbBoatDTO: dbBoatDTOS) {
            vboxInformationBackgroundColor.getChildren().add(new Row(this, dbBoatDTO));
        }

        var hboxTable = new HBox();
        var ownerTitlePane = new TitledPane();
        var boatInfoTitlePane = new TitledPane();
        var vboxPicture = new VBox();
        var hboxPictureControls = new HBox();
        var imageView = new ImageView();
        var viewPane = new ImageViewPane(imageView);
        var buttonForward = new Button(">");
        var buttonReverse = new Button("<");
        var buttonAddPicture = new Button("Add");
        var buttonDelete = new Button("Delete");

        var col1 = new TableColumn<MembershipListDTO, Integer>("MEM");
        var col2 = new TableColumn<MembershipListDTO, String>("Last Name");
        var col3 = new TableColumn<MembershipListDTO, String>("First Name");
        var boatOwnerAdd = new Button("Add");
        var boatOwnerDelete = new Button("Delete");

        /////////////// ATTRIBUTES ////////////////

        boatOwnerAdd.setPrefWidth(60);
        boatOwnerDelete.setPrefWidth(60);
        vboxButtons.setPrefWidth(80);
        vboxLeftContainer.setMaxWidth(350);
        hboxPictureControls.setPrefHeight(40);
        vboxPicture.setPrefWidth(630);
        vboxPicture.setPrefHeight(489);

        vboxBlue.setId("box-blue");
        vboxPink.setId("box-pink");
        vboxTableBackgroundColor.setId("box-grey");
        vboxInformationBackgroundColor.setId("box-grey");
        vboxTableFrame.setId("box-pink");

        // imageView.maxWidth(630);
        // imageView.setFitWidth(700);

        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);

        // vboxGrey.setId("slip-box");
        VBox.setVgrow(vboxGrey, Priority.ALWAYS);
        HBox.setHgrow(vboxGrey, Priority.ALWAYS);
        VBox.setVgrow(vboxPink, Priority.ALWAYS);
        HBox.setHgrow(boatOwnerTableView, Priority.ALWAYS);
        VBox.setVgrow(ownerTitlePane, Priority.ALWAYS);
        HBox.setHgrow(vboxRightContainer, Priority.ALWAYS);
        HBox.setHgrow(vboxPicture, Priority.ALWAYS);
        VBox.setVgrow(vboxPicture, Priority.ALWAYS);

        // vboxPicture.setStyle("-fx-background-color: #e83115;");
        // hboxPictureControls.setStyle("-fx-background-color: #201ac9;"); // blue

        // spacer.setPrefHeight(50);
        vboxLeftContainer.setSpacing(10);
        vboxButtons.setSpacing(5); // spacing between buttons
        vboxGrey.setSpacing(10);
        hboxPictureControls.setSpacing(10);

        hboxPictureControls.setAlignment(Pos.CENTER);
        // sets size of table
        // ownerTitlePane.setPrefHeight(130);

        ownerTitlePane.setText("Owner(s)");
        boatInfoTitlePane.setText("Boat Information");
        vboxButtons.setPadding(new Insets(0, 0, 0, 5));
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
        vboxTableBackgroundColor.setPadding(new Insets(10, 10, 10, 10));
        vboxTableFrame.setPadding(new Insets(2, 2, 2, 2));

        boatOwnerTableView.setItems(boatOwners);
        boatOwnerTableView.setFixedCellSize(30);
        boatOwnerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        boatOwnerTableView.setPrefHeight(90);

        col1.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        col2.setCellValueFactory(new PropertyValueFactory<>("lname"));
        col3.setCellValueFactory(new PropertyValueFactory<>("fname"));

        /// sets width of columns by percentage
        col1.setMaxWidth(1f * Integer.MAX_VALUE * 20); // Mem 5%
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 40); // Join Date 15%
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 40); // Type

        /////////////// LISTENERS ////////////////////
        // must have this to work
        imageView.setOnDragOver(event -> {
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != imageView &&
                    event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                //event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

		imageView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                String srcPath = db.getFiles().get(0).getAbsolutePath();
                storeNewImage(imageView, srcPath);
            }
            event.setDropCompleted(success);
            event.consume();
        });

        buttonDelete.setOnAction((event) -> {

        });

        buttonAddPicture.setOnAction((event) -> {
			LoadFileChooser fc = new LoadFileChooser(System.getProperty("user.home"));
			System.out.println(fc.getFile().toString());

        });

        buttonForward.setOnAction((event) -> {

        });

        buttonReverse.setOnAction((event) -> {

        });

        boatOwnerAdd.setOnAction((event) -> {
            new Dialogue_ChooseMember(boatOwners, boatDTO.getBoat_id());
            // boatOwners.add(new Object_MembershipList());
        });

        boatOwnerDelete.setOnAction((event) -> {
            int selectedIndex = boatOwnerTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0)
                if (SqlDelete.deleteBoatOwner(boatDTO.getBoat_id(), boatOwners.get(selectedIndex).getMsid())) // if it is																						// our database
                    boatOwnerTableView.getItems().remove(selectedIndex); // remove it from our GUI
        });

        /////////////// SET CONTENT //////////////////

        /////////////////////// LEFT CONTAINER /////////////////////
        boatOwnerTableView.getColumns().addAll(Arrays.asList(col1, col2, col3));
        vboxTableFrame.getChildren().add(boatOwnerTableView);
        vboxButtons.getChildren().addAll(boatOwnerAdd, boatOwnerDelete);
        hboxTable.getChildren().addAll(vboxTableFrame, vboxButtons);
        vboxTableBackgroundColor.getChildren().add(hboxTable);
        ownerTitlePane.setContent(vboxTableBackgroundColor);

        boatInfoTitlePane.setContent(vboxInformationBackgroundColor);
        vboxLeftContainer.getChildren().addAll(boatInfoTitlePane, ownerTitlePane);

        /////////////////////// RIGHT CONTAINER /////////////////////
        imageView.setImage(image);
        // imageView sent to viewPane through constructor of ImageViewPane
        vboxPicture.getChildren().add(viewPane);
        hboxPictureControls.getChildren().addAll(buttonReverse, buttonForward, buttonAddPicture);
        vboxRightContainer.getChildren().addAll(hboxPictureControls, vboxPicture);

        hboxContainer.getChildren().addAll(vboxLeftContainer, vboxRightContainer);
        vboxGrey.getChildren().addAll(hboxContainer, new HBoxBoatNotes(boatDTO));
        vboxBlue.getChildren().add(vboxPink);
        vboxPink.getChildren().add(vboxGrey);
        setContent(vboxBlue);
    }

    private void storeNewImage(ImageView imageView, String srcPath) {
        if(isImageType(srcPath)) {
            int fileNumber = getNextFileNumberAvailable();
            String fileName = boatDTO.getBoat_id() + "_" + fileNumber + "." + FileIO.getFileExtension(srcPath);
            BoatPhotosDTO boatPhotosDTO = new BoatPhotosDTO(0,
                    boatDTO.getBoat_id(),"",fileName,fileNumber,isFirstPic());
            scp.sendFile(srcPath,remotePath + boatPhotosDTO.getFilename());
            scp.changeGroup(remotePath + boatPhotosDTO.getFilename(),groupId);
            SqlInsert.addBoatImage(boatPhotosDTO);
            images.add(boatPhotosDTO);
            FileIO.copyFile(new File(srcPath),new File(localPath + fileName));
            Image newImage = new Image("file:" + localPath + fileName);
            imageView.setImage(newImage);
        } else {
            // TODO not an image type do nothing?
        }
    }

    private int getNextFileNumberAvailable() {
        if(images.size() == 0) return 1;
        else images.sort(Comparator.comparingInt(BoatPhotosDTO::getFileNumber));
        return images.get(images.size() - 1).getFileNumber() + 1;
    }

    private boolean isImageType(String srcPath) {
        String extension = FileIO.getFileExtension(srcPath);
        for (String ex : extensionsAllowed) {
            if (ex.equals(extension)) return true;
        }
        return false;
    }

    private boolean isFirstPic() {
        return images.size() == 0;
    }

    private BoatPhotosDTO getDefaultBoatPhotoDTO() {
        BoatPhotosDTO boatPhotosDTO1 = images.stream()
                .filter(boatPhotosDTO -> boatPhotosDTO.isDefault())
                .findFirst()
                .orElse(new BoatPhotosDTO(0, 0, "", "no_image.png", 0, true));
        return boatPhotosDTO1;
    }
}
