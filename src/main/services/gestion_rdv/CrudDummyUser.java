/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.gestion_rdv;

import main.services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import main.entities.DummyUser;
import main.entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import main.services.gestion_rdv.exceptions.SelectExecuteQueryException;
import utils.JdbcConnection;

/**
 *
 * @author rbaih
 */
public class CrudDummyUser {

    public DummyUser get_dummyUserData(int user_id) throws ConnectionOrPrepareStatmentException{

        String sql = " SELECT u.id , u.email, u.first_name , u.last_name ,u.phone_number , u.roles "
                + "FROM user u "
                + "WHERE u.id = ? ;";

        try (
                JdbcConnection instanceJdbc = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJdbc.getConnection().prepareStatement(sql);) {

            preStatment.setInt(1, user_id);

            try (ResultSet rs = preStatment.executeQuery()) {

                DummyUser dummyUser = new DummyUser();
                if (rs.next()) {
                    dummyUser.setId(rs.getInt("id"));
                    dummyUser.setFirst_name(rs.getString("first_name"));
                    dummyUser.setLast_name(rs.getString("last_name"));
                    dummyUser.setEmail(rs.getString("email"));
                    dummyUser.setPhone_number(rs.getString("phone_number"));
                    dummyUser.setRoles(rs.getString("roles"));

                    System.out.println(dummyUser);
                    return dummyUser;
                }
                return null;

            } catch (SQLException e) {
                System.err.println("error on the lesvel of ResultSet object on get_Patient_Email_name_date_time_from() ==> " + e.getMessage());
                throw new SelectExecuteQueryException("Exception during executeQuery  ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("get_Patient_Email_name_date_time_from() error occured  ==> " + e.getMessage());
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());

        }
    }// end

}
