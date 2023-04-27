/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import entities.event;
import entities.event_ticket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.eventService;
import services.ticketService;
import utils.MyDB;

/**
 *
 * @author 
 */
public class test {

    public static void main(String[] args) throws ParseException {
       
        
        try {
            //event c = new event("zied", "a", "e", "2023-05-07", "2023-05-07", "e","valide");
            // event c = new event("zied", "a", "e", "2023-05-07", "2023-05-07", "e","valide",47);
           //eventService cs = new eventService();
            // cs.ajouter(c);
            //cs.modifier(c);
            //cs.supprimer(c);
            //System.out.println(cs.recuperer());
            
            
             //event_ticket t = new event_ticket(29,"12ACC", "a", "2023-05-07", "valide", "3DT");
             event_ticket t = new event_ticket("55V22", "a", "2023-05-07", "valide", "3DT",29,62);
            ticketService ts = new ticketService();
             //ts.ajouter(t);
            //ts.modifier(t);
              ts.supprimer(t);
            System.out.println(ts.recuperer());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}