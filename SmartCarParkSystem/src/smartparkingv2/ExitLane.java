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
import java.util.List;
import java.util.Random;

public class ExitLane implements Runnable {
    private final String laneId;
    private final LotManager lot;
    private final Random rand;
    private final ParkingGUI guiRef;
    private volatile boolean running = true;

    public ExitLane(String laneId, LotManager lot, ParkingGUI guiRef) {
        this.laneId = laneId;
        this.lot = lot;
        this.rand = new Random();
        this.guiRef = guiRef;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000 + rand.nextInt(1000)); // 1–2s per exit attempt

                List<CarUnit> cars = lot.getSnapshotOfParkedCars();
                if (cars.isEmpty()) continue;

                CarUnit car = cars.get(rand.nextInt(cars.size()));
                car.markExit(System.currentTimeMillis());
                car.setSimExitMinute(SimulationRunner.clock.getCurrentSimulatedMinute());

                double payment = calculateFee(car.getSimulatedDuration());
                car.updatePayment(payment);

                if (lot.exitCar(car)) {
                    SwingUtilities.invokeLater(() -> {
                        CarIcon icon = new CarIcon(car.getNumberPlate());
                        CarAnimator.animateCar(guiRef.getLotPanel(), icon, () -> {});
                    });

                    int stayMins = car.getSimulatedDuration();
                    int hours = stayMins / 60;
                    int mins = stayMins % 60;
                    String formattedStay = String.format("%d hrs %d mins", hours, mins);

                    System.out.printf("[%s] Car Exited: %s | Stay: %s | Paid: RM%.2f%n",
                            laneId, car.getNumberPlate(), formattedStay, car.getPaymentAmount());
                }
            } catch (InterruptedException e) {
                System.err.println("[" + laneId + "] Exit interrupted");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[" + laneId + "] Error while exiting car: " + e.getMessage());
            }
        }
    }

    private double calculateFee(long durationMins) {
        if (durationMins <= 60) return 6.0;
        long extraBlocks = (durationMins - 60 + 29) / 30; // Round up
        return 6.0 + extraBlocks * 1.5;
    }
}


