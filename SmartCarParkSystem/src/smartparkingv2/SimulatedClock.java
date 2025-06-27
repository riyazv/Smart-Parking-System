/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartparkingv2;

/**
 *
 * @author sucku
 */
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatedClock extends Thread {
    private final int totalSimulatedMinutes = 24 * 60; // 24 hours
    private final int realMillisecondsPerSimulatedMinute;
    private int currentSimulatedMinute = 0;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public SimulatedClock(int realMillisecondsPerSimulatedMinute) {
        this.realMillisecondsPerSimulatedMinute = realMillisecondsPerSimulatedMinute;
    }

    @Override
    public void run() {
        while (running.get() && currentSimulatedMinute < totalSimulatedMinutes) {
            try {
                Thread.sleep(realMillisecondsPerSimulatedMinute);
                currentSimulatedMinute++;
            } catch (InterruptedException e) {
                running.set(false);
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n[Clock] 24 simulated hours reached. Ending simulation.");
        running.set(false);
    }

    public int getCurrentSimulatedMinute() {
        return currentSimulatedMinute;
    }

    public boolean isRunning() {
        return running.get();
    }

    public void stopClock() {
        running.set(false);
    }
} 
