/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelike;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
class InventoryScreen extends JPanel{

    public InventoryScreen() {
        this.setBackground(Color.red);
        this.setForeground(Color.pink);
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.GREEN);
        g.drawString("Inventory!", 0, 0);
    }
}
