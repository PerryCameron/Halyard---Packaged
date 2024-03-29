package com.ecsail.views.tabs.boatview;


import com.ecsail.BaseApplication;
import com.ecsail.FileIO;
import com.ecsail.HalyardPaths;
import com.ecsail.connection.Sftp;
import com.ecsail.dto.*;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.interfaces.SettingsRepository;
import com.ecsail.views.common.HBoxNotes;
import com.ecsail.views.common.ImageViewPane;
import com.ecsail.views.dialogues.Dialogue_ChooseMember;
import javafx.collections.FXCollections;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
// TODO need to add history to boat_owner table
public class TabBoatView extends Tab {
    private ObservableList<MembershipListDTO> boatOwners;
    protected SettingsRepository settingsRepository = new SettingsRepositoryImpl();
    private MembershipRepository membershipRepository = new MembershipRepositoryImpl();
    private BoatRepository boatRepository = new BoatRepositoryImpl();
    private Sftp scp;
    protected ArrayList<DbBoatSettingsDTO> boatSettings;
    protected BoatDTO boatDTO;
    protected BoatListDTO boatListDTO;
    protected ArrayList<BoatPhotosDTO> boatPhotosDTOS;
    protected BoatPhotosDTO selectedImage;
    private String user = BaseApplication.getModel().getCurrentLogon().getSshUser();
    private String remotePath = "/home/"+user+"/ecsc_membership/boat_images/";
    private String localPath = System.getProperty("user.home") + "/.ecsc/boat_images/";
    private String[] extensionsAllowed = {"jpg","jpeg","png","bmp","gif"};
    private ImageView imageView;

    public TabBoatView(String text, BoatDTO boat) {
        super(text);
        this.boatDTO = boat;
        this.boatSettings = (ArrayList<DbBoatSettingsDTO>) settingsRepository.getBoatSettings();
        createBoatView();
    }

