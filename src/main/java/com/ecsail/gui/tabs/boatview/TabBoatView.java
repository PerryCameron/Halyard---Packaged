package com.ecsail.gui.tabs.boatview;


import com.ecsail.HalyardPaths;
import com.ecsail.ImageViewPane;
import com.ecsail.gui.dialogues.Dialogue_ChooseMember;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.select.SqlDbBoat;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.DbBoatDTO;
import com.ecsail.structures.MembershipListDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TabBoatView extends Tab {
    private final ObservableList<MembershipListDTO> boatOwners;
    private int pictureNumber = 0;
    //	private Sftp ftp;
    private final ArrayList<String> localImageFiles;
    private final ObservableList<DbBoatDTO> dbBoatDTOS;
    /// need to add history to boat_owner table
    protected BoatDTO boatDTO;
    public TabBoatView(String text, BoatDTO boat) {
        super(text);
        this.boatDTO = boat;
        this.boatOwners = SqlMembershipList.getBoatOwnerRoster(boatDTO.getBoat_id());
        this.dbBoatDTOS = SqlDbBoat.getDbBoat();
//		this.ftp = Halyard.getConnect().getForwardedConnection().getFtp();
//		checkRemoteFiles();
        // make sure directory exists, and create it if it does not
        HalyardPaths.checkPath(HalyardPaths.BOATDIR + "/" + boatDTO.getBoat_id() + "/");
        File imagePath = new File(HalyardPaths.BOATDIR + "/" + boatDTO.getBoat_id() + "/");
        this.localImageFiles = HalyardPaths.listFilesForFolder(imagePath);
        Image image = null;
        if (localImageFiles.size() > 0)
            image = getImage(HalyardPaths.BOATDIR + "/" + boatDTO.getBoat_id() + "/" + localImageFiles.get(pictureNumber));
//		checkIfLocalandRemoteDirectoriesMatch();

        dbBoatDTOS.forEach(System.out::println);
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
        // need to continue with labels


        // vboxFieldsContainer.setStyle("-fx-background-color: #201ac9;"); // blue

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

//		imageView.setOnDragDropped(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                Dragboard db = event.getDragboard();
//                boolean success = false;
//                if (db.hasFiles()) {
//                    //File fileName = db.getFiles().get(0);
//                    String filename = getNewName(db.getFiles().get(0));
//                    File newImage = new File(imagePath, filename);
//                    copyFile(db.getFiles().get(0), newImage);
//                    ftp.sendFile(imagePath + "/" + filename, "/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + filename);
//        			localImageFiles.add(newImage.getName().toString());
//                    success = true;
//                }
//                /* let the source know whether the string was successfully
//                 * transferred and used */
//                event.setDropCompleted(success);
//
//                event.consume();
//            }
//        });

        buttonDelete.setOnAction((event) -> {

        });

        buttonAddPicture.setOnAction((event) -> {
//			LoadFileChooser fc = new LoadFileChooser(System.getProperty("user.home"));
//			System.out.println(fc.getFile().toString());
//			String filename = getNewName(fc.getFile());
//			File newImage = new File(imagePath, filename);
//			copyFile(fc.getFile(), newImage);
//			ftp.sendFile(imagePath + "/" + filename, "/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + filename);
//			localImageFiles.add(newImage.getName().toString());
        });

        buttonForward.setOnAction((event) -> {
            pictureNumber++;
            if (pictureNumber == localImageFiles.size())
                pictureNumber = 0;
            Image newImage = getImage(HalyardPaths.BOATDIR + "/" + boatDTO.getBoat_id() + "/" + localImageFiles.get(pictureNumber));
            imageView.setImage(newImage);
        });

        buttonReverse.setOnAction((event) -> {
            pictureNumber--;
            if (pictureNumber < 0)
                pictureNumber = localImageFiles.size() - 1;
            Image newImage = getImage(HalyardPaths.BOATDIR + "/" + boatDTO.getBoat_id() + "/" + localImageFiles.get(pictureNumber));
            imageView.setImage(newImage);
        });

        boatOwnerAdd.setOnAction((event) -> {
            // int phone_id = SqlSelect.getCount("phone", "phone_id"); // get last phone_id
            // number
            // phone_id++; // increase to first available number
            // if (SqlInsert.addRecord(phone_id, person.getP_id(), true, "new phone", ""))
            // // if added with no errors
            // phone.add(new Object_Phone(phone_id, person.getP_id(), true, "new phone",
            // "")); // lets add it to our GUI
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

//	private void checkIfLocalandRemoteDirectoriesMatch() {
//		ArrayList<String> remoteMissingImages = new ArrayList<String>();
//		ArrayList<String> localMissingImages = new ArrayList<String>();
//		for(String l: localImageFiles) {
//			boolean missing = true;
//			for(String r: remoteImageFiles) {
//				if(l.equals(r)) missing = false;
//			}
//			if(missing) remoteMissingImages.add(l);
//		}
//		for(String r: remoteImageFiles) {
//			boolean missing = true;
//			for(String l: localImageFiles) {
//				if(r.equals(l)) missing = false;
//			}
//			if(missing) localMissingImages.add(r);
//		}
//		System.out.println("Remote missing images:");
//		for(String rmm: remoteMissingImages) {
//			ftp.sendFile(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + rmm, "/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + rmm);
//		}
//		System.out.println("Local missing images:");
//		for(String lmm: localMissingImages) {
//			ftp.getFile("/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + lmm, HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + lmm);
//			localImageFiles.add(lmm);
//		}
//	}

//	private void checkRemoteFiles() {
//		boolean hasDirectory = false;
//		ArrayList<String> remoteImageDirectories = ftp.ls("/home/pcameron/Documents/ECSC/Boats"); // prints files from directory
//		for(String fn: remoteImageDirectories) {
//			System.out.println(fn);
//			if(fn.equals(b.getBoat_id() + "")) {
//				hasDirectory = true;
//			}
//		}
//		if(!hasDirectory) {  // if the directory doesn't exist create it
//			ftp.mkdir("/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id());
//		} else {  // else put file names in that directory into a string array
//			remoteImageFiles = ftp.ls("/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id());
//		}
//	}

    public Image getImage(String file) {
        FileInputStream input = null;
        try {
            // System.out.println("pictureNumber=" + pictureNumber);
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert input != null;
        return new Image(input);
    }

    private void copyFile(File srcFile, File destFile) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(destFile);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
                assert os != null;
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
