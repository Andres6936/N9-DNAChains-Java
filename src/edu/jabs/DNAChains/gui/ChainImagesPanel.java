package edu.jabs.DNAChains.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.jabs.DNAChains.domain.Chain;
import edu.jabs.DNAChains.domain.NitrogenousBase;

/**
 * Panel in which the chain images are displayed
 */
public class ChainImagesPanel extends JPanel
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Height of a base square
     */
    private static final int BASE_HEIGHT = 10;

    /**
     * Space for a chain
     */
    private static final int CHAIN_HEIGHT = 40;

    // -----------------------------------------------------------------
    // GUI Attributes
    // -----------------------------------------------------------------

    /**
     * Panel in which the chain images are displayed
     */
    private JScrollPane scroll;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Builds the panel
     */
    public ChainImagesPanel( )
    {
        setLayout( new BorderLayout( ) );
        scroll = new JScrollPane( );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
        scroll.getViewport( ).setBackground( Color.WHITE );
        add( scroll );

        setBackground( Color.WHITE );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Updates the chains displayed
     * @param chains A list with the chains to be displayed - chains != null
     */
    public void updateDisplayedChains( ArrayList chains )
    {
        BufferedImage image = generateChainImage( chains );
        scroll.getViewport( ).removeAll( );
        scroll.getViewport( ).add( new JLabel( new ImageIcon( image ) ) );
    }

    /**
     * Builds the image that displays a set of chains
     * @param chains The list with the chains to be displayed - chains != null
     * @return A image with the chains is returned
     */
    private BufferedImage generateChainImage( ArrayList chains )
    {
        ArrayList chainImages = new ArrayList( );
        int width = 0;

        for( int i = 0; i < chains.size( ); i++ )
        {
            Chain chain = ( Chain )chains.get( i );
            BufferedImage chainImage = generateChainImage( chain );

            // Looks for the broadest image
            if( chainImage.getWidth( ) > width )
                width = chainImage.getWidth( );

            chainImages.add( chainImage );
        }

        BufferedImage chainImage = null;

        if( width > 0 )
        {
            chainImage = new BufferedImage( width, CHAIN_HEIGHT * chainImages.size( ), BufferedImage.TYPE_INT_RGB );
            Graphics2D g = chainImage.createGraphics( );
            g.setColor( Color.WHITE );
            g.fillRect( 0, 0, chainImage.getWidth( ), chainImage.getHeight( ) );

            int posY = 0;
            for( int i = 0; i < chainImages.size( ); i++ )
            {
                BufferedImage tempChainImage = ( BufferedImage )chainImages.get( i );
                g.drawImage( tempChainImage, 0, posY, null );
                posY += CHAIN_HEIGHT;
            }
        }
        else
        {
            chainImage = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_RGB );
        }
        return chainImage;
    }

    /**
     * Builds the image of a chain
     * @param chain The chain with a image to be build - chain != null
     * @return The image of a chain is returned
     */
    private BufferedImage generateChainImage( Chain chain )
    {
        int BaseWidth = 11;
        int width = Math.max( chain.getLength( ) * BaseWidth + 10, getWidth( ) );

        BufferedImage image = new BufferedImage( width, 50, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = image.createGraphics( );
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, width, 50 );

        g.setColor( Color.BLACK );
        if( chain.isCoherent( ) )
            g.drawString( chain.getDescription( ) + ": Coherent - " + chain.getLength( ) + " bases", 5, BASE_HEIGHT + 2 );
        else
            g.drawString( chain.getDescription( ) + ": NOT-Coherent - " + chain.getLength( ) + " bases", 5, BASE_HEIGHT + 2 );

        int posX = 5;
        NitrogenousBase temp = chain.getFirstBase( );

        while( temp != null )
        {
            String letter = null;
            Color color = null;
            if( temp.getType( ) == NitrogenousBase.ADENINE )
            {
                letter = "A";
                color = new Color( 0, 0, 150 );
            }
            else if( temp.getType( ) == NitrogenousBase.THYMINE )
            {
                letter = "T";
                color = new Color( 150, 150, 0 );
            }
            else if( temp.getType( ) == NitrogenousBase.GUANINE )
            {
                letter = "G";
                color = new Color( 150, 0, 0 );
            }
            else if( temp.getType( ) == NitrogenousBase.CYTOSINE )
            {
                letter = "C";
                color = new Color( 0, 150, 0 );
            }

            g.setColor( color );
            g.fillRect( posX, BASE_HEIGHT + 6, BaseWidth, BASE_HEIGHT + 8 );

            g.setColor( Color.WHITE );
            g.drawString( letter, posX + 1, 2 * BASE_HEIGHT + 9 );

            posX += BaseWidth;
            temp = temp.getNext( );
        }

        return image;
    }
}
