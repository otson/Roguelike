
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
public class MiningGnome extends HostileCreature{

    public MiningGnome(Tile[][] tileMap, Player player) {
        super(tileMap, player);
        this.canDig = true;
        this.glyph = 'g';
        this.color = new Color(142, 88, 60);
        addToMap();
    }
    
}
