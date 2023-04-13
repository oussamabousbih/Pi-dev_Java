/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Ficheconsultation;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
}

   

