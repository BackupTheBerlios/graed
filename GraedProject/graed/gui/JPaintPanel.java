/*
 * Created on 24 mars 2005
 */
package graed.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class JPaintPanel extends JPanel {

    /**
     * Version number
     */
    private static final long serialVersionUID = 1L;
    
    private Component moving = null;

    public JPaintPanel() {
        super();
        setMouseListener();
        setMouseMotionListener();
    }

    public JPaintPanel(boolean b) {
        super(b);
        setMouseListener();
        setMouseMotionListener();
    }

    public JPaintPanel(LayoutManager lay, boolean b) {
        super(lay, b);
        setMouseListener();
        setMouseMotionListener();
    }

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
                    setComponentZOrder(moving,0);
                    //validate();
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
                if( moving != null ) {
                	if( e.getX()<=(getWidth()-moving.getWidth())
                        && e.getY()<=(getHeight()-moving.getHeight())
                        && e.getX()>=0
                        && e.getY()>=0)
                			moving.setLocation(e.getX(),e.getY());
                }
            }

            public void mouseMoved(MouseEvent e) {}
            
        });
    }
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        Container c = f.getContentPane();
        JPanel p = new JPanel();
        JPaintPanel pp = new JPaintPanel();
        JPanel ppp = new JPanel();
        pp.setPreferredSize(new Dimension(300,400));
        pp.setOpaque(true);
        pp.setBackground(Color.YELLOW);
        p.setLayout(new BorderLayout());
        JLabel jl = new JLabel("TEATEWFD");
        jl.setOpaque(true);
        jl.setBackground(Color.GREEN);
        jl.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JLabel jl2 = new JLabel("TEAdsfsdfFD");
        jl2.setOpaque(true);
        jl2.setBackground(Color.RED);
        jl2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JButton b = new JButton("OK");
        JButton bb = new JButton("Cancel");
        pp.add( jl );
        pp.add( jl2 );
        ppp.add(b);
        ppp.add(bb);
        p.add( pp, BorderLayout.CENTER);
        p.add( ppp,BorderLayout.SOUTH );
        c.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }




    

}
