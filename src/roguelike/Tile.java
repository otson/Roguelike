package roguelike;


import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

/**
 *
 * @author otso
 */
class Tile {

    private MapObject mapObject;
    private Creature creature;
    private JTextField text;
    private final static Font MAP_FONT = new Font("Lucida Console", Font.PLAIN, 16);
    private final static Color BACKGROUND_COLOR = Color.BLACK;
    
    public Tile(){ 
    }
    
    public Tile(MapObject mapObject, int x, int y){
        this.mapObject = mapObject;
        creature = null;
        text = new JTextField();
        text.setBorder(null);
        text.setFocusable(false);
        text.setFont(MAP_FONT);
        text.setBackground(BACKGROUND_COLOR);
        text.setForeground(this.mapObject.getMapColor());
        text.setText(""+this.mapObject.getMapCharacter());
        
    }
    
    public boolean isNotOccupied(){
        return creature == null;
    }
    
    public boolean isWalkable(){
        return mapObject.isWalkable();
    }
    public boolean isDiggable(){
        if(mapObject.isWall()){
            return mapObject.isDiggable();
        }
        else return false;
    }

    public MapObject getMapObject() {
        return mapObject;
    }

    public void setMapObject(MapObject mapObject) {
        this.mapObject = mapObject;
        text.setText(""+mapObject.getMapCharacter());
    }
    

    public JTextField getText() {
        return text;
    }

    public void setText(JTextField text) {
        this.text = text;
    }
    public boolean hasPlayer(){
        if(creature == null)
            return false;
        else return creature.getName().equals("You");
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        
        this.creature = creature;
        if(creature == null){
            text.setText(""+mapObject.getMapCharacter());
            text.setForeground(mapObject.getMapColor());
        }
        else{
            text.setText(""+creature.getGlyph());
            text.setForeground(creature.getColor());
        }
    }
    
    public Color getColor(){
        if(creature != null)
            return creature.getColor();
        else
            return mapObject.getMapColor();
    }
    
    public char getGlyph(){
        if(creature != null)
            return creature.getGlyph();
        else
            return mapObject.getMapCharacter();
    }

}
