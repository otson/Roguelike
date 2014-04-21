package roguelike;


import java.awt.Color;

/**
 *
 * @author otso
 */
class Tile {

    private MapObject mapObject;
    private MapObject lastSeenMapObject;
    private Creature creature;
    private Creature lastSeenCreature;
    private boolean seen;
    private boolean currentlySeen;
    
    public Tile(){ 
    }
    
    public Tile(MapObject mapObject, int x, int y){
        this.mapObject = mapObject;
        creature = null;
        seen = false;
        currentlySeen = false;
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
        return creature.getName().equals("You");
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }
    
    public Color getColor(){
        if(lastSeenCreature != null && currentlySeen)
            return lastSeenCreature.getColor();
        else
            return lastSeenMapObject.getMapColor();
    }
    
    public char getGlyph(){
        if(lastSeenCreature != null && currentlySeen)
            return lastSeenCreature.getGlyph();
        else
            return lastSeenMapObject.getMapCharacter();
    }

    public boolean isSeen() {
        return seen;
    }
    public boolean isCurrentlySeen() {
        return currentlySeen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
        this.currentlySeen = seen;
        this.lastSeenCreature = creature;
        this.lastSeenMapObject = mapObject;
    }
    public boolean blocksVision(){
        return !mapObject.isSeeThrough();
    }

    void setCurrentlySeen(boolean b) {
        this.currentlySeen = b;
    }

}
