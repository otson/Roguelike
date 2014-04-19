
import java.awt.Color;

/**
 *
 * @author otso
 */
public abstract class Wall extends MapObject{
    

    public Wall() {
        this.walkable = false;
        this.mapCharacter = '#';
        this.currentCharacter = this.mapCharacter;
        this.seeThrough = false;
        this.wall = true;
     
    }

}
