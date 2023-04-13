/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv.services;

import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

/**
 *
 * @author rbaih
 */
public class MyDatePickerUtil {
    
    public static void disableDatesPassed_andDatesBeforeToday(List<LocalDate> datesToDisable, DatePicker datePicker) {
    datePicker.setDayCellFactory(picker -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);

            // Disable dates before today
            if (date.isBefore(LocalDate.now())) {
                setDisable(true);
                setStyle("-fx-background-color: #d3d3d3;");
                setOnMouseClicked(event -> {});
                setOnKeyPressed(event -> {});
                return;
            }

            // Disable dates in the list
            if (datesToDisable.contains(date)) {
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
                setOnMouseClicked(event -> {});
                setOnKeyPressed(event -> {});
            } else {
                setDisable(false);
                setStyle("");
                setOnMouseClicked(null);
                setOnKeyPressed(null);
            }
        }
    });
}
    
    public void disableDatesPassed(List<LocalDate> datesToDisable, DatePicker datePicker) {
    datePicker.setDayCellFactory(picker -> new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);

            // Disable dates before today
            if (date.isBefore(LocalDate.now())) {
                setDisable(true);
                setStyle("-fx-background-color: #d3d3d3;");
                setOnMouseClicked(event -> {});
                setOnKeyPressed(event -> {});
                return;
            }

            // Disable dates in the list
            if (datesToDisable.contains(date)) {
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
                setOnMouseClicked(event -> {});
                setOnKeyPressed(event -> {});
            } else {
                setDisable(false);
                setStyle("");
                setOnMouseClicked(null);
                setOnKeyPressed(null);
            }
        }
    });
}

    
}
