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
import java.util.Random;
import roguelike.mapobjects.walls.dugWall;

/**
 *
 * @author otso
 */
public abstract class Creature {

    protected char glyph;
    protected int maxHealth;
    protected int currentHealth;
    protected int attack;
    protected int defense;
    protected int x, y;
    protected int movesPerTurn;
    protected int movesLeft;
    protected int visionDistance;
    protected int turnsToRegenerate;
    protected int turnsSinceRegeneration;
    protected int regenAmount;
    protected int xpOnKill;
    protected boolean canDig;
    protected boolean alive;
    protected Random rand;
    protected Color color;
    protected String name;
    protected Messages messages;
    protected Player player;
    protected Tile[][] tileMap;
    protected boolean[][] seen;
    protected boolean[][] currentlySeen;
    protected boolean seesPlayer;
    protected int lastSeenPlayerX = -1;
    protected int lastSeenPlayerY = -1;
    protected int mapLevel;
    protected int[] inventory = new int[PlayScreen.ITEM_LIST.size()];
    protected float ox, oy, xx, yy;
    protected static CosSineTable table = new CosSineTable();

    public Creature(Tile[][] tileMap, Player player, Messages messages) {
        this.tileMap = tileMap;
        this.player = player;
        this.messages = messages;
        defense = 1;
        seen = new boolean[tileMap.length][tileMap[0].length];
        currentlySeen = new boolean[tileMap.length][tileMap[0].length];
        rand = new Random();
        alive = true;
        movesPerTurn = 1;
        turnsSinceRegeneration = 0;
        turnsToRegenerate = 5;
        regenAmount = 1;
        movesLeft = movesPerTurn;
        canDig = false;
        visionDistance = 20;
        seesPlayer = false;
        xpOnKill = 1;
        initializeVisionArrays();
    }

    public int getMapLevel() {
        return mapLevel;
    }
    
    protected void addToMap() {
        boolean notSet = true;
        int count = 0;
        while (notSet) {
            count++;

            int xx = rand.nextInt(tileMap.length - 1) + 1;
            int yy = rand.nextInt(tileMap[0].length - 1) + 1;
            if (tileMap[xx][yy].isNotOccupied() && tileMap[xx][yy].isWalkable()) {
                x = xx;
                y = yy;
                tileMap[xx][yy].setCreature(this);
                notSet = false;
                FOV();

            }
            else if (count == 100) {
                System.out.println("Failed to place a creature after 100 tries. Breaking the loop.");
                notSet = false;
            }
        }
    }

    public void FOV(int distance) {
        resetCurrentVision();

        for (int i = 0; i < 360; i += 4) // how many rays of light
        {
            xx = (float) Math.cos((float) i * 0.01745f);
            yy = (float) Math.sin((float) i * 0.01745f);
            DoFov(xx, yy, distance);
        }
    }

    void DoFov(float x, float y, int distance) {

        ox = (float) this.x + 0.5f;
        oy = (float) this.y + 0.5f;
        for (int i = 0; i < distance; i++) {
            seen[(int) ox][(int) oy] = true;
            if (tileMap[(int) ox][(int) oy].hasPlayer()) {
                seesPlayer = true;
                lastSeenPlayerX = tileMap[(int) ox][(int) oy].getCreature().x;
                lastSeenPlayerY = tileMap[(int) ox][(int) oy].getCreature().y;
            }
            if (tileMap[(int) ox][(int) oy].blocksVision()) {
                return;
            }
            ox += x;
            oy += y;
        }
    }

    public void FOV() {
        resetCurrentVision();
        for (int i = 0; i < 360; i += 4) // how many rays of light
        {
            xx = (float) Math.cos((float) i * 0.01745f);
            yy = (float) Math.sin((float) i * 0.01745f);
            DoFov(xx, yy);
        }
    }

    void DoFov(float x, float y) {

        ox = (float) this.x + 0.5f;
        oy = (float) this.y + 0.5f;
        for (int i = 0; i < visionDistance; i++) {
            seen[(int) ox][(int) oy] = true;
            if (tileMap[(int) ox][(int) oy].hasPlayer()) {
                seesPlayer = true;
                lastSeenPlayerX = tileMap[(int) ox][(int) oy].getCreature().x;
                lastSeenPlayerY = tileMap[(int) ox][(int) oy].getCreature().y;
            }
            if (tileMap[(int) ox][(int) oy].blocksVision()) {
                return;
            }
            ox += x;
            oy += y;
        }
    }

