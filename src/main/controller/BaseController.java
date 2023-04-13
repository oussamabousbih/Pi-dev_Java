/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;

/**
 *
 * @author rbaih
 */

public class BaseController {
    
    
    protected UserManager userManager; // module manager
    protected RdvManager rdvManager;  // module manager
    protected String fxmlName; // fxml fileName holder 
    protected ViewFactory viewFactory; // the view manager 
    
    
// constructors base on modules used 
    public BaseController(UserManager userManager, String fxmlName, ViewFactory viewFactory) {
        this.userManager = userManager;
        this.fxmlName = fxmlName;
        this.viewFactory = viewFactory;
    }

    public BaseController(RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        this.rdvManager = rdvManager;
        this.fxmlName = fxmlName;
        this.viewFactory = viewFactory;
    }

    public BaseController(UserManager userManager, RdvManager rdvManager,  String fxmlName, ViewFactory viewFactory) {
        this.userManager = userManager;
        this.rdvManager = rdvManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
    
    
    
    
    
    
    
    
// getters
    public UserManager getUserManager() {
        return userManager;
    }

    public RdvManager getRdvManager() {
        return rdvManager;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    
    
    

    
    
    
}
