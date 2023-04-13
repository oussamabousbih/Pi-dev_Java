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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import main.entities.gestion_rdv.Appointment;
import main.entities.gestion_rdv.CalandarDay;
import main.entities.gestion_rdv.TimeSlot;
import main.services.gestion_rdv.exceptions.DeleteException;
import main.services.gestion_rdv.exceptions.InsertException;
import main.services.gestion_rdv.exceptions.SelectExecuteQueryException;
import main.services.gestion_rdv.exceptions.UpdateException;
import utils.JdbcConnection;

/**
 *
 * @author rbaih
 */
public class CrudTimeSlot {

    // inserting a single timeSlot
    public boolean insertTimeSlot(int calandar_id, TimeSlot ts) throws InsertException {

        String sql = "INSERT INTO time_slot( "
                + "calandar_day_id, start_time, end_time, "
                + " status, reason, note , index_slot "
                + ") "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ? )";
        try (
                JdbcConnection instanceJdbc = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJdbc.getConnection().prepareStatement(sql);) {

            this.setting_PrepareStatment_timeSlot_fields(preStatment, ts, calandar_id);

            return preStatment.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new InsertException("exception occured on insertTimeSlot() ==> " + e.getMessage());
        }

    }

    // Auto inserting timeslots based on CalandarDay Attributes 
    public boolean insertTimeSlots_from_Calandar(CalandarDay cd) throws InsertException {

        String sql = "INSERT INTO time_slot( "
                + "calandar_day_id, start_time, end_time, "
                + " status, reason, note ,index_slot "
                + ") "
                + " VALUES ( ?, ?, ?, ?, ?, ? ,?) ;";

        try (
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            Collection<TimeSlot> timeSlotsArray = TimeSloUtil.generateTimeSlotsBasedOnCalandarDay(cd);

            for (TimeSlot ts : timeSlotsArray) {
                this.setting_PrepareStatment_timeSlot_fields(preStatment, ts, cd.getId());
                preStatment.addBatch();
            }
            preStatment.executeBatch(); // return []results that shows what have been updated 
//           instanceJDBC.getConnection().commit(); // unnecessary alreeady connecion.setAutoCommit(true); 

            ResultSet rs = preStatment.getGeneratedKeys();
            if (rs != null) {
                while (rs.next()) {
                    timeSlotsArray.iterator().next().setId(rs.getInt(1));
                }
                cd.setTime_slots(timeSlotsArray);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new InsertException("exception occured on insertTimeSlot() ==> " + e.getMessage());
        }

    }

    //--------------Update-timeSlot-----------------------------------------------------------------------
    public boolean update_timeSlot(TimeSlot ts, boolean trueForStatusReasonNoteOnly_falseForAllFields) throws UpdateException {

        String sql;
        if (trueForStatusReasonNoteOnly_falseForAllFields) {
            sql = "UPDATE time_slot set status = ? , reason=?, note=? "
                    + "where id = ? ;";
        } else {
            sql = "UPDATE time_slot SET calandar_day_id=?, start_time=?, end_time=?, status=?, reason=?, note=?, index_slot= ? "
                    + " WHERE id=? ;";
        }

        try (JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatement = instanceJDBC.getConnection().prepareStatement(sql);) {

            if (trueForStatusReasonNoteOnly_falseForAllFields) {
                this.setting_PrepareStatment_timeSlot_status_reason_and_note_only(preStatement, ts);
                preStatement.setInt(4, ts.getId());
            } else {
                this.setting_PrepareStatment_timeSlot_fields(preStatement, ts, ts.getCalnadar_day_id());
                preStatement.setInt(8, ts.getId());
            }

            return preStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new UpdateException("exception occured on Update_TimeSlot() ==> " + e.getMessage());
        }

    }

    // ******
    public boolean update_timeSlot_afterAppointmentDeleted(int timeSlot_id) throws UpdateException {

        String sql;
        sql = "UPDATE time_slot set status = available , reason= unbooked , note=Patient:\nNumber:\nEmail:\nOther: "
                + "where id = ? ;";

        try (JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatement = instanceJDBC.getConnection().prepareStatement(sql);) {

                preStatement.setInt(1, timeSlot_id );
            

            return preStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new UpdateException("exception occured on Update_TimeSlot() ==> " + e.getMessage());
        }

    }

    // ------ Delete-all-available-timeSlots-older-then-today-----------------------
    public boolean deleteOldAvailableTimeSlot() throws DeleteException {

        String sql = "DELETE ts "
                + "FROM time_slot ts "
                + "JOIN calandar_day cd ON cd.id = ts.calandar_day_id "
                + "WHERE cd.date < CURDATE() AND ts.status = 'available' ;";

        try (
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);) {

            return preStatment.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new DeleteException("exception occured on Deleting_TimeSlot() ==> " + e.getMessage());
        }

    }

    // ------ Delete-manual on cascade 
    public boolean deletetimeSlot_and_appointment_related(TimeSlot timeSlot) throws DeleteException {

        String sql = "BEGIN;"
                + "DELETE FROM appointment WHERE time_slot_id = ? ;"
                + "DELETE  FROM time_slot WHERE id = ? ;"
                + "COMMIT;";

        try (
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);) {
            preStatment.setInt(1, timeSlot.getId());
            preStatment.setInt(2, timeSlot.getId());
            return preStatment.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new DeleteException("exception occured on Deleting_TimeSlot() ==> " + e.getMessage());
        }

    }

    // ------------Select-timeSlot-----------------------------------------
    // ------------Select-timeSlot-----------------------------------------
    // ------------Select-timeSlot-----------------------------------------
    // ------------Select-timeSlot-----------------------------------------
    // by ts.id
    public TimeSlot getTimeSlotById(int time_slot_id) throws SelectExecuteQueryException, ConnectionOrPrepareStatmentException {

        String sql = "SELECT * FROM time_slot ts where ts.id=? ";

        try (
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);) {

            preStatment.setInt(1, time_slot_id);
            try (ResultSet rs = preStatment.executeQuery()) {

                Collection<TimeSlot> ts_array = this.getTimeSlots_From_ResultSet(rs);
                if (ts_array.isEmpty()) {
                    return null;
                } else {
                    return ts_array.iterator().next(); // UNique object in this case thats why used this directly
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
                throw new SelectExecuteQueryException("exception occured on " + e.getLocalizedMessage() + " ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new ConnectionOrPrepareStatmentException("exception occured on Connection_ready_for_select_TimeSlot() ==> " + e.getMessage());
        }
    }

    //by calandar_id
    public Collection<TimeSlot> getTimeSlotsBelongsTo_calandar_Day(Integer calandar_day_id, boolean patientView) throws ConnectionOrPrepareStatmentException {

        String sql = "select * , app.* "
                + " FROM time_slot ts "
                + " LEFT JOIN appointment app on app.time_slot_id= ts.id "
                + "JOIN calandar_day cd ON cd.id = ts.calandar_day_id  "
                + "WHERE cd.id=? "
                + " ORDER BY ts.start_time ;";
        //replace WhereStatment if we want a PatientView where it's irrelevant to see the notAvailable TimeSlots (must escape special character its regex based first argument)
        if (patientView == true) {
            sql = sql.replaceFirst("WHERE\\ cd.id=\\?", "WHERE cd.id=? and ts.status='available' "); // must escape arg1 special characer in regex expression
        }
        System.out.println(sql);

        try (
                JdbcConnection instanceJDBC = JdbcConnection.getInstance();
                PreparedStatement preStatment = instanceJDBC.getConnection().prepareStatement(sql);) {

            preStatment.setInt(1, calandar_day_id);
            try (
                    ResultSet rs = preStatment.executeQuery();) {

                Collection<TimeSlot> ts_array = this.getTimeSlots_From_ResultSet(rs);
                return ts_array;

            } catch (SQLException e) {
                System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
                throw new SelectExecuteQueryException("exception occured on ResultSet level on " + e.getLocalizedMessage() + " ==> " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "  \n==> " + e.getLocalizedMessage());
            throw new ConnectionOrPrepareStatmentException("exception occured on Connection_ready_for_select_TimeSlot() ==> " + e.getMessage());
        }

    }

//    ************* COMMON PRIVATE METHODS ***************************************************
//    ************* COMMON PRIVATE METHODS ***************************************************
//    ************* COMMON PRIVATE METHODS ***************************************************
//    ************* COMMON PRIVATE METHODS ***************************************************
//    ************* COMMON PRIVATE METHODS ***************************************************
//    ************* COMMON PRIVATE METHODS ***************************************************
    // Result-Set-Fetcher
    private Collection<TimeSlot> getTimeSlots_From_ResultSet(ResultSet rs) throws SQLException {
        Collection<TimeSlot> ts_array = new ArrayList<>();
        while (rs.next()) {
            TimeSlot ts = new TimeSlot(); // must be a new object @ each iteration

            ts.setId(rs.getInt("id"));
            ts.setCalnadar_day_id(rs.getInt("calandar_day_id"));
            ts.setStart_time(rs.getTime("start_time").toLocalTime());
            ts.setEnd_time(rs.getTime("end_time").toLocalTime());
            ts.setStatus(rs.getString("status"));
            ts.setReason(rs.getString("reason"));
            ts.setNote(rs.getString("note"));
            ts.setIndex_slot(rs.getInt("index_slot"));

            // fill Up Appointment
            Appointment app = new Appointment();
            if (rs.getInt("app.id") == 0) {
                app = null;
            } else {
                app.setId(rs.getInt("app.id"));
                app.setDoctor_id(rs.getInt("app.doctor_id"));
                app.setDoctor_id(rs.getInt("app.patient_id"));
                app.setTime_slot_id(rs.getInt("app.time_slot_id"));
                app.setDate(rs.getDate("app.date").toLocalDate());
                app.setHour(rs.getTime("app.hour").toLocalTime());
                app.setReason(rs.getString("app.reason"));
                app.setBooking_state(rs.getString("app.booking_state"));
            }
            //puss appointment in TimeSlot
            ts.setAppointment(app);
            ts_array.add(ts);

        }

        return ts_array;
    }

    // prepareStarment all fields for insert_update
    private PreparedStatement setting_PrepareStatment_timeSlot_fields(PreparedStatement preStatment, TimeSlot ts, int calandar_id) throws SQLException {
        preStatment.setInt(1, calandar_id);
        preStatment.setTime(2, java.sql.Time.valueOf(ts.getStart_time()));
        preStatment.setTime(3, java.sql.Time.valueOf(ts.getEnd_time()));
        preStatment.setString(4, ts.getStatus());
        preStatment.setString(5, ts.getReason());
        preStatment.setString(6, ts.getNote());
        preStatment.setInt(7, ts.getIndex_slot());
        return preStatment;
    }

    // prepareStatment for update some
    private PreparedStatement setting_PrepareStatment_timeSlot_status_reason_and_note_only(PreparedStatement preStatment, TimeSlot ts) throws SQLException {

        preStatment.setString(1, ts.getStatus());
        preStatment.setString(2, ts.getReason());
        preStatment.setString(3, ts.getNote());

        return preStatment;
    }

}
