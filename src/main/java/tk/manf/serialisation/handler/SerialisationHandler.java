package tk.manf.serialisation.handler;

import java.io.File;
import java.io.IOException;
import tk.manf.serialisation.annotations.Unit;

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
}
