/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.lang.reflect.Field;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import entities.gestion_rdv.CalandarDay;


/**
 *
 * @author rbaih
 */
public class SettingUpTableView {
    
    /**
     * 
     * @param <T>  indicates that We re working with a generic type
     * @param objTableView => TableView javafx
     * @param objCollection => type Collection<WhateverEntityClass>
     * @param customEventHandler => type EventHandler<? super MouseEvent>
     * @param fields =>type Fields[]  example: Fields[] fields = WhateverEntityClass.class.getDeclaredFields(); -> this holds the name of 
     * attributes of the class
     * @param columnsToHideByIteration => type int , on your entity class arrange the attributes you want to hide to be the first declared
     * after arranging count how many of attributes u wish to hide and passe it as argument so we can iterate and set visibility to false . 
     */
    public static <T> void Tableview_SettingUp( TableView <T> objTableView , 
            Collection <T> objCollection , EventHandler<? super MouseEvent> customEventHandler ,Field[] fields , int columnsToHideByIteration ){
           
        
                // Create TableColumn objects to determine which properties of the User object will be displayed in the TableView
//            TableColumn<CalandarDay, String> idColumn = new TableColumn<>("Id");
//            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//            TableColumn<CalandarDay, String> dateColumn = new TableColumn<>("Date");
//            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//            TableColumn<CalandarDay, String> startTimeColumn = new TableColumn<>("Starting time");
//            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("day_start"));
//            TableColumn<CalandarDay, String> endTimeColumn = new TableColumn<>("closing time");
//            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("day_end"));
//            TableColumn<CalandarDay, String> lunchStartColumn = new TableColumn<>("Lunch break start");
//            lunchStartColumn.setCellValueFactory(new PropertyValueFactory<>("lunch_break_start"));
//            TableColumn<CalandarDay, String> lunchEndColumn = new TableColumn<>("Lunch break end");
//            lunchEndColumn.setCellValueFactory(new PropertyValueFactory<>("lunch_break_end"));
//            // Add the TableColumn objects to the TableView
//            tableViewCalandar.getColumns().addAll(idColumn, dateColumn, startTimeColumn , endTimeColumn, lunchStartColumn, lunchEndColumn);

        // replacing all above with this loop
        System.out.println("fields number passed to iterate: "+fields.length);
        for (Field field : fields) {
            TableColumn<T, ?> column = new TableColumn<>();
            column.setText(field.getName().toUpperCase()); // set the column header text to the field name
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName())); // use the field name as the cell value factory
            objTableView.getColumns().add(column);
        }
        
        
        //hiding attributs that we dont like to see on the tableView
//            tableViewCalandar.getColumns().get(0).setVisible(false);
//            tableViewCalandar.getColumns().get(1).setVisible(false);
//            tableViewCalandar.getColumns().get(2).setVisible(false);
//            tableViewCalandar.getColumns().get(3).setVisible(false);
//            tableViewCalandar.getColumns().get(4).setVisible(false);

        //replacing above with this code
        for(int i=0; i<columnsToHideByIteration ; i++)
            objTableView.getColumns().get(i).setVisible(false);
        
        // Create some sample User objects to populate the TableView
        ObservableList<T> observalObjectList = FXCollections.observableArrayList( objCollection);
        // Add the User objects to the TableView
        objTableView.setItems(observalObjectList );
        // Set the TableView to be editable
        objTableView.setEditable(true);
        objTableView.setOnMouseClicked(customEventHandler); // event passed here 
        
        // making table fill up whole space
        MyTableViewUtil.autoAdjustTableColumn_basedOnSpace(objTableView);
        
    }
    
   
    
    
}
