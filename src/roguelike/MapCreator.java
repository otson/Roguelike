package roguelike;

import java.util.HashMap;
import java.util.Random;
import roguelike.mapobjects.Door;
import roguelike.mapobjects.RoomFloor;
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
                    upLeft = tileMap[x - 1][y - 1].getMapObject().isWallOrDoor();
                    upMid = tileMap[x - 1][y].getMapObject().isWallOrDoor();
                    upRight = tileMap[x - 1][y + 1].getMapObject().isWallOrDoor();
                    midLeft = tileMap[x][y - 1].getMapObject().isWallOrDoor();
                    midRight = tileMap[x][y + 1].getMapObject().isWallOrDoor();
                    downLeft = tileMap[x + 1][y - 1].getMapObject().isWallOrDoor();
                    downMid = tileMap[x + 1][y].getMapObject().isWallOrDoor();
                    downRight = tileMap[x + 1][y + 1].getMapObject().isWallOrDoor();

                    if (midRight && midLeft && ((!upMid && downLeft && downRight) || (!downMid && upLeft && upRight) || (!downMid && !upMid))) {
                        tileMap[x][y] = new Tile(new DigWallHorizontal(), x, y);
                    }

                    else if (upMid && downMid && ((!midRight && upLeft && downLeft) || (!midLeft && upRight && downRight) || (!midLeft && !midRight))) {
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

    private void addRooms(Tile[][] tileMap, int count) {
        int roomWidth;
        int roomHeight;
        for (int i = 0; i < count; i++) {
            roomWidth = rand.nextInt(8) + 4;
            roomHeight = rand.nextInt(6) + 3;
            addRoom(tileMap, roomWidth, roomHeight);
        }
    }

    private void addRoom(Tile[][] tileMap, int width, int height) {
        boolean notSet = true;
        int x, y;
        int minDistanceFromBorder = 2;
        int counter = 0;
        while (notSet) {
            counter++;
            x = rand.nextInt(tileMap.length - (width + 2 * minDistanceFromBorder)) + minDistanceFromBorder;
            y = rand.nextInt(tileMap[x].length - (height + 2 * minDistanceFromBorder)) + minDistanceFromBorder;
            boolean valid = true;
            for (int x0 = 0; x0 < width; x0++) {
                for (int y0 = 0; y0 < height; y0++) {
                    if (tileMap[x + x0][y + y0].getMapObject().isRoomFloor()) {
                        valid = false;
                    }
                }
            }
            if (valid) {
                int doorSpots = (width - 2) * 2 + (height - 2) * 2;
                int doorInt = rand.nextInt(doorSpots);
                int doorCounter = 0;
                boolean doorSet = false;
                for (int x0 = -1; x0 < width + 1; x0++) {
                    for (int y0 = -1; y0 < height + 1; y0++) {
                        tileMap[x + x0][y + y0].setMapObject(new RoomFloor());
                    }
                }

                for (int x0 = 0; x0 < width; x0++) {
                    for (int y0 = 0; y0 < height; y0++) {
                        if (x0 == 0 || y0 == 0 || x0 == width - 1 || y0 == height - 1) {
                            if (!doorSet && doorCounter >= doorInt && (x0 != 0 || y0 != 0) && (x0 != 0 || y0 != height - 1) && (x0 != width - 1 || y0 != 0) && (x0 != width - 1 || y0 != height - 1)) {
                                tileMap[x + x0][y + y0].setMapObject(new Door());
                                doorSet = true;
                            }
                            else {
                                tileMap[x + x0][y + y0].setMapObject(new DigWall());
                            }
                            doorCounter++;
                        }
                    }
                }
                notSet = false;
            }
            else {
                if (counter == 100) {
                    System.out.println("Failed to place room after " + counter + " tries.");
                    return;
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
        generateBorders(tileMap);
        generateRandomness(tileMap, WALL_PERCENTAGE);
        generateCaves(tileMap, 25, 5, 4);
        //addRooms(tileMap, 100);
        addStairs(tileMap);
        finalizeWalls(tileMap);
        levels.put(level, tileMap);
        System.out.println(levels.size());
    }

}
