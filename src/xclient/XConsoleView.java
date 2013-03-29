/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego A. Fons
 */
public class XConsoleView extends Thread {

    private Boolean done = Boolean.FALSE;

    public void stopThread() {
        done = Boolean.TRUE;
        interrupt();
    }

    @Override
    public void run() {
        XModel xm = XModel.getInstance();
        
        while (!done) {
            Collection<XData> values = XModel.getInstance().getValues();
            System.out.println("[CV] Data size: " + values.size());

            if (values.size() > 0) {
                System.out.print("[CV] Nodes: ");
                for (XData xData : values) {
                    System.out.print("" + xData.getNodeID() + ", ");
                }
                System.out.println();
            }

            System.out.print("[CV] Light: "
                    + xm.getLightThreshold() + " "
                    + xm.getLightMote1() + " "
                    + xm.getLightMote2() + " "
                    + xm.getLightAlarm() + "\n");

            System.out.print("[CV] Move: "
                    + xm.getMoveThreshold() + " "
                    + xm.getMoveMote1() + " "
                    + xm.getMoveMote2() + " "
                    + xm.getMoveAlarm() + "\n");

            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(XConsoleView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
