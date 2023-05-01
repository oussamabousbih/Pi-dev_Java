/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.DossierMedical;
import java.util.List;

/**
 *
 * @author AOUADI HADIL
 */
public interface InterfaceDossier {
    
    public void ajouterDossier(DossierMedical D);
    public void ajouterDossier2(DossierMedical D);
    public void modifierDossier(DossierMedical D , int id);
    public void supprimerDossier(int id);
    public List<DossierMedical> afficherDossier();
}
