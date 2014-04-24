/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelike;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
class PlayScreen extends JPanel implements KeyListener {

    private final int MESSAGES_HEIGHT = 60;
    private final int MAP_WIDTH = 200;
    private final int MAP_HEIGHT = 40;
    private final Font MESSAGES_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private MapCreator mapCreator;

    private CreatureFactory creatureFactory;

    private boolean digEvent = false;
    private Player player;
    private Messages messages;
    private Map map;
    private Stats stats;
    private int startLevel = 1;

    public PlayScreen() {

        mapCreator = new MapCreator(startLevel, MAP_WIDTH, MAP_HEIGHT);
        initMessages();
        addPlayer();
        initStats();
        createCreatureFactory();
        addContent();
    }

    private void createCreatureFactory() {
        creatureFactory = new CreatureFactory(mapCreator.getLevels(), player, messages);
    }

    private void addPlayer() {
        player = new Player(mapCreator.getLevels().get(startLevel), player, messages, mapCreator);
        player.setLevel(startLevel);
    }

    private void addContent() {
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        map = new Map(mapCreator.getLevels(), player);
        this.add(map, BorderLayout.CENTER);
        this.add(stats, BorderLayout.SOUTH);
        this.add(messages, BorderLayout.NORTH);
    }

    private void initMessages() {
        messages = new Messages(MESSAGES_HEIGHT, MESSAGES_FONT);
    }

    private void initStats() {
        stats = new Stats(player);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_NUMPAD7:
                if (digEvent) {
                    player.dig(-1, -1);
                    digEvent = false;
                }
                else {
                    player.move(-1, -1);
                }
                break;
            case KeyEvent.VK_NUMPAD8:
                if (digEvent) {
                    player.dig(0, -1);
                    digEvent = false;
                }
                else {
                    player.move(0, -1);
                }
                break;
            case KeyEvent.VK_NUMPAD9:
                if (digEvent) {
                    player.dig(1, -1);
                    digEvent = false;
                }
                else {
                    player.move(1, -1);
                }
                break;
            case KeyEvent.VK_NUMPAD4:
                if (digEvent) {
                    player.dig(-1, 0);
                    digEvent = false;
                }
                else {
                    player.move(-1, 0);
                }
                break;
            case KeyEvent.VK_NUMPAD5:
                player.Wait(1);
                break;
            case KeyEvent.VK_NUMPAD6:
                if (digEvent) {
                    player.dig(1, 0);
                    digEvent = false;
                }
                else {
                    player.move(1, 0);
                }
                break;
            case KeyEvent.VK_NUMPAD1:
                if (digEvent) {
                    player.dig(-1, 1);
                    digEvent = false;
                }
                else {
                    player.move(-1, 1);
                }
                break;
            case KeyEvent.VK_NUMPAD2:
                if (digEvent) {
                    player.dig(0, 1);
                    digEvent = false;
                }
                else {
                    player.move(0, 1);
                }

                break;
            case KeyEvent.VK_NUMPAD3:
                if (digEvent) {
                    player.dig(1, 1);
                    digEvent = false;
                }
                else {
                    player.move(1, 1);
                }
                break;
            case KeyEvent.VK_D:
                messages.digDirection();
                digEvent = true;
                break;
            case KeyEvent.VK_C:
                player.toggleEyes();
                break;
            case KeyEvent.VK_UP:
                player.goUp();
                break;
            case KeyEvent.VK_DOWN:
                player.goDown();
                break;
        }
        if (player.getMovesLeft() == 0) {
            turnEnd();
        }
        map.repaint();
        stats.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void turnEnd() {
        creatureFactory.setCreatureLevel(player.level);
        creatureFactory.ActMonsters();
        turnStart();
    }

    private void turnStart() {
        creatureFactory.setCreatureLevel(player.level);
        creatureFactory.SpawnCreatures();
        creatureFactory.resetMoves();
        player.resetMoves();
        player.FOV();
        player.checkRegen();
    }

}
