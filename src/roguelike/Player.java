package roguelike;

import java.awt.Color;
import roguelike.mapobjects.Door;
import roguelike.mapobjects.walls.dugWall;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author otso
 */
public class Player extends Creature {

    private boolean eyesOpen;
    private MapCreator mapCreator;
    protected int currentExp;
    protected int expToLevel;
    protected int xpLevel;

    public Player(Tile[][] tileMap, Player player, Messages messages, MapCreator mapCreator) {
        super(tileMap, player, messages);
        this.mapCreator = mapCreator;
        this.glyph = '@';
        this.attack = 12;
        this.defense = 2;
        this.maxHealth = 10;
        this.movesPerTurn = 2;
        this.turnsToRegenerate = 2;
        this.regenAmount = 1;
        this.currentExp = 0;
        this.expToLevel = 20;
        this.xpLevel = 1;
        this.movesLeft = this.movesPerTurn;
        this.currentHealth = this.maxHealth;
        this.color = Color.WHITE;
        this.canDig = true;
        this.name = "You";
        this.eyesOpen = true;
        this.visionDistance = 80;
        this.mapCreator = mapCreator;

        addToMap();
    }

    @Override
    void dig(int x, int y) {
        if (tileMap[this.x + x][this.y + y].getMapObject().isWall()) {
            if (tileMap[this.x + x][this.y + y].getMapObject().isDiggable()) {
                tileMap[this.x + x][this.y + y].setMapObject(new dugWall());
                messages.wallDug();
            }
            else {
                messages.wallDugFailed();
            }
        }
        else {
            messages.digAir();
        }
        action();
    }

    @Override
    protected void move(int x, int y) {
        if (visionDistance == 1 && !tileMap[this.x + x][this.y + y].isWalkable() && !tileMap[this.x + x][this.y + y].isSeen()) {
            setSeenByTouch(x, y);
        }
        if (tileMap[this.x + x][this.y + y].isNotOccupied() && tileMap[this.x + x][this.y + y].isWalkable()) {
            tileMap[this.x][this.y].setCreature(null);
            tileMap[this.x + x][this.y + y].setCreature(this);
            this.x += x;
            this.y += y;

            action();
        }
        else if (!tileMap[this.x + x][this.y + y].isNotOccupied()) {
            attack(this, tileMap[this.x + x][this.y + y].getCreature());
            action();
        }

    }

    @Override
    protected void action() {
        movesLeft--;
        FOV();
    }

    @Override
    public void resetCurrentVision() {
        for (Tile[] tileMap1 : tileMap) {
            for (Tile tileMap11 : tileMap1) {
                tileMap11.setCurrentlySeen(false);
            }
        }

    }

    public void toggleEyes() {
        eyesOpen = !eyesOpen;
        if (eyesOpen) {
            visionDistance = 80;
        }
        else {
            visionDistance = 1;
        }
        messages.toggleEyes(eyesOpen);
        action();
    }

    @Override
    public void FOV() {
        resetCurrentVision();
        float xx, yy;
        int i;
        for (i = 0; i < 360; i += 1) // how many rays of light
        {
            xx = (float) Math.cos((float) i * 0.01745f);
            yy = (float) Math.sin((float) i * 0.01745f);
            DoFov(xx, yy);
        }
    }

    @Override
    void DoFov(float x, float y) {
        int i;
        float ox, oy;
        ox = (float) this.x + 0.5f;
        oy = (float) this.y + 0.5f;
        for (i = 0; i < visionDistance; i++) {
            tileMap[(int) ox][(int) oy].setSeen(true);
            if (tileMap[(int) ox][(int) oy].blocksVision()) {
                return;
            }
            ox += x;
            oy += y;
        }
    }

    void setSeenByTouch(int x, int y) {
        tileMap[this.x + x][this.y + y].setSeen(true);
        messages.findWallBlind();
    }

    @Override
    void hunt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void goUp() {
        if (tileMap[this.x][this.y].hasStairs()) {
            if (tileMap[this.x][this.y].hasUpStairs()) {
                messages.goingUpStairs();
                useStairs(1);
            }
            else {
                messages.goingUpDownstairs();
            }
        }
        else {
            messages.goingUpNoStairs();
        }

    }

    void goDown() {
        if (tileMap[this.x][this.y].hasStairs()) {
            if (tileMap[this.x][this.y].hasDownStairs()) {
                messages.goingDownStairs();
                useStairs(-1);
            }
            else {
                messages.goingDownUpstairs();
            }
        }
        else {
            messages.goingDownNoStairs();
        }
    }

    private void useStairs(int is) {
        if (mapCreator.getLevels().containsKey(level + is)) {

            this.tileMap = mapCreator.getLevels().get(level + is);
            if (is > 0) { // going up
                System.out.println("going up");
                for (int xx = 0; xx < tileMap.length; xx++) {
                    for (int yy = 0; yy < tileMap[xx].length; yy++) {
                        if (tileMap[xx][yy].hasDownStairs()) {
                            mapCreator.getLevels().get(level)[x][y].setCreature(null);
                            x = xx;
                            y = yy;
                            level++;
                            System.out.println("placed downstair location. x, y: " + x + "," + y);
                            tileMap[xx][yy].setCreature(this);
                        }
                    }
                }
            }
            else { // going down
                System.out.println("going down");
                for (int xx = 0; xx < tileMap.length; xx++) {
                    for (int yy = 0; yy < tileMap[xx].length; yy++) {
                        if (tileMap[xx][yy].hasUpStairs()) {
                            mapCreator.getLevels().get(level)[x][y].setCreature(null);
                            x = xx;
                            y = yy;
                            level--;
                            System.out.println("placed upstair location. x, y: " + x + "," + y);
                            tileMap[xx][yy].setCreature(this);
                        }
                    }
                }
            }
            action();
        }
    }

    void tryDoor(int x, int y) {
        if (tileMap[this.x + x][this.y + y].getMapObject().isDoor()) {
            Door door = (Door) tileMap[this.x + x][this.y + y].getMapObject();
            if (door.isOpen()) {
                messages.youCloseDoor();
                door.toggle();
            }
            else {
                messages.youOpenDoor();
                door.toggle();
            }
        }
        else {
            messages.noDoor();
        }
        action();
    }

    public void addExp(int amount) {
        this.currentExp += amount;
        if (currentExp >= expToLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        this.xpLevel++;
        expToLevel = Math.round((expToLevel * 2 + expToLevel / 2 + expToLevel / 4) / 10) * 10;
        this.maxHealth += rand.nextInt(5)+4;
        this.currentHealth += maxHealth/4;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
    }
}
