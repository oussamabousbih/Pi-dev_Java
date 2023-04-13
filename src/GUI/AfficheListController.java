/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.DossierMedical;
import static java.awt.SystemColor.text;
import java.awt.event.MouseEvent;
import java.io.File;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import services.CRUDdossier;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AfficheListController implements Initializable {

    @FXML
    private ListView<DossierMedical> fxlistview;
    @FXML
    private TextField fxselectionDossier;
    @FXML
    private Button fxmodifier;
    @FXML
    private Button fxsupprimer;
    
    private final ObservableList<DossierMedical> dossiers = FXCollections.observableArrayList();
    private int SelectedIndex = -1;
   

    /**
     * Initializes the controller class.
     */
    @Override
  public void initialize(URL url, ResourceBundle rb) {
    System.out.println("initialize method called");
    fxlistview.setItems(dossiers);
    fxlistview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        selectionDossier(null);
    }
});
}
    
    public void setDossiers(List<DossierMedical> dossiers) {
    System.out.println("setDossiers method called");
    fxlistview.getItems().clear();
    fxlistview.getItems().addAll(dossiers);
}

    
     @FXML
 private void selectionDossier(javafx.scene.input.MouseEvent event) {
    System.out.println("selectionDossier method called");
    String selectedDossier = "";
    if (!fxlistview.getItems().isEmpty()) {
        selectedDossier = fxlistview.getSelectionModel().getSelectedItem().toString();
    }
    SelectedIndex = fxlistview.getSelectionModel().getSelectedIndex();
    fxselectionDossier.setText(selectedDossier);
}

   
    @FXML
    private void modifierDossier(ActionEvent event) {
        System.out.println("AfficheListController : modifierDossier method called");
        if (SelectedIndex >= 0) {
            DossierMedical dossier = dossiers.get(SelectedIndex);
            int id = dossier.getId();
            System.out.println("AfficheListController : Modification du dossier avec l'ID " + id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierDossier.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ModifierDossierController modifierDossierController = loader.getController();
            modifierDossierController.setDossier(dossier);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            dossiers.set(SelectedIndex, modifierDossierController.getDossier());
            fxlistview.getSelectionModel().clearSelection();
            fxselectionDossier.setText("");
            SelectedIndex = -1;
        }
    }

    @FXML
   private void supprimerDossier(ActionEvent event) {
    if (SelectedIndex >= 0) {
        DossierMedical dossier = dossiers.get(SelectedIndex); // Obtenir le dossier sélectionné
        int id = dossier.getId(); // Obtenir l'ID du dossier
        System.out.println("Suppression du dossier avec l'ID " + id);
        CRUDdossier crud = new CRUDdossier();
        crud.supprimerDossier(id); // Supprimer le dossier dans la base de données
        System.out.println("Le dossier avec l'ID " + id + " a été supprimé de la base de données");
        dossiers.remove(SelectedIndex); // Supprimer le dossier de la liste
        System.out.println("Le dossier avec l'ID " + id + " a été supprimé de la liste");
        fxlistview.getSelectionModel().clearSelection(); // Effacer la sélection dans le ListView
        fxselectionDossier.setText(""); // Effacer le texte dans le champ de texte
        SelectedIndex = -1; // Réinitialiser l'index sélectionné
        System.out.println("La suppression du dossier avec l'ID " + id + " est terminée");
    }
}



    @FXML
   private void handleMouseClicked(javafx.scene.input.MouseEvent event) {
    if (event.getClickCount() == 1) { // Vérifier si l'utilisateur a cliqué une fois
        DossierMedical selectedDossier = fxlistview.getSelectionModel().getSelectedItem(); // Obtenir le dossier sélectionné
        if (selectedDossier != null) { // Vérifier si un dossier est sélectionné
            fxselectionDossier.setText(selectedDossier.toString()); // Mettre à jour le champ de texte avec le dossier sélectionné
        }
    }
}
   

   

    
}
