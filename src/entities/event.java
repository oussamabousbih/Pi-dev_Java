/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ThinkPad
 */
public class event {
    private int id;
    private String status,nom_event,discription_event,image_event,date_debut_event,date_fin_event,adresse_event;

    
public event(String nom_event, String discription_event, String image_event, String date_debut_event, String date_fin_event, String adresse_event,String status) {
      
        
        this.nom_event = nom_event;
        this.discription_event = discription_event;
        this.image_event = image_event;
        this.date_debut_event = date_debut_event;
        this.date_fin_event = date_fin_event;
        this.adresse_event = adresse_event;
        this.status = status;
    }

    public event( String nom_event, String discription_event, String image_event, String date_debut_event, String date_fin_event, String adresse_event, String status,int id) {
        this.id = id;
        this.status = status;
        this.nom_event = nom_event;
        this.discription_event = discription_event;
        this.image_event = image_event;
        this.date_debut_event = date_debut_event;
        this.date_fin_event = date_fin_event;
        this.adresse_event = adresse_event;
    }

    public event() {
    }
    
    
    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", status=" + status + ", nom_event=" + nom_event + ", discription_event=" + discription_event + ", image_event=" + image_event + ", date_debut_event=" + date_debut_event + ", date_fin_event=" + date_fin_event + ", adresse_event=" + adresse_event + '}';
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getNom_event() {
        return nom_event;
    }

    public String getDiscription_event() {
        return discription_event;
    }

    public String getImage_event() {
        return image_event;
    }

    public String getDate_debut_event() {
        return date_debut_event;
    }

    public String getDate_fin_event() {
        return date_fin_event;
    }

    public String getAdresse_event() {
        return adresse_event;
    }

   

    

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public void setDiscription_event(String discription_event) {
        this.discription_event = discription_event;
    }

    public void setImage_event(String image_event) {
        this.image_event = image_event;
    }

    public void setDate_debut_event(String date_debut_event) {
        this.date_debut_event = date_debut_event;
    }

    public void setDate_fin_event(String date_fin_event) {
        this.date_fin_event = date_fin_event;
    }

    public void setAdresse_event(String adresse_event) {
        this.adresse_event = adresse_event;
    }

}