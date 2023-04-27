/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
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
public class eventService implements IService<event>  {
        Connection cnx;

     public eventService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(event t) throws SQLException {
        String req = "INSERT INTO event(nom_event,discription_event,image_event,date_debut_event,date_fin_event,adresse_event,status) VALUES("
               + "'"  + t.getNom_event()+ "','" +t.getDiscription_event()+ "','" +t.getImage_event()+   "','" +t.getDate_debut_event()+ "','" +t.getDate_fin_event()+ "','" +t.getAdresse_event()+ "','"+   t.getStatus()+  "'"+")";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);  
    }
    


    @Override
    public void modifier(event t) throws SQLException {
       
          
      String req = "UPDATE event SET nom_event = ? ,discription_event = ?,image_event = ?,date_debut_event = ?,date_fin_event = ?,adresse_event = ?,status = ? WHERE Id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getNom_event());
ps.setString(2, t.getDiscription_event());
ps.setString(3, t.getImage_event());
ps.setString(4, t.getDate_debut_event());
ps.setString(5, t.getDate_fin_event());
ps.setString(6, t.getAdresse_event());
ps.setString(7, t.getStatus());
ps.setInt(8, t.getId());
        
         
        ps.executeUpdate();       }


   
    

    @Override
    public void supprimer(event t) throws SQLException {
        String querry = "DELETE FROM event WHERE id = '"+t.getId()+"'";
                Statement stm = cnx.createStatement();

                stm.executeUpdate(querry);
    }


    @Override
    public List<event> recuperer() throws SQLException {
        
       List<event> event = new ArrayList<>();
        String s = "select * from event";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            event p = new event();

            p.setId(rs.getInt("Id"));
            p.setNom_event(rs.getString("Nom_event"));
            p.setDiscription_event(rs.getString("Discription_event"));
            p.setImage_event(rs.getString("Image_event"));
            p.setDate_debut_event(rs.getString("Date_debut_event"));
            p.setDate_fin_event(rs.getString("Date_fin_event"));
            p.setAdresse_event(rs.getString("Adresse_event"));
            p.setStatus(rs.getString("Status"));

            event.add(p);

        }
        return event;    }

    }

