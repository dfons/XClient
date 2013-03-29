/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Diego A. Fons
 */
public class SAXHandler extends DefaultHandler {

    private boolean isNameElement = false;
    private boolean isConvertedValueElement = false;
    private boolean isParsedDataElement = false;
    private String name = "";
    private String value = "";
    private XData xData = null;

    @Override
    public void startElement(String namespaceURI, String localName,
            String qualifiedName, Attributes atts) throws SAXException {

        if ("".equals(namespaceURI) && qualifiedName.equals("MotePacket")) {
            xData = new XData();
        }

        if ("".equals(namespaceURI) && qualifiedName.equals("ParsedDataElement")) {
            isParsedDataElement = true;

            name = "";
            value = "";
        }

        if ("".equals(namespaceURI) && qualifiedName.equals("Name")) {
            isNameElement = true;
        }

        if ("".equals(namespaceURI) && qualifiedName.equals("ConvertedValue")) {
            isConvertedValueElement = true;
        }

    }

    @Override
    public void endElement(String namespaceURI, String localName,
            String qualifiedName) throws SAXException {

        if ("".equals(namespaceURI) && qualifiedName.equals("MotePacket")) {
            // Set XData to XModel.
            if (xData.getType().equals(11)) {
                //System.out.println(xData);
                XModel.getInstance().put(xData);
            }
        }

        if ("".equals(namespaceURI) && qualifiedName.equals("ParsedDataElement")) {
            isParsedDataElement = false;

            switch (name) {
                case "nodeid":
                    xData.setNodeID(Integer.valueOf(value));
                    break;
                case "parent":
                    xData.setParent(Integer.valueOf(value));
                    break;
                case "group":
                    xData.setGroupID(Integer.valueOf(value));
                    break;
                case "voltage":
                    xData.setVoltage(Integer.valueOf(value));
                    break;
                case "humid":
                    xData.setHumHumidity(Integer.valueOf(value));
                    break;
                case "humtemp":
                    xData.setHumTemperature(Integer.valueOf(value));
                    break;
                case "prtemp":
                    xData.setTemperature(Double.valueOf(value));
                    break;
                case "press":
                    xData.setPressure(Double.valueOf(value));
                    break;
                case "accel_x":
                    xData.setAccelX(Double.valueOf(value) / 1000.0);
                    break;
                case "accel_y":
                    xData.setAccelY(Double.valueOf(value) / 1000.0);
                    break;
                case "taoch0":
                    if (value.equals("NaN")) {
                        value = "0.0";
                    }
                    xData.setLight(Double.valueOf(value));
                    break;
                case "amtype":
                    xData.setType(Integer.valueOf(value));
                    break;
                default:
                    //throw new AssertionError();
                    break;
            }
        }

        if ("".equals(namespaceURI) && qualifiedName.equals("Name")) {
            isNameElement = false;
        }

        if ("".equals(namespaceURI) && qualifiedName.equals("ConvertedValue")) {
            isConvertedValueElement = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        for (int i = start; i < start + length; i++) {
            if (isParsedDataElement && isNameElement) {
                name += ch[i];
            }
            if (isParsedDataElement && isConvertedValueElement) {
                value += ch[i];
            }
        }
    }
}
