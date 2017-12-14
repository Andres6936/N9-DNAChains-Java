package edu.jabs.DNAChains.domain;

/**
 * Represents a nitrogenous base that makes a DNA Chain. <br>
 * The nitrogenous bases of a chain form a doubly-linked list.<br>
 * <b>inv: </b> <br>
 * type is {ADENINE, THYMINE, GUANINE, CYTOSINE}
 */
public class NitrogenousBase
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * ADENINE base type
     */
    public static final int ADENINE = 1;

    /**
     * THYMINE base type
     */
    public static final int THYMINE = 2;

    /**
     * GUANINE base type
     */
    public static final int GUANINE = 3;

    /**
     * CYTOSINE base type
     */
    public static final int CYTOSINE = 4;

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * the type of nitrogenous base
     */
    private int type;

    /**
     * The next base in the chain
     */
    private NitrogenousBase next;

    /**
     * The previous base in the chain
     */
    private NitrogenousBase previous;

    // -----------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------

    /**
     * Builds the nitrogenous base
     * @param baseType The type of the new nitrogenous base - baseType is one of {ADENINE, THYMINE, GUANINE, CYTOSINE}
     */
    public NitrogenousBase( int baseType )
    {
        type = baseType;

        verifyInvariant( );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Returns the type of the nitrogenous base
     * @return the type
     */
    public int getType( )
    {
        return type;
    }

    /**
     * Returns the previous base
     * @return the previous base
     */
    public NitrogenousBase getPrevious( )
    {
        return previous;
    }

    /**
     * Returns the next base
     * @return the next base
     */
    public NitrogenousBase getNext( )
    {
        return next;
    }

    /**
     * Changes the previous nitrogenous base
     * @param newPrevious the new previous base
     */
    public void setPrevious( NitrogenousBase newPrevious )
    {
        previous = newPrevious;

        verifyInvariant( );
    }

    /**
     * Changes the next nitrogenous base
     * @param newNext the new next base
     */
    public void setNext( NitrogenousBase newNext )
    {
        next = newNext;

        verifyInvariant( );
    }

    /**
     * Points if the nitrogenous base can be bound with another nitrogenous base.<br>
     * The only acceptable pairs are Adenine with Thymina and Guanine with Cytosine.
     * @param base The other base to make the pair - base != null
     * @return true if the pair can be made.
     */
    public boolean canBond( NitrogenousBase base )
    {
        boolean canBond = false;

        if( type == ADENINE && base.getType( ) == THYMINE )
            canBond = true;

        else if( type == THYMINE && base.getType( ) == ADENINE )
            canBond = true;

        else if( type == GUANINE && base.getType( ) == CYTOSINE )
            canBond = true;

        else if( type == CYTOSINE && base.getType( ) == GUANINE )
            canBond = true;

        return canBond;
    }

    /**
     * Inserts the base after the current one. <br>
     * <b>post: </b> The specified base is inserted afther the current one. <br>
     * @param base the base to be inserted - base!=null
     */
    public void insertNext( NitrogenousBase base )
    {
        base.next = next;
        if( next != null )
            next.previous = base;
        base.previous = this;
        next = base;
        verifyInvariant( );
    }

    // -----------------------------------------------------------------
    // Invariant
    // -----------------------------------------------------------------

    /**
     * Verifies the class invariant<br>
     * <b>inv: </b> <br>
     * type is one of {ADENINE, THYMINE, GUANINE, CYTOSINE}
     */
    private void verifyInvariant( )
    {
        assert ( type == ADENINE || type == THYMINE || type == GUANINE || type == CYTOSINE ) : "type is incorrect";
    }
}