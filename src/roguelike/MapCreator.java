package roguelike;


import java.util.Random;
import roguelike.walls.DigWall;
import roguelike.walls.DigWallCross;
import roguelike.walls.DigWallDownLeft;
import roguelike.walls.DigWallDownRight;
import roguelike.walls.DigWallHorizontal;
import roguelike.walls.DigWallMiddle;
import roguelike.walls.DigWallTDown;
import roguelike.walls.DigWallTLeft;
import roguelike.walls.DigWallTRight;
import roguelike.walls.DigWallTUp;
import roguelike.walls.DigWallUpLeft;
import roguelike.walls.DigWallUpRight;
import roguelike.walls.DigWallVertical;
import roguelike.walls.SolidWall;

/**
 *
 * @author otso
 */
public class MapCreator {
    
    public Tile[][] tileMap;
    private final int WALL_PERCENTAGE = 45;
    private final int CAVE_ROUNDS = 5;
    private final int BIRTH = 5;
    private final int STAY_ALIVE = 4;
    private Random rand;

    MapCreator(int MAP_ROWS, int MAP_COLUMNS) {
        rand = new Random();
        tileMap = new Tile[MAP_ROWS][MAP_COLUMNS];
        
        generateBorders();   
        generateRandomness(WALL_PERCENTAGE);
        generateCaves(25, 5, 4);
        finalizeWalls();
    }
    
    private void generateBorders(){
        for(int i = 0; i<tileMap.length; i++){
            for(int j = 0; j<tileMap[i].length; j++){
                tileMap[i][j] = new Tile(new SolidWall(), i, j);
            }
        }
    }
    
    private void generateRandomness(int wallPercentage){
        for(int i = 1; i+1<tileMap.length; i++){
            for(int j = 1; j+1<tileMap[i].length; j++){
                if(rand.nextInt(101) > wallPercentage)
                    tileMap[i][j] = new Tile(new Floor(), i, j);
                else{
                    tileMap[i][j] = new Tile(new DigWall(), i, j);
                }
                
            }
        }
    }
    
    private void generateCaves(int rounds, int birth, int alive){
        int[][] wallCount = new int[tileMap.length][tileMap[0].length];
        int count = 0;
        int times = 0;
        
        while(times < rounds){
            for(int i = 1; i+1<tileMap.length; i++){
                for(int j = 1; j+1<tileMap[i].length; j++){
                    if(tileMap[i-1][j-1].getMapObject().isWall())
                        count++;
                    if(tileMap[i-1][j].getMapObject().isWall())
                        count++;
                    if(tileMap[i-1][j+1].getMapObject().isWall())
                        count++;
                    if(tileMap[i][j-1].getMapObject().isWall())
                        count++;
                    if(tileMap[i][j+1].getMapObject().isWall())
                        count++;
                    if(tileMap[i+1][j-1].getMapObject().isWall())
                        count++;
                    if(tileMap[i+1][j].getMapObject().isWall())
                        count++;
                    if(tileMap[i+1][j+1].getMapObject().isWall())
                        count++;
                    wallCount[i][j] = count;
                    count = 0;
                }
            }
            for(int i = 1; i+1<tileMap.length; i++){
                for(int j = 1; j+1<tileMap[i].length; j++){
                    if(tileMap[i][j].getMapObject().isWall()){
                        if(wallCount[i][j] < alive )               
                            tileMap[i][j] = new Tile(new Floor(), i, j);
                    }
                    else if(!tileMap[i][j].getMapObject().isWall()){
                        if(wallCount[i][j] >= birth)
                            tileMap[i][j] = new Tile(new DigWall(), i, j); 
                    }
                }
            }
            times++;
        }
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public void setTileMap(Tile[][] tileMap) {
        this.tileMap = tileMap;
    }

    private void finalizeWalls() {
        boolean upLeft;
        boolean upMid;
        boolean upRight;
        boolean midLeft;
        boolean midRight;
        boolean downLeft;
        boolean downMid;
        boolean downRight;
        
        
        for(int i = 1; i+1<tileMap.length; i++){
                for(int j = 1; j+1<tileMap[i].length; j++){
                    if(tileMap[i][j].getMapObject().isWall()){
                        upLeft = tileMap[i-1][j-1].getMapObject().isWall();
                        upMid = tileMap[i-1][j].getMapObject().isWall();
                        upRight = tileMap[i-1][j+1].getMapObject().isWall();
                        midLeft = tileMap[i][j-1].getMapObject().isWall();
                        midRight = tileMap[i][j+1].getMapObject().isWall();
                        downLeft = tileMap[i+1][j-1].getMapObject().isWall();
                        downMid = tileMap[i+1][j].getMapObject().isWall();
                        downRight = tileMap[i+1][j+1].getMapObject().isWall();
                        
                        if(midRight && midLeft && ((!upMid && downLeft && downRight) || (!downMid && upLeft && upRight)))
                            tileMap[i][j] = new Tile(new DigWallHorizontal(), i, j);
                        
                        else if(upMid && downMid && ((!midRight && upLeft && downLeft) || (!midLeft && upRight && downRight)))
                            tileMap[i][j] = new Tile(new DigWallVertical(), i, j);
                        
                        else if(upLeft && upMid && upRight && midLeft && midRight && downLeft && downMid && downRight)
                            tileMap[i][j] = new Tile(new DigWallMiddle(), i, j);
                        
                        // corners
                        else if(downMid && midRight && !upMid && !midLeft)
                            tileMap[i][j] = new Tile(new DigWallUpLeft(), i, j);
                        
                        else if(downMid && midLeft && !upMid && !midRight)
                            tileMap[i][j] = new Tile(new DigWallUpRight(), i, j);
                        
                        else if(upMid && midRight && !downMid && !midLeft)
                            tileMap[i][j] = new Tile(new DigWallDownLeft(), i, j);
                        
                        else if(upMid && midLeft && !downMid && !midRight)
                            tileMap[i][j] = new Tile(new DigWallDownRight(), i, j);
                        
                        // crossing
                        
                        else if((!upRight && !downLeft) || (!downRight && !upLeft))
                            tileMap[i][j] = new Tile(new DigWallCross(), i, j);
                        
                        // T corners
                        else if(!upMid)
                            tileMap[i][j] = new Tile(new DigWallTUp(), i, j);
                        
                        else if(!midRight)
                            tileMap[i][j] = new Tile(new DigWallTRight(), i, j);
                        
                        else if(!downMid)
                            tileMap[i][j] = new Tile(new DigWallTDown(), i, j);
                        
                        else if(!midLeft)
                            tileMap[i][j] = new Tile(new DigWallTLeft(), i, j);
                        
                        // more corners
                        else if(!downRight)
                            tileMap[i][j] = new Tile(new DigWallUpLeft(), i, j);
                        
                        else if(!downLeft)
                            tileMap[i][j] = new Tile(new DigWallUpRight(), i, j);
                        
                        else if(!upRight)
                            tileMap[i][j] = new Tile(new DigWallDownLeft(), i, j);
                        
                        else if(!upLeft)
                            tileMap[i][j] = new Tile(new DigWallDownRight(), i, j);
                        
                        
                        
                        else
                            tileMap[i][j] = new Tile(new DigWallMiddle(), i, j);
                    }
                }
        }
    }

}
