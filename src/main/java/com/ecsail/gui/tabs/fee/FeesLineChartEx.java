package com.ecsail.gui.tabs.fee;

import com.ecsail.sql.select.SqlFee;
import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.math.BigDecimal;
import java.util.Comparator;

public class FeesLineChartEx extends LineChart<String, Number> {
	ObservableList<FeeDTO> Fees;


	public FeesLineChartEx() {
	super(new CategoryAxis(), new NumberAxis());
		this.getXAxis().setAnimated (false);
		this.getYAxis().setAnimated (false);
	this.Fees = FXCollections.observableArrayList();
	}
	
	public void refreshChart(String description) {
		Fees.clear();
		if(this.getData().size() > 0)
		this.getData().clear();
		Fees = SqlFee.getAllFeesByDescription(description);
		Fees.sort(Comparator.comparing(FeeDTO::getFeeYear));
		Series<String,Number> series = new Series<>();
		populateChart(description, series);
	}
	
	public void populateChart(String description, Series<String, Number> series) {
		this.getData().add(series);
		for (FeeDTO d: Fees) {
        		series.getData().add(new Data<>(d.getFeeYear() + "", new BigDecimal(d.getFieldValue())));
		}
		series.setName(description);
		setTitle(description + " by Year");
	}

}

