
import java.util.LinkedList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public class CreatureFactory {
    
    private Tile[][] tileMap;
    private Player player;
    private LinkedList<Creature> creatureList = new LinkedList<>();
    private Random rand = new Random();
    private final int SPAWN_CHANCE_PERCENTAGE = 10;
    private int monsterCount = 0;
    private final int MAX_MONSTER_COUNT = 10;

    CreatureFactory(Tile[][] tileMap, Player player) {
        this.tileMap = tileMap;
        this.player = player;
    }
    

    void SpawnCreatures() {
        if(monsterCount < MAX_MONSTER_COUNT)
            if(rand.nextInt(101) < SPAWN_CHANCE_PERCENTAGE){
                addRandomMonster();
            }
    }

    void ActMonsters() {
        for(Creature c : creatureList){
            c.hunt();
        }
    }

    void resetMoves() {
    }

    private void addRandomMonster() {
        monsterCount++;
        int result = rand.nextInt(2);
        if(result == 0)
            creatureList.add(new MiningGnome(tileMap, player));
        else if(result == 1)
            creatureList.add(new Blob(tileMap, player));
    }
    
}
