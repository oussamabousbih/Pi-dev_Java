/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.DossierMedical;
import java.awt.Font;
import static java.awt.SystemColor.text;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private TextField fxrecherche;
    @FXML
    private Button fxtelecharger;
    @FXML
    private Button fxhome;
    @FXML
    private Button fxsuivie;
    @FXML
    private Button fxAjout;
    @FXML
    private Button fxtrier;
   

    /**
     * Initializes the controller class.
     */
   @Override
public void initialize(URL url, ResourceBundle rb) {
    System.out.println("initialize method called");
    //dossiers.addAll(new CRUDdossier().getAllDossiers());
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
   
   private void rechercherDossier() {
    String searchText = fxrecherche.getText().toLowerCase(); // Convertir le texte en minuscules pour une recherche insensible à la casse
    ObservableList<DossierMedical> filteredList = FXCollections.observableArrayList();
    for (DossierMedical dossier : dossiers) {
        if (dossier.getFirstName().toLowerCase().contains(searchText) || dossier.getLastName().toLowerCase().contains(searchText)) {
            filteredList.add(dossier);
        }
    }
    fxlistview.setItems(filteredList);
}


    @FXML
    private void handleKeyReleased(KeyEvent event) {
        rechercherDossier();
    }

    @FXML
private void telechargerdossier(ActionEvent event) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedDirectory = directoryChooser.showDialog(null);
    if (selectedDirectory != null) {
        try {
            DossierMedical selectedDossier = fxlistview.getSelectionModel().getSelectedItem();
            String fileName = selectedDossier.getFirstName() + ".pdf";
            File file = new File(selectedDirectory.getAbsolutePath() + "\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();
            com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 20, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontData = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 16, com.itextpdf.text.Font.NORMAL);
            Paragraph title = new Paragraph("Dossier médical de " + selectedDossier.getLastName() + " " + selectedDossier.getFirstName(), fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Nom: " + selectedDossier.getLastName(), fontData));
            document.add(new Paragraph("Prénom: " + selectedDossier.getFirstName(), fontData));
            document.add(new Paragraph("Date_naissance: " + selectedDossier.getDate_naissance(), fontData));
            document.add(new Paragraph("Email: " + selectedDossier.getEmail(), fontData));
            document.add(new Paragraph("Vaccins: " + selectedDossier. getVaccins(), fontData));
            document.add(new Paragraph("Maladies: " + selectedDossier.getMaladies(), fontData));
            document.add(new Paragraph("Allergies: " + selectedDossier.getAllergies(), fontData));
             document.add(new Paragraph("Analyses: " + selectedDossier.getAnalyses(), fontData));
              document.add(new Paragraph("Intervention_chirurgicale: " + selectedDossier.getIntervention_chirurgicale(), fontData));
            //com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 16, com.itextpdf.text.Font.NORMAL);
            //Chunk chunk = new Chunk(selectedDossier.toString(), font);
           
            //document.add(chunk);
            document.close();
            fos.close();
            System.out.println("Le fichier PDF a été téléchargé avec succès dans " + selectedDirectory.getAbsolutePath());
        } catch (IOException | DocumentException e) {
            System.err.println("Une erreur est survenue lors du téléchargement du fichier PDF");
            e.printStackTrace();
        }
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
    private void suivreSanté(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Calcul.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CalculController.class.getName()).log(Level.SEVERE, null, ex);
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

   
private void trierDossiersParNom() {
    // Créer une copie de la liste originale
    List<DossierMedical> copieDossiers = new ArrayList<>(dossiers);
    // Trier la copie en utilisant un comparateur
    copieDossiers.sort(Comparator.comparing(DossierMedical::getFirstName));
    // Mettre à jour la liste originale avec la liste triée
    dossiers.setAll(copieDossiers);
    // Effacer la sélection dans le ListView et le champ de texte
    fxlistview.getSelectionModel().clearSelection();
    fxselectionDossier.setText("");
    SelectedIndex = -1;
}
    @FXML
    private void trierparNom(ActionEvent event) {
        dossiers.sort(Comparator.comparing(DossierMedical::getFirstName));
    }
    }




   

    

