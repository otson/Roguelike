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
import java.util.HashMap;
import javax.swing.JPanel;
import roguelike.items.MiningGnomeCorpse;
import roguelike.items.Item;

/**
 *
 * @author otso
 */
class PlayScreen extends JPanel implements KeyListener {

    public static final HashMap<Integer, Item> ITEM_LIST = new HashMap<>();

    private final int MESSAGES_HEIGHT = 60;
    private final int MAP_WIDTH = 200;
    private final int MAP_HEIGHT = 40;
    private final Font MESSAGES_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private MapCreator mapCreator;
    private CreatureFactory creatureFactory;
    private Player player;
    private Messages messages;
    private Map map;
    private Stats stats;
    private int startLevel = 1;
    private boolean digEvent = false;
    private InventoryScreen inventoryScreen;
    private boolean inventoryIsActive = false;
    private boolean playIsActive = true;

    public PlayScreen() {

        initItemList();
        mapCreator = new MapCreator(startLevel, MAP_WIDTH, MAP_HEIGHT);
        initMessages();
        addPlayer();
        initStats();
        initInventory();
        createCreatureFactory();
        addContent();
    }
    
    private void initInventory(){
        inventoryScreen = new InventoryScreen(player);
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
        map = new Map(mapCreator.getLevels(), player);
        inventoryScreen.setPreferredSize(map.getPreferredSize());
        showMap();
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
        if (playIsActive) {
            respondToPlayInput(e);
        }
        else if (inventoryIsActive) {
            respondToInventoryInput(e);
        }
    }

    public void respondToPlayInput(KeyEvent e) {
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
            case KeyEvent.VK_COMMA:
                player.pickUpItems();
                break;
            case KeyEvent.VK_PERIOD:
                player.dropItems();
                break;
            case KeyEvent.VK_I:
                toggleInventory();
                break;
        }

        if (player.getMovesLeft() == 0) {
            turnEnd();
        }
        repaint();

    }

    public void respondToInventoryInput(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_I:
                toggleInventory();
                break;
        }
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

    private void initItemList() {
        ITEM_LIST.put(0, new MiningGnomeCorpse());
    }

    private void showMap() {
        this.setLayout(new BorderLayout());
        this.add(map, BorderLayout.CENTER);
        this.add(stats, BorderLayout.SOUTH);
        this.add(messages, BorderLayout.NORTH);
    }

    private void toggleInventory() {
        if (!inventoryIsActive) {
            this.remove(map);
            this.add(inventoryScreen, BorderLayout.CENTER);
            validate();
            repaint();
            inventoryIsActive = true;
            playIsActive = false;
        }
        else {
            this.remove(inventoryScreen);
            this.add(map, BorderLayout.CENTER);
            validate();
            repaint();
            inventoryIsActive = false;
            playIsActive = true;
        }
    }

}
