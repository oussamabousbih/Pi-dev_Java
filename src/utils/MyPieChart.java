/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author rbaih
 */
import java.util.Collection;
import static javafx.application.Application.STYLESHEET_MODENA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;

import javafx.scene.chart.PieChart;

public class MyPieChart {

    private PieChart pieChart;
    private ObservableList<PieChart.Data> observableData;

    private void defaultStyling() {

        // Set the CSS style for the label and the legend
        pieChart.setStyle(
                ".chart-legend {\n"
                + "    -fx-background-color: white;\n"
                + "    -fx-border-color: black;\n"
                + "    -fx-border-width: 1px;\n"
                + "}\n"
                + ".pie-label-line {\n"
                + "    -fx-stroke: black;\n"
                + "}\n"
                + ".chart-legend-item > .symbol {\n"
                + "    -fx-background-color: black;\n"
                + "}\n"
                + ".chart-pie-label {\n"
                + "    -fx-fill: black;\n"
                + "}\n"
                + ".pie-chart {\n"
                + "    -fx-background-color: turquoise;\n"
                + "}"
                + ".pie-chart .chart-legend {"
                + "-fx-background-color: #ffffff66;"
                + "-fx-padding: 5px;"
                + "}"
                + ".pie-chart .chart-legend-item > .symbol {"
                + " -fx-background-color: #000000;"
                + "}"
                + ".pie-chart .chart-legend-item > .text {"
                + "-fx-text-fill: black;"
                + "}"
        );

        pieChart.setLegendVisible(true);
        pieChart.setLabelLineLength(8);
        pieChart.setLabelsVisible(true);
        pieChart.setTitleSide(Side.TOP);
//        pieChart.setStyle(STYLESHEET_MODENA);
        pieChart.setAnimated(true);
        pieChart.setMaxSize(800, 600);
    }

    public MyPieChart(PieChart pieChart, String title) {
        // Create the pie chart
        this.pieChart = pieChart;
        pieChart.setTitle(title);
        observableData = FXCollections.observableArrayList();

        defaultStyling();

        pieChart.setData(observableData);
    }

    public MyPieChart(PieChart pieChart, String title, Collection<PieChart.Data> collectionData) {
        // Create the pie chart
        this.pieChart = pieChart;
        pieChart.setTitle(title);
        observableData = FXCollections.observableArrayList(collectionData);

        defaultStyling();
        pieChart.setData(observableData);
    }

    public void setPieChartData_byObservableList(PieChart.Data... data_toshow) {

        // Create the initial data for the chart
        for (PieChart.Data data : data_toshow) {
            observableData.add(data);
        }

        pieChart.setData(observableData);

    }

    public void setPieChartData_byObservableList(Collection<PieChart.Data> collectionData) {

        // Create the initial data for the chart
        for (PieChart.Data data : collectionData) {
            observableData.add(data);
        }

        pieChart.setData(observableData);

    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public ObservableList<PieChart.Data> getObservableData() {
        return observableData;
    }

    public void setObservableData(ObservableList<PieChart.Data> observableData) {
        this.observableData = observableData;
    }

}
