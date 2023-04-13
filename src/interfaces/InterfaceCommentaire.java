/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;


import entities.Commentaire;
import java.util.List;

/**
 *
 * @author mazee
 */
public interface InterfaceCommentaire {
    public void ajouterCommentaire(Commentaire C);
    public void modifierCommentaire(Commentaire C);
    public void supprimerCommentaire(Commentaire C);
    public List<Commentaire> afficherCommentaire();
}
