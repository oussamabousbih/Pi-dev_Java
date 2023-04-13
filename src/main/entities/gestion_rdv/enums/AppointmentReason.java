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
public enum AppointmentReason {
    APPOINTMENT("appointment"),
    CHECK_UP("check-up");

    private final String name;

    private AppointmentReason(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
