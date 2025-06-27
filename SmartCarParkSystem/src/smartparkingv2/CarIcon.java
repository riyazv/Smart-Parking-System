/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smartparkingv2;

/**
 *
 * @author sucku
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CarIcon extends JPanel {
    private final String plateNumber;
    private static BufferedImage carImage;

    static {
        try {
            carImage = ImageIO.read(CarIcon.class.getResource("/smartparkingv2/resources/car_sprite.png"));
            if (carImage == null) {
                System.err.println("[CarIcon] Resource returned null. Check file path or rebuild project.");
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("[CarIcon] Failed to load car sprite image: " + e.getMessage());
        }
    }

    public CarIcon(String plateNumber) {
        this.plateNumber = plateNumber;
        setPreferredSize(new Dimension(60, 30));
        setOpaque(false); // transparent background for sprite
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (carImage != null) {
            g.drawImage(carImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(Color.BLACK);
        g.drawString(plateNumber, 5, 25);
    }

    public String getPlateNumber() {
        return plateNumber;
    }
} 




