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
public class DossierMedical {
    
    private int id ;
    private String firstName;
    private String lastName;
    private LocalDate date_naissance;
    private String email;
    private String vaccins;
    private String maladies;
    private String allergies;
    private String analyses;
    private String intervention_chirurgicale;

    public DossierMedical() {
    }

    public DossierMedical(int id, String firstName, String lastName, LocalDate date_naissance, String email, String vaccins, String maladies, String allergies, String analyses, String intervention_chirurgicale) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date_naissance = date_naissance;
        this.email = email;
        this.vaccins = vaccins;
        this.maladies = maladies;
        this.allergies = allergies;
        this.analyses = analyses;
        this.intervention_chirurgicale = intervention_chirurgicale;
    }

    public DossierMedical(String firstName, String lastName, LocalDate date_naissance, String email, String vaccins, String maladies, String allergies, String analyses, String intervention_chirurgicale) {
        this.firstName = firstName;
        this.lastName = lastName;
       this.date_naissance = date_naissance;
        this.email = email;
        this.vaccins = vaccins;
        this.maladies = maladies;
        this.allergies = allergies;
        this.analyses = analyses;
        this.intervention_chirurgicale = intervention_chirurgicale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

  
     
    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVaccins() {
        return vaccins;
    }

    public void setVaccins(String vaccins) {
        this.vaccins = vaccins;
    }

    public String getMaladies() {
        return maladies;
    }

    public void setMaladies(String maladies) {
        this.maladies = maladies;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAnalyses() {
        return analyses;
    }

    public void setAnalyses(String analyses) {
        this.analyses = analyses;
    }

    public String getIntervention_chirurgicale() {
        return intervention_chirurgicale;
    }

    public void setIntervention_chirurgicale(String intervention_chirurgicale) {
        this.intervention_chirurgicale = intervention_chirurgicale;
    }

    @Override
    public String toString() {
        return "DossierMedical{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", date_naissance=" + date_naissance + ", email=" + email + ", vaccins=" + vaccins + ", maladies=" + maladies + ", allergies=" + allergies + ", analyses=" + analyses + ", intervention_chirurgicale=" + intervention_chirurgicale + '}';
    }

   public void setDate_naissance(String date_naissance) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dob = LocalDate.parse(date_naissance, formatter);
    this.date_naissance = dob;
}

   
    
    
    
}
