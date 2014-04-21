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
    
    private boolean eyesOpen;
    
    public Player(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
        this.glyph = '@';
        this.attack = 3;
        this.defense = 2;
        this.maxHealth = 1500000;
        this.movesPerTurn = 2;
        this.movesLeft = this.movesPerTurn;
        this.currentHealth = this.maxHealth;
        this.color = Color.WHITE;
        this.canDig = true;
        this.name = "You";
        eyesOpen = true;
        this.visionDistance = 800;
        
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
        action();
    }
    
    @Override
    protected void move(int x, int y){
            if(visionDistance == 1 && !tileMap[this.x+x][this.y+y].isWalkable() && !tileMap[this.x+x][this.y+y].isSeen()){
                setSeenByTouch(x,y);
            }
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
    public void toggleEyes(){
        eyesOpen = !eyesOpen;
        if(eyesOpen)
            visionDistance = 80;
        else
            visionDistance = 1;
        messages.toggleEyes(eyesOpen);
        action();
    }
    
    @Override
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

    @Override
    void DoFov(float x,float y){
        int i;
        float ox,oy;
        ox = (float)this.x+0.5f;
        oy = (float)this.y+0.5f;
        for(i=0;i<visionDistance;i++)
        {
            tileMap[(int)ox][(int)oy].setSeen(true); 
            if(tileMap[(int)ox][(int)oy].blocksVision())
                return;
            ox+=x;
            oy+=y;
        }
    }
    void setSeenByTouch(int x, int y){
        tileMap[this.x+x][this.y+y].setSeen(true);
        messages.findWallBlind();
    }
    

    @Override
    void hunt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
