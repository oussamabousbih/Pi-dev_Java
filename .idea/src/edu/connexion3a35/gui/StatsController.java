package edu.connexion3a35.gui;

import edu.connexion3a35.utils.MyConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatsController implements Initializable {

    @FXML
    private PieChart pieChart;

    Connection connexion;
    Statement stm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            afficherPie();
        } catch (SQLException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public StatsController() {
        connexion = MyConnection.getInstance().getCnx();
    }
    public void afficherPie() throws SQLException{
        int infticketsCount=0 ;
        int VIPticketsCount=0 ;
        int StandardticketsCount=0 ;
        String req = "SELECT COUNT(*) AS count FROM tickets WHERE type='VIP' ";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            infticketsCount = rst.getInt(1);
        }
        PieChart.Data qtr1= new PieChart.Data("TYPE VIP", +infticketsCount) ;
        String req1 = "SELECT COUNT(*) AS count FROM tickets WHERE type='VIP' ";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst1 = stm.executeQuery(req1);

        while (rst1.next()) {
            VIPticketsCount = rst1.getInt(1);
        }
        PieChart.Data qtr2= new PieChart.Data("TYPE Standard", +VIPticketsCount) ;
        String req2 = "SELECT COUNT(*) AS count FROM tickets WHERE type='Standard' ";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst2 = stm.executeQuery(req2);

        pieChart.getData().addAll(qtr1,qtr2) ;

    }
}
