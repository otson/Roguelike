
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public abstract class MapObject {
    
    protected char mapCharacter;
    protected char currentCharacter;
    protected boolean walkable;
    protected boolean notOccupied;
    protected boolean seeThrough;
    protected boolean needsUpdate;
    protected boolean wall;
    protected boolean diggable;
    protected Color mapColor;
    protected Creature creature;
    

    public MapObject() {
        notOccupied = true;
        needsUpdate = false;
        creature = null;
    }

    public char getMapCharacter() {
        return mapCharacter;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isSeeThrough() {
        return seeThrough;
    }

    public Color getMapColor() {
        return mapColor;
    }

    public boolean isWall() {
        return wall;
    }
    public boolean isDiggable(){
        return diggable;
    }

    public boolean isNotOccupied() {
        return notOccupied;
    }

    public char getCurrentCharacter() {
        return currentCharacter;
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        update();
    }

    private void update() {
        needsUpdate = true;
        
        if(creature == null){
            notOccupied = true;
            currentCharacter = mapCharacter;
        }
        else{
            notOccupied = false;     
            currentCharacter = creature.getGlyph();
        }
        
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
    
 
}
