package com.ecsail.plugin;

import com.ecsail.ConnectDatabase;
import com.ecsail.Log;
import com.ecsail.Plugin;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DarkMode implements Plugin {

    private boolean isDark;

    private Scene scene;
    private Button toggleDark;


    @Override
    public void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar) {
        scene = stage.getScene();
        toggleDark = new Button();
        toggleDark.setText("Light");
        toggleDark.setOnAction(e -> toggleDark());
        toggleDark.setFocusTraversable(false);
        toolBar.getItems().add(toggleDark);
    }

    private void toggleDark() {
        if (isDark) {
//            scene.getRoot().setStyle("");
//            toggleDark.setText("Light");
        } else {
//            scene.getRoot().setStyle("-fx-base:#25292D;");
//            ConnectDatabase.logonStage.getScene().getRoot().setStyle("-fx-base:#25292D;");
//            scene.getStylesheets().add("css/dark/dark.css");
//            toggleDark.setText("Dark");
        }
        isDark = !isDark;
    }
}
