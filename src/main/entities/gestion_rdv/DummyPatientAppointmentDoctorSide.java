/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entities.gestion_rdv;

import java.time.LocalDate;
import java.time.LocalTime;
import main.controller.gestion_rdv.PatientCalandarTimeSlots_listController;

/**
 *
 * @author rbaih
 */
public class DummyPatientAppointmentDoctorSide {
    
    protected Integer id;
    protected Integer doctor_id;
    protected Integer appoinment_id;
    protected Integer time_slot_id;
    protected Integer calandar_day_id;
    protected String gender;
    protected LocalDate date;
    protected LocalTime time;
    protected String first_name;
    protected String last_name;
    protected String booking_state;
    protected String reason;
    protected String phone_number;
    protected String email;
    protected String age;
   
    public DummyPatientAppointmentDoctorSide() {
    }

    @Override
    public String toString() {
        return "Date="+date+ ", time=" + time + ", first_name="+ first_name +  ", phone_number=" + phone_number +", booking_state=" + booking_state + ", reason=" + reason  ;
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
    public Integer getAppoinment_id() {
        return appoinment_id;
    }

    public void setAppoinment_id(Integer appoinment_id) {
        this.appoinment_id = appoinment_id;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getCalandar_day_id() {
        return calandar_day_id;
    }

    public void setCalandar_day_id(Integer calandar_day_id) {
        this.calandar_day_id = calandar_day_id;
    }

    public String getBooking_state() {
        return booking_state;
    }

    public void setBooking_state(String booking_state) {
        this.booking_state = booking_state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



    public Appointment convertToAppointment(){
        Appointment appointment = new Appointment();
        
        appointment.setId( this.appoinment_id);
        appointment.setBooking_state(this.booking_state);
        appointment.setReason(this.reason);
        appointment.setDate(this.date);
        appointment.setHour(this.time);
        appointment.setPatient_id(this.id);
        appointment.setTime_slot_id(this.time_slot_id);
        appointment.setDoctor_id(this.doctor_id);
        //doctor
        User doctor = new User();
        doctor.setId(this.doctor_id);
        appointment.setDoctor(doctor);
        //patient
        User patient = new User();
        patient.setId(this.id);
        patient.setFirst_name(this.first_name);
        patient.setLast_name(this.last_name);
        patient.setAge(this.age);
        patient.setEmail(this.email);
        patient.setGender(this.gender);
        patient.setPhone_number(this.phone_number);
        appointment.setPatient(patient);
        //timeSlot
        TimeSlot timeSlot= new TimeSlot();
        timeSlot.setId(this.time_slot_id);
        appointment.setTime_slot(timeSlot);
        //other
        appointment.setCalandar_day_id(this.calandar_day_id);
        
        return appointment;
    }
    
    
    

  
    
    
}
