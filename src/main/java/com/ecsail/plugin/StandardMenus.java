package com.ecsail.plugin;

import com.ecsail.*;
import com.ecsail.excel.Xls_email_list;
import com.ecsail.views.dialogues.Dialogue_DirectoryCreation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.getProperty;

public class StandardMenus implements Plugin {

    private MenuBar menuBar;

    public static Logger logger = LoggerFactory.getLogger(StandardMenus.class);
    public static boolean isMac() {
        return getProperty("os.name").contains("Mac");
    }

    public static MenuItem Configure(String name, EventHandler<ActionEvent> action, KeyCode keyCode) {
        MenuItem item = new MenuItem(name);
        item.setOnAction(action);
//        if (keyCode != null)
//            item.setAccelerator(new KeyCodeCombination(keyCode, KeyCombination.SHORTCUT_DOWN));
        return item;
    }

    public void standardMenus() {

        Menu file = new Menu("File");
        MenuItem createScript = Configure("Close Connection", x -> closeConnection(BaseApplication.primaryStage), KeyCode.J);
        file.getItems().addAll(createScript);

        if (!isMac()) {
            MenuItem quit = Configure("Quit", x -> Platform.exit(), KeyCode.Q);
            file.getItems().add(quit);
        } else {
            menuBar.setUseSystemMenuBar(true);
        }

        Menu reports = new Menu("Reports");

        Menu subMenuCreate = new Menu("Create");
        MenuItem boatReport = Configure("Boat Report", (event) -> Launcher.createBoatReport(), KeyCode.C);
        MenuItem membershipReport = Configure("Membership Report", (event) -> Launcher.createMembershipReport(), KeyCode.V);
        MenuItem emailListReport = Configure("Email List", (event) -> Xls_email_list.createSpreadSheet(), KeyCode.N);
//        MenuItem renewalForms = Configure("Renewal Forms", (event) -> Launcher.createRenewalForms(), KeyCode.M);
        MenuItem directory = Configure("Directory", (event) -> new Dialogue_DirectoryCreation(), KeyCode.Z);
        subMenuCreate.getItems().addAll(boatReport,membershipReport,emailListReport,directory);
//        subMenuCreate.getItems().addAll(boatReport,membershipReport,emailListReport,renewalForms,directory);

        reports.getItems().add(subMenuCreate);

        Menu membership = new Menu("Membership");
        Menu subMenuMembershipCreate = new Menu("Create");
        MenuItem newMembership = Configure("New Membership", (event) -> CreateMembership.Create(), KeyCode.P);
        MenuItem envelopes = Configure("Envelopes", (event) -> Launcher.openEnvelopesDialogue(), KeyCode.L);
        subMenuMembershipCreate.getItems().addAll(newMembership,envelopes);

        Menu subMenuTabs = new Menu("Tabs");
        MenuItem rosters = Configure("Rosters", (event) -> Launcher.openRosterTab(), null);
        MenuItem bod = Configure("Board Of Directors", (event) -> Launcher.openBoardTab(), null);
        MenuItem people = Configure("People", (event) -> Launcher.openPeopleTab(), null);
        MenuItem boats = Configure("Boats", (event) -> Launcher.openBoatsTab(), null);
        MenuItem slips = Configure("Slips", (event) -> Launcher.openSlipsTab(), null);
        MenuItem deposits = Configure("Deposits", (event) -> Launcher.openDepositsTab(), null);
        MenuItem fees = Configure("Fees", (event) -> Launcher.openFeeTab2(), null);
        MenuItem notes = Configure("Notes", (event) -> Launcher.openNotesTab(), null);
        MenuItem jotform = Configure("Jotform", (event) -> Launcher.openJotFormTab(), null);
        MenuItem newYear = Configure("New Year Wizard", (event) -> Launcher.launchNewYearWizard(),null);
//        MenuItem tabStub = Configure("TabStub", (event) -> Launcher.launchTabStub(),null);
        subMenuTabs.getItems().addAll(rosters,bod,people,boats,slips,deposits,fees,notes,jotform,newYear);
        membership.getItems().addAll(subMenuMembershipCreate, subMenuTabs);
        menuBar.getMenus().addAll(file, membership, reports);
    }

    private void closeConnection(Stage primaryStage) {
        logger.info("Closing Database connection");
        Platform.runLater(BaseApplication.getModel().closeConnections());
//        BaseApplication.getModel().closeDatabaseConnection();
        Launcher.closeTabs();
        primaryStage.setTitle("ECSC Membership Database (not connected)");
        BaseApplication.connectDatabase();
    }

    @Override
    public void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar) {
        this.menuBar = menuBar;

        standardMenus();
    }
}
