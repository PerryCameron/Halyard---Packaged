package com.ecsail.plugin;

import com.ecsail.*;
import com.ecsail.excel.Xls_email_list;
import com.ecsail.gui.dialogues.Dialogue_DirectoryCreation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static java.lang.System.getProperty;

public class StandardMenus implements Plugin {

    private Stage stage;
    private MenuBar menuBar;
    private Log output;

    public static boolean isMac() {
        return getProperty("os.name").contains("Mac");
    }

    public static MenuItem Configure(String name, EventHandler<ActionEvent> action, KeyCode keyCode) {
        MenuItem item = new MenuItem(name);
        item.setOnAction(action);
        if (keyCode != null)
            item.setAccelerator(new KeyCodeCombination(keyCode, KeyCombination.SHORTCUT_DOWN));
        return item;
    }

    public void standardMenus() {

        Menu file = new Menu("File");
//        MenuItem newFile = Configure("New", x -> output.log("File -> New"), KeyCode.N);
//        MenuItem open = Configure("Open...", x -> openFileDialog(), KeyCode.O);
        MenuItem createScript = Configure("Close Connection", x -> closeConnection(BaseApplication.stage), KeyCode.J);
        MenuItem closeConnection = Configure("Back Up Database", (event) -> SqlScriptMaker.createSql(), KeyCode.B);

        file.getItems().addAll(createScript, closeConnection);

        if (!isMac()) {
            MenuItem quit = Configure("Quit", x -> Platform.exit(), KeyCode.Q);
            file.getItems().add(quit);
        } else {
            menuBar.setUseSystemMenuBar(true);
        }

//        Menu edit = new Menu("Edit");
//        MenuItem undo = Configure("Undo", x -> output.log("Undo"), KeyCode.Z);
//        MenuItem redo = Configure("Redo", x -> output.log("Redo"), KeyCode.R);
//        SeparatorMenuItem editSeparator = new SeparatorMenuItem();
//        MenuItem cut = Configure("Cut", x -> output.log("Cut"), KeyCode.X);
//        MenuItem copy = Configure("Copy", x -> output.log("Copy"), KeyCode.C);
//        MenuItem paste = Configure("Paste", x -> output.log("Paste"), KeyCode.V);

//        edit.getItems().addAll(undo, redo, editSeparator, cut, copy, paste);

        Menu menu3 = new Menu("Membership");

        Menu subMenuCreate = new Menu("Create");

        MenuItem boatReport = Configure("Boat Report", (event) -> Launcher.createBoatReport(), KeyCode.C);
        MenuItem membershipReport = Configure("Membership Report", (event) -> Launcher.createMembershipReport(), KeyCode.V);
        MenuItem emailListReport = Configure("Email List", (event) -> Xls_email_list.createSpreadSheet(), KeyCode.N);
        MenuItem renewalForms = Configure("Renewal Forms", (event) -> Launcher.createRenewalForms(), KeyCode.M);
        MenuItem directory = Configure("Directory", (event) -> new Dialogue_DirectoryCreation(), KeyCode.Z);




        subMenuCreate.getItems().add(boatReport);


        menu3.getItems().add(subMenuCreate);


        menuBar.getMenus().addAll(file, menu3);
    }

    private void closeConnection(Stage primaryStage) {
        BaseApplication.closeDatabaseConnection();
        Launcher.closeTabs();
        primaryStage.setTitle("ECSC Membership Database (not connected)");
        BaseApplication.connectDatabase();
    }

    @Override
    public void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar) {
        this.menuBar = menuBar;
        this.output = log;
        this.stage = stage;

        standardMenus();
    }
}
