package edu.jabs.DNAChains.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * DNA Processor manages a list of chains that has to be ordered according their length<br>
 * <b>inv:</b><br>
 * For each chain, chain.getLength() <= chain.next().getLength()<br>
 * The chain is well built: there is not a chain such  chain.next() == firstChain<br>
 * The chain is well built: There are not chain1 and chain2 such that chain1.next() == chain2.next() && chain1 != chain2<br>
 * There are not two chains with the same description
 */
public class DNAProcessor
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * First chain in the list
     */
    private Chain firstChain;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Builds a new DNA Processor without chains
     */
    public DNAProcessor( )
    {
        verifyInvariant( );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Loads a new chain from a file and inserts it in the list
     * @param file The file that contains the information of the chain - file != null
     * @throws IOException of there are problems reading the file
     * @throws RepeatedChainException if there is already a chain with the same description that the new one
     * @throws IncorrectChainException if the file has not the correct format
     */
    public void loadChain( File file ) throws IOException, RepeatedChainException, IncorrectChainException
    {
        BufferedReader br = new BufferedReader( new FileReader( file ) );
        Chain newChain = new Chain( br );
        br.close( );

        if( getChains( newChain.getDescription( ) ) != null )
            throw new RepeatedChainException( newChain.getDescription( ) );

        insertChain( newChain );

        verifyInvariant( );
    }

    /**
     * Saves the chain in a file
     * @param file the file in which the chain will be saved - file != null
     * @param chain the chain to be saved - chain != null
     * @throws IOException if there are problems saving the chain
     */
    public void saveChain( File file, Chain chain ) throws IOException
    {
        PrintWriter out = new PrintWriter( file );
        chain.saveChain( out );
        out.close( );
    }

    /**
     * Returns a list with all the chain descriptions, in the same order of the chains
     * @return the list of Strings corresponding to the description of the chains
     */
    public ArrayList getChainDescriptions( )
    {
        ArrayList descriptions = new ArrayList( );

        Chain temp = firstChain;
        while( temp != null )
        {
            descriptions.add( temp.getDescription( ) );
            temp = temp.next( );
        }

        return descriptions;
    }

    /**
     * Returns the chain with the same description given as parameter
     * @param chain The description of the searched chain  - description != null
     * @return the searched chain; if it is not found, returns null
     */
    public Chain getChains( String chain )
    {
        Chain searched = null;

        Chain temp = firstChain;
        while( temp != null && searched == null )
        {
            if( temp.getDescription( ).equalsIgnoreCase( chain ) )
                searched = temp;
            else
                temp = temp.next( );
        }

        return searched;
    }

    /**
     * Inserts a chain in a list, maintaining the order of the list, according to the length of the chains<br>
     * <b>pre:</b>There is not a chain in the list with the same description. <br>
     * @param newChain the chain to be inserted - newChain != null
     */
    private void insertChain( Chain newChain )
    {
        if( firstChain == null )
        {
            firstChain = newChain;
        }
        else if( firstChain.getLength( ) > newChain.getLength( ) )
        {
            newChain.setNext( firstChain );
            firstChain = newChain;
        }
        else
        {
            Chain temp = firstChain;
            while( temp.next( ) != null && temp.next( ).getLength( ) < newChain.getLength( ) )
            {
                temp = temp.next( );
            }

            temp.insertNext( newChain );
        }
    }

    /**
     * Deletes from the list the chains with the same description that the given as parameter
     * @param description the description of the chain that will be eliminated - description != null
     * @throws NonexistentChainException If there is not a chain with the specified description
     */
    public void deleteChain( String description ) throws NonexistentChainException
    {
        if( firstChain != null && firstChain.getDescription( ).equalsIgnoreCase( description ) )
        {
            firstChain = firstChain.next( );
        }
        else if( firstChain != null )
        {

            Chain previous = searchPrevious( description );

            if( previous != null )
            {
                previous.disconnectNext( );
            }
            else
            {
                throw new NonexistentChainException( description );
            }
        }
        else
        {
            throw new NonexistentChainException( description );
        }

        verifyInvariant( );
    }

    /**
     * Searches the chain previous to the one with the specified description. <br>
     * <b>pre: </b> firstChain != null && description != firstChain.getDescription( )
     * @param description Description of the chain - description != null
     * @return the previous chain to the one with the given description. Returns null if the there is not a chain found or if it is the first in the list <br>
     */
    private Chain searchPrevious( String description )
    {
        Chain previous = firstChain;
        Chain current = firstChain.next( );
        while( current != null && !current.getDescription( ).equalsIgnoreCase( description ) )
        {
            previous = current;
            current = current.next( );
        }
        return current != null ? previous : null;
    }

    /**
     * Searches the longest commons sequence between two chains and adds it as a new chain.<br>
     * The new chain will have the description "CommonChain"
     * @param chain1 First chain to compare - chain1 != null
     * @param chain2 Second chain to compare - chain2 != null
     * @throws RepeatedChainException if there is a chain with the same description that the common sequence
     */
    public void searchCommonSequence( Chain chain1, Chain chain2 ) throws RepeatedChainException
    {
        Chain commonChain = chain1.searchCommonSequence( chain2 );

        if( getChains( commonChain.getDescription( ) ) != null )
            throw new RepeatedChainException( commonChain.getDescription( ) );

        insertChain( commonChain );

        verifyInvariant( );
    }

    /**
     * Builds a new chain in which all occurrences of the mutatedChain are replaced for sequence replacementChain <br>
     * Corrected chain has the description "originalDescription - corrected"
     * @param chain The chain in which the replacement will be done - chain != null
     * @param mutatedChain Sequence identified as a mutation - mutatedChain != null
     * @param replacementChain Sequence which has to replace all occurrences of the mutatedChain - replacementChain != null
     * @throws RepeatedChainException if there is a chain with the same description that the one with the new corrected chain
     */
    public void createCorrectedChain( Chain chain, NitrogenousBase mutatedChain, NitrogenousBase replacementChain ) throws RepeatedChainException
    {
        Chain newChain = chain.createCorrectedChain( mutatedChain, replacementChain );

        if( getChains( newChain.getDescription( ) ) != null )
            throw new RepeatedChainException( newChain.getDescription( ) );

        insertChain( newChain );

        verifyInvariant( );
    }

    /**
     * Deletes from the chain the first fragment in which the indicated sequence is found
     * @param chain The chain in which the fragment will be deleted - chain != null
     * @param searchedSequence The sequence to be searched and deleted - searchedSequence != null
     * @throws NonexistentChainException if the searched chain does not exist
     */
    public void deleteFragment( Chain chain, NitrogenousBase searchedSequence ) throws NonexistentChainException
    {
        // Deletes the chain from the list
        deleteChain( chain.getDescription( ) );

        // Deletes the fragment
        chain.deleteFragment( searchedSequence );

        // Inserts again the chain on the list
        insertChain( chain );

        verifyInvariant( );
    }

    /**
     * Adds to a chain the nitrogenous bases of another chain
     * @param chain1 The chain which the bases will be added - chain1 != null
     * @param chain2 The chain that will provide the nitrogenous bases to add to chain1 - chain2 != null
     * @throws NonexistentChainException If the chain1 does not exist in the list of chains
     */
    public void addBasesToChain( Chain chain1, Chain chain2 ) throws NonexistentChainException
    {
        // Deletes the chain from the list
        deleteChain( chain1.getDescription( ) );

        // Deletes the fragment
        chain1.addBaseToChain( chain2 );

        // Inserts again the chain to the list
        insertChain( chain1 );

        verifyInvariant( );
    }

    // -----------------------------------------------------------------
    // Invariants
    // -----------------------------------------------------------------

    /**
     * Verifies the class invariant <br>
     * <b>inv:</b><br>
     * For each chain, chain.getLength() <= chain.next().getLength()<br>
     * The chain is well built: there is not a chain such  chain.next() == firstChain<br>
     * The chain is well built: There are not chain1 and chain2 such that chain1.next() == chain2.next() && chain1 != chain2<br>
     * There are not two chains with the same description
     */
    private void verifyInvariant( )
    {
        // Verifies the order
        Chain temp = firstChain;
        while( temp != null && temp.next( ) != null )
        {
            assert temp.getLength( ) <= temp.next( ).getLength( ) : "The list is not ordered";
            temp = temp.next( );
        }

        //The list has not cycles with the first element
        if( firstChain != null )
        {
            Chain temp1 = firstChain;
            while( temp1 != null )
            {
                assert ( temp1.next( ) != firstChain ) : "The list has a cycle";
                temp1 = temp1.next( );
            }
        }

        // There are not cycles or repeated descriptions
        Chain c1 = firstChain;
        while( c1 != null )
        {
            Chain c2 = c1.next( );
            while( c2 != null )
            {
                assert ( c2.next( ) != c1 ) : "The list has a cycle";
                assert ( c2.getDescription( ) != c1.getDescription( ) ) : "The list has chains with repeated descriptions";
                c2 = c2.next( );
            }

            c1 = c1.next( );
        }
    }
    // -----------------------------------------------------------------
    // Extension points
    // -----------------------------------------------------------------

    /**
     * Method for extension 1
     * @return answer1
     */
    public String method1( )
    {
        return "Answer 1";
    }

    /**
     * Method for extension 2
     * @return answer2
     */
    public String method2( )
    {
        return "Answer 2";
    }

}