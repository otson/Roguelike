package roguelike;


import java.awt.Color;
import java.awt.Dimension;
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
public class Stats extends JPanel{
    
    private final Font STATS_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private boolean blind;
    private JLabel hpBar;
    
    Stats(JPanel statsArea) {
        this.setPreferredSize(new Dimension(50,50));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setFocusable(false);
        this.setLayout(new GridLayout(3,6));
        JLabel label = new JLabel("Stat Jlabel");
        label.setForeground(Color.red);
        label.setFont(STATS_FONT);
        hpBar = new JLabel("HP");
        hpBar.setOpaque(true);
        hpBar.setBackground(Color.GREEN);
        this.add(hpBar);
        this.add(label);
        this.setForeground(Color.WHITE); 
    }
    
    
}
