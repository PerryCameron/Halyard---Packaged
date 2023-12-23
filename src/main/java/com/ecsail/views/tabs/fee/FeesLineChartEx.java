package com.ecsail.views.tabs.fee;

import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.dto.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.math.BigDecimal;
import java.util.Comparator;

public class FeesLineChartEx extends LineChart<String, Number> {
	ObservableList<FeeDTO> feeDTOS;
	InvoiceRepository invoiceRepository;
	public FeesLineChartEx(TabFee tabFee) {
	super(new CategoryAxis(), new NumberAxis());
		this.getXAxis().setAnimated (false);
		this.getYAxis().setAnimated (false);
	this.feeDTOS = FXCollections.observableArrayList();
	this.invoiceRepository = tabFee.getInvoiceRepository();
	}
	
	public void refreshChart(String description) {
		feeDTOS.clear();
		if(this.getData().size() > 0)
		this.getData().clear();
		feeDTOS = FXCollections.observableArrayList(invoiceRepository.getAllFeesByDescription(description));
		feeDTOS.sort(Comparator.comparing(FeeDTO::getFeeYear));
		Series<String,Number> series = new Series<>();
		populateChart(description, series);
	}
	
	public void populateChart(String description, Series<String, Number> series) {
		this.getData().add(series);
		for (FeeDTO d: feeDTOS) {
        		series.getData().add(new Data<>(d.getFeeYear() + "", new BigDecimal(d.getFieldValue())));
		}
		series.setName(description);
		setTitle(description + " by Year");
	}
}

