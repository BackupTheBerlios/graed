/*
 * @(#)MetalSliderUI.java	1.34 03/12/19
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hokage.swing.plaf;

import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Insets;
import java.awt.Color;
import java.io.Serializable;
import java.awt.IllegalComponentStateException;
import java.awt.Polygon;
import java.beans.*;

import javax.swing.border.AbstractBorder;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

/**
 * A Java L&F implementation of SliderUI.  
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
 * @version 1.34 12/19/03
 * @author Tom Santos
 */
public class HokageSliderUI extends BasicSliderUI {

    protected final int TICK_BUFFER = 4;
    protected boolean filledSlider = false;
    protected static Color thumbColor;
    protected static Color highlightColor;
    protected static Color darkShadowColor;
    protected static int trackWidth;
    protected static int tickLength;
    protected static Icon horizThumbIcon;
    protected static Icon vertThumbIcon;


    protected final String SLIDER_FILL = "JSlider.isFilled";

    public static ComponentUI createUI(JComponent c)    {
        return new HokageSliderUI();
    }

    public HokageSliderUI() {
        super( null );
    }

    public void installUI( JComponent c ) {
        trackWidth = ((Integer)UIManager.get( "Slider.trackWidth" )).intValue();
        tickLength = ((Integer)UIManager.get( "Slider.majorTickLength" )).intValue();
        horizThumbIcon = UIManager.getIcon( "Slider.horizontalThumbIcon" );
        vertThumbIcon = UIManager.getIcon( "Slider.verticalThumbIcon" );

	super.installUI( c );

        thumbColor = UIManager.getColor("Slider.thumb");
        highlightColor = UIManager.getColor("Slider.highlight");
        darkShadowColor = UIManager.getColor("Slider.darkShadow");

        scrollListener.setScrollByBlock( false );

        Object sliderFillProp = c.getClientProperty( SLIDER_FILL );
        if ( sliderFillProp != null ) {
            filledSlider = ((Boolean)sliderFillProp).booleanValue();
        }
    }

    protected PropertyChangeListener createPropertyChangeListener( JSlider slider ) {
        return new MetalPropertyListener();
    }

    protected class MetalPropertyListener extends BasicSliderUI.PropertyChangeHandler {
        public void propertyChange( PropertyChangeEvent e ) {  // listen for slider fill
	    super.propertyChange( e );

	    String name = e.getPropertyName();
	    if ( name.equals( SLIDER_FILL ) ) {
	        if ( e.getNewValue() != null ) {
		    filledSlider = ((Boolean)e.getNewValue()).booleanValue();
		}
		else {
		    filledSlider = false;
		}
	    }
	}
    }

    public void paintThumb(Graphics g)  {
        Rectangle knobBounds = thumbRect;

        g.translate( knobBounds.x, knobBounds.y );

        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            horizThumbIcon.paintIcon( slider, g, 0, 0 );
        }
        else {
            vertThumbIcon.paintIcon( slider, g, 0, 0 );
        }

