package edu.jabs.DNAChains.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import edu.jabs.DNAChains.domain.Chain;
import edu.jabs.DNAChains.domain.DNAProcessor;
import edu.jabs.DNAChains.domain.IncorrectChainException;
import edu.jabs.DNAChains.domain.NitrogenousBase;
import edu.jabs.DNAChains.domain.NonexistentChainException;
import edu.jabs.DNAChains.domain.RepeatedChainException;

/**
 * Application main window.
 */
public class DNAChainsGUI extends JFrame
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Main Class
     */
    private DNAProcessor DNAProcessor;

    /**
     * Last directory in which a file was loaded
     */
    private File lastDirectory;

    /**
     * List with all the description of displayed chains
     */
    private ArrayList displayedChains;

    // -----------------------------------------------------------------
    // GUI Attributes
    // -----------------------------------------------------------------

    /**
     * Panel in which chain images are displayed
     */
    private ChainImagesPanel chainImagesPanel;

    /**
     * Panel in which available chains are displayed
     */
    private ChainListPanel listPanel;

    /**
     * Panel in which the buttons that control the application are displayed
     */
    private ButtonsPanel buttonPanel;

    /**
     * Panel with the extensions
     */
    private ExtensionPanel extensionPanel;

    /**
     * Panel with a decorative image
     */
    private ImagePanel imagePanel;

    // -----------------------------------------------------------------
    // Constructorw
    // -----------------------------------------------------------------

    /**
     * Builds the main window <br>
     * <b>post: </b> The main application window is built
     */
    public DNAChainsGUI( )
    {
        // Creates the main class
        DNAProcessor = new DNAProcessor( );

        lastDirectory = new File( "./data" );

        // Builds the shape
        setLayout( new BorderLayout( ) );
        setSize( 730, 630 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "DNA Chains" );

        // Creates the panel
        setLayout( new GridBagLayout( ) );

        GridBagConstraints gbc = new GridBagConstraints( 0, 0, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
        imagePanel = new ImagePanel( );

        add( imagePanel, gbc );

        gbc = new GridBagConstraints( 0, 1, 1, 1, 0.5, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
        chainImagesPanel = new ChainImagesPanel( );
        add( chainImagesPanel, gbc );

        gbc = new GridBagConstraints( 1, 1, 1, 2, 0.4, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
        listPanel = new ChainListPanel( this );
        JScrollPane scroll = new JScrollPane( );
        scroll.getViewport( ).add( listPanel );
        scroll.setBorder( new TitledBorder( "Chains" ) );
        add( scroll, gbc );

        gbc = new GridBagConstraints( 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
        buttonPanel = new ButtonsPanel( this );
        getContentPane( ).add( buttonPanel, gbc );

        gbc = new GridBagConstraints( 0, 3, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
        extensionPanel = new ExtensionPanel( this );
        add( extensionPanel, gbc );
        closeWindow();
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Center the window on the screen
     */
    private void closeWindow( )
    {
        Dimension dScreen = Toolkit.getDefaultToolkit( ).getScreenSize( );
        Dimension dWindow = getSize( );

        int xCorner = ( dScreen.width / 2 ) - ( dWindow.width / 2 );
        int yCorner = ( dScreen.height / 2 ) - ( dWindow.height / 2 );

        setLocation( xCorner, yCorner );
    }

    /**
     * Updates the displayed chains
     */
    private void updateDisplayedChains( )
    {
        ArrayList chains = new ArrayList( );

        if (displayedChains!=null)
        {
            for( int i = 0; i < displayedChains.size( ); i++ )
            {
                String description = ( String )displayedChains.get( i );
                Chain c = DNAProcessor.getChains( description );
                chains.add( c );
            }
        }

        chainImagesPanel.updateDisplayedChains( chains );
    }

    /**
     * Changes the displayed chains
     * @param chainDescriptions A list with the descriptions of chains that should be displayed - chainDescriptions != null
     */
    public void changeDisplayedChains( ArrayList chainDescriptions )
    {
        displayedChains = chainDescriptions;
        ArrayList chains = new ArrayList( );

        for( int i = 0; i < chainDescriptions.size( ); i++ )
        {
            String description = ( String )chainDescriptions.get( i );
            Chain c = DNAProcessor.getChains( description );
            chains.add( c );
        }

        chainImagesPanel.updateDisplayedChains( chains );
    }

    /**
     * Updates the list of chains
     */
    private void updateChainList( )
    {
        ArrayList descriptions = DNAProcessor.getChainDescriptions( );
        listPanel.updateChainList(descriptions);
        validate( );
    }

    /**
     * Loads a chain from a file selected by the user
     */
    public void loadChain( )
    {
        JFileChooser fc = new JFileChooser( lastDirectory );
        fc.setDialogTitle( "Load DNA Chain" );
        int result = fc.showOpenDialog( this );
        if( result == JFileChooser.APPROVE_OPTION )
        {
            File chainFile = fc.getSelectedFile( );
            lastDirectory = chainFile.getParentFile( );
            try
            {
                DNAProcessor.loadChain( chainFile );
                updateChainList( );
            }
            catch( IOException e )
            {
                JOptionPane.showMessageDialog( this, "An error ocurred while reading the file:\n" + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
            catch( RepeatedChainException e )
            {
                JOptionPane.showMessageDialog( this, "An error ocurred while loading the file:\n" + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
            catch( IncorrectChainException e )
            {
                JOptionPane.showMessageDialog( this, "An error ocurred while loading the file:\n" + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Saves a chain
     */
    public void saveChain( )
    {
        Chain chain = selectChain( "Save Chain" );
        if( chain != null )
        {
            JFileChooser fc = new JFileChooser( lastDirectory );
            fc.setDialogTitle( "Save DNA Chain" );
            int resultado = fc.showSaveDialog( this );
            if( resultado == JFileChooser.APPROVE_OPTION )
            {
                File chainFile = fc.getSelectedFile( );
                lastDirectory = chainFile.getParentFile( );
                try
                {
                    DNAProcessor.saveChain( chainFile, chain );
                    updateChainList( );
                }
                catch( IOException e )
                {
                    JOptionPane.showMessageDialog( this, "An error occurred while saving the file:\n" + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }
    }

    /**
     * Searches for the longest common fragment between two chains
     */
    public void searchCommonFragment( )
    {
        Chain chain1 = selectChain( "Chain # 1" );
        if( chain1 != null )
        {
            Chain chain2 = selectChain( "Chain # 2" );
            if( chain2 != null )
            {
                try
                {
                    DNAProcessor.searchCommonSequence( chain1, chain2 );
                    updateChainList( );
                }
                catch( RepeatedChainException e )
                {
                    JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }
    }

    /**
     * Deletes a fragment from a chain
     */
    public void deleteFragment( )
    {
        Chain chain1 = selectChain( "Delete Fragment" );
        if( chain1 != null )
        {
            String fragment = JOptionPane.showInputDialog( this, "Enter the sequence of bases that will be deleted.\nUse only the characters A, T, G or C." );
            if( fragment != null )
            {
                fragment = fragment.toUpperCase( ).trim( );
                if( fragment.matches( "[ATGC]*" ) && fragment.length( ) > 0 )
                {
                    NitrogenousBase first = null;
                    NitrogenousBase last = null;

                    char[] fragmentLetters = fragment.toCharArray( );
                    for( int i = 0; i < fragmentLetters.length; i++ )
                    {
                        char letter = fragmentLetters[ i ];
                        NitrogenousBase newBase = null;
                        if( letter == 'A' )
                            newBase = new NitrogenousBase( NitrogenousBase.ADENINE );
                        else if( letter == 'G' )
                            newBase = new NitrogenousBase( NitrogenousBase.GUANINE );
                        else if( letter == 'T' )
                            newBase = new NitrogenousBase( NitrogenousBase.THYMINE );
                        else if( letter == 'C' )
                            newBase = new NitrogenousBase( NitrogenousBase.CYTOSINE );

                        if( first == null )
                        {
                            first = newBase;
                            last = newBase;
                        }
                        else
                        {
                            last.setNext( newBase );
                            newBase.setPrevious( last );
                            last = newBase;
                        }
                    }

                    try
                    {
                        DNAProcessor.deleteFragment( chain1, first );
                        updateDisplayedChains( );
                    }
                    catch( NonexistentChainException e )
                    {
                        JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( this, "Only enter characters A, T, G o C", "Error", JOptionPane.ERROR_MESSAGE );
                }

            }
        }
    }

    /**
     * Deletes a chain
     */
    public void deleteChain( )
    {
        Chain chain = selectChain( "Delete Chain" );
        if( chain != null )
        {
            try
            {
                DNAProcessor.deleteChain( chain.getDescription( ) );
                boolean found = false;
                if(displayedChains!=null)
                    for( int i = 0; i < displayedChains.size( ) && !found; i++ )
                    {
                        String displayedDescription = ( String )displayedChains.get( i );
                        if( displayedDescription.equals( chain.getDescription( ) ) )
                        {
                            found = true;
                            displayedChains.remove( displayedDescription );
                        }
                    }
            }
            catch( NonexistentChainException e )
            {
                JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }

        updateChainList( );
        updateDisplayedChains( );
    }

    /**
     * Corrects a mutation from a chain
     */
    public void correctMutation( )
    {
        try
        {
            Chain chain1 = selectChain( "Mutated Chain" );
            if( chain1 != null )
            {
                String mutationChain = JOptionPane.showInputDialog( this, "Enter the sequence of bases that is a mutation.\nUse only characters A, T, G or C." );
                if( mutationChain != null )
                {
                    String correctionChain = JOptionPane.showInputDialog( this, "Enter the sequence of bases to correct the mutation.\nUse only characters A, T, G or C." );
                    if( correctionChain != null )
                    {
                        NitrogenousBase mutationFragment = buildBasesChain( mutationChain );
                        NitrogenousBase correctionFragment = buildBasesChain( correctionChain );

                        DNAProcessor.createCorrectedChain( chain1, mutationFragment, correctionFragment );
                        updateChainList( );
                    }
                }
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Builds a chain of NitrogenousBases from a String
     * @param string The string to be used for building the chain of bases
     * @return The chain of NitrogenousBases built from the String
     * @throws IncorrectChainException if the string has non-valid characters
     */
    private NitrogenousBase buildBasesChain( String string ) throws IncorrectChainException
    {
        string = string.toUpperCase( ).trim( );
        if( string.matches( "[ATGC]*" ) && string.length( ) > 0 )
        {
            NitrogenousBase first = null;
            NitrogenousBase last = null;

            char[] fragmentLetters = string.toCharArray( );
            for( int i = 0; i < fragmentLetters.length; i++ )
            {
                char letter = fragmentLetters[ i ];
                NitrogenousBase newBase = null;
                if( letter == 'A' )
                    newBase = new NitrogenousBase( NitrogenousBase.ADENINE );
                else if( letter == 'G' )
                    newBase = new NitrogenousBase( NitrogenousBase.GUANINE );
                else if( letter == 'T' )
                    newBase = new NitrogenousBase( NitrogenousBase.THYMINE );
                else if( letter == 'C' )
                    newBase = new NitrogenousBase( NitrogenousBase.CYTOSINE );

                if( first == null )
                {
                    first = newBase;
                    last = newBase;
                }
                else
                {
                    last.setNext( newBase );
                    newBase.setPrevious( last );
                    last = newBase;
                }
            }

            return first;
        }
        else
            throw new IncorrectChainException( "Chains can only be built with A, T, G or C" );
    }

    /**
     * Adds to a chain, the bases from another chain
     */
    public void addBases( )
    {
        Chain c1 = selectChain( "Chain that will be extended" );
        if( c1 != null )
        {
            Chain c2 = selectChain( "Chain that will be added" );
            if( c2 != null )
            {
                if( !c1.equals( c2 ) )
                {
                    try
                    {
                        DNAProcessor.addBasesToChain( c1, c2 );
                        updateDisplayedChains( );

                    }
                    catch( NonexistentChainException e )
                    {
                        JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog( this, "The chain added can not be the same chain to be extended", "Add bases", JOptionPane.INFORMATION_MESSAGE );
                }
            }
        }
    }

    /**
     * Shows a dialog for selecting a chain
     * @param title The title to be displayed on the dialog - title != null
     * @return The chain selected by the user
     */
    private Chain selectChain( String title )
    {
        ArrayList descriptions = DNAProcessor.getChainDescriptions( );
        if( descriptions.size( ) > 0 )
        {
            String selection = ( String )JOptionPane.showInputDialog( this, "Select the chain", title, JOptionPane.QUESTION_MESSAGE, null, descriptions.toArray( ), descriptions.get( 0 ) );
            if( selection != null )
            {
                Chain chain = DNAProcessor.getChains( selection );
                return chain;
            }
        }
        return null;
    }

    // -----------------------------------------------------------------
    // Extension Points
    // -----------------------------------------------------------------

    /**
     * Method for extension 1
     */
    public void funcReqOpt1( )
    {
        String answer = DNAProcessor.method1( );
        JOptionPane.showMessageDialog( this, answer, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Method for extension 2
     */
    public void funcReqOpt2( )
    {
        String answer = DNAProcessor.method2( );
        JOptionPane.showMessageDialog( this, answer, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Executs the application, creating a new GUI
     * @param args Execution Arguments.
     */
    public static void main( String[] args )
    {

        DNAChainsGUI GUI = new DNAChainsGUI( );
        GUI.setVisible( true );
    }

}
