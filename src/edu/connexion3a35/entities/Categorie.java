/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msi
 */
public class Categorie {
    private int id;
    private String nom_Categorie;



    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    private List<Produit> produits=new ArrayList<>();

    public int getNbProduits() {
        return produits.stream().mapToInt(p -> 1).sum();
    }
    public Categorie(int id, String nom_Categorie) {
        this.id = id;
        this.nom_Categorie = nom_Categorie;
        this.produits = new ArrayList<>();

    }

    public Categorie(String nom_Categorie) {
        this.nom_Categorie = nom_Categorie;
        this.produits = new ArrayList<>();

    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public String getNom_Categorie() {
        return nom_Categorie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom_Categorie(String nom_Categorie) {
        this.nom_Categorie = nom_Categorie;
    }
    public Categorie(int id, String nom_Categorie, List<Produit> produits) {
        this.id = id;
        this.nom_Categorie = nom_Categorie;
        this.produits = produits;
    }
    
    @Override
    public String toString() {
        return nom_Categorie;
    }
    
    
   
 
            
}
