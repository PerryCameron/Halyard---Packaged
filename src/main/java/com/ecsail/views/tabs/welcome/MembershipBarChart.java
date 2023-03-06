package com.ecsail.views.tabs.welcome;

import com.ecsail.dto.StatsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;

import java.util.ArrayList;

public class MembershipBarChart extends BarChart<String,Number> {
    public ArrayList<StatsDTO> stats;
    int set;
    ObservableList<Data<String,Number>> nonRenewData = FXCollections.observableArrayList();
    ObservableList<Data<String,Number>> newMemberData = FXCollections.observableArrayList();
    ObservableList<Data<String,Number>> returnMemberData = FXCollections.observableArrayList();
    Series seriesData = new Series();
    public MembershipBarChart(Axis xAxis, Axis yAxis, ArrayList<StatsDTO> stats, int set) {
        super(xAxis, yAxis);
        this.stats = stats;
        this.set = set;
        setLegendVisible(false);
        setAnimated(false);
        setTitle("Non-Renewed Memberships");
        xAxis.setLabel("Years");

    addData();
    setSeriesData();
    getData().addAll(seriesData);
    }

    private void addData() {
        for (StatsDTO s: stats) {
            switch (set) {
                case 1:
                    nonRenewData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getNonRenewMemberships()));
                    break;
                case 2:
                    newMemberData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getNewMemberships()));
                    break;
                case 3:
                    returnMemberData.add(new Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getReturnMemberships()));
            }
        }
    }

    private void setSeriesData() {
        switch (set) {
            case 1:
                seriesData.setData(nonRenewData);
                setTitle("Non-Renewed Memberships");

                break;
            case 2:
                seriesData.setData(newMemberData);
                setTitle("New Memberships");
                changeSeriesColor("#860061");
                break;
            case 3:
                seriesData.setData(returnMemberData);
                setTitle("Return Memberships");
                changeSeriesColor("#22bad9");
        }
    }

    private void clearSeries() {
        seriesData.getData().clear();
    }

    public void changeData(int change) {
        this.set = change;
        clearSeries();
        addData();
        setSeriesData();
        setData(FXCollections.singletonObservableList(seriesData));
    }

    public void changeSeriesColor(String color) {
        for(int i = 0; i < stats.size(); i++) {
            for(Node n:lookupAll(".data"+i+".chart-bar")) {
               n.setStyle("-fx-bar-fill: "+color+";");
            }
        }
    }

    public void refreshChart() {
        changeData(set);
    }

}
