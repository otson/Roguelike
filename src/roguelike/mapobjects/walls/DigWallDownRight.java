package roguelike.mapobjects.walls;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public class DigWallDownRight extends DigWall{

    public DigWallDownRight() {
        this.mapCharacter = '┘';
        if(DigWall.SIMPLE_WALL)
            this.mapCharacter = DigWall.SIMPLE_WALL_CHAR;
    }
    
}
