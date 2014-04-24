package roguelike;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author otso
 */
public class Roguelike2 {
    
    private static ContentPanel content;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        int[][] array = new int[][]{{1, 2, 3}, {3, 4, 5}, {7, 8, 9}};
        System.out.println(array[2][0]);

        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Roguelike");
        frame.add(getContent());
        frame.setResizable(false);
        frame.setFocusable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel getContent() {

        content = new ContentPanel();
        return content;
    }

}
