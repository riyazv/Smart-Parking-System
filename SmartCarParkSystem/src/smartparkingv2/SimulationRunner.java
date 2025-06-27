/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartparkingv2;

/**
 *
 * @author sucku
 */
public class SimulationRunner {
    public static SimulatedClock clock = new SimulatedClock(250); // 250ms = 1 simulated minute (~6 min total)

    public static void main(String[] args) {
        ParkingGUI gui = new ParkingGUI();
        LotManager lot = new LotManager(50);

        ExitLane exitLane1 = new ExitLane("EastGate", lot, gui);
        ExitLane exitLane2 = new ExitLane("WestGate", lot, gui);

        Thread entry1 = new Thread(new EntryLane("NorthGate", lot, 75, gui));
        Thread entry2 = new Thread(new EntryLane("SouthGate", lot, 75, gui));
        Thread exit1 = new Thread(exitLane1);
        Thread exit2 = new Thread(exitLane2);

        clock.start();
        entry1.start();
        entry2.start();
        exit1.start();
        exit2.start();

        // Wait for the simulated 24-hour period to end
        while (clock.isRunning()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        // Stop exit threads
        exitLane1.stop();
        exitLane2.stop();

        try {
            exit1.join();
            exit2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Gracefully end the simulation
        System.out.println("\n--- SIMULATION COMPLETE ---");
        System.out.printf("Total Cars Processed: %d\n", lot.getTotalExitedCars());
        System.out.printf("Total Revenue Collected: RM%.2f\n", lot.getTotalRevenue());
        System.out.printf("Average Stay Duration: %.2f minutes\n", lot.getAverageStayTime());
    }
}
