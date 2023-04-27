package edu.connexion3a35.gui;

import edu.connexion3a35.utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatsController implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private BarChart<?, ?> barChart;
    private Stage stage;
    private Scene scene;
    private Parent root;

    java.sql.Connection connexion = MyConnection.getInstance().getCnx();



    public void chart(){
        String chartSql = "SELECT marque, SUM(quantite_produit) AS total FROM produit GROUP BY marque ORDER BY marque ASC LIMIT 4";
        connexion = MyConnection.getInstance().getCnx();
        try{
            XYChart.Series chartData = new XYChart.Series();
            PreparedStatement k=connexion.prepareStatement(chartSql);
            ResultSet rs=k.executeQuery(chartSql);
            while(rs.next()){
                chartData.getData().add(new XYChart.Data(rs.getString(1),rs.getInt(2)));
            }
            barChart.getData().add(chartData);

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart();





    }

    public void retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Affichage_Produit.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}

