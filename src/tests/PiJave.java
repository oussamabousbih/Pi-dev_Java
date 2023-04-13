/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Utils.MyConnection;
import entities.DossierMedical;
import entities.Ficheconsultation;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import services.CRUDdossier;
import services.CRUDfiche;

/**
 *
 * @author AOUADI HADIL
 */
public class PiJave {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
       MyConnection cc = MyConnection.getInstance();
   

       DossierMedical D = new DossierMedical("test","ttt",LocalDate.parse("2000-03-22"),"mmm@gmail.com","rrrn","tttt","ffff","mmmm","kkkk");
       CRUDdossier dos = new CRUDdossier();
       
      //dos.ajouterDossier(D);
     System.out.println( dos.afficherDossier());
          D.setFirstName("test2");

     // dos.modifierDossier(D,1);
     
   // dos.supprimerDossier(59);

       
       
       Ficheconsultation F = new Ficheconsultation (LocalDate.parse("2000-05-15"),"test","aaa","jjj","kkk","ttt");
       CRUDfiche fic = new CRUDfiche ();
      // fic.ajouterFiche(F);
       System.out.println( fic.afficherFiche());
      // F.setFirstName("test 2");
    // fic.modifierFiche(F,2);
     // F.setDate_consultation(LocalDate.parse(F.getDate_consultation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
     // F.setFirstName("nouveau nom");
     // F.setLastName("nouveau prenom");
     // F.setSpecialite("nouveau specialite");
     // F.setTraitement( "nouveau traitement");
     // F.setReccomendation("nouveau recom");
   //  fic.supprimerFiche(4);
    }    
}
