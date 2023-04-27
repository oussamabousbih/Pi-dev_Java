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
import entities.gestion_rdv.Appointment;
import services.gestion_rdv.exceptions.DeleteException;
import services.gestion_rdv.exceptions.InsertException;
import services.gestion_rdv.exceptions.UpdateException;
import utils.JdbcConnection;



/**
 *
 * @author rbaih
 */
public class CrudAppointment {
    
    
    //"Update ------------------------------------------------------------------------"
    
    public boolean UpdateAppointmentFields_reason_bookingState( Appointment app) throws UpdateException{
        
        // appointment must have doctor_id , patient_id can't be changed , (time_Slot_id may be changed for advanced option );  
        String sql ;
        sql = "UPDATE appointment app "
                + "SET app.reason=? , app.booking_state=? "
                + "WHERE app.id = ? ";
        
        try( JdbcConnection instanceJDBC = JdbcConnection.getInstance(); 
             PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
           ){
            
           preStatment.setString(1, app.getReason());
           preStatment.setString(2, app.getBooking_state());
           preStatment.setInt(3, app.getId());
           
           return preStatment.executeUpdate() == 1; // test if anything have been updated
           
        } catch (SQLException e) {
            throw new UpdateException(e.getMessage()+" "+e.getLocalizedMessage());
        }
    }
    
    
    // "INSERT ---------------------------------------------------------------------"
    // rely on sql knowlaedge
    public boolean Insert_AddAppointment_Requires_atMinimum_TimeSlot_and_Patient_Id( Appointment app, int time_slot_id , int patient_id  ) throws InsertException{
        
        String sql ="INSERT INTO appointment  "
                + "( " 
                + "time_slot_id, patient_id, "
                + "reason , booking_state , "
                + "hour, "
                + "doctor_id, "
                + "date "
                + ") "
                + "values( "
                + "? , ? ," 
                + " ? , ? ,"
                + "(SELECT ts.start_time FROM time_slot ts WHERE ts.id = ? ), "
                + "(SELECT cd.doctor_id  FROM time_slot ts JOIN calandar_day cd "
                + "  ON cd.id=ts.calandar_day_id WHERE ts.id = ? ) , "
                + "(SELECT cd.date FROM time_slot ts JOIN calandar_day cd "
                + "  ON cd.id=ts.calandar_day_id WHERE ts.id = ? ) "
                + "); ";
        
        try( JdbcConnection instanceJDBC = JdbcConnection.getInstance(); 
             PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ){
            
            
            //Data that we got from argumentsS
            preStatment.setInt(1 ,time_slot_id );
            preStatment.setInt(2 ,patient_id);
            preStatment.setString(3 ,app.getReason());
            preStatment.setString(4 ,app.getBooking_state());
            //Data we will get from some subQuery based on the relationship from TimeSlot
            //thats why we pass time_slot_id_for_all
            preStatment.setInt(5 ,time_slot_id );
            preStatment.setInt(6 ,time_slot_id );
            preStatment.setInt(7 ,time_slot_id );
            
            
            System.out.println(sql);
            return  preStatment.executeUpdate() == 1 ;

            
        } catch(SQLException e ){
            throw new InsertException(e.getLocalizedMessage()+""+e.getMessage());
        }
        
    }
    //Insert Heavily relies on data passed as argument Appointment
    public boolean insertAppointment( Appointment app) throws InsertException{
        String sql ="INSERT INTO appointments app "
                + "( " 
                + "app.time_slot_id, app.patient_id, app.reason , app.booking_state , "
                + "app.hour, app.date, app.doctor_id "
                + ") "
                + "values( "
                + "? , ? , ?, ? , "
                + "?, ? , ?"
                + "); ";
        
        try( JdbcConnection instanceJDBC = JdbcConnection.getInstance(); 
             PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ){

            //Data that we got from argumentsS
            preStatment.setInt(1 ,app.getPatient_id());
            preStatment.setInt(2 ,app.getCalandar_day_id() );
            preStatment.setString(3 ,app.getReason());
            preStatment.setString(4 ,app.getBooking_state());
            preStatment.setTime(5 ,java.sql.Time.valueOf(app.getHour()) );
            preStatment.setDate(6 ,java.sql.Date.valueOf(app.getDate()) );
            preStatment.setInt(7 ,app.getDoctor_id() );
            
            return  preStatment.executeUpdate() == 1 ;
 
        } catch(SQLException e ){
            throw new InsertException(e.getLocalizedMessage()+""+e.getMessage());
        }
    }
    
