package edu.jabs.DNAChains.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import junit.framework.TestCase;

import edu.jabs.DNAChains.domain.Chain;
import edu.jabs.DNAChains.domain.NitrogenousBase;

/**
 * Class that verifies the methods from Chain class
 */
public class ChainTest extends TestCase
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Object for doing the tests
     */
    private Chain chain1;

    /**
     * Object for doing the tests
     */
    private Chain chain2;

    /**
     * Chain with a mutation
     */
    private Chain mutatedChain;

    /**
     * Chain that is a mutation
     */
    private Chain mutationChain;

    /**
     * Chain that has to replace the mutation
     */
    private Chain correctionChain;

    /**
     * Fragment to be eliminated from a chain
     */
    private Chain fragmentChain;

    /**
     * A coherent chain
     */
    private Chain coherentChain;

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Builds a new empty chain
     */
    private void setupScenario1( )
    {
        chain1 = new Chain( "start" );
    }

    /**
     * Builds a pair of chain from file information
     */
    private void setupScenario2( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/chain1.dna" ) );
            chain1 = new Chain( br1 );
            br1.close( );

            BufferedReader br2 = new BufferedReader( new FileReader( "./test/data/chain2.dna" ) );
            chain2 = new Chain( br2 );
            br2.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds a single chain from file information
     */
    private void setupScenario3( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/simple.dna" ) );
            chain1 = new Chain( br1 );
            br1.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds a pair of chain from file information
     */
    private void setupScenario4( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/chainA.dna" ) );
            chain1 = new Chain( br1 );
            br1.close( );

            BufferedReader br2 = new BufferedReader( new FileReader( "./test/data/chainC.dna" ) );
            chain2 = new Chain( br2 );
            br2.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds some chains for testing correction of mutated chains
     */
    private void setupScenario5( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/mutatedChain.dna" ) );
            mutatedChain = new Chain( br1 );
            br1.close( );

            BufferedReader br2 = new BufferedReader( new FileReader( "./test/data/mutation.dna" ) );
            mutationChain = new Chain( br2 );
            br2.close( );

            BufferedReader br3 = new BufferedReader( new FileReader( "./test/data/correction.dna" ) );
            correctionChain = new Chain( br3 );
            br3.close( );

        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds some chains for testing the elimination of fragments
     */
    private void setupScenario6( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/chain2.dna" ) );
            chain1 = new Chain( br1 );
            br1.close( );

            BufferedReader br2 = new BufferedReader( new FileReader( "./test/data/fragment.dna" ) );
            fragmentChain = new Chain( br2 );
            br2.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds a chain for testing coherence
     */
    private void setupScenario7( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/coherent.dna" ) );
            coherentChain = new Chain( br1 );
            br1.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds some chains for testing the deleting of a fragment
     */
    private void setupScenario8( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/chain1.dna" ) );
            chain1 = new Chain( br1 );
            br1.close( );

            BufferedReader br2 = new BufferedReader( new FileReader( "./test/data/fragment2.dna" ) );
            fragmentChain = new Chain( br2 );
            br2.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds some chains for testing the deleting of a fragment
     */
    private void setupScenario9( )
    {
        try
        {
            BufferedReader br1 = new BufferedReader( new FileReader( "./test/data/chain1.dna" ) );
            chain1 = new Chain( br1 );
            br1.close( );

            BufferedReader br2 = new BufferedReader( new FileReader( "./test/data/fragment3.dna" ) );
            fragmentChain = new Chain( br2 );
            br2.close( );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }
    /**
     * Verifies the constructor of an empty chain.<br>
     * <b> Methods for testing: </b> <br>
     * Chain (constructor) <br>
     * <b> Objective: </b> Test the creation of the chain with the description and without nitrogenous bases. <br>
     * <b> Expected results: </b> <br>
     * 1. The chain must have a description equal to the defined in the scenario. <br>
     * 2. The chain has not first base, the reference to the first base is null. <br>
     * 3. The chain has not last base, the reference to the last base is null. <br>
     * 4. The chain has not next, the reference to the next chain is null.
     */
    public void testChain1( )
    {
        setupScenario1( );

        assertEquals( "Description is not the expected", "start", chain1.getDescription( ) );
        assertNull( "Chain should be empty", chain1.getFirstBase( ) );
        assertNull( "Chain should be empty", chain1.getLastBase( ) );
        assertNull( "Chain should not be bond", chain1.next( ) );
    }

    /**
     * Verifies the constructor of a chain from file. <br>
     * <b> Methods for testing: </b> <br>
     * Chain( BufferedReader br )- constructor from file <br>
     * <b> Objective: </b> Testing if the chain is well built from file. <br>
     * <b> Expected results: </b> <br>
     * 1. The description of the chain must be the defined in the scenario. <br>
     * 2. The chain must not be empty in the first base, it must not return null. <br>
     * 3. The chain must no be empty in the last base, it must not return null. <br>
     * 4. The chain must not be chained to a next, the next must be null. <br>
     * 5. The sequence of bases must correspond to the file sequence "ATCGATCG".
     */

    public void testChain2( )
    {
        setupScenario3( );

        assertEquals( "Description is not the expected", "simple", chain1.getDescription( ) );
        assertNotNull( "Chain should be empty", chain1.getFirstBase( ) );
        assertNotNull( "Chain should be empty", chain1.getLastBase( ) );
        assertNull( "Chain should not be bound", chain1.next( ) );

        String strChain = "ATCGATCG";
        boolean equals = compareChainWithString( chain1, strChain );
        assertTrue( "Chains sould be equal", equals );
    }

    /**
     * Verifies the correct adding of the bases. <br>
     * <b> Methods for testing: </b> <br>
     * addBase <br>
     * <b> Objective: </b> Testing that a base is correctly added to a chain. <br>
     * <b> Expected Results: </b> <br>
     * 1. An Adenine is added, the last base must be ADENINE. <br>
     * 2. The sequence of bases must be equal to the defined on the scenario plus an ADENINE at the end.
     */
    public void testAddBase( )
    {
        setupScenario2( );

        chain1.addBase( new NitrogenousBase( NitrogenousBase.ADENINE ) );
        NitrogenousBase last = chain1.getLastBase( );
        assertEquals( "The last base type is incorrect", NitrogenousBase.ADENINE, last.getType( ) );

        String strChain = "AAAATCGGCCCACGGGCATAAATTCGAGGCTAAGCGGCTA";
        boolean equals= compareChainWithString( chain1, strChain );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Verifies the correct searching of common sequences. <br>
     * <b> Methods for testing: </b> <br>
     * searchCommonSequence <br>
     * <b> Objective: </b> Testing that the method searchCommonSequence finds the common sequences correctly. <br>
     * <b> Expected results: </b> <br>
     * 1. The common chain for the defined chains in the scenario must be ACGGGCATAAATTCGAGGC.
     */
    public void testSearchCommonSequence0( )
    {
        setupScenario2( );

        Chain commonChain = chain1.searchCommonSequence( chain2 );
        String strCommonChain = "ACGGGCATAAATTCGAGGC";
        boolean equals = compareChainWithString( commonChain, strCommonChain );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Verifies the correct searching of common sequences. <br>
     * <b> Methods for testing: </b> <br>
     * searchCommonSequence <br>
     * <b> Objective: </b> Testing that the method searchCommonSequence searches correctly the common sequences when there are not common bases between the chains. <br>
     * <b> Expected Results: </b> <br>
     * 1. The common chain for the chains defined in the scenario must be empty.
     */
    public void testSearchCommonSequence1( )
    {
        setupScenario4( );

        Chain commonChain = chain1.searchCommonSequence( chain2 );
        String strCommonChain = "";
        boolean equals = compareChainWithString( commonChain, strCommonChain );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Verifies the correct correction of chains. <br>
     * <b> Methods for testing: </b> <br>
     * createCorrectedChain <br>
     * <b> Objective: </b> Testing that the method createCorrectedChain corrects correctly the chains. <br>
     * <b> Expected Results: </b> <br>
     * 1. The correct chain for the scenario chains must be AATTAGGGCTGACGATGCAAAATTTGAGGACCAATTCTTTTGACAAAACCCGAGATGTACCAATTCTTCAGTCAGT.
     */
    public void testCreateCorrectedChain( )
    {
        setupScenario5( );

        Chain correctedChain = mutatedChain.createCorrectedChain( mutationChain.getFirstBase( ), correctionChain.getFirstBase( ) );
        String strCorrectedChain = "AATTAGGGCTGACGATGCAAAATTTGAGGACCAATTCTTTTGACAAAACCCGAGATGTACCAATTCTTCAGTCAGT";

        boolean equals = compareChainWithString( correctedChain, strCorrectedChain );
        assertTrue( "Chains should be equal", equals );

    }

    /**
     * Verifies the method getLength for an empty chain. <br>
     * <b> Methods for testing: </b> <br>
     * getLength <br>
     * <b> Objective: </b> Testing that the method getLength() returns 0 when the chain is empty. <br>
     * <b> Expected Results: </b> <br>
     * 1. The chain length must be 0.
     */
    public void testGetLength0( )
    {
        setupScenario1( );
        assertEquals( "Length is incorrect", 0, chain1.getLength( ) );
    }

    /**
     * Verifies the method getLength for a non-empty chain. <br>
     * <b> Methods for testing: </b> <br>
     * getLength <br>
     * <b> Objective: </b> Testing that the method getLength() returns the correct chain length. <br>
     * <b> Expected results: </b> <br>
     * 1. The length of "AAAATCGGCCCACGGGCATAAATTCGAGGCTAAGCGGCT" must be 39.
     */
    public void testGetLength1( )
    {
        setupScenario2( );
        assertEquals( "Length is incorrect", 39, chain1.getLength( ) );
    }

    /**
     * Verifies the correct deleting of fragments from the chains.<br>
     * <b> Methods for testing: </b> <br>
     * deleteFragment <br>
     * <b> Objective: </b> Testing that the method deleteFragment() deletes correctly the chains. <br>
     * <b> Expected Results: </b> <br>
     * 1. Deletes correctly the chain. The modified chain of the scenario must be AAGGGGAGAGAGTC.
     */
    public void testDeleteFragment0( )
    {
        setupScenario6( );

        chain1.deleteFragment( fragmentChain.getFirstBase( ) );
        String strRemaining = "AAGGGGAGAGAGTC";

        boolean equals = compareChainWithString( chain1, strRemaining );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Verifies the method deleteFragment, deleting a fragment at the start of the chain.<br>
     * <b> Methods for testing: </b> <br>
     * deleteFragment <br>
     * <b> Objective: </b> Testing that the method deleteFragment() deletes correctly the chains. <br>
     * <b> Expected Results: </b> <br>
     * 1. Deletes correctly the first four bases. The modified scenario chains must be TCGGCCCACGGGCATAAATTCGAGGCTAAGCGGCT.
     */
    public void testDeleteFragment1( )
    {
        setupScenario8( );

        chain1.deleteFragment( fragmentChain.getFirstBase( ) );
        String strRemaining = "TCGGCCCACGGGCATAAATTCGAGGCTAAGCGGCT";

        boolean equals = compareChainWithString( chain1, strRemaining );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Verifies the method deleteFragment, deleting a fragment at the end of the chain.<br>
     * <b> Methods for testing: </b> <br>
     * deleteFragment <br>
     * <b> Objective: </b> Testing that the method deleteFragment deletes correctly the chains. <br>
     * <b> Expected Results: </b> <br>
     * 1. Deletes correctly a fragment at the end of the chain. The modified scenario chain must be AAAATCGGCCCACGGGCATAAATTCG.<br>
     */
    public void testDeleteFragment2( )
    {
        setupScenario9( );

        chain1.deleteFragment( fragmentChain.getFirstBase( ) );
        String strRemaining = "AAAATCGGCCCACGGGCATAAATTCG";

        boolean equals = compareChainWithString( chain1, strRemaining );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Verifies the coherence of a chain.<br>
     * <b> Methods for testing: </b> <br>
     * isCoherent <br>
     * <b> Objective: </b> Testing that the method isCoherent() is checking correctly the coherence of a chain. <br>
     * <b> Expected Results: </b> <br>
     * 1. The method returns true for the coherent chain of the scenario.
     */
    public void testIsCoherent0( )
    {
        setupScenario7( );

        assertTrue( "The chain is coherent", coherentChain.isCoherent( ) );
    }

    /**
     * Verifies the coherence of a chain.<br>
     * <b> Methods for testing: </b> <br>
     * isCoherent <br>
     * <b> Objective: </b> Testing that the method isCoherent() coherence of a chain. <br>
     * <b> Expected Results: </b> <br>
     * 1. The methods returns false for chain1, defined as not coherent in the scenario.<br>
     * 2. The methods returns false for chain2, defined as not coherent in the scenario.
     */
    public void testIsCoherent1( )
    {
        setupScenario2( );

        assertFalse( "The chain is not coherent", chain1.isCoherent( ) );
        assertFalse( "The chain is not coherent", chain2.isCoherent( ) );
    }

    /**
     * Verifies that the chains are being saved correctly.<br>
     * <b> Methods for testing: </b> <br>
     * saveChain <br>
     * <b> Objective: </b> Testing that the method saveChain() is saving correctly the chain. <br>
     * <b> Expected Results: </b> <br>
     * 1. The saved chain must be equal to the chain defined in the scenario.
     */
    public void testSaveChain( )
    {
        setupScenario3( );

        ByteArrayOutputStream baos = new ByteArrayOutputStream( );
        PrintWriter out = new PrintWriter( baos );
        chain1.saveChain( out );
        out.close( );
        String savedChain = baos.toString( );
        String separator = System.getProperty( "line.separator" );

        assertEquals( "chain is not saved correctly", "simple" + separator + "ATCGATCG", savedChain );
    }

    /**
     * Verifies that the bases are being correctly added. <br>
     * <b> Methods for resting: </b> <br>
     * AddBasesToChain<br>
     * <b> Objective: </b> Testing that the method addBasesToAChain adds all the bases from a chain to another. <br>
     * <b> Expected Results: </b> <br>
     * 1. The bases are correctly added to chain1. The modified chain must be
     * AAAATCGGCCCACGGGCATAAATTCGAGGCTAAGCGGCTAAGGGGTTCTACGGGCATAAATTCGAGGCAATTCTTTTTTTTTGAAAACCCGAGAGAGTC.
     */
    public void testAddBasesToChain( )
    {
        setupScenario2( );

        chain1.addBaseToChain( chain2 );
        String strResult = "AAAATCGGCCCACGGGCATAAATTCGAGGCTAAGCGGCTAAGGGGTTCTACGGGCATAAATTCGAGGCAATTCTTTTTTTTTGAAAACCCGAGAGAGTC";

        boolean equals = compareChainWithString( chain1, strResult );
        assertTrue( "Chains should be equal", equals );
    }

    /**
     * Points if the String has the same bases that the chain
     * @param chain The chain that will be compared - chain != null
     * @param strChain The string that represents the chain of nitrogenous bases
     * @return true if the string is equal to the chain of nitrogenous base
     */
    private boolean compareChainWithString( Chain chain, String strChain )
    {
        boolean equals = true;

        char[] letters = strChain.toCharArray( );
        NitrogenousBase temp = chain.getFirstBase( );
        int pos = 0;
        while( temp != null && pos < letters.length && equals )
        {
            if( letters[ pos ] == 'A' && NitrogenousBase.ADENINE != temp.getType( ) )
                equals = false;

            else if( letters[ pos ] == 'T' && NitrogenousBase.THYMINE != temp.getType( ) )
                equals = false;

            else if( letters[ pos ] == 'G' && NitrogenousBase.GUANINE != temp.getType( ) )
                equals = false;

            else if( letters[ pos ] == 'C' && NitrogenousBase.CYTOSINE != temp.getType( ) )
                equals = false;

            temp = temp.getNext( );
            pos++;
        }

        if( equals )
        {
            equals = ( temp == null ) && ( pos == letters.length );
        }

        return equals;
    }
}
