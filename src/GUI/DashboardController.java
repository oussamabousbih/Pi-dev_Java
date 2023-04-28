/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.TypeService;
import Services.CRUDTypeService;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yasmine Rajhi
 */
public class DashboardController implements Initializable {

    @FXML
    private Button btn_overview;
    @FXML
    private Button btn_service;
    @FXML
    private Button btn_types;
    @FXML
    private LineChart<String, Number> chart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Create a new series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Get the data from the database
        CRUDTypeService crud_ts = new CRUDTypeService();
        List<TypeService> types = crud_ts.showTypesServices();

        // Count the number of services for each type
        Map<String, Integer> counts = new HashMap<>();
        for (TypeService type : types) {
            String typeName = type.getNomType();
            counts.put(typeName, counts.getOrDefault(typeName, 0) + 1);
        }

        // Add the data to the series
        counts.forEach((typeName, count) -> {
            series.getData().add(new XYChart.Data<>(typeName, count));
        });

        // Set the series name and add it to the chart
        series.setName("Number of Services per Type");
        chart.getData().add(series);
    }    

    @FXML
    private void service_page(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServicesTables.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_service.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }

    @FXML
    private void type_page(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TypesTables.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_types.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }
    
}
