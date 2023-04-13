/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.GUI.ViewFactory;

/**
 *
 * @author rbaih
 */
public class MainClass extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ViewFactory viewFactory = new ViewFactory(new UserManager(),new RdvManager());
        viewFactory.showLogin_newStage();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
