/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv.services;

import javafx.scene.control.Label;

/**
 *
 * @author rbaih
 */
public class MyLabelUtil {
    
    public static void labelmsg_collabsable_bold_textVisibleRed(Label label /*, int max_width_text */){
        label.setStyle(" -fx-background-color: red; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 2, 0, 0);");
        label.setWrapText(true);
//        label.setMaxWidth( max_width_text ); 
    }
    public static void labelmsg_collabsable_bold_textVisibleGreen(Label label /*, int max_width_text*/){
        label.setStyle("-fx-background-color: green; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 2, 0, 0);");
        label.setWrapText(true);
//        label.setMaxWidth( max_width_text ); 
    }
    
    
    
}
