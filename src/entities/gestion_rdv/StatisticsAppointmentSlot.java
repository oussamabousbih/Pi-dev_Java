/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.gestion_rdv;

/**
 *
 * @author rbaih
 */
public class StatisticsAppointmentSlot {
    
    int locallyBookedSlots;
    int appointmentsReasonCheckUp;
    int AppointmentsReasonAppointment;
    int sumTotal;
    float bookedSlotPercent;
    float checkUpPercent;
    float appointmentPercent;
    
    
    private void calculatePercent(){
        sumTotal= locallyBookedSlots+AppointmentsReasonAppointment+appointmentsReasonCheckUp;
        
        bookedSlotPercent=  Math.round( ( locallyBookedSlots/ (float)sumTotal)*100  *100.0f )/100.0f ;
        checkUpPercent=      Math.round( ( appointmentsReasonCheckUp/(float) sumTotal)*100 *100.0f )/100.0f;
        appointmentPercent = Math.round( ( AppointmentsReasonAppointment/ (float)sumTotal ) *100 *100.0f )/100.0f;
    }
            
            
    public StatisticsAppointmentSlot(int locallyBookedSlots, int appointmentsReasonCheckUp, int AppointmentsReasonAppointment) {
        this.locallyBookedSlots = locallyBookedSlots;
        this.appointmentsReasonCheckUp = appointmentsReasonCheckUp;
        this.AppointmentsReasonAppointment = AppointmentsReasonAppointment;
        
        this.calculatePercent();  
    }

    @Override
    public String toString() {
        return "StatisticsAppointmentSlot{" + "locallyBookedSlots=" + locallyBookedSlots + ", appointmentsReasonCheckUp=" + appointmentsReasonCheckUp + ", AppointmentsReasonAppointment=" + AppointmentsReasonAppointment + ", sumTotal=" + sumTotal + ", bookedSlotPercent=" + bookedSlotPercent + ", checkUpPercent=" + checkUpPercent + ", appointmentPercent=" + appointmentPercent + '}';
    }
    
    

    public int getLocallyBookedSlots() {
        return locallyBookedSlots;
    }



    public int getAppointmentsReasonCheckUp() {
        return appointmentsReasonCheckUp;
    }



    public int getAppointmentsReasonAppointment() {
        return AppointmentsReasonAppointment;
    }

    public int getSumTotal() {
        return sumTotal;
    }

    public float getBookedSlotPercent() {
        return bookedSlotPercent;
    }

    public float getCheckUpPercent() {
        return checkUpPercent;
    }

    public float getAppointmentPercent() {
        return appointmentPercent;
    }



  
   
    
    
    
    
}
