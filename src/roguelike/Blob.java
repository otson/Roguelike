package roguelike;
import java.awt.Color;

/**
 *
 * @author otso
 */
public class Blob extends HostileCreature{

    public Blob(Tile[][] tileMap, Player player, Messages messages){
        super(tileMap, player, messages);
        this.attack = 4;
        this.defense = 1;
        this.color = Color.YELLOW;
        this.glyph = 'b';
        this.maxHealth = 5;
        this.currentHealth = maxHealth;
        this.name = "Yellow Blob";
        addToMap();
    }
}
