/*
 * Created on 9 mars 2005
 */
package graed.util;

import java.awt.Cursor;

import javax.swing.AbstractButton;

/**
 * @author Helder DE SOUSA
 */
public class Graphic {
    static public void makeTransparent( AbstractButton b ) {
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
