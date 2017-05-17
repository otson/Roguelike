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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
public class ContentPanel extends JPanel{

    private BorderLayout layout = new BorderLayout();
    private PlayScreen playScreen;
    private StartScreen startScreen;
    private endScreen endScreen;
    private InventoryScreen inventoryScreen;
    boolean inventoryIsActive = false;
    boolean playIsActive = false;
    boolean startIsActive = false;
    boolean endIsActive = false;
    
    public ContentPanel() {

        playScreen = new PlayScreen();
        this.setLayout(layout);
        this.add(new PlayScreen(), BorderLayout.CENTER);
        playIsActive = true;
        this.requestFocusInWindow();
    }

}
