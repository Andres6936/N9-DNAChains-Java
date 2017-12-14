package edu.jabs.DNAChains.domain;

/**
 * This exceptions points the building of a chain with incorrect values
 */
public class IncorrectChainException extends Exception
{
    /**
     * Builds the exception with the indicated message
     * @param message the description of the problem
     */
    public IncorrectChainException( String message )
    {
        super( message );
    }
}
