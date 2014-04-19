/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author otso
 */
public class ContentPanel extends JPanel implements KeyListener{
    
    private JTextArea messages;
    private JTextArea stats;
    private Player player;
    private final int STATS_HEIGHT = 60;
    private final int MESSAGES_HEIGHT = 60;
    private int MAP_ROWS = 42;
    private int MAP_COLUMNS = 160;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Font STATS_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private final Font MESSAGES_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private MapCreator mapCreator;
    private JPanel mapPanel;
    
    
    public ContentPanel(){
        this.addKeyListener(this);
        this.setFocusable(true);

        mapCreator = new MapCreator(MAP_ROWS, MAP_COLUMNS);
        player = new Player(mapCreator.getTileMap());
        player.addToMap();

        initStats();
        initMessages();
        
        this.setLayout(new BorderLayout());
        GridLayout grid = new GridLayout(MAP_ROWS, MAP_COLUMNS);
        mapPanel = new JPanel(grid);
        mapPanel.setBorder(null);

        this.add(mapPanel, BorderLayout.CENTER);
        this.add(stats, BorderLayout.SOUTH);
        this.add(messages, BorderLayout.NORTH);
        
        Tile[][] temp = mapCreator.getTileMap();
        for (Tile[] temp1 : temp) {
            for (Tile temp11 : temp1) {
                mapPanel.add(temp11.getText());
            }
        }
    }

    private void initMessages() {
        messages = new JTextArea();
        messages.setPreferredSize(new Dimension(50, MESSAGES_HEIGHT));
        messages.setBackground(BACKGROUND_COLOR);
        messages.setForeground(TEXT_COLOR);
        messages.setFocusable(false);
        messages.setFont(MESSAGES_FONT);
        messages.setText("messages");
    }

    private void initStats() {
        stats = new JTextArea();
        stats.setPreferredSize(new Dimension(50, STATS_HEIGHT));
        stats.setBackground(BACKGROUND_COLOR);
        stats.setForeground(TEXT_COLOR);
        stats.setFocusable(false);
        stats.setFont(STATS_FONT);
        stats.setText("stats stats stats \nstats stats stats \nstats stats stats");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_ESCAPE: System.exit(0); 
            break; 
            case KeyEvent.VK_NUMPAD7:
                player.move(-1, -1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD8: 
                player.move(-1,  0); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD9: 
                player.move(-1,  1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD4: 
                player.move(0, -1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD5: 
                player.Wait(1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD6: 
                player.move(0,  1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD1: 
                player.move(1, -1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD2: 
                player.move(1, 0); 
                player.action(); 

                break;
            case KeyEvent.VK_NUMPAD3: 
                player.move(1, 1); 
                player.action(); 
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}