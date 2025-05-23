package com.ecsail.views.tabs.boatview;

import com.ecsail.BaseApplication;
import javafx.stage.FileChooser;

import java.io.File;

public class LoadFileChooser {
    private File file;

    public LoadFileChooser(String directory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"));
        fileChooser.setInitialDirectory(new File(directory + "/"));
        this.file = fileChooser.showOpenDialog(BaseApplication.primaryStage);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