    private void createBoatView() {
        this.boatOwners = FXCollections.observableArrayList(membershipRepository.getBoatOwnerRoster(boatDTO.getBoatId()));
        this.scp = BaseApplication.connect.getScp();
        this.boatPhotosDTOS = (ArrayList<BoatPhotosDTO>) boatRepository.getImagesByBoatId(boatDTO.getBoatId());
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
        var vboxRightContainer = new VBox(); // contains boxes on right side
        var vboxButtons = new VBox(); // holds phone buttons
        var vboxTableFrame = new VBox(); // holds table
        var vboxInformationBackgroundColor = new VBox();
        var vboxTableBackgroundColor = new VBox();

        for(DbBoatSettingsDTO dbBoatDTO: boatSettings) {
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
        col2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("firstName"));

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
            boatRepository.deleteBoatPhoto(selectedImage);
            // move to next image
            moveToNextImage(true); // this will also update selectedImage
            // if old image was default set the new one as default
            if(imageIsDefault) {
                selectedImage.setDefault(true);
                boatRepository.updateBoatImages(selectedImage);
            }
            BoatPhotosDTO boatPhotosDTO = getBoatPhotoDTOById(id);
            // remove old BoatPhotosDTO arraylist
            boatPhotosDTOS.remove(boatPhotosDTO);
        });

        buttonAddPicture.setOnAction((event) -> {
			LoadFileChooser fc = new LoadFileChooser(System.getProperty("user.home"));
        });

        buttonDefault.setOnAction((event) -> {
            for(BoatPhotosDTO photo: boatPhotosDTOS) {
                if(photo.getId() == selectedImage.getId()) photo.setDefault(true);
                else photo.setDefault(false);
                boatRepository.updateBoatImages(photo);
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
        });

        boatOwnerDelete.setOnAction((event) -> {
            int selectedIndex = boatOwnerTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0)
                if (boatRepository.deleteBoatOwner(boatDTO.getBoatId(), boatOwners.get(selectedIndex).getMsId()) > 0) // if it is																						// our database
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
        vboxPink.getChildren().add(vboxGrey);
        vboxBlue.getChildren().add(vboxPink);
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
        boatPhotosDTOS.sort(Comparator.comparingInt(BoatPhotosDTO::getFileNumber));
        // find index of current image
        int index = boatPhotosDTOS.indexOf(selectedImage);
        if (moveForward) {
            if (index < boatPhotosDTOS.size() - 1) index++;
            else index = 0;
        } else { // we are moving backwards
            if(index == 0) index = boatPhotosDTOS.size() - 1;
            else index--;
        }
        selectedImage = boatPhotosDTOS.get(index);
        String localFile = localPath + selectedImage.getFilename();
        String remoteFile = remotePath + selectedImage.getFilename();
        // if we don't have file on local computer then retrieve it
        if (!HalyardPaths.fileExists(localFile)) scp.getFile(remoteFile, localFile);
        Image image = new Image("file:" + localFile);
        imageView.setImage(image);
    }

    private void saveImage(String srcPath) {
        if(isImageType(srcPath)) {
            // get number for photo
            int fileNumber = getNextFileNumberAvailable();
            // create filename
            String fileName = boatDTO.getBoatId() + "_" + fileNumber + "." + FileIO.getFileExtension(srcPath);
            // create new POJO
            BoatPhotosDTO boatPhotosDTO =
            boatRepository.insertBoatImage(new BoatPhotosDTO(0,
                    boatDTO.getBoatId(),"",fileName,fileNumber,isFirstPic()));
            // send file to remote server and change its group
            scp.sendFile(srcPath,remotePath + boatPhotosDTO.getFilename());
            // no need to changeGroup as we moved this dir to the users home.
//            scp.changeGroup(remotePath + boatPhotosDTO.getFilename(),groupId);
            // update SQL

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
        boatPhotosDTOS.clear();
        boatPhotosDTOS.addAll(boatRepository.getImagesByBoatId(boatDTO.getBoatId()));
        // sort them so one just created is last
        boatPhotosDTOS.sort(Comparator.comparingInt(BoatPhotosDTO::getId));
        // get the last
        return boatPhotosDTOS.get(boatPhotosDTOS.size() -1);
    }

    private int getNextFileNumberAvailable() {
        if(boatPhotosDTOS.size() == 0) return 1;
        else boatPhotosDTOS.sort(Comparator.comparingInt(BoatPhotosDTO::getFileNumber));
        return boatPhotosDTOS.get(boatPhotosDTOS.size() - 1).getFileNumber() + 1;
    }

    private void refreshBoatList() {  // this should update boat view when images are added probably should add all columns
        boatListDTO.setNumberOfImages(boatPhotosDTOS.size());
    }

    private boolean isImageType(String srcPath) {
        String extension = FileIO.getFileExtension(srcPath);
        for (String ex : extensionsAllowed) {
            if (ex.equals(extension.toLowerCase())) return true;
        }
        return false;
    }

    private boolean isFirstPic() {
        return boatPhotosDTOS.size() == 0;
    }

    private BoatPhotosDTO getDefaultBoatPhotoDTO() {
        BoatPhotosDTO boatPhotosDTO1 = boatPhotosDTOS.stream()
                .filter(boatPhotosDTO -> boatPhotosDTO.isDefault())
                .findFirst()
                .orElse(new BoatPhotosDTO(0, 0, "", "no_image.png", 0, true));
        return boatPhotosDTO1;
    }

    private BoatPhotosDTO getBoatPhotoDTOById(int id) {
        BoatPhotosDTO boatPhotosDTO = boatPhotosDTOS.stream()
                .filter(boatPhotosDTO1 -> boatPhotosDTO1.getId() == id)
                .findFirst().orElse(null);
        return boatPhotosDTO;
    }

    public BoatRepository getBoatRepository() {
        return boatRepository;
    }

    public BoatDTO getBoatDTO() {
        return boatDTO;
    }
}
