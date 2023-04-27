/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import entities.event_ticket;
import entities.event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;


/**
 *
 * @author utilisateur
 */
public class ticketService implements IService<event_ticket>  {
        Connection cnx;

     public ticketService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(event_ticket t) throws SQLException {
        String req = "INSERT INTO event_ticket(event_id_id,matricule_event,image,date_ticket,valide_ticket,prix_ticket) VALUES("
               + "'"  + t.getEventID()+ "','"+ t.getMatricule_event()+ "','" +t.getImage()+ "','" +t.getDate_ticket()+   "','" +t.getValide_ticket()+ "','" +t.getPrix_ticket()+ "'"+   ")";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);  
    }
    


    @Override
    public void modifier(event_ticket t) throws SQLException {
       
          
      String req = "UPDATE event_ticket SET matricule_event = ? ,image = ?,date_ticket = ?,valide_ticket = ?,prix_ticket = ?,event_id_id = ? WHERE Id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getMatricule_event());
        ps.setString(2, t.getImage());
        ps.setString(3, t.getDate_ticket());
        ps.setString(4, t.getValide_ticket());
        ps.setString(5, t.getPrix_ticket());
       
        ps.setInt(6, t.getEventID());
        ps.setInt(7, t.getId());
        
         
        ps.executeUpdate();       }


   
    

    @Override
    public void supprimer(event_ticket t) throws SQLException {
        String querry = "DELETE FROM event_ticket WHERE id = '"+t.getId()+"'";
                Statement stm = cnx.createStatement();

                stm.executeUpdate(querry);
    }


    @Override
    public List<event_ticket> recuperer() throws SQLException {
        
       List<event_ticket> event_ticket = new ArrayList<>();
        String s = "select * from event_ticket";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            event_ticket p = new event_ticket();

            p.setId(rs.getInt("Id"));
            p.setMatricule_event(rs.getString("Matricule_event"));
            p.setImage(rs.getString("Image"));
            p.setDate_ticket(rs.getString("Date_ticket"));
            p.setValide_ticket(rs.getString("Valide_ticket"));
            p.setPrix_ticket(rs.getString("Prix_ticket"));
         
            p.setEventID(rs.getInt("event_id_id"));

            event_ticket.add(p);

        }
        return event_ticket;    }

    }

