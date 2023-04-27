/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.gestion_rdv;

import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import entities.DummyUser;
import services.gestion_rdv.exceptions.InsertException;
import utils.JdbcConnection;

/**
 *
 * @author rbaih
 */
public class CrudDoctorWithCalandars {
    
    
     public Collection<DummyUser> SelectDoctors_withCalandarDays( ) throws ConnectionOrPrepareStatmentException  {
        String sql=" SELECT u.id, u.email, u.first_name, u.last_name, u.phone_number, u.roles " +
                " FROM user u " +
                " WHERE " +
                " u.id IN (SELECT  cd.doctor_id FROM calandar_day cd " +
                "          where cd.date >= CURRENT_DATE ) " +
                " ORDER BY u.last_name ASC ;";
        
        try(
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
            ){
            
            
            try(ResultSet rs = preStatment.executeQuery(); ){
                
                return this.fetchDummyUserData(rs);
                
            } catch(InsertException e){
                throw new InsertException("ResultSet_level"+e.getLocalizedMessage()+""+e.getMessage());
            }
            
        }catch( SQLException e ){
            throw new ConnectionOrPrepareStatmentException(e.getLocalizedMessage()+""+e.getMessage());
        }
    } 
    // ---- PRIVATE-COMMON-Methods ----------------
    // ---- PRIVATE-COMMON-Methods ----------------
    // ---- PRIVATE-COMMON-Methods ----------------
    
    private Collection<DummyUser> fetchDummyUserData(ResultSet rs ) throws SQLException{
        Collection<DummyUser> dummyUsers = new ArrayList<>();
        while ( rs.next() ) {
            DummyUser user = new DummyUser();
            
            user.setId( rs.getInt("id") );
            user.setEmail(rs.getString("email") );
            user.setFirst_name(rs.getString("first_name") );
            user.setLast_name(rs.getString("last_name") );
            user.setPhone_number(rs.getString("phone_number") );
            user.setRoles(rs.getString("roles") );
            
            
            dummyUsers.add(user);
               
        }
//        System.out.println( dummyUsers );
        return dummyUsers;   
    }
    
}
