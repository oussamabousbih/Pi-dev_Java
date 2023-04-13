/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entities.gestion_rdv;

import java.time.LocalTime;

/**
 *
 * @author rbaih
 */
public class TimeSlot {
    private Integer id;
    private Integer calnadar_day_id;
    private CalandarDay calandar_day;
    private LocalTime end_time;
    private Integer index_slot;
    private LocalTime start_time;
    private String status;
    private String reason;
    private String note;
    
    // relationship dummy bidirectional 
    private Appointment appointment;
    

    public TimeSlot() {
        this.calandar_day=new CalandarDay();
    }
    
    
    public TimeSlot(Integer id, Integer calnadar_day_id, CalandarDay calandar_day, LocalTime start_time, LocalTime end_time, String status, String reason, String note, Integer index_slot, Appointment appointment) {
        this.id = id;
        this.calnadar_day_id = calnadar_day_id;
        this.calandar_day = calandar_day;
        if( this.calandar_day == null )
            this.calandar_day = new CalandarDay(); 
        this.calandar_day.setId(id);
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
        this.reason = reason;
        this.note = note;
        this.index_slot = index_slot;
        this.appointment = appointment;
      
    }

    public TimeSlot(Integer calnadar_day_id, CalandarDay calandar_day, LocalTime start_time, LocalTime end_time, String status, String reason, String note, Integer index_slot, Appointment appointment) {
        this.calnadar_day_id = calnadar_day_id;
        this.calandar_day = calandar_day;
        if( this.calandar_day == null )
            this.calandar_day = new CalandarDay();
        this.calandar_day.setId(id);
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
        this.reason = reason;
        this.note = note;
        this.index_slot = index_slot;
        this.appointment = appointment;
        
    }

    public TimeSlot(Integer id, Integer calnadar_day_id, LocalTime start_time, LocalTime end_time, String status, String reason, String note, Integer index_slot) {
        this.id = id;
        this.calnadar_day_id = calnadar_day_id;
        this.calandar_day = new CalandarDay();
        this.calandar_day.setId(id);
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
        this.reason = reason;
        this.note = note;
        this.index_slot = index_slot;
       
    }
    
    public TimeSlot( Integer calnadar_day_id, LocalTime start_time, LocalTime end_time, String status, String reason, String note, Integer index_slot, Integer appointment_id, Integer doctor_id) {
        
        this.calnadar_day_id = calnadar_day_id;
        this.calandar_day = new CalandarDay(); 
        this.calandar_day.setId(id);
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
        this.reason = reason;
        this.note = note;
        this.index_slot = index_slot;
       
    }

    @Override
    public String toString() {
        System.out.println("Callin TimeSlot.toString()");
        return "  start_time=" + start_time +  ", status=" + status + ", reason=" + reason +"\n";
    }


    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalnadar_day_id() {
        return calnadar_day_id;
    }

    public void setCalnadar_day_id(Integer calnadar_day_id) {
        this.calnadar_day_id = calnadar_day_id;
    }

    public CalandarDay getCalandar_day() {
        return calandar_day;
    }

    public void setCalandar_day(CalandarDay calandar_day) {
        this.calandar_day = calandar_day;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIndex_slot() {
        return index_slot;
    }

    public void setIndex_slot(Integer index_slot) {
        this.index_slot = index_slot;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

   
    
    
}
