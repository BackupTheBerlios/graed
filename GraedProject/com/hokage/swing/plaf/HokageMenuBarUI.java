/*
 * @(#)MetalMenuBarUI.java	1.5 03/12/19
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hokage.swing.plaf;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.*;

/**
 * Metal implementation of <code>MenuBarUI</code>. This class is responsible
 * for providing the metal look and feel for <code>JMenuBar</code>s.
 *
 * @version 1.5 12/19/03
 * @see javax.swing.plaf.MenuBarUI
 * @since 1.5
 */
public class HokageMenuBarUI extends BasicMenuBarUI  {
    /**
     * Creates the <code>ComponentUI</code> implementation for the passed
     * in component.
     *
     * @param x JComponent to create the ComponentUI implementation for
     * @return ComponentUI implementation for <code>x</code>
     * @throws NullPointerException if <code>x</code> is null
     */
    public static ComponentUI createUI(JComponent x) {
        if (x == null) {
            throw new NullPointerException("Must pass in a non-null component");
        }
	return new HokageMenuBarUI();
    }

    /**
     * Configures the specified component appropriate for the metal look and
     * feel.
     *
     * @param c the component where this UI delegate is being installed
     * @throws NullPointerException if <code>c</code> is null.
     */
    public void installUI(JComponent c) {
        super.installUI(c);
        HokageToolBarUI.register(c);
    }

    /**
     * Reverses configuration which was done on the specified component during
     * <code>installUI</code>.
     *
     * @param c the component where this UI delegate is being installed
     * @throws NullPointerException if <code>c</code> is null.
     */
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        HokageToolBarUI.unregister(c);
    }

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
        boolean isOpaque = c.isOpaque();
        if (isOpaque && (c.getBackground() instanceof UIResource) &&
                        UIManager.get("MenuBar.gradient") != null) {
            if (HokageToolBarUI.doesMenuBarBorderToolBar((JMenuBar)c)) {
                JToolBar tb = (JToolBar)HokageToolBarUI.
                     findRegisteredComponentOfType(c, JToolBar.class);
                if (tb.isOpaque() &&tb.getBackground() instanceof UIResource) {
                    HokageUtils.drawGradient(c, g, "MenuBar.gradient", 0, 0,
                                            c.getWidth(), c.getHeight() +
                                            tb.getHeight(), true);
                    paint(g, c);
                    return;
                }
            }
            HokageUtils.drawGradient(c, g, "MenuBar.gradient", 0, 0,
                                    c.getWidth(), c.getHeight(),true);
            paint(g, c);
        }
        else {
            super.update(g, c);
        }
    }
}
