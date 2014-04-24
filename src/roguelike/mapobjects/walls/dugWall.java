package roguelike.mapobjects.walls;


import java.awt.Color;
import roguelike.Floor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public class dugWall extends Floor{

    public dugWall() {
        this.mapCharacter = '▒';
        this.mapColor = Color.LIGHT_GRAY;
    }
    
}
