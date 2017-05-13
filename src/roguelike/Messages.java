/* 
 * Copyright (C) 2017 Otso Nuortimo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package roguelike;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author otso
 */
public class Messages extends JPanel {

    private JTextField fresh;
    private JTextField stale;
    private JTextField old;
    private Color freshColor = Color.WHITE;
    private Color staleColor = Color.LIGHT_GRAY;
    private Color oldColor = Color.GRAY;
    private Color bgColor = Color.BLACK;
    private GridLayout grid = new GridLayout(3, 1);

    Messages() {

    }

    Messages(int MESSAGES_HEIGHT, Font MESSAGES_FONT) {
        this.setPreferredSize(new Dimension(50, MESSAGES_HEIGHT));
        this.setFocusable(false);
        this.setBorder(null);
        this.setLayout(grid);
        this.setBackground(bgColor);
        fresh = new JTextField();
        stale = new JTextField();
        old = new JTextField();

        fresh.setFont(MESSAGES_FONT);
        stale.setFont(MESSAGES_FONT);
        old.setFont(MESSAGES_FONT);

        fresh.setFocusable(false);
        stale.setFocusable(false);
        old.setFocusable(false);

        fresh.setBackground(bgColor);
        stale.setBackground(bgColor);
        old.setBackground(bgColor);

        fresh.setForeground(freshColor);
        stale.setForeground(staleColor);
        old.setForeground(oldColor);

        fresh.setBorder(null);
        stale.setBorder(null);
        old.setBorder(null);

        this.add(old);
        this.add(stale);
        this.add(fresh);
        setInitialtext();
    }

    private void setInitialtext() {
        t("Welcome to Roguelike!");
        t("Controls: numpad to move, 'd' to dig, ',' to pick up items, 'i' to open inventory, yp and down arrows keys to use stairs.");
    }

    private void t(String message) {
        old.setText(stale.getText());
        stale.setText(fresh.getText());
        fresh.setText(message);
    }

    public void digDirection() {
        t("Which direction would you like to dig?");
    }

    public void wallDug() {
        t("You succesfully dig through the wall.");
    }

    public void wallDugFailed() {
        t("The wall is too hard to dig.");
    }

    public void digAir() {
        t("There's no wall there!");
    }

    public void hit(Creature target, Creature attacker, int damage) {
        if ("You".equals(attacker.name)) {
            if (damage == 0)
                t(attacker.name + " hit " + target.name.toLowerCase() + ", but the attack does no damage!");

            else
                t(attacker.name + " hit " + target.name.toLowerCase() + " for " + damage + " damage.");

        }
        else if (damage == 0)
            t(attacker.name + " hits " + target.name.toLowerCase() + ", but the attack does no damage.");

        else
            t(attacker.name + " hits " + target.name.toLowerCase() + " for " + damage + " damage.");

    }

    public void kill(Creature target, Creature attacker) {
        if ("You".equals(attacker.name)) {
            t(attacker.name + " kill " + target.name.toLowerCase() + "!");
        }
        else {
            t(attacker.name + " mortally hits  " + target.name.toLowerCase() + ". You die.");
        }
    }

    void toggleEyes(boolean eyesOpen) {
        if (eyesOpen) {
            t("You open your eyes. You can see again!");
        }
        else {
            t("You close your eyes. You can't see what is happening around you.");
        }
    }

    void findWallBlind() {
        t("Blind, you bump into solid wall.");
    }

    void goingUpDownstairs() {
        t("These stairs go down.");
    }

    void goingUpNoStairs() {
        t("There are no stairs here.");
    }

    void goingDownUpstairs() {
        t("These stairs go up.");
    }

    void goingDownNoStairs() {
        t("There are no stairs here.");
    }

    void goingUpStairs() {
        t("You go up the stairs.");
    }

    void goingDownStairs() {
        t("You go down the stairs.");
    }

    void youCloseDoor() {
        t("You close the door.");
    }

    void youOpenDoor() {
        t("You open the door.");
    }

    void doorDirection() {
        t("Which direction?");
    }

    void noDoor() {
        t("There's no door there.");
    }

}
