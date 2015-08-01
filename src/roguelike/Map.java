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
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
public class Map extends JPanel {

    private Graphics2D og;
    private Dimension od = null;
    private Image oi = null;
    private Tile[][] tiles;
    private Player player;
    private int mapTotalWidth;
    private int mapTotalHeight;
    private Font MAP_FONT = new Font("Lucida Console", Font.PLAIN, 18);
    private FontMetrics fontMetrics;
    private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private int tileWidth;
    private int tileHeight;
    private int VISIBLE_TILES_X = 160;
    private int VISIBLE_TILES_Y = 48;
    private int counter = 0;
    private int playerX;
    private int playerY;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int xCounter;
    private int yCounter;
    private final char DARK = ' ';
    private HashMap<Integer, Tile[][]> levels;

    public Map(HashMap<Integer, Tile[][]> levels, Player player) {
        this.player = player;
        this.levels = levels;

        mapTotalWidth = levels.get(player.level).length;
        mapTotalHeight = levels.get(player.level)[0].length;

        if (VISIBLE_TILES_X > mapTotalWidth) {
            VISIBLE_TILES_X = mapTotalWidth;
        }
        if (VISIBLE_TILES_Y > mapTotalHeight) {
            VISIBLE_TILES_Y = mapTotalHeight;
        }
        fontMetrics = img.getGraphics().getFontMetrics(MAP_FONT);
        tileWidth = fontMetrics.stringWidth("a");
        tileHeight = fontMetrics.getHeight();

        this.setPreferredSize(new Dimension(tileWidth * VISIBLE_TILES_X, tileHeight * VISIBLE_TILES_Y));

    }

    @Override
    protected void paintComponent(Graphics g) {
        tiles = levels.get(player.level);
        setDoubleBuffering();
        og.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        og.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        og.setFont(MAP_FONT);

        yMin = player.y - VISIBLE_TILES_Y / 2;
        yMax = player.y + VISIBLE_TILES_Y / 2;

        xMin = player.x - VISIBLE_TILES_X / 2;
        xMax = player.x + VISIBLE_TILES_X / 2;

        if (yMin < 0) {
            yMin = 0;
            yMax = VISIBLE_TILES_Y;
        }
        else if (yMax > mapTotalHeight) {
            yMax = mapTotalHeight;
            yMin = yMax - VISIBLE_TILES_Y;
        }

        if (xMin < 0) {
            xMin = 0;
            xMax = VISIBLE_TILES_X;
        }
        else if (xMax > mapTotalWidth) {
            xMax = mapTotalWidth;
            xMin = xMax - VISIBLE_TILES_X;
        }

        xCounter = 0;
        yCounter = 0;
        for (int x = xMin; x < xMax; x++) {
            for (int y = yMin; y < yMax; y++) {
                if (tiles[x][y].isSeen()) {
                    og.setColor(tiles[x][y].getColor());
                    og.drawString("" + tiles[x][y].getGlyph(), tileWidth * xCounter, tileHeight * yCounter + tileHeight);
                }
                else {
                    og.setColor(Color.BLACK);
                    og.drawString("" + DARK, tileWidth * xCounter, tileHeight * yCounter + tileHeight);
                }
                yCounter++;
            }
            xCounter++;
            yCounter = 0;
        }
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
}
