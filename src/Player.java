
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
public class Player extends Creature{

    public Player(Tile[][] tileMap) {
        
        super(tileMap);
        this.glyph = '@';
        this.attack = 3;
        this.defense = 2;
        this.maxHealth = 15;
        this.currentHealth = this.maxHealth;
        this.color = Color.WHITE;
    }

}
