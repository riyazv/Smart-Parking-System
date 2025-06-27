/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartparkingv2;

/**
 *
 * @author sucku
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class LotManager {
    private final Semaphore availableSpots;
    private final List<CarUnit> parkedCars;
    private final Lock listLock;

    private double totalRevenue = 0.0;
    private int totalExitedCars = 0;
    private long totalStayTime = 0; // in minutes

    public LotManager(int totalSpots) {
        this.availableSpots = new Semaphore(totalSpots);
        this.parkedCars = new ArrayList<>();
        this.listLock = new ReentrantLock();
    }

    public boolean tryPark(CarUnit car, long waitTimeMillis) throws InterruptedException {
        if (availableSpots.tryAcquire(waitTimeMillis, TimeUnit.MILLISECONDS)) {
            listLock.lock();
            try {
                parkedCars.add(car);
            } finally {
                listLock.unlock();
            }
            return true;
        } else {
            return false; // congestion simulation
        }
    }

    public boolean exitCar(CarUnit car) {
        listLock.lock();
        try {
            if (parkedCars.remove(car)) {
                availableSpots.release();
                totalExitedCars++;
                totalRevenue += car.getPaymentAmount();
                totalStayTime += car.getDurationInMinutes();
                return true;
            }
        } finally {
            listLock.unlock();
        }
        return false;
    }

    public List<CarUnit> getSnapshotOfParkedCars() {
        listLock.lock();
        try {
            return new ArrayList<>(parkedCars);
        } finally {
            listLock.unlock();
        }
    }

    public int getCurrentOccupancy() {
        return parkedCars.size();
    }

    public int getTotalCapacity() {
        return availableSpots.availablePermits() + parkedCars.size();
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalExitedCars() {
        return totalExitedCars;
    }

    public double getAverageStayTime() {
        if (totalExitedCars == 0) return 0;
        return (double) totalStayTime / totalExitedCars;
    }
} 

