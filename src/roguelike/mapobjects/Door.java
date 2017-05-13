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
