/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Diego A. Fons
 */
public class XReader extends Thread {

    private Boolean done = Boolean.FALSE;
    private Socket socket = null;
    private BufferedReader in = null;
    private XMLReader parser = null;

    public XReader(String host, int port) {
        // Connect to XServe.
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(XReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create XML parser.
        try {
            parser = XMLReaderFactory.createXMLReader(
                    "org.apache.xerces.parsers.SAXParser");
        } catch (SAXException ex) {
            Logger.getLogger(XReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        org.xml.sax.ContentHandler handler = new SAXHandler();
        parser.setContentHandler(handler);
        
        System.out.println("Ready for XML!");
    }

    public void stopThread() {
        done = Boolean.TRUE;
        interrupt();
    }

    @Override
    public void run() {
        char[] cSize = new char[4];
        char[] cData = new char[4096];
        int size;
        
        System.out.println("Waiting...");

        while (!done) {
            try {
                // Read data from XServe.
                size = in.read(cSize, 0, 4);
                size = in.read(cData, 0, 4096);
            } catch (IOException e) {
                Logger.getLogger(XReader.class.getName()).log(Level.SEVERE, null, e);
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(XReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }

            // Convert data to String.
            String ss = String.valueOf(cData, 0, size);

            try {
                // Parse XML data from XServe.
                parser.parse(new InputSource(new ByteArrayInputStream(ss.getBytes("utf-8"))));
            } catch (IOException | SAXException ex) {
                Logger.getLogger(XReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
