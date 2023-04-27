/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author rbaih
 */
public class MyLabelUtil {
    
    public static void labelmsg_collabsable_bold_textVisibleRed(Label label /*, int max_width_text */){
        label.setVisible(true);
        label.setStyle(" -fx-background-color: red; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 2, 0, 0);");
        label.setWrapText(true);
//        label.setMaxWidth( max_width_text ); 
    }
    public static void labelmsg_collabsable_bold_textVisibleRed(Label label , String msg /*, int max_width_text */){
        label.setVisible(true);
        label.setText(msg);
        label.setStyle(" -fx-background-color: red; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 2, 0, 0);");
        label.setWrapText(true);
//        label.setMaxWidth( max_width_text ); 
    }
    
    
    public static void labelmsg_collabsable_bold_textVisibleGreen(Label label /*, int max_width_text*/){
        label.setVisible(true);
        label.setStyle("-fx-background-color: green; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 2, 0, 0);");
        label.setWrapText(true);
//        label.setMaxWidth( max_width_text ); 
    }
    public static void labelmsg_collabsable_bold_textVisibleGreen(Label label, String msg /*, int max_width_text*/){
        label.setVisible(true);
        label.setText(msg);
        label.setStyle("-fx-background-color: green; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 2, 0, 0);");
        label.setWrapText(true);
//        label.setMaxWidth( max_width_text ); 
    }
    
    
    
    
    
    public static void LoadingGaraphicMsg(Label myLabel  ,String loding_msg ){
        myLabel.setVisible(true);
        Label inGraficLabel = new Label( loding_msg );
        ImageView image = new ImageView(new Image("resources/images/load50px.gif"));
        myLabel.setGraphic(new HBox(image,inGraficLabel));
        //clear msg after a while
       
        
    }
    
    public static void hideLabelAfter(Label myLable  , int secondsToHideAfter){
        
        //clear msg after a while
        Consumer<Label> myconsumerClearLabelandHide = label -> {label.setText(""); label.setVisible(false);  };
        MyDelayedExecution.executeAfterDelay_JavaFX(myconsumerClearLabelandHide , myLable , secondsToHideAfter);

    }
    
    
}
