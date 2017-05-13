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
package roguelike;

import java.awt.Color;

/**
 *
 * @author otso
 */
public class Blob extends HostileCreature {

    public Blob(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
        this.attack = 6;
        this.defense = 1;
        this.color = Color.YELLOW;
        this.glyph = 'b';
        this.maxHealth = 5;
        this.currentHealth = maxHealth;
        this.name = "Yellow Blob";
        addToMap();
    }
}
