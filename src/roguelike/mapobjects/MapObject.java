/* 
 * Copyright (C) 2017 Otso Nuortimo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package roguelike.mapobjects;

import java.awt.Color;
import roguelike.Creature;
import roguelike.mapobjects.walls.Wall;

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
    
    public boolean isWallOrDoor(){
        return this instanceof Wall || this instanceof Door;
    }

    public boolean isDiggable() {
        return diggable;
    }

    public boolean isNotOccupied() {
        return notOccupied;
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

        if (creature == null) {
            notOccupied = true;
        }
        else {
            notOccupied = false;
        }

    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
    public boolean isRoomFloor(){
        return this instanceof RoomFloor;
    }
    public boolean isDoor(){
        return this instanceof Door;
    }

}
