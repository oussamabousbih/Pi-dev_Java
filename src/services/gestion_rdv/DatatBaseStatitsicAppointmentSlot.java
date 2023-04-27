/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.gestion_rdv;

import entities.gestion_rdv.StatisticsAppointmentSlot;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.JdbcConnection;

/**
 *
 * @author rbaih
 */
public class DatatBaseStatitsicAppointmentSlot {

    public static StatisticsAppointmentSlot countTypeAppointments(int doctor_id) {

        int appointmentReasonCheckup=-1;
        int appointmentReasonAppointmen=-1;
        int slotsWhereStatusNotAvailabeAndBooked=-1;

 
        
        
        String sql = "SELECT "
                + "SUM(CASE WHEN app.reason = 'check-up' THEN 1 ELSE 0 END) AS checkup_appointments "
                + " , "
                + "SUM(CASE WHEN app.reason = 'appointment' THEN 1 ELSE 0 END) AS appointment_appointment "
                + " FROM  appointment app "
                + " JOIN time_slot ts ON  ts.id = app.time_slot_id "
                + " JOIN calandar_day cd ON cd.id = ts.calandar_day_id  "
                + " where cd.doctor_id =  ?  ";

        try (JdbcConnection jdbcConnetion = JdbcConnection.getInstance();
                PreparedStatement preStatment = jdbcConnetion.getConnection().prepareStatement(sql);) {
            preStatment.setInt(1, doctor_id);

            try (ResultSet rs = preStatment.executeQuery();) {
                
                if(rs.next()){
                    appointmentReasonCheckup = rs.getInt("checkup_appointments");
                    appointmentReasonAppointmen=rs.getInt("appointment_appointment");
                }
                 
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
        //****************************************************************************************
        //*****TimeSlot Booked Now ***********************************************************************************
        
        sql = "SELECT COUNT(*) as slots_booked "
                + "FROM  time_slot ts "
                + "JOIN calandar_day cd ON cd.id=ts.calandar_day_id  "
                + "where cd.doctor_id =  ? and ts.status='not-available' and ts.reason='booked' ";

        try (JdbcConnection jdbcConnetion = JdbcConnection.getInstance();
                PreparedStatement preStatment = jdbcConnetion.getConnection().prepareStatement(sql);) {
            preStatment.setInt(1, doctor_id);

            try (ResultSet rs = preStatment.executeQuery();) {
                
                if(rs.next()){
                    slotsWhereStatusNotAvailabeAndBooked = rs.getInt("slots_booked");
                }
                 
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
        
        //***** Storing Results in object ****************
        
        StatisticsAppointmentSlot objAppointmentStats = new StatisticsAppointmentSlot(slotsWhereStatusNotAvailabeAndBooked, appointmentReasonCheckup, appointmentReasonAppointmen);
        System.out.println(objAppointmentStats);
        return objAppointmentStats ;
    }

}
