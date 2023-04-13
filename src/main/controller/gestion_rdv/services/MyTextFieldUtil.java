/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv.services;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


/**
 *
 * @author rbaih
 */
public class MyTextFieldUtil {
    
    /**
     * make an event when typing on a textField triggered after 500millies/sec
     * @param textField passe arg as type TextFiel textfield,
     * @param eventHandler what you wish to do pass arg as type EventHandler<ActionEvent> eventHandler
     */
    public static void Do_something_On_typying(TextField textField, EventHandler<ActionEvent> eventHandler ) {
        javafx.util.Duration delay = javafx.util.Duration.millis(500);
        PauseTransition pause = new PauseTransition(delay);
        // event to be executed after TimeOut
        pause.setOnFinished(eventHandler);
        //when change input do something
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.playFromStart();
        });
    }
    
    
    
    /**
     * limits symbols , safe from sql and javascript injection
     * @param textField  arg type TextField textField
     * @param maxChars arg type int number of chars to limit
     */
    public static void LimitTextLengh_plus_ValidateInput_JsSqlInjection(TextField textField , int maxChars) {
        // set a TextFormatter to limit the maximum length of the text to 999 max_characters
        textField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= maxChars && !containsSQLInjection(newText) && !containsJSInjection(newText)) {
                return change;
            } else {
                return null;
            }
        }));
    }

    // check if the text contains SQL injection
    private static boolean containsSQLInjection(String text) {
        // add your SQL injection detection logic here
        return text.matches("(?i).*\\b(select|insert|update|delete|drop|alter)\\b.*");
    }

    // check if the text contains JavaScript injection
    private static boolean containsJSInjection(String text) {
        // add your JavaScript injection detection logic here
        return text.matches("(?i).*<script>.*");
    }
    
    
    

}
