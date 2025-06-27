/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartparkingv2;

/**
 *
 * @author sucku
 */
import javax.swing.*;
import java.util.Random;

public class EntryLane implements Runnable {
    private final String laneId;
    private final LotManager lot;
    private final int maxVehicles;
    private final Random rand;
    private final ParkingGUI guiRef;

    public EntryLane(String laneId, LotManager lot, int maxVehicles, ParkingGUI guiRef) {
        this.laneId = laneId;
        this.lot = lot;
        this.maxVehicles = maxVehicles;
        this.rand = new Random();
        this.guiRef = guiRef;
    }

    @Override
    public void run() {
        for (int i = 0; i < maxVehicles; i++) {
            try {
                Thread.sleep(300 + rand.nextInt(301)); // 300–600ms
                CarUnit car = new CarUnit();
                car.setSimEntryMinute(SimulationRunner.clock.getCurrentSimulatedMinute());

                boolean parked = lot.tryPark(car, 2000); // 2s timeout
                if (parked) {
                    System.out.println("[" + laneId + "] Car Entered: " + car.getNumberPlate());
                    SwingUtilities.invokeLater(() -> {
                        CarIcon icon = new CarIcon(car.getNumberPlate());
                        CarAnimator.animateCar(guiRef.getLotPanel(), icon, () -> {});
                    });
                } else {
                    System.out.println("[" + laneId + "] Car BLOCKED (Parking Full): " + car.getNumberPlate());
                }
            } catch (InterruptedException e) {
                System.err.println("[" + laneId + "] Entry interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}

