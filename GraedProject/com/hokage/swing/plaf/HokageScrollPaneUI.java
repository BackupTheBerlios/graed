/*
 * @(#)MetalScrollPaneUI.java	1.19 03/12/19
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hokage.swing.plaf;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.awt.*;
import java.beans.*;
import java.awt.event.*;


/**
 * A Metal L&F implementation of ScrollPaneUI.
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
 * @version 1.19 12/19/03
 * @author Steve Wilson
 */
public class HokageScrollPaneUI extends BasicScrollPaneUI
{

    private PropertyChangeListener scrollBarSwapListener;

    public static ComponentUI createUI(JComponent x) {
	return new HokageScrollPaneUI();
    }

    public void installUI(JComponent c) {

        super.installUI(c);

	JScrollPane sp = (JScrollPane)c;
	JScrollBar hsb = sp.getHorizontalScrollBar();
	JScrollBar vsb = sp.getVerticalScrollBar();
        updateScrollbarsFreeStanding();
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);

	JScrollPane sp = (JScrollPane)c;
	JScrollBar hsb = sp.getHorizontalScrollBar();
	JScrollBar vsb = sp.getVerticalScrollBar();
	hsb.putClientProperty( HokageScrollBarUI.FREE_STANDING_PROP, null);
	vsb.putClientProperty( HokageScrollBarUI.FREE_STANDING_PROP, null);	
    }


    public void installListeners(JScrollPane scrollPane) {
        super.installListeners(scrollPane);
	scrollBarSwapListener = createScrollBarSwapListener();
	scrollPane.addPropertyChangeListener(scrollBarSwapListener);
    }


    public void uninstallListeners(JScrollPane scrollPane) {
        super.uninstallListeners(scrollPane);

	scrollPane.removePropertyChangeListener(scrollBarSwapListener);
    }

    /**
     * If the border of the scrollpane is an instance of
     * <code>MetalBorders.ScrollPaneBorder</code>, the client property
     * <code>FREE_STANDING_PROP</code> of the scrollbars 
     * is set to false, otherwise it is set to true.
     */
    private void updateScrollbarsFreeStanding() {
        if (scrollpane == null) {
            return;
        }
        Border border = scrollpane.getBorder();
        Object value;

        if (border instanceof HokageBorders.ScrollPaneBorder) {
            value = Boolean.FALSE;
        }
        else {
            value = Boolean.TRUE;
        }
        JScrollBar sb = scrollpane.getHorizontalScrollBar();
        if (sb != null) {
            sb.putClientProperty
                   (HokageScrollBarUI.FREE_STANDING_PROP, value);
        }
        sb = scrollpane.getVerticalScrollBar();
        if (sb != null) {
            sb.putClientProperty
                   (HokageScrollBarUI.FREE_STANDING_PROP, value);
        }
    }

    protected PropertyChangeListener createScrollBarSwapListener() {
        return new PropertyChangeListener() {
	    public void propertyChange(PropertyChangeEvent e) {
		  String propertyName = e.getPropertyName();
		  if (propertyName.equals("verticalScrollBar") ||
		      propertyName.equals("horizontalScrollBar")) {
                      JScrollBar oldSB = (JScrollBar)e.getOldValue();
                      if (oldSB != null) {
                          oldSB.putClientProperty(
                              HokageScrollBarUI.FREE_STANDING_PROP, null);
                      }
                      JScrollBar newSB = (JScrollBar)e.getNewValue();
                      if (newSB != null) {
                          newSB.putClientProperty(
                              HokageScrollBarUI.FREE_STANDING_PROP,
                              Boolean.FALSE);
                      }
		  }	  
                  else if ("border".equals(propertyName)) {
                      updateScrollbarsFreeStanding();
                  }
	}};
    }

}
