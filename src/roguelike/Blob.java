package roguelike;


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

    public Blob(Tile[][] tileMap, Player player, Messages messages){
        super(tileMap, player, messages);
        this.attack = 1;
        this.defense = 1;
        this.color = Color.YELLOW;
        this.glyph = 'b';
        this.maxHealth = 5;
        this.currentHealth = maxHealth;
        this.name = "Yellow Blob";
        addToMap();
    }
}
