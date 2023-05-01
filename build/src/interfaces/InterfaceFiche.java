/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Ficheconsultation;
import java.util.List;

/**
 *
 * @author AOUADI HADIL
 */
public interface InterfaceFiche {
    
    public void ajouterFiche(Ficheconsultation F);
    public void ajouterFiche2(Ficheconsultation F);
    public void modifierFiche(Ficheconsultation F ,int id);
    public void supprimerFiche( int id);
    public List<Ficheconsultation > afficherFiche();
    
}
