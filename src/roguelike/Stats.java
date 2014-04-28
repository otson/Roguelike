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
import javax.swing.JTextField;

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
    private final Font HP_FONT = new Font("Helvetica", Font.BOLD, 14);
    private Color bgColor = Color.BLACK;
    private Color fgColor = Color.WHITE;
    private JLabel hpBar;
    private Player player;
    private Color hpBarColor;
    private JPanel secondRow;
    private JPanel thirdRow;
    private JTextField playerName;
    private JTextField level;
    private JTextField exp;

    Stats(Player player) {
        this.player = player;
        this.setPreferredSize(new Dimension(50, 50));
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        this.setLayout(new GridLayout(3, 1));
        hpBar = new JLabel();
        hpBar.setFocusable(false);
        hpBar.setFont(HP_FONT);
        hpBar.setForeground(Color.BLACK);
        this.add(hpBar);
        this.setForeground(Color.WHITE);

        secondRow = new JPanel(new GridLayout(1, 8));
        secondRow.setBackground(Color.BLACK);
        secondRow.setBorder(null);
        thirdRow = new JPanel(new GridLayout(1, 8));
        thirdRow.setBackground(Color.BLACK);
        thirdRow.setBorder(null);
        this.add(secondRow);
        this.add(thirdRow);

        playerName = new JTextField();
        playerName.setFont(STATS_FONT);
        playerName.setBorder(null);
        playerName.setBackground(bgColor);
        playerName.setForeground(fgColor);

        secondRow.add(playerName);

        level = new JTextField();
        level.setFont(STATS_FONT);
        level.setBorder(null);
        level.setBackground(bgColor);
        level.setForeground(fgColor);

        secondRow.add(level);

        exp = new JTextField();
        exp.setFont(STATS_FONT);
        exp.setBorder(null);
        exp.setBackground(bgColor);
        exp.setForeground(fgColor);

        secondRow.add(exp);
    }

    @Override
    protected void paintComponent(Graphics g) {

        setDoubleBuffering();
        og.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        og.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        og.setFont(STATS_FONT);
        drawHP(og);

        og.setColor(Color.WHITE);
        playerName.setText(player.name);
        level.setText("Level: " + player.xpLevel);
        exp.setText("XP: " + player.currentExp + "/" + player.expToLevel);
        g.drawImage(oi, 0, 0, this);

    }

    private void setDoubleBuffering() {
        Dimension d = this.getSize();
        if (d != od || oi == null) {
            od = d;
            oi = this.createImage(d.width, d.height);
            og = (Graphics2D) oi.getGraphics();
        }
        og.fillRect(0, 0, d.width, d.height);

    }

    private void drawHP(Graphics2D og) {
        hpBar.setText("HP: " + player.currentHealth + "/" + player.maxHealth);
        double scale = (double) player.currentHealth / (double) player.maxHealth;
        //System.out.println("scale: "+scale);
        //System.out.println("r: "+((int)Math.max(scale / 100, 1)*255)+" g: "+((int)(1-Math.max(scale / 100, 1))*255));
        hpBarColor = new Color((int) (Math.abs(scale - 1) * 255), (int) ((scale) * 255), 0);
        og.setColor(hpBarColor);
        og.fillRect(0, 0, (int) (hpBar.getWidth() * ((float) player.currentHealth / (float) player.maxHealth)), hpBar.getHeight());
    }

}
