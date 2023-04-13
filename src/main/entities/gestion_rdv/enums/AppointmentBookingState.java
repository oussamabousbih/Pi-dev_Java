/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entities.gestion_rdv.enums;

/**
 *
 * @author rbaih
 */
public enum AppointmentBookingState {
    
    CONFIRMED("confirmed"),
    NOT_CONFIRMED("not-confirmed");

    private final String name;

    private AppointmentBookingState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
