package com.ecsail.views.tabs.welcome;

import com.ecsail.dto.StatsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;

import java.util.ArrayList;
import java.util.Arrays;

public class MembershipStackedBarChart extends StackedBarChart<String,Number> {

	ArrayList<StatsDTO> stats;
	ObservableList<Data<String,Number>> familyData = FXCollections.observableArrayList();
	ObservableList<Data<String,Number>> regularData = FXCollections.observableArrayList();
	ObservableList<Data<String,Number>> socialData = FXCollections.observableArrayList();
	ObservableList<Data<String,Number>> lakeAssociateData = FXCollections.observableArrayList();
	ObservableList<Data<String,Number>> lifeMemberData = FXCollections.observableArrayList();

	Series<String,Number> seriesFamily = new Series<>();
	Series<String,Number> seriesRegular = new Series<>();
	Series<String,Number> seriesSocial = new Series<>();
	Series<String,Number> seriesLakeAssociate = new Series<>();
	Series<String,Number> seriesLifeMember = new Series<>();

	public MembershipStackedBarChart(ArrayList<StatsDTO> stats, CategoryAxis xAxis, NumberAxis yAxis) {
		super(xAxis,yAxis);
//		super(new CategoryAxis(), xAxis);

		this.stats = stats;
	        setTitle("Active Memberships By Year");
			xAxis.setAutoRanging(true);
			setNames();
			addData();

			setAnimated(false);
		getData().addAll(Arrays.asList(seriesFamily,seriesRegular,seriesSocial,seriesLakeAssociate,seriesLifeMember));
	}

	public void setNames() {
		seriesFamily.setName("Family");
		seriesRegular.setName("Regular");
		seriesSocial.setName("Social");
		seriesLakeAssociate.setName("Lake Associate");
		seriesLifeMember.setName("Life Member");
	}

	public void addData() {
		for (StatsDTO s: stats) {
			familyData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getFamily()));
			regularData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getRegular()));
			socialData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getSocial()));
			lakeAssociateData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getLakeAssociates()));
			lifeMemberData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getLifeMembers()));
		}
		setData();
	}

	private void setData() {
		seriesFamily.setData(familyData);
		seriesRegular.setData(regularData);
		seriesSocial.setData(socialData);
		seriesLakeAssociate.setData(lakeAssociateData);
		seriesLifeMember.setData(lifeMemberData);
	}

	public void clearData() {
			familyData.clear();
			regularData.clear();
			socialData.clear();
			lakeAssociateData.clear();
			lifeMemberData.clear();
	}

	public void refreshChart() {
		clearData();
		addData();
		setData(FXCollections.observableArrayList(seriesFamily,seriesRegular,seriesSocial,seriesLakeAssociate,seriesLifeMember));
	}
}
