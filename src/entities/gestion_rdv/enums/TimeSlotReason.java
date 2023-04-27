/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.gestion_rdv.enums;

/**
 *
 * @author rbaih
 */
public enum TimeSlotReason {
    
    UNBOOKED("unbooked"),
    BOOKED("booked"),
    LUNCH_TIME("lunch-time");
    
    private final String name;

    private TimeSlotReason(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    
}
