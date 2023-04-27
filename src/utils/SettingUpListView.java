/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Collection;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


/**
 *
 * @author rbaih
 */
public class SettingUpListView {
    
    
    
    /**
     * 
     * @param <T>
     * @param objListView type=> ListView<T> from javafx
     * @param objcoll type=> Collection<T> from java.collection , entities/record class usually
     * @param customEventHandler type => EventHandler<? super Event> not MouseEvent
     * @param buttonName type => String if you wish to change the button Name ;
     */
    public static <T> void list_view_with_buttonActionListener(ListView <T> objListView , 
            Collection <T> objcoll , EventHandler<? super Event> customEventHandler  , String buttonName){
           
        objListView.setCellFactory(param -> new ListCell<T>() {
            
            final Button button = new Button( buttonName );
            @Override
            protected void updateItem(T obj, boolean empty) {
                super.updateItem(obj, empty);
                if (empty || obj == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(obj.toString()); // Display all attributes of User object
                    setGraphic(new StackPane(button));
                    setGraphicTextGap(50);
                    
                    button.addEventHandler(ActionEvent.ACTION, customEventHandler);
//                    setOnMouseClicked(customEventHandler); // not cool better MouseEvent
                }
            }
        }//end of anonymous class
        );// closing function setCellFactory()
        
        // creating an observableList from the java.collection we passed to be able to illustrate it 
        ObservableList<T> observableUsers = FXCollections.observableArrayList( objcoll );
        objListView.setItems(observableUsers); // adding the observable list to the List View to illustrate it 
    }
    
    
    /**
     * creating a list view from generic recordClass or entity with custom MouseEvent passed
     * on argument.
     * @param <T>
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
//                    setText(obj.toString()); // ugly choice use pain better
                    setOnMouseClicked(customEventHandler);
                    Label label=new Label(obj.toString());
                    label.setStyle("-fx-text-fill: black ;");
                    // pane to use as graphyic output 
                    HBox hBox= new HBox( label );
                    hBox.setStyle("-fx-font-size: 20 ;" );
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(10); // space in case more then on element passed useless in this case because 1 element only passed
                    setGraphic(hBox);
                }
            }
        }//end of anonymous class
        );// closing function setCellFactory()
        
        // creating an observableList from the java.collection we passed to be able to illustrate it 
        ObservableList<T> observableObjects = FXCollections.observableArrayList( objcoll );
        objListView.setItems(observableObjects); // adding the observable list to the List View to illustrate it 
        
    }
    
    
}
