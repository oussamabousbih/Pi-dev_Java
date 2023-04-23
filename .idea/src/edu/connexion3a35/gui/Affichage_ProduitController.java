/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.gui;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Produit;
import edu.connexion3a35.services.CategorieCrud;
import edu.connexion3a35.services.ProduitCrud;
import edu.connexion3a35.services.SettingUpListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author msi
 */
public class Affichage_ProduitController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    private ListView<Produit> list_view_produit;
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
    private Button id_modifier;

    @FXML
    private Button id_supprimer;

    List<Produit> listproduit = new ArrayList<>();
    List<Categorie> listcat = new ArrayList<>();
    private Produit produit;

    public ListView<Produit> getList_view_produit() {
        return list_view_produit;
    }

    public void setList_view_produit(ListView<Produit> list_view_produit) {
        this.list_view_produit = list_view_produit;
    }

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

    public List<Produit> getListproduit() {
        return listproduit;
    }

    public void setListproduit(List<Produit> listproduit) {
        this.listproduit = listproduit;
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getSelectedindex() {
        return selectedindex;
    }

    public void setSelectedindex(int selectedindex) {
        this.selectedindex = selectedindex;
    }

    int selectedindex;

    @FXML
    void delete_produit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous supprimer ?", ButtonType.APPLY,
                ButtonType.CANCEL);
        alert.setTitle("Information Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.APPLY) {
            new ProduitCrud().supprimerEntite(this.getProduit().getId());
        }

        list_view_produit.getItems().remove(selectedindex);
    }

    @FXML
    void modify_pro(ActionEvent event) throws IOException {
        Stage stage = (Stage) list_view_produit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProduit.fxml"));
        Parent root = loader.load();
        ModifyProduitController produitController = loader.getController();

        produitController.getTf_nomp().setText(produit.getNom_produit());
        produitController.getTf_prix().setText(String.valueOf(produit.getPrix()));
        produitController.getTf_quantite().setText(String.valueOf(produit.getQuantite()));
        produitController.getTd_desc().setText(produit.getDescription());
        produitController.getTf_image().setText(produit.getImage_produit());
        produitController.getTf_marque().setText(produit.getMarque());

        produitController.setProduit(produit);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        id_modifier.setVisible(false);
        id_supprimer.setVisible(false);
        Produit produit1 = new Produit();
        ProduitCrud pr = new ProduitCrud();
        this.setListproduit(pr.listeDes());

        EventHandler<MouseEvent> customEvent = (event2) -> {
            // storing data to pass on PRODUCTManagement
            if (event2.getClickCount() == 1) {
                Produit produit = this.getList_view_produit().getSelectionModel().getSelectedItem();
                setProduit(produit);
                id_modifier.setVisible(true);
                selectedindex = list_view_produit.getSelectionModel().getSelectedIndex();
                id_supprimer.setVisible(true);
                System.out.println("produit selected" + this.getProduit());

            }
        };

//list_view_produit.getItems().add(produit);
        //SettingUpListView.listObjectsOn_listView(list_view_produit, listproduit, customEvent);
        SettingUpListView.listProduit_inside_listView(list_view_produit, listproduit, customEvent);
    }
    public void notif(String title, String text) {
//    Image img = new Image("/gui/logofoot.png");
//    ImageView imgView = new ImageView(img);
//    imgView.setFitWidth(50); // adjust the size of the image as needed
//    imgView.setFitHeight(50);

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                //.graphic(imgView) // set the image as the graphic for the notification
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.show();
    }
    @FXML
    void calculerT(ActionEvent event) {
        int num = list_view_produit.getItems().size();
        Integer tot = 0;
        for (int i = 0; i < num; i++) {
            Integer val = list_view_produit.getItems().get(i).getQuantite();
            tot += val;

        }

        notif("Total des quantites","Votre total des quantite  est :\n" +Integer.toString(tot));


        }

}
