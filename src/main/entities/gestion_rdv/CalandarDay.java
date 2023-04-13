/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entities.gestion_rdv;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rbaih
 */
public class CalandarDay {
    private Integer id;
    private Integer doctor_id;
    private User doctor;
    private  Collection<TimeSlot> time_slots;
    private Integer total_time_slots;
    private LocalDate date;
    private LocalTime day_start;
    private LocalTime day_end;
    private Integer session_duration;
    private LocalTime lunch_break_start;
    private LocalTime lunch_break_end;
    

    @Override
    public String toString() {
        return date + "             Open: " + day_start + " - " +lunch_break_start  + "   BreakTime:" + lunch_break_end + " - " +day_end  
                + "   Session: "+ session_duration ;
    }
    
 
    public CalandarDay() {
        this.time_slots = new ArrayList<>();
        this.doctor=new User();
    }

    
    public CalandarDay(Integer id, Integer doctor_id, User doctor, LocalDate date,
           LocalTime day_start,LocalTime day_end, Integer session_duration,LocalTime lunch_break_start,
           LocalTime lunch_break_end, Integer total_time_slots , Collection<TimeSlot> time_slots ) {
        
        this.time_slots = time_slots;
        if(this.time_slots==null)
            this.time_slots = new ArrayList<>();
        
        
        this.id = id;
        this.doctor_id = doctor_id;
        this.doctor = doctor;
        if(this.doctor==null)
            this.doctor=new User();
        this.doctor.setId(doctor_id);
        this.date = date; 
        this.day_start = day_start;
        this.day_end = day_end;
        this.session_duration = session_duration;
        this.lunch_break_start = lunch_break_start;
        this.lunch_break_end = lunch_break_end;
        
        this.total_time_slots = total_time_slots;
        
        
    }
    public CalandarDay(Integer doctor_id, User doctor, LocalDate date,
           LocalTime day_start,LocalTime day_end, Integer session_duration,LocalTime lunch_break_start,
           LocalTime lunch_break_end, Integer total_time_slots , Collection<TimeSlot> time_slots ) {
        
        this.time_slots = time_slots;
        if(this.time_slots==null)
            this.time_slots = new ArrayList<>();
        
//        this.id = id;
        this.doctor_id = doctor_id;
        this.doctor = doctor;
        if(this.doctor==null)
            this.doctor=new User();
        this.doctor.setId(doctor_id);
        this.date = date;
        this.day_start = day_start;
        this.day_end = day_end;
        this.session_duration = session_duration;
        this.lunch_break_start = lunch_break_start;
        this.lunch_break_end = lunch_break_end;
        this.total_time_slots = total_time_slots;
        
    }

    
    
    
  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getDay_start() {
        return day_start;
    }

    public void setDay_start(LocalTime day_start) {
        this.day_start = day_start;
    }

    public LocalTime getDay_end() {
        return day_end;
    }

    public void setDay_end(LocalTime day_end) {
        this.day_end = day_end;
    }

    public Integer getSession_duration() {
        return session_duration;
    }

    public void setSession_duration(Integer session_duration) {
        this.session_duration = session_duration;
    }

    public LocalTime getLunch_break_start() {
        return lunch_break_start;
    }

    public void setLunch_break_start(LocalTime lunch_break_start) {
        this.lunch_break_start = lunch_break_start;
    }

    public LocalTime getLunch_break_end() {
        return lunch_break_end;
    }

    public void setLunch_break_end(LocalTime lunch_break_end) {
        this.lunch_break_end = lunch_break_end;
    }

    public Integer getTotal_time_slots() {
        return total_time_slots;
    }

    public void setTotal_time_slots(Integer total_time_slots) {
        this.total_time_slots = total_time_slots;
    }

    public Collection<TimeSlot> getTime_slots() {
        return time_slots;
    }

    public void setTime_slots(Collection<TimeSlot> time_slots) {
        this.time_slots=time_slots;
//        this.time_slots.addAll(time_slots);
    }
    
    
    
    
    
    
}
