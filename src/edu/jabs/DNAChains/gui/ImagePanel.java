package edu.jabs.DNAChains.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Panel which displays a decorative image
 */
public class ImagePanel extends JPanel
{

    // -----------------------------------------------------------------
    // GUI Attributes
    // -----------------------------------------------------------------

    /**
     * Title image
     */
    private JLabel image;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Panel Constructor
     */
    public ImagePanel( )
    {
        FlowLayout layout = new FlowLayout( );
        layout.setHgap( 0 );
        layout.setVgap( 0 );
        setLayout( layout );
        //
        // Loads the image
        ImageIcon icon = new ImageIcon( "data/titulo.png" );

        // Adds the image to the label
        image = new JLabel( "" );
        image.setIcon( icon );
        add( image );
        //
        // White color background
        setBackground( Color.WHITE );
        setBorder( new LineBorder( Color.GRAY ) );
    }
}