/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author AOUADI HADIL
 */
public class Ficheconsultation {
    
  private int id ;
 
  private LocalDate date_consultation;
  private String firstName;
  private String lastName;
  private String specialite;
  private String traitement;
  private String reccomendation;
  

    public Ficheconsultation() {
    }
  
  

    public Ficheconsultation(int id, LocalDate date_consultation, String firstName, String lastName, String specialite, String traitement, String reccomendation) {
        this.id = id;
       
        this.date_consultation = date_consultation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialite = specialite;
        this.traitement = traitement;
        this.reccomendation = reccomendation;
    }

    public Ficheconsultation(  LocalDate date_consultation, String firstName, String lastName, String specialite, String traitement, String reccomendation) {
        this.date_consultation = date_consultation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialite = specialite;
        this.traitement = traitement;
        this.reccomendation = reccomendation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate_consultation() {
        return date_consultation;
    }

    public void setDate_consultation(LocalDate date_consultation) {
        this.date_consultation = date_consultation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public String getReccomendation() {
        return reccomendation;
    }

    public void setReccomendation(String reccomendation) {
        this.reccomendation = reccomendation;
    }

    @Override
    public String toString() {
        return "Ficheconsultation{" + "id=" + id + ", date_consultation=" + date_consultation + ", firstName=" + firstName + ", lastName=" + lastName + ", specialite=" + specialite + ", traitement=" + traitement + ", reccomendation=" + reccomendation + '}';
    }
  
  
}
