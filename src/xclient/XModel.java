/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 *xm
 * @author Diego A. Fons
 */
public class XModel {

    // Attributes.
    private static final XModel instance = new XModel();
    private HashMap<Integer, XData> datas = new HashMap();
    private Boolean modified = Boolean.TRUE;
    private Integer lightThreshold = 0;
    private Integer lightMote1 = -1;
    private Integer lightMote2 = -1;
    private Integer moveThreshold = 0;
    private Integer moveMote1 = -1;
    private Integer moveMote2 = -1;
    private Boolean lightAlarm = Boolean.FALSE;
    private Boolean moveAlarm = Boolean.FALSE;
    private Integer angleMote = -1;

    // Private methods.
    private XModel() {
    }

    /**
     * @return the lightMote1
     */
    public synchronized Integer getLightMote1() {
        return lightMote1;
    }

    /**
     * @param aLightMote1 the lightMote1 to set
     */
    public synchronized void setLightMote1(Integer aLightMote1) {
        lightMote1 = aLightMote1;
    }

    /**
     * @return the lightMote2
     */
    public synchronized Integer getLightMote2() {
        return lightMote2;
    }

    /**
     * @param aLightMote2 the lightMote2 to set
     */
    public synchronized void setLightMote2(Integer aLightMote2) {
        lightMote2 = aLightMote2;
    }

    /**
     * @return the moveMote1
     */
    public synchronized Integer getMoveMote1() {
        return moveMote1;
    }

    /**
     * @param aMoveMote1 the moveMote1 to set
     */
    public synchronized void setMoveMote1(Integer aMoveMote1) {
        moveMote1 = aMoveMote1;
    }

    /**
     * @return the moveMote2
     */
    public synchronized Integer getMoveMote2() {
        return moveMote2;
    }

    /**
     * @param aMoveMote2 the moveMote2 to set
     */
    public synchronized void setMoveMote2(Integer aMoveMote2) {
        moveMote2 = aMoveMote2;
    }

    /**
     * @return the lightThreshold
     */
    public synchronized Integer getLightThreshold() {
        return lightThreshold;
    }

    /**
     * @param aLightThreshold the lightThreshold to set
     */
    public synchronized void setLightThreshold(Integer aLightThreshold) {
        modified = Boolean.TRUE;
        lightThreshold = aLightThreshold;
    }

    /**
     * @return the moveThreshold
     */
    public synchronized Integer getMoveThreshold() {
        return moveThreshold;
    }

    /**
     * @param aMoveThreshold the moveThreshold to set
     */
    public synchronized void setMoveThreshold(Integer aMoveThreshold) {
        modified = Boolean.TRUE;
        moveThreshold = aMoveThreshold;
    }

    /**
     * @return the lightAlarm
     */
    public synchronized Boolean getLightAlarm() {
        return lightAlarm;
    }

    /**
     * @param aLightAlarm the lightAlarm to set
     */
    public synchronized void setLightAlarm(Boolean aLightAlarm) {
        modified = Boolean.TRUE;
        lightAlarm = aLightAlarm;
    }

    /**
     * @return the moveAlarm
     */
    public synchronized Boolean getMoveAlarm() {
        return moveAlarm;
    }

    /**
     * @param aMoveAlarm the moveAlarm to set
     */
    public synchronized void setMoveAlarm(Boolean aMoveAlarm) {
        modified = Boolean.TRUE;
        moveAlarm = aMoveAlarm;
    }

    // Public methods.
    public static XModel getInstance() {
        return instance;
    }

    public synchronized Boolean isModified() {
        Boolean aux = modified;
        modified = Boolean.FALSE;
        return aux;
    }

    public synchronized XData put(XData elem) {
        modified = Boolean.TRUE;
        return datas.put(elem.getNodeID(), elem);
    }

    public synchronized XData get(Integer key) {
        return datas.get(key);
    }

    public synchronized Collection<XData> getValues() {
        return datas.values();
    }

    public synchronized Set<Integer> getKeys() {
        return datas.keySet();
    }

    /**
     * @return the angleMote
     */
    public Integer getAngleMote() {
        return angleMote;
    }

    /**
     * @param angleMote the angleMote to set
     */
    public void setAngleMote(Integer angleMote) {
        this.angleMote = angleMote;
    }
}
