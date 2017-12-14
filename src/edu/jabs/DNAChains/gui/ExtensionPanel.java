package edu.jabs.DNAChains.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Panel that manages extensions
 */
public class ExtensionPanel extends JPanel implements ActionListener
{

    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Option Command 1
     */
    private static final String OPTION_1 = "OPTION_1";

    /**
     * Option Command 2
     */
    private static final String OPTION_2 = "OPTION_2";

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Application main window
     */
    private DNAChainsGUI main;

    // -----------------------------------------------------------------
    // GUI attributes
    // -----------------------------------------------------------------

    /**
     * Option Button 1
     */
    private JButton optionButton1;

    /**
     * Option Button 2
     */
    private JButton optionButton2;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Panel Constructor
     * @param mainWindow  main window - mainWindow!=null
     */
    public ExtensionPanel( DNAChainsGUI mainWindow )
    {
        main = mainWindow;

        setBorder( new TitledBorder( "Options" ) );
        setLayout( new GridBagLayout( ) );

        // Option Button 1
        optionButton1 = new JButton( "Option 1" );
        optionButton1.setActionCommand( OPTION_1 );
        optionButton1.addActionListener( this );
        GridBagConstraints gbc = new GridBagConstraints( );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets( 0, 0, 0, 5 );
        add( optionButton1, gbc );

        // Option Button 2
        optionButton2 = new JButton( "Option 2" );
        optionButton2.setActionCommand( OPTION_2 );
        optionButton2.addActionListener( this );
        gbc.gridx = 1;
        add( optionButton2, gbc );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Manages the button events
     * @param e Action that generated the event.
     */
    public void actionPerformed( ActionEvent e )
    {
        if( OPTION_1.equals( e.getActionCommand( ) ) )
        {
            main.funcReqOpt1();
        }
        else if( OPTION_2.equals( e.getActionCommand( ) ) )
        {
            main.funcReqOpt2();
        }
    }

}
