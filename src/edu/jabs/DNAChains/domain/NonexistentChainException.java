package edu.jabs.DNAChains.domain;

/**
 * This exception points when the program tries to operate an inexistent chain
 */
public class NonexistentChainException extends Exception
{
    /**
     * Builds the exception pointing that there is not a chain with the given description
     * @param message The chain description
     */
    public NonexistentChainException( String message )
    {
        super( message );
    }

}
