/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.gui;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.services.CategorieCrud;
import edu.connexion3a35.services.SettingUpListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class Categories_affichage_Controller implements Initializable {

    int selectedindex;
    @FXML
    private Label label_msg;

    private Categorie cat;

    public Label getLabel_msg() {
        return label_msg;
    }

    public void setLabel_msg(Label label_msg) {
        this.label_msg = label_msg;
    }

    public Categorie getCat() {
        return cat;
    }
    public int x;

    public void setCat(Categorie cat) {
        this.cat = cat;
    }

    public Button getId_test() {
        return id_test;
    }

    public void setId_test(Button id_test) {
        this.id_test = id_test;
    }

    List<Categorie> listcategorie = new ArrayList<>();

    @FXML
    private Button id_test;

    public List<Categorie> getListcategorie() {
        return listcategorie;
    }

    public void setListcategorie(List<Categorie> listcategorie) {
        this.listcategorie = listcategorie;
    }

    public ListView<Categorie> getList_view_cat() {
        return list_view_cat;
    }

    public void setList_view_cat(ListView<Categorie> list_view_cat) {
        this.list_view_cat = list_view_cat;
    }

    @FXML
    private ListView<Categorie> list_view_cat;

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button id_modifier;

    @FXML
    private Button id_supprimer;

    public Button getId_modifier() {
        return id_modifier;
    }

    public void setId_modifier(Button id_modifier) {
        this.id_modifier = id_modifier;
    }

    public Button getId_supprimer() {
        return id_supprimer;
    }

    public void setId_supprimer(Button id_supprimer) {
        this.id_supprimer = id_supprimer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        id_modifier.setVisible(false);
        id_supprimer.setVisible(false);
        CategorieCrud cr = new CategorieCrud();
        this.setListcategorie(cr.listeDesEntites());
        //<Event> because i have a button that Does NOt accept <MouseEvent>
        EventHandler<MouseEvent> customEvent = (event2) -> {
            // storing data to pass on UserManagement
            if (event2.getClickCount() == 1) {
                setCat(this.getList_view_cat().getSelectionModel().getSelectedItem());
                id_modifier.setVisible(true);
                selectedindex = list_view_cat.getSelectionModel().getSelectedIndex();
                id_supprimer.setVisible(true);
                System.out.println("categories selected" + this.getCat());

            }
        };

        SettingUpListView.listObjectsOn_listView(list_view_cat, listcategorie, customEvent);

    }

    @FXML
    void delete_categorie(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous supprimer ?", ButtonType.APPLY,
                ButtonType.CANCEL);
        alert.setTitle("Information Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.APPLY) {
            new CategorieCrud().supprimerEntite(this.getCat().getId());
        }

        list_view_cat.getItems().remove(selectedindex);
    }
    @FXML
    private Button id_ajouter;

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Stage stage = (Stage) id_ajouter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Acceuil_page.fxml"));
        Parent root = loader.load();
        Acceuil_pageController categoriesController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void modify_cat(ActionEvent event) throws IOException {
        Stage stage = (Stage) list_view_cat.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyCategorie.fxml"));
        Parent root = loader.load();
        ModifyCategorieController categoriesController = loader.getController();

        categoriesController.getTf_nomcatmodif().setText(cat.getNom_Categorie());
        categoriesController.setCat(cat);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
