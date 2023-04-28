/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author Yasmine Rajhi
 */
public class Service {
    private int id,type_service_id;
    private String NomService, Proprietaire, id_type, Prix;
    private Date date_debut, date_fin;

    public Service() {
    }

    public Service(int id, int type_service_id, String NomService, String Proprietaire, String id_type, String Prix, Date date_debut, Date date_fin) {
        this.id = id;
        this.type_service_id = type_service_id;
        this.NomService = NomService;
        this.Proprietaire = Proprietaire;
        this.id_type = id_type;
        this.Prix = Prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Service(int id, String NomService, String Proprietaire, String id_type, String Prix, Date date_debut, Date date_fin) {
        this.id = id;
        this.NomService = NomService;
        this.Proprietaire = Proprietaire;
        this.id_type = id_type;
        this.Prix = Prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }
    
    

    public int getType_service_id() {
        return type_service_id;
    }

    public void setType_service_id(int type_service_id) {
        this.type_service_id = type_service_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomService() {
        return NomService;
    }

    public void setNomService(String NomService) {
        this.NomService = NomService;
    }

    public String getProprietaire() {
        return Proprietaire;
    }

    public void setProprietaire(String Proprietaire) {
        this.Proprietaire = Proprietaire;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String Prix) {
        this.Prix = Prix;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "id=" + id + ", NomService=" + NomService + ", Proprietaire=" + Proprietaire + ", id_type=" + id_type + ", Prix=" + Prix + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
    }
    
}
