package com.ecsail.customwidgets;

import com.ecsail.BaseApplication;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressIndicatorWindow {

    private Stage stage;
    private Scene scene;
    private ProgressIndicator progressIndicator;

    public ProgressIndicatorWindow() {
        progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(0.0);

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(progressIndicator);
        root.setBackground(null);

        scene = new Scene(root, 200, 200);
        scene.setFill(null);

        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
    }

    // Position the progress indicator window to the center of the owner stage


    public void show() {
        stage.show();
        Stage ownerStage = BaseApplication.stage;
        Bounds bounds = ownerStage.getScene().getRoot().getLayoutBounds();
        double centerX = bounds.getMinX() + bounds.getWidth() / 2.0;
        double centerY = bounds.getMinY() + bounds.getHeight() / 2.0;
        stage.setX(centerX - scene.getWidth() / 2.0);
        stage.setY(centerY - scene.getHeight() / 2.0);
    }

    public void hide() {
        stage.hide();
    }

    public void startTask(Task<Void> task) {
        progressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

}

