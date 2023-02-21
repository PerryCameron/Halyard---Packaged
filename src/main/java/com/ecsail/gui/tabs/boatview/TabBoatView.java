package com.ecsail.gui.tabs.boatview;


import com.ecsail.BaseApplication;
import com.ecsail.FileIO;
import com.ecsail.HalyardPaths;
import com.ecsail.gui.common.ImageViewPane;
import com.ecsail.connection.Sftp;
import com.ecsail.gui.common.HBoxNotes;
import com.ecsail.gui.dialogues.Dialogue_ChooseMember;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlBoatPhotos;
import com.ecsail.sql.select.SqlDbBoat;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.dto.*;
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
// TODO need to add history to boat_owner table
public class TabBoatView extends Tab {
    private ObservableList<MembershipListDTO> boatOwners;
    private Sftp scp;
    private ObservableList<DbBoatDTO> dbBoatDTOS;
    protected BoatDTO boatDTO;
    protected BoatListDTO boatListDTO;
    protected ArrayList<BoatPhotosDTO> images;
    protected BoatPhotosDTO selectedImage;
    String remotePath = "/home/ecsc/ecsc_files/boat_images/";
    String localPath = System.getProperty("user.home") + "/.ecsc/boat_images/";
    String[] extensionsAllowed = {"jpg","jpeg","png","bmp","gif"};
    // this is the group number for ecsc
    int groupId = 1006;
    private ImageView imageView;

    protected boolean fromList;

    public TabBoatView(String text, BoatDTO boat) {
        super(text);
        this.boatDTO = boat;
        this.fromList = false;
        createBoatView();
    }

    public TabBoatView(String text, BoatListDTO boatList) {
        super(text);
        this.boatListDTO = boatList;
        this.boatDTO = boatList;
        this.fromList = true;
        createBoatView();
    }

