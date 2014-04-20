/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelike;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
public class Map extends JPanel{
    
    Tile[][] tiles;
    int width;
    int height;
    private Font MAP_FONT = new Font("Lucida Console", Font.PLAIN, 16);
    private FontMetrics fontMetrics;
    private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private int tileWidth;
    private int tileHeight;
    int counter = 0;

    public Map(Tile[][] tiles) {
        this.tiles = tiles;
        width = this.tiles.length;
        height = this.tiles[0].length;
        fontMetrics = img.getGraphics().getFontMetrics(MAP_FONT);
        tileWidth = fontMetrics.stringWidth("a");
        tileHeight = fontMetrics.getHeight();
        this.setPreferredSize(new Dimension(1000, 520));

    }
    
    @Override
    protected void paintComponent(Graphics g){
        long start = System.currentTimeMillis();
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 1000);
        g.setFont(MAP_FONT);
        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                g.setColor(tiles[i][j].getColor());
                g.drawString(""+tiles[i][j].getGlyph(), tileWidth*j, tileHeight*i+tileHeight);

            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent in paintComponent: "+(end-start)+" milliseconds.");
    }
}
