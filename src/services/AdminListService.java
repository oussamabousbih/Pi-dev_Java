package services;

import java.util.Collection;

import entities.User;
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
public class AdminListService {
    public static <T> void listObjectsOn_listView(ListView<T> objListView ,
                                                  Collection <T> objcoll , EventHandler<? super MouseEvent> customEventHandler ){

        objListView.setCellFactory(param -> new ListCell<T>() {

                    @Override
                    protected void updateItem(T obj, boolean empty) {
                        super.updateItem(obj, empty);
                        if (empty || obj == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            String[] tokens = obj.toString().split(",");
                            String id = tokens[0];
                            String email = tokens[1];
                            String nom = tokens[5]+" "+tokens[6];
                            String role = tokens[2];
                            setText("ID: "+id.substring(8)+" - Email: "+email.substring(8)+" - Nom : "+nom.substring(9)+"- Role: "+role.substring(5)); // Display all attributes of User object
                            setOnMouseClicked(customEventHandler);
                        }
                    }
                }//end of anonymous class
        );// closing function setCellFactory()

        // creating an observableList from the java.collection we passed to be able to illustrate it
        ObservableList<T> observableObjects = FXCollections.observableArrayList( objcoll );
        objListView.setItems(observableObjects); // adding the observable list to the List View to illustrate it

    }

}
