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
public enum TimeSlotStatus {
   
    AVAILABLE("available"),
    NOT_AVAILABLE("not-available");

    private final String name;

    private TimeSlotStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

 
}
