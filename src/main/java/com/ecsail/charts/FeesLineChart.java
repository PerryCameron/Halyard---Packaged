package com.ecsail.charts;

import com.ecsail.structures.DefinedFeeDTO;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Arrays;

public class FeesLineChart extends LineChart<String, Number> {
	ObservableList<DefinedFeeDTO> definedFees;


	public FeesLineChart(ObservableList<DefinedFeeDTO> definedFees) {
	super(new CategoryAxis(), new NumberAxis());
	Series<String,Number> series = new Series<String, Number>();
	this.definedFees = definedFees;
		populateChart("Initiation Fee", series);

	}
	
	public void refreshChart(String type) {
//		seriesDues.getData().clear();
		this.getData().clear();
//		System.out.println("cleared size=" + seriesDues.getData().size());
		Series<String,Number> series = new Series<String, Number>();
		populateChart(type, series);

	}
	
	public void populateChart(String type, Series<String, Number> series) {
//		Series<String,Number> series = new Series<String, Number>();
//		Series<String,Number> seriesNewMembers = new Series<String, Number>();
//		Series<String,Number> seriesReturnMembers = new Series<String, Number>();

//		seriesNewMembers.setName("New");
//		seriesReturnMembers.setName("Return");
		getData().addAll(Arrays.asList(series));
		for (DefinedFeeDTO d: definedFees) {
			if(type.equals("Regular Dues"))
        		series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_regular()));
			if(type.equals("Family Dues"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_family()));
			if(type.equals("Lake Associate Dues"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_lake_associate()));
			if(type.equals("Social Membership Dues"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_social()));
			if(type.equals("Initiation Fee"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getInitiation()));
			if(type.equals("Wetslip"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWet_slip()));
			if(type.equals("Beach Parking"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getBeach()));
			if(type.equals("Winter Storage"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWinter_storage()));
			if(type.equals("Extra Gate Key Fee"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getMain_gate_key()));
			if(type.equals("Sail Loft Access"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getSail_loft()));
			if(type.equals("Sail Loft Key"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getSail_loft_key()));
			if(type.equals("Sail School Loft Access"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getSail_school_laser_loft()));
			if(type.equals("Sail School Loft Key"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getSail_school_loft_key()));
			if(type.equals("Kayak Rack Fee"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getKayak_rack()));
			if(type.equals("Kayak Inside Storage"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getKayak_shed()));
			if(type.equals("Kayak Inside Storage Key"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getKayak_shed_key()));
			if(type.equals("Work Credit Amount"))
				series.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWork_credit()));

		}
		series.setName(type);
		setTitle(type + " by Year");
	}

}

