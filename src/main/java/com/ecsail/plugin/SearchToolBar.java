package com.ecsail.plugin;

import com.ecsail.Log;
import com.ecsail.Plugin;
import com.ecsail.views.dialogues.Dialogue_MembershipIdSearch;
import com.ecsail.views.dialogues.Dialogue_Msid;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.Taskbar.Feature;

import static com.ecsail.plugin.StandardMenus.Configure;
import static java.awt.Taskbar.getTaskbar;
import static java.awt.Taskbar.isTaskbarSupported;

public class SearchToolBar implements Plugin {

    public Menu extraDesktopIntegration(Log log) {
        if (!isTaskbarSupported())
            return null;

        for (Feature feature : Feature.values()) {
            log.log(" " + feature.name() + " " + getTaskbar().isSupported(feature));
        }


        MenuItem useCustomIcon = Configure("by ID", event -> new Dialogue_MembershipIdSearch(), null);
        MenuItem useDefaultAppIcon = Configure("by MSID", event -> new Dialogue_Msid(), null);


        Menu desktopIntegration = new Menu("Search");


        desktopIntegration.getItems().addAll(useCustomIcon, useDefaultAppIcon);

        return desktopIntegration;
    }



    @Override
    public void setup(Stage stage, TabPane tabPane, ToolBar toolBar, Log log, MenuBar menuBar) {
        menuBar.getMenus().add(extraDesktopIntegration(log));
    }
}
