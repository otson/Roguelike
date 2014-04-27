/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelike.mapobjects;

import java.awt.Color;

/**
 *
 * @author otso
 */
public class Door extends MapObject {

    protected char openGlyph;
    protected char closedGlyph;
    protected boolean open;

    public Door() {
        open = false;
        openGlyph = '.';
        closedGlyph = '+';
        this.mapCharacter = closedGlyph;
        this.mapColor = new Color(120, 70, 50);
        this.diggable = false;
        this.seeThrough = false;
    }

    public void toggle() {
        if (mapCharacter == closedGlyph) {
            mapCharacter = openGlyph;
            open = true;
            this.walkable = true;
            this.seeThrough = true;
        }
        else if (mapCharacter == openGlyph) {
            mapCharacter = closedGlyph;
            open = false;
            this.walkable = false;
            this.seeThrough = false;
        }

    }
    public boolean isOpen(){
        return open;
    }

}
