/*
 * Created on 3 mars 2005
 */
package graed.export;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import com.sun.image.codec.jpeg.ImageFormatException;

import sun.awt.image.codec.JPEGImageEncoderImpl;

/**
 * @author Helder DE SOUSA
 */
public class Exporter {
    /**
     * Exporte un component vers une image au format Jpeg
     * @param c Le component à exporter
     * @param filePath Le chemin de l'image où exporter
     * @throws ImageFormatException
     * @throws IOException
     */
    public static void exportToJpeg( Component c, String filePath ) throws ImageFormatException, IOException {
        FileOutputStream out = new FileOutputStream(filePath);
        JPEGImageEncoderImpl j = new JPEGImageEncoderImpl(out);
        /* Création d'une image de la taille du component */
        BufferedImage img = new BufferedImage(c.getWidth(),c.getHeight(),BufferedImage.TYPE_INT_RGB);
        /* Ecriture du component sur le graphics de l'image */
        Graphics g = img.getGraphics();
        c.printAll(g);
        /* Encodage de l'image */
        j.encode(img);
        out.close();
    }
    
    public static void exportToPrinter( JFrame jf, Component c ) {
        Properties props = new Properties();
    	
    	props.setProperty("awt.print.paperSize", "A4");
    	props.setProperty("awt.print.destination", "printer");
    	    	
    	PrintJob job = jf.getToolkit().getPrintJob
    	    (jf, "Impression", props);
    	if (job != null) {
    	    Graphics g = job.getGraphics();
    	    
    	    jf.printAll(g);
    	    g.dispose();
    	    job.end();
    	}
    }
    
    public static void main( String[] args ) throws ImageFormatException, IOException {
        javax.swing.JFrame f = new javax.swing.JFrame();
        javax.swing.JButton test = new javax.swing.JButton("Test export JPEG");
        f.getContentPane().add( test);
        f.pack();
        f.setVisible(true);
        /*javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        int state = jfc.showSaveDialog(null);
        f.dispose();
        Exporter.exportToJpeg( f.getContentPane(), jfc.getSelectedFile().getCanonicalPath() );*/
        Exporter.exportToPrinter(f,test);
    }
}
