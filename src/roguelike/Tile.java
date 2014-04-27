package roguelike;

import roguelike.mapobjects.MapObject;
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
    private int x;
    private int y;
    protected int[] inventory = new int[PlayScreen.ITEM_LIST.size()];

    public Tile() {
    }

    public Tile(MapObject mapObject, int x, int y) {
        this.x = x;
        this.y = y;
        this.mapObject = mapObject;
        creature = null;
        seen = false;
        currentlySeen = true;
    }

    public boolean isNotOccupied() {
        return creature == null;
    }
    
    public boolean canEnter(){
        return creature == null && mapObject.isWalkable();
    }

    public boolean isWalkable() {
        return mapObject.isWalkable();
    }

    public boolean isDiggable() {
        if (mapObject.isWall()) {
            return mapObject.isDiggable();
        }
        else {
            return false;
        }
    }

    public MapObject getMapObject() {
        return mapObject;
    }

    public void setMapObject(MapObject mapObject) {
        this.mapObject = mapObject;
    }

    public boolean hasPlayer() {
        if (creature == null) {
            return false;
        }
        return creature.getName().equals("You");
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public Color getColor() {
        if (lastSeenCreature != null && currentlySeen) {
            return lastSeenCreature.getColor();
        }
        else if(inventory[0] != 0){
            return PlayScreen.ITEM_LIST.get(0).getColor();
        }
        else {
            return lastSeenMapObject.getMapColor();
        }
    }

    public char getGlyph() {
        if (lastSeenCreature != null && currentlySeen) {
            return lastSeenCreature.getGlyph();
        }
        else if(inventory[0] != 0){
            return PlayScreen.ITEM_LIST.get(0).getGlyph();
        }
        else {
            return lastSeenMapObject.getMapCharacter();
        }
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

    public boolean blocksVision() {
        return !mapObject.isSeeThrough();
    }

    void setCurrentlySeen(boolean b) {
        this.currentlySeen = b;
    }

    public boolean hasStairs() {
        return mapObject instanceof Stairs;
    }

    public boolean hasUpStairs() {
        return mapObject instanceof UpStairs;
    }

    public boolean hasDownStairs() {
        return mapObject instanceof DownStairs;
    }
    
    public void addItem(int id, int amount) {
        inventory[id] += amount;
    }

    public void removeItem(int id, int amount) {
        inventory[id] -= amount;
    }

    public void dropAllItems() {
        for(int i = 0; i<creature.inventory.length; i++){
            if(creature.inventory[i] != 0)
                inventory[i] += creature.inventory[i];
        }
        creature.emptyInventory();
    }

    public void emptyInventory() {
        for(int i = 0; i<inventory.length; i++)
            inventory[i] = 0;
    }
    public boolean hasItems(){
        for(int i = 0; i<inventory.length; i++){
            if(inventory[i] != 0)
                return true;
        }
        return false;
    }
}
