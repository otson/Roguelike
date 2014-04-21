package roguelike;


import java.awt.Color;

/**
 *
 * @author otso
 */
class Tile {

    private MapObject mapObject;
    private Creature creature;
    
    public Tile(){ 
    }
    
    public Tile(MapObject mapObject, int x, int y){
        this.mapObject = mapObject;
        creature = null;
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
