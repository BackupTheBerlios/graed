/*
 * Created on 24 mars 2005
 */
package graed.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JPaintPanel extends JPanel {

    /**
     * Version number
     */
    private static final long serialVersionUID = 1L;
    
    private Component moving = null;
    /**
     * 
     */
    public JPaintPanel() {
        super();
        setMouseListener();
        setMouseMotionListener();
    }

    /**
     * @param arg0
     */
    public JPaintPanel(boolean b) {
        super(b);
        setMouseListener();
        setMouseMotionListener();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public JPaintPanel(LayoutManager lay, boolean b) {
        super(lay, b);
        setMouseListener();
        setMouseMotionListener();
    }

    /**
     * @param arg0
     */
    public JPaintPanel(LayoutManager lay) {
        super(lay);
        setMouseListener();
        setMouseMotionListener();
    }
    
    protected void setMouseListener() {
        addMouseListener( new MouseListener() {

            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                Component c = getComponentAt(p);
                if( c!=null && c!=JPaintPanel.this ) {
                    moving = c;
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
            }

            public void mouseReleased(MouseEvent e) {
                if( moving != null ) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    moving = null;
                }
            }

            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            
        });
    }
    
    
    protected void setMouseMotionListener() {
        addMouseMotionListener( new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if( moving != null
                        && e.getX()<=(getWidth()-moving.getWidth())
                        && e.getY()<=(getHeight()-moving.getHeight())
                        && e.getX()>=0
                        && e.getY()>=0)
                    moving.setLocation(e.getX(),e.getY());
            }

            public void mouseMoved(MouseEvent e) {}
            
        });
    }
    

    
    
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        Container c = f.getContentPane();
        JPaintPanel pp = new JPaintPanel();
        pp.setPreferredSize(new Dimension(300,400));
        pp.setOpaque(true);
        pp.setBackground(Color.YELLOW);
        JLabel jl = new JLabel("TEATEWFD");
        jl.setOpaque(true);
        jl.setBackground(Color.GREEN);
        JLabel jl2 = new JLabel("TEAdsfsdfFD");
        jl2.setOpaque(true);
        jl2.setBackground(Color.RED);
        pp.add( jl );
        c.add(pp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }




    

}
