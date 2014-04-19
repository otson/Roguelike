
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public class Blob extends HostileCreature{

    public Blob(Tile[][] tileMap, Player player) {
        super(tileMap, player);
        this.attack = 1;
        this.defense = 1;
        this.color = Color.YELLOW;
        this.glyph = 'b';
        this.maxHealth = 5;
        addToMap();
    }

}
