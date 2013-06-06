package tk.manf.serialisation.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import tk.manf.serialisation.annotations.Unit;

/**
 * 
 * 
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
     * @param <T>
     * @param c
     * @param unit
     * @param dataFolder
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException 
     */
    public <T> List<T> load(Class<T> c, Unit unit, File dataFolder) throws IllegalAccessException, InstantiationException;
}