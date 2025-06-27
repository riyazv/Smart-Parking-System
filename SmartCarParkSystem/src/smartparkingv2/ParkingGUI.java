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

public class ParkingGUI extends JFrame {
    private JPanel northGatePanel;
    private JPanel southGatePanel;
    private JPanel eastGatePanel;
    private JPanel westGatePanel;
    private JPanel lotPanel;

    public ParkingGUI() {
        setTitle("Smart Parking System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Entry Panels (left side)
        JPanel entryPanel = new JPanel(new GridLayout(2, 1));
        northGatePanel = createGatePanel("NorthGate", Color.WHITE);
        southGatePanel = createGatePanel("SouthGate", Color.WHITE);
        entryPanel.add(northGatePanel);
        entryPanel.add(southGatePanel);

        // Exit Panels (right side)
        JPanel exitPanel = new JPanel(new GridLayout(2, 1));
        eastGatePanel = createGatePanel("EastGate", Color.RED);
        westGatePanel = createGatePanel("WestGate", Color.RED);
        exitPanel.add(eastGatePanel);
        exitPanel.add(westGatePanel);

        // Center Parking Lot
        lotPanel = new JPanel();
        lotPanel.setBackground(new Color(220, 220, 220));
        lotPanel.setLayout(new FlowLayout());

        add(entryPanel, BorderLayout.WEST);
        add(exitPanel, BorderLayout.EAST);
        add(lotPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createGatePanel(String label, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createTitledBorder(label));
        panel.setPreferredSize(new Dimension(100, 100));
        return panel;
    }

    public JPanel getNorthGatePanel() { return northGatePanel; }
    public JPanel getSouthGatePanel() { return southGatePanel; }
    public JPanel getEastGatePanel() { return eastGatePanel; }
    public JPanel getWestGatePanel() { return westGatePanel; }
    public JPanel getLotPanel() { return lotPanel; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParkingGUI::new);
    }
} 

