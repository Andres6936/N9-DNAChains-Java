package edu.jabs.DNAChains.test;

import junit.framework.TestCase;

import edu.jabs.DNAChains.domain.NitrogenousBase;

/**
 * This class test the methods from the class NitogenousBase
 */
public class NitrogenousBaseTest extends TestCase
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * One of the nitrogenous bases wich will be used for testing<br>
     * It is ADENINE.
     */
    private NitrogenousBase baseA;

    /**
     * One of the nitrogenous bases wich will be used for testing<br>
     * It is THYMINE.
     */
    private NitrogenousBase baseT;

    /**
     * One of the nitrogenous bases wich will be used for testing<br>
     * It is GUANINE.
     */
    private NitrogenousBase baseG;

    /**
     * One of the nitrogenous bases wich will be used for testing<br>
     * It is CYTOSINE.
     */
    private NitrogenousBase baseC;

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Builds a set of nitrogenous bases
     */
    private void setupScenario1( )
    {
        baseA = new NitrogenousBase( NitrogenousBase.ADENINE );
        baseT = new NitrogenousBase( NitrogenousBase.THYMINE );
        baseG = new NitrogenousBase( NitrogenousBase.GUANINE );
        baseC = new NitrogenousBase( NitrogenousBase.CYTOSINE );
    }

    /**
     * Verifies the class constructor.<br>
     * <b> Methods for testing: </b> <br>
     * NitrogenousBases (constructor), getType. <br>
     * <b> Objective: </b> Testing that the constructor can create a correct nitrogenous base. <br>
     * <b> Expected Results: </b> <br>
     * 1. The type of the nitrogenous base must be correct. <br>
     */
    public void testNitrogenousBase( )
    {
        setupScenario1( );

        assertEquals( "Type is incorrect", NitrogenousBase.ADENINE, baseA.getType( ) );
        assertEquals( "Type is incorrect", NitrogenousBase.THYMINE, baseT.getType( ) );
        assertEquals( "Type is incorrect", NitrogenousBase.GUANINE, baseG.getType( ) );
        assertEquals( "Type is incorrect", NitrogenousBase.CYTOSINE, baseC.getType( ) );
    }

    /**
     * Verifies the method that check if two bases can be matched.<br>
     * The only alowed matches are Adenine with Thymine and Guanine with Cytosine. <br>
     * <b> Methods for testing: </b> <br>
     * canBond. <br>
     * <b> Objective: </b> Testing that the method canBond() can check if two bases match <br>
     * <b> Expected Results: </b> <br>
     * 1. When matching Adenine and Thymine the method returns true. <br>
     * 2. When matching Thymine and Adenine the method returns true. <br>
     * 3. When matching Cytosine and Guanine the method returns true. <br>
     * 4. When matching Guanine and Cytosine the method returns true. <br>
     * 5. When matching Adenine and Adenine the method returns true
     * 6. When matching Adenine and Cytosine the method returns true
     * 7. When matching Adenine and Guanine the method returns true
     * 8. When matching Thymine and Thymine the method returns true
     * 9. When matching Thymine and Cytosine the method returns true
     * 10. When matching Thymine and Guanine the method returns true
     * 11. When matching Guanine and Guanine the method returns true
     * 12. When matching Guanine and Adenine the method returns true
     * 13. When matching Guanine and Thymine the method returns true
     * 14. When matching Cytosine and Cytosine the method returns true
     * 15. When matching Cytosine and Adenine the method returns true
     * 16. When matching Cytosine and Thymine the method returns true
     */
    public void testCanBond( )
    {
        setupScenario1( );

        assertTrue( "This pair is possible", baseA.canBond( baseT ) );
        assertTrue( "This pair is possible", baseT.canBond( baseA ) );
        assertTrue( "This pair is possible", baseC.canBond( baseG ) );
        assertTrue( "This pair is possible", baseG.canBond( baseC ) );

        assertFalse( "This pair is not possible", baseA.canBond( baseA ) );
        assertFalse( "This pair is not possible", baseA.canBond( baseC ) );
        assertFalse( "This pair is not possible", baseA.canBond( baseG ) );

        assertFalse( "This pair is not possible", baseT.canBond( baseT ) );
        assertFalse( "This pair is not possible", baseT.canBond( baseC ) );
        assertFalse( "This pair is not possible", baseT.canBond( baseG ) );

        assertFalse( "This pair is not possible", baseG.canBond( baseG ) );
        assertFalse( "This pair is not possible", baseG.canBond( baseA ) );
        assertFalse( "This pair is not possible", baseG.canBond( baseT ) );

        assertFalse( "This pair is not possible", baseC.canBond( baseC ) );
        assertFalse( "This pair is not possible", baseC.canBond( baseA ) );
        assertFalse( "This pair is not possible", baseC.canBond( baseT ) );

    }
}