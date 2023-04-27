/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.gestion_rdv;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rbaih
 */
public class DummyCalandar {

    LocalDate date;
    Collection<Slot> Slots;

    public DummyCalandar() {
    }

    public DummyCalandar(LocalDate date) {
        this.date = date;
        Slots = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Collection<Slot> getSlots() {
        return Slots;
    }

    public void setSlots(Collection<Slot> Slots) {
        this.Slots = Slots;
    }

    @Override
    public String toString() {
        return "\ndummyCalandar{" + "date=" + date + ", Slots=" + Slots + "}";
    }
    
    
    //*********************************
    //*********************************
    //*********************************
    //*********************************
    public static class Slot {

        LocalTime time;
        String status;
        String reason;
        String note;
        Appoint appointment;

        public Slot() {
        }

        public Slot(LocalTime time, String status, String reason, String note, Appoint appointment) {
            this.time = time;
            this.status = status;
            this.reason = reason;
            this.note = note;
            this.appointment = appointment;
        }

        @Override
        public String toString() {
            return "Slot{" + "time=" + time + ", status=" + status +  ", appointment=" + appointment + '}';
        }

        public LocalTime getTime() {
            return time;
        }

        public void setTime(LocalTime time) {
            this.time = time;
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

        public Appoint getAppointment() {
            return appointment;
        }

        public void setAppointment(Appoint appointment) {
            this.appointment = appointment;
        }

        //***************************************
        //***************************************
        //***************************************
        //***************************************
        //***************************************
        //***************************************
        //***************************************
        //***************************************
        //***************************************
        public static class Appoint {

            String reasonApp;
            Patient patient;

            public Appoint(String reasonApp, Patient patient) {
                this.reasonApp = reasonApp;
                this.patient = patient;
            }

            public Appoint() {
            }

            @Override
            public String toString() {
                return "Appoint{" + patient + '}';
            }

            public String getReasonApp() {
                return reasonApp;
            }

            public void setReasonApp(String reasonApp) {
                this.reasonApp = reasonApp;
            }

            public Patient getPatient() {
                return patient;
            }

            public void setPatient(Patient patient) {
                this.patient = patient;
            }

            //****************************
            //****************************
            //****************************
            //****************************
            public static class Patient {

                String name;
                String email;
                String phone;
                
                public Patient() {
                }

                public Patient(String name, String email, String phone) {
                    this.name = name;
                    this.email = email;
                    this.phone= phone;
                }

                @Override
                public String toString() {
                    return "Patient{" +name +'}';
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }
                 

            }
            
            

        }

    }

}
