package roguelike.mapobjects.walls;
import roguelike.mapobjects.MapObject;

/**
 *
 * @author otso
 */
public abstract class Wall extends MapObject{
    

    public Wall() {
        this.walkable = false;
        this.mapCharacter = '#';
        this.seeThrough = false;
        this.wall = true;
     
    }

}
