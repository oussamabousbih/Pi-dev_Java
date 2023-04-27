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
import entities.gestion_rdv.DummyPatientAppointment;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import services.gestion_rdv.exceptions.SelectExecuteQueryException;
import utils.JdbcConnection;

/**
 *
 * @author rbaih
 */
public class CrudPatientAppointment { // useful for emails illustrating appointment doctor_side

    /**
     * this method returns a list of DummyPatient contains some data of patient
     * + date_of_calandar + id appointment + id timeslot
     *
     * @param calandar_day_id
     * @return empty || full dummyPatientAppointmentDoctorSide collection , not
     * that some fields are null
     *
     * @throws services.gestion_rdv.exceptions.SelectExecuteQueryException
     * @throws main.services.gestion_rdv.ConnectionOrPrepareStatmentException
     */
    public Collection<DummyPatientAppointmentDoctorSide> get_Patients_Email_name_date_time_from_CalandarDay(int calandar_day_id) throws SelectExecuteQueryException, ConnectionOrPrepareStatmentException {

        String sql = " SELECT u.id , u.email, u.first_name , u.last_name ,u.phone_number ,u.gender, u.age , "
                + "app.id as appointment_id , app.time_slot_id, app.doctor_id, "
                + "app.reason, app.booking_state,  "
                + "cd.date , "
                + " ts.calandar_day_id, ts.start_time "
                + "from calandar_day cd "
                + "join time_slot ts on ts.calandar_day_id= cd.id "
                + "join appointment app on app.time_slot_id = ts.id "
                + "join user u on u.id = app.patient_id "
                + "where cd.id = ? "
                + "ORDER BY ts.start_time;";

        try (
                JdbcConnection instanceJdbc = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJdbc.getConnection().prepareStatement(sql);) {

            preStatment.setInt(1, calandar_day_id);

            try (ResultSet rs = preStatment.executeQuery()) {
                Collection<DummyPatientAppointmentDoctorSide> patients = new ArrayList<>();
                while (rs.next()) {
                    DummyPatientAppointmentDoctorSide dummyPatient = new DummyPatientAppointmentDoctorSide();

                    dummyPatient.setId(rs.getInt("id"));
                    dummyPatient.setDoctor_id(rs.getInt("doctor_id"));
                    dummyPatient.setAppoinment_id(rs.getInt("appointment_id"));
                    dummyPatient.setTime_slot_id(rs.getInt("time_slot_id"));
                    dummyPatient.setCalandar_day_id(rs.getInt("calandar_day_id"));
                    dummyPatient.setDate(rs.getDate("date").toLocalDate());
                    dummyPatient.setTime(rs.getTime("start_time").toLocalTime());
                    dummyPatient.setFirst_name(rs.getString("first_name"));
                    dummyPatient.setLast_name(rs.getString("last_name"));
                    dummyPatient.setEmail(rs.getString("email"));
                    dummyPatient.setPhone_number(rs.getString("phone_number"));
                    dummyPatient.setGender(rs.getString("gender"));
                    dummyPatient.setAge(rs.getString("age"));
                    dummyPatient.setReason(rs.getString("reason"));
                    dummyPatient.setBooking_state(rs.getString("booking_state"));

                    patients.add(dummyPatient);

                }
                System.out.println(patients);
                return patients;

            } catch (SQLException e) {
                System.err.println("error on the lesvel of ResultSet object on get_Patient_Email_name_date_time_from() ==> " + e.getMessage());
                throw new SelectExecuteQueryException("Exception during executeQuery  ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("get_Patient_Email_name_date_time_from() error occured  ==> " + e.getMessage());
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());

        }
    }// end method get_Patient_Email_name_date_time_from_CalandarDay()

    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //*************************************************************************************************************************
    public Collection<DummyPatientAppointment> DummyPatientAppointment(Integer appointment_id) throws ConnectionOrPrepareStatmentException, SelectExecuteQueryException {
        String sql = "SELECT u.id, u.email, u.first_name, u.last_name, u.phone_number, u.gender, "
                + "u.age, app.id as appointment_id, app.time_slot_id, ts.calandar_day_id, app.date, ts.start_time, "
                + " app.reason, app.booking_state, "
                + " app.doctor_id, d.last_name AS doctor_last_name, d.email as doctor_email, "
                + " d.phone_number AS doctor_phone_number "
                + " FROM appointment app JOIN user u ON u.id = app.patient_id "
                + " JOIN user d ON d.id = app.doctor_id JOIN time_slot ts ON ts.id = app.time_slot_id "
                + " WHERE app.id = ? ; ";

        try (JdbcConnection instanceJdBC = JdbcConnection.getInstance();
                PreparedStatement preStatement = instanceJdBC.getConnection().prepareStatement(sql);) {

            preStatement.setInt(1, appointment_id);
            try (ResultSet rs = preStatement.executeQuery()) {

                Collection<DummyPatientAppointment> patients = new ArrayList<>();
                while (rs.next()) {

                    DummyPatientAppointment dummyPatient = new DummyPatientAppointment();
                    //appointment timeSlot calandar useful
                    dummyPatient.setAppoinment_id(rs.getInt("appointment_id"));
                    dummyPatient.setTime_slot_id(rs.getInt("time_slot_id"));
                    dummyPatient.setCalandar_day_id(rs.getInt("calandar_day_id"));
                    dummyPatient.setDate(rs.getDate("date").toLocalDate());
                    dummyPatient.setTime(rs.getTime("start_time").toLocalTime());
                    dummyPatient.setReason(rs.getString("reason"));
                    dummyPatient.setBooking_state(rs.getString("booking_state"));
                    //user data
                    dummyPatient.setId(rs.getInt("id"));
                    dummyPatient.setFirst_name(rs.getString("first_name"));
                    dummyPatient.setLast_name(rs.getString("last_name"));
                    dummyPatient.setEmail(rs.getString("email"));
                    dummyPatient.setPhone_number(rs.getString("phone_number"));
                    dummyPatient.setGender(rs.getString("gender"));
                    dummyPatient.setAge(rs.getString("age"));
                    // doctor data
                    dummyPatient.setDoctor_id(rs.getInt("doctor_id"));
                    dummyPatient.setDoctor_last_name(rs.getString("doctor_last_name"));
                    dummyPatient.setDoctor_email(rs.getString("doctor_email"));
                    dummyPatient.setDoctor_phone_number(rs.getString("doctor_phone_number"));
                    //adding to collection
                    patients.add(dummyPatient);

                }
                System.out.println(patients);
                return patients;
            } catch (SQLException e) {
                System.err.println(e.getMessage() + " \n ==> " + e.getLocalizedMessage());
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " \n ==> " + e.getLocalizedMessage());
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }
    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //*************************************************************************************************************************

    /**
     * element inside Collection are of type (DummyPatientAppointment) can be
     * casted back to it
     *
     * @param doctor
     * @return return collection contains objects able to be cust to
     * (DummyPatientAppointment)
     * @throws ConnectionOrPrepareStatmentException
     * @throws SelectExecuteQueryException
     */
    public Collection<DummyPatientAppointmentDoctorSide> get_DummyPatientAppointment_doctor(DummyUser doctor, boolean dateToday,
            String name, String email, String phone) throws ConnectionOrPrepareStatmentException, SelectExecuteQueryException {
        String sql = "SELECT u.id, u.email, u.first_name, u.last_name, u.phone_number, u.gender, "
                + "u.age, app.id as appointment_id, app.time_slot_id, ts.calandar_day_id, app.date, ts.start_time, "
                + " app.reason, app.booking_state, "
                + " app.doctor_id, d.last_name AS doctor_last_name, d.email as doctor_email, "
                + " d.phone_number AS doctor_phone_number "
                + " FROM appointment app "
                + "JOIN user u ON u.id = app.patient_id "
                + " JOIN user d ON d.id = app.doctor_id "
                + "JOIN time_slot ts ON ts.id = app.time_slot_id "
                + "JOIN calandar_day cd ON cd.id= ts.calandar_day_id  "
                + " WHERE app.doctor_id = ?  ";
        if (dateToday) {
            sql += " and  cd.date >= CURRENT_DATE ";
        }
        //email highest priority unique then phone then name
        if (!email.isEmpty()) {
            sql += " and u.email LIKE ?  ";
            phone = ""; // make them empty
            name = ""; // make them empty
        }
        if (!phone.isEmpty()) { // enter only if email not passed 
            sql += " and u.phone_number = ?  ";
            name = "";
        }
        if (!name.isEmpty()) {
            sql += " and u.first_name LIKE  ?  OR u.last_name LIKE  ?  ";
        }
        sql+=" ORDER BY cd.date ASC , ts.start_time ASC ;";
        

        try (JdbcConnection instanceJdBC = JdbcConnection.getInstance();
                PreparedStatement preStatement = instanceJdBC.getConnection().prepareStatement(sql);) {

            preStatement.setInt(1, doctor.getId());

           
            if (!email.isEmpty()) {
                 preStatement.setString(2, "%"+email+"%");
            }
            if (!phone.isEmpty()) { // enter only if email not passed 
                preStatement.setString(2, phone);
            }
            if (!name.isEmpty()) {
                preStatement.setString(2, "%"+name+"%");
                preStatement.setString(3, "%"+name+"%");
            }

            try (ResultSet rs = preStatement.executeQuery()) {

                Collection<DummyPatientAppointmentDoctorSide> patients = new ArrayList<>();
                while (rs.next()) {

                    DummyPatientAppointment dummyPatient = new DummyPatientAppointment();
                    //appointment timeSlot calandar useful
                    dummyPatient.setAppoinment_id(rs.getInt("appointment_id"));
                    dummyPatient.setTime_slot_id(rs.getInt("time_slot_id"));
                    dummyPatient.setCalandar_day_id(rs.getInt("calandar_day_id"));
                    dummyPatient.setDate(rs.getDate("date").toLocalDate());
                    dummyPatient.setTime(rs.getTime("start_time").toLocalTime());
                    dummyPatient.setReason(rs.getString("reason"));
                    dummyPatient.setBooking_state(rs.getString("booking_state"));
                    //user data
                    dummyPatient.setId(rs.getInt("id"));
                    dummyPatient.setFirst_name(rs.getString("first_name"));
                    dummyPatient.setLast_name(rs.getString("last_name"));
                    dummyPatient.setEmail(rs.getString("email"));
                    dummyPatient.setPhone_number(rs.getString("phone_number"));
                    dummyPatient.setGender(rs.getString("gender"));
                    dummyPatient.setAge(rs.getString("age"));
                    // doctor data
                    dummyPatient.setDoctor_id(rs.getInt("doctor_id"));
                    dummyPatient.setDoctor_last_name(rs.getString("doctor_last_name"));
                    dummyPatient.setDoctor_email(rs.getString("doctor_email"));
                    dummyPatient.setDoctor_phone_number(rs.getString("doctor_phone_number"));
                    //adding to collection

                    patients.add(dummyPatient);

                }
                System.out.println(patients);
                return patients;
            } catch (SQLException e) {
                System.err.println(e.getMessage() + " \n ==> " + e.getLocalizedMessage());
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " \n ==> " + e.getLocalizedMessage());
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }

    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //*************************************************************************************************************************
    /**
     *
     * @param user
     * @return return collection contains objects able to be cust to
     * (DummyPatientAppointment)
     * @throws ConnectionOrPrepareStatmentException
     * @throws SelectExecuteQueryException
     */
    public Collection<DummyPatientAppointment> get_DummyPatientAppointment_user(DummyUser user) throws ConnectionOrPrepareStatmentException {

        String sql = "SELECT u.id, u.email, u.first_name, u.last_name, u.phone_number, u.gender, "
                + "u.age, app.id as appointment_id, app.time_slot_id, ts.calandar_day_id, app.date, ts.start_time, "
                + " app.reason, app.booking_state, "
                + " app.doctor_id, d.last_name AS doctor_last_name, d.email as doctor_email, "
                + " d.phone_number AS doctor_phone_number "
                + " FROM appointment app "
                + "JOIN user u ON u.id = app.patient_id "
                + " JOIN user d ON d.id = app.doctor_id "
                + "JOIN time_slot ts ON ts.id = app.time_slot_id "
                + "JOIN calandar_day cd ON cd.id= ts.calandar_day_id  "
                + " WHERE app.patient_id = ? AND cd.date >= CURRENT_DATE  "
                + " ORDER BY cd.date ASC ,  ts.start_time ASC ;";

        try (JdbcConnection instanceJdBC = JdbcConnection.getInstance();
                PreparedStatement preStatement = instanceJdBC.getConnection().prepareStatement(sql);) {

            preStatement.setInt(1, user.getId());
            try (ResultSet rs = preStatement.executeQuery()) {

                Collection<DummyPatientAppointment> patients = new ArrayList<>();
                while (rs.next()) {

                    DummyPatientAppointment dummyPatient = new DummyPatientAppointment();
                    //appointment timeSlot calandar useful
                    dummyPatient.setAppoinment_id(rs.getInt("appointment_id"));
                    dummyPatient.setTime_slot_id(rs.getInt("time_slot_id"));
                    dummyPatient.setCalandar_day_id(rs.getInt("calandar_day_id"));
                    dummyPatient.setDate(rs.getDate("date").toLocalDate());
                    dummyPatient.setTime(rs.getTime("start_time").toLocalTime());
                    dummyPatient.setReason(rs.getString("reason"));
                    dummyPatient.setBooking_state(rs.getString("booking_state"));
                    //user data
                    dummyPatient.setId(rs.getInt("id"));
                    dummyPatient.setFirst_name(rs.getString("first_name"));
                    dummyPatient.setLast_name(rs.getString("last_name"));
                    dummyPatient.setEmail(rs.getString("email"));
                    dummyPatient.setPhone_number(rs.getString("phone_number"));
                    dummyPatient.setGender(rs.getString("gender"));
                    dummyPatient.setAge(rs.getString("age"));
                    // doctor data
                    dummyPatient.setDoctor_id(rs.getInt("doctor_id"));
                    dummyPatient.setDoctor_last_name(rs.getString("doctor_last_name"));
                    dummyPatient.setDoctor_email(rs.getString("doctor_email"));
                    dummyPatient.setDoctor_phone_number(rs.getString("doctor_phone_number"));
                    //adding to collection

                    patients.add(dummyPatient);

                }
                System.out.println(patients);
                return patients;
            } catch (SQLException e) {
                System.err.println(e.getMessage() + " \n ==> " + e.getLocalizedMessage());
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " \n ==> " + e.getLocalizedMessage());
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }

    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //*************************************************************************************************************************
}
