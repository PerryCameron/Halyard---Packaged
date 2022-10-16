package com.ecsail.plugin;

import com.ecsail.BaseApplication;
import com.ecsail.Log;
import com.ecsail.Plugin;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import static com.ecsail.HalyardPaths.LOGFILEDIR;
import static java.awt.Desktop.getDesktop;
import static java.lang.System.out;
import static java.util.Calendar.getInstance;

public class LogFile implements Plugin {
    @Override
    public void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar) {

        Menu menu = new Menu("Debug");
        MenuItem findDebugLog = new MenuItem("Find Debug Log folder");
        findDebugLog.setOnAction(e -> showDebugLogFolder());
        MenuItem showDebugLog = new MenuItem("Show Log");
        showDebugLog.setOnAction(event -> showDebugLog());
        menu.getItems().addAll(findDebugLog,showDebugLog);
        menuBar.getMenus().add(menu);
    }

    private void showDebugLog() {
        Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
        // Open the document
        try {
            desktop.open(BaseApplication.outputFile);
        } catch (IOException e) {
            BaseApplication.logger.error(e.getMessage());
        }
    }

    private void showDebugLogFolder() {
        getDesktop().browseFileDirectory(BaseApplication.outputFile);
    }
}
