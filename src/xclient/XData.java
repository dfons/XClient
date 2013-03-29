/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

/**
 *
 * @author Diego A. Fons
 */
public class XData {
    private Integer type            = -1;
    private Integer nodeID          = -1;
    private Integer parent          = -1;
    private Integer groupID         = -1;
    private Integer voltage         = -1;
    private Integer humHumidity     = -1;
    private Integer humTemperature  = -1;
    private Double  temperature     = -1.0;
    private Double  pressure        = -1.0;
    private Double  accelX          = -1.0;
    private Double  accelY          = -1.0;
    private Double  light           = -1.0;

    @Override
    public String toString() {
        String ret;
        
        ret = "NodeID: " + getNodeID() + 
              ", GroupID: " + getGroupID() + 
              ", Parent: " + getParent() + "\n";
        ret += "Voltage: " + getVoltage() + "\n";
        ret += "Humidity.hum: " + getHumHumidity() +
               ", Humidity.temp: " + getHumTemperature() + "\n";
        ret += "Temperature: " + getTemperature() + "\n";
        ret += "Pressure: " + getPressure() + "\n";
        ret += "Light: " + getLight() + "\n";
        ret += "AccelX: " +getAccelX() +
               ", AccelY: " + getAccelY() + "\n";
        
        return ret;
    }
    
    /**
     * @return the nodeID
     */
    public Integer getNodeID() {
        return nodeID;
    }

    /**
     * @param nodeID the nodeID to set
     */
    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    /**
     * @return the parent
     */
    public Integer getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    /**
     * @return the groupID
     */
    public Integer getGroupID() {
        return groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    /**
     * @return the voltage
     */
    public Integer getVoltage() {
        return voltage;
    }

    /**
     * @param voltage the voltage to set
     */
    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    /**
     * @return the humHumidity
     */
    public Integer getHumHumidity() {
        return humHumidity;
    }

    /**
     * @param humHumidity the humHumidity to set
     */
    public void setHumHumidity(Integer humHumidity) {
        this.humHumidity = humHumidity;
    }

    /**
     * @return the humTemperature
     */
    public Integer getHumTemperature() {
        return humTemperature;
    }

    /**
     * @param humTemperature the humTemperature to set
     */
    public void setHumTemperature(Integer humTemperature) {
        this.humTemperature = humTemperature;
    }

    /**
     * @return the temperature
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     * @return the accelX
     */
    public Double getAccelX() {
        return accelX;
    }

    /**
     * @param accelX the accelX to set
     */
    public void setAccelX(Double accelX) {
        this.accelX = accelX;
    }

    /**
     * @return the accelY
     */
    public Double getAccelY() {
        return accelY;
    }

    /**
     * @param accelY the accelY to set
     */
    public void setAccelY(Double accelY) {
        this.accelY = accelY;
    }

    /**
     * @return the light
     */
    public Double getLight() {
        return light;
    }

    /**
     * @param light the light to set
     */
    public void setLight(Double light) {
        this.light = light;
    }
    
    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }
}
