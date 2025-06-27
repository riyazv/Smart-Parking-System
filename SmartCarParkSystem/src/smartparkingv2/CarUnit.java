/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartparkingv2;

/**
 *
 * @author sucku
 */
import java.util.Random;

public class CarUnit {
    private final String numberPlate;
    private long timeOfEntry;
    private long timeOfExit;
    private double paymentAmount;

    private int simEntryMinute;
    private int simExitMinute;

    public CarUnit() {
        this.numberPlate = createRandomPlate();
        this.timeOfEntry = System.currentTimeMillis();
    }

    private String createRandomPlate() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random generator = new Random();
        return "" + alphabet.charAt(generator.nextInt(26)) + alphabet.charAt(generator.nextInt(26)) + alphabet.charAt(generator.nextInt(26)) +
                " " + (1000 + generator.nextInt(9000));
    }

    public void markExit(long exitTime) {
        this.timeOfExit = exitTime;
    }

    public void updatePayment(double amount) {
        this.paymentAmount = amount;
    }

    public long getDurationInMinutes() {
        return (timeOfExit - timeOfEntry) / 60000; // convert ms to min
    }

    public int getSimulatedDuration() {
        return simExitMinute - simEntryMinute;
    }

    public void setSimEntryMinute(int minute) {
        this.simEntryMinute = minute;
    }

    public void setSimExitMinute(int minute) {
        this.simExitMinute = minute;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
} 


