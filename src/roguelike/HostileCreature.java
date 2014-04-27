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
