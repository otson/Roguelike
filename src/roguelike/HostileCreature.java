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
public abstract class HostileCreature extends Creature{
    
    public static long time = 0;
    public static long startTime;
    public static long endTime;

    public HostileCreature(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
    }

    @Override
    protected void hunt() {
        if(movesLeft > 0){
            int dirX = GetRelativePlayerX();
            int dirY = GetRelativePlayerY();

            if(canDig && tileMap[x+dirX][y+dirY].isDiggable())
                dig(dirX, dirY);
            else if(tileMap[x+dirX][y+dirY].hasPlayer())
                attack(this, player);
            else
                move(dirX, dirY);
        }
    }

    private int GetRelativePlayerX() {
        if(player.getX() > x)
            return 1;
        else if(player.getX() < x)
            return -1;
        else
            return 0;
    }
    
    private int GetRelativePlayerY() {
        if(player.getY() > y)
            return 1;
        else if(player.getY() < y)
            return -1;
        else
            return 0;
    }

}
