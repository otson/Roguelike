package roguelike;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author otso
 */
public class ContentPanel extends JPanel {

    private BorderLayout layout = new BorderLayout();
    private PlayScreen playScreen;
    private StartScreen startScreen;
    private endScreen endScreen;

    public ContentPanel() {

        this.setLayout(layout);
        this.add(new PlayScreen(), BorderLayout.CENTER);
    }
}
