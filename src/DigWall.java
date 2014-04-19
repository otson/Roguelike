
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
public class DigWall extends Wall{

    public DigWall() {
        this.diggable = true;
        this.mapColor = Color.LIGHT_GRAY;
    }
}
