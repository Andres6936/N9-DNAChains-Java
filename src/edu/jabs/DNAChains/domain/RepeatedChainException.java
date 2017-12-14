package edu.jabs.DNAChains.domain;

/**
 * This exception points that there is a chain with repeated description
 */
public class RepeatedChainException extends Exception
{
    /**
     * Builds the description
     * @param message The repeated description - message != null
     */
    public RepeatedChainException( String message )
    {
        super( "There is another chain with the description: " + message );
    }
}