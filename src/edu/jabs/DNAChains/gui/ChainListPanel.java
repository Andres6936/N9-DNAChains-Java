package edu.jabs.DNAChains.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * In this panel, the chains with a image to be displayed are selected
 */
public class ChainListPanel extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * The command for the checkboxes
     */
    private static final String CHAIN_DESCRIPTION = "ChainDescription";

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Reference to the application main window
     */
    private DNAChainsGUI main;

    // -----------------------------------------------------------------
    // Interface attributes
    // -----------------------------------------------------------------

    /**
     * Checkboxes to select the displayed chains
     */
    private JCheckBox[] chkDescription;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Builds the panel
     * @param mainWindow A reference to the main application window - mainWindow!=null
     */
    public ChainListPanel( DNAChainsGUI mainWindow )
    {
        main = mainWindow;
        setLayout( new GridBagLayout( ) );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Updates the list of displayed chains
     * @param chainDescriptions The list with the chain descriptions - chainDescriptions != null
     */
    public void updateChainList( ArrayList chainDescriptions )
    {
        removeAll( );
        validate( );
        chkDescription = new JCheckBox[chainDescriptions.size( )];

        GridBagConstraints gbc = new GridBagConstraints( 0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 );
        for( int i = 0; i < chainDescriptions.size( ); i++ )
        {
            String description = ( String )chainDescriptions.get( i );
            JCheckBox descriptionBox = new JCheckBox( description );
            descriptionBox.addActionListener( this );
            descriptionBox.setActionCommand( CHAIN_DESCRIPTION );
            chkDescription[ i ] = descriptionBox;
            add( descriptionBox, gbc );
            gbc.gridy++;
        }
        repaint();
    }

    /**
     * Executes an action when a box is selected or de-selected
     * @param event The event of a change in one of the checkboxes - event != null
     */
    public void actionPerformed( ActionEvent event )
    {
        String command = event.getActionCommand( );

        if( CHAIN_DESCRIPTION.equals( command ) )
        {
            ArrayList SelectedChain = new ArrayList( );

            for( int i = 0; i < chkDescription.length; i++ )
            {
                JCheckBox box = chkDescription[ i ];
                if( box.isSelected( ) )
                    SelectedChain.add( box.getText( ) );
            }

            main.changeDisplayedChains( SelectedChain );
        }
    }
}
