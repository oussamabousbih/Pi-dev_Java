/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package utils;
import java.util.concurrent.*;
import java.util.function.Consumer;
import javafx.application.Platform;
/**
 *
 * @author rbaih
 */


// DO NOT USE UNSAFE Method
public class MyScheduleLoopExecution<T> {

    private static ScheduledExecutorService executorService;

    private MyScheduleLoopExecution() {
        // private constructor to prevent instantiation
    }

    public static <T> void start(Consumer<T> consumer, T object, long seconds) {
        if (executorService == null || executorService.isShutdown()) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> consumer.accept(object));
        }, 0, seconds, TimeUnit.SECONDS);
    }

    public static void stop() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            try {
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (!executorService.isTerminated()) {
                    executorService.shutdownNow();
                }
            }
        }
    }
    
    
    
    
}
