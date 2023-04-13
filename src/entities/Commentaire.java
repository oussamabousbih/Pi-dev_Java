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
public class Commentaire {
    private int id,id_Auteur,id_Article;
    private String contenu,created_at;

    public Commentaire() {
    }

    public Commentaire( int id_Article,int id_Auteur, String contenu) {
        this.id_Article = id_Article;
        this.id_Auteur = id_Auteur;
        this.contenu = contenu;
    }

    public Commentaire(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public Commentaire(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Auteur() {
        return id_Auteur;
    }

    public void setId_Auteur(int id_Auteur) {
        this.id_Auteur = id_Auteur;
    }

    public int getId_Article() {
        return id_Article;
    }

    public void setId_Article(int id_Article) {
        this.id_Article = id_Article;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", id_Auteur=" + id_Auteur + ", id_Article=" + id_Article + ", contenu=" + contenu + ", created_at=" + created_at + '}';
    }
    
    
    
}
