
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author otso
 */
public class Messages {
    
    private JTextArea messageArea;

    Messages(JTextArea messageArea) {
        this.messageArea = messageArea;
        setInitialtext();
    }

    private void setInitialtext() {
        t("Messages are working correctly.");
    }

    private void t(String message) {
        messageArea.setText(message);
    }
    
    public void digDirection(){
        t("Which direction would you like to dig?");
    }

    void wallDug() {
        t("You succesfully dig through the wall.");
    }

    void wallDugFailed() {
        t("The wall is too hard to dig.");
    }

    void digAir() {
        t("There's no wall there!");
    }
    
}
