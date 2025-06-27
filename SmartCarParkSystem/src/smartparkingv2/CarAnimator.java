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
import java.awt.*;

public class CarAnimator {
    public static void animateCar(JPanel lotPanel, CarIcon car, Runnable onComplete) {
        lotPanel.add(car);
        lotPanel.setComponentZOrder(car, 0);
        lotPanel.repaint();

        int startX = 10;
        int endX = lotPanel.getWidth() - 80;
        int y = 50 + (int)(Math.random() * (lotPanel.getHeight() - 100));

        car.setLocation(startX, y);
        car.setSize(60, 30);
        car.setLayout(null);
        car.setVisible(true);
        lotPanel.setLayout(null);

        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            Point current = car.getLocation();
            if (current.x < endX) {
                car.setLocation(current.x + 4, current.y);
            } else {
                timer.stop();
                lotPanel.remove(car);
                lotPanel.repaint();
                onComplete.run();
            }
        });

        timer.start();
    }
} 

