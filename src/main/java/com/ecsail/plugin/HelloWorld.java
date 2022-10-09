package com.ecsail.plugin;

import com.ecsail.Log;
import com.ecsail.Plugin;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HelloWorld implements Plugin {

    Button button;

    @Override
    public void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar) {
        button = new Button();
        button.setText("Hello World");
        button.setOnAction(event -> log.log("Hello World! " + java.util.Calendar.getInstance().getTime()));
        button.setFocusTraversable(false);

        toolBar.getItems().add(button);
    }
}
