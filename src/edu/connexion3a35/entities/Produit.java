/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.entities;

import javafx.scene.image.Image;

/**
 *
 * @author msi
 */
public class Produit {
    private int id ,quantite,categorie_produit;
    private float prix;

    public Produit( String nom_produit,float quantite, String description, String image_produit) {
        this.nom_produit = nom_produit;
        this.prix = quantite;
        this.description = description;
        this.image_produit = image_produit;
    }

    public boolean testimagenull() {
if (image_produit==null)
            return true;
        else
            if (image_produit.equals(""))
                return true;
            else
                return false;



    }

    public int getCategorie_produit() {
        return categorie_produit;
    }

    public void setCategorie_produit(int categorie_produit) {
        this.categorie_produit = categorie_produit;
    }
    
    private String nom_produit,marque,description,image_produit;
    Categorie cat;

    public Produit(int id, int quantite, int categorie_produit, float prix, String nom_produit, String marque, String description, String image_produit, Categorie cat) {
        this.id = id;
        this.quantite = quantite;
        this.categorie_produit = categorie_produit;
        this.prix = prix;
        this.nom_produit = nom_produit;
        this.marque = marque;
        this.description = description;
        this.image_produit = image_produit;
        this.cat = cat;
    }

    public Produit(int quantite, int categorie_produit, float prix, String nom_produit, String marque, String description, String image_produit) {
        this.quantite = quantite;
        this.categorie_produit = categorie_produit;
        this.prix = prix;
        this.nom_produit = nom_produit;
        this.marque = marque;
        this.description = description;
        this.image_produit = image_produit;
    }

    public Produit(int quantite, int categorie_produit, float prix, String nom_produit, String marque, String description, String image_produit, Categorie cat) {
        this.quantite = quantite;
        this.categorie_produit = categorie_produit;
        this.prix = prix;
        this.nom_produit = nom_produit;
        this.marque = marque;
        this.description = description;
        this.image_produit = image_produit;
        this.cat = cat;
    }
   
    
    
    
    public Categorie getCat() {
        return cat;
    }


    public void setCat(Categorie cat) {
        this.cat = cat;
    }



 

 

   
 
    public Produit() {
    }

    
    public Produit( int quantite, float prix, String nom_produit, String marque, String description, String image_produit, Categorie cat) {
        this.quantite = quantite;
        this.prix = prix;
        this.nom_produit = nom_produit;
        this.marque = marque;
        this.description = description;
        this.image_produit = image_produit;
        this.cat = cat;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

   

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_produit(String image_produit) {
        this.image_produit = image_produit;
    }

    public int getId() {
        return id;
    }

    public int getQuantite() {
        return quantite;
    }

    public float getPrix() {
        return prix;
    }

   

    public String getNom_produit() {
        return nom_produit;
    }

    public String getMarque() {
        return marque;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_produit() {
        return image_produit;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", quantite=" + quantite + ", prix=" + prix + ", nom_produit=" + nom_produit + ", marque=" + marque + ", description=" + description + ", image_produit=" + image_produit + ", categorie_produit=" + categorie_produit + ", cat=" + cat + '}';
    }
//    @Override
//    public String toString() {
//        return "/images/"+image_produit;
//    }

    


 
}
