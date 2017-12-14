package edu.jabs.DNAChains.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class represents the nitrogenous bases. <br>
 * The nitrogenous bases are organized in a doubly-linked list <br>
 * Chain knows the first and the last bases.<br>
 * The chains are organized in a singly-linked list.<br>
 * <b>inv: </b> <br>
 * description != null<br>
 * if firstBase == null then lastBase == null <br>
 * firstBase.getPrevious() == null <br>
 * lastBase.next() == null <br>
 * For each base, if base.next() != null then base.darSiguiente().getPrevious() == base<br>
 * For each base, if base.getPrevious() != null then base.getPrevious().next() == base<br>
 * If it starts with firstBase, there has to be a lastBase.
 */
public class Chain
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * The next chain that the processor has
     */
    private Chain next;

    /**
     * The chain first base
     */
    private NitrogenousBase firstBase;

    /**
     * The chain last base
     */
    private NitrogenousBase lastBase;

    /**
     * Description of this chain
     */
    private String description;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Builds a new empty chain
     * @param desc Description of this chain
     */
    public Chain( String desc )
    {
        description = desc;
        firstBase = null;
        verifyInvariant( );
    }

    /**
     * Builds a chain from the data in a file
     * @param br Stream that allows the reading of the chain
     * @throws IOException Throws the exception if there is a problem reading the chain from the file
     * @throws IncorrectChainException if the file has not the right format
     */
    public Chain( BufferedReader br ) throws IOException, IncorrectChainException
    {
        description = br.readLine( ).trim( );
        int data = 0;

        while( ( data = br.read( ) ) != -1 )
        {
            char letter = ( char )data;
            NitrogenousBase newBase = null;
            if( letter == 'A' )
                newBase = new NitrogenousBase( NitrogenousBase.ADENINE );
            else if( letter == 'G' )
                newBase = new NitrogenousBase( NitrogenousBase.GUANINE );
            else if( letter == 'T' )
                newBase = new NitrogenousBase( NitrogenousBase.THYMINE );
            else if( letter == 'C' )
                newBase = new NitrogenousBase( NitrogenousBase.CYTOSINE );
            else
                throw new IncorrectChainException( "The file has incorrect information: " + letter );

            if( lastBase == null )
            {
                lastBase = newBase;
                firstBase = newBase;
            }
            else
            {
                lastBase.insertNext( newBase );
                lastBase = newBase;
            }
        }

        verifyInvariant( );
    }

    // -----------------------------------------------------------------
    // Méthods
    // -----------------------------------------------------------------

    /**
     * Returns the next chain
     * @return the next chain
     */
    public Chain next( )
    {
        return next;
    }

    /**
     * changes the next chain
     * @param newNext the chain that will be the next chain
     */
    public void setNext( Chain newNext )
    {
        next = newNext;

        verifyInvariant( );
    }

    /**
     * Returns the first base of the list of nitrogenous bases
     * @return The first base of the list
     */
    public NitrogenousBase getFirstBase( )
    {
        return firstBase;
    }

    /**
     * Returns the last base of the list of nitrogenous bases
     * @return the last base of the list
     */
    public NitrogenousBase getLastBase( )
    {
        return lastBase;
    }

    /**
     * Returns the description of this chain
     * @return the description
     */
    public String getDescription( )
    {
        return description;
    }

    /**
     * Adds a base at the end of the chain
     * @param newBase the base to be added at the end of the chain - newBase != null
     */
    public void addBase( NitrogenousBase newBase )
    {
        if( firstBase == null )
        {
            firstBase = newBase;
            newBase.setPrevious( null );
            lastBase = newBase;
            newBase.setNext( null );
        }
        else
        {
            lastBase.insertNext( newBase );
            lastBase = newBase;
            newBase.setNext( null );
        }

        verifyInvariant( );
    }

    /**
     * Saves a chain in a file
     * @param out The stream in which the information of the chain will be written - out != null
     */
    public void saveChain( PrintWriter out )
    {
        out.println( description );

        NitrogenousBase temp = firstBase;
        while( temp != null )
        {
            if( temp.getType( ) == NitrogenousBase.ADENINE )
                out.print( "A" );
            else if( temp.getType( ) == NitrogenousBase.THYMINE )
                out.print( "T" );
            else if( temp.getType( ) == NitrogenousBase.GUANINE )
                out.print( "G" );
            else if( temp.getType( ) == NitrogenousBase.CYTOSINE )
                out.print( "C" );

            temp = temp.getNext( );
        }
    }

    /**
     * Returns the number of nitrogenous bases that the chain has
     * @return the number of nitrogenous bases
     */
    public int getLength( )
    {
        int length = 0;

        NitrogenousBase temp = firstBase;
        while( temp != null )
        {
            length++;
            temp = temp.getNext( );
        }

        return length;
    }

    /**
     * Adds to this chain the nitrogenous bases from another chain
     * @param otherChain the chain with the nitrogenous bases to be added - otherChain != null
     */
    public void addBaseToChain( Chain otherChain )
    {
        NitrogenousBase temp = otherChain.getFirstBase( );
        while( temp != null )
        {
            NitrogenousBase newBase = new NitrogenousBase( temp.getType( ) );

            if( firstBase == null )
            {
                firstBase = newBase;
                lastBase = newBase;
            }
            else
            {
                lastBase.insertNext( newBase );
                lastBase = newBase;
            }

            temp = temp.getNext( );
        }

        verifyInvariant( );
    }

    /**
     * Builds a new chain in which the sequence of mutatedChain is replaced for replacementChain<br>
     * The corrected chain has the description "originalDesciption - corrected"
     * @param mutatedChain The sequence identified as a mutation - mutatedChain != null
     * @param replacementChain The sequence that has to replace all occurrences of the mutated chain - replacementChain != null
     * @return the chain with the mutations corrected
     */
    public Chain createCorrectedChain( NitrogenousBase mutatedChain, NitrogenousBase replacementChain )
    {
        Chain c = new Chain( description + " - corrected" );

        NitrogenousBase temp = firstBase;
        while( temp != null )
        {
            if( startSequence( temp, mutatedChain ) )
            {
                // Copies to the new chain the replacement sequence
                NitrogenousBase tempReplacement = replacementChain;
                while( tempReplacement != null )
                {
                    NitrogenousBase newBase = new NitrogenousBase( tempReplacement.getType( ) );
                    c.addBase( newBase );
                    tempReplacement = tempReplacement.getNext( );
                }

                // Moves forward on the original chain
                NitrogenousBase tempMutation = mutatedChain;
                while( tempMutation != null && temp!=null )
                {
                    tempMutation = tempMutation.getNext( );

                    temp = temp.getNext( );
                }

            }
            else
            {
                NitrogenousBase newBase = new NitrogenousBase( temp.getType( ) );
                c.addBase( newBase );
                temp = temp.getNext( );
            }
        }

        return c;
    }
    /**
     * Points if from the indicated chain (chainStart) starts a sequence of nitrogenous bases equal to the indicated mutated chain
     * @param chainStart The base from the original chain in which the mutation will be searched - chainStart != null
     * @param mutatedChain The sequence to be searched - cadenaMutada != null
     * @return true if from chainStart a mutated sequence of nitrogenous bases is foundedn
     */
    private boolean startSequence( NitrogenousBase chainStart, NitrogenousBase mutatedChain )
    {
        NitrogenousBase temp1 = chainStart;
        NitrogenousBase temp2 = mutatedChain;
        boolean thisSequence = true;

        while( thisSequence && temp1 != null && temp2 != null )
        {
            if( temp1.getType( ) != temp2.getType( ) )
            {
                thisSequence = false;
            }
            else
            {
                temp1 = temp1.getNext( );
                temp2 = temp2.getNext( );
            }
        }

        if((temp1== null && temp2!=null))
        {
            thisSequence= false;
        }

        return thisSequence;
    }

    /**
     * Deleted from the chain the first fragment in which the indicated sequence is founded
     * @param searchedSequence The sequence that will be searched and deleted - searchedSequence != null
     */
    public void deleteFragment( NitrogenousBase searchedSequence )
    {
        boolean found = false;

        NitrogenousBase previous = null;
        NitrogenousBase temp = firstBase;
        while( temp != null && !found )
        {
            if( startSequence( temp, searchedSequence ) )
            {
                found = true;

                // Moves Forward on the original chain, looking for the end of the sequence
                NitrogenousBase tempSearched = searchedSequence;
                while( tempSearched != null )
                {
                    tempSearched = tempSearched.getNext( );
                    temp = temp.getNext( );
                }

                // Deletes the fragment of the chain
                if( previous != null )
                {
                    previous.setNext( temp );
                }
                else
                    firstBase = temp;

                if( temp != null )
                {
                    temp.setPrevious( previous );
                }
                else
                    lastBase = previous;
            }
            else
            {
                previous = temp;
                temp = temp.getNext( );
            }
        }

        verifyInvariant( );
    }

    /**
     * Returns the longest common chain between this chain and otherChain
     * @param otherChain The chain in which the common chain will be searched - otherChain != null
     * @return the longest common chain with the description "CommonChain"
     */
    public Chain searchCommonSequence( Chain otherChain )
    {
        int maxCommonLength = 0;
        int maxCurrentLength = 0;
        Chain maxCommonChain = new Chain( "CommonChain" );
        Chain currentCommonChain = null;

        NitrogenousBase temp1 = firstBase;

        while( temp1 != null )
        {
            maxCurrentLength = 0;
            currentCommonChain = null;

            NitrogenousBase temp2 = otherChain.getFirstBase( );
            while( temp2 != null )
            {
                if( temp1.getType( ) == temp2.getType( ) )
                {
                    currentCommonChain = searchCommonSequence( temp1, temp2 );
                    maxCurrentLength = currentCommonChain.getLength( );

                    if( maxCurrentLength > maxCommonLength )
                    {
                        maxCommonLength = maxCurrentLength;
                        maxCommonChain = currentCommonChain;
                    }
                }

                temp2 = temp2.getNext( );
            }

            temp1 = temp1.getNext( );
        }

        return maxCommonChain;
    }

    /**
     * Finds the common sequence at the start of two chains that start with equal bases
     * @param chain1Start The first chain in which the sequence will be searched - chain1Start != null
     * @param chain2Start The second chain in which the sequence will be searched - chain2Start != null
     * @return The first common sequence between the chains
     */
    private Chain searchCommonSequence( NitrogenousBase chain1Start, NitrogenousBase chain2Start )
    {
        Chain commonChain = new Chain( "CommonChain" );

        NitrogenousBase temp1 = chain1Start;
        NitrogenousBase temp2 = chain2Start;

        while( temp1 != null && temp2 != null && temp1.getType( ) == temp2.getType( ) )
        {
            NitrogenousBase newBase = new NitrogenousBase( temp1.getType( ) );
            commonChain.addBase( newBase );

            temp1 = temp1.getNext( );
            temp2 = temp2.getNext( );
        }

        return commonChain;
    }

    /**
     * Points if the chain is coherent.<br>
     * A chain is coherent if the bases could be paired so that a tymine joins a adenine, and a guanine joins a cytosin <br>
     * The pairs has to be:<br>
     * first base with last base<br>
     * second base with penultimate base<br>
     * third base with ante-penultimate base<br>
     * etc...<br>
     * @return true if the chain is coherent
     */
    public boolean isCoherent( )
    {
        boolean isCoherent = true;

        NitrogenousBase temp1 = firstBase;
        NitrogenousBase temp2 = lastBase;

        while( temp1.getNext( ) != temp2 && temp1 != temp2 && isCoherent )
        {
            if( !temp1.canBond( temp2 ) )
            {
                isCoherent = false;
            }

            temp1 = temp1.getNext( );
            temp2 = temp2.getPrevious( );
        }

        if( temp1 == temp2 )
            isCoherent = false;

        return isCoherent;
    }

    /**
     * Inserts a chain after the current one. <br>
     * <b>post: </b> The specified chain is inserted after the current one. <br>
     * @param chain to be inserted - chain!=null
     */
    public void insertNext( Chain chain )
    {
        chain.next = next;
        next = chain;
    }

    /**
     * Disconnect the chain that follows in the list of the current chain. <br>
     * <b>post: </b> The Next chain was disconnected from the list.<br>
     */
    public void disconnectNext( )
    {
        next = next.next;
    }

    /**
     * Creates a string representation of the chain
     * @return the String representation of the chain
     */
    public String toString( )
    {
        StringBuffer sb = new StringBuffer( );

        NitrogenousBase temp = firstBase;
        while( temp != null )
        {
            if( temp.getType( ) == NitrogenousBase.ADENINE )
                sb.append( "A" );
            else if( temp.getType( ) == NitrogenousBase.THYMINE )
                sb.append( "T" );
            else if( temp.getType( ) == NitrogenousBase.GUANINE )
                sb.append( "G" );
            else if( temp.getType( ) == NitrogenousBase.CYTOSINE )
                sb.append( "C" );

            temp = temp.getNext( );
        }

        return sb.toString( );
    }

    // -----------------------------------------------------------------
    // Invariant
    // -----------------------------------------------------------------

    /**
     * Verifies the class invariant<br>
     * <b>inv: </b> <br>
     * description != null<br>
     * if firstBase == null then lastBase == null <br>
     * firstBase.getPrevious() == null <br>
     * lastBase.next() == null <br>
     * For each base, if base.next() != null then base.darSiguiente().getPrevious() == base<br>
     * For each base, if base.getPrevious() != null then base.getPrevious().next() == base<br>
     * If it starts with firstBase, there has to be a lastBase.
     */
    private void verifyInvariant( )
    {
        assert description != null : "The first description cannot be null";

        if( firstBase != null )
            assert firstBase.getPrevious( ) == null : "The first base can not have a Previous";

        if( lastBase != null )
            assert lastBase.getNext( ) == null : "The last base can not have a Next";

        assert ( firstBase == null ) == ( lastBase == null ) : "The ends of the chains are not well built";

        // Verificar que la cadena esté bien construida
        NitrogenousBase temp = firstBase;
        while( temp != null )
        {
            if( temp.getNext( ) != null )
            {
                assert ( temp.getNext( ).getPrevious( ) == temp ) : "The chain is not well built";
            }

            if( temp.getPrevious( ) != null )
            {
                assert ( temp.getPrevious( ).getNext( ) == temp ) : "The chain is not well built";
            }

            temp = temp.getNext( );
        }

        // Verifies that there is a path between first base and last base
        temp = firstBase;
        while( temp != null && temp.getNext( ) != null )
        {
            temp = temp.getNext( );
        }
        assert temp == lastBase : "There is not a path between the first base and the last base";
    }

}