/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author rbaih
 */
public class MySpinnerUtil {

    public static void Change_spinner_digitOnly(Spinner<Integer>... spinners) {

        for (Spinner<Integer> spinner : spinners) {
            TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), null, c -> {
                if (c.getControlNewText().matches("\\d+")) {
                    return c;
                } else {
                    return null;
                }
            });
            spinner.getEditor().setTextFormatter(formatter);
        }
    }
    
    
    //******************************************************************************************
    
      public static void addFocusListenerToSpinners(Spinner<Integer>... spinners) {
        for (Spinner<Integer> spinner : spinners) {
            spinner.focusedProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue) {
                    spinner.increment(0); // commit the edit when the Spinner loses focus
                }
            });
        }
    }

}
