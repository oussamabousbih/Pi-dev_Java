/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.DossierMedical;
import entities.Ficheconsultation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CRUDfiche;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AfficheListFicheController implements Initializable {

    @FXML
    private ListView<Ficheconsultation> fxlistview;
    @FXML
    private Button fxmodifFiche;
    @FXML
    private Button fxsuppFiche;
    @FXML
    private TextField fxselectionF;
    
    private final ObservableList<Ficheconsultation> fiches = FXCollections.observableArrayList();
    private int SelectedIndex = -1;
    @FXML
    private Button fxhome;
    @FXML
    private Button fxajout;
    @FXML
    private Button fxafficheList;
    @FXML
    private Button fxtrier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       System.out.println("initialize method called");
    fxlistview.setItems(fiches);
    fxlistview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        selectionFiche(null);
    }
});
    }
    
    public void setFiches(List<Ficheconsultation> fiches) {
        System.out.println("setDossiers method called");
        fxlistview.getItems().clear();
        fxlistview.getItems().addAll(fiches);
    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
         if (event.getClickCount() == 1) { // Vérifier si l'utilisateur a cliqué une fois
        Ficheconsultation selectedDossier = fxlistview.getSelectionModel().getSelectedItem(); // Obtenir le dossier sélectionné
        if (selectedDossier != null) { // Vérifier si un dossier est sélectionné
            fxselectionF.setText(selectedDossier.toString()); // Mettre à jour le champ de texte avec le dossier sélectionné
        }
    }
    }
    
    @FXML
    private void selectionFiche(ActionEvent event) {
    System.out.println("selectionDossier method called");
    String selectedFiche = "";
    if (!fxlistview.getItems().isEmpty()) {
        selectedFiche = fxlistview.getSelectionModel().getSelectedItem().toString();
    }
    SelectedIndex = fxlistview.getSelectionModel().getSelectedIndex();
    fxselectionF.setText(selectedFiche);
}

    @FXML
    private void modifierfiche(ActionEvent event) {
        System.out.println("AfficheListController : modifierFiche method called");
        if (SelectedIndex >= 0) {
            Ficheconsultation fiche = fiches.get(SelectedIndex);
            int id = fiche.getId();
            System.out.println("AfficheListController : Modification du fiche avec l'ID " + id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierFiche.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ModifierFicheController ModifierFicheControllerController = loader.getController();
            ModifierFicheControllerController.setFiche(fiche);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            fiches.set(SelectedIndex, ModifierFicheControllerController.getFiche());
            fxlistview.getSelectionModel().clearSelection();
            fxselectionF.setText("");
            SelectedIndex = -1;
        }
    }

    @FXML
    private void supprimerFiche(ActionEvent event) {
     if (SelectedIndex >= 0) {
        Ficheconsultation fiche = fiches.get(SelectedIndex); // Obtenir le dossier sélectionné
        int id = fiche.getId(); // Obtenir l'ID du dossier
        System.out.println("Suppression du dossier avec l'ID " + id);
        CRUDfiche crud = new CRUDfiche();
        crud.supprimerFiche(id); // Supprimer le dossier dans la base de données
        System.out.println("Le dossier avec l'ID " + id + " a été supprimé de la base de données");
        fiches.remove(SelectedIndex); // Supprimer le dossier de la liste
        System.out.println("Le dossier avec l'ID " + id + " a été supprimé de la liste");
        fxlistview.getSelectionModel().clearSelection(); // Effacer la sélection dans le ListView
        fxselectionF.setText(""); // Effacer le texte dans le champ de texte
        SelectedIndex = -1; // Réinitialiser l'index sélectionné
        System.out.println("La suppression du dossier avec l'ID " + id + " est terminée");
    }
    }

    @FXML
    private void orientationhomeFiche(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFiche.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(HomeFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void AjouterFiche(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutFiche.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AjoutFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void afficheList(ActionEvent event) {
          CRUDfiche CF = new CRUDfiche ();
    List<Ficheconsultation> fiches = CF.afficherFiche();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheListFiche.fxml"));
    try {
        Parent root = loader.load();
        AfficheListFicheController ALCF = loader.getController();
        ALCF.setFiches(fiches);
        Scene scene = new Scene(root);
        
        // Récupérer la scène actuelle pour la remplacer par la nouvelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace(); // afficher les détails de l'erreur dans la console
    Logger.getLogger(AfficheListFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }

  private void trierDossiersParNom() {
    // Créer une copie de la liste originale
    List<Ficheconsultation> copieFiches = new ArrayList<>(fiches);
    // Trier la copie en utilisant un comparateur
    copieFiches.sort(Comparator.comparing(Ficheconsultation::getFirstName));
    // Mettre à jour la liste originale avec la liste triée
    fiches.setAll(copieFiches);
    // Effacer la sélection dans le ListView et le champ de texte
    fxlistview.getSelectionModel().clearSelection();
    fxselectionF.setText("");
    SelectedIndex = -1;
}
    @FXML
    private void trierparNom(ActionEvent event) {
        fiches.sort(Comparator.comparing(Ficheconsultation::getFirstName));
    }
        
    }


   

