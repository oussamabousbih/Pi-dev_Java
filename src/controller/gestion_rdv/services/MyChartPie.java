/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv.services;


import static javafx.application.Application.STYLESHEET_MODENA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;

import javafx.scene.chart.PieChart;

/**
 *
 * @author rbaih
 */
public class MyChartPie {
    
    
    private PieChart pieChart ;
    private ObservableList<PieChart.Data> observableData;

    public MyChartPie( PieChart pieChart  , ObservableList<PieChart.Data> observableData){
        this.pieChart =pieChart;
        this.observableData=observableData;

    }
    
    
    public void generateChart(){

        pieChart.setTitle("Expenses");

        // Create the initial data for the chart
        observableData = FXCollections.observableArrayList(
                new PieChart.Data("Rent", 1000),
                new PieChart.Data("Food", 500),
                new PieChart.Data("Transportation", 200),
                new PieChart.Data("Utilities", 300)
        );
        pieChart.setData(observableData);
        
        
        pieChart.setLabelLineLength(12);
        pieChart.setLabelsVisible(false);
        pieChart.setTitleSide(Side.BOTTOM);
        pieChart.setStyle(STYLESHEET_MODENA);
        pieChart.setAnimated(true);
        pieChart.setMaxSize(600, 800);

        
     

    }
    
   
    

}
