/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego A. Fons
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create and start reader.
        XReader2 xReader = new XReader2("157.92.51.250", 9002);
        xReader.start();

        // Create and start console view.
        XConsoleView xConsole = new XConsoleView();
        xConsole.start();
        
        // Create and start control.
        XControl xControl = new XControl();
        xControl.start();

        // Create and start GUI view.
        XGUIView xGUI = new XGUIView();
        xGUI.start();

        // Wait for threads.
        try {
            System.out.println("Waiting for threads...");
            xReader.join();
            xConsole.join();
            xControl.join();
            xGUI.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
