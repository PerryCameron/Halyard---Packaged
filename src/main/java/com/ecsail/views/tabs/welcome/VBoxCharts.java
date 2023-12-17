package com.ecsail.views.tabs.welcome;

import com.ecsail.HalyardPaths;
import com.ecsail.dto.StatsDTO;
import com.ecsail.sql.select.SqlStats;
import com.ecsail.views.dialogues.Dialogue_LoadNewStats;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;


public class VBoxCharts extends VBox {
    public static final int NON_RENEW = 1;
    public static final int NEW_MEMBER = 2;
    public static final int RETURN_MEMBER = 3;
    public ArrayList<StatsDTO> stats;
    int currentYear;
    int defaultStartYear;
    int defaultNumbOfYears = 20;
    int totalNumbOfYears;
    BooleanProperty dataBaseStatisticsRefreshed = new SimpleBooleanProperty(false);

    public VBoxCharts() {
        this.currentYear = Year.now().getValue();
        this.defaultStartYear = currentYear - 20;
        // todo I think this needs to be changed to reload stats
        this.stats = SqlStats.getStatistics(defaultStartYear, defaultStartYear + defaultNumbOfYears);
        // problem is that the object hasn't been created yet
//        reloadStats();
        this.totalNumbOfYears = SqlStats.getNumberOfStatYears();
//        final CategoryAxis xAxis = new CategoryAxis();
//        final NumberAxis yAxis = new NumberAxis();
        var membershipsByYearChart = new MembershipStackedBarChart(stats);
        var membershipBarChart = new MembershipBarChart(stats,1);
        var hBoxControlBar = new HBox();
        var vBoxCharts = new VBox();
        var refreshButton = new Button("Refresh Data");
        var comboBoxStartYear = new ComboBox<Integer>();
        var comboBoxYears = new ComboBox<Integer>();
        var comboBoxBottomChartSelection = new ComboBox<String>();

        var hBoxStart = new HBox();
        var hBoxStop = new HBox();
        var hBoxTop = new HBox();
        comboBoxBottomChartSelection.getItems().addAll("Non-Renew","New","Return");

        populateComboBoxWithYears(comboBoxStartYear);
        populateComboBoxWithNumberOfYears(comboBoxYears);
        this.setMinWidth(350);
        this.setMaxWidth(1400);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setPrefHeight(1200);
        comboBoxStartYear.setValue(defaultStartYear);
        comboBoxYears.setValue(defaultNumbOfYears +1);
        comboBoxBottomChartSelection.setValue("Non-Renew");
        hBoxControlBar.setPadding(new Insets(5,0,5,5));
//        comboBoxStartYear.setValue(defaultStartYear);
        hBoxStart.setSpacing(5);
        hBoxStop.setSpacing(5);
        hBoxStart.setAlignment(Pos.CENTER_LEFT);
        hBoxStop.setAlignment(Pos.CENTER_LEFT);
        hBoxControlBar.setSpacing(10);
        hBoxTop.setSpacing(5);
        hBoxTop.setAlignment(Pos.CENTER_LEFT);
        hBoxControlBar.setAlignment(Pos.CENTER);

        ///////// LISTENERS ////////////

        comboBoxBottomChartSelection.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            switch (newValue) {
                case "Non-Renew" -> membershipBarChart.changeData(NON_RENEW);
                case "New" -> membershipBarChart.changeData(NEW_MEMBER);
                case "Return" -> membershipBarChart.changeData(RETURN_MEMBER);
            }
        });

        comboBoxYears.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            defaultNumbOfYears = newValue;
            reloadStats();
            refreshCharts(membershipsByYearChart, membershipBarChart);
        });

        comboBoxStartYear.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            defaultStartYear = newValue;
            reloadStats();
            refreshCharts(membershipsByYearChart, membershipBarChart);
        });

        refreshButton.setOnAction((event)-> new Dialogue_LoadNewStats(dataBaseStatisticsRefreshed));

        // this waits for the database to update on another thread then refreshes the charts
        dataBaseStatisticsRefreshed.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                reloadStats();
                refreshCharts(membershipsByYearChart, membershipBarChart);
                setDataBaseStatisticsRefreshed(false);
            }
        });

        hBoxStart.getChildren().addAll(new Label("Start"),comboBoxStartYear);
        hBoxStop.getChildren().addAll(new Label("Year Span"),comboBoxYears);
        hBoxTop.getChildren().addAll(new Label("Bottom"),comboBoxBottomChartSelection);
        hBoxControlBar.getChildren().addAll(hBoxStart,hBoxStop,hBoxTop,refreshButton);
        vBoxCharts.getChildren().addAll(membershipsByYearChart,membershipBarChart);
        this.getChildren().addAll(vBoxCharts,hBoxControlBar);
    }

    private void refreshCharts(MembershipStackedBarChart membershipsByYearChart, MembershipBarChart membershipBarChart) {
        membershipsByYearChart.refreshChart();
        membershipBarChart.refreshChart();
    }

    private void reloadStats() {
        int endYear = defaultStartYear + defaultNumbOfYears;
        if(endYear > currentYear) endYear = currentYear;
        this.stats.clear();
        this.stats.addAll(SqlStats.getStatistics(defaultStartYear, endYear));
    }

    private void populateComboBoxWithYears(ComboBox<Integer> comboBox) {
        IntStream.rangeClosed(1969, currentYear - 10)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(comboBox.getItems()::add);
    }

    private void populateComboBoxWithNumberOfYears(ComboBox<Integer> comboBox) {
        IntStream.rangeClosed(10, totalNumbOfYears - 1)
                .forEach(comboBox.getItems()::add);
    }

    public void setDataBaseStatisticsRefreshed(boolean dataBaseStatisticsRefreshed) {
        this.dataBaseStatisticsRefreshed.set(dataBaseStatisticsRefreshed);
    }
}
