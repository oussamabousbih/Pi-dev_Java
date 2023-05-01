/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import services.CRUDArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import services.CRUDCommentaire;
/**
 * FXML Controller class
 *
 * @author mazee
 */
public class StatArticleController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private VBox root2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CRUDArticle art = new CRUDArticle();
        String NbrArticle = art.statArticle();
        String TodayArticle = art.statArticleToday();
        PieChart pieChart = new PieChart();
ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
    new PieChart.Data("Total Articles", Integer.parseInt(NbrArticle)),
    new PieChart.Data("Today's Articles", Integer.parseInt(TodayArticle))
);
pieChart.setData(pieChartData);
pieChart.setTitle("Article Statistics");

// Enable label visibility and set the label format
pieChart.setLabelLineLength(10);
pieChart.setLegendVisible(true);
pieChart.setLabelsVisible(true);
pieChart.getData().forEach(data -> data.nameProperty().setValue(
        String.format("%s : %d", data.getName(), (int) data.getPieValue())));

root.getChildren().add(pieChart);


        CRUDCommentaire cmnt = new CRUDCommentaire();
        String NbrComment = cmnt.statCommentaire();
        String TodayComment = cmnt.statCommentToday();
        PieChart pieChart2 = new PieChart();
ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(
    new PieChart.Data("Total Comments", Integer.parseInt(NbrComment)),
    new PieChart.Data("Today's Comments", Integer.parseInt(TodayComment))
);
pieChart2.setData(pieChartData2);
pieChart2.setTitle("Comments Statistics");

// Enable label visibility and set the label format
pieChart2.setLabelLineLength(10);
pieChart2.setLegendVisible(true);
pieChart2.setLabelsVisible(true);
pieChart2.getData().forEach(data -> data.nameProperty().setValue(
        String.format("%s : %d", data.getName(), (int) data.getPieValue())));

root2.getChildren().add(pieChart2);
            }    
    
}
