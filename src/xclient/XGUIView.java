/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Diego A. Fons
 */
public class XGUIView extends Thread {

    private Boolean done = Boolean.FALSE;
    private XClientGUI gui = new XClientGUI();
    private XModel xm = XModel.getInstance();
    private DefaultComboBoxModel<String> light1ComboModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel<String> light2ComboModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel<String> move1ComboModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel<String> move2ComboModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel<String> viewMoteComboModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel<String> angleComboModel = new DefaultComboBoxModel();

    public XGUIView() {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                gui.setVisible(true);
            }
        });

        light1ComboModel.removeAllElements();
        light1ComboModel.addElement("<none>");
        gui.light1Combo.setModel(light1ComboModel);

        light2ComboModel.removeAllElements();
        light2ComboModel.addElement("<none>");
        gui.light2Combo.setModel(light2ComboModel);

        move1ComboModel.removeAllElements();
        move1ComboModel.addElement("<none>");
        gui.move1Combo.setModel(move1ComboModel);

        move2ComboModel.removeAllElements();
        move2ComboModel.addElement("<none>");
        gui.move2Combo.setModel(move2ComboModel);

        viewMoteComboModel.removeAllElements();
        viewMoteComboModel.addElement("<none>");
        gui.viewMoteCombo.setModel(viewMoteComboModel);
        
        angleComboModel.removeAllElements();
        angleComboModel.addElement("<none>");
        gui.angleCombo.setModel(angleComboModel);
    }

    public void stopThread() {
        done = Boolean.TRUE;
        interrupt();
    }

    @Override
    public void run() {
        while (!done) {
            if (xm.isModified()) {
                updateComboModel();
                updateAlarmStatus();
                updateMoteView();
                updateAngleView();
            }

            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(XConsoleView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void updateComboModel() {
        Collection<Integer> values = xm.getKeys();

        for (Integer id : values) {
            Integer prevIndex = light1ComboModel.getIndexOf(Integer.toString(id));
            if (prevIndex == -1) {
                light1ComboModel.addElement(Integer.toString(id));
            }

            prevIndex = light2ComboModel.getIndexOf(Integer.toString(id));
            if (prevIndex == -1) {
                light2ComboModel.addElement(Integer.toString(id));
            }

            prevIndex = move1ComboModel.getIndexOf(Integer.toString(id));
            if (prevIndex == -1) {
                move1ComboModel.addElement(Integer.toString(id));
            }

            prevIndex = move2ComboModel.getIndexOf(Integer.toString(id));
            if (prevIndex == -1) {
                move2ComboModel.addElement(Integer.toString(id));
            }

            prevIndex = viewMoteComboModel.getIndexOf(Integer.toString(id));
            if (prevIndex == -1) {
                viewMoteComboModel.addElement(Integer.toString(id));
            }
            
            prevIndex = angleComboModel.getIndexOf(Integer.toString(id));
            if (prevIndex == -1) {
                angleComboModel.addElement(Integer.toString(id));
            }
        }
    }

    private void updateAlarmStatus() {
        if ((xm.getLightMote1() == -1) || (xm.getLightMote2() == -1)) {
            gui.lightAlarmIndicator.setBackground(Color.orange);
        } else {
            if (xm.getLightAlarm()) {
                gui.lightAlarmIndicator.setBackground(Color.red);
            } else {
                gui.lightAlarmIndicator.setBackground(Color.green);
            }
        }

        if ((xm.getMoveMote1() == -1) || (xm.getMoveMote2() == -1)) {
            gui.moveAlarmIndicator.setBackground(Color.orange);
        } else {
            if (xm.getMoveAlarm()) {
                gui.moveAlarmIndicator.setBackground(Color.red);
            } else {
                gui.moveAlarmIndicator.setBackground(Color.green);
            }
        }
    }

    private void updateMoteView() {
        gui.textArea.removeAll();

        Integer index = gui.viewMoteCombo.getSelectedIndex();
        if (index != 0) {
            String value = viewMoteComboModel.getElementAt(index);
            XData data = xm.get(Integer.valueOf(value));

            gui.textArea.setText(data.toString());
        } else {
            gui.textArea.setText("NO DATA");
        }
    }
    
    private void updateAngleView() {
        Integer index = gui.angleCombo.getSelectedIndex();
        if (index != 0) {
            String value = angleComboModel.getElementAt(index);
            XData data = xm.get(Integer.valueOf(value));
            
            Graphics2D g2 = (Graphics2D)gui.angleIndicator.getGraphics();
            
            Double xAccel = data.getAccelX();
            Double yAccel = data.getAccelY();
            //Double alpha = calculateAlpha( xAccel, yAccel );
            //Double talpha = transformAlpha( xAccel, yAccel, alpha );
            
            Double alpha = Math.asin(data.getAccelY()/Math.sqrt(data.getAccelY()*data.getAccelY() + data.getAccelX()*data.getAccelX()));
            alpha = Math.toDegrees(alpha);
            
            if( (yAccel > 0) && (xAccel > 0 ) )
                alpha += 90.0;
//            if( (yAccel < 0) && (xAccel > 0 ) )
//                alpha -= 90.0;
            
            System.out.println("--- "+data.getAccelX()+" "+data.getAccelY()+" "+xAccel+" "+yAccel+" "+alpha);
            
            gui.angleLabel.setText( String.format( "%3.2f°" , alpha ) );
            
            Double w = gui.angleIndicator.getSize().getWidth();
            Double h = gui.angleIndicator.getSize().getHeight();
            Double xCenter = w / 2.0;
            Double yCenter = h / 2.0;
            Double size;
            Double xOff, yOff;
            if( w > h )
            {
                size = h - 1;
                yOff = 0.0;
                xOff = (w - size) / 2.0;
            }
            else
            {
                size = w - 1;
                xOff = 0.0;
                yOff = (h - size) / 2.0;
            }

            // Clear previous draw.
            g2.clearRect( 0, 0, w.intValue(), h.intValue() );
            
            // Draw circle.
            g2.draw(new Ellipse2D.Double( xOff, yOff, size, size ) );
            
            Double x = Math.cos( Math.toRadians(alpha) ) * size / 2.0;
            Double y = Math.sin( Math.toRadians(alpha) ) * size / 2.0;
            
            System.out.println("ALPHA: "+x+" "+y+" "+alpha);
            
            // Draw line.
            g2.draw(new Line2D.Double( xCenter, yCenter, x + xCenter, y + yCenter ) );
        }
    }
    
    private Double calculateAlpha( Double xAccel, Double yAccel )
    {
//        Double degs = 0.0;
//        
//        // Calculate arc-tangent
//        if( xAccel != 0 )
//        {
//            Double rads = Math.atan( yAccel / xAccel );
//            degs = Math.toDegrees(rads);
//            
//            if( xAccel < 0 )
//            {
//                if( yAccel <  0 ) degs +=   0.0;
//                if( yAccel >  0 ) degs  = 360.0 - degs;
//                if( yAccel == 0 ) degs  =   0.0;
//            }
//            
//            if( xAccel > 0 )
//            {
//                if( yAccel <  0 ) degs = 360.0 - degs - 180.0;
//                if( yAccel >  0 ) degs += 180.0;
//                if( yAccel == 0 ) degs  = 180.0;
//            }
//        }
//        else
//        {
//            if( yAccel > 0 ) degs =  90.0;
//            if( yAccel < 0 ) degs = 270.0;
//        }
//        
//        return degs;
        
        Double rads = 0.0;
        
        if( yAccel > 0 )
        {
            if( xAccel > 0 )
                rads = Math.atan( yAccel / xAccel );
            else if( xAccel < 0 )
                rads = Math.atan( xAccel / yAccel );
            else
                // xAccel == 0
                rads = Math.PI / 2.0;
        }
        else if( yAccel < 0 )
        {
            if( xAccel < 0 )
                rads = Math.atan( yAccel / xAccel );
            else if( xAccel > 0 )
                rads = Math.atan( xAccel / yAccel );
            else
                // xAccel == 0
                rads = 3.0 * Math.PI / 4.0;
        }
        else
        {
            // yAccel == 0
            if( xAccel >= 0 )
                rads = 0.0;
            else
                // xAccel < 0
                rads = Math.PI;
        }

        return Math.toDegrees(rads);
    }
    
    private Double transformAlpha( Double xAccel, Double yAccel, Double alpha )
    {
        Double degs = alpha;
        
        if( xAccel != 0 )
        {
            if( xAccel < 0 )
            {
                if( yAccel <  0 ) degs +=   0.0;
                if( yAccel >  0 ) degs  = 360.0 - degs;
                if( yAccel == 0 ) degs  =   0.0;
            }
            
            if( xAccel > 0 )
            {
                if( yAccel <  0 ) degs = 360.0 - degs - 180.0;
                if( yAccel >  0 ) degs += 180.0;
                if( yAccel == 0 ) degs  = 180.0;
            }
        }
        
        return degs;
    }
}