    private void createBoatView() {
        this.boatOwners = SqlMembershipList.getBoatOwnerRoster(boatDTO.getBoatId());
        this.dbBoatDTOS = SqlDbBoat.getDbBoat();
        this.scp = BaseApplication.connect.getScp();
        this.images = SqlBoatPhotos.getImagesByBoatId(boatDTO.getBoatId());
        this.imageView = new ImageView();
        // make sure directory exists, and create it if it does not
        this.selectedImage = getDefaultBoatPhotoDTO();
        setDefaultImage();

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

        var viewPane = new ImageViewPane(imageView);
        var buttonForward = new Button(">");
        var buttonReverse = new Button("<");
        var buttonAddPicture = new Button("Add");
        var buttonDelete = new Button("Delete");
        var buttonDefault = new Button("Set As Default");

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
                System.out.println("Saving image");
                saveImage(srcPath);
            }
            event.setDropCompleted(success);
            event.consume();
        });

        buttonDelete.setOnAction((event) -> {
            // find if current image is default
            boolean imageIsDefault = selectedImage.isDefault();
            // get id of selected image
            int id = selectedImage.getId();
            // delete remote
            scp.deleteFile(remotePath + selectedImage.getFilename());
            // delete local
            FileIO.deleteFile(localPath + selectedImage.getFilename());
            // remove database entry
            SqlDelete.deleteBoatPhoto(selectedImage);
            // move to next image
            moveToNextImage(true); // this will also update selectedImage
            // if old image was default set the new one as default
            if(imageIsDefault) {
                selectedImage.setDefault(true);
                SqlUpdate.updateBoatImages(selectedImage);
            }
            BoatPhotosDTO boatPhotosDTO = getBoatPhotoDTOById(id);
            // remove old BoatPhotosDTO arraylist
            images.remove(boatPhotosDTO);
        });

        buttonAddPicture.setOnAction((event) -> {
			LoadFileChooser fc = new LoadFileChooser(System.getProperty("user.home"));
			System.out.println(fc.getFile().toString());

        });

        buttonDefault.setOnAction((event) -> {
            for(BoatPhotosDTO photo: images) {
                if(photo.getId() == selectedImage.getId()) photo.setDefault(true);
                else photo.setDefault(false);
                SqlUpdate.updateBoatImages(photo);
            }
        });

        buttonForward.setOnAction((event) -> {
            moveToNextImage(true);
        });

        buttonReverse.setOnAction((event) -> {
            moveToNextImage(false);
        });

        boatOwnerAdd.setOnAction((event) -> {
            new Dialogue_ChooseMember(boatOwners, boatDTO.getBoatId());
            // boatOwners.add(new Object_MembershipList());
        });

        boatOwnerDelete.setOnAction((event) -> {
            int selectedIndex = boatOwnerTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0)
                if (SqlDelete.deleteBoatOwner(boatDTO.getBoatId(), boatOwners.get(selectedIndex).getMsId())) // if it is																						// our database
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

        // imageView sent to viewPane through constructor of ImageViewPane
        vboxPicture.getChildren().add(viewPane);
        hboxPictureControls.getChildren().addAll(buttonReverse, buttonForward, buttonDefault, buttonAddPicture, buttonDelete);
        vboxRightContainer.getChildren().addAll(hboxPictureControls, vboxPicture);

        hboxContainer.getChildren().addAll(vboxLeftContainer, vboxRightContainer);
        vboxGrey.getChildren().addAll(hboxContainer, new HBoxNotes(boatDTO));
        vboxBlue.getChildren().add(vboxPink);
        vboxPink.getChildren().add(vboxGrey);
        setContent(vboxBlue);
    }

    private void setDefaultImage() {
        Image image = null;
        String localFile = localPath + selectedImage.getFilename();
        String remoteFile = remotePath + selectedImage.getFilename();
        if(selectedImage.getFilename().equals("no_image.png")) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/no_image.png")));
        } else if(HalyardPaths.fileExists(localFile)) {
            image = new Image("file:" + localFile);
        } else {
            scp.getFile(remoteFile,localFile);
            image = new Image("file:" + localFile);
        }
        imageView.setImage(image);
    }

    private void moveToNextImage(boolean moveForward) {
        // put them in ascending order, in case a new image has recently been added
        images.sort(Comparator.comparingInt(BoatPhotosDTO::getFileNumber));
        // find index of current image
        int index = images.indexOf(selectedImage);
        if (moveForward) {
            if (index < images.size() - 1) index++;
            else index = 0;
        } else { // we are moving backwards
            if(index == 0) index = images.size() - 1;
            else index--;
        }
        selectedImage = images.get(index);
        String localFile = localPath + selectedImage.getFilename();
        String remoteFile = remotePath + selectedImage.getFilename();
        // if we don't have file on local computer then retrieve it
        if (!HalyardPaths.fileExists(localFile)) scp.getFile(remoteFile, localFile);
        Image image = new Image("file:" + localFile);
        imageView.setImage(image);
    }

    private void saveImage(String srcPath) {
        if(isImageType(srcPath)) {
            System.out.println(srcPath);
            // get number for photo
            int fileNumber = getNextFileNumberAvailable();
            // create filename
            String fileName = boatDTO.getBoatId() + "_" + fileNumber + "." + FileIO.getFileExtension(srcPath);
            // create new POJO
            BoatPhotosDTO boatPhotosDTO = new BoatPhotosDTO(0,
                    boatDTO.getBoatId(),"",fileName,fileNumber,isFirstPic());
            // send file to remote server and change its group
            scp.sendFile(srcPath,remotePath + boatPhotosDTO.getFilename());
            scp.changeGroup(remotePath + boatPhotosDTO.getFilename(),groupId);
            // update SQL
            SqlInsert.addBoatImage(boatPhotosDTO);
            // move a copy to local HD
            FileIO.copyFile(new File(srcPath),new File(localPath + fileName));
            // resets everything to work correctly in GUI
            selectedImage = resetImages();
            // Show our new image
            Image newImage = new Image("file:" + localPath + fileName);
            imageView.setImage(newImage);
            // to update tableview in TabBoats
            refreshBoatList();
        } else {
            // TODO not an image type do nothing?
        }
    }

    private BoatPhotosDTO resetImages() {
        images.clear();
        images.addAll(SqlBoatPhotos.getImagesByBoatId(boatDTO.getBoatId()));
        // sort them so one just created is last
        images.sort(Comparator.comparingInt(BoatPhotosDTO::getId));
        // get the last
        return images.get(images.size() -1);
    }

    private int getNextFileNumberAvailable() {
        if(images.size() == 0) return 1;
        else images.sort(Comparator.comparingInt(BoatPhotosDTO::getFileNumber));
        return images.get(images.size() - 1).getFileNumber() + 1;
    }

    private void refreshBoatList() {  // this should update boat view when images are added probably should add all columns
        boatListDTO.setNumberOfImages(images.size());
    }

    private boolean isImageType(String srcPath) {
        String extension = FileIO.getFileExtension(srcPath);
        for (String ex : extensionsAllowed) {
            if (ex.equals(extension.toLowerCase())) return true;
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

    private BoatPhotosDTO getBoatPhotoDTOById(int id) {
        BoatPhotosDTO boatPhotosDTO = images.stream()
                .filter(boatPhotosDTO1 -> boatPhotosDTO1.getId() == id)
                .findFirst().orElse(null);
        return boatPhotosDTO;
    }
}
