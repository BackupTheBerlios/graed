/*
 * @(#)MetalButtonUI.java	1.37 04/04/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
 
package com.hokage.swing.plaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

import com.sun.java.swing.SwingUtilities2;

/**
 * MetalButtonUI implementation
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans<sup><font size="-2">TM</font></sup>
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @version 1.37 04/02/04
 * @author Tom Santos
 */
public class HokageButtonUI extends BasicButtonUI {

    private final static HokageButtonUI hokageButtonUI = new HokageButtonUI(); 

    // NOTE: These are not really needed, but at this point we can't pull
    // them. Their values are updated purely for historical reasons.
    protected Color focusColor;
    protected Color selectColor;
    protected Color disabledTextColor;
 
    // ********************************
    //          Create PLAF
    // ********************************
    public static ComponentUI createUI(JComponent c) {
        
        return hokageButtonUI;
    }
 
    // ********************************
    //          Install
    // ********************************
    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        //b.setBorder(HokageBorders.getButtonBorder());
    }

    public void uninstallDefaults(AbstractButton b) {
	super.uninstallDefaults(b);
    }

    // ********************************
    //         Create Listeners
    // ********************************
    protected BasicButtonListener createButtonListener(AbstractButton b) {
        return super.createButtonListener(b);
    }

    
    // ********************************
    //         Default Accessors 
    // ********************************
    protected Color getSelectColor() {
        selectColor = UIManager.getColor(getPropertyPrefix() + "select");
	return selectColor;
    }

    protected Color getDisabledTextColor() {
        disabledTextColor = UIManager.getColor(getPropertyPrefix() +
                                               "disabledText");
	return disabledTextColor;
    }

    protected Color getFocusColor() {
        focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
	return focusColor;
    }

    // ********************************
    //          Paint
    // ********************************
    /**
     * If necessary paints the background of the component, then
     * invokes <code>paint</code>.
     *
     * @param g Graphics to paint to
     * @param c JComponent painting on
     * @throws NullPointerException if <code>g</code> or <code>c</code> is
     *         null
     * @see javax.swing.plaf.ComponentUI#update
     * @see javax.swing.plaf.ComponentUI#paint
     * @since 1.5
     */
    public void update(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton)c;
        if ((c.getBackground() instanceof UIResource) &&
                  button.isContentAreaFilled() && c.isEnabled()) {
            ButtonModel model = button.getModel();
            if (!HokageUtils.isToolBarButton(c)) {
                if (!model.isArmed() && !model.isPressed() &&
                        HokageUtils.drawGradient(
                        c, g, "Button.gradient", 0, 0, c.getWidth(),
                        c.getHeight(), true)) {
                    paint(g, c);
                    return;
                }
            }
            else if (model.isRollover() && HokageUtils.drawGradient(
                        c, g, "Button.gradient", 0, 0, c.getWidth(),
                        c.getHeight(), true)) {
                paint(g, c);
                return;
            }
        }
        super.update(g, c);
    }

    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        if ( b.isContentAreaFilled() ) {
            Dimension size = b.getSize();
	    g.setColor(getSelectColor());
	    g.fillRect(0, 0, size.width, size.height);
	}
    }

    protected void paintIcon(Graphics g, JComponent b, Rectangle rect) {
        ButtonModel bm = ((AbstractButton)b).getModel();
        if( bm.isPressed() ) {
            Rectangle rectB = new Rectangle(rect);
            rectB.x+=2;
            rectB.y+=2;
            super.paintIcon(g, b, rectB);
        } else 
            super.paintIcon(g, b, rect);
    }
    
    protected void paintFocus(Graphics g, AbstractButton b,
			      Rectangle viewRect, Rectangle textRect, Rectangle iconRect){

        Rectangle focusRect = new Rectangle();
	String text = b.getText();
	boolean isIcon = b.getIcon() != null;

        // If there is text
        if ( text != null && !text.equals( "" ) ) {
  	    if ( !isIcon ) {
	        focusRect.setBounds( textRect );
	    }
	    else {
	        focusRect.setBounds( iconRect.union( textRect ) );
	    }
        }
        // If there is an icon and no text
        else if ( isIcon ) {
  	    focusRect.setBounds( iconRect );
        }

        g.setColor(getFocusColor());
	
        ButtonModel bm = b.getModel();
        
        if( !bm.isPressed() ) {
            g.drawRect((focusRect.x-1), (focusRect.y-1),
          focusRect.width+1, focusRect.height+1);
        }
        else {
            g.drawRect((focusRect.x+1), (focusRect.y+1),
                    focusRect.width+1, focusRect.height+1);
        }
            
            /*g.drawLine(focusRect.x-1,focusRect.y-1,focusRect.x-4,focusRect.y-4);
        g.drawLine(focusRect.x+focusRect.width,
                focusRect.y+focusRect.height,
                focusRect.x+focusRect.width+3,
                focusRect.y+focusRect.height+3);
        g.drawLine(focusRect.x-1,
                focusRect.y+focusRect.height,
                focusRect.x-4,
                focusRect.y+focusRect.height+3);
        g.drawLine(focusRect.x+focusRect.width,
                focusRect.y-1,
                focusRect.x+focusRect.width+3,
                focusRect.y-4);*/
    }
    
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
	AbstractButton b = (AbstractButton) c;			     
	ButtonModel model = b.getModel();
	FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
        int mnemIndex = b.getDisplayedMnemonicIndex();

        
        
	/* Draw the Text */
	if(model.isEnabled()) {
	    /*** paint the text normally */
	    g.setColor(b.getForeground());
        if( model.isPressed() ) {
            g.setColor(getFocusColor());
        }
	}
	else {
	    /*** paint the text disabled ***/
	    g.setColor(getDisabledTextColor());
        }
        
        if( !model.isPressed() ) 
            SwingUtilities2.drawStringUnderlineCharAt(c, g,text,mnemIndex,
                                  textRect.x, textRect.y + fm.getAscent());
        else 
            SwingUtilities2.drawStringUnderlineCharAt(c, g,text,mnemIndex,
                    textRect.x+2, textRect.y+2 + fm.getAscent());
    }
}
