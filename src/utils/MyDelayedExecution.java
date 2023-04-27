package utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;


/**
 *
 * @author rbaih
 */
public class MyDelayedExecution {
    
    
//********Delay only ************************************************************************************************    
//********Delay only ************************************************************************************************    
//********Delay only ************************************************************************************************    

    public static <T> void executeAfterDelay_JavaOnly(Consumer<T> consumer,T object, long seconds ) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            // Use the consumer to execute the code with the object passed as a parameter
            consumer.accept(object);
        }, seconds, TimeUnit.SECONDS);
        executorService.shutdown();
    }
    
  

    public static <T> void executeAfterDelay_JavaFX(Consumer<T> consumer, T object, long seconds) {
        // Create a Timeline with a KeyFrame that triggers the consumer operation after the specified delay
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> consumer.accept(object)));

        // Start the Timeline
        timeline.play();
    }

//*** interval Execution*******************************************************************************
//*** interval Execution*******************************************************************************
//*** interval Execution*******************************************************************************
    
    



    //illustration what code does example
  /*  public static void main(String[] args) {
        
        // how to use 
        TimeSlot ts = new TimeSlot(1, 2, LocalTime.NOON, LocalTime.MIDNIGHT , "good", "no reaso", "pff", 0);
        Consumer<TimeSlot> myConsumer = myTimeSlot -> System.out.println(myTimeSlot.getNote());
        System.out.println("wait 5 seconds : consumer example code will run soon");
        executeAfterDelay(myConsumer, 5, ts);
    }*/
    
    
    
    
}




