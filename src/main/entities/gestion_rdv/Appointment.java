/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entities.gestion_rdv;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author rbaih
 */
public class Appointment {
    private Integer id;
    private User doctor;
    private Integer doctor_id;
    private User patient;
    private Integer patient_id;
    private TimeSlot time_slot;
    private Integer time_slot_id;
    private LocalDate date;
    private String reason;
    private LocalTime hour;
    private String booking_state;
    private Integer calandar_day_id;

    public Appointment() {
        this.time_slot = new TimeSlot();
        this.doctor=new User();
        this.patient=new User();
    }
    
    
    
    public Appointment(Integer id, LocalDate date, String reason, LocalTime hour, String booking_state,
           User doctor, User patient, TimeSlot time_slot, Integer calandar_day_id,
           Integer time_slot_id, Integer doctor_id, Integer patient_id) {
        this.id = id;
        this.date = date;
        this.reason = reason;
        this.hour = hour;
        this.booking_state = booking_state;
        this.calandar_day_id = calandar_day_id;
        this.time_slot_id = time_slot_id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.time_slot = time_slot;
        this.doctor=doctor;
        this.patient=patient;
        if(this.time_slot==null)
            this.time_slot= new TimeSlot();
        this.time_slot.setId(time_slot_id);
        if(this.doctor == null)
            this.doctor=new User();
        this.doctor.setId(doctor_id);
        if(this.patient == null)
            this.patient=new User();
        this.patient.setId(patient_id);
    }

    public Appointment(Integer id, Integer doctor_id, Integer patient_id, Integer time_slot_id, LocalDate date, String reason, LocalTime hour, String booking_state) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.time_slot_id = time_slot_id;
        this.date = date;
        this.reason = reason;
        this.hour = hour;
        this.booking_state = booking_state;
        if(this.time_slot==null)
            this.time_slot= new TimeSlot();
        this.time_slot.setId(time_slot_id);
        if(this.doctor == null)
            this.doctor=new User();
        this.doctor.setId(doctor_id);
        if(this.patient == null)
            this.patient=new User();
        this.patient.setId(patient_id);
    }
    
    

    public Appointment(LocalDate date, String reason, LocalTime hour, String booking_state,
           User doctor, User patient, TimeSlot time_slot, Integer calandar_day_id,
           Integer time_slot_id, Integer doctor_id, Integer patient_id) {
        this.date = date;
        this.reason = reason;
        this.hour = hour;
        this.booking_state = booking_state;
        this.calandar_day_id = calandar_day_id;
        this.time_slot_id = time_slot_id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
         this.time_slot = time_slot;
        this.doctor=doctor;
        this.patient=patient;
        if(this.time_slot==null)
            this.time_slot= new TimeSlot();
        this.time_slot.setId(time_slot_id);
        if(this.doctor == null)
            this.doctor=new User();
        this.doctor.setId(doctor_id);
        if(this.patient == null)
            this.patient=new User();
        this.patient.setId(patient_id);
        
    }

    public Appointment(Integer doctor_id, Integer patient_id, Integer time_slot_id, LocalDate date, String reason, LocalTime hour, String booking_state, Integer calandar_day_id) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.time_slot_id = time_slot_id;
        this.date = date;
        this.reason = reason;
        this.hour = hour;
        this.booking_state = booking_state;
        this.calandar_day_id = calandar_day_id;
        if(this.time_slot==null)
            this.time_slot= new TimeSlot();
        this.time_slot.setId(time_slot_id);
        if(this.doctor == null)
            this.doctor=new User();
        this.doctor.setId(doctor_id);
        if(this.patient == null)
            this.patient=new User();
        this.patient.setId(patient_id);
    }

    @Override
    public String toString() {
        return "reason: " + reason +  "\nstate: " + booking_state ;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public TimeSlot getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(TimeSlot time_slot) {
        this.time_slot = time_slot;
    }

    public Integer getTime_slot_id() {
        return time_slot_id;
    }

    public void setTime_slot_id(Integer time_slot_id) {
        this.time_slot_id = time_slot_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public String getBooking_state() {
        return booking_state;
    }

    public void setBooking_state(String booking_state) {
        this.booking_state = booking_state;
    }

    public Integer getCalandar_day_id() {
        return calandar_day_id;
    }

    public void setCalandar_day_id(Integer calandar_day_id) {
        this.calandar_day_id = calandar_day_id;
    }
    
    
    
    
    
}
