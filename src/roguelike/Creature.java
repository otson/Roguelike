package roguelike;


import roguelike.walls.dugWall;
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
    protected boolean alive;
    protected Random rand;
    protected Color color;
    protected String name;
    
    protected Messages messages;
    protected Player player;
    protected Tile[][] tileMap;


    public Creature(Tile[][] tileMap, Player player, Messages messages){
        
        this.tileMap = tileMap;
        this.player = player;
        this.messages = messages;
        rand = new Random();
        alive = true;
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
                System.out.println("Added creature at: x:"+x+" y:"+y);
                notSet = false;
            }
            else if(count == 100){
                System.out.println("Failed to place a creature after 100 tries. Breaking the loop.");
                notSet = false;
            }

        }
    }
    protected void move(int x, int y){
        if(movesLeft > 0)
            if(tileMap[this.x+x][this.y+y].isNotOccupied() && tileMap[this.x+x][this.y+y].isWalkable()){
                tileMap[this.x][this.y].setCreature(null);
                tileMap[this.x+x][this.y+y].setCreature(this);
                this.x +=x;
                this.y +=y;
                action();
            } 
    }
    protected void attack(Creature attacker, Creature target){
        target.hit(attacker, attack);
    }
    
    void dig(int x, int y) {
        if(movesLeft > 0)
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
    protected void hit(Creature attacker, int damage){
        damage -= this.defense;
        if(damage < 0)
            damage = 0;
        this.currentHealth -= damage;
        messages.hit(this, attacker, damage);
        if(currentHealth < 0){
            die();
            messages.kill(this, attacker);
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

    private void die() {
        alive = false;
        tileMap[x][y].setCreature(null);
    }
    

}
