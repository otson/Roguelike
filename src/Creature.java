
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
        protected Random rand;
        protected Color color;
        protected Tile[][] tileMap;
        
        public Creature(Tile[][] tileMap){
            rand = new Random();
            this.tileMap = tileMap;
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
                else if(count == 1000){
                    System.out.println("could not place creature after 1000 tries");
                    System.exit(0);
                }
                    
            }
        }
        protected void move(int x, int y){
            if(tileMap[this.x+x][this.y+y].isNotOccupied() && tileMap[this.x+x][this.y+y].isWalkable()){
                tileMap[this.x][this.y].setCreature(null);
                tileMap[this.x+x][this.y+y].setCreature(this);
                this.x +=x;
                this.y +=y;
            } 
        }
        
        protected void action(){
            
        }
        
        protected void Wait(int times){
            
        }

        public char getGlyph() {
            return glyph;
        }

        public Color getColor() {
            return color;
        }
        
        
}
