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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
public class Map extends JPanel{
    
    private Graphics2D og;           
    private Dimension od = null;           
    private Image oi = null;   
    private Tile[][] tiles;
    private Player player;
    private int mapTotalWidth;
    private int mapTotalHeight;
    private Font MAP_FONT = new Font("Lucida Console", Font.PLAIN, 16);
    private FontMetrics fontMetrics;
    private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private int tileWidth;
    private int tileHeight;
    private final int VISIBLE_TILES_X = 80;
    private final int VISIBLE_TILES_Y = 24;
    private int counter = 0;
    private int playerX;
    private int playerY;
    private int iMin;
    private int jMin;
    private int iMax;
    private int jMax;
    private int iCounter;
    private int jCounter;

    public Map(Tile[][] tiles, Player player) {
        this.player = player;
        this.tiles = tiles;
        mapTotalHeight = this.tiles.length;
        mapTotalWidth = this.tiles[0].length;
        fontMetrics = img.getGraphics().getFontMetrics(MAP_FONT);
        tileWidth = fontMetrics.stringWidth("a");
        tileHeight = fontMetrics.getHeight();
        //System.out.println("TileWidth: "+tileWidth+" tiles: "+mapTotalWidth +" tileHeight: "+tileHeight+" mapTotalHeight: "+mapTotalHeight);
        this.setPreferredSize(new Dimension(tileWidth*VISIBLE_TILES_X, tileHeight*VISIBLE_TILES_Y));

    }
    
    @Override
    protected void paintComponent(Graphics g){
        getPlayerLocation();
        long start = System.currentTimeMillis();
        setDoubleBuffering();
        og.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        og.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        og.setFont(MAP_FONT);
        
        jMin = playerX-VISIBLE_TILES_X/2;
        jMax = playerX+VISIBLE_TILES_X/2;
        iMin = playerY-VISIBLE_TILES_Y/2;
        iMax = playerY+VISIBLE_TILES_Y/2;
        
        System.out.println("jMin: "+jMin+" jMax:"+jMax+" iMin:"+iMin+" iMax:"+iMax);
        
        
        if(jMin<0){
            jMin = 0;
            jMax = VISIBLE_TILES_X;
        }
        else if(jMax > mapTotalWidth){
            jMax = mapTotalWidth;
            jMin = jMax-VISIBLE_TILES_X;
        }
        
        if(iMin<0){
            iMin = 0;
            iMax = VISIBLE_TILES_Y;
        }
        else if(iMax > mapTotalHeight){
            iMax = mapTotalHeight;
            iMin = iMax-VISIBLE_TILES_Y;
        }
            
        
        System.out.println("jMin: "+jMin+" jMax:"+jMax+" iMin:"+iMin+" iMax:"+iMax);
        
        iCounter = 0;
        jCounter = 0;
        int tileCounter = 0;
        for(int i = iMin; i<iMax; i++){
            for(int j = jMin; j<jMax; j++){
                og.setColor(tiles[i][j].getColor());
                og.drawString(""+tiles[i][j].getGlyph(), tileWidth*jCounter, tileHeight*iCounter+tileHeight);
                jCounter++;
                tileCounter++;
            }
            iCounter++;
            jCounter = 0;
        }
        System.out.println("Drawn tiles: "+tileCounter);
        g.drawImage(oi, 0, 0, this);
        long end = System.currentTimeMillis();
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

    private void getPlayerLocation() {
        playerY = player.getX();
        playerX = player.getY();
    }
}
