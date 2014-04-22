package roguelike;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author otso
 */
public class CreatureFactory {
    
    private Tile[][] tileMap;
    private Player player;
    private LinkedList<Creature> creatureList = new LinkedList<>();
    private HashMap<Integer, LinkedList<Creature>> creatureList2 = new HashMap<>();
    HashMap<Integer, Tile[][]> levels;
    private Random rand = new Random();
    private final int SPAWN_CHANCE_PERCENTAGE = 50;
    private int monsterCount = 0;
    private final int MAX_MONSTER_COUNT = 200;
    private Messages messages;

    CreatureFactory(HashMap<Integer, Tile[][]> levels, Player player, Messages messages) {
        this.messages = messages;
        this.levels = levels;
        this.player = player;
    }
    
    public void SpawnCreatures() {
        if(monsterCount < MAX_MONSTER_COUNT)
            if(rand.nextInt(101) < SPAWN_CHANCE_PERCENTAGE){
                addRandomMonster();
            }
    }

    public void ActMonsters() {
        for(int i = 0; i<creatureList.size(); i++){
        Creature c = creatureList.get(i);
            if(!c.isAlive()){
                creatureList.remove(c);
                i--;
                monsterCount--;
            }
            else
                c.hunt();
        }
    }

    public void resetMoves() {
        for(Creature c : creatureList){
            c.resetMoves();
        }
    }

    private void addRandomMonster() {
        monsterCount++;
        int result = rand.nextInt(2);
        if(result == 0)
            creatureList.add(new MiningGnome(tileMap, player, messages));
        else if(result == 1)
            creatureList.add(new Blob(tileMap, player, messages));
        System.out.println("Monster count: "+monsterCount);
    }
    
    private void addRandomMonster(int amount) {
        int i = 0;
        while(i<amount){
            monsterCount++;
            int result = rand.nextInt(2);
            if(result == 0)
                creatureList.add(new MiningGnome(tileMap, player, messages));
            else if(result == 1)
                creatureList.add(new Blob(tileMap, player, messages));
            i++;
        }
        System.out.println("Monster count: "+monsterCount);
    }

    void setCreatureLevel(int level) {
        if(creatureList2.containsKey(level))
            creatureList = creatureList2.get(level);
        else{
            creatureList = new LinkedList<>();
            creatureList2.put(level, creatureList);
        }
        tileMap = levels.get(level);
            
    }
    
}