    public void resetCurrentVision() {
        seesPlayer = false;
        for (int yyy = 0; yyy > seen.length; yyy++) {
            for (int xxx = 0; xxx > seen[yyy].length; xxx++) {
                currentlySeen[xxx][yyy] = false;
            }
        }
    }

    protected void move(int x, int y) {

        if (movesLeft > 0) {
            if (tileMap[this.x + x][this.y + y].canEnter()) {
                tileMap[this.x][this.y].setCreature(null);
                tileMap[this.x + x][this.y + y].setCreature(this);
                this.x += x;
                this.y += y;
                action();
            }
            else {
                wander();
            }
        }
    }

    protected void sleep() {
        movesLeft = 0;
        FOV(5);
    }

    protected void wanderMove(int x, int y) {
        tileMap[this.x][this.y].setCreature(null);
        tileMap[this.x + x][this.y + y].setCreature(this);
        this.x += x;
        this.y += y;
        action();
    }

    protected void wander() {
        int dirX = rand.nextInt((1 - (-1)) + 1) + (-1);
        int dirY = rand.nextInt((1 - (-1)) + 1) + (-1);
        if (tileMap[this.x + dirX][this.y + dirY].canEnter()) {
            wanderMove(dirX, dirY);
        }
    }

    protected void attack(Creature attacker, Creature target) {
        int incDamage = attack / 4 + rand.nextInt(attack * 3 / 4) + 1;
        target.hit(attacker, incDamage);
    }

    void dig(int x, int y) {
        if (movesLeft > 0) {
            if (tileMap[this.x + x][this.y + y].getMapObject().isWall()) {
                if (tileMap[this.x + x][this.y + y].getMapObject().isDiggable()) {
                    tileMap[this.x + x][this.y + y].setMapObject(new dugWall());
                }
            }
        }
    }

    protected void action() {
        movesLeft--;
        FOV();
    }

    protected void Wait(int times) {
        action();
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void resetMoves() {
        movesLeft = movesPerTurn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void hit(Creature attacker, int damage) {
        damage -= this.defense;
        if (damage < 0) {
            damage = 0;
        }
        this.currentHealth -= damage;
        messages.hit(this, attacker, damage);
        if (currentHealth <= 0) {
            die();
            messages.kill(this, attacker);
            if (attacker.isPlayer()) {
                player.addExp(this.xpOnKill);
            }
        }

    }

    protected void checkRegen() {
        if (turnsSinceRegeneration >= turnsToRegenerate) {
            regenerate();
        }
        else {
            turnsSinceRegeneration++;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    protected int getCurrentHealth() {
        return currentHealth;
    }

    protected void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;

    }

    protected int getAttack() {
        return attack;
    }

    protected int getDefense() {
        return defense;
    }

    public String getName() {
        return name;
    }

    abstract void hunt();

    protected void die() {
        alive = false;
        tileMap[x][y].dropAllItems();
        tileMap[x][y].setCreature(null);
    }

    private void initializeVisionArrays() {
        for (int xx = 0; xx > seen.length; xx++) {
            for (int yy = 0; yy > seen[xx].length; yy++) {
                seen[xx][yy] = false;
                currentlySeen[xx][yy] = false;
            }
        }
    }

    public void setMapLevel(int mapLevel) {
        this.mapLevel = mapLevel;
    }
    

    private void regenerate() {
        if (this.currentHealth < this.maxHealth) {
            this.currentHealth += regenAmount;
            if (this.currentHealth > this.maxHealth) {
                this.currentHealth = this.maxHealth;
            }
        }
        turnsSinceRegeneration = 0;
    }

    public void pickUpItems() {
        if (tileMap[x][y].hasItems()) {
            pickUpAll();
        }
    }

    private void pickUpAll() {
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] += tileMap[x][y].inventory[i];
        }
        tileMap[x][y].emptyInventory();
        action();
    }

    public void dropItems() {
        dropAll();
    }

    private void dropAll() {
        for (int i = 0; i < inventory.length; i++) {
            tileMap[x][y].inventory[i] += inventory[i];
        }
        emptyInventory();
        action();
    }

    public void addItem(int id, int amount) {
        inventory[id] += amount;
    }

    public void removeItem(int id, int amount) {
        inventory[id] -= amount;
    }

    public void emptyInventory() {
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = 0;
        }
    }

    public int[] getInventory() {
        return inventory;
    }

    public boolean isPlayer() {
        return this instanceof Player;
    }
}
