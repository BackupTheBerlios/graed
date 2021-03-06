package com.hokage.swing.plaf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import java.io.Serializable;
import java.beans.*;


/**
 * Metal UI for JComboBox
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
 * @see MetalComboBoxEditor
 * @see MetalComboBoxButton
 * @version 1.49 05/18/04
 * @author Tom Santos
 */
public class HokageComboBoxUI extends BasicComboBoxUI {

    public static ComponentUI createUI(JComponent c) {
        return new HokageComboBoxUI();
    }

    public void paint(Graphics g, JComponent c) {
        if (HokageLookAndFeel.usingOcean()) {
            super.paint(g, c);
        }
    }

    /**
     * If necessary paints the currently selected item.
     *
     * @param g Graphics to paint to
     * @param bounds Region to paint current value to
     * @param hasFocus whether or not the JComboBox has focus
     * @throws NullPointerException if any of the arguments are null.
     * @since 1.5
     */
    public void paintCurrentValue(Graphics g, Rectangle bounds,
                                  boolean hasFocus) {
        // This is really only called if we're using ocean.
        if (HokageLookAndFeel.usingOcean()) {
            bounds.x += 2;
            bounds.y += 2;
            bounds.width -= 3;
            bounds.height -= 4;
            super.paintCurrentValue(g, bounds, hasFocus);
        }
        else if (g == null || bounds == null) {
            throw new NullPointerException(
                "Must supply a non-null Graphics and Rectangle");
        }
    }

