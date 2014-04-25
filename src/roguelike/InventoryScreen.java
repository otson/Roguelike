/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelike;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
class InventoryScreen extends JPanel {
    
    private Graphics2D og;
    private Dimension od = null;
    private Image oi = null;
    private final Font INVENTORY_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private Player player;

    public InventoryScreen(Player player) {
        this.player = player;
        this.setBackground(Color.red);
        this.setForeground(Color.pink);
    }

    @Override
    public void paintComponent(Graphics g) {
        setDoubleBuffering();
        og.setFont(INVENTORY_FONT);
        og.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        og.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        og.setColor(Color.WHITE);
        og.drawString(getPlayerInventory(), 5, 25);
        g.drawImage(oi, 0, 0, this);
    }
    
    private void setDoubleBuffering() {
        Dimension d = this.getSize();
        if (d != od || oi == null) {
            od = d;
            oi = this.createImage(d.width, d.height);
            og = (Graphics2D) oi.getGraphics();
        }
        og.setColor(Color.BLACK);
        og.fillRect(0, 0, d.width, d.height);
    }
    
    private String getPlayerInventory(){
        String invString = "Inventory: ";
        for(int i = 0; i< player.inventory.length; i++){
            if(player.inventory[i] != 0){
                invString = invString.concat("\n"+player.inventory[i] +" "+ PlayScreen.ITEM_LIST.get(i).getName());
            }
        }
        return invString;
    }
}
