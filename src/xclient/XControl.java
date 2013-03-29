/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego A. Fons
 */
public class XControl extends Thread {

    private Boolean done = Boolean.FALSE;

    public void stopThread() {
        done = Boolean.TRUE;
        interrupt();
    }

    @Override
    public void run() {
        XModel xm = XModel.getInstance();
        
        while (!done) {
            Integer lightMote1ID = xm.getLightMote1();
            Integer lightMote2ID = xm.getLightMote2();
            Integer moveMote1ID = xm.getMoveMote1();
            Integer moveMote2ID = xm.getMoveMote2();

            System.out.println("[XC] MOVE - IDs: " + moveMote1ID + " " + moveMote2ID);
            
            if ((moveMote1ID != -1) && (moveMote2ID != -1)) {
                Double x1 = xm.getInstance().get(moveMote1ID).getAccelX();
                Double x2 = xm.getInstance().get(moveMote2ID).getAccelX();
                Double y1 = xm.getInstance().get(moveMote1ID).getAccelY();
                Double y2 = xm.getInstance().get(moveMote2ID).getAccelY();
                
                System.out.println("[XC] MOVE - X Values: " + x1 + " " + x2);
                System.out.println("[XC] MOVE - Y Values: " + y1 + " " + y2);

                Boolean test1 = (x1 > xm.getMoveThreshold()) && (x2 > xm.getMoveThreshold());
                Boolean test2 = (y1 > xm.getMoveThreshold()) && (y2 > xm.getMoveThreshold());

                xm.setMoveAlarm(test1 || test2);
                
                System.out.println("[XC] MOVE - Alarm: " + xm.getMoveAlarm());
            }

            System.out.println("[XC] LIGHT - IDs: " + lightMote1ID + " " + lightMote2ID);
            
            if ((lightMote1ID != -1) && (lightMote2ID != -1)) {
                Double l1 = XModel.getInstance().get(lightMote1ID).getLight();
                Double l2 = XModel.getInstance().get(lightMote2ID).getLight();

                System.out.println("[XC] LIGHT - Values: " + l1 + " " + l2);

                xm.setLightAlarm((l1 < xm.getLightThreshold()) && (l2 < xm.getLightThreshold()));

                System.out.println("[XC] LIGHT - Alarm: " + xm.getLightAlarm());
            }
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(XConsoleView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
