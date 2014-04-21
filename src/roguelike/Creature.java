package roguelike;


import java.awt.Color;
import java.util.Random;
import roguelike.walls.dugWall;


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


    public Creature(Tile[][] tileMap, Player player, Messages messages){
        
        this.tileMap = tileMap;
        this.player = player;
        this.messages = messages;
        seen = new boolean[tileMap.length][tileMap[0].length];
        currentlySeen = new boolean[tileMap.length][tileMap[0].length];
        rand = new Random();
        alive = true;
        movesPerTurn = 1;
        movesLeft = movesPerTurn;
        canDig = false;
        visionDistance = 20;
        seesPlayer = false;
        initializeVisionArrays();
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
                FOV();
                
            }
            else if(count == 100){
                System.out.println("Failed to place a creature after 100 tries. Breaking the loop.");
                notSet = false;
            }
        }
    }
    public void FOV(){
        resetCurrentVision();
        float xx,yy;
        int i;
        for(i=0;i<360;i+=1) // how many rays of light
        {
            xx=(float) Math.cos((float)i*0.01745f);
            yy=(float) Math.sin((float)i*0.01745f);
            DoFov(xx,yy);
        }
    }

    void DoFov(float x,float y){
        int i;
        float ox,oy;
        ox = (float)this.x+0.5f;
        oy = (float)this.y+0.5f;
        for(i=0;i<visionDistance;i++)
        {
            seen[(int)ox][(int)oy] = true;
            if(tileMap[(int)ox][(int)oy].hasPlayer())
                    seesPlayer = true;
            if(tileMap[(int)ox][(int)oy].blocksVision())
                return;
            ox+=x;
            oy+=y;
        }
    }
    public void resetCurrentVision() {
        seesPlayer = false;
        for(int i = 0; i>seen.length;i++){
            for(int j = 0; j>seen[0].length; j++){
                currentlySeen[i][j] = false;
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
    
    protected void wander(){
        move(rand.nextInt((1 - (-1)) + 1) + (-1), rand.nextInt((1 - (-1)) + 1) + (-1));
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
        FOV();
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

    private void initializeVisionArrays() {
        for(int i = 0; i>seen.length;i++){
            for(int j = 0; j>seen[0].length; j++){
                seen[i][j] = false;
                currentlySeen[i][j] = false;
            }
        }
    }
    

}
