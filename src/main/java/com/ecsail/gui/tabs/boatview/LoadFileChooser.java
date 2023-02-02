package com.ecsail.gui.tabs.boatview;

import com.ecsail.BaseApplication;
import javafx.stage.FileChooser;

import java.io.File;

public class LoadFileChooser {
    private File file;

    public LoadFileChooser(String directory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        //fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Description, extention));
        fileChooser.setInitialDirectory(new File(directory + "/"));
        //fileChooser.setInitialFileName(fileName);
        this.file = fileChooser.showOpenDialog(BaseApplication.stage);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
