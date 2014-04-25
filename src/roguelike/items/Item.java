/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelike.items;

import java.awt.Color;

/**
 *
 * @author otso
 */
public abstract class Item {
    
    protected char glyph;
    protected String name;
    protected Color color;
    protected int id;
    protected int weight;
    protected int value;

    public Item() {
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
    
    
}
