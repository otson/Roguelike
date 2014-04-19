
import java.awt.Color;
import java.awt.Font;
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
class Tile {
    
    private int x;
    private int y;
    private MapObject mapObject;
    private Creature creature;
    private JTextField text;
    private final static Font MAP_FONT = new Font("Consolas", Font.PLAIN, 18);
    private final static Color bgColor = Color.BLACK;
    private Color fgColor;
    
    public Tile(){ 
    }
    
    public Tile(MapObject mapObject, int x, int y){
        this.x = x;
        this.y = y;
        creature = null;
        text = new JTextField();
        text.setBorder(null);
        text.setFocusable(false);
        text.setFont(MAP_FONT);
        fgColor = mapObject.getMapColor();
        text.setBackground(bgColor);
        text.setForeground(fgColor);
        text.setText(""+mapObject.getMapCharacter());
        this.mapObject = mapObject;
    }
    
    public boolean isNotOccupied(){
        return creature == null;
    }
    
    public boolean isWalkable(){
        return mapObject.isWalkable();
    }

    public MapObject getMapObject() {
        return mapObject;
    }

    public JTextField getText() {
        return text;
    }

    public void setText(JTextField text) {
        this.text = text;
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        if(creature == null){
            text.setText(""+mapObject.getMapCharacter());
            fgColor = mapObject.getMapColor();
            text.setForeground(fgColor);
        }
        else{
            text.setText(""+creature.getGlyph());
            fgColor = creature.getColor();
            text.setForeground(fgColor);
        }
    }
    
    
}
