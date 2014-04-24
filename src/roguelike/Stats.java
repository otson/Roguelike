package roguelike;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
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
public class Stats extends JPanel {

    private Graphics2D og;
    private Dimension od = null;
    private Image oi = null;
    private final Font STATS_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private JLabel hpBar;
    private Player player;
    private Color hpBarColor;

    Stats(Player player) {
        this.player = player;
        this.setPreferredSize(new Dimension(50, 50));
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        this.setLayout(new GridLayout(3, 6));
        hpBar = new JLabel("HP");
        hpBar.setFocusable(false);
        hpBar.setFont(STATS_FONT);
        hpBar.setForeground(Color.BLACK);
        this.add(hpBar);
        this.setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {

        setDoubleBuffering();
        og.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        og.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        og.setFont(STATS_FONT);
        drawHP(og);
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

    private void drawHP(Graphics2D og) {
        double scale = (double) player.currentHealth / (double) player.maxHealth;
        //System.out.println("scale: "+scale);
        //System.out.println("r: "+((int)Math.max(scale / 100, 1)*255)+" g: "+((int)(1-Math.max(scale / 100, 1))*255));
        hpBarColor = new Color((int) (Math.abs(scale - 1) * 255), (int) ((scale) * 255), 0);
        og.setColor(hpBarColor);
        og.fillRect(0, 0, (int) (hpBar.getWidth() * ((float) player.currentHealth / (float) player.maxHealth)), hpBar.getHeight());
    }

}
