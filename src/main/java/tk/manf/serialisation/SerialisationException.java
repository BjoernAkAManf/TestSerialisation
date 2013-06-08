package tk.manf.serialisation;

/**
 * Generic Exception thrown whenever a serialisation fails
 * 
 * @author Björn 'manf' Heinrichs
 */
public final class SerialisationException extends Exception{
    /**
     * {@inheritDoc}
     * <b>This exception occurs whenever a serialisation failed!</b>
     */
    public SerialisationException(String msg) {
        super(msg);
    }
}