    /**
     * If necessary paints the background of the currently selected item.
     *
     * @param g Graphics to paint to
     * @param bounds Region to paint background to
     * @param hasFocus whether or not the JComboBox has focus
     * @throws NullPointerException if any of the arguments are null.
     * @since 1.5
     */
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds,
                                            boolean hasFocus) {
        // This is really only called if we're using ocean.
        if (HokageLookAndFeel.usingOcean()) {
            g.setColor(HokageLookAndFeel.getControlDarkShadow());
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height - 1);
            g.setColor(HokageLookAndFeel.getControlShadow());
            g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 2,
                       bounds.height - 3);
        }
        else if (g == null || bounds == null) {
            throw new NullPointerException(
                "Must supply a non-null Graphics and Rectangle");
        }
    }

    protected ComboBoxEditor createEditor() {
        return new HokageComboBoxEditor.UIResource();
    }

    protected ComboPopup createPopup() {
        return super.createPopup();
    }

    protected JButton createArrowButton() {
        boolean iconOnly = (comboBox.isEditable() ||
                            HokageLookAndFeel.usingOcean());
        JButton button = new HokageComboBoxButton( comboBox,
                                                  new HokageComboBoxIcon(),
                                                  iconOnly,
                                                  currentValuePane,
                                                  listBox );
        button.setMargin( new Insets( 0, 1, 1, 3 ) );
        if (HokageLookAndFeel.usingOcean()) {
            // Disabled rollover effect.
            button.putClientProperty(HokageBorders.NO_BUTTON_ROLLOVER,
                                     Boolean.TRUE);
        }
        updateButtonForOcean(button);
        return button;
    }

    /**
     * Resets the necessary state on the ComboBoxButton for ocean.
     */
    private void updateButtonForOcean(JButton button) {
        if (HokageLookAndFeel.usingOcean()) {
            // Ocean renders the focus in a different way, this
            // would be redundant.
            button.setFocusPainted(comboBox.isEditable());
        }
    }

    public PropertyChangeListener createPropertyChangeListener() {
        return new HokagePropertyChangeListener();
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     */          
    public class HokagePropertyChangeListener extends BasicComboBoxUI.PropertyChangeHandler {
        public void propertyChange(PropertyChangeEvent e) {
            super.propertyChange( e );
            String propertyName = e.getPropertyName();

            if ( propertyName == "editable" ) {
		HokageComboBoxButton button = (HokageComboBoxButton)arrowButton;
		button.setIconOnly( comboBox.isEditable() ||
                                    HokageLookAndFeel.usingOcean() );
		comboBox.repaint();
                updateButtonForOcean(button);
            } else if ( propertyName == "background" ) {
                Color color = (Color)e.getNewValue();
                arrowButton.setBackground(color);
                listBox.setBackground(color);
                
            } else if ( propertyName == "foreground" ) {
                Color color = (Color)e.getNewValue();
                arrowButton.setForeground(color);
                listBox.setForeground(color);
            }
        }
    }

    /**
     * As of Java 2 platform v1.4 this method is no longer used. Do not call or
     * override. All the functionality of this method is in the
     * HokagePropertyChangeListener.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    protected void editablePropertyChanged( PropertyChangeEvent e ) { }

    protected LayoutManager createLayoutManager() {
        return new HokageComboBoxLayoutManager();
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     */          
    public class HokageComboBoxLayoutManager extends BasicComboBoxUI.ComboBoxLayoutManager {
        public void layoutContainer( Container parent ) {
            layoutComboBox( parent, this );
        }
        public void superLayout( Container parent ) {
            super.layoutContainer( parent );
        }
    }

    // This is here because of a bug in the compiler.  
    // When a protected-inner-class-savvy compiler comes out we
    // should move this into HokageComboBoxLayoutManager.
    public void layoutComboBox( Container parent, HokageComboBoxLayoutManager manager ) {
        if (comboBox.isEditable() && !HokageLookAndFeel.usingOcean()) {
            manager.superLayout( parent );
            return;
        }

        if (arrowButton != null) {
            if (HokageLookAndFeel.usingOcean() &&
                                (arrowButton instanceof HokageComboBoxButton)) {
                Icon icon = ((HokageComboBoxButton)arrowButton).getComboIcon();
                Insets buttonInsets = arrowButton.getInsets();
                Insets insets = comboBox.getInsets();
                int buttonWidth = icon.getIconWidth() + buttonInsets.left +
                                  buttonInsets.right;
		arrowButton.setBounds(HokageUtils.isLeftToRight(comboBox)
				? (comboBox.getWidth() - insets.right - buttonWidth)
				: insets.left,
                            insets.top, buttonWidth,
                            comboBox.getHeight() - insets.top - insets.bottom);
            }
            else {
                Insets insets = comboBox.getInsets();
                int width = comboBox.getWidth();
                int height = comboBox.getHeight();
                arrowButton.setBounds( insets.left, insets.top,
                                       width - (insets.left + insets.right),
                                       height - (insets.top + insets.bottom) );
            }
        }

        if (editor != null && HokageLookAndFeel.usingOcean()) {
            Rectangle cvb = rectangleForCurrentValue();
            editor.setBounds(cvb);
        }
    }

    /**
     * As of Java 2 platform v1.4 this method is no
     * longer used.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    protected void removeListeners() {
        if ( propertyChangeListener != null ) {
            comboBox.removePropertyChangeListener( propertyChangeListener );
        }
    }

    // These two methods were overloaded and made public. This was probably a
    // mistake in the implementation. The functionality that they used to 
    // provide is no longer necessary and should be removed. However, 
    // removing them will create an uncompatible API change.

    public void configureEditor() {
	super.configureEditor();
    }

    public void unconfigureEditor() {
	super.unconfigureEditor();
    }

    public Dimension getMinimumSize( JComponent c ) {
        if ( !isMinimumSizeDirty ) {
            return new Dimension( cachedMinimumSize );
        }

        Dimension size = null;

        if ( !comboBox.isEditable() &&
             arrowButton != null &&
             arrowButton instanceof HokageComboBoxButton ) {

            HokageComboBoxButton button = (HokageComboBoxButton)arrowButton;
            Insets buttonInsets = button.getInsets();
            Insets insets = comboBox.getInsets();

            size = getDisplaySize();
            size.width += insets.left + insets.right;
            size.width += buttonInsets.left + buttonInsets.right;
            size.width += buttonInsets.right + button.getComboIcon().getIconWidth();
            size.height += insets.top + insets.bottom;
            size.height += buttonInsets.top + buttonInsets.bottom;
        }
        else if ( comboBox.isEditable() &&
                  arrowButton != null &&
                  editor != null ) {
            size = super.getMinimumSize( c );
            Insets margin = arrowButton.getMargin();
            size.height += margin.top + margin.bottom;
            size.width += margin.left + margin.right;
        }
        else {
            size = super.getMinimumSize( c );
        }

        cachedMinimumSize.setSize( size.width, size.height ); 
        isMinimumSizeDirty = false;

        return new Dimension( cachedMinimumSize );
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     *
     * This class is now obsolete and doesn't do anything and
     * is only included for backwards API compatibility. Do not call or 
     * override.
     * 
     * @deprecated As of Java 2 platform v1.4.
     */
    public class HokageComboPopup extends BasicComboPopup {

	public HokageComboPopup( JComboBox cBox) {
	    super( cBox );
	}

	// This method was overloaded and made public. This was probably
	// mistake in the implementation. The functionality that they used to 
	// provide is no longer necessary and should be removed. However, 
	// removing them will create an uncompatible API change.

	public void delegateFocus(MouseEvent e) {
	    super.delegateFocus(e);
	}
    }
}


