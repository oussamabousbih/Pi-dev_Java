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
public class MyTextAreaUtil {

    /**
     * make an event when typing on a textArea triggered after 500millies/sec
     * @param textArea passe arg as type TextArea textArea,
     * @param eventHandler what you wish to do pass arg as type EventHandler<ActionEvent> eventHandler
     */
    public static void Do_something_On_typying(TextArea textArea, EventHandler<ActionEvent> eventHandler ) {
        javafx.util.Duration delay = javafx.util.Duration.millis(500);
        PauseTransition pause = new PauseTransition(delay);
        // event to be executed after TimeOut
        pause.setOnFinished(eventHandler);
        //when change input do something
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.playFromStart();
        });
    }
    
    
    /**
     * limits the number of text can be written on the TextArea
     * @param textArea  arg type TextArea textArea
     * @param maxChars arg type int number of chars to limit
     */
    public static void LimitTextLengh(TextArea textArea, int maxChars) {

        textArea.setTextFormatter(new TextFormatter<String>( change
                -> change.getControlNewText().length() <= 500 ? change : null) 
        );
    }
    
    
    /**
     * limits symbols , safe from sql and javascript injection
     * @param textArea  arg type TextArea textArea
     * @param maxChars arg type int number of chars to limit
     */
    public static void LimitTextLengh_plus_ValidateInput_JsSqlInjection(TextArea textArea, int maxChars) {
        // set a TextFormatter to limit the maximum length of the text to 999 max_characters
        textArea.setTextFormatter(new TextFormatter<String>(change -> {
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
