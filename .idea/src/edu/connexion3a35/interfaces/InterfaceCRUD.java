/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.interfaces;

import java.util.List;

/**
 *
 * @author karra
 */
public interface InterfaceCRUD<T> {
    public void ajouterEntitee(T t);
    public List<T> listeDesEntites();
}
