/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.DummyUser;
import entities.gestion_rdv.CalandarDay;

/**
 *
 * @author rbaih
 */
public class UserManager {
    private DummyUser logged_User;
    private DummyUser selectedDoctor;

    public DummyUser getLogged_User() {
        return logged_User;
    }

    public void setLogged_User(DummyUser logged_User) {
        this.logged_User = logged_User;
    }

    public DummyUser getSelectedDoctor() {
        return selectedDoctor;
    }

    public void setSelectedDoctor(DummyUser selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }
    
    
    public void clear_all_fields(){
        logged_User=null;
        selectedDoctor=null;
        
    }
    
    
}
