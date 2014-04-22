/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelike;

import java.awt.Color;

/**
 *
 * @author otso
 */
public abstract class Stairs extends Floor{
    
    protected boolean up;
    public Stairs() {
        this.mapColor = Color.WHITE;
    }
    
    
}
