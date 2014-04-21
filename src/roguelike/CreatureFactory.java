package roguelike;


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
    private Random rand = new Random();
    private final int SPAWN_CHANCE_PERCENTAGE = 50;
    private int monsterCount = 0;
    private final int MAX_MONSTER_COUNT = 200;
    private Messages messages;

    CreatureFactory(Tile[][] tileMap, Player player, Messages messages) {
        this.messages = messages;
        this.tileMap = tileMap;
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
    
}
