/*
 * Created on 3 mars 2005
 */
package graed.export;

import graed.exception.ExportException;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.JobAttributes;
import java.awt.PageAttributes;
import java.awt.PrintJob;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public static void exportToJpeg( Component c, String filePath ) throws ExportException {
        try {
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
        } catch( Exception e ) {
            throw (ExportException)new ExportException("Impossible de sauvegarder au format jpeg").initCause(e);
        }
    }
    
    /**
     * Imprime le contenu d'un Component.
     * @param jf La JFrame contenant le Component
     * @param c Le Component à imprimer
     */
    public static void exportToPrinter( JFrame jf, Component c ) throws ExportException {
        /*
         * Impression en couleur sur la zone imprimable de la page avec une orientation du papier en paysage
         */
        PageAttributes pa = new PageAttributes();
    	pa.setColor(PageAttributes.ColorType.COLOR);
    	pa.setOrigin(PageAttributes.OriginType.PRINTABLE);
    	pa.setOrientationRequested(PageAttributes.OrientationRequestedType.LANDSCAPE);
    	
    	PrintJob job = jf.getToolkit().getPrintJob
    	    (jf, "Impression d'un emploi du temps",
    	            new JobAttributes(), // attributs par défaut
    	            pa);
    	
    	if (job != null) {
    	    Graphics g = job.getGraphics();
    	    c.printAll(g);
    	    g.dispose();
    	    job.end();
    	} else
    	    throw new ExportException("Impossible de lancer l'impression");
    }
    
    /**
     * Imprime le contenu d'une JFrame.
     * @param jf La JFrame à imprimer.
     * @throws ExportException
     */
    public static void exportToPrinter( JFrame jf ) throws ExportException {
        exportToPrinter(jf,jf.getContentPane());
    }
    
    public static void main( String[] args ) throws ExportException {
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
