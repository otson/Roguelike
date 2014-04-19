/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
class Tile {
    
    private int x;
    private int y;
    private MapObject mapObject;
    
    public Tile(){
        
    }
    
    public Tile(MapObject mapObject, int x, int y){
        this.x = x;
        this.y = y;
        this.mapObject = mapObject;
    }

    public MapObject getMapObject() {
        return mapObject;
    }
    
    
}
