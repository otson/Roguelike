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

    public HostileCreature(Tile[][] tileMap, Player player) {
        super(tileMap, player);
    }

    @Override
    void hunt() {
        int dirX = 0;
        int dirY = 0;
        if(player.getX() > x)
            dirX = 1;
        else if(player.getX() < x)
            dirX = -1;
        if(player.getY() > y)
            dirY = 1;
        else if(player.getY() < y)
            dirY = -1;
        if(canDig){
            if(tileMap[x+dirX][y+dirY].isDiggable())
                dig(dirX, dirY);
            else
                move(dirX, dirY);
        }
        else{
            move(dirX, dirY);
        }
    }

}
