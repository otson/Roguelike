package roguelike.walls;
import roguelike.MapObject;

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
