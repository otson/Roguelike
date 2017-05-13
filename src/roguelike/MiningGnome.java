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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author otso
 */
public class MiningGnome extends HostileCreature {

    public MiningGnome(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
        this.canDig = true;
        this.glyph = 'g';
        this.attack = 8;
        this.defense = 2;
        this.maxHealth = 10;
        this.xpOnKill = 3;
        this.currentHealth = maxHealth;
        this.color = new Color(142, 88, 60);
        this.name = "Gnome Miner";
        addItem(0,1);
        addToMap();
    }

}