        g.translate( -knobBounds.x, -knobBounds.y );
    }

    /**
     * If <code>chooseFirst</code>is true, <code>c1</code> is returned,
     * otherwise <code>c2</code>.
     */
    private Color chooseColor(boolean chooseFirst, Color c1, Color c2) {
        if (chooseFirst) {
            return c2;
        }
        return c1;
    }

    /**
     * Returns a rectangle enclosing the track that will be painted.
     */
    private Rectangle getPaintTrackRect() {
        int trackLeft = 0, trackRight = 0, trackTop = 0, trackBottom = 0;
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            trackBottom = (trackRect.height - 1) - getThumbOverhang();
            trackTop = trackBottom - (getTrackWidth() - 1);
            trackRight = trackRect.width - 1;
        }
        else {
	    if (HokageUtils.isLeftToRight(slider)) {
	        trackLeft = (trackRect.width - getThumbOverhang()) - 
		                                         getTrackWidth();
		trackRight = (trackRect.width - getThumbOverhang()) - 1;
	    }
	    else {
	        trackLeft = getThumbOverhang();
		trackRight = getThumbOverhang() + getTrackWidth() - 1;
	    }
            trackBottom = trackRect.height - 1;
        }
        return new Rectangle(trackRect.x + trackLeft, trackRect.y + trackTop,
                             trackRight - trackLeft, trackBottom - trackTop);
    }

    public void paintTrack(Graphics g)  {
        if (HokageLookAndFeel.usingOcean()) {
            oceanPaintTrack(g);
            return;
        }
        Color trackColor = !slider.isEnabled() ? HokageLookAndFeel.getControlShadow() :
                           slider.getForeground();

	boolean leftToRight = HokageUtils.isLeftToRight(slider);

        g.translate( trackRect.x, trackRect.y );

        int trackLeft = 0;
        int trackTop = 0;
        int trackRight = 0;
        int trackBottom = 0;

        // Draw the track
	if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            trackBottom = (trackRect.height - 1) - getThumbOverhang();
            trackTop = trackBottom - (getTrackWidth() - 1);
            trackRight = trackRect.width - 1;
	}
	else {
	    if (leftToRight) {
	        trackLeft = (trackRect.width - getThumbOverhang()) - 
		                                         getTrackWidth();
		trackRight = (trackRect.width - getThumbOverhang()) - 1;
	    }
	    else {
	        trackLeft = getThumbOverhang();
		trackRight = getThumbOverhang() + getTrackWidth() - 1;
	    }
            trackBottom = trackRect.height - 1;
	}

	if ( slider.isEnabled() ) {
	    g.setColor( HokageLookAndFeel.getControlDarkShadow() );
	    g.drawRect( trackLeft, trackTop,
			(trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1 );
	    
	    g.setColor( HokageLookAndFeel.getControlHighlight() );
	    g.drawLine( trackLeft + 1, trackBottom, trackRight, trackBottom );
	    g.drawLine( trackRight, trackTop + 1, trackRight, trackBottom );

	    g.setColor( HokageLookAndFeel.getControlShadow() );
	    g.drawLine( trackLeft + 1, trackTop + 1, trackRight - 2, trackTop + 1 );
	    g.drawLine( trackLeft + 1, trackTop + 1, trackLeft + 1, trackBottom - 2 );
	}
	else {
	    g.setColor( HokageLookAndFeel.getControlShadow() );
	    g.drawRect( trackLeft, trackTop,
			(trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1 );
	}

        // Draw the fill
	if ( filledSlider ) {
	    int middleOfThumb = 0;
	    int fillTop = 0;
	    int fillLeft = 0;
	    int fillBottom = 0;
	    int fillRight = 0;

	    if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
	        middleOfThumb = thumbRect.x + (thumbRect.width / 2);
		middleOfThumb -= trackRect.x; // To compensate for the g.translate()
		fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
		fillBottom = !slider.isEnabled() ? trackBottom - 1 : trackBottom - 2;
		
		if ( !drawInverted() ) {
		    fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
		    fillRight = middleOfThumb;
		}
		else {
		    fillLeft = middleOfThumb;
		    fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;
		}
	    }
	    else {
	        middleOfThumb = thumbRect.y + (thumbRect.height / 2);
		middleOfThumb -= trackRect.y; // To compensate for the g.translate()
		fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
		fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;
		
		if ( !drawInverted() ) {
		    fillTop = middleOfThumb;
		    fillBottom = !slider.isEnabled() ? trackBottom - 1 : trackBottom - 2;
		}
		else {
		    fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
		    fillBottom = middleOfThumb;
		}
	    }
	    
	    if ( slider.isEnabled() ) {
	        g.setColor( slider.getBackground() );
		g.drawLine( fillLeft, fillTop, fillRight, fillTop );
		g.drawLine( fillLeft, fillTop, fillLeft, fillBottom );

		g.setColor( HokageLookAndFeel.getControlShadow() );
		g.fillRect( fillLeft + 1, fillTop + 1,
			    fillRight - fillLeft, fillBottom - fillTop );
	    }
	    else {
	        g.setColor( HokageLookAndFeel.getControlShadow() );
		g.fillRect( fillLeft, fillTop,
			    fillRight - fillLeft, trackBottom - trackTop );
	    }
	}

        g.translate( -trackRect.x, -trackRect.y );
    }

    private void oceanPaintTrack(Graphics g)  {
	boolean leftToRight = HokageUtils.isLeftToRight(slider);
        boolean drawInverted = drawInverted();
        Color sliderAltTrackColor = (Color)UIManager.get(
                                    "Slider.altTrackColor");

        // Translate to the origin of the painting rectangle
        Rectangle paintRect = getPaintTrackRect();
        g.translate(paintRect.x, paintRect.y);

        // Width and height of the painting rectangle.
        int w = paintRect.width;
        int h = paintRect.height;

        if (!slider.isEnabled()) {
            g.setColor(HokageLookAndFeel.getControlShadow());
            g.drawRect(0, 0, w - 1, h - 1);
        }
	else if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int middleOfThumb = thumbRect.x + (thumbRect.width / 2) -
                                paintRect.x;
            int fillMinX;
            int fillMaxX;

            if (middleOfThumb > 0) {
                g.setColor(chooseColor(drawInverted,
                           HokageLookAndFeel.getPrimaryControlDarkShadow(),
                           HokageLookAndFeel.getControlDarkShadow()));
               g.drawRect(0, 0, middleOfThumb - 1, h - 1);
            }
            if (middleOfThumb < w) {
                g.setColor(chooseColor(drawInverted,
                           HokageLookAndFeel.getControlDarkShadow(),
                           HokageLookAndFeel.getPrimaryControlDarkShadow()));
                g.drawRect(middleOfThumb, 0, w - middleOfThumb - 1, h - 1);
            }
            g.setColor(HokageLookAndFeel.getPrimaryControlShadow());
            if (drawInverted) {
                fillMinX = middleOfThumb;
                fillMaxX = w - 2;
                g.drawLine(1, 1, middleOfThumb, 1);
            }
            else {
                fillMinX = 1;
                fillMaxX = middleOfThumb;
                g.drawLine(middleOfThumb, 1, w - 1, 1);
            }
            if (h == 6) {
                g.setColor(HokageLookAndFeel.getWhite());
                g.drawLine(fillMinX, 1, fillMaxX, 1);
                g.setColor(sliderAltTrackColor);
                g.drawLine(fillMinX, 2, fillMaxX, 2);
                g.setColor(HokageLookAndFeel.getControlShadow());
                g.drawLine(fillMinX, 3, fillMaxX, 3);
                g.setColor(HokageLookAndFeel.getPrimaryControlShadow());
                g.drawLine(fillMinX, 4, fillMaxX, 4);
            }
        }
        else {
            int middleOfThumb = thumbRect.y + (thumbRect.height / 2) -
                                paintRect.y;
            int fillMinY;
            int fillMaxY;

            if (middleOfThumb > 0) {
                g.setColor(chooseColor(drawInverted,
                           HokageLookAndFeel.getControlDarkShadow(),
                           HokageLookAndFeel.getPrimaryControlDarkShadow()));
                g.drawRect(0, 0, w - 1, middleOfThumb - 1);
            }
            if (middleOfThumb < h) {
                g.setColor(chooseColor(drawInverted,
                           HokageLookAndFeel.getPrimaryControlDarkShadow(),
                           HokageLookAndFeel.getControlDarkShadow()));
                g.drawRect(0, middleOfThumb, w - 1, h - middleOfThumb - 1);
            }
            g.setColor(HokageLookAndFeel.getPrimaryControlShadow());
            if (drawInverted()) {
                fillMinY = 1;
                fillMaxY = middleOfThumb;
                if (leftToRight) {
                    g.drawLine(1, middleOfThumb, 1, h - 1);
                }
                else {
                    g.drawLine(w - 2, middleOfThumb, w - 2, h - 1);
                }
            }
            else {
                fillMinY = middleOfThumb;
                fillMaxY = h - 2;
                if (leftToRight) {
                    g.drawLine(1, 1, 1, middleOfThumb);
                }
                else {
                    g.drawLine(w - 2, 1, w - 2, middleOfThumb);
                }
            }
            if (w == 6) {
                g.setColor(chooseColor(!leftToRight,
                           HokageLookAndFeel.getWhite(),
                           HokageLookAndFeel.getPrimaryControlShadow()));
                g.drawLine(1, fillMinY, 1, fillMaxY);
                g.setColor(chooseColor(!leftToRight, sliderAltTrackColor,
                           HokageLookAndFeel.getControlShadow()));
                g.drawLine(2, fillMinY, 2, fillMaxY);
                g.setColor(chooseColor(!leftToRight,
                           HokageLookAndFeel.getControlShadow(),
                           sliderAltTrackColor));
                g.drawLine(3, fillMinY, 3, fillMaxY);
                g.setColor(chooseColor(!leftToRight,
                           HokageLookAndFeel.getPrimaryControlShadow(),
                           HokageLookAndFeel.getWhite()));
                g.drawLine(4, fillMinY, 4, fillMaxY);
            }
        }

        g.translate(-paintRect.x, -paintRect.y);
    }

    public void paintFocus(Graphics g)  {        
    }

    protected Dimension getThumbSize() {
        Dimension size = new Dimension();

        if ( slider.getOrientation() == JSlider.VERTICAL ) {
	    size.width = vertThumbIcon.getIconWidth();
	    size.height = vertThumbIcon.getIconHeight();
	}
	else {
	    size.width = horizThumbIcon.getIconWidth();
	    size.height = horizThumbIcon.getIconHeight();
	}

	return size;
    }

    /**
     * Gets the height of the tick area for horizontal sliders and the width of the
     * tick area for vertical sliders.  BasicSliderUI uses the returned value to
     * determine the tick area rectangle.
     */
    public int getTickLength() {
        return slider.getOrientation() == JSlider.HORIZONTAL ? tickLength + TICK_BUFFER + 1 :
        tickLength + TICK_BUFFER + 3;
    }

    /**
     * Returns the shorter dimension of the track.
     */
    protected int getTrackWidth() {
        // This strange calculation is here to keep the
        // track in proportion to the thumb.
        final double kIdealTrackWidth = 7.0;
	final double kIdealThumbHeight = 16.0;
        final double kWidthScalar = kIdealTrackWidth / kIdealThumbHeight;

        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
	    return (int)(kWidthScalar * thumbRect.height);
	}
	else {
	    return (int)(kWidthScalar * thumbRect.width);
	}
    }

    /**
     * Returns the longer dimension of the slide bar.  (The slide bar is only the
     * part that runs directly under the thumb)
     */
    protected int getTrackLength() {   
        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            return trackRect.width; 
        }
        return trackRect.height;
    }

    /**
     * Returns the amount that the thumb goes past the slide bar.
     */
    protected int getThumbOverhang() {
        return (int)(getThumbSize().getHeight()-getTrackWidth())/2;
    }

    protected void scrollDueToClickInTrack( int dir ) {
        scrollByUnit( dir );
    }

    protected void paintMinorTickForHorizSlider( Graphics g, Rectangle tickBounds, int x ) {
        g.setColor( slider.isEnabled() ? slider.getForeground() : HokageLookAndFeel.getControlShadow() );
        g.drawLine( x, TICK_BUFFER, x, TICK_BUFFER + (tickLength / 2) );
    }

    protected void paintMajorTickForHorizSlider( Graphics g, Rectangle tickBounds, int x ) {
        g.setColor( slider.isEnabled() ? slider.getForeground() : HokageLookAndFeel.getControlShadow() );
        g.drawLine( x, TICK_BUFFER , x, TICK_BUFFER + (tickLength - 1) );
    }

    protected void paintMinorTickForVertSlider( Graphics g, Rectangle tickBounds, int y ) {
        g.setColor( slider.isEnabled() ? slider.getForeground() : HokageLookAndFeel.getControlShadow() );

	if (HokageUtils.isLeftToRight(slider)) {
	    g.drawLine( TICK_BUFFER, y, TICK_BUFFER + (tickLength / 2), y );
	}
	else {
	    g.drawLine( 0, y, tickLength/2, y );
	}
    }

    protected void paintMajorTickForVertSlider( Graphics g, Rectangle tickBounds, int y ) {
        g.setColor( slider.isEnabled() ? slider.getForeground() : HokageLookAndFeel.getControlShadow() );

	if (HokageUtils.isLeftToRight(slider)) {
	    g.drawLine( TICK_BUFFER, y, TICK_BUFFER + tickLength, y );
	}
	else {
	    g.drawLine( 0, y, tickLength, y );
	}
    }
}
