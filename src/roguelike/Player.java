package roguelike;


import java.awt.Color;
import roguelike.walls.dugWall;

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

    public Player(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
        this.glyph = '@';
        this.attack = 30;
        this.defense = 2;
        this.maxHealth = 1500000;
        this.movesPerTurn = 1;
        this.movesLeft = this.movesPerTurn;
        this.currentHealth = this.maxHealth;
        this.color = Color.WHITE;
        this.canDig = true;
        this.name = "You";
        addToMap();
    }
    
  
    @Override
    void dig(int x, int y) {
        System.out.println("Player health: "+ currentHealth);
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
    protected void move(int x, int y){
            if(tileMap[this.x+x][this.y+y].isNotOccupied() && tileMap[this.x+x][this.y+y].isWalkable()){
                tileMap[this.x][this.y].setCreature(null);
                tileMap[this.x+x][this.y+y].setCreature(this);
                this.x +=x;
                this.y +=y;
                action();
            }
            else if(!tileMap[this.x+x][this.y+y].isNotOccupied()){
                attack(this, tileMap[this.x+x][this.y+y].getCreature());
                action();
            }
            
    }
    
    @Override
    protected void action(){
        movesLeft--;
        
    }
    

    @Override
    void hunt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}