package com.ecsail.gui.tabs.fee;

import com.ecsail.sql.select.SqlFee;
import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FeesLineChartEx extends LineChart<String, Number> {
	ObservableList<FeeDTO> Fees;


	public FeesLineChartEx() {
	super(new CategoryAxis(), new NumberAxis());
	Series<String,Number> series = new Series<String, Number>();
	this.Fees = FXCollections.observableArrayList();
	}
	
	public void refreshChart(String description) {
		Fees.clear();
		this.getData().clear();
		Fees = SqlFee.getAllFeesByDescription(description);
		Collections.sort(Fees, Comparator.comparing(FeeDTO::getFeeYear));
		Series<String,Number> series = new Series<String, Number>();
		populateChart(description, series);

	}
	
	public void populateChart(String description, Series<String, Number> series) {
		getData().addAll(Arrays.asList(series));
		for (FeeDTO d: Fees) {
        		series.getData().add(new Data<String, Number>(d.getFeeYear() + "", d.getFieldValue()));
		}
		series.setName(description);
		setTitle(description + " by Year");
	}

}

