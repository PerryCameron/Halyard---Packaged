package com.ecsail.gui.common;

import com.ecsail.BaseApplication;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

public class SaveFileChooser {
	private File file;
	
	public SaveFileChooser(String directory, String fileName, String Description, String extention) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Description, extention));
        fileChooser.setInitialDirectory(new File(directory + "/"));
        fileChooser.setInitialFileName(fileName);
        this.file = fileChooser.showSaveDialog(BaseApplication.stage);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
