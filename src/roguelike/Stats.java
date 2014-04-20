package roguelike;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public class Stats {
    
    private JPanel statsPanel;
    private final Font STATS_FONT = new Font("Helvetica", Font.PLAIN, 14);
    
    Stats(JPanel statsArea) {
        statsPanel = statsArea;
        statsPanel.setLayout(new GridLayout(3,6));
        JLabel label = new JLabel("Stat Jlabel");
        label.setForeground(Color.red);
        label.setFont(STATS_FONT);
        statsPanel.add(label);
        statsPanel.setForeground(Color.WHITE);
        
    }
    
}
