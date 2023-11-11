package com.ecsail;

import com.ecsail.connection.ConnectDatabase;
import com.ecsail.connection.PortForwardingL;
import com.ecsail.dto.BoardPositionDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.models.MainModel;
import com.ecsail.plugin.FileDrop;
import com.ecsail.plugin.LogFile;
import com.ecsail.plugin.SearchToolBar;
import com.ecsail.plugin.StandardMenus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.ecsail.HalyardPaths.LOGFILEDIR;

public class BaseApplication extends Application implements Log {

    public static File outputFile;
    /**
     * This is the very simple "registry" for the various demonstration features of this application.
     */
    private final Plugin[] plugins = new Plugin[]{
            new StandardMenus(),
//            new HelloWorld(),
            new FileDrop(),
            new SearchToolBar(),
            new LogFile()
//            new DarkMode()
    };

    public static TabPane tabPane;
    public static Logger logger = LoggerFactory.getLogger(BaseApplication.class);
    public static ObservableList<MembershipListDTO> activeMemberships;
    public static ArrayList<BoardPositionDTO> boardPositions;
    public static ConnectDatabase connect;
    public static String selectedYear;
    public static Stage stage;
    public static String user = "membership";
    public static Label statusLabel;

    MainModel mainModel;


    public static void main(String[] args) {
        setUpForFirstTime();
        startFileLogger();
        logger.info("Starting application...");
        BaseApplication.selectedYear = HalyardPaths.getYear();
        launch(args);
    }

    private static void startFileLogger() {
        try {
            outputFile = File.createTempFile("debug", ".log", new File(LOGFILEDIR));
            PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)), true);
            System.setOut(output);
            System.setErr(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String s) {
        statusLabel.setText(s);
    }

    @Override
    public void start(Stage stage) {
        BaseApplication.stage = stage;

        BorderPane borderPane = new BorderPane();

        VBox topElements = new VBox();

        MenuBar menuBar = new MenuBar();
        topElements.getChildren().add(menuBar);

        ToolBar toolbar = new ToolBar();
        topElements.getChildren().add(toolbar);

        tabPane = new TabPane();

        statusLabel = new Label();
        statusLabel.setPadding(new Insets(5.0f, 5.0f, 5.0f, 5.0f));
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        borderPane.setTop(topElements);
        borderPane.setBottom(statusLabel);
        borderPane.setCenter(tabPane);

        Scene scene = new Scene(borderPane, 1028, 830);

        // closing program with x button
        stage.setOnHiding(event -> Platform.runLater(getModel().closeDatabaseConnection()));
        Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
        stage.getIcons().add(mainIcon);
        stage.setTitle("Halyard");
        stage.setScene(scene);

        for (Plugin plugin : plugins) {
            try {
                plugin.setup(stage, tabPane, toolbar, this, menuBar);
            } catch (Exception e) {
                logger.error("Unable to start plugin");
                logger.error(plugin.getClass().getName());
                e.printStackTrace();
            }
        }

        statusLabel.setText("(Not Connected) Ready.");
        stage.show();
        //put window to front to avoid it to being hidden behind another.
        stage.setAlwaysOnTop(true);
        stage.requestFocus();
        stage.toFront();
        stage.setAlwaysOnTop(false);
        connect = new ConnectDatabase(stage);
        this.mainModel = connect.getMainModel();
        scene.getStylesheets().addAll(
                "css/dark/dark.css",
                "css/dark/tabpane.css",
                "css/dark/tableview.css",
                "css/dark/chart.css",
                "css/dark/bod.css",
                "css/dark/table_changes.css",
                "css/dark/invoice.css");
    }



    public static void connectDatabase() {
        connect = new ConnectDatabase(stage);
    }

    public static PortForwardingL getSSHConnection() {
        return connect.getSshConnection();
    }
    public static void setUpForFirstTime() {
        HalyardPaths.checkPath(System.getProperty("user.home") + "/.ecsc/scripts");
        HalyardPaths.checkPath(System.getProperty("user.home") + "/.ecsc/logs");
    }
    public static DataSource getDataSource() {
        return connect.getMainModel().getAppConfig().getDataSource();
    }

    public static com.ecsail.models.MainModel getModel() { return connect.getMainModel(); }

    public MainModel getMainModel() {
        return mainModel;
    }
}
