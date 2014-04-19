
import java.awt.Color;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    protected boolean canDig;
    protected Random rand;
    protected Color color;
    protected Tile[][] tileMap;
    protected Player player;

    public Creature(Tile[][] tileMap, Player player){
        rand = new Random();
        this.player = player;
        this.tileMap = tileMap;
        movesPerTurn = 1;
        movesLeft = 1;
        canDig = false;
        
    }

    protected void addToMap(){
        boolean notSet = true;
        int count = 0;
        while(notSet){
            count++;
            int xx = rand.nextInt(tileMap.length-1)+1;
            int yy = rand.nextInt(tileMap[0].length-1)+1;
            if(tileMap[xx][yy].isNotOccupied() && tileMap[xx][yy].isWalkable()){    
                x = xx;
                y = yy;
                tileMap[xx][yy].setCreature(this);
                notSet = false;
                System.out.println("added creature at" +x+" "+y);
            }
            else if(count == 100){
                System.out.println("Failed to place a creature after 100 tries. Breaking the loop.");
                notSet = false;
            }

        }
    }
    protected void move(int x, int y){
            if(tileMap[this.x+x][this.y+y].isNotOccupied() && tileMap[this.x+x][this.y+y].isWalkable()){
                tileMap[this.x][this.y].setCreature(null);
                tileMap[this.x+x][this.y+y].setCreature(this);
                this.x +=x;
                this.y +=y;
                action();
            } 
    }
    void dig(int x, int y) {
        if(tileMap[this.x+x][this.y+y].getMapObject().isWall())
            if(tileMap[this.x+x][this.y+y].getMapObject().isDiggable())
                tileMap[this.x+x][this.y+y].setMapObject(new dugWall());
    }

    protected void action(){
        movesLeft--;   
    }

    protected void Wait(int times){
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
    
    
    abstract void hunt();
    

}
