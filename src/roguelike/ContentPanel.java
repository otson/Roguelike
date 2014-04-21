package roguelike;

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

/**
 *
 * @author otso
 */
public class ContentPanel extends JPanel implements KeyListener{

    private JPanel statsArea;
    
    private final int STATS_HEIGHT = 60;
    private final int MESSAGES_HEIGHT = 60;
    private int MAP_ROWS = 3000;
    private int MAP_COLUMNS = 3000;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Font MESSAGES_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private MapCreator mapCreator = new MapCreator(MAP_ROWS, MAP_COLUMNS);
    private Map map;
    private CreatureFactory creatureFactory;
    private JPanel mapPanel;
    private Stats stats;
    private boolean digEvent = false;
    private Player player;
    private Messages messages;
    
    
    public ContentPanel(){
        initStats();
        initMessages();
        addPlayer();
        createCreatureFactory();
        addContent();
        displayInitialMap();
    }

    private void createCreatureFactory() {
        creatureFactory = new CreatureFactory(mapCreator.getTileMap(), player, messages);
    }

    private void addPlayer() {
        player = new Player(mapCreator.getTileMap(), player, messages);
    }

    private void displayInitialMap() {
        map.repaint();
    }

    private void addContent() {
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        GridLayout grid = new GridLayout(MAP_ROWS, MAP_COLUMNS);
        mapPanel = new JPanel(grid);
        mapPanel.setBorder(null);
        map = new Map(mapCreator.getTileMap(), player);
        this.add(map, BorderLayout.CENTER);
        this.add(statsArea, BorderLayout.SOUTH);
        this.add(messages, BorderLayout.NORTH);
    }

    private void initMessages() {
        messages = new Messages(MESSAGES_HEIGHT, MESSAGES_FONT);
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
                break;
            case KeyEvent.VK_NUMPAD8: 
                if(digEvent){
                   player.dig(-1, 0);  
                   digEvent = false;
                }
                else
                    player.move(-1,  0); 
                break;
            case KeyEvent.VK_NUMPAD9:
                if(digEvent){
                   player.dig(-1, 1);  
                   digEvent = false;
                }
                else
                    player.move(-1,  1); 
                break;
            case KeyEvent.VK_NUMPAD4:
                if(digEvent){
                   player.dig(0, -1); 
                   digEvent = false;
                }
                else
                    player.move(0, -1); 
                break;
            case KeyEvent.VK_NUMPAD5: 
                player.Wait(1); 
                break;
            case KeyEvent.VK_NUMPAD6: 
                if(digEvent){
                   player.dig(0, 1); 
                   digEvent = false;
                }
                else
                    player.move(0,  1); 
                break;
            case KeyEvent.VK_NUMPAD1:
                if(digEvent){
                   player.dig(1, -1);  
                   digEvent = false;
                }
                else
                    player.move(1, -1); 
                break;
            case KeyEvent.VK_NUMPAD2: 
                if(digEvent){
                   player.dig(1, 0); 
                   digEvent = false;
                }
                else
                    player.move(1, 0); 

                break;
            case KeyEvent.VK_NUMPAD3: 
                if(digEvent){
                   player.dig(1, 1);  
                   digEvent = false;
                }
                else
                    player.move(1, 1); 
                break;
            case KeyEvent.VK_D: 
                messages.digDirection();
                digEvent = true;
                player.action(); 
                break;
        }
        if(player.getMovesLeft() == 0){
            turnEnd();
        }
        map.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void turnEnd() {
        creatureFactory.ActMonsters();
        turnStart();
    }

    private void turnStart() {
        creatureFactory.SpawnCreatures();
        creatureFactory.resetMoves();
        player.resetMoves();
    }

}