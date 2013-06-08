package tk.manf.serialisation.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import tk.manf.serialisation.annotations.Unit;

/**
 * Interface that must be implemented by any SerialisationHandler
 * 
 * @author Björn 'manf' Heinrichs
 */
public interface SerialisationHandler {
    /**
     * Saves given Unit to Memory
     * <p/>
     * @param unit
     * @param dataFolder
     */
    public void save(Unit unit, File dataFolder, String id) throws IOException;

    /**
     * Saves current Property
     * <p/>
     * @param unit
     * @param dataFolder
     * @param string
     * @param get
     */
    public void save(Unit unit, File dataFolder, String id, String string, Object get);

    /**
     * Loads current Units from Memory
     *
     * @param c Class of Unit
     * @param unit Unit
     * @param dataFolder dataFolder of Plugin#
     * 
     * @return all Units
     * 
     * @throws Exception Depending on the Implementation any Exception may be thrown
     */
    public <T> List<T> load(Class<T> c, Unit unit, File dataFolder) throws Exception;
}