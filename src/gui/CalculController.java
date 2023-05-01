/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.DossierMedical;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CRUDdossier;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class CalculController implements Initializable {

    @FXML
    private TextField fxtaille;
    @FXML
    private TextField fxpoids;
    @FXML
    private Label fxmessage;
    @FXML
    private Button fxcalculcal;
    @FXML
    private TextField fxage;
    @FXML
    private TextField fxtaillecal;
    @FXML
    private TextField fxpoidcal;
    @FXML
    private ComboBox<String> fxsexe;
    @FXML
    private ComboBox<String> fxniveau;
    @FXML
    private Label fxmessagecal;
    
    private final ObservableList<String> genderList = FXCollections.observableArrayList("Masculin", "Féminin");
    private final ObservableList<String> activityLevelList = FXCollections.observableArrayList("Sédentaire", "Léger", "Modéré", "Actif", "Très actif");
    @FXML
    private Button fxhome;
    @FXML
    private Button fxafficheList;
    @FXML
    private Button fxajout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxsexe.setItems(genderList);
        fxniveau.setItems(activityLevelList);
    }    

    @FXML
    private void calculIMC(ActionEvent event) {
       
        try {
            double taille = Double.parseDouble(fxtaille.getText());
            double poids = Double.parseDouble(fxpoids.getText());
            double imc = poids / (taille * taille);
            
            String message = "";
            if (imc < 16.5) {
                message = "Dénutrition ou anorexie";
            } else if (imc < 18.5) {
                message = "Maigreur";
            } else if (imc < 25) {
                message = "Corpulence normale";
            } else if (imc < 30) {
                message = "Surpoids";
            } else if (imc < 35) {
                message = "Obésité modérée";
            } else if (imc < 40) {
                message = "Obésité sévère";
            } else {
                message = "Obésité morbide ou massive";
            }
            
            fxmessage.setText("Votre IMC est de " + String.format("%.2f", imc) + " : " + message);
        } catch (NumberFormatException e) {
            fxmessage.setText("Veuillez entrer des valeurs numériques valides pour le poids et la taille.");
        }
    }

    @FXML
    private void calculcalories(ActionEvent event) {
    try {
        double height = Double.parseDouble(fxtaillecal.getText());
        double weight = Double.parseDouble(fxpoidcal.getText());
        int age = Integer.parseInt(fxage.getText());
        String gender = fxsexe.getValue();
        String activityLevel = fxniveau.getValue();

        // Vérification pour s'assurer que tous les champs ont été remplis
        if (height <= 0 || weight <= 0 || age <= 0 || gender == null || activityLevel == null) {
            fxmessagecal.setText("Veuillez remplir tous les champs avec des valeurs numériques valides.");
            return;
        }

        double bmr;
        if (gender.equals("Masculin")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        double activityMultiplier;
        switch (activityLevel) {
            case "Sédentaire":
                activityMultiplier = 1.2;
                break;
            case "Léger":
                activityMultiplier = 1.375;
                break;
            case "Modéré":
                activityMultiplier = 1.55;
                break;
            case "Actif":
                activityMultiplier = 1.725;
                break;
            case "Très actif":
                activityMultiplier = 1.9;
                break;
            default:
                activityMultiplier = 1.0;
                break;
        }

        double calories = bmr * activityMultiplier;
        fxmessagecal.setText("Votre apport calorique quotidien est de : " + String.format("%.2f", calories) + " calories.");

    } catch (NumberFormatException e) {
        fxmessagecal.setText("Veuillez entrer des valeurs numériques valides pour tous les champs.");
    }
}

    @FXML
    private void orientationhome(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }


    @FXML
    private void afficheList(ActionEvent event) {
        try {
         CRUDdossier CD = new CRUDdossier();
         List<DossierMedical> dossiers = CD.afficherDossier();
        // Charger le fichier FXML de l'interface graphique d'affichage des dossiers
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheList.fxml"));
        Parent root = loader.load();
        AfficheListController ALC = loader.getController();
        ALC.setDossiers(dossiers);
        
        // Créer une nouvelle scène pour afficher l'interface graphique
        Scene scene = new Scene(root);
        
        // Récupérer la scène actuelle pour la remplacer par la nouvelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AfficheListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void ajouterDossier(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutDossier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AfficheListController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
}

        
    
    

