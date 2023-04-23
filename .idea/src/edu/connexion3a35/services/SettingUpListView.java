/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.services;

import edu.connexion3a35.entities.Categorie;
import java.util.Collection;

import edu.connexion3a35.entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author rbaih
 */
public class SettingUpListView {

    
    /**
     * creating a list view from generic recordClass or entity with custom MouseEvent passed
     * on argument.
     * @param <T>//categorie
     * @param objListView type=> ListView<T> from javafx
     * @param objcoll type=> Collection<T> from java.collection , entities/record class usually
     * @param customEventHandler type => EventHandler<? super MouseEvent> 
     */
    public static <T> void listObjectsOn_listView(ListView <T> objListView ,
            Collection <T> objcoll , EventHandler<? super MouseEvent> customEventHandler ){
           
        objListView.setCellFactory(param -> new ListCell<T>() {
            
            @Override
            protected void updateItem(T obj, boolean empty) {
                super.updateItem(obj, empty);
                if (empty || obj == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(obj.toString()); // Display all attributes of User object
                    setOnMouseClicked(customEventHandler);
                }
            }
        }//end of anonymous class
        );// closing function setCellFactory()
        
        // creating an observableList from the java.collection we passed to be able to illustrate it 
        ObservableList<T> observableObjects = FXCollections.observableArrayList( objcoll );
        objListView.setItems(observableObjects); // adding the observable list to the List View to illustrate it 
 
    }
    public static void listProduit_inside_listView(ListView<Produit> objListView,
                                            Collection<Produit> objProduit, EventHandler<? super MouseEvent> customEventHandler) {

        objListView.setCellFactory(param -> new ListCell<Produit>() {

                    @Override
                    protected void updateItem(Produit objProd, boolean empty) {
                        super.updateItem(objProd, empty);
                        if (empty || objProd == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            //set event passed as Argument matensech
                            setOnMouseClicked(customEventHandler);

//                   setText(obj.toString()); // ugly choice use pane better then regular text
                            Label label1 = new Label(objProd.getNom_produit());
                            Label label2 = new Label(objProd.getMarque());
                            Label label3 = new Label(objProd.getPrix() + " DT");
                            // pane to fill up labels horizontaly
                            HBox hBox = new HBox(label1, label2, label3);
                            hBox.setStyle("-fx-font-size: 20; -fx-text-fill: black ;");
                            hBox.setAlignment(Pos.CENTER); // 7ot fi nos
                            hBox.setSpacing(8);
                            // vertical pane to fill up image on top and labels stored in hbox below it
                            // set image dimetions as you wish if you want to
                            if (objProd.testimagenull()==true) {
                                objProd.setImage_produit("no_img_avaliable.jpg");
                            }

                            ImageView image = new ImageView("/images/" + objProd.getImage_produit());
                            image.setFitHeight(100);
                            image.setFitWidth(100);
                            VBox vBox = new VBox( image,hBox);
                            vBox.setAlignment(Pos.CENTER);
                            setGraphic(vBox);
                        }
                    }//end of updateItem
                }//end of lambda param -> ........
        );// closing function objListView.setCellFactory()
        ObservableList<Produit> observableObjects = FXCollections.observableArrayList( objProduit);
        objListView.setItems(observableObjects);

    }
     // adding the observable list to the List View to illustrate it

}
