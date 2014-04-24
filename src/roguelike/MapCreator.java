package roguelike;

import java.util.HashMap;
import java.util.Random;
import roguelike.mapobjects.walls.DigWall;
import roguelike.mapobjects.walls.DigWallCross;
import roguelike.mapobjects.walls.DigWallDownLeft;
import roguelike.mapobjects.walls.DigWallDownRight;
import roguelike.mapobjects.walls.DigWallHorizontal;
import roguelike.mapobjects.walls.DigWallMiddle;
import roguelike.mapobjects.walls.DigWallTDown;
import roguelike.mapobjects.walls.DigWallTLeft;
import roguelike.mapobjects.walls.DigWallTRight;
import roguelike.mapobjects.walls.DigWallTUp;
import roguelike.mapobjects.walls.DigWallUpLeft;
import roguelike.mapobjects.walls.DigWallUpRight;
import roguelike.mapobjects.walls.DigWallVertical;
import roguelike.mapobjects.walls.SolidWall;

/**
 *
 * @author otso
 */
public class MapCreator {

    private final int WALL_PERCENTAGE = 45;
    private int width;
    private int height;
    private Random rand = new Random();
    private HashMap<Integer, Tile[][]> levels = new HashMap<>();

    MapCreator(int level, int width, int height) {
        this.width = width;
        this.height = height;
        createLevels(10);

    }

    private void createLevels(int count) {
        int i = count / 2 - count;
        while (i < count / 2) {
            createLevel(i);
            i++;
        }
    }