    //---------Delete_appointment------------------------------  
    public boolean DeleteAppointment( Appointment appointment) throws DeleteException{
        String sql="Delete From appointment where id = ? ";
        try(
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
            ){
            preStatment.setInt(1, appointment.getId()  );
            return preStatment.executeUpdate() == 1;
            
        }catch( SQLException e ){
            throw new DeleteException(e.getLocalizedMessage()+""+e.getMessage());
        }
    }
    
    //-------Select-Appointment -------------------------------------------------
    public Collection<Appointment> SelectAppointment( int appointment_id ) throws ConnectionOrPrepareStatmentException{
        String sql="SELECT * FROM appointment where id = ? ";
        try(
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
            ){
            
            preStatment.setInt(1, appointment_id);
            try(ResultSet rs = preStatment.executeQuery(); ){
                
                return this.fetchAppointmentData_fromResultSet(rs);
                
            } catch(InsertException e){
                throw new InsertException(e.getLocalizedMessage()+""+e.getMessage());
            }
            
        }catch( SQLException e ){
            throw new ConnectionOrPrepareStatmentException(e.getLocalizedMessage()+""+e.getMessage());
        }
    }
    
    public Collection<Appointment> SelectAppointment_based_on_timeSlot_one_to_one_relationship( int time_slot_id ) throws ConnectionOrPrepareStatmentException {
        String sql="SELECT * FROM appointment app where app.time_slot_id = ? ";
        try(
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
            ){
            
            preStatment.setInt(1, time_slot_id);
            try(ResultSet rs = preStatment.executeQuery(); ){
                
                return this.fetchAppointmentData_fromResultSet(rs);
                
            } catch(InsertException e){
                throw new InsertException(e.getLocalizedMessage()+""+e.getMessage());
            }
            
        }catch( SQLException e ){
            throw new ConnectionOrPrepareStatmentException(e.getLocalizedMessage()+""+e.getMessage());
        }
    }
    
     public Collection<Appointment> SelectAppointments_ByPatientId( int patient_id ) throws ConnectionOrPrepareStatmentException{
        String sql="SELECT * FROM appointment app "
                + "where app.patient_id = ? and Date >= CURRENT_DATE "
                + "ORDER BY ASC ;";
        try(
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
            ){
            
            preStatment.setInt(1, patient_id);
            try(ResultSet rs = preStatment.executeQuery(); ){
                
                return this.fetchAppointmentData_fromResultSet(rs);
                
            } catch(InsertException e){
                throw new InsertException(e.getLocalizedMessage()+""+e.getMessage());
            }
            
        }catch( SQLException e ){
            throw new ConnectionOrPrepareStatmentException(e.getLocalizedMessage()+""+e.getMessage());
        }
    }
     
    public Collection<Appointment> SelectAppointments_ByDoctor_id( int patient_id ) throws ConnectionOrPrepareStatmentException  {
        String sql="SELECT * FROM appointment app where app.patient_id = ? "
                + "ORDER BY ASC";
        try(
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);
            ){
            
            preStatment.setInt(1, patient_id);
            try(ResultSet rs = preStatment.executeQuery(); ){
                
                return this.fetchAppointmentData_fromResultSet(rs);
                
            } catch(InsertException e){
                throw new InsertException(e.getLocalizedMessage()+""+e.getMessage());
            }
            
        }catch( SQLException e ){
            throw new ConnectionOrPrepareStatmentException(e.getLocalizedMessage()+""+e.getMessage());
        }
    } 
    // ---- PRIVATE-COMMON-Methods ----------------
    // ---- PRIVATE-COMMON-Methods ----------------
    // ---- PRIVATE-COMMON-Methods ----------------
    
    private Collection<Appointment> fetchAppointmentData_fromResultSet(ResultSet rs ) throws SQLException{
        Collection<Appointment> appointments = new ArrayList<>();
        while ( rs.next() ) {
            Appointment app = new Appointment();
            
            app.setId( rs.getInt("id") );
            app.setDoctor_id( rs.getInt("doctor_id") );
            app.setDoctor_id( rs.getInt("patient_id") );
            app.setTime_slot_id( rs.getInt("time_slot_id") );
            app.setDate( rs.getDate("date").toLocalDate() );
            app.setHour( rs.getTime("hour").toLocalTime() );
            app.setReason(rs.getString("reason") );
            app.setBooking_state(rs.getString("booking_state") );
            
            appointments.add(app);
               
        }
//        System.out.println( appointments );
        return appointments;   
    }
}
