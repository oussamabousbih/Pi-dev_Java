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
public class event_ticket {
    private int id,event_id_id,userID;
    private String image,date_ticket,valide_ticket,matricule_event,prix_ticket;
     
     
    public void setId(int id) {
        this.id = id;
    }

    public event_ticket(String matricule_event,String image, String date_ticket, String valide_ticket, String prix_ticket, int event_id_id,int id) {
        this.id = id;
        this.image = image;
        this.date_ticket = date_ticket;
        this.valide_ticket = valide_ticket;
        this.matricule_event = matricule_event;
        this.prix_ticket = prix_ticket;
        this.event_id_id = event_id_id;
    }

    public int getId() {
        return id;
    }
   

    @Override
    public String toString() {
        return "Event_Ticket{" + "matricule_event=" + matricule_event + ", valide_ticket=" + valide_ticket + ", prix_ticket=" + prix_ticket + ", userID=" + userID + ", event_id_id=" + event_id_id + ", image=" + image + ", date_ticket=" + date_ticket + '}';
    }

    public event_ticket(int event_id_id,String matricule_event, String image, String date_ticket, String valide_ticket, String prix_ticket) {
        this.matricule_event = matricule_event;
         this.image = image;
        this.date_ticket = date_ticket;
        this.valide_ticket = valide_ticket;
        this.prix_ticket = prix_ticket;
     
        this.event_id_id = event_id_id;
       
    }

    public event_ticket() {
    }

    

    public String getMatricule_event() {
        return matricule_event;
    }

    public void setMatricule_event(String matricule_event) {
        this.matricule_event = matricule_event;
    }

    public String getValide_ticket() {
        return valide_ticket;
    }

    public void setValide_ticket(String valide_ticket) {
        this.valide_ticket = valide_ticket;
    }

    public String getPrix_ticket() {
        return prix_ticket;
    }

    public void setPrix_ticket(String prix_ticket) {
        this.prix_ticket = prix_ticket;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEventID() {
        return event_id_id;
    }

    public void setEventID(int event_id_id) {
        this.event_id_id = event_id_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate_ticket() {
        return date_ticket;
    }

    public void setDate_ticket(String date_ticket) {
        this.date_ticket = date_ticket;
    }
    
}