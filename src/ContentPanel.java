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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
    private JTextPane map;
    private Player player;
    private int PANEL_WIDTH;
    private int MAP_HEIGHT;
    private final int STATS_HEIGHT = 60;
    private final int MESSAGES_HEIGHT = 60;
    private int MAP_ROWS = 40;
    private int MAP_COLUMNS = 180;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Font MAP_FONT = new Font("Consolas", Font.PLAIN, 14);
    private final Font STATS_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private final Font MESSAGES_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private SimpleAttributeSet set = new SimpleAttributeSet();
    private MapCreator mapCreator;
    private StyledDocument doc;
    private BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private FontMetrics fm;
    
    
    public ContentPanel(){

        fm = img.getGraphics().getFontMetrics(MAP_FONT);
        String test = "#";
        int fontWidth = fm.stringWidth(test);
        int fontHeight = fm.getHeight();
        PANEL_WIDTH = MAP_COLUMNS*fontWidth+6;
        MAP_HEIGHT = MAP_ROWS*fontHeight;
        doc = new DefaultStyledDocument();
        map = new JTextPane(doc);
        this.addKeyListener(this);
        this.setFocusable(true);
        map.setVisible(true);
        map.setFocusable(false);
        
        mapCreator = new MapCreator(map, MAP_ROWS, MAP_COLUMNS);
        player = new Player(mapCreator.getTileMap());
        player.addToMap();
        map.setPreferredSize(new Dimension(PANEL_WIDTH, MAP_HEIGHT));
        map.setBackground(BACKGROUND_COLOR);
        map.setForeground(TEXT_COLOR);
        map.setFont(MAP_FONT);
        
        String initFill = "";
        for(int i = 0; i< MAP_ROWS; i++){
            for(int j = 0; j<MAP_COLUMNS; j++)
                initFill = initFill.concat("x");
            initFill = initFill.concat("\n");
        }
        map.setText(initFill);
        initStats();
        initMessages();
        
        this.setLayout(new BorderLayout());
        this.add(map, BorderLayout.CENTER);
        this.add(stats, BorderLayout.SOUTH);
        this.add(messages, BorderLayout.NORTH);
        
        Tile[][] temp = mapCreator.getTileMap();
        
        for(int i = 0; i<temp.length; i++){
            for(int j = 0; j<temp[i].length; j++){
                
                map.select(MAP_COLUMNS*(i)+j+i,MAP_COLUMNS*(i)+j+i+1);

                if(temp[i][j].getMapObject().isNotOccupied()){
                    StyleConstants.setForeground(set, temp[i][j].getMapObject().getMapColor());
                    map.replaceSelection(""+temp[i][j].getMapObject().getMapCharacter());
                }
                else{
                    System.out.println("occupied: "+i+" "+j);
                    StyleConstants.setForeground(set, temp[i][j].getMapObject().getCreature().getColor());
                    map.replaceSelection(""+temp[i][j].getMapObject().getCurrentCharacter());
                }
                
                doc.setCharacterAttributes(MAP_COLUMNS*(i)+j+i, 1, set, true);
            }
        }
        

    }

    private void initMessages() {
        messages = new JTextArea();
        messages.setPreferredSize(new Dimension(PANEL_WIDTH, MESSAGES_HEIGHT));
        messages.setBackground(BACKGROUND_COLOR);
        messages.setForeground(TEXT_COLOR);
        messages.setFocusable(false);
        messages.setFont(MESSAGES_FONT);
        messages.setText("messages");
    }

    private void initStats() {
        stats = new JTextArea();
        stats.setPreferredSize(new Dimension(PANEL_WIDTH, STATS_HEIGHT));
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
                update();
                break;
            case KeyEvent.VK_NUMPAD8: 
                player.move(-1,  0); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD9: 
                player.move(-1,  1); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD4: 
                player.move(0, -1); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD5: 
                player.Wait(1); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD6: 
                player.move(0,  1); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD1: 
                player.move(1, -1); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD2: 
                player.move(1, 0); 
                player.action(); 
                update();
                break;
            case KeyEvent.VK_NUMPAD3: 
                player.move(1, 1); 
                player.action(); 
                update();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void update(){
        Tile[][] temp = mapCreator.getTileMap();

        for(int i = 1; i+1<temp.length; i++){
            for(int j = 1; j+1<temp[i].length; j++){
                if(temp[i][j].getMapObject().isNeedsUpdate()){
                    update(i,j);
                    temp[i][j].getMapObject().setNeedsUpdate(false);
                }
            }
        }      
    }
    
    public void update(int x, int y){
        map.select(MAP_COLUMNS*(x)+y+x,MAP_COLUMNS*(x)+y+x+1);
        
        if(mapCreator.getTileMap()[x][y].getMapObject().isNotOccupied()){
            StyleConstants.setForeground(set, mapCreator.getTileMap()[x][y].getMapObject().getMapColor());
            map.replaceSelection(""+mapCreator.getTileMap()[x][y].getMapObject().getMapCharacter());
        }
        else{
            StyleConstants.setForeground(set, mapCreator.getTileMap()[x][y].getMapObject().getCreature().getColor());
            map.replaceSelection(""+mapCreator.getTileMap()[x][y].getMapObject().getCurrentCharacter());
        }

        doc.setCharacterAttributes(MAP_COLUMNS*(x)+y+x, 1, set, true);
    }


}