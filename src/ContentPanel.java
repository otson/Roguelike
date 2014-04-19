/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author otso
 */
public class ContentPanel extends JPanel implements KeyListener{
    
    private JTextArea messageArea;
    private JPanel statsArea;
    private Player player;
    private final int STATS_HEIGHT = 60;
    private final int MESSAGES_HEIGHT = 60;
    private int MAP_ROWS = 40;
    private int MAP_COLUMNS = 150;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BACKGROUND_COLOR = Color.BLACK;
    
    private final Font MESSAGES_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private MapCreator mapCreator;
    private JPanel mapPanel;
    private Messages messages;
    private Stats stats;
    private boolean digEvent = false;
    
    
    public ContentPanel(){
        this.addKeyListener(this);
        this.setFocusable(true);
        
        initStats();
        initMessages();
        
        mapCreator = new MapCreator(MAP_ROWS, MAP_COLUMNS);
        player = new Player(mapCreator.getTileMap(), messages);
        player.addToMap();

        
        
        this.setLayout(new BorderLayout());
        GridLayout grid = new GridLayout(MAP_ROWS, MAP_COLUMNS);
        mapPanel = new JPanel(grid);
        mapPanel.setBorder(null);

        this.add(mapPanel, BorderLayout.CENTER);
        this.add(statsArea, BorderLayout.SOUTH);
        this.add(messageArea, BorderLayout.NORTH);
        
        Tile[][] temp = mapCreator.getTileMap();
        for (Tile[] temp1 : temp) {
            for (Tile temp11 : temp1) {
                mapPanel.add(temp11.getText());
            }
        }
    }

    private void initMessages() {
        messageArea = new JTextArea();
        messages = new Messages(messageArea);
        messageArea.setPreferredSize(new Dimension(50, MESSAGES_HEIGHT));
        messageArea.setBackground(BACKGROUND_COLOR);
        messageArea.setForeground(TEXT_COLOR);
        messageArea.setFocusable(false);
        messageArea.setFont(MESSAGES_FONT);
    }

    private void initStats() {
        statsArea = new JPanel();
        stats = new Stats(statsArea);
        statsArea.setPreferredSize(new Dimension(50, STATS_HEIGHT));
        statsArea.setBackground(BACKGROUND_COLOR);
        statsArea.setFocusable(false);
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
                if(digEvent){
                   player.dig(-1, -1); 
                   digEvent = false;
                }
                else{
                    player.move(-1, -1);
                }
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD8: 
                if(digEvent){
                   player.dig(-1, 0);  
                   digEvent = false;
                }
                else
                    player.move(-1,  0); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD9:
                if(digEvent){
                   player.dig(-1, 1);  
                   digEvent = false;
                }
                else
                    player.move(-1,  1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD4:
                if(digEvent){
                   player.dig(0, -1); 
                   digEvent = false;
                }
                else
                    player.move(0, -1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD5: 
                player.Wait(1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD6: 
                if(digEvent){
                   player.dig(0, 1); 
                   digEvent = false;
                }
                else
                    player.move(0,  1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD1:
                if(digEvent){
                   player.dig(1, -1);  
                   digEvent = false;
                }
                else
                    player.move(1, -1); 
                player.action(); 
                break;
            case KeyEvent.VK_NUMPAD2: 
                if(digEvent){
                   player.dig(1, 0); 
                   digEvent = false;
                }
                else
                    player.move(1, 0); 
                player.action(); 

                break;
            case KeyEvent.VK_NUMPAD3: 
                if(digEvent){
                   player.dig(1, 1);  
                   digEvent = false;
                }
                else
                    player.move(1, 1); 
                player.action(); 
                break;
            case KeyEvent.VK_D: 
                messages.digDirection();
                digEvent = true;
                player.action(); 
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}