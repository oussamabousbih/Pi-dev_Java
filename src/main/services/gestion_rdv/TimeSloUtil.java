/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.gestion_rdv;


import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import main.entities.gestion_rdv.CalandarDay;
import main.entities.gestion_rdv.TimeSlot;
import main.entities.gestion_rdv.enums.TimeSlotReason;
import main.entities.gestion_rdv.enums.TimeSlotStatus;

/**
 *
 * @author rbaih
 */
public class TimeSloUtil {
    
    public static Collection<TimeSlot> generateTimeSlotsBasedOnCalandarDay( CalandarDay cd ){
       
        
            // can't be called unless CalandarDay has an id which is why is only called after inserting the calandar and make sure you have the id of calandar day set to the object calandar
        int calandar_id = cd.getId(); 
        int session_duration = cd.getSession_duration();
        LocalTime start_time = LocalTime.from(cd.getDay_start()) ;
        LocalTime end_time = LocalTime.from(cd.getDay_end()) ; 
        int day_duration = (int) Duration.between( cd.getDay_start() ,cd.getDay_end() ).toMinutes();
        LocalTime break_start = LocalTime.from(cd.getLunch_break_start());
        LocalTime break_end = LocalTime.from(cd.getLunch_break_end());
        int break_duration = (int)Duration.between(cd.getLunch_break_start(), cd.getLunch_break_end()).toMinutes();
        
         int TimeSlotCounter = 1;
        Collection<TimeSlot> timeSlotsArray = new ArrayList<>();
        
        while( start_time.isBefore( end_time ) ){
            
            TimeSlot ts = new TimeSlot(); // must be a new object each iteration
            
            ts.setCalnadar_day_id(calandar_id);
            ts.setCalandar_day( cd );
            
            if(  start_time.compareTo(break_start)>=0 && start_time.compareTo(break_end)<0){ // its lunch time
                ts.setStart_time(LocalTime.from(start_time) );
                start_time= start_time.plusMinutes(break_duration);// updating time after knowing session duration for flexibility
                ts.setEnd_time(LocalTime.from(start_time) );
                break_end = LocalTime.from(start_time); // update break end for the while(loop)
                ts.setNote("");
                ts.setStatus(TimeSlotStatus.NOT_AVAILABLE.getName());
                ts.setReason(TimeSlotReason.LUNCH_TIME.getName()); 
                ts.setIndex_slot(TimeSlotCounter++);
            }
            else{// its working time
                
                ts.setStart_time( LocalTime.from(start_time)   );
                start_time= start_time.plusMinutes(session_duration);
                ts.setEnd_time(LocalTime.from(start_time) );
                ts.setNote("Patient:\nNumber:\nEmail:\nOther:");
                ts.setStatus(TimeSlotStatus.AVAILABLE.getName());
                ts.setReason(TimeSlotReason.UNBOOKED.getName());
                ts.setIndex_slot(TimeSlotCounter++);
            } 
                
            timeSlotsArray.add(ts);
                
        }
        cd.setTotal_time_slots(TimeSlotCounter); 
        cd.setTime_slots(timeSlotsArray); // when using at insertion make sure that you got the Id oc the calandarday first after being inserted
        return timeSlotsArray;
        
    }
    

    
    
}
