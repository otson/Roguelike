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
public class MiningGnome extends HostileCreature {

    public MiningGnome(Tile[][] tileMap, Player player, Messages messages) {
        super(tileMap, player, messages);
        this.canDig = true;
        this.glyph = 'g';
        this.attack = 5;
        this.defense = 2;
        this.maxHealth = 10;
        this.currentHealth = maxHealth;
        this.color = new Color(142, 88, 60);
        this.name = "Gnome Miner";
        addToMap();
    }

}
