package com.ecsail;

import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

/**
 * This is a very basic, leaky example of a plugin interface
 */
public interface Plugin {

    void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar);

}
