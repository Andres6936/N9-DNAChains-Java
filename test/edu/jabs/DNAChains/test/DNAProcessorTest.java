package edu.jabs.DNAChains.test;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import edu.jabs.DNAChains.domain.Chain;
import edu.jabs.DNAChains.domain.DNAProcessor;
import edu.jabs.DNAChains.domain.NitrogenousBase;
import edu.jabs.DNAChains.domain.NonexistentChainException;
import edu.jabs.DNAChains.domain.RepeatedChainException;

/**
 * This class verifies that the methods DNAProcessor class are correctly implemented
 */
public class DNAProcessorTest extends TestCase
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * The class in which the test will be done
     */
    private DNAProcessor DNAProcessor;

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Builds a new DNAProcessor with three chains
     *
     */
    private void setupScenario1( )
    {
        try
        {
            DNAProcessor = new DNAProcessor( );
            DNAProcessor.loadChain( new File( "./test/data/chain1.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/chain2.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/chain3.dna" ) );
        }
        catch( Exception e )
        {
            fail( "There should be no exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds a new processor with four chains
     *
     */
    private void setupScenario2( )
    {
        try
        {
            DNAProcessor = new DNAProcessor( );
            DNAProcessor.loadChain( new File( "./test/data/chain1.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/chain2.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/chain3.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/fragment.dna" ) );
        }
        catch( Exception e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds a new processor with two original chains and the union between them
     *
     */
    private void setupScenario3( )
    {
        try
        {
            DNAProcessor = new DNAProcessor( );
            DNAProcessor.loadChain( new File( "./test/data/chain1.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/chain2.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/union.dna" ) );
        }
        catch( Exception e )
        {
            fail( "There should be no exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Builds a new processor with a mutated chain, the mutation, the fragment of the correction and the corrected chain
     */
    private void setupScenario4( )
    {
        try
        {
            DNAProcessor = new DNAProcessor( );
            DNAProcessor.loadChain( new File( "./test/data/mutatedChain.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/mutation.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/correction.dna" ) );
            DNAProcessor.loadChain( new File( "./test/data/correctedChain.dna" ) );
        }
        catch( Exception e )
        {
            fail( "There should be no exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Verifies the method searchCommonSequence<br>
     * <b> Methods for testing: </b> <br>
     * searchCommonSequence <br>
     * <b> Objective: </b> Testing that the method searchCommonSequence() searches correctly the common sequences. <br>
     * <b> Expected Results: </b> <br>
     * 1. Creation of the chain with correct description "CommonChain". <br>
     * 2. The common sequence (for scenario chains) result of the calling from the processor and the common sequence result from the calling from one of the chains must be equal.<br>
     */
    public void testSearchCommonSequence( )
    {
        setupScenario1( );

        Chain chain1 = DNAProcessor.getChains( "test1" );
        Chain chain2 = DNAProcessor.getChains( "test2" );
        try
        {
            DNAProcessor.searchCommonSequence( chain1, chain2 );
        }
        catch( RepeatedChainException e )
        {
            fail( "There should be no exception" );
        }

        Chain commonChain1 = chain1.searchCommonSequence( chain2 );
        Chain commonChain2 = DNAProcessor.getChains( "CommonChain" );
        assertNotNull( "The chain should be created with that name", commonChain2 );

        assertTrue( "Chains are not equals", equals( commonChain1, commonChain2 ) );
    }

    /**
     * Verifies the method createCorrectedChain<br>
     * <b> MethodsForTesting: </b> <br>
     * createCorrectedChain <br>
     * <b> Objective: </b> Testing that the method createCorrectedChain() creates corrected chains in a correct form.<br>
     * <b> Expected Results: </b> <br>
     * 1. Creation of a chain with the correct name "mutated - corrected". <br>
     * 2. The chain defined in the scenario named corrected and the result chain must be equals.
     */
    public void testCreateCorrectedChain( )
    {
        setupScenario4( );

        Chain mutated = DNAProcessor.getChains( "mutated" );
        Chain mutation = DNAProcessor.getChains( "mutation" );
        Chain correction = DNAProcessor.getChains( "correction" );
        Chain corrected = DNAProcessor.getChains( "corrected" );

        try
        {
            DNAProcessor.createCorrectedChain( mutated, mutation.getFirstBase( ), correction.getFirstBase( ) );

            Chain correctedCreated = DNAProcessor.getChains( "mutated - corrected" );
            assertNotNull( "The chain should be created with that name", correctedCreated );
            assertTrue( "Chains are not equals", areEqual( corrected, correctedCreated, false ) );
        }
        catch( RepeatedChainException e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }

    }

    /**
     * Verifies the method getChain <br>
     * <b> Methods for testing: </b> <br>
     * getChain<br>
     * <b> Objective: </b> Testing that the method getChain() returns correctly the chain.<br>
     * <b> Expected results: </b> <br>
     * 1. When a chain is asked in the scenario, given its description, a valid chain is returned. <br>
     * 2. When the description of the chain is asked, this has to be the same defined in the scenario. <br>
     * 3. When an inexisten chain is asked, the result must be null. <br>
     */
    public void testGetChain( )
    {
        setupScenario1( );

        Chain chain1 = DNAProcessor.getChains( "test1" );
        assertNotNull( "Chain with description 'test1' should be found", chain1 );
        assertEquals( "Chain description is not the expected", "test1", chain1.getDescription( ) );

        Chain chain2 = DNAProcessor.getChains( "test136" );
        assertNull( "Chain with description 'test136' should not be found", chain2 );
    }

    /**
     * Verifies the method that returns the description of the chains. <br>
     * <b> Methods for testing: </b> <br>
     * getChainDescriptions <br>
     * <b> Objective: </b> Testing that the method getChainDescriptions returns the descriptions of the chains in the processor<br>
     * <b> Expected Results: </b> <br>
     * 1. The number of descriptions must be 3. <br>
     * 2. The description returned in the position 1 of the vector must be the same that the description of the first chain.<br>
     * 3. The description returned in the position 2 of the vector must be the same that the description of the second chain.<br>
     * 4. The description returned in the position 3 of the vector must be the same that the description of the third chain.<br>
     */
    public void testGetChainDescriptions( )
    {
        setupScenario1( );

        ArrayList descriptions = DNAProcessor.getChainDescriptions( );
        assertEquals( "The ammount of descriptions is incorrect", 3, descriptions.size( ) );

        String desc1 = ( String )descriptions.get( 0 );
        assertEquals( "Chain description is incorrect", "test1", desc1 );
        String desc2 = ( String )descriptions.get( 1 );
        assertEquals( "Chain description is incorrect", "test2", desc2 );
        String desc3 = ( String )descriptions.get( 2 );
        assertEquals( "Chain description is incorrect", "test3", desc3 );
    }

    /**
     * Verifies the deleting of chains inside the processor in the initial position. <br>
     * <b> Methods for testing: </b> <br>
     * deleteChain <br>
     * <b> Objective: </b> Testing that the method deleteChain() deletes correctly the chains.<br>
     * <b> Expected Results: </b> <br>
     * 1. When the chain is deleted, the processor must have just 2 chains.<br>
     * 2. The first description in the vector must be the description of the second chain in the scenario.<br>
     * 3. The second description in the vector must be the description of the third chain in the scenario.<br>     */
    public void testDeleteChain0( )
    {
        setupScenario1( );

        try
        {
            DNAProcessor.deleteChain( "Test1" );
            ArrayList descriptions = DNAProcessor.getChainDescriptions( );
            assertEquals( "The ammount of descriptions is incorrect", 2, descriptions.size( ) );

            String desc2 = ( String )descriptions.get( 0 );
            assertEquals( "Chain description is incorrect", "test2", desc2 );
            String desc3 = ( String )descriptions.get( 1 );
            assertEquals( "Chain description is incorrect", "test3", desc3 );
        }
        catch( NonexistentChainException e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }

    }

    /**
     * Verifies the deleting of chains inside the processor in a central position. <br>
     * <b> Methods for testing: </b> <br>
     * deleteChain <br>
     * <b> Objective: </b> Testing that the method deleteChain() deletes correctly the chains.<br>
     * <b> Expected Results: </b> <br>
     * 1. When the chain is deleted, the processor must have just 2 chains.<br>
     * 2. The first description in the vector must be the description of the first chain in the scenario.<br>
     * 3. The second description in the vector must be the description of the third chain in the scenario.<br>
     */
    public void testDeleteChain1( )
    {
        setupScenario1( );

        try
        {
            DNAProcessor.deleteChain( "test2" );
            ArrayList descriptions = DNAProcessor.getChainDescriptions( );
            assertEquals( "The ammount of descriptions is incorrect", 2, descriptions.size( ) );

            String desc2 = ( String )descriptions.get( 0 );
            assertEquals( "Chain description is incorrect", "test1", desc2 );
            String desc3 = ( String )descriptions.get( 1 );
            assertEquals( "Chain description is incorrect", "test3", desc3 );
        }
        catch( NonexistentChainException e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }

    }

    /**
     * Verifies the deleting of chains inside the processor in a final position. <br>
     * <b> Methods for testing: </b> <br>
     * deleteChain <br>
     * <b> Objective: </b> Testing that the method deleteChain() deletes correctly the chains.<br>
     * <b> Expected Results: </b> <br>
     * 1. When the chain is deleted, the processor must have just 2 chains.<br>
     * 2. The first description in the vector must be the description of the first chain in the scenario.<br>
     * 3. The second description in the vector must be the description of the second chain in the scenario.<br>
     */
    public void testDeleteChain2( )
    {
        setupScenario1( );

        try
        {
            DNAProcessor.deleteChain( "test3" );

            ArrayList descriptions = DNAProcessor.getChainDescriptions( );
            assertEquals( "The ammount of descriptions is incorrect", 2, descriptions.size( ) );

            String desc1 = ( String )descriptions.get( 0 );
            assertEquals( "Chain description is incorrect", "test1", desc1 );
            String desc2 = ( String )descriptions.get( 1 );
            assertEquals( "Chain description is incorrect", "test2", desc2 );
        }
        catch( NonexistentChainException e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }

    }

    /**
     * Verifies the non-deleting of inexistent chains. <br>
     * <b> Methods for testing: </b> <br>
     * deleteChain <br>
     * <b> Objective: </b> Testing that the method deleteChain() throws exception when it tries to delete an inexistent chain.<br>
     * <b> Expected Results: </b> <br>
     * 1. The method has to throw an exception.<br>
     * 2. The first description in the vector must be the description of the first chain in the scenario.<br>
     * 3. The second description in the vector must be the description of the second chain in the scenario.<br>
     * 4. The third description in the vector must be the description of the third chain in the scenario.<br>
     */
    public void testDeleteChain3( )
    {
        setupScenario1( );

        try
        {
            DNAProcessor.deleteChain( "test 10000" );
            fail( "There should be an exception " );

        }
        catch( NonexistentChainException e )
        {
            ArrayList descripciones = DNAProcessor.getChainDescriptions( );
            assertEquals( "The ammount of descriptions is incorrect", 3, descripciones.size( ) );

            String desc1 = ( String )descripciones.get( 0 );
            assertEquals( "Chain description is incorrect", "test1", desc1 );
            String desc2 = ( String )descripciones.get( 1 );
            assertEquals( "Chain description is incorrect", "test2", desc2 );
            String desc3 = ( String )descripciones.get( 2 );
            assertEquals( "Chain description is incorrect", "test3", desc3 );
        }

    }

    /**
     * Verifies the deleting of fragments in the chains inside the processor. <br>
     * <b> Methods for testing: </b> <br>
     * deleteFragment <br>
     * <b> Objective: </b> Testing that the method deleteFragment() deletes correctly the fragment of a chain.<br>
     * <b> Expected Results: </b> <br>
     * 1. When a fragment is deleted, the number of chains is still the same.<br>
     * 2. When the chain "fragment" is deleted from the chain "test2", the description of the first chain must be "test2".<br>
     * 2. When the chain "fragment" is deleted from the chain "test2", the description of the first chain must be "test1".<br>
     * 2. When the chain "fragment" is deleted from the chain "test2", the description of the first chain must be "fragment".<br>
     * 2. When the chain "fragment" is deleted from the chain "test2", the description of the first chain must be "test3".<br>
     */
    public void testDeleteFragment( )
    {
        setupScenario2( );

        Chain chainFragment = DNAProcessor.getChains( "fragment" );
        Chain chain2 = DNAProcessor.getChains( "test2" );
        try
        {
            DNAProcessor.deleteFragment( chain2, chainFragment.getFirstBase( ) );

            ArrayList descriptions = DNAProcessor.getChainDescriptions( );
            assertEquals( "The ammount of descriptions is incorrect", 4, descriptions.size( ) );

            String desc1 = ( String )descriptions.get( 0 );
            assertEquals( "Chain description is incorrect", "test2", desc1 );
            String desc2 = ( String )descriptions.get( 1 );
            assertEquals( "Chain description is incorrect", "test1", desc2 );
            String desc3 = ( String )descriptions.get( 2 );
            assertEquals( "Chain description is incorrect", "fragment", desc3 );
            String desc4 = ( String )descriptions.get( 3 );
            assertEquals( "Chain description is incorrect", "test3", desc4 );

        }
        catch( NonexistentChainException e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }

    }

    /**
     * Verifies the correctly saving of the chains. <br>
     * <b> Methods for testing: </b> <br>
     * saveChain <br>
     * <b> Objective: </b> Testing that the method saveChain() saves correctly a chain.<br>
     * <b> ExpectedResults: </b> <br>
     * 1. The saved chain has to be the same that the original one.
     */
    public void testSaveChain( )
    {
        setupScenario1( );

        Chain chain1 = DNAProcessor.getChains( "test1" );
        try
        {
            DNAProcessor.saveChain( new File( "./test/data/temp.dna" ), chain1 );

            DNAProcessor.deleteChain( chain1.getDescription( ) );
            DNAProcessor.loadChain( new File( "./test/data/temp.dna" ) );

            Chain chain2 = DNAProcessor.getChains( "test1" );
            assertTrue( "Chain should be equal", equals( chain1, chain2 ) );
        }
        catch( Exception e )
        {
            fail( "Chain could not be saved: " + e.getMessage( ) );
        }
    }

    /**
     * Verifies the correct adding of the bases. <br>
     * <b> Methods for testing: </b> <br>
     * addBasesToChain <br>
     * <b> Objective: </b> Testing that the method addBasesToChain add a base correctly. <br>
     * <b> Expected Results: </b> <br>
     * 1. When the bases of chain "test1" are added to chain "test2", the chain "test1" must be equal to chain "union".
     */
    public void testAddBasesToChain( )
    {
        setupScenario3( );

        Chain chain1 = DNAProcessor.getChains( "test1" );
        Chain chain2 = DNAProcessor.getChains( "test2" );
        try
        {
            DNAProcessor.addBasesToChain( chain1, chain2 );
            Chain unitedChain = DNAProcessor.getChains( "union" );

            assertTrue( "The chains are not equal", areEqual( chain1, unitedChain, false ) );

        }
        catch( NonexistentChainException e )
        {
            fail( "There should not be exceptions: " + e.getMessage( ) );
        }
    }

    /**
     * Checks if two chains are equals, including their description
     * @param c1 The first chain to compare - c1 != null
     * @param c2 The second chain to compare - c2 != null
     * @return true of both chain are equal
     */
    private boolean equals( Chain c1, Chain c2 )
    {
        return areEqual( c1, c2, true );
    }

    /**
     * Checks if two chains are equal
     * @param c1 The first chain to compare - c1 != null
     * @param c2 The second chain to compare - c2 != null
     * @param checkDescriptions points if the description must be revised
     * @return true if the chains are equal
     */
    private boolean areEqual( Chain c1, Chain c2, boolean checkDescriptions )
    {
        boolean equals = c1.getDescription( ).equals( c2.getDescription( ) );
        if( !checkDescriptions )
            equals = true;

        NitrogenousBase temp1 = c1.getFirstBase( );
        NitrogenousBase temp2 = c2.getFirstBase( );
        while( temp1 != null && temp2 != null && equals )
        {
            equals = ( temp1.getType( ) == temp2.getType( ) );
            temp1 = temp1.getNext( );
            temp2 = temp2.getNext( );
        }

        return equals;
    }
}