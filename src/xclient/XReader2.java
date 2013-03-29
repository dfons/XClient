/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xclient;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
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
public class XReader2 extends Thread {

    private Boolean done = Boolean.FALSE;
    private Socket socket = null;
    private DataInputStream in = null;
    private XMLReader parser = null;

    public XReader2(String host, int port) {
        // Connect to XServe.
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create XML parser.
        try {
            parser = XMLReaderFactory.createXMLReader(
                    "org.apache.xerces.parsers.SAXParser");
        } catch (SAXException ex) {
            Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
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
        int size;

        System.out.println("[XR] Waiting for XML stream...");

        while (!done) {
            try {
                // Read data from XServe.
                size = in.readInt();
                size = Integer.reverseBytes(size);
                if ((size < 0) || (size > 10 * 1024)) {
                    System.out.println("[XR] ERROR while reading header");
                    try {
                        sleep(500);
                        continue;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                byte[] data = new byte[size];

                System.out.println("[XR] Header: " + size + " bytes");

                int nbytes = 0;
                int off = 0;
                int nsize = size;
                int retries = 5;
                while ((nbytes != size) && (retries > 0)) {
                    System.out.println("[XR] Trying to read " + nsize + " bytes");
                    nbytes = in.read(data, off, nsize);
                    if (nbytes > 0) {
                        nsize -= nbytes;
                        off += nbytes;
                    } else {
                        retries--;
                    }
                }
                if (retries <= 0) {
                    System.out.println("[XR] ERROR while reading data");
                    try {
                        sleep(500);
                        continue;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                System.out.println("[XR] Data: " + size + " bytes");

                try {
                    System.out.println("[XR] Parsing data");
                    parser.parse(new InputSource(new ByteArrayInputStream(data, 0, size)));
                } catch (SAXException ex) {
                    Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(XReader2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
