
import java.awt.Color;

/**
 *
 * @author otso
 */
public abstract class Wall extends MapObject{
    
    protected boolean diggable;

    public Wall() {
        this.walkable = false;
        this.mapCharacter = '#';
        this.seeThrough = false;
        this.wall = true;
     
    }

    public boolean isDiggable() {
        return diggable;
    }
    
}
