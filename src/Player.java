
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
public class Player extends Creature{
    
    Messages messages;

//    public Player(Tile[][] tileMap, Messages messages) {
//        
//        super(tileMap);
//        this.messages = messages;
//        this.glyph = '@';
//        this.attack = 3;
//        this.defense = 2;
//        this.maxHealth = 15;
//        this.movesPerTurn = 1;
//        this.movesLeft = this.movesPerTurn;
//        this.currentHealth = this.maxHealth;
//        this.color = Color.WHITE;
//        addToMap();
//    }

    public Player(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player);
        this.messages = messages;
        this.glyph = '@';
        this.attack = 3;
        this.defense = 2;
        this.maxHealth = 15;
        this.movesPerTurn = 2;
        this.movesLeft = this.movesPerTurn;
        this.currentHealth = this.maxHealth;
        this.color = Color.WHITE;
        this.canDig = true;
        addToMap();
    }
    
  
    void dig(int x, int y) {
        if(tileMap[this.x+x][this.y+y].getMapObject().isWall()){
            if(tileMap[this.x+x][this.y+y].getMapObject().isDiggable()){
                tileMap[this.x+x][this.y+y].setMapObject(new dugWall());
                messages.wallDug();
            }
            else
                messages.wallDugFailed();
        }
        else
            messages.digAir();
    }

    @Override
    void hunt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
