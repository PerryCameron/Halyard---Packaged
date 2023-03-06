package com.ecsail.views.tabs.boatlist;

import com.ecsail.dto.BoatListDTO;
import com.ecsail.models.BoatListModel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TabBoatList extends Tab {
	BoatListModel model;
	protected BoatListDTO selectedBoat;
	protected ControlBox controlBox;
	protected ArrayList<SettingsCheckBox> checkBoxes = new ArrayList<>();
	protected RadioHBox selectedRadioBox;
	protected Text numberOfRecords;
	protected VBox boatDetailsBox;
	protected boolean isActiveSearch;
	protected TextField textField = new TextField();
	protected TableView<BoatListDTO> boatListTableView;

	public TabBoatList(String text) {
		super(text);
		this.model = new BoatListModel();
		this.boatListTableView = new BoatListTableView(this);
		this.controlBox = new ControlBox(this);
		setContent(createContentBox(createSplitScreenBox()));
	}

	private Node createContentBox(Node hBox) {
		VBox vBox = new VBox();
		vBox.setId("box-blue");
		vBox.setPadding(new Insets(10,10,10,10));
		vBox.getChildren().add(hBox);
		return vBox;
	}

	private Node createSplitScreenBox() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(boatListTableView, controlBox);
		VBox.setVgrow(hBox, Priority.ALWAYS);
		return hBox;
	}

	public BoatListModel getModel() {
		return model;
	}
}