    private void generateBorders(Tile[][] tileMap) {
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap[x].length; y++) {
                tileMap[x][y] = new Tile(new SolidWall(), x, y);
            }
        }
    }

    private void generateRandomness(Tile[][] tileMap, int wallPercentage) {
        for (int x = 1; x + 1 < tileMap.length; x++) {
            for (int y = 1; y + 1 < tileMap[x].length; y++) {
                if (rand.nextInt(101) > wallPercentage) {
                    tileMap[x][y] = new Tile(new Floor(), x, y);
                }
                else {
                    tileMap[x][y] = new Tile(new DigWall(), x, y);
                }

            }
        }
    }

    private void generateCaves(Tile[][] tileMap, int rounds, int birth, int alive) {
        int[][] wallCount = new int[tileMap.length][tileMap[0].length];
        int count = 0;
        int times = 0;

        while (times < rounds) {
            for (int x = 1; x + 1 < tileMap.length; x++) {
                for (int y = 1; y + 1 < tileMap[x].length; y++) {
                    if (tileMap[x - 1][y - 1].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x - 1][y].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x - 1][y + 1].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x][y - 1].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x][y + 1].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x + 1][y - 1].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x + 1][y].getMapObject().isWall()) {
                        count++;
                    }
                    if (tileMap[x + 1][y + 1].getMapObject().isWall()) {
                        count++;
                    }
                    wallCount[x][y] = count;
                    count = 0;
                }
            }
            for (int x = 1; x + 1 < tileMap.length; x++) {
                for (int y = 1; y + 1 < tileMap[x].length; y++) {
                    if (tileMap[x][y].getMapObject().isWall()) {
                        if (wallCount[x][y] < alive) {
                            tileMap[x][y] = new Tile(new Floor(), x, y);
                        }
                    }
                    else if (!tileMap[x][y].getMapObject().isWall()) {
                        if (wallCount[x][y] >= birth) {
                            tileMap[x][y] = new Tile(new DigWall(), x, y);
                        }
                    }
                }
            }
            times++;
        }
    }

    private void finalizeWalls(Tile[][] tileMap) {
        boolean upLeft;
        boolean upMid;
        boolean upRight;
        boolean midLeft;
        boolean midRight;
        boolean downLeft;
        boolean downMid;
        boolean downRight;

        for (int x = 1; x + 1 < tileMap.length; x++) {
            for (int y = 1; y + 1 < tileMap[x].length; y++) {
                if (tileMap[x][y].getMapObject().isWall()) {
                    upLeft = tileMap[x - 1][y - 1].getMapObject().isWall();
                    upMid = tileMap[x - 1][y].getMapObject().isWall();
                    upRight = tileMap[x - 1][y + 1].getMapObject().isWall();
                    midLeft = tileMap[x][y - 1].getMapObject().isWall();
                    midRight = tileMap[x][y + 1].getMapObject().isWall();
                    downLeft = tileMap[x + 1][y - 1].getMapObject().isWall();
                    downMid = tileMap[x + 1][y].getMapObject().isWall();
                    downRight = tileMap[x + 1][y + 1].getMapObject().isWall();

                    if (midRight && midLeft && ((!upMid && downLeft && downRight) || (!downMid && upLeft && upRight))) {
                        tileMap[x][y] = new Tile(new DigWallHorizontal(), x, y);
                    }

                    else if (upMid && downMid && ((!midRight && upLeft && downLeft) || (!midLeft && upRight && downRight))) {
                        tileMap[x][y] = new Tile(new DigWallVertical(), x, y);
                    }

                    else if (upLeft && upMid && upRight && midLeft && midRight && downLeft && downMid && downRight) {
                        tileMap[x][y] = new Tile(new DigWallMiddle(), x, y);
                    }

                    // corners
                    else if (downMid && midRight && !upMid && !midLeft) {
                        tileMap[x][y] = new Tile(new DigWallUpLeft(), x, y);
                    }

                    else if (downMid && midLeft && !upMid && !midRight) {
                        tileMap[x][y] = new Tile(new DigWallUpRight(), x, y);
                    }

                    else if (upMid && midRight && !downMid && !midLeft) {
                        tileMap[x][y] = new Tile(new DigWallDownLeft(), x, y);
                    }

                    else if (upMid && midLeft && !downMid && !midRight) {
                        tileMap[x][y] = new Tile(new DigWallDownRight(), x, y);
                    }

                        // crossing
                    else if ((!upRight && !downLeft) || (!downRight && !upLeft)) {
                        tileMap[x][y] = new Tile(new DigWallCross(), x, y);
                    }

                    // T corners
                    else if (!upMid) {
                        tileMap[x][y] = new Tile(new DigWallTUp(), x, y);
                    }

                    else if (!midRight) {
                        tileMap[x][y] = new Tile(new DigWallTRight(), x, y);
                    }

                    else if (!downMid) {
                        tileMap[x][y] = new Tile(new DigWallTDown(), x, y);
                    }

                    else if (!midLeft) {
                        tileMap[x][y] = new Tile(new DigWallTLeft(), x, y);
                    }

                    // more corners
                    else if (!downRight) {
                        tileMap[x][y] = new Tile(new DigWallUpLeft(), x, y);
                    }

                    else if (!downLeft) {
                        tileMap[x][y] = new Tile(new DigWallUpRight(), x, y);
                    }

                    else if (!upRight) {
                        tileMap[x][y] = new Tile(new DigWallDownLeft(), x, y);
                    }

                    else if (!upLeft) {
                        tileMap[x][y] = new Tile(new DigWallDownRight(), x, y);
                    }

                    else {
                        tileMap[x][y] = new Tile(new DigWallMiddle(), x, y);
                    }
                }
            }
        }
    }

    private void addStairs(Tile[][] tileMap) {
        boolean notSet = true;
        int count = 0;
        while (notSet) {
            count++;
            int xx = rand.nextInt(tileMap.length - 1) + 1;
            int yy = rand.nextInt(tileMap[xx].length - 1) + 1;
            if (tileMap[xx][yy].isNotOccupied() && tileMap[xx][yy].isWalkable()) {
                tileMap[xx][yy].setMapObject(new UpStairs());
                notSet = false;
            }
            else if (count == 100) {
                System.out.println("Failed to place upstairs.");
                notSet = false;
            }
        }
        notSet = true;
        count = 0;
        while (notSet) {
            count++;
            int xx = rand.nextInt(tileMap.length - 1) + 1;
            int yy = rand.nextInt(tileMap[xx].length - 1) + 1;
            if (tileMap[xx][yy].isNotOccupied() && tileMap[xx][yy].isWalkable()) {
                tileMap[xx][yy].setMapObject(new DownStairs());
                notSet = false;
            }
            else if (count == 100) {
                System.out.println("Failed to place downstairs.");
                notSet = false;
            }
        }
    }

    public HashMap<Integer, Tile[][]> getLevels() {
        return levels;
    }

    public final void createLevel(int level) {
        Tile[][] tileMap = new Tile[width][height];
        //System.out.println("Use .length for maximum x, [0].length for maximum y");
        generateBorders(tileMap);
        generateRandomness(tileMap, WALL_PERCENTAGE);
        generateCaves(tileMap, 25, 5, 4);
        addStairs(tileMap);
        finalizeWalls(tileMap);
        levels.put(level, tileMap);
        System.out.println(levels.size());
    }

}
