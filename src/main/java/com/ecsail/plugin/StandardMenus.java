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

        Menu reports = new Menu("Reports");

        Menu subMenuCreate = new Menu("Create");
        MenuItem boatReport = Configure("Boat Report", (event) -> Launcher.createBoatReport(), KeyCode.C);
        MenuItem membershipReport = Configure("Membership Report", (event) -> Launcher.createMembershipReport(), KeyCode.V);
        MenuItem emailListReport = Configure("Email List", (event) -> Xls_email_list.createSpreadSheet(), KeyCode.N);
        MenuItem renewalForms = Configure("Renewal Forms", (event) -> Launcher.createRenewalForms(), KeyCode.M);
        MenuItem directory = Configure("Directory", (event) -> new Dialogue_DirectoryCreation(), KeyCode.Z);
        subMenuCreate.getItems().addAll(boatReport,membershipReport,emailListReport,renewalForms,directory);
        reports.getItems().add(subMenuCreate);

        Menu membership = new Menu("Membership");
        Menu subMenuMembershipCreate = new Menu("Create");
        MenuItem newMembership = Configure("New Membership", (event) -> CreateMembership.Create(), KeyCode.P);
        MenuItem envelopes = Configure("Envelopes", (event) -> Launcher.openEnvelopesDialogue(), KeyCode.SHIFT.L);
        subMenuMembershipCreate.getItems().addAll(newMembership,envelopes);

        Menu subMenuTabs = new Menu("Tabs");
        MenuItem rosters = Configure("Rosters", (event) -> Launcher.openRosterTab(), null);
        MenuItem bod = Configure("Board Of Directors", (event) -> Launcher.openBoardTab(), null);
        MenuItem people = Configure("People", (event) -> Launcher.openPeopleTab(), null);
        MenuItem boats = Configure("Boats", (event) -> Launcher.openBoatsTab(), null);
        MenuItem slips = Configure("Slips", (event) -> Launcher.openSlipsTab(), null);
        MenuItem deposits = Configure("Deposits", (event) -> Launcher.openDepositsTab(), null);
        MenuItem fees = Configure("Fees", (event) -> Launcher.openFeeTab(), null);
        MenuItem feesExp = Configure("Fees (experimental)", (event) -> Launcher.openFeeTab2(), null);
        MenuItem notes = Configure("Notes", (event) -> Launcher.openNotesTab(), null);
        MenuItem jotform = Configure("Jotform", (event) -> Launcher.openJotFormTab(), null);
        subMenuTabs.getItems().addAll(rosters,bod,people,boats,slips,deposits,fees,feesExp,notes,jotform);

        membership.getItems().addAll(subMenuMembershipCreate, subMenuTabs);



        menuBar.getMenus().addAll(file, membership, reports);
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
