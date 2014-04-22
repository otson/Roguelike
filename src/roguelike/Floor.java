package roguelike;


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
public class Floor extends MapObject{
    
    protected boolean floor;

    public Floor() {
        this.walkable = true;
        this.mapCharacter = '.';
        this.seeThrough = true;
        this.mapColor = Color.GREEN;
        this.wall = false;
    }

}
