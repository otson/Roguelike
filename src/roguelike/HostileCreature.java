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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author otso
 */
public abstract class HostileCreature extends Creature {

    public static long time = 0;
    public static long startTime;
    public static long endTime;

    public HostileCreature(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
    }

    @Override
    protected void hunt() {
        
        if (this.x == this.lastSeenPlayerX && this.y == this.lastSeenPlayerY) {
            this.lastSeenPlayerY = -1;
            this.lastSeenPlayerX = -1;
        }

        if (seesPlayer) {
            if (movesLeft > 0) {
                int dirX = GetRelativePlayerX();
                int dirY = GetRelativePlayerY();

                if (canDig && tileMap[x + dirX][y + dirY].isDiggable()) {
                    dig(dirX, dirY);
                }
                else if (tileMap[x + dirX][y + dirY].hasPlayer()) {
                    attack(this, player);
                }
                else {
                    move(dirX, dirY);
                }
            }
        }
        else if (this.lastSeenPlayerX != -1 && this.lastSeenPlayerY != -1) {
            int dirX = GetLastSeenPlayerX();
            int dirY = GetLastSeenPlayerY();

            if (canDig && tileMap[x + dirX][y + dirY].isDiggable()) {
                dig(dirX, dirY);
            }
            else if (tileMap[x + dirX][y + dirY].hasPlayer()) {
                attack(this, player);
            }
            else {
                move(dirX, dirY);
            }
        }
        else {
            wander();
        }
    }

    private int GetRelativePlayerX() {
        if (player.getX() > x) {
            return 1;
        }
        else if (player.getX() < x) {
            return -1;
        }
        else {
            return 0;
        }
    }

    private int GetRelativePlayerY() {
        if (player.getY() > y) {
            return 1;
        }
        else if (player.getY() < y) {
            return -1;
        }
        else {
            return 0;
        }
    }

    private int GetLastSeenPlayerX() {
        if (this.lastSeenPlayerX > x) {
            return 1;
        }
        else if (this.lastSeenPlayerX < x) {
            return -1;
        }
        else {
            return 0;
        }
    }

    private int GetLastSeenPlayerY() {
        if (this.lastSeenPlayerY > y) {
            return 1;
        }
        else if (this.lastSeenPlayerY < y) {
            return -1;
        }
        else {
            return 0;
        }
    }

}
