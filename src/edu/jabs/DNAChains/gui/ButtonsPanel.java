package edu.jabs.DNAChains.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The panel with the buttons for controlling the applitation
 */
public class ButtonsPanel extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * The command for loading a chain
     */
    private static final String LOAD = "Load";

    /**
     * The command for saving a chain
     */
    private static final String SAVE = "Save";

    /**
     * The command for deleting a chain
     */
    private static final String DELETE = "Delete";

    /**
     * The command for adding a base to a chain
     */
    private static final String ADD_BASES = "AddBases";

    /**
     * The command for correcting mutations in a chain
     */
    private static final String CORRECT = "Correct";

    /**
     * The command for searching the longest common fragment between two chains
     */
    private static final String COMMON_FRAGMENT = "CommonFragment";

    /**
     * the command for deleting a fragment of a chain.
     */
    private static final String DELETE_FRAGMENT = "DeleteFragment";

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Reference to application main window
     */
    private DNAChainsGUI main;

    // -----------------------------------------------------------------
    // GUI Attributes
    // -----------------------------------------------------------------

    /**
     * Button "Load Chain"
     */
    private JButton LoadButton;

    /**
     * Button "Save Chain"
     */
    private JButton SaveButton;

    /**
     * Button "Delete Chain"
     */
    private JButton DeleteButton;

    /**
     * Button "Add Bases"
     */
    private JButton AddBasesButton;

    /**
     * Button "Correct Mutation"
     */
    private JButton CorrectButton;

    /**
     * Button "Common Fragment"
     */
    private JButton CommonFragmentButton;

    /**
     * Button "Delete Fragment"
     */
    private JButton DeleteFragmentButton;

    /**
     * Buton "Coherent Chain"
     */
    private JButton CoherentChainButton;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Builds the main panel
     * @param mainWindow Reference to the application main window - mainWindow!=null
     */
    public ButtonsPanel( DNAChainsGUI mainWindow )
    {
        main = mainWindow;

        setLayout( new GridBagLayout( ) );

        GridBagConstraints gbc = new GridBagConstraints( 0, 0, 2, 1, 0.3, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        LoadButton = new JButton( "Load Chain" );
        LoadButton.addActionListener( this );
        LoadButton.setActionCommand( LOAD );
        add( LoadButton, gbc );

        gbc = new GridBagConstraints( 2, 0, 2, 1, 0.3, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        SaveButton = new JButton( "Save Chain" );
        SaveButton.addActionListener( this );
        SaveButton.setActionCommand( SAVE );
        add( SaveButton, gbc );

        gbc = new GridBagConstraints( 4, 0, 2, 1, 0.3, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        DeleteButton = new JButton( "Delete Chain" );
        DeleteButton.addActionListener( this );
        DeleteButton.setActionCommand( DELETE );
        add( DeleteButton, gbc );

        gbc = new GridBagConstraints( 0, 1, 3, 1, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        AddBasesButton = new JButton( "Add Bases" );
        AddBasesButton.addActionListener( this );
        AddBasesButton.setActionCommand( ADD_BASES );
        add( AddBasesButton, gbc );

        gbc = new GridBagConstraints( 3, 1, 3, 1, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        CorrectButton = new JButton( "Correct Mutation" );
        CorrectButton.addActionListener( this );
        CorrectButton.setActionCommand( CORRECT );
        add( CorrectButton, gbc );

        gbc = new GridBagConstraints( 0, 2, 3, 1, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        CommonFragmentButton = new JButton( "Common Fragment" );
        CommonFragmentButton.addActionListener( this );
        CommonFragmentButton.setActionCommand( COMMON_FRAGMENT );
        add( CommonFragmentButton, gbc );

        gbc = new GridBagConstraints( 3, 2, 3, 1, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 );
        DeleteFragmentButton = new JButton( "Delete Fragment" );
        DeleteFragmentButton.addActionListener( this );
        DeleteFragmentButton.setActionCommand( DELETE_FRAGMENT );
        add( DeleteFragmentButton, gbc );

    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * When there is a click on a button, an action is executed
     * @param event button click event- event != null
     */
    public void actionPerformed( ActionEvent event )
    {
        String command = event.getActionCommand( );

        if( LOAD.equals( command ) )
        {
            main.loadChain();
        }
        else if( ADD_BASES.equals( command ) )
        {
            main.addBases();
        }
        else if( CORRECT.equals( command ) )
        {
            main.correctMutation();
        }
        else if( DELETE.equals( command ) )
        {
            main.deleteChain();
        }
        else if( DELETE_FRAGMENT.equals( command ) )
        {
            main.deleteFragment();
        }
        else if( COMMON_FRAGMENT.equals( command ) )
        {
            main.searchCommonFragment();
        }
        else if( SAVE.equals( command ) )
        {
            main.saveChain();
        }
    }
}
