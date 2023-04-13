/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author mazee
 */
public class Article {
    private int id,views,id_Auteur;
    private String sujet,contenu,image,created_at;

    public Article() {
    }


    public Article(int id,int id_Auteur, int views, String sujet, String contenu, String image, String created_at) {
        this.id = id;
        this.id_Auteur = id_Auteur;
        this.views = views;
        this.sujet = sujet;
        this.contenu = contenu;
        this.image = image;
        this.created_at = created_at;
    }

    public Article(int id_Auteur,String sujet, String contenu, String image) {
        this.id_Auteur = id_Auteur;
        this.sujet = sujet;
        this.contenu = contenu;
        this.image = image;
    }
    
    public Article(int id,String sujet, String contenu) {
        this.id = id;
        this.sujet = sujet;
        this.contenu = contenu;
    }
        public Article(int id) {
        this.id = id;

    }


    public int getId_Auteur() {
        return id_Auteur;
    }

    public void setId_Auteur(int id_Auteur) {
        this.id_Auteur = id_Auteur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", views=" + views + ", id_Auteur=" + id_Auteur + ", sujet=" + sujet + ", contenu=" + contenu + ", image=" + image + ", created_at=" + created_at + '}';
    }

   



  
    
}
