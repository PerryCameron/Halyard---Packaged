package com.ecsail.widgetfx;
import com.ecsail.BaseApplication;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertFX {
    public static void tieAlertToStage(Alert alert, double stageWidth, double stageHeight) {
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        // Flag to ensure positioning runs only once
        final boolean[] hasPositioned = {false};


        // Position the dialog only once when about to show
        EventHandler<WindowEvent> positionHandler = e -> {
            if (!hasPositioned[0]) {
                if (BaseApplication.primaryStage == null) {
                    System.out.println("Warning: primaryStage is null");
                    return;
                }
                hasPositioned[0] = true;
                double primaryX = BaseApplication.primaryStage.getX();
                double primaryY = BaseApplication.primaryStage.getY();
                double primaryWidth = BaseApplication.primaryStage.getWidth();
                double primaryHeight = BaseApplication.primaryStage.getHeight();




                alertStage.setX(primaryX + (primaryWidth / 2) - (stageWidth / 2));
                alertStage.setY(primaryY + (primaryHeight / 2) - (stageHeight / 2));
            }
        };


        // Add handler and remove it after first show to prevent re-triggering
        alertStage.setOnShowing(positionHandler);
        alertStage.setOnShown(e -> {
            alertStage.removeEventHandler(WindowEvent.WINDOW_SHOWING, positionHandler);
        });
    }

}
