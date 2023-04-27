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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import entities.gestion_rdv.CalandarDay;
import entities.gestion_rdv.DummyCalandar;
import entities.gestion_rdv.TimeSlot;
import entities.gestion_rdv.User;
import java.time.LocalTime;
import services.gestion_rdv.exceptions.DeleteException;
import services.gestion_rdv.exceptions.InsertException;
import services.gestion_rdv.exceptions.SelectExecuteQueryException;
import utils.JdbcConnection;

/**
 *
 * @author rbaih
 */
public class CrudCalandarDay {

    // adjusting search of calandarDay 
    public Collection<CalandarDay> getCalandar_view_greaterEqualDateOptional(Integer doctor_id_Optional, boolean currentDay_startIndex,
            LocalDate dateToSearch_Optional) throws ConnectionOrPrepareStatmentException, SelectExecuteQueryException {
        String doctorIdClause = " doctor_id = ? ";
        String dateClause = " date >= CURRENT_DATE ";
        String andString = "";

        if (doctor_id_Optional == null) {
            doctorIdClause = "";
            andString = "";
        }

        if (doctor_id_Optional != null && (currentDay_startIndex == true || dateToSearch_Optional != null)) {
            andString = " and ";
        }

        if (!currentDay_startIndex) {
            dateClause = "";
        }

        if (dateToSearch_Optional != null) {
            dateClause = "  date >= \"" + dateToSearch_Optional.format(DateTimeFormatter.ISO_DATE) + "\"";
        }

        String sql = "select * from calandar_day "
                + " where " + doctorIdClause + andString + dateClause
                + " ORDER BY date ASC ";

        // case of select * from ... sorted by .. ( must get rid of word where )
        if (doctor_id_Optional == null && dateToSearch_Optional == null && (currentDay_startIndex == false)) {
            sql = sql.replace("where", "");
        }

        try (
                JdbcConnection mydbconn = JdbcConnection.getInstance();
                PreparedStatement preStatment = mydbconn.getConnection().prepareStatement(sql);) {
            // binding value to sql if and only if argument is passed because of sql statement
            if (doctor_id_Optional != null) {
                preStatment.setInt(1, doctor_id_Optional);
            }

            System.out.println(" query to be executed is \n=> " + sql);

            // storing execute query result in a resultSet 
            try (ResultSet resultSetfromQuery = preStatment.executeQuery();) {

                Collection<CalandarDay> calandars;
                calandars = this.fetchFullCalandars_from_ResultSet(resultSetfromQuery, null);
                System.out.println(calandars.toString());
                return calandars;

            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " => exception on outer try()Catch \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " => exception on outer try()Catch"
                    + "\n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }

    // *************************************************************************************************************
    // adjusting search of calandarDay 
    public Collection<CalandarDay> get_a_Calandar_with_related_timeSlotData(int calandar_id,
            Collection<TimeSlot> time_slots) throws ConnectionOrPrepareStatmentException, SelectExecuteQueryException {

        String sql = "select * from calandar_day cd"
                + " where cd.id = ? ";

        try (
                JdbcConnection mydbconn = JdbcConnection.getInstance();
                PreparedStatement preStatment = mydbconn.getConnection().prepareStatement(sql);) {
            // binding value to sql  
            preStatment.setInt(1, calandar_id);

            System.out.println(" query to be executed is \n=> " + sql);

            // storing execute query result in a resultSet 
            try (ResultSet resultSetfromQuery = preStatment.executeQuery();) {
                // looping the result set and stor its data

                Collection<CalandarDay> calandars;
                calandars = this.fetchFullCalandars_from_ResultSet(resultSetfromQuery, time_slots);
                System.out.println(calandars.toString());
                return calandars;

            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " => exception on outer try()Catch \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " => exception on outer try()Catch , \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }

    // findSingle calandar by doctor_id and Date
    public CalandarDay getcalandarBy_Doctor_and_date(int Doctor_id, LocalDate date) throws ConnectionOrPrepareStatmentException {
        String sql = "select * from calandar_day cd"
                + " where cd.doctor_id = ?  and cd.date = ? ";

        try (
                JdbcConnection mydbconn = JdbcConnection.getInstance();
                PreparedStatement preStatment = mydbconn.getConnection().prepareStatement(sql);) {
            // binding value to sql  
            preStatment.setInt(1, Doctor_id);
            preStatment.setDate(2, java.sql.Date.valueOf(date));

            System.out.println(" query to be executed is \n=> " + sql);

            // storing execute query result in a resultSet 
            try (ResultSet resultSetfromQuery = preStatment.executeQuery();) {
                // looping the result set and stor its data

                Collection<CalandarDay> calandars;
                calandars = this.fetchFullCalandars_from_ResultSet(resultSetfromQuery, null);//no need for timeSlots
                System.out.println(calandars.toString());
                if (calandars.isEmpty()) {
                    return null;
                }

                return calandars.iterator().next();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " => exception on outer try()Catch \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " => exception on outer try()Catch , \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }

    //********Calandar for PDF AND EXCEL ***********************
    //********Calandar for PDF AND EXCEL ***********************
    //********Calandar for PDF AND EXCEL ***********************
    //********Calandar for PDF AND EXCEL ***********************
    // findSingle calandar by doctor_id and Date
    
    
    public Collection<DummyCalandar> getcalandar_Pdf_Excel(int Doctor_id) throws ConnectionOrPrepareStatmentException {
        String sql = "select cd.date , ts.start_time , ts.status , ts.reason ,ts.note , app.reason as reasonApp , u.first_name , u.email ,u.phone_number "
                + " from calandar_day cd "
                + "JOIN time_slot ts ON ts.calandar_day_id = cd.id  "
                + "LEFT JOIN appointment app ON ts.id = app.time_slot_id "
                + "LEFT JOIN user u ON u.id=app.patient_id "
                + " where cd.doctor_id = ? and cd.date <= CURRENT_DATE  "
                + "and ts.status = \"not-available\" and ts.reason != \"lunch-time\"  "
                + "ORDER BY cd.date ASC, ts.start_time ASC ;";

        try (
                JdbcConnection mydbconn = JdbcConnection.getInstance();
                PreparedStatement preStatment = mydbconn.getConnection().prepareStatement(sql);) {
            // binding value to sql  
            preStatment.setInt(1, Doctor_id);

            System.out.println(" query to be executed is \n=> " + sql + "\n\n\n\n" + preStatment.toString() + "\n");

            
            // storing execute query result in a resultSet 
            try (ResultSet rs = preStatment.executeQuery();) {
                // looping the result set and stor its data

                ArrayList<DummyCalandar> arrayCalandars = new ArrayList<>();

                
                while (rs.next()) { // this will throw an exception it will be catched anyway in big try(){}catch(){} block anyway
                    

                    String name = rs.getString("first_name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone_number");
                    DummyCalandar.Slot.Appoint.Patient patient = new DummyCalandar.Slot.Appoint.Patient(name, email , phone);
                    
                    
                    String reasonApp = rs.getString("reasonApp");
                    DummyCalandar.Slot.Appoint appointment= new DummyCalandar.Slot.Appoint(reasonApp, patient);
                    
                    
                    LocalTime time = rs.getTime("start_time").toLocalTime();
                    String status = rs.getString("status");
                    String reason = rs.getString("reason");
                    String note = rs.getString("note"); 
                    DummyCalandar.Slot slot = new DummyCalandar.Slot(time, status, reason, note, appointment);
                    
                    
                    // date of the current row result fro rs
                    LocalDate date=rs.getDate("date").toLocalDate();
                    
                    if( arrayCalandars.isEmpty()  ){ // timeinitial
                        DummyCalandar calandar = new DummyCalandar(date);
                        calandar.getSlots().add(slot); 
                        
                        arrayCalandars.add(calandar);
                        continue; 
                    }
                   
                    // getting the latest added calandar wich is the current one we want to add to based on the current row data field date
                    DummyCalandar CurrentCalandar = arrayCalandars.get( arrayCalandars.size()-1 );
                    
                    if( CurrentCalandar.getDate().compareTo(date) == 0 ){
                        CurrentCalandar.getSlots().add(slot);
                        continue;
                    }else{
                        DummyCalandar calandar = new DummyCalandar(date);
                        calandar.getSlots().add(slot);
                        arrayCalandars.add(calandar);
                        continue;
                    }
          

                }
                
                
                System.out.println(arrayCalandars.toString());
                
                if(arrayCalandars.isEmpty())
                    return null;
                
                return arrayCalandars;
 
                

                

            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " => exception on outer try()Catch \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
                throw new SelectExecuteQueryException("Exception during execute query calandarDay ==> " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " => exception on outer try()Catch , \n main.services.gestion_rdv.CrudCalandarDay.getCalandar_daysByDoctorId_view_greater()");
            throw new ConnectionOrPrepareStatmentException("Exception during prepare statmen ==> " + e.getMessage());
        }

    }
    //********Calandar for PDF AND EXCEL ***********************
    //********Calandar for PDF AND EXCEL ***********************
    //********Calandar for PDF AND EXCEL ***********************
    //********Calandar for PDF AND EXCEL ***********************


    // ******************************************************************************************************************
    // ******************************************************************************************************************
    // ******************************************************************************************************************
    // ***********Create***************************************************************************
    public Boolean create_CalanadarDay_only(CalandarDay cd) throws InsertException {

        String sql = "INSERT INTO calandar_day( "
                + "doctor_id, date, day_start, day_end, session_duration,"
                + " lunch_break_start, lunch_break_end "
                + ") VALUES(?, ?, ?, ?, ?, "
                + "?, ? )";

        try (JdbcConnection instanceJdbc = JdbcConnection.getInstance();
                PreparedStatement pstmt = instanceJdbc.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, cd.getDoctor_id());
            pstmt.setDate(2, java.sql.Date.valueOf(cd.getDate()));
            pstmt.setTime(3, java.sql.Time.valueOf(cd.getDay_start()));
            pstmt.setTime(4, java.sql.Time.valueOf(cd.getDay_end()));
            pstmt.setInt(5, cd.getSession_duration());
            pstmt.setTime(6, java.sql.Time.valueOf(cd.getLunch_break_start()));
            pstmt.setTime(7, java.sql.Time.valueOf(cd.getLunch_break_end()));
//            pstmt.setInt(8,  cd.getTotal_time_slots() ); // problem pointerNull if not calculated
            if (pstmt.executeUpdate() == 1) {
                System.out.println("calandar successfully created.");

                // on this level We are sure that calandar is added already 
                ResultSet keysResultSet = pstmt.getGeneratedKeys();
                //moving cursor one step to get our unique value in this scenario
                keysResultSet.next();
                // getting the calandar id that was auto generated 
                int calandar_id = keysResultSet.getInt(1); // "1" because its first element.
                cd.setId(calandar_id);// now we can generate TimeSlot from this calandar object

                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " \n==> " + e.getLocalizedMessage());
            throw new InsertException("Exception during Inserting calandarDay ==> " + e.getMessage());
        }
    }

    public CalandarDay create_CalanadarDay_WithTimeSlotsOption(CalandarDay cd, boolean generateTimeSlots) throws InsertException {

        String sql = "INSERT INTO calandar_day( "
                + "doctor_id, date, day_start, day_end, session_duration,"
                + " lunch_break_start, lunch_break_end "
                + ") VALUES(?, ?, ?, ?, ?, "
                + "?, ? )";

        try (JdbcConnection instanceJdbc = JdbcConnection.getInstance();
                PreparedStatement pstmt = instanceJdbc.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, cd.getDoctor_id());
            pstmt.setDate(2, java.sql.Date.valueOf(cd.getDate()));
            pstmt.setTime(3, java.sql.Time.valueOf(cd.getDay_start()));
            pstmt.setTime(4, java.sql.Time.valueOf(cd.getDay_end()));
            pstmt.setInt(5, cd.getSession_duration());
            pstmt.setTime(6, java.sql.Time.valueOf(cd.getLunch_break_start()));
            pstmt.setTime(7, java.sql.Time.valueOf(cd.getLunch_break_end()));
//            pstmt.setInt(8,  cd.getTotal_time_slots() ); // problem pointerNull if not calculated
            if (pstmt.executeUpdate() == 1) {

                // here We are sure that calandar is added already 
                ResultSet keysResultSet = pstmt.getGeneratedKeys();
                //moving cursor one step to get our unique value in this scenario
                keysResultSet.next();
                // getting the calandar id that was auto generated 
                int calandar_id = keysResultSet.getInt(1); // "1" because its first element.
                cd.setId(calandar_id);// now we can generate TimeSlot from this calandar object

                System.out.println("calandar " + cd.getId() + " successfully Inserted.");
                if (generateTimeSlots == true) // generate timeSlots 
                {
                    if (new CrudTimeSlot().insertTimeSlots_from_Calandar(cd));
                }
                System.out.println("timeSlots generated and inserted . ");

                return cd;
            }
            return null;

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " \n==> " + e.getLocalizedMessage());
            throw new InsertException("Exception during Inserting calandarDay ==> " + e.getMessage());
        }
    }

    // ******************************************************************************************************************
    // ******************************************************************************************************************
    // ******************************************************************************************************************
    // ***********delete***************************************************************************
    public Boolean delete_calandar_day_withAllRelated(CalandarDay calandar) throws DeleteException {

        System.out.println("id of calandar to be deleted : " + calandar.getId());

        String sql1 = " DELETE FROM appointment WHERE time_slot_id IN ( SELECT id FROM  "
                + " time_slot WHERE calandar_day_id = " + calandar.getId() + " ); ";
        String sql2 = " DELETE FROM time_slot WHERE calandar_day_id = " + calandar.getId() + " ; ";
        String sql3 = " DELETE FROM calandar_day WHERE id =  " + calandar.getId() + " ; ";

        try (
                JdbcConnection instanceFromJdbc = JdbcConnection.getInstance();
                Statement statment = instanceFromJdbc.getConnection().createStatement();) {

            statment.addBatch(sql1);
            statment.addBatch(sql2);
            statment.addBatch(sql3);
            int[] result = statment.executeBatch();
            //index 0 for appointment can be 0 , index 1of timeSlot must be > 0 , index 2 of calandar must be >0 
            return result[1] > 0 && result[2] > 0;

        } catch (SQLException e) {
            System.err.println(" error on connection level on Database level @ delete_calandar_day()" + "\n" + e.getMessage());
            throw new DeleteException("Exception during Inserting calandarDay ==> " + e.getMessage());
        }

    }

    // ---- PRIVATE INNER METHODS ----------------------------------------------------
    // ---- PRIVATE INNER METHODS ----------------------------------------------------
    // ---- PRIVATE INNER METHODS ----------------------------------------------------
    // ---- PRIVATE INNER METHODS ----------------------------------------------------
    /**
     * fetch all the calandars from a select * from calandar_day ;
     *
     * @param rs
     * @param TimeSlots optional if a collection already exists
     * @return Collection<CalandarDay> as an ArrayList downcasted in this case
     * @throws SQLException
     */
    private Collection<CalandarDay> fetchFullCalandars_from_ResultSet(ResultSet rs, Collection<TimeSlot> TimeSlots) throws SQLException {
        Collection<CalandarDay> calandars = new ArrayList<>();
        // looping the result set and stor its data
        while (rs.next()) { // this will throw an exception it will be catched anyway in big try(){}catch(){} block anyway

            CalandarDay cd = new CalandarDay();
            User doctor = new User();
            doctor.setId(rs.getInt("doctor_id"));
            cd.setDoctor(doctor);

            cd.setId(rs.getInt("id"));
            cd.setDoctor_id(rs.getInt("doctor_id"));
            cd.setDate(rs.getDate("date").toLocalDate());
            cd.setDay_start(rs.getTime("day_start").toLocalTime());
            cd.setDay_end(rs.getTime("day_end").toLocalTime());
            cd.setSession_duration(rs.getInt("session_duration"));
            cd.setLunch_break_start(rs.getTime("lunch_break_start").toLocalTime());
            cd.setLunch_break_end(rs.getTime("lunch_break_end").toLocalTime());
            cd.setTotal_time_slots(rs.getInt("total_time_slots"));
            cd.setTime_slots(TimeSlots);

            calandars.add(cd);
        }

        return calandars;

    }

}
