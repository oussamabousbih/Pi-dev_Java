/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.entities.gestion_rdv.Appointment;
import main.entities.gestion_rdv.CalandarDay;
import main.entities.gestion_rdv.DummyPatientAppointment;
import main.entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import main.entities.gestion_rdv.TimeSlot;

/**
 *
 * @author rbaih
 */
public class RdvManager {
    
    
    private CalandarDay selectedCalandar;
    private TimeSlot selectedTimeSlot;
    private Appointment selectedAppointment;
    private DummyPatientAppointmentDoctorSide selectedDummyPatientAppointmentDoctorSide;
    private DummyPatientAppointment selectedDummyPatientAppointment;
    
    private Collection<CalandarDay> collecCalandars = FXCollections.observableArrayList();
    private Collection<TimeSlot> collecTimeSlots = FXCollections.observableArrayList();
    private Collection<Appointment> collecAppointments = FXCollections.observableArrayList();
    private Collection<DummyPatientAppointment> collecPatientappointments = FXCollections.observableArrayList();
    private Collection<DummyPatientAppointmentDoctorSide> collecDoctorPatientAppointment = FXCollections.observableArrayList();
    
    private ObservableList<CalandarDay> obsListCalandars = FXCollections.observableArrayList();
    private ObservableList<TimeSlot> obsListTimeSlots = FXCollections.observableArrayList();
    private ObservableList<Appointment> obsListAppointments = FXCollections.observableArrayList();
    private ObservableList<DummyPatientAppointment> obsListPatientappointments = FXCollections.observableArrayList();
    private ObservableList<DummyPatientAppointmentDoctorSide> obsListDoctorPatientAppointment = FXCollections.observableArrayList();

  
    public void SetEverythingToNull(){
        this.Clear_setNull_SelectedCalandarTimeSlotAndDummyPatientAppDoctorSide();
        this.Clear_setToNull_AllSelectedFields();
        this.clearAllCollections_ClearList();
        this.clearAllObservableList_ClearList();
    }
    
    public void Clear_setToNull_AllSelectedFields(){
        selectedCalandar=null;
        selectedTimeSlot=null;
        selectedAppointment=null;
        selectedDummyPatientAppointment=null;
        selectedDummyPatientAppointmentDoctorSide=null;
    }
    public void Clear_setNull_SelectedCalandarTimeSlotAndDummyPatientAppDoctorSide(){
        selectedCalandar=null;
        selectedTimeSlot=null;
        selectedDummyPatientAppointmentDoctorSide=null;
    }
    
    public void clearAllObservableList_ClearList(){
        obsListCalandars.clear();
        obsListTimeSlots.clear();
        obsListAppointments.clear();
        obsListPatientappointments.clear();
        obsListDoctorPatientAppointment.clear();
    }
    public void clearAllCollections_ClearList(){
        collecCalandars.clear();
        collecTimeSlots.clear();
        collecAppointments.clear();
        collecPatientappointments.clear();
        collecDoctorPatientAppointment.clear();
    }
    
    
    public CalandarDay getSelectedCalandar() {
        
        return selectedCalandar;
    }

    public void setSelectedCalandar(CalandarDay selectedCalandar) {
        this.selectedCalandar = selectedCalandar;
    }

    public TimeSlot getSelectedTimeSlot() {
        return selectedTimeSlot;
    }

    public void setSelectedTimeSlot(TimeSlot selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }


    public DummyPatientAppointmentDoctorSide getSelectedDummyPatientAppointmentDoctorSide() {
        return selectedDummyPatientAppointmentDoctorSide;
    }

    public void setSelectedDummyPatientAppointmentDoctorSide(DummyPatientAppointmentDoctorSide selectedDummyPatientAppointmentDoctorSide) {
        this.selectedDummyPatientAppointmentDoctorSide = selectedDummyPatientAppointmentDoctorSide;
    }

    public DummyPatientAppointment getSelectedDummyPatientAppointment() {
        return selectedDummyPatientAppointment;
    }

    public void setSelectedDummyPatientAppointment(DummyPatientAppointment selectedDummyPatientAppointment) {
        this.selectedDummyPatientAppointment = selectedDummyPatientAppointment;
    }

    public Collection<CalandarDay> getCollecCalandars() {
        return collecCalandars;
    }

    public void setCollecCalandars(Collection<CalandarDay> collecCalandars) {
        this.collecCalandars = collecCalandars;
    }

    public Collection<TimeSlot> getCollecTimeSlots() {
        return collecTimeSlots;
    }

    public void setCollecTimeSlots(Collection<TimeSlot> collecTimeSlots) {
        this.collecTimeSlots = collecTimeSlots;
    }

    public Collection<Appointment> getCollecappointments() {
        return collecAppointments;
    }

    public void setCollecappointments(Collection<Appointment> collecAppointments) {
        this.collecAppointments = collecAppointments;
    }

    public Collection<DummyPatientAppointment> getCollecPatientappointments() {
        return collecPatientappointments;
    }

    public void setCollecPatientappointments(Collection<DummyPatientAppointment> collecPatientappointments) {
        this.collecPatientappointments = collecPatientappointments;
    }

    public Collection<DummyPatientAppointmentDoctorSide> getCollecDoctorPatientAppointment() {
        return collecDoctorPatientAppointment;
    }

    public void setCollecDoctorPatientAppointment(Collection<DummyPatientAppointmentDoctorSide> collecDoctorPatientAppointment) {
        this.collecDoctorPatientAppointment = collecDoctorPatientAppointment;
    }

    public ObservableList<CalandarDay> getObsListCalandars() {
        return obsListCalandars;
    }

    public void setObsListCalandars(ObservableList<CalandarDay> obsListCalandars) {
        this.obsListCalandars = obsListCalandars;
    }

    public ObservableList<TimeSlot> getObsListTimeSlots() {
        return obsListTimeSlots;
    }

    public void setObsListTimeSlots(ObservableList<TimeSlot> obsListTimeSlots) {
        this.obsListTimeSlots = obsListTimeSlots;
    }

    public ObservableList<Appointment> getObsListAppointments() {
        return obsListAppointments;
    }

    public void setObsListAppointments(ObservableList<Appointment> obsListAppointments) {
        this.obsListAppointments = obsListAppointments;
    }

    public ObservableList<DummyPatientAppointment> getObsListPatientappointments() {
        return obsListPatientappointments;
    }

    public void setObsListPatientappointments(ObservableList<DummyPatientAppointment> obsListPatientappointments) {
        this.obsListPatientappointments = obsListPatientappointments;
    }

    public ObservableList<DummyPatientAppointmentDoctorSide> getObsListDoctorPatientAppointment() {
        return obsListDoctorPatientAppointment;
    }

    public void setObsListDoctorPatientAppointment(ObservableList<DummyPatientAppointmentDoctorSide> obsListDoctorPatientAppointment) {
        this.obsListDoctorPatientAppointment = obsListDoctorPatientAppointment;
    }
    
    
    
   
    
}